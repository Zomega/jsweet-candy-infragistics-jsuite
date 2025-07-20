package pv.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PVBorder {
  public static final int NONE = 0;

  public static final int PLANE = 1;

  public static final int RAISED = 11;

  public static final int SUNKEN = 21;

  public static final int OUTDENT = 31;

  public static final int INDENT = 41;

  public static final int PICTURE = 51;

  public static final int RAISED2 = 61;

  public static final int SUNKEN2 = 71;

  public static final int BOTTOM_RIGHT = 81;

  public static final int TOP_LEFT = 91;

  public static final int FOCUS = 256;

  public static void draw(Graphics paramGraphics, Rectangle paramRectangle, int paramInt, Color paramColor, boolean paramBoolean) {
    draw(paramGraphics, paramRectangle, set(paramInt, !paramBoolean), set(paramInt, paramBoolean), 0x7 & (((paramInt & 0x38000) == 0) ? (paramInt >>> 8) : (paramInt >>> 30)), paramColor);
  }

  public static void draw(Graphics paramGraphics, Rectangle paramRectangle, int paramInt, Color paramColor) {
    draw(paramGraphics, paramRectangle, paramInt, paramColor, false);
  }

  public static void draw(Graphics paramGraphics, Rectangle paramRectangle, int paramInt1, int paramInt2, int paramInt3, Color paramColor) {
    if (paramRectangle != null) {
      if (paramInt1 == 0 && paramInt3-- == 0)
        return;
      paramInt3++;
      if (paramGraphics == null) {
        int m;
        for (m = 1; m < 10 && ((paramInt1 >>= 3) & 0x7) != 0 && ((paramInt2 >>= 3) & 0x7) != 0; m++);
        m *= paramInt3;
        if (m > paramRectangle.width >>> 1)
          m = paramRectangle.width >>> 1;
        if (m > paramRectangle.height >>> 1)
          m = paramRectangle.height >>> 1;
        paramRectangle.x += m;
        paramRectangle.y += m;
        paramRectangle.width -= m << 1;
        paramRectangle.height -= m << 1;
        return;
      }
      if (paramColor == null)
        paramColor = Color.lightGray;
      if (paramInt1 == 0) {
        paramGraphics.setColor(paramColor);
        while (paramInt3-- > 0) {
          focus(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width - 1, paramRectangle.height - 1);
          paramRectangle.grow(-1, -1);
        }
        return;
      }
      int[] arrayOfInt1 = new int[3];
      int[] arrayOfInt2 = new int[6];
      int i = 170 + ((paramColor.getRed() - 170) * 5 >> 3);
      int j = 170 + ((paramColor.getGreen() - 170) * 5 >> 3);
      int k = 170 + ((paramColor.getBlue() - 170) * 5 >> 3);
      byte b1;
      for (b1 = 0; b1 < 6; b1++) {
        arrayOfInt1[0] = i;
        arrayOfInt1[1] = j;
        arrayOfInt1[2] = k;
        for (byte b = 0; b < 3; b++) {
          switch (b1) {
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
        arrayOfInt2[b1] = arrayOfInt1[0] << 16 | arrayOfInt1[1] << 8 | arrayOfInt1[2];
      }
      byte b2 = 0;
      while (b2 < 10) {
        i = paramInt1 & 0x7;
        k = paramInt2 & 0x7;
        if (i-- != 0) {
          if (k-- == 0)
            return;
          for (b1 = 0; b1 < paramInt3 && paramRectangle.width >= 2 && paramRectangle.height >= 2; b1++)
            rect(paramGraphics, paramRectangle, (i == 6) ? paramColor : new Color(arrayOfInt2[i]), (k == 6) ? paramColor : new Color(arrayOfInt2[k]));
          paramInt1 >>= 3;
          paramInt2 >>= 3;
          b2++;
          continue;
        }
        break;
      }
    }
  }

  static void rect(Graphics paramGraphics, Rectangle paramRectangle, Color paramColor1, Color paramColor2) {
    int i = paramRectangle.x + paramRectangle.width - 1;
    int j = paramRectangle.y + paramRectangle.height - 1;
    paramGraphics.setColor(paramColor2);
    paramGraphics.drawLine(paramRectangle.x, paramRectangle.y, paramRectangle.x, j);
    paramGraphics.drawLine(paramRectangle.x, paramRectangle.y, i, paramRectangle.y);
    paramGraphics.setColor(paramColor1);
    paramGraphics.drawLine(paramRectangle.x, j, i, j);
    paramGraphics.drawLine(i, paramRectangle.y, i, j);
    if (paramRectangle.width > 2) {
      paramRectangle.x++;
      paramRectangle.width -= 2;
    }
    if (paramRectangle.height > 2) {
      paramRectangle.y++;
      paramRectangle.height -= 2;
    }
  }

  public static final int set(int paramInt, boolean paramBoolean) {
    if ((paramInt & 0x38000) != 0)
      return paramBoolean ? (paramInt & 0x7FFF) : (paramInt >> 15 & 0x7FFF);
    if ((paramInt &= 0xFF) > 40 && (paramInt < 51 || (paramInt > 70 && paramInt < 81) || (paramInt > 90 && paramInt < 101))) {
      paramInt -= 10;
      paramBoolean = !paramBoolean;
    }
    switch (paramInt) {
      case 0:
        return 0;
      case 1:
        return 7;
      case 2:
        return 1;
      case 3:
        return 33;
      case 4:
        return 1433;
      case 5:
        return 125649;
      case 6:
        return 342387;
      case 7:
        return 22211345;
      case 8:
        return 175304025;
      case 9:
        return 500914385;
      case 10:
        return 175304107;
      case 11:
        return paramBoolean ? 1 : 6;
      case 12:
        return paramBoolean ? 2 : 5;
      case 13:
        return paramBoolean ? 17 : 37;
      case 14:
        return paramBoolean ? 209 : 373;
      case 15:
        return paramBoolean ? 1681 : 2997;
      case 16:
        return paramBoolean ? 145041 : 159148;
      case 17:
        return paramBoolean ? 42796 : 155060;
      case 18:
        return paramBoolean ? 4718545 : 10223603;
      case 19:
        return paramBoolean ? 440401617 : 635436971;
      case 20:
        return paramBoolean ? 301801425 : 788488174;
      case 21:
        return paramBoolean ? 6 : 1;
      case 22:
        return paramBoolean ? 5 : 2;
      case 23:
        return paramBoolean ? 38 : 10;
      case 24:
        return paramBoolean ? 302 : 83;
      case 25:
        return paramBoolean ? 2421 : 659;
      case 26:
        return paramBoolean ? 14708 : 5347;
      case 27:
        return paramBoolean ? 117676 : 145033;
      case 28:
        return paramBoolean ? 1277939 : 393163;
      case 29:
        return paramBoolean ? 635436971 : 174063186;
      case 30:
        return paramBoolean ? 905928693 : 184332234;
      case 31:
        return paramBoolean ? 42 : 21;
      case 32:
        return paramBoolean ? 49 : 14;
      case 33:
        return paramBoolean ? 409 : 94;
      case 34:
        return paramBoolean ? 3289 : 734;
      case 35:
        return paramBoolean ? 19801 : 6004;
      case 36:
        return paramBoolean ? 157986 : 84276;
      case 37:
        return paramBoolean ? 1267921 : 342388;
      case 38:
        return paramBoolean ? 10180817 : 2739124;
      case 39:
        return paramBoolean ? 81152209 : 21907828;
      case 40:
        return paramBoolean ? 651577553 : 175262644;
      case 51:
        return paramBoolean ? 640899281 : 214251892;
      case 52:
        return paramBoolean ? 510188825 : 197961524;
      case 53:
        return paramBoolean ? 695515545 : 221615987;
      case 54:
        return paramBoolean ? 624105881 : 226968371;
      case 55:
        return paramBoolean ? 881216801 : 211436326;
      case 56:
        return paramBoolean ? 649271577 : 194046324;
      case 57:
        return paramBoolean ? 647185185 : 210135348;
      case 58:
        return paramBoolean ? 882524961 : 210128166;
      case 59:
        return paramBoolean ? 649173273 : 175331637;
      case 60:
        return paramBoolean ? 482035297 : 175000998;
      case 61:
        return paramBoolean ? 121 : 446;
      case 62:
        return paramBoolean ? 185 : 382;
      case 63:
        return paramBoolean ? 186 : 381;
      case 64:
        return paramBoolean ? 1017 : 3582;
      case 65:
        return paramBoolean ? 1529 : 3070;
      case 66:
        return paramBoolean ? 1530 : 3069;
      case 67:
        return paramBoolean ? 7801 : 28606;
      case 68:
        return paramBoolean ? 11961 : 24446;
      case 69:
        return paramBoolean ? 11962 : 24445;
      case 70:
        return paramBoolean ? 62417 : 228846;
      case 81:
        return paramBoolean ? 1 : 7;
      case 82:
        return paramBoolean ? 2 : 7;
      case 83:
        return paramBoolean ? 17 : 63;
      case 84:
        return paramBoolean ? 209 : 511;
      case 85:
        return paramBoolean ? 209 : 505;
      case 86:
        return paramBoolean ? 5 : 7;
      case 87:
        return paramBoolean ? 6 : 7;
      case 88:
        return paramBoolean ? 46 : 63;
      case 89:
        return paramBoolean ? 302 : 511;
      case 90:
        return paramBoolean ? 302 : 510;
    }
    return paramInt;
  }

  public static void focus(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt3 & 0x1;
    int j = paramInt2 - 1;
    paramInt3 += paramInt1;
    j += 2;
    while (j <= paramInt4 + paramInt2) {
      paramGraphics.drawLine(paramInt1, j, paramInt1, j);
      if (j + i <= paramInt4 + paramInt2)
        paramGraphics.drawLine(paramInt3, j + i, paramInt3, j + i);
    }
    i = paramInt4 & 0x1;
    paramInt4 += paramInt2;
    j = paramInt1 - 1;
    j += 2;
    while (j <= paramInt3) {
      paramGraphics.drawLine(j, paramInt2, j, paramInt2);
      if (j + i <= paramInt3)
        paramGraphics.drawLine(j + i, paramInt4, j + i, paramInt4);
    }
  }

  public static void shadow(Graphics paramGraphics, Rectangle paramRectangle, Color paramColor1, Color paramColor2, int paramInt1, int paramInt2) {
    if (paramInt1 > 0 && paramRectangle != null) {
      if (paramInt2 > paramRectangle.width)
        paramInt2 = paramRectangle.width;
      if (paramInt2 > paramRectangle.height)
        paramInt2 = paramRectangle.height;
      if (paramInt2 > 0) {
        paramRectangle.width -= paramInt2;
        paramRectangle.height -= paramInt2;
        if (paramGraphics != null) {
          int i = paramInt1 & 0x1;
          int j = paramInt1 + 1 & 0x1;
          paramGraphics.setColor(paramColor2);
          paramGraphics.fillRect(paramRectangle.x + i * paramRectangle.width, paramRectangle.y, paramInt2, paramInt2);
          paramGraphics.fillRect(paramRectangle.x + j * paramRectangle.width, paramRectangle.y + paramRectangle.height, paramInt2, paramInt2);
          i = (2 - (paramInt1 & 0x2)) / 2;
          j = (4 - paramInt1) / 2;
          paramGraphics.setColor(paramColor1);
          paramGraphics.fillRect(paramRectangle.x + i * paramInt2, paramRectangle.y + j * paramRectangle.height, paramRectangle.width, paramInt2);
          paramGraphics.fillRect(paramRectangle.x + i * paramRectangle.width, paramRectangle.y + j * paramInt2, paramInt2, paramRectangle.height);
        }
        if (paramInt1 > 2)
          paramRectangle.y += paramInt2;
        if ((paramInt1 & 0x2) != 0)
          paramRectangle.x += paramInt2;
      }
    }
  }
}
