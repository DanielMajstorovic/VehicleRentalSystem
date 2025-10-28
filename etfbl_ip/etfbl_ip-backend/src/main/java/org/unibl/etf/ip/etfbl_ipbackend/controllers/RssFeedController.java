package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Post;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Promotion;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.PostRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.repos.PromotionRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rss")
public class RssFeedController {
    @Autowired private PromotionRepository promoRepo;
    @Autowired private PostRepository postRepo;

    private final DateTimeFormatter RFC_1123 =
            DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("GMT"));

    @GetMapping(value = "/feed", produces = MediaType.APPLICATION_XML_VALUE)
    public String feed() {
        List<Post> posts = postRepo.findAll();
        List<Promotion> promos = promoRepo.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<rss version=\"2.0\"><channel>")
                .append("<title>ETFBL_IP Feed</title>")
                .append("<link>http://localhost:8080/api/rss/feed</link>")
                .append("<description>Objave i promocije</description>")
                .append("<lastBuildDate>")
                .append(RFC_1123.format(Instant.now()))
                .append("</lastBuildDate>");

        // POST items
        for (Post p : posts) {
            sb.append("<item>")
                    .append("<title>").append(escape(p.getTitle())).append("</title>")
                    .append("<description>").append(escape(p.getContent())).append("</description>")
                    .append("<category>post</category>")
                    .append("</item>");
        }
        // PROMOTION items
        for (Promotion pr : promos) {
            sb.append("<item>")
                    .append("<title>").append(escape(pr.getTitle())).append("</title>")
                    .append("<description>").append(escape(pr.getDescription())).append("</description>")
                    .append("<category>promotion</category>")
                    .append("<startsAt>")
                    .append(RFC_1123.format(pr.getStartsAt()))
                    .append("</startsAt>")
                    .append("<endsAt>")
                    .append(RFC_1123.format(pr.getEndsAt()))
                    .append("</endsAt>")
                    .append("</item>");
        }
        sb.append("</channel></rss>");
        return sb.toString();
    }

    private String escape(String s) {
        return s.replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;")
                .replace("'","&apos;");
    }
}
