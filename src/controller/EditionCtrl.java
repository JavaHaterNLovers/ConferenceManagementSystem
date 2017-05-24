package controller;

import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.security.access.AccessDeniedException;
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
import domain.User;
import repo.ConferenceRepository;
import repo.EditionRepository;
import service.ConferenceService;
import util.BaseController;

@Controller
public class EditionCtrl extends BaseController
{
    @RequestMapping(value = "/createEdition/submit", method = RequestMethod.POST)
    public String createEditionSubmit(@Valid @ModelAttribute("edition")Edition edition,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        edition.setConference(((ConferenceRepository) this.get("repo.conference")).get(Integer.parseInt(edition.getAuxConferenceId())));

        if (result.hasErrors()) {
            model.addAttribute("conferences", ((ConferenceRepository) this.get("repo.conference")).all());
            return "edition/createEdition";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        edition.setAuthor(user);


        ((ConferenceService) this.get("service.conference")).add(edition);

        redirAttr.addFlashAttribute("flashMessage", "Editie adaugata cu success");

        return "redirect:/profile";
    }

    @RequestMapping(value = "/createEdition", method = RequestMethod.GET)
    public String createEdition(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("edition", new Edition());
        model.addAttribute("conferences", ((ConferenceRepository) this.get("repo.conference")).all());
        return "edition/createEdition";
    }

    @RequestMapping(value = "/viewEdition/{id}", method = RequestMethod.GET)
    public String viewEdition(Model model, @PathVariable int id) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        if (ed == null) {
            throw new AccessDeniedException("Editie inexistenta");
        }

        model.addAttribute("edition", ed);
        model.addAttribute("valid", Calendar.getInstance().compareTo(ed.getEndSubmissions()) == -1);

        return "edition/view";
    }
}
