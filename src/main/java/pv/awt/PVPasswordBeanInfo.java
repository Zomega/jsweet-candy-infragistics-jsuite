package pv.awt;

public class PVPasswordBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVPasswordCustomizer;

  static Class class$pv$awt$PVPassword;

  public PVPasswordBeanInfo() {
    this.m_i = 50;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVPasswordCustomizer != null) ? class$pv$awt$PVPasswordCustomizer : (class$pv$awt$PVPasswordCustomizer = PVEditBeanInfo.class$("pv.awt.PVPasswordCustomizer"))) : ((class$pv$awt$PVPassword != null) ? class$pv$awt$PVPassword : (class$pv$awt$PVPassword = PVEditBeanInfo.class$("pv.awt.PVPassword")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 47)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 47:
        return "EchoCharacter";
      case 48:
        return "Password";
    }
    return "DigitsOnly";
  }

  protected String iconName() {
    return "Pass";
  }
}
