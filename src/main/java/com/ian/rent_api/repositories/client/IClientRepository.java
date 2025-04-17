package com.ian.rent_api.repositories.client;

import com.ian.rent_api.dtos.client.ClientRequestPatchDTO;
import com.ian.rent_api.dtos.client.ClientResponseDTO;
import com.ian.rent_api.models.client.Client;

import java.util.List;

public interface IClientRepository {
    List<ClientResponseDTO> getClients();

    void createClient(Client client);

    ClientResponseDTO getClient(String clientNumber);

    void updateClient(ClientRequestPatchDTO client, String clientNumber);

    void deleteClient(long id);
}
