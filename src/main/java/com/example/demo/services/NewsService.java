package com.example.demo.services;

import com.example.demo.models.CommentModel;
import com.example.demo.models.StoryMapper;
import com.example.demo.models.StoryModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.utilServices.DateUtils;
import com.example.demo.services.utilServices.StoryUtilService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
@Service
public class NewsService {

    private final NewsClient newsClient;
    private final StoryUtilService storyUtilService;
    private final MemcachedService memcachedService;

    public NewsService(NewsClient newsClient, StoryUtilService storyUtilService,
                       MemcachedService memcachedService) {
        this.memcachedService = memcachedService;
        this.storyUtilService = storyUtilService;
        this.newsClient = newsClient;
    }

    private static final String BEST_STORIES_URL = "https://hacker-news.firebaseio.com/v0/beststories.json";
    private static final String GET_ITEM_URL = "https://hacker-news.firebaseio.com/v0/item/{}.json?print=pretty";
    private static final String STORIES_KEY = "topStories";
    private static final String USER_API = "https://hacker-news.firebaseio.com/v0/user/{}.json?print=pretty";

    /**
     * This function is used to get top ten best news stories based on user comments list.
     * This also save the stories to db and cache them in memcached for 15 min.
     *
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public Object getTopTenBestNewsStories() throws Exception {

        String response = newsClient.makeGetCall(BEST_STORIES_URL);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> bestStoryIdList = objectMapper.readValue(response,
                new TypeReference<List<Long>>() {
                });
        return processTopTenStories(bestStoryIdList);
    }


    /**
     * This function is used to fetch the top ten stories from best stories ID's and parse them into
     * required Model.
     *
     * @param bestStoriesIds - List of story id's.
     * @return - return List of Story Model that contains the essential story data.
     * @throws Exception
     */
    public List<StoryMapper> processTopTenStories(List<Long> bestStoriesIds) throws Exception {
        Object cachedObject = memcachedService.getFromCache(STORIES_KEY);

        if (Objects.isNull(cachedObject)) {
            return saveTopTenStoriesToDB(bestStoriesIds);
        } else {
            return (List<StoryMapper>) cachedObject;
        }
    }


    /***
     * This function is used to save the top ten stories to database as well to cache memory.
     * @param bestStoriesIds
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public List<StoryMapper> saveTopTenStoriesToDB(List<Long> bestStoriesIds) throws Exception {
        List<StoryModel> storyModels = new ArrayList<>();
        List<StoryMapper> storyMappers = new ArrayList<>();
        int i = 0;
        for (Long storyId : bestStoriesIds) {
            if (i == 10) {
                break;
            }
            StoryMapper storyMapper = getStoryById(storyId);
            if (StringUtils.isNotEmpty(storyMapper.getType()) && storyMapper.getType().equalsIgnoreCase("story")) {
                i++;
            } else {
                continue;
            }
            storyMapper.setKids(null);
            storyMapper.setType(null);
            StoryModel storyModel = new StoryModel(storyMapper.getTitle(),
                    storyMapper.getCreated_at(), storyMapper.getScore(), storyMapper.getUserName(),
                    storyMapper.getUrl(), storyMapper.getId());
            storyModels.add(storyModel);
            storyMappers.add(storyMapper);
        }

        storyUtilService.saveStories(storyModels);
        memcachedService.saveToCache(STORIES_KEY, 900, storyMappers);

        return storyMappers;
    }


    /**
     * This function is used to get the past stories except top ten current stories.
     *
     * @return - List of StoryMapper class.
     */
    public List<StoryMapper> getPastStories() {
        List<StoryModel> storyModels = storyUtilService.getPastTopStories();
        return mapStoryModelToMapper(storyModels);
    }

    /**
     * This function is used to map story model to story mapper class.
     *
     * @param storyModels
     * @return
     */
    public List<StoryMapper> mapStoryModelToMapper(List<StoryModel> storyModels) {
        List<StoryMapper> storyMapperList;
        if (CollectionUtils.isEmpty(storyModels)) {
            return Collections.emptyList();
        } else {
            storyMapperList = new ArrayList<>();
            for (StoryModel storyModel : storyModels) {
                StoryMapper storyMapper = new StoryMapper(storyModel.getTitle(),
                        storyModel.getUserName(), storyModel.getCreatedAt(), storyModel.getScore(),
                        storyModel.getUrl(), storyModel.getStoryId());
                storyMapperList.add(storyMapper);
            }
            return storyMapperList;
        }


    }


    /**
     * This function is used to get the top ten comments from story id either cached or from server.
     *
     * @param storyId
     * @return
     * @throws Exception
     */
    public List<CommentModel> processTopTenCommentsFor(Long storyId) throws Exception {

        Object cachedObject = memcachedService.getFromCache(String.valueOf(storyId));

        if (cachedObject != null) {
            return (List<CommentModel>) cachedObject;
        } else {
            return getTopCommentsFor(storyId);
        }
    }

    /***
     * This function is used to get top ten comment for particular story id.
     * @param storyId
     * @return
     * @throws Exception
     */
    public List<CommentModel> getTopCommentsFor(Long storyId) throws Exception {

        List<CommentModel> commentModelList = new ArrayList<>();
        StoryMapper storyMapper = getStoryById(storyId);
        if (storyMapper != null) {
            List<Long> comments = storyMapper.getKids();
            if (!CollectionUtils.isEmpty(comments)) {
                for (int i = 0; i < 10; i++) {
                    Long commentId = comments.get(i);
                    CommentModel commentModel = getCommentById(commentId);
                    UserModel userModel = getUserByUserUniqueName(commentModel.getUserName());
                    String localDateTimeString = userModel.getCreated();
                    String yearDuration = DateUtils.getUserSinceYears(localDateTimeString);
                    commentModel.setUserSince(yearDuration);
                    commentModelList.add(commentModel);
                }
            }
        }
        memcachedService.saveToCache(String.valueOf(storyId), 900, commentModelList);
        return commentModelList;
    }

    /**
     * This function is used to get the story by id.
     *
     * @param storyId
     * @return
     * @throws Exception
     */
    public StoryMapper getStoryById(Long storyId) throws Exception {
        String storyUrl = GET_ITEM_URL.replaceAll("\\{\\}", String.valueOf(storyId));
        String response = newsClient.makeGetCall(storyUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        StoryMapper storyMapper = objectMapper.readValue(response, StoryMapper.class);
        return storyMapper;
    }


    /**
     * This function is used to get the story by id.
     *
     * @param commentId
     * @return
     * @throws Exception
     */
    public CommentModel getCommentById(Long commentId) throws Exception {
        String storyUrl = GET_ITEM_URL.replaceAll("\\{\\}", String.valueOf(commentId));
        String response = newsClient.makeGetCall(storyUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        CommentModel commentModel = objectMapper.readValue(response, CommentModel.class);
        return commentModel;
    }

    /****
     * This function is used to fetch the user created at time by provided user unique name.
     * @param userUniqueName
     * @return
     * @throws Exception
     */
    public UserModel getUserByUserUniqueName(String userUniqueName) throws Exception {
        String userUrl = USER_API.replaceAll("\\{\\}", String.valueOf(userUniqueName));
        String response = newsClient.makeGetCall(userUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        UserModel userModel = objectMapper.readValue(response, UserModel.class);
        return userModel;

    }
}
