package com.kubrafelek.blogapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {
    private final Path root = Paths.get("./uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException ex) {
            throw new RuntimeException("Could not initialize root folder");
        }
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Resource load(String filename) {
        if (filename == null) return null;
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException mex) {
            throw new RuntimeException("Error: " + mex.getMessage());
        }
    }
}
