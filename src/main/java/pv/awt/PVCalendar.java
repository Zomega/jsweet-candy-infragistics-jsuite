package pv.awt;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import pv.util.CalDay;
import pv.util.PVBorder;
import pv.util.PVDate0;
import pv.util.PVImage;
import pv.util.PVMultiLine;

public class PVCalendar extends Container implements Serializable {
  public static final int CUSTOM_LINE_PRESS = 128;

  public static final int CUSTOM_LINE_SELECTION = 256;

  public static final int INVALIDATE = 512;

  public static final int PAINT_EVENT = 513;

  public static final int RANGE = 8388608;

  public static final int ADD = 16777216;

  public static final int REMOVE = 33554432;

  public static final int MONTH_YEAR = 67108864;

  public static final int DOUBLE_CLICK = 134217728;

  public static final int ENTER = 268435456;

  public static final int ESCAPE = 536870912;

  public static final int TAB = 805306368;

  public static final int FOCUS_GAINED = 1073741824;

  public static final int FOCUS_LOST = -2147483648;

  Color m_cBo;

  Color m_cCal;

  Color m_cSel;

  Color m_c3D = Color.white;

  Color m_cSh = Color.gray;

  Color m_cSu = new Color(-4194304);

  Color m_cDay;

  Color m_cCSB = new Color(-4144897);

  Color m_cLSB = new Color(-16777088);

  Color m_cLB;

  Color m_cLF;

  Color m_cLSF = Color.white;

  Color m_cCX;

  Font m_fDOW = new Font("SansSerif", 2, 10);

  Font m_fList = new Font("SansSerif", 0, 12);

  Font m_fMY;

  Font m_fDay = new Font("SansSerif", 0, 10);

  String m_sDOW;

  Vector m_vMon;

  protected int m_cellBodr = 2;

  int m_iNum = 1;

  int m_iBodr = 13;

  int m_iBdrS = 12;

  int m_iBdrB = -1;

  int m_min;

  int m_max;

  int m_iFL;

  int m_0 = -1996140544;

  int m_1 = 67108864;

  int m_2 = -8388608;

  int m_3 = 1686175745;

  transient int m_4;

  transient Rectangle m_rE;

  transient int m_iCurY;

  transient int m_iCurM;

  transient int m_iMonW;

  transient int m_iButF;

  transient int m_iSelX;

  Vector m_vDay;

  Dimension m_size = new Dimension();

  public Calendar m_calendar;

  public transient CalDay m_focus;

  public transient int m_hit;

  protected transient int m_cellW;

  protected transient int m_cellH;

  transient int m_iDays;

  transient int m_iBefore;

  transient int m_iDow;

  transient int m_iCellsX;

  transient int m_iCellsY;

  transient int m_iW;

  transient int m_iH;

  transient int m_iCapH;

  transient int m_iLCellW;

  transient int m_iLCellH;

  transient int m_iL1st;

  transient int m_iLSel;

  transient int m_iLNum;

  transient int m_iLCols;

  transient int m_iLRows;

  transient int m_iCTop;

  transient int m_iCNum;

  transient int m_iC0;

  transient ActionListener m_aLs;

  protected transient Rectangle m_r;

  protected transient Vector m_vSel;

  transient Image m_img;

  transient Image m_im0;

  public transient Image[] m_imgs;

  transient Color m_c1;

  transient Color m_c2;

  transient Color m_c3;

  transient String m_tip;

  public PVCalendar() {
    setFont(new Font("SansSerif", 1, 12));
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
    if ((this.m_0 & Integer.MIN_VALUE) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0();
  }

  void init0() {
    enableEvents(60L);
    this.m_vSel = new Vector(1);
    this.m_rE = new Rectangle();
    this.m_r = new Rectangle();
    this.m_4 = 0;
    setLocaleStrings(getLocaleStrings());
    setLayout(null);
    setCurrentDate(getCurrentDate());
  }

  public void setLocale(Locale paramLocale) {
    super.setLocale(paramLocale);
    setLocaleStrings(getLocaleStrings());
  }

  public Locale getLocale() {
    try {
      return super.getLocale();
    } catch (Exception exception) {
      return Locale.getDefault();
    }
  }

  void initMon() {
    this.m_vMon = new Vector(12);
    for (byte b = 0; b < 12; b++)
      this.m_vMon.addElement(PVDate0.month(b, false, getLocaleStrings(), getLocale()));
  }

  void initDOW() {
    this.m_sDOW = "";
    for (byte b = 0; b < 7; b++)
      this.m_sDOW = String.valueOf(this.m_sDOW) + PVDate0.dow(b, true, getLocaleStrings(), getLocale()) + ((b < 6) ? " " : "");
  }

  public void removeNotify() {
    this.m_4 &= 0xFFFFFFEF;
    super.removeNotify();
  }

  public void addNotify() {
    super.addNotify();
    this.m_4 = 16;
  }

  public boolean isFocusTraversable() {
    return isEnabled();
  }

  public void setVisible(boolean paramBoolean) {
    this.m_4 &= 0x4000010;
    super.setVisible(paramBoolean);
  }

  public void eventCancel() {
    this.m_4 |= 0x8000000;
  }

  public int checkFocus() {
    return this.m_4 >> 9 & 0x1;
  }

  public int getActionMask() {
    return this.m_2 & 0xFF800380;
  }

  public void setActionMask(int paramInt) {
    this.m_2 = this.m_2 & 0x7FFC7F | paramInt & 0xFF800380;
  }

  public boolean getLocaleStrings() {
    return !((this.m_2 & 0x8000) == 0);
  }

  public void setLocaleStrings(boolean paramBoolean) {
    this.m_2 &= 0xFFFF7FFF;
    if (this.m_calendar == null || paramBoolean) {
      this.m_calendar = Calendar.getInstance(getLocale());
      byte b = 10;
      while (b++ < 14)
        this.m_calendar.set(b, 0);
    }
    if (paramBoolean) {
      this.m_4 &= 0xFBFFFFFF;
      this.m_2 |= 0x8000;
      this.m_sDOW = null;
      this.m_vMon = null;
      setMondayFirst(!(this.m_calendar.getFirstDayOfWeek() != 2));
      repaint();
    }
  }

  public boolean getMondayFirst() {
    return !((this.m_1 & Integer.MIN_VALUE) == 0);
  }

  public void setMondayFirst(boolean paramBoolean) {
    this.m_1 &= Integer.MAX_VALUE;
    if (paramBoolean)
      this.m_1 |= Integer.MIN_VALUE;
  }

  public int getFormat() {
    return this.m_2 >> 10 & 0x3;
  }

  public void setFormat(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFFF3FF | (paramInt & 0x3) << 10;
  }

  public int getLocationOfNumbers() {
    return ((this.m_2 & 0x10000) == 0) ? 0 : this.m_iFL;
  }

  public void setLocationOfNumbers(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFEFFFF | (((this.m_iFL = paramInt & 0x7F) > 0) ? 65536 : 0);
    this.m_4 &= 0xFBFFFFFF;
  }

  public boolean getIgnoreFocusWhileMouse() {
    return !((this.m_0 & 0x20) != 0);
  }

  public void setIgnoreFocusWhileMouse(boolean paramBoolean) {
    this.m_0 |= 0x20;
    if (paramBoolean)
      this.m_0 &= 0xFFFFFFDF;
  }

  public void setEnableRightMouse(boolean paramBoolean) {
    this.m_1 &= 0xBFFFFFFF;
    if (paramBoolean)
      this.m_1 |= 0x40000000;
  }

  public boolean getEnableRightMouse() {
    return !((this.m_1 & 0x40000000) == 0);
  }

  public boolean getCurrentDateButton() {
    return !((this.m_2 & 0x2000) != 0);
  }

  public void setCurrentDateButton(boolean paramBoolean) {
    this.m_2 |= 0x2000;
    if (paramBoolean)
      this.m_2 &= 0xFFFFDFFF;
    this.m_4 &= 0xFBFFFFFF;
  }

  public void setHideYearField(boolean paramBoolean) {
    this.m_2 &= 0xFFF7FFFF;
    if (paramBoolean)
      this.m_2 |= 0x80000;
  }

  public boolean getHideYearField() {
    return !((this.m_2 & 0x80000) == 0);
  }

  public void setEnableLists(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFFE7F | 3 - (paramInt & 0x3) << 7;
  }

  public int getEnableLists() {
    return 3 - (this.m_0 >> 7 & 0x3);
  }

  public boolean getMultiSelection() {
    return !((this.m_1 & 0x1000000) != 0);
  }

  public void setMultiSelection(boolean paramBoolean) {
    this.m_1 |= 0x1000000;
    if (paramBoolean)
      this.m_1 &= 0xFEFFFFFF;
  }

  public boolean getRangeSelection() {
    return !((this.m_1 & 0x2000000) != 0);
  }

  public void setRangeSelection(boolean paramBoolean) {
    this.m_1 &= 0xFDFFFFFF;
    if (!paramBoolean)
      this.m_1 |= 0x2000000;
  }

  public void setSelDatesRange(Date paramDate1, Date paramDate2) {
    if (paramDate1 != null && paramDate2 != null) {
      this.m_vSel.setSize(0);
      int i = pack0(paramDate1);
      this.m_vSel.addElement(new Integer(i));
      setSel(pack0(paramDate2), true);
    }
  }

  public Vector getSelDates() {
    return this.m_vSel;
  }

  public void setSelDates(Vector paramVector) {
    this.m_vSel.setSize(0);
    if (paramVector != null)
      for (byte b = 0; b < paramVector.size(); b++) {
        Integer integer = (Integer)paramVector.elementAt(b);
        if (integer instanceof Integer) {
          int i = ((Integer)integer).intValue();
          this.m_vSel.addElement(new Integer(i));
          if (b == 0) {
            this.m_iCurM = i >> 5 & 0xF;
            this.m_iCurY = i >>> 9 & 0x3FFF;
          }
        }
      }
    fireAction(16777216);
    repaint();
  }

  public int getSelDatesSize() {
    return this.m_vSel.size();
  }

  public boolean isSelDatesRange() {
    return (getSelDatesSize() != 2) ? false : (!((dateAt(0) & 0xC0000000) == 0));
  }

  public Date getSelDateAt(int paramInt) {
    return (paramInt < 0 || paramInt >= getSelDatesSize()) ? null : unpack0(dateAt(paramInt));
  }

  protected int dateAt(int paramInt) {
    return ((Integer)this.m_vSel.elementAt(paramInt)).intValue();
  }

  public void removeSelDateAt(int paramInt) {
    if (paramInt >= 0 && paramInt < getSelDatesSize()) {
      this.m_vSel.removeElementAt(paramInt);
      if (getSelDatesSize() == 1)
        this.m_vSel.setElementAt(new Integer(dateAt(0) & 0x7FFFFF), 0);
      fireAction(33554432);
      this.m_1 &= 0xFF800000;
      this.m_4 |= Integer.MIN_VALUE;
      paint0(null);
    }
  }

  public void addSelDate(Date paramDate) {
    if (paramDate != null)
      addToSel(pack0(paramDate), false);
  }

  public Date getSelDate() {
    return getSelDateAt(0);
  }

  public Date getDate() {
    return getSelDateAt(0);
  }

  public void setSelDate(Date paramDate) {
    int i = -1;
    if (paramDate != null && limits(i = pack0(paramDate)))
      return;
    setSel(i, false);
  }

  public void setDate(Date paramDate) {
    setSelDate(paramDate);
  }

  public void setCalendar(Calendar paramCalendar) {
    int i = -1;
    if (paramCalendar != null && limits(i = PVDate0.pack0(this.m_calendar = (Calendar)paramCalendar.clone())))
      return;
    setSel(i, false);
  }

  public Calendar getCalendar() {
    return (getSelDatesSize() == 0) ? null : PVDate0.unpack0(dateAt(0), this.m_calendar);
  }

  public void addCustomDate(int paramInt1, int paramInt2, Image paramImage, Color paramColor1, Color paramColor2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, Object paramObject) {
    if (this.m_vDay == null)
      this.m_vDay = new Vector();
    if (paramInt3 >= 0 && paramInt3 < 7) {
      paramInt3 = ++paramInt3 << 24;
      paramInt1 |= paramInt3;
      paramInt2 |= paramInt3;
    }
    if (paramBoolean3)
      paramInt1 |= 0x10000000;
    if (paramBoolean4)
      paramInt1 |= 0x20000000;
    if (paramBoolean2)
      paramInt1 |= 0x40000000;
    if (!paramBoolean1)
      paramInt1 |= Integer.MIN_VALUE;
    if (paramBoolean5)
      paramInt1 |= 0x8000000;
    CalDay calDay = new CalDay();
    calDay.m_d0 = paramInt1;
    calDay.m_d1 = paramInt2;
    calDay.m_im = paramImage;
    calDay.m_object = paramObject;
    calDay.m_back = paramColor1;
    calDay.m_fore = paramColor2;
    calDay.fix();
    this.m_vDay.addElement(calDay);
  }

  public void addCustomDate(int paramInt1, int paramInt2, Image paramImage, Color paramColor1, Color paramColor2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3) {
    addCustomDate(paramInt1, paramInt2, paramImage, paramColor1, paramColor2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramInt3, false, null);
  }

  public void addCustomDate(Date paramDate1, Date paramDate2, Image paramImage, Color paramColor1, Color paramColor2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt) {
    addCustomDate(pack0(paramDate1), pack0(paramDate2), paramImage, paramColor1, paramColor2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramInt, false, null);
  }

  public void addCustomDate(Date paramDate, Image paramImage, Color paramColor1, Color paramColor2) {
    addCustomDate(pack0(paramDate), 0, paramImage, paramColor1, paramColor2, true, false, false, false, -1, false, null);
  }

  public void addCustomDate(Date paramDate, Image paramImage, boolean paramBoolean) {
    addCustomDate(pack0(paramDate), 0, paramImage, null, null, false, false, false, false, -1, paramBoolean, null);
  }

  public void addCustomDate(Date paramDate, Color paramColor, Object paramObject) {
    addCustomDate(pack0(paramDate), 0, null, paramColor, null, true, false, false, false, -1, false, paramObject);
  }

  public void addCustomDate(int paramInt1, int paramInt2, Image paramImage, Color paramColor1, Color paramColor2) {
    addCustomDate(paramInt1 & 0x1F | (paramInt2 & 0xF) << 5, 0, paramImage, paramColor1, paramColor2, true, false, true, false, -1, false, null);
  }

  public void addCustomDate(int paramInt, Image paramImage, Color paramColor1, Color paramColor2) {
    addCustomDate(1, 31, paramImage, paramColor1, paramColor2, true, false, true, true, paramInt, false, null);
  }

  public Vector getCustomDates() {
    return this.m_vDay;
  }

  public void removeCustomDateAt(int paramInt) {
    this.m_vDay.removeElementAt(paramInt);
  }

  public static int pack(Date paramDate) {
    return PVDate0.pack(paramDate);
  }

  public static Date unpack(int paramInt) {
    return PVDate0.unpack(paramInt);
  }

  public int pack0(Date paramDate) {
    return PVDate0.pack(paramDate, this.m_calendar);
  }

  public Date unpack0(int paramInt) {
    return PVDate0.unpack(paramInt, this.m_calendar);
  }

  boolean isSel(int paramInt) {
    int i = 0;
    int j = getSelDatesSize();
    while (j-- > 0) {
      if (((i = dateAt(j)) & 0xC0000000) != 0)
        return !(paramInt > (i & 0x7FFFFF) || paramInt < (dateAt(0) & 0x7FFFFF));
      if (paramInt == i)
        return true;
    }
    return false;
  }

  public CalDay calDay(int paramInt) {
    byte b = (this.m_vDay == null) ? 0 : this.m_vDay.size();
    CalDay calDay = null;
    while (b-- > 0) {
      calDay = this.m_vDay.elementAt(b);
      int k = paramInt;
      int i = calDay.m_d0;
      int j = calDay.m_d1;
      if ((i & 0x7000000) == 0)
        k &= 0x7FFFFF;
      if ((i & 0x20000000) != 0) {
        i &= 0xFF7FFE1F;
        k &= 0x77FFE1F;
        j &= 0x77FFE1F;
      }
      if ((i & 0x10000000) != 0) {
        i &= 0xFF0001FF;
        k &= 0x70001FF;
        j &= 0x70001FF;
      }
      i &= 0x7FFFFFF;
      if (j == 0)
        j = i;
      if (k >= i && k <= j)
        return calDay;
    }
    return null;
  }

  boolean setSel(int paramInt, boolean paramBoolean) {
    if (!getRangeSelection())
      paramBoolean = false;
    int i = 0;
    int j = 0;
    int k = getSelDatesSize();
    Integer integer = null;
    if (paramInt == -1) {
      if (k == 0)
        return false;
      fireAction(0x2000000 | dateAt(0) & 0x7FFFFF);
      if ((this.m_4 & 0x8000000) != 0)
        return false;
      this.m_vSel.setSize(0);
      this.m_1 &= 0xFF800000;
      this.m_4 |= Integer.MIN_VALUE;
      fireAction(33554432);
    } else {
      j = paramInt >> 5 & 0xF;
      i = paramInt >> 9 & 0x3FFF;
      if (this.m_iCurY == i && this.m_iCurM / this.m_iNum == j / this.m_iNum)
        i = 0;
      if ((this.m_1 & 0x7FFFFF) == paramInt && (paramBoolean || k == 1)) {
        if (i == 0)
          return false;
      } else {
        if (limits(paramInt))
          return false;
        fireAction(0x1000000 | paramInt);
        if ((this.m_4 & 0x8000000) != 0)
          return false;
        integer = new Integer(paramInt);
      }
    }
    if (integer != null) {
      this.m_0 &= 0xFFFFFFE0;
      int m = this.m_1 & 0x7FFFE0;
      if (m == (paramInt & 0x7FFFE0)) {
        int n = (this.m_1 & 0x7FFFFF) - (paramInt & 0x7FFFFF);
        if (k < 2) {
          this.m_4 |= 0x20000000;
          if (!paramBoolean)
            this.m_0 |= this.m_1 & 0x1F;
        } else if (k == 2) {
          if (paramBoolean && n <= 1 && n >= -1) {
            this.m_0 |= this.m_1 & 0x1F;
            this.m_4 |= 0x20000000;
          } else if ((dateAt(1) & 0x7FFFE0) == m && (dateAt(0) & 0x7FFFE0) == m) {
            this.m_4 |= 0x20000000;
          } else {
            m = 0;
          }
        } else {
          m = 0;
        }
      } else {
        m = 0;
      }
      if (m == 0)
        this.m_4 |= Integer.MIN_VALUE;
      this.m_1 = this.m_1 & 0xFF800000 | paramInt;
      if (k == 0) {
        this.m_vSel.addElement(integer);
      } else {
        m = dateAt(0);
        if (paramBoolean && k > 1 && (m & 0xC0000000) == 0)
          paramBoolean = false;
        if (paramBoolean) {
          if (k == 1)
            m |= Integer.MIN_VALUE;
          paramInt |= 0x40000000;
          if ((m & Integer.MIN_VALUE) == 0)
            m = dateAt(1);
          if ((m & 0x7FFFFF) > (paramInt & 0x7FFFFF)) {
            int n = m;
            m = paramInt;
            paramInt = n;
          }
          this.m_vSel.setElementAt(new Integer(m), 0);
          this.m_vSel.insertElementAt(new Integer(paramInt), 1);
          this.m_vSel.setSize(2);
        } else {
          if (k > 1) {
            this.m_vSel.setSize(1);
            paramInt = -1;
          }
          if (m == paramInt) {
            paramInt = 0;
          } else {
            this.m_vSel.setElementAt(integer, 0);
          }
        }
      }
      fireAction(16777216);
    }
    if (i != 0) {
      this.m_4 |= 0x90000000;
      this.m_iCurY = i;
      this.m_iCurM = j;
      fireAction(67108864);
    }
    this.m_focus = null;
    this.m_4 &= 0xFFE07FFF;
    paint0(null);
    return true;
  }

  boolean addToSel(int paramInt, boolean paramBoolean) {
    if (!getMultiSelection())
      return setSel(paramInt, false);
    if (limits(paramInt))
      return false;
    this.m_0 = this.m_0 & 0xFFFFFFE0 | paramInt & 0x1F;
    int i = getSelDatesSize();
    if (i == 2) {
      int j = dateAt(1);
      if ((j & 0xC0000000) != 0) {
        j &= 0x7FFFFF;
        if (j > paramInt && j - paramInt >= 384)
          j = paramInt & 0x7FFE00 | 0x17F;
        i = dateAt(0) & 0x7FFFFF;
        if (i < paramInt && paramInt - i >= 384)
          i = paramInt & 0x7FFE00 | 0x21;
        this.m_vSel.setSize(0);
        int k = i & 0x7FFFE0 | PVDate0.maxDay(this.m_iCurY, i >> 5 & 0xF);
        while (i <= j) {
          this.m_vSel.addElement(new Integer(i++));
          if (i > k) {
            k = (k >> 5 & 0xF) + 1;
            i = i & 0x7FFE00 | k << 5 | 0x1;
            k = i & 0x7FFFE0 | PVDate0.maxDay(this.m_iCurY, k);
          }
        }
        i = getSelDatesSize();
      }
    }
    while (i > 0) {
      int j = dateAt(--i);
      if (paramInt == j) {
        if (paramBoolean) {
          fireAction(0x2000000 | paramInt);
          if ((this.m_4 & 0x8000000) != 0)
            return false;
          this.m_vSel.removeElementAt(i);
        } else {
          return false;
        }
        i = -1;
        continue;
      }
      if (paramInt > j) {
        i++;
        break;
      }
    }
    if (i >= 0) {
      fireAction(0x1000000 | paramInt);
      if ((this.m_4 & 0x8000000) != 0)
        return false;
      this.m_vSel.insertElementAt(new Integer(paramInt), i);
    }
    this.m_iCurY = paramInt >> 9 & 0x3FFF;
    this.m_iCurM = paramInt >> 5 & 0xF;
    fireAction((i >= 0) ? 16777216 : 33554432);
    this.m_1 = this.m_1 & 0xFF80001F | paramInt & 0x7FFFE0;
    if ((paramInt & 0x7FFFE0) == (this.m_1 & 0x7FFFE0))
      this.m_4 |= 0x20000000;
    paint0(null);
    return true;
  }

  public int getMonth() {
    return this.m_iCurM;
  }

  public void setMonth(int paramInt) {
    if (paramInt >= 0 && paramInt <= 11) {
      this.m_iCurM = paramInt;
      repaint();
    }
  }

  public int getYear() {
    return this.m_iCurY;
  }

  public void setYear(int paramInt) {
    if (paramInt >= 1 && paramInt <= 9999) {
      this.m_iCurY = paramInt;
      repaint();
    }
  }

  public void setBackColor(Color paramColor) {
    setBackground(paramColor);
  }

  public Color getBackColor() {
    Color color = getBackground();
    return (color == null) ? Color.lightGray : color;
  }

  public void setText3DColor(Color paramColor) {
    this.m_c3D = paramColor;
    repaint();
  }

  public Color getText3DColor() {
    return (this.m_c3D == null) ? getBackColor() : this.m_c3D;
  }

  public void setBorderColor(Color paramColor) {
    if (paramColor != getBorderColor())
      this.m_cBo = paramColor;
    repaint();
  }

  public Color getBorderColor() {
    return (this.m_cBo == null) ? getBackColor() : this.m_cBo;
  }

  public void setShadowColor(Color paramColor) {
    this.m_cSh = paramColor;
    repaint();
  }

  public Color getShadowColor() {
    return (this.m_cSh == null) ? Color.gray : this.m_cSh;
  }

  public void setSelCellColor(Color paramColor) {
    if (paramColor != getSelCellColor())
      this.m_cSel = paramColor;
    repaint();
  }

  public Color getSelCellColor() {
    Color color = this.m_cSel;
    if (color == null) {
      if (this.m_c3 == null)
        doCol();
      color = this.m_c3;
    }
    return color;
  }

  public void setCalendarColor(Color paramColor) {
    if (paramColor != getCalendarColor()) {
      this.m_cCal = paramColor;
      this.m_c3 = null;
      repaint();
    }
  }

  public Color getCalendarColor() {
    return (this.m_cCal == null) ? getBackColor() : this.m_cCal;
  }

  public void setSundayColor(Color paramColor) {
    this.m_cSu = paramColor;
    repaint();
  }

  public Color getSundayColor() {
    return (this.m_cSu == null) ? getForeground() : this.m_cSu;
  }

  public void setScrollBarColor(Color paramColor) {
    if (paramColor != getScrollBarColor())
      this.m_cCX = paramColor;
  }

  public Color getScrollBarColor() {
    return (this.m_cCX == null) ? getSelCellColor() : this.m_cCX;
  }

  public void setScrollBarWidth(int paramInt) {
    this.m_0 = this.m_0 & 0xF0FFFFFF | (paramInt & 0xF) << 24;
  }

  public int getScrollBarWidth() {
    return this.m_0 >> 24 & 0xF;
  }

  public void setScrollBarBorder(int paramInt) {
    this.m_iBdrS = paramInt;
  }

  public int getScrollBarBorder() {
    return this.m_iBdrS;
  }

  public void setDrawCustomText(boolean paramBoolean) {
    this.m_2 |= 0x400000;
    if (paramBoolean)
      this.m_2 &= 0xFFBFFFFF;
  }

  public boolean getDrawCustomText() {
    return !((this.m_2 & 0x400000) != 0);
  }

  public void setCustomFlags(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    setCustomFlags(paramInt1 & 0x3F | (paramInt2 & 0x3) << 13 | (paramInt3 & 0x3) << 15 | (paramBoolean1 ? 131072 : 0) | (paramBoolean2 ? 0 : 262144) | (paramBoolean3 ? 524288 : 0));
  }

  public void setCustomFlags(int paramInt) {
    if ((this.m_3 & 0x700000) != 0)
      paramInt = (paramInt | 0x80000) & 0xFFFF7FFF;
    this.m_3 = this.m_3 & 0xFFF00F00 | paramInt & 0xFF0BF;
    if ((paramInt & 0x3F) > 5)
      this.m_3 |= 0x40;
    fix();
  }

  public int getCustomFlags() {
    return this.m_3 & 0xFF0BF;
  }

  public void setCustomForeground(Color paramColor) {
    if (paramColor != getCustomForeground())
      this.m_cDay = paramColor;
    repaint();
  }

  public Color getCustomForeground() {
    return (this.m_cDay == null) ? getForeground() : this.m_cDay;
  }

  public void setListSelectionBackground(Color paramColor) {
    if (paramColor != getListSelectionBackground())
      this.m_cLSB = paramColor;
  }

  public Color getListSelectionBackground() {
    return (this.m_cLSB == null) ? getListBackground() : this.m_cLSB;
  }

  public void setListSelectionForeground(Color paramColor) {
    if (paramColor != getListSelectionForeground())
      this.m_cLSF = paramColor;
  }

  public Color getListSelectionForeground() {
    return (this.m_cLSF == null) ? getListForeground() : this.m_cLSF;
  }

  public void setListBackground(Color paramColor) {
    if (paramColor != getListBackground())
      this.m_cLB = paramColor;
  }

  public Color getListBackground() {
    return (this.m_cLB == null) ? Color.white : this.m_cLB;
  }

  public void setListForeground(Color paramColor) {
    if (paramColor != getListForeground())
      this.m_cLF = paramColor;
  }

  public Color getListForeground() {
    return (this.m_cLF == null) ? Color.black : this.m_cLF;
  }

  public void setCustomSelectionBackground(Color paramColor) {
    if (paramColor != getCustomSelectionBackground())
      this.m_cCSB = paramColor;
  }

  public Color getCustomSelectionBackground() {
    return (this.m_cCSB == null) ? getListSelectionBackground() : this.m_cCSB;
  }

  public int getCustomSelectionStyle() {
    return this.m_3 >> 20 & 0x7;
  }

  public void setCustomSelectionStyle(int paramInt) {
    this.m_3 &= 0xFF8FFFFF;
    if (paramInt != 0)
      this.m_3 = (this.m_3 | 0x80000 | (paramInt & 0x7) << 20) & 0xFFFF7FFF;
  }

  public boolean getShowTips() {
    return !((this.m_3 & 0x800000) == 0);
  }

  public void setShowTips(boolean paramBoolean) {
    this.m_3 &= 0xFF7FFFFF;
    if (paramBoolean)
      this.m_3 |= 0x800000;
  }

  public int checkCustomState() {
    return this.m_4 >> 14 & 0x3F;
  }

  public void setCurrentDate(boolean paramBoolean) {
    this.m_0 |= 0x40;
    if (paramBoolean) {
      this.m_0 &= 0xFFFFFFBF;
      setSelDate(new Date());
    }
  }

  public boolean getCurrentDate() {
    return !((this.m_0 & 0x40) != 0);
  }

  public boolean getAureole() {
    return !((this.m_1 & 0x20000000) == 0);
  }

  public void setAureole(boolean paramBoolean) {
    this.m_1 &= 0xDFFFFFFF;
    if (paramBoolean)
      this.m_1 |= 0x20000000;
    repaint();
  }

  public String getDOW() {
    if (this.m_sDOW == null)
      initDOW();
    return this.m_sDOW;
  }

  public void setDOW(String paramString) {
    if (paramString != null)
      this.m_sDOW = paramString;
    this.m_4 &= 0xFBFFFFFF;
  }

  public String getMonthString(int paramInt) {
    if (this.m_vMon == null)
      initMon();
    return (paramInt < 0 || paramInt > 11) ? "" : this.m_vMon.elementAt(paramInt);
  }

  public void setMonthString(int paramInt, String paramString) {
    if (this.m_vMon == null)
      initMon();
    if (paramString != null)
      this.m_vMon.setElementAt(paramString, paramInt);
    this.m_4 &= 0xFBFFFFFF;
  }

  public Font getDOWFont() {
    return (this.m_fDOW == null) ? getFont() : this.m_fDOW;
  }

  public void setDOWFont(Font paramFont) {
    this.m_fDOW = paramFont;
    this.m_4 &= 0xFBFFFFFF;
  }

  public Font getMonthYearFont() {
    return (this.m_fMY == null) ? getFont() : this.m_fMY;
  }

  public void setMonthYearFont(Font paramFont) {
    if (paramFont != getMonthYearFont())
      this.m_fMY = paramFont;
    this.m_4 &= 0xFBFFFFFF;
  }

  public Font getListFont() {
    return (this.m_fList == null) ? getFont() : this.m_fList;
  }

  public void setListFont(Font paramFont) {
    this.m_fList = paramFont;
  }

  public void setCustomFont(Font paramFont) {
    this.m_fDay = paramFont;
    fix();
  }

  public Font getCustomFont() {
    return (this.m_fDay == null) ? getDOWFont() : this.m_fDay;
  }

  public int getMonthYearBorder() {
    return this.m_iBdrB;
  }

  public void setMonthYearBorder(int paramInt) {
    this.m_iBdrB = paramInt;
    repaint();
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    this.m_4 &= 0xFBFFFFFF;
  }

  public int getCellBorderWidth() {
    return this.m_cellBodr;
  }

  public void setCellBorderWidth(int paramInt) {
    this.m_cellBodr = paramInt & 0x7;
    this.m_4 &= 0xFBFFFFFF;
  }

  public int getShadow() {
    return this.m_0 >> 9 & 0x7;
  }

  public void setShadow(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFF1FF | (paramInt & 0x7) << 9;
    this.m_4 &= 0xFBFFFFFF;
  }

  public int getShadowWidth() {
    return this.m_0 >> 12 & 0xF;
  }

  public void setShadowWidth(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFF0FFF | (paramInt & 0xF) << 12;
    this.m_4 &= 0xFBFFFFFF;
  }

  public int getYearListCount() {
    return this.m_3 >>> 25;
  }

  public void setYearListCount(int paramInt) {
    this.m_3 = this.m_3 & 0x1FFFFFF | (paramInt & 0x7F) << 25;
  }

  public int getHorizontal3D() {
    return ((this.m_0 & 0x10000) == 0) ? 0 : ((this.m_0 >> 16 & 0x3) - 2);
  }

  public void setHorizontal3D(int paramInt) {
    if (paramInt > 0)
      paramInt = 3;
    if (paramInt < 0)
      paramInt = 1;
    this.m_0 = this.m_0 & 0xFFFCFFFF | paramInt << 16;
  }

  public int getVertical3D() {
    return ((this.m_0 & 0x40000) == 0) ? 0 : ((this.m_0 >> 18 & 0x3) - 2);
  }

  public void setVertical3D(int paramInt) {
    if (paramInt > 0)
      paramInt = 3;
    if (paramInt < 0)
      paramInt = 1;
    this.m_0 = this.m_0 & 0xFFF3FFFF | paramInt << 18;
  }

  public void setText3D(int paramInt1, int paramInt2) {
    setHorizontal3D(paramInt1);
    setVertical3D(paramInt2);
  }

  public int getSharpness3D() {
    return this.m_1 >> 26 & 0x3;
  }

  public void setSharpness3D(int paramInt) {
    this.m_1 = this.m_1 & 0xF3FFFFFF | (paramInt & 0x3) << 26;
  }

  public boolean getSolidTriangle() {
    return !((this.m_1 & 0x10000000) == 0);
  }

  public void setSolidTriangle(boolean paramBoolean) {
    this.m_1 &= 0xEFFFFFFF;
    if (paramBoolean)
      this.m_1 |= 0x10000000;
  }

  public Dimension getCellDimension() {
    return new Dimension(this.m_cellW, this.m_cellH);
  }

  public boolean getInvertedSelection() {
    return !((this.m_0 & 0x400000) != 0);
  }

  public void setInvertedSelection(boolean paramBoolean) {
    this.m_0 |= 0x400000;
    if (paramBoolean)
      this.m_0 &= 0xFFBFFFFF;
  }

  public boolean getIndentCellBorder() {
    return !((this.m_0 & 0x800000) != 0);
  }

  public void setIndentCellBorder(boolean paramBoolean) {
    this.m_0 |= 0x800000;
    if (paramBoolean)
      this.m_0 &= 0xFF7FFFFF;
  }

  public boolean getAutoResize() {
    return !((this.m_2 & 0x100000) != 0);
  }

  public void setAutoResize(boolean paramBoolean) {
    this.m_2 |= 0x100000;
    if (paramBoolean)
      this.m_2 &= 0xFFEFFFFF;
    this.m_4 &= 0xFBFFFFFF;
  }

  public boolean getDisplaySelDate() {
    return !((this.m_2 & 0x4000) != 0);
  }

  public void setDisplaySelDate(boolean paramBoolean) {
    this.m_2 |= 0x4000;
    if (paramBoolean)
      this.m_2 &= 0xFFFFBFFF;
    this.m_4 &= 0xFBFFFFFF;
  }

  public int getNumberOfMonths() {
    return this.m_iNum;
  }

  public void setNumberOfMonths(int paramInt) {
    if (paramInt < 3) {
      paramInt = 1;
    } else if (paramInt < 6) {
      paramInt = 3;
    } else if (paramInt < 12) {
      paramInt = 6;
    } else {
      paramInt = 12;
    }
    this.m_iNum = paramInt;
    this.m_4 &= 0xFBFFFFFF;
  }

  public void setLowerLimit(Date paramDate) {
    this.m_min = pack0(paramDate);
  }

  public Date getLowerLimit() {
    return unpack0(this.m_min);
  }

  public void setUpperLimit(Date paramDate) {
    this.m_max = pack0(paramDate);
  }

  public Date getUpperLimit() {
    return unpack0(this.m_max);
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_2 &= 0xFFFFEFFF;
    if (paramDimension != null) {
      this.m_2 |= 0x1000;
      this.m_size = paramDimension;
    }
  }

  public Dimension getPreferredSize() {
    Dimension dimension = super.getSize();
    if (dimension.width < 2)
      dimension.width = 260;
    if (dimension.height < 2)
      dimension.height = 180;
    int i = dimension.width;
    int j = dimension.height;
    if (i < 100)
      i = 100;
    if (j < 100)
      j = 100;
    this.m_r = new Rectangle(0, 0, i, j);
    PVBorder.shadow(null, this.m_r, null, null, getShadow(), getShadowWidth());
    PVBorder.draw(null, this.m_r, this.m_iBodr, null);
    int k = this.m_r.height;
    int m = this.m_r.width;
    FontMetrics fontMetrics = getFontMetrics(getMonthYearFont());
    int i2 = 20;
    int i3 = fontMetrics.getHeight() + 6 | 0x1;
    this.m_rE.height = i3;
    this.m_iButF = fontMetrics.getAscent() + 3;
    int n;
    for (n = 0; n < 12; n++) {
      int i5;
      if ((i5 = fontMetrics.stringWidth(getMonthString(n))) > i2)
        i2 = i5;
    }
    n = fontMetrics.stringWidth("00997");
    i2 += n / 3 + i3 - 1;
    int i4 = n + i3;
    fontMetrics = getFontMetrics(getDOWFont());
    this.m_iCapH = fontMetrics.getHeight();
    if (this.m_iNum > 1)
      this.m_iCapH <<= 1;
    fontMetrics = getFontMetrics(getFont());
    this.m_cellW = (this.m_r.width - this.m_cellBodr - 2) / 7;
    if (this.m_iNum > 1)
      this.m_cellW /= 3;
    this.m_cellH = ((this.m_r.height - i3) / (this.m_iNum + 2) / 3 - this.m_iCapH - 1 - this.m_cellBodr) / 6;
    n = this.m_cellBodr + fontMetrics.stringWidth("20");
    if ((this.m_2 & 0x10000) == 0 || this.m_iFL < 1)
      this.m_iFL = fontMetrics.getAscent() * 8 / 9;
    int i1 = this.m_cellBodr + 1 + this.m_iFL;
    if (getAureole()) {
      n++;
      i1++;
    }
    n += 3;
    i1 += 2;
    if (this.m_cellW < n)
      this.m_cellW = n;
    if (this.m_cellH < i1)
      this.m_cellH = i1;
    i1 = this.m_cellW * ((this.m_iNum > 1) ? 12 : 4);
    if (i2 > i1)
      i2 = i1;
    i1 = this.m_cellW * ((this.m_iNum > 1) ? 9 : 3);
    if (i4 > i1)
      i4 = i1;
    this.m_iMonW = i2;
    this.m_rE.width = i4 + 1;
    this.m_rE.x = this.m_r.x + 4 + i2;
    this.m_r.y += 2;
    this.m_iCellsX = 1;
    this.m_iCellsY = this.m_iCapH;
    this.m_r.y += i3;
    this.m_iW = this.m_cellW * 7 + this.m_cellBodr;
    this.m_iH = this.m_cellH * 6 + this.m_cellBodr;
    this.m_r.width = (this.m_iW + 2) * ((this.m_iNum > 1) ? 3 : 1);
    this.m_r.height = (this.m_iH + this.m_iCapH) * (this.m_iNum + 2) / 3;
    j += this.m_r.height + this.m_rE.height + 1 - k;
    i += this.m_r.width - m;
    if (getDisplaySelDate()) {
      m = this.m_r.width - getFontMetrics(getDOWFont()).stringWidth("Wad 30, 2089") - 2;
      k = this.m_iMonW + this.m_rE.width + 4;
      if (getCurrentDateButton())
        k += this.m_rE.height + 2;
      if (m >= k) {
        if (m - k > 5)
          m -= 2;
      } else {
        m = 0;
      }
      this.m_iSelX = m;
    }
    this.m_4 |= 0x4000000;
    if ((this.m_2 & 0x1000) == 0) {
      this.m_size.width = i;
      this.m_size.height = j;
      if ((this.m_4 & 0x20) == 0 || getAutoResize()) {
        dimension.width = i;
        dimension.height = j;
      }
    } else {
      dimension = this.m_size;
    }
    fix();
    return dimension;
  }

  public Dimension getSize() {
    Dimension dimension = super.getSize();
    if ((this.m_4 & 0x4000000) == 0 && dimension.width > 2 && dimension.height > 2) {
      getPreferredSize();
      if (getAutoResize() && !dimension.equals(this.m_size))
        setSize(dimension = this.m_size);
    }
    return dimension;
  }

  public void invalidate() {
    if ((this.m_4 & 0x10) != 0) {
      Dimension dimension = super.getSize();
      if (!dimension.equals(this.m_size))
        this.m_4 &= 0xFBFFFFFF;
    }
    fireAction(512);
  }

  protected void drawButtons(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = 0;
    if (paramInt5 != 0) {
      paramGraphics.setColor(getBackColor());
      paramGraphics.fillRect(paramInt5 - 1, 0, this.m_r.width - paramInt5, paramInt1);
      if (getSelDatesSize() > 0) {
        String str1;
        if (getLocaleStrings()) {
          str1 = DateFormat.getDateInstance(2, getLocale()).format(getSelDate());
        } else {
          i = dateAt(0);
          str1 = getMonthString(i >> 5 & 0xF);
          if (str1.length() > 2)
            str1 = str1.substring(0, 3);
          String str2 = ((this.m_2 & 0x800) == 0) ? " " : "-";
          int j = i & 0x1F;
          if ((this.m_2 & 0x400) == 0) {
            str1 = String.valueOf(str1) + str2 + j;
          } else {
            str1 = String.valueOf(j) + str2 + str1;
          }
          if ((this.m_2 & 0x800) == 0)
            str1 = String.valueOf(str1) + ",";
          str1 = String.valueOf(str1) + str2 + (i >> 9 & 0x3FFF);
        }
        paramGraphics.setFont(getDOWFont());
        paramGraphics.setColor(getText3DColor());
        paramGraphics.drawString(str1, paramInt5 + 1, paramInt2);
        paramGraphics.setColor(getForeground());
        paramGraphics.drawString(str1, paramInt5, paramInt2 - 1);
      }
    }
    if ((this.m_4 & 0xA0000000) == 536870912)
      return;
    Color color2 = this.m_c1;
    Color color3 = this.m_c2;
    Color color1 = getCalendarColor();
    paramGraphics.setColor(color1);
    paramGraphics.fillRect(2, 2, paramInt3, paramInt1 - 1);
    boolean bool2 = ((this.m_4 & 0x80) == 0) ? false : true;
    i = paramInt1 - 7 | 0x1;
    paramInt5 = getSolidTriangle() ? 5 : 1;
    if (getImage(0) != null) {
      PVImage.center(paramGraphics, null, getImage(0), 5, 5, i, i, 100, 9);
    } else {
      PVImage.center(paramGraphics, bool2 ? color3 : color2, bool2 ? color2 : color3, 5, 5, i, i, 20 - paramInt5, paramInt5);
    }
    paramGraphics.setFont(getMonthYearFont());
    FontMetrics fontMetrics = paramGraphics.getFontMetrics();
    String str = getMonthString(this.m_iCurM / this.m_iNum * this.m_iNum);
    drawStr(paramGraphics, str, getForeground(), 1 + i + (paramInt3 - i + fontMetrics.stringWidth(str)) / 2, paramInt2, getText3DColor(), bool2);
    if (this.m_iBdrB != -1) {
      PVBorder.draw(paramGraphics, new Rectangle(2, 2, paramInt3, paramInt1 - 1), this.m_iBdrB, color1, bool2);
    } else {
      bdr(paramGraphics, true, bool2, 2, 2, paramInt3 + 1, paramInt1);
    }
    paramInt3 += 5;
    bool2 = ((this.m_4 & 0x40) == 0) ? false : true;
    boolean bool1;
    paramGraphics.setColor((bool1 = (bool2 && !getHideYearField()) ? false : true) ? color1 : getListBackground());
    paramGraphics.fillRect(paramInt3, 2, paramInt4, paramInt1 - 1);
    if (bool1)
      if (getImage(1) != null) {
        PVImage.center(paramGraphics, null, getImage(1), paramInt3 + 3, 5, i, i, 100, 9);
      } else {
        PVImage.center(paramGraphics, bool2 ? color3 : color2, bool2 ? color2 : color3, paramInt3 + 3, 5, i, i, 20 - paramInt5, paramInt5);
      }
    if ((paramInt5 = this.m_iC0) < 0 || bool1)
      paramInt5 = this.m_iCurY;
    if (paramInt5 > 0) {
      str = String.valueOf(paramInt5);
      paramInt5 = fontMetrics.stringWidth(str);
      if (bool1) {
        drawStr(paramGraphics, str, getForeground(), paramInt3 - 1 + i + (paramInt4 - i + paramInt5) / 2, paramInt2 + 1, getText3DColor(), bool2);
      } else {
        int j = paramInt3 - 1 + (paramInt4 - paramInt5) / 2;
        if (this.m_iC0 < 0) {
          paramGraphics.setColor(getListSelectionBackground());
          paramGraphics.fillRect(j, 5, paramInt5, paramInt1 - 7);
        }
        paramGraphics.setColor((this.m_iC0 < 0) ? getListSelectionForeground() : getListForeground());
        paramGraphics.drawString(str, j, paramInt2 + 1);
      }
    }
    if (this.m_iBdrB != -1) {
      PVBorder.draw(paramGraphics, new Rectangle(paramInt3, 2, paramInt4, paramInt1 - 1), this.m_iBdrB, color1, bool2);
    } else {
      bdr(paramGraphics, true, bool2, paramInt3, 2, paramInt3 + paramInt4 - 1, paramInt1);
    }
    paramInt4 += 3 + paramInt3;
    i = paramInt4 + paramInt1 - 2;
    if (getCurrentDateButton() && this.m_r.width >= i) {
      paramGraphics.setColor(color1);
      paramGraphics.fillRect(paramInt4, 2, paramInt1 - 1, paramInt1 - 1);
      int j = 4;
      if ((paramInt5 = paramInt1 - 10) > 10) {
        j += (paramInt5 - 10) / 2;
        paramInt5 = 10;
      }
      if (getImage(2) != null) {
        PVImage.center(paramGraphics, null, getImage(2), paramInt4 + 3, 5, paramInt1 - 7, paramInt1 - 7, 100, 9);
      } else {
        j++;
        paramGraphics.setColor(color2);
        paramGraphics.drawOval(paramInt4 + j, j + 2, paramInt5, paramInt5);
        paramGraphics.setColor(color3);
        paramGraphics.drawOval(paramInt4 + j - 1, j + 1, paramInt5, paramInt5);
        paramGraphics.setColor(color1);
        paramGraphics.fillOval(paramInt4 + j, j + 2, --paramInt5, paramInt5);
        paramGraphics.drawOval(paramInt4 + j, j + 2, paramInt5, paramInt5);
      }
      if (this.m_iBdrB != -1) {
        PVBorder.draw(paramGraphics, new Rectangle(paramInt4, 2, paramInt1 - 1, paramInt1 - 1), this.m_iBdrB, color1);
        return;
      }
      bdr(paramGraphics, true, false, paramInt4, 2, paramInt4 + paramInt1 - 2, paramInt1);
    }
  }

  void bdr(Graphics paramGraphics, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramGraphics.setColor(paramBoolean2 ? this.m_c1 : this.m_c2);
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt3, paramInt2);
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt4);
    if (paramInt1 > 0 || this.m_cellBodr > 1) {
      if (paramBoolean1)
        paramGraphics.setColor(paramBoolean2 ? this.m_c2 : this.m_c1);
      paramGraphics.drawLine(paramInt1, paramInt4, paramInt3, paramInt4);
      paramGraphics.drawLine(paramInt3, paramInt2, paramInt3, paramInt4);
    }
  }

  void setFirstLastDays(int paramInt) {
    Calendar calendar = (Calendar)this.m_calendar.clone();
    calendar.set(this.m_iCurY, paramInt, 1);
    int i = calendar.get(7) - 1;
    if (getMondayFirst())
      i += 6;
    i %= 7;
    if (i == 0 && this.m_iNum == 1)
      i = 7;
    this.m_iDow = i;
    this.m_iBefore = PVDate0.maxDay(this.m_iCurY, paramInt - 1);
    this.m_iDays = PVDate0.maxDay(this.m_iCurY, paramInt);
  }

  public void setImages(Image paramImage1, Image paramImage2, Image paramImage3) {
    this.m_imgs = new Image[] { paramImage1, paramImage2, paramImage3 };
  }

  public Image getImage(int paramInt) {
    return (this.m_imgs == null) ? null : this.m_imgs[paramInt];
  }

  public void shiftDate(int paramInt, boolean paramBoolean) {
    int i = getSelDatesSize() - 1;
    if (isListOpened() || i < 0)
      return;
    int j = dateAt(i);
    if (i > 0)
      if ((j & 0xC0000000) == 0) {
        paramBoolean = false;
      } else if ((j & Integer.MIN_VALUE) != 0) {
        j = dateAt(0);
      }
    i = j & 0x1F;
    int k = this.m_iCurY = j >>> 9 & 0x3FFF;
    int m = j = this.m_iCurM = j >> 5 & 0xF;
    setFirstLastDays(m);
    if (paramInt < -28 || paramInt > 28) {
      m += (paramInt > 28) ? 1 : -1;
      if (i > 28 && m == 1)
        i = PVDate0.maxDay(k, 1);
      if (i > 30)
        i = 30;
    } else if ((i += paramInt) < 1) {
      i += this.m_iBefore;
      m--;
    } else if (i > this.m_iDays) {
      i -= this.m_iDays;
      m++;
    }
    if (m < j && m < 0) {
      m = 11;
      k--;
    }
    if (m > 11) {
      m = 0;
      k++;
    }
    setSel(k << 9 | m << 5 | i, paramBoolean);
  }

  public void openList(boolean paramBoolean) {
    closeList();
    if (paramBoolean) {
      if ((getEnableLists() & 0x2) == 0)
        return;
      this.m_4 |= 0x40;
    } else {
      if ((getEnableLists() & 0x1) == 0)
        return;
      this.m_4 |= 0x80;
    }
    this.m_iC0 = -1;
    this.m_focus = null;
    this.m_4 &= 0x9FE07FFF;
    this.m_iLSel = -1;
    paint0(null);
  }

  public void closeList() {
    closeList(-2, -1);
  }

  public boolean isListOpened() {
    return !((this.m_4 & 0xC0) == 0);
  }

  public int hitTest(int paramInt1, int paramInt2, boolean paramBoolean) {
    this.m_hit = 0;
    if ((this.m_4 & 0x30) != 48)
      return 0;
    if (!paramBoolean && closeList(paramInt1, paramInt2))
      return 0;
    paramInt1 -= this.m_r.x;
    if ((paramInt2 -= this.m_r.y) < 0) {
      paramInt1 -= 2;
      if (paramInt2 + this.m_rE.height > 3 && !paramBoolean && paramInt2 <= -3 && paramInt1 > 2) {
        paramInt1 -= this.m_iMonW;
        if ((paramInt2 = paramInt1) > -1) {
          if (paramInt1 < 5)
            return 0;
          if ((paramInt2 -= this.m_rE.width) > 0) {
            paramInt2 -= 6;
            if (paramInt2 > 0 && getCurrentDateButton() && paramInt2 + 4 < this.m_rE.height)
              setSelDate(new Date());
            return 0;
          }
        }
        openList(!(paramInt1 <= -1));
      }
      return 0;
    }
    int i = this.m_iCapH;
    int j = 1;
    int k = 0;
    if (this.m_iNum > 1) {
      while (paramInt1 > j + this.m_iW + 2) {
        j += this.m_iW + 2;
        if (k++ >= 2)
          return 0;
      }
      while (paramInt2 > i + this.m_iH + this.m_iCapH) {
        i += this.m_iH + this.m_iCapH;
        k += 3;
        if (k >= this.m_iNum)
          return 0;
      }
      k += this.m_iCurM / this.m_iNum * this.m_iNum;
    } else {
      k = this.m_iCurM;
    }
    int m = (paramInt1 - j) / this.m_cellW + 1;
    int n = (paramInt2 - i) / this.m_cellH * 7;
    if (paramInt1 < j || m > 7 || paramInt2 < i || n > 35)
      return 0;
    setFirstLastDays(k);
    m += n - this.m_iDow;
    n = this.m_iCurY;
    if (m < 1) {
      if (this.m_iNum > 1)
        return 0;
      if (--k < 0) {
        k = 11;
        n--;
      }
      m += this.m_iBefore;
    } else if (m > this.m_iDays) {
      if (this.m_iNum > 1)
        return 0;
      m -= this.m_iDays;
      if (++k > 11) {
        k = 0;
        n++;
      }
    }
    if ((this.m_4 & 0x1000000) != 0) {
      paramInt1 = (paramInt1 - 1) % (this.m_iW + 2);
      paramInt2 = (paramInt2 - this.m_iCapH) % (this.m_iH + this.m_iCapH);
      if (paramInt1 % this.m_cellW > this.m_cellBodr && paramInt2 > 0 && (this.m_iLSel = paramInt2 % this.m_cellH) >= 0) {
        if ((this.m_3 & 0x800100) == 8388864) {
          this.m_iLCellW = -(paramInt1 % this.m_cellW);
          paramInt2 = ((this.m_3 & 0x80000) == 0) ? 1000 : (this.m_3 & 0x3F);
          this.m_iLCellH = -((this.m_iLSel - this.m_iCTop + 2) % paramInt2);
        }
        if ((paramInt1 /= this.m_cellW) < 7) {
          if (getMondayFirst())
            paramInt1 = (paramInt1 + 1) % 7;
          m |= paramInt1 + 1 << 24;
        }
      }
    }
    return this.m_hit = n << 9 | k << 5 | m;
  }

  public int getFocusDate() {
    return this.m_1 & 0x7FFFFF;
  }

  public int getIndexOfLine() {
    return this.m_iLSel;
  }

  public String getLine(MouseEvent paramMouseEvent) {
    boolean bool = ((this.m_4 & 0x2000000) == 0) ? false : true;
    this.m_4 &= 0xFDFFFFFF;
    int i = this.m_3 & 0x3F;
    if ((this.m_3 & 0x1000000) == 0 || isListOpened() || i < 5)
      return null;
    this.m_4 |= 0x1000000;
    this.m_iLSel = -1;
    int k = hitTest(paramMouseEvent.getX(), paramMouseEvent.getY(), true);
    this.m_4 &= 0xFEFFFFFF;
    CalDay calDay = (this.m_iLSel >= 0) ? calDay(k) : null;
    int j;
    if (calDay != null && (j = calDay.m_count) > 0 && (this.m_iLSel -= this.m_iCTop - 2) >= 0) {
      if ((this.m_3 & 0x80000) == 0) {
        this.m_iLSel = 0;
        return calDay.toString();
      }
      if (isSel(k & 0x7FFFFF) && getSelDatesSize() == 1) {
        if (bool)
          this.m_focus = calDay;
        if (j > this.m_iCNum) {
          int m = this.m_4 & 0xF;
          if ((paramMouseEvent.getX() - this.m_r.x) % (this.m_iW + 2) % this.m_cellW + m >= this.m_cellW) {
            if (bool) {
              this.m_4 |= 0x400000;
              m -= 4;
              if (m < 8 && ++m < 6)
                m++;
              i = i * this.m_iCNum + 1;
              if ((k = this.m_iLSel) <= m) {
                this.m_4 |= 0x10000;
              } else if (k >= i - m) {
                this.m_4 |= 0x20000;
              } else if (i - m - m > 3) {
                if (k >= (m += this.m_iLCols))
                  this.m_4 |= 0x80000;
                if (k <= m + this.m_iLNum)
                  this.m_4 |= 0x40000;
              }
            }
            return null;
          }
        }
      }
      j -= this.m_iCNum;
      if ((this.m_3 & 0x800100) == 8388864)
        this.m_iL1st = ((k & 0x7FFFFF) == (this.m_1 & 0x7FFFFF) && j > 0) ? (this.m_4 & 0xF) : 0;
      this.m_iLSel /= i;
      return (this.m_iLSel >= this.m_iCNum) ? null : (((this.m_3 & 0x10000) != 0 && j < 0 && (this.m_iLSel += j) < 0) ? null : calDay.itemAt(this.m_iLSel += calDay.m_first));
    }
    return null;
  }

  boolean limits(int paramInt) {
    return !((this.m_min == 0 || paramInt >= this.m_min) && (this.m_max == 0 || paramInt <= this.m_max));
  }

  public void fireAction(int paramInt) {
    this.m_4 &= 0xF7FFFFFF;
    if ((getActionMask() & paramInt) != 0 && ((paramInt & 0xC0000200) != 0 || ((this.m_4 & 0x20) != 0 && (this.m_2 & 0xFF800000 & paramInt) == (0xFF800000 & paramInt))))
      fire(paramInt);
  }

  void fire(int paramInt) {
    if (this.m_aLs != null)
      this.m_aLs.actionPerformed(new ActionEvent(this, 1001, null, paramInt));
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  public void transferFocus() {
    fireAction(805306368);
    super.transferFocus();
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    super.processEvent(paramAWTEvent);
    if (!isEnabled())
      return;
    this.m_4 &= 0xFF7FFFFF;
    int i = paramAWTEvent.getID();
    if (i == 1004) {
      this.m_4 |= 0x200;
      fireAction(1073741824);
      this.m_4 &= 0xFFE07FFF;
    }
    if (i == 1005) {
      closeList(-2, -1);
      this.m_4 &= 0xFFFFFDFF;
      fireAction(-2147483648);
      if ((this.m_4 & 0xF8000) != 0)
        this.m_4 |= 0x800000;
      this.m_4 &= 0xEFE07FFF;
    }
    if ((this.m_4 & 0x4000000) == 0)
      return;
    byte b = (paramAWTEvent instanceof InputEvent) ? (((InputEvent)paramAWTEvent).getModifiers() & 0x1F) : 0;
    if (i == 401 && (this.m_4 & 0x100) == 0) {
      int j = 0;
      int k = ((KeyEvent)paramAWTEvent).getKeyCode();
      if (b > 1) {
        if (b == 9 && k == 112)
          this.m_4 |= 0x801000;
      } else {
        if ((this.m_4 & 0x8000) != 0) {
          if (getCustomSelectionStyle() < 3)
            b = 0;
          if (b != 1)
            this.m_4 &= 0xFFEFFFFF;
          int n = this.m_iCNum;
          if (n > 1)
            n--;
          switch (k) {
            case 39:
            case 40:
              j = 1;
              break;
            case 37:
            case 38:
              j = -1;
              break;
            case 34:
              j = n;
              break;
            case 33:
              j = -n;
              break;
          }
          if (j != 0) {
            if ((n = this.m_focus.m_count) < 1)
              return;
            int[] arrayOfInt = this.m_focus.m_selection;
            byte b1 = (arrayOfInt == null) ? 0 : arrayOfInt.length;
            if (!b1) {
              arrayOfInt = new int[] { j = 0 };
            } else {
              i = 0;
              if ((this.m_4 & 0x100000) != 0 && b == 1)
                i = arrayOfInt.length - 1;
              j += arrayOfInt[i];
              if (j >= n)
                j = n - 1;
              if (j < 0)
                j = 0;
              if (b1 == 1 && j == arrayOfInt[i])
                return;
              if (b != 1) {
                if (b1 > 1)
                  arrayOfInt = new int[1];
                arrayOfInt[0] = j;
              } else {
                this.m_4 |= 0x100000;
                i = 1;
                n = arrayOfInt[0];
                k = j - n;
                if (k < 0) {
                  k = -k;
                  i = -1;
                }
                arrayOfInt = new int[++k];
                while (k-- > 0)
                  arrayOfInt[k] = n + i * k;
              }
            }
            this.m_focus.m_selection = arrayOfInt;
            fireAction(256);
            if (j < this.m_focus.m_first)
              this.m_focus.m_first = j;
            if (j >= this.m_iCNum + this.m_focus.m_first)
              this.m_focus.m_first = j - this.m_iCNum + 1;
            this.m_4 |= 0x800000;
            k = 0;
          }
        }
        if ((this.m_4 & 0x40) != 0 && !getHideYearField() && b == 0) {
          j = this.m_iC0;
          if (k == 8 || k == 127 || (k >= 48 && k <= 57) || ((KeyEvent)paramAWTEvent).isActionKey()) {
            if (k > 32 && k < 48) {
              if (j < 100)
                j = this.m_iCurY;
              if (k == 38)
                j++;
              if (k == 40)
                j--;
              k = 0;
            }
            if (j == -1)
              j = 0;
            if (k == 8 || k == 127)
              j /= 10;
            if (k >= 48 && k <= 57)
              j = ((j >= 1000) ? (j / 10) : j) * 10 + k - 48;
          }
          if (j != this.m_iC0) {
            this.m_iC0 = j;
            this.m_4 |= 0x80800000;
          }
        }
        j = 0;
        int m = 0;
        switch (k) {
          case 9:
            this.m_4 &= 0xFFFFFBFF;
            j = -1;
            m = 805306368;
            break;
          case 27:
            j = -2;
            m = 536870912;
            break;
          case 10:
            m = 268435456;
          case 32:
            j = -1;
            break;
          case 37:
            shiftDate(-1, !(b != 1));
            break;
          case 39:
            shiftDate(1, !(b != 1));
            break;
          case 38:
            shiftDate(-7, !(b != 1));
            break;
          case 40:
            shiftDate(7, !(b != 1));
            break;
          case 33:
            shiftDate(-30, !(b != 1));
            break;
          case 34:
            shiftDate(30, !(b != 1));
            break;
        }
        if (j != 0) {
          if (closeList(j, -1))
            m = 0;
          if (m != 0)
            fireAction(m);
          if ((this.m_4 & 0x8000) != 0)
            this.m_4 = this.m_4 & 0xFFFF7FFF | 0x800000;
        }
      }
      this.m_tip = null;
    }
    if (i == 502) {
      if ((this.m_4 & 0x10000800) == 2048)
        fireAction(8388608);
      if ((this.m_4 & 0xF0000) != 0)
        this.m_4 |= 0x800000;
      this.m_4 &= 0xEFF0F6FF;
    } else if (i == 505) {
      this.m_4 &= 0xFFFFFBFF;
    } else if (paramAWTEvent instanceof MouseEvent && ((b & 0x1004) == 0 || getEnableRightMouse())) {
      mouse((MouseEvent)paramAWTEvent);
    }
    if ((this.m_4 & 0x800000) != 0)
      paint0(null);
  }

  void mouse(MouseEvent paramMouseEvent) {
    int i = paramMouseEvent.getX();
    int j = paramMouseEvent.getY();
    int k = paramMouseEvent.getID();
    int m = paramMouseEvent.getModifiers() & 0x1F;
    int n = 0;
    int i1 = paramMouseEvent.getClickCount();
    if (k == 503 && m != 0)
      k = 506;
    if (i1 > 1 && (this.m_4 & 0x400000) != 0)
      i1 = 1;
    if (i1 > 2)
      return;
    boolean bool = false;
    this.m_4 |= 0x400;
    if (i < 0 || i > (super.getSize()).width || j < 0 || j > (super.getSize()).height)
      this.m_4 &= 0xFFFFFBFF;
    if (k == 501) {
      if (this.m_tip != null)
        this.m_4 |= 0x800000;
      this.m_tip = null;
      this.m_4 |= 0x100;
      this.m_4 &= 0xEFBFC7FF;
      n = hitTest(i, j, false) & 0x7FFFFF;
      if ((this.m_4 & 0x200) == 0)
        requestFocus();
      if (n == 0) {
        this.m_4 |= 0x10000000;
        return;
      }
      bool = (!isSel(n) || getSelDatesSize() != 1) ? false : true;
      if (i1 == 2) {
        fireAction(0x8000000 | n);
      } else if (n > 0 && (!bool || (this.m_4 & 0x8000) == 0)) {
        this.m_4 |= Integer.MIN_VALUE;
        if (paramMouseEvent.isControlDown()) {
          if (addToSel(n, true)) {
            n = 1;
            bool = false;
          }
        } else if (setSel(n, paramMouseEvent.isShiftDown())) {
          n = 1;
          bool = false;
        }
      }
      if ((this.m_4 & 0x8000) != 0 && !bool) {
        this.m_focus = null;
        this.m_4 &= 0xFFE07FFF;
        return;
      }
    }
    if (isListOpened()) {
      if (k == 503)
        mouseMove(true, i, j);
      return;
    }
    if (k == 503) {
      if (getShowTips()) {
        k = this.m_iLCellW;
        m = this.m_iLCellH;
        this.m_3 |= 0x1000100;
        this.m_tip = getLine(paramMouseEvent);
        if (this.m_tip == null) {
          this.m_iLCellW = this.m_iLCellH = -1;
        } else {
          this.m_iLCellW += i;
          this.m_iLCellH += j;
        }
        this.m_3 &= 0xFEFFFEFF;
        if (k != this.m_iLCellW || m != this.m_iLCellH)
          this.m_4 |= 0x800000;
      }
      return;
    }
    if (k == 506 && (n = hitTest(i, j, true) & 0x7FFFFF) > 0 && (this.m_4 & 0x100F8000) == 0 && (m & 0xB) == 0 && (this.m_iNum > 1 || (n & 0x7FFFE0) == (this.m_iCurY << 9 | this.m_iCurM << 5)) && setSel(n, true)) {
      this.m_4 |= 0x800;
      return;
    }
    if ((m &= 0x3) == 3 || (k != 501 && k != 506))
      return;
    int i2 = getCustomSelectionStyle();
    if (((this.m_4 & 0x8000) == 0 && (i2 & 0x1) == 0 && i1 == 1) || i2 < 1)
      bool = false;
    if (k == 501) {
      if (n < 100)
        return;
      this.m_4 |= 0x2000000;
    }
    this.m_3 |= 0x1000000;
    String str = getLine(paramMouseEvent);
    this.m_3 &= 0xFEFFFFFF;
    if (k == 501 && (this.m_2 & 0x80) != 0 && this.m_aLs != null && str != null)
      this.m_aLs.actionPerformed(new ActionEvent(this, this.m_iLSel, str, 128));
    if (bool && this.m_focus != null && str != null && (this.m_4 & 0x8000) == 0)
      this.m_4 |= 0x808000;
    if ((this.m_4 & 0xF8000) == 0 || this.m_focus == null)
      return;
    int i3 = this.m_focus.m_count - this.m_iCNum;
    int i4 = this.m_focus.m_first;
    if (k == 501) {
      this.m_iLCellH = j;
      this.m_iC0 = i4;
      this.m_iLCellW = this.m_cellW - 1 - this.m_cellBodr;
      this.m_iL1st = this.m_r.x + 1 + this.m_cellBodr + (i - this.m_r.x - 1) % (this.m_iW + 2) / this.m_cellW * this.m_cellW;
      if ((this.m_4 & 0xF0000) != 0) {
        this.m_iL1st += this.m_iLCellW + 2 - (this.m_iLCellW = (this.m_4 & 0xF) + 4);
      } else if (i3 > 0) {
        this.m_iLCellW -= this.m_4 >>> 28;
      }
      if ((this.m_4 & 0xC0000) == 262144) {
        if ((this.m_focus.m_first -= this.m_iCNum - 1) < 0)
          this.m_focus.m_first = 0;
      } else if ((this.m_4 & 0xC0000) == 524288 && (this.m_focus.m_first += this.m_iCNum - 1) > i3) {
        this.m_focus.m_first = i3;
      }
    } else {
      if (i <= this.m_iL1st || i >= this.m_iL1st + this.m_iLCellW)
        return;
      if ((this.m_1 & 0x7FFFFF) != n)
        str = null;
      if ((this.m_4 & 0xC0000) == 786432) {
        n = this.m_iC0 + (j - this.m_iLCellH) * 100000 / this.m_iLRows;
        if (n > i3)
          n = i3;
        if (n < 0)
          n = 0;
        if (n == i4)
          return;
        this.m_focus.m_first = n;
      }
    }
    if ((this.m_4 & 0x10000) != 0) {
      if (i4 > 0)
        this.m_focus.m_first--;
    } else if ((this.m_4 & 0x20000) != 0 && i4 < i3) {
      this.m_focus.m_first++;
    }
    if ((this.m_4 & 0xF8000) == 32768) {
      if (str == null) {
        if (i3 <= 0 || k == 501)
          return;
        if (j > this.m_iLCellH) {
          if (i4 >= i3)
            return;
          this.m_focus.m_first++;
          this.m_iLSel = i4 + this.m_iCNum;
        } else if (j < this.m_iLCellH) {
          if (i4 < 1)
            return;
          this.m_focus.m_first--;
          this.m_iLSel = i4 - 1;
        }
      }
      int[] arrayOfInt = this.m_focus.m_selection;
      int i5 = (arrayOfInt == null) ? 0 : arrayOfInt.length;
      i4 = this.m_iLSel;
      if (i2 < 3) {
        k = 0;
        m &= 0x2;
      }
      if (k == 506) {
        if (!i5 || m != 0)
          return;
        if (i4 == arrayOfInt[i5 - 1])
          return;
        m = 1;
      }
      this.m_4 = this.m_4 & 0xFFEFFFFF | (m & 0x1) << 20;
      if (i5 > 0)
        if (m == 1) {
          if (i4 == arrayOfInt[i5 - 1])
            return;
          k = 1;
          m = arrayOfInt[0];
          i5 = i4 - m;
          if (i5 < 0) {
            i5 = -i5;
            k = -1;
          }
          arrayOfInt = new int[++i5];
          while (i5-- > 0)
            arrayOfInt[i5] = m + k * i5;
        } else if (m == 2) {
          k = i5;
          while (k-- > 0 && arrayOfInt[k] != i4);
          arrayOfInt = new int[m = i5 + ((k >= 0) ? -1 : 1)];
          if (i2 < 3 && m > 1)
            return;
          if (k >= 0) {
            m = 1;
            while (i5-- > 0) {
              if (i5 < k)
                m = 0;
              if (i5 > 0 || k > 0)
                arrayOfInt[i5 - m] = this.m_focus.m_selection[i5];
            }
          } else {
            arrayOfInt[i5] = i4;
            while (i5-- > 0)
              arrayOfInt[i5] = this.m_focus.m_selection[i5];
          }
        } else {
          if (i5 == 1 && i4 == arrayOfInt[0])
            return;
          i5 = 0;
        }
      if (i5 == 0 && i4 >= 0)
        arrayOfInt = new int[] { i4 };
      this.m_focus.m_selection = arrayOfInt;
      fireAction(256);
    }
    this.m_4 |= 0x20800000;
  }

  boolean closeList(int paramInt1, int paramInt2) {
    if (!isListOpened())
      return false;
    this.m_4 |= Integer.MIN_VALUE;
    int i = (paramInt1 == -1) ? this.m_iC0 : mouseMove(false, paramInt1, paramInt2);
    if ((this.m_4 & 0xC0) != 0 && i >= 0)
      if ((this.m_4 & 0x80) != 0 && i < 12) {
        i *= this.m_iNum;
        if (this.m_iCurM < i && this.m_max != 0 && limits(this.m_iCurY << 9 | i << 5 | 0x1))
          i = this.m_max >> 5 & 0xF;
        if (this.m_iCurM > i && this.m_min != 0 && limits(this.m_iCurY << 9 | i << 5 | 0x1C))
          i = this.m_min >> 5 & 0xF;
        if (this.m_iCurM != i) {
          fireAction(0x4000000 | this.m_iCurY << 9 | i << 5);
          if ((this.m_4 & 0x8000000) == 0) {
            paramInt1 = 123456;
            this.m_iCurM = i;
          }
        }
      } else {
        paramInt2 = this.m_iCurM;
        if (this.m_iCurY < i && this.m_max != 0 && limits(i << 9 | paramInt2 << 5 | 0x1)) {
          i = this.m_max >>> 9;
          if (paramInt2 > (this.m_max >> 5 & 0xF))
            paramInt2 = this.m_max >> 5 & 0xF;
        }
        if (this.m_iCurY > i && this.m_min != 0 && limits(i << 9 | paramInt2 << 5 | 0x1C)) {
          i = this.m_min >>> 9;
          if (paramInt2 < (this.m_min >> 5 & 0xF))
            paramInt2 = this.m_min >> 5 & 0xF;
        }
        if (this.m_iCurY != i) {
          fireAction(0x4000000 | i << 9 | paramInt2 << 5);
          if ((this.m_4 & 0x8000000) == 0) {
            paramInt1 = 123456;
            this.m_iCurY = i;
            this.m_iCurM = paramInt2;
          }
        }
      }
    if (paramInt1 == 123456)
      fireAction(67108864);
    this.m_4 &= 0xFFFFFF3F;
    paint0(null);
    return true;
  }

  protected void drawDay(Graphics paramGraphics, int paramInt, boolean paramBoolean) {}

  protected void drawDay(Graphics paramGraphics, CalDay paramCalDay, int paramInt) {
    if (paramCalDay.m_im != null)
      if ((paramCalDay.m_d0 & Integer.MIN_VALUE) != 0) {
        byte b = ((paramCalDay.m_d0 & 0x8000000) == 0) ? (this.m_cellH - this.m_cellBodr - paramCalDay.m_im.getHeight(null) - 1) : 1;
        paramGraphics.drawImage(paramCalDay.m_im, paramInt + this.m_cellW - this.m_cellBodr - paramCalDay.m_im.getWidth(null) - 1, paramInt + b, null);
      } else {
        paramGraphics.drawImage(paramCalDay.m_im, paramInt, paramInt, this.m_cellW - this.m_cellBodr, this.m_cellH - this.m_cellBodr, null);
      }
    int i;
    if (getDrawCustomText() && paramCalDay.m_object != null && (i = (this.m_3 & 0x3F) * this.m_iCNum) > 0)
      drawDayText(paramGraphics, paramCalDay, new Rectangle(paramInt, this.m_iCTop - this.m_cellBodr - paramInt, this.m_cellW - this.m_cellBodr - 1, i));
  }

  protected void drawDayText(Graphics paramGraphics, CalDay paramCalDay, Rectangle paramRectangle) {
    Color color;
    int i = this.m_3 & 0x3F;
    int j = 0;
    int k = paramCalDay.m_first;
    int m = 0;
    int n = this.m_iCNum;
    if ((this.m_3 & 0x80000) != 0 && (this.m_4 & 0x4000) != 0) {
      int[] arrayOfInt = paramCalDay.m_selection;
      byte b = (arrayOfInt == null) ? 0 : arrayOfInt.length;
      if ((m = paramCalDay.m_count) > n) {
        j = getScrollBarWidth();
        if (j + j >= paramRectangle.height && (j = paramRectangle.height / 2) < 7)
          j = paramRectangle.height * 2 / 3;
        j |= 0x1;
        this.m_4 = this.m_4 & 0xFFFFFFF0 | j;
        paramRectangle.width -= j + 1;
      }
      if ((this.m_4 & 0x8000) != 0) {
        paramGraphics.setColor(getListBackground());
        paramGraphics.fillRect(paramRectangle.x, paramRectangle.y - 1, paramRectangle.width, i * n + 2);
      }
      paramGraphics.setColor(getCustomSelectionBackground());
      while (b-- > 0) {
        int i1 = arrayOfInt[b];
        if (i1 >= k && i1 - k < n && i1 < m) {
          if ((this.m_3 & 0x10000) != 0 && m < n)
            i1 += n - m;
          paramGraphics.fillRect(paramRectangle.x + 1, paramRectangle.y + (i1 - k) * i, paramRectangle.width - 2, i);
        }
      }
      color = getListForeground();
    } else {
      color = (paramCalDay.m_foreground == null) ? getCustomForeground() : paramCalDay.m_foreground;
    }
    paramGraphics.setColor(color);
    paramGraphics.setFont((paramCalDay.m_font == null) ? getCustomFont() : paramCalDay.m_font);
    PVMultiLine.draw(paramGraphics, paramCalDay.toString(), paramRectangle, 0, false, getCustomFlags());
    if (m > n) {
      i = i * n + 1;
      int i1 = paramRectangle.x + paramRectangle.width + 1;
      int i2;
      for (i2 = 2; i2 + i2 + 7 < j; i2++);
      int i3 = j - ((i2 > 3) ? 3 : i2);
      if (i3 < 6)
        i3++;
      color = getScrollBarColor();
      paramGraphics.setColor(color);
      paramGraphics.fillRect(i1, paramRectangle.y - 1, j, paramRectangle.y + i);
      PVImage.triangle(paramGraphics, null, null, i1 + i2, paramRectangle.y - 1 + i2, j - i2 - i2, 67);
      PVImage.triangle(paramGraphics, null, null, i1 + i2, paramRectangle.y + i - j / 2 - 2, j - i2 - i2, 69);
      PVBorder.draw(paramGraphics, new Rectangle(i1, paramRectangle.y - 1, j, i3), this.m_iBdrS, color, !((this.m_4 & 0x10000) == 0));
      PVBorder.draw(paramGraphics, new Rectangle(i1, paramRectangle.y + i - i3, j, i3), this.m_iBdrS, color, !((this.m_4 & 0x20000) == 0));
      i -= i3 + i3 - 1;
      i2 = i3 + paramRectangle.y - 1;
      paramGraphics.setColor(PVImage.shift(color, -3));
      paramGraphics.fillRect(i1, i2, j, i);
      if (i > 3) {
        double d = i * n / m;
        if (d < 3.0D)
          d = 3.0D;
        i3 = (int)(d + 0.5D);
        d = (i - d) / (m - n);
        k = (int)(k * d + 0.5D);
        if (!isListOpened()) {
          this.m_iLNum = i3;
          this.m_iLRows = (int)(d * 100000.0D);
          this.m_iLCols = k;
        }
        paramGraphics.setColor(color);
        paramGraphics.fillRect(i1, i2 += k, j, i3);
        PVBorder.draw(paramGraphics, new Rectangle(i1, i2, j, i3), this.m_iBdrS, color, !((this.m_4 & 0xC0000) != 786432));
      }
    }
  }

  void drawDay(Graphics paramGraphics1, Graphics paramGraphics2, int paramInt1, int paramInt2, boolean paramBoolean) {
    boolean bool = paramBoolean ? false : isSel(paramInt2);
    int i = (paramInt1 + (getMondayFirst() ? 1 : 0)) % 7;
    CalDay calDay = (!paramBoolean || this.m_iNum == 1) ? calDay(paramInt2 | i + 1 << 24) : null;
    Color color = bool ? getSelCellColor() : getCalendarColor();
    if (calDay != null)
      if (calDay.m_back != null) {
        if (!bool || (calDay.m_d0 & 0x40000000) != 0)
          color = calDay.m_back;
      } else if ((calDay.m_d0 & 0x40000000) != 0 && bool) {
        color = getCalendarColor();
      }
    paramGraphics2.setColor(color);
    paramGraphics2.fillRect(0, 0, this.m_cellW, this.m_cellH);
    int j = (this.m_cellBodr > 0) ? 1 : 0;
    this.m_4 &= 0xFFFFBFFF;
    if (bool && getSelDatesSize() == 1)
      this.m_4 |= 0x4000;
    if (calDay != null)
      drawDay(paramGraphics2, calDay, j);
    drawDay(paramGraphics2, paramInt2, paramBoolean);
    if (!paramBoolean || this.m_iNum == 1) {
      if (!paramBoolean)
        paramBoolean = limits(paramInt2);
      paramInt2 &= 0x1F;
      color = (i == 0) ? getSundayColor() : getForeground();
      if (calDay != null && calDay.m_fore != null)
        color = calDay.m_fore;
      i = this.m_iFL + j++;
      String str = String.valueOf(paramInt2);
      paramGraphics2.setFont(getFont());
      if (paramBoolean) {
        paramGraphics2.setColor(this.m_c3);
        paramGraphics2.drawString(str, j, i);
      } else {
        drawStr(paramGraphics2, str, color, j, i, getText3DColor(), bool);
      }
    }
    j = 0;
    if (this.m_cellBodr > 0) {
      if (this.m_cellBodr == 1) {
        bool = getIndentCellBorder();
      } else if (!getIndentCellBorder()) {
        bool = !bool;
      }
      bdr(paramGraphics2, true, bool, 0, 0, this.m_cellW - this.m_cellBodr + 1, this.m_cellH - this.m_cellBodr + 1);
      j = this.m_cellBodr - 1;
    }
    paramGraphics1.drawImage(this.m_im0, this.m_iCellsX + paramInt1 % 7 * this.m_cellW + j, this.m_iCellsY + paramInt1 / 7 * this.m_cellH + j, null);
  }

  void drawStr(Graphics paramGraphics, String paramString, Color paramColor1, int paramInt1, int paramInt2, Color paramColor2, boolean paramBoolean) {
    paramGraphics.setColor((paramBoolean &= getInvertedSelection()) ? paramColor1 : paramColor2);
    int i = paramInt1;
    int j = paramInt2;
    if (getAureole()) {
      for (i = -1; i < 2; i++) {
        for (j = -1; j < 2; j++) {
          if (i != 0 || j != 0)
            paramGraphics.drawString(paramString, paramInt1 + i, paramInt2 + j);
        }
      }
    } else if ((this.m_0 & 0xF0000) != 0) {
      if ((this.m_0 & 0x30000) == 65536) {
        paramInt1++;
      } else if ((this.m_0 & 0x20000) != 0) {
        i++;
      }
      if ((this.m_0 & 0xC0000) == 262144) {
        paramInt2++;
      } else if ((this.m_0 & 0x80000) != 0) {
        j++;
      }
      paramGraphics.drawString(paramString, i, j);
    }
    paramGraphics.setColor(paramBoolean ? paramColor2 : paramColor1);
    paramGraphics.drawString(paramString, paramInt1, paramInt2);
  }

  void drawNumbers(Graphics paramGraphics, boolean paramBoolean) {
    int i = this.m_iDow;
    int j = 0;
    int k = this.m_iBefore - this.m_iDow + 1;
    if (this.m_iCurM > 0) {
      k |= this.m_iCurY << 9 | this.m_iCurM - 1 << 5;
    } else {
      k |= this.m_iCurY - 1 << 9 | 0x160;
    }
    Graphics graphics = this.m_im0.getGraphics();
    if (paramBoolean) {
      while (j < i)
        drawDay(paramGraphics, graphics, j++, k++, true);
    } else {
      j = i;
    }
    k = this.m_iCurY << 9 | this.m_iCurM << 5 | 0x1;
    i = k & 0x7FFFE0 | this.m_iDays;
    if (paramBoolean || (this.m_0 & 0x1F) == 0) {
      while (k <= i)
        drawDay(paramGraphics, graphics, j++, k++, false);
    } else {
      while (k <= i) {
        if ((k & 0x1F) == (this.m_1 & 0x1F) || (k & 0x1F) == (this.m_0 & 0x1F))
          drawDay(paramGraphics, graphics, j, k, false);
        j++;
        k++;
      }
    }
    if (paramBoolean) {
      k = 1;
      if (this.m_iCurM == 11) {
        k |= this.m_iCurY + 1 << 9;
      } else {
        k |= this.m_iCurY << 9 | this.m_iCurM + 1 << 5;
      }
      while (j < 42)
        drawDay(paramGraphics, graphics, j++, k++, true);
    }
    graphics.dispose();
  }

  void drawDOW(Graphics paramGraphics, String paramString1, String paramString2, int paramInt1, int paramInt2) {
    paramGraphics.setColor(getBackColor());
    paramGraphics.fillRect(paramInt1, paramInt2, this.m_iW, this.m_iCapH);
    paramGraphics.setColor(getForeground());
    paramGraphics.setFont(getDOWFont());
    FontMetrics fontMetrics = paramGraphics.getFontMetrics();
    paramInt2 -= fontMetrics.getDescent();
    if (paramString2 != null)
      paramGraphics.drawString(paramString2, paramInt1 + 3, paramInt2 + this.m_iCapH / 2);
    paramInt2 += this.m_iCapH;
    paramInt1 += this.m_cellW / 2;
    byte b1 = 0;
    int i = paramString1.length();
    byte b2 = 0;
    for (byte b3 = 0; b3 < 7; b3++) {
      b2 = b1;
      while (b1 < i && paramString1.charAt(b1) != ' ')
        b1++;
      if (b2 != b1) {
        String str = paramString1.substring(b2, b1++);
        paramGraphics.drawString(str, paramInt1 - fontMetrics.stringWidth(str) / 2, paramInt2);
        paramInt1 += this.m_cellW;
      }
    }
  }

  void drawCal(Graphics paramGraphics, boolean paramBoolean) {
    setFirstLastDays(this.m_iCurM);
    int i = this.m_iCellsX;
    int j = this.m_iCellsY;
    int k = this.m_iW;
    int m = this.m_iH;
    if (paramBoolean && this.m_cellBodr > 2) {
      paramGraphics.setColor(getCalendarColor());
      paramGraphics.fillRect(i, j, k, m);
    }
    drawNumbers(paramGraphics, paramBoolean);
    if (paramBoolean && this.m_cellBodr > 0)
      bdr(paramGraphics, !(this.m_cellBodr <= 1), getIndentCellBorder(), i, j, i + --k, j + --m);
  }

  void fix() {
    int i = this.m_3 & 0x3F;
    int j = this.m_cellH - this.m_cellBodr + this.m_iFL + ((this.m_cellBodr == 0) ? 2 : 3);
    if ((this.m_3 & 0x40) == 0 || i < 6)
      this.m_3 = this.m_3 & 0xFFFFFFC0 | (i = getFontMetrics(getCustomFont()).getAscent() & 0x3F);
    if (i + 1 == j)
      j++;
    this.m_iCNum = j / i;
    i *= this.m_iCNum;
    if ((j = (j - i) / 2) > 1)
      j = 1;
    this.m_iCTop = this.m_cellH - i - j + 1;
  }

  void doCol() {
    int i = 1 + getSharpness3D();
    this.m_c1 = PVImage.shift(this.m_c3 = getCalendarColor(), i);
    this.m_c2 = PVImage.shift(this.m_c3, -(i + 3));
    this.m_c3 = PVImage.shift(this.m_c3, 1);
  }

  public void paint(Graphics paramGraphics) {
    boolean bool = (paramGraphics != null) ? false : true;
    if (!bool || (this.m_4 & 0x20) != 0)
      try {
        if (bool && (!isShowing() || (paramGraphics = getGraphics()) == null))
          return;
        fireAction(513);
        if (getParent() != null)
          this.m_4 |= 0x10;
        if ((this.m_4 & 0xC4000000) != 1073741824) {
          Dimension dimension = ((this.m_4 & 0x4000000) == 0) ? super.getSize() : getPreferredSize();
          if (getAutoResize() && (dimension.width < 50 || dimension.height < 50)) {
            setSize(dimension = getPreferredSize());
          } else if ((this.m_4 & 0x4000000) == 0) {
            dimension = getSize();
          }
          Rectangle rectangle = new Rectangle(dimension);
          int i = rectangle.width - this.m_size.width;
          int j = rectangle.height - this.m_size.height;
          PVBorder.shadow(paramGraphics, rectangle, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), getShadow(), getShadowWidth());
          PVBorder.draw(paramGraphics, rectangle, this.m_iBodr, getBorderColor());
          paramGraphics.setColor(getBackground());
          paramGraphics.fillRect(rectangle.x + rectangle.width - i, rectangle.y, i, rectangle.height);
          paramGraphics.fillRect(rectangle.x, rectangle.y + rectangle.height - j, rectangle.width, j);
          doCol();
          i = (this.m_cellBodr > 2) ? (this.m_cellBodr - 2) : 0;
          j = this.m_cellH - i;
          i = this.m_cellW - i;
          if (this.m_im0 != null && (this.m_im0.getWidth(null) != i || this.m_im0.getHeight(null) != j))
            this.m_im0 = null;
          if (this.m_im0 == null)
            this.m_im0 = createImage(i, j);
        }
        this.m_4 |= 0x20;
        paint0(paramGraphics);
        if (bool)
          paramGraphics.dispose();
      } catch (Exception exception) {}
    this.m_4 &= 0x1FFFFFFF;
  }

  public void repaint() {
    if ((this.m_4 & 0x30) == 48) {
      this.m_4 |= Integer.MIN_VALUE;
      paint(null);
    }
  }

  public void paint0(Graphics paramGraphics) {
    int i = this.m_rE.height;
    int j = this.m_iNum;
    int k = this.m_iCurM;
    this.m_iCurM = this.m_iCurM / j * j;
    int m = this.m_r.width;
    int n = this.m_r.height + 1 + i;
    if (paramGraphics == null) {
      if ((this.m_4 & 0x30) == 48) {
        this.m_4 |= 0x40000000;
        paint(null);
      }
      return;
    }
    if (this.m_img != null && (this.m_img.getWidth(null) != m || this.m_img.getHeight(null) != n))
      this.m_img = null;
    if (this.m_img == null)
      try {
        this.m_img = createImage(m, n);
      } catch (Exception exception) {}
    if (this.m_img == null)
      return;
    Graphics graphics = this.m_img.getGraphics();
    boolean bool = ((this.m_4 & 0xA0000000) == 536870912 || isListOpened()) ? false : true;
    String str = null;
    if (bool) {
      graphics.setColor(getBackColor());
      graphics.fillRect(0, 0, m, n);
      str = new String(getDOW());
      m = 0;
      n = str.length();
      if (getMondayFirst()) {
        while (m < n && str.charAt(m) != ' ')
          m++;
        if (m < n / 3) {
          str = String.valueOf(str.substring(m + 1)) + ' ' + str.substring(0, m);
          n = str.length();
        }
      }
    }
    drawButtons(graphics, i - 1, this.m_iButF, this.m_iMonW - 1, this.m_rE.width, this.m_iSelX);
    this.m_iCellsY = this.m_iCapH + i;
    this.m_iCellsX = 1;
    if (j > 1) {
      for (m = 0; m < j / 3; m++) {
        for (n = 0; n < 3; n++) {
          if (bool) {
            drawDOW(graphics, str, getMonthString(this.m_iCurM), this.m_iCellsX, this.m_iCellsY - this.m_iCapH);
            drawCal(graphics, bool);
          } else if ((this.m_iCurM | this.m_iCurY << 4) == (this.m_1 >> 5 & 0x3FFFF)) {
            drawCal(graphics, bool);
          }
          this.m_iCellsX += this.m_iW + 2;
          this.m_iCurM++;
        }
        this.m_iCellsX = 1;
        this.m_iCellsY += this.m_iH + this.m_iCapH;
      }
      this.m_iCurM = k;
    } else {
      if (bool)
        drawDOW(graphics, str, null, this.m_iCellsX, this.m_iCellsY - this.m_iCapH);
      drawCal(graphics, bool);
    }
    str = "";
    if ((n = this.m_4 & 0x3000) != (m = 0)) {
      long l = (n == 8192) ? 34555749885939838L : 16123002742581525L;
      while (m < 11)
        str = String.valueOf(str) + (char)(int)(65L + (l >> m++ * 5 & 0x1FL));
      graphics.setColor(getBackColor());
      graphics.fillRect(10, 70, 150, 50);
      graphics.setColor(getForeground());
      graphics.drawString(str, 20, 100);
    }
    if (isListOpened()) {
      graphics.setFont(getListFont());
      FontMetrics fontMetrics = graphics.getFontMetrics();
      int i1 = this.m_iLCellW;
      int i2 = this.m_iLRows;
      int i3 = this.m_iLCols;
      int i4 = this.m_iL1st;
      int i5 = this.m_iLNum;
      int i6 = this.m_iLSel;
      int i7 = fontMetrics.getHeight();
      boolean bool1 = ((this.m_4 & 0x40) == 0) ? false : true;
      if (i6 < 0) {
        if (bool1) {
          i1 = i7 * 3;
          i3 = (this.m_r.width - 7) / i1;
          i2 = (this.m_r.height - 7) / i7;
          i5 = i3 * i2;
          if ((m = getYearListCount()) > 1 && i5 > m)
            i5 = m;
          i4 = this.m_iCurY - i5 / 2;
          if (i4 < 1)
            i4 = 1;
          if (this.m_min != 0) {
            m = this.m_min >>> 9;
            if (m > i4)
              i4 = m;
          }
          if (this.m_max != 0) {
            m = this.m_max >>> 9;
            if (m < i4 + i5)
              i5 = m - i4 + 1;
          }
          i2 = (i5 - 1) / i3 + 1;
          if (i2 > 1 && i2 < 5)
            i3 = (i5 - 1) / ++i2 + 1;
          if (i2 > 5) {
            m = (i2 > 7) ? 10 : 5;
            m = (i2 + m / 2) / m * m;
            if (m * i7 + 7 < this.m_r.height)
              i2 = m;
            m = i5 / i2;
            if (m * i1 + 7 < this.m_r.width)
              i3 = m;
          }
          i6 = this.m_iCurY - i4;
        } else {
          i5 = 12;
          i4 = 0;
          i1 = 65;
          i2 = 6;
          i3 = 2;
          for (m = 0; m < 12; m++) {
            int i8 = fontMetrics.stringWidth(getMonthString(m));
            if (i1 < i8)
              i1 = i8;
          }
          i1 += 5;
          i6 = this.m_iCurM / j;
          if (j > 1) {
            i3 = 1;
            i2 = 12 / j;
          }
        }
        if (i5 > i2 * i3)
          i5 = i2 * i3;
        this.m_iLCellW = i1;
        this.m_iLCellH = i7;
        this.m_iLRows = i2;
        this.m_iLCols = i3;
        this.m_iLSel = i6;
        this.m_iL1st = i4;
        this.m_iLNum = i5;
      }
      graphics.setColor(getListBackground());
      m = i3 * i1 + 3;
      n = i2 * i7 + 3;
      graphics.fillRect(1, ++i, m, n);
      graphics.setColor(Color.black);
      graphics.drawRect(1, i, m, n);
      graphics.setColor(getListSelectionBackground());
      i += 2;
      graphics.fillRect(3 + i1 * i6 / i2, i + i6 % i2 * i7, i1, i7);
      graphics.setColor(getListForeground());
      i -= fontMetrics.getDescent();
      for (m = 0; m < this.m_iLNum; m++) {
        str = bool1 ? String.valueOf(i4 + m) : getMonthString(m * j);
        if (m == i6)
          graphics.setColor(getListSelectionForeground());
        graphics.drawString(str, 5 + i1 * m / i2, (m % i2 + 1) * i7 + i);
        if (m == i6)
          graphics.setColor(getListForeground());
      }
    }
    graphics.dispose();
    paramGraphics.drawImage(this.m_img, this.m_r.x, this.m_r.y - this.m_rE.height, null);
    if (this.m_tip != null)
      drawTip(paramGraphics, this.m_tip, this.m_iLCellW + this.m_cellBodr, this.m_iLCellH - 1, (this.m_3 & 0x3F) + 1);
  }

  protected void drawTip(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    paramGraphics.setFont(getCustomFont());
    FontMetrics fontMetrics = paramGraphics.getFontMetrics();
    int i = fontMetrics.stringWidth(paramString) + 3;
    if (i + this.m_cellBodr + this.m_iL1st < this.m_cellW)
      return;
    if (paramInt1 + i > this.m_r.x + this.m_r.width && (paramInt1 = this.m_r.x + this.m_r.width - i) < 0)
      paramInt1 = 0;
    if (i > this.m_r.width) {
      i = this.m_r.width;
      do {

      } while (fontMetrics.stringWidth(paramString = paramString.substring(0, paramString.length() - 1)) + 3 > i);
    }
    paramGraphics.setColor(getListBackground());
    paramGraphics.fillRect(paramInt1, paramInt2, i, paramInt3);
    paramGraphics.setColor(getCustomForeground());
    paramGraphics.drawRect(paramInt1, paramInt2, i, paramInt3);
    paramGraphics.drawString(paramString, paramInt1 + 1, paramInt2 + paramInt3 + 1 - fontMetrics.getDescent());
  }

  int mouseMove(boolean paramBoolean, int paramInt1, int paramInt2) {
    paramInt1 -= this.m_r.x + 1;
    paramInt2 -= this.m_r.y + 1;
    int i = paramInt2 / this.m_iLCellH;
    if (paramInt1 < 0 || paramInt2 < 0 || i >= this.m_iLRows)
      return -1;
    i += paramInt1 / this.m_iLCellW * this.m_iLRows;
    if (i >= this.m_iLNum)
      return -1;
    if (this.m_iLSel == i || !paramBoolean)
      return this.m_iLSel + this.m_iL1st;
    this.m_iLSel = i;
    this.m_4 |= 0x40000000;
    paint(null);
    return 0;
  }
}
