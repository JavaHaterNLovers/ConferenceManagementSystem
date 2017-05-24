package controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
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

import domain.Proposal;
import domain.Topic;
import domain.User;
import repo.BaseRepository;
import repo.EditionRepository;
import repo.ProposalRepository;
import util.BaseController;

@Controller
public class ProposalCtrl extends BaseController
{
    @RequestMapping(value = "/createProposal/submit/{id}", method = RequestMethod.POST)
    public String createEditionSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute("proposal") Proposal proposal,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("listAllTopics", ((BaseRepository<Topic>) this.get("repo.topic")).all());

            return "proposal/createProposal";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        proposal.setUser(user);
        proposal.setEdition(((EditionRepository) this.get("repo.edition")).get(id));

        ((ProposalRepository) this.get("repo.proposal")).save(proposal);
        redirAttr.addFlashAttribute("flashMessage", "Propunere adaugata cu success");

        return "redirect:/profile";
    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createProposal/{id}", method = RequestMethod.GET)
    public String createEdition(@PathVariable("id") Integer id,Model model) {

        model.addAttribute("id", id);
        model.addAttribute("proposal", new Proposal());
        model.addAttribute("listAllTopics", ((BaseRepository<Topic>) this.get("repo.topic")).all());

        return "proposal/createProposal";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "topics", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                Integer id = null;

                if(element instanceof String && !((String)element).equals("")){
                    try{
                        id = Integer.parseInt((String) element);
                    }
                    catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                else if(element instanceof Integer) {
                    id = (Integer) element;
                }

                return id != null ? ((BaseRepository<Topic>) get("repo.topic")).get(id) : null;
            }
        });
    }
}
