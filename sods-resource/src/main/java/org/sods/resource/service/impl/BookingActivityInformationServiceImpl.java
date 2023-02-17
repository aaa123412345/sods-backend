package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BookingActivityInformation;
import org.sods.resource.mapper.BookingActivityInformationMapper;

import org.sods.resource.service.BookingActivityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BookingActivityInformationServiceImpl implements BookingActivityInformationService {
    @Autowired
    private BookingActivityInformationMapper bookingActivityInformationMapper;

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
        bookingActivityInformationMapper.updateById(old);
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
}
