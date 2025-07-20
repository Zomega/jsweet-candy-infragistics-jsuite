package pv.awt;

public class PVCurrencyBeanInfo extends PVEditBeanInfo {
  static Class class$pv$awt$PVCurrencyCustomizer;

  static Class class$pv$awt$PVCurrency;

  public PVCurrencyBeanInfo() {
    this.m_i = 64;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVCurrencyCustomizer != null) ? class$pv$awt$PVCurrencyCustomizer : (class$pv$awt$PVCurrencyCustomizer = PVEditBeanInfo.class$("pv.awt.PVCurrencyCustomizer"))) : ((class$pv$awt$PVCurrency != null) ? class$pv$awt$PVCurrency : (class$pv$awt$PVCurrency = PVEditBeanInfo.class$("pv.awt.PVCurrency")));
  }

  protected String getStr(int paramInt) {
    if (paramInt < 43)
      return super.getStr(paramInt);
    switch (paramInt) {
      case 43:
        return "DecimalSeparator";
      case 44:
        return "ThousandSeparator";
      case 45:
        return "ThousandSeparators";
      case 46:
        return "Decimals";
      case 47:
        return "Symbol";
      case 48:
        return "FormatPositive";
      case 49:
        return "FormatNegative";
      case 50:
        return "AllowNull";
      case 51:
        return "LowerValidation";
      case 52:
        return "UpperValidation";
      case 53:
        return "MinValue";
      case 54:
        return "MaxValue";
      case 55:
        return "EnableArrows";
      case 56:
        return "ValidateLimitsOnKey";
      case 57:
        return "DefaultNull";
      case 58:
        return "UseLocale";
      case 59:
        return "ToLimit";
      case 60:
        return "NegativeForeground";
      case 61:
        return "1000Multiplier";
      case 62:
        return "1000000Multiplier";
    }
    return "Delta";
  }

  protected String iconName() {
    return "Curr";
  }
}
