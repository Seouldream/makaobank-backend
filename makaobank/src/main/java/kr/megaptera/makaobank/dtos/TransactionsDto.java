package kr.megaptera.makaobank.dtos;

import java.util.*;

public class TransactionsDto {
  private List<TransactionDto> transactions;

  public TransactionsDto(List<TransactionDto> transactions) {
    this.transactions = transactions;
  }

  public TransactionsDto() {
  }

  public List<TransactionDto> getTransactions() {
    return transactions;
  }
}
