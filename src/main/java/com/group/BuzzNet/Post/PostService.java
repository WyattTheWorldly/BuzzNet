package com.group.BuzzNet.Post;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//TODO Begin implementation of the Post Service class and test!
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

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
