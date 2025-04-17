package com.ian.rent_api.services.client;

import com.ian.rent_api.dtos.client.ClientRequestPatchDTO;
import com.ian.rent_api.dtos.client.ClientResponseDTO;
import com.ian.rent_api.models.client.Client;
import com.ian.rent_api.repositories.client.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final IClientRepository clientRepository;

    @Autowired
    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void createClient(Client client) {
        this.clientRepository.createClient(client);
    }

    public ClientResponseDTO getClient(String clientNumber) {
        return this.clientRepository.getClient(clientNumber);
    }

    public List<ClientResponseDTO> getClients() {
        return this.clientRepository.getClients();
    }

    public void updateClient(ClientRequestPatchDTO client, String clientNumber) {
        this.clientRepository.updateClient(client, clientNumber);
    }

    public void deleteClient(long id) {
        this.clientRepository.deleteClient(id);
    }
}
