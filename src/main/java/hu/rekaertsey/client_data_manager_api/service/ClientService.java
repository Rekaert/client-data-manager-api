package hu.rekaertsey.client_data_manager_api.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

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

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updated) {
        Client client = getClientById(id);
        client.setFirstName(updated.getFirstName());
        client.setLastName(updated.getLastName());
        client.setBirthDate(updated.getBirthDate());
        client.setEmail(updated.getEmail());
        client.setPhone(updated.getPhone());
        client.setAddress(updated.getAddress());
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.delete(getClientById(id));
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
