package pv.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PVRoundButton extends PVButton {
  public PVRoundButton(String paramString, int paramInt) {
    super(paramString, paramInt, 1);
    this.m_iBodr = 0;
    this.m_cBo = Color.black;
    setFocusRectAroundText(true);
  }

  public PVRoundButton(String paramString) {
    this(paramString, 12);
  }

  public PVRoundButton() {
    this("Button", 12);
  }

  public int getColorsDepth() {
    return this.m_4 >> 16 & 0x7;
  }

  public void setColorsDepth(int paramInt) {
    this.m_4 = this.m_4 & 0xFFF8FFFF | (paramInt & 0x7) % 6 << 16;
    paint(null);
  }

  public int getCenterArea() {
    return this.m_4 >> 22 & 0x7;
  }

  public void setCenterArea(int paramInt) {
    this.m_4 = this.m_4 & 0xFE3FFFFF | (paramInt & 0x7) << 22;
    paint(null);
  }

  public int getShape() {
    return this.m_4 >> 19 & 0x7;
  }

  public void setShape(int paramInt) {
    this.m_4 = this.m_4 & 0xFFC7FFFF | (paramInt & 0x7) << 19;
    paint(null);
  }

  public void paint0(Graphics paramGraphics) {
    Rectangle rectangle = new Rectangle(getSize());
    if (paramGraphics != null && rectangle.width > 1 && rectangle.height > 1) {
      int i = getShadow();
      if (getShadowWidth() == 0)
        i = 0;
      if (i > 0) {
        if ((this.m_1 & 0x202) == 514)
          i += 2;
        if (i > 4)
          i -= 4;
      }
      drawShadow(paramGraphics, rectangle, i);
      int j = this.m_4 & 0xFFF;
      if ((this.m_1 & 0x1) != 0 || getColorsDepth() == 0) {
        paramGraphics.setColor(getBackColor());
        draw(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, j, 1);
      } else {
        drawBack(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this.m_1 & 0x3);
      }
      if (getBorderStyle() != 0 && (this.m_1 & 0x1) == 0) {
        paramGraphics.setColor(getBorderColor());
        draw(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, j, 2);
      }
      foc0(paramGraphics, drawTextAndImage(paramGraphics, rectangle.x += 2, rectangle.y += getShape() / 7, rectangle.width -= 3, rectangle.height), rectangle);
    }
  }

  void drawShadow(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
    if (getParent() != null) {
      paramGraphics.setColor(getParent().getBackground());
      paramGraphics.fillRect(0, 0, paramRectangle.width--, paramRectangle.height--);
    }
    int i = 0;
    int j = 0;
    int k = getShadowWidth();
    if (k > paramRectangle.width / 2)
      k = paramRectangle.width / 2;
    if (k > paramRectangle.height / 2)
      k = paramRectangle.height / 2;
    if (paramInt == 0)
      k = 0;
    paramRectangle.width -= k;
    paramRectangle.height -= k;
    int m = (paramRectangle.width > paramRectangle.height) ? paramRectangle.height : paramRectangle.width;
    m = (m - 2) * getShape() / 5;
    if (m < 2)
      m = 2;
    this.m_4 = this.m_4 & 0xFFFFF000 | m;
    if (k < 1)
      return;
    switch (paramInt) {
      case 0:
        return;
      case 1:
        i = j = k;
        break;
      case 2:
        j = k;
        paramRectangle.x += k;
        break;
      case 3:
        paramRectangle.x += k;
        paramRectangle.y += k;
        break;
      case 4:
        i = k;
        paramRectangle.y += k;
        break;
    }
    paramGraphics.setColor(getShadowColor());
    draw(paramGraphics, i, j, paramRectangle.width, paramRectangle.height, m, 3);
  }

  void draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if ((paramInt6 & 0x1) == 1)
      if (getShape() == 7) {
        paramGraphics.fillOval(paramInt1, paramInt2, paramInt3, paramInt4);
      } else {
        paramGraphics.fillRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt5);
      }
    if ((paramInt6 & 0x2) == 2) {
      if (getShape() == 7) {
        paramGraphics.drawOval(paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      paramGraphics.drawRoundRect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt5);
    }
  }

  void drawBack(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = (paramInt3 > paramInt4) ? paramInt4 : paramInt3;
    int j = 4 + i / 4;
    double d1 = (i * (8 - getCenterArea())) * 0.115D / j;
    int k = getBackColor().getRGB();
    int[] arrayOfInt1 = new int[3];
    for (i = 0; i < 3; i++) {
      arrayOfInt1[i] = k >> 8 * i & 0xFF;
      if (paramInt5 == 0) {
        arrayOfInt1[i] = arrayOfInt1[i] + (255 - arrayOfInt1[i] >>> 2);
      } else {
        arrayOfInt1[i] = arrayOfInt1[i] - (arrayOfInt1[i] >>> 4) + (arrayOfInt1[i] >>> 6) * 10;
      }
    }
    double d2 = (paramInt5 == 2) ? 0.62D : 0.36D;
    int m = this.m_4 & 0xFFF;
    int[] arrayOfInt2 = new int[3];
    for (i = 0; i <= j; i++) {
      for (byte b = 0; b < 3; b++)
        arrayOfInt2[b] = arrayOfInt1[b] - arrayOfInt1[b] * getColorsDepth() * (j - i) / j / 5;
      paramGraphics.setColor(new Color(arrayOfInt2[2], arrayOfInt2[1], arrayOfInt2[0]));
      if (i == 0)
        draw(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, m, 2);
      int i1 = (int)(i * d1);
      int i2 = (int)(i * d1 * d2);
      int n = m - (i1 + i2) / 2;
      if (n < 1)
        n = 1;
      draw(paramGraphics, paramInt1 + i2, paramInt2 + i2, paramInt3 - i1, paramInt4 - i1, n, 1);
    }
  }

  protected Dimension getTextDimension() {
    Dimension dimension = super.getTextDimension();
    dimension.width += dimension.height >> 1;
    if (getShape() == 7) {
      dimension.width += dimension.width / dimension.height * dimension.height / 3;
      dimension.height += dimension.height >> 2;
    }
    return dimension;
  }
}
