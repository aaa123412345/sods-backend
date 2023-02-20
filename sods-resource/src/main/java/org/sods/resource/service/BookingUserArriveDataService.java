package org.sods.resource.service;


import org.sods.common.domain.ResponseResult;

public interface BookingUserArriveDataService {

    ResponseResult getUserArriveData(Long user_id, Long booking_activity_id);
    ResponseResult createUserArriveData(Long user_id,Long booking_activity_id);
    ResponseResult UserArriveDataSetIsArrive(Long user_id,Long booking_activity_id);
    ResponseResult deleteUserArriveData(Long user_id,Long booking_activity_id);

    ResponseResult userJoin(Long booking_activity_id);


}
