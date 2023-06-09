package com.group.BuzzNet.Post;

import com.group.BuzzNet.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void save(){
        long userId = 1;
        String caption = """
                The quick brown fox jumps over the lazy dog. The quick brown fox jumps over the lazy dog.""";
        PostModel post = PostModel.builder()
                .caption(caption)
                .date(LocalDate.now())
                .user(userRepository.findById(userId).orElse(null))
                .build();

        postService.save(post);
    }
}