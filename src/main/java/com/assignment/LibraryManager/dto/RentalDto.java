package com.assignment.LibraryManager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentalDto {
    private Integer id;
    //  @NotBlank(message = "BookID shouldn;t be null")
    private Integer bookId;

    private String renterName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate rentalDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate returnDate;

    // Getters and setters
}

