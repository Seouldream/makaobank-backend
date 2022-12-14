package kr.megaptera.makaobank.models;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AccountNumberTest {
  @Test
  void equals() {

    assertThat(new AccountNumber("123"))
        .isEqualTo(new AccountNumber("123"));

    assertThat(new AccountNumber("1234"))
        .isNotEqualTo(new AccountNumber("1235"));

    assertThat(new AccountNumber("1234")).isNotEqualTo(null);

    assertThat(new AccountNumber("1234")).isNotEqualTo("1234");
  }
}