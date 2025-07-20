package pv.awt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import pv.util.PVDate0;

public class PVDate extends PVEdit {
  public static final int MM = 0;

  public static final int MMM = 1;

  public static final int MMMM = 2;

  public static final int MMMM_COMA = 3;

  public static final int MDY = 0;

  public static final int DMY = 1;

  public static final int YMD = 2;

  public static final int YDM = 3;

  public static final int MD = 4;

  public static final int DM = 5;

  public static final int MY = 6;

  public static final int YM = 7;

  public static final int INCREMENT = 64;

  public static final int DECREMENT = 128;

  protected char m_chSep = '/';

  protected int m_null;

  protected int m_upper;

  protected int m_lower;

  protected int m_delta;

  int m_5 = 6370;

  int m_d;

  transient int m_field;

  transient String[] m_mon;

  transient String[] m_dow;

  public Calendar m_calendar;

  public PVDate(Date paramDate) {
    super(null);
    this.m_5 = 6370;
    this.m_1 |= 0x1010040;
    this.m_4 |= 0x3000;
    setDate(paramDate);
  }

  public PVDate() {
    this(null);
  }

  public Object getValue() {
    return getDate();
  }

  public void setValue(Object paramObject) {
    if (paramObject instanceof Date) {
      setDate((Date)paramObject);
      return;
    }
    super.setValue(paramObject);
  }

  protected void init0() {
    if (this.m_calendar == null) {
      this.m_calendar = Calendar.getInstance(getLocale());
      byte b = 10;
      while (b++ < 14)
        this.m_calendar.set(b, 0);
    }
    super.init0();
    setCurrentDate(getCurrentDate());
  }

  public void updateLocale(Locale paramLocale) {
    DateFormat dateFormat = DateFormat.getDateInstance(getLocaleFormat(), (paramLocale == null) ? getLocale() : paramLocale);
    if (dateFormat instanceof SimpleDateFormat)
      setPattern(((SimpleDateFormat)dateFormat).toPattern());
  }

  public void setPattern(String paramString) {
    byte b = (paramString == null) ? 0 : paramString.length();
    if (b < 5)
      return;
    this.m_4 |= 0x4000;
    this.m_2 |= 0x8000008;
    char c = 'y';
    int i = 0;
    int j = 0;
    int k = 0;
    int m = paramString.indexOf('d');
    int n = paramString.indexOf('M');
    int i1 = paramString.indexOf('y');
    if (i1 < m) {
      i = ((m > n) ? 2 : 3) + ((i1 < 0) ? 2 : 0);
    } else {
      c = 'M';
      if (m < n) {
        c = 'd';
        i = 1;
      }
    }
    k = paramString.lastIndexOf(c) + 1;
    if (k < b) {
      c = paramString.charAt(k);
      if (c == ',' || c == '\'')
        c = ' ';
      j = chekCh(c);
      if (c != '\000' && (j == -1 || j == -5 || j == -7))
        this.m_chSep = c;
    }
    if (i1 + 2 < paramString.lastIndexOf('y'))
      this.m_2 &= 0xFFFFFFF7;
    if (m < paramString.lastIndexOf('d'))
      this.m_2 &= 0xF7FFFFFF;
    this.m_5 &= ((this.m_4 & 0x8000) == 0) ? -18841601 : -18612225;
    if ((k = paramString.indexOf('E')) >= 0) {
      this.m_5 |= 0x1000000;
      if ((this.m_4 & 0x8000) == 0)
        this.m_5 = this.m_5 & 0xFFFC7FFF | ((k > n) ? 229376 : 196608);
      if (paramString.lastIndexOf('E') > k + 2)
        this.m_5 |= 0x100000;
    }
    k = paramString.lastIndexOf('M') - n - 1;
    if (k == 2 && paramString.indexOf(',') > 0)
      k++;
    if (k > 0)
      this.m_5 |= (k & 0x3) << 18;
    setFormat(i);
    setShowDOW(getShowDOW());
    this.m_4 &= 0xFFFFBFFF;
    setMonthFormat(getMonthFormat());
  }

  public void setSeparator(String paramString) {
    if (paramString != null && paramString.length() > 0) {
      char c = paramString.charAt(0);
      if (!Character.isLetterOrDigit(c)) {
        this.m_chSep = c;
        this.m_2 &= 0xBFFFFFFF;
        if (getShowDOW() > 0 || c == ' ')
          this.m_2 |= 0x40000000;
        txt();
      }
    }
  }

  public String getSeparator() {
    return String.valueOf(this.m_chSep);
  }

  char sep() {
    return ((this.m_2 & 0xC0000000) == -1073741824) ? '-' : this.m_chSep;
  }

  public void setLocaleFormat(int paramInt) {
    this.m_4 = this.m_4 & 0xFFFFCFFF | (paramInt & 0x3) << 12;
  }

  public int getLocaleFormat() {
    return this.m_4 >> 12 & 0x3;
  }

  public void setFormat(int paramInt) {
    this.m_5 = this.m_5 & 0xC7FF8000 | (paramInt &= 0x7) << 27;
    switch (paramInt) {
      case 0:
        this.m_5 |= 0x18E2;
        break;
      case 1:
        this.m_5 |= 0x18A3;
        break;
      case 2:
        this.m_5 |= 0x38A1;
        break;
      case 3:
        this.m_5 |= 0x28E1;
        break;
      case 4:
        this.m_5 |= 0xE2;
        break;
      case 5:
        this.m_5 |= 0xA3;
        break;
      case 6:
        this.m_5 |= 0x62;
        break;
      case 7:
        this.m_5 |= 0xA1;
        break;
    }
    txt();
  }

  public int getFormat() {
    return this.m_5 >>> 27;
  }

  public void setShowCentury(boolean paramBoolean) {
    this.m_2 |= 0x8;
    if (paramBoolean)
      this.m_2 &= 0xFFFFFFF7;
    txt();
  }

  public boolean getShowCentury() {
    return !((this.m_2 & 0x8) != 0);
  }

  public void setAutoCentury(boolean paramBoolean) {
    this.m_5 |= 0x800000;
    if (paramBoolean)
      this.m_5 &= 0xFF7FFFFF;
    txt();
  }

  public boolean getAutoCentury() {
    return !((this.m_5 & 0x800000) != 0);
  }

  public void setLeadingZero(boolean paramBoolean) {
    this.m_2 |= 0x8000000;
    if (paramBoolean)
      this.m_2 &= 0xF7FFFFFF;
    txt();
  }

  public boolean getLeadingZero() {
    return !((this.m_2 & 0x8000000) != 0);
  }

  public boolean getAllowNull() {
    return !((this.m_2 & 0x800) != 0);
  }

  public void setAllowNull(boolean paramBoolean) {
    this.m_2 |= 0x800;
    if (paramBoolean)
      this.m_2 &= 0xFFFFF7FF;
  }

  public void setAutoSelectNextField(boolean paramBoolean) {
    this.m_5 &= 0xFFBFFFFF;
    if (paramBoolean)
      this.m_5 |= 0x400000;
  }

  public boolean getAutoSelectNextField() {
    return !((this.m_5 & 0x400000) == 0);
  }

  public void setCurrentDate(boolean paramBoolean) {
    this.m_2 &= 0xFFFFFF7F;
    if (paramBoolean) {
      this.m_2 |= 0x80;
      setDate(new Date());
    }
  }

  public boolean getCurrentDate() {
    return !((this.m_2 & 0x80) == 0);
  }

  public void setEnableArrows(boolean paramBoolean) {
    this.m_1 |= 0x400000;
    if (paramBoolean)
      this.m_1 &= 0xFFBFFFFF;
  }

  public boolean getEnableArrows() {
    return !((this.m_1 & 0x400000) != 0);
  }

  public void setDelta(int paramInt) {
    this.m_delta = paramInt;
  }

  public int getDelta() {
    return this.m_delta;
  }

  public void setText(String paramString) {
    this.m_2 &= Integer.MAX_VALUE;
    buildFields(paramString, true);
    limits();
    txt();
  }

  void txt() {
    if ((this.m_4 & 0x4000) == 0)
      super.setText(buildText());
  }

  public void setDate(Date paramDate) {
    set(pack(paramDate));
  }

  public Date getDate() {
    return unpack(isNull() ? this.m_null : this.m_d);
  }

  public void setCalendar(Calendar paramCalendar) {
    byte b = (paramCalendar == null) ? 0 : PVDate0.pack0(this.m_calendar = (Calendar)paramCalendar.clone());
    set(!b ? 0 : (b + 32));
  }

  void set(int paramInt) {
    this.m_d = paramInt;
    limits();
    this.m_text = buildText();
    paint(null);
  }

  public Calendar getCalendar() {
    int i = isNull() ? this.m_null : this.m_d;
    return PVDate0.unpack0((i > 32) ? (i - 32) : i, this.m_calendar);
  }

  int pack(Date paramDate) {
    int i = PVDate0.pack(paramDate, this.m_calendar);
    return (i == 0) ? 0 : (i + 32);
  }

  Date unpack(int paramInt) {
    return PVDate0.unpack((paramInt > 32) ? (paramInt - 32) : paramInt, this.m_calendar);
  }

  public boolean isNull() {
    return !(getField(1) >= 100 && getField(2) >= 1 && getField(3) >= 1);
  }

  public void setDefaultNull(Date paramDate) {
    this.m_null = pack(paramDate);
    refresh();
  }

  public Date getDefaultNull() {
    return unpack(this.m_null);
  }

  public void setUpperLimit(Date paramDate) {
    this.m_upper = pack(paramDate);
    if (paramDate != null)
      refresh();
  }

  public Date getUpperLimit() {
    return unpack(this.m_upper);
  }

  public void setLowerLimit(Date paramDate) {
    this.m_lower = pack(paramDate);
    if (paramDate != null)
      refresh();
  }

  public Date getLowerLimit() {
    return unpack(this.m_lower);
  }

  public int getField(int paramInt) {
    return (paramInt == 1) ? (this.m_d >> 9 & 0x3FFF) : ((paramInt == 3) ? (this.m_d & 0x1F) : (this.m_d >> 5 & 0xF));
  }

  public void setField(int paramInt1, int paramInt2) {
    if (paramInt2 == 1) {
      if (paramInt1 > 9999)
        paramInt1 = -1;
      if (paramInt1 >= 0) {
        if (paramInt1 < 100) {
          paramInt2 = getAutoCentury() ? 0 : (getShowCentury() ? paramInt1 : (getField(1) / 100 * 100));
          if (paramInt2 == 0)
            paramInt2 = (paramInt1 < 30) ? 2000 : 1900;
          if (paramInt2 > 99)
            paramInt1 += paramInt2;
        }
      } else {
        paramInt1 = 0;
      }
      this.m_d = this.m_d & 0x1FF | paramInt1 << 9;
      return;
    }
    if (paramInt1 < 0 || paramInt1 > 31)
      paramInt1 = 0;
    if (paramInt2 == 3) {
      this.m_d = this.m_d & 0xFFFFFFE0 | paramInt1;
      return;
    }
    if (paramInt1 > 12)
      paramInt1 = 0;
    this.m_d = this.m_d & 0xFFFFFE1F | paramInt1 << 5;
  }

  public void refresh() {
    limits();
    this.m_2 &= Integer.MAX_VALUE;
    this.m_text = buildText();
    this.m_field = 0;
    paint(null);
  }

  protected void onLostFocus() {
    this.m_2 &= Integer.MAX_VALUE;
    this.m_field = 0;
    if ((this.m_5 & 0x200000) != 0)
      buildFields(this.m_text, true);
    limits();
    this.m_text = buildText();
  }

  protected void onSetFocus() {
    if ((this.m_1 & 0x8000000) == 0) {
      this.m_2 &= Integer.MAX_VALUE;
    } else if ((this.m_2 & Integer.MIN_VALUE) != 0) {
      return;
    }
    this.m_text = buildText();
    super.onSetFocus();
  }

  public void fireAction(int paramInt) {
    if ((paramInt & 0xC0) != 0) {
      this.m_1 &= 0xEFFFFFFE;
      shiftField(-1, !((paramInt & 0x40) == 0));
    }
    super.fireAction(paramInt);
  }

  public void shiftField(int paramInt, boolean paramBoolean) {
    if ((this.m_2 & 0xC0000000) == -1073741824) {
      this.m_2 &= Integer.MAX_VALUE;
      this.m_text = buildText();
    }
    int i = paramInt;
    int j = 0;
    int k = paramBoolean ? 1 : -1;
    if (this.m_text.length() < 3 || getField(1) < 1) {
      this.m_d = PVDate0.pack(new Date(), this.m_calendar) + 32;
      limits();
      this.m_text = buildText();
      if (this.m_text.length() < 3)
        return;
    }
    if (paramInt < 1) {
      paramInt = 0;
      if ((this.m_5 & 0x38000) == 196608 && this.m_chSep == ' ')
        paramInt--;
      if (this.m_delta > 0 && i == -1) {
        if ((paramInt = this.m_delta) < 30) {
          if (paramInt > 28)
            paramInt = 28;
          k *= paramInt;
          paramInt = 3;
        } else if ((paramInt /= 30) < 12) {
          k *= paramInt;
          paramInt = 2;
        } else {
          if ((paramInt /= 12) > 10)
            paramInt = 10;
          k *= paramInt;
          paramInt = 1;
        }
      } else if ((this.m_1 & Integer.MIN_VALUE) == 0 && i == -1) {
        paramInt = 3;
      } else {
        while (j < this.m_iCaret && (this.m_text.charAt(j++) != this.m_chSep || ++paramInt <= 1));
        if (paramInt < 0)
          paramInt = 0;
        paramInt = this.m_5 >> paramInt * 6 & 0x7;
      }
    }
    if (paramInt < 1 || paramInt > 3)
      return;
    j = getField(paramInt) + k;
    if (paramInt == 3)
      if (j < 1) {
        if (getField(2) > 1) {
          setField(getField(2) - 1, 2);
        } else {
          setField(getField(1) - 1, 1);
          setField(12, 2);
        }
        j += PVDate0.maxDay(getField(1), getField(2) - 1);
      } else if (j > (k = PVDate0.maxDay(getField(1), getField(2) - 1))) {
        j -= k;
        if (getField(2) == 12) {
          setField(getField(1) + 1, 1);
          setField(1, 2);
        } else {
          setField(getField(2) + 1, 2);
        }
      }
    if (paramInt == 2) {
      if (j < 1) {
        j += 12;
        setField(getField(1) - 1, 1);
      }
      if (j > 12) {
        j -= 12;
        setField(getField(1) + 1, 1);
      }
      if (getField(3) > PVDate0.maxDay(getField(1), j - 1))
        setField(PVDate0.maxDay(getField(1), j - 1), 3);
    }
    setField(j, paramInt);
    limits();
    this.m_text = buildText();
    char c = Character.MIN_VALUE;
    if ((this.m_1 & Integer.MIN_VALUE) != 0) {
      j = this.m_5 & 0x3FFFF;
      if (j >> 15 == 6)
        j = j << 6 | 0x26;
      i = 0;
      for (byte b = 0; b < 6 && (j >> b++ * 3 & 0x7) != paramInt; b++) {
        while ((c = this.m_text.charAt(i++)) != this.m_chSep) {
          if (i >= this.m_text.length()) {
            b = 6;
            break;
          }
          if (c != ' ')
            continue;
          break;
        }
      }
      this.m_iCaret = this.m_iEndSel = this.m_iStartSel = i;
    }
    paint(null);
  }

  public int getMonthFormat() {
    return this.m_5 >> 18 & 0x3;
  }

  public void setMonthFormat(int paramInt) {
    this.m_5 = this.m_5 & 0xFFF3FFFF | (paramInt &= 0x3) << 18;
    if (paramInt == 3) {
      this.m_chSep = ' ';
      this.m_2 |= 0x40000000;
    }
    if (paramInt > 0)
      initMon();
    txt();
  }

  public void setShowDOW(int paramInt) {
    if (paramInt != getShowDOW())
      this.m_4 |= 0x8000;
    this.m_5 &= 0xFFEC7FFF;
    this.m_2 &= 0xBFFFFFFF;
    if (paramInt != 0 || this.m_chSep == ' ')
      this.m_2 |= 0x40000000;
    if (paramInt > 0) {
      this.m_5 |= ((paramInt & 0x1) == 0) ? 229376 : 196608;
      if (paramInt > 2)
        this.m_5 |= 0x100000;
      initDOW();
    }
    txt();
  }

  public int getShowDOW() {
    switch (this.m_5 >> 15 & 0x7) {
      case 6:
        return 1 + ((this.m_5 >> 20 & 0x1) << 1);
      case 7:
        return 2 + ((this.m_5 >> 20 & 0x1) << 1);
    }
    return 0;
  }

  public void setFreeEntry(boolean paramBoolean) {
    this.m_5 &= 0xFFDFFFFF;
    if (paramBoolean)
      this.m_5 |= 0x200000;
  }

  public boolean getFreeEntry() {
    return !((this.m_5 & 0x200000) == 0);
  }

  public int getDOW() {
    try {
      Calendar calendar = (Calendar)this.m_calendar.clone();
      calendar.set(getField(1), getField(2) - 1, getField(3));
      return calendar.get(7) - 1;
    } catch (Exception exception) {
      return -1;
    }
  }

  String buildText() {
    String str1 = "";
    if (this.m_text == null) {
      this.m_5 = 6370;
      this.m_text = str1;
    }
    int i = this.m_5;
    if (this.m_d == 0) {
      if ((this.m_1 & 0x88000000) == 0 || getFreeEntry())
        return str1;
      i &= 0x7FFF;
    }
    if ((this.m_2 & Integer.MIN_VALUE) == 0 && getUseLocale()) {
      Date date = getDate();
      if (date != null) {
        DateFormat dateFormat = DateFormat.getDateInstance(getLocaleFormat(), getLocale());
        dateFormat.setTimeZone(TimeZone.getDefault());
        str1 = dateFormat.format(date);
        i &= 0x38000;
        if ((this.m_5 & 0x1000000) != 0)
          i = 0;
      }
    }
    if ((this.m_2 & 0xC0000000) == -1073741824)
      i &= 0x7FFF;
    String str2 = null;
    for (byte b = 0; b < 6; b++) {
      int k;
      int j = i >> b * 3 & 0x7;
      switch (j) {
        case 4:
          str1 = String.valueOf(str1) + sep();
          break;
        case 6:
        case 7:
          str2 = getDOWStringAt(getDOW());
          if (str2.length() >= 2) {
            if (j == 6) {
              str1 = String.valueOf(str2) + ", " + str1;
              break;
            }
            str1 = String.valueOf(str1) + ", " + str2;
          }
          break;
        case 2:
          k = getMonthFormat();
          if (k > 0) {
            str2 = getMonthStringAt(getField(2) - 1);
            if (str2.length() == 0) {
              str2 = " ";
            } else if (k == 3 && getFormat() == 1 && sep() == ' ') {
              str2 = String.valueOf(str2) + ",";
            }
            str1 = String.valueOf(str1) + str2;
            break;
          }
        default:
          k = getField(j);
          if (k <= 0) {
            str1 = String.valueOf(str1) + " ";
            break;
          }
          if (k > 99 && !getShowCentury())
            k %= 100;
          str2 = String.valueOf(k);
          if (getLeadingZero() || j == 1) {
            if (k < 10)
              str2 = "0" + str2;
            if (j == 1 && getShowCentury())
              while (str2.length() < 4)
                str2 = "0" + str2;
          }
          str1 = String.valueOf(str1) + str2;
          if (getMonthFormat() == 3 && sep() == ' ')
            switch (getFormat()) {
              case 0:
                if (j == 3)
                  str1 = String.valueOf(str1) + ",";
                break;
              case 2:
              case 3:
                if (j == 1)
                  str1 = String.valueOf(str1) + ",";
                break;
            }
          break;
        case 0:
          break;
      }
    }
    return str1;
  }

  int buildFields(String paramString, boolean paramBoolean) {
    byte b1 = (paramString == null) ? 0 : paramString.length();
    if (b1 < 2) {
      this.m_d = 0;
      return -1;
    }
    if (b1 > 30) {
      b1 = 30;
      paramString = paramString.substring(0, 30);
    }
    char c = paramString.charAt(0);
    int i = -1;
    byte b2 = 0;
    byte b3 = 0;
    int j = this.m_5 & 0x3FFFF;
    if ((this.m_2 & 0xC0000000) == -1073741824) {
      j &= 0x7FFF;
    } else if (j >> 15 == 6) {
      j = j << 3 | 0x6;
    }
    for (byte b4 = 0; b4 < 6; b4++) {
      this.m_field = j >> b4 * 3 & 0x7;
      if (b2 >= b1)
        return -1;
      switch (this.m_field) {
        case 6:
          while (chekCh(c = paramString.charAt(b2)) <= -6) {
            if (++b2 >= b1)
              return -1;
          }
          if (c == ' ') {
            if (++b2 >= b1)
              return -1;
            c = paramString.charAt(b2);
          }
          if (!paramBoolean && b2 > this.m_iCaret) {
            this.m_iStartSel = this.m_iEndSel = this.m_iCaret = b2;
            return -1;
          }
          break;
        case 0:
        case 7:
          b4 = 7;
          if (!paramBoolean)
            this.m_iStartSel = this.m_iEndSel = this.m_iCaret = b2;
          return -1;
        case 4:
          if (++b2 < b1)
            c = paramString.charAt(b2);
          break;
        default:
          i = -1;
          b3 = b2;
          if (this.m_field == 2 && chekCh(c) == -6) {
            while (chekCh(c = paramString.charAt(b2)) == -6) {
              if (++b2 >= b1) {
                b4 = 7;
                break;
              }
            }
            i = findMonth(paramString.substring(b3, b2));
            if (i != -1) {
              i = (i & 0xF) + 1;
            } else if (c == ' ') {
              b2++;
            }
          } else {
            int k;
            while ((k = chekCh(c)) >= 0) {
              if (i < 0)
                i = 0;
              i = i * 10 + k;
              if (++b2 >= b1) {
                b4 = 7;
                break;
              }
              c = paramString.charAt(b2);
            }
            if (i == -1 && k == -1)
              b2++;
          }
          if (c == ',' && b2 + 1 < b1)
            c = paramString.charAt(++b2);
          if (paramBoolean) {
            if (i < 0 && (this.m_2 & Integer.MIN_VALUE) == 0) {
              b4 = 7;
              this.m_d = 0;
              break;
            }
            setField(i, this.m_field);
            break;
          }
          if (b2 >= this.m_iCaret) {
            this.m_iStartSel = b3;
            this.m_iEndSel = b2;
            return i;
          }
          break;
      }
    }
    return -1;
  }

  int findMonth(String paramString) {
    byte b = -1;
    int i = paramString.length() + 1;
    if (i > 15)
      i = 15;
    while (--i > 0) {
      b = -1;
      while (++b < 12) {
        String str = getMonthStringAt(b);
        if (str.length() >= i && paramString.substring(0, i).equalsIgnoreCase(str.substring(0, i)))
          return b | i << 4;
      }
    }
    return -1;
  }

  protected boolean filterChar(char paramChar) {
    if (paramChar == '\t' || paramChar == '\n' || paramChar == '\033') {
      this.m_2 &= Integer.MAX_VALUE;
      if (getFreeEntry())
        buildFields(this.m_text, true);
      refresh();
      return true;
    }
    int i = this.m_text.length();
    int j = -100;
    int k = chekCh(paramChar);
    boolean bool = (paramChar >= '\003') ? false : true;
    if (!bool) {
      if (getFreeEntry())
        return false;
      if (getMonthFormat() < 1 && k == -6)
        return true;
      if ((this.m_2 & 0xC0000000) == 1073741824) {
        this.m_2 |= Integer.MIN_VALUE;
        this.m_text = buildText();
        i = this.m_text.length();
        if ((j = this.m_iEndSel) > i)
          j = i;
        if ((getShowDOW() & 0x1) == 1 || this.m_iStartSel < 2) {
          this.m_iCaret = 0;
        } else if (this.m_iCaret > i) {
          this.m_iCaret = i;
        }
        buildFields(this.m_text, false);
        this.m_iCaret = this.m_iStartSel;
        if (this.m_iEndSel < j)
          this.m_iEndSel = j;
        j = -100;
      }
      this.m_2 |= Integer.MIN_VALUE;
      if (this.m_iEndSel != this.m_iStartSel) {
        if (!cutSel(k))
          return true;
        i = this.m_text.length();
      } else if (!getAutoSelectNextField() && this.m_iStartSel < i && this.m_text.charAt(this.m_iStartSel) == sep() && (k >= 0 || k == -6)) {
        j = buildFields(this.m_text, false);
        if (k == -6) {
          if (this.m_field != 2)
            j = -100;
        } else if (this.m_field == 1) {
          if (this.m_iStartSel + 4 == this.m_iEndSel) {
            j = -100;
          } else if (this.m_iStartSel + 2 == this.m_iEndSel && !getShowCentury()) {
            j = -100;
          }
        } else if (this.m_iStartSel + 2 == this.m_iEndSel) {
          j = -100;
        } else if (this.m_field == 3) {
          if (j > 3) {
            j = -100;
          } else if (j == 3 && k > 1) {
            j = -100;
          }
        } else if (this.m_field == 2) {
          if (chekCh(this.m_text.charAt(this.m_iStartSel)) == -6) {
            j = -100;
          } else if (j > 1) {
            j = -100;
          } else if (j == 1 && k > 2) {
            j = -100;
          }
        }
        if (j == -100)
          this.m_iCaret = this.m_iStartSel = ++this.m_iEndSel;
      }
    } else if (!getEnableArrows()) {
      return true;
    }
    if (j == -100)
      j = buildFields(this.m_text, false);
    if (this.m_field < 1 || this.m_field > 3)
      return true;
    if (bool) {
      this.m_1 &= 0xEFFFFFFF;
      fireAction(paramChar * 64);
      return true;
    }
    String str = this.m_text.substring(this.m_iStartSel, this.m_iEndSel);
    if (str.length() == 0)
      str = " ";
    bool = (!getInsertEditMode() && this.m_iCaret != i) ? false : true;
    switch (k) {
      case -6:
        if (this.m_field != 2 || getMonthFormat() < 1 || chekCh(str.charAt(0)) >= 0) {
          this.m_iStartSel = this.m_iCaret = this.m_iEndSel;
          buildFields(this.m_text, false);
          return true;
        }
        if (str.charAt(0) == ' ') {
          bool = false;
          this.m_iCaret = this.m_iStartSel;
        }
        break;
      case -7:
      case -5:
        if (this.m_iCaret <= this.m_iStartSel || this.m_iEndSel >= i) {
          this.m_iCaret = this.m_iEndSel = this.m_iStartSel;
          return true;
        }
        if (this.m_iCaret >= this.m_iEndSel) {
          this.m_iCaret = this.m_iStartSel = ++this.m_iEndSel;
          return true;
        }
        this.m_text = String.valueOf(this.m_text.substring(0, this.m_iStartSel)) + str.substring(0, this.m_iCaret - this.m_iStartSel) + this.m_text.substring(this.m_iEndSel);
        this.m_iEndSel = this.m_iStartSel = ++this.m_iCaret;
        return true;
      case -2:
        if (this.m_iCaret == this.m_iEndSel) {
          if (this.m_iCaret < i)
            this.m_iEndSel = this.m_iStartSel = ++this.m_iCaret;
          return true;
        }
        if (j < 0) {
          this.m_iEndSel = this.m_iStartSel = ++this.m_iCaret;
          return true;
        }
        paramChar = Character.MIN_VALUE;
        break;
      case -3:
        if (this.m_iCaret == this.m_iStartSel) {
          if (this.m_iCaret > 0)
            this.m_iEndSel = this.m_iStartSel = --this.m_iCaret;
          return true;
        }
        this.m_iCaret--;
        if (j < 0) {
          this.m_iEndSel = this.m_iStartSel = this.m_iCaret;
          return true;
        }
        paramChar = Character.MIN_VALUE;
        break;
      case -1:
        if (j >= 0)
          this.m_text = String.valueOf(this.m_text.substring(0, this.m_iStartSel)) + " " + this.m_text.substring(this.m_iEndSel);
        this.m_iCaret = this.m_iEndSel = this.m_iStartSel;
        setField(-1, this.m_field);
        return true;
      default:
        if (this.m_field == 2 && getMonthFormat() > 0 && chekCh(str.charAt(0)) == -6) {
          if (chekCh(str.charAt(0)) == -6 && this.m_iCaret == this.m_iEndSel) {
            if (this.m_iEndSel < i)
              this.m_iEndSel++;
            this.m_iStartSel = this.m_iEndSel;
          }
          this.m_iCaret = this.m_iEndSel;
          buildFields(this.m_text, false);
          return true;
        }
        if (j < 0) {
          bool = false;
          this.m_iCaret = this.m_iStartSel;
        }
        break;
    }
    if (paramChar == '\000') {
      bool = false;
      if (this.m_iEndSel - this.m_iStartSel == 1)
        paramChar = ' ';
    }
    i = this.m_iCaret - this.m_iStartSel + (bool ? 0 : 1);
    if (i > str.length())
      i--;
    str = String.valueOf(str.substring(0, this.m_iCaret - this.m_iStartSel)) + ((paramChar != '\000') ? String.valueOf(paramChar) : "") + str.substring(i);
    if (this.m_field == 2 && getMonthFormat() > 0 && chekCh(str.charAt(0)) == -6) {
      j = findMonth(str);
      if (j == -1) {
        str = " ";
      } else {
        i = j >> 4;
        str = getMonthStringAt(j &= 0xF).substring(0, i);
      }
      i = str.length();
      if (paramChar != '\000' && i < 3 && this.m_iCaret - this.m_iStartSel >= i)
        this.m_iCaret--;
      j++;
    } else {
      i = str.length();
      j = 2;
      if (this.m_field == 1 && getShowCentury())
        j = 4;
      if (i > j) {
        if (this.m_iCaret == this.m_iEndSel) {
          if (this.m_iCaret < this.m_text.length()) {
            this.m_iCaret++;
            buildFields(this.m_text, false);
          } else {
            this.m_iStartSel = this.m_iCaret = this.m_iEndSel;
          }
          return true;
        }
        str = str.substring(0, --i);
      }
      if (paramChar != '\000') {
        byte b = -1;
        for (j = 0; ++b < i; j = j * 10 + chekCh(str.charAt(b)));
        b = 31;
        if (this.m_field == 2)
          b = 12;
        if (this.m_field == 1)
          b = getShowCentury() ? 9999 : 99;
        if (j > b) {
          str = String.valueOf(paramChar);
          if (this.m_iCaret == this.m_iStartSel && ((paramChar == '1' && this.m_field == 2) || (paramChar == '3' && this.m_field == 3))) {
            str = String.valueOf(str) + "0";
          } else {
            str = "0" + str;
            this.m_iCaret = this.m_iEndSel;
          }
        }
      }
      j = -1;
      try {
        j = (new Integer(str)).intValue();
      } catch (Exception exception) {}
    }
    this.m_text = String.valueOf(this.m_text.substring(0, this.m_iStartSel)) + str + this.m_text.substring(this.m_iEndSel);
    setField(j, this.m_field);
    if (paramChar != '\000')
      this.m_iCaret++;
    this.m_iEndSel = this.m_iStartSel = this.m_iCaret;
    return true;
  }

  boolean cutSel(int paramInt) {
    int i = this.m_field = 0;
    while (i <= this.m_iStartSel) {
      if (this.m_text.charAt(i++) == sep())
        this.m_field++;
    }
    this.m_field = this.m_5 >> this.m_field * 6 & 0x7;
    if (paramInt == -5 || paramInt < -6 || (this.m_field != 2 && paramInt < -3))
      return false;
    boolean bool = ((i = this.m_iStartSel) != 0) ? false : true;
    if (!bool && this.m_text.charAt(i - 1) == sep())
      bool = true;
    String str = this.m_text.substring(0, i);
    while (i < this.m_iEndSel) {
      if (this.m_text.charAt(i++) == sep()) {
        if (bool)
          str = String.valueOf(str) + " ";
        str = String.valueOf(str) + sep();
        bool = true;
      }
    }
    if (bool)
      if (this.m_iEndSel == this.m_text.length()) {
        str = String.valueOf(str) + " ";
      } else if (this.m_text.charAt(i) == sep()) {
        str = String.valueOf(str) + " ";
      }
    this.m_text = String.valueOf(str) + this.m_text.substring(this.m_iEndSel);
    buildFields(this.m_text, true);
    if ((i = this.m_iStartSel) < this.m_text.length() && this.m_text.charAt(i) == sep())
      i++;
    this.m_iCaret = this.m_iEndSel = this.m_iStartSel = i;
    return !(paramInt < 0 && paramInt != -6);
  }

  void initMon() {
    this.m_mon = new String[12];
    for (byte b = 0; b < 12; b++)
      this.m_mon[b] = PVDate0.month(b, !(getMonthFormat() >= 2), true, getLocale());
  }

  void initDOW() {
    this.m_dow = new String[7];
    for (byte b = 0; b < 7; b++)
      this.m_dow[b] = PVDate0.dow(b, !(getShowDOW() >= 3), true, getLocale());
  }

  public String getMonthStringAt(int paramInt) {
    if (this.m_mon == null)
      initMon();
    return (paramInt < 0 || paramInt > 11) ? "" : this.m_mon[paramInt];
  }

  public void setMonthStringAt(String paramString, int paramInt) {
    if (this.m_mon == null)
      initMon();
    if (paramInt >= 0 && paramInt <= 11 && paramString != null)
      this.m_mon[paramInt] = (paramString.length() > 15) ? paramString.substring(0, 15) : paramString;
  }

  public String getDOWStringAt(int paramInt) {
    if (this.m_dow == null)
      initDOW();
    return (paramInt < 0 || paramInt > 6) ? "" : this.m_dow[paramInt];
  }

  public void setDOWStringAt(String paramString, int paramInt) {
    if (this.m_dow == null)
      initDOW();
    if (paramInt >= 0 && paramInt <= 6)
      this.m_dow[paramInt] = paramString;
  }

  void limits() {
    int i = getField(1);
    int j = getField(3);
    switch (getFormat()) {
      case 4:
      case 5:
        if (i < 100)
          setField(i = this.m_calendar.get(1), 1);
        break;
      case 6:
      case 7:
        if (j <= 0)
          setField(j = 1, 3);
        break;
    }
    if (i <= 0 || getField(2) <= 0 || j <= 0) {
      if (getAllowNull()) {
        this.m_d = 0;
        return;
      }
      if (i < 0)
        i = 0;
      if (i < 100)
        setField(i, 1);
      if (getField(2) < 1)
        setField(1, 2);
      if (getField(3) < 1)
        setField(1, 3);
    }
    if (this.m_null != 0 && this.m_null == this.m_d && getAllowNull()) {
      this.m_d = 0;
      return;
    }
    if (this.m_upper != 0 && this.m_upper < this.m_d) {
      this.m_d = this.m_upper;
      return;
    }
    if (this.m_lower != 0 && this.m_lower > this.m_d) {
      this.m_d = this.m_lower;
      return;
    }
    j = PVDate0.maxDay(getField(1), getField(2) - 1);
    if (getField(3) > j)
      setField(j, 3);
  }

  int chekCh(char paramChar) {
    return (paramChar >= '0' && paramChar <= '9') ? (paramChar - 48) : ((paramChar == sep()) ? -5 : ((paramChar == ' ') ? -1 : ((paramChar == '') ? -2 : ((paramChar == '\b') ? -3 : (Character.isLetter(paramChar) ? -6 : -7)))));
  }
}
