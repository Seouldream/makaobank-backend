package kr.megaptera.makaobank.dtos;

public class MyAccountFoundErrorDto extends ErrorDto {
  public MyAccountFoundErrorDto() {
    super(1005, "본인의 계좌입니다. 다시 입력해주세요");
  }
}
