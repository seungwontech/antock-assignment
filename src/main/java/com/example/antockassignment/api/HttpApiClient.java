package com.example.antockassignment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@RequiredArgsConstructor
@Component
public class HttpApiClient {

    private final ObjectMapper objectMapper;
    private final WebClient.Builder webClientBuilder;

    public <T> T get(String urlString, Class<T> responseType) throws Exception {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(urlString);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                        .responseTimeout(Duration.ofSeconds(5)));

        WebClient webClient = webClientBuilder.uriBuilderFactory(factory)
                .clientConnector(httpConnector)
                .baseUrl(urlString)
                .build();

        String response = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return objectMapper.readValue(response, responseType);
    }
}
