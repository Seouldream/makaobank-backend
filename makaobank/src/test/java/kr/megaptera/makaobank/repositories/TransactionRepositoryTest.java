package kr.megaptera.makaobank.repositories;

import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.models.Transaction;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

import javax.transaction.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TransactionRepositoryTest {
  private TransactionRepository transactionRepository;

  @Test
  void save() {
    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber("5678");
    Long amount = 100_000L;
    String name= "Test";

    Transaction transaction = new Transaction(
        sender, receiver,amount, name
    );

    transactionRepository.save(transaction);
  }
}