package controller;

import javax.validation.Valid;

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

import domain.Conference;
import domain.User;
import repo.ConferenceRepository;
import service.UserService;
import util.BaseController;

@Controller
public class ConferenceCtrl extends BaseController
{

    @RequestMapping(value = "/conference", method = RequestMethod.GET)
    public String conference(Model model) {
        model.addAttribute("conference", new Conference());

        return "conference/createConference";
    }
    
    @RequestMapping(value = "/createConference/submit", method = RequestMethod.POST)
    public String registerSubmit(@Valid @ModelAttribute("conference")Conference conference,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        if (result.hasErrors()) {
            return "redirect:/conference";
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        conference.setAuthor(user);
        
        ((ConferenceRepository) this.get("repo.conference")).save(conference);

        redirAttr.addFlashAttribute("flashMessage", "Conferinta a fost creata cu succes!");

        return "redirect:/profile";
    }
    
    
}
