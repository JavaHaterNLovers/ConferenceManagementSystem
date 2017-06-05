package controller;

import java.beans.PropertyEditorSupport;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Edition;
import domain.Orar;
import domain.Session;
import domain.User;
import repo.EditionRepository;
import repo.OrarRepository;
import repo.SessionRepository;
import repo.UserRepository;
import util.BaseController;

@Controller
public class SessionCtrl extends BaseController
{
    @Secured({"ROLE_CHAIR", "ROLE_CO_CHAIR"})
    @RequestMapping(value = "/createSession/submit/{id}", method = RequestMethod.POST)
    public String createSessionSubmit(@Valid @ModelAttribute("session") Session session,
        @PathVariable int id,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        if (result.hasErrors()) {
            model.addAttribute("edition", ed);
            model.addAttribute("users", ((UserRepository) this.get("repo.user")).getChairCoChair());

            return "session/create";
        }

        session.setEdition(ed);

        ((SessionRepository) this.get("repo.session")).save(session);

        redirAttr.addFlashAttribute("flashMessage", "Sesiune adaugata");

        return "redirect:/viewOrar/" + id;
    }

    @Secured({"ROLE_CHAIR", "ROLE_CO_CHAIR"})
    @RequestMapping(value = "/createSession/{id}", method = RequestMethod.GET)
    public String createTopic(Model model, @PathVariable int id) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        model.addAttribute("edition", ed);
        model.addAttribute("session", new Session());
        model.addAttribute("users", ((UserRepository) this.get("repo.user")).getChairCoChair());

        return "session/create";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, "user", new PropertyEditorSupport(User.class) {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                Integer id = null;

                if(!text.equals("")){
                    try{
                        id = Integer.parseInt(text);
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                setValue(id != null ? ((UserRepository) get("repo.user")).get(id) : null);
            }
        });
    }

    @RequestMapping(value = "/viewSession/{id}")
    public String viewOrar(Model model, @PathVariable int id) {
        Session s = ((SessionRepository) this.get("repo.session")).get(id);

        if (s == null) {
            throw new NotFoundException("Sesiunea nu exista");
        }

        List<Orar> orare = ((OrarRepository) this.get("repo.orar")).getBySession(s);

        model.addAttribute("session", s);
        model.addAttribute("orare", orare);

        return "session/view";
    }
}
