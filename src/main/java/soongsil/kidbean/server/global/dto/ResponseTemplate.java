package soongsil.kidbean.server.global.dto;

import lombok.Builder;

@Builder
public record ResponseTemplate<T>(
        Boolean isSuccess,
        String code,
        String message,
        T results
) {
    public static <T> ResponseTemplate<Object> from(T dto) {
        return ResponseTemplate.builder()
                .isSuccess(true)
                .code("REQUEST_OK")
                .message("request succeeded")
                .results(dto)
                .build();
    }
}
