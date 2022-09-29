package kr.megaptera.makaobank.dtos;

public class LoginRequestDto {
  private String accountNumber;
      private String password;

  public LoginRequestDto() {
  }

  public LoginRequestDto(String accountNumber, String password) {
    this.accountNumber = accountNumber;
    this.password = password;
  }

  public String getAccount() {
    return accountNumber;
  }

  public String getPassword() {
    return password;
  }
}
