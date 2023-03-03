package ru.practicum.statistic.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.practicum.statistic.dto.HitDto;
import ru.practicum.statistic.dto.StatisticViewDto;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class StatisticHttpClient {

    private final HttpClient httpClient;
    private final String app;
    private final String statsServiceUri;
    private final ObjectMapper mapper;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public StatisticHttpClient(@Value("${ewm-statistic-service.uri}") String statsServiceUri,
                               @Value("${spring.application.name}") String app,
                               ObjectMapper mapper) {
        this.app = app;
        this.statsServiceUri = statsServiceUri;
        this.mapper = mapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
    }

    public void addHit(HttpServletRequest request) {

        HitDto hitDto =  new HitDto();
        hitDto.setApp(app);
        hitDto.setUri(request.getRequestURI());
        hitDto.setIp(request.getRemoteAddr());
        hitDto.setTimestamp(LocalDateTime.now().format(FORMATTER));

        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest
                    .BodyPublishers
                    .ofString(mapper.writeValueAsString(hitDto));
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statsServiceUri + "/hit"))
                    .POST(bodyPublisher)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json")
                    .build();
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());

        } catch (InterruptedException e) {
            log.error("Произошло непредвиденное прерывание потока", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Не удалось сохранить информацию о том, что на uri конкретного сервиса был отправлен " +
                    "запрос пользователем", e);

        }
    }

    public List<StatisticViewDto> getStatistic(String start, String end, List<String> uris, Boolean unique) {
        try {
            String queryString = toQueryString(start, end, uris, unique);
            log.info("StatisticHttpClient: получить данные по запросу {}", queryString);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(statsServiceUri + "/stats" + queryString))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (HttpStatus.valueOf(response.statusCode()).is2xxSuccessful()) {
                return mapper.readValue(response.body(), new TypeReference<>() {
                });
            }

        } catch (InterruptedException e) {
            log.error("Произошло непредвиденное прерывание потока", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Не удалось получить статистику по запросу: " + uris, e);
        }

        return new ArrayList<>();
    }

    private String toQueryString(String start, String end, List<String> uris, Boolean unique) {

        String queryString = String.format("?start=%s&end=%s&unique=%b", start, end, unique);
        if (!uris.isEmpty()) {
            queryString += "&uris=" + String.join(",", uris);
        }

        return queryString;
    }
}
