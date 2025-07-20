package pv.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class TabBorder extends PVBorder {
  public static void draw(Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, Color paramColor, boolean paramBoolean, int paramInt2) {
    int i = PVBorder.set(paramInt1, true);
    int j = PVBorder.set(paramInt1, false);
    if (i == 0)
      return;
    int[] arrayOfInt1 = new int[3];
    int[] arrayOfInt2 = new int[6];
    int k = 170 + ((paramColor.getRed() - 170) * 5 >> 3);
    int m = 170 + ((paramColor.getGreen() - 170) * 5 >> 3);
    int n = 170 + ((paramColor.getBlue() - 170) * 5 >> 3);
    for (byte b2 = 0; b2 < 6; b2++) {
      arrayOfInt1[0] = k;
      arrayOfInt1[1] = m;
      arrayOfInt1[2] = n;
      for (byte b = 0; b < 3; b++) {
        switch (b2) {
          case 0:
            arrayOfInt1[b] = 0;
            break;
          case 1:
            arrayOfInt1[b] = arrayOfInt1[b] * 3 >>> 2;
            break;
          case 3:
            arrayOfInt1[b] = arrayOfInt1[b] + (255 - arrayOfInt1[b] << 1) / 6;
            break;
          case 4:
            arrayOfInt1[b] = arrayOfInt1[b] + (255 - arrayOfInt1[b] << 3) / 11;
            break;
          case 5:
            arrayOfInt1[b] = 255;
            break;
        }
      }
      arrayOfInt2[b2] = arrayOfInt1[0] << 16 | arrayOfInt1[1] << 8 | arrayOfInt1[2];
    }
    byte b1 = 0;
    while (b1 < 10) {
      k = i & 0x7;
      n = j & 0x7;
      if (k-- != 0 && n-- != 0 && paramRectangle.width >= 2 && paramRectangle.height >= 2) {
        rect(paramGraphics, paramRectangle, (k == 6) ? paramColor : new Color(arrayOfInt2[k]), (n == 6) ? paramColor : new Color(arrayOfInt2[n]), paramInt2 - b1, b1, paramBoolean);
        i >>= 3;
        j >>= 3;
        b1++;
        continue;
      }
      break;
    }
    paramGraphics.setColor(paramColor);
    paramInt2 += (paramBoolean ? 1 : 0) - b1;
    if (paramRectangle.width <= paramInt2)
      paramInt2 = paramRectangle.width - 1;
    if (paramInt2 < 0)
      paramInt2 = 0;
    int i1 = paramBoolean ? paramRectangle.y : (paramRectangle.y + paramRectangle.height - paramInt2);
    paramGraphics.fillOval(paramRectangle.x, i1, paramInt2, paramInt2);
    paramGraphics.drawOval(paramRectangle.x, i1, paramInt2, paramInt2);
    paramGraphics.fillOval(paramRectangle.x + paramRectangle.width - paramInt2, i1, paramInt2, paramInt2);
    paramGraphics.drawOval(paramRectangle.x + paramRectangle.width - paramInt2, i1, paramInt2, paramInt2);
    paramGraphics.fillRect(paramRectangle.x + paramInt2 / 2, i1, paramRectangle.width - paramInt2, paramInt2);
    paramGraphics.drawRect(paramRectangle.x + paramInt2 / 2, i1, paramRectangle.width - paramInt2, paramInt2);
    i1 = paramBoolean ? (paramRectangle.y + paramInt2 / 2) : (paramRectangle.y - b1 * 2);
    paramGraphics.fillRect(paramRectangle.x, i1, paramRectangle.width + 1, paramRectangle.height + b1 * 2 - paramInt2 / 2);
  }

  static void rect(Graphics paramGraphics, Rectangle paramRectangle, Color paramColor1, Color paramColor2, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (paramRectangle.width <= paramInt1)
      paramInt1 = paramRectangle.width - 1;
    if (paramInt1 < 1)
      paramInt1 = 1;
    paramInt2 = paramBoolean ? (paramRectangle.height + paramInt2 * 2 - 1) : (-paramInt2 * 2);
    int i = paramInt1 / 2;
    int j = paramRectangle.x;
    int k = paramRectangle.y + (paramBoolean ? (i + 1) : (paramRectangle.height - i));
    paramInt2 += paramRectangle.y;
    if (paramBoolean) {
      paramGraphics.setColor(paramColor2);
      paramGraphics.drawLine(j, k, j, paramInt2);
      paramGraphics.drawLine(j + i, paramRectangle.y, j + paramRectangle.width - i - 1, paramRectangle.y);
      paramGraphics.drawOval(j, paramRectangle.y, paramInt1, paramInt1);
      paramGraphics.drawOval(j, paramRectangle.y + 1, paramInt1, paramInt1);
      j = paramRectangle.x++ + paramRectangle.width--;
      paramGraphics.drawOval(j - paramInt1, paramRectangle.y, paramInt1 - 1, paramInt1);
      paramGraphics.setColor(paramColor1);
      paramGraphics.drawOval(j - --paramInt1, paramRectangle.y + 1, paramInt1, paramInt1);
      paramGraphics.drawOval(j - paramInt1, paramRectangle.y + 2, paramInt1, paramInt1);
      paramGraphics.drawLine(j, k, j, paramInt2);
    } else {
      paramGraphics.setColor(paramColor2);
      paramGraphics.drawLine(j, --k, j, paramInt2);
      i = paramRectangle.y + paramRectangle.height - paramInt1;
      paramGraphics.drawOval(j, i, paramInt1, paramInt1);
      paramGraphics.setColor(paramColor1);
      paramGraphics.drawOval(++j, i, paramInt1, paramInt1);
      j = paramRectangle.x++ + paramRectangle.width--;
      paramGraphics.drawLine(j, k, j, paramInt2);
      paramGraphics.drawOval(j - --paramInt1, i++, paramInt1, paramInt1);
      paramGraphics.drawOval(j - paramInt1, i, paramInt1, paramInt1);
      paramGraphics.drawLine(paramRectangle.x + paramInt1 / 2, i + paramInt1, j - paramInt1 / 2 - 1, i + paramInt1);
    }
    paramRectangle.y++;
    paramRectangle.width--;
    paramRectangle.height -= 2;
  }
}
