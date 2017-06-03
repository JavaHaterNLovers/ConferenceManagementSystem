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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Proposal;
import domain.ProposalStatus;
import domain.Topic;
import domain.User;
import repo.BaseRepository;
import repo.EditionRepository;
import repo.ProposalRepository;
import util.BaseController;

@Controller
public class AnalyzesProposalCtrl extends BaseController
{
    @Secured({"ROLE_CHAIR","ROLE_CO_CHAIR"}) 
    @RequestMapping(value = "/analyzesProposal/{id}", method = RequestMethod.GET)
    public String analyzesProposal(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("proposalStatus", new ProposalStatus());
        model.addAttribute("id", id);
        
        return "proposal/analyzesProposal";
    }
    
    @RequestMapping(value = "/analyzesProposal/submit/{id}", method = RequestMethod.POST)
    public String createEditionSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute("proposalStatus") ProposalStatus proposalStatus,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            return "proposal/analyzesProposal";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        proposalStatus.setUser(user);
        proposalStatus.setProposal(((ProposalRepository) this.get("repo.proposal")).get(id));

        ((BaseRepository<ProposalStatus>) this.get("repo.proposalStatus")).save(proposalStatus);
        redirAttr.addFlashAttribute("flashMessage", "Analiza adaugata cu success");

        return "redirect:/profile";
    }
}
