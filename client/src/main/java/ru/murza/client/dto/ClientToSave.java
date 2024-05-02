package ru.murza.client.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientToSave {

    @NotEmpty(message = "not empty number!")
    String number;

    // Поля для работника
    String name;

    String secondName;

    String itn;

    Double salary;

    String password;

}
