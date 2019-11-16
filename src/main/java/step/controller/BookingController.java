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
import java.util.function.Predicate;

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
        List<TimetableLine> ttlList1 =new ArrayList<>();

        ttlList1= DAOTTL.getBy((TimetableLine ttl) -> ttl.getFlightNumber().equals(flightNumber));

        /*
        ttlList1=DAOTTL.getBy(timetableLine -> {
            return timetableLine.getFlightDate().equals(flightNumber);
        });
*/
        TimetableLine exactMatch1 = new TimetableLine();
        exactMatch1=ttlList1.get(0);

            return exactMatch1;
    }

    public void remove() throws IOException, ParseException {
      DAOTTL.delete(1);

  }

    public void showMyBooking() throws IOException, ParseException {
        Console console = new SystemConsole();
        ArrayList<TimetableLine> ttlMyBooking = new ArrayList<>(DAOTTL.getAll(pathMyBooking));

        if (ttlMyBooking.isEmpty())
            console.printLn("No booking information.");
        else
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


      ArrayList<TimetableLine> ttlSearch_ = new ArrayList<>(DAOTTL.getBy((TimetableLine ttl) -> {
                  return ttl.getDst().getName().equals(searchCity)
                          && ttl.getFlightDate().compareTo(searchDate) == 0
                          && ttl.getFreeSeat() > searchSeat;
              }
      ));

      if (ttlSearch_.isEmpty()){
          console.printLn("There is no such a flight:");
          searchFlight();
      }

      for(TimetableLine s: ttlSearch_)
          System.out.println(s);

      Menu menu= new Menu();
      console.printLn ("Please choose flight number for booking or press 0 for main menu:  ");
      String Selection=console.readLn();

      if (Selection.equals("0")) menu.show();

      else {


          TimetableLine myBooking= new TimetableLine();
          ArrayList<TimetableLine> ttlBooking = new ArrayList<>(DAOTTL.getBy(new Predicate<TimetableLine>() {
              @Override
              public boolean test(TimetableLine timetableLine) {
                  return (timetableLine.getFlightDate().equals(searchDate))
                   &&    (timetableLine.getFlightNumber().equals(Selection)) ;
              }
          }));
          for(TimetableLine s: ttlBooking)
              myBooking=s;
      DAOTTL.put(999,myBooking);
      }
  }


}
