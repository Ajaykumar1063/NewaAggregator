package org.airtribe.NewsAggregator.service;

import org.airtribe.NewsAggregator.exception.NewsApiException;
import org.airtribe.NewsAggregator.model.ApiResponse;
import reactor.core.publisher.Mono;


public interface NewsService {

    ApiResponse getPreference() throws NewsApiException;

    Mono<ApiResponse> getNews() throws NewsApiException;

    ApiResponse updatePreference(String search) throws NewsApiException;
}
