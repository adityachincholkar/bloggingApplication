package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 2, message = "Min 2 characters")
    private String categoryTitle;
    @NotBlank
    @Size(min = 10, message = "min 10 characters")
    private String categoryDescription;
}
