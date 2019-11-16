package step.service;

import step.Console;
import step.SystemConsole;
import step.dao.DAO;
import step.entity.City;
import step.entity.TimeTable;
import step.entity.TimetableLine;

import java.io.*;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Service implements DAO<TimetableLine> {

    @Override
    public List<TimetableLine> getAll(String path) throws IOException, ParseException {

        return readFromFile(path);
    }

    @Override
    public List<TimetableLine> get(int id) throws IOException, ParseException {

        List<TimetableLine> returnList =new ArrayList<>();
        List<TimetableLine> checkList =new ArrayList<>();
        TimetableLine exactMatch = new TimetableLine();


        return returnList;
    }

    @Override
    public List<TimetableLine> getBy(Predicate<TimetableLine> predicate) throws IOException, ParseException {
        List<TimetableLine> SearchBy=getAll("src/main/java/step/data/timetableVerA.txt");
        return SearchBy.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public void put(int id, TimetableLine myBooking) throws IOException, ParseException {

        ArrayList<TimetableLine> ttlarlMyBooking = new ArrayList<>();
        ttlarlMyBooking=readFromFile("src/main/java/step/data/mybookings.txt");
        ttlarlMyBooking.add(myBooking);
        BufferedWriter bw = new BufferedWriter(
                new FileWriter(
                        new File("src/main/java/step/data/mybookings.txt")));
        // -------------
        for (TimetableLine mb: ttlarlMyBooking) {
            bw.write(mb.toString2());
            bw.newLine();
        }
        bw.close();
    }



            @Override
    public void delete(int id) throws IOException, ParseException {
            Console console = new SystemConsole();
            ArrayList<TimetableLine> deleteBooking = new ArrayList<>();
            deleteBooking=readFromFile("src/main/java/step/data/mybookings.txt");

            if(deleteBooking.isEmpty())
                console.printLn("No booked flight available");
            else {

                console.printLn ("Please insert flight number: ");
                String fltnumber= console.readLn();

                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(
                                new File("src/main/java/step/data/mybookings.txt")));

                for (TimetableLine itr : deleteBooking)
                    if (!itr.getFlightNumber().equals(fltnumber)) {
                        bw.write(itr.toString2());
                        bw.newLine();
                    }

                bw.close();
            }
    }

    public ArrayList<TimetableLine> readFromFile (String path) throws IOException, ParseException {
        ArrayList<String> ttlStr = new ArrayList<>();
        ArrayList<TimetableLine> ttlarl = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) break;
            ttlStr.add(line);
        }
        br.close();
        String[] linettl = new String[6];

        for (String s: ttlStr)
        {
            linettl=s.split(" ");

            String flightNumber = linettl[0];
            Date flightDate=new SimpleDateFormat("dd/MM/yyyy").parse(linettl[1]);

            Date flightTime = new SimpleDateFormat("HHmm").parse(linettl[2]);
            City C1= new City(linettl[3]);
            City C2 =new City(linettl[4]);
            int freeSeat= Integer.parseInt(linettl[5]);
            TimetableLine ttlLongLine= new TimetableLine(flightNumber, flightDate,flightTime,C1,C2,freeSeat);
            ttlarl.add(ttlLongLine);
        }

        return ttlarl;

    }

}
