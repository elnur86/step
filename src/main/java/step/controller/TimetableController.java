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
import java.util.function.Predicate;

public class TimetableController {

String pathTable="src/main/java/step/data/timetableVerA.txt";

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

    List<TimetableLine> ttlArrL = new ArrayList<TimetableLine>();
      ttlArrL=DAOTTL.getBy(timetableLine -> timetableLine.getFlightNumber().equals(fltnumber));

    for(TimetableLine s: ttlArrL)
      System.out.println(s);
  }
}
