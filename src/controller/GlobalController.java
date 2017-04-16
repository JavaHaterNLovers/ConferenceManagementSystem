package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController
{
    @ModelAttribute
    public void addCurrentRoute(Model model, HttpServletRequest request) {
        model.addAttribute("currentPage", request.getRequestURI().replaceAll("/CMSWeb/", ""));
    }
}
