package pv.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.Serializable;

public class CalDay implements Serializable {
  public int m_d0;

  public int m_d1;

  public Color m_back;

  public Color m_fore;

  public transient Image m_im;

  public Object m_object;

  public Color m_foreground;

  public Font m_font;

  public int m_first;

  public transient int[] m_selection;

  public int m_count;

  public void fix() {
    this.m_count = 0;
    if (this.m_object != null) {
      this.m_count++;
      int i = 0;
      String str = this.m_object.toString();
      while (!i) {
        if ((i = str.indexOf('\n', i)) >= 0) {
          i++;
          this.m_count++;
        }
      }
    }
  }

  public String itemAt(int paramInt) {
    if (this.m_object == null)
      return null;
    String str = this.m_object.toString();
    int i = 0;
    int j = paramInt;
    while (j-- > 0) {
      if ((i = str.indexOf('\n', i) + 1) == 0)
        return null;
    }
    if ((j = str.indexOf('\n', i)) < 0)
      j = str.length();
    return str.substring(i, j);
  }

  public String toString() {
    String str = (this.m_object == null) ? "" : this.m_object.toString();
    int i = this.m_first;
    int j = 0;
    if (i > 0) {
      while (i-- > 0 && !j) {
        if ((j = str.indexOf('\n', j)) >= 0)
          j++;
      }
      str = (j >= 0) ? str.substring(j) : "";
    }
    return str;
  }
}
