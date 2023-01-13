package es.ulpgc.dacd.loadweatherdata;
import java.util.List;

public class Controller {
    public void run() {
        WeatherDataLoader loader = new WeatherDataLoader();
        WeatherDataSaver saver = new WeatherDataSaver();

        List<WeatherEvent> weatherEvents = loader.loadData();
        saver.updateData(weatherEvents);
    }
}
