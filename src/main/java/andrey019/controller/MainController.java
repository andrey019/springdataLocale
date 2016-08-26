package andrey019.controller;

import andrey019.service.maintenance.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private LogService logService;


	@RequestMapping("/")
	public String listAdvs() {
		logService.accessToPage("main");
        if (checkAuthentication() != null) {
            return "redirect:/user";
        }
		return "main_page";
	}

	@RequestMapping("/favicon.ico")
    public String favicon() {
        logService.accessToPage("favicon.ico");
        return "forward:/resources/images/favicon.ico";
    }

    private String checkAuthentication(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        }
        return null;
    }
}