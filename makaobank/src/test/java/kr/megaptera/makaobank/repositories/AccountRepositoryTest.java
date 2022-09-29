package kr.megaptera.makaobank.repositories;

import kr.megaptera.makaobank.models.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountRepositoryTest {
  @Autowired
  private AccountRepository accountRepository;

  @Test
  void save() {
    Account account = new Account(new AccountNumber("12345"), "Tester");

    accountRepository.save(account);
  }

}