package vn.edu.likelion.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @JsonProperty("full_name")
    @NotEmpty(message = "Full name must not be empty")
    @Size(min = 5, max = 45, message = "Full name must have 5-45 characters")
    private String fullName;

    @Email
    @NotEmpty(message = "Email must not be empty")
    @Size(min = 10, max = 45, message = "Email must have 10-45 characters")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 45, message = "Password must have 8-45 characters")
    private String password;

    @JsonProperty("role_id")
    private Integer roleId;


}
