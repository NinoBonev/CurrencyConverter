package com.nbonev.converter;

import com.nbonev.converter.areas.currencies.entities.Currency;
import com.nbonev.converter.areas.currencies.repositories.CurrencyRepository;
import com.nbonev.converter.areas.roles.entities.Role;
import com.nbonev.converter.areas.roles.enums.RoleEnum;
import com.nbonev.converter.areas.roles.repositories.RoleRepository;
import com.nbonev.converter.areas.users.entities.User;
import com.nbonev.converter.areas.users.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class CurrencyConverterApplication {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            List<User> allUsers = this.userRepository.findAll();
            List<Currency> allCurrencies = this.currencyRepository.findAll();

            if (allUsers.size() < 1) {
                User admin = new User(
                        "admin",
                        "1234");

                admin.setPassword(this.bCryptPasswordEncoder.encode(admin.getPassword()));

                Role adminRole = new Role();
                adminRole.setRole(RoleEnum.ADMIN.getRoleName());

                this.roleRepository.save(adminRole);

                admin.setAuthorities(Collections.singleton(adminRole));

                this.userRepository.save(admin);
            }

            if (allCurrencies.size() < 1){
                Currency currency0 = new Currency("BG Lev", "BGN",
                        BigDecimal.valueOf(1));
                Currency currency1 = new Currency("US Dollar", "USD",
                        BigDecimal.valueOf(1.75834));
                Currency currency2 = new Currency("British Pound", "GBP",
                        BigDecimal.valueOf(3.26278));
                Currency currency3 = new Currency("Danish Krone", "DKK",
                        BigDecimal.valueOf(0.261954));
                Currency currency4 = new Currency("Indonesian Rupiah", "IDR",
                        BigDecimal.valueOf(0.000123541));

                this.currencyRepository.save(currency0);
                this.currencyRepository.save(currency1);
                this.currencyRepository.save(currency2);
                this.currencyRepository.save(currency3);
                this.currencyRepository.save(currency4);
            }
        };
    }
}
