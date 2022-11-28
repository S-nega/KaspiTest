package kz.narxoz.demokaspi.services.servisecImpl;

import kz.narxoz.demokaspi.entity.*;
import kz.narxoz.demokaspi.repository.*;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private IbanRepository ibanRepository;
//    @Autowired
//    private User user;

    @Override
    public List<User> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findOneById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public void saveOperation(Operation operation) {
        operationRepository.save(operation);
    }

    @Override
    public List<Message> findAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
//
//    @Override
//    public User findByPhone_number(String phone_number) {
//        List<User> users = userRepository.findAll();
//        User user = null;
//        String thisUs = phone_number;
//        for (User us: users) {
//            String checkUser = us.getPhone_number();
//            if(thisUs == checkUser){
//                user = us;
//            }
//        }
//        return user;
//    }

    @Override
    public void notify(Operation operation) {
        System.out.println("По номеру счета KZ00900" + operation.getIban_getter() + " поступил перевод в размере: " + operation.getSum() + "тг");
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean createUser(User user){
        User checkUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if(checkUser == null){
            Iban iban = new Iban();
//            Role role = roleRepository.findByRole("user");
//            if(role!=null){
//                ArrayList<Role> arrRole = new ArrayList<>();
//                arrRole.add(role);
//                user.setRole("user");
////                user.setRole(String.valueOf(arrRole));
//                user.setPassword(user.getPassword());
                iban.setUser_phoneNumber(user.getPhoneNumber());
                ibanRepository.save(iban);
                user.setIban(iban.getId());
                user.setRole("user");
                userRepository.save(user);
                return true;
//            }
        }
        return false;
    }


//    @Override
//    public void visited(Message message) {
//        messageRepository.save(message);
//    }

//
//    @Override
//    public List<Operation> findAllOperations() {
//        List<Operation> operations = operationRepository.findAll();
//        return operations;
//    }

    @Override
    public int findOneByUserId(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user!= null){
            return user.getIban();
        }

            return 0;
                //        return userRepository.findById(id).get().getIban();
    }

//    @Override
//    public List<Operation> findAllByIbanId(int id) {
//        ArrayList<Operation> firstCatch = (ArrayList<Operation>) operationRepository.findAll();
//        ArrayList<Operation> operations = new ArrayList <>();
//        for (int i=0; i<firstCatch.size(); i++){
//            if(firstCatch.get(i).getIban_id() == id){
//                operations.add(firstCatch.get(i));
//            }
//        }
//        return operations;
//    }
}
