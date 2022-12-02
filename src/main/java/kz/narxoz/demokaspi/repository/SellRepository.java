package kz.narxoz.demokaspi.repository;

import kz.narxoz.demokaspi.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer> {
}
