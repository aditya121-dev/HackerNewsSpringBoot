package com.example.demo.repositories;

import com.example.demo.models.StoryModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoryRepository extends CrudRepository<StoryModel, Long> {

    @Query(value = "SELECT t.*\n" +
            "FROM (\n" +
            "  SELECT *, row_number() OVER(ORDER BY id desc) AS row\n" +
            "  FROM story\n" +
            ") t\n" +
            "WHERE t.row > 10", nativeQuery = true)
    public List<StoryModel> getPastStories();

}
