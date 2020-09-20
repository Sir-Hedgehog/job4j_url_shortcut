package ru.job4j.url.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.job4j.url.model.Url;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Component
public interface UrlRepository extends CrudRepository<Url, Integer> {


    /**
     * Метод возвращает список url-адресов
     * @return - список url-адресов
     */

    List<Url> findAll();

    /**
     * Метод находит url-адрес по уникальному ключу
     * @param key - уникальный ключ
     * @return - url-адрес
     */

    Url findByKey(String key);
}
