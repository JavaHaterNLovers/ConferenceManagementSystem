package controller;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Edition;
import domain.Proposal;
import domain.User;
import repo.EditionRepository;
import repo.ProposalRepository;
import repo.SessionRepository;
import util.BaseController;

@Controller
public class OrarCtrl extends BaseController
{
    @Secured({"ROLE_CHAIR", "ROLE_CO_CHAIR"})
    @RequestMapping(value = "/viewOrar/{id}")
    public String viewOrar(Model model, @PathVariable int id) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        List<Proposal> proposals = ((ProposalRepository) this.get("repo.proposal")).getByEdition(ed);

        model.addAttribute("edition", ed);
        model.addAttribute("proposals", proposals);
        model.addAttribute("service", this.get("service.proposalStatus"));
        model.addAttribute("sessions", ((SessionRepository) this.get("repo.session")).getForEdition(ed));

        return "orar/view";
    }


}
