package io.github.shinyruo.c2g.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.shinyruo.c2g.config.GithubApiConfig;
import io.github.shinyruo.c2g.model.DeviceCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GithubApiService {

    private final GithubApiConfig githubApiConfig;
    private final WebClient webClient;
    private final ObjectMapperService objectMapper;

    @Autowired
    public GithubApiService(GithubApiConfig githubApiConfig, WebClient.Builder webClientBuilder, ObjectMapperService objectMapper) {
        this.githubApiConfig = githubApiConfig;
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public Mono<DeviceCodeResponse> getDeviceCode() {
        return webClient.post()
                .uri(githubApiConfig.getDeviceCodeUrl())
                .header("Accept", "application/json")
                .body(BodyInserters.fromFormData("client_id", githubApiConfig.getClientId()))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response);
                        String deviceCode = jsonNode.path("device_code").asText();
                        String userCode = jsonNode.path("user_code").asText();

                        if (deviceCode.isEmpty() || userCode.isEmpty()) {
                            return Mono.error(new RuntimeException("Device code or user code is null"));
                        }

                        return Mono.just(new DeviceCodeResponse(deviceCode, userCode));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

    public Mono<String> getGhuTokenByDeviceCode(DeviceCodeResponse deviceCodeResponse) {
        return webClient.post()
                .uri(githubApiConfig.getAccessTokenUrl())
                .header("Accept", "application/json")
                .body(BodyInserters.fromFormData("client_id", githubApiConfig.getClientId())
                        .with("device_code", deviceCodeResponse.getDeviceCode())
                        .with("grant_type", "urn:ietf:params:oauth:grant-type:device_code"))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response);
                        String token = jsonNode.path("access_token").asText();
                        if (token.isEmpty()) {
                            return Mono.error(new RuntimeException("Access token is null"));
                        }
                        return Mono.just(token);
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

    public Mono<String> getToken() {
        return webClient.get()
                .uri(githubApiConfig.getTokenUrl())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getCompletions() {
        return webClient.get()
                .uri(githubApiConfig.getCompletionsUrl())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getEmbeddings() {
        return webClient.get()
                .uri(githubApiConfig.getEmbeddingsUrl())
                .retrieve()
                .bodyToMono(String.class);
    }
}