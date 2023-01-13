package es.ulpgc.dacd.datamartbuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteAemetDatabase {
    private static final String dbPath = "C:\\Users\\cynth\\IdeaProjects\\aemet\\Datamart\\datamart.db";
    public void sqliteAemetDatabase(List<Event> events) throws SQLException {
        Connection conn = connect();
        Statement statement = conn.createStatement();
        createTables(statement);
        insertData(statement, events);
    }
    private Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createTables(Statement statement) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS max_temperatures (" +
                "date TEXT," +
                "time TEXT," +
                "place TEXT," +
                "station TEXT," +
                "value NUMBER" +
                ");");
        statement.execute("CREATE TABLE IF NOT EXISTS min_temperatures (" +
                "date TEXT," +
                "time TEXT," +
                "place TEXT," +
                "station TEXT," +
                "value NUMBER" +
                ");");
    }

    private void insertData(Statement statement, List<Event> events) throws SQLException {
        Map<String, Event> maxTemperature = new HashMap<>();
        Map<String, Event> minTemperature = new HashMap<>();
        for (Event event : events) {
            String date = event.getDate();
            String time = event.getTime();
            String place = event.getUbi();
            String station = event.getStation();
            Double value = event.getTemperature();
            if(maxTemperature.containsKey(date)){
                if(value>maxTemperature.get(date).getTemperature()){
                    event.setStation(station);
                    event.setTime(time);
                    event.setUbi(place);
                    event.setTemperature(value);
                    maxTemperature.put(date, event);
                }
            }else{
                event.setStation(station);
                event.setTime(time);
                event.setUbi(place);
                event.setTemperature(value);
                maxTemperature.put(date, event);
            }
            if(minTemperature.containsKey(date)){
                if(value<minTemperature.get(date).getTemperature()){
                    event.setStation(station);
                    event.setTime(time);
                    event.setUbi(place);
                    event.setTemperature(value);
                    minTemperature.put(date, event);
                }
            }else{
                event.setStation(station);
                event.setTime(time);
                event.setUbi(place);
                event.setTemperature(value);
                minTemperature.put(date, event);
            }
        }
        for (Map.Entry<String, Event> entry : maxTemperature.entrySet()) {
            statement.execute(DMLTranslate.insertMaxTemperature(entry.getValue()));
        }
        for (Map.Entry<String, Event> entry : minTemperature.entrySet()) {
            statement.execute(DMLTranslate.insertMinTemperature(entry.getValue()));
        }
    }
}


