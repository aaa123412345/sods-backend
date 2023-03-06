package org.sods.application;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.junit.jupiter.api.Test;
import org.sods.resource.domain.*;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.FloorPlanMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.sods.resource.mapper.StoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class CRUDTest {

    @Autowired
    FloorPlanMapper floorPlanMapper;

    @Autowired
    MarkerMapper markerMapper;

    @Autowired
    BoothMapper boothMapper;

    @Autowired
    StoryMapper storyMapper;

    public String getRandNum() {
        Random rand = new Random();
        return Integer.toString(rand.nextInt(10000000));
    }

    public List<Double> getRandCoordinate() {
        List<Double> coordinates = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < 2; i++)
            coordinates.add(rand.nextDouble()*100);
        return coordinates;
    }

    public String getResultText(int result) {
        return result == 1 ? "OK" : "Error";
    }

    // ============================ FloorPlan =================================

    // post
    @Test
    public void testCreateFloorPlan(){

        String num = getRandNum();

        FloorPlan floorPlan = new FloorPlan();
        floorPlan.setRegionEN("Testing Floor " + num);
        floorPlan.setRegionZH("測試平面圖 " + num);
        floorPlan.setImageUrl("http://sim-aws-cdn.com/testing-image-"+num+".png");

        int result = floorPlanMapper.insert(floorPlan);
        System.out.println("result: " + getResultText(result));
        System.out.println("id: " + floorPlan.getId());

    }

    //get
    @Test
    public void testGetAllFloorPlan(){

        List<FloorPlan> list = floorPlanMapper.selectList(null);
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

    }

    // get("/{id}")
    @Test
    public void testGetFloorPlanById(){

        FloorPlan floorPlan = floorPlanMapper.selectById(1);
        System.out.println(floorPlan);

    }

    // update("/{id}")
    @Test
    public void testUpdateFloorPlanById(){

        Integer id = 1;
        String num = getRandNum();

        FloorPlan floorPlan = new FloorPlan();
        //floorPlan.setId(id);
        floorPlan.setRegionEN("Testing Floor " + num);
        floorPlan.setRegionZH("測試平面圖 " + num);
        floorPlan.setImageUrl("http://sim-aws-cdn.com/testing-image-"+num+".png");

        int result = floorPlanMapper.updateById(floorPlan);
        System.out.println("result: " + getResultText(result));

    }

    // delete("/{id}")
    @Test
    public void testDeleteFloorPlanById(){

        Integer id = 1;

        int result = floorPlanMapper.deleteById(id);
        System.out.println("result: "+ (result == 1 ? "OK" : "Error"));

        if(result == 1){

            QueryWrapper query = new QueryWrapper<>();
            query.eq("fk_floorplan_id", id);

            List<Marker> markerList = markerMapper.selectList(query);
            for(int i = 0; i < markerList.size(); i++){
                Marker marker = markerList.get(i);
                if(marker.getBoothID() != null)
                    boothMapper.deleteById(marker.getBoothID());
                //markerMapper.deleteMarkerByCID(marker.getY(), marker.getX(), marker.getFloorPlanID());
            }

        }



    }

    // ============================ Marker =================================
    // post
    @Test
    public void testCreateMarker(){

        //List<Double> coordinate = getRandCoordinate();
        List <Double> coordinate = Arrays.asList(1.0, 1.0);// for testing delete
        Integer floorPlanID = 1;

        Marker marker = new Marker();
        marker.setFloorPlanID(floorPlanID);
        marker.setX(coordinate.get(0));
        marker.setY(coordinate.get(1));

        System.out.println(marker);
        int result = markerMapper.insert(marker);
        System.out.println("result: " + getResultText(result));

    }

    // get
    @Test
    public void testGetAllMarker(){

        List<Marker> list = markerMapper.selectList(null);
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

    }

    // get("/{y}/{x}/{floorPlanID}")
    @Test
    public void testGetMarkerById(){

        Double x = 21.14791240950146;
        Double y = 9.258270254303957;
        Integer floorPlanID = 1;

        //Marker result = markerMapper.findMarkerByCID(y, x, floorPlanID);
        //System.out.println("result: " + result);

    }

    // get("/markers?booth_id={id}")
    @Test
    public void testGetMarkerByBoothID(){

        Integer boothID = 1;

        QueryWrapper<Marker> query = new QueryWrapper<>();
        query.eq("fk_booth_id", boothID);

        List<Marker> result = markerMapper.selectList(query);
        System.out.println("result: " + result);

    }

    // get("/markers?floorplan_id={id}")
    @Test
    public void testGetMarkersByFloorPlanID(){

        Integer floorPlanID = 1;

        QueryWrapper<Marker> query = new QueryWrapper<>();
        query.eq("fk_floorplan_id", floorPlanID);

        List<Marker> result = markerMapper.selectList(query);
        System.out.println("result: " + result);

    }

    // put("/markers/y/x/floorPlanID/booths/boothID")
    @Test
    public void testAssignBooth(){
        Double x = 21.14791240950146;
        Double y = 9.258270254303957;
        Integer floorPlanID = 1;
        Integer boothID = 1;
/**
 *

        Marker markerWithBoothID = markerMapper.findMarkerByBoothID(boothID);
        if(markerWithBoothID != null)
            markerMapper.updateBoothOfMarker(
                    markerWithBoothID.getY(), markerWithBoothID.getX(),
                    markerWithBoothID.getFloorPlanID(), null
            );

        Marker result = markerMapper.updateBoothOfMarker(y, x, floorPlanID, boothID);
        System.out.println("result: " + getResultText(result != null ? 0 : 1));

 */
    }

    // delete("{y}/{x}/{floorPlanID}")
    @Test
    public void testDeleteMarkerById(){

        /*
        Double y = 1.0;
        Double x = 1.0;
        Integer floorPlanID = 1;
        Marker result = markerMapper.deleteMarkerByCID(y, x, floorPlanID);
        System.out.println("result: " + getResultText(result != null ? 0 : 1));
        if(result != null && result.getBoothID() != null){
            boothMapper.deleteById(result.getBoothID());
            System.out.println("deleted related booth: " + result.getBoothID());
        }
         */


    }

    // ============================ Booth =================================

    // post("/marker/y/x/floorID/booth")
    @Test
    public void testCreateBooth(){

        Double x = 10.796422849737741;
        Double y = 60.82977728823984;
        Integer floorPlanID = 1;

        String num = getRandNum();

        Booth booth = new Booth();
        booth.setTitleEN("Testing Booth " + num);
        booth.setTitleZH("測試攤位 " + num);
        booth.setVenueEN("Testing Room " + num);
        booth.setVenueZH("測試課室" + num);
        booth.setDescriptionEN("Testing Description About Booth " + num);
        booth.setDescriptionZH("測試攤位內容" + num);

        int result = boothMapper.insert(booth);
        System.out.println("result: " + getResultText(result));
        System.out.println("id: " + booth.getId());

        //Integer newBoothID = booth.getId();

        //if(result == 1)
            //markerMapper.updateBoothOfMarker(y, x, floorPlanID, newBoothID);

    }

    // get
    @Test
    public void testGetAllBooth(){

        List<Booth> list = boothMapper.selectList(null);
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

    }

    // get("/{id}")
    @Test
    public void testGetBoothById(){

        Integer id = 1;
        Booth booth = boothMapper.selectById(id);
        System.out.println(booth);

    }

    // get("?floorplan_id={id}")
    @Test
    public void testGetBoothByFloorPlanID(){

        //Integer floorPlanID = 1;
        //List<Booth> result = boothMapper.findBoothsByFloorPlanID(floorPlanID);
        //for(int i = 0; i < result.size(); i++)
        //    System.out.println(result.get(i));

    }


    // update("/{id}")
    @Test
    public void testUpdateBoothById(){

        Integer id = 1;
        String num = getRandNum();

        Booth booth = new Booth();
        //booth.setId(id);
        booth.setTitleEN("Testing Booth " + num);
        booth.setTitleZH("測試攤位 " + num);
        booth.setVenueEN("Testing Room " + num);
        booth.setVenueZH("測試課室" + num);
        booth.setDescriptionEN("Testing Description About Booth " + num);
        booth.setDescriptionZH("測試攤位內容" + num);

        int result = boothMapper.updateById(booth);
        System.out.println("result: " + getResultText(result));

    }

    // delete("/{id}")
    @Test void testDeleteBoothById(){

        Integer id = 1;
        int result = boothMapper.deleteById(id);
        System.out.println("result: "+ (result == 1 ? "OK" : "Error"));

    }

    // ============================ Story =================================
    // post
    @Test
    public void testCreateStory(){

        String num = getRandNum();

        Story story = new Story();
        story.setTitleEN("Testing Story " + num);
        story.setTitleZH("測試故事 " + num);
        story.setContentEN("Testing Content " + num);
        story.setContentZH("測試故事內容 " + num);
        story.setImageUrl("http://sim-aws-cdn.com/testing-image-"+num+".png");

        int result = storyMapper.insert(story);
        System.out.println("result: " + getResultText(result));
        System.out.println("id: " + story.getId());

    }

    // get
    @Test
    public void testGetAllStory(){

        List<Story> list = storyMapper.selectList(null);
        for(int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

    }

    // get("/{id}")
    @Test
    public void testGetStoryById(){

        Integer id = 1;
        Story story = storyMapper.selectById(id);
        System.out.println(story);

    }

    // update("/{id}")
    @Test
    public void testUpdateStoryById(){

        Integer id = 2;
        String num = getRandNum();

        Story story = new Story();
        //story.setId(id);
        story.setTitleEN("Testing Story " + num);
        story.setTitleZH("測試故事 " + num);
        story.setContentEN("Testing Content " + num);
        story.setContentZH("測試故事內容 " + num);
        story.setImageUrl("http://sim-aws-cdn.com/testing-image-"+num+".png");

        int result = storyMapper.updateById(story);
        System.out.println("result: " + getResultText(result));
    }

    // delete("/{id}")
    @Test
    public void testDeleteStoryById(){

        Integer id = 1;
        int result = storyMapper.deleteById(id);
        System.out.println("result: "+ (result == 1 ? "OK" : "Error"));

    }

}

