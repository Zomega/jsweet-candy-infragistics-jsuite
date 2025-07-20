package pv.awt;

import java.awt.AWTEvent;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;
import pv.util.Appointment;
import pv.util.PVBorder;

public class PVWeek extends Container implements Serializable, ActionListener, AdjustmentListener {
  int m_iBodr = 23;

  Color m_cBo;

  Color m_cSh = Color.gray;

  int m_0 = 34623504;

  int m_6 = 1879048192;

  public Vector m_days;

  protected PVDatePlus m_date;

  protected Adjustable m_scrollV;

  protected Adjustable m_scrollH;

  public String m_format = "EEE, MMM d";

  transient int m_z1 = -1;

  transient int m_zA = -1;

  transient int m_z0 = -1;

  public PVWeek() {
    init0();
    setNumberOfDays(5);
  }

  private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
    paramObjectInputStream.defaultReadObject();
    init0();
  }

  void init0() {
    setLayout(null);
    if (this.m_days == null)
      this.m_days = new Vector(8);
    this.m_z0 = -1;
  }

  public void doLayout() {
    pos(1000);
  }

  void pos(int paramInt) {
    int j = getNumberOfDays();
    if ((this.m_0 & 0x800) != 0 || this.m_date == null || j == 0)
      return;
    PVDay pVDay = dayAt(0);
    Adjustable adjustable = scrollBar(1);
    adjustable.setMaximum(pVDay.m_end - pVDay.m_start);
    int i;
    if ((i = pVDay.m_rows - 1) < 1)
      i = 1;
    adjustable.setVisibleAmount(i--);
    adjustable.setBlockIncrement((i < 1) ? 1 : i);
    adjustable.setValue(pVDay.m_first);
    if (paramInt == 0)
      return;
    Rectangle rectangle = doRect(null);
    Dimension dimension = dateSelector().getPreferredSize();
    int k = dimension.width;
    int m = dimension.height;
    i = getTimeScrollBarWidth();
    int n = (this.m_0 >> 23) + 1 & 0x1;
    if (m < 3)
      m = 0;
    if (k > rectangle.width)
      k = rectangle.width;
    if (paramInt == 1000) {
      dateSelector().setBounds(rectangle.x, rectangle.y, k, m);
      boolean bool;
      ((Component)scrollBar(0)).setVisible(bool = (!getShowDateScrollBar() || m == 0) ? false : true);
      if (bool)
        ((Component)scrollBar(0)).setBounds(rectangle.x + k, rectangle.y, rectangle.width - k, m);
    }
    if (m > 0)
      rectangle.y += m += n;
    m = rectangle.height - m;
    if ((rectangle.height = pVDay.m_iCapH - 1) > m)
      rectangle.height = m;
    ((Component)adjustable).setVisible(!(i <= 1));
    ((Component)adjustable).setBounds(rectangle.x + (getTimeScrollBarOnLeft() ? 0 : (rectangle.width - i)), rectangle.y + rectangle.height, i, m - rectangle.height);
    if (paramInt != 1000 || (rectangle.width -= i) < 2)
      return;
    if (getTimeScrollBarOnLeft())
      rectangle.x += i;
    String str = "00:00";
    if (pVDay.getTwelveHours())
      str = String.valueOf(str) + ' ' + pVDay.m_ampm[0];
    i = pVDay.getFontMetrics(pVDay.getRowLabelFont()).stringWidth(str) + 4 + (pVDay.getRowLabelBorder() & 0x7) / 2 * 2;
    if (pVDay.m_iBodr != 0)
      i += 3;
    if (pVDay.getShadow() != 0)
      i += pVDay.getShadowWidth();
    rectangle.width += n;
    k = (rectangle.width - i) / j;
    if (m < 1 || k < 1)
      m = k = n;
    if ((i = rectangle.width - k * j) < 1)
      i = n;
    int i1 = getNumberOfWeeks();
    int i2 = m / i1;
    int i3 = j + 1;
    for (paramInt = 0; paramInt < i1; paramInt++) {
      j = i3;
      while (j-- > 0) {
        pVDay = dayAt(paramInt * i3 + j);
        pVDay.setBounds(rectangle.x + ((j % i3 == 0) ? 0 : (i + k * (j % i3 - 1))), rectangle.y + paramInt * i2, ((j % i3 == 0) ? i : k) - n, i2 + ((paramInt + 1 == i1) ? (m - i2 * i1) : -n));
        if ((this.m_0 & 0x8000000) != 0)
          pVDay.paint(null);
      }
    }
    this.m_0 &= 0xF7FFFFFF;
  }

  Rectangle doRect(Graphics paramGraphics) {
    Rectangle rectangle = new Rectangle(getSize());
    PVBorder.shadow(paramGraphics, rectangle, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), getShadow(), getShadowWidth());
    PVBorder.draw(paramGraphics, rectangle, this.m_iBodr, getBorderColor());
    return rectangle;
  }

  public void paint(Graphics paramGraphics) {
    if (paramGraphics == null)
      return;
    Rectangle rectangle = doRect(paramGraphics);
    PVDay pVDay = dayAt(0);
    if (pVDay != null) {
      paramGraphics.setColor(getBackground());
      Rectangle rectangle1 = dateSelector().getBounds();
      int i = rectangle1.height;
      int j = (pVDay.getBounds()).y;
      if (i > 0 && rectangle1.width < rectangle.width)
        paramGraphics.fillRect(rectangle1.x + rectangle1.width, rectangle1.y, rectangle.width - rectangle1.width, i);
      rectangle1 = ((Component)scrollBar(1)).getBounds();
      paramGraphics.fillRect(rectangle1.x, j, rectangle1.width, pVDay.m_iCapH - 1);
      if (getShowLines()) {
        paramGraphics.setColor(pVDay.getGridColor());
        int k = getNumberOfWeeks();
        int m = getNumberOfDays() + 1;
        while (k-- > 0) {
          if (i > 0 || k > 0) {
            rectangle1 = dayAt(k * m).getBounds();
            paramGraphics.drawLine(rectangle.x, rectangle1.y - 1, rectangle.x + rectangle.width - 1, rectangle1.y - 1);
          }
        }
        k = rectangle.height + rectangle.y - 1;
        m--;
        while (--m > 0) {
          rectangle = dayAt(m).getBounds();
          i = rectangle.x + rectangle.width;
          paramGraphics.drawLine(i, j, i, k);
        }
      }
    }
    super.paint(paramGraphics);
  }

  public int getNumberOfDays() {
    return this.m_0 & 0xF;
  }

  public void setNumberOfDays(int paramInt) {
    fix(paramInt, 0, true);
  }

  public int getNumberOfWeeks() {
    return this.m_0 >> 4 & 0x7;
  }

  public void setNumberOfWeeks(int paramInt) {
    fix(0, paramInt, true);
  }

  public void fix() {
    fix(0, 0, false);
  }

  void fix(int paramInt1, int paramInt2, boolean paramBoolean) {
    int i = getNumberOfDays();
    int j = getNumberOfWeeks();
    if ((paramInt1 &= 0xF) == 0)
      paramInt1 = i;
    if ((paramInt2 &= 0x7) == 0)
      paramInt2 = j;
    if (paramInt1 == 0 || (paramInt1 == i && paramInt2 == j && paramBoolean))
      return;
    if (i > 0)
      i++;
    i *= j;
    int k = ++paramInt1 * paramInt2;
    this.m_0 |= paramBoolean ? 2176 : 134219904;
    while (i > k)
      remove(dayAt(--i));
    PVDay pVDay = null;
    Object object1 = null;
    Object object2 = null;
    j = -1;
    while (++j < k) {
      PVDay pVDay1;
      if (j < this.m_days.size()) {
        pVDay1 = dayAt(j);
      } else {
        this.m_days.addElement(pVDay1 = day(j));
      }
      pVDay = pVDay1;
      if (j == 0 && (object1 = pVDay1.m_format) == null)
        object1 = "";
      if (j == 1 && (object2 = pVDay1.m_format) == null) {
        object2 = new SimpleDateFormat(this.m_format);
        ((DateFormat)object2).setTimeZone(TimeZone.getDefault());
      }
      pVDay1.m_2 = pVDay1.m_2 & 0xE000007F | pVDay.m_2 & 0x1FFFFF80;
      pVDay1.m_3 = pVDay1.m_3 & 0xFF800000 | pVDay.m_3 & 0x7FFFFF;
      pVDay1.m_4 = (pVDay1.m_4 & 0xE00FF000 | pVDay.m_4 & 0x7F00FFF | 0x18000000) & ((j % paramInt1 == 0) ? -134217729 : -268435457);
      pVDay1.m_apps = pVDay.m_apps;
      pVDay1.m_format = (j % paramInt1 == 0) ? object1 : object2;
      pVDay1.m_rowH = pVDay.m_rowH;
      pVDay1.m_start = pVDay.m_start;
      pVDay1.m_end = pVDay.m_end;
      pVDay1.m_delta = pVDay.m_delta;
      pVDay1.m_first = pVDay.m_first;
      pVDay1.m_focus = pVDay.m_focus;
      pVDay1.m_rows = pVDay.m_rows;
      pVDay1.m_ampm = pVDay.m_ampm;
      pVDay1.m_calendar = pVDay.m_calendar;
      pVDay1.m_iBodr = pVDay.m_iBodr;
      pVDay1.m_cBo = pVDay.m_cBo;
      pVDay1.m_cSel = pVDay.m_cSel;
      pVDay1.m_cLn = pVDay.m_cLn;
      pVDay1.m_fD = pVDay.m_fD;
      pVDay1.m_fT = pVDay.m_fT;
      pVDay1.m_imgs = pVDay.m_imgs;
      pVDay1.m_aLs = pVDay.m_aLs;
      if (j >= i)
        add(pVDay1);
    }
    this.m_0 = this.m_0 & 0xFFFFF780 | --paramInt1 | paramInt2 << 4;
    setDate(getDate());
    doLayout();
    this.m_0 &= 0xFFFFFF7F;
  }

  public String getFormat() {
    return this.m_format;
  }

  public void setFormat(String paramString) {
    this.m_format = paramString;
    (dayAt(1)).m_format = null;
    fix();
  }

  public int getDeltaOfWeek() {
    return this.m_6 >> 28 & 0xF;
  }

  public void setDeltaOfWeek(int paramInt) {
    this.m_6 = this.m_6 & 0xFFFFFFF | (paramInt & 0xF) << 28;
  }

  public int getTimeScrollBarWidth() {
    return this.m_0 >> 16 & 0x1F;
  }

  public void setTimeScrollBarWidth(int paramInt) {
    this.m_0 = this.m_0 & 0xFFE0FFFF | (paramInt & 0x1F) << 16;
    doLayout();
  }

  public boolean getTimeScrollBarOnLeft() {
    return !((this.m_0 & 0x200000) != 0);
  }

  public void setTimeScrollBarOnLeft(boolean paramBoolean) {
    this.m_0 |= 0x200000;
    if (paramBoolean)
      this.m_0 &= 0xFFDFFFFF;
    doLayout();
  }

  public boolean getShowLines() {
    return !((this.m_0 & 0x800000) != 0);
  }

  public void setShowLines(boolean paramBoolean) {
    this.m_0 |= 0x800000;
    if (paramBoolean)
      this.m_0 &= 0xFF7FFFFF;
    doLayout();
  }

  public boolean getShowDateScrollBar() {
    return !((this.m_0 & 0x400000) != 0);
  }

  public void setShowDateScrollBar(boolean paramBoolean) {
    this.m_0 |= 0x400000;
    if (paramBoolean)
      this.m_0 &= 0xFFBFFFFF;
    doLayout();
  }

  public int getDOW() {
    return this.m_0 >> 24 & 0x7;
  }

  public void setDOW(int paramInt) {
    this.m_0 = this.m_0 & 0xF8FFFFFF | (paramInt & 0x7) << 24;
  }

  public PVDay dayAt(int paramInt) {
    return (paramInt >= 0 && paramInt < this.m_days.size()) ? this.m_days.elementAt(paramInt) : null;
  }

  public Appointment add(Date paramDate, String paramString, int paramInt1, int paramInt2) {
    return dayAt(0).add(paramDate, paramString, paramInt1, paramInt2, false);
  }

  PVDay day(int paramInt) {
    PVDay pVDay = new PVDay(this) {
        private final PVWeek this$0;

        protected void processEvent(AWTEvent param1AWTEvent) {
          int i = param1AWTEvent.getID();
          int j = this.this$0.getNumberOfDays() + 1;
          int k = this.m_2 & 0x7F;
          if (i == 1004 && k % j == 0) {
            this.this$0.dayAt(k + 1).requestFocus();
            return;
          }
          if (this.this$0.m_z0 > 0 && param1AWTEvent instanceof java.awt.event.FocusEvent)
            this.this$0.end(null);
          if (!(param1AWTEvent instanceof MouseEvent) || this.this$0.m_z0 < 0)
            super.processEvent(param1AWTEvent);
          if (param1AWTEvent instanceof KeyEvent && i == 401) {
            i = ((KeyEvent)param1AWTEvent).getKeyCode();
            if (i == 37 || i == 39) {
              byte b = (i == 37) ? -1 : 1;
              if ((i = k + b) % j == 0)
                i += b;
              if (i > 0 && i < j * this.this$0.getNumberOfWeeks())
                this.this$0.dayAt(i).requestFocus();
            }
          }
          if (param1AWTEvent instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent)param1AWTEvent;
            if (i == 501)
              this.this$0.m_z0 = -1;
            if (i == 506) {
              if (k % j != 0 && (this.m_3 & 0x30000000) == 268435456 && this.this$0.m_z0 < 0) {
                this.this$0.m_z1 = this.this$0.m_z0 = k;
                this.this$0.m_zA = this.m_id0;
              }
              if (this.this$0.m_z0 > 0)
                this.this$0.drag(mouseEvent);
            }
            if (i == 502 && this.this$0.m_z0 > 0 && this.this$0.end(mouseEvent))
              super.processEvent(param1AWTEvent);
          }
        }

        public int getCount() {
          return super.getCount() * (this.m_4 >> 27 & 0x1);
        }

        public synchronized void startEditing(boolean param1Boolean, int param1Int) {
          if ((this.m_4 & 0x8000000) != 0)
            super.startEditing(param1Boolean, param1Int);
        }

        public Appointment get(int param1Int) {
          if (this.this$0.m_z0 > 0) {
            int i = this.m_2 & 0x7F;
            if (i == this.this$0.m_z0) {
              if (this.this$0.m_z1 != i && param1Int == this.this$0.m_zA && (param1Int = this.m_id1) == this.this$0.m_zA)
                param1Int = this.m_id2;
            } else if (i == this.this$0.m_z1 && this.this$0.m_z0 > 0 && param1Int == this.m_id2 && this.this$0.m_zA >= 0) {
              param1Int = this.this$0.m_zA;
            }
          }
          if (param1Int < 0 && (param1Int = this.this$0.m_zA) < 0)
            param1Int = 0;
          return super.get(param1Int);
        }

        void calcSizes() {
          super.calcSizes();
          if ((this.m_2 & 0x7F) == 0 && (this.m_1 & 0x8000000) != 0)
            this.this$0.pos((getBounds()).y + this.m_iCapH - (((Component)this.this$0.scrollBar(1)).getBounds()).y - 1);
        }

        void run0() {
          if ((this.m_2 & 0x7F) == 1)
            super.run0();
        }

        protected void spin() {
          if (this.this$0.m_z0 < 0)
            super.spin();
        }

        public void fireAction(int param1Int1, int param1Int2) {
          int i = this.m_2 & 0x7F;
          if ((this.this$0.m_0 & 0x80) == 0 && (i == 1 || param1Int1 != 32) && this.this$0.m_days != null) {
            if (this.m_aLs == null && i > 0)
              this.m_aLs = (this.this$0.dayAt(0)).m_aLs;
            this.this$0.actW(this, param1Int1, param1Int2);
            super.fireAction(param1Int1, param1Int2);
          }
        }
      };
    if (paramInt == 0) {
      setBackground(pVDay.getBackground());
      setForeground(pVDay.getForeground());
      setFont(pVDay.getFont());
    }
    pVDay.m_2 |= paramInt;
    pVDay.setBorderStyle(0);
    pVDay.setCaptionBorder(2);
    pVDay.setRowLabelBorder(2);
    pVDay.setSpinDateHeight(0);
    pVDay.setSpinTimeHeight(0);
    pVDay.setFont(null);
    pVDay.setForeground(null);
    pVDay.setBackground(null);
    return pVDay;
  }

  void drag(MouseEvent paramMouseEvent) {
    PVDay pVDay1 = null;
    PVDay pVDay2 = (PVDay)paramMouseEvent.getSource();
    PVDay pVDay3 = dayAt(this.m_z1);
    Rectangle rectangle = pVDay2.getBounds();
    int i = paramMouseEvent.getX() + rectangle.x;
    int j = paramMouseEvent.getY();
    int k = getNumberOfDays() + 1;
    int m = k * getNumberOfWeeks();
    while ((pVDay1 = dayAt(--m)) != null && !pVDay1.getBounds().contains(i, j + rectangle.y));
    m = (pVDay1 == null) ? 0 : (pVDay1.m_2 & 0x7F);
    if (m % k == 0)
      return;
    if (pVDay2 != pVDay1)
      j += rectangle.y - (pVDay1.getBounds()).y;
    if (j < pVDay2.m_iCapH)
      return;
    j += pVDay2.m_i2 - pVDay2.m_i1;
    if (j < 0)
      j = 0;
    Appointment appointment = get(this.m_zA);
    if (m == this.m_z1) {
      if (appointment.m_y == j || j + appointment.m_h > (pVDay2.m_end - pVDay2.m_start + 1) * pVDay2.m_rowH)
        return;
    } else {
      i = this.m_z1;
      this.m_z1 = m;
      if (pVDay1 != pVDay2) {
        pVDay2.fireAction(32768, this.m_zA);
        if ((pVDay2.m_date & 0x800000) != 0) {
          this.m_z1 = i;
          return;
        }
      }
      pVDay3.m_2 &= Integer.MAX_VALUE;
      pVDay1.m_2 |= Integer.MIN_VALUE;
      pVDay3.m_id0 = -1;
      if (pVDay3 == pVDay2) {
        if (pVDay2.m_id1 == pVDay2.m_id2)
          pVDay2.m_id1 = pVDay2.m_id2 = -1;
      } else if (pVDay3.m_id1 == pVDay3.m_id2) {
        pVDay3.m_id1 = pVDay3.m_id2 = -1;
      } else {
        pVDay3.m_id2--;
      }
      if (pVDay1 == pVDay2) {
        pVDay2.m_id0 = this.m_zA;
        if (pVDay2.m_id1 < 0)
          pVDay2.m_id2 = pVDay2.m_id1 = this.m_zA;
      } else {
        if (pVDay1.m_id1 < 0) {
          pVDay1.m_id1 = pVDay1.m_id2 = 0;
        } else {
          pVDay1.m_id2++;
        }
        pVDay1.m_id0 = pVDay1.m_id2;
      }
    }
    appointment.m_y = j;
    pVDay1.m_1 |= Integer.MIN_VALUE;
    pVDay3.m_1 |= Integer.MIN_VALUE;
    pVDay1.paint0(null);
    pVDay3.paint0(null);
  }

  boolean end(AWTEvent paramAWTEvent) {
    PVDay pVDay1 = dayAt(this.m_z0);
    PVDay pVDay2 = dayAt(this.m_z1);
    this.m_z0 = -1;
    if (pVDay2 == pVDay1 || pVDay1 == null)
      return true;
    pVDay2.m_2 &= Integer.MAX_VALUE;
    pVDay1.m_2 |= Integer.MIN_VALUE;
    pVDay1.m_date &= 0xFBFFFFFF;
    pVDay1.m_id0 = -1;
    int i = -1;
    int j = getNumberOfDays() + 1;
    if (paramAWTEvent != null)
      if (paramAWTEvent.getID() != 502) {
        paramAWTEvent = null;
      } else {
        pVDay1.fireAction(16384, this.m_zA);
        if ((pVDay1.m_date & 0x800000) != 0)
          paramAWTEvent = null;
      }
    if (paramAWTEvent == null) {
      if (pVDay2.m_id1 == pVDay2.m_id2) {
        pVDay2.m_id1 = pVDay2.m_id2 = -1;
      } else {
        pVDay2.m_id2--;
      }
      pVDay1.m_id0 = this.m_zA;
      pVDay2 = null;
    } else {
      Vector vector = pVDay1.m_apps;
      Appointment appointment = vector.elementAt(this.m_zA);
      appointment.m_time = pVDay2.getTimeAt(0) + (((appointment.m_y + 1) * pVDay2.m_delta - 5) / pVDay2.m_rowH + pVDay2.getStartTime()) * 60000L;
      while (++i < vector.size() && appointment.m_time >= ((Appointment)vector.elementAt(i)).m_time);
      if (i != this.m_zA && i != this.m_zA + 1) {
        vector.removeElementAt(this.m_zA);
        if (i > this.m_zA)
          i--;
        vector.insertElementAt(appointment, i);
      }
      pVDay2.m_id0 = this.m_zA = vector.indexOf(appointment);
    }
    i = j * getNumberOfWeeks();
    while (--i > 0) {
      PVDay pVDay;
      if (i % j != 0 && (pVDay = dayAt(i)) != null) {
        pVDay.fix();
        if (pVDay != pVDay2)
          pVDay.paint0(null);
      }
    }
    pVDay1.fireAction(1024, this.m_zA);
    if (pVDay2 != null)
      pVDay2.requestFocus();
    return false;
  }

  public Appointment getFocusAppointment() {
    byte b = 0;
    while (++b < this.m_days.size()) {
      int i;
      if ((i = ((PVDay)this.m_days.elementAt(b)).m_id0) >= 0)
        return get(i);
    }
    return null;
  }

  public Vector appointments() {
    return (dayAt(0)).m_apps;
  }

  public Appointment get(int paramInt) {
    return appointments().elementAt(paramInt);
  }

  public int indexOf(Appointment paramAppointment) {
    return appointments().indexOf(paramAppointment);
  }

  public Color getShadowColor() {
    return (this.m_cSh == null) ? Color.gray : this.m_cSh;
  }

  public void setShadowColor(Color paramColor) {
    this.m_cSh = paramColor;
  }

  public Color getBorderColor() {
    return (this.m_cBo == null) ? getBackground() : this.m_cBo;
  }

  public void setBorderColor(Color paramColor) {
    if (!getBorderColor().equals(paramColor))
      this.m_cBo = paramColor;
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    doLayout();
  }

  public int getShadow() {
    return this.m_0 >> 8 & 0x7;
  }

  public void setShadow(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFF8FF | (paramInt & 0x7) % 5 << 8;
    doLayout();
  }

  public int getShadowWidth() {
    return this.m_0 >> 12 & 0xF;
  }

  public void setShadowWidth(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFF0FFF | (paramInt & 0xF) << 12;
    doLayout();
  }

  public void setDelta(int paramInt) {
    dayAt(0).setDelta(paramInt);
  }

  public int getDelta() {
    return dayAt(0).getDelta();
  }

  public void setStartTime(int paramInt) {
    dayAt(0).setStartTime(paramInt);
  }

  public int getStartTime() {
    return dayAt(0).getStartTime();
  }

  public void setEndTime(int paramInt) {
    dayAt(0).setEndTime(paramInt);
  }

  public int getEndTime() {
    return dayAt(0).getEndTime();
  }

  public void setRowHeight(int paramInt) {
    dayAt(0).setRowHeight(paramInt);
  }

  public int getRowHeight() {
    return dayAt(0).getRowHeight();
  }

  public void setDate(Date paramDate) {
    if (paramDate == null)
      paramDate = new Date();
    Calendar calendar = (Calendar)(dayAt(0)).m_calendar.clone();
    calendar.setTime(paramDate);
    int i = getDeltaOfWeek() + 1;
    int j = getNumberOfDays() + 1;
    int k = getDOW();
    if ((i -= j) < 0)
      i = 0;
    if (k != 0)
      while (calendar.get(7) != k)
        calendar.add(5, -1);
    dateSelector().setDate(paramDate = calendar.getTime());
    long l = paramDate.getTime();
    if ((this.m_0 & 0x40000000) == 0) {
      this.m_0 |= Integer.MIN_VALUE;
      scrollBar(0).setValue((int)(l / 86400000L));
      this.m_0 &= Integer.MAX_VALUE;
    }
    for (k = 1; k < j * getNumberOfWeeks(); k++) {
      if (k % j != 0)
        dayAt(k).setDate(new Date(l));
      l += 86400000L * ((k % j == 0) ? i : 1L);
    }
  }

  public Date getDate() {
    return dayAt(1).getDate();
  }

  public void setDateLimits(Date paramDate1, Date paramDate2) {
    dateSelector().getCalendarObject().setLowerLimit(paramDate1);
    dateSelector().getCalendarObject().setUpperLimit(paramDate2);
    scrollBar(0).setMinimum((int)(paramDate1.getTime() / 86400000L));
    scrollBar(0).setMaximum((int)(paramDate2.getTime() / 86400000L));
  }

  public void setPreferredSize(Dimension paramDimension) {
    (dayAt(0)).m_size = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension = (dayAt(0)).m_size;
    if (dimension == null)
      dimension = getSize();
    dimension = new Dimension(dimension);
    if (dimension.width < 2)
      dimension.width = 400;
    if (dimension.height < 2)
      dimension.height = 240;
    return dimension;
  }

  public Adjustable scrollBar(int paramInt) {
    Adjustable adjustable = this.m_scrollV;
    if (paramInt == 0) {
      adjustable = this.m_scrollH;
    } else {
      paramInt = 1;
    }
    if (adjustable == null) {
      adjustable = createScroll(paramInt);
      if (paramInt == 0) {
        paramInt = (int)(getDate().getTime() / 86400000L);
        adjustable.setMinimum(paramInt - 100);
        adjustable.setMaximum(paramInt + 200);
        adjustable.setVisibleAmount(28);
        adjustable.setUnitIncrement(7);
        adjustable.setBlockIncrement(28);
        adjustable.setValue(paramInt);
        this.m_scrollH = adjustable;
      } else {
        this.m_scrollV = adjustable;
      }
      add((Component)adjustable);
      adjustable.addAdjustmentListener(this);
    }
    return adjustable;
  }

  protected Adjustable createScroll(int paramInt) {
    try {
      return new PVScrollBar(paramInt);
    } catch (Error error) {
      return new Scrollbar(paramInt);
    }
  }

  public PVDatePlus dateSelector() {
    PVDatePlus pVDatePlus = this.m_date;
    if (pVDatePlus == null) {
      pVDatePlus = new PVDatePlus();
      pVDatePlus.m_4 = 4305;
      pVDatePlus.setUseLocale(true);
      pVDatePlus.setButtonInFront(true);
      pVDatePlus.setButtonWidth(16);
      pVDatePlus.setBorderStyle(0);
      pVDatePlus.m_1 |= 0xC1088;
      pVDatePlus.addActionListener(this);
      pVDatePlus.setPreferredSize(new Dimension(160, 20));
      add(this.m_date = pVDatePlus);
    }
    return pVDatePlus;
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    setDate(this.m_date.getDate());
  }

  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent) {
    if ((this.m_0 & 0x80000080) == 0) {
      this.m_0 |= 0x40000000;
      if (paramAdjustmentEvent.getSource() == this.m_scrollH) {
        setDate(new Date(86400000L * paramAdjustmentEvent.getValue()));
      } else {
        actW(null, 4096, paramAdjustmentEvent.getValue());
      }
      this.m_0 &= 0xBFFFFFFF;
    }
  }

  void actW(Object paramObject, int paramInt1, int paramInt2) {
    byte b = 0;
    int i = getNumberOfDays();
    if (i++ != 0 && (paramInt1 == 4096 || paramInt1 == 8192 || paramInt1 == 8 || paramInt1 == 2 || (paramInt1 == 65536 && paramInt2 >= 0))) {
      i *= getNumberOfWeeks();
      boolean bool = true;
      PVDay pVDay = null;
      while ((pVDay = dayAt(b)) != paramObject && ++b < i);
      if (pVDay != null)
        bool = ((pVDay.m_1 & 0x8000000) == 0) ? false : true;
      for (byte b1 = 0; b1 < i; b1++) {
        if (b1 != b) {
          if ((pVDay = dayAt(b1)) == null)
            return;
          if (paramInt1 == 4096) {
            pVDay.m_first = paramInt2;
          } else if (paramInt1 == 8192) {
            pVDay.m_focus = paramInt2;
            pVDay.setFocusIndex(-1);
          } else if (paramInt1 == 65536) {
            pVDay.setFocusIndex(-1);
          } else if (bool) {
            pVDay.fix();
          } else {
            return;
          }
          if (bool) {
            pVDay.m_1 |= 0xA0000000;
            pVDay.paint0(null);
          }
        }
      }
      if ((this.m_0 & 0x40000000) == 0 && paramInt1 == 4096 && paramObject != null) {
        this.m_0 |= Integer.MIN_VALUE;
        scrollBar(1).setValue(paramInt2);
        this.m_0 &= Integer.MAX_VALUE;
      }
    }
  }

  public void addActionListener(ActionListener paramActionListener) {
    dayAt(0).addActionListener(paramActionListener);
  }

  public void removeActionListener(ActionListener paramActionListener) {
    dayAt(0).removeActionListener(paramActionListener);
  }

  public int dragID(boolean paramBoolean) {
    return paramBoolean ? this.m_z1 : this.m_zA;
  }
}
