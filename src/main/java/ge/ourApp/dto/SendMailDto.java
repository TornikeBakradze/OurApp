package ge.ourApp.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SendMailDto(
        String userName,
        String userEmail,
        UUID confirmLinkUUID
) {
}
