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
public class BookingActivityController {

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





}
