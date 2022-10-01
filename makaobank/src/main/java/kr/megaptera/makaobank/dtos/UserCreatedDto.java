package kr.megaptera.makaobank.dtos;

public class UserCreatedDto {
  private Long id;

  private String name;

  private String accountNumber;

  public UserCreatedDto() {
  }

  public UserCreatedDto(Long id, String name, String accountNumber) {
    this.id = id;
    this.name = name;
    this.accountNumber = accountNumber;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAccountNumber() {
    return accountNumber;
  }
}
