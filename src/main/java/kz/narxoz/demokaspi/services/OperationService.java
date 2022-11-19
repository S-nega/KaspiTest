package kz.narxoz.demokaspi.services;

import kz.narxoz.demokaspi.entity.Operation;

import java.util.List;

public interface OperationService {
    public List<Operation> findAllOperations();
    void saveOperation(Operation operation);
    Operation findOneById(int id);
    void deleteOperation(int id);

//    int findOneByUserId(int id);
    public List<Operation> findAllByIbanId(int id);
}
