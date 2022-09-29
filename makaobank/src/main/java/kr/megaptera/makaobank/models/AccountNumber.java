package kr.megaptera.makaobank.models;

import javax.persistence.*;


@Embeddable
public class AccountNumber {
  @Column(name = "account_number")
  private String value;

  public AccountNumber(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public AccountNumber() {
  }

  @Override
  public String toString() {
    return "AccountNumber(" + value + ")";
  }

  @Override
  public boolean equals(Object other) {
    return other != null &&
        other.getClass() == AccountNumber.class &&
        this.value.equals(((AccountNumber) other).value);

  }
}
