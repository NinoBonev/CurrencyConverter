package com.nbonev.converter.areas.currencies.repositories;

import com.nbonev.converter.areas.currencies.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Nino Bonev - 28.4.2019 г., 9:58
 */

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCurrencyName(String name);

    Optional<Currency> findByCurrencyCode(String currencyCode);
}
