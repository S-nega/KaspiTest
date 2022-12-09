package kz.narxoz.demokaspi.repository;

import kz.narxoz.demokaspi.entity.Message;
import kz.narxoz.demokaspi.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByIbanGetter(int ibanGetter);
}
