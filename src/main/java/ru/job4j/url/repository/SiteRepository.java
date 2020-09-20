package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.job4j.url.model.Account;
import ru.job4j.url.model.Site;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Component
public interface SiteRepository extends CrudRepository<Site, Integer> {

    /**
     * Метод возвращает список сайтов
     * @return - список сайтов
     */

    List<Site> findAll();

    /**
     * Метод находит сайт по аккаунту
     * @param account - аккаунт
     * @return - сайт
     */

    Site findByAccount(Account account);
}
