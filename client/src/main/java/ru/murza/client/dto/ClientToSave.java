package ru.murza.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientToSave {

    String name;

    String number;

    String password;

    String secondName; // Поле для менеджера

    String address; // Поле для менеджера

    String itn; // Поле для менеджера
}
