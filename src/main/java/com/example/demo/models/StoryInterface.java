package com.example.demo.models;

import org.joda.time.LocalDateTime;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
public interface StoryInterface {

    String getTitle();
    LocalDateTime getCreatedAt();
    String getUserName();
    Long getScore();
    String getUrl();
    Long getStoryId();
}
