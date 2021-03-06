package controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
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
import repo.ProposalRepository;
import service.ProposalStatusService;
import util.BaseController;

@Controller
public class UpdateProposalCtrl extends BaseController
{
    @Secured("ROLE_USER")
    @RequestMapping(value = "/updateProposal/submit/{id}", method = RequestMethod.POST)
    public String updateProposalSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute("proposal") Proposal proposal,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        ProposalRepository repository = (ProposalRepository) this.get("repo.proposal");
        Proposal old = repository.get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (old == null || old.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("Nu puteti edita aceasta propunere");
        }

        old.setDescription(proposal.getDescription());
        old.setFile(proposal.getFile());
        old.setName(proposal.getName());
        old.setKeywords(proposal.getKeywords());
        old.setTopics(proposal.getTopics());
        repository.save(old);

        redirAttr.addFlashAttribute("flashMessage", "Propunerea a fost updatata");

        return "redirect:/profile";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/updateProposal/{idProposal}", method = RequestMethod.GET)
    public String updateProposal(@PathVariable("idProposal") Integer idProposal,Model model, HttpSession session, RedirectAttributes redirAttr) {
        Proposal proposal = ((ProposalRepository) this.get("repo.proposal")).get(idProposal);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (proposal == null || proposal.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("Nu puteti edita aceasta propunere");
        }

        int status = ((ProposalStatusService)this.get("service.proposalStatus")).getProposalStatus(proposal);

        if (Calendar.getInstance().compareTo(proposal.getEdition().getEndSubmissions()) == 1
            && (status != ProposalStatusService.STATUS_ACCEPTED || Calendar.getInstance().compareTo(proposal.getEdition().getEndReview()) == -1)) {
            redirAttr.addFlashAttribute("flashMessage", "Propunerea nu poate fi momentat updatata");

            return "redirect:/viewProposal/" + idProposal;
        }

        model.addAttribute("proposal", proposal);
        model.addAttribute("id",idProposal);
        model.addAttribute("listAllTopics", ((BaseRepository<Topic>) this.get("repo.topic")).all());

        return "update/updateProposal";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "topics", new CustomCollectionEditor(List.class) {
            @SuppressWarnings("unchecked")
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