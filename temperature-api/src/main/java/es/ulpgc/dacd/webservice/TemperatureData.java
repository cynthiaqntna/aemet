package es.ulpgc.dacd.webservice;

public class TemperatureData {
    private String date;
    private String time;
    private String place;
    private String station;
    private double value;

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}