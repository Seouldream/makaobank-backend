package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.repositories.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AccountServiceTest {
  AccountService accountService;
  AccountRepository accountRepository;


  @BeforeEach
  void setUp() {
    accountRepository = mock(AccountRepository.class);

    given(accountRepository.findByAccountNumber(any()))
        .willReturn(Optional.of(Account.fake("1234")));
    accountService = new AccountService(accountRepository);
  }

  @Test
  void detail() {
    AccountService accountService = new AccountService(accountRepository);

    Account account = accountService.detail("1234");


    verify(accountRepository).findByAccountNumber("1234");

    assertThat(account.getAccountNumber()).isEqualTo("1234");
  }

}