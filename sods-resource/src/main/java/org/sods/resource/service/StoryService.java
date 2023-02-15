package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Story;
import org.springframework.web.multipart.MultipartFile;

public interface StoryService {

    ResponseResult createStory(Story story);
    //ResponseResult createStory(Story story, MultipartFile imageFile);
    ResponseResult getAllStories();
    ResponseResult getStoryById(Integer id);
    ResponseResult updateStoryById(Integer id, Story newStory);
    //ResponseResult updateStoryById(Integer id, Story newStory, MultipartFile imageFile);
    ResponseResult deleteStoryById(Integer id);

}
