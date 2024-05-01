package com.assignment.LibraryManager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Integer id;
    @NotBlank(message = "Title shouldn't be null")
    private String title;
    @NotBlank(message = "Author shouldn't be null")
    private String author;
    @NotBlank(message = "Isbn Shouldn't be null")
    private String isbn;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate publicationYear;


}
