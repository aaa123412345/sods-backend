package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Story;
import org.sods.resource.mapper.StoryMapper;
import org.sods.resource.mapper.TourguideConfigMapper;
import org.sods.resource.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService {


    @Autowired
    TourguideConfigMapper tourguideConfigMapper;
    @Autowired
    StoryMapper storyMapper;

    @Override
    public ResponseResult createStory(Story story) {

        storyMapper.insert(story);
        return new ResponseResult(201,"New story is created successfully.");

    }

    @Override
    public ResponseResult getAllStories(Integer deleteFlag) {
        Integer delFlag = deleteFlag == null ? 0 : deleteFlag;
        List<Story> result =  delFlag == 0 ? storyMapper.selectList(null) : storyMapper.findDeletedStories();
        return new ResponseResult(200, "Story List is retrieved successfully.", result);

    }

    @Override
    public ResponseResult getStoryById(Long id) {

        Story result = storyMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found.");
        return new ResponseResult(200, "Story (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateStoryById(Long id, Story newStory) {

        Story story = storyMapper.selectById(id);
        if(story == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found. ");

        String newImageUrl = newStory.getImageUrl() == null ? story.getImageUrl() : newStory.getImageUrl();

        story.setTitleEN(newStory.getTitleEN());
        story.setTitleZH(newStory.getTitleZH());
        story.setContentEN(newStory.getContentEN());
        story.setContentZH(newStory.getContentZH());
        story.setImageUrl(newImageUrl);

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
    public ResponseResult deleteStoryById(Long id) {

        Story story = storyMapper.selectById(id);
        if(story == null)
            return new ResponseResult(404, "Failed: Story (id: " + id + ") is not found. ");

        storyMapper.deleteById(id);
        return new ResponseResult(200,"Story (id: " + id + ") is deleted successfully. ");

    }
}
