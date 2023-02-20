package org.sods.resource.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BookingUserArriveData;
import org.sods.resource.mapper.BookingUserArriveDataMapper;
import org.sods.resource.service.BookingActivityInformationService;
import org.sods.resource.service.BookingUserArriveDataService;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class BookingUserArriveDataServiceImpl implements BookingUserArriveDataService {

    @Autowired
    private BookingUserArriveDataMapper bookingUserArriveDataMapper;
    @Override
    public ResponseResult getUserArriveData(Long user_id, Long booking_activity_id) {

        BookingUserArriveData bookingUserArriveData = getObjectWithComposeID(user_id,booking_activity_id);

        if(Objects.isNull(bookingUserArriveData)){
            return new ResponseResult<>(404,"Booking User Arrive Data is not found");
        }
        return new ResponseResult<>(200,"Booking User Arrive Data is found",bookingUserArriveData);

    }

    @Override
    public ResponseResult createUserArriveData( Long user_id, Long booking_activity_id) {
        BookingUserArriveData old = getObjectWithComposeID(user_id,booking_activity_id);

        if(Objects.isNull(old)){
            return new ResponseResult<>(404,"Create failed: Booking User Arrive Data is exist");
        }

        BookingUserArriveData bookingUserArriveData = new BookingUserArriveData();
        bookingUserArriveData.setUserId(user_id);
        bookingUserArriveData.setBookingActivityId(booking_activity_id);
        bookingUserArriveDataMapper.insert(bookingUserArriveData);
        return new ResponseResult<>(200,"Booking User Arrive Data is created");
    }

    @Override
    public ResponseResult UserArriveDataSetIsArrive(Long user_id, Long booking_activity_id) {


        BookingUserArriveData old = getObjectWithComposeID(user_id, booking_activity_id);


        if(Objects.isNull(old)){
            return new ResponseResult<>(404,"Update Failed: Booking User Arrive Data is not found");
        }

        old.setIsArrive(true);

        UpdateWrapper<BookingUserArriveData> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",user_id);
        updateWrapper.eq("booking_activity_id",booking_activity_id);
        bookingUserArriveDataMapper.update(old,updateWrapper);


        return new ResponseResult<>(200,"Update Success: Booking User Arrive Data is found");
    }

    @Override
    public ResponseResult deleteUserArriveData(Long user_id, Long booking_activity_id) {


        BookingUserArriveData old = getObjectWithComposeID(user_id, booking_activity_id);

        if(Objects.isNull(old)){
            return new ResponseResult<>(404,"Delete Failed: Booking User Arrive Data is not found");
        }

        UpdateWrapper<BookingUserArriveData> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",user_id);
        updateWrapper.eq("booking_activity_id",booking_activity_id);

        bookingUserArriveDataMapper.delete(updateWrapper);
        return new ResponseResult<>(200,"Delete Success: Booking User Arrive Data is found");

    }

    @Override
    public ResponseResult userJoin(Long booking_activity_id) {
        //Get user info
        Long user_id = getUserID();

        return createUserArriveData(user_id,booking_activity_id);
    }

    @Override
    public ResponseResult userLeave(Long booking_activity_id) {
        //Get user info
        Long user_id = getUserID();
        return deleteUserArriveData(user_id,booking_activity_id);
    }

    @Override
    public ResponseResult userCheckIsBook(Long booking_activity_id) {
        Long user_id = getUserID();
        return getUserArriveData(user_id,booking_activity_id);
    }


    public BookingUserArriveData getObjectWithComposeID(Long user_id, Long booking_activity_id){
        QueryWrapper<BookingUserArriveData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user_id);
        queryWrapper.eq("booking_activity_id",booking_activity_id);
        return bookingUserArriveDataMapper.selectOne(queryWrapper);
    }

    public Long getUserID(){
        Long userid;
        //Get user info
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(Objects.isNull(authentication)){
            userid = -999L;
        }else{
            Object principal = authentication.getPrincipal();

            //Get User ID => if (No login, userid:-1)
            if(principal instanceof LoginUser){
                LoginUser loginUser = ((LoginUser)principal);
                userid = loginUser.getUser().getId();
            }else{
                userid = -1L;
            }
        }

        return userid;
    }



}

