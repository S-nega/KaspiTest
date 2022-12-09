package kz.narxoz.demokaspi.controllers;

import kz.narxoz.demokaspi.authorization.Server;
import kz.narxoz.demokaspi.entity.*;
import kz.narxoz.demokaspi.publisher.EventManager;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    public EventManager events;
    private static Server server;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/footer")
    public String footer(){
        return "footer";
    }

    @RequestMapping(value = "/menu{id}")
    public String menu(@PathVariable(value = "id") int id, Model model){
        User user =  userService.findOneById(id);
        if (user.getActiveAccount() == true) {
            model.addAttribute("user");
            return "menu";
        }
        return "pay_signIn";
    }

    @GetMapping(value = "/logIn/{success}")
    public String kaspiPaySignIn(@PathVariable(name = "success") String success, Model model){
        List<User> users = userService.findAllUsers();
        for (User user: users) {
            user.setActiveAccount(false);
            userService.saveUser(user);
        }
        model.addAttribute("success", success);
        return "pay_signIn";
    }

    @GetMapping(value = "/account/{id}/{success}")
    public String account(@PathVariable(value = "id") int id, Model model,
                          @PathVariable(value = "success") String success){
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true && user.getRole().equals("user")) {
            int ibanId = user.getIban();
            List<Operation> current = new ArrayList<>();
            List<Operation> operations = userService.findAllOperations();
            for (Operation operation : operations) {
                if (operation.getMessage().equals("#buyingOperation")){
                }
                else{
                    if (operation.getIbanGetter() == ibanId) {
                        current.add(operation);
                    }
                    if (operation.getIbanSender() == ibanId) {
                        operation.setOperation_type("-");
                        current.add(operation);
                    }
                }
            }
            model.addAttribute("user", user);
            model.addAttribute("operations", current);
            model.addAttribute("iban", userService.findOneIbanById(ibanId));
            model.addAttribute("success", success);
//        model.addAttribute("operations", userService.findAllByIbanId(userService.findOneByUserId(id)));
            return "account";
        }
        return "pay_signIn";
    }

    @GetMapping(value = "/admin/{id}")
    public String admin(@PathVariable(value = "id") int id, Model model) {
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true && user.getRole().equals("admin")) {
            model.addAttribute("user", user);
            return "admin";
        }
        return "pay_signIn";
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
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true) {
            model.addAttribute("user", user);
            return "successOperation";
        }
        return "pay_signIn";
    }

    @GetMapping(value = "/messages/{id}")
    public String massages(@PathVariable(value = "id") int id, Model model){
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true && user.getRole().equals("user")) {
            List<Message> messages = userService.findAllMessagesByUserIban(user.getIban());
            model.addAttribute("messages", messages);
            model.addAttribute("user", user);
            model.addAttribute("sells", userService.findAllSells());

            for (Message message : messages) {
                message.setVisible(true);
            }
            return "messages";
        }
        return "pay_signIn";
    }

    @GetMapping(value = "/operations/{id}")
    public String operations(@PathVariable() int id, Model model){
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true && user.getRole().equals("user")) {

            int ibanId = user.getIban();
            List<Operation> buyOperations = new ArrayList<>();
            List<Operation> minusOperations = userService.findAllOperationsBySenderIban(ibanId);
            List<Operation> operations = userService.findAllOperations();
            for (Operation operation : operations) {
                if (operation.getMessage().equals("#buyingOperation")) {
                    minusOperations.remove(operation);
                    buyOperations.add(operation);
                }
            }
            model.addAttribute("user", user);
            model.addAttribute("plusOperations", userService.findAllOperationsByGetterIban(ibanId));
            model.addAttribute("minusOperations", minusOperations);
            model.addAttribute("buyOperations", buyOperations);
            return "operations";
        }
        return "pay_signIn";
    }

    @GetMapping("/addOperation/{id}")
    public String addOperatingPage(@PathVariable(value = "id") int id, Model model){
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true && user.getRole().equals("user")) {
            int ibanId = user.getIban();
            model.addAttribute("iban", userService.findOneIbanById(ibanId));
            List<Operation> currentOperations = userService.findAllOperationsBySenderIban(ibanId);
            List<Operation> operations = userService.findAllOperationsBySenderIban(ibanId);
            for (Operation operation : operations) {
                if (operation.getMessage().equals("#buyingOperation")) {
                    currentOperations.remove(operation);
                }
            }
            model.addAttribute("user", user);
            model.addAttribute("operations", currentOperations);
            return "addOperation2";
        }
        return "pay_signIn";
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
        messages.setIbanGetter(currentIbanGetter);
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
        if (user.getActiveAccount() == true && user.getRole().equals("user")){
            List<Operation> buyOperations = new ArrayList<>();
            List<Operation> operations = userService.findAllOperationsBySenderIban(user.getIban());
            for (Operation operation: operations) {
                if (operation.getMessage().equals("#buyingOperation")){
                    buyOperations.add(operation);
                }
            }
            model.addAttribute("user", user);
            model.addAttribute("operations", buyOperations);
            return "payment";
        }
        return "pay_signIn";
    }

    @GetMapping(value = "/addPayment/{id}")
    public String addPaymentPage(@PathVariable(value = "id") int id, Model model){
        User user = userService.findOneById(id);
        if (user.getActiveAccount() == true && user.getRole().equals("user")) {
            int ibanId = user.getIban();
            model.addAttribute("user", user);
            model.addAttribute("iban", userService.findOneIbanById(ibanId));
            model.addAttribute("operations", userService.findAllOperationsBySenderIban(ibanId));
            return "addPayment";
        }
        return "pay_signIn";
    }

    @GetMapping(value = "/register/{success}")
    public String registrationPage(@PathVariable(value = "success") String success, Model model) {
        model.addAttribute("success", success);
        return "registrationPage";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("kaspi_users") User user,
                               HttpServletRequest request){
//        ModelAndView model = new ModelAndView("error");//+message
        String success = "true";
        if(userService.createUser(user)){
//            model = new ModelAndView("pay_signIn");//+message
            user.setActiveAccount(true);
            userService.saveUser(user);
            System.out.println("регистрация выполнена успешно");
            return "redirect:" + request.getScheme() + ":/account/" + user.getId() + "/" + success;
        }
        success = "false";
        System.out.println("что-то пошло не так");
        return "redirect:" + request.getScheme() + ":/register/" + success;

    }

    @PostMapping(value = "/myLog")
    public String authorizeUser(@RequestParam(name = "phone_number") String phone_number,
                                @RequestParam(name = "password") String password,
                                HttpServletRequest request){
        User user = userService.getUserByPhoneNumber(phone_number);
        String success = "false";
        System.out.println(user);
        if (user == null){
            System.out.println("user == null");
            return "redirect:" + request.getScheme() + ":/logIn/" + success;
        }
        System.out.println(password);
        String usPas = user.getPassword();
        System.out.println(usPas);
        String route = ":/account/";
        if ("admin".equals(user.getRole())){
            route = ":/admin/";
        }
        if (usPas.equals(password)){
            user.setActiveAccount(true);
            userService.saveUser(user);
            System.out.println("enter is successful");
            return "redirect:" + request.getScheme() + route + user.getId() + "/" + success;
        }
        else {
            System.out.println("enter пошло не так");
        }
        return "redirect:" + request.getScheme() + ":/logIn/" + success;
    }

    @RequestMapping(value = "/logout/{id}")
    public String LogOut(@PathVariable() int id, Model model){
        User user = userService.findOneById(id);
        user.setActiveAccount(false);
        userService.saveUser(user);
        model.addAttribute("success", "true");
        return "pay_signIn";
    }
}
