package pv.util;

import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
  public int m_length = 1;

  public long m_time;

  public String m_text;

  public int m_alarm = -1;

  public Object m_object;

  public transient Image m_image;

  public Color m_color;

  public boolean m_stretch = false;

  public int m_x;

  public int m_y;

  public int m_w;

  public int m_h;

  public void setDate(Date paramDate) {
    this.m_time = (paramDate == null) ? 0L : paramDate.getTime();
  }

  public Date getDate() {
    return new Date(this.m_time);
  }

  public String toString() {
    return (this.m_text == null) ? "" : this.m_text;
  }
}
