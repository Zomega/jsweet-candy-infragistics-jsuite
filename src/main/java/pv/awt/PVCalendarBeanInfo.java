package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVCalendarBeanInfo extends SimpleBeanInfo {
  protected int m_i = 57;

  static Class class$pv$awt$PVCalendar;

  public PVCalendarBeanInfo() {
    this.m_i = 57;
  }

  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[this.m_i];
      for (byte b = 0; b < this.m_i; b++) {
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(getStr(b), getCls());
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  protected String getStr(int paramInt) {
    switch (paramInt) {
      case 0:
        return "Background";
      case 1:
        return "Foreground";
      case 2:
        return "Enabled";
      case 3:
        return "CalendarColor";
      case 4:
        return "Text3DColor";
      case 5:
        return "ShadowColor";
      case 6:
        return "BorderColor";
      case 7:
        return "SelCellColor";
      case 8:
        return "SundayColor";
      case 9:
        return "Font";
      case 10:
        return "MonthYearFont";
      case 11:
        return "ListFont";
      case 12:
        return "DOWFont";
      case 13:
        return "MondayFirst";
      case 14:
        return "IndentCellBorder";
      case 15:
        return "CurrentDate";
      case 16:
        return "Aureole";
      case 17:
        return "DOW";
      case 18:
        return "BorderStyle";
      case 19:
        return "CellBorderWidth";
      case 20:
        return "Shadow";
      case 21:
        return "ShadowWidth";
      case 22:
        return "DisplaySelDate";
      case 23:
        return "NumberOfMonths";
      case 24:
        return "Name";
      case 25:
        return "LocaleStrings";
      case 26:
        return "Vertical3D";
      case 27:
        return "Horizontal3D";
      case 28:
        return "ActionMask";
      case 29:
        return "RangeSelection";
      case 30:
        return "MultiSelection";
      case 31:
        return "Sharpness3D";
      case 32:
        return "SolidTriangle";
      case 33:
        return "ScrollBarBorder";
      case 34:
        return "ScrollBarColor";
      case 35:
        return "ScrollBarWidth";
      case 36:
        return "HideYearField";
      case 37:
        return "EnableLists";
      case 38:
        return "CustomFont";
      case 39:
        return "DrawCustomText";
      case 40:
        return "CurrentDateButton";
      case 41:
        return "AutoResize";
      case 42:
        return "YearListCount";
      case 43:
        return "CustomForeground";
      case 44:
        return "Format";
      case 45:
        return "ListSelectionBackground";
      case 46:
        return "ListSelectionForeground";
      case 47:
        return "ListBackground";
      case 48:
        return "ListForeground";
      case 49:
        return "CustomSelectionBackground";
      case 50:
        return "CustomSelectionStyle";
      case 51:
        return "CustomFlags";
      case 52:
        return "ShowTips";
      case 53:
        return "LocationOfNumbers";
      case 54:
        return "MonthYearBorder";
      case 55:
        return "InvertedSelection";
    }
    return "Bounds";
  }

  public Image getIcon(int paramInt) {
    return loadImage(String.valueOf(iconName()) + ((paramInt == 2) ? "32.gif" : "16.gif"));
  }

  protected Class getCls() {
    return (class$pv$awt$PVCalendar != null) ? class$pv$awt$PVCalendar : (class$pv$awt$PVCalendar = class$("pv.awt.PVCalendar"));
  }

  protected String iconName() {
    return "Cal";
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(getCls(), PVCalendarCustomizer.class);
  }
}
