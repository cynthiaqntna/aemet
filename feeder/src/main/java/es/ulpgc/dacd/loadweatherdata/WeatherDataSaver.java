package es.ulpgc.dacd.loadweatherdata;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class WeatherDataSaver {
    private static final String DATALAKE_PATH = "C:\\Users\\cynth\\IdeaProjects\\aemet\\Datalake";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask(){
        public void run(){
            updateData(new WeatherDataLoader().loadData());
        }
    };
    public void scheduleUpdates(){
        timer.schedule(task, 0, 3600*1000);
    }
    private Set<WeatherEvent> savedEvents = new HashSet<>();

    public void updateData(List<WeatherEvent> weatherEvents) {
        try {
            String dateString = DATE_FORMAT.format(new Date());
            String filePath = DATALAKE_PATH + "\\" + dateString + ".events";
            FileWriter writer = new FileWriter(filePath, false); // false to truncate the file
            for (WeatherEvent event : weatherEvents) {
                if (!savedEvents.contains(event)) {
                    savedEvents.add(event);
                    writer.write(new Gson().toJson(event));
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

