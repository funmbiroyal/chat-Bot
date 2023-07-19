package dev.gracie.elearn.data.dtos.responses;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MailResponse {

    private String message;
    private boolean status;
}