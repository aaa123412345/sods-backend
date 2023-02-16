package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Story;
import org.sods.resource.mapper.StoryMapper;
import org.sods.resource.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    StoryMapper storyMapper;

    @Override
    public ResponseResult createStory(Story story) {

        storyMapper.insert(story);
        return new ResponseResult(201,"New story is created successfully.");

    }
    /**
    public ResponseResult createStory(Story story, MultipartFile imageFile) {

        //story.setImageUrl(getImageUrl(imageFile));
        storyMapper.insert(story);
        return new ResponseResult(201,"New story is created successfully.");

    }**/

    @Override
    public ResponseResult getAllStories() {

        List<Story> result = storyMapper.selectList(null);
        return new ResponseResult(200, "Story List is retrieved successfully.", result);

    }

    @Override
    public ResponseResult getStoryById(Integer id) {

        Story result = storyMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found.");
        return new ResponseResult(200, "Story (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateStoryById(Integer id, Story newStory) {

        Story story = storyMapper.selectById(id);
        if(story == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found. ");

        story.setTitleEN(newStory.getTitleEN());
        story.setTitleZH(newStory.getTitleZH());
        story.setContentEN(newStory.getContentEN());
        story.setContentZH(newStory.getContentZH());
        // story.setImageUrl(newStory.getImageUrl());

        storyMapper.updateById(story);
        return new ResponseResult(200, "Story (id: " + id + ") is updated successfully.");

    }
    /**
    public ResponseResult updateStoryById(Integer id, Story newStory, MultipartFile imageFile) {

        Story story = storyMapper.selectById(id);
        if(story == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found. ");

        story.setTitleEN(newStory.getTitleEN());
        story.setTitleZH(newStory.getTitleZH());
        story.setContentEN(newStory.getContentEN());
        story.setContentZH(newStory.getContentZH());


        if(!imageFile.isEmpty())
            story.setImageUrl(getImageUrl(imageFile));


        storyMapper.updateById(story);
        return new ResponseResult(200, "Story (id: " + id + ") is updated successfully.");

    }**/

    @Override
    public ResponseResult deleteStoryById(Integer id) {

        Story story = storyMapper.selectById(id);
        if(story == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found. ");

        storyMapper.deleteById(id);
        return new ResponseResult(200,"Story (id: " + id + ") is deleted successfully. ");

    }
}
