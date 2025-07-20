package pv.awt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class PVTime extends PVEdit {
  public static final int STOP = 0;

  public static final int START_CLOCK = 1;

  public static final int START_TIMER = 2;

  public static final int START_TIMER_BACK = 3;

  public static final int INCREMENT = 64;

  public static final int DECREMENT = 128;

  protected char m_chSep = ':';

  protected int m_delta;

  protected String m_ampm;

  protected transient Thread m_timer;

  transient int[] m_i;

  public PVTime() {
    super("");
    this.m_1 |= 0x1010040;
    setCalendar(new GregorianCalendar());
  }

  public Object getValue() {
    return getTime();
  }

  public void setValue(Object paramObject) {
    if (paramObject instanceof Date || paramObject == null) {
      setTime((Date)paramObject);
      return;
    }
    super.setValue(paramObject);
  }

  protected void init0() {
    this.m_i = new int[3];
    if (this.m_ampm == null)
      this.m_ampm = "AMPM";
    super.init0();
  }

  public void run() {
    int i = getTimer();
    if ((this.m_1 & Integer.MIN_VALUE) != 0 || i == 0) {
      super.run();
      return;
    }
    while (this.m_timer != null && ((this.m_1 & Integer.MIN_VALUE) == 0 || i != 0)) {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException interruptedException) {}
      if (this.m_timer != null) {
        if (i == 1) {
          setCalendar(new GregorianCalendar());
        } else {
          shiftField(2, !(i != 2));
        }
        super.fireAction((i == 3) ? 128 : 64);
        continue;
      }
      break;
    }
  }

  public void removeNotify() {
    timer(false);
    super.removeNotify();
  }

  public void timer(boolean paramBoolean) {
    if (paramBoolean) {
      if ((this.m_1 & Integer.MIN_VALUE) != 0 || this.m_timer != null || getTimer() == 0)
        return;
      stopCaret();
      try {
        this.m_timer = new Thread(this);
        this.m_timer.start();
        return;
      } catch (Exception exception) {
        return;
      }
    }
    if (this.m_timer != null) {
      try {
        this.m_timer.interrupt();
      } catch (Exception exception) {}
      try {
        this.m_timer.stop();
      } catch (Exception exception) {}
    }
    this.m_timer = null;
  }

  public void updateLocale(Locale paramLocale) {
    try {
      String str = null;
      DateFormat dateFormat = DateFormat.getTimeInstance(2, (paramLocale == null) ? getLocale() : paramLocale);
      if (dateFormat instanceof SimpleDateFormat)
        str = ((SimpleDateFormat)dateFormat).toPattern();
      if (str == null)
        return;
      this.m_2 &= 0xF7FFFF77;
      if (str.indexOf('s') < 0)
        this.m_2 |= 0x8;
      char c = 'h';
      int i = str.indexOf(c);
      this.m_2 |= 0x80;
      if (i < 0 && (i = str.indexOf(c = 'H')) < 0)
        return;
      if (i + 1 < str.length())
        i++;
      int j = str.indexOf(c, i);
      if (j > 0) {
        this.m_2 |= 0x8000000;
        i = j + 1;
      }
      c = str.charAt(i);
      if (i + 1 < str.length() && str.charAt(i + 1) != c) {
        i = chekCh(c);
        if (c != '\000' && (i == -1 || i == -6))
          this.m_chSep = c;
      }
      if (str.indexOf('a') > 0) {
        String[] arrayOfString = ((SimpleDateFormat)dateFormat).getDateFormatSymbols().getAmPmStrings();
        if (arrayOfString != null && arrayOfString.length > 1) {
          while (arrayOfString[0].length() < 2)
            arrayOfString[0] = String.valueOf(arrayOfString[0]) + ' ';
          while (arrayOfString[1].length() < 2)
            arrayOfString[1] = String.valueOf(arrayOfString[1]) + ' ';
          this.m_ampm = String.valueOf(arrayOfString[0].substring(0, 2)) + arrayOfString[1].substring(0, 2);
        }
      }
      setText(null);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  public void invalidate() {
    timer(!(checkFocus() != 0));
    super.invalidate();
  }

  protected void onSetFocus() {
    if ((this.m_1 & 0x88000000) != -2013265920) {
      timer(false);
      super.onSetFocus();
      setText(this.m_text);
    }
  }

  protected void onLostFocus() {
    timer(true);
    setText(this.m_text);
  }

  public void setTimer(int paramInt) {
    this.m_2 = this.m_2 & 0x3FFFFFFF | (paramInt & 0x3) << 30;
    timer(!(paramInt == 0));
  }

  public int getTimer() {
    return this.m_2 >>> 30;
  }

  public void setSeparator(String paramString) {
    if (paramString != null && paramString.length() > 0 && !Character.isLetterOrDigit(paramString.charAt(0)) && paramString.charAt(0) != ' ') {
      this.m_chSep = paramString.charAt(0);
      setText(null);
    }
  }

  public String getSeparator() {
    return String.valueOf(this.m_chSep);
  }

  public void setShowSeconds(boolean paramBoolean) {
    this.m_2 |= 0x8;
    if (paramBoolean)
      this.m_2 &= 0xFFFFFFF7;
    setText(null);
  }

  public boolean getShowSeconds() {
    return !((this.m_2 & 0x8) != 0);
  }

  public void setLeadingZero(boolean paramBoolean) {
    this.m_2 &= 0xF7FFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x8000000;
    setText(null);
  }

  public boolean getLeadingZero() {
    return !((this.m_2 & 0x8000000) == 0);
  }

  public void setAllowNull(boolean paramBoolean) {
    this.m_2 |= 0x800;
    if (paramBoolean)
      this.m_2 &= 0xFFFFF7FF;
  }

  public boolean getAllowNull() {
    return !((this.m_2 & 0x800) != 0);
  }

  public void setBlankForNull(boolean paramBoolean) {
    this.m_4 |= 0x40000000;
    if (paramBoolean)
      this.m_4 &= 0xBFFFFFFF;
    paint(null);
  }

  public boolean getBlankForNull() {
    return !((this.m_4 & 0x40000000) != 0);
  }

  public void setTwelveHours(boolean paramBoolean) {
    this.m_2 |= 0x80;
    if (paramBoolean)
      this.m_2 &= 0xFFFFFF7F;
    setText(null);
  }

  public boolean getTwelveHours() {
    return !((this.m_2 & 0x80) != 0);
  }

  public void setAMPM(String paramString) {
    StringBuffer stringBuffer = new StringBuffer(this.m_ampm);
    int i = paramString.length();
    if (i > 4)
      i = 4;
    while (i-- > 0) {
      switch (chekCh(paramString.charAt(i))) {
        case -6:
        case -4:
        case -3:
          stringBuffer.setCharAt(i, paramString.charAt(i));
      }
    }
    this.m_ampm = stringBuffer.toString();
    setText(null);
  }

  public String getAMPM() {
    return this.m_ampm;
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
    this.m_1 &= 0xEFFFFFFE;
    if (paramString != null)
      setFieldsFromText(paramString, true);
    paramString = "";
    char c;
    for (c = Character.MIN_VALUE; c < (getShowSeconds() ? '\003' : '\002'); c++) {
      if (c > Character.MIN_VALUE)
        paramString = String.valueOf(paramString) + this.m_chSep;
      int i = this.m_i[c];
      if (c == Character.MIN_VALUE && getTwelveHours() && (i %= 12) == 0)
        i = 12;
      String str = String.valueOf(i);
      if (this.m_i[c] < 0) {
        if (getBlankForNull() && (this.m_1 & 0x88000000) == 0) {
          c = 'Ϩ';
          break;
        }
        str = "  ";
      }
      if (str.length() == 1 && (c > '\000' || getLeadingZero()))
        str = "0" + str;
      paramString = String.valueOf(paramString) + str;
    }
    if (c == 'Ϩ') {
      paramString = "";
    } else {
      c = (this.m_i[0] > 11) ? '\002' : Character.MIN_VALUE;
      if (getTwelveHours())
        paramString = String.valueOf(paramString) + " " + this.m_ampm.substring(c, c + 2);
    }
    this.m_text = paramString;
    if (checkFocus() == 0)
      paint(null);
  }

  public void setCalendar(Calendar paramCalendar) {
    if (paramCalendar != null) {
      this.m_i[0] = paramCalendar.get(11);
      this.m_i[1] = paramCalendar.get(12);
      this.m_i[2] = paramCalendar.get(13);
    } else {
      for (byte b = 0; b < 3; b++)
        this.m_i[b] = getAllowNull() ? -1 : 0;
    }
    setText(null);
  }

  public Calendar getCalendar() {
    setFieldsFromText(this.m_text, true);
    if (isNull())
      return null;
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    return new GregorianCalendar(gregorianCalendar.get(1), gregorianCalendar.get(2), gregorianCalendar.get(5), this.m_i[0], this.m_i[1], this.m_i[2]);
  }

  public void setTime(Date paramDate) {
    GregorianCalendar gregorianCalendar = null;
    if (paramDate != null) {
      gregorianCalendar = new GregorianCalendar();
      gregorianCalendar.setTime(paramDate);
    }
    setCalendar(gregorianCalendar);
  }

  public Date getTime() {
    Calendar calendar = getCalendar();
    return (calendar == null) ? null : calendar.getTime();
  }

  public boolean isNull() {
    return !(this.m_i[0] >= 0 && this.m_i[1] >= 0 && this.m_i[2] >= 0);
  }

  public int getField(int paramInt) {
    if (paramInt > 2 || paramInt < 0)
      return -1;
    setFieldsFromText(this.m_text, true);
    return this.m_i[paramInt];
  }

  public void setField(int paramInt1, int paramInt2) {
    if (paramInt1 > 2 || paramInt1 < 0 || paramInt2 < 0 || paramInt2 > 59 || (paramInt1 == 0 && paramInt2 > 23))
      return;
    setFieldsFromText(this.m_text, true);
    this.m_i[paramInt1] = paramInt2;
    setText(null);
  }

  void setFieldsFromText(String paramString, boolean paramBoolean) {
    byte b = -1;
    byte b1 = 0;
    int i = paramString.length();
    for (b1 = 0; b1 < 3; b1++)
      this.m_i[b1] = getAllowNull() ? -1 : 0;
    if (!getShowSeconds())
      this.m_i[2] = 0;
    if (i < 3)
      return;
    b1 = 0;
    while (++b < 3) {
      while (b1 < i) {
        int j = chekCh(paramString.charAt(b1++));
        if (j != -1) {
          if (j >= 0) {
            if (this.m_i[b] < 0)
              this.m_i[b] = 0;
            this.m_i[b] = this.m_i[b] * 10 + j;
            continue;
          }
          if (j == -3 || j == -4) {
            i = this.m_i[0];
            if (i >= 1 || paramBoolean) {
              if (i < 1 || i > 12) {
                this.m_i[0] = 24;
              } else if (j == -4) {
                if (i < 12)
                  this.m_i[0] = this.m_i[0] + 12;
              } else if (i == 12) {
                this.m_i[0] = 0;
              }
              b = 5;
            }
          }
          break;
        }
      }
    }
    if (paramBoolean) {
      b = 24;
      for (b1 = 0; b1 < 3; b1++) {
        if (this.m_i[b1] >= b || this.m_i[b1] < 0) {
          if (getAllowNull()) {
            for (b = 0; b < 3; b++)
              this.m_i[b] = -1;
            return;
          }
          this.m_i[b1] = 0;
        }
        b = 60;
      }
    }
  }

  int chekCh(char paramChar) {
    return (paramChar >= '0' && paramChar <= '9') ? (paramChar - 48) : ((paramChar == this.m_chSep) ? -5 : ((paramChar == ' ') ? -1 : ((paramChar == '\b' || paramChar == '') ? -2 : ((paramChar == this.m_ampm.toUpperCase().charAt(0) || paramChar == this.m_ampm.toLowerCase().charAt(0)) ? -3 : ((paramChar == this.m_ampm.toUpperCase().charAt(2) || paramChar == this.m_ampm.toLowerCase().charAt(2)) ? -4 : -6)))));
  }

  protected boolean filterChar(char paramChar) {
    if (paramChar < '\003') {
      if (getEnableArrows())
        fireAction((paramChar == '\001') ? 64 : 128);
      return true;
    }
    if (paramChar == '\n') {
      setText(this.m_text);
      return true;
    }
    int i = this.m_iCaret;
    if (paramChar == '\b') {
      if (this.m_iEndSel == 0)
        return true;
      this.m_iCaret--;
    }
    StringBuffer stringBuffer = new StringBuffer(this.m_text.toString());
    byte b = -1;
    int j = stringBuffer.length();
    int k = chekCh(paramChar);
    if (j == 0)
      return true;
    if (this.m_iEndSel != this.m_iStartSel) {
      if (k < -4)
        return true;
      if (k < -2) {
        if (this.m_iStartSel < j - 3)
          return true;
        this.m_iStartSel = this.m_iEndSel = this.m_iCaret;
      } else {
        int m = j - 1;
        if (stringBuffer.charAt(this.m_iStartSel) == this.m_chSep)
          this.m_iStartSel++;
        for (m = (m >= this.m_iEndSel) ? (this.m_iEndSel - 1) : m; m >= this.m_iStartSel; m--) {
          if (chekCh(stringBuffer.charAt(m)) > -2) {
            if (m == this.m_iStartSel && k >= 0) {
              stringBuffer.setCharAt(this.m_iStartSel++, paramChar);
              break;
            }
            stringBuffer.setCharAt(m, ' ');
          }
        }
        this.m_iCaret = this.m_iStartSel;
      }
    } else {
      int m;
      if (k < -4) {
        if (this.m_iCaret < j && !Character.isLetter(paramChar))
          this.m_iEndSel = this.m_iStartSel = ++this.m_iCaret;
        return true;
      }
      int n = this.m_iCaret;
      b = -1;
      while (n > 0 && stringBuffer.charAt(n - 1) != this.m_chSep)
        n--;
      char c = stringBuffer.charAt(n);
      boolean bool = (this.m_iCaret < j) ? stringBuffer.charAt(this.m_iCaret) : true;
      switch (k) {
        case -1:
          stringBuffer.setCharAt(n, ' ');
          if (n + 1 < j && stringBuffer.charAt(n + 1) != this.m_chSep)
            stringBuffer.setCharAt(n + 1, ' ');
          this.m_iCaret = n;
          break;
        case -2:
          if (this.m_iCaret >= j)
            return true;
          if (chekCh(bool) >= 0)
            stringBuffer.setCharAt(this.m_iCaret, ' ');
          if (paramChar == '')
            this.m_iCaret++;
          break;
        case -3:
          if (this.m_iCaret < j - 3)
            return true;
          if (chekCh(stringBuffer.charAt(j - 2)) == -4) {
            stringBuffer.setCharAt(j - 2, this.m_ampm.charAt(0));
            stringBuffer.setCharAt(j - 1, this.m_ampm.charAt(1));
          }
          break;
        case -4:
          if (this.m_iCaret < j - 3)
            return true;
          if (chekCh(stringBuffer.charAt(j - 2)) == -3) {
            stringBuffer.setCharAt(j - 2, this.m_ampm.charAt(2));
            stringBuffer.setCharAt(j - 1, this.m_ampm.charAt(3));
          }
          break;
        default:
          if ((m = this.m_iCaret - n) > 2)
            return true;
          if (m == 0) {
            if (paramChar > '5' || (n == 0 && paramChar >= '2')) {
              if (n + 1 < j && stringBuffer.charAt(n + 1) != this.m_chSep)
                stringBuffer.setCharAt(n + 1, ' ');
              b = 0;
            }
            if (c == ' ' || !getInsertEditMode())
              b = 0;
            if (n == 0 && c > '2') {
              if (paramChar > '2')
                b = 0;
              if (getTwelveHours() && paramChar >= '1')
                b = 0;
              if (paramChar == '2' && c > '4')
                b = 0;
            }
            if (b != 0)
              switch (j - n) {
                case 1:
                  b = 1;
                  break;
                case 2:
                  b = 2;
                  break;
                default:
                  if (stringBuffer.charAt(n + 1) == this.m_chSep) {
                    b = 1;
                    break;
                  }
                  switch (chekCh(stringBuffer.charAt(n + 2))) {
                    case -4:
                    case -3:
                      b = 1;
                      break;
                    case -5:
                    case -1:
                      b = 2;
                      break;
                  }
                  break;
              }
          }
          if (m == 2) {
            if (this.m_iCaret == j)
              return true;
            if (bool == this.m_chSep)
              if (this.m_iCaret > 2 && c == ' ' && stringBuffer.charAt(this.m_iCaret - 1) == ' ') {
                this.m_iCaret--;
                b = 0;
              } else {
                this.m_iCaret++;
                filterChar(paramChar);
                return true;
              }
          }
          if (m == 1) {
            if (n == 0) {
              if (c < '1')
                b = (bool == this.m_chSep) ? 1 : 0;
              if (c > '0' && b < 0 && chekCh(bool) < 0) {
                if (c == '2' && (getTwelveHours() || paramChar > '4'))
                  b = -10;
                if (c >= '1' && getTwelveHours() && paramChar > '2')
                  b = -10;
                if (c > '2')
                  b = -10;
              }
            }
            if (c > '5')
              b = -10;
            if (b == -10) {
              if (this.m_iCaret + 1 < j) {
                if (stringBuffer.charAt(++this.m_iCaret) == this.m_chSep)
                  this.m_iCaret++;
                filterChar(paramChar);
              }
              return true;
            }
            if (b != 0)
              if (this.m_iCaret == j) {
                b = 1;
              } else if (bool == this.m_chSep) {
                b = 1;
              } else if (this.m_iCaret + 1 == j) {
                b = 0;
              } else if (c == ' ' && getInsertEditMode()) {
                b = 3;
              } else if (this.m_iCaret + 1 < j) {
                m = chekCh(stringBuffer.charAt(this.m_iCaret + 1));
                if (m == -3 || m == -4)
                  b = 1;
              }
            if (b < 0)
              b = 0;
          }
          switch (b) {
            case 0:
              stringBuffer.setCharAt(this.m_iCaret, paramChar);
              break;
            case 1:
              stringBuffer.insert(this.m_iCaret, paramChar);
              break;
            case 2:
              stringBuffer.setCharAt(n + 1, c);
              stringBuffer.setCharAt(n, paramChar);
              break;
            case 3:
              stringBuffer.setCharAt(n, paramChar);
              this.m_iCaret--;
              break;
          }
          this.m_iCaret++;
          break;
      }
    }
    String str = new String(stringBuffer);
    setFieldsFromText(str, !(k != -2));
    byte b2 = 24;
    for (byte b1 = 0; b1 < 3; b1++) {
      if (this.m_i[b1] >= b2)
        b1 = 100;
      b2 = 60;
    }
    if (b1 > 3) {
      this.m_iCaret = i;
    } else {
      this.m_iStartSel = this.m_iEndSel = this.m_iCaret;
      this.m_text = str;
    }
    return true;
  }

  public void fireAction(int paramInt) {
    if ((paramInt & 0xC0) != 0)
      shiftField(-2, !((paramInt & 0x40) == 0));
    super.fireAction(paramInt);
  }

  public void shiftField(int paramInt, boolean paramBoolean) {
    if (paramInt > 3)
      return;
    int i = paramInt;
    int j = paramBoolean ? 1 : -1;
    int k = this.m_text.length() - 1;
    int m = paramInt;
    if (this.m_iCaret < k)
      k = this.m_iCaret;
    if (paramInt < 0) {
      paramInt = 0;
      if (this.m_delta > 0 && i == -2) {
        if ((paramInt = this.m_delta) < 60) {
          j *= paramInt;
          paramInt = 2;
        } else if ((paramInt /= 60) < 60) {
          j *= paramInt;
          paramInt = 1;
        } else if ((paramInt /= 60) < 7) {
          j *= paramInt;
          paramInt = 0;
        } else {
          paramInt = 3;
        }
      } else if ((this.m_1 & Integer.MIN_VALUE) == 0 && i == -2) {
        paramInt = 1;
      } else {
        for (i = 0; i <= k; i++) {
          switch (chekCh(this.m_text.charAt(i))) {
            case -5:
              paramInt++;
              break;
            case -4:
            case -3:
              paramInt = 3;
              i = k;
              break;
          }
        }
      }
    }
    setFieldsFromText(this.m_text, false);
    for (i = 0; i < 3; i++) {
      if (this.m_i[i] < 0)
        this.m_i[i] = 0;
    }
    if (paramInt == 3) {
      this.m_i[0] = this.m_i[0] + 12;
      if (this.m_i[0] > 23)
        this.m_i[0] = this.m_i[0] - 24;
    } else {
      this.m_i[paramInt] = this.m_i[paramInt] + j;
      if (this.m_i[paramInt] < 0) {
        switch (paramInt) {
          case 0:
            this.m_i[0] = this.m_i[0] + 24;
            break;
          case 1:
            this.m_i[0] = this.m_i[0] - 1;
            if (this.m_i[0] - 1 < 0)
              this.m_i[0] = 23;
            this.m_i[1] = this.m_i[1] + 60;
            break;
          case 2:
            this.m_i[1] = this.m_i[1] - 1;
            if (this.m_i[1] - 1 < 0) {
              this.m_i[0] = this.m_i[0] - 1;
              if (this.m_i[0] - 1 < 0)
                this.m_i[0] = 23;
              this.m_i[1] = 59;
            }
            this.m_i[2] = this.m_i[2] + 60;
            break;
        }
      } else if (this.m_i[paramInt] > 23) {
        switch (paramInt) {
          case 0:
            this.m_i[0] = this.m_i[0] - 24;
            break;
          case 1:
            if (this.m_i[1] > 59) {
              this.m_i[0] = this.m_i[0] + 1;
              if (this.m_i[0] + 1 > 23)
                this.m_i[0] = 0;
              this.m_i[1] = this.m_i[1] - 60;
            }
            break;
          case 2:
            if (this.m_i[2] > 59) {
              this.m_i[1] = this.m_i[1] + 1;
              if (this.m_i[1] + 1 > 59) {
                this.m_i[0] = this.m_i[0] + 1;
                if (this.m_i[0] + 1 > 23)
                  this.m_i[0] = 0;
                this.m_i[1] = 0;
              }
              this.m_i[2] = this.m_i[2] - 60;
            }
            break;
        }
      }
    }
    setText(null);
    k = this.m_text.length();
    i = 0;
    if (paramInt == 3) {
      this.m_iCaret = k - 1;
    } else {
      this.m_iCaret = 0;
      while (this.m_iCaret < k && i != paramInt) {
        if (this.m_text.charAt(this.m_iCaret) == this.m_chSep)
          i++;
        this.m_iCaret++;
      }
    }
    this.m_iStartSel = this.m_iEndSel = this.m_iCaret;
    if (m != -1001)
      paint(null);
  }
}
