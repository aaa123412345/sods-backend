package org.sods.resource.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Story;
import org.sods.resource.mapper.StoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tourguide-stories")
public class StoryController {

    @Autowired
    StoryMapper storyMapper;

    @PostMapping
    public ResponseResult createStory(@RequestParam("story") String storyString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Story story = mapper.readValue(storyString, Story.class);
        // upload file to aws
        // get url from cdn
        // story.setImageUrl(cdn);
        storyMapper.insert(story);
        return new ResponseResult(200, "OK", story);
    }

    @GetMapping
    public ResponseResult getAllStories(){
        List<Story> result = storyMapper.selectList(null);
        return new ResponseResult(200, "OK", result);
    }

    @GetMapping("/{id}")
    public ResponseResult getStoryById(@PathVariable Integer id){
        Story result = storyMapper.selectById(id);
        return new ResponseResult(200, "OK", result);
    }

    @PutMapping("/{id}")
    public ResponseResult updateStoryById(@PathVariable Integer id, @RequestParam("story") String storyString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Story story = mapper.readValue(storyString, Story.class);
        // upload file to aws
        // get url from cdn
        // story.setImageUrl(cdn);
        story.setId(id);
        storyMapper.updateById(story);
        return new ResponseResult(200, "OK", story);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteStoryById(@PathVariable Integer id){
        storyMapper.deleteById(id);
        return new ResponseResult(200, "OK", null);
    }

}
