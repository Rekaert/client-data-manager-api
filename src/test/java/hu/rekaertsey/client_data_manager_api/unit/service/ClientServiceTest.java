package hu.rekaertsey.client_data_manager_api.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.rekaertsey.client_data_manager_api.dto.ClientCreateRequest;
import hu.rekaertsey.client_data_manager_api.dto.ClientUpdateRequest;
import hu.rekaertsey.client_data_manager_api.entity.Client;
import hu.rekaertsey.client_data_manager_api.exception.ClientNotFoundException;
import hu.rekaertsey.client_data_manager_api.repository.ClientRepository;
import hu.rekaertsey.client_data_manager_api.service.ClientService;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private List<Client> clients;

    @BeforeEach
    void buildClientsList() {
        clients = List.of(
                new Client(1L, "Klára", "Horváth", LocalDate.of(1955, 10, 31), "klara.horvath@test.com", "+36304567898",
                        "Sopron 1234, Vas u. 15."),
                new Client(2L, "Máté", "Kelemen", LocalDate.of(2010, 03, 02), "kelemen.mate@test.com", "+36204922899",
                        "Budapest 1096, Fő út 155."));
    }

    @Test
    void testGetAllClients() {
        when(clientRepository.findAll()).thenReturn(clients);
        List<Client> result = clientService.getAllClients();
        assertEquals(2, result.size());
        assertEquals("Klára", result.get(0).getFirstName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClientById_Found() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clients.get(0)));
        Client result = clientService.getClientById(1L);
        assertNotNull(result);
        assertEquals("Horváth", result.getLastName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(3L));
        verify(clientRepository, times(1)).findById(3L);
    }

    @Test
    void testCreateClient() {

        ClientCreateRequest request = new ClientCreateRequest(
                "Klára",
                "Horváth",
                LocalDate.of(1955, 10, 31),
                "klara.horvath@test.com",
                "+36304567898",
                "Sopron 1234, Vas u. 15.");

        when(clientRepository.save(any(Client.class))).thenReturn(clients.get(0));
        Client result = clientService.createClient(request);
        assertNotNull(result);
        assertEquals("Klára", result.getFirstName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdateClient() {

        ClientUpdateRequest request = new ClientUpdateRequest(null, null, null, null, "+36704927898", null);

        when(clientRepository.findById(2L)).thenReturn(Optional.of(clients.get(1)));
        when(clientRepository.save(any(Client.class))).thenReturn(clients.get(1));

        Client result = clientService.updateClient(2L, request);

        assertNotNull(result);
        assertEquals("+36704927898", result.getPhone());
        verify(clientRepository, times(1)).findById(2L);
        verify(clientRepository, times(1)).save(clients.get(1));
    }

    @Test
    void testDeleteClient() {
        clientService.deleteClient(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetClientAVGAge() {
        when(clientRepository.findAVGAge()).thenReturn(45.0);
        Double result = clientService.getClientAVGAge();
        assertEquals(45.0, result);
        verify(clientRepository, times(1)).findAVGAge();
    }

    @Test
    void testGetClientsBetween18And40() {
        when(clientRepository.findAll()).thenReturn(List.of());
        List<Client> result = clientService.getClientsBetween18And40();
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(clientRepository, times(1)).findAll();
    }
}
