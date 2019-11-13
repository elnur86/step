package step.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DAO<T> {
  List<T> get(int id, String flight, String dsc, Date date, int np, String path) throws IOException, ParseException;
  List<T> getAll(String path) throws IOException, ParseException;
  void put(int id, T object) throws IOException;
  void delete(int id, String Code) throws IOException, ParseException;
}
