package com.blogapp.payloads;

import com.blogapp.entities.Category;
import com.blogapp.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private String title;

    private String content;

    private String imageName;

    private Date addDate;

    private CategoryDTO category;

    private UserDTO user;

}
