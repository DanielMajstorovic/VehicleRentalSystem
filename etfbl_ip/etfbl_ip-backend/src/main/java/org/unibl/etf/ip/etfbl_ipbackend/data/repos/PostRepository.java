package org.unibl.etf.ip.etfbl_ipbackend.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.etfbl_ipbackend.data.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}