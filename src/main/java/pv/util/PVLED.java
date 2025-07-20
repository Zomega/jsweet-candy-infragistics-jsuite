package pv.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

public class PVLED {
  public static void draw(Graphics paramGraphics, String paramString, Rectangle paramRectangle) {
    draw(paramGraphics, paramString, paramRectangle, 0, false, 0, 0, null, null);
  }

  public static void draw(Graphics paramGraphics, String paramString, Rectangle paramRectangle, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, Color paramColor1, Color paramColor2) {
    if (paramGraphics == null || paramRectangle == null || paramString == null)
      return;
    if (paramInt2 == paramInt3 || paramColor2 == null)
      paramInt2 = paramInt3 = 100000;
    if (paramColor1 != null)
      paramGraphics.setColor((paramInt1 >= paramInt2) ? paramColor2 : paramColor1);
    Polygon polygon = new Polygon(new int[7], new int[7], 7);
    byte b = 1;
    int i = paramRectangle.height;
    int j = paramBoolean ? 16 : 9;
    while (i > 7 * (b + 1) + 4 * (i - b) / j)
      b++;
    int k = (i - b) / j;
    if (k >= b) {
      k = b;
      if (j > 12)
        k--;
    }
    if (k == 0)
      k++;
    int m = (k >>> 2) + 3 >>> 1;
    int n = k + paramRectangle.x;
    int i1 = n + m;
    int i2 = n + k + b + 1;
    int i3 = i2 + k + b + k;
    int i4 = (i - b * 5 + k * 4 >>> 1) + paramRectangle.y;
    int i5 = i4 + m;
    int i6 = i4 + k + b + b;
    int i7 = i6 + m;
    int i8 = i6 + k + b + b + k - 1;
    int i9 = i8 - k + m + 1;
    int i10 = i6 + k - 1 - i4 + m + m;
    int i11 = i3 - n + m + m;
    j = (i10 >> 1) + m;
    int i12 = (i8 + i4 >> 1) - 1;
    int i13 = i3 - n + m * 3 + (k >>> 1);
    k--;
    int i14 = paramString.length();
    int i15 = paramRectangle.x + paramRectangle.width;
    while (n < i15 && paramInt1 < i14) {
      if (paramInt1 >= 0) {
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        if (paramInt1 == paramInt2)
          paramGraphics.setColor(paramColor2);
        if (paramInt1 == paramInt3)
          paramGraphics.setColor(paramColor1);
        char c;
        switch (c = paramString.charAt(paramInt1)) {
          case 'Q':
            poly45(paramGraphics, polygon, i3 - m, i8 + b + (k >> 1) + m + 1, -(b + (k >> 1)), k);
          case '0':
          case 'O':
          case 'Ö':
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
          case 'C':
          case 'Ç':
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case '2':
            poly0(paramGraphics, polygon, n, i4, i11 + m, k, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11 + m, -k, 1);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            break;
          case 'H':
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
          case '4':
            poly0(paramGraphics, polygon, n, i4, k, i10 + m, -1);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i4, -k, i10 + m, -1);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 + m, 1);
            break;
          case '8':
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
          case '9':
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
          case '3':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            break;
          case '5':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 1);
            break;
          case '6':
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
          case 'b':
          case 'ß':
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
          case 'o':
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
          case '_':
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case '$':
            i16 = (k >> 1) + m + m;
            paramGraphics.fillRect(i2, i4 - i16 - 1, k + 1, i16);
            paramGraphics.fillRect(i2, i8 + 2, k + 1, i16);
            i16 = (i8 - i5 - k * 3 >> 1) - 2;
            paramGraphics.fillRect(i2, i4 + k + 2, k + 1, i16);
            paramGraphics.fillRect(i2, i6 + k + 2, k + 1, i16);
          case 'S':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            break;
          case 'A':
          case 'Ä':
          case 'Å':
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
          case 'h':
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 + m, 1);
          case 'r':
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
          case '-':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            break;
          case 'E':
          case '£':
          case 'Æ':
          case 'É':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 1);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 1);
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            break;
          case 'B':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
          case 'D':
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, n, i4, k, i10 + m, -2);
            if (k == 1) {
              poly0(paramGraphics, polygon, n, i7, k, i10 + m - 1, 2);
            } else {
              poly0(paramGraphics, polygon, n, i7, k, i10 + m, 2);
            }
            poly0(paramGraphics, polygon, i1 + k + 1, i8, i11 - k - 1, -k, -2);
            poly0(paramGraphics, polygon, i1 + k + 1, i4, i11 - k - 1, k, -2);
            break;
          case 'R':
            poly65(paramGraphics, polygon, i3 - m, i8, -j + (k >> 2) + 1, k);
          case 'P':
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
          case 'F':
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            break;
          case 'G':
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i2, i6, i11 + k >> 1, k, 0);
            poly0(paramGraphics, polygon, i1, i8, i11 - m, -k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 + m, 0);
            polygon.ypoints[1] = i8;
            paramGraphics.drawPolygon(polygon);
            paramGraphics.fillPolygon(polygon);
            break;
          case 'I':
            poly0(paramGraphics, polygon, i1 + m, i8, i11 - m - m, -k, 0);
            poly0(paramGraphics, polygon, i1 + m, i4, i11 - m - m, k, 0);
            paramGraphics.fillRect(i2, i5 + k + 1, k + 1, i8 - k - m - i5 - k - 1);
            break;
          case 'd':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
          case 'J':
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i3, i4, -k, i10 + m, -1);
            break;
          case 'W':
            poly0(paramGraphics, polygon, i2, i5, k, i10, 0);
            poly0(paramGraphics, polygon, i2, i7, k, i10 - k - m, 2);
          case 'U':
          case 'Ü':
            poly0(paramGraphics, polygon, i3, i4, -k, i10 + m, -1);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
          case 'L':
            poly0(paramGraphics, polygon, n, i4, k, i10 + m, -1);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case 'N':
          case 'Ñ':
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
            poly0(paramGraphics, polygon, i3, i4, -k, i10 + m, -1);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 + m, 1);
            poly0(paramGraphics, polygon, n, i4, k, i10 + m, -1);
            i17 = k * 3 / 2;
            i18 = (b << 1) + (k + 1 >> 1);
            polygon.xpoints[2] = polygon.xpoints[4] + m + 1 + (k >> 3);
            polygon.xpoints[1] = polygon.xpoints[4] + m + 1 + (k >> 3);
            polygon.xpoints[0] = polygon.xpoints[4] + m + 1 + (k >> 3);
            polygon.xpoints[6] = polygon.xpoints[4] + m + 1 + (k >> 3);
            polygon.xpoints[5] = polygon.xpoints[0] + i18;
            polygon.xpoints[4] = polygon.xpoints[0] + i18;
            polygon.xpoints[3] = polygon.xpoints[0] + i18;
            polygon.ypoints[1] = polygon.ypoints[4] + m + 1 + (b + k >> 1);
            polygon.ypoints[0] = polygon.ypoints[4] + m + 1 + (b + k >> 1);
            polygon.ypoints[6] = polygon.ypoints[4] + m + 1 + (b + k >> 1);
            polygon.ypoints[2] = polygon.ypoints[0] + i17;
            polygon.ypoints[4] = polygon.ypoints[2] + i18;
            polygon.ypoints[3] = polygon.ypoints[2] + i18;
            polygon.ypoints[5] = polygon.ypoints[3] - i17;
            paramGraphics.drawPolygon(polygon);
            paramGraphics.fillPolygon(polygon);
            break;
          case 'M':
            poly0(paramGraphics, polygon, i2, i5 + k + m, k, i10 - k, -2);
            poly0(paramGraphics, polygon, i2, i7 + m, k, i10, 0);
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
          case '7':
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 + m, 1);
            break;
          case 'T':
            poly0(paramGraphics, polygon, n - (k + 1) / 2, i4, i11 + m + m + k, k, 0);
            poly0(paramGraphics, polygon, i2, i5 + k + m, k, i10 - k, -2);
            poly0(paramGraphics, polygon, i2, i7 + m, k, i10, 1);
            break;
          case 'f':
            poly0(paramGraphics, polygon, i1, i7, i11, k, 0);
            poly0(paramGraphics, polygon, i2, i5, k, i6 - i4 - m - 1, 2);
            poly0(paramGraphics, polygon, i2, i7 + k + m + 1, k, i10 - k / 2, -2);
            poly0(paramGraphics, polygon, i2 + m, i4, b + k + m, k, 0);
            break;
          case 't':
            poly0(paramGraphics, polygon, i1, i6 - 1, i11, k, 0);
            poly0(paramGraphics, polygon, i2, i4 - 1, k, i6 - i4 - m - 1, 2);
            poly0(paramGraphics, polygon, i2, i6 + k + m, k, i10 - k, -2);
            poly0(paramGraphics, polygon, i2 + m, i8, b + k + m, -k, 0);
            break;
          case 'm':
            poly0(paramGraphics, polygon, i2, i7 + k + 1, k, i10 - k, -2);
          case 'n':
          case 'ñ':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 + m, 1);
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
            break;
          case 'p':
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, n, i9, k, i10, 0);
            break;
          case 'g':
            poly0(paramGraphics, polygon, i1, i8 + i10 - k + m, i11, -k, 0);
          case 'q':
            poly0(paramGraphics, polygon, i3, i9, -k, i10 - m, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case 'k':
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
            poly0(paramGraphics, polygon, n, i4, k, i10 + m, -1);
            i19 = (int)((k + 0.5D) * 3.0D) >> 2;
            i20 = (i11 >> 1) - k + m;
            if (k == 0)
              i20--;
            polygon.xpoints[1] = i1 + k + 1;
            polygon.xpoints[0] = i1 + k + 1;
            polygon.xpoints[6] = i1 + k + 1;
            polygon.xpoints[2] = polygon.xpoints[0] + i19;
            polygon.xpoints[5] = polygon.xpoints[0] + (i20 << 1);
            polygon.xpoints[3] = polygon.xpoints[5] + i19;
            polygon.xpoints[4] = polygon.xpoints[5] + i19;
            polygon.ypoints[0] = i8 + i6 >> 1;
            polygon.ypoints[6] = i8 + i6 >> 1;
            polygon.ypoints[2] = polygon.ypoints[0] + i19;
            polygon.ypoints[1] = polygon.ypoints[0] + i19;
            polygon.ypoints[4] = polygon.ypoints[0] - i20;
            polygon.ypoints[5] = polygon.ypoints[0] - i20;
            polygon.ypoints[3] = polygon.ypoints[4] + i19;
            paramGraphics.drawPolygon(polygon);
            paramGraphics.fillPolygon(polygon);
            poly45(paramGraphics, polygon, i3, i8, -(b + k - m + 1), k);
            break;
          case '1':
          case 'l':
          case '|':
            poly0(paramGraphics, polygon, i2, i4, k, i10 + m, -1);
            poly0(paramGraphics, polygon, i2, i7, k, i10 + m, 1);
            break;
          case 's':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i1, i6 + (b + b + k + 1 >> 1), i11, k, 0);
            poly0(paramGraphics, polygon, n, i6 + (1 + m >> 1), k, (k * 5 >> 2) + b - 1, 0);
            poly0(paramGraphics, polygon, i3, i6 + (1 + m >> 1) + (b + b + k + 1 >> 1), -k, (k * 5 >> 2) + b, 0);
            break;
          case 'V':
          case 'v':
            poly65(paramGraphics, polygon, i2 - 1 + (k >> 1), i8, ((k == 0) ? 1 : 0) - j + m, k);
            if (c == 'V') {
              polygon.xpoints[0] = polygon.xpoints[5];
              polygon.xpoints[6] = polygon.xpoints[5];
              polygon.xpoints[3] = polygon.xpoints[5] - k;
              polygon.xpoints[2] = polygon.xpoints[5] - k;
              polygon.xpoints[1] = polygon.xpoints[2] + (k >> 1);
              polygon.ypoints[5] = polygon.ypoints[5] - m + 1;
              polygon.ypoints[4] = polygon.ypoints[5] - m + 1;
              polygon.ypoints[3] = polygon.ypoints[4] - m;
              polygon.ypoints[0] = i4 + (k >> 1);
              polygon.ypoints[2] = i4 + (k >> 1);
              polygon.ypoints[6] = i4 + (k >> 1);
              polygon.ypoints[1] = i4;
              paramGraphics.drawPolygon(polygon);
              paramGraphics.fillPolygon(polygon);
            }
            poly65(paramGraphics, polygon, i2 + k / 2 + 1, i8, j + m - ((k == 0) ? 1 : 0), k);
            if (c == 'V') {
              polygon.xpoints[0] = polygon.xpoints[5];
              polygon.xpoints[6] = polygon.xpoints[5];
              polygon.xpoints[3] = polygon.xpoints[5] + k;
              polygon.xpoints[2] = polygon.xpoints[5] + k;
              polygon.xpoints[1] = polygon.xpoints[2] - (k >> 1);
              polygon.ypoints[5] = polygon.ypoints[5] - m + 1;
              polygon.ypoints[4] = polygon.ypoints[5] - m + 1;
              polygon.ypoints[3] = polygon.ypoints[4] - m;
              polygon.ypoints[0] = i4 + (k >> 1);
              polygon.ypoints[2] = i4 + (k >> 1);
              polygon.ypoints[6] = i4 + (k >> 1);
              polygon.ypoints[1] = i4;
              paramGraphics.drawPolygon(polygon);
              paramGraphics.fillPolygon(polygon);
            }
            break;
          case 'w':
            poly0(paramGraphics, polygon, n, i6, k, i10 + m, -1);
            poly0(paramGraphics, polygon, i3, i6, -k, i10 + m, -1);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i2, i7, k, i10 - k - m, 2);
            break;
          case '!':
            poly0(paramGraphics, polygon, i2, i4, k, i10 + b, 0);
          case '.':
            paramGraphics.fillRect(i2, i8 - k, k + 2, k + 2);
            break;
          case ';':
            paramGraphics.fillRect(i2, i6 + i4 >> 1, k + 2, k + 2);
          case ',':
            polygon.xpoints[0] = i2 - k - 1;
            polygon.xpoints[3] = i2 - k - 1;
            polygon.xpoints[1] = i2;
            polygon.xpoints[2] = i2 + k;
            polygon.ypoints[0] = i8 + k + 3;
            polygon.ypoints[3] = i8 + k + 3;
            polygon.ypoints[2] = i8 + 2;
            polygon.ypoints[1] = i8 + 2;
            paramGraphics.drawPolygon(polygon.xpoints, polygon.ypoints, 4);
            paramGraphics.fillPolygon(polygon.xpoints, polygon.ypoints, 4);
            paramGraphics.fillRect(i2, i8 - k, k + 2, k + 2);
            break;
          case ':':
            paramGraphics.fillRect(i2, i6 + i4 >> 1, k + 2, k + 2);
            paramGraphics.fillRect(i2, i8 + i6 >> 1, k + 2, k + 2);
            break;
          case 'x':
            poly45(paramGraphics, polygon, n + m, i8, b + k + 1 << 1, k);
            poly45(paramGraphics, polygon, polygon.xpoints[4], i8, -(b + k + 1 << 1), k);
            break;
          case 'z':
            poly0(paramGraphics, polygon, n, i6, i11 + m + (k >> 1), k, 1);
            poly0(paramGraphics, polygon, n, i8, i11 + m + (k >> 1), -k, -1);
            i21 = k * 3 >> 1;
            polygon.ypoints[2] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[1] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[0] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[6] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[5] = i6 + m + 1 + k;
            polygon.ypoints[4] = i6 + m + 1 + k;
            polygon.ypoints[3] = i6 + m + 1 + k;
            i22 = polygon.ypoints[0] - polygon.ypoints[5];
            polygon.xpoints[0] = polygon.xpoints[2] + m + m;
            if (k < 3)
              polygon.xpoints[0] = polygon.xpoints[0] + 1;
            polygon.xpoints[1] = polygon.xpoints[0];
            polygon.xpoints[6] = polygon.xpoints[0];
            polygon.xpoints[2] = polygon.xpoints[0] + i21;
            polygon.xpoints[5] = polygon.xpoints[0] + i22;
            polygon.xpoints[3] = polygon.xpoints[5] + i21;
            polygon.xpoints[4] = polygon.xpoints[5] + i21;
            paramGraphics.drawPolygon(polygon);
            paramGraphics.fillPolygon(polygon);
            break;
          case 'Z':
            poly0(paramGraphics, polygon, n, i4, i11 + (m << 1) + (k >> 1), k, 1);
            poly0(paramGraphics, polygon, n, i8, i11 + (m << 1) + (k >> 1), -k, -1);
            i24 = (k << 2) / 3;
            polygon.ypoints[2] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[1] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[0] = polygon.ypoints[2] - m + 1;
            polygon.ypoints[6] = polygon.ypoints[2] - m + 1;
            i23 = polygon.ypoints[0] - i4 + m + 1 + k & 0xFFFE;
            polygon.ypoints[5] = polygon.ypoints[0] - i23;
            polygon.ypoints[4] = polygon.ypoints[0] - i23;
            polygon.ypoints[3] = polygon.ypoints[0] - i23;
            polygon.xpoints[0] = (k > 8) ? polygon.xpoints[2] : (polygon.xpoints[2] + 1);
            if (k < 5)
              polygon.xpoints[0] = polygon.xpoints[0] + m;
            polygon.xpoints[1] = polygon.xpoints[0];
            polygon.xpoints[6] = polygon.xpoints[0];
            polygon.xpoints[2] = polygon.xpoints[0] + i24;
            polygon.xpoints[5] = polygon.xpoints[0] + (i23 >> 1);
            polygon.xpoints[3] = polygon.xpoints[5] + i24;
            polygon.xpoints[4] = polygon.xpoints[5] + i24;
            paramGraphics.drawPolygon(polygon);
            paramGraphics.fillPolygon(polygon);
            break;
          case '@':
            poly0(paramGraphics, polygon, i2 + m, i6, b + k + k - m, k, 0);
            poly0(paramGraphics, polygon, n, i5, k, i10, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i2, i5 + k + m, k, i10 - k - m, -2);
            break;
          case ']':
            poly0(paramGraphics, polygon, n - (m >> 1), i4, b + k + k, k, 0);
            poly0(paramGraphics, polygon, n - (m >> 1), i8, b + k + k, -k, 0);
            poly0(paramGraphics, polygon, i2, i5, k, i10, 0);
            poly0(paramGraphics, polygon, i2, i7, k, i10, 0);
            break;
          case '[':
            poly0(paramGraphics, polygon, i2 + m, i4, b + k + k, k, 0);
            poly0(paramGraphics, polygon, i2 + m, i8, b + k + k, -k, 0);
            poly0(paramGraphics, polygon, i2, i5, k, i10, 0);
            poly0(paramGraphics, polygon, i2, i7, k, i10, 0);
            break;
          case '\\':
            poly65(paramGraphics, polygon, i3 - m, i8, -j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4] - 1, i12, -j, k);
            break;
          case '*':
            poly0(paramGraphics, polygon, n, i6, i11, k, 0);
          case 'X':
            poly65(paramGraphics, polygon, n + m, i8, j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4], i12, -j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[1] + m + 1, i12, j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4], i8, -j, k);
            break;
          case '&':
            poly65(paramGraphics, polygon, i3 - m, i8, -j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4] - 1, i12, -j + k / 2 + 1, k);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11 - k / 2 - m, -k, 0);
            poly45(paramGraphics, polygon, i1 + (k >> 1), i6 + (k >> 1), i10 - (k + m >> 1), k);
            poly0(paramGraphics, polygon, i1 + k + 1, i4, i11 + k >> 1, k, 0);
            break;
          case 'Y':
          case '¥':
            poly0(paramGraphics, polygon, i2, i7, k, i10 + m, 1);
            poly65(paramGraphics, polygon, polygon.xpoints[5] + m, i12, j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[1] - m - m, i12, -j, k);
            break;
          case '^':
            poly65(paramGraphics, polygon, i1 + (k >> 1), i6, j, k);
            poly65(paramGraphics, polygon, i3 - m, i6, -j, k);
            break;
          case ')':
          case '}':
            poly65(paramGraphics, polygon, i2 - k, i8, j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4], i12, -j, k);
            break;
          case '(':
          case '{':
            poly65(paramGraphics, polygon, i2 + k + k, i8, -j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4], i12, j, k);
            break;
          case '>':
            poly45(paramGraphics, polygon, i1 + k, i8 - k, i10 - k, k);
            poly45(paramGraphics, polygon, polygon.xpoints[4], i6, -i10 + k, k);
            break;
          case '<':
            poly45(paramGraphics, polygon, i3 - k - m, i8 - k, -i10 + k, k);
            poly45(paramGraphics, polygon, polygon.xpoints[4], i6, i10 - k, k);
            break;
          case 'K':
            poly0(paramGraphics, polygon, n, i4, k, i10 + m, -1);
            poly0(paramGraphics, polygon, n, i7, k, i10 + m, 1);
            poly0(paramGraphics, polygon, i1, i6, b + (m - 1 << 1) + k + k, k, 0);
            poly65(paramGraphics, polygon, i3, i8, -j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4], i12, j, k);
            break;
          case '%':
            poly0(paramGraphics, polygon, i1, i5, k, i10 + k >> 1, 0);
            poly0(paramGraphics, polygon, i3 - m - k, i8 - (i10 + k >> 1), -k, i10 + k >> 1, 0);
          case '/':
            poly65(paramGraphics, polygon, n + m, i8, j, k);
            poly65(paramGraphics, polygon, polygon.xpoints[4] + 1, i12, j, k);
            break;
          case '+':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i2, i6 - (i11 - k + 1 >> 1), k, i11, 0);
            break;
          case '#':
            poly0(paramGraphics, polygon, n + i2 >> 1, i4, k, i8 - i5, 1);
            poly0(paramGraphics, polygon, (i2 + i3 - k >> 1) - 1, i4, k, i8 - i5, 1);
          case '=':
            poly0(paramGraphics, polygon, n, i6 + i4 >> 1, i11 + m, k, 0);
            poly0(paramGraphics, polygon, n, i8 + i6 >> 1, i11 + m, k, 0);
            break;
          case '?':
            poly0(paramGraphics, polygon, i2 + m, i6, (i11 + k >> 1) - m, k, 0);
            poly0(paramGraphics, polygon, n, i5, k, i10 + k >> 1, 0);
            poly0(paramGraphics, polygon, i3, i5, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            poly0(paramGraphics, polygon, i2, i7, k, i10 + k >> 1, 0);
            paramGraphics.fillRect(i2, i8 - k - 1, k + 2, k + 2);
            break;
          case 'ÿ':
            paramGraphics.fillRect(i2 - 1, i4, k + 2, k + 2);
          case 'y':
            poly0(paramGraphics, polygon, i3, i9, -k, i10 - m, 0);
            poly0(paramGraphics, polygon, i1, i8 + i10 - k + m, i11, -k, 0);
            poly0(paramGraphics, polygon, n, i6, k, i10 + m, -1);
            poly0(paramGraphics, polygon, i3, i6, -k, i10 + m, -1);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case 'ù':
          case 'ú':
          case 'û':
          case 'ü':
            paramGraphics.fillRect(i2 - 1, i4, k + 2, k + 2);
          case 'u':
            poly0(paramGraphics, polygon, n, i6, k, i10 + m, -1);
            poly0(paramGraphics, polygon, i3, i6, -k, i10 + m, -1);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case 'j':
            poly0(paramGraphics, polygon, i2, i9 - m, k, i10, -2);
            poly0(paramGraphics, polygon, i1 - (m >> 1), i8 + i10 - k + m, b + k + k - m, -k, 0);
          case 'i':
          case 'ì':
          case 'í':
          case 'î':
          case 'ï':
            paramGraphics.fillRect(i2 - 1, i4, k + 2, k + 2);
            poly0(paramGraphics, polygon, i2, i6, k, i10 + k + m, -1);
            break;
          case 'c':
          case '¢':
          case 'ç':
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            break;
          case 'æ':
          case 'è':
          case 'é':
          case 'ê':
          case 'ë':
            paramGraphics.fillRect(i2 - 1, i4, k + 2, k + 2);
          case 'e':
            poly0(paramGraphics, polygon, i1 + k + 1, i6 + (b + b + k + 1 >> 1), i11 - k + 1, k, -2);
            poly0(paramGraphics, polygon, i3, i6 + (1 + m) / 2, -k, (k * 5 >> 2) + b - 1, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11 + m + (k >> 1), -k, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            break;
          case 'à':
          case 'á':
          case 'â':
          case 'ä':
          case 'å':
            paramGraphics.fillRect(i2 - 1, i4, k + 2, k + 2);
          case 'a':
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11 + k + m, -k, 1);
            poly0(paramGraphics, polygon, i3, i7, -k, i10 - k - m, 2);
            break;
          case 'ò':
          case 'ó':
          case 'ô':
          case 'ö':
            paramGraphics.fillRect(i2 - 1, i4, k + 2, k + 2);
            poly0(paramGraphics, polygon, n, i7, k, i10, 0);
            poly0(paramGraphics, polygon, i1, i6, i11, k, 0);
            poly0(paramGraphics, polygon, i3, i7, -k, i10, 0);
            poly0(paramGraphics, polygon, i1, i8, i11, -k, 0);
            break;
          case '"':
            poly0(paramGraphics, polygon, i2 + m + m, i5, k, k + k + m, -2);
          case '\'':
          case '`':
            poly0(paramGraphics, polygon, i2 - k - m, i5, k, k + k + m, -2);
            break;
          default:
            poly0(paramGraphics, polygon, i1, i4, i11, k, 0);
            break;
          case ' ':
            break;
        }
      }
      paramInt1++;
      n += i13;
      i1 += i13;
      i2 += i13;
      i3 += i13;
    }
  }

  static void poly0(Graphics paramGraphics, Polygon paramPolygon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramInt3 > paramInt4) {
      int i = (paramInt4 > 0) ? (paramInt4 / 2) : -(-paramInt4 / 2);
      paramPolygon.ypoints[0] = paramInt2;
      paramPolygon.ypoints[6] = paramInt2;
      paramPolygon.ypoints[5] = paramInt2;
      paramPolygon.ypoints[1] = paramInt2 + i;
      paramPolygon.ypoints[4] = paramInt2 + i;
      paramPolygon.ypoints[3] = paramInt2 + paramInt4;
      paramPolygon.ypoints[2] = paramInt2 + paramInt4;
      if (paramInt4 < 0) {
        paramInt4 = -paramInt4;
        i = -i;
      }
      paramPolygon.xpoints[2] = paramInt1;
      paramPolygon.xpoints[1] = paramInt1;
      paramPolygon.xpoints[6] = paramInt1;
      paramPolygon.xpoints[0] = paramInt1;
      paramPolygon.xpoints[5] = paramInt1 + paramInt3;
      paramPolygon.xpoints[4] = paramInt1 + paramInt3;
      paramPolygon.xpoints[3] = paramInt1 + paramInt3;
      if (paramInt5 > -2) {
        paramPolygon.xpoints[1] = paramPolygon.xpoints[1] + i;
        paramPolygon.xpoints[2] = paramPolygon.xpoints[2] + paramInt4;
        if (paramInt5 != -1) {
          paramPolygon.xpoints[0] = paramPolygon.xpoints[0] + i + i;
          paramPolygon.xpoints[6] = paramPolygon.xpoints[0] + i + i;
        }
      }
      if (paramInt5 != 2) {
        paramPolygon.xpoints[4] = paramPolygon.xpoints[4] - i;
        paramPolygon.xpoints[3] = paramPolygon.xpoints[3] - paramInt4;
        if (paramInt5 != 1)
          paramPolygon.xpoints[5] = paramPolygon.xpoints[5] - i + i;
      }
    } else {
      int i = (paramInt3 > 0) ? (paramInt3 / 2) : -(-paramInt3 / 2);
      paramPolygon.xpoints[0] = paramInt1;
      paramPolygon.xpoints[6] = paramInt1;
      paramPolygon.xpoints[1] = paramInt1;
      paramPolygon.xpoints[5] = paramInt1 + i;
      paramPolygon.xpoints[2] = paramInt1 + i;
      paramPolygon.xpoints[4] = paramInt1 + paramInt3;
      paramPolygon.xpoints[3] = paramInt1 + paramInt3;
      if (paramInt3 < 0) {
        paramInt3 = -paramInt3;
        i = -i;
      }
      paramPolygon.ypoints[4] = paramInt2;
      paramPolygon.ypoints[5] = paramInt2;
      paramPolygon.ypoints[0] = paramInt2;
      paramPolygon.ypoints[6] = paramInt2;
      paramPolygon.ypoints[3] = paramInt2 + paramInt4;
      paramPolygon.ypoints[2] = paramInt2 + paramInt4;
      paramPolygon.ypoints[1] = paramInt2 + paramInt4;
      switch (paramInt5) {
        case -1:
          paramPolygon.ypoints[0] = paramPolygon.ypoints[0] + i;
          paramPolygon.ypoints[6] = paramPolygon.ypoints[0] + i;
          paramPolygon.ypoints[4] = paramPolygon.ypoints[4] + paramInt3 - i;
          break;
        default:
          paramPolygon.ypoints[5] = paramPolygon.ypoints[5] + i;
          paramPolygon.ypoints[4] = paramPolygon.ypoints[4] + paramInt3;
          paramPolygon.ypoints[0] = paramPolygon.ypoints[0] + i + i;
          paramPolygon.ypoints[6] = paramPolygon.ypoints[0] + i + i;
          break;
        case -2:
          break;
      }
      switch (paramInt5) {
        case 1:
          paramPolygon.ypoints[1] = paramPolygon.ypoints[1] - i;
          paramPolygon.ypoints[3] = paramPolygon.ypoints[3] - paramInt3 - i;
        case 2:
          if (paramInt3 == 1) {
            paramPolygon.ypoints[1] = paramPolygon.ypoints[1] + 1;
            paramPolygon.ypoints[2] = paramPolygon.ypoints[2] + 1;
            paramPolygon.ypoints[3] = paramPolygon.ypoints[3] + 1;
          }
          break;
        default:
          paramPolygon.ypoints[2] = paramPolygon.ypoints[2] - i;
          paramPolygon.ypoints[3] = paramPolygon.ypoints[3] - paramInt3;
          paramPolygon.ypoints[1] = paramPolygon.ypoints[1] - i + i;
          break;
      }
    }
    paramGraphics.fillPolygon(paramPolygon);
    paramGraphics.drawPolygon(paramPolygon);
  }

  static void poly45(Graphics paramGraphics, Polygon paramPolygon, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = (paramInt4 * 2 + 1) / 3;
    int j = (paramInt4 * 2 + 2) / 3;
    paramPolygon.ypoints[2] = paramInt2;
    paramPolygon.ypoints[1] = paramInt2;
    paramPolygon.ypoints[0] = paramInt2 - i;
    paramPolygon.ypoints[6] = paramInt2 - i;
    paramPolygon.ypoints[4] = (paramInt3 > 0) ? (paramInt2 - paramInt3) : (paramInt2 + paramInt3);
    paramPolygon.ypoints[5] = (paramInt3 > 0) ? (paramInt2 - paramInt3) : (paramInt2 + paramInt3);
    paramPolygon.ypoints[3] = paramPolygon.ypoints[4] + i;
    if (paramInt3 < 0) {
      i = -i;
      j = -j;
    }
    paramInt3 -= i;
    paramPolygon.xpoints[1] = paramInt1;
    paramPolygon.xpoints[0] = paramInt1;
    paramPolygon.xpoints[6] = paramInt1;
    paramPolygon.xpoints[2] = paramInt1 + j;
    paramPolygon.xpoints[5] = paramPolygon.xpoints[0] + paramInt3;
    paramPolygon.xpoints[3] = paramPolygon.xpoints[5] + j;
    paramPolygon.xpoints[4] = paramPolygon.xpoints[5] + j;
    paramGraphics.fillPolygon(paramPolygon);
    paramGraphics.drawPolygon(paramPolygon);
  }

  static void poly65(Graphics paramGraphics, Polygon paramPolygon, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt4;
    paramInt4 = (int)((paramInt4 + 0.5D) * 3.0D) >> 2;
    if (paramInt3 < 0)
      paramInt4 = -paramInt4;
    paramPolygon.xpoints[1] = paramInt1;
    paramPolygon.xpoints[0] = paramInt1;
    paramPolygon.xpoints[6] = paramInt1;
    paramPolygon.xpoints[2] = paramInt1 + paramInt4;
    paramPolygon.xpoints[3] = paramInt1 + paramInt3;
    paramPolygon.xpoints[4] = paramInt1 + paramInt3;
    paramPolygon.xpoints[5] = paramPolygon.xpoints[4] - paramInt4;
    if (paramInt3 < 0) {
      paramInt4 = -paramInt4;
      paramInt3 = -paramInt3;
    }
    paramInt3 += paramInt3 - paramInt4;
    if (i == 1) {
      paramInt4 = 0;
    } else if (i == 2) {
      paramInt4 = 2;
    }
    paramPolygon.ypoints[2] = paramInt2;
    paramPolygon.ypoints[1] = paramInt2;
    paramPolygon.ypoints[0] = paramInt2 - paramInt4;
    paramPolygon.ypoints[6] = paramInt2 - paramInt4;
    paramPolygon.ypoints[4] = paramInt2 - paramInt3;
    paramPolygon.ypoints[5] = paramInt2 - paramInt3;
    paramPolygon.ypoints[3] = paramPolygon.ypoints[4] + paramInt4;
    paramGraphics.fillPolygon(paramPolygon);
    paramGraphics.drawPolygon(paramPolygon);
  }
}
