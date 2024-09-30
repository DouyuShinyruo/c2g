package io.github.shinyruo.c2g.controller;

import io.github.shinyruo.c2g.model.DeviceCodeResponse;
import io.github.shinyruo.c2g.service.GithubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GithubApiService githubApiService;
    private DeviceCodeResponse deviceCodeResponse;

    @Autowired
    public AuthController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @GetMapping("/get-device-key")
    @ResponseBody
    public Mono<String> getDeviceKey() {
        return githubApiService.getDeviceCode()
                .doOnNext(response -> this.deviceCodeResponse = response)
                .map(DeviceCodeResponse::getUserCode);
    }

    @GetMapping("/get-ghutoken")
    @ResponseBody
    public Mono<String> getGhuToken() {
        return githubApiService.getGhuTokenByDeviceCode(deviceCodeResponse)
                .onErrorReturn("Waiting for log in...");
    }
}