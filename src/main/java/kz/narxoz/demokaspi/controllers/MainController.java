package kz.narxoz.demokaspi.controllers;

import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String Hello() {
        return "authorizationPage";
    }

    @RequestMapping(value = "/menu")
    public String menu(){
        return "menu";
    }

    @RequestMapping(value = "/kaspiPay")
    public String kaspiPaySignIn(){
        return "pay_signIn";
    }

    @RequestMapping(value = "/footer")
    public String footer(){
        return "footer";
    }

    @GetMapping(value = "/account/{id}")
    public String account(@PathVariable(value = "id") int id, Model model){
        model.addAttribute("user", userService.findOneById(id));
        return "account";
    }

    @RequestMapping(value = "/greeting")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

//    @Autowired
////    private Users usersService;
//
//    @RequestMapping("/")
////    @GetMapping("/index")
////    public ModelAndView index(User user){
////        ModelAndView model = new ModelAndView("authorisationPage");
//////        model.addObject("employee", employeeService.findAllEmployees());
////        return model;
////    }
}
