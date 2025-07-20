package pv.awt;

public class PVCalendarPlusBeanInfo extends PVCalendarBeanInfo {
  static Class class$pv$awt$PVCalendarPlus;

  public PVCalendarPlusBeanInfo() {
    this.m_i = 61;
  }

  protected Class getCls() {
    return (class$pv$awt$PVCalendarPlus != null) ? class$pv$awt$PVCalendarPlus : (class$pv$awt$PVCalendarPlus = PVCalendarBeanInfo.class$("pv.awt.PVCalendarPlus"));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 57)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 57:
        return "AutoAddCustomDate";
      case 58:
        return "Accelerator";
      case 59:
        return "DayWidth";
    }
    return "DayHeight";
  }

  protected String iconName() {
    return "CalP";
  }
}
