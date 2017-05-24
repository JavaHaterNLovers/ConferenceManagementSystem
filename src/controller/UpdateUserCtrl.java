package controller;

import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Conference;
import domain.Edition;
import domain.User;
import domain.User.UserRole;
import repo.BaseRepository;
import repo.ConferenceRepository;
import repo.EditionRepository;
import repo.UserRepository;
import service.ConferenceService;
import util.BaseController;

@Controller
public class UpdateUserCtrl extends BaseController
{

	@RequestMapping(value = "/updateUser/submit", method = RequestMethod.POST)
    public String updateUserSubmit(@Valid @ModelAttribute("user")User user,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
       // user.setRol("coChair");
		System.out.println(user.getNume().toString());
		((UserRepository) this.get("repo.user")).save(user);

        return "redirect:/profile";
    }
	
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String updateUser(Model model, @RequestParam("usr") int usr) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = new User();
        for(User u : ((UserRepository) this.get("repo.user")).all()){
        	if(u.getId() == usr){
		        user = (User) u;
		        model.addAttribute("user", user);
        	}
        }
        
        model.addAttribute("enumRol",User.UserRole.values());
        return "update/updateUser";
    }
}
