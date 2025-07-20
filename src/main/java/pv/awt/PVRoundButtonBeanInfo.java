package pv.awt;

public class PVRoundButtonBeanInfo extends PVButtonBeanInfo {
  static Class class$pv$awt$PVRoundButtonCustomizer;

  static Class class$pv$awt$PVRoundButton;

  public PVRoundButtonBeanInfo() {
    this.m_i = 49;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVRoundButtonCustomizer != null) ? class$pv$awt$PVRoundButtonCustomizer : (class$pv$awt$PVRoundButtonCustomizer = PVButtonBeanInfo.class$("pv.awt.PVRoundButtonCustomizer"))) : ((class$pv$awt$PVRoundButton != null) ? class$pv$awt$PVRoundButton : (class$pv$awt$PVRoundButton = PVButtonBeanInfo.class$("pv.awt.PVRoundButton")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 46)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 46:
        return "ColorsDepth";
      case 47:
        return "CenterArea";
    }
    return "Shape";
  }

  protected String iconName() {
    return "Round";
  }
}
