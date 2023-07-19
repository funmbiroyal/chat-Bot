package dev.gracie.elearn.data.dtos.ai;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Message {
    private String role = "user";
    private String content;
}
