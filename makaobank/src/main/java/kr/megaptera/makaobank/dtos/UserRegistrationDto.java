package kr.megaptera.makaobank.dtos;

public class UserRegistrationDto {
  private String name;

  private String accountNumber;

  private String password;

  private String confirmPassword;

  public UserRegistrationDto() {
  }

  public UserRegistrationDto(String name,
                             String accountNumber,
                             String password,
                             String confirmPassword) {
    this.name = name;
    this.accountNumber = accountNumber;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getName() {
    return name;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }
}
