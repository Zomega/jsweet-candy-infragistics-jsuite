package pv.awt;

public class PVDatePlusBeanInfo extends PVDateBeanInfo {
  static Class class$pv$awt$PVDatePlusCustomizer;

  static Class class$pv$awt$PVDatePlus;

  public PVDatePlusBeanInfo() {
    this.m_i = 70;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVDatePlusCustomizer != null) ? class$pv$awt$PVDatePlusCustomizer : (class$pv$awt$PVDatePlusCustomizer = PVEditBeanInfo.class$("pv.awt.PVDatePlusCustomizer"))) : ((class$pv$awt$PVDatePlus != null) ? class$pv$awt$PVDatePlus : (class$pv$awt$PVDatePlus = PVEditBeanInfo.class$("pv.awt.PVDatePlus")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 59)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 59:
        return "CalendarWidth";
      case 60:
        return "CalendarHeight";
      case 61:
        return "ButtonInFront";
      case 62:
        return "ButtonWidth";
      case 63:
        return "ButtonColor";
      case 64:
        return "ButtonBorder";
      case 65:
        return "WaitForCalendarDate";
      case 66:
        return "CalendarMonths";
      case 67:
        return "EditableByCalendar";
      case 68:
        return "CloseOnDoubleClick";
    }
    return "Triangle";
  }

  protected String iconName() {
    return "DatP";
  }
}
