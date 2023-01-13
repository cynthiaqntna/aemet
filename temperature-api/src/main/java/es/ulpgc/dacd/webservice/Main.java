package es.ulpgc.dacd.webservice;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        port(8080);
        String dbPath = "C:\\Users\\cynth\\IdeaProjects\\aemet\\Datamart\\datamart.db";
        TemperatureService temperatureService = new TemperatureService(dbPath);
        get("/v1/places/with-max-temperature", temperatureService::getMaxTemperature);
        get("/v1/places/with-min-temperature", temperatureService::getMinTemperature);
    }
}

