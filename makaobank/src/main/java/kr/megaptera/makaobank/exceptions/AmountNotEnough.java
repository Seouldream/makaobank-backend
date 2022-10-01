package kr.megaptera.makaobank.exceptions;

public class AmountNotEnough extends RuntimeException {
  public AmountNotEnough(Long amount) {
    super("계좌 잔액이 부족합니다");
  }
}
