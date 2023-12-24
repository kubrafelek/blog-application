package com.kubrafelek.blogapp.config;

import com.kubrafelek.blogapp.model.Account;
import com.kubrafelek.blogapp.model.Authority;
import com.kubrafelek.blogapp.model.Post;
import com.kubrafelek.blogapp.repository.AuthorityRepository;
import com.kubrafelek.blogapp.service.AccountService;
import com.kubrafelek.blogapp.service.FileService;
import com.kubrafelek.blogapp.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {

    private FileService fileService;

    private PostService postService;

    private AccountService accountService;

    private AuthorityRepository authorityRepository;

    public SeedData(FileService fileService, PostService postService, AccountService accountService, AuthorityRepository authorityRepository) {
        this.fileService = fileService;
        this.postService = postService;
        this.accountService = accountService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        fileService.init();

        List<Post> posts = postService.getAll();

        if (posts.size() == 0) {

            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Account account1 = Account
                    .builder()
                    .firstName("user_first")
                    .lastName("user_last")
                    .email("user.user@domain.com")
                    .password("password")
                    .build();

            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            account1.setAuthorities(authorities1);

            Account account2 = Account
                    .builder()
                    .firstName("admin_first")
                    .lastName("admin_last")
                    .email("admin.admin@domain.com")
                    .password("password")
                    .build();

            Set<Authority> authorities2 = new HashSet<>();
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            //authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            account2.setAuthorities(authorities2);

            accountService.save(account1);
            accountService.save(account2);

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
                    .account(account2)
                    .imageFilePath("pexels-adrien-olichon-16059681.jpg")
                    .build();

            postService.save(post1);
            postService.save(post2);
        }
    }
}

