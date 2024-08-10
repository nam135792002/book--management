package vn.edu.likelion.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("role_name")
    private String roleName;
}
