package com.ian.rent_api.dtos.client;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequestPatchDTO {
    private String clientNumber;

    @Size(min = 2, max = 50)
    private String name;

    @Size(min = 2, max = 50)
    private String lastName;

    @Pattern(regexp = "^\\d{10,15}$", message = "Phone number must contain only digits and be between 10 and 15 characters long.")
    private String phoneNumber;

    @Email(message = "Invalid email format.")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format.")
    private String email;

    private Boolean isFromMercadoLibre;

    private List<Integer> consoleIds;

    public boolean isNull(){
        return clientNumber == null && name == null && lastName == null && phoneNumber == null && email == null && consoleIds == null;
    }
}
