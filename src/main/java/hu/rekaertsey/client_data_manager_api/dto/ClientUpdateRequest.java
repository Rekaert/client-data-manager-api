package hu.rekaertsey.client_data_manager_api.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientUpdateRequest {

    private String firstName;
    
    private String lastName;
    
    @Past(message="Date of birth must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    @Email(message="Invalid email format")
    private String email;
    
    @Pattern(regexp = "^(\\+[1-9]\\d{6,14})?$", message = "Valid phone number format is +36201231234")
    private String phone;
    
    private String address;

}
