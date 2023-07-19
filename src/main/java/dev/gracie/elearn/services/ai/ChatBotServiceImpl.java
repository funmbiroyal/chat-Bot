package dev.gracie.elearn.services.ai;

import dev.gracie.elearn.data.dtos.ai.ChatRequest;
import dev.gracie.elearn.data.dtos.ai.ChatResponse;
import dev.gracie.elearn.data.repositories.AppUserRepository;
import dev.gracie.elearn.models.AppUser;
import dev.gracie.elearn.models.ai.ChatResponseModel;
//import dev.gracie.elearn.models.ai.ResponseDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
@RequiredArgsConstructor
@Slf4j

public class ChatBotServiceImpl implements ChatBotService{
    Dotenv dotenv = Dotenv.configure().load();
    private final AppUserRepository appUserRepository;
    private final RestTemplate restTemplate;
    private final String OPEN_API_KEY= dotenv.get("OPEN_API_KEY");


        @Override
        public ChatResponse chatGptResponse(Long id, ChatRequest request) {
            AppUser appUser = appUserRepository.findUserById(id);
            String URL = "https://api.openai.com/v1/chat/completions";

            HttpHeaders header = new HttpHeaders();
            header.add(CONTENT_TYPE, "application/json");
            header.add(AUTHORIZATION, OPEN_API_KEY);

            HttpEntity<?> httpEntity = new HttpEntity<>(request, header);
            ChatResponseModel response = restTemplate.postForObject(URL, httpEntity, ChatResponseModel.class);

            String content = null;
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                content = response.getChoices().get(0).getText().getContent();
            }

            appUser.getResponses().add(content);
            appUserRepository.save(appUser);

            return ChatResponse.builder()
                    .response(content)
                    .build();
        }
    }
