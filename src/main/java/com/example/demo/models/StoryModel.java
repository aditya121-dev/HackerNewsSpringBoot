package com.example.demo.models;


import com.example.demo.services.utilServices.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "story")
public class StoryModel implements StoryInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @JsonIgnore
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "score")
    private Long score;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "url")
    private String url;

    @Column(name = "story_id")
    private Long storyId;

    public StoryModel(String title, String time, String score, String userName, String url,
                      Long storyId) {
        this.title = title;
        this.createdAt = DateUtils.parseDateTimeString(DateUtils.convertUnixTimeToLocalDateTime(time));
        this.score = Long.parseLong(score);
        this.userName = userName;
        this.url = url;
        this.storyId = storyId;
    }


    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public Long getScore() {
        return this.score;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public Long getStoryId() {
        return this.storyId;
    }
}
