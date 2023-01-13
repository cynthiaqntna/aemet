package es.ulpgc.dacd.datamartbuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        String directoryPath = "C:\\Users\\cynth\\IdeaProjects\\aemet\\Datalake\\";
        Controller controller = new Controller();
        controller.run(directoryPath);
    }
}


