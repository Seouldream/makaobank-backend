package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.models.Transaction;
import kr.megaptera.makaobank.repositories.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;

@Service
@Transactional
public class TransactionService {
  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<Transaction> list(AccountNumber accountNumber) {
    return transactionRepository
          .findAllBySenderOrReceiverOrderByCreatedAtDesc(
            accountNumber, accountNumber);
  }
}
