package hu.rekaertsey.client_data_manager_api.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    
    @NotBlank(message="First name is required")
    @Size(min=2, max=100, message="First name must be between 2 and 100 characters")
    private String firstName;
    
    @NotBlank(message="Last name is required")
    @Size(min=2, max=100, message="Last name must be between 2 and 100 characters")
    private String lastName;
    
    @NotNull(message="Date of birth is required")
    @Past(message="Date of birth must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    @NotBlank(message="Email is required")
    @Email(message="Invalid email format")
    @Size(min=2, max=100, message="Email must be between 2 and 50 characters")
    private String email;
    
    @NotBlank(message="Phone number is required")
    @Pattern(regexp = "^\\+[1-9]\\d{6,14}$", message = "Valid phone number format is +36201231234")
    @Size(min=2, max=100, message="Phone number must be between 2 and 50 characters")
    private String phone;
    
    @NotBlank(message="Address is required")
    @Size(min=2, max=100, message="Address must be between 2 and 100 characters")
    private String address;

}
