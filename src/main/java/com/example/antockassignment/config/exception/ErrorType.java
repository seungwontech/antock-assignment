package com.example.antockassignment.config.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    PUBLIC_DATA_NOT_FOUND(ErrorCode.NOT_FOUND, "공공데이터를 찾을 수 없습니다.", LogLevel.ERROR),
    JUSO_DATA_NOT_FOUND(ErrorCode.NOT_FOUND, "주소를 찾을 수 없습니다.", LogLevel.ERROR),
    API_RESPONSE_FAILED(ErrorCode.NOT_FOUND, "API 응답 실패", LogLevel.ERROR),
    CSV_FILE_NOT_FOUND(ErrorCode.BAD_REQUEST, "CSV 파일을 찾을 수 없습니다.", LogLevel.ERROR)

    ;

    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;
}