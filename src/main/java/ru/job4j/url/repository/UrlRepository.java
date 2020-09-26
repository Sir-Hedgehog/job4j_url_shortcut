package ru.job4j.url.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.url.model.Url;
import java.util.List;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 2.0
 * @since 26.09.2020
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

    /**
     * Метод инкрементирует значение посещения url-адреса
     * @param urlId - идентификатор url
     */

    @Modifying
    @Transactional
    @Query(value = "UPDATE urls SET total = total + 1 WHERE id = :urlId", nativeQuery = true)
    void updateUrlTotal(@Param("urlId") int urlId);
}
