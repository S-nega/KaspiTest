package kz.narxoz.demokaspi.services;

import kz.narxoz.demokaspi.entity.*;

import java.util.List;

public interface UserService {
    public List<User> findAllUsers();
    void saveUser(User user);
    User findOneById(int id);
    User findOneByIbanId(int id);
    void deleteUser(int id);


    public List<Sell> findAllSells();
    void saveSell(Sell sell);
    Sell findOneSellById(int id);
    void deleteSell(int id);

    public List<Iban> findAllIbans();
    void saveIban(Iban iban);
    Iban findOneIbanById(int id);
    void deleteIban(int id);

    public List<Operation> findAllOperations();
    public List<Operation> findAllOperationsByGetterIban(int iban);
    public List<Operation> findAllOperationsBySenderIban(int iban);
    Operation findOneOperationById(int id);
    void saveOperation(Operation operation);

    public List<Message> findAllMessages();
    public List<Message> findAllMessagesByUserIban(int ibanGetter);
    void saveMessage(Message message);

    void notify(Operation operation);

    User getUserByPhoneNumber(String phoneNumber);

    boolean createUser(User user);
//    void visited(Message message);

    int findOneByUserId(int id);
}
