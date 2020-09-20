package ru.job4j.url.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Objects;
import java.util.Random;

/**
 * @author Sir-Hedgehog (mailto:quaresma_08@mail.ru)
 * @version 1.0
 * @since 20.09.2020
 */

@Entity(name = "Urls")
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "key")
    private String key;

    @Column(name = "total")
    private int total;

    @ManyToOne
    @JoinColumn(name = "site_id")
    @JsonIgnore
    private Site site;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        this.key = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void setTotal() {
        total = ++total;
    }

    public int getTotal() {
        return total;
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
        Url url1 = (Url) o;
        return id == url1.id && Objects.equals(url, url1.url) && Objects.equals(key, url1.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, key);
    }

    @Override
    public String toString() {
        return "Url{"
                + "id=" + id
                + ", url='" + url + '\''
                + ", key='" + key + '\''
                + '}';
    }
}
