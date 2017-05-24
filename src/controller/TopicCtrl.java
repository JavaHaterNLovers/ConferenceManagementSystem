package controller;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import domain.Topic;
import repo.BaseRepository;
import util.BaseController;

@Controller
public class TopicCtrl extends BaseController
{

    @Secured({"ROLE_CHAIR"})
    @RequestMapping(value = "/createTopic/submit", method = RequestMethod.POST)
    public String createTopicSubmit(@Valid @ModelAttribute("topic")Topic topic,
        BindingResult result, ModelMap model,
        RedirectAttributes redirAttr
    ) {
        if (result.hasErrors()) {
            return "topic/createTopic";
        }

        ((BaseRepository<Topic>) this.get("repo.topic")).save(topic);

        redirAttr.addFlashAttribute("flashMessage", "Topic adaugat cu success");

        return "redirect:/createTopic";
    }

    @Secured({"ROLE_CHAIR"})
    @RequestMapping(value = "/createTopic", method = RequestMethod.GET)
    public String createTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "topic/createTopic";
    }
}
