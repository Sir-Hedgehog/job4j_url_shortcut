package ru.job4j.url.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Entity(name = "Accounts")
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "registered")
    private boolean registered;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "account")
    private Site site;

    public Account() {

    }

    public Account(boolean registered, String login, String password) {
        this.registered = registered;
        this.login = login;
        this.password = password;
    }

    public boolean isRegistered() {
        return registered;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id
                && registered == account.registered
                && Objects.equals(login, account.login)
                && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registered, login, password);
    }

    @Override
    public String toString() {
        return "Account{"
                + "id=" + id
                + ", registered=" + registered
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + '}';
    }
}
