package kr.megaptera.makaobank.exceptions;

public class MyAccountFound extends RuntimeException {
  public MyAccountFound() {
    super("본인의 계좌입니다. 다시 입력해주세요");
  }
}
