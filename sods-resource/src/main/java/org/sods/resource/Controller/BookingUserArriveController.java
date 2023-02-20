package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BookingActivityInformation;
import org.sods.resource.domain.BookingUserArriveData;
import org.sods.resource.service.BookingActivityInformationService;
import org.sods.resource.service.BookingUserArriveDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/BookingSystem")
public class BookingUserArriveController {


    @Autowired
    private BookingUserArriveDataService bookingUserArriveDataService;



    @GetMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult getUserArriveData(@PathVariable("booking_activity_id")String booking_activity_id,
                                            @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.getUserArriveData(Long.parseLong(user_id),
                Long.parseLong(booking_activity_id));
    }

    @PreAuthorize("@ex.hasAuthority('system:booking:buad:create')")
    @PostMapping("/user_arrive_data/{booking_activity_id}")
    public ResponseResult postUserArriveData(
                                             @PathVariable("booking_activity_id")String booking_activity_id,
                                             @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.createUserArriveData(
                Long.parseLong(user_id),Long.parseLong(booking_activity_id));
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:buad:update')")
    @PutMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult putUserArriveData(
                                            @PathVariable("booking_activity_id")String booking_activity_id,
                                            @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.UserArriveDataSetIsArrive(
                Long.parseLong(user_id),Long.parseLong(booking_activity_id));
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:buad:delete')")
    @DeleteMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult deleteUserArriveData(@PathVariable("booking_activity_id")String booking_activity_id,
                                               @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.deleteUserArriveData(Long.parseLong(user_id),Long.parseLong(booking_activity_id));
    }

    @GetMapping("/user_arrive_data/client/{booking_activity_id}")
    public ResponseResult userCheck(@PathVariable("booking_activity_id")String booking_activity_id){
        return bookingUserArriveDataService.userCheckIsBook(Long.parseLong(booking_activity_id));
    }

    @PostMapping("/user_arrive_data/client/{booking_activity_id}")
    public ResponseResult userJoin(@PathVariable("booking_activity_id")String booking_activity_id){
        return bookingUserArriveDataService.userJoin(Long.parseLong(booking_activity_id));
    }

    @DeleteMapping("/user_arrive_data/client/{booking_activity_id}")
    public ResponseResult userLeave(@PathVariable("booking_activity_id")String booking_activity_id){
        return bookingUserArriveDataService.userLeave(Long.parseLong(booking_activity_id));
    }


}
