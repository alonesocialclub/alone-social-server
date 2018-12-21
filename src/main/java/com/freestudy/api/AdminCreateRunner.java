package com.freestudy.api;

import com.freestudy.api.account.Account;
import com.freestudy.api.account.AccountRepository;
import com.freestudy.api.account.AccountRole;
import com.freestudy.api.account.AccountService;
import com.freestudy.api.common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("!test")
public class AdminCreateRunner implements ApplicationRunner {

  @Autowired
  AccountService accountService;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  AppProperties appProperties;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    System.out.println(appProperties.getAdminUsername());
    Account account = Account.builder()
            .email(appProperties.getAdminUsername())
            .password("admin")
            .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
            .build();

    accountService.saveAccount(account);

  }
}
