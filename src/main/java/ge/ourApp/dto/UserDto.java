package ge.ourApp.dto;

import lombok.Builder;

@Builder
public record UserDto(
         String firstName,
         String lastName,
         String login,
         String role,
         String token) {
}
