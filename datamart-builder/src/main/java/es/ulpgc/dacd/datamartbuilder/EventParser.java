package es.ulpgc.dacd.datamartbuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventParser {
    public static List<Event> parseEvents(String filePath) throws IOException {
        List<Event> events = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            Event event = new Event();
            String[] parts = line.split(",");
            event.setDate(parts[0].split(":")[1].replaceAll("\"", ""));
            event.setTime(parts[1].split(":")[1].replaceAll("\"", "") + ":" + parts[1].split(":")[2].replaceAll("\"", ""));
            event.setStation(parts[2].split(":")[1].replaceAll("\"", ""));
            event.setUbi(parts[3].split(":")[1].replaceAll("\"", ""));
            event.setTemperature(Double.valueOf(parts[4].split(":")[1].replaceAll("\"", "")));
            events.add(event);
        }
        return events;
    }
}
