package com.example.demo.models;

import org.joda.time.LocalDateTime;

public interface StoryInterface {

    String getTitle();
    LocalDateTime getCreatedAt();
    String getUserName();
    Long getScore();
    String getUrl();
    Long getStoryId();
}
