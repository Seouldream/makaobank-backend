package kr.megaptera.makaobank.dtos;

public class ExistedAccountNumberErrorDto extends ErrorDto {
  public ExistedAccountNumberErrorDto() {
    super(1006, "이미 존재하는 계좌입니다.");
  }
}
