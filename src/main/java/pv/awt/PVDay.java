package pv.awt;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.Beans;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
import pv.util.Appointment;
import pv.util.PVBorder;
import pv.util.PVDate0;
import pv.util.PVImage;
import pv.util.PVMultiLine;
import pv.util.PVUtility;

public class PVDay extends Container implements Runnable, Serializable, WindowListener, KeyListener, FocusListener, ActionListener {
  public static final int BEFORE_ADD = 1;

  public static final int ADDED = 2;

  public static final int BEFORE_DELETE = 4;

  public static final int DELETED = 8;

  public static final int ALARM = 16;

  public static final int DATE = 32;

  public static final int BEFORE_CALENDAR = 64;

  public static final int CALENDAR_CLOSED = 128;

  public static final int BEFORE_EDIT = 256;

  public static final int EDIT_STOP = 512;

  public static final int MOUSE = 1024;

  public static final int KEY = 2048;

  public static final int SCROLLED = 4096;

  public static final int FOCUS = 8192;

  public static final int BEFORE_MOUSE = 16384;

  public static final int MOUSE_OVER = 32768;

  public static final int FOCUS_APPOINTMENT = 65536;

  public static final int SHOW_CUSTOM_DIALOG = 131072;

  Color m_cRB;

  Color m_cRF;

  Color m_cApp;

  Color m_cBk = Color.white;

  Color m_cBo;

  Color m_cSel = Color.blue;

  Color m_c3D = Color.white;

  Color m_cSh = Color.gray;

  Color m_cCap;

  Color m_cTS;

  Color m_cLn;

  Font m_fD;

  Font m_fT = new Font("SansSerif", 1, 12);

  protected PVCalendar m_cal;

  public Dimension m_calSize;

  public Vector m_apps;

  public Calendar m_calendar;

  protected Dimension m_size;

  protected Object m_format;

  int m_iBodr = 13;

  transient int m_1;

  int m_2 = 675840;

  int m_3 = 9437183;

  int m_4 = 221;

  protected int m_spinH = 10;

  int m_iBut2H = 16;

  protected int m_rowH = 15;

  public int m_start = 14;

  public int m_end = 38;

  protected int m_delta = 30;

  public transient int m_first;

  public transient int m_focus;

  public transient int m_rows;

  protected transient int m_date;

  public transient int m_id1;

  public transient int m_id2;

  public transient int m_id0;

  public transient Image m_back;

  protected transient Image[] m_imgs;

  protected transient Component[] m_edit;

  public transient Rectangle m_r;

  public transient Rectangle m_r0;

  transient int m_iMon = 85;

  transient int m_iMonW = 90;

  transient int m_iDay = 176;

  transient int m_iDayW = 20;

  transient int m_iYear = 197;

  transient int m_iYearW = 40;

  transient int m_iCapH = 32;

  transient int m_iSpeed = 350;

  transient int m_i1;

  transient int m_i2;

  transient long m_z;

  transient Image m_img;

  public transient String[] m_ampm;

  transient ActionListener m_aLs;

  protected transient Thread m_run;

  public void run() {
    if (this.m_run != null)
      synchronized (this.m_run) {
        while ((this.m_1 & 0x4000000) != 0 && this.m_run != null) {
          try {
            spin();
            Thread.sleep((((this.m_1 & 0xFF00) == 0) ? 5000L : this.m_iSpeed));
          } catch (InterruptedException interruptedException) {}
        }
        return;
      }
  }

  public PVDay() {
    setFont(new Font("SansSerif", 0, 12));
    setForeground(Color.black);
    setBackground(Color.lightGray);
    init0();
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    if (this.m_aLs instanceof Serializable) {
      paramObjectOutputStream.writeObject("aLs");
      paramObjectOutputStream.writeObject(this.m_aLs);
    }
    paramObjectOutputStream.writeObject(null);
  }

  private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
    paramObjectInputStream.defaultReadObject();
    if ((this.m_3 & 0x800000) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0();
  }

  void init0() {
    this.m_ampm = new String[] { "AM", "PM" };
    setUseLocale(getUseLocale());
    enableEvents(60L);
    setLayout(null);
    if (this.m_apps == null)
      this.m_apps = new Vector();
    this.m_focus = this.m_id0 = this.m_id1 = this.m_id2 = -1;
    if (this.m_calendar == null) {
      this.m_calendar = new GregorianCalendar(getLocale());
      byte b = 10;
      while (b++ < 14)
        this.m_calendar.set(b, 0);
    }
    this.m_date = PVDate0.pack(new Date(), this.m_calendar);
    this.m_1 &= 0x4000000;
    this.m_2 &= 0x1FFFFFFF;
    this.m_3 &= 0xFFFFFFF;
  }

  public boolean getUseLocale() {
    return !((this.m_4 & 0x4000000) == 0);
  }

  public void setUseLocale(boolean paramBoolean) {
    if (paramBoolean) {
      this.m_4 |= 0x4000000;
      this.m_format = DateFormat.getDateInstance(0, getLocale());
      ((DateFormat)this.m_format).setTimeZone(TimeZone.getDefault());
      try {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)DateFormat.getTimeInstance(2, getLocale());
        if (paramBoolean = !(simpleDateFormat.toPattern().indexOf('a') < 0))
          this.m_ampm = simpleDateFormat.getDateFormatSymbols().getAmPmStrings();
        setTwelveHours(paramBoolean);
        return;
      } catch (Exception exception) {
        return;
      }
    }
    if (getUseLocale()) {
      this.m_4 &= 0xFBFFFFFF;
      this.m_format = null;
      calcSizes();
    }
  }

  public void setLocale(Locale paramLocale) {
    super.setLocale(paramLocale);
    setUseLocale(getUseLocale());
  }

  public Locale getLocale() {
    try {
      return super.getLocale();
    } catch (Exception exception) {
      return Locale.getDefault();
    }
  }

  public void addNotify() {
    super.addNotify();
    run0();
    this.m_1 |= 0x4000000;
  }

  void run0() {
    if (this.m_run == null && !Beans.isDesignTime())
      try {
        this.m_run = new Thread(this);
        this.m_run.start();
        return;
      } catch (Exception exception) {
        return;
      }
  }

  public void removeNotify() {
    stop();
    this.m_1 = 0;
    if (this.m_run != null)
      try {
        this.m_run.interrupt();
        this.m_run.stop();
      } catch (Exception exception) {}
    this.m_run = null;
    this.m_2 &= 0x1FFFFFFF;
    super.removeNotify();
  }

  public boolean isFocusTraversable() {
    return foc();
  }

  boolean foc() {
    return !((this.m_1 & 0x8000000) == 0 || (this.m_date & 0x40000000) != 0 || !isEnabled());
  }

  public int checkState() {
    return this.m_date >>> 27;
  }

  Component imgs() {
    Component component = new Component(this) {
        private final PVDay this$0;

        public void paint(Graphics param1Graphics) {
          enableEvents(48L);
          boolean bool = (param1Graphics != null) ? false : true;
          if (bool && (param1Graphics = getGraphics()) == null)
            return;
          byte b = -1;
          byte b1 = (this.this$0.m_imgs == null) ? 0 : this.this$0.m_imgs.length;
          Rectangle rectangle = new Rectangle();
          while (++b < 20) {
            rectangle.x = b % 10 * 22;
            rectangle.y = b / 10 * 22;
            rectangle.width = rectangle.height = 22;
            PVBorder.draw(param1Graphics, rectangle, (this.this$0.m_i1 == b) ? 557094 : ((this.this$0.m_i2 == b) ? 1245201 : 2064447), getBackground());
            if (!bool) {
              param1Graphics.setColor((b <= b1) ? this.this$0.getAppointmentBackground() : this.this$0.colorAt(b - b1));
              param1Graphics.fillRect(rectangle.x, rectangle.y, 18, 18);
              if (b < b1 && this.this$0.m_imgs[b] != null)
                param1Graphics.drawImage(this.this$0.m_imgs[b], rectangle.x, rectangle.y, 18, 18, this);
            }
          }
          if (bool)
            param1Graphics.dispose();
        }

        protected void processEvent(AWTEvent param1AWTEvent) {
          if (param1AWTEvent instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent)param1AWTEvent;
            int i = mouseEvent.getX() / 22 + mouseEvent.getY() / 23 * 10;
            if (mouseEvent.getID() == 501 && i >= 0 && i < 20) {
              if (this.this$0.m_i1 == i && mouseEvent.getClickCount() < 2)
                i = -1;
              this.this$0.m_i1 = i;
              paint(null);
            }
            if (mouseEvent.getID() == 505)
              i = -1;
            if (mouseEvent.getID() == 503 || mouseEvent.getID() == 505) {
              if (i < 0 || i >= 20)
                i = -1;
              if (i != this.this$0.m_i2) {
                this.this$0.m_i2 = i;
                paint(null);
              }
            }
          }
        }
      };
    this.m_i1 = this.m_i2 = -1;
    if (this.m_id0 >= 0 && this.m_imgs != null)
      for (byte b = 0; b < this.m_imgs.length; b++) {
        if (this.m_imgs[b] == (get(this.m_id0)).m_image) {
          this.m_i1 = b;
          break;
        }
      }
    component.setBounds(123, 116, 220, 44);
    return component;
  }

  public Color colorAt(int paramInt) {
    return new Color((paramInt > 16) ? (0xFF00C040 | 108 << 19 - paramInt) : ((0xFFB0F0B0 | 16257023 << paramInt) & (7680 * paramInt % 7 ^ 0xFFFFFFFF)));
  }

  String date0() {
    DateFormat dateFormat = DateFormat.getDateInstance(0, getLocale());
    dateFormat.setTimeZone(TimeZone.getDefault());
    return dateFormat.format(getDate());
  }

  void dialog() {
    Rectangle rectangle = getBounds();
    Dialog dialog = dialog("Edit Appointment on " + date0(), rectangle, false);
    if (dialog == null)
      return;
    stop();
    this.m_date |= 0x10000000;
    if ((rectangle.y += this.m_i1) > 400)
      rectangle.y -= 270;
    int i = (getToolkit().getScreenSize()).width;
    if (rectangle.x > i - 350)
      rectangle.x = i - 350;
    dialog.setBounds(rectangle.x, rectangle.y, 350, 242);
    this.m_edit = new Component[13];
    this.m_edit[0] = new TextArea((this.m_id0 < 0) ? "" : get(this.m_id0).toString(), 5, 0, 3);
    this.m_edit[0].setBounds(8, 42, 336, 70);
    String str = "OK";
    for (i = 2; i > 0; i--) {
      this.m_edit[i] = new Button(str);
      this.m_edit[i].setBounds(270, 211 - i * 24, 70, 20);
      ((Button)this.m_edit[i]).addActionListener(this);
      str = "Cancel";
    }
    while (i < 3)
      this.m_edit[i++].addKeyListener(this);
    this.m_edit[3] = imgs();
    this.m_edit[4] = new Checkbox("Stretch image", (this.m_id0 < 0) ? false : (get(this.m_id0)).m_stretch);
    this.m_edit[4].setBounds(125, 167, 110, 18);
    this.m_edit[8] = new Label("Text  (Enter==OK,  Escape==Cancel,  Enter+Shift==NewLine)");
    this.m_edit[8].setBounds(8, 28, 338, 14);
    long l = (this.m_id0 < 0) ? getTimeAt((this.m_focus < this.m_start) ? this.m_start : this.m_focus) : (get(this.m_id0)).m_time;
    i = 5;
    while (i < 9) {
      switch (i) {
        case 5:
          str = "Start:";
          break;
        case 6:
          str = "Length:";
          break;
        case 7:
          str = "Alarm:";
          break;
        case 8:
          str = "(Number of minutes before appointment)";
          break;
      }
      this.m_edit[i + 4] = new Label(str);
      if (i != 8) {
        this.m_edit[i + 4].setBounds(8, 120 + (i - 5) * 24, 49, 14);
        str = "";
        switch (i) {
          case 5:
            str = string(l);
            break;
          case 6:
            str = String.valueOf(str) + ((this.m_id0 < 0) ? this.m_delta : (get(this.m_id0)).m_length);
            break;
          case 7:
            if (this.m_id0 >= 0 && (get(this.m_id0)).m_alarm >= 0)
              str = String.valueOf(str) + (get(this.m_id0)).m_alarm;
            break;
        }
        this.m_edit[i] = new TextField(str);
        this.m_edit[i].setBounds(57, 115 + (i - 5) * 24, 60, 22);
        i++;
        continue;
      }
      break;
    }
    this.m_edit[12].setBounds(5, 112 + (i - 5) * 24, 210, 14);
    for (i = 0; i < 13; i++) {
      if (this.m_edit[i] != null)
        dialog.add(this.m_edit[i]);
    }
    if (this.m_id0 >= 0)
      this.m_date |= 0x20000000;
    this.m_z = System.currentTimeMillis();
    dialog.setVisible(true);
  }

  public void openCalendar() {
    int i;
    if ((this.m_date & 0xC0000000) != 0 || !getCalendar())
      return;
    fireAction(64, this.m_date);
    if ((this.m_date & 0x800000) != 0)
      return;
    Rectangle rectangle = getBounds();
    Dialog dialog = dialog("Initial day: " + date0(), rectangle, false);
    if (dialog == null)
      return;
    stop();
    this.m_date |= Integer.MIN_VALUE;
    this.m_1 = this.m_1 & 0xFCFFFFFF | 0x92000000;
    paint0(null);
    if (getUseLocale()) {
      this.m_cal.setLocale(getLocale());
      this.m_cal.setLocaleStrings(true);
    }
    this.m_cal.m_calendar = this.m_calendar;
    if (this.m_calSize == null) {
      Dimension dimension1 = this.m_cal.getPreferredSize();
      i = dimension1.width + 8;
      rectangle.height = dimension1.height + 42;
    } else {
      i = this.m_calSize.width;
      rectangle.height = this.m_calSize.height;
    }
    Dimension dimension = getToolkit().getScreenSize();
    if (rectangle.x + i + rectangle.width > dimension.width + 5) {
      if (rectangle.x > i + 5 || rectangle.x - i > dimension.width - rectangle.x - i - rectangle.width) {
        if ((rectangle.x -= i) < 0)
          rectangle.x = 0;
      } else {
        rectangle.x += rectangle.width;
      }
    } else {
      rectangle.x += rectangle.width;
    }
    if (rectangle.x + i > dimension.width)
      rectangle.x = dimension.width - i;
    dialog.setBounds(rectangle.x, rectangle.y, i, rectangle.height);
    this.m_edit = new Component[] { this.m_cal };
    this.m_cal.addActionListener(this);
    this.m_cal.setSel(this.m_date & 0x7FFFFF, false);
    dialog.setLayout(new BorderLayout());
    dialog.add("Center", this.m_cal);
    dialog.setVisible(true);
  }

  public void alarm(int paramInt) {
    Appointment appointment = get(paramInt);
    fireAction(16, paramInt);
    appointment.m_alarm = -2;
    if ((this.m_date & 0x40800000) != 0 || !getEnableAlarmDialog())
      return;
    boolean bool = ((this.m_date & 0x8000000) == 0) ? false : true;
    DateFormat dateFormat = DateFormat.getDateTimeInstance(0, 2, getLocale());
    dateFormat.setTimeZone(TimeZone.getDefault());
    Rectangle rectangle = getBounds();
    Dialog dialog = dialog("Reminder: " + dateFormat.format(new Date(appointment.m_time)), rectangle, true);
    if (dialog == null)
      return;
    appointment.m_alarm = -3;
    stop();
    if (bool) {
      this.m_1 |= Integer.MIN_VALUE;
      paint0(null);
    }
    this.m_date |= 0x40000000;
    this.m_i1 = paramInt;
    rectangle.x = (getToolkit().getScreenSize()).width / 2 - 200;
    dialog.setBounds(rectangle.x, rectangle.x, 390, 194);
    Choice choice = new Choice();
    for (paramInt = 5; paramInt < 22; paramInt += 5)
      choice.addItem(String.valueOf((paramInt < 20) ? paramInt : 30) + " minutes");
    choice.addItem("1 hour");
    for (paramInt = 2; paramInt < 10; paramInt *= 2)
      choice.addItem(String.valueOf(paramInt) + " hours");
    choice.select(0);
    choice.setBounds(292, 95, 78, 20);
    this.m_edit = new Component[] { choice, new Button("Dismiss"), new Button("Remind in"), new Label(appointment.toString(), 1) };
    this.m_edit[3].setBounds(10, 40, 280, 70);
    paramInt = 4;
    while (paramInt-- > 0) {
      if (paramInt > 0 && paramInt < 3) {
        this.m_edit[paramInt].setBounds(292, 5 + paramInt * 30, 78, 22);
        ((Button)this.m_edit[paramInt]).addActionListener(this);
      }
      dialog.add(this.m_edit[paramInt]);
    }
    dialog.setVisible(true);
  }

  boolean stop() {
    this.m_3 &= 0xFFFFFFF;
    setCursor(Cursor.getPredefinedCursor(0));
    this.m_date |= 0x2000000;
    stopEditing(true);
    this.m_date &= 0xFDFFFFFF;
    return !(this.m_edit != null);
  }

  public synchronized void stopEditing(boolean paramBoolean) {
    if (System.currentTimeMillis() - this.m_z < 300L || this.m_edit == null)
      return;
    int i = 512;
    this.m_1 |= 0xC0000000;
    if (this.m_edit[0] != null)
      if ((this.m_date & 0x8000000) != 0) {
        TextArea textArea = (TextArea)this.m_edit[0];
        if (paramBoolean) {
          String str = textArea.getText();
          if ((this.m_date & 0x20000000) != 0 && this.m_id0 >= 0)
            (get(this.m_id0)).m_text = str;
          if ((this.m_date & 0x20000000) == 0 && this.m_focus >= 0 && str.length() != 0) {
            this.m_1 |= 0x20000000;
            add(str, this.m_focus);
          }
        }
        textArea.removeFocusListener(this);
        textArea.removeKeyListener(this);
        this.m_i1 = (textArea.getBounds()).y + (textArea.getBounds()).height;
        remove(textArea);
      } else if ((this.m_date & 0xD0000000) != 0) {
        i = endDialog(paramBoolean, this.m_date & 0xD0000000);
      }
    if ((this.m_date & 0x30000000) == 805306368) {
      fix();
      setFocusIndex(this.m_id0);
    } else {
      this.m_1 |= 0x20000000;
    }
    this.m_date &= 0x7FFFFFF;
    this.m_edit = null;
    fireAction(i, this.m_id0);
    paint0(null);
  }

  int endDialog(boolean paramBoolean, int paramInt) {
    Dialog dialog = (Dialog)this.m_edit[0].getParent();
    if (dialog == null)
      return 1048576;
    dialog.removeWindowListener(this);
    if (paramInt == 268435456) {
      paramInt = -1;
      while (++paramInt < 3) {
        this.m_edit[paramInt].removeKeyListener(this);
        if (paramInt > 0)
          ((Button)this.m_edit[paramInt]).removeActionListener(this);
      }
      if (paramBoolean) {
        Appointment appointment = (this.m_id0 < 0) ? new Appointment() : get(this.m_id0);
        appointment.m_text = ((TextArea)this.m_edit[0]).getText();
        if ((paramInt = minutes(((TextField)this.m_edit[5]).getText())) < getStartTime()) {
          paramInt = getStartTime();
        } else if (paramInt > getEndTime() + this.m_delta / 2) {
          paramInt = getEndTime() + this.m_delta / 2;
        }
        appointment.m_time = getTimeAt(0) + paramInt * 60000L;
        if ((paramInt = minutes(((TextField)this.m_edit[6]).getText())) < 1)
          paramInt = this.m_delta;
        appointment.m_length = paramInt;
        appointment.m_alarm = minutes(((TextField)this.m_edit[7]).getText());
        appointment.m_stretch = ((Checkbox)this.m_edit[4]).getState();
        if (this.m_i1 >= 0 && this.m_i1 < 20) {
          if (this.m_imgs != null) {
            if (this.m_i1 < this.m_imgs.length)
              appointment.m_image = this.m_imgs[this.m_i1];
            this.m_i1 -= this.m_imgs.length;
          }
          if (this.m_i1 >= 0)
            appointment.m_color = (this.m_i1 == 0) ? null : colorAt(this.m_i1);
        }
        if (this.m_id0 < 0)
          add(appointment, true);
      }
      paramInt = 512;
    } else if (paramInt == 1073741824) {
      ((Button)this.m_edit[1]).removeActionListener(this);
      ((Button)this.m_edit[2]).removeActionListener(this);
      long l = System.currentTimeMillis();
      Appointment appointment = get(this.m_i1);
      paramInt = -2;
      if (appointment.m_time > l && paramBoolean) {
        paramInt = ((Choice)this.m_edit[0]).getSelectedIndex();
        if (paramInt++ > 1) {
          paramInt = 15 << paramInt - 3;
        } else {
          paramInt *= 5;
        }
        paramInt = (int)((appointment.m_time - l) / 60000L) - paramInt;
        if (paramInt < 0)
          paramInt = 0;
      }
      appointment.m_alarm = paramInt;
      paramInt = 1048576;
    } else if (paramInt == Integer.MIN_VALUE) {
      this.m_cal.removeActionListener(this);
      if (this.m_calSize == null)
        this.m_calSize = dialog.getSize();
      paramInt = 128;
      this.m_1 = this.m_1 & 0xFCFFFFFF | 0x10000000;
    } else {
      paramInt = 1048576;
    }
    dialog.removeAll();
    try {
      dialog.dispose();
    } catch (Exception exception) {}
    return paramInt;
  }

  public synchronized void startEditing(boolean paramBoolean, int paramInt) {
    if ((this.m_date & 0x40000000) != 0)
      return;
    fireAction(256, this.m_id0);
    if ((this.m_date & 0x800000) != 0)
      return;
    if (paramBoolean) {
      if (getCustomEditDialog()) {
        stop();
        fix();
        fireAction(131072, this.m_id0);
        return;
      }
      dialog();
      return;
    }
    if (!stop())
      return;
    int i = getEnableEditDialog() ? 7 : 0;
    int j = i & 0x6;
    Rectangle rectangle = new Rectangle(this.m_r.x + this.m_r0.x + i, 0, this.m_r0.width - i, this.m_rowH);
    String str = "";
    Appointment appointment = null;
    if ((i = this.m_id0) >= 0) {
      appointment = get(this.m_id0);
      if (this.m_first * this.m_rowH + 6 > appointment.m_y + appointment.m_h)
        i = -1;
      if (this.m_first * this.m_rowH + this.m_r0.height < appointment.m_y + 6)
        i = -1;
    }
    if (i < 0) {
      j = this.m_start + this.m_first;
      i = this.m_focus - j;
      if (i < 0 || i * this.m_rowH + 12 > this.m_r0.height) {
        this.m_1 |= 0xA0000000;
        setFocusIndex(-1);
        if (this.m_focus != j)
          fireAction(8192, this.m_focus = j);
      } else {
        rectangle.y = i * this.m_rowH;
      }
      j = this.m_r.y + this.m_r0.y + 2 + (this.m_focus - j) * this.m_rowH;
      i = hitTest(this.m_r.x + this.m_r0.x + 2, j);
      j = hitTest(this.m_r.x + this.m_r0.x + this.m_r0.width - 4, j);
      rectangle.x += 12;
      if ((i & 0xFFF8) != 0 && (rectangle.width -= 12) < 15) {
        rectangle.x -= 10;
        rectangle.width += 10;
      }
      if ((j & 0xFFF8) != 0 && j != i && (rectangle.width -= 12) < 15)
        rectangle.width += 10;
    } else {
      str = appointment.toString();
      rectangle.x += appointment.m_x;
      rectangle.y = appointment.m_y - this.m_first * this.m_rowH;
      rectangle.width = appointment.m_w - j;
      rectangle.height = appointment.m_h + 1;
      if (rectangle.y < 0) {
        rectangle.height += rectangle.y;
        rectangle.y = 0;
      }
      this.m_date |= 0x20000000;
    }
    this.m_date |= 0x8000000;
    i = this.m_rowH * (this.m_first + this.m_end - this.m_start + 1);
    if (i > this.m_r0.height)
      i = this.m_r0.height;
    if (rectangle.y + rectangle.height >= i)
      rectangle.height = i - rectangle.y;
    rectangle.y += this.m_r.y + this.m_r0.y - 1;
    rectangle.height += 2;
    if (rectangle.width < 4)
      rectangle.width = 4;
    if (paramInt != 0)
      str = String.valueOf(str) + (char)paramInt;
    this.m_z = System.currentTimeMillis();
    TextArea textArea = new TextArea(str, rectangle.height / 12, 0, 3);
    this.m_edit = new Component[] { textArea };
    textArea.setBackground(Color.white);
    textArea.setForeground(Color.black);
    textArea.setBounds(rectangle);
    add(textArea);
    textArea.requestFocus();
    textArea.addFocusListener(this);
    textArea.addKeyListener(this);
    i = str.length();
    textArea.select((paramInt == 0) ? 0 : i, i);
    paint0(null);
  }

  void key(KeyEvent paramKeyEvent) {
    if ((this.m_date & 0x18000000) != 0) {
      switch (paramKeyEvent.getKeyCode()) {
        case 9:
          if ((this.m_date & 0x8000000) == 0)
            return;
        case 10:
          if (paramKeyEvent.getModifiers() != 0)
            return;
        case 27:
          paramKeyEvent.consume();
          stopEditing(!(paramKeyEvent.getKeyCode() == 27));
          requestFocus();
          break;
      }
      return;
    }
  }

  public void eventCancel() {
    this.m_date |= 0x800000;
  }

  public void setFont(Font paramFont) {
    super.setFont(paramFont);
    calcSizes();
  }

  public Image[] getImages() {
    return this.m_imgs;
  }

  public void setImages(Image[] paramArrayOfImage, boolean paramBoolean) {
    this.m_imgs = paramArrayOfImage;
    if (paramBoolean) {
      this.m_2 |= 0x8000000;
      return;
    }
    this.m_2 &= 0xF7FFFFFF;
  }

  public Image getImage() {
    return this.m_back;
  }

  public void setImage(Image paramImage) {
    this.m_back = paramImage;
  }

  public int first(Date paramDate) {
    if (paramDate == null)
      return -1;
    byte b = -1;
    long l1 = paramDate.getTime();
    long l2 = 86400000L;
    l1 = l1 / l2 * l2;
    l2 += l1;
    while (++b < getCount()) {
      Appointment appointment = get(b);
      if (appointment.m_time >= l1) {
        if (appointment.m_time <= l2)
          return b;
        break;
      }
    }
    return -1;
  }

  public int last(Date paramDate) {
    int i = getCount();
    if (paramDate == null)
      return -1;
    long l1 = paramDate.getTime();
    long l2 = 86400000L;
    l1 = l1 / l2 * l2;
    l2 += l1;
    while (i-- > 0) {
      Appointment appointment = get(i);
      if (appointment.m_time <= l2) {
        if (appointment.m_time >= l1)
          return i;
        break;
      }
    }
    return -1;
  }

  public Vector getAppointments() {
    return this.m_apps;
  }

  public Appointment get(int paramInt) {
    return this.m_apps.elementAt(paramInt);
  }

  public int getCount() {
    return this.m_apps.size();
  }

  void fix2() {
    this.m_1 |= Integer.MIN_VALUE;
    int j = 0;
    int k = this.m_id1;
    if ((this.m_1 & 0x8000000) == 0 || k-- < 0)
      return;
    while (++k <= this.m_id2) {
      Appointment appointment = get(k);
      appointment.m_x = appointment.m_w = -1;
    }
    for (k = this.m_id1 - 1; ++k <= this.m_id2; k = i5 - 1) {
      int i5 = k;
      Appointment appointment = get(k);
      int m = appointment.m_y + appointment.m_h;
      while (++i5 <= this.m_id2) {
        appointment = get(i5);
        if (appointment.m_y < m) {
          int i6;
          if ((i6 = appointment.m_y + appointment.m_h) < m)
            m = i6;
          continue;
        }
        break;
      }
      int i4 = i5;
      while (--i4 >= k) {
        int i6 = i5 - 1;
        int i7 = i5;
        m = -1;
        while (--i7 >= k) {
          appointment = get(i7);
          int i8;
          if (appointment.m_x < 0 && (i8 = appointment.m_y + appointment.m_h) > m) {
            i6 = i7;
            m = i8;
          }
        }
        (get(i6)).m_x = i4 - k;
        if (i4 == i5 - 1)
          (get(i6)).m_w = 0;
      }
      int i2 = k;
      i4 = i5;
      appointment = get(k);
      int i3 = appointment.m_x;
      m = appointment.m_y;
      while (--i4 > k) {
        appointment = get(i4);
        int i6;
        if ((i6 = appointment.m_x) > i3) {
          m = appointment.m_y;
          i2 = i4;
        }
      }
      int n = this.m_r0.width;
      int i1;
      if ((i1 = n * (i5 - k) / 6) > n)
        i1 = n;
      i4 = k;
      while (--i4 >= this.m_id1) {
        appointment = get(i4);
        if (appointment.m_y + appointment.m_h > m && appointment.m_x <= n) {
          if (appointment.m_x > i1) {
            n = appointment.m_x;
            continue;
          }
          if ((get(i2)).m_y + (get(i2)).m_h <= appointment.m_y + appointment.m_h && appointment.m_x + appointment.m_w >= n && appointment.m_x <= n - n / (i5 - k)) {
            appointment.m_w -= 3;
            continue;
          }
          if (!j)
            j |= 0x4;
        }
      }
      if (j == 4) {
        n -= 3;
        j = 1;
      } else {
        j = 0;
      }
      i4 = i5;
      i1 = n / (i5 - k);
      while (--i4 >= k) {
        appointment = get(i4);
        appointment.m_x *= i1;
        appointment.m_w = (appointment.m_w == 0) ? (n - appointment.m_x) : i1;
      }
    }
    k = this.m_id1 - 1;
    int i = this.m_r0.width - 2;
    while (++k <= this.m_id2) {
      Appointment appointment = get(k);
      if ((appointment.m_w -= 2) < 9) {
        appointment.m_w = 9;
        continue;
      }
      if (appointment.m_x + appointment.m_w == i)
        appointment.m_w--;
    }
  }

  public void fix() {
    long l1 = getTimeAt(0);
    long l2 = l1 + ((this.m_end + 1) * this.m_delta) * 60000L;
    int i = getCount();
    this.m_1 |= Integer.MIN_VALUE;
    this.m_id1 = this.m_id2 = -1;
    while (i-- > 0) {
      Appointment appointment = get(i);
      long l = appointment.m_time;
      if (this.m_id2 < 0) {
        if (l >= l1) {
          if (l < l2)
            this.m_id1 = this.m_id2 = i;
          continue;
        }
        break;
      }
      if (l >= l1) {
        this.m_id1 = i;
        continue;
      }
      break;
    }
    if ((i = this.m_id1 - 1) < -1)
      return;
    l1 = l1 / 60000L + getStartTime();
    int j = (this.m_end - this.m_start + 1) * this.m_rowH;
    while (++i <= this.m_id2) {
      Appointment appointment = get(i);
      appointment.m_y = (int)((appointment.m_time / 60000L - l1) * this.m_rowH / this.m_delta);
      appointment.m_h = appointment.m_length * this.m_rowH / this.m_delta;
      if (appointment.m_h < 8)
        appointment.m_h = 8;
      if (appointment.m_y < 0)
        if (appointment.m_y + appointment.m_h > 5) {
          appointment.m_h += appointment.m_y;
          appointment.m_y = 0;
        } else {
          appointment.m_h = 0;
        }
      if (appointment.m_y + appointment.m_h > j)
        appointment.m_h = j - appointment.m_y;
    }
    if ((this.m_date & 0x2000000) == 0)
      fix2();
  }

  public void delete(Appointment paramAppointment) {
    delete(this.m_apps.indexOf(paramAppointment));
  }

  public void delete(int paramInt) {
    fireAction(4, paramInt);
    if ((this.m_date & 0x800000) == 0 && paramInt >= 0 && paramInt < this.m_apps.size()) {
      this.m_apps.removeElementAt(paramInt);
      fireAction(8, paramInt);
      fix();
      setFocusIndex(this.m_id0);
    }
  }

  public Appointment add(Appointment paramAppointment, boolean paramBoolean) {
    if (paramAppointment == null)
      return null;
    fireAction(1, -1);
    if ((this.m_date & 0x800000) != 0 || this.m_apps.indexOf(paramAppointment) >= 0)
      return null;
    byte b = 0;
    int i = this.m_apps.size();
    if (i > 0 && (get(i - 1)).m_time > paramAppointment.m_time) {
      while (--i > 0 && (get(--i)).m_time > paramAppointment.m_time);
      this.m_apps.insertElementAt(paramAppointment, ++i);
      b++;
    }
    if (b == 0)
      this.m_apps.addElement(paramAppointment);
    fix();
    setFocusIndex(paramBoolean ? i : this.m_id0);
    fireAction(2, i);
    return paramAppointment;
  }

  public Appointment add(Date paramDate, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    Appointment appointment = new Appointment();
    appointment.setDate(paramDate);
    appointment.m_length = paramInt1;
    appointment.m_text = paramString;
    appointment.m_alarm = paramInt2;
    return add(appointment, paramBoolean);
  }

  public Appointment add(Date paramDate, String paramString, int paramInt) {
    return add(paramDate, paramString, paramInt, -1, false);
  }

  public Appointment add(Date paramDate) {
    return add(paramDate, null, this.m_delta, -1, false);
  }

  public Appointment add(String paramString, int paramInt) {
    Calendar calendar = (Calendar)this.m_calendar.clone();
    calendar.set(this.m_date >> 9 & 0x3FFF, this.m_date >> 5 & 0xF, this.m_date & 0x1F, paramInt * this.m_delta / 60, paramInt * this.m_delta % 60, 0);
    return add(calendar.getTime(), paramString, this.m_delta, -1, true);
  }

  public void setDate(Calendar paramCalendar) {
    stop();
    if (paramCalendar != null) {
      paramCalendar = (Calendar)paramCalendar.clone();
      byte b = 10;
      while (b++ < 14)
        paramCalendar.set(b, 0);
      this.m_calendar = paramCalendar = (Calendar)paramCalendar.clone();
    }
    setDate(PVDate0.pack0(paramCalendar));
  }

  public void setDate(Date paramDate) {
    stop();
    setDate(PVDate0.pack(paramDate, this.m_calendar));
  }

  public Date getDate() {
    return PVDate0.unpack(this.m_date, this.m_calendar);
  }

  public Color getBackground() {
    Color color = super.getBackground();
    return (color == null) ? Color.lightGray : color;
  }

  public void setText3DColor(Color paramColor) {
    this.m_c3D = paramColor;
    paint(null);
  }

  public Color getText3DColor() {
    return (this.m_c3D == null) ? getBackground() : this.m_c3D;
  }

  public void setBorderColor(Color paramColor) {
    if (paramColor != getBorderColor())
      this.m_cBo = paramColor;
    paint(null);
  }

  public Color getBorderColor() {
    return (this.m_cBo == null) ? getBackground() : this.m_cBo;
  }

  public void setShadowColor(Color paramColor) {
    this.m_cSh = paramColor;
    paint(null);
  }

  public Color getShadowColor() {
    return (this.m_cSh == null) ? dark() : this.m_cSh;
  }

  public void setSelectionColor(Color paramColor) {
    this.m_cSel = paramColor;
    paint(null);
  }

  public Color getSelectionColor() {
    return (this.m_cSel == null) ? dark() : this.m_cSel;
  }

  Color dark() {
    return getBackground().darker();
  }

  public void setCaptionForeground(Color paramColor) {
    if (paramColor != getCaptionForeground())
      this.m_cCap = paramColor;
    paint(null);
  }

  public Color getCaptionForeground() {
    return (this.m_cCap == null) ? getForeground() : this.m_cCap;
  }

  public void setRowLabelBackground(Color paramColor) {
    if (paramColor != getRowLabelBackground())
      this.m_cRB = paramColor;
    repaint();
  }

  public Color getRowLabelBackground() {
    return (this.m_cRB == null) ? getBackground() : this.m_cRB;
  }

  public void setRowLabelForeground(Color paramColor) {
    if (paramColor != getRowLabelForeground())
      this.m_cRF = paramColor;
    repaint();
  }

  public Color getRowLabelForeground() {
    return (this.m_cRF == null) ? getForeground() : this.m_cRF;
  }

  public void setSpinColor(Color paramColor) {
    if (paramColor != getSpinColor())
      this.m_cTS = paramColor;
    paint(null);
  }

  public Color getSpinColor() {
    return (this.m_cTS == null) ? getBackground() : this.m_cTS;
  }

  public void setAppointmentBackground(Color paramColor) {
    if (paramColor != getAppointmentBackground())
      this.m_cApp = paramColor;
    paint(null);
  }

  public Color getAppointmentBackground() {
    return (this.m_cApp == null) ? getRowBackground() : this.m_cApp;
  }

  public void setRowBackground(Color paramColor) {
    this.m_cBk = paramColor;
    paint(null);
  }

  public Color getRowBackground() {
    return (this.m_cBk == null) ? getBackground() : this.m_cBk;
  }

  public void setGridColor(Color paramColor) {
    if (paramColor != getGridColor())
      this.m_cLn = paramColor;
    paint0(null);
  }

  public Color getGridColor() {
    return (this.m_cLn == null) ? getForeground() : this.m_cLn;
  }

  public Font getCaptionFont() {
    return (this.m_fD == null) ? getRowLabelFont() : this.m_fD;
  }

  public void setCaptionFont(Font paramFont) {
    if (paramFont != getCaptionFont())
      this.m_fD = paramFont;
    calcSizes();
  }

  public Font getRowLabelFont() {
    return (this.m_fT == null) ? getFont() : this.m_fT;
  }

  public void setRowLabelFont(Font paramFont) {
    this.m_fT = paramFont;
    calcSizes();
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    this.m_2 &= 0xBFFFFFFF;
    paint(null);
  }

  public int getShadow() {
    return this.m_2 >> 8 & 0x7;
  }

  public void setShadow(int paramInt) {
    this.m_2 = this.m_2 & 0xBFFFF8FF | (paramInt & 0x7) % 5 << 8;
    paint(null);
  }

  public int getShadowWidth() {
    return this.m_2 >> 12 & 0xF;
  }

  public void setShadowWidth(int paramInt) {
    this.m_2 = this.m_2 & 0xBFFF0FFF | (paramInt & 0xF) << 12;
    paint(null);
  }

  public void setCaptionText3D(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFCFFFF | (paramInt & 0x3) << 16;
    calcSizes();
  }

  public int getCaptionText3D() {
    return this.m_2 >> 16 & 0x3;
  }

  public void setRowLabelText3D(int paramInt) {
    this.m_2 = this.m_2 & 0xFFF3FFFF | (paramInt & 0x3) << 18;
    calcSizes();
  }

  public int getRowLabelText3D() {
    return this.m_2 >> 18 & 0x3;
  }

  public void setShowRowLabels(boolean paramBoolean) {
    this.m_4 |= 0x8000000;
    if (paramBoolean)
      this.m_4 &= 0xF7FFFFFF;
    calcSizes();
  }

  public boolean getShowRowLabels() {
    return !((this.m_4 & 0x8000000) != 0);
  }

  public void setShowCaption(boolean paramBoolean) {
    this.m_4 |= 0x2000000;
    if (paramBoolean)
      this.m_4 &= 0xFDFFFFFF;
    calcSizes();
  }

  public boolean getShowCaption() {
    return !((this.m_4 & 0x2000000) != 0);
  }

  public void setShowDOW(boolean paramBoolean) {
    this.m_2 |= 0x800;
    if (paramBoolean)
      this.m_2 &= 0xFFFFF7FF;
    calcSizes();
  }

  public boolean getShowDOW() {
    return !((this.m_2 & 0x800) != 0);
  }

  public void setFormat(Object paramObject) {
    this.m_format = paramObject;
    calcSizes();
  }

  public Object getFormat() {
    return this.m_format;
  }

  public void setTwelveHours(boolean paramBoolean) {
    this.m_2 |= 0x80;
    if (paramBoolean)
      this.m_2 &= 0xFFFFFF7F;
    calcSizes();
  }

  public boolean getTwelveHours() {
    return !((this.m_2 & 0x80) != 0);
  }

  public void setCustomEditDialog(boolean paramBoolean) {
    this.m_2 &= 0xFF7FFFFF;
    if (paramBoolean)
      this.m_2 |= 0x800000;
  }

  public boolean getCustomEditDialog() {
    return !((this.m_2 & 0x800000) == 0);
  }

  public void setGrid(boolean paramBoolean) {
    this.m_4 |= 0x100000;
    if (paramBoolean)
      this.m_4 &= 0xFFEFFFFF;
    paint0(null);
  }

  public boolean getGrid() {
    return !((this.m_4 & 0x100000) != 0);
  }

  public void setDarkPressedButton(boolean paramBoolean) {
    this.m_3 |= 0x400000;
    if (paramBoolean)
      this.m_3 &= 0xFFBFFFFF;
  }

  public boolean getDarkPressedButton() {
    return !((this.m_3 & 0x400000) != 0);
  }

  public void setFocusRectangle(boolean paramBoolean) {
    this.m_2 |= 0x10000000;
    if (paramBoolean)
      this.m_2 &= 0xEFFFFFFF;
  }

  public boolean getFocusRectangle() {
    return !((this.m_2 & 0x10000000) != 0);
  }

  public void setEnableDeleteKey(boolean paramBoolean) {
    this.m_2 |= 0x100000;
    if (paramBoolean)
      this.m_2 &= 0xFFEFFFFF;
  }

  public boolean getEnableDeleteKey() {
    return !((this.m_2 & 0x100000) != 0);
  }

  public void setEnableAlarmDialog(boolean paramBoolean) {
    this.m_4 |= 0x1000000;
    if (paramBoolean)
      this.m_4 &= 0xFEFFFFFF;
  }

  public boolean getEnableAlarmDialog() {
    return !((this.m_4 & 0x1000000) != 0);
  }

  public void setEnableEditDialog(boolean paramBoolean) {
    this.m_4 |= 0x800000;
    if (paramBoolean)
      this.m_4 &= 0xFF7FFFFF;
  }

  public boolean getEnableEditDialog() {
    return !((this.m_4 & 0x800000) != 0);
  }

  public void setDialogOnDoubleClick(boolean paramBoolean) {
    this.m_4 &= 0xFFDFFFFF;
    if (paramBoolean)
      this.m_4 |= 0x200000;
  }

  public boolean getDialogOnDoubleClick() {
    return !((this.m_4 & 0x200000) == 0);
  }

  public void setEditable(boolean paramBoolean) {
    this.m_4 |= 0x400000;
    if (paramBoolean)
      this.m_4 &= 0xFFBFFFFF;
  }

  public boolean getEditable() {
    return !((this.m_4 & 0x400000) != 0);
  }

  public void setRowLabelBorder(int paramInt) {
    this.m_4 = this.m_4 & 0xFFFFFFF0 | paramInt & 0xF;
    calcSizes();
  }

  public int getRowLabelBorder() {
    return this.m_4 & 0xF;
  }

  public int getSpinBorder() {
    return this.m_4 >> 4 & 0xF;
  }

  public void setSpinBorder(int paramInt) {
    this.m_4 = this.m_4 & 0xFFFFFF0F | (paramInt & 0xF) << 4;
    calcSizes();
  }

  public int getCaptionBorder() {
    return this.m_4 >> 8 & 0xF;
  }

  int capB() {
    return this.m_4 >> 8 & (getCalendar() ? 15 : 7);
  }

  public void setCaptionBorder(int paramInt) {
    this.m_4 = this.m_4 & 0xFFFFF0FF | (paramInt & 0xF) << 8;
    calcSizes();
  }

  public void setSpinTimeHeight(int paramInt) {
    this.m_iBut2H = paramInt;
    calcSizes();
  }

  public int getSpinTimeHeight() {
    return this.m_iBut2H & 0x7F;
  }

  public void setSpinDateHeight(int paramInt) {
    this.m_spinH = paramInt;
    calcSizes();
  }

  public int getSpinDateHeight() {
    return this.m_spinH;
  }

  public void setRowHeight(int paramInt) {
    this.m_rowH = paramInt;
    this.m_2 |= 0x200000;
    if (paramInt < 6 || paramInt > 255)
      this.m_2 &= 0xFFDFFFFF;
    calcSizes();
  }

  public int getRowHeight() {
    return ((this.m_2 & 0x200000) == 0) ? 0 : this.m_rowH;
  }

  public int getActionMask() {
    return this.m_3 & 0x1FFFF;
  }

  public void setActionMask(int paramInt) {
    this.m_3 = this.m_3 & 0xFFF00000 | paramInt & 0xFFFFF;
  }

  public void setDelta(int paramInt) {
    if (paramInt < 1)
      paramInt = 1;
    if (paramInt > 240)
      paramInt = 240;
    this.m_start = getStartTime() / paramInt;
    this.m_end = getEndTime() / paramInt;
    this.m_delta = paramInt;
    calcSizes();
  }

  public int getDelta() {
    return this.m_delta;
  }

  public void setStartTime(int paramInt) {
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt > 1200)
      paramInt = 1200;
    this.m_start = paramInt / this.m_delta;
    if (this.m_end <= this.m_start)
      this.m_end = this.m_start + 1;
    calcSizes();
  }

  public int getStartTime() {
    return this.m_start * this.m_delta;
  }

  public void setEndTime(int paramInt) {
    if (paramInt > 1439)
      paramInt = 1439;
    if ((paramInt /= this.m_delta) <= 0)
      paramInt = 1;
    this.m_end = paramInt;
    if (this.m_start >= paramInt)
      this.m_start = paramInt - 1;
    calcSizes();
  }

  public int getEndTime() {
    return this.m_end * this.m_delta;
  }

  public void setCalendarObject(PVCalendar paramPVCalendar) {
    if ((this.m_cal = paramPVCalendar) != null) {
      if (capB() == 0)
        this.m_4 |= this.m_4 << 4 & 0xF00;
      paramPVCalendar.m_1 |= 0x3000000;
    }
    calcSizes();
  }

  public PVCalendar getCalendarObject() {
    return this.m_cal;
  }

  public void setCalendar(boolean paramBoolean) {
    if (paramBoolean != getCalendar())
      setCalendarObject(paramBoolean ? new PVCalendar() : null);
  }

  public boolean getCalendar() {
    return !(this.m_cal == null);
  }

  public long getTimeAt(int paramInt) {
    return getDate().getTime() + (paramInt * this.m_delta) * 60000L;
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_size = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension = new Dimension((this.m_size == null) ? getSize() : this.m_size);
    if (dimension.width < 2)
      dimension.width = 240;
    if (dimension.height < 2)
      dimension.height = 240;
    return dimension;
  }

  protected String monthAt(int paramInt) {
    return PVDate0.month(paramInt, !((this.m_2 & 0x4000000) == 0), true);
  }

  protected String dowAt(int paramInt) {
    return PVDate0.dow(paramInt, !((this.m_2 & 0x1000000) == 0), true);
  }

  public int hitTest(int paramInt1, int paramInt2) {
    if (this.m_r == null)
      return -1;
    if ((this.m_date & 0x8000000) != 0 && this.m_edit != null && getEnableEditDialog()) {
      Rectangle rectangle = this.m_edit[0].getBounds();
      if (paramInt1 < rectangle.x && paramInt1 > rectangle.x - 8 && paramInt2 >= rectangle.y && paramInt2 <= rectangle.y + rectangle.height)
        return Integer.MIN_VALUE;
    }
    if ((paramInt1 -= this.m_r.x) <= 0 || (paramInt2 -= this.m_r.y) <= 0)
      return -1;
    if (paramInt1 >= this.m_r.width || paramInt2 >= this.m_r.height)
      return -1;
    int i = this.m_spinH;
    int j = this.m_iCapH;
    int k = 2;
    if (paramInt2 < j) {
      if (getCalendar() && paramInt2 > i && paramInt2 + i < j)
        return 16777216;
      if (paramInt1 <= this.m_iMon || paramInt1 >= this.m_iYear + this.m_iYearW)
        return -1;
      if (paramInt2 > i) {
        if (paramInt2 < j - i)
          return -1;
        paramInt2 = 1;
      } else {
        paramInt2 = 0;
      }
      return (paramInt1 <= this.m_iDay) ? paramInt2 : ((paramInt1 < this.m_iYear) ? (paramInt2 + 2) : (paramInt2 + 4));
    }
    paramInt2 -= this.m_r0.y;
    i = this.m_iBut2H & 0x80;
    if (paramInt2 <= 0)
      return (i == 0) ? 6 : -1;
    if (paramInt2 >= this.m_r0.height)
      return (i == 0) ? 7 : -1;
    if ((paramInt1 -= this.m_r0.x) > 0) {
      i = this.m_id0;
      j = this.m_rowH * this.m_first;
      do {
        if (i >= 0) {
          Appointment appointment = get(i);
          if (paramInt1 >= appointment.m_x && paramInt1 <= appointment.m_x + appointment.m_w && paramInt2 + j + k >= appointment.m_y && paramInt2 + j <= appointment.m_y + appointment.m_h + k) {
            if (k == 2 && (this.m_date & 0x8000000) == 0)
              if (paramInt1 <= appointment.m_x + 7) {
                k = 268435456;
              } else if (paramInt2 + j <= appointment.m_y + 2) {
                k = 536870912;
              } else if (paramInt2 + j + 2 >= appointment.m_y + appointment.m_h) {
                k = 1073741824;
              }
            return k & 0xF0000000 | ++i << 3;
          }
        }
        if (k == 2) {
          i = this.m_id2 + 1;
          k = 0;
        }
        if (--i != this.m_id0)
          continue;
        i--;
      } while (i >= this.m_id1);
    }
    return 1 + paramInt2 / this.m_rowH << 16;
  }

  public void fireAction(int paramInt1, int paramInt2) {
    this.m_date &= 0xFF7FFFFF;
    if (this.m_aLs != null && (paramInt1 & 0xFFFFF & this.m_3) != 0)
      this.m_aLs.actionPerformed(new ActionEvent(this, paramInt1, null, paramInt2));
  }

  public void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  protected void processFocusEvent(FocusEvent paramFocusEvent) {
    this.m_2 &= Integer.MAX_VALUE;
    if (paramFocusEvent.getID() == 1004) {
      if (!foc())
        return;
      this.m_2 |= Integer.MIN_VALUE;
    }
    this.m_1 |= 0xA0000000;
    paint0(null);
    super.processFocusEvent(paramFocusEvent);
  }

  protected void processMouseEvent(MouseEvent paramMouseEvent) {
    Appointment appointment;
    long l;
    super.processMouseEvent(paramMouseEvent);
    int i = paramMouseEvent.getID();
    if (i == 505) {
      move(-1);
      this.m_2 &= 0xDFFFFFFF;
      return;
    }
    if (!foc() || (paramMouseEvent.getModifiers() & 0x1804) != 0 || paramMouseEvent.isConsumed())
      return;
    this.m_3 &= 0xFEFFFFFF;
    int j = hitTest(paramMouseEvent.getX(), paramMouseEvent.getY());
    switch (i) {
      case 501:
        this.m_date |= 0x4000000;
        if (j == -1)
          return;
        if (j == 16777216) {
          openCalendar();
          return;
        }
        if ((this.m_3 & Integer.MIN_VALUE) != 0) {
          startEditing(true, 0);
          return;
        }
        stopEditing(true);
        if (j < 0)
          return;
        if (j < 8) {
          this.m_1 = this.m_1 & 0xFFFF0000 | 256 << j | 0x40000000;
          this.m_iSpeed = 350;
          try {
            this.m_run.interrupt();
            if (this.m_run.isInterrupted())
              return;
          } catch (Exception exception) {}
          spin();
          return;
        }
        this.m_2 |= Integer.MIN_VALUE;
        requestFocus();
        if ((j & 0xFF0000) != 0) {
          j = (j >> 16 & 0xFF) - 1 + this.m_start + this.m_first;
          if (j == this.m_focus)
            if (paramMouseEvent.isControlDown()) {
              j = -1;
            } else {
              if (paramMouseEvent.getClickCount() == 2 && getEditable())
                startEditing(getDialogOnDoubleClick(), 0);
              return;
            }
          if (j > this.m_end)
            return;
          this.m_1 = this.m_1 & 0xFF000000 | 0xA0000000;
          setFocusIndex(-1);
          if (this.m_focus != j)
            fireAction(8192, this.m_focus = j);
          break;
        }
        switch (this.m_3 & 0x70000000) {
          case 1073741824:
            j = (j >> 3 & 0x1FFF) - 1;
            if (j == this.m_id0) {
              if (paramMouseEvent.getClickCount() == 2 && getEditable())
                startEditing(false, 0);
              return;
            }
            setFocusIndex(j);
            this.m_1 |= Integer.MIN_VALUE;
            paint0(null);
          case 0:
            return;
          case 805306368:
            j = (get(this.m_id0)).m_h;
            break;
          default:
            j = (get(this.m_id0)).m_y;
            break;
        }
        if (this.m_id0 >= 0) {
          this.m_i1 = paramMouseEvent.getY();
          this.m_i2 = j;
        }
        return;
      case 502:
        this.m_date &= 0xFBFFFFFF;
        if ((this.m_1 & 0xFFFF) != 0) {
          this.m_1 &= 0xFFFF0000;
          if (j < 8 && j >= 0)
            this.m_1 |= 1 << j;
          this.m_1 |= 0x40000000;
          break;
        }
        if ((i = this.m_3 & 0x30000000) == 0 || this.m_id0 < 0)
          return;
        appointment = get(this.m_id0);
        fireAction(16384, this.m_id0);
        if ((this.m_date & 0x800000) != 0) {
          fix();
          break;
        }
        switch (i) {
          case 805306368:
            appointment.m_length = appointment.m_h * this.m_delta / this.m_rowH;
            break;
          case 268435456:
          case 536870912:
            l = appointment.m_time;
            appointment.m_time = getTimeAt(0) + (((appointment.m_y + 1) * this.m_delta - 5) / this.m_rowH + getStartTime()) * 60000L;
            if (i == 536870912) {
              appointment.m_length = (int)(appointment.m_length + (l - appointment.m_time) / 60000L);
              appointment.m_h = appointment.m_length * this.m_rowH / this.m_delta;
            }
            break;
        }
        j = getCount();
        i = -1;
        while (++i < j && (i == this.m_id0 || appointment.m_time >= (get(i)).m_time));
        if (i != this.m_id0 && i != this.m_id0 + 1) {
          this.m_apps.removeElementAt(this.m_id0);
          if (i > this.m_id0)
            i--;
          this.m_apps.insertElementAt(appointment, i);
          setFocusIndex(i);
        }
        fix2();
        fireAction(1024, this.m_id0);
        break;
      default:
        return;
    }
    paint0(null);
  }

  protected void processMouseMotionEvent(MouseEvent paramMouseEvent) {
    super.processMouseMotionEvent(paramMouseEvent);
    this.m_2 |= 0x20000000;
    if (!foc() || (paramMouseEvent.getModifiers() & 0x180F) != 0 || paramMouseEvent.isConsumed())
      return;
    int i = paramMouseEvent.getX();
    int j = paramMouseEvent.getY();
    int k = hitTest(i, j);
    if (paramMouseEvent.getID() == 503) {
      if (paramMouseEvent.getModifiers() == 0) {
        this.m_date &= 0xFBFFFFFF;
        move(k);
      }
      return;
    }
    if (paramMouseEvent.getID() == 506 && k > 0 && this.m_id0 >= 0) {
      if ((k = this.m_i2 + paramMouseEvent.getY() - this.m_i1) < 0)
        k = 0;
      i = (this.m_end - this.m_start + 1) * this.m_rowH;
      Appointment appointment = get(this.m_id0);
      switch (this.m_3 & 0x30000000) {
        case 536870912:
          j = appointment.m_y - k;
          if (appointment.m_h + j < 8)
            return;
          appointment.m_h += j;
        case 268435456:
          if (appointment.m_y == k || k + appointment.m_h > i)
            return;
          appointment.m_y = k;
          break;
        case 805306368:
          if (k < 8)
            k = 8;
          if (appointment.m_h == k || k + appointment.m_y > i)
            return;
          appointment.m_h = k;
          break;
        default:
          return;
      }
      this.m_1 |= Integer.MIN_VALUE;
      paint0(null);
    }
  }

  void move(int paramInt) {
    if ((this.m_date & 0xD4000000) != 0)
      return;
    int i = paramInt;
    if (getEditable()) {
      if (i == -1) {
        i = 0;
      } else if ((i & 0xFFF8) != 0) {
        if ((i & 0x40000000) != 0)
          i = i & 0xBFFFFFFF | 0x30000000;
        if ((i & 0x70000000) == 0)
          i |= 0x40000000;
        paramInt = -1;
      }
      i &= 0xF0000000;
      if ((this.m_3 & 0xF0000000) != i) {
        this.m_3 = this.m_3 & 0xFFFFFFF | i;
        switch (i) {
          case 268435456:
            i = 13;
            break;
          case 536870912:
          case 805306368:
            i = 8;
            break;
          case 1073741824:
            i = 1;
            break;
          case -2147483648:
            i = 12;
            break;
          default:
            i = 0;
            break;
        }
        setCursor(Cursor.getPredefinedCursor(i));
      }
    }
    i = this.m_1 & 0x3FFFFFF;
    if (paramInt >= 0) {
      if (paramInt < 8) {
        paramInt = 1 << paramInt;
      } else if (paramInt == 16777216) {
        if ((this.m_1 & 0x3000000) != 0)
          return;
      } else if ((paramInt & 0xFF0000) == 0) {
        return;
      }
      if (i == paramInt)
        return;
      i |= paramInt;
    } else {
      paramInt = 0;
    }
    this.m_1 = this.m_1 & 0xFE000000 | paramInt;
    if (getSpinBorder() > 7 && (i & 0xFFFF) != 0)
      this.m_1 |= 0x40000000;
    if (capB() > 7 && (i & 0x1000000) != 0)
      this.m_1 |= 0x10000000;
    if (getRowLabelBorder() > 7 && (i & 0xFF0000) != 0)
      this.m_1 |= 0x20000000;
    paint0(null);
  }

  protected void processKeyEvent(KeyEvent paramKeyEvent) {
    super.processKeyEvent(paramKeyEvent);
    int i = 0;
    int j = paramKeyEvent.getModifiers() & 0xF;
    if (j == 8 || !foc() || this.m_edit != null)
      return;
    if ((this.m_3 & 0xF0000000) != 0) {
      setCursor(Cursor.getPredefinedCursor(0));
      this.m_3 &= 0xFFFFFFF;
    }
    if (paramKeyEvent.getID() == 400 && (i = paramKeyEvent.getKeyChar()) >= ' ' && getEditable())
      startEditing(false, i);
    if (paramKeyEvent.getID() != 401)
      return;
    this.m_2 |= Integer.MIN_VALUE;
    switch (paramKeyEvent.getKeyCode()) {
      case 9:
      case 10:
      case 27:
      case 32:
        fireAction(2048, paramKeyEvent.getKeyCode());
        return;
      case 127:
        if (!getEnableDeleteKey())
          return;
        delete(this.m_id0);
        break;
      case 112:
        if (j == 9)
          this.m_3 |= 0x1000000;
        paint0(null);
        return;
      case 38:
        i = -1;
        break;
      case 40:
        i = 1;
        break;
      case 36:
        i = -500;
        break;
      case 35:
        i = 500;
        break;
      case 33:
        i = 1 - this.m_rows;
        break;
      case 34:
        i = this.m_rows - 1;
        break;
      default:
        return;
    }
    scroll(i, !(j != 0));
  }

  public int getFocusIndex() {
    return this.m_id0;
  }

  public void setFocusIndex(int paramInt) {
    if (paramInt > this.m_id2)
      paramInt = this.m_id2;
    if (paramInt < this.m_id1)
      paramInt = -1;
    if (this.m_id0 != paramInt) {
      this.m_1 |= 0xA0000000;
      fireAction(65536, this.m_id0 = paramInt);
    }
  }

  public Appointment getFocusAppointment() {
    return (this.m_id0 < 0) ? null : get(this.m_id0);
  }

  public void setFocusAppointment(Appointment paramAppointment) {
    setFocusIndex(this.m_apps.indexOf(paramAppointment));
  }

  public void selectTime(int paramInt) {
    paramInt = 1 + (paramInt - 1) / this.m_delta;
    if (paramInt > this.m_end || paramInt < this.m_first)
      paramInt = -1;
    if (this.m_focus != paramInt)
      fireAction(8192, this.m_focus = paramInt);
    scroll(0, true);
  }

  public void scroll(int paramInt, boolean paramBoolean) {
    int i = this.m_focus;
    int j = this.m_first;
    if (i < 0 && paramBoolean) {
      paramInt = 0;
      i = this.m_start + j;
    }
    if (paramInt <= 0) {
      if (j == 0) {
        if (paramBoolean) {
          if (i <= this.m_start && paramInt != 0)
            return;
          if ((i += paramInt) < this.m_start)
            i = this.m_start;
        } else {
          return;
        }
      } else if (paramBoolean) {
        if ((i += paramInt) < this.m_start)
          i = this.m_start;
      } else {
        j += paramInt;
      }
    } else if (j + this.m_rows - 1 >= this.m_end - this.m_start) {
      if (paramBoolean) {
        if (i >= this.m_end)
          return;
        if ((i += paramInt) >= this.m_end)
          i = this.m_end;
      } else {
        return;
      }
    } else if (paramBoolean) {
      if ((i += paramInt) >= this.m_end)
        i = this.m_end;
    } else {
      j += paramInt;
    }
    if (paramBoolean) {
      setFocusIndex(-1);
      if (j > (paramInt = i - this.m_start)) {
        j = paramInt;
      } else if (j < (paramInt = i - this.m_start + this.m_rows - 1)) {
        j = paramInt;
      }
    }
    if (j > (paramInt = this.m_end - this.m_start - this.m_rows + 1))
      j = paramInt;
    if (j < 0)
      j = 0;
    paramInt = this.m_1 & 0x8000000;
    if (this.m_first != j)
      this.m_1 &= 0xF7FFFFFF;
    if (paramBoolean && i >= 0 && this.m_focus != i)
      fireAction(8192, this.m_focus = i);
    this.m_1 |= paramInt;
    if (this.m_first != j)
      fireAction(4096, this.m_first = j);
    this.m_1 = this.m_1 & 0xFF00FFFF | 0xE0000000;
    paint0(null);
  }

  protected void spin() {
    int j = this.m_1 & 0xFF00;
    if (j == 0) {
      long l = System.currentTimeMillis();
      j = -1;
      while (++j < getCount()) {
        Appointment appointment = get(j);
        int k;
        if ((k = appointment.m_alarm) >= 0 && appointment.m_time >= l && appointment.m_time <= l + k * 60000L + 6000L)
          alarm(j);
      }
      return;
    }
    if (!stop())
      return;
    this.m_iSpeed = (this.m_iSpeed + 5) * 7 >> 3;
    if (j >= 16384) {
      scroll(((j & 0x4000) == 0) ? 1 : -1, false);
      return;
    }
    int i = (this.m_date & 0x7FFFFF) + 544;
    if (j <= 512) {
      i += (j == 512) ? -32 : 32;
    } else if (j <= 2048) {
      j = (i & 0x1F) + ((j == 2048) ? -1 : 1);
      if (j <= 0) {
        i -= 32;
        j = 31;
      } else if (j > PVDate0.maxDay(this.m_date >> 9 & 0x3FFF, this.m_date >> 5 & 0xF)) {
        i += 32;
        j = 1;
      }
      i = i & 0xFFFFFFE0 | j;
    } else if (j <= 8192) {
      i += (j == 8192) ? -512 : 512;
    }
    j = (i >> 5 & 0xF) - 1;
    if (j < 0) {
      i -= 512;
      j = 11;
    }
    if (j > 11) {
      i += 512;
      j = 0;
    }
    i = i & 0xFFFFFE1F | j << 5;
    if (i <= 512 || i >= 8388607)
      return;
    i -= 512;
    if ((i & 0x1F) > (j = PVDate0.maxDay(i >> 9 & 0x3FFF, i >> 5 & 0xF)))
      i = i & 0xFFFFFFE0 | j;
    setDate(i);
  }

  public void setDate(int paramInt) {
    if ((this.m_date & 0x7FFFFF) == (paramInt &= 0x7FFFFF)) {
      paramInt = 0;
    } else {
      this.m_date = this.m_date & 0xFF800000 | paramInt;
    }
    setFocusIndex(-1);
    fix();
    if (paramInt != 0)
      fireAction(32, paramInt);
    this.m_1 |= 0xD0000000;
    paint0(null);
  }

  void calcSizes() {
    this.m_2 &= 0xBFFFFFFF;
    if ((this.m_1 & 0x8000000) == 0)
      return;
    FontMetrics fontMetrics = getFontMetrics(getCaptionFont());
    this.m_2 &= 0xF8FFFFFF;
    int k = -1;
    int m = 0;
    int j;
    if ((j = (capB() & 0x7) / 2 * 2) < 2)
      j = 2;
    if (getFormat() != null) {
      this.m_iMon = this.m_iDay = this.m_iYearW = 0;
      this.m_iYear = this.m_iMonW = this.m_iDayW = this.m_r.width;
    } else {
      this.m_iDayW = fontMetrics.stringWidth("089");
      this.m_iYearW = this.m_iDayW * 5 / 3;
      this.m_iMonW = 10;
      while (++k < 12) {
        int n;
        if (this.m_iMonW < (n = fontMetrics.stringWidth(monthAt(k))))
          this.m_iMonW = n;
        if (k < 7 && getShowDOW() && m < (n = fontMetrics.stringWidth(dowAt(k))))
          m = n;
      }
      this.m_iMonW += this.m_iDayW / 3;
      if (m + this.m_iYearW + this.m_iDayW + this.m_iMonW + j + 7 > this.m_r.width) {
        this.m_iMon = fontMetrics.stringWidth("Www");
        if (m > 0) {
          m = this.m_iMon;
          if (m + this.m_iYearW + this.m_iDayW + this.m_iMonW + j >= this.m_r.width) {
            m = 0;
            this.m_2 |= 0x2000000;
          } else {
            this.m_2 |= 0x1000000;
          }
        }
        if (m + this.m_iYearW + this.m_iDayW + this.m_iMonW + j > this.m_r.width) {
          this.m_2 |= 0x4000000;
          this.m_iMonW = this.m_iMon;
        }
      }
      if ((k = m + this.m_iYearW + this.m_iDayW + this.m_iMonW + j) <= this.m_r.width) {
        k = (this.m_r.width - k) / 2;
      } else {
        k = 0;
      }
      this.m_iMon = k + m;
      this.m_iDay = this.m_iMon + this.m_iMonW;
      this.m_iYear = this.m_iDay + this.m_iDayW;
    }
    int i = this.m_spinH + fontMetrics.getAscent() + (j >>= 1);
    this.m_iCapH = getShowCaption() ? (i + fontMetrics.getDescent() + this.m_spinH + 2 + j) : 0;
    this.m_r0.y = this.m_iCapH;
    this.m_r.height -= this.m_r0.y;
    if ((j = (getRowLabelBorder() & 0x7) / 2 * 2) < 2)
      j = 2;
    fontMetrics = getFontMetrics(getRowLabelFont());
    this.m_iBut2H = this.m_iBut2H & 0xE000007F | fontMetrics.stringWidth("0") << 24 | 0x80;
    this.m_r0.x = getShowRowLabels() ? (((this.m_4 & 0x10000000) != 0) ? this.m_r.width : (fontMetrics.stringWidth(getTwelveHours() ? ("00:00 " + this.m_ampm[0]) : "00:00") + 5 + j)) : 0;
    this.m_r.width -= this.m_r0.x;
    m = (getRowLabelText3D() == 0) ? 2 : 3;
    k = fontMetrics.getAscent() + m;
    if ((this.m_2 & 0x200000) == 0)
      this.m_rowH = k + j;
    if ((j = (this.m_rowH - j - k) / 2 + k - m) < 4)
      j = 4;
    this.m_iBut2H |= i << 16 | j << 8;
    j = this.m_rows = this.m_end - this.m_start + 1;
    if ((m = this.m_r0.height / this.m_rowH) < j) {
      if ((this.m_iBut2H & 0x7F) != 0) {
        k = (this.m_iBut2H &= 0xFFFFFF7F) & 0x7F;
        this.m_r0.y += k;
        this.m_r0.height -= k + k;
      }
      m = this.m_rows = this.m_r0.height / this.m_rowH;
    }
    if (this.m_first > (j -= m))
      this.m_first = (j < 0) ? 0 : j;
    this.m_1 = -67108864;
    this.m_2 |= 0x40000000;
  }

  public void paint(Graphics paramGraphics) {
    boolean bool = (paramGraphics != null) ? false : true;
    if (bool && (this.m_1 & 0x8000000) == 0)
      return;
    if (bool && (paramGraphics = getGraphics()) == null)
      return;
    try {
      this.m_r = new Rectangle(getSize());
      if (this.m_r0 == null)
        this.m_r0 = new Rectangle();
      PVBorder.shadow(paramGraphics, this.m_r, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), getShadow(), getShadowWidth());
      PVBorder.draw(paramGraphics, this.m_r, this.m_iBodr, getBorderColor());
      if (this.m_img != null && !bool && (this.m_img.getWidth(null) != this.m_r.width || this.m_img.getHeight(null) != this.m_r.height)) {
        this.m_img = null;
        this.m_2 &= 0xBFFFFFFF;
      }
      if (this.m_img == null && (this.m_img = createImage(this.m_r.width, this.m_r.height)) == null)
        return;
      this.m_1 |= 0xF8000000;
      paint0(paramGraphics);
    } catch (Exception exception) {}
    if (bool)
      paramGraphics.dispose();
  }

  public synchronized void paint0(Graphics paramGraphics) {
    if ((this.m_1 & 0x8000000) == 0 || (this.m_date & 0x2000000) != 0)
      return;
    if ((this.m_2 & 0x40000000) == 0) {
      calcSizes();
      fix();
    }
    boolean bool2 = (paramGraphics != null) ? false : true;
    if (bool2 && (paramGraphics = getGraphics()) == null)
      return;
    run0();
    Graphics graphics = this.m_img.getGraphics();
    Rectangle rectangle = new Rectangle();
    int i2 = this.m_start + this.m_first + (this.m_1 >> 16 & 0xFF) - 1;
    int k = this.m_iBut2H >> 8 & 0x7F;
    Color color = getRowLabelBackground();
    boolean bool1;
    if (bool1 = ((this.m_1 & 0x20000000) == 0 || !getShowRowLabels()) ? false : true) {
      graphics.setColor(color);
      int i3;
      int i4;
      graphics.fillRect(0, i4 = this.m_r0.y, i3 = this.m_r0.x, this.m_r0.height);
      i3--;
      if (getGrid()) {
        graphics.setColor(dark());
        graphics.drawLine(i3, i4, i3, i4 + this.m_r0.height);
      }
    }
    int i1 = getRowLabelBorder();
    int j = (i1 == 1 || i1 == 8 || i1 == 9) ? 1 : 0;
    int i = -1;
    int m = this.m_start + this.m_first - 1;
    graphics.setFont(getRowLabelFont());
    int n;
    if ((n = getRowLabelText3D()) > 1)
      n = -1;
    while (++i <= this.m_rows && ++m <= this.m_end) {
      if (bool1) {
        rectangle.x = 0;
        this.m_r0.y += i * this.m_rowH;
        rectangle.height = this.m_rowH + j;
        rectangle.width = this.m_r0.x;
        rectangle.width = n * bodr(graphics, rectangle, color, i1, !(m != this.m_focus), !(m == i2));
        drawRowLbl(graphics, rectangle.x + 2, rectangle.y + k, m * this.m_delta, rectangle.width);
      }
    }
    if ((this.m_1 & Integer.MIN_VALUE) != 0) {
      drawBack(graphics, this.m_r0.x, this.m_r0.y, this.m_r0.width, this.m_r0.height + j);
      graphics.setFont(getFont());
      k = this.m_r0.y - 1 + j;
      i1 = this.m_r0.x;
      i2 = this.m_focus - this.m_first + this.m_start;
      for (m = 0; m < i; m++) {
        if (m == i2 && getFocusRectangle()) {
          graphics.setColor(((this.m_2 & Integer.MIN_VALUE) == 0) ? color : getSelectionColor());
          graphics.drawRect(i1, k + 1, this.m_r0.width - 1, this.m_rowH - 2);
        }
        k += this.m_rowH;
        if (getGrid()) {
          graphics.setColor(getGridColor());
          graphics.drawLine(i1, k, i1 + this.m_r0.width, k);
        }
      }
      i2 = this.m_first * this.m_rowH;
      bool1 = true;
      if ((m = this.m_id1 - 1) > -2)
        while (m++ <= this.m_id2 && bool1) {
          if (m != this.m_id0) {
            if (m > this.m_id2)
              if ((m = this.m_id0) >= 0) {
                bool1 = false;
              } else {
                break;
              }
            Appointment appointment = get(m);
            rectangle.x = appointment.m_x + this.m_r0.x;
            rectangle.y = appointment.m_y - i2;
            if (rectangle.y + 1 < this.m_r0.height) {
              rectangle.width = appointment.m_w;
              rectangle.height = appointment.m_h;
              if (rectangle.y < 0)
                if ((rectangle.height += --rectangle.y) > 1) {
                  rectangle.y = -1;
                } else {
                  continue;
                }
              if (rectangle.y + rectangle.height <= this.m_r0.height || (this.m_r0.height -= rectangle.y) > 1) {
                if (rectangle.y <= 0)
                  if ((this.m_iBut2H & 0x80) == 0) {
                    this.m_1 |= 0x40000000;
                  } else {
                    this.m_1 |= 0x10000000;
                  }
                rectangle.y += this.m_r0.y - 1;
                graphics.setColor((bool1 || (this.m_2 & Integer.MIN_VALUE) == 0) ? color : getSelectionColor());
                if (bool1) {
                  graphics.fillRect(rectangle.x, rectangle.y, 6, rectangle.height);
                } else {
                  graphics.fillRect(rectangle.x, --rectangle.y, rectangle.width, rectangle.height += 2);
                }
                graphics.setColor(getGridColor());
                graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                if (!bool1) {
                  rectangle.x++;
                  rectangle.width--;
                  rectangle.y += 2;
                  rectangle.height -= 4;
                }
                Color color1;
                if ((color1 = appointment.m_color) == null)
                  color1 = getAppointmentBackground();
                graphics.setColor(color1);
                rectangle.x += 5;
                rectangle.y++;
                rectangle.height -= 2;
                if ((rectangle.width -= 6) >= 2) {
                  graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                  graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                  i1 = 0;
                  Image image;
                  if ((image = appointment.m_image) == null && appointment.m_alarm != -1)
                    if ((this.m_2 & 0x8000000) == 0) {
                      i1 = k = 13;
                    } else if (this.m_imgs != null) {
                      image = this.m_imgs[0];
                    }
                  if (image != null) {
                    i1 = appointment.m_stretch ? rectangle.width : image.getWidth(null);
                    k = appointment.m_stretch ? rectangle.height : image.getHeight(null);
                  }
                  if (i1 > 2) {
                    i1 = Math.min(i1, rectangle.width);
                    k = Math.min(k, rectangle.height);
                    if (image != null) {
                      graphics.drawImage(image, rectangle.x, rectangle.y, i1, k, this);
                      i1 = appointment.m_stretch ? 0 : ++i1;
                    } else if (i1 > 12) {
                      boolean bool = (appointment.m_alarm == -2 || System.currentTimeMillis() >= appointment.m_time) ? false : true;
                      graphics.setColor(Color.gray);
                      color1 = new Color(bool ? -4145152 : -4144960);
                      n = ++rectangle.x + 3;
                      i1 = rectangle.y;
                      if (k <= 6) {
                        k = 6;
                      } else if (k > 10) {
                        i1 += --k - 10;
                      }
                      graphics.drawOval(n - 1, i1 + 2, 9, k - 5);
                      graphics.setColor(color1);
                      graphics.fillOval(n, i1, 7, k - 3);
                      graphics.setColor(Color.black);
                      graphics.drawOval(n, i1, 7, k - 3);
                      graphics.setColor(color1);
                      graphics.drawOval(n, i1 + 2, 7, k - 5);
                      if (bool)
                        graphics.setColor(Color.yellow);
                      graphics.fillOval(n + 1, i1 + 1, 4, k - 4);
                      n += 3;
                      k = ++k >> 1;
                      if (bool)
                        graphics.setColor(Color.white);
                      graphics.fillOval(n - 2, i1 + 3, 2, 2);
                      graphics.fillOval(rectangle.x, i1 += k, 13, k - 1);
                      if (bool)
                        graphics.setColor(color1);
                      graphics.fillOval(rectangle.x, i1, 13, k - 3);
                      graphics.setColor(Color.black);
                      graphics.drawOval(rectangle.x, i1, 13, k - 1);
                      if (bool) {
                        graphics.drawLine(n, i1 + 2, n, i1 + k);
                        graphics.setColor(Color.gray);
                        graphics.drawOval(n - 1, i1 + 1, 3, k);
                      }
                      rectangle.x--;
                      i1 = 15;
                    } else {
                      i1 = 0;
                    }
                  }
                  if (rectangle.height > 4) {
                    graphics.setColor(getForeground());
                    graphics.setFont((rectangle.height < 11) ? new Font("SansSerif", 0, rectangle.height + 1) : getFont());
                    PVMultiLine.draw(graphics, appointment.toString(), rectangle, i1, false, 0);
                  }
                }
              }
            }
          }
        }
    }
    if ((this.m_1 & 0xA0000000) != 0 && (i *= this.m_rowH) < this.m_r0.height) {
      i += j;
      graphics.setColor(getBackground());
      graphics.fillRect(0, this.m_r0.y + i, this.m_r.width, this.m_r0.height - i);
    }
    byte b = (this.m_spinH > 0) ? 1 : 0;
    i2 = n = i = 0;
    i1 = getSpinBorder();
    color = getBackground();
    bool1 = ((this.m_3 & 0x1000000) == 0) ? false : true;
    Object object = getFormat();
    if (getShowCaption() && ((this.m_1 & 0x10000000) != 0 || bool1)) {
      String str = "";
      graphics.setFont(getCaptionFont());
      graphics.setColor(color);
      graphics.fillRect(0, 0, this.m_r.width, this.m_iCapH);
      graphics.setFont(getCaptionFont());
      if ((n = getCaptionText3D()) > 1)
        n = -1;
      rectangle.x = 0;
      rectangle.y = this.m_spinH + b;
      rectangle.width = this.m_r.width;
      rectangle.height = this.m_iCapH - this.m_spinH * 2 - 3 * b;
      if ((this.m_1 & 0x2000000) != 0)
        n = -n;
      i2 = bodr(graphics, rectangle, color, capB(), !((this.m_1 & 0x2000000) == 0), !((this.m_1 & 0x1000000) != 0));
      FontMetrics fontMetrics = graphics.getFontMetrics();
      if (bool1) {
        while (i < 11)
          str = String.valueOf(str) + (char)(int)(65L + (16123002742581525L >> i++ * 5 & 0x1FL));
      } else {
        str = (bool1 = (object == null) ? false : true) ? ((object instanceof Format) ? ((Format)object).format(getDate()) : object.toString()) : monthAt(this.m_date >> 5 & 0xF);
      }
      k = this.m_iBut2H >> 16 & 0xFF;
      drawCap(graphics, str, this.m_iMon, this.m_iMonW, n, k, fontMetrics);
      if (!bool1) {
        if ((this.m_2 & 0x2000800) == 0) {
          Calendar calendar = (Calendar)this.m_calendar.clone();
          calendar.setTime(getDate());
          drawCap(graphics, dowAt(calendar.get(7) - 1), 0, this.m_iMon, n, k, fontMetrics);
        }
        drawCap(graphics, String.valueOf(this.m_date & 0x1F), this.m_iDay, this.m_iDayW, n, k, fontMetrics);
        drawCap(graphics, String.valueOf(this.m_date >> 9 & 0x3FFF), this.m_iYear, this.m_iYearW, n, k, fontMetrics);
      }
      bool1 = (i2 != 0 || (this.m_iBut2H & 0x80) == 0) ? false : true;
    }
    if (getShowCaption() && (this.m_1 & 0x50000000) != 0) {
      i = -1;
      while (++i < 6) {
        rectangle.y = 0;
        rectangle.x = this.m_iMon;
        rectangle.width = this.m_iMonW;
        rectangle.height = this.m_spinH;
        m = 3;
        if ((i & 0x1) != 0) {
          m += 2;
          rectangle.y += this.m_iCapH - this.m_spinH - b;
        }
        j = this.m_1 & 257 << i;
        if (j >= 256) {
          m |= 0x10;
        } else if (j == 0) {
          m |= 0x40;
        }
        if (i / 2 == 1) {
          rectangle.x = this.m_iDay;
          rectangle.width = this.m_iDayW;
        } else if (i / 2 == 2) {
          rectangle.x = this.m_iYear;
          rectangle.width = this.m_iYearW;
        }
        drawSpin(graphics, rectangle, color, i1, m);
        if (object == null || i != 3)
          continue;
        break;
      }
    }
    color = getSpinColor();
    if ((this.m_iBut2H & 0x80) == 0 && (this.m_1 & 0xE0000000) != 0) {
      i1 = getSpinBorder();
      k = this.m_iBut2H & 0x7F;
      i = -1;
      while (++i < 2) {
        rectangle.x = 0;
        rectangle.y = this.m_iCapH;
        rectangle.width = this.m_r.width;
        rectangle.height = k;
        m = 3;
        if (i == 1) {
          m += 2;
          rectangle.y = this.m_r.height - k;
        }
        j = this.m_1 & 16448 << i;
        if (j >= 16384) {
          m |= 0x10;
        } else if (j == 0) {
          m |= 0x40;
          if (i == 0 && getSpinBorder() > 7)
            bool1 = true;
        }
        drawSpin(graphics, rectangle, color, i1, m);
      }
    }
    if (bool1) {
      graphics.setColor(getGridColor());
      m = this.m_r0.y - 1;
      graphics.drawLine(getGrid() ? (this.m_r0.x - 1) : 0, m, this.m_r.width, m);
    }
    if ((this.m_date & 0x8000000) != 0 && this.m_edit != null && getEnableEditDialog()) {
      rectangle = this.m_edit[0].getBounds();
      rectangle.x -= this.m_r.x + 8;
      rectangle.y -= this.m_r.y;
      rectangle.width = 8;
      graphics.setColor(color);
      graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
      PVBorder.draw(graphics, rectangle, 1671185, color);
      PVImage.triangle(graphics, color, null, rectangle.x, rectangle.y + rectangle.height / 2 - 2, 6, 69);
    }
    super.paint(graphics);
    graphics.dispose();
    paramGraphics.drawImage(this.m_img, this.m_r.x, this.m_r.y, this);
    if (bool2)
      paramGraphics.dispose();
    this.m_1 &= 0xFFFFFFF;
  }

  protected void drawBack(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramGraphics.setColor(getRowBackground());
    paramGraphics.fillRect(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.m_back != null)
      paramGraphics.drawImage(this.m_back, paramInt1, paramInt2, paramInt3, paramInt4, null);
  }

  void drawRowLbl(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt3 / 60;
    String str = "";
    if (getTwelveHours()) {
      str = " " + this.m_ampm[(i < 12) ? 0 : 1];
      if ((i %= 12) == 0)
        i = 12;
    }
    if (i < 10)
      paramInt1 += this.m_iBut2H >> 24 & 0x1F;
    str = String.valueOf(String.valueOf(i)) + ":" + ((paramInt3 % 60 < 10) ? "0" : "") + (paramInt3 % 60) + str;
    if (paramInt4 != 0) {
      paramGraphics.setColor(getText3DColor());
      paramGraphics.drawString(str, paramInt1 + paramInt4, paramInt2 + paramInt4);
    }
    paramGraphics.setColor(getRowLabelForeground());
    paramGraphics.drawString(str, paramInt1, paramInt2);
  }

  int bodr(Graphics paramGraphics, Rectangle paramRectangle, Color paramColor, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramInt == 0)
      return 0;
    if (!paramBoolean1 && paramBoolean2 && (paramInt & 0x8) != 0) {
      paramInt = (paramInt & 0x7) / 2;
      while (true) {
        paramRectangle.x++;
        paramRectangle.y++;
        paramRectangle.height -= 2;
        paramRectangle.width -= 2;
        if (--paramInt <= 0)
          return 0;
      }
    }
    paramInt &= 0x7;
    switch (paramInt) {
      case 0:
        paramInt = 65538;
        break;
      case 1:
        paramInt = 32769;
        break;
      case 2:
        paramInt = 163842;
        break;
      case 3:
        paramInt = 196609;
        break;
      case 4:
        paramInt = 1409050;
        break;
      case 5:
        paramInt = 1671185;
        break;
      case 6:
        paramInt = 12124297;
        break;
      case 7:
        paramInt = 14614649;
        break;
    }
    PVBorder.draw(paramGraphics, paramRectangle, paramInt, paramColor, paramBoolean1);
    paramGraphics.setColor((paramBoolean1 && getDarkPressedButton()) ? PVImage.shift(paramColor, 0) : paramColor);
    paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    return paramBoolean1 ? -1 : 1;
  }

  void drawSpin(Graphics paramGraphics, Rectangle paramRectangle, Color paramColor, int paramInt1, int paramInt2) {
    paramGraphics.setColor(paramColor);
    paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
    boolean bool = ((paramInt2 & 0x1F) < 16) ? false : true;
    bodr(paramGraphics, paramRectangle, paramColor, paramInt1, bool, !(paramInt2 < 64));
    if (paramInt2 < 64 || (paramInt1 & 0x8) == 0)
      paramInt2 &= 0x1F;
    if (bool && getDarkPressedButton())
      paramInt2 = paramInt2 & 0xF | 0x30;
    PVImage.center(paramGraphics, paramColor, null, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 13, paramInt2);
  }

  void drawCap(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FontMetrics paramFontMetrics) {
    int i = paramFontMetrics.stringWidth(paramString);
    if (paramInt1 == 0 && this.m_iMon > 0) {
      paramInt1 = paramInt2 - i - 2;
    } else {
      paramInt1 += (paramInt2 - i) / 2;
    }
    if (paramInt3 != 0) {
      paramGraphics.setColor(getText3DColor());
      paramGraphics.drawString(paramString, paramInt1 + paramInt3, paramInt4 + paramInt3);
    }
    paramGraphics.setColor(getCaptionForeground());
    paramGraphics.drawString(paramString, paramInt1, paramInt4);
  }

  public static final int minutes(String paramString) {
    int i = 0;
    byte b = -1;
    int j;
    for (j = -1; ++b < paramString.length(); j = j * 10 + c - 48) {
      char c = paramString.charAt(b);
      if (c < '0' || c > '9') {
        if (j >= 0) {
          if (c == ':') {
            if (j <= 24 && i) {
              i = j;
              j = 0;
              continue;
            }
            break;
          }
          if (c == 'P' || c == 'p') {
            i = i % 12 + 12;
            continue;
          }
          if ((c == 'A' || c == 'a') && i == 12)
            i = 0;
        }
        continue;
      }
      if (j < 0)
        j = 0;
    }
    return j + i * 60;
  }

  public String string(long paramLong) {
    Calendar calendar = (Calendar)this.m_calendar.clone();
    calendar.setTime(new Date(paramLong));
    int i = calendar.get(12);
    null = String.valueOf((i < 10) ? ":0" : ":") + i;
    i = calendar.get(11);
    if (getTwelveHours()) {
      null = String.valueOf(null) + ' ' + this.m_ampm[(i < 12) ? 0 : 1];
      if ((i %= 12) == 0)
        i = 12;
    }
    return String.valueOf(String.valueOf(i)) + null;
  }

  Dialog dialog(String paramString, Rectangle paramRectangle, boolean paramBoolean) {
    if (!stop())
      return null;
    try {
      Dialog dialog = new Dialog(PVUtility.frame(this, paramRectangle), paramString, paramBoolean);
      dialog.addWindowListener(this);
      dialog.setResizable(false);
      dialog.setBackground(Color.lightGray);
      dialog.setFont(new Font("SansSerif", 0, 11));
      dialog.setLayout(null);
      return dialog;
    } catch (Exception exception) {
      return null;
    }
  }

  public void windowOpened(WindowEvent paramWindowEvent) {}

  public void windowClosed(WindowEvent paramWindowEvent) {}

  public void windowIconified(WindowEvent paramWindowEvent) {}

  public void windowDeiconified(WindowEvent paramWindowEvent) {}

  public void windowActivated(WindowEvent paramWindowEvent) {
    this.m_z = System.currentTimeMillis();
  }

  public void windowClosing(WindowEvent paramWindowEvent) {
    stopEditing(false);
  }

  public void windowDeactivated(WindowEvent paramWindowEvent) {
    if ((this.m_date & 0x40000000) == 0)
      stopEditing(false);
  }

  public void keyReleased(KeyEvent paramKeyEvent) {}

  public void keyTyped(KeyEvent paramKeyEvent) {}

  public void keyPressed(KeyEvent paramKeyEvent) {
    key(paramKeyEvent);
  }

  public void focusGained(FocusEvent paramFocusEvent) {}

  public void focusLost(FocusEvent paramFocusEvent) {
    stopEditing(true);
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if ((this.m_date & 0xD0000000) == 0 || this.m_edit == null)
      return;
    if ((this.m_date & Integer.MIN_VALUE) != 0) {
      int i = paramActionEvent.getModifiers();
      if ((i & 0xCFFFFFFF) != 0 && (i & 0x8FFFFFF) <= 134217728) {
        if (i == 16777216 && this.m_cal.getSelDatesSize() > 0)
          setDate(this.m_cal.dateAt(0));
        return;
      }
      stopEditing(true);
    } else {
      stopEditing(!(paramActionEvent.getSource() != this.m_edit[2]));
    }
    requestFocus();
  }
}
