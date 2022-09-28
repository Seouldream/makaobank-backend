package kr.megaptera.makaobank.dtos;

import javax.validation.constraints.*;

public class TransferDto {
  @NotBlank
  private String to;
  @NotNull
  private Long amount;

  public String getTo() {
    return to;
  }

  public Long getAmount() {
    return amount;
  }
}
