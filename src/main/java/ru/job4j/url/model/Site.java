package ru.job4j.url.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Entity(name = "Sites")
@Table(name = "sites")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "host")
    private String host;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "site_id")
    private List<Url> urls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Url> getUrl() {
        return urls;
    }

    public void setUrl(List<Url> urls) {
        for (Url url : urls) {
            if (url.getUrl().contains(host)) {
                this.urls = urls;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return id == site.id && Objects.equals(host, site.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, host);
    }

    @Override
    public String toString() {
        return "Site{" + "id=" + id + ", host='" + host + '\'' + '}';
    }
}
