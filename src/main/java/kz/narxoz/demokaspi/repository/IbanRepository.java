package kz.narxoz.demokaspi.repository;

import kz.narxoz.demokaspi.entity.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbanRepository extends JpaRepository<Iban, Integer> {
}
