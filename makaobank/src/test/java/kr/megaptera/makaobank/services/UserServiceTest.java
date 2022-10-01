package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.dtos.UserRegistrationDto;
import kr.megaptera.makaobank.models.User;
import kr.megaptera.makaobank.repositories.AccountRepository;
import kr.megaptera.makaobank.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
  @Test
  void create() {
    UserRepository userRepository = mock(UserRepository.class);
    AccountRepository accountRepository = mock(AccountRepository.class);
    PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    UserService userService = new UserService(
        userRepository,
        passwordEncoder,
        accountRepository
    );

    UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
        "김명훈", "11112222", "Qwe1234!", "Qwe1234!"
    );

    User user = userService.create(userRegistrationDto);

    assertThat(user).isNotNull();

    verify(userRepository).save(user);
  }
}
