package com.assignment.LibraryManager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
   // @NotBlank(message = "Id shouldn't be null")
    private Integer id;
    @NotBlank(message = "Name shouldn't be null")
    private String name;
    @NotBlank(message = "Biography shouldn't be null")
    private String biography;
}
