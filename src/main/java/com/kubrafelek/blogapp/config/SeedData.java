package com.kubrafelek.blogapp.config;

import com.kubrafelek.blogapp.model.Account;
import com.kubrafelek.blogapp.model.Post;
import com.kubrafelek.blogapp.service.AccountService;
import com.kubrafelek.blogapp.service.FileService;
import com.kubrafelek.blogapp.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    private final FileService fileService;
    private final PostService postService;
    private final AccountService accountService;

    public SeedData(FileService fileService, PostService postService, AccountService accountService) {
        this.fileService = fileService;
        this.postService = postService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {

        fileService.init();

        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {

            Account account1 = Account
                    .builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin.admin@domain.com")
                    .password("password")
                    .adminRole("ROLE_ADMIN")
                    .build();

            accountService.save(account1);

            Post post1 = Post
                    .builder()
                    .title("What is Lorem Ipsum?")
                    .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                    .account(account1)
                    .imageFilePath("pexels-adrien-olichon-16059681.jpg")
                    .build();

            Post post2 = Post
                    .builder()
                    .title("Something else Ipsum")
                    .body("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Magna eget est lorem ipsum dolor sit amet consectetur adipiscing. Tempus quam pellentesque nec nam aliquam sem et tortor. Pellentesque sit amet porttitor eget. Sed augue lacus viverra vitae congue eu consequat. Ultrices vitae auctor eu augue. Mattis rhoncus urna neque viverra. Consectetur lorem donec massa sapien faucibus et molestie ac feugiat. Sociis natoque penatibus et magnis dis parturient montes nascetur. Cursus turpis massa tincidunt dui ut ornare lectus. Odio pellentesque diam volutpat commodo sed egestas egestas fringilla. Id cursus metus aliquam eleifend mi. Nibh nisl condimentum id venenatis a condimentum.")
                    .account(account1)
                    .imageFilePath("pexels-adrien-olichon-16059681.jpg")
                    .build();

            postService.save(post1);
            postService.save(post2);
        }
    }
}

