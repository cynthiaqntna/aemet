package es.ulpgc.dacd.loadweatherdata;

import java.io.IOException;
import java.util.List;

public interface DataLoader {
    public List<WeatherEvent> loadData() throws IOException;
}

