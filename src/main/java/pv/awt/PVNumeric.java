package pv.awt;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Locale;

public class PVNumeric extends PVEdit {
  public static final int LIMIT = 32;

  public static final int INCREMENT = 64;

  public static final int DECREMENT = 128;

  public double m_null = 2.2E-306D;

  protected Color m_cN;

  protected char m_chDec = '.';

  protected double m_max = 1.0E10D;

  protected double m_min = -1.0E10D;

  protected double m_delta = 1.0D;

  protected char m_chK;

  protected char m_chM;

  public PVNumeric(double paramDouble) {
    super("");
    this.m_1 |= 0x1012040;
    this.m_4 |= 0x69800;
    this.m_3 |= 0x13;
    setDouble(paramDouble);
  }

  public PVNumeric(long paramLong) {
    this(2.2E-306D);
    setLong(paramLong);
  }

  public PVNumeric() {
    this(2.2E-306D);
  }

  public Object getValue() {
    int i = getValueType();
    if ((i & 0xFFFFFFFC) == 0)
      return getText();
    if (i < 6) {
      Long long_ = getLongObj();
      if (long_ != null) {
        if (i == -6)
          return new Byte(long_.byteValue());
        if (i == 4)
          return new Integer(long_.intValue());
        if (i == 5)
          return new Short(long_.shortValue());
      }
      return long_;
    }
    if (i == 6)
      try {
        return new Float(this.m_text);
      } catch (NumberFormatException numberFormatException) {
        return null;
      }
    return getDoubleObj();
  }

  public void setValue(Object paramObject) {
    if (paramObject instanceof Number) {
      int i = getValueType();
      if (i < 6) {
        setLong(((Number)paramObject).longValue());
        return;
      }
      setDouble(((Number)paramObject).doubleValue());
      return;
    }
    super.setValue(paramObject);
  }

  public int getValueType() {
    int i = this.m_4 >> 22 & 0x7;
    return (i == 0) ? 8 : ((i < 3) ? (i - 7) : (i % 7));
  }

  public void setValueType(int paramInt) {
    paramInt += 7;
    if (paramInt < 4 && paramInt > 2 && paramInt < 9)
      paramInt = 0;
    if (paramInt <= 0) {
      paramInt = 7;
    } else if (paramInt >= 7) {
      paramInt = 0;
    }
    this.m_4 = this.m_4 & 0xFE3FFFFF | paramInt << 22;
  }

  public void updateLocale(Locale paramLocale) {
    String str = null;
    try {
      str = NumberFormat.getNumberInstance((paramLocale == null) ? getLocale() : paramLocale).format(1234.567D);
    } catch (Exception exception) {}
    byte b = (str == null) ? 0 : str.length();
    while (--b > 2) {
      if (str.charAt(b - 1) == '4') {
        boolean bool = (this.m_text == null) ? true : this.m_text.indexOf(this.m_chDec);
        this.m_chDec = str.charAt(b);
        if (bool) {
          StringBuffer stringBuffer = new StringBuffer(this.m_text);
          stringBuffer.setCharAt(b, this.m_chDec);
          this.m_text = new String(stringBuffer);
          repaint();
          return;
        }
        break;
      }
    }
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
    return (this.m_cN != null && this.m_text.length() > 0 && this.m_text.charAt(0) == '-') ? this.m_cN : super.foreClr();
  }

  public void setDouble(double paramDouble) {
    if (paramDouble != paramDouble)
      paramDouble = this.m_null;
    if (paramDouble == this.m_null && !getAllowNull())
      paramDouble = 0.0D;
    String str = null;
    if (paramDouble != this.m_null) {
      paramDouble = limit(paramDouble);
      if (getEnableFloatPoint()) {
        str = toString(paramDouble, this.m_chDec, getFreeEntry() ? 69 : Character.MIN_VALUE);
      } else {
        str = String.valueOf((long)paramDouble);
      }
    }
    super.setText(str);
  }

  public double getDouble() {
    return toDouble(this.m_text, this.m_chDec);
  }

  public void setLong(long paramLong) {
    double d = paramLong;
    if (limit(d) != d)
      paramLong = (long)limit(d);
    super.setText(String.valueOf(paramLong));
  }

  public long getLong() {
    try {
      return (new Long(this.m_text)).longValue();
    } catch (NumberFormatException numberFormatException) {
      return (long)getDouble();
    }
  }

  public Long getLongObj() {
    try {
      return new Long(this.m_text);
    } catch (NumberFormatException numberFormatException) {
      return null;
    }
  }

  public void setLongObj(Long paramLong) {
    if (paramLong == null) {
      setDouble(this.m_null);
      return;
    }
    setLong(paramLong.longValue());
  }

  public Double getDoubleObj() {
    return isNull() ? null : new Double(getDouble());
  }

  public void setDoubleObj(Double paramDouble) {
    setDouble((paramDouble == null) ? this.m_null : paramDouble.doubleValue());
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

  public void setDelta(double paramDouble) {
    this.m_delta = paramDouble;
  }

  public double getDelta() {
    return this.m_delta;
  }

  public void setMaxValue(double paramDouble) {
    this.m_max = paramDouble;
    setUpperValidation(true);
  }

  public double getMaxValue() {
    return this.m_max;
  }

  public void setMinValue(double paramDouble) {
    this.m_min = paramDouble;
    setLowerValidation(true);
  }

  public double getMinValue() {
    return this.m_min;
  }

  public void setUpperValidation(boolean paramBoolean) {
    this.m_2 &= 0xFFFFFFF7;
    if (paramBoolean)
      this.m_2 |= 0x8;
  }

  public boolean getUpperValidation() {
    return !((this.m_2 & 0x8) == 0);
  }

  public void setLowerValidation(boolean paramBoolean) {
    this.m_2 &= 0xFFFFFF7F;
    if (paramBoolean)
      this.m_2 |= 0x80;
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
    this.m_4 &= 0xFFDFFFFF;
    if (paramBoolean)
      this.m_4 |= 0x200000;
  }

  public boolean getValidateLimitsOnKey() {
    return !((this.m_4 & 0x200000) == 0);
  }

  public void setMaxIntegers(int paramInt) {
    if (paramInt < 1)
      paramInt = 1;
    if (paramInt > 30)
      paramInt = 30;
    this.m_4 = this.m_4 & 0xFFFF07FF | paramInt << 11;
    setText(this.m_text);
  }

  public int getMaxIntegers() {
    return this.m_4 >> 11 & 0x1F;
  }

  public void setMaxDecimals(int paramInt) {
    if (paramInt < 1)
      paramInt = 1;
    if (paramInt > 30)
      paramInt = 30;
    this.m_4 = this.m_4 & 0xFFE0FFFF | paramInt << 16;
    setText(this.m_text);
  }

  public int getMaxDecimals() {
    return this.m_4 >> 16 & 0x1F;
  }

  public void setEnableFloatPoint(boolean paramBoolean) {
    this.m_2 |= 0x8000000;
    if (paramBoolean)
      this.m_2 &= 0xF7FFFFFF;
    setText(this.m_text);
  }

  public boolean getEnableFloatPoint() {
    return !((this.m_2 & 0x8000000) != 0);
  }

  public void setFreeEntry(boolean paramBoolean) {
    this.m_2 &= 0xBFFFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x40000000;
  }

  public boolean getFreeEntry() {
    return !((this.m_2 & 0x40000000) == 0);
  }

  public void setDecimalSeparator(String paramString) {
    char c;
    if (paramString != null && paramString.length() > 0 && checkChar(c = paramString.charAt(0)) == -5) {
      this.m_chDec = c;
      setText(this.m_text);
    }
  }

  public String getDecimalSeparator() {
    return String.valueOf(this.m_chDec);
  }

  public void setText(String paramString) {
    if (this.m_text == null) {
      super.setText(null);
      return;
    }
    double d = toDouble(paramString, this.m_chDec);
    if (getEnableFloatPoint() || d == this.m_null) {
      setDouble(d);
      return;
    }
    try {
      setLong((new Long(paramString)).longValue());
      return;
    } catch (NumberFormatException numberFormatException) {
      setLong((long)d);
      return;
    }
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
      this.m_3 |= 0x800000;
      if (getEnableFloatPoint()) {
        double d = getDouble();
        if (d == this.m_null)
          d = 0.0D;
        setDouble(d + (((paramInt & 0x40) != 0) ? this.m_delta : -this.m_delta));
      } else {
        long l = getLong();
        setLong(l + (long)(((paramInt & 0x40) != 0) ? this.m_delta : -this.m_delta));
      }
    }
    super.fireAction(paramInt);
  }

  public boolean isNull() {
    return !(getDouble() != this.m_null);
  }

  protected void onLostFocus() {
    if (getSelectionOnFocus() == 1)
      this.m_3 |= 0x400000;
    setText(this.m_text);
  }

  int checkChar(char paramChar) {
    return (paramChar >= '0' && paramChar <= '9') ? 0 : ((paramChar == this.m_chDec && getEnableFloatPoint()) ? -1 : ((paramChar == '-') ? -2 : ((paramChar == '+') ? -3 : ((paramChar == '\000') ? -4 : ((paramChar == 'E' || paramChar == 'e') ? -6 : -5)))));
  }

  double limit(double paramDouble) {
    return (getUpperValidation() && paramDouble > this.m_max) ? this.m_max : ((getLowerValidation() && paramDouble < this.m_min) ? this.m_min : paramDouble);
  }

  protected boolean filterChar(char paramChar) {
    if (paramChar < '\003') {
      if (!getEnableArrows())
        return false;
      fireAction(paramChar * 64);
      return true;
    }
    int i = this.m_iCaret;
    switch (paramChar) {
      case '\n':
        onLostFocus();
        return true;
      case '\b':
        if (this.m_iEndSel == 0)
          return true;
        paramChar = Character.MIN_VALUE;
        if (!getFreeEntry())
          this.m_iCaret--;
        break;
      case '':
        paramChar = Character.MIN_VALUE;
        break;
    }
    int j = 0;
    int k = checkChar(paramChar);
    if (k == -5) {
      if ((paramChar = Character.toUpperCase(paramChar)) == this.m_chK) {
        j = 1000;
      } else if (paramChar == this.m_chM) {
        j = 1000000;
      }
      if (j > 0) {
        double d1 = getDouble();
        if (d1 != this.m_null && d1 != 0.0D) {
          d1 *= j;
          if (d1 == limit(d1))
            setDouble(d1);
        }
      }
      return true;
    }
    if (getFreeEntry())
      return false;
    if (k < -4)
      return true;
    StringBuffer stringBuffer = new StringBuffer(this.m_text);
    if (this.m_iEndSel != this.m_iStartSel) {
      stringBuffer.setLength(this.m_iStartSel);
      stringBuffer.append(this.m_text.substring(this.m_iEndSel));
      this.m_iCaret = this.m_iStartSel;
    }
    j = stringBuffer.length();
    if (j > 0 && this.m_iCaret == 0 && stringBuffer.charAt(0) == '-')
      k = -3;
    switch (k) {
      case -5:
        return true;
      case -4:
        if (this.m_iCaret < j && this.m_iEndSel == this.m_iStartSel) {
          stringBuffer.setLength(this.m_iCaret);
          stringBuffer.append(this.m_text.substring(this.m_iCaret + 1));
        }
        break;
      case -3:
      case -2:
        if (!doSign(stringBuffer, !(k != -3), j))
          return true;
        break;
      case -1:
        if (!insertDot(stringBuffer, j))
          return true;
        break;
      default:
        insertDigit(stringBuffer, paramChar, j);
        break;
    }
    this.m_iEndSel = this.m_iStartSel = this.m_iCaret;
    double d = toDouble(new String(stringBuffer), this.m_chDec);
    if (d != this.m_null && d != limit(d)) {
      fireAction(32);
      if (getValidateLimitsOnKey()) {
        if (!getToLimit() && !isNull()) {
          if (i < this.m_text.length())
            this.m_iEndSel = this.m_iStartSel = this.m_iCaret = ++i;
          return true;
        }
        setDouble(limit(d));
        return true;
      }
    }
    this.m_text = new String(stringBuffer);
    return true;
  }

  boolean insertDot(StringBuffer paramStringBuffer, int paramInt) {
    int i = paramInt;
    if (paramInt == 1 && paramStringBuffer.charAt(0) == '-')
      i = 0;
    if (i == 0) {
      paramStringBuffer.append(this.m_chDec);
      this.m_text = paramStringBuffer.toString();
      this.m_iStartSel = this.m_iEndSel = this.m_iCaret = ++paramInt;
      return false;
    }
    for (i = 0; i < paramInt && paramStringBuffer.charAt(i) != this.m_chDec; i++);
    if (i == paramInt) {
      paramStringBuffer.insert(this.m_iCaret++, this.m_chDec);
      return true;
    }
    if (i == this.m_iCaret) {
      this.m_iCaret++;
      return true;
    }
    if (i + 1 == this.m_iCaret)
      return true;
    if (i < this.m_iCaret) {
      while (i + 1 < this.m_iCaret) {
        paramStringBuffer.setCharAt(i, paramStringBuffer.charAt(i + 1));
        i++;
      }
    } else {
      while (i > this.m_iCaret) {
        paramStringBuffer.setCharAt(i, paramStringBuffer.charAt(i - 1));
        i--;
      }
      this.m_iCaret++;
    }
    paramStringBuffer.setCharAt(i, this.m_chDec);
    return true;
  }

  boolean doSign(StringBuffer paramStringBuffer, boolean paramBoolean, int paramInt) {
    if (paramInt > 0 && paramStringBuffer.charAt(0) == '-') {
      if (!paramBoolean || (getUpperValidation() && this.m_max < 0.0D))
        return false;
      String str = paramStringBuffer.toString();
      paramStringBuffer.setLength(0);
      paramStringBuffer.append(str.substring(1));
      this.m_iCaret = 0;
      return true;
    }
    if (paramBoolean || (getLowerValidation() && this.m_min >= 0.0D))
      return false;
    this.m_iStartSel = this.m_iEndSel = this.m_iCaret = 1;
    if (paramInt == 0) {
      this.m_text = "-";
      return false;
    }
    paramStringBuffer.insert(0, '-');
    return true;
  }

  void insertDigit(StringBuffer paramStringBuffer, char paramChar, int paramInt) {
    if (paramInt == 0) {
      paramStringBuffer.insert(this.m_iCaret++, paramChar);
      return;
    }
    if (this.m_iCaret == 0 && paramStringBuffer.charAt(0) == '-')
      this.m_iCaret++;
    if (getInsertEditMode() || this.m_iCaret == paramInt || paramStringBuffer.charAt(this.m_iCaret) == this.m_chDec) {
      paramStringBuffer.insert(this.m_iCaret, paramChar);
    } else {
      paramStringBuffer.setCharAt(this.m_iCaret, paramChar);
    }
    this.m_iCaret++;
  }

  void checkLen() {
    int i = this.m_text.length();
    int j = -1;
    int k = 1000;
    int m = 0;
    while (++j < i) {
      switch (checkChar(this.m_text.charAt(j))) {
        case 0:
          m++;
        case -1:
          k = j + 1;
        case -6:
          i = j;
      }
    }
    j = k--;
    j += getMaxDecimals();
    if (i > j) {
      this.m_text = String.valueOf(this.m_text.substring(0, j)) + this.m_text.substring(i);
      m -= i - j;
      i = j;
    }
    if (k > 900)
      k = i;
    j = getMaxIntegers();
    if (k >= j) {
      this.m_text = String.valueOf(this.m_text.substring(0, j)) + this.m_text.substring(k);
      m -= k - j;
      i -= k - j;
    }
    super.checkLen();
  }

  public static String toString(double paramDouble, char paramChar1, char paramChar2) {
    boolean bool = true;
    byte b1 = 0;
    if (paramDouble == 0.0D)
      return "0";
    if (paramDouble < 0.0D) {
      paramDouble = -paramDouble;
      bool = false;
    }
    while (paramDouble >= 10.0D) {
      paramDouble /= 10.0D;
      if (++b1 > 'ı')
        return "";
    }
    while (paramDouble + 8.0E-17D < 1.0D) {
      paramDouble *= 10.0D;
      if (--b1 < -305)
        return "";
    }
    for (byte b2 = 0; b2 < 14; b2++)
      paramDouble *= 10.0D;
    StringBuffer stringBuffer = new StringBuffer(String.valueOf((long)(paramDouble + 0.5D)));
    int i = stringBuffer.length();
    byte b3 = b1;
    if (paramChar2 != '\000')
      b1 = 0;
    while (i > b1 + 1 && i > 0 && stringBuffer.charAt(i - 1) <= '0')
      stringBuffer.setLength(--i);
    while (i <= b1) {
      stringBuffer.append("0");
      i++;
    }
    if (b1 >= 0) {
      if (b1 + 1 < i)
        stringBuffer.insert(b1 + 1, paramChar1);
    } else {
      for (byte b = b1; b < 0; b++)
        stringBuffer.insert(0, '0');
      stringBuffer.insert(1, paramChar1);
    }
    if (b1 != b3) {
      stringBuffer.append(paramChar2);
      stringBuffer.append(String.valueOf(b3 - b1));
    }
    if (!bool)
      stringBuffer.insert(0, '-');
    return new String(stringBuffer);
  }

  public double toDouble(String paramString, char paramChar) {
    return toDouble(paramString, paramChar, this.m_null);
  }

  public static double toDouble(String paramString, char paramChar, double paramDouble) {
    int i = (paramString == null) ? 0 : paramString.length();
    if (!i)
      return paramDouble;
    int j = -1;
    int k = -1;
    byte b = 0;
    int m = -1;
    char c = Character.MIN_VALUE;
    while (++j < i) {
      c = paramString.charAt(j);
      if (c >= '0' && c <= '9') {
        m = 0;
        continue;
      }
      if (c == paramChar) {
        if (k < 0) {
          k = j;
          continue;
        }
        break;
      }
      if (j == 0) {
        if (c == '-' || c == '(') {
          b = -1;
          continue;
        }
        if (c == '+') {
          b = 1;
          continue;
        }
      }
      break;
    }
    if (m != 0)
      return paramDouble;
    if (j < i) {
      if (j + 1 < i && (c == 'E' || c == 'e')) {
        m = j + 1;
        while (++m < i) {
          c = paramString.charAt(m);
          if (c < '0' || c > '9') {
            paramString = paramString.substring(0, m);
            break;
          }
        }
        m = 0;
        try {
          m = (int)(new Double(paramString.substring(j + 1))).doubleValue();
        } catch (Exception exception) {}
        if (m > 306 || m < -306)
          return paramDouble;
      }
      paramString = paramString.substring(0, i = j);
    }
    if (b != 0) {
      if (--i > 0)
        paramString = paramString.substring(1);
      k--;
    }
    if (k == i - 1)
      paramString = paramString.substring(0, --i);
    if (i <= 0)
      return paramDouble;
    if (k >= 0 && k < i) {
      m -= i - k - 1;
      j = -1;
      while (++j < i) {
        if (paramString.charAt(j) == paramChar) {
          i--;
          paramString = String.valueOf(paramString.substring(0, j)) + paramString.substring(j + 1);
          break;
        }
      }
    }
    while (paramString.charAt(0) == '0') {
      if (--i <= 0)
        return 0.0D;
      paramString = paramString.substring(1);
    }
    while (i > 1 && (i > 18 || paramString.charAt(i - 1) == '0')) {
      paramString = paramString.substring(0, --i);
      m++;
    }
    try {
      paramDouble = (new Long(paramString)).longValue();
    } catch (Exception exception) {
      return paramDouble;
    }
    if (m != 0) {
      double d = 1.0D;
      j = (m > 0) ? m : -m;
      while (j-- > 0)
        d *= 10.0D;
      paramDouble = (m > 0) ? (paramDouble * d) : (paramDouble / d);
    }
    return (b < 0) ? -paramDouble : paramDouble;
  }
}
