package controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Edition;
import domain.Proposal;
import domain.ProposalStatus;
import domain.Reviewers;
import domain.Topic;
import domain.User;
import repo.BaseRepository;
import repo.EditionRepository;
import repo.PaymentRepository;
import repo.ProposalRepository;
import repo.ProposalStatusRepository;
import service.EmailService;
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

        if (Calendar.getInstance().compareTo(ed.getEndSubmissions()) == 1) {
            throw new NotFoundException("Perioada de submissions terminata");
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

        if (Calendar.getInstance().compareTo(ed.getEndSubmissions()) == 1) {
            throw new NotFoundException("Perioada de submissions terminata");
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

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR", "ROLE_STAFF"})
    @RequestMapping(value = "/viewProposals", method = RequestMethod.GET)
    public String viewProposals(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Proposal> proposals = ((ProposalRepository) this.get("repo.proposal")).getNewProposals(user);
        List<Proposal> proposalsReview = ((ProposalRepository) this.get("repo.proposal")).getToReview(user);

        model.addAttribute("proposals", proposals);
        model.addAttribute("proposalsReview", proposalsReview);

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
        model.addAttribute("proposalStatus",((ProposalStatusService)this.get("service.proposalStatus")).getByProposalAndReviewedIgnore(pr, user));

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
        model.addAttribute("edition", ed);
        model.addAttribute("service", this.get("service.proposalStatus"));
        model.addAttribute("reviewEnded", Calendar.getInstance().compareTo(ed.getEndReview()) == 1);

        return "proposal/viewEditionProposals";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/viewEditionProposals/viewCommentsProposal/{id}", method = RequestMethod.GET)
    public String viewCommentsProposals(Model model, @PathVariable int id) {
        Proposal pr = ((ProposalRepository) this.get("repo.proposal")).get(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (pr == null || user.getId() != pr.getEdition().getAuthor().getId()){
            throw new NotFoundException("Nu sunteti autorul editiei");
        }

        Boolean reviewersAlereadyChoosen = false;
        List<ProposalStatus> reviewers = ((ProposalStatusService)this.get("service.proposalStatus")).getByProposalReviwers(pr);
        if (reviewers.size() != 0) {
            reviewersAlereadyChoosen = true;
        }

        List<ProposalStatus> proposalStatus = ((ProposalStatusService)this.get("service.proposalStatus")).getByProposalAndReviewed(pr);

        model.addAttribute("proposal", pr);
        model.addAttribute("status", ((ProposalStatusService)this.get("service.proposalStatus")).getProposalStatus(pr));
        model.addAttribute("proposalStatus", proposalStatus);
        model.addAttribute("proposalStatusWithoutRevieweri", ((ProposalStatusService)this.get("service.proposalStatus")).getByProposalWithoutReviews(pr));
        model.addAttribute("submissionsEnded", Calendar.getInstance().compareTo(pr.getEdition().getEndSubmissions()) == 1);
        model.addAttribute("reviewers", reviewers);
        model.addAttribute("reviewersAlereadyChoosen", reviewersAlereadyChoosen || !proposalStatus.isEmpty());
        return "proposal/viewProposalComments";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/chooseReviewers/{id}", method = RequestMethod.GET)
    public String chooseReviewers(Model model, @PathVariable int id) {
        Proposal pr = ((ProposalRepository) this.get("repo.proposal")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<ProposalStatus> reviewers = ((ProposalStatusService)this.get("service.proposalStatus")).getByProposalReviwers(pr);
        if (user.getId() != pr.getEdition().getAuthor().getId()
            || Calendar.getInstance().compareTo(pr.getEdition().getEndSubmissions()) == -1
            || reviewers.size() != 0
        ) {
            throw new NotFoundException("Nu sunteti autorul editiei.");
        }

        model.addAttribute("proposal", pr);
        model.addAttribute("proposalStatusWithoutRevieweri", ((ProposalStatusService)this.get("service.proposalStatus")).getByProposalWithAnalyzes(pr));
        model.addAttribute("reviewers", new Reviewers());
        return "proposal/chooseReviewers";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/chooseReviewers/submit/{id}", method = RequestMethod.POST)
    public String chooseReviewersSubmit(@ModelAttribute("reviewers")Reviewers reviewers,
        @PathVariable int id,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        Proposal pr = ((ProposalRepository) this.get("repo.proposal")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<ProposalStatus> reviewersOthers = ((ProposalStatusService)this.get("service.proposalStatus")).getByProposalReviwers(pr);
        if (user.getId() != pr.getEdition().getAuthor().getId()
            || Calendar.getInstance().compareTo(pr.getEdition().getEndSubmissions()) == -1
            || reviewersOthers.size() != 0
        ) {
            throw new NotFoundException("Nu sunteti autorul editiei.");
        }

        if (reviewers.getIdProposalsStatus().size() != 2) {
            redirAttr.addFlashAttribute("flashMessage", "Trebuie sa alegi 2 evaluatori!");
            return "redirect:/chooseReviewers/" + id;
        }

        ProposalStatusRepository repo = ((ProposalStatusRepository)this.get("repo.proposalStatus"));
        for (String idStr : reviewers.getIdProposalsStatus()){
            Integer idProposalStatus = Integer.parseInt(idStr);
            ProposalStatus ps = repo.get(idProposalStatus);
            ps.setStatus(ProposalStatus.proposalStatus.toReview);
            repo.save(ps);
        }
        redirAttr.addFlashAttribute("flashMessage", "Evaluatori adaugati!");

        return "redirect:/viewEditionProposals/viewCommentsProposal/" + id;
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/rejectedProposals/{id}", method = RequestMethod.GET)
    public String rejectedProposals(@PathVariable("id") Integer id, Model model) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        if (Calendar.getInstance().compareTo(ed.getEndSubmissions()) != 1) {
            throw new NotFoundException("Perioada de submissions nu este terminata");
        }

        model.addAttribute("edition", ed);

        return "proposal/rejectedProposals";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"})
    @RequestMapping(value = "/rejectedProposals/submit/{id}", method = RequestMethod.POST)
    public String rejectedProposalsSubmit(@PathVariable("id") Integer id,
        @RequestParam String message, Model model, RedirectAttributes redir
    ) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        if (Calendar.getInstance().compareTo(ed.getEndSubmissions()) != 1) {
            throw new NotFoundException("Perioada de submissions nu este terminata");
        }

        if (message.isEmpty()) {
            redir.addFlashAttribute("flashMessage", "Mesajul nu poate fi gol.");

            return "redirect:/rejectedProposals/" + id;
        }

        ProposalStatusService statusService = (ProposalStatusService) this.get("service.proposalStatus");

        List<String> emails = new ArrayList<>();

        ((ProposalRepository) this.get("repo.proposal")).getByEdition(ed).stream()
            .filter((p) -> statusService.getProposalStatus(p) == ProposalStatusService.STATUS_REJECTED)
            .forEach((p) -> emails.add(p.getUser().getEmail()));

        EmailService service = (EmailService) this.get("service.email");

        try {
            service.sendMail(message, emails);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        redir.addFlashAttribute("flashMessage", "Mesajul a fost trimis!");

        return "redirect:/viewEditionProposals/" + id;
    }
}