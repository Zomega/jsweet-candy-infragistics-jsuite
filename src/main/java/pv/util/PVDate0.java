package pv.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class PVDate0 {
  public static final int pack(Date paramDate) {
    return pack(paramDate, null);
  }

  public static final int pack(Date paramDate, Calendar paramCalendar) {
    if (paramDate == null)
      return 0;
    paramCalendar = (paramCalendar == null) ? new GregorianCalendar() : (Calendar)paramCalendar.clone();
    paramCalendar.setTime(paramDate);
    return (paramCalendar == null) ? 0 : (paramCalendar.get(1) << 9 | paramCalendar.get(2) << 5 | paramCalendar.get(5));
  }

  public static final int pack0(Calendar paramCalendar) {
    return (paramCalendar == null) ? 0 : (paramCalendar.get(1) << 9 | paramCalendar.get(2) << 5 | paramCalendar.get(5));
  }

  public static final Date unpack(int paramInt) {
    return (paramInt == 0) ? null : unpack0(paramInt, null).getTime();
  }

  public static final Date unpack(int paramInt, Calendar paramCalendar) {
    return (paramInt == 0) ? null : unpack0(paramInt, paramCalendar).getTime();
  }

  public static final Calendar unpack0(int paramInt) {
    return unpack0(paramInt, null);
  }

  public static final Calendar unpack0(int paramInt, Calendar paramCalendar) {
    if (paramInt == 0)
      return null;
    if (paramCalendar == null) {
      paramCalendar = new GregorianCalendar(paramInt >> 9 & 0x3FFF, paramInt >> 5 & 0xF, paramInt & 0x1F);
    } else {
      paramCalendar = (Calendar)paramCalendar.clone();
      paramCalendar.set(paramInt >> 9 & 0x3FFF, paramInt >> 5 & 0xF, paramInt & 0x1F);
    }
    return paramCalendar;
  }

  public static final int maxDay(int paramInt1, int paramInt2) {
    if (paramInt2 == 1) {
      try {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(paramInt1, 1, 29);
        gregorianCalendar.setTime(gregorianCalendar.getTime());
        if (gregorianCalendar.get(5) == 29)
          return 29;
      } catch (Exception exception) {}
      return 28;
    }
    return (paramInt2 < 0) ? 31 : (((paramInt2 % 7 & 0x1) == 0) ? 31 : 30);
  }

  public static final String month(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    return month(paramInt, paramBoolean1, paramBoolean2, null);
  }

  public static final String dow(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    return dow(paramInt, paramBoolean1, paramBoolean2, null);
  }

  public static final String month(int paramInt, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) {
    if (paramInt < 0 || paramInt > 11)
      return "";
    String str = paramBoolean1 ? "MMM" : "MMMM";
    if (paramBoolean2)
      try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, (paramLocale == null) ? Locale.getDefault() : paramLocale);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format((new GregorianCalendar(1997, paramInt, 2)).getTime());
      } catch (Exception exception) {}
    switch (paramInt) {
      case 0:
        str = "January";
        break;
      case 1:
        str = "February";
        break;
      case 2:
        str = "March";
        break;
      case 3:
        str = "April";
        break;
      case 4:
        str = "May";
        break;
      case 5:
        str = "June";
        break;
      case 6:
        str = "July";
        break;
      case 7:
        str = "August";
        break;
      case 8:
        str = "September";
        break;
      case 9:
        str = "October";
        break;
      case 10:
        str = "November";
        break;
      case 11:
        str = "December";
        break;
    }
    return paramBoolean1 ? str.substring(0, 3) : str;
  }

  public static final String dow(int paramInt, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) {
    if (paramInt < 0 || paramInt > 6)
      return "";
    String str = paramBoolean1 ? "EEE" : "EEEE";
    if (paramBoolean2)
      try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, (paramLocale == null) ? Locale.getDefault() : paramLocale);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format((new GregorianCalendar(1998, 1, paramInt + 1)).getTime());
      } catch (Exception exception) {}
    switch (paramInt) {
      case 0:
        str = "Sunday";
        break;
      case 1:
        str = "Monday";
        break;
      case 2:
        str = "Tuesday";
        break;
      case 3:
        str = "Wednesday";
        break;
      case 4:
        str = "Thursday";
        break;
      case 5:
        str = "Friday";
        break;
      case 6:
        str = "Saturday";
        break;
    }
    return paramBoolean1 ? str.substring(0, 3) : str;
  }
}
