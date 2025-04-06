package com.ian.rent_api.repositories.client;

import com.ian.rent_api.dtos.client.ClientResponseDTO;
import com.ian.rent_api.models.client.Client;
import com.ian.rent_api.models.client.Console;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClientRepository implements IClientRepository{
    JdbcTemplate jdbcTemplate;

    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClientResponseDTO> getClients() {
        Map<Long, ClientResponseDTO> clients = new HashMap<>();

        String query = "SELECT c.id, c.name, c.last_name, c.phone_number, c.email, " +
                "c.is_from_mercado_libre, c.registration_number, cons.name AS console, cons.id AS console_id " +
                "FROM client AS c " +
                "JOIN client_console AS cc ON c.id = cc.client_id " +
                "JOIN console AS cons ON cons.id = cc.console_id ";

        jdbcTemplate.query(query, resultSet -> {
            Long clientId = resultSet.getLong("id");

            Console console = Console.builder()
                    .id(resultSet.getInt("console_id"))
                    .name(resultSet.getString("console"))
                    .build();

            ClientResponseDTO clientDTO = clients.get(clientId);

            if (clientDTO == null) {
                clientDTO = ClientResponseDTO.builder()
                        .clientNumber(resultSet.getString("registration_number"))
                        .name(resultSet.getString("name"))
                        .lastName(resultSet.getString("last_name"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .email(resultSet.getString("email"))
                        .isFromMercadoLibre(resultSet.getBoolean("is_from_mercado_libre"))
                        .consoles(new ArrayList<>(List.of(console)))
                        .build();

                clients.put(clientId, clientDTO);
            } else {
                clientDTO.getConsoles().add(console);
            }
        });


        return new ArrayList<>(clients.values());
    }

    @Override
    public void createClient(Client client) {
        String queryClient = "INSERT INTO client (name, last_name, phone_number, email, is_from_mercado_libre, registration_number) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        String queryClientConsole = "INSERT INTO client_console (client_id, console_id) VALUES (?, ?)";

        Long clientId  = jdbcTemplate.queryForObject(queryClient, Long.class,
                client.getName(), client.getLastName(), client.getPhoneNumber(),
                client.getEmail(), client.isFromMercadoLibre(), client.getClientNumber());

        client.getConsoleIds().forEach((id) -> {
            jdbcTemplate.update(queryClientConsole, clientId, id);
        });
    }

    @Override
    public ClientResponseDTO getClient(String clientNumber) {
        String query = "SELECT c.name, c.last_name, c.phone_number, c.email, " +
                "c.is_from_mercado_libre, c.registration_number, cons.name AS console, cons.id AS console_id " +
                "FROM client AS c " +
                "JOIN client_console AS cc ON c.id = cc.client_id " +
                "JOIN console AS cons ON cons.id = cc.console_id " +
                "WHERE c.registration_number = ?";

        final ClientResponseDTO[] clientDTOContainer = new ClientResponseDTO[1];
        List<Console> consoles = new ArrayList<>();

        jdbcTemplate.query(query, (resultSet) -> {
            if (clientDTOContainer[0] == null) {
                clientDTOContainer[0] = ClientResponseDTO.builder()
                        .clientNumber(resultSet.getString("registration_number"))
                        .name(resultSet.getString("name"))
                        .lastName(resultSet.getString("last_name"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .email(resultSet.getString("email"))
                        .isFromMercadoLibre(resultSet.getBoolean("is_from_mercado_libre"))
                        .consoles(consoles)
                        .build();
            }

            Console console = Console.builder()
                    .id(resultSet.getInt("console_id"))
                    .name(resultSet.getString("console"))
                    .build();
            consoles.add(console);

        }, clientNumber);

        return clientDTOContainer[0];
    }
}
