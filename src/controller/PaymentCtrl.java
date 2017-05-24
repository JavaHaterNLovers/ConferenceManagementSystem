package controller;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Payment;
import domain.User;
import repo.PaymentRepository;
import util.BaseController;

@Controller
public class PaymentCtrl extends BaseController {

	@Secured({"ROLE_CHAIR"})
    @RequestMapping(value = "/createPayment/submit", method = RequestMethod.POST)
    public String createPaymentSubmit(@Valid @ModelAttribute("payment")Payment payment,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        if (result.hasErrors()) {
            return "redirect:/createPayment";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        payment.setListener(user);
        
        ((PaymentRepository) this.get("repo.Payment")).save(payment);

        redirAttr.addFlashAttribute("flashMessage", "Plata a fost creata cu succes!");

        return "redirect:/profile";
    }
	
	
	 @RequestMapping(value = "/createPayment", method = RequestMethod.GET)
	    public String register(Model model) {
	        model.addAttribute("payment", new Payment());
	        return "payment/createPayment";
	    }
	
}