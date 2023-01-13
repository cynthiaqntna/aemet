package es.ulpgc.dacd.loadweatherdata;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class WeatherDataLoader {
    private static final String API_URL = "https://opendata.aemet.es/opendata/api/observacion/convencional/todas";
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjeW50aGlhcXVpaW50YWFuYWEwN0BnbWFpbC5jb20iLCJqdGkiOiJhZWM2YWMxZC1kMGUwLTRiYTEtYTE2MC00YjNmNmRjZGQ5OGMiLCJpc3MiOiJBRU1FVCIsImlhdCI6MTY3MzU2MzQ2NCwidXNlcklkIjoiYWVjNmFjMWQtZDBlMC00YmExLWExNjAtNGIzZjZkY2RkOThjIiwicm9sZSI6IiJ9.DAxS8zPEOwXGHUwPqxMZOrEm8_SPR1qXsCHv-caU9f0";
    public List<WeatherEvent> loadData() {
        List<WeatherEvent> filteredEvents = new ArrayList<>();

        try {
            Gson gson = new Gson();
            JsonArray jsonElements = getJsonElements(API_KEY);
            for (JsonElement jsonElement : jsonElements) {
                WeatherEvent weatherEvent = parseWeatherEvent(jsonElement);
                if (isValidLocation(weatherEvent) && isValidDate(weatherEvent)) {
                    filteredEvents.add(weatherEvent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredEvents;
    }


    private JsonArray getJsonElements(String apiKey) throws IOException {
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(get(API_URL, apiKey), JsonElement.class).getAsJsonObject();
        return gson.fromJson(get(response.get("datos").getAsString(), apiKey), JsonArray.class);
    }
    private WeatherEvent parseWeatherEvent(JsonElement jsonElement) {
        WeatherEvent event = new WeatherEvent();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        event.setDate(jsonObject.get("fint").getAsString().substring(0, 10));
        event.setTime(jsonObject.get("fint").getAsString().substring(11, 19));
        event.setStation(jsonObject.get("idema").getAsString());
        event.setUbi(jsonObject.get("ubi").getAsString());
        event.setLatitude(jsonObject.get("lat").getAsDouble());
        event.setLongitude(jsonObject.get("lon").getAsDouble());
        try {
            event.setTemperature(jsonObject.get("ta").getAsDouble());
        } catch (NullPointerException e) {
            event.setTemperature(null);
        }
        return event;
    }
    private boolean isValidLocation(WeatherEvent weatherEvent) {
        return weatherEvent.getLatitude() < 28.4 && weatherEvent.getLatitude() > 27.5 &&
                weatherEvent.getLongitude() < -15 && weatherEvent.getLongitude() > -16;
    }

    private boolean isValidDate(WeatherEvent weatherEvent) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayAtMidnight = LocalDateTime.of(now.toLocalDate(), LocalTime.MIDNIGHT);
        LocalDateTime eventTime = LocalDateTime.parse(weatherEvent.getDate() + "T" + weatherEvent.getTime());
        return eventTime.isAfter(todayAtMidnight) || eventTime.isEqual(todayAtMidnight) && eventTime.isBefore(now);
    }

    private static String get(String url, String apiKey) throws IOException {
        return Jsoup.connect(url)
                .validateTLSCertificates(false)
                .timeout(15000)
                .ignoreContentType(true)
                .header("accept", "application/json")
                .header("api_key", apiKey)
                .method(Connection.Method.GET)
                .maxBodySize(0).execute().body();
    }
}
