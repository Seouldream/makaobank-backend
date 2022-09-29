package kr.megaptera.makaobank.backdoor;

import org.springframework.jdbc.core.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.*;
import java.time.*;

@RestController
@RequestMapping("backdoor")
@Transactional
public class BackdoorController {
  private final JdbcTemplate jdbcTemplate;

  private final PasswordEncoder passwordEncoder;

  public BackdoorController(JdbcTemplate jdbcTemplate,
                            PasswordEncoder passwordEncoder) {
    this.jdbcTemplate = jdbcTemplate;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("setup-database")
  public String setupDatabase() {
    LocalDateTime now = LocalDateTime.now();

    jdbcTemplate.execute("DELETE FROM transaction");
    jdbcTemplate.execute("DELETE FROM account");

    jdbcTemplate.update("" +
              "INSERT INTO account(" +
              "id, account_number, encoded_password, name," +
              "amount, created_at, updated_at" +
              ")" +
              " VALUES(1, ?, ? ,?, ?, ?, ?)",
        "1234",passwordEncoder.encode("password"),"Tester"
        , 1_000_000, now, now
    );

    jdbcTemplate.update("" +
            "INSERT INTO account(" +
            "id, account_number, encoded_password, name," +
            "amount, created_at, updated_at" +
            ")" +
            " VALUES(2, ?, ? ,?, ?, ?, ?)",
        "1234567890",passwordEncoder.encode("password"),"Ashal"
        , 1_234_567_890, now, now
    );

    return "OK";
  }

  @GetMapping("change-amount")
  public String changeAmount(
      @RequestParam Long userId,
      @RequestParam Long amount
  ) {
    jdbcTemplate.update("UPDATE account SET amount=?", amount);

    return "OK";
  }
}
