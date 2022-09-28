package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.repositories.*;
import org.springframework.stereotype.*;

import javax.transaction.*;

@Service
@Transactional
public class TransferService {
  private final AccountRepository accountRepository;

  public TransferService(AccountRepository accountRepository) {

    this.accountRepository = accountRepository;
  }

  public Long transfer(String from, String to, Long amount) {
    Account account1 = accountRepository.findByAccountNumber(from)
        .orElseThrow(() -> new AccountNotFound(from));
    System.out.println("from의 넘버: " + account1.accountNumber());

    System.out.println("to: " + to);
    Account account2 = accountRepository.findByAccountNumber(to)
        .orElseThrow(() -> new AccountNotFound(to));
    System.out.println("to의 넘버: " + account2.accountNumber());

    account1.transferTo(account2, amount);
    return amount;
  }
}
