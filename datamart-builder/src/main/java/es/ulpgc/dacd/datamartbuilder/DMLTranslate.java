package es.ulpgc.dacd.datamartbuilder;

public class DMLTranslate {
    private static final String INSERT_MAX_TEMPERATURE =
            "INSERT INTO max_temperatures(date, time, place, station, value) VALUES('%s', '%s', '%s', '%s', '%f');";

    public static String insertMaxTemperature(Event event) {
        return String.format(INSERT_MAX_TEMPERATURE,
                event.getDate(),
                event.getTime(),
                event.getUbi(),
                event.getStation(),
                event.getTemperature());
    }

    private static final String INSERT_MIN_TEMPERATURE =
            "INSERT INTO min_temperatures(date, time, place, station, value) VALUES('%s', '%s', '%s', '%s', '%f');";

    public static String insertMinTemperature(Event event) {
        return String.format(INSERT_MIN_TEMPERATURE,
                event.getDate(),
                event.getTime(),
                event.getUbi(),
                event.getStation(),
                event.getTemperature());
    }


}
