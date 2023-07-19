package dev.gracie.elearn.controllers;

import dev.gracie.elearn.data.dtos.ai.ChatRequest;
import dev.gracie.elearn.services.ai.ChatBotService;
import dev.gracie.elearn.services.ai.ChatBotServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/chat-bot")
@AllArgsConstructor
@Slf4j

public class ChatBotController {
    private final ChatBotServiceImpl chatBotService;
    @PostMapping("/chat/{id}")
    public ResponseEntity<?> sendChat(@PathVariable Long id, @RequestBody ChatRequest request){
        return ResponseEntity.ok(chatBotService.chatGptResponse(id, request));
    }
}
