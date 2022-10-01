package kr.megaptera.makaobank.models;

import kr.megaptera.makaobank.dtos.*;
import kr.megaptera.makaobank.exceptions.*;
import org.hibernate.annotations.*;
import org.springframework.security.crypto.password.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.*;

@Entity
public class Account {
  @Id
  @GeneratedValue
  private Long id;

  @Embedded
  private AccountNumber accountNumber;

  private String encodedPassword;

  private String name;

  private Long amount;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;


  public Account() {
  }

  public Account(AccountNumber accountNumber, String name) {
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = 0L;
  }

  public Account(AccountNumber accountNumber, String name, String passwoprd) {
    this.accountNumber = accountNumber;
    this.name = name;
    this.encodedPassword = passwoprd;
    this.amount = 1000L;
  }

  public Account(Long id, AccountNumber accountNumber, String name, Long amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.accountNumber = accountNumber;
  }

  public void transferTo(Account other, Long amount) {
    if (amount <= 0 || amount > this.amount) {
      throw new IncorrectAmount(amount);
    }

    this.amount -= amount;

    other.amount += amount;
  }

  public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, encodedPassword);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    encodedPassword = passwordEncoder.encode(password);
  }

  public AccountNumber accountNumber() {
    return accountNumber;
  }

  public Long amount() {
    return amount;
  }

  public String name() {
    return name;
  }

  public AccountDto toDto() {
    return new AccountDto(accountNumber.value(), name, amount);
  }

  public static Account fake(String accountNumber) {
    return new Account(
        1L, new AccountNumber(accountNumber), "Tester", 100L);
  }

  public static Account fake(AccountNumber accountNumber) {
    return Account.fake(accountNumber.value());
  }
}
