package controller;

import javax.servlet.http.HttpServletRequest;
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

import domain.Topic;
import domain.User;
import domain.User.UserRole;
import repo.BaseRepository;
import repo.ConferenceRepository;
import repo.EditionRepository;
import repo.ProposalRepository;
import repo.UserRepository;
import service.UserService;
import util.BaseController;

@Controller
public class UserCtrl extends BaseController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        if (request.getParameter("error") != null) {
            model.addAttribute("error", "Email sau parola gresite.");
        }

        if (request.getParameter("logout") != null) {
            model.addAttribute("message", "Ati fost delogat cu success.");
        }

        return "user/login";
    }

    @RequestMapping(value = "/register/submit", method = RequestMethod.POST)
    public String registerSubmit(@Valid @ModelAttribute("user")User user,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        if (result.hasErrors()) {
            return "user/register";
        }

        ((UserService) this.get("service.user")).add(user);

        redirAttr.addFlashAttribute("flashMessage", "Ati fost inregistrat cu success");

        return "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());

        return "user/register";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("proposals", ((ProposalRepository) this.get("repo.proposal")).getByUser(user));

        if (user.getRol() == UserRole.chair) {
            model.addAttribute("conferences", ((ConferenceRepository) this.get("repo.conference")).getByUser(user));
            model.addAttribute("topics", ((BaseRepository<Topic>) this.get("repo.topic")).all());
        }

        if (user.getRol() == UserRole.chair || user.getRol() == UserRole.coChair) {
            model.addAttribute("editions", ((EditionRepository) this.get("repo.edition")).getByUser(user));
        }

        if (user.getRol() == UserRole.superAdmin) {
        	model.addAttribute("users", ((UserRepository) this.get("repo.user")).all());
        }

        return "user/profile";
    }
}
