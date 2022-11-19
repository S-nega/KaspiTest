package kz.narxoz.demokaspi.services.servisecImpl;

import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.entity.User;
import kz.narxoz.demokaspi.repository.MessageRepository;
import kz.narxoz.demokaspi.repository.OperationRepository;
import kz.narxoz.demokaspi.repository.UserRepository;
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

    @Override
    public void notify(Operation operation) {
        System.out.println("По номеру счета KZ00900" + operation.getIban_getter() + " поступил перевод в размере: " + operation.getSum() + "тг");
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

//    @Override
//    public int findOneByUserId(int id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user!= null){
//            return user.getIban();
//        }
//        else{
//            return 0;
//        }
//                //        return userRepository.findById(id).get().getIban();
//    }

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
