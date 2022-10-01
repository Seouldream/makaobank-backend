package kr.megaptera.makaobank.exceptions;

public class ExistedAccountNumber extends RuntimeException {
  public ExistedAccountNumber() {
    super("이미 존재하는 계좌입니다.");
  }
}
