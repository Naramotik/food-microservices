package ru.murza.client.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientToSave {

    @NotEmpty(message = "not empty name!")
    String name;

    @NotEmpty(message = "not empty number!")
    String number;

    @NotEmpty(message = "not empty password!")
    String password;

    String secondName; // Поле для менеджера

    String address; // Поле для менеджера

    String itn; // Поле для менеджера
}
