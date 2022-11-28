package kz.narxoz.demokaspi.controllers;

import kz.narxoz.demokaspi.authorization.Server;
import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.entity.User;
import kz.narxoz.demokaspi.publisher.EventManager;
import kz.narxoz.demokaspi.repository.IbanRepository;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private IbanRepository ibanRepository;
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;//BCryptPasswordEncoder

    @Autowired
//    private Message message;

//    private OperationService operationService;
//    private IbanService ibanService;
//
//    @GetMapping("/index")
//    public String Hello() {
//        return "authorizationPage";
//    }
//
//    public MainController(UserService userService){
//        this.userService = userService;
//    }

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
        model.addAttribute("iban", ibanRepository.findById(ibanId));
//        model.addAttribute("operations", userService.findAllByIbanId(userService.findOneByUserId(id)));
        return "account";
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

    @GetMapping("/addOperation")
    public ModelAndView addOperatingPage(){
        ModelAndView model = new ModelAndView("addOperation");
        return model;
    }

    @PostMapping(value = "/addOperation")
    public ModelAndView addOperation(@ModelAttribute("operation") Operation operation){
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

//    @PostMapping(value = "/register")
//    public String toRegister(@RequestParam(name = "firstname") String firstname,
//                             @RequestParam(name = "lastname") String lastname,
//                             @RequestParam(name = "birth_data") int birth_data,
//                             @RequestParam(name = "address") String address,
//                             @RequestParam(name = "email") String email,
//                             @RequestParam(name = "phoneNumber") String phoneNumber,
//                             @RequestParam(name = "password") String password){
//
//        User newUser = new User();
////        newUser.setStudentId(studentId);
////        newUser.setPassword(passwordEncoder.encode(password));
//        newUser.setFirstname(firstname);
//        newUser.setLastname(lastname);
//        newUser.setBirth_data(birth_data);
//        newUser.setAddress(address);
//        newUser.setEmail(email);
//        newUser.setRole("user");
//        newUser.setPhoneNumber(phoneNumber);
//        newUser.setPassword(password);
//
////        if(userService.createUser(newUser)!=null){
//        if(userService.getUserByPhoneNumber(phoneNumber) != newUser){
//            userService.saveUser(newUser);
//            return "redirect:/pay_signIn";
//        }
//
//        //ваш номер уже зарегистрирован, авторизуйтесь
//        return "register?error";
//    }

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

//
//    private User getUserData(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(!(authentication instanceof AnonymousAuthenticationToken)){
//            User secUser = (User)authentication.getPrincipal();
//            User myUser = userService.getUserByPhoneNumber(secUser.getPhoneNumber());
//            return myUser;
//        }
//        return null;
//    }



//
//    @RequestMapping(value = "/greeting")
//    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }


//    @RequestMapping("/")
////    @GetMapping("/index")
////    public ModelAndView index(User user){
////        ModelAndView model = new ModelAndView("authorisationPage");
//////        model.addObject("employee", employeeService.findAllEmployees());
////        return model;
////    }
//
}
