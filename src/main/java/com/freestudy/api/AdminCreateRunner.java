package com.freestudy.api;

import com.freestudy.api.account.Account;
import com.freestudy.api.account.AccountRepository;
import com.freestudy.api.account.AccountRole;
import com.freestudy.api.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminCreateRunner implements ApplicationRunner {

  @Autowired
  AccountService accountService;

  @Autowired
  AccountRepository accountRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    accountRepository.deleteAll();

    Account account = Account.builder()
            .email("jh@test.com")
            .password("admin")
            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
            .build();

    accountService.saveAccount(account);

  }
}
