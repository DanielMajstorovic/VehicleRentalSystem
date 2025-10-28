package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PromotionID", nullable = false)
    private Integer id;

    @Column(name = "Title", nullable = false, length = 128)
    private String title;

    @Column(name = "Description", nullable = false, length = 512)
    private String description;

    @Column(name = "StartsAt", nullable = false)
    private Instant startsAt;

    @Column(name = "EndsAt", nullable = false)
    private Instant endsAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Instant startsAt) {
        this.startsAt = startsAt;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Instant endsAt) {
        this.endsAt = endsAt;
    }

}