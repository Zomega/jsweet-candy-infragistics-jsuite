package pv.awt;

public class PVDateBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVDateCustomizer;

  static Class class$pv$awt$PVDate;

  public PVDateBeanInfo() {
    this.m_i = 59;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVDateCustomizer != null) ? class$pv$awt$PVDateCustomizer : (class$pv$awt$PVDateCustomizer = PVEditBeanInfo.class$("pv.awt.PVDateCustomizer"))) : ((class$pv$awt$PVDate != null) ? class$pv$awt$PVDate : (class$pv$awt$PVDate = PVEditBeanInfo.class$("pv.awt.PVDate")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 43)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 43:
        return "LeadingZero";
      case 44:
        return "AutoCentury";
      case 45:
        return "AllowNull";
      case 46:
        return "ShowCentury";
      case 47:
        return "EnableArrows";
      case 48:
        return "Format";
      case 49:
        return "AutoSelectNextField";
      case 50:
        return "Separator";
      case 51:
        return "CurrentDate";
      case 52:
        return "Delta";
      case 53:
        return "UseLocale";
      case 54:
        return "DefaultNull";
      case 55:
        return "MonthFormat";
      case 56:
        return "ShowDOW";
      case 57:
        return "LocaleFormat";
    }
    return "FreeEntry";
  }

  protected String iconName() {
    return "Date";
  }
}
