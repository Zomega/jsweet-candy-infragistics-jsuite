package pv.awt;

public class PVStaticTextBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVStaticTextCustomizer;

  static Class class$pv$awt$PVStaticText;

  public PVStaticTextBeanInfo() {
    this.m_i = 22;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVStaticTextCustomizer != null) ? class$pv$awt$PVStaticTextCustomizer : (class$pv$awt$PVStaticTextCustomizer = PVEditBeanInfo.class$("pv.awt.PVStaticTextCustomizer"))) : ((class$pv$awt$PVStaticText != null) ? class$pv$awt$PVStaticText : (class$pv$awt$PVStaticText = PVEditBeanInfo.class$("pv.awt.PVStaticText")));
  }

  protected String iconName() {
    return "Static";
  }
}
