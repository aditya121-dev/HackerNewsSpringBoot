package com.example.demo.controllers;

import com.example.demo.models.ApiResponse;
import com.example.demo.models.ApiResponseSuccess;
import com.example.demo.services.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
@Controller
@RequestMapping(value = "/news")
public class MainController {

    private final NewsService newsService;

    public MainController(NewsService newsService) {
        this.newsService = newsService;
    }

    /***
     * This API is used to get the top ten best stories from hacker news API .
     * This API is also cached for 15 minutes.
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/best-stories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getBestStories() throws Exception {

        return new ResponseEntity<>(new ApiResponseSuccess(newsService.getTopTenBestNewsStories()), HttpStatus.OK);

    }

    /***
     * This API is used to return the past stories except the top 10 current stories.
     * @return
     */
    @RequestMapping(value = "/past-stories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getPastStories() {

        return new ResponseEntity<>(new ApiResponseSuccess(newsService.getPastStories()), HttpStatus.OK);

    }

    /***
     * This API is used to return the top ten comments of a story.
     * @return
     */
    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getTopTenComments(@RequestParam(value = "storyId") Long storyId) throws Exception {
        return new ResponseEntity<>(new ApiResponseSuccess(newsService.processTopTenCommentsFor(storyId)), HttpStatus.OK);

    }
}
