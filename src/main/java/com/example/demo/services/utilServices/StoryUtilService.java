package com.example.demo.services.utilServices;

import com.example.demo.models.StoryModel;
import com.example.demo.repositories.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
@Service
public class StoryUtilService {


    private final StoryRepository storyRepository;

    public StoryUtilService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }


    /***
     * This function is used to save the story model list to database.
     * @param stories
     */
    public void saveStories(List<StoryModel> stories) {
        storyRepository.saveAll(stories);
    }

    /**
     * This function is used to get the past top stories except the current top ten stories.
     *
     * @return - List of story model - Past top stories.
     */
    public List<StoryModel> getPastTopStories() {
        return storyRepository.getPastStories();
    }
}


