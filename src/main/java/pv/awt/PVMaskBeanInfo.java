package pv.awt;

public class PVMaskBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVMaskCustomizer;

  static Class class$pv$awt$PVMask;

  public PVMaskBeanInfo() {
    this.m_i = 53;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVMaskCustomizer != null) ? class$pv$awt$PVMaskCustomizer : (class$pv$awt$PVMaskCustomizer = PVEditBeanInfo.class$("pv.awt.PVMaskCustomizer"))) : ((class$pv$awt$PVMask != null) ? class$pv$awt$PVMask : (class$pv$awt$PVMask = PVEditBeanInfo.class$("pv.awt.PVMask")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 46)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 46:
        return "EscapeChar";
      case 47:
        return "PromptChar";
      case 48:
        return "Mask";
      case 49:
        return "CleanText";
      case 50:
        return "EmptyChar";
      case 51:
        return "ValueType";
    }
    return "CutByEmptyPosition";
  }

  protected String iconName() {
    return "Mask";
  }
}
