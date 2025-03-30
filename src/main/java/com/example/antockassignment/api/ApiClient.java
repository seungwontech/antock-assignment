package com.example.antockassignment.api;

import com.example.antockassignment.config.exception.CoreException;
import com.example.antockassignment.config.exception.ErrorType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiClient {

    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClientBuilder;

    public <T> T get(String urlString, Class<T> responseType) throws Exception {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(urlString);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                        .responseTimeout(Duration.ofSeconds(30)));

        WebClient webClient = webClientBuilder.uriBuilderFactory(factory)
                .clientConnector(httpConnector)
                .baseUrl(urlString)
                .build();

        String response = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            return objectMapper.readValue(response, responseType);
        } catch (IOException e) {
            throw new CoreException(ErrorType.API_RESPONSE_FAILED, e.getLocalizedMessage());
        }
    }
}
