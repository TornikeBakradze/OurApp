package ge.ourApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String token) {
}
