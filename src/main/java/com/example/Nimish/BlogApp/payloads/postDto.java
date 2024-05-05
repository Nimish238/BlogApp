package com.example.Nimish.BlogApp.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class postDto {
    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private categoryDto category;

    private userDto user;

    private Set<commentDto> comments = new HashSet<>();
}
