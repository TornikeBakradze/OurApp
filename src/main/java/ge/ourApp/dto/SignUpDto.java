package ge.ourApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    @NotEmpty(message = "User name must not be empty")
    @Pattern(regexp = "^[a-zA-Z]+$",
            message = "User name must contain only letters")
    @Size(max = 200, message = "The size of the name is large")
    private String firstname;

    @NotEmpty(message = "User lastname must not be empty")
    @Pattern(regexp = "^[a-zA-Z()]+$",
            message = "User lastname must contain only letters")
    @Size(max = 300,
            message = "The size of the lastname is large")
    private String lastName;

    @NotEmpty(message = "User email must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Invalid email address")
    @Size(max = 300,
            message = "The size of the email is large")
    private String email;

    @NotEmpty(message = "User password must not be empty")
    @Pattern(regexp =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])(?!.*\\s)[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and contain at least" +
                    " one lowercase letter, one uppercase letter, one digit, one special character, and no spaces.")
    @Size(max = 300,
            message = "The size of the password is large")
    private String password;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    @Size(min = 9, max = 9, message = "Phone number size must be 9 digits")
    private String phoneNumber;

    @NotEmpty(message = "Gender is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Gender can be Female or Male")
    private String gender;

}
