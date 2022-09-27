package kr.megaptera.makaobank.backdoor;

import org.springframework.jdbc.core.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.*;
import java.time.*;

@RestController
@RequestMapping("backdoor")
@Transactional
public class BackdoorController {
  private final JdbcTemplate jdbcTemplate;

  public BackdoorController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @GetMapping("setup-database")
  public String setupDatabase() {
    LocalDateTime now = LocalDateTime.now();

    jdbcTemplate.execute("DELETE FROM account");

    jdbcTemplate.update("" +
              "INSERT INTO account(" +
              " id, name, account_number, amount," +
              " created_at, updated_at" +
              ")" +
              " VALUES(1, 'Tester', '1234', 123000, ?, ?)",
        now, now
    );

    jdbcTemplate.update("" +
              "INSERT INTO account(" +
              " id, name, account_number, amount," +
              " created_at, updated_at" +
              ")" +
              " VALUES(2, 'Ashal', '1234567890', 123456000, ?, ?)",
        now, now
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
