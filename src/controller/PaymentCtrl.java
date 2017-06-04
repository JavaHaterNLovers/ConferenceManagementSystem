package controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Edition;
import domain.Payment;
import domain.User;
import repo.EditionRepository;
import repo.PaymentRepository;
import util.BaseController;

@Controller
public class PaymentCtrl extends BaseController
{
    public static final String PAYMENT_SPEAKER = "payment_speaker";

    @RequestMapping(value = "/createPayment/submit/{editie}", method = RequestMethod.POST)
    @Secured({"ROLE_USER"})
    public String createPaymentSubmit(@Valid @ModelAttribute("payment") Payment payment,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr,
        @PathVariable int editie, HttpSession session
    ) {
	    EditionRepository repo = (EditionRepository) this.get("repo.edition");

        Edition ed = repo.get(editie);
        if (ed == null) {
            throw new AccessDeniedException("Editie inexistenta");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        PaymentRepository paymentRepo = (PaymentRepository) this.get("repo.payment");

        if (paymentRepo.getForUserEdition(user, ed) != null) {
            throw new AccessDeniedException("Ati platit deja pentru aceasta editie");
        }

        if (result.hasErrors()) {
            model.addAttribute("edition", editie);

            return "payment/createPayment";
        }

        payment.setEdition(ed);
        payment.setUser(user);
        payment.setSuma(Payment.sumaDePlata);

        ((PaymentRepository) this.get("repo.payment")).save(payment);

        redirAttr.addFlashAttribute("flashMessage", "Plata a fost creata cu succes!");

        if (session.getAttribute(PAYMENT_SPEAKER) != null) {
            session.setAttribute(PAYMENT_SPEAKER, null);

            return "redirect:/createProposal/" + ed.getId();
        } else {
            // for listeners
            return "redirect:/profile";
        }
    }

	 @RequestMapping(value = "/createPayment/{edition}", method = RequestMethod.GET)
	 @Secured({"ROLE_USER"})
	 public String createPayment(Model model, @PathVariable int edition, RedirectAttributes redirAttr) {
         EditionRepository repo = (EditionRepository) this.get("repo.edition");

         Edition ed = repo.get(edition);
         if (ed == null) {
             throw new AccessDeniedException("Editie inexistenta");
         }

         PaymentRepository paymentRepo = (PaymentRepository) this.get("repo.payment");

         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         User user = (User) auth.getPrincipal();

         if (paymentRepo.getForUserEdition(user, ed) != null) {
             redirAttr.addFlashAttribute("flashMessage", "Ati platit deja pentru aceasta editie");

             return "redirect:/";
         }

	     model.addAttribute("payment", new Payment());
	     model.addAttribute("edition", edition);

         return "payment/createPayment";
    }

}
