package de.thm.ateam.memory;

import java.sql.Date;

public class Result implements Comparable<Result>{

  private Date date;
  private Double value;

  public Result(Date date, Double value) {
    this.date = date;
    this.value = value;
  }

  public Date getDate() {
    return date;
  }

  public Double getValue() {
    return value;
  }

  public int compareTo(Result rs) {
    return value.compareTo(rs.getValue());
  }
}

