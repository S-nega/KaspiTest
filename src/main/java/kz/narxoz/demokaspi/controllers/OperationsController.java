//package kz.narxoz.demokaspi.controllers;
//
//import kz.narxoz.demokaspi.services.OperationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//public class OperationsController {
//
//    @Autowired
//    private OperationService operationService;
//
//    @GetMapping(value = "/operations/{id}")
//    public String operations(@PathVariable(value = "id") int id, Model model){
////        model.addAttribute("operations", userService.findAllByIbanId(userService.findOneByUserId(id)));
//        model.addAttribute("operations", operationService.findAllOperations());
////        findAllByIbanId(id)
////        operationService.findOneByUserId(1)
//        return "operations";
//    }
//}
