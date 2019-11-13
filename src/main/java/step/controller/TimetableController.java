package step.controller;

import step.Console;
import step.SystemConsole;
import step.dao.DAO;
import step.entity.TimetableLine;
import step.service.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TimetableController {

String pathTable="src/main/java/step/data/timetableVerA.txt";
String pathMyBooking= "src/main/java/step/data/mybookings.txt";

DAO<TimetableLine> DAOTTL =new Service();

  public void show() throws IOException, ParseException {

    ArrayList<TimetableLine> ttlArrL = new ArrayList<>(DAOTTL.getAll(pathTable));

    for(TimetableLine s: ttlArrL)
      System.out.println(s);

  }

  public void showByFlightNumber() throws IOException, ParseException {

    Console console = new SystemConsole();
    console.printLn ("Please insert the flight number: ");
    String fltnumber= console.readLn();

    ArrayList<TimetableLine> ttlArrL = new ArrayList<>(DAOTTL.get(999,fltnumber,"NA", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2001"),999,pathTable));

    for(TimetableLine s: ttlArrL)
      System.out.println(s);
  }
}
