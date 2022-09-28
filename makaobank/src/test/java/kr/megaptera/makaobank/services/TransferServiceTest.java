package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.repositories.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TransferServiceTest {
  TransferService transferService;

  AccountRepository accountRepository;

  @BeforeEach
  void setup() {
    accountRepository = mock(AccountRepository.class);
    transferService = new TransferService(accountRepository);
  }

  @Test
  void transfer() {
    Long amount1 = 1_000_000L;
    Long amount2 = 20L;
    Long transferAmount = 100_000L;

    Account account1 = new Account(1L, "1234", "From", amount1);
    Account account2 = new Account(2L, "5678", "To", amount2);


    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));
    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    transferService.transfer("1234", "5678", transferAmount);

    assertThat(account1.amount()).isEqualTo(amount1 - transferAmount);
    assertThat(account2.amount()).isEqualTo(amount2 + transferAmount);
  }

  @Test
  void transferWithIncorrectFromAccountNumber() {

    Account account2 = new Account(2L, "5678", "To", 100L);

    given(accountRepository.findByAccountNumber(account2.accountNumber()))
        .willReturn(Optional.of(account2));

    assertThrows(AccountNotFound.class, () -> {
      transferService.transfer("1234", "5678", 100L);
    });
  }

  @Test
  void transferWithIncorrectToAccountNumber() {
    Account account1 = new Account(2L, "5678", "To", 100L);

    given(accountRepository.findByAccountNumber(account1.accountNumber()))
        .willReturn(Optional.of(account1));

    assertThrows(AccountNotFound.class, () -> {
      transferService.transfer("1234", "5678", 100L);
    });
  }


}