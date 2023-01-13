package es.ulpgc.dacd.datamartbuilder;

public class Event {
    private String Date;
    private String Time;
    private String station;
    private String ubi;
    private Double temperature;
    public Event() {
    }
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getUbi() {
        return ubi;
    }

    public void setUbi(String ubi) {
        this.ubi = ubi;
    }

    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }



}

