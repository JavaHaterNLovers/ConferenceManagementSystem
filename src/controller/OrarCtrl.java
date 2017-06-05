package controller;

import java.beans.PropertyEditorSupport;
import java.util.Calendar;
import java.util.List;

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
import domain.Proposal;
import domain.Session;
import domain.User;
import repo.EditionRepository;
import repo.OrarRepository;
import repo.ProposalRepository;
import repo.SessionRepository;
import service.ProposalStatusService;
import util.BaseController;

@Controller
public class OrarCtrl extends BaseController
{
    @Secured({"ROLE_CHAIR", "ROLE_CO_CHAIR"})
    @RequestMapping(value = "/viewOrar/{id}")
    public String viewOrar(Model model, @PathVariable int id) {
        Edition ed = ((EditionRepository) this.get("repo.edition")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ed == null || ed.getAuthor().getId() != user.getId()) {
            throw new NotFoundException("Editia nu exista");
        }

        List<Proposal> proposals = ((ProposalRepository) this.get("repo.proposal")).getByEdition(ed);

        model.addAttribute("edition", ed);
        model.addAttribute("proposals", proposals);
        model.addAttribute("service", this.get("service.proposalStatus"));
        model.addAttribute("sessions", ((SessionRepository) this.get("repo.session")).getForEdition(ed));

        return "orar/view";
    }

    @Secured({"ROLE_CHAIR", "ROLE_CO_CHAIR"})
    @RequestMapping(value = "/createOrar/submit/{id}", method = RequestMethod.POST)
    public String createOrarSubmit(@ModelAttribute("orar") Orar orar,
        @PathVariable int id,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        Proposal ps = ((ProposalRepository) this.get("repo.proposal")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ps == null || ps.getEdition().getAuthor().getId() != user.getId()
            || Calendar.getInstance().compareTo(ps.getEdition().getEndReview()) != 1
            || ((ProposalStatusService)this.get("service.proposalStatus")).hasPendingProposals(ps.getEdition())
        ) {
            throw new NotFoundException("Propunerea nu exista");
        }

        Orar orarOld = ((OrarRepository) this.get("repo.orar")).getByProposal(ps);

        if (orarOld != null) {
            orar.setId(orarOld.getId());
        } else {
            orar.setId(null);
        }

        if (!((OrarRepository) this.get("repo.orar")).isOrarValid(orar)) {
            result.rejectValue("beginDate", "orar.invalidDate", "Exista deja o propunere programata atunci");
        }

        if (result.hasErrors()) {
            model.addAttribute("proposal", ps);
            model.addAttribute("sessions", ((SessionRepository) this.get("repo.session")).getForEdition(ps.getEdition()));

            return "orar/create";
        }


        orar.setProposal(ps);;

        ((OrarRepository) this.get("repo.orar")).save(orar);

        redirAttr.addFlashAttribute("flashMessage", "Orar adaugat");

        return "redirect:/viewOrar/" + ps.getEdition().getId();
    }

    @Secured({"ROLE_CHAIR", "ROLE_CO_CHAIR"})
    @RequestMapping(value = "/createOrar/{id}", method = RequestMethod.GET)
    public String createOrar(Model model, @PathVariable int id) {
        Proposal ps = ((ProposalRepository) this.get("repo.proposal")).get(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (ps == null || ps.getEdition().getAuthor().getId() != user.getId()
            || Calendar.getInstance().compareTo(ps.getEdition().getEndReview()) != 1
            || ((ProposalStatusService)this.get("service.proposalStatus")).hasPendingProposals(ps.getEdition())
        ) {
            throw new NotFoundException("Propunerea nu exista");
        }

        Orar orar = ((OrarRepository) this.get("repo.orar")).getByProposal(ps);

        model.addAttribute("orar", orar != null ? orar : new Orar());
        model.addAttribute("sessions", ((SessionRepository) this.get("repo.session")).getForEdition(ps.getEdition()));
        model.addAttribute("proposal", ps);

        return "orar/create";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Session.class, "section", new PropertyEditorSupport(Session.class) {
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

                setValue(id != null ? ((SessionRepository) get("repo.session")).get(id) : null);
            }
        });
    }
}
