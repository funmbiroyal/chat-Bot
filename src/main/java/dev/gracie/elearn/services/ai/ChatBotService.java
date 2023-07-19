package dev.gracie.elearn.services.ai;

import dev.gracie.elearn.data.dtos.ai.ChatRequest;
import dev.gracie.elearn.data.dtos.ai.ChatResponse;
import org.springframework.stereotype.Service;

@Service

public interface ChatBotService {
    ChatResponse chatGptResponse(Long id, ChatRequest request);
}
