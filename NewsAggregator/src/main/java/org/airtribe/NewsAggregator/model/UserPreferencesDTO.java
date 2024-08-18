package org.airtribe.NewsAggregator.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserPreferencesDTO {
    private String title;
    private String content;
    private String description;
    private String url;
    private String image;
    private String publishedAt;
}
