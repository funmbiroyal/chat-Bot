package dev.gracie.elearn.data.dtos.requests;

import lombok.*;

@Getter
@Setter

public class SendMailRequest {

    private String name;
    private String to;
    private String from;
    private String subject;
}
