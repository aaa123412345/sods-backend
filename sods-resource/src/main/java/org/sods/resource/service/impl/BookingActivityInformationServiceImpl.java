package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.BookingActivityInformation;
import org.sods.resource.domain.BookingUserArriveData;
import org.sods.resource.mapper.BookingActivityInformationMapper;

import org.sods.resource.mapper.BookingUserArriveDataMapper;
import org.sods.resource.service.BookingActivityInformationService;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingActivityInformationServiceImpl implements BookingActivityInformationService {
    @Autowired
    private BookingActivityInformationMapper bookingActivityInformationMapper;

    @Autowired
    private BookingUserArriveDataMapper bookingUserArriveDataMapper;

    @Override
    public ResponseResult getBookingActivityInfo(Long booking_activity_id) {


        BookingActivityInformation bookingActivityInformation =
                bookingActivityInformationMapper.selectById(booking_activity_id);
        if(Objects.isNull(bookingActivityInformation)){
            return new ResponseResult<>(404,"Booking Activity Information is not found");
        }
        return new ResponseResult<>(200,"Booking Activity Information is found",bookingActivityInformation);
    }

    @Override
    public ResponseResult createBookingActivityInfo(BookingActivityInformation bookingActivityInformation) {

        bookingActivityInformationMapper.insert(bookingActivityInformation);
        return new ResponseResult<>(200,"Create Booking Activity Information Success");
    }

    @Override
    public ResponseResult updateBookingActivityInfo(BookingActivityInformation bookingActivityInformation, Long booking_activity_id) {
        BookingActivityInformation old =
                bookingActivityInformationMapper.selectById(booking_activity_id);
        if(Objects.isNull(old)){
            return new ResponseResult<>(404,"Update failed: Booking Activity Information is not found");
        }
        bookingActivityInformation.setBookingActivityId(old.getBookingActivityId());
        bookingActivityInformationMapper.updateById(bookingActivityInformation);
        return new ResponseResult<>(200,"Update Booking Activity Information Success");
    }

    @Override
    public ResponseResult deleteBookingActivityInfo(Long booking_activity_id) {
        BookingActivityInformation old =
                bookingActivityInformationMapper.selectById(booking_activity_id);
        if(Objects.isNull(old)){
            return new ResponseResult<>(404,"Delete failed: Booking Activity Information is not found");
        }
        bookingActivityInformationMapper.deleteById(booking_activity_id);
        return new ResponseResult<>(200,"Delete Booking Activity Information Success");
    }

    @Override
    public ResponseResult getCurrentBookingActivityInfo() {
        Long userid = getUserID();
        List<String> userJoinedActivityID = new ArrayList<>();
        if(userid>0){
            QueryWrapper<BookingUserArriveData> arriveDataQueryWrapperWrapper = new QueryWrapper<>();
            arriveDataQueryWrapperWrapper.eq("user_id",userid);
            List<BookingUserArriveData> buadList = bookingUserArriveDataMapper.selectList(arriveDataQueryWrapperWrapper);
            buadList.forEach((e)->{
                userJoinedActivityID.add(e.getBookingActivityId().toString());
            });
        }


        QueryWrapper<BookingActivityInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("end_time", LocalDateTime.now());

        List<Map> list =
                BookingActivityInformation.getJsonResultforClient(bookingActivityInformationMapper.selectList(queryWrapper));

        //For Client Display
        list.forEach((e)->{
            if(userJoinedActivityID.contains( ((Long)e.get("bookingActivityId")).toString())){
                e.put("isJoin",true);
            }else{
                e.put("isJoin",false);
            }

        });
        return new ResponseResult(200,"Get All current booking activity information ",list);
    }

    @Override
    public ResponseResult getAllBookingActivityInfo() {
        List<BookingActivityInformation> list = bookingActivityInformationMapper.selectList(null);
        return new ResponseResult(200,"Get All booking activity information ",list);
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
