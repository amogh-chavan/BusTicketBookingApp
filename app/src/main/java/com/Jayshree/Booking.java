package com.Jayshree;

public class Booking {

    public String username,usernumber,pickuplocation,droplocation,date,
            time,seatType,bookingId,platenumber,drivername;

    public Booking(){

    }

    public Booking( String username,String usernumber,String pickuplocation,String droplocation,String date,
                    String time,String seatType,String bookingId,String platenumber,String drivername) {
        this.username = username;
        this.usernumber = usernumber;
        this.pickuplocation = pickuplocation;
        this.droplocation=droplocation;
        this.date=date;
        this.time=time;
        this.seatType=seatType;
        this.bookingId=bookingId;
        this.platenumber=platenumber;
        this.drivername=drivername;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    public void setPickuplocation(String pickuplocation) {
        this.pickuplocation = pickuplocation;
    }

    public void setDroplocation(String droplocation) {
        this.droplocation = droplocation;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getUsername() {
        return username;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public String getPickuplocation() {
        return pickuplocation;
    }

    public String getDroplocation() {
        return droplocation;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSeatType() {
        return seatType;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public String getDrivername() {
        return drivername;
    }


}
