package kr.megaptera.makaobank.services;

import kr.megaptera.makaobank.dtos.UserRegistrationDto;
import kr.megaptera.makaobank.exceptions.ExistedAccountNumber;
import kr.megaptera.makaobank.models.Account;
import kr.megaptera.makaobank.models.AccountNumber;
import kr.megaptera.makaobank.models.User;
import kr.megaptera.makaobank.repositories.AccountRepository;
import kr.megaptera.makaobank.repositories.TransactionRepository;
import kr.megaptera.makaobank.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;

  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     AccountRepository accountRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.accountRepository = accountRepository;
  }

  public User create(UserRegistrationDto userRegistrationDto) {
    String accountNumber = userRegistrationDto.getAccountNumber();

    if(!(userRepository.getByAccountNumber(new AccountNumber(accountNumber)) == null)) {
      throw new ExistedAccountNumber();
    }

    User user = new User(null,
        userRegistrationDto.getName(),
        new AccountNumber(userRegistrationDto.getAccountNumber()));

    user.changePassword(passwordEncoder, userRegistrationDto.getPassword());

    Account account = user.registerAccount(user);

    userRepository.save(user);
    accountRepository.save(account);

    return user;
  }
}
