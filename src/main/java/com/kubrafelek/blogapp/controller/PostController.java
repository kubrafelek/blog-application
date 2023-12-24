package com.kubrafelek.blogapp.controller;

import com.kubrafelek.blogapp.model.Account;
import com.kubrafelek.blogapp.model.Post;
import com.kubrafelek.blogapp.service.AccountService;
import com.kubrafelek.blogapp.service.FileService;
import com.kubrafelek.blogapp.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;


@Controller
@Slf4j
public class PostController {

    private final PostService postService;
    private final AccountService accountService;
    private final FileService fileService;

    public PostController(PostService postService, AccountService accountService, FileService fileService) {
        this.postService = postService;
        this.accountService = accountService;
        this.fileService = fileService;
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {

        // find post by id
        Optional<Post> optionalPost = this.postService.getById(id);

        // if post exists put it in model
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
    }

    @PostMapping("/posts/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updatePost(@PathVariable Long id, Post post, @RequestParam("file") MultipartFile file) {

        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            existingPost.setTitle(post.getTitle());
            existingPost.setBody(post.getBody());

            try {
                fileService.save(file);
                existingPost.setImageFilePath(file.getOriginalFilename());
            } catch (Exception e) {
                log.error("Error processing file: {}", file.getOriginalFilename());
            }

            postService.save(existingPost);
        }

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/new")
    public String createNewPost(Model model) {

        Post post = new Post();
        model.addAttribute("post", post);
        return "post_new";
    }

    @PostMapping("/posts/new")
    public String createNewPost(@ModelAttribute Post post) {
        Account account = accountService.findOneByEmail("admin.admin@domain.com")
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        post.setAccount(account);
        postService.save(post);
        return "redirect:/";
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPostForEdit(@PathVariable Long id, Model model) {

        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        // if post exist put it in model
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post_edit";
        } else {
            return "404";
        }
    }

    @GetMapping("/posts/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePost(@PathVariable Long id) {

        // find post by id
        Optional<Post> optionalPost = postService.getById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            postService.delete(post);
            return "redirect:/";
        } else {
            return "404";
        }
    }

}