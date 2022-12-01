package kz.narxoz.demokaspi.controllers;

import kz.narxoz.demokaspi.authorization.Server;
import kz.narxoz.demokaspi.entity.Iban;
import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.entity.User;
import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.publisher.EventManager;
import kz.narxoz.demokaspi.repository.IbanRepository;
import kz.narxoz.demokaspi.services.IbanService;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;


@Controller
public class MainController {

    public EventManager events;
    private static Server server;

    @Autowired
    private UserService userService;


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
        User user = userService.findOneById(id);
        int ibanId = user.getIban();
        List<Operation> current = new ArrayList<>();
        List<Operation> operations = userService.findAllOperations();
        for (Operation operation: operations) {
            if (operation.getIbanGetter() == ibanId){
//              operations.remove(operation);
                current.add(operation);
            }
            if(operation.getIbanSender() == ibanId){
                operation.setOperation_type("-");
                current.add(operation);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("operations", current);
        model.addAttribute("iban", userService.findOneIbanById(ibanId));
//        model.addAttribute("operations", userService.findAllByIbanId(userService.findOneByUserId(id)));
        return "account";
    }

    @GetMapping(value = "/successOperation/{id}")
    public String successOperation(@PathVariable(value = "id") int id, Model model){
        model.addAttribute("user", userService.findOneById(id));
        return "successOperation";
    }

//    @GetMapping(value = "/account")
////    @PreAuthorize("isAuthenticated()")
//    public String profile(Model model){
////        model.addAttribute("currentUser", getUserData());
//        model.addAttribute("account");
//        return "account";
//    }

    @GetMapping(value = "/messages")//в дальнейшем привязать к аккаунту и айдишке
    public String massages(){
        for (int i=0; i<userService.findAllMessages().size(); i++){
//            message.setVisible(true);//просмотрено
//            userService.saveMessage(message);
        }
        return "messages";
    }

//    @GetMapping("/addOperation")
//    public ModelAndView addOperatingPage(){
//        ModelAndView model = new ModelAndView("addOperation2");
//        model.addAttribute("iban", ibanRepository.findById(ibanId));
//        return model;
//    }

    @GetMapping(value = "/operations/{id}")
    public String operations(@PathVariable() int id, Model model){
        User user = userService.findOneById(id);
        int ibanId = user.getIban();
        List<Operation> buyOperations = new ArrayList<>();
        List<Operation> operations = userService.findAllOperations();
        for (Operation operation: operations) {
            if (operation.getMessage() == "#buyingOperation"){
                buyOperations.add(operation);
            }
        }
        model.addAttribute("plusOperations", userService.findAllOperationsByGetterIban(ibanId));
        model.addAttribute("minusOperations", userService.findAllOperationsBySenderIban(ibanId));
        model.addAttribute("buyOperations", buyOperations);
        return "operations";
    }

    @GetMapping("/addOperation/{id}")
    public String addOperatingPage(@PathVariable(value = "id") int id, Model model){
        User user = userService.findOneById(id);
        int ibanId = user.getIban();
        model.addAttribute("iban", userService.findOneIbanById(ibanId));
        model.addAttribute("operations", userService.findAllOperationsBySenderIban(ibanId));
        return "addOperation2";
    }

    @PostMapping(value = "/addOperation")
    public String addOperation(@RequestParam(name = "user_phone_number") String user_phone_number,
                                     @RequestParam(name = "ibanGetter") int ibanGetter,
                                     @RequestParam(name = "sum") int sum,
                                     @RequestParam(name = "message") String message,
                                     @RequestParam(name = "operation_type") String operation_type,
                                     @RequestParam(name = "ibanSender") int ibanSender,
                                     HttpServletRequest request){
        Operation operation = new Operation();
        User user = userService.getUserByPhoneNumber(user_phone_number);
        int currentIbanGetter = 0;
        if (ibanGetter != 0){
            operation.setIbanGetter(ibanGetter);
            currentIbanGetter = ibanGetter;
        }
        else{
            operation.setIbanGetter(user.getIban());
            currentIbanGetter = user.getIban();
        }
        operation.setSum(sum);
        operation.setMessage(message);
        operation.setOperation_type(operation_type);
        operation.setIbanSender(ibanSender);
        userService.saveOperation(operation);

        Iban get_iban = userService.findOneIbanById(currentIbanGetter);
        int getCurrentSum = get_iban.getSum();
        getCurrentSum += sum;
        get_iban.setSum(getCurrentSum);
        userService.saveIban(get_iban);

        Iban send_iban = userService.findOneIbanById(ibanSender);
        int sendCurrentSum = send_iban.getSum();
        sendCurrentSum -= sum;
        send_iban.setSum(sendCurrentSum);
        userService.saveIban(send_iban);

        Message messages = new Message();
        messages.setIban_getter(currentIbanGetter);
        messages.setIban_sender(ibanSender);
        messages.setSum(sum);
        messages.setMessage(message);
        messages.setVisible(false);
        userService.saveMessage(messages);

        userService.notify(operation);//реализовать какое-то уведомление

//        ModelAndView model = new ModelAndView("successOperation");
//        List allOperations = operationService.findAllOperations();
//        model.addObject("operation", operationService.findAllOperations());
//        return model;
        return "redirect:" + request.getScheme() +":/successOperation/" + user.getId();
    }



    @GetMapping(value = "/register")
    public ModelAndView registrationPage() {
        ModelAndView model = new ModelAndView("registrationPage");
        return model;
    }

    @PostMapping(value = "/register")
    public ModelAndView registration(@ModelAttribute("kaspi_users") User user){
//        userService.saveUser(user);
        ModelAndView model = new ModelAndView("error");//+message
        if(userService.createUser(user)){
//            userService.saveUser(user);
            model = new ModelAndView("pay_signIn");//+message
            System.out.println("регистрация выполнена успешно");
            //уведомление "регистрация выполнена успешно"
            //возможно добавить смс верификацию (смс будет отправляться в терминал)
        }
        else {
            System.out.println("что-то пошло не так");
        }
        //уведомление с ошибкой
        return model;
    }

    @RequestMapping(value = "/auth")
    public String authorizeUser(@RequestParam(name = "phone_number") String phone_number,
                                @RequestParam(name = "password") String password,
                                HttpServletRequest request){
        User user = userService.getUserByPhoneNumber(phone_number);
        String usPas = user.getPassword();
        if (usPas.equals(password)){
            System.out.println("enter is successful");
            return "redirect:" + request.getScheme() +":/account/" + user.getId();
        }
        else {
            System.out.println("enter пошло не так");
        }
        return "error";
    }

//    private User getUserData(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(!(authentication instanceof AnonymousAuthenticationToken)){
//            User secUser = (User)authentication.getPrincipal();
//            User myUser = userService.getUserByPhoneNumber(secUser.getPhoneNumber());
//            return myUser;
//        }
//        return null;
//    }
}
