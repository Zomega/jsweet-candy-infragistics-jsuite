package pv.awt;

import java.awt.Component;

public class PVSpinPlus extends PVSpin {
  public static final int NONE = 0;

  public static final int NUMERIC = 1;

  public static final int CURRENCY = 2;

  public static final int DATE = 3;

  public static final int DATE_LONG = 4;

  public static final int TIME = 5;

  public static final int DATE_PLUS = 6;

  public static final int DATE_LONG_PLUS = 7;

  public static final int NUMERIC_PLUS = 8;

  public PVSpinPlus(int paramInt1, int paramInt2) {
    super(paramInt1);
    setPVObject(paramInt2);
  }

  public PVSpinPlus(int paramInt) {
    super(paramInt);
  }

  public PVSpinPlus() {
    super(0);
  }

  public void setObject(Component paramComponent) {
    if (this.m_obj != paramComponent) {
      super.setObject(paramComponent);
      if ((this.m_0 & 0x3C000000) != 1006632960) {
        byte b = 11;
        while (--b > 0 && (paramComponent == null || !paramComponent.getClass().getName().equals(pv(b))));
        this.m_0 = this.m_0 & 0xC3FFFFFF | b << 26;
      }
    }
  }

  public boolean setPVObject(int paramInt) {
    Object object = null;
    if (paramInt < 0) {
      paramInt = 0;
    } else {
      if (paramInt > 8)
        return false;
      try {
        object = Class.forName(pv(paramInt)).newInstance();
      } catch (Exception exception) {
        return false;
      }
    }
    if (object != null)
      this.m_0 |= 0x3C000000;
    setObject((Component)object);
    this.m_0 = this.m_0 & 0xC3FFFFFF | paramInt << 26;
    return true;
  }

  String pv(int paramInt) {
    switch (paramInt) {
      case 1:
        return "pv.awt.PVNumeric";
      case 2:
        return "pv.awt.PVCurrency";
      case 3:
        return "pv.awt.PVDate";
      case 4:
        return "pv.awt.PVDateLong";
      case 5:
        return "pv.awt.PVTime";
      case 6:
        return "pv.awt.PVDatePlus";
      case 7:
        return "pv.awt.PVDateLongPlus";
      case 8:
        return "pv.awt.PVNumericPlus";
    }
    return null;
  }

  public int getPVObject() {
    return this.m_0 >> 26 & 0xF;
  }

  public void setAutoSpin(boolean paramBoolean) {
    if (paramBoolean) {
      this.m_0 &= 0xFFFFF7FF;
      return;
    }
    this.m_0 |= 0x800;
  }

  public boolean getAutoSpin() {
    return !((this.m_0 & 0x800) != 0);
  }

  public void fireSpin(int paramInt) {
    try {
      if (getAutoSpin() && this.m_obj instanceof PVEdit)
        ((PVEdit)this.m_obj).fireAction(paramInt);
    } catch (Exception exception) {}
    super.fireSpin(paramInt);
  }
}
