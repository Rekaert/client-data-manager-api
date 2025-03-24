package hu.rekaertsey.client_data_manager_api.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException (Long id) {
        super(String.format("Client with ID %d not found.", id));
    }

}
