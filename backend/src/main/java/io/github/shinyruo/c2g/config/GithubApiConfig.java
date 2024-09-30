package io.github.shinyruo.c2g.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "api.url")
public class GithubApiConfig {

    private String baseUrl;
    private String apiBaseUrl;
    private String deviceCodePath;
    private String tokenPath;
    private String completionsPath;
    private String embeddingsPath;
    private String accessTokenPath;

    @Getter
    @Value("${api.client.id}")
    private String clientId;

    public String getDeviceCodeUrl() {
        return baseUrl + deviceCodePath;
    }

    public String getTokenUrl() {
        return apiBaseUrl + tokenPath;
    }

    public String getCompletionsUrl() {
        return apiBaseUrl + completionsPath;
    }

    public String getEmbeddingsUrl() {
        return apiBaseUrl + embeddingsPath;
    }

    public String getAccessTokenUrl() {
        return apiBaseUrl + accessTokenPath;
    }
}