package com.example.demo;

import com.example.demo.controllers.MainController;
import com.example.demo.repositories.StoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
/***
 * @author Aditya Soni( adityasoni182@gmail.com )
 * @version v1
 * @since 12 August 2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public  class MainControllerIntegrationTest {



    @Autowired
    private StoryRepository storyRepository;


    @Autowired
    private MainController controller;

    private MockMvc mockMvc;
    @Before
    public void init() {
     storyRepository.deleteAll();
    }



    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void shouldFetchStoriesAndSave() throws Exception {
        String API = "/news/best-stories";
        mockMvc.perform(get(API)
                .requestAttr("principal", "abc")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void shouldFetchPastStories() throws Exception {
        String API = "/news/past-stories";
        mockMvc.perform(get(API)
                .requestAttr("principal", "abc")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
    @Test
    public void shouldFetchCommentsForStory() throws Exception {
        String API = "/news/comments?storyId=24113798";
        mockMvc.perform(get(API)
                .requestAttr("principal", "abc")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

}