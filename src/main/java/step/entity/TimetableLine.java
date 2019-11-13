package step.entity;

import step.entity.City;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimetableLine {
  private  String flightNumber;
  private Date flightDate;
  private Date flightTime;
  private City src;
  private City dst;
  private int freeSeat;

  public TimetableLine(String flightNumber,Date flightDate, Date flightTime, City src, City dst, int freeSeat) {
    this.flightNumber=flightNumber;
    this.flightDate=flightDate;
    this.flightTime=flightTime;
    this.src = src;
    this.dst = dst;
    this.freeSeat=freeSeat;
  }

 public TimetableLine()
 {}

  public City getSrc() {
    return src;
  }

  public City getDst() {
    return dst;
  }

  public Date getFlightTime() {
    return flightTime;
  }

  public String getFlightNumber(){return flightNumber;}

  public Date getFlightDate() {return flightDate;}

  public int getFreeSeat() {return freeSeat;}

    @Override
  public String toString() {

      DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
      String formattedFlightDate = dateFormatter.format(flightDate);

      SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");

    return String.format("%-20s%-20s%-20s%-20s%-20s%d",flightNumber,formattedFlightDate,dateFormat.format(flightTime),src,dst,freeSeat);
//    "TimetableLine{" +"src=" + src +", time=" + time +", dst=" + dst +'}';
  }

    public String toString1() {
        return String.format("%s %s %s %s %s %d",flightNumber,flightDate,flightTime,src,dst,freeSeat);
//    "TimetableLine{" +"src=" + src +", time=" + time +", dst=" + dst +'}';
    }

    public String toString2() {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedFlightDate = dateFormatter.format(flightDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        return String.format("%s %s %s %s %s %d",flightNumber,formattedFlightDate,dateFormat.format(flightTime),src,dst,freeSeat);
//    "TimetableLine{" +"src=" + src +", time=" + time +", dst=" + dst +'}';
    }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TimetableLine)) return false;

    TimetableLine that = (TimetableLine) o;

    if (!flightNumber.equals(this.flightNumber)) return false;
    if (src != null ? !src.equals(that.src) : that.src != null) return false;
    return dst != null ? dst.equals(that.dst) : that.dst == null;
  }

  @Override
  public int hashCode() {
    return 1;
  }
}
