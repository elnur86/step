package step.service;

import step.dao.DAO;
import step.entity.City;
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

public class Service implements DAO<TimetableLine> {

    @Override
    public List<TimetableLine> getAll(String path) throws IOException, ParseException {

        return readFromFile(path);
    }

    @Override
    public List<TimetableLine> get(int id, String flight,String dsc, Date date, int np, String path) throws IOException, ParseException {

        List<TimetableLine> returnList =new ArrayList<>();
        List<TimetableLine> checkList =new ArrayList<>();
        List<TimetableLine> checkList1 =new ArrayList<>();
        List<TimetableLine> checkList2 =new ArrayList<>();
        List<TimetableLine> checkList3 =new ArrayList<>();
        TimetableLine exactMatch = new TimetableLine();
        TimetableLine exactMatch1 = new TimetableLine();
        TimetableLine exactMatch2 = new TimetableLine();
        TimetableLine exactMatch3 = new TimetableLine();
        SimpleDateFormat formatedDate = new SimpleDateFormat("dd/MM/yyyy");
        Date checkDate=formatedDate.parse("01/01/2001");
        //Date checkDate;
        if (id==999)
        {
            List<TimetableLine> ttlList =new ArrayList<>();

            ttlList=getAll(path);
            int i=0;
            for(TimetableLine ttlFlightNumber: ttlList)
            {
                if (flight.equals(ttlFlightNumber.getFlightNumber()))
                {
                    exactMatch = ttlFlightNumber;
                    i++;

                    returnList.add(exactMatch);
                }
            }
            if (i==0) {
                System.out.println("There is no such a flight");
            }
                      if (date.compareTo(checkDate)!=0)
            {
                checkList = returnList;
                returnList = new ArrayList<TimetableLine>();
                for (TimetableLine flightDate : checkList) {
                    if (date.compareTo(flightDate.getFlightDate()) == 0) {
                        exactMatch = flightDate;
                        returnList.add(flightDate);

                    }

                }

            }

        }
        else if(!dsc.equals("NA")) {
            List<TimetableLine> ttlList = new ArrayList<>();
            String cityCheck;
            ttlList = getAll(path);
            int i = 0;
            for (TimetableLine desCity : ttlList) {
                if (dsc.equals(desCity.getDst().getName())) {
                    exactMatch = desCity;
                    i++;
                    returnList.add(exactMatch);
                }
            }
            if (i == 0) {
                System.out.println("There is no such a flight");

            }
            int j = 0;
            if (date.compareTo(checkDate) != 0) {
                checkList = returnList;
                returnList = new ArrayList<TimetableLine>();
                for (TimetableLine flightDate : checkList) {
                    if (date.compareTo(flightDate.getFlightDate()) == 0) {
                        exactMatch = flightDate;
                        j++;
                        returnList.add(flightDate);

                    }

                }
                if (j == 0) {
                    System.out.printf("%s flight is not available on %s", dsc, date); }

               int l = 0;
                if (np!=999) {
                    checkList = returnList;
                    returnList = new ArrayList<TimetableLine>();
                    for (TimetableLine freeSeat : checkList) {
                        if (np<=freeSeat.getFreeSeat()) {
                            exactMatch = freeSeat;
                            l++;
                            returnList.add(freeSeat);
                        }
                    }
                    if (l == 0) {
                        System.out.printf("%s flight has no %d number of free seats", dsc, np); }

                }            }

        }
                return returnList;
    }

    @Override
    public void put(int id, TimetableLine myBooking) throws IOException {
        BufferedWriter bw = new BufferedWriter(
                new FileWriter(
                        new File("src/main/java/step/data/mybookings.txt")));
        // -------------
        bw.write(myBooking.toString2());
        bw.newLine();
        bw.close();
    }



            @Override
    public void delete(int id, String Code) throws IOException, ParseException {
            ArrayList<TimetableLine> deleteBooking = new ArrayList<>();
            deleteBooking=readFromFile("src/main/java/hw/step/data/mybookings.txt");


            BufferedWriter bw = new BufferedWriter(
                new FileWriter(
                      new File("src/main/java/hw/step/data/mybookings.txt")));

                for (TimetableLine itr: deleteBooking)
                    if(!itr.getFlightNumber().equals(Code)) {
                        bw.write(itr.toString1());
                        bw.newLine();
                    }

                bw.close();

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
