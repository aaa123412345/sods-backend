package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BookingUserArriveData;

public interface BookingUserArriveDataService {
    ResponseResult getUserArriveData(Long user_id,Long booking_activity_id);
    ResponseResult createUserArriveData(BookingUserArriveData bookingUserArriveData,Long user_id,Long booking_activity_id);
    ResponseResult updateUserArriveData(BookingUserArriveData bookingUserArriveData,Long user_id,Long booking_activity_id);
    ResponseResult deleteUserArriveData(Long user_id,Long booking_activity_id);
}
