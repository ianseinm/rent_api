package com.ian.rent_api.models.client;

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
public class Client {
    @NotBlank
    private String clientNumber;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{10,15}", message = "El teléfono debe contener solo números y tener entre 10 y 15 dígitos")
    private String phoneNumber;

    @NotBlank
    @Email(message = "El email debe ser válido")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "Formato de email no válido")
    private String email;

    private boolean isFromMercadoLibre;

    @NotEmpty
    private List<Integer> consoleIds;
}
