package org.unibl.etf.ip.etfbl_ipbackend.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostID", nullable = false)
    private Integer id;

    @Column(name = "Title", nullable = false, length = 128)
    private String title;

    @Column(name = "Content", nullable = false, length = 512)
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}