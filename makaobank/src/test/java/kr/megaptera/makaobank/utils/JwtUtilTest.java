package kr.megaptera.makaobank.utils;

import com.auth0.jwt.exceptions.*;
import kr.megaptera.makaobank.models.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtUtilTest {

  static final String SECRET = "SECRET";

  JwtUtil jwtUtil;

  @BeforeEach
  void setUp() {
    jwtUtil = new JwtUtil(SECRET);
  }

  @Test
  void encodeAndDecode() {

    AccountNumber original = new AccountNumber("1234");
    String token = jwtUtil.encode(original);

    assertThat(token).contains(".");

    AccountNumber accountNumber = jwtUtil.decode(token);

    assertThat(accountNumber).isEqualTo(original);
  }

  @Test
  void decodeError() {
    assertThrows(JWTDecodeException.class, () -> {
      jwtUtil.decode("xxx");
    });
  }
}
