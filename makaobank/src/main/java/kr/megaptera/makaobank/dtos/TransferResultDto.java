package kr.megaptera.makaobank.dtos;

public class TransferResultDto {
  public Long getTransferred() {
    return transferred;
  }

  public TransferResultDto(Long transferred) {
    this.transferred = transferred;
  }

  private Long transferred;

  public TransferResultDto() {
  }
}
