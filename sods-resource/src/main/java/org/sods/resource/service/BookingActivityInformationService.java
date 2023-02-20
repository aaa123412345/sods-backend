package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BookingActivityInformation;


public interface BookingActivityInformationService {

    ResponseResult getBookingActivityInfo(Long booking_activity_id);
    ResponseResult createBookingActivityInfo(BookingActivityInformation bookingActivityInformation);
    ResponseResult updateBookingActivityInfo(BookingActivityInformation bookingActivityInformation,Long booking_activity_id);
    ResponseResult deleteBookingActivityInfo(Long booking_activity_id);
    ResponseResult getCurrentBookingActivityInfo();
    ResponseResult getAllBookingActivityInfo();

}
