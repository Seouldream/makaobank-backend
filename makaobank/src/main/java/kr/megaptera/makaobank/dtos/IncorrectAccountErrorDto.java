package kr.megaptera.makaobank.dtos;

public class IncorrectAccountErrorDto extends ErrorDto {
  public IncorrectAccountErrorDto() {
    super(1004, "아이디 혹은 비밀번호가 맞지 않습니다");
  }
}
