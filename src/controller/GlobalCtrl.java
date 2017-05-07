package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalCtrl
{
    /**
     * Add parameters to be available in all templates.
     */
    @ModelAttribute
    public void addParameters(Model model, HttpServletRequest request) {
        model.addAttribute("baseUrl", request.getContextPath());
    }
}
