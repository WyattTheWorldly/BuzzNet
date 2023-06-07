package com.group.BuzzNet.Post;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {

    private long id;
    private String caption;
    private LocalDate date;
    private long userId;

}
