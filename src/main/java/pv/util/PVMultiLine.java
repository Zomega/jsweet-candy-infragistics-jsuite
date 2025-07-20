package pv.util;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PVMultiLine {
  public static int draw(Graphics paramGraphics, String paramString, Rectangle paramRectangle, int paramInt1, boolean paramBoolean, int paramInt2) {
    FontMetrics fontMetrics = paramGraphics.getFontMetrics();
    int i = paramBoolean | (((paramInt2 & 0x8000) == 0) ? 0 : 1);
    paramInt1 |= paramInt2 >> 13 & 0x3;
    int j = 0;
    int k = -1;
    int m = -1;
    int n = (paramString == null) ? 0 : paramString.length();
    int i1 = fontMetrics.charWidth('.');
    int i2 = fontMetrics.charWidth('W');
    if (!n || paramRectangle.width < i1 || i1 < 1)
      return 0;
    int i3 = paramRectangle.height + 1;
    int i4 = fontMetrics.getHeight();
    int i5 = fontMetrics.getDescent();
    boolean bool1 = ((paramInt2 & 0x10000) == 0) ? false : true;
    boolean bool2 = ((paramInt2 & 0x20000) == 0) ? false : true;
    boolean bool3 = ((paramInt2 & 0x40000) != 0) ? false : true;
    boolean bool4 = ((paramInt2 & 0x80000) == 0) ? false : true;
    if ((paramInt2 &= 0x3F) == 0) {
      paramInt2 = i4;
    } else if (paramInt2 == 1) {
      paramInt2 = i4 - i5;
      i3 -= i5;
    }
    int i6 = paramRectangle.width;
    byte b1 = (i3 > paramInt2) ? (i3 / paramInt2) : 1;
    if (!bool4 && n > (i3 = i6 * b1 / i1)) {
      if (i2 <= i1 + 1)
        i3++;
      paramString = paramString.substring(0, n = i3);
    }
    if (paramInt1 > 2)
      i6 -= paramInt1;
    int i8 = i6 / i2;
    byte b2 = 0;
    String[] arrayOfString = new String[b1--];
    char c = Character.MIN_VALUE;
    while (true) {
      if (j < n && (c = paramString.charAt(j)) == ' ') {
        j++;
        continue;
      }
      if (j != n) {
        m = (m == 0) ? j : 0;
        int i9 = i8;
        int i10 = 0;
        boolean bool = false;
        if (c == '\n') {
          m = j;
        } else {
          while (++j < n) {
            c = paramString.charAt(j);
            if (c < ' ') {
              if (!bool4)
                k = j;
              if (c != '\t') {
                k = j;
                break;
              }
              c = ' ';
              paramString = String.valueOf(paramString.substring(0, j)) + "   " + paramString.substring(j + 1);
              n += 2;
              j--;
            }
            if (!bool) {
              if (bool = (j < i9) ? false : true) {
                i3 = fontMetrics.stringWidth(paramString.substring(m, j + 1));
                if (!(bool = (i3 <= i6) ? false : true))
                  i9 = j - m + (i6 - i3) / i2;
              }
              if (bool) {
                i10 = j;
                if (paramInt1 > 2 && j == 1 && !b2)
                  j = 0;
                if (bool4 && j != 0)
                  continue;
                break;
              }
            }
            continue;
            if (!bool4 && c == ' ')
              k = j;
          }
        }
        if (b2 < b1)
          if (j < n && k > 0) {
            if (j == i10)
              i10 = 0;
            j = k;
          } else if (!bool4 && n > j && j > m + 1 && Character.isLetter(c)) {
            if (fontMetrics.stringWidth(String.valueOf(paramString.substring(m, j)) + '-') + 2 > i6)
              j--;
            i10 = -1;
          }
        String str = paramString.substring(m, j);
        if (i10 < 0) {
          str = String.valueOf(str) + '-';
        } else if ((i10 -= m) > 0 || (b2 == b1 && j != n)) {
          if (bool = (b2 < b1 && !bool4) ? false : bool3) {
            if (i3 == 0 || i10 == 0)
              i3 = fontMetrics.stringWidth(str) + i1;
            if (i3 + i1 * 2 <= i6) {
              i10 = 0;
            } else {
              if (i10 == 0)
                i10 = j - m;
              i3 = (i2 < i1 + 1) ? 3 : 2;
              if (bool = (i10 <= i3) ? false : true)
                i10 -= i3;
            }
          }
          if (i10 > 0 && i10 < j - m)
            str = str.substring(0, i10);
          if (bool)
            str = String.valueOf(str) + "...";
        }
        arrayOfString[b2++] = str;
        i6 = paramRectangle.width;
        if (b2 <= b1 && j < n) {
          if (c < ' ')
            j++;
          paramString = paramString.substring(j);
          n = paramString.length();
          k = j = m = 0;
          if (j >= n)
            break;
          continue;
        }
      }
      break;
    }
    if (paramInt1 > 2)
      i6 -= paramInt1;
    if (paramInt2 < i4)
      i5 = (i5 + 1) / 2;
    int i7 = paramInt2 - i5;
    if (paramInt2 >= paramRectangle.height) {
      i7 -= (paramInt2 - paramRectangle.height + 1) / 2;
    } else if (i != 0) {
      if ((j = paramRectangle.height - b2 * paramInt2) > 1)
        i7 += j >>> 1;
    } else if (bool1 && paramRectangle.height > i4) {
      i7 = paramRectangle.height - paramInt2 * (b2 - 1) + i5;
    }
    j = 0;
    while (j < b2) {
      String str = arrayOfString[j++];
      n = str.length();
      i3 = fontMetrics.stringWidth(str);
      m = paramRectangle.x;
      if (paramInt1 == 1) {
        m += i6 - i3 >> 1;
      } else if (paramInt1 == 2) {
        m += i6 - i3 - 1;
      } else {
        m += 1 + paramInt1;
        paramInt1 = 0;
      }
      paramGraphics.drawString(str, m, i7 + paramRectangle.y);
      if (bool2)
        paramGraphics.drawLine(m, i7 + paramRectangle.y + 2, m + i3, i7 + paramRectangle.y + 2);
      i6 = paramRectangle.width;
      i7 += paramInt2;
    }
    return j;
  }

  public static int draw(Graphics paramGraphics, String paramString, Rectangle paramRectangle) {
    return draw(paramGraphics, paramString, paramRectangle, 0, false, 0);
  }
}
