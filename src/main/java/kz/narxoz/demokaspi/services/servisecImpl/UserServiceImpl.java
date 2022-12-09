package kz.narxoz.demokaspi.services.servisecImpl;

import kz.narxoz.demokaspi.entity.*;
import kz.narxoz.demokaspi.repository.*;
import kz.narxoz.demokaspi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SellRepository sellRepository;


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
    public User findOneByIbanId(int id) {
        return userRepository.findByIban(id);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public List<Sell> findAllSells() {
        List<Sell> sells = sellRepository.findAll();
        return sells;
    }

    @Override
    public void saveSell(Sell sell) {
        sellRepository.save(sell);
    }

    @Override
    public Sell findOneSellById(int id) {
        return sellRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteSell(int id) {
        sellRepository.deleteById(id);
    }


    @Override
    public List<Iban> findAllIbans() {
        List<Iban> ibans = ibanRepository.findAll();
        return ibans;
    }

    @Override
    public void saveIban(Iban iban) {
        ibanRepository.save(iban);
    }

    @Override
    public Iban findOneIbanById(int id) {
        return ibanRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteIban(int id) {
        ibanRepository.deleteById(id);
    }


    @Override
    public List<Operation> findAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations;
    }

    @Override
    public List<Operation> findAllOperationsByGetterIban(int iban) {
        List<Operation> operations = operationRepository.findAllByIbanGetter(iban);
        return operations;
    }

    @Override
    public List<Operation> findAllOperationsBySenderIban(int iban) {
        List<Operation> operations = operationRepository.findAllByIbanSender(iban);
        return operations;
    }

    @Override
    public Operation findOneOperationById(int id) {
        return operationRepository.findById(id).orElse(null);
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
    public List<Message> findAllMessagesByUserIban(int ibanGetter) {
        List<Message> messages = messageRepository.findAllByIbanGetter(ibanGetter);
        return messages;
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void notify(Operation operation) {
        System.out.println("По номеру счета KZ00900" + operation.getIbanGetter() + " поступил перевод в размере: " + operation.getSum() + "тг");
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

    @Override
    public int findOneByUserId(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user!= null){
            return user.getIban();
        }
        return 0;
    }
}
