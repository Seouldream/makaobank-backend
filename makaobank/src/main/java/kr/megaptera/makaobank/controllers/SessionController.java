package kr.megaptera.makaobank.controllers;

import kr.megaptera.makaobank.dtos.*;
import kr.megaptera.makaobank.exceptions.*;
import kr.megaptera.makaobank.models.*;
import kr.megaptera.makaobank.services.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("session")
public class SessionController {
  private final LoginService loginService;

  public SessionController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResultDto login(
      @RequestBody LoginRequestDto loginRequestDto
  ) {
    AccountNumber accountNumber
        = new AccountNumber(loginRequestDto.getAccountNumber());
    String password = loginRequestDto.getPassword();

    Account account = loginService.login(accountNumber, password);

    return new LoginResultDto(
        account.accountNumber().value(),
        account.name(),
        account.amount()
    );
  }

  @ExceptionHandler(LoginFailed.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String loginFailed() {
    return "Login failed";
  }
}
