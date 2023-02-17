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
public class BookingSystemController {
    @Autowired
    private BookingUserArriveDataService bookingUserArriveDataService;
    @Autowired
    private BookingActivityInformationService bookingActivityInformationService;

    @GetMapping("/booking_activity_information/{booking_activity_id}")
    public ResponseResult getBookingActivity(@PathVariable("booking_activity_id")String booking_activity_id){
        return bookingActivityInformationService.getBookingActivityInfo(Long.parseLong(booking_activity_id));
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:bai:create')")
    @PostMapping("/booking_activity_information/")
    public ResponseResult postBookingActivity(@RequestBody BookingActivityInformation payload){
        return bookingActivityInformationService.createBookingActivityInfo(payload);
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:bai:update')")
    @PutMapping("/booking_activity_information/{booking_activity_id}")
    public ResponseResult putBookingActivity(@RequestBody BookingActivityInformation payload,
                                             @PathVariable("booking_activity_id")String booking_activity_id){
        return bookingActivityInformationService.updateBookingActivityInfo(payload, Long.parseLong(booking_activity_id));
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:bai:delete')")
    @DeleteMapping("/booking_activity_information/{booking_activity_id}")
    public ResponseResult deleteBookingActivity(@PathVariable("booking_activity_id")String booking_activity_id){
        return bookingActivityInformationService.deleteBookingActivityInfo(Long.parseLong(booking_activity_id));
    }


    @GetMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult getUserArriveData(@PathVariable("booking_activity_id")String booking_activity_id,
                                            @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.getUserArriveData(Long.parseLong(user_id),
                Long.parseLong(booking_activity_id));
    }

    @PreAuthorize("@ex.hasAuthority('system:booking:buad:create')")
    @PostMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult postUserArriveData(@RequestBody BookingUserArriveData payload,
                                             @PathVariable("booking_activity_id")String booking_activity_id,
                                             @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.createUserArriveData(payload,
                Long.parseLong(user_id),Long.parseLong(booking_activity_id));
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:buad:update')")
    @PutMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult putUserArriveData(@RequestBody BookingUserArriveData payload,
                                            @PathVariable("booking_activity_id")String booking_activity_id,
                                            @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.updateUserArriveData(payload,
                Long.parseLong(user_id),Long.parseLong(booking_activity_id));
    }
    @PreAuthorize("@ex.hasAuthority('system:booking:buad:delete')")
    @DeleteMapping("/user_arrive_data/{booking_activity_id}/{user_id}")
    public ResponseResult deleteUserArriveData(@PathVariable("booking_activity_id")String booking_activity_id,
                                               @PathVariable("user_id")String user_id){
        return bookingUserArriveDataService.deleteUserArriveData(Long.parseLong(user_id),Long.parseLong(booking_activity_id));
    }


}
