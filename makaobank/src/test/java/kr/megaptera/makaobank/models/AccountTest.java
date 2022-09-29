package kr.megaptera.makaobank.models;

import kr.megaptera.makaobank.exceptions.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

  final Long AMOUNT1 = 1_000_000L;
  final Long AMOUNT2 = 20L;

  Account account1;
  Account account2;

  @BeforeEach
  void setUp() {
    account1 = new Account(1L, new AccountNumber("1234"),"From", AMOUNT1);
    account2 = new Account(2L, new AccountNumber("5678"),"to", AMOUNT2);
  }
  @Test
  void transferTo() {
    Long transferAmount = 100_000L;

    account1.transferTo(account2, transferAmount);

    assertThat(account1.amount()).isEqualTo(AMOUNT1 - transferAmount);
    assertThat(account2.amount()).isEqualTo(AMOUNT2 + transferAmount);
  }

  @Test
  void transferWithNegativeAmount() {
    Long transferAmount = -100_000L;

    AccountNumber accountNumber1 = new AccountNumber("1234");
    AccountNumber accountNumber2 = new AccountNumber("5678");

    Account account1 = new Account(1L, accountNumber1, "From", AMOUNT1);
    Account account2 = new Account(2L, accountNumber2, "To", AMOUNT2);



    assertThrows(IncorrectAmount.class, () -> {
      account1.transferTo(account2, transferAmount);
    });
  }

  @Test
  void transferWithTooLargeAmount() {
    final Long transferAmount = AMOUNT1 + 100_000L;

    AccountNumber accountNumber1 = new AccountNumber("1234");
    AccountNumber accountNumber2 = new AccountNumber("5678");

    Account account1 = new Account(1L, accountNumber1, "From", AMOUNT1);
    Account account2 = new Account(2L, accountNumber2, "To", AMOUNT2);


    assertThrows(IncorrectAmount.class, () -> {
      account1.transferTo(account2, transferAmount);
    });
  }
}