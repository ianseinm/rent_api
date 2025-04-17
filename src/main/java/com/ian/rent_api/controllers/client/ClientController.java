package com.ian.rent_api.controllers.client;

import com.ian.rent_api.dtos.ApiResponse;
import com.ian.rent_api.dtos.client.ClientRequestPatchDTO;
import com.ian.rent_api.dtos.client.ClientResponseDTO;
import com.ian.rent_api.models.client.Client;
import com.ian.rent_api.services.client.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public ResponseEntity<ApiResponse<Client>> createClient(@Valid @RequestBody Client client) {
        clientService.createClient(client);

        ApiResponse<Client> apiResponse = ApiResponse.ofInformation("Client created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/client/{clientNumber}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> getClient(@PathVariable String clientNumber){
        ClientResponseDTO client = this.clientService.getClient(clientNumber);

        ApiResponse<ClientResponseDTO> response = ApiResponse.ofSingle(client);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> getAll(){
        List<ClientResponseDTO> clients = this.clientService.getClients();

        ApiResponse<ClientResponseDTO> response = ApiResponse.ofList(clients);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/client/{clientNumber}")
    public ResponseEntity<ApiResponse<Client>> updateClient(@Valid @RequestBody ClientRequestPatchDTO client, @PathVariable String clientNumber) throws IllegalAccessException {
        if(client.isNull()){
            throw new IllegalArgumentException("No arguments provided to update");
        }
        clientService.updateClient(client, clientNumber);

        ApiResponse<Client> apiResponse = ApiResponse.ofInformation("Client updated successfully");

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
