package pv.awt;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Locale;

public class PVCurrency extends PVEdit {
  public static final int LIMIT = 32;

  public static final int INCREMENT = 64;

  public static final int DECREMENT = 128;

  public double m_null = 2.2E-306D;

  Color m_cN;

  int m_5 = 10829834;

  protected char m_chDec = '.';

  protected char m_chTho = ',';

  protected double m_max = 1000000.0D;

  protected double m_min = -1000000.0D;

  protected double m_delta = 1.0D;

  protected char m_chK;

  protected char m_chM;

  protected byte m_iDec = 2;

  protected String m_symbol = "$";

  public PVCurrency(double paramDouble) {
    super("");
    this.m_1 |= 0x1012000;
    setDouble(paramDouble);
  }

  public PVCurrency() {
    this(2.2E-306D);
  }

  public void updateLocale(Locale paramLocale) {
    byte b = -1;
    int i = 0;
    boolean bool1 = false;
    boolean bool2 = false;
    char c = Character.MIN_VALUE;
    String str1 = null;
    String str2 = "";
    if (paramLocale == null)
      paramLocale = getLocale();
    try {
      str1 = NumberFormat.getCurrencyInstance(paramLocale).format(1234.5678D);
    } catch (Exception exception) {}
    byte b1 = (str1 == null) ? 0 : str1.length();
    if (!b1)
      return;
    double d = getDouble();
    this.m_4 |= 0x4000;
    this.m_iDec = 0;
    while (++b < b1) {
      if (chekCh(c = str1.charAt(b)) == 0) {
        bool1 = true;
        if (b == 0)
          i++;
        if (bool2) {
          this.m_iDec = (byte)(this.m_iDec + 1);
          continue;
        }
        if (c == '1') {
          this.m_2 |= 0x8000000;
          if (chekCh(c = str1.charAt(++b)) != 0) {
            this.m_chTho = c;
            this.m_2 &= 0xF7FFFFFF;
          }
          continue;
        }
        if (c == '4') {
          bool2 = true;
          if (b + 1 < b1 && chekCh(c = str1.charAt(b + 1)) != 0)
            this.m_chDec = c;
        }
        continue;
      }
      if (!bool1 || b + 1 >= b1 || chekCh(str1.charAt(b + 1)) != 0) {
        bool1 = false;
        if (c == ' ') {
          i |= 0x2;
          continue;
        }
        str2 = String.valueOf(str2) + c;
      }
    }
    setFormatPositive(i);
    try {
      str1 = NumberFormat.getCurrencyInstance(paramLocale).format(-5.0D);
    } catch (Exception exception) {}
    b1 = (str1 == null) ? 0 : str1.length();
    if (b1 > 2) {
      bool1 = false;
      b = 0;
      while (++b < b1) {
        if (str1.charAt(b) == ' ')
          bool1 = true;
      }
      switch (str1.charAt(0)) {
        case '(':
          i = (str1.charAt(1) == '5') ? 4 : 0;
          break;
        case '-':
          if (str1.charAt(1) == '5') {
            i = bool1 ? 8 : 5;
            break;
          }
          i = bool1 ? 9 : 1;
          break;
        case '5':
          if (bool1) {
            i = 10;
            break;
          }
          i = (str1.charAt(b1 - 1) == '-') ? 7 : 6;
          break;
        default:
          i = (str1.charAt(b1 - 1) == '-') ? 3 : 2;
          break;
      }
      setFormatNegative(i);
    }
    setSymbol(str2);
    this.m_4 &= 0xFFFFBFFF;
    setDouble(d);
  }

  public Object getValue() {
    return getDoubleObj();
  }

  public void setValue(Object paramObject) {
    if (paramObject instanceof Number) {
      setDouble(((Number)paramObject).doubleValue());
      return;
    }
    super.setValue(paramObject);
  }

  public void setDefaultNull(double paramDouble) {
    this.m_null = paramDouble;
  }

  public double getDefaultNull() {
    return this.m_null;
  }

  public Color getNegativeForeground() {
    return (this.m_cN == null) ? getForeground() : this.m_cN;
  }

  public void setNegativeForeground(Color paramColor) {
    this.m_cN = paramColor;
  }

  Color foreClr() {
    return (this.m_cN == null || (this.m_5 & 0x1000000) == 0) ? super.foreClr() : this.m_cN;
  }

  public void setDouble(double paramDouble) {
    if (paramDouble == this.m_null || paramDouble != paramDouble) {
      if (getAllowNull()) {
        super.setText("");
        return;
      }
      paramDouble = 0.0D;
    }
    for (byte b = 0; b < this.m_iDec; b++)
      paramDouble *= 10.0D;
    this.m_5 &= 0xFFF1FF;
    paramDouble += (paramDouble >= 0.0D) ? 0.5D : -0.5D;
    super.setText(buildText(limit((long)paramDouble)));
  }

  public double getDouble() {
    long l = getNumber(this.m_text, true);
    if (l == Long.MIN_VALUE)
      return this.m_null;
    int i = 1;
    for (byte b = 0; b < this.m_iDec; b++)
      i *= 10;
    return l / i;
  }

  public void setLong(long paramLong) {
    if (paramLong == Long.MIN_VALUE) {
      setDouble(this.m_null);
      return;
    }
    for (byte b = 0; b < this.m_iDec; b++)
      paramLong *= 10L;
    this.m_5 &= 0xFFF1FF;
    super.setText(buildText(limit(paramLong)));
  }

  public long getLong() {
    long l = getNumber(this.m_text, true);
    if (l == Long.MIN_VALUE)
      return 0L;
    for (byte b = 0; b < this.m_iDec; b++)
      l /= 10L;
    return l;
  }

  public void setLongBy10000(long paramLong) {
    for (byte b = this.m_iDec; b < 4; b++)
      paramLong /= 10L;
    this.m_5 &= 0xFFF1FF;
    super.setText(buildText(limit(paramLong)));
  }

  public long getLongBy10000() {
    long l = getNumber(this.m_text, true);
    if (l == Long.MIN_VALUE)
      return 0L;
    for (byte b = this.m_iDec; b < 4; b++)
      l *= 10L;
    return l;
  }

  public Double getDoubleObj() {
    return isNull() ? null : new Double(getDouble());
  }

  public void setDoubleObj(Double paramDouble) {
    setDouble((paramDouble == null) ? this.m_null : paramDouble.doubleValue());
  }

  public boolean isNull() {
    return !(getNumber(this.m_text, true) != Long.MIN_VALUE);
  }

  public void setDecimals(int paramInt) {
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt > 6)
      paramInt = 6;
    long l = getNumber(this.m_text, false);
    byte b = this.m_iDec;
    this.m_iDec = (byte)paramInt;
    while (b > paramInt) {
      paramInt++;
      l /= 10L;
    }
    while (b < paramInt) {
      paramInt--;
      l *= 10L;
    }
    setText(buildText(l));
  }

  public int getDecimals() {
    return this.m_iDec;
  }

  public void setDelta(double paramDouble) {
    this.m_delta = paramDouble;
  }

  public double getDelta() {
    return this.m_delta;
  }

  public void setMaxValue(double paramDouble) {
    if (paramDouble > 9.223372036854776E18D)
      paramDouble = 9.223372036854776E18D;
    if (paramDouble < -9.223372036854776E18D)
      paramDouble = -9.223372036854776E18D;
    this.m_max = paramDouble;
    setUpperValidation(true);
  }

  public double getMaxValue() {
    return this.m_max;
  }

  public void setMinValue(double paramDouble) {
    if (paramDouble > 9.223372036854776E18D)
      paramDouble = 9.223372036854776E18D;
    if (paramDouble < -9.223372036854776E18D)
      paramDouble = -9.223372036854776E18D;
    this.m_min = paramDouble;
    setLowerValidation(true);
  }

  public double getMinValue() {
    return this.m_min;
  }

  public void setUpperValidation(boolean paramBoolean) {
    this.m_2 &= 0xFFFFFFF7;
    if (paramBoolean) {
      this.m_2 |= 0x8;
      setText(this.m_text);
    }
  }

  public boolean getUpperValidation() {
    return !((this.m_2 & 0x8) == 0);
  }

  public void setLowerValidation(boolean paramBoolean) {
    this.m_2 &= 0xFFFFFF7F;
    if (paramBoolean) {
      this.m_2 |= 0x80;
      setText(this.m_text);
    }
  }

  public boolean getLowerValidation() {
    return !((this.m_2 & 0x80) == 0);
  }

  public void setToLimit(boolean paramBoolean) {
    this.m_2 &= Integer.MAX_VALUE;
    if (paramBoolean)
      this.m_2 |= Integer.MIN_VALUE;
  }

  public boolean getToLimit() {
    return !((this.m_2 & Integer.MIN_VALUE) == 0);
  }

  public void setValidateLimitsOnKey(boolean paramBoolean) {
    this.m_2 &= 0xBFFFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x40000000;
  }

  public boolean getValidateLimitsOnKey() {
    return !((this.m_2 & 0x40000000) == 0);
  }

  public String get1000Multiplier() {
    return String.valueOf(this.m_chK);
  }

  public void set1000Multiplier(String paramString) {
    this.m_chK = Character.MIN_VALUE;
    if (paramString != null && paramString.length() > 0)
      this.m_chK = Character.toUpperCase(paramString.charAt(0));
  }

  public String get1000000Multiplier() {
    return String.valueOf(this.m_chM);
  }

  public void set1000000Multiplier(String paramString) {
    this.m_chM = Character.MIN_VALUE;
    if (paramString != null && paramString.length() > 0)
      this.m_chM = Character.toUpperCase(paramString.charAt(0));
  }

  public void setDecimalSeparator(String paramString) {
    char c = '0';
    if (paramString != null && paramString.length() > 0)
      c = paramString.charAt(0);
    long l = getNumber(this.m_text, false);
    String str = getSymbol();
    if (c == this.m_chTho) {
      this.m_chTho = (c == ',') ? '.' : ',';
    } else if (chekCh(c) != -7) {
      return;
    }
    this.m_chDec = c;
    this.m_4 |= 0x4000;
    setSymbol(str);
    this.m_4 &= 0xFFFFBFFF;
    setText(buildText(l));
  }

  public String getDecimalSeparator() {
    return String.valueOf(this.m_chDec);
  }

  public void setThousandSeparator(String paramString) {
    char c = '0';
    if (paramString != null && paramString.length() > 0 && chekCh(c = paramString.charAt(0)) == -7) {
      String str = getSymbol();
      this.m_chTho = c;
      setSymbol(str);
    }
  }

  public String getThousandSeparator() {
    return String.valueOf(this.m_chTho);
  }

  public void setThousandSeparators(boolean paramBoolean) {
    this.m_2 |= 0x8000000;
    if (paramBoolean)
      this.m_2 &= 0xF7FFFFFF;
    setText(this.m_text);
  }

  public boolean getThousandSeparators() {
    return !((this.m_2 & 0x8000000) != 0);
  }

  public void setSymbol(String paramString) {
    StringBuffer stringBuffer = new StringBuffer((paramString == null) ? "" : paramString);
    int i = stringBuffer.length();
    if (i > 12)
      stringBuffer.setLength(i = 12);
    while (i-- > 0) {
      char c;
      int j;
      if ((j = chekCh(c = stringBuffer.charAt(i))) != -7)
        stringBuffer.setCharAt(i, (j >= -2 && j <= -5) ? 191 : ((c == this.m_chDec) ? 1 : 2));
    }
    this.m_symbol = new String(stringBuffer);
    setText(this.m_text);
  }

  public String getSymbol() {
    return fix(this.m_symbol);
  }

  public String getText() {
    return fix(this.m_text);
  }

  protected String text() {
    return fix(super.text());
  }

  String fix(String paramString) {
    StringBuffer stringBuffer = new StringBuffer((paramString == null) ? "" : paramString);
    int i = stringBuffer.length();
    while (i-- > 0) {
      char c;
      if ((c = stringBuffer.charAt(i)) <= '\002')
        stringBuffer.setCharAt(i, (c == '\001') ? this.m_chDec : this.m_chTho);
    }
    return new String(stringBuffer);
  }

  public void setFormatPositive(int paramInt) {
    this.m_5 &= 0x1FFF000;
    switch (paramInt & 0x3) {
      case 0:
        this.m_5 |= 0xA;
        break;
      case 1:
        this.m_5 |= 0x11;
        break;
      case 2:
        this.m_5 |= 0x72;
        break;
      case 3:
        this.m_5 |= 0xB1;
        break;
    }
    setText(this.m_text);
  }

  public int getFormatPositive() {
    byte b = -1;
    while (++b < 3 && (13050954 >> b * 6 & 0x3F) != (this.m_5 & 0x3F));
    return b;
  }

  public void setFormatNegative(int paramInt) {
    if (paramInt > 10 || paramInt < 0)
      return;
    this.m_5 &= 0x10001FF;
    switch (paramInt) {
      case 0:
        this.m_5 |= 0xA54000;
        break;
      case 1:
        this.m_5 |= 0x53000;
        break;
      case 2:
        this.m_5 |= 0x5A000;
        break;
      case 3:
        this.m_5 |= 0xCA000;
        break;
      case 4:
        this.m_5 |= 0xA8C000;
        break;
      case 5:
        this.m_5 |= 0x8B000;
        break;
      case 6:
        this.m_5 |= 0x99000;
        break;
      case 7:
        this.m_5 |= 0xD1000;
        break;
      case 8:
        this.m_5 |= 0x58B000;
        break;
      case 9:
        this.m_5 |= 0x393000;
        break;
      case 10:
        this.m_5 |= 0x6B1000;
        break;
    }
    setText(this.m_text);
  }

  public int getFormatNegative() {
    int i = 23635540;
    int j = this.m_5 >> 12 & 0x1FF;
    byte b = -1;
    while (++b < 10) {
      if (b == 3)
        i = 36509898;
      if (b == 6)
        i = 103654041;
      if (b == 9)
        i = 91027;
      if ((i >> b % 3 * 9 & 0x1FF) != j)
        continue;
      break;
    }
    return b;
  }

  public void setText(String paramString) {
    if ((this.m_4 & 0x4000) != 0)
      return;
    this.m_5 &= 0xFFF1FF;
    long l = getNumber(paramString, true);
    if (l != Long.MIN_VALUE) {
      l = limit(l);
      if (l == 0L && paramString.length() > 0 && chekCh(paramString.charAt(0)) == -2)
        this.m_5 |= 0x1000000;
      paramString = buildText(l);
    } else {
      paramString = "";
    }
    super.setText(paramString);
  }

  public boolean getAllowNull() {
    return !((this.m_2 & 0x800) != 0);
  }

  public void setAllowNull(boolean paramBoolean) {
    this.m_2 |= 0x800;
    if (paramBoolean)
      this.m_2 &= 0xFFFFF7FF;
  }

  public void setEnableArrows(boolean paramBoolean) {
    this.m_1 |= 0x400000;
    if (paramBoolean)
      this.m_1 &= 0xFFBFFFFF;
  }

  public boolean getEnableArrows() {
    return !((this.m_1 & 0x400000) != 0);
  }

  public void fireAction(int paramInt) {
    if ((paramInt & 0xC0) != 0) {
      long l1 = getNumber(this.m_text, false);
      if (l1 == Long.MIN_VALUE)
        l1 = 0L;
      double d = this.m_delta;
      for (byte b = 0; b < this.m_iDec; b++)
        d *= 10.0D;
      long l2 = limit(l1 + (long)d * (((paramInt & 0x40) != 0) ? 1L : -1L));
      if (l1 == l2)
        return;
      this.m_3 |= 0x800000;
      super.setText(buildText(l2));
    }
    super.fireAction(paramInt);
  }

  protected void onLostFocus() {
    setText(this.m_text);
  }

  int chekCh(char paramChar) {
    return (paramChar >= '0' && paramChar <= '9') ? 0 : ((paramChar == this.m_chDec) ? -1 : ((paramChar == '-' || paramChar == '(' || paramChar == ')') ? -2 : ((paramChar == '+') ? -3 : ((paramChar == '') ? -4 : ((paramChar == '\b') ? -5 : ((paramChar == this.m_chTho) ? -6 : -7))))));
  }

  long limit(long paramLong) {
    double d = this.m_max;
    byte b;
    for (b = 0; b < this.m_iDec; b++)
      d *= 10.0D;
    if (getUpperValidation() && paramLong > (long)d) {
      paramLong = (long)d;
    } else {
      d = this.m_min;
      for (b = 0; b < this.m_iDec; b++)
        d *= 10.0D;
      if (getLowerValidation() && paramLong < (long)d)
        paramLong = (long)d;
    }
    if (paramLong < 0L && paramLong != Long.MIN_VALUE) {
      this.m_5 |= 0x1000000;
    } else if (paramLong > 0L) {
      this.m_5 &= 0xFFF1FF;
    }
    return paramLong;
  }

  int packCaret() {
    byte b1 = -1;
    int i = -1;
    byte b2 = 0;
    int j = this.m_text.length();
    if (j < 1)
      return 0;
    this.m_iCaret = this.m_iStartSel;
    while (++b1 < j) {
      switch (chekCh(this.m_text.charAt(b1))) {
        case 0:
          b2 = b1;
          i++;
          if (b1 > this.m_iStartSel)
            this.m_iStartSel = b1;
          if (b1 == this.m_iStartSel) {
            if (++b1 < j && !getInsertEditMode() && chekCh(this.m_text.charAt(b1)) == -1)
              i |= 0x200;
            this.m_iCaret = this.m_iStartSel;
            if (this.m_iEndSel < this.m_iStartSel)
              this.m_iEndSel = this.m_iStartSel;
            return i;
          }
        case -1:
          if (b1 == this.m_iStartSel)
            return i | 0x300;
      }
    }
    this.m_iEndSel = this.m_iStartSel = this.m_iCaret = ++b2;
    return ++i | 0x100;
  }

  int unpackCaret(int paramInt) {
    byte b1 = -1;
    byte b2 = 0;
    byte b3 = -1;
    int i = this.m_text.length();
    if (i < 1)
      return 0;
    if (paramInt == 255)
      paramInt = 0;
    if (paramInt > 255)
      paramInt &= 0x600;
    while (++b3 < i) {
      switch (chekCh(this.m_text.charAt(b3))) {
        case 0:
          b2 = b3;
          if (++b1 == paramInt)
            return b3;
        case -1:
          if (paramInt == 512)
            return b3;
          if (paramInt == 1024)
            return ++b3;
      }
    }
    return b2 + 1;
  }

  protected boolean filterChar(char paramChar) {
    if (paramChar < '\003') {
      if (!getEnableArrows())
        return false;
      fireAction(paramChar * 64);
      return true;
    }
    if (paramChar == '\n') {
      if (!getValidateLimitsOnKey()) {
        this.m_3 |= 0x400000;
        setText(this.m_text);
      }
      return true;
    }
    String str = this.m_text;
    long l1 = getNumber(str, false);
    int i = chekCh(paramChar);
    if (i == -7) {
      if ((paramChar = Character.toUpperCase(paramChar)) == this.m_chK) {
        i = 1000;
      } else if (paramChar == this.m_chM) {
        i = 1000000;
      } else {
        return true;
      }
      if (l1 != Long.MIN_VALUE && l1 != 0L && l1 < Long.MAX_VALUE / i && l1 > Long.MIN_VALUE / i) {
        l1 *= i;
        if (l1 == limit(l1))
          this.m_text = buildText(l1);
      }
      return true;
    }
    int k = str.length();
    if (i == -4 && this.m_iStartSel == this.m_iEndSel) {
      if (this.m_iCaret >= k)
        return true;
      int m = chekCh(str.charAt(this.m_iCaret));
      if (m == -2) {
        i = -3;
      } else if (m < -2) {
        this.m_iEndSel = this.m_iStartSel = ++this.m_iCaret;
        return true;
      }
    }
    long l2 = (this.m_iEndSel == this.m_iStartSel + k) ? Long.MIN_VALUE : 0L;
    int j = packCaret();
    if (i <= -6)
      return true;
    if (this.m_iStartSel != this.m_iEndSel) {
      if (this.m_iEndSel >= k) {
        j |= 0x200;
      } else if (str.charAt(this.m_iEndSel) == this.m_chDec) {
        j |= 0x200;
      }
      str = String.valueOf(str.substring(0, this.m_iStartSel)) + str.substring(this.m_iEndSel);
      j |= 0x100;
      if (i <= -4)
        i = 1;
      k = str.length();
    }
    switch (i) {
      case -5:
        if (--j < 0)
          return true;
        this.m_iCaret--;
      case -4:
        if (this.m_iCaret < k && chekCh(str.charAt(this.m_iCaret)) == -6)
          this.m_iCaret += (i == -4) ? 1 : -1;
        if (j == 0 && getLong() < 10L && i == -4)
          j++;
        i = 100;
        if (this.m_iDec > 0) {
          i = 0;
          while (++i < k && str.charAt(i) != this.m_chDec);
        }
        if (i == this.m_iCaret) {
          l2 = l1;
          j = (paramChar == '') ? 1024 : 512;
          break;
        }
        if (this.m_iCaret > i) {
          str = String.valueOf(str.substring(0, this.m_iCaret)) + "0" + str.substring(this.m_iCaret + 1);
          if (paramChar == '')
            j++;
        } else {
          str = String.valueOf(str.substring(0, this.m_iCaret)) + str.substring(this.m_iCaret + 1);
        }
        l2 = getNumber(str, false);
        break;
      case -3:
        if (l1 > 0L || (this.m_max < 0.0D && getUpperValidation()))
          return true;
        if (l1 == 0L) {
          if ((this.m_5 & 0x1000000) != 0) {
            this.m_5 &= 0xFFF1FF;
            this.m_text = buildText(0L);
            this.m_iEndSel = this.m_iStartSel = this.m_iCaret = unpackCaret(0);
          }
          return true;
        }
        if (l1 != Long.MIN_VALUE)
          l2 = -l1;
        this.m_5 &= 0xFFF1FF;
        j = 255;
        break;
      case -2:
        if (this.m_min >= 0.0D && getLowerValidation())
          return true;
        if (l1 == Long.MIN_VALUE) {
          l2 = 0L;
        } else {
          if (l1 < 0L)
            return true;
          if (l1 == 0L) {
            if ((this.m_5 & 0x1000000) == 0) {
              this.m_5 |= 0x1000000;
              this.m_text = buildText(0L);
              this.m_iEndSel = this.m_iStartSel = this.m_iCaret = unpackCaret(0);
            }
            return true;
          }
          l2 = -l1;
        }
        this.m_5 |= 0x1000000;
        j = 255;
        break;
      case -1:
        if (this.m_iDec <= 0)
          return true;
        j = 1280;
        i = 0;
        while (++i + 1 < k) {
          if (str.charAt(i) == this.m_chDec) {
            if (this.m_iCaret == i)
              this.m_iEndSel = this.m_iStartSel = ++this.m_iCaret;
            if (this.m_iCaret == i + 1)
              return true;
            if (this.m_iCaret > i)
              this.m_iEndSel = this.m_iStartSel = --this.m_iCaret;
            str = String.valueOf(str.substring(0, i)) + str.substring(i + 1);
            break;
          }
        }
      case 0:
        if (this.m_iCaret < k) {
          i = ((j & 0x100) == 256 || getInsertEditMode()) ? 0 : 1;
          if (j == 0 && getLong() == 0L) {
            i = 1;
            j |= 0x200;
          }
          str = String.valueOf(str.substring(0, this.m_iCaret)) + paramChar + str.substring(this.m_iCaret + i);
        } else {
          str = String.valueOf(str) + paramChar;
        }
        if (k == 0 && paramChar != this.m_chDec)
          j = 512;
        j++;
      case 1:
        l1 = getNumber(str, false);
        if (l2 == Long.MIN_VALUE && l1 < 0L) {
          l2 = -l1;
          break;
        }
        l2 = l1;
        break;
    }
    if (l2 == Long.MIN_VALUE && paramChar == this.m_chDec) {
      l2 = 0L;
      this.m_5 &= 0xFFF1FF;
    }
    if (l2 == Long.MIN_VALUE && getAllowNull()) {
      this.m_text = "";
      this.m_5 &= 0xFFF1FF;
    } else {
      l1 = limit(l2);
      if (l2 != l1) {
        fireAction(32);
        if (getValidateLimitsOnKey())
          l2 = (!getToLimit() && !isNull()) ? Long.MIN_VALUE : l1;
      }
      if (l2 != Long.MIN_VALUE)
        this.m_text = buildText(l2);
    }
    this.m_iEndSel = this.m_iStartSel = this.m_iCaret = unpackCaret(j & 0x6FF);
    return true;
  }

  long getNumber(String paramString, boolean paramBoolean) {
    if (paramString == null)
      paramString = "";
    String str = "";
    long l1 = 0L;
    long l2 = Long.MIN_VALUE;
    byte b1 = 0;
    byte b = -1;
    byte b2 = 0;
    int i = paramString.length();
    if (i > 40)
      i = 40;
    boolean bool = false;
    while (b2 < i) {
      char c = paramString.charAt(b2++);
      switch (chekCh(c)) {
        case -3:
        case -2:
          if (b1)
            i = b2;
          if (l1 != 0L) {
            i = b2;
            continue;
          }
          l1 = ((c == '+') ? 1L : -1L);
        case -1:
          if (this.m_iDec == 0 || b >= 0)
            bool = true;
          if (!bool)
            b = 0;
        case 0:
          if (!bool) {
            str = String.valueOf(str) + c;
            if (b1++ > 18)
              bool = true;
            if (b >= 0 && ++b >= this.m_iDec)
              bool = true;
          }
      }
    }
    if (b1 > 0) {
      if (this.m_iDec > 0) {
        if (b < 0)
          b = 0;
        while (b++ < this.m_iDec) {
          str = String.valueOf(str) + "0";
          b1++;
        }
      }
      if (b1 > 19)
        str = str.substring(0, 19);
      if (l1 == 0L)
        l1 = 1L;
      try {
        l2 = l1 * (new Long(str)).longValue();
      } catch (Exception exception) {}
      l1 = 1L;
      b = this.m_iDec;
      while (b-- > 0)
        l1 *= 10L;
      if (paramBoolean && this.m_null == l2 / l1)
        l2 = Long.MIN_VALUE;
    }
    if (!getAllowNull() && l2 == Long.MIN_VALUE)
      l2 = 0L;
    return l2;
  }

  String buildText(long paramLong) {
    int i = this.m_5;
    if ((i & 0x1000000) != 0)
      i >>>= 12;
    String str = "";
    for (byte b = 0; b < 4; b++) {
      switch (i >> b * 3 & 0x7) {
        case 1:
          str = String.valueOf(str) + buildNum(paramLong);
          break;
        case 2:
          str = String.valueOf(str) + this.m_symbol;
          break;
        case 3:
          str = String.valueOf(str) + "-";
          break;
        case 4:
          str = String.valueOf(str) + "(";
          break;
        case 5:
          str = String.valueOf(str) + ")";
          break;
        case 6:
          str = String.valueOf(str) + " ";
          break;
      }
    }
    return str;
  }

  String buildNum(long paramLong) {
    if (paramLong < 0L)
      paramLong = -paramLong;
    String str = String.valueOf(paramLong);
    byte b = 0;
    int i = str.length();
    if (this.m_iDec > 0) {
      while (i <= this.m_iDec) {
        str = "0" + str;
        i++;
      }
      str = String.valueOf(str.substring(0, i - this.m_iDec)) + this.m_chDec + str.substring(i - this.m_iDec, i);
      i -= this.m_iDec;
    }
    while (--i > 0) {
      if (++b % 3 == 0 && getThousandSeparators())
        str = String.valueOf(str.substring(0, i)) + this.m_chTho + str.substring(i, str.length());
    }
    return str;
  }
}
