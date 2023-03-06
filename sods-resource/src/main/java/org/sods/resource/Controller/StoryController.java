package org.sods.resource.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Story;
import org.sods.resource.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/TourGuide/stories")
public class StoryController {

    @Autowired
    StoryService storyService;

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PostMapping
    public ResponseResult createStory(@RequestBody Story story) {
        return storyService.createStory(story);
    }
    /**
    public ResponseResult createStory(@RequestParam("story") String storyString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Story story = mapper.readValue(storyString, Story.class);
        return storyService.createStory(story, imageFile);
    }
     **/

    @GetMapping
    public ResponseResult getAllStories(@RequestParam(name = "deleteFlag", required = false) Integer deleteFlag){
        return storyService.getAllStories(deleteFlag);
    }

    @GetMapping("/{id}")
    public ResponseResult getStoryById(@PathVariable Long id){
        return storyService.getStoryById(id);
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PutMapping("/{id}")
    public ResponseResult updateStoryById(@PathVariable Long id, @RequestBody Story story) {
        return storyService.updateStoryById(id, story);
    }
    /**
    public ResponseResult updateStoryById(@PathVariable Integer id, @RequestParam("story") String storyString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Story story = mapper.readValue(storyString, Story.class);
        return storyService.updateStoryById(id, story, imageFile);
    }**/

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteStoryById(@PathVariable Long id){
        return storyService.deleteStoryById(id);
    }

}
