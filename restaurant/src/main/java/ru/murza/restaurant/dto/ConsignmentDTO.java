package ru.murza.restaurant.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentDTO {
    private Long id;
    private String title;
    private Date expiration_date;
    private Double count;
    private String ingredient_title;
}
