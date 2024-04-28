package ru.murza.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientsExpensesDTO {
    HashMap<String, Double> expenses;
    HashMap<String, String> measures;
}
