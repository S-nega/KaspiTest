package kz.narxoz.demokaspi.repository;

import kz.narxoz.demokaspi.entity.Iban;
import kz.narxoz.demokaspi.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
    List<Operation> findAllByIbanSender(int ibanSender);
    List<Operation> findAllByIbanGetter(int ibanGetter);
}
