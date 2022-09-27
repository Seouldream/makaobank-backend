package kr.megaptera.makaobank.models;

import kr.megaptera.makaobank.dtos.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.*;

@Entity
public class Account {
  @Id
  @GeneratedValue
  private Long id;

  private String accountNumber;

  private String name;

  private Long amount;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updateAt;


  public Account() {
  }

  public Account(String accountNumber, String name) {
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = 0L;
  }

  public Account(Long id, String accountNumber, String name, Long amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.accountNumber = accountNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public static Account fake(String accountNumber) {
    return new Account(1L, accountNumber, "Tester", 100L);
  }

  public AccountDto toDto() {
    return new AccountDto(accountNumber, name, amount);
  }
}
