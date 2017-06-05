package controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.model.NotFoundException;
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

import domain.Proposal;
import domain.ProposalStatus;
import domain.ProposalStatus.proposalStatus;
import domain.User;
import repo.ProposalRepository;
import repo.ProposalStatusRepository;
import service.ProposalStatusService;
import util.BaseController;

@Controller
public class AnalyzesProposalCtrl extends BaseController
{
    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR", "ROLE_STAFF"})
    @RequestMapping(value = "/analyzesProposal/{id}", method = RequestMethod.GET)
    public String analyzesProposal(Model model, @PathVariable("id") Integer id) {
        Proposal proposal = ((ProposalRepository) this.get("repo.proposal")).get(id);

        if (proposal == null || Calendar.getInstance().compareTo(proposal.getEdition().getEndBidding()) == 1) {
            throw new NotFoundException("Propunerea nu a fost gasita");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        ProposalStatus status = ((ProposalStatusRepository) this.get("repo.proposalStatus")).getForUserProposal(user, proposal);

        model.addAttribute("proposalStatus", status != null ? status : new ProposalStatus());
        model.addAttribute("proposal", proposal);

        return "proposal/analyzesProposal";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR", "ROLE_STAFF"})
    @RequestMapping(value = "/analyzesProposal/submit/{id}", method = RequestMethod.POST)
    public String analyzesProposalSubmit(@PathVariable("id") Integer id,
        @Valid @ModelAttribute("proposalStatus") ProposalStatus proposalStatus,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {

        Proposal proposal = ((ProposalRepository) this.get("repo.proposal")).get(id);

        if (proposal == null || Calendar.getInstance().compareTo(proposal.getEdition().getEndBidding()) == 1) {
            throw new NotFoundException("Propunerea nu a fost gasita");
        }

        if (result.hasErrors()) {
            model.addAttribute("proposal", proposal);
            return "proposal/analyzesProposal";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        ProposalStatus status = ((ProposalStatusRepository) this.get("repo.proposalStatus")).getForUserProposal(user, proposal);

        if (status != null) {
            proposalStatus.setId(status.getId());
            proposalStatus.setCreated(status.getCreated());
        } else {
            proposalStatus.setId(null);
        }

        proposalStatus.setUser(user);
        proposalStatus.setProposal(proposal);

        ((ProposalStatusRepository) this.get("repo.proposalStatus")).save(proposalStatus);
        redirAttr.addFlashAttribute("flashMessage", "Analiza adaugata cu success");

        return "redirect:/viewProposals";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR", "ROLE_STAFF"})
    @RequestMapping(value = "/reviewProposal/{id}", method = RequestMethod.GET)
    public String reviewProposal(Model model, @PathVariable("id") Integer id) {
        Proposal proposal = ((ProposalRepository) this.get("repo.proposal")).get(id);

        if (proposal == null) {
            throw new NotFoundException("Propunerea nu a fost gasita");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        ProposalStatus status = ((ProposalStatusRepository) this.get("repo.proposalStatus")).getForUserProposal(user, proposal);

        if (status == null
            || status.getStatus() == proposalStatus.analyzes
            || status.getStatus() == proposalStatus.maybeAnalyzes
            || status.getStatus() == proposalStatus.rejectAnalyzes
        ) {
            throw new NotFoundException("Nu puteti face review.");
        }

        model.addAttribute("proposalStatus", status);
        model.addAttribute("proposal", proposal);
        model.addAttribute("reviewEnd", Calendar.getInstance().compareTo(proposal.getEdition().getEndReview()) == 1);

        List<ProposalStatus> allReviews = ((ProposalStatusService) this.get("service.proposalStatus")).getByProposalAndReviewedIgnore(proposal, user);
        model.addAttribute("allReviews", allReviews);

        return "proposal/reviewProposal";
    }

    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR", "ROLE_STAFF"})
    @RequestMapping(value = "/reviewProposal/submit/{id}", method = RequestMethod.POST)
    public String reviewProposalSubmit(@PathVariable("id") Integer id,
        @Valid @ModelAttribute("proposalStatus") ProposalStatus proposalStatus,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {

        Proposal proposal = ((ProposalRepository) this.get("repo.proposal")).get(id);

        if (proposal == null || Calendar.getInstance().compareTo(proposal.getEdition().getEndReview()) == 1) {
            throw new NotFoundException("Propunerea nu a fost gasita");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        ProposalStatus status = ((ProposalStatusRepository) this.get("repo.proposalStatus")).getForUserProposal(user, proposal);

        if (status == null
            || status.getStatus() == ProposalStatus.proposalStatus.analyzes
            || status.getStatus() == ProposalStatus.proposalStatus.maybeAnalyzes
            || status.getStatus() == ProposalStatus.proposalStatus.rejectAnalyzes
        ) {
            throw new NotFoundException("Nu puteti face review.");
        }

        if (result.hasErrors()) {
            model.addAttribute("proposalStatus", status);
            model.addAttribute("proposal", proposal);
            return "proposal/reviewProposal";
        }

        proposalStatus.setId(status.getId());
        proposalStatus.setUser(user);
        proposalStatus.setProposal(proposal);
        proposalStatus.setCreated(status.getCreated());

        ((ProposalStatusRepository) this.get("repo.proposalStatus")).save(proposalStatus);
        redirAttr.addFlashAttribute("flashMessage", "Review adaugat cu success");

        return "redirect:/viewProposals";
    }
}
