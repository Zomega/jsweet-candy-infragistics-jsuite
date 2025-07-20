package pv.awt;

public class PVTimeBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVTimeCustomizer;

  static Class class$pv$awt$PVTime;

  public PVTimeBeanInfo() {
    this.m_i = 54;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVTimeCustomizer != null) ? class$pv$awt$PVTimeCustomizer : (class$pv$awt$PVTimeCustomizer = PVEditBeanInfo.class$("pv.awt.PVTimeCustomizer"))) : ((class$pv$awt$PVTime != null) ? class$pv$awt$PVTime : (class$pv$awt$PVTime = PVEditBeanInfo.class$("pv.awt.PVTime")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 43)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 43:
        return "Timer";
      case 44:
        return "LeadingZero";
      case 45:
        return "AllowNull";
      case 46:
        return "TwelveHours";
      case 47:
        return "AMPM";
      case 48:
        return "EnableArrows";
      case 49:
        return "BlankForNull";
      case 50:
        return "Delta";
      case 51:
        return "ShowSeconds";
      case 52:
        return "UseLocale";
    }
    return "Separator";
  }

  protected String iconName() {
    return "Time";
  }
}
