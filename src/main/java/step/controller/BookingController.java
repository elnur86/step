package step.controller;

import step.Console;
import step.Database;
import step.Menu;
import step.SystemConsole;
import step.dao.DAO;
import step.entity.TimetableLine;
import step.service.Service;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingController {
DAO<TimetableLine> DAOTTL =new Service();
String pathTable="src/main/java/step/data/timetableVerA.txt";
String pathMyBooking= "src/main/java/step/data/mybookings.txt";

  public void add() throws Exception {

      TimetableLine addLine;
      Console console = new SystemConsole();
      console.printLn ("Please insert flight number: ");
      String fltnumber= console.readLn();

      addLine= getbyFlightNum(fltnumber);
      DAOTTL.put(1,addLine);

  }

    public TimetableLine getbyFlightNum(String flightNumber) throws Exception {

        List<TimetableLine> ttlList =new ArrayList<>();
        ttlList=DAOTTL.getAll(pathTable);
        TimetableLine exactMatch = new TimetableLine();
        int i=0;
        for(TimetableLine ttlFlightNumber: ttlList)
        {
            if (flightNumber.equals(ttlFlightNumber.getFlightNumber()))
                {
                exactMatch = ttlFlightNumber;
                i++;
            }
        }
        if (i==0) {
            System.out.println("There is no such a flight");
            add();
        }
            return exactMatch;
    }

    public void remove() throws IOException, ParseException {
        TimetableLine removeLine;
        Console console = new SystemConsole();
        console.printLn ("Please insert flight number: ");
        String fltnumber= console.readLn();

        DAOTTL.delete(1,fltnumber);

  }

  public void show() throws IOException, ParseException {

      ArrayList<TimetableLine> ttlMyBooking = new ArrayList<>(DAOTTL.get(1,"NA","NA", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2001"),12,pathTable));

      for(TimetableLine s: ttlMyBooking)
          System.out.println(s);

  }

    public void showMyBooking() throws IOException, ParseException {

        ArrayList<TimetableLine> ttlMyBooking = new ArrayList<>(DAOTTL.getAll(pathMyBooking));

        for(TimetableLine s: ttlMyBooking)
            System.out.println(s);

    }



  public void searchFlight() throws IOException, ParseException {
      Console console = new SystemConsole();
      console.printLn ("Please insert the destination city: ");
      String searchCity= console.readLn();
      console.printLn ("Please insert the date: ");
      String date=console.readLn();
      Date searchDate=new SimpleDateFormat("dd/MM/yyyy").parse(date);
      console.printLn ("Please insert how many free seats required: ");
      String seat=console.readLn();
      int searchSeat= Integer.parseInt(seat);


      ArrayList<TimetableLine> ttlSearch = new ArrayList<>(DAOTTL.get(1,"NA",searchCity, searchDate,searchSeat,pathTable));

      for(TimetableLine s: ttlSearch)
          System.out.println(s);

      Menu menu= new Menu();
      console.printLn ("Please choose flight number for booking or press 0 for main menu:  ");
      String Selection=console.readLn();

      if (Selection.equals("0")) menu.show();

      else {
          TimetableLine myBooking= new TimetableLine();
          ArrayList<TimetableLine> ttlBooking = new ArrayList<>(DAOTTL.get(999,Selection,"NA", searchDate,999,pathTable));
          for(TimetableLine s: ttlBooking)
              myBooking=s;
      DAOTTL.put(999,myBooking);
      }
  }


}