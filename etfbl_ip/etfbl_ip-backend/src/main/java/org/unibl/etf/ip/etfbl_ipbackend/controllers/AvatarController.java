package org.unibl.etf.ip.etfbl_ipbackend.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@RestController
@RequestMapping("/api/avatars")
public class AvatarController {

    private final Path root = Paths.get("uploads/avatars");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(root);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> upload(@PathVariable Integer userId,
                                    @RequestBody byte[] bytes,
                                    @RequestHeader("Content-Type") String type) {
        try {
            if (!type.startsWith("image/"))
                return ResponseEntity.badRequest().body("Only images!");
            Files.write(root.resolve(userId + ".png"), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
}