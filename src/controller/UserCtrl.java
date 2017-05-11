package controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.User;
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
    public String submit(@Valid @ModelAttribute("user")User user,
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
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "user/register";
    }
}
