package pv.util;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.io.InputStream;

public class PVImage {
  public static final void node(Image paramImage, Color paramColor1, Color paramColor2, int paramInt1, int paramInt2, int paramInt3) {
    if (paramImage != null) {
      Graphics graphics = paramImage.getGraphics();
      graphics.setColor((paramColor2 == null) ? Color.white : paramColor2);
      graphics.fillRect(0, 0, paramInt1--, paramInt2--);
      node(graphics, (paramColor1 == null) ? Color.lightGray : paramColor1, 0, 0, paramInt1, paramInt2, paramInt3);
      graphics.dispose();
    }
  }

  public static final void node(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt5 == 0)
      paramInt3--;
    int j = 0;
    int[] arrayOfInt1 = new int[8];
    int[] arrayOfInt2 = new int[8];
    paramGraphics.setColor(paramColor);
    if (paramInt5 >= 2) {
      int m;
      double d;
      if (paramInt3 > paramInt4)
        paramInt3 = paramInt4;
      int k = (paramInt4 - paramInt3 + 1) / 2;
      switch (paramInt5) {
        case 2:
          paramInt3 -= 2;
          paramInt2 += k;
          paramGraphics.setColor(shift(paramColor, -4));
          paramGraphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt3);
          paramGraphics.drawOval(paramInt1 + 1, paramInt2, paramInt3, paramInt3);
          paramGraphics.drawOval(paramInt1++, ++paramInt2, paramInt3, paramInt3);
          paramGraphics.setColor(shift(paramColor, 2));
          paramGraphics.drawOval(paramInt1 + 1, paramInt2, paramInt3, paramInt3);
          paramGraphics.drawOval(paramInt1 + 1, paramInt2 + 1, paramInt3, paramInt3);
          paramGraphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt3);
          paramGraphics.setColor(paramColor);
          paramGraphics.fillOval(paramInt1, paramInt2, paramInt3, paramInt3);
          paramGraphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt3);
          return;
        case 3:
          paramInt4 = paramInt3;
          d = paramInt4 / 24.0D;
          while (j < 5) {
            paramGraphics.setColor(shift(paramColor, 6 - j * 3));
            paramInt3 = (int)(paramInt4 - d * 3.0D * j);
            int n = (int)(d * ++j);
            paramGraphics.fillOval(paramInt1 + n, paramInt2 + n + k, paramInt3, paramInt3);
            if (j == 0)
              paramGraphics.drawOval(paramInt1 + n, paramInt2 + n + k, paramInt3, paramInt3);
          }
          return;
        case 4:
          paramInt3 -= 2;
          paramInt4 = k + 1;
          paramInt2 += paramInt4;
          paramGraphics.setColor(shift(paramColor, -4));
          paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt3);
          paramGraphics.setColor(shift(paramColor, 2));
          paramGraphics.drawLine(paramInt1, paramInt2 + paramInt3, paramInt1 + paramInt3, paramInt2 + paramInt3);
          paramGraphics.drawLine(paramInt1 + paramInt3, paramInt2 + 1, paramInt1 + paramInt3, paramInt2 + paramInt3);
          paramGraphics.setColor(paramColor);
          paramInt3 -= 2;
          paramGraphics.fillRect(++paramInt1, ++paramInt2, paramInt3, paramInt3);
          paramGraphics.drawRect(paramInt1, paramInt2, paramInt3, paramInt3);
          return;
        case 6:
          if ((paramInt3 & 0x1) == 0)
            paramInt3--;
          m = paramInt3 / 2;
          paramInt4 = k;
          paramInt2 += paramInt4;
          paramGraphics.setColor(shift(paramColor, 2));
          paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt3 - 1);
          paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt3 - 1, paramInt1 + paramInt3 - 1, paramInt2 + 1);
          paramGraphics.drawLine(paramInt1, paramInt2 + m, paramInt1 + paramInt3, paramInt2 + m);
          paramGraphics.drawLine(paramInt1 + m, paramInt2, paramInt1 + m, paramInt2 + paramInt3);
          paramGraphics.setColor(paramColor);
          paramInt3 -= 2;
          paramGraphics.fillOval(++paramInt1, ++paramInt2, paramInt3, paramInt3);
          paramGraphics.setColor(shift(paramColor, 2));
          paramGraphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt3);
          return;
        case 7:
        case 8:
          j = (paramInt3 + 1) / 3;
          arrayOfInt2[1] = j + paramInt2;
          arrayOfInt1[3] = paramInt1;
          arrayOfInt1[4] = paramInt1;
          arrayOfInt2[0] = paramInt2;
          arrayOfInt2[5] = paramInt2;
          arrayOfInt2[4] = paramInt2;
          arrayOfInt1[2] = paramInt1 + paramInt3;
          arrayOfInt1[1] = paramInt1 + paramInt3;
          arrayOfInt1[5] = paramInt1 + paramInt3 - j;
          arrayOfInt1[0] = paramInt1 + paramInt3 - j;
          arrayOfInt2[3] = paramInt2 + paramInt4;
          arrayOfInt2[2] = paramInt2 + paramInt4;
          paramGraphics.setColor(shift(paramColor, -3));
          paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 6);
          paramGraphics.setColor(Color.black);
          paramGraphics.drawPolygon(arrayOfInt1, arrayOfInt2, 6);
          paramGraphics.drawLine(arrayOfInt1[0], paramInt2, arrayOfInt1[0], paramInt2 + j);
          paramGraphics.drawLine(arrayOfInt1[0], paramInt2 + j, paramInt1 + paramInt3, paramInt2 + j);
          paramInt3 -= 3;
          k = 2 + paramInt4 / 18;
          paramInt4 -= paramInt4 / 6;
          if (paramInt5 == 8) {
            while ((j += k) < paramInt4)
              paramGraphics.drawLine(paramInt1 + 3, paramInt2 + j, paramInt1 + paramInt3, paramInt2 + j);
            return;
          }
          return;
        case 9:
          m = (paramInt3 - 6) / 2;
          if (m > 2)
            m = 2;
          paramInt3 -= m + m;
          paramInt4 -= m + m;
          arrayOfInt1[0] = m;
          while (j <= arrayOfInt1[0]) {
            m = j++ * 2;
            paramGraphics.setColor(shift(paramColor, -3));
            paramGraphics.fillRect(paramInt1 + m, paramInt2 + m, paramInt3, paramInt4);
            paramGraphics.setColor(shift(paramColor, 3));
            paramGraphics.drawRect(paramInt1 + m, paramInt2 + m, paramInt3, paramInt4);
          }
          return;
      }
      if (paramInt5 > 13)
        return;
      paramInt3 /= 2;
      paramInt2 += k;
      if (paramInt5 == 10 || paramInt5 == 11) {
        paramGraphics.fillRect(paramInt1, paramInt2, paramInt3 + paramInt3, paramInt3 + paramInt3);
        paramGraphics.setColor(Color.gray);
        paramGraphics.drawRect(paramInt1, paramInt2, paramInt3 + paramInt3, paramInt3 + paramInt3);
        paramGraphics.setColor(Color.black);
      } else {
        arrayOfInt1[4] = paramInt1;
        arrayOfInt1[0] = paramInt1;
        arrayOfInt2[1] = paramInt2;
        arrayOfInt1[3] = paramInt1 + paramInt3;
        arrayOfInt1[1] = paramInt1 + paramInt3;
        arrayOfInt2[2] = paramInt3 + paramInt2;
        arrayOfInt2[4] = paramInt3 + paramInt2;
        arrayOfInt2[0] = paramInt3 + paramInt2;
        arrayOfInt1[2] = paramInt3 + paramInt3 + paramInt1;
        arrayOfInt2[3] = paramInt3 + paramInt3 + paramInt2;
        paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 5);
        paramGraphics.setColor((paramInt5 == 5) ? shift(paramColor, 2) : Color.black);
        paramGraphics.drawPolygon(arrayOfInt1, arrayOfInt2, 5);
        if (paramInt5 == 5) {
          paramGraphics.setColor(shift(paramColor, -4));
          paramGraphics.drawLine(arrayOfInt1[0], arrayOfInt2[0], arrayOfInt1[1] = arrayOfInt1[1] - 1, arrayOfInt2[1] + 1);
          paramGraphics.drawLine(arrayOfInt1[0], arrayOfInt2[0], arrayOfInt1[1], arrayOfInt2[3] - 1);
          return;
        }
      }
      paramInt1 += paramInt3;
      paramInt2 += paramInt3;
      paramInt3 = (paramInt3 >= 6) ? 3 : 2;
      paramGraphics.drawLine(paramInt1 - paramInt3, paramInt2, paramInt1 + paramInt3, paramInt2);
      if ((paramInt5 & 0x1) != 0)
        paramGraphics.drawLine(paramInt1, paramInt2 - paramInt3, paramInt1, paramInt2 + paramInt3);
      return;
    }
    j = paramInt4 / 10 + 1;
    arrayOfInt1[3] = (paramInt5 == 1) ? (paramInt1 + j) : paramInt1;
    arrayOfInt1[4] = (paramInt5 == 1) ? (paramInt1 + j) : paramInt1;
    arrayOfInt1[5] = arrayOfInt1[4] + j;
    arrayOfInt1[6] = arrayOfInt1[5] + j + (paramInt3 - 5) / 4;
    arrayOfInt1[7] = arrayOfInt1[6] + j;
    arrayOfInt1[0] = arrayOfInt1[6] + j;
    arrayOfInt1[2] = paramInt1 + paramInt3 - 1;
    arrayOfInt1[1] = paramInt1 + paramInt3 - 1;
    arrayOfInt2[6] = paramInt2;
    arrayOfInt2[5] = paramInt2;
    arrayOfInt2[1] = paramInt2 + j;
    arrayOfInt2[7] = paramInt2 + j;
    arrayOfInt2[0] = paramInt2 + j;
    arrayOfInt2[4] = paramInt2 + j;
    arrayOfInt2[3] = paramInt2 + paramInt4 - 1;
    arrayOfInt2[2] = paramInt2 + paramInt4 - 1;
    paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 8);
    paramGraphics.setColor(shift(paramColor, 3));
    paramGraphics.drawPolygon(arrayOfInt1, arrayOfInt2, 8);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawLine(arrayOfInt1[3] + 1, paramInt2 + paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
    paramGraphics.drawLine(paramInt1 + paramInt3, arrayOfInt2[1] + 1, paramInt1 + paramInt3, paramInt2 + paramInt4);
    if (paramInt5 == 1) {
      paramGraphics.setColor(shift(paramColor, -6));
      paramGraphics.drawLine(arrayOfInt1[5], paramInt2 + 1, arrayOfInt1[6] - 1, paramInt2 + 1);
      paramGraphics.drawLine(arrayOfInt1[4] + 1, arrayOfInt2[4], arrayOfInt1[4] + 1, arrayOfInt2[3] - 2);
      paramGraphics.drawLine(arrayOfInt1[0], arrayOfInt2[0] + 1, arrayOfInt1[1] - 2, arrayOfInt2[0] + 1);
      paramGraphics.setColor(paramColor);
      arrayOfInt1[5] = paramInt1;
      arrayOfInt1[4] = paramInt1;
      arrayOfInt1[0] = paramInt1;
      arrayOfInt1[1] = arrayOfInt1[1] - j;
      arrayOfInt2[4] = arrayOfInt2[4] + j + 1;
      arrayOfInt2[5] = arrayOfInt2[4] + j + 1;
      arrayOfInt2[1] = arrayOfInt2[4] + j + 1;
      arrayOfInt2[0] = arrayOfInt2[4] + j + 1;
      arrayOfInt2[4] = arrayOfInt2[4] + 1;
      paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 6);
    }
    paramGraphics.setColor(shift(paramColor, -6));
    int i = paramInt1 + 1;
    paramInt4 = arrayOfInt2[1] + 1;
    paramGraphics.drawLine(i, paramInt4, i + ((paramInt5 == 1) ? j : 0), arrayOfInt2[3] - 1);
    paramGraphics.drawLine(i, paramInt4, arrayOfInt1[1] - 1, paramInt4);
    if (paramInt5 == 1) {
      paramGraphics.setColor(shift(paramColor, 3));
      paramGraphics.drawPolygon(arrayOfInt1, arrayOfInt2, 6);
    }
  }

  public static final Color shift(Color paramColor, int paramInt) {
    if (paramColor == null)
      return Color.gray;
    byte b = -1;
    int[] arrayOfInt = new int[3];
    arrayOfInt[2] = paramColor.getRGB();
    while (b++ < 2) {
      arrayOfInt[b] = arrayOfInt[2] >> b * 8 & 0xFF;
      if (paramInt == 0) {
        arrayOfInt[b] = (arrayOfInt[b] << 2) / 5;
        continue;
      }
      if (paramInt > 0) {
        arrayOfInt[b] = (arrayOfInt[b] << 1) / (paramInt + 2);
        continue;
      }
      arrayOfInt[b] = arrayOfInt[b] + ((arrayOfInt[b] - 255) * paramInt >> 3);
    }
    return new Color(arrayOfInt[2] << 16 | arrayOfInt[1] << 8 | arrayOfInt[0]);
  }

  public static final void triangle(Graphics paramGraphics, Color paramColor1, Color paramColor2, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Polygon polygon = null;
    int i = 0;
    int j = paramInt4 >> 4 & 0xF;
    paramInt4 = (paramInt4 & 0x7) - 1;
    if (paramInt4 < 1 || paramInt4 > 4) {
      paramInt4 = 0;
      j &= 0x1;
    }
    if (paramInt3 < 2 || (paramInt4 == 0 && paramInt3 < 4))
      return;
    if (paramColor1 == null)
      paramColor1 = paramColor2 = Color.black;
    if (paramColor2 == null) {
      if ((j & 0xC) != 4)
        i = (paramInt4 == 0) ? (((j & 0x1) == 0) ? -6 : 4) : (((j & 0x2) == 0) ? -(6 + (j & 0x1) * 2) : ((j & 0x1) * 9 - 7));
      paramColor2 = shift(paramColor1, i);
      if ((j & 0x8) != 0) {
        i = 1;
      } else {
        i = (paramInt4 == 0) ? (((j & 0x1) == 0) ? 4 : -6) : (3 - (j & 0x1) * (2 + (j & 0x2) * 4));
      }
      paramColor1 = shift(paramColor1, i);
    }
    paramGraphics.setColor(paramColor1);
    if (paramInt4 != 0) {
      paramInt3 = paramInt3 + 1 & 0xFE;
      polygon = new Polygon(new int[4], new int[4], 4);
      polygon.ypoints[1] = 0;
      polygon.xpoints[1] = 0;
      polygon.ypoints[0] = 0;
      polygon.xpoints[0] = 0;
      polygon.ypoints[2] = paramInt3;
      polygon.xpoints[2] = paramInt3;
      if ((paramInt4 & 0x1) == 1) {
        polygon.ypoints[2] = polygon.ypoints[2] - 2;
        polygon.ypoints[0] = polygon.ypoints[2] / 2;
        polygon.xpoints[2] = polygon.ypoints[0];
        polygon.xpoints[1] = polygon.ypoints[0];
      } else {
        polygon.xpoints[2] = polygon.xpoints[2] - 2;
        polygon.xpoints[0] = polygon.xpoints[2] / 2;
        polygon.ypoints[2] = polygon.xpoints[0];
        polygon.ypoints[1] = polygon.xpoints[0];
      }
      polygon.xpoints[3] = polygon.xpoints[0];
      polygon.ypoints[3] = polygon.ypoints[0];
      if (paramInt4 == 3) {
        polygon.xpoints[3] = polygon.xpoints[2] + 1;
        polygon.xpoints[2] = polygon.xpoints[0] + 1;
        polygon.xpoints[1] = polygon.xpoints[0] + 1;
        polygon.xpoints[0] = polygon.xpoints[3];
      } else if (paramInt4 == 4) {
        polygon.ypoints[3] = polygon.ypoints[2] + 1;
        polygon.ypoints[2] = polygon.ypoints[0] + 1;
        polygon.ypoints[1] = polygon.ypoints[0] + 1;
        polygon.ypoints[0] = polygon.ypoints[3];
      }
      polygon.translate(paramInt1, paramInt2);
      paramGraphics.drawPolygon(polygon);
      paramGraphics.fillPolygon(polygon);
      if (paramInt3 >= 6 && (j & 0xC) != 4) {
        paramGraphics.setColor(paramColor2);
        if ((j & 0x8) != 0) {
          j = (paramInt4 > 2) ? 3 : 1;
          paramGraphics.drawLine(polygon.xpoints[2], polygon.ypoints[2], polygon.xpoints[j], polygon.ypoints[j]);
          return;
        }
        if (paramInt4 < 3) {
          paramGraphics.drawLine(polygon.xpoints[1], polygon.ypoints[1], polygon.xpoints[0], polygon.ypoints[0]);
          paramGraphics.drawLine(polygon.xpoints[2], polygon.ypoints[2], polygon.xpoints[0], polygon.ypoints[0]);
          return;
        }
        paramGraphics.drawLine(polygon.xpoints[1], polygon.ypoints[1], polygon.xpoints[2], polygon.ypoints[2]);
      }
      return;
    }
    if ((j & 0xC) == 0)
      paramGraphics.setColor(paramColor2);
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + paramInt3 - 2, paramInt2);
    paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + paramInt3 - 2);
    j = (paramInt3 - 4) / 2;
    i = (paramInt3 - j - 1) / 2;
    paramGraphics.drawLine(paramInt1 + 2, paramInt2 + i, paramInt1 + 1 + j + j, paramInt2 + i);
    paramGraphics.drawLine(paramInt1 + 2, paramInt2 + ++i, paramInt1 + 2 + j, paramInt2 + i + j);
    paramGraphics.setColor(paramColor1);
    paramGraphics.drawLine(paramInt1 + 1, paramInt2 + paramInt3 - 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt3 - 1);
    paramGraphics.drawLine(paramInt1 + paramInt3 - 1, paramInt2 + 1, paramInt1 + paramInt3 - 1, paramInt2 + paramInt3 - 1);
    paramInt1 += 2 + j;
    paramInt2 += i;
    paramGraphics.drawLine(paramInt1, paramInt2 + j, paramInt1 + j, paramInt2);
    paramGraphics.drawLine(paramInt1 + 1, paramInt2 + j, paramInt1 + j, paramInt2 + 1);
  }

  public static final Dimension center(Graphics paramGraphics, Color paramColor, Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    int i = paramInt5;
    int j = paramInt5;
    int k = (paramInt6 & 0xF) - 1;
    if (k >= 9 && k <= 12) {
      paramInt1 += paramInt5;
      paramInt2 += paramInt5;
      k -= 7;
      paramInt3 = (paramInt3 - (paramInt5 += paramInt5 + 2)) / k;
      paramInt4 = (paramInt4 - paramInt5) / k;
      if (paramInt6 > 15) {
        paramInt1++;
        paramInt2++;
      }
      paramInt5 = -1;
      if (paramObject == null) {
        paramObject = shift(paramColor, -4);
        paramColor = shift(paramColor, 2);
      }
      while (++paramInt5 < 2) {
        paramGraphics.setColor((((paramInt5 != 0) ? false : true) == ((paramInt6 <= 15) ? false : true)) ? paramColor : (Color)paramObject);
        j = -1;
        while (++j <= paramInt3) {
          i = -1;
          while (++i <= paramInt4) {
            if ((i & 0x1) == (j & 0x1) || (k & 0x1) != 0)
              paramGraphics.drawLine(paramInt1 + j * k, paramInt2 + i * k, paramInt1 + j * k, paramInt2 + i * k);
          }
        }
        paramInt1++;
        paramInt2++;
      }
      return null;
    }
    if (k > 7) {
      j = ((Image)paramObject).getWidth(null);
      i = ((Image)paramObject).getHeight(null);
      if (j > paramInt3)
        j = paramInt3;
      if (j > paramInt5)
        j = paramInt5;
      if (i > paramInt4)
        i = paramInt4;
      if (i > paramInt5)
        i = paramInt5;
      if (paramInt6 > 15) {
        if (j < paramInt3)
          paramInt1++;
        if (i < paramInt4)
          paramInt2++;
      }
    } else if (k > 4) {
      j = i = 13;
    } else {
      paramInt3 = paramInt3 - 1 | 0x1;
      paramInt4 = paramInt4 - 1 | 0x1;
      if (k <= 0) {
        if (paramInt3 < paramInt5)
          paramInt5 = paramInt3;
        if (paramInt4 < paramInt5)
          paramInt5 = paramInt4;
        i = j = paramInt5;
      } else if ((k & 0x1) == 0) {
        if (paramInt3 < paramInt5)
          paramInt5 = paramInt3;
        if (paramInt4 + paramInt4 < paramInt5 + 3)
          paramInt5 = paramInt4 + paramInt4 - 3;
        j = paramInt5;
        i = paramInt5 / 2 + 3;
      } else {
        if (paramInt4 < paramInt5)
          paramInt5 = paramInt4;
        if (paramInt3 + paramInt3 < paramInt5 + 3)
          paramInt5 = paramInt3 + paramInt3 - 3;
        i = paramInt5;
        j = paramInt5 / 2 + 3;
      }
    }
    paramInt1 += (paramInt3 - j) / 2;
    paramInt2 += (paramInt4 - i) / 2;
    if (paramGraphics != null)
      if (k > 7) {
        paramGraphics.drawImage((Image)paramObject, paramInt1, paramInt2, j, i, null);
      } else if (k > 4) {
        checkbox(paramGraphics, paramColor, paramInt1, paramInt2, !((paramInt6 & 0x10) == 0), !((paramInt6 & 0x20) == 0));
      } else {
        triangle(paramGraphics, paramColor, (Color)paramObject, paramInt1, paramInt2, paramInt5, paramInt6);
      }
    return new Dimension(j, i);
  }

  public static final void checkbox(Graphics paramGraphics, Color paramColor, boolean paramBoolean) {
    checkbox(paramGraphics, paramColor, 0, 0, paramBoolean, false);
  }

  public static final void checkbox(Graphics paramGraphics, Color paramColor, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramColor == null)
      paramColor = Color.white;
    paramGraphics.setColor(Color.gray);
    paramGraphics.fillRect(paramInt1++, paramInt2++, 13, 13);
    paramGraphics.setColor(paramColor);
    paramGraphics.fillRect(paramInt1 + 1, paramInt2 + 1, 9, 9);
    if (!paramBoolean2) {
      paramGraphics.setColor(Color.white);
      paramGraphics.drawLine(paramInt1 + 11, paramInt2, paramInt1 + 11, paramInt2 + 11);
      paramGraphics.drawLine(paramInt1, paramInt2 + 11, paramInt1 + 11, paramInt2 + 11);
      paramGraphics.setColor(Color.darkGray);
      paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + 10, paramInt2);
      paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt2 + 10);
      paramGraphics.setColor(shift(paramColor, 0));
      paramGraphics.drawLine(paramInt1 + 10, paramInt2 + 1, paramInt1 + 10, paramInt2 + 10);
      paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 10, paramInt1 + 10, paramInt2 + 10);
    }
    if (paramBoolean1) {
      paramInt1 += 2;
      paramInt2 += 2;
      paramGraphics.setColor(Color.black);
      paramGraphics.drawLine(paramInt1, paramInt2 + 2, paramInt1 + 2, paramInt2 + 4);
      paramGraphics.drawLine(paramInt1, paramInt2 + 3, paramInt1 + 2, paramInt2 + 5);
      paramGraphics.drawLine(paramInt1, paramInt2 + 4, paramInt1 + 2, paramInt2 + 6);
      paramInt1 += 2;
      paramGraphics.drawLine(paramInt1, paramInt2 + 4, paramInt1 + 4, paramInt2);
      paramGraphics.drawLine(paramInt1, paramInt2 + 5, paramInt1 + 4, paramInt2 + 1);
      paramGraphics.drawLine(paramInt1, paramInt2 + 6, paramInt1 + 4, paramInt2 + 2);
    }
  }

  public static final Image file(Component paramComponent, String paramString) {
    Image image = null;
    Component component;
    for (component = paramComponent; !(component instanceof Applet); component = component.getParent()) {
      if (component == null) {
        try {
          image = Toolkit.getDefaultToolkit().getImage(paramString);
        } catch (Exception exception) {}
        break;
      }
    }
    if (component != null)
      try {
        image = ((Applet)component).getImage(((Applet)component).getDocumentBase(), paramString);
      } catch (Exception exception) {}
    return wait(image, paramComponent);
  }

  public static final Image file11(Component paramComponent, String paramString) {
    Image image = null;
    if (paramString.indexOf('.') < 0)
      paramString = String.valueOf(paramString) + ".gif";
    try {
      InputStream inputStream = paramComponent.getClass().getResourceAsStream(paramString);
      if (inputStream != null) {
        byte[] arrayOfByte = new byte[inputStream.available()];
        inputStream.read(arrayOfByte);
        image = Toolkit.getDefaultToolkit().createImage(arrayOfByte);
      }
    } catch (Exception exception) {}
    return (image == null) ? file(paramComponent, paramString) : wait(image, paramComponent);
  }

  public static final Image wait(Image paramImage, Component paramComponent) {
    if (paramImage != null)
      try {
        MediaTracker mediaTracker = new MediaTracker(paramComponent);
        mediaTracker.addImage(paramImage, 0);
        mediaTracker.waitForID(0, 3000L);
        return (paramImage.getWidth(null) > 0) ? paramImage : null;
      } catch (InterruptedException interruptedException) {}
    return null;
  }
}
