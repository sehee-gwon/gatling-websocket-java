package com.example.gatling.infrastructure.stomp;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.util.StringUtils;

@NoArgsConstructor
public class SendFrame extends StompFrame {
    private String destination = "/app/designId";
    private MediaType contentType;
    private Integer contentLength;

    @Builder
    public SendFrame(MediaType contentType, Integer contentLength, String body) {
        this.commend = StompCommand.SEND;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.body = body;
    }

    @Override
    protected void setHeader(StringBuilder sb) {
        sb.append("\n").append("destination:").append(this.destination);
        if (this.contentType != null) sb.append("\n").append("content-type:").append(this.contentType);
        if (this.contentLength != null) sb.append("\n").append("content-length:").append(this.contentLength);
    }

    @Override
    protected void validator() {
        if (!StringUtils.hasText(this.destination)) {
            throw new IllegalArgumentException(this.commend + ": destination value is required");
        }
    }
}
