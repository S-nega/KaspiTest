package kz.narxoz.demokaspi.services;

import kz.narxoz.demokaspi.entity.Iban;
import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUsers();
    void saveUser(User user);
    User findOneById(int id);
    void deleteUser(int id);

    public List<Iban> findAllIbans();
    void saveIban(Iban iban);
    Iban findOneIbanById(int id);
    void deleteIban(int id);

    public List<Operation> findAllOperations();
    public List<Operation> findAllOperationsByGetterIban(int iban);
    public List<Operation> findAllOperationsBySenderIban(int iban);
    void saveOperation(Operation operation);

    public List<Message> findAllMessages();
    void saveMessage(Message message);

    void notify(Operation operation);

    User getUserByPhoneNumber(String phoneNumber);

    boolean createUser(User user);
//    void visited(Message message);

    int findOneByUserId(int id);
}
