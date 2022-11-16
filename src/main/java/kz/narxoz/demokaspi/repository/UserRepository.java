package kz.narxoz.demokaspi.repository;

import kz.narxoz.demokaspi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
