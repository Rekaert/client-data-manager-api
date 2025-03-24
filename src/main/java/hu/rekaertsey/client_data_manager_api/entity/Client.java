package hu.rekaertsey.client_data_manager_api.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String firstName;
    
    @Column(nullable = false, length = 100)
    private String lastName;
    
    @Column(nullable = false)
    private LocalDate birthDate;
    
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    
    @Column(nullable = false, length = 50, unique = true)
    private String phone;
    
    @Column(nullable = false, length = 100)
    private String address;

}
