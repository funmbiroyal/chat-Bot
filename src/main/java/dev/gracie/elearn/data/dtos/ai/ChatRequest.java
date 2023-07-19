package dev.gracie.elearn.data.dtos.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ChatRequest {
    private String model = "gpt-3.5-turbo";
    private List<Message> messages;
    private double temperature = 0.1;
}
