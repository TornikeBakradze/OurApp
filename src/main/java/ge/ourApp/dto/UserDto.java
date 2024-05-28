package ge.ourApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ge.ourApp.enums.Gender;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        Gender gender,
        String token) {
}
