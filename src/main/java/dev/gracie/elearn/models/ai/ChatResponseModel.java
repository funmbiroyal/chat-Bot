package dev.gracie.elearn.models.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ChatResponseModel {
    private String id;
    private String object;
    private Long created;
    private String model;
    private Usage usage;
    private List<Choice> choices;
}
