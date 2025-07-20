package pv.awt;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import pv.util.PVBorder;
import pv.util.PVImage;

public class PVScrollBar extends Container implements Runnable, Serializable, Adjustable {
  int m_v;

  int m_max = 100;

  int m_min;

  int m_vis = 10;

  int m_unit = 1;

  int m_blok = 10;

  int m_bdr1 = -1;

  int m_bdr1P;

  int m_bdr0 = 1507345;

  int m_bdr0P = -1;

  int m_bdr = 851994;

  Color m_c0P;

  Color m_c1;

  Color m_c1P;

  Color m_c2 = new Color(-2039584);

  Color m_c2P = Color.black;

  int m_0 = 63744;

  int m_d = 1645215808;

  protected Dimension m_prefer;

  protected Dimension m_minS;

  protected Dimension m_maxS;

  public transient Image[] m_images;

  protected transient Image m_im;

  transient int m_w0;

  transient int m_x;

  transient int m_w;

  transient int m_x1;

  transient int m_dd;

  transient int m_mouse;

  transient AdjustmentListener m_adLs;

  static transient Thread m_run;

  public PVScrollBar() {
    this(0);
  }

  public PVScrollBar(int paramInt) {
    init0();
    setOrientation(paramInt);
    setForeground(Color.black);
    setBackground(Color.lightGray);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    if (this.m_adLs instanceof Serializable) {
      paramObjectOutputStream.writeObject("adLs");
      paramObjectOutputStream.writeObject(this.m_adLs);
    }
    paramObjectOutputStream.writeObject(null);
  }

  private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
    paramObjectInputStream.defaultReadObject();
    Object object;
    while ((object = paramObjectInputStream.readObject()) != null) {
      if ("adLs".equals(object))
        addAdjustmentListener((AdjustmentListener)paramObjectInputStream.readObject());
    }
    init0();
  }

  void init0() {
    this.m_0 &= 0x500FFFFF;
    enableEvents(48L);
  }

  public void removeNotify() {
    stop();
    this.m_0 &= 0x500FFFFF;
    super.removeNotify();
  }

  public void run() {
    synchronized (m_run) {
      while ((this.m_0 & 0x2F000000) > 671088640 && m_run != null) {
        try {
          Thread.sleep(this.m_dd);
        } catch (InterruptedException interruptedException) {}
        if ((this.m_0 & 0x2F000000) > 671088640 && m_run != null) {
          int i = getDelay() >> getSpeedUp();
          if (i < 1)
            i = 1;
          if ((this.m_dd = this.m_dd * 3 / 4) < i)
            this.m_dd = i;
          mouse(null);
          fire();
          continue;
        }
        break;
      }
      return;
    }
  }

  void stop() {
    if (m_run != null) {
      try {
        m_run.interrupt();
      } catch (Exception exception) {}
      try {
        m_run.stop();
      } catch (Exception exception) {}
      m_run = null;
    }
  }

  void start() {
    if (m_run == null)
      try {
        this.m_dd = getDelay();
        m_run = new Thread(this);
        m_run.start();
        return;
      } catch (Exception exception) {
        return;
      }
  }

  public void paint(Graphics paramGraphics) {
    boolean bool = (paramGraphics != null) ? false : true;
    if (bool) {
      if (!isShowing())
        return;
      if (this.m_im == null) {
        if ((this.m_0 & 0x20000000) != 0)
          repaint();
        return;
      }
      if ((paramGraphics = getGraphics()) == null)
        return;
    }
    this.m_0 &= Integer.MAX_VALUE;
    Dimension dimension = fix();
    int i = m0(4);
    int j = 0;
    int k = 0;
    if (this.m_im != null && ((j = this.m_im.getWidth(null)) < dimension.width || (k = this.m_im.getHeight(null)) < dimension.height))
      this.m_im = null;
    if (getDoubleBuffered() && this.m_im == null)
      try {
        this.m_im = createImage((dimension.width > j) ? dimension.width : j, (dimension.height > k) ? dimension.height : k);
      } catch (Exception exception) {}
    Graphics graphics = (this.m_im == null) ? paramGraphics : this.m_im.getGraphics();
    Rectangle rectangle = new Rectangle(dimension);
    if (getMouseMovement() && i > 0) {
      graphics.setColor((getParent() == null) ? getBackground() : getParent().getBackground());
      graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    if ((j = this.m_bdr) == -1)
      j = 851994;
    if ((this.m_0 & 0x80002) != 524288)
      PVBorder.draw(((this.m_0 & 0x8780000) == 524288) ? null : graphics, rectangle, j, getBackground());
    dimension.width -= i * 2;
    dimension.height -= i * 2;
    int m = rectangle.x;
    int n = rectangle.y;
    j = k = this.m_x - this.m_w0;
    if ((this.m_0 & 0x1) == 0) {
      m = i + this.m_w0;
      k = rectangle.height;
    } else {
      n = i + this.m_w0;
      j = rectangle.width;
    }
    graphics.setColor(isPressed(3) ? getPressedBlockColor() : getBlockColor());
    graphics.fillRect(m, n, j, k);
    if (this.m_w > 0) {
      if ((this.m_0 & 0x1) == 0) {
        m += j + this.m_w;
        j = i + this.m_x1 - m;
      } else {
        n += k + this.m_w;
        k = i + this.m_x1 - n;
      }
      graphics.setColor(isPressed(4) ? getPressedBlockColor() : getBlockColor());
      graphics.fillRect(m, n, j, k);
    }
    rectangle.x = rectangle.y = i;
    if ((rectangle.width = rectangle.height = this.m_w0) > 0) {
      if ((this.m_0 & 0x1) == 0) {
        rectangle.height = dimension.height;
      } else {
        rectangle.width = dimension.width;
      }
      draw(graphics, rectangle, 2);
      rectangle.x = rectangle.y = i;
      rectangle.width = rectangle.height = this.m_w0;
      if ((this.m_0 & 0x1) == 0) {
        rectangle.x += this.m_x1;
        rectangle.height = dimension.height;
      } else {
        rectangle.y += this.m_x1;
        rectangle.width = dimension.width;
      }
      draw(graphics, rectangle, 1);
    }
    rectangle.x = rectangle.y = i;
    if ((rectangle.width = rectangle.height = this.m_w) >= 0) {
      if ((this.m_0 & 0x1) == 0) {
        rectangle.x += this.m_x;
        rectangle.height = dimension.height;
      } else {
        rectangle.y += this.m_x;
        rectangle.width = dimension.width;
      }
      draw(graphics, rectangle, 5);
    }
    super.paint(graphics);
    if (graphics != paramGraphics) {
      graphics.dispose();
      paramGraphics.drawImage(this.m_im, 0, 0, null);
    }
    if (bool)
      paramGraphics.dispose();
    this.m_0 |= 0xA0000000;
  }

  public Dimension fix() {
    Dimension dimension = getSize();
    if (m0(24) == 5)
      return dimension;
    int j = 0;
    int k = getButtonSize();
    int m = getMinThumbSize();
    this.m_0 &= 0xFFFFFF8F;
    if (!getInnerBorder()) {
      Rectangle rectangle = new Rectangle(dimension);
      int n;
      if ((n = this.m_bdr) == -1)
        n = 851994;
      PVBorder.draw(null, rectangle, n, null);
      this.m_0 |= (j = rectangle.x & 0x7) << 4;
    }
    int i = (((this.m_0 & 0x1) == 0) ? dimension.width : dimension.height) - j * 2;
    this.m_w0 = k = (i / 2 > k) ? k : (i / 2);
    this.m_x1 = i -= k;
    i -= k;
    k = this.m_max - this.m_min;
    j = getVisibleAmount();
    if ((this.m_w0 < 1 || getReadOnly()) && k <= j) {
      this.m_x = this.m_w0;
      this.m_w = i;
    } else if (!isEnabled() || k <= j) {
      this.m_w = -1;
    } else if (i < 4) {
      this.m_w = 0;
    } else {
      if (m > i * 3 / 4)
        m = i * 3 / 4;
      double d = i / k;
      if ((this.m_w = (int)(d * j + 0.5D)) < m) {
        d = (i - m + this.m_w) / k;
        this.m_w = m;
      }
      k -= j;
      if ((j = this.m_v - this.m_min) < 0)
        j = 0;
      if ((k = i - this.m_w) < 0)
        k = 0;
      if ((j = (int)(d * j + 0.5D)) > k)
        j = k;
      j += this.m_w0;
    }
    k = this.m_0 & Integer.MIN_VALUE;
    if (this.m_w < 1) {
      this.m_x = this.m_x1;
    } else if (k != 0 || this.m_x < this.m_w0 || this.m_x > k) {
      this.m_x = j;
    }
    if (k != 0)
      paint(null);
    return dimension;
  }

  protected void draw(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
    int i = m0(20);
    int j = m0(24);
    boolean bool1 = isPressed(paramInt);
    boolean bool2 = isFlat(paramInt);
    Color color = (paramInt == 5) ? (bool1 ? getPressedThumbColor() : getThumbColor()) : (bool1 ? getPressedButtonColor() : getBackground());
    i = (paramInt == 5 && this.m_bdr1 != -1) ? this.m_bdr1 : this.m_bdr0;
    if (i == -1)
      i = 1507345;
    j = i;
    if (bool1) {
      bool1 = !((i = (paramInt == 5) ? this.m_bdr1P : this.m_bdr0P) != -1);
      if (bool1 || i == 0)
        i = j;
    }
    j = 0;
    while (j++ < 2) {
      if (bool2) {
        paramGraphics.setColor(color);
        paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
      } else {
        PVBorder.draw((j == 2) ? null : paramGraphics, paramRectangle, i, color, bool1);
      }
      bool2 = !bool2;
    }
    bool1 = isPressed(paramInt);
    Image image = imageFor(paramInt);
    if (image != null)
      PVImage.center(paramGraphics, null, image, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, 1000, bool1 ? 25 : 9);
    if (paramInt == 5 && (i = getBumps() - 1) >= 0)
      PVImage.center(paramGraphics, color, null, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, getBumpsMargin(), 10 + i / 2 + ((bool1 && (i & 0x1) != 0) ? 16 : 0));
    if (image != null || (paramInt = (3 - paramInt) * 2) < 2)
      return;
    if ((this.m_0 & 0x1) != 0)
      paramInt++;
    i = getTriangle();
    color = getForeground();
    if (this.m_w < 0) {
      paramInt |= 0x80;
      color = Color.gray;
    } else {
      if (bool2 || i == 3) {
        paramInt |= 0x40;
      } else if (bool1) {
        paramInt |= i << 4;
      }
      if (bool1) {
        paramRectangle.x++;
        paramRectangle.y++;
      }
    }
    PVImage.center(paramGraphics, color, Color.white, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, getTriangleSize(), paramInt);
  }

  public boolean isPressed(int paramInt) {
    return !((this.m_0 & 0x8000000) == 0 || m0(24) != paramInt || (m0(20) != paramInt && (paramInt != 5 || m0(20) <= 2)));
  }

  public boolean isFlat(int paramInt) {
    return !(!getMouseMovement() || isPressed(paramInt) || (m0(20) == paramInt && (this.m_0 & 0x8000000) == 0));
  }

  public Image imageFor(int paramInt) {
    return (this.m_images != null && this.m_images.length >= paramInt / 2) ? this.m_images[paramInt / 2] : null;
  }

  public void setImages(String paramString1, String paramString2, String paramString3) {
    this.m_images = new Image[3];
    for (byte b = 0; b < 3; b++)
      this.m_images[b] = PVImage.file11(this, (b == 0) ? paramString2 : ((b == 1) ? paramString1 : paramString3));
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    super.processEvent(paramAWTEvent);
    if (paramAWTEvent instanceof MouseEvent && this.m_w >= 0 && !getReadOnly()) {
      MouseEvent mouseEvent = (MouseEvent)paramAWTEvent;
      mouse(mouseEvent);
      int i = mouseEvent.getModifiers();
      int j = i & 0x1BCF;
      int k = m0(20);
      switch (paramAWTEvent.getID()) {
        case 501:
          if (j != 0)
            return;
          this.m_0 = this.m_0 & 0xF0FFFFFF | k << 24 | 0x8000000;
          if (k > 0 && k < 5) {
            fire();
            start();
          }
          break;
        case 502:
          this.m_0 &= 0xF0FFFFFF;
          stop();
          break;
        case 503:
          if (i == 0) {
            this.m_0 &= 0xF0FFFFFF;
            if (!getMouseMovement())
              return;
            break;
          }
        case 506:
          if (j != 0 || m0(24) != 5 || k <= 0)
            return;
          i = this.m_max;
          k = (((this.m_0 & 0x1) == 0) ? mouseEvent.getX() : mouseEvent.getY()) - m0(4) - this.m_mouse;
          if (k != 0) {
            if ((j = this.m_x - this.m_w0) + k <= 0) {
              k = -j;
              j = this.m_min;
            } else if (k >= (j = this.m_x1 - this.m_x - this.m_w)) {
              k = j;
              j = i - this.m_vis;
            } else {
              double d = (j = this.m_x1 - this.m_w0) / (i -= this.m_min);
              int m = (int)(d * this.m_vis + 0.5D) - this.m_w;
              if (m < 0)
                d = (j + m) / i;
              j = this.m_min + (int)((this.m_x + k - this.m_w0) / d + 0.5D);
            }
            if (k == 0)
              return;
            this.m_v = j;
            this.m_mouse += k;
            this.m_x += k;
            fire();
          }
          break;
        case 505:
          this.m_0 &= 0xFF8FFFFF;
          if (!getMouseMovement())
            return;
          break;
      }
      paint(null);
    }
  }

  void mouse(MouseEvent paramMouseEvent) {
    Dimension dimension = getSize();
    int i = m0(4);
    int j = this.m_mouse;
    dimension.width -= i * 2;
    dimension.height -= i * 2;
    int k = j;
    if (paramMouseEvent != null) {
      j = paramMouseEvent.getX() - i;
      k = paramMouseEvent.getY() - i;
      i = (m0(24) == 5 && paramMouseEvent.getID() != 502) ? -30 : 0;
      if (j < i || k < i || j + i > dimension.width || k + i > dimension.height)
        j = k = -1;
      if ((this.m_0 & 0x1) != 0) {
        j = k;
        dimension.width = dimension.height;
      }
      if (j < 0 || j > dimension.width)
        j = -1;
      if (i == 0)
        this.m_mouse = j;
    }
    if (j < 0) {
      j = 0;
    } else if (j < this.m_w0) {
      j = 2;
    } else if (j > this.m_x1) {
      j = 1;
    } else if (this.m_w < 1) {
      j = 0;
    } else if (j < this.m_x) {
      j = 3;
    } else if (j <= this.m_x + this.m_w) {
      j = 5;
    } else {
      j = 4;
    }
    k = (paramMouseEvent == null && m0(20) == m0(24)) ? 8388608 : 0;
    this.m_0 = this.m_0 & 0xFF0FFFFF | j << 20 | k;
  }

  int m0(int paramInt) {
    return this.m_0 >> paramInt & 0x7;
  }

  void fire() {
    int i = this.m_v;
    int j = m0(24);
    if (j < 5) {
      if (j != m0(20) || (this.m_0 & 0x8000000) == 0) {
        if ((this.m_0 & 0x800000) != 0)
          paint(null);
        this.m_dd = getDelay();
        return;
      }
      switch (j) {
        case 1:
          i += this.m_unit;
          break;
        case 2:
          i -= this.m_unit;
          break;
        case 3:
          i -= this.m_blok;
          break;
        case 4:
          i += this.m_blok;
          break;
      }
      if (i < this.m_min)
        i = this.m_min;
      if (i + this.m_vis > this.m_max)
        i = this.m_max - this.m_vis;
      if (i == this.m_v)
        return;
      this.m_v = i;
      paint(null);
    }
    if (this.m_adLs != null)
      this.m_adLs.adjustmentValueChanged(new AdjustmentEvent(this, 601, j, this.m_v));
  }

  public int getOrientation() {
    return this.m_0 & 0x1;
  }

  public void setOrientation(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFFFFE | paramInt & 0x1;
    fix();
  }

  public int getValue() {
    return this.m_v;
  }

  public void setValue(int paramInt) {
    if (this.m_v != paramInt) {
      this.m_v = paramInt;
      fix();
    }
  }

  public int getMinimum() {
    return this.m_min;
  }

  public void setMinimum(int paramInt) {
    if (this.m_min != paramInt) {
      this.m_min = paramInt;
      fix();
    }
  }

  public int getMaximum() {
    return this.m_max;
  }

  public void setMaximum(int paramInt) {
    if (this.m_max != paramInt) {
      this.m_max = paramInt;
      fix();
    }
  }

  public int getVisibleAmount() {
    return this.m_vis;
  }

  public void setVisibleAmount(int paramInt) {
    if (this.m_vis != paramInt) {
      this.m_vis = paramInt;
      fix();
    }
  }

  public int getUnitIncrement() {
    return this.m_unit;
  }

  public void setUnitIncrement(int paramInt) {
    this.m_unit = paramInt;
  }

  public int getBlockIncrement() {
    return this.m_blok;
  }

  public void setBlockIncrement(int paramInt) {
    this.m_blok = paramInt;
  }

  public void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.m_v = paramInt1;
    this.m_vis = paramInt2;
    this.m_min = paramInt3;
    this.m_max = paramInt4;
    fix();
  }

  public synchronized void addAdjustmentListener(AdjustmentListener paramAdjustmentListener) {
    this.m_adLs = AWTEventMulticaster.add(this.m_adLs, paramAdjustmentListener);
  }

  public synchronized void removeAdjustmentListener(AdjustmentListener paramAdjustmentListener) {
    this.m_adLs = AWTEventMulticaster.remove(this.m_adLs, paramAdjustmentListener);
  }

  public boolean getDoubleBuffered() {
    return !((this.m_0 & 0x40000) != 0);
  }

  public void setDoubleBuffered(boolean paramBoolean) {
    this.m_0 |= 0x40000;
    if (paramBoolean)
      this.m_0 &= 0xFFFBFFFF;
  }

  public int getDelay() {
    return this.m_d >> 12 & 0x3FF;
  }

  public void setDelay(int paramInt) {
    if ((paramInt &= 0x3FF) < 10)
      paramInt = 10;
    this.m_d = this.m_d & 0xFFC00FFF | paramInt << 12;
  }

  public int getSpeedUp() {
    return this.m_d >>> 29;
  }

  public void setSpeedUp(int paramInt) {
    this.m_d = this.m_d & 0x1FFFFFFF | (paramInt & 0x7) << 29;
  }

  public int getBumps() {
    return this.m_d >> 8 & 0xF;
  }

  public void setBumps(int paramInt) {
    this.m_d = this.m_d & 0xFFFFF0FF | (paramInt & 0xF) % 9 << 8;
  }

  public int getBumpsMargin() {
    return this.m_d >> 6 & 0x3;
  }

  public void setBumpsMargin(int paramInt) {
    this.m_d = this.m_d & 0xFFFFFF3F | (paramInt & 0x3) << 6;
  }

  public void setTriangle(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFFFF3 | (paramInt & 0x3) << 2;
  }

  public int getTriangle() {
    return this.m_0 >> 2 & 0x3;
  }

  public int getTriangleSize() {
    return this.m_0 >> 8 & 0xF;
  }

  public void setTriangleSize(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFF0FF | (paramInt & 0xF) << 8;
  }

  public int getButtonSize() {
    return this.m_0 >> 12 & 0x3F;
  }

  public void setButtonSize(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFC0FFF | (paramInt & 0x3F) << 12;
    fix();
  }

  public boolean getMouseMovement() {
    return !((this.m_0 & 0x80000) == 0);
  }

  public void setMouseMovement(boolean paramBoolean) {
    this.m_0 &= 0xFFF7FFFF;
    if (paramBoolean)
      this.m_0 |= 0x80000;
  }

  public boolean getReadOnly() {
    return !((this.m_0 & 0x80) == 0);
  }

  public void setReadOnly(boolean paramBoolean) {
    this.m_0 &= 0xFFFFFF7F;
    if (paramBoolean)
      this.m_0 |= 0x80;
  }

  public boolean getInnerBorder() {
    return !((this.m_0 & 0x2) != 0);
  }

  public void setInnerBorder(boolean paramBoolean) {
    this.m_0 |= 0x2;
    if (paramBoolean)
      this.m_0 &= 0xFFFFFFFD;
  }

  public int getBorderStyle() {
    return this.m_bdr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_bdr = paramInt;
  }

  public int getButtonBorder() {
    return this.m_bdr0;
  }

  public void setButtonBorder(int paramInt) {
    this.m_bdr0 = paramInt;
  }

  public int getPressedButtonBorder() {
    return this.m_bdr0P;
  }

  public void setPressedButtonBorder(int paramInt) {
    this.m_bdr0P = paramInt;
  }

  public void setPressedButtonColor(Color paramColor) {
    if (paramColor != getPressedButtonColor())
      this.m_c0P = paramColor;
  }

  public Color getPressedButtonColor() {
    return (this.m_c0P == null) ? getBackground() : this.m_c0P;
  }

  public void setBlockColor(Color paramColor) {
    if (paramColor != getBlockColor())
      this.m_c2 = paramColor;
  }

  public Color getBlockColor() {
    return (this.m_c2 == null) ? getBackground() : this.m_c2;
  }

  public void setPressedBlockColor(Color paramColor) {
    if (paramColor != getPressedBlockColor())
      this.m_c2P = paramColor;
  }

  public Color getPressedBlockColor() {
    return (this.m_c2P == null) ? getBlockColor() : this.m_c2P;
  }

  public int getMinThumbSize() {
    return this.m_d >> 22 & 0x3F;
  }

  public void setMinThumbSize(int paramInt) {
    this.m_d = this.m_d & 0xF03FFFFF | (paramInt & 0x3F) << 22;
    fix();
  }

  public int getThumbBorder() {
    return this.m_bdr1;
  }

  public void setThumbBorder(int paramInt) {
    this.m_bdr1 = paramInt;
  }

  public int getPressedThumbBorder() {
    return this.m_bdr1P;
  }

  public void setPressedThumbBorder(int paramInt) {
    this.m_bdr1P = paramInt;
  }

  public void setThumbColor(Color paramColor) {
    if (paramColor != getThumbColor())
      this.m_c1 = paramColor;
  }

  public Color getThumbColor() {
    return (this.m_c1 == null) ? getBackground() : this.m_c1;
  }

  public void setPressedThumbColor(Color paramColor) {
    if (paramColor != getPressedThumbColor())
      this.m_c1P = paramColor;
  }

  public Color getPressedThumbColor() {
    return (this.m_c1P == null) ? getThumbColor() : this.m_c1P;
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_prefer = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension = new Dimension((this.m_prefer == null) ? getSize() : this.m_prefer);
    if (dimension.width < 2)
      dimension.width = 16 + 84 * (this.m_0 + 1 & 0x1);
    if (dimension.height < 2)
      dimension.height = 16 + 84 * (this.m_0 & 0x1);
    return dimension;
  }

  public void setMinimumSize(Dimension paramDimension) {
    this.m_minS = paramDimension;
  }

  public Dimension getMinimumSize() {
    return (this.m_minS == null) ? super.getMinimumSize() : this.m_minS;
  }

  public void setMaximumSize(Dimension paramDimension) {
    this.m_maxS = paramDimension;
  }

  public Dimension getMaximumSize() {
    return (this.m_maxS == null) ? super.getMaximumSize() : this.m_maxS;
  }

  public boolean isFocusTraversable() {
    return false;
  }
}
