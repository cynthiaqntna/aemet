package es.ulpgc.dacd.datamartbuilder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
public class Controller {
    public void run(String directoryPath) throws SQLException, IOException, ParseException {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        SQLiteAemetDatabase sqLiteAEMETDatabase = new SQLiteAemetDatabase();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                List<Event> events = EventParser.parseEvents(file.getAbsolutePath());
                sqLiteAEMETDatabase.sqliteAemetDatabase(events);
            }
        }
    }
}
