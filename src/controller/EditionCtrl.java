package controller;

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

import domain.Edition;
import domain.User;
import domain.User.UserRole;
import repo.ConferenceRepository;
import repo.EditionRepository;
import repo.ProposalRepository;
import service.ConferenceService;
import util.BaseController;

@Controller
public class EditionCtrl extends BaseController
{
    @RequestMapping(value = "/createEdition/submit", method = RequestMethod.POST)
    public String createEditionSubmit(@ModelAttribute("edition")Edition edition,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        edition.setAuthor(user);
        
        edition.setConference(((ConferenceRepository) this.get("repo.conference")).getById(Integer.parseInt(edition.getAuxConferenceId())));
        
        if (result.hasErrors()) {
            model.addAttribute("conferences", ((ConferenceRepository) this.get("repo.conference")).getByUser(user));
            return "edition/createEdition";
        }
           
        
        ((ConferenceService) this.get("service.conference")).add(edition);

        model.addAttribute("message", "Editie adaugata cu success");

        model.addAttribute("user", user);
        model.addAttribute("proposals", ((ProposalRepository) this.get("repo.proposal")).getByUser(user));

        if (user.getRol() == UserRole.chair) {
            model.addAttribute("conferences", ((ConferenceRepository) this.get("repo.conference")).getByUser(user));
        }

        if (user.getRol() == UserRole.chair || user.getRol() == UserRole.coChair) {
            model.addAttribute("editions", ((EditionRepository) this.get("repo.edition")).getByUser(user));
        }
        return "user/profile";
    }
    
    @RequestMapping(value = "/createEdition", method = RequestMethod.GET)
    public String createEdition(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("edition", new Edition());
        model.addAttribute("conferences", ((ConferenceRepository) this.get("repo.conference")).getByUser(user));
        return "edition/createEdition";
    }
}
