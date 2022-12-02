package kz.narxoz.demokaspi.controllers;

import kz.narxoz.demokaspi.authorization.Server;
import kz.narxoz.demokaspi.entity.*;
import kz.narxoz.demokaspi.publisher.EventManager;
import kz.narxoz.demokaspi.repository.IbanRepository;
import kz.narxoz.demokaspi.services.IbanService;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
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


    @RequestMapping(value = "/menu{id}")
    public String menu(@PathVariable(value = "id") int id, Model model){
        model.addAttribute("user", userService.findOneById(id));
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

//    @PreAuthorize("")
    @GetMapping(value = "/account/{id}")
    public String account(@PathVariable(value = "id") int id, Model model){
        User user = userService.findOneById(id);
        int ibanId = user.getIban();
        List<Operation> current = new ArrayList<>();
        List<Operation> operations = userService.findAllOperations();
        for (Operation operation: operations) {
            if (operation.getIbanGetter() == ibanId){
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

//    @RolesAllowed("admin")
    @GetMapping(value = "/admin/{id}")
    public String admin(@PathVariable(value = "id") int id, Model model) {
        User user = userService.findOneById(id);
        model.addAttribute("user", user);
        return "admin";
    }


    @PostMapping(value = "/addSell")
    public String addSell(@RequestParam(name = "companyName") String companyName,
                           @RequestParam(name = "description") String description,
                          @RequestParam(name = "id") int id) {
        Sell sell = new Sell();
        sell.setCompanyName(companyName);
        sell.setDescription(description);
        sell.setVisible(false);
        userService.saveSell(sell);
        return "redirect:/admin/" + id;
//        return "admin";
    }

    @GetMapping(value = "/successOperation/{id}")
    public String successOperation(@PathVariable(value = "id") int id, Model model){
        model.addAttribute("user", userService.findOneById(id));
        return "successOperation";
    }

    @GetMapping(value = "/messages/{id}")
    public String massages(@PathVariable(value = "id") int id, Model model){
        List<Message> messages =  userService.findAllMessages();

        model.addAttribute("messages", messages);
        model.addAttribute("user", userService.findOneById(id));
        model.addAttribute("sells", userService.findAllSells());

        for (Message message: messages) {
            message.setVisible(true);
        }
        return "messages";
    }

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
        model.addAttribute("user", user);
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
        List<Operation> currentOperations = userService.findAllOperationsBySenderIban(ibanId);
        List<Operation> operations =  userService.findAllOperationsBySenderIban(ibanId);
        for (Operation operation: operations) {
            if (operation.getMessage().equals("#buyingOperation")){
                currentOperations.remove(operation);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("operations", currentOperations);
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
        User currentUser = userService.findOneByIbanId(ibanSender);
        int currentIbanGetter = 0;
        if (message.equals("#buyingOperation")){
            String operator = "";
            if (user_phone_number.contains("+7747")){
                operator = "Tele2";
            }
            else if (user_phone_number.contains("+7701")){
                operator = "Activ";
            }
            else if (user_phone_number.contains("+7777")){
                operator = "Beeline";
            }
            else if (user_phone_number.contains("+7702")){
                operator = "Kcell";
            }
            operation.setSum(sum);
            operation.setMessage(message);
            operation.setOperation_type(operation_type);
            operation.setIbanSender(ibanSender);
            operation.setIbanGetter(ibanGetter);
            userService.saveOperation(operation);

            Iban send_iban = userService.findOneIbanById(ibanSender);
            int sendCurrentSum = send_iban.getSum();
            sendCurrentSum -= sum;
            send_iban.setSum(sendCurrentSum);
            userService.saveIban(send_iban);

            System.out.println(operator + ": Ваш счёт пополнен на " + sum +" тенге.");
            return "redirect:" + request.getScheme() +":/successOperation/" + currentUser.getId();
        }
        else if (ibanGetter != 0){
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

        userService.notify(operation);
        return "redirect:" + request.getScheme() +":/successOperation/" + currentUser.getId();
    }

    @GetMapping(value = "/payment/{id}")
    public String payments(@PathVariable() int id, Model model){
        User user = userService.findOneById(id);
        List<Operation> buyOperations = new ArrayList<>();
        List<Operation> operations = userService.findAllOperations();
        for (Operation operation: operations) {
            if (operation.getMessage() == "#buyingOperation"){
                buyOperations.add(operation);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("operations", buyOperations);
        return "payment";
    }

    @GetMapping(value = "/addPayment/{id}")
    public String addPaymentPage(@PathVariable(value = "id") int id, Model model){
        User user = userService.findOneById(id);
        int ibanId = user.getIban();
        model.addAttribute("user", user);
        model.addAttribute("iban", userService.findOneIbanById(ibanId));
        model.addAttribute("operations", userService.findAllOperationsBySenderIban(ibanId));
        return "addPayment";
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
        String route = ":/account/";
        if ("admin".equals(user.getRole())){
            route = ":/admin/";
        }
        if (usPas.equals(password)){
            System.out.println("enter is successful");
            return "redirect:" + request.getScheme() + route + user.getId();
        }
        else {
            System.out.println("enter пошло не так");

        }
//        return "error";
        return "redirect:" + request.getScheme() + ":/error";
    }

    @RequestMapping(value = "/logout")
    public String LogOut(){
        return "pay_signIn";
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
