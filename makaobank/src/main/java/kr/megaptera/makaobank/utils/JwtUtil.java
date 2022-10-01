package kr.megaptera.makaobank.utils;

import com.auth0.jwt.*;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.interfaces.*;
import kr.megaptera.makaobank.models.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

public class JwtUtil {
  private Algorithm algorithm;

  public JwtUtil(String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  public String encode(AccountNumber accountNumber) {
    String token = JWT.create()
        .withClaim("accountNumber", accountNumber.value())
        .sign(algorithm);
    return token;
  }

  public AccountNumber decode(String token) {
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT verify = verifier.verify(token);
    String value = verify.getClaim("accountNumber").asString();

    return new AccountNumber(value);
  }
}
