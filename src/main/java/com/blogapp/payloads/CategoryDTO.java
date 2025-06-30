package com.blogapp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;

    @NotBlank
    @Size(min = 4, max = 20)
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, max = 200)
    private String categoryDescription;
}
