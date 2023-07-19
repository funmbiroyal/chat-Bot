package dev.gracie.elearn.models.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Choice {
    private Double confidence;
    private Text text;
    private String finish_reason;
    private Integer index;
}
