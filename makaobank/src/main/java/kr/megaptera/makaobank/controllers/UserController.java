package kr.megaptera.makaobank.controllers;

import kr.megaptera.makaobank.dtos.ErrorDto;
import kr.megaptera.makaobank.dtos.ExistedAccountNumberErrorDto;
import kr.megaptera.makaobank.dtos.UserCreatedDto;
import kr.megaptera.makaobank.dtos.UserRegistrationDto;
import kr.megaptera.makaobank.exceptions.ExistedAccountNumber;
import kr.megaptera.makaobank.models.User;
import kr.megaptera.makaobank.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserCreatedDto register(
      @RequestBody UserRegistrationDto userRegistrationDto
  ) {
    User user = userService.create(userRegistrationDto);
    return user.toCreatedDto();
  }

  @ExceptionHandler(ExistedAccountNumber.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto existedAccountNumber() {
    return new ExistedAccountNumberErrorDto();
  }
}
