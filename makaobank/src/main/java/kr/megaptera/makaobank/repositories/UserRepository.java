package kr.megaptera.makaobank.repositories;

import kr.megaptera.makaobank.models.AccountNumber;
import kr.megaptera.makaobank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User getByAccountNumber(AccountNumber accountNumber);
}
