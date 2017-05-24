package controller;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.User;
import repo.UserRepository;
import util.BaseController;

@Controller
public class UpdateUserCtrl extends BaseController
{
	@RequestMapping(value = "/updateUser/submit", method = RequestMethod.POST)
	@Secured({"ROLE_SUPER_ADMIN"})
    public String updateUserSubmit(@Valid @ModelAttribute("user") User user,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
		UserRepository repo = (UserRepository) this.get("repo.user");

		User old = repo.get(user.getId());

		old.setNume(user.getNume());
		old.setPrenume(user.getPrenume());
		old.setEmail(user.getEmail());
		old.setRol(user.getRol());

		repo.save(old);

		redirAttr.addFlashAttribute("flashMessage", "Userul a fost updatat");

        return "redirect:/profile";
    }

    @RequestMapping(value = "/updateUser/{usr}", method = RequestMethod.GET)
    @Secured({"ROLE_SUPER_ADMIN"})
    public String updateUser(Model model, @PathVariable int usr) {
        User user = new User();
        for(User u : ((UserRepository) this.get("repo.user")).all()){
        	if(u.getId() == usr){
		        user = u;
		        model.addAttribute("user", user);
        	}
        }

        model.addAttribute("enumRol",User.UserRole.values());
        return "update/updateUser";
    }
}
