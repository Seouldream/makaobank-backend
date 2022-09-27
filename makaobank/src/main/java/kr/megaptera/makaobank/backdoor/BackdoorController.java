package kr.megaptera.makaobank.backdoor;

import org.springframework.jdbc.core.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.*;

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
    jdbcTemplate.execute("DELETE FROM account");

    jdbcTemplate.execute("" +
        "INSERT INTO account(id, name, account_number, amount)" +
        " VALUES(1, 'Tester', '1234', 123000)"
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
