package com.example.demo.models;


import com.example.demo.services.utilServices.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoryMapper implements StoryMapperInterface, Serializable {

    private String title;
    private String by;
    private String time;
    private String score;
    private String url;
    private Long id;
    private List<Long> kids;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public StoryMapper(String title, String userName, LocalDateTime time, Long score, String url,
                       Long id) {
        this.title = title;
        this.by = userName;
        this.time = time.toString(DateUtils.DATE_TIME_FORMAT);
        this.score = String.valueOf(score);
        this.url = url;
        this.id = id;
    }


    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getCreated_at() {
        return this.time;
    }

    @Override
    public String getUserName() {
        return this.by;
    }

    @Override
    public String getScore() {
        return this.score;
    }

    @Override
    public String getUrl() {
        return this.url;
    }


    public List<Long> getKids() {
        return this.kids;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

    public String getType()
    {
        return this.type;
    }
}
