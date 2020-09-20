package ru.job4j.url.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.url.model.Account;
import ru.job4j.url.model.Site;
import ru.job4j.url.repository.AccountRepository;
import ru.job4j.url.repository.SiteRepository;
import static java.util.Collections.emptyList;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private SiteRepository sites;
    private AccountRepository accounts;

    @Autowired
    public UserDetailsServiceImplementation(SiteRepository sites, AccountRepository accounts) {
        this.sites = sites;
        this.accounts = accounts;
    }

    /**
     * Метод загружает детали сохраненного пользователя в SecurityContextHolder
     * @param login - логин
     * @return - данные пользователя
     */

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accounts.findByLogin(login);
        Site site = sites.findByAccount(account);
        if (site == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.getAccount().getLogin(), site.getAccount().getPassword(), emptyList());
    }
}