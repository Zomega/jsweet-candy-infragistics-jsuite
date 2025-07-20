package pv.awt;

public class PVNumericBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVNumericCustomizer;

  static Class class$pv$awt$PVNumeric;

  public PVNumericBeanInfo() {
    this.m_i = 65;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVNumericCustomizer != null) ? class$pv$awt$PVNumericCustomizer : (class$pv$awt$PVNumericCustomizer = PVEditBeanInfo.class$("pv.awt.PVNumericCustomizer"))) : ((class$pv$awt$PVNumeric != null) ? class$pv$awt$PVNumeric : (class$pv$awt$PVNumeric = PVEditBeanInfo.class$("pv.awt.PVNumeric")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 44)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 44:
        return "EnableFloatPoint";
      case 45:
        return "LowerValidation";
      case 46:
        return "UpperValidation";
      case 47:
        return "MinValue";
      case 48:
        return "MaxValue";
      case 49:
        return "EnableArrows";
      case 50:
        return "AllowNull";
      case 51:
        return "MaxLength";
      case 52:
        return "MaxDecimals";
      case 53:
        return "MaxIntegers";
      case 54:
        return "Delta";
      case 55:
        return "ToLimit";
      case 56:
        return "ValidateLimitsOnKey";
      case 57:
        return "DefaultNull";
      case 58:
        return "FreeEntry";
      case 59:
        return "UseLocale";
      case 60:
        return "NegativeForeground";
      case 61:
        return "1000Multiplier";
      case 62:
        return "1000000Multiplier";
      case 63:
        return "ValueType";
    }
    return "DecimalSeparator";
  }

  protected String iconName() {
    return "Numer";
  }
}
