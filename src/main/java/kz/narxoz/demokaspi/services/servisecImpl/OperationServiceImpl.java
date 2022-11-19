package kz.narxoz.demokaspi.services.servisecImpl;

import kz.narxoz.demokaspi.entity.Operation;
import kz.narxoz.demokaspi.entity.User;
import kz.narxoz.demokaspi.repository.OperationRepository;
import kz.narxoz.demokaspi.repository.UserRepository;
import kz.narxoz.demokaspi.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;
//    private UserRepository userRepository;


    @Override
    public List<Operation> findAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        return operations;
    }

    @Override
    public void saveOperation(Operation operation) {
        operationRepository.save(operation);
    }

    @Override
    public Operation findOneById(int id) {
        return operationRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteOperation(int id) {
        operationRepository.deleteById(id);
    }



//    @Override
//    public int findOneByUserId(int id) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user!= null){
//            return user.getIban();
//        }
//        else{
//            return 0;
//        }
//        //        return userRepository.findById(id).get().getIban();
//    }
//
    @Override
    public List<Operation> findAllByIbanId(int id) {
        ArrayList<Operation> firstCatch = (ArrayList<Operation>) operationRepository.findAll();
        ArrayList<Operation> operations = new ArrayList <>();
        for (int i=0; i<firstCatch.size(); i++){
            if(firstCatch.get(i).getIban_getter() == id){
                operations.add(firstCatch.get(i));
            }
        }
        return operations;
    }
}
