package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldCtrl
{
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/welcome")
    public String helloWorld(Model model) {
        String message = "This message comes from the controller!";

        model.addAttribute("message", message);

        return "welcome";
    }
}
