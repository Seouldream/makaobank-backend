package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.models.Transaction;
import kr.megaptera.makaobank.repositories.*;
import org.springframework.stereotype.*;

import javax.transaction.*;

@Service
@Transactional
public class TransferService {
  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {

    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  public Long transfer(AccountNumber from, AccountNumber to, Long amount, String name) {
    Account account1 = accountRepository.findByAccountNumber(from)
        .orElseThrow(() -> new AccountNotFound(from));
    System.out.println("from의 넘버: " + account1.accountNumber());

    System.out.println("to: " + to);
    Account account2 = accountRepository.findByAccountNumber(to)
        .orElseThrow(() -> new AccountNotFound(to));
    System.out.println("to의 넘버: " + account2.accountNumber());

    account1.transferTo(account2, amount);

    Transaction transaction = new Transaction(account1.accountNumber(), account2.accountNumber(),
        amount, name
    );
    transactionRepository.save(transaction);
    return amount;
  }
}
