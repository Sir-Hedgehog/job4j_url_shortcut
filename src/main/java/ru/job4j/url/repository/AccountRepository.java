package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.job4j.url.model.Account;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Component
public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Метод находит аккаунт по логину
     * @param login - логин
     * @return - данные аккаунта
     */

    Account findByLogin(String login);
}
