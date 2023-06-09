package com.group.BuzzNet.Post;


import com.group.BuzzNet.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO Begin implementation of the Post Service class and test!
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public void save(PostModel post){
        if (post.getDate() == null){
            throw new IllegalStateException("The date field can not be left null.");
        }

        if (post.getCaption().length() > PostConstants.CAPTION_LENGTH){
            throw new IllegalStateException("The post caption is too large, expecting "+PostConstants.CAPTION_LENGTH+" characters or less");
        }

        if (post.getUser() == null){
            throw new IllegalStateException("No user was provided to create post.");
        }

        long userId = post.getUser().getId();
        if (userRepository.findById(userId).isEmpty()){
            throw new IllegalStateException("No user was found with the id: "+userId);
        }

        postRepository.save(post);
    }

    public List<PostDto> findAll(){
        return postRepository.findAll()
                .stream()
                .map(this::adapter)
                .collect(Collectors.toList());
    }

    public PostDto findById(long postId){
        return postRepository.findById(postId)
                .map(this::adapter)
                .orElseThrow(() -> new IllegalStateException("No post was found with the id: "+postId));
    }

    private PostDto adapter(PostModel post){
        return PostDto.builder()
                .id(post.getId())
                .caption(post.getCaption())
                .date(post.getDate())
                .userId(post.getUser().getId())
                .build();
    }
}
