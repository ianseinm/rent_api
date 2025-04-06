package com.ian.rent_api.dtos.client;

import com.ian.rent_api.models.client.Console;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponseDTO {
    private String clientNumber;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private boolean isFromMercadoLibre;
    private List<Console> consoles = new ArrayList<>();
}
