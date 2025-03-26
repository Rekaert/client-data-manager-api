package hu.rekaertsey.client_data_manager_api.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.rekaertsey.client_data_manager_api.dto.ClientCreateRequest;
import hu.rekaertsey.client_data_manager_api.dto.ClientUpdateRequest;
import hu.rekaertsey.client_data_manager_api.entity.Client;
import hu.rekaertsey.client_data_manager_api.exception.ClientNotFoundException;
import hu.rekaertsey.client_data_manager_api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Client createClient(ClientCreateRequest client) {
        Client createdClient = new Client();
        createdClient.setFirstName(client.getFirstName());
        createdClient.setLastName(client.getLastName());
        createdClient.setBirthDate(client.getBirthDate());
        createdClient.setEmail(client.getEmail());
        createdClient.setPhone(client.getPhone());
        createdClient.setAddress(client.getAddress());
        return clientRepository.save(createdClient);
    }

    public Client updateClient(Long id, ClientUpdateRequest updated) {
        Client client = getClientById(id);
        if (StringUtils.hasText(updated.getFirstName())) client.setFirstName(updated.getFirstName());
        if (StringUtils.hasText(updated.getLastName())) client.setLastName(updated.getLastName());
        if (updated.getBirthDate() != null) client.setBirthDate(updated.getBirthDate());
        if (StringUtils.hasText(updated.getEmail())) client.setEmail(updated.getEmail());
        if (StringUtils.hasText(updated.getPhone())) client.setPhone(updated.getPhone());
        if (StringUtils.hasText(updated.getAddress())) client.setAddress(updated.getAddress());
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public Double getClientAVGAge() {
        return clientRepository.findAVGAge();
    }

    public List<Client> getClientsBetween18And40() {
        return getAllClients().stream()
                .filter(client -> {
                    int age = Period.between(client.getBirthDate(), LocalDate.now()).getYears();
                    return age >= 18 && age <= 40;
                })
                .toList();
    }

}
