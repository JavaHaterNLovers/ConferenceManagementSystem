package controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Edition;
import domain.Proposal;
import domain.Topic;
import domain.User;
import repo.BaseRepository;
import repo.EditionRepository;
import repo.PaymentRepository;
import repo.ProposalRepository;
import service.ProposalStatusService;
import util.BaseController;

@Controller
public class ProposalCtrl extends BaseController
{
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createProposal/submit/{id}", method = RequestMethod.POST)
    public String createProposalSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute("proposal") Proposal proposal,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        if (ed == null) {
            throw new NotFoundException("Editia nu exista");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        PaymentRepository paymentRepo = (PaymentRepository) this.get("repo.payment");

        if (paymentRepo.getForUserEdition(user, ed) == null) {
            throw new AccessDeniedException("Trebuie sa platiti pentru aceasta editie");
        }

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("listAllTopics", ((BaseRepository<Topic>) this.get("repo.topic")).all());

            return "proposal/createProposal";
        }

        proposal.setUser(user);
        proposal.setEdition(((EditionRepository) this.get("repo.edition")).get(id));

        ((ProposalRepository) this.get("repo.proposal")).save(proposal);
        redirAttr.addFlashAttribute("flashMessage", "Propunere adaugata cu success");

        return "redirect:/";
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createProposal/{id}", method = RequestMethod.GET)
    public String createProposal(@PathVariable("id") Integer id,Model model, HttpSession session) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        if (ed == null) {
            throw new NotFoundException("Editia nu exista");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        PaymentRepository paymentRepo = (PaymentRepository) this.get("repo.payment");

        if (paymentRepo.getForUserEdition(user, ed) == null) {
            session.setAttribute(PaymentCtrl.PAYMENT_SPEAKER, true);

            return "redirect:/createPayment/" + ed.getId();
        }

        model.addAttribute("id", id);
        model.addAttribute("proposal", new Proposal());
        model.addAttribute("listAllTopics", ((BaseRepository<Topic>) this.get("repo.topic")).all());

        return "proposal/createProposal";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "topics", new CustomCollectionEditor(List.class) {
            @SuppressWarnings("unchecked")
            @Override
            protected Object convertElement(Object element) {
                Integer id = null;

                if(element instanceof String && !((String)element).equals("")){
                    try{
                        id = Integer.parseInt((String) element);
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                else if(element instanceof Integer) {
                    id = (Integer) element;
                }

                return id != null ? ((BaseRepository<Topic>) get("repo.topic")).get(id) : null;
            }
        });
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/viewProposals", method = RequestMethod.GET)
    public String viewProposals(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Proposal> proposals = ((ProposalRepository) this.get("repo.proposal")).getNewProposals(user);

        model.addAttribute("proposals", proposals);

        return "proposal/viewProposals";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/viewProposal/{id}", method = RequestMethod.GET)
    public String viewProposal(Model model, @PathVariable int id) {
        Proposal pr = ((ProposalRepository) this.get("repo.proposal")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (pr == null || pr.getUser().getId() != user.getId()) {
            throw new NotFoundException("Propunere inexistenta");
        }
        model.addAttribute("proposal", pr);
        model.addAttribute("status", ((ProposalStatusService)this.get("service.proposalStatus")).getProposalStatus(pr));
        model.addAttribute("valid", Calendar.getInstance().compareTo(pr.getEdition().getEndSubmissions()) == -1);
        model.addAttribute("proposalStatus",((ProposalStatusService)this.get("service.proposalStatus")).getByProposalAndReviewed(pr));

        return "proposal/viewProposal";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/viewEditionProposals/{id}", method = RequestMethod.GET)
    public String viewEditionProposals(Model model, @PathVariable int id) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        List<Proposal> proposals = ((ProposalRepository) this.get("repo.proposal")).getByEdition(ed);

        model.addAttribute("proposals", proposals);
        model.addAttribute("edition", ed.getName());
        model.addAttribute("service", this.get("service.proposalStatus"));

        return "proposal/viewEditionProposals";
    }
}
