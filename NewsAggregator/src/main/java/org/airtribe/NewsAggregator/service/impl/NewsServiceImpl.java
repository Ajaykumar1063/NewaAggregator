package org.airtribe.NewsAggregator.service.impl;

import org.airtribe.NewsAggregator.exception.NewsApiException;
import org.airtribe.NewsAggregator.model.ApiResponse;
import org.airtribe.NewsAggregator.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.news.base-url}")
    private String newsBaseUrl;

    @Value("${spring.news.total-url}")
    private String totalNewsUrl;


    @Value("${spring.news.api-key}")
    private String apiKey;

    /**
     * This method is used to get News API preferences
     *
     * @return
     */
    @Override
    public ApiResponse getPreference() throws NewsApiException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newsBaseUrl);
            builder.queryParam("country", "in");
            builder.queryParam("apiKey", apiKey);
            ApiResponse apiResponse = restTemplate.getForObject(builder.toUriString(), ApiResponse.class);
            return apiResponse;
        } catch (Exception e) {
            log.error("Error while getting News API preferences", e);
            throw new NewsApiException(e.getMessage());
        }
    }

    /**
     * This method is used to get total english news
     *
     * @return
     * @throws NewsApiException
     */
    @Override
    public Mono<ApiResponse> getNews() throws NewsApiException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(totalNewsUrl);
            builder.queryParam("q", "india");
            builder.queryParam("apiKey", apiKey);
            Mono<ApiResponse> apiResponse = webClient.get().uri(builder.toUriString())
                    .retrieve().onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error( new NewsApiException("Error while getting Total news"))).
                    bodyToMono(ApiResponse.class);
            return apiResponse;
        } catch (Exception e) {
            log.error("Error while getting Total news", e);
            throw new NewsApiException(e.getMessage());
        }
    }

    /**
     *
     * @param search
     * @return
     * @throws NewsApiException
     */
    @Override
    public ApiResponse updatePreference(String search) throws NewsApiException {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newsBaseUrl);
            builder.queryParam("q", search);
            builder.queryParam("apiKey", apiKey);
            ApiResponse apiResponse = restTemplate.getForObject(builder.toUriString(), ApiResponse.class);
            return apiResponse;
        } catch (Exception e) {
            log.error("Error while updating News API preferences", e);
            throw new NewsApiException(e.getMessage());
        }
    }
}
