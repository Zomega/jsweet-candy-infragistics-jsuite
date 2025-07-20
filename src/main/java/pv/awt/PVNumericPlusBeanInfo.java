package pv.awt;

public class PVNumericPlusBeanInfo extends PVNumericBeanInfo {
  static Class class$pv$awt$PVNumericPlus;

  public PVNumericPlusBeanInfo() {
    this.m_i = 72;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((PVNumericBeanInfo.class$pv$awt$PVNumericCustomizer != null) ? PVNumericBeanInfo.class$pv$awt$PVNumericCustomizer : (PVNumericBeanInfo.class$pv$awt$PVNumericCustomizer = PVEditBeanInfo.class$("pv.awt.PVNumericCustomizer"))) : ((class$pv$awt$PVNumericPlus != null) ? class$pv$awt$PVNumericPlus : (class$pv$awt$PVNumericPlus = PVEditBeanInfo.class$("pv.awt.PVNumericPlus")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 65)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 65:
        return "ButtonInFront";
      case 66:
        return "ButtonWidth";
      case 67:
        return "ButtonColor";
      case 68:
        return "Triangle";
      case 69:
        return "WaitOK_Cancel";
      case 70:
        return "Dialog";
    }
    return "ButtonBorder";
  }

  protected String iconName() {
    return "NumP";
  }
}
