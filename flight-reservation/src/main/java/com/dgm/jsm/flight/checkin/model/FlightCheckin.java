package com.dgm.jsm.flight.checkin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class FlightCheckin implements Serializable {

    private int id;
    private String flightRoute;
    private String passengerName;
    private String passengerDocumentType;
    private String passengerDocumentNumber;
    private Date checkinTime;

    public FlightCheckin() {
        this.checkinTime = new Date();
    }

    public FlightCheckin(int id, String flightRoute, String passengerName, String passengerDocumentType, String passengerDocumentNumber) {
        this.id = id;
        this.flightRoute = flightRoute;
        this.passengerName = passengerName;
        this.passengerDocumentType = passengerDocumentType;
        this.passengerDocumentNumber = passengerDocumentNumber;
        this.checkinTime = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(String flightRoute) {
        this.flightRoute = flightRoute;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerDocumentType() {
        return passengerDocumentType;
    }

    public void setPassengerDocumentType(String passengerDocumentType) {
        this.passengerDocumentType = passengerDocumentType;
    }

    public String getPassengerDocumentNumber() {
        return passengerDocumentNumber;
    }

    public void setPassengerDocumentNumber(String passengerDocumentNumber) {
        this.passengerDocumentNumber = passengerDocumentNumber;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FlightCheckin that = (FlightCheckin) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FlightCheckin{" +
                "id=" + id +
                ", flightRoute='" + flightRoute + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", passengerDocumentType='" + passengerDocumentType + '\'' +
                ", passengerDocumentNumber='" + passengerDocumentNumber + '\'' +
                ", checkinTime=" + checkinTime +
                '}';
    }
}
