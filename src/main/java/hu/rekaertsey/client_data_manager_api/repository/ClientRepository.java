package hu.rekaertsey.client_data_manager_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.rekaertsey.client_data_manager_api.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(c.birthDate)) FROM Client c")
    Double findAVGAge();

}
