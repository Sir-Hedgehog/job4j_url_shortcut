package ru.job4j.url.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.url.model.Account;
import ru.job4j.url.model.Site;
import ru.job4j.url.model.Url;
import ru.job4j.url.repository.SiteRepository;
import ru.job4j.url.repository.UrlRepository;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@RestController
@RequestMapping("/")
public class SiteController {
    private final SiteRepository sites;
    private final UrlRepository urls;
    private BCryptPasswordEncoder encoder;
    private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);

    public SiteController(SiteRepository sites, UrlRepository urls, BCryptPasswordEncoder encoder) {
        this.sites = sites;
        this.urls = urls;
        this.encoder = encoder;
    }

    /**
     * Метод регистрирует новый сайт и отображает результат сохранения аккаунта в систему
     * @param site - новый сайт
     * @return - аккаунт
     */

    @PostMapping("/registration")
    public ResponseEntity<Account> register(@RequestBody Site site) {
        String login = site.getHost();
        String password = site.getHost().split("\\.")[0];
        String encodedPassword = encoder.encode(password);
        Account accountForView = new Account(true, login, password);
        Account accountForDatabase = new Account(true, login, encodedPassword);
        site.setAccount(accountForDatabase);
        this.sites.save(site);
        return new ResponseEntity<>(accountForView, HttpStatus.CREATED);
    }

    /**
     * Метод генерирует уникальный ключ для url сохраненного сайта
     * @param url - url сохраненного сайта
     * @return - уникальный ключ
     */

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody Url url) {
        Site elect = new Site();
        for (Site site : sites.findAll()) {
            if (url.getUrl().contains(site.getHost()) && site.getAccount().isRegistered()) {
                elect = site;
                break;
            }
        }
        url.setSite(elect);
        url.setKey();
        this.urls.save(url);
        return new ResponseEntity<>(url.getKey(), HttpStatus.OK);
    }

    /**
     * Метод совершает переадресацию на url сайта по уникальному ключу
     * @param uniqueKey - уникальный ключ
     * @return - переадресация на url сайта
     */

    @GetMapping("/redirect/{unique_key}")
    public ResponseEntity<String> redirect(@PathVariable(value = "unique_key") String uniqueKey) {
        Url url = urls.findByKey(uniqueKey);
        url.setTotal();
        this.urls.save(url);
        return new ResponseEntity<>("HTTP CODE: 302. REDIRECT: " + url.getUrl(), HttpStatus.FOUND);
    }

    /**
     * Метод формирует статистические данные по количеству вызовов всех адресов
     * @return - статистические данные
     */

    @GetMapping("/statistic")
    public ResponseEntity<Map<String, Integer>> getStatistic() {
        Map<String, Integer> privateCounter = new HashMap<>();
        for (Url url : urls.findAll()) {
            privateCounter.put(url.getUrl(), url.getTotal());
        }
        return new ResponseEntity<>(privateCounter, HttpStatus.OK);
    }
}
