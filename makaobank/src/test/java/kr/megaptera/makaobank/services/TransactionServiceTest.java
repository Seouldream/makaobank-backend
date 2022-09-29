package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.repositories.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TransactionServiceTest {

  private TransactionService transactionService;
  private TransactionRepository transactionRepository;

  @BeforeEach
  void setup() {
    transactionRepository = mock(TransactionRepository.class);
    transactionService = new TransactionService(transactionRepository);
  }
  @Test
  void list() {
    AccountNumber accountNumber = new AccountNumber("1234");

    Transaction transaction = mock(Transaction.class);

    given(transactionRepository
        .findAllBySenderOrReceiverOrderByCreatedAtDesc(
        accountNumber, accountNumber))
        .willReturn(List.of(transaction));

    List<Transaction> transactions = transactionService.list(accountNumber);

    assertThat(transactions).hasSize(1);
  }

}