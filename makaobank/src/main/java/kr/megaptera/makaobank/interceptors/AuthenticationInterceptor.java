package kr.megaptera.makaobank.interceptors;

import com.auth0.jwt.exceptions.*;
import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.repositories.*;
import kr.megaptera.makaobank.utils.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;

public class AuthenticationInterceptor implements HandlerInterceptor {
  private final JwtUtil jwtUtil;

  public AuthenticationInterceptor(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;

  }

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) throws Exception {

    String authorization = request.getHeader(("Authorization"));

    if (authorization == null || !authorization.startsWith("Bearer ")) {
      //에러
      return true;
    }

    String accessToken = authorization.substring("Bearer ".length());
    try {
      AccountNumber accountNumber = jwtUtil.decode(accessToken);

      request.setAttribute("accountNumber", accountNumber);

      return true;
    } catch (JWTCreationException exception) {
      throw new AuthenticationError();
    }
  }
}
