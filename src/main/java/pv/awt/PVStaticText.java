package pv.awt;

import java.awt.Color;
import java.awt.Font;

public class PVStaticText extends PVEdit {
  public PVStaticText(String paramString, int paramInt1, int paramInt2, int paramInt3) {
    super(paramString);
    setFont(new Font("SansSerif", 1, paramInt1));
    setText3DColor(Color.white);
    setBorderStyle(0);
    setHorizontal3D(paramInt2);
    setVertical3D(paramInt3);
    setBackground(null);
  }

  public PVStaticText(String paramString) {
    this(paramString, 12, 1, 1);
  }

  public PVStaticText() {
    this(null);
  }

  protected void init0() {
    enableEvents(16L);
    this.m_1 = this.m_1 & 0x7FFFFFF | 0xB0080;
  }

  public boolean isFocusTraversable() {
    return false;
  }
}
