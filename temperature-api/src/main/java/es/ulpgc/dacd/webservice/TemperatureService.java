package es.ulpgc.dacd.webservice;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TemperatureService {
    private final String dbPath;

    public TemperatureService(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getMaxTemperature(Request req, Response res) {
        String from = req.queryParams("from");
        String to = req.queryParams("to");
        String query = String.format("SELECT * FROM max_temperatures WHERE date BETWEEN '%s' AND '%s'", from, to);
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<TemperatureData> data = new ArrayList<>();
            while (rs.next()) {
                TemperatureData temperatureData = new TemperatureData();
                temperatureData.setDate(rs.getString("date"));
                temperatureData.setTime(rs.getString("time"));
                temperatureData.setPlace(rs.getString("place"));
                temperatureData.setStation(rs.getString("station"));
                temperatureData.setValue(rs.getFloat("value"));
                data.add(temperatureData);
            }
            return new Gson().toJson(data);
        } catch (SQLException e) {
            res.status(500);
            TemperatureData temperatureData = new TemperatureData();
            temperatureData.setPlace(e.getMessage());
            return Collections.singletonList(temperatureData).toString();
        }
    }
    public String getMinTemperature(Request req, Response res) {
        String from = req.queryParams("from");
        String to = req.queryParams("to");
        String query = String.format("SELECT * FROM min_temperatures WHERE date BETWEEN '%s' AND '%s'", from, to);
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<TemperatureData> data = new ArrayList<>();
            while (rs.next()) {
                TemperatureData temperatureData = new TemperatureData();
                temperatureData.setDate(rs.getString("date"));
                temperatureData.setTime(rs.getString("time"));
                temperatureData.setPlace(rs.getString("place"));
                temperatureData.setStation(rs.getString("station"));
                temperatureData.setValue(rs.getFloat("value"));
                data.add(temperatureData);
            }
            return new Gson().toJson(data);
        } catch (SQLException e) {
            res.status(500);
            TemperatureData temperatureData = new TemperatureData();
            temperatureData.setPlace(e.getMessage());
            return Collections.singletonList(temperatureData).toString();
        }
    }
}