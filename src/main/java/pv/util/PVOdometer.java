package pv.util;

import java.awt.Color;
import java.awt.Graphics;

public class PVOdometer {
  public static final byte NORMAL = 4;

  public static final byte LIGHT = 1;

  public static final byte DARK = 7;

  public static final byte TOP = 0;

  public static final byte CENTER = 1;

  public static final byte BOTTOM = 2;

  public static void draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, int paramInt5) {
    if (paramGraphics != null) {
      if (paramColor == null)
        paramColor = Color.white;
      int i = 0;
      int j = paramColor.getRGB();
      switch (paramInt5 % 3) {
        case 0:
          i += paramInt4 * 11 >>> 4;
          break;
        case 1:
          i += paramInt4 >>> 2;
          break;
        default:
          i += paramInt4 * 7 >>> 4;
          break;
      }
      int k = (i << 1 > paramInt4) ? i : (paramInt4 - i);
      int m = (k >> 4) + 1;
      int n = i;
      int i1 = k / m + 1;
      int i2 = j & 0xFF;
      int i3 = j >> 8 & 0xFF;
      int i4 = j >> 16 & 0xFF;
      int i5 = n + m;
      int i6 = (paramInt5 - 1) / 3 + 1;
      if (i6 < 1)
        i6 = 1;
      if (i6 > 3)
        i6 = 3;
      if (paramInt5 % 3 == 2) {
        paramGraphics.setColor(paramColor);
        paramGraphics.fillRect(paramInt1, n + paramInt2, paramInt3, m << 1);
        n -= m;
        i5 += m;
      }
      for (k = 0; k <= i1; k++) {
        paramGraphics.setColor(new Color(i4 - i4 * i6 * k / i1 / 3, i3 - i3 * i6 * k / i1 / 3, i2 - i2 * i6 * k / i1 / 3));
        if (n + m >= 0) {
          paramGraphics.fillRect(paramInt1, n + paramInt2, paramInt3, m);
          n -= m;
        }
        if (i5 < paramInt4) {
          paramGraphics.fillRect(paramInt1, i5 + paramInt2, paramInt3, m);
          i5 += m;
        }
      }
    }
  }
}
