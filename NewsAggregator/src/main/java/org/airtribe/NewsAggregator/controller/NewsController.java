package org.airtribe.NewsAggregator.controller;

import org.airtribe.NewsAggregator.model.ApiResponse;
import org.airtribe.NewsAggregator.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    private static final Logger log = LoggerFactory.getLogger(NewsController.class);

    /**
     * This is the user preference news based on india and top head lines
     *
     * @return
     */
    @GetMapping("/api/preferences")
    public ResponseEntity<ApiResponse> getPreferences() {
        try {
            ApiResponse apiResponse = newsService.getPreference();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error while getting preferences", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * This is the user preference news which can be updated based on search
     *
     * @return
     */
    @PutMapping("/api/preferences")
    public ResponseEntity<ApiResponse> updatePreferences(@RequestParam String search) {
        try {
            ApiResponse apiResponse = newsService.updatePreference(search);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error while getting preferences", e);
            return ResponseEntity.internalServerError().build();
        }
    }


    /**
     * This is the total english news Articles using asynchronous programming
     *
     * @return
     */
    @GetMapping("/api/news")
    public ResponseEntity<Mono<ApiResponse>> getNews() {
        try {
            Mono<ApiResponse> apiResponse = newsService.getNews();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            log.error("Error while getting preferences", e);
            return ResponseEntity.internalServerError().build();
        }
    }


}
