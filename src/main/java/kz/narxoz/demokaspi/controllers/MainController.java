package kz.narxoz.demokaspi.controllers;

import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.publisher.EventManager;
import kz.narxoz.demokaspi.services.IbanService;
import kz.narxoz.demokaspi.services.OperationService;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    public EventManager events;

    @Autowired
    private UserService userService;

    @Autowired
//    private Message message;

//    private OperationService operationService;
//    private IbanService ibanService;

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
//        model.addAttribute("operations", userService.findAllByIbanId(userService.findOneByUserId(id)));
        return "account";
    }

    @GetMapping(value = "/messages")//в дальнейшем привязать к аккаунту и айдишке
    public String massages(){
        for (int i=0; i<userService.findAllMessages().size(); i++){
//            message.setVisible(true);//просмотрено
//            userService.saveMessage(message);
        }
        return "messages";
    }

    @GetMapping("/addOperation")
    public ModelAndView addOperatingPage(){
        ModelAndView model = new ModelAndView("addOperation");
        return model;
    }

    @PostMapping(value = "/addOperation")
    public ModelAndView addOperation(
            @ModelAttribute("operation") Operation operation){

//        operationService.saveOperation(operation);
        userService.saveOperation(operation);
        Message message = new Message();
        message.setIban_getter(operation.getIban_getter());
        message.setIban_sender(operation.getIban_sender());
        message.setSum(operation.getSum());
        message.setMessage(operation.getMessage());
        message.setVisible(false);
        userService.saveMessage(message);

        userService.notify(operation);//реализовать какое-то уведомление

        ModelAndView model = new ModelAndView("successOperation");
//        List allOperations = operationService.findAllOperations();
//        model.addObject("operation", operationService.findAllOperations());

        return model;
    }

    @GetMapping(value = "/operations")
    public String operations(@PathVariable() int id, Model model){
//        value = "id"
//        model.addAttribute("operations", operationService.findAllOperations());
//        model.addAttribute("operations", operationService.findAllByIbanId(operationService.findOneByUserId(id)));
//        model.addAttribute("operations", userService.findAllByIbanId(userService.findOneByUserId(1)));
        return "operations";
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
