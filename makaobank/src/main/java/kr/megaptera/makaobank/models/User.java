package kr.megaptera.makaobank.models;

import kr.megaptera.makaobank.dtos.UserCreatedDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class User {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Embedded
  private AccountNumber accountNumber;

  private String password;

  private String confirmPassword;

  public User() {
  }

  public User(Long id,
              String name,
              AccountNumber accountNumber) {

    this.id = id;
    this.name = name;
    this.accountNumber = accountNumber;
  }

  public User(Long id,
              String name,
              AccountNumber accountNumber,
              String password,
              String confirmPassword) {
    this.id = id;
    this.name = name;
    this.accountNumber = accountNumber;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public AccountNumber getAccountNumber() {
    return accountNumber;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void changePassword(PasswordEncoder passwordEncoder, String password) {
    this.password = passwordEncoder.encode(password);
  }

  public boolean authenticate(PasswordEncoder passwordEncoder, String password) {
    return passwordEncoder.matches(password, this.password);
  }

  public UserCreatedDto toCreatedDto() {
    return new UserCreatedDto(id, name, accountNumber.value());
  }

  public Account registerAccount(User user) {
    return new Account(user.accountNumber, user.name, user.password);
  }
}
