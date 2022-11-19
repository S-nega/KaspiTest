package kz.narxoz.demokaspi.services;

import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAllUsers();
    void saveUser(User user);
    User findOneById(int id);
    void deleteUser(int id);

    void saveOperation(Operation operation);

    public List<Message> findAllMessages();
    void saveMessage(Message message);


    void notify(Operation operation);

//    void visited(Message message);
//    public List<Operation> findAllOperations();

//    int findOneByUserId(int id);
//    public List<Operation> findAllByIbanId(int id);

}
