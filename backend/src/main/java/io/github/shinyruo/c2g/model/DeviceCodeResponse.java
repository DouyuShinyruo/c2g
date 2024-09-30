package io.github.shinyruo.c2g.model;

import lombok.Getter;

@Getter
public class DeviceCodeResponse {
    private String deviceCode;
    private String userCode;

    public DeviceCodeResponse(String deviceCode, String userCode) {
        this.deviceCode = deviceCode;
        this.userCode = userCode;
    }
}
