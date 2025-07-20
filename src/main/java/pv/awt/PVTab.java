package pv.awt;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import pv.util.PVBorder;
import pv.util.Page0;
import pv.util.TabBorder;

public class PVTab extends Container implements ActionListener, Serializable {
  public Vector m_pages;

  protected PVSpin m_spin;

  Color m_c3D = Color.white;

  int m_0 = 8983;

  int m_1 = 2055;

  int m_iBodr = 11;

  transient int m_iSel = -1;

  transient int m_iW;

  transient int m_iH;

  transient int m_iBoW;

  transient int m_iLeft;

  transient ActionListener m_aLs;

  transient Image m_im;

  protected Dimension m_prefer;

  public PVTab() {
    this(3);
  }

  public PVTab(int paramInt) {
    setForeground(Color.black);
    setFont(new Font("SansSerif", 1, 12));
    init0();
    while (paramInt > 0)
      insertPageAt(null, "Page " + paramInt--, 0);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    if (this.m_aLs instanceof Serializable) {
      paramObjectOutputStream.writeObject("aLs");
      paramObjectOutputStream.writeObject(this.m_aLs);
    }
    paramObjectOutputStream.writeObject(null);
  }

  private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
    paramObjectInputStream.defaultReadObject();
    if ((this.m_1 & 0x800) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0();
  }

  void init0() {
    enableEvents(16L);
    setLayout(null);
    if (this.m_pages == null)
      this.m_pages = new Vector();
    if (this.m_spin == null) {
      this.m_spin = new PVSpin();
      this.m_spin.setButtonWidth(60);
      this.m_spin.setSpeedUp(false);
      this.m_spin.setDelay(300);
      this.m_spin.setVisible(false);
      this.m_spin.addActionListener(this);
    }
    this.m_0 &= 0xFFFFFFF7;
    setBorderStyle(this.m_iBodr);
  }

  public void removeNotify() {
    super.removeNotify();
    this.m_0 &= 0xFFFFFFF7;
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    doScroll(!(paramActionEvent.getModifiers() != 64));
  }

  public void paint(Graphics paramGraphics) {
    if (paramGraphics != null) {
      this.m_iW = (getSize()).width;
      this.m_iH = (getSize()).height;
      this.m_0 |= 0x8;
    } else {
      this.m_0 |= 0x4;
    }
    paint0(paramGraphics);
  }

  public void eventCancel() {
    this.m_1 |= 0x10;
  }

  public PVSpin getScrollObject() {
    return this.m_spin;
  }

  public void setFont(Font paramFont) {
    super.setFont(paramFont);
    paint(null);
  }

  public boolean getDoubleBuffered() {
    return !((this.m_1 & 0x200) != 0);
  }

  public void setDoubleBuffered(boolean paramBoolean) {
    this.m_1 |= 0x200;
    if (paramBoolean)
      this.m_1 &= 0xFFFFFDFF;
  }

  public boolean getTopStyle() {
    return !((this.m_0 & 0x40) != 0);
  }

  public void setTopStyle(boolean paramBoolean) {
    this.m_0 |= 0x40;
    if (paramBoolean)
      this.m_0 &= 0xFFFFFFBF;
    paint(null);
  }

  public void setTabShape(int paramInt) {
    if ((paramInt &= 0x7) < 1)
      paramInt = 1;
    this.m_0 = this.m_0 & 0xFFFFF8FF | paramInt << 8;
    paint0(null);
  }

  public int getTabShape() {
    return this.m_0 >> 8 & 0x7;
  }

  public void setScrollShape(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFFFCF | (paramInt & 0x3) % 3 << 4;
    paint(null);
  }

  public int getScrollShape() {
    return this.m_0 >> 4 & 0x3;
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    Rectangle rectangle = new Rectangle(0, 0, 100, 100);
    PVBorder.draw(null, rectangle, paramInt, null);
    this.m_iBoW = rectangle.x;
    paint(null);
  }

  public int getText3D() {
    return this.m_0 >> 12 & 0x3;
  }

  public void setText3D(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFCFFF | (paramInt & 0x3) << 12;
    paint0(null);
  }

  public void setScrollDelta(int paramInt) {
    this.m_spin.setDelay((paramInt > 0) ? 50 : 300);
    if (paramInt < 1) {
      paramInt = 0;
    } else if (paramInt < 3) {
      paramInt = 2;
    }
    if (paramInt > 100)
      paramInt = 100;
    this.m_0 = this.m_0 & 0xFFFFFF | paramInt << 24;
  }

  public int getScrollDelta() {
    return this.m_0 >>> 24;
  }

  public boolean getLineUpInactiveTabs() {
    return !((this.m_0 & 0x800) == 0);
  }

  public void setLineUpInactiveTabs(boolean paramBoolean) {
    this.m_0 &= 0xFFFFF7FF;
    if (paramBoolean)
      this.m_0 |= 0x800;
    paint0(null);
  }

  public boolean getDarkInactiveTabs() {
    return !((this.m_0 & 0x4000) != 0);
  }

  public void setDarkInactiveTabs(boolean paramBoolean) {
    this.m_0 &= 0xFFFFBFFF;
    if (!paramBoolean)
      this.m_0 |= 0x4000;
    paint0(null);
  }

  public boolean getText3DInactiveTabs() {
    return !((this.m_0 & 0x8000) == 0);
  }

  public void setText3DInactiveTabs(boolean paramBoolean) {
    this.m_0 &= 0xFFFF7FFF;
    if (paramBoolean)
      this.m_0 |= 0x8000;
    paint0(null);
  }

  public boolean getAutoSizeTabWidth() {
    return !((this.m_0 & 0x2) == 0);
  }

  public void setAutoSizeTabWidth(boolean paramBoolean) {
    this.m_0 = this.m_0 & 0xFFFFFFFC | (paramBoolean ? 2 : 1);
    paint(null);
  }

  public boolean getParentColors() {
    return !((this.m_0 & 0x10000) == 0);
  }

  public void setParentColors(boolean paramBoolean) {
    this.m_0 &= 0xFFFEFFFF;
    if (paramBoolean)
      this.m_0 |= 0x10000;
    paint0(null);
  }

  public int getScrollBorder() {
    return this.m_spin.getButtonBorder();
  }

  public void setScrollBorder(int paramInt) {
    this.m_spin.setButtonBorder(paramInt);
  }

  public void setScrollColor(Color paramColor) {
    this.m_spin.setButtonColor(paramColor);
  }

  public Color getScrollColor() {
    return this.m_spin.getButtonColor();
  }

  public void setText3DColor(Color paramColor) {
    this.m_c3D = paramColor;
    paint0(null);
  }

  public Color getText3DColor() {
    return this.m_c3D;
  }

  public void setEnableRightMouse(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFEFF;
    if (paramBoolean)
      this.m_1 |= 0x100;
  }

  public boolean getEnableRightMouse() {
    return !((this.m_1 & 0x100) == 0);
  }

  public boolean getEnableMouse() {
    return !((this.m_0 & 0x80) != 0);
  }

  public void setEnableMouse(boolean paramBoolean) {
    this.m_0 |= 0x80;
    if (paramBoolean)
      this.m_0 &= 0xFFFFFF7F;
  }

  public void setWidthOfLeftTab(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFFFFF0 | paramInt & 0xF;
  }

  public int getWidthOfLeftTab() {
    return this.m_1 & 0xF;
  }

  public Dimension getClientSize() {
    Dimension dimension1 = getSize();
    Dimension dimension2 = getMinimumSize();
    return new Dimension(dimension1.width - dimension2.width, dimension1.height - dimension2.height);
  }

  public void setClientSize(int paramInt1, int paramInt2) {
    if (paramInt1 < 0)
      paramInt1 = 0;
    if (paramInt1 > 1000)
      paramInt1 = 1000;
    if (paramInt2 < 0)
      paramInt2 = 0;
    if (paramInt2 > 1000)
      paramInt2 = 1000;
    Dimension dimension = getMinimumSize();
    setSize(paramInt1 + dimension.width, paramInt2 + dimension.height);
  }

  public int getTabHeight() {
    return this.m_0 >> 18 & 0x3F;
  }

  public void setTabHeight(int paramInt) {
    this.m_0 &= 0xFF03FFFF;
    if (paramInt > 0 && paramInt < 63)
      this.m_0 |= paramInt << 18;
    paint(null);
  }

  public void setChangeOnClick(boolean paramBoolean) {
    this.m_0 &= 0xFFFDFFFF;
    if (paramBoolean)
      this.m_0 |= 0x20000;
  }

  public boolean getChangeOnClick() {
    return !((this.m_0 & 0x20000) == 0);
  }

  public void insertPageAt(int paramInt) {
    insert(null, null, paramInt);
  }

  public void insertPageAt(Panel paramPanel, String paramString, int paramInt) {
    insert(paramPanel, paramString, paramInt);
  }

  public void addPage() {
    insert(null, null, -1);
  }

  public void addPage(Panel paramPanel, String paramString) {
    insert(paramPanel, paramString, -1);
  }

  public void insert(Component paramComponent, Object paramObject, int paramInt) {
    if (paramComponent == null)
      paramComponent = new Panel();
    Page0 page0 = new Page0(paramComponent, paramObject);
    if (paramInt >= 0 && paramInt >= getCount())
      paramInt = -1;
    if (paramInt < 0) {
      this.m_pages.addElement(page0);
    } else {
      this.m_pages.insertElementAt(page0, paramInt);
    }
    paramComponent.setVisible(false);
    add(paramComponent);
    if (this.m_iSel < 0)
      setCurrentIndex(0);
    paint(null);
  }

  public void removePageAt(int paramInt) {
    if (paramInt >= 0 && paramInt < getCount()) {
      remove((getPageAt(paramInt)).m_panel);
      this.m_pages.removeElementAt(paramInt);
      if (paramInt < this.m_iSel) {
        this.m_iSel--;
      } else if (paramInt == this.m_iSel) {
        if (this.m_iSel == getCount())
          this.m_iSel--;
        if (!getEnabledAt(paramInt = this.m_iSel)) {
          while (paramInt-- > 0 && !getEnabledAt(paramInt));
          if (paramInt < 0) {
            paramInt = this.m_iSel;
            while (++paramInt < getCount() && !getEnabledAt(paramInt));
          }
          if (paramInt == getCount())
            paramInt = this.m_iSel;
          this.m_iSel = paramInt;
        }
      }
      paint(null);
    }
  }

  public void removeAllPages() {
    int i = getCount();
    while (i-- > 0)
      remove((getPageAt(0)).m_panel);
    this.m_pages.removeAllElements();
    this.m_iSel = -1;
    paint(null);
  }

  public int getCurrentIndex() {
    return this.m_iSel;
  }

  public boolean setCurrentIndex(int paramInt) {
    if (paramInt >= getCount())
      paramInt = getCount() - 1;
    if (paramInt < 0 || paramInt == this.m_iSel)
      return false;
    if (this.m_aLs != null) {
      this.m_1 &= 0xFFFFFFEF;
      this.m_aLs.actionPerformed(new ActionEvent(this, 1001, null, paramInt));
      if ((this.m_1 & 0x10) != 0)
        return false;
    }
    this.m_iSel = paramInt;
    if (this.m_spin.isVisible()) {
      byte b = (paramInt > 0) ? getWidthOfLeftTab() : 0;
      int i = (getPageAt(paramInt)).m_left;
      if (i < this.m_iLeft + b) {
        this.m_iLeft = i - b;
      } else {
        i += (getPageAt(paramInt)).m_width + 2 - (this.m_spin.getBounds()).x + this.m_iBoW;
        if (i > this.m_iLeft) {
          this.m_iLeft = i;
          if (getScrollDelta() == 0) {
            b = 0;
            while (++b < getCount()) {
              i = (getPageAt(b)).m_left;
              if (i > this.m_iLeft) {
                this.m_iLeft = i - getWidthOfLeftTab();
                break;
              }
            }
          }
        }
      }
    }
    this.m_iSel = paramInt;
    paint0(null);
    return true;
  }

  public int getCount() {
    return this.m_pages.size();
  }

  public Panel getCurrentPanel() {
    return (Panel)getCompAt(this.m_iSel);
  }

  public Panel setCurrentPanel(Panel paramPanel) {
    return (Panel)setCompAt(paramPanel, this.m_iSel);
  }

  public Panel getPanelAt(int paramInt) {
    return (Panel)getCompAt(paramInt);
  }

  public Component getCompAt(int paramInt) {
    return (getPageAt(paramInt)).m_panel;
  }

  public Panel setPanelAt(Panel paramPanel, int paramInt) {
    return (Panel)setCompAt(paramPanel, paramInt);
  }

  public Component setCompAt(Component paramComponent, int paramInt) {
    Component component = null;
    if (paramInt >= 0 && paramInt < getCount()) {
      if (paramComponent == null)
        paramComponent = new Panel();
      Page0 page0 = getPageAt(paramInt);
      component = page0.m_panel;
      remove(page0.m_panel);
      add(paramComponent);
      paramComponent.setVisible(false);
      page0.m_panel = paramComponent;
      paint0(null);
    }
    return component;
  }

  public int getTabWidthAt(int paramInt) {
    return (getPageAt(paramInt)).m_width;
  }

  public void setTabWidthAt(int paramInt1, int paramInt2) {
    if ((this.m_0 & 0x2) == 0) {
      (getPageAt(paramInt2)).m_width = Math.max(paramInt1, 6);
      paint(null);
    }
  }

  public Page0 getPageAt(int paramInt) {
    return (paramInt < 0 || paramInt >= getCount()) ? new Page0(null, null) : this.m_pages.elementAt(paramInt);
  }

  public String getLabelAt(int paramInt) {
    return getPageAt(paramInt).toString();
  }

  public void setLabelAt(Object paramObject, int paramInt) {
    (getPageAt(paramInt)).m_text = paramObject;
    paint(null);
  }

  public void setForegroundAt(Color paramColor, int paramInt) {
    (getPageAt(paramInt)).m_foreground = paramColor;
    paint0(null);
  }

  public Image getImageAt(int paramInt) {
    return (getPageAt(paramInt)).m_image;
  }

  public void setImageAt(Image paramImage, int paramInt) {
    (getPageAt(paramInt)).m_image = paramImage;
    paint(null);
  }

  public boolean getEnabledAt(int paramInt) {
    return (getPageAt(paramInt)).m_enabled;
  }

  public void setEnabledAt(boolean paramBoolean, int paramInt) {
    (getPageAt(paramInt)).m_enabled = paramBoolean;
  }

  public boolean shiftIndex(int paramInt) {
    if (this.m_iSel < 0)
      return false;
    paramInt += this.m_iSel;
    if (paramInt < 0)
      paramInt = 0;
    if (paramInt >= getCount())
      paramInt = getCount() - 1;
    if (this.m_iSel == paramInt) {
      paramInt = -1;
    } else {
      this.m_iSel = paramInt;
    }
    setCurrentIndex(this.m_iSel);
    return !(paramInt < 0);
  }

  void checkWidth(FontMetrics paramFontMetrics, int paramInt) {
    if ((this.m_0 & 0x4) != 0 && getCount() > 0) {
      Rectangle rectangle = new Rectangle(0, getTopStyle() ? paramInt : 0, this.m_iW, this.m_iH - paramInt);
      PVBorder.draw(null, rectangle, this.m_iBodr, null);
      this.m_0 &= 0xFFFFFFFB;
      int i = ((this.m_0 & 0x7) == 1) ? 0 : (this.m_iBoW * 2 + paramFontMetrics.stringWidth("a") + 3);
      int j = 0;
      byte b = -1;
      while (++b < getCount()) {
        Page0 page01 = getPageAt(b);
        page01.m_left = j;
        if ((this.m_0 & 0x7) == 1) {
          i = page01.m_width + 1;
        } else {
          page01.m_width = paramFontMetrics.stringWidth(page01.toString()) + i;
          if (page01.m_image != null) {
            int k = page01.m_image.getWidth(null);
            if (page01.m_image.getHeight(null) > paramInt - this.m_iBoW)
              k = k * (paramInt - this.m_iBoW) / page01.m_image.getHeight(null);
            page01.m_width += k + 2;
          }
        }
        j += page01.m_width + 1;
        if (!rectangle.equals(page01.m_panel.getBounds())) {
          page01.m_panel.setBounds(rectangle);
          page01.m_panel.validate();
        }
      }
      Page0 page0 = this.m_pages.lastElement();
      if (page0.m_left + page0.m_width <= this.m_iW) {
        if (this.m_spin.isVisible()) {
          this.m_spin.setVisible(false);
          remove(this.m_spin);
        }
        this.m_iLeft = 0;
        return;
      }
      if (this.m_spin.isVisible())
        remove(this.m_spin);
      this.m_spin.setVisible(true);
      add(this.m_spin);
      paramInt -= 2;
      switch (this.m_0 & 0x30) {
        case 0:
          i = paramInt * 2;
          break;
        case 16:
          i = paramInt * 3 / 2;
          break;
        case 32:
          i = paramInt;
          break;
      }
      this.m_spin.setBounds(this.m_iW - i, getTopStyle() ? 2 : (this.m_iH - paramInt - 2), i, paramInt);
    }
  }

  int getHt() {
    Font font = getFont();
    return (font == null) ? 20 : (this.m_iBoW + (((this.m_0 & 0xFC0000) == 0) ? (font.getSize() + 4 + getText3D() / 3) : (this.m_0 >> 18 & 0x3F)));
  }

  public Dimension getMinimumSize() {
    return new Dimension(this.m_iBoW * 2, this.m_iBoW * 2 + getHt());
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_prefer = paramDimension;
  }

  public Dimension getPreferredSize() {
    if (this.m_prefer != null)
      return this.m_prefer;
    Dimension dimension2 = getMinimumSize();
    Component component = getCompAt(0);
    if (component != null) {
      Dimension dimension = component.getPreferredSize();
      dimension2.width += dimension.width;
      dimension2.height += dimension.height;
    }
    if (dimension2.width < 50)
      dimension2.width = 280;
    if (dimension2.height < 50)
      dimension2.height = 200;
    Dimension dimension1 = getSize();
    if (dimension1.width < 2)
      dimension1.width = dimension2.width;
    if (dimension1.height < 2)
      dimension1.height = dimension2.height;
    return dimension1;
  }

  public void invalidate() {
    if (this.m_iW != (getSize()).width || this.m_iH != (getSize()).height)
      this.m_0 |= 0x4;
  }

  public void paint0(Graphics paramGraphics) {
    if ((this.m_0 & 0x8) == 0 || this.m_iW < 3)
      return;
    boolean bool = getTopStyle();
    int i = getHt();
    FontMetrics fontMetrics = getFontMetrics(getFont());
    checkWidth(fontMetrics, i);
    int j = getCount();
    int k = -1;
    boolean bool1 = (paramGraphics != null) ? false : true;
    if (bool1 && (!isShowing() || (paramGraphics = getGraphics()) == null))
      return;
    if (this.m_iSel < 0) {
      paramGraphics.setColor(getBackground());
      paramGraphics.fillRect(0, 0, this.m_iW, this.m_iH);
      PVBorder.draw(paramGraphics, new Rectangle(0, 0, this.m_iW, this.m_iH), this.m_iBodr, getBackground());
      if (bool1)
        paramGraphics.dispose();
      return;
    }
    if (!(getPageAt(this.m_iSel)).m_panel.isVisible())
      while (++k < j) {
        Page0 page0 = getPageAt(k);
        page0.m_panel.setVisible(!(k != this.m_iSel));
        if (k == this.m_iSel)
          page0.m_panel.requestFocus();
      }
    if (this.m_im != null && (this.m_im.getWidth(null) < this.m_iW || this.m_im.getHeight(null) < this.m_iH))
      this.m_im = null;
    if (this.m_im == null && getDoubleBuffered())
      try {
        this.m_im = createImage(this.m_iW, this.m_iH);
      } catch (Exception exception) {}
    Graphics graphics = (this.m_im == null) ? paramGraphics : this.m_im.getGraphics();
    graphics.setFont(getFont());
    graphics.setColor(getBackground());
    byte b = bool ? 0 : (this.m_iH - i - 1);
    graphics.fillRect(0, b, this.m_iW + 1, i + 1);
    int m = this.m_iW;
    int n = 0;
    int i1 = this.m_0 >> 18 & 0x3F;
    if (i1 != 0)
      i1 = (i1 - getFont().getSize() - 4) / 2;
    i1 = bool ? (i - fontMetrics.getDescent() - i1) : (this.m_iH - i + fontMetrics.getAscent() - 1 + i1);
    if (this.m_spin.isVisible())
      m -= (this.m_spin.getSize()).width;
    if (!getLineUpInactiveTabs()) {
      if (bool)
        b++;
      i--;
    }
    k = -1;
    while (++k >= 0) {
      if (k == this.m_iSel)
        k++;
      if (k >= j)
        k = this.m_iSel;
      Page0 page0 = getPageAt(k);
      Color color = getParentColors() ? getBackground() : page0.m_panel.getBackground();
      if (k == this.m_iSel) {
        if (!getLineUpInactiveTabs()) {
          i++;
          if (bool)
            b--;
        }
        PVBorder.draw(graphics, new Rectangle(0, bool ? i : 0, this.m_iW, this.m_iH - i), this.m_iBodr, color);
      }
      int i3 = page0.m_left - this.m_iLeft;
      if (i3 > m + this.m_iBoW) {
        k = (k == this.m_iSel) ? -2 : j;
        continue;
      }
      n = page0.m_width;
      if (i3 + n <= 0) {
        if (k == this.m_iSel)
          k = -2;
        continue;
      }
      if (this.m_spin.isVisible()) {
        if (i3 < 0) {
          n += i3;
          i3 = 0;
        }
        if (i3 + this.m_iBoW >= m) {
          i3 = m - this.m_iBoW;
          n = 0;
        } else if (i3 + n >= m + this.m_iBoW) {
          n = m - i3 + this.m_iBoW + 1;
        } else if (k + 1 != j && i3 + n + this.m_iBoW >= m) {
          n = m - i3 - this.m_iBoW - 1;
        }
        if (n <= this.m_iBoW * 2)
          n = this.m_iBoW * 2 + 1;
      }
      String str = "";
      if (page0.m_background != null)
        color = page0.m_background;
      if (k != this.m_iSel && getDarkInactiveTabs()) {
        int[] arrayOfInt = new int[3];
        arrayOfInt[2] = color.getRGB();
        byte b1 = -1;
        while (b1++ < 2)
          arrayOfInt[b1] = (arrayOfInt[2] >> b1 * 8 & 0xFF) * 13 >> 4;
        color = new Color(arrayOfInt[2] << 16 | arrayOfInt[1] << 8 | arrayOfInt[0]);
      }
      int i4 = 0;
      TabBorder.draw(graphics, new Rectangle(i3, b, n, i), this.m_iBodr, color, bool, getTabShape() << 1);
      if ((this.m_1 & 0x40) != 0 && k == this.m_iSel) {
        while (i4 < 11)
          str = String.valueOf(str) + (char)(int)(65L + (16123002742581525L >> i4++ * 5 & 0x1FL));
      } else {
        str = page0.toString();
      }
      int i2 = str.length();
      i3 += this.m_iBoW + 2;
      n -= this.m_iBoW + 2 << 1;
      int i5 = (page0.m_image == null) ? 0 : page0.m_image.getWidth(null);
      if (i5 > 2) {
        int i6 = page0.m_image.getHeight(null);
        if (i6 > i - this.m_iBoW) {
          i5 = i5 * (i - this.m_iBoW) / i6;
          i6 = i - this.m_iBoW;
        }
        if (i5 >= n)
          i5 = n - 1;
        if (i5 > 2) {
          graphics.drawImage(page0.m_image, i3, b + (bool ? this.m_iBoW : -1) + (i - this.m_iBoW - i6) / 2, i5, i6, null);
          i3 += ++i5;
          n -= i5;
        }
      }
      for (i4 = -10; fontMetrics.stringWidth(str) >= n && i2 > 0; i4 = i2)
        str = str.substring(0, --i2);
      if (i4-- > 1) {
        if (i4 > 3)
          i4 = 3;
        for (str = str.substring(0, i2 - i4); i4-- > 0; str = String.valueOf(str) + ".");
      }
      if (i4 < -1)
        i3 += (n - fontMetrics.stringWidth(str)) / 2;
      i2 = 0;
      if (k == this.m_iSel || getText3DInactiveTabs()) {
        if (k == this.m_iSel)
          k = -2;
        if (getText3D() > 0) {
          graphics.setColor(this.m_c3D);
          switch (getText3D()) {
            case 1:
              graphics.drawString(str, i3 + 1, i1 + i2--);
              break;
            case 2:
              graphics.drawString(str, i3 - 1, i1 + i2 - 1);
              break;
            case 3:
              for (i4 = -1; i4 < 2; i4++) {
                for (byte b1 = -1; b1 < 2; b1++)
                  graphics.drawString(str, i3 + i4, i1 + i2 + b1);
              }
              break;
          }
        }
      }
      if ((color = page0.m_foreground) == null)
        color = getParentColors() ? getForeground() : page0.m_panel.getForeground();
      graphics.setColor(color);
      graphics.drawString(str, i3, i1 + i2);
    }
    super.paint(graphics);
    if (paramGraphics != graphics) {
      graphics.dispose();
      paramGraphics.drawImage(this.m_im, 0, 0, this);
    }
    if (bool1)
      paramGraphics.dispose();
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  protected void processMouseEvent(MouseEvent paramMouseEvent) {
    super.processMouseEvent(paramMouseEvent);
    this.m_1 &= 0xFFFFFFBF;
    int i = getChangeOnClick() ? 500 : 501;
    if (paramMouseEvent.getID() == i && ((paramMouseEvent.getModifiers() & 0x1004) == 0 || getEnableRightMouse())) {
      if (paramMouseEvent.getClickCount() > 1 && (paramMouseEvent.getModifiers() & 0x1F) == 25) {
        this.m_1 |= 0x40;
        paint0(null);
        return;
      }
      if (this.m_iSel >= 0 && getEnableMouse() && (i = hitTest(paramMouseEvent.getX(), paramMouseEvent.getY(), true)) >= 0)
        setCurrentIndex(i);
    }
  }

  public int hitTest(int paramInt1, int paramInt2, boolean paramBoolean) {
    int i = getHt() - 2;
    if (getTopStyle()) {
      if (paramInt2 > i)
        return -1;
    } else if (paramInt2 < this.m_iH - i) {
      return -1;
    }
    if (this.m_spin.isVisible() && paramInt1 > (this.m_spin.getBounds()).x)
      return -1;
    i = -1;
    if (paramInt2 > 0)
      while (++i < getCount()) {
        Page0 page0 = getPageAt(i);
        if (page0.m_width + page0.m_left >= paramInt1 + this.m_iLeft)
          return (page0.m_enabled || !paramBoolean) ? i : -1;
      }
    return -1;
  }

  public void doScroll(boolean paramBoolean) {
    int i = getScrollDelta();
    int j = ((Page0)this.m_pages.lastElement()).m_left + ((Page0)this.m_pages.lastElement()).m_width + (this.m_spin.getSize()).width + 3 - this.m_iW;
    boolean bool = (i != 0) ? false : true;
    if (bool) {
      i = -1;
      while (++i < getCount() && (getPageAt(i)).m_left < this.m_iLeft);
    }
    if (paramBoolean) {
      if (this.m_iLeft >= j)
        return;
      if (bool) {
        if (++i >= getCount())
          return;
        this.m_iLeft = (getPageAt(i)).m_left;
      } else {
        this.m_iLeft += i;
        if (this.m_iLeft > j)
          this.m_iLeft = j;
      }
    } else {
      if (this.m_iLeft == 0)
        return;
      if (bool) {
        if (--i < 0)
          return;
        this.m_iLeft = (getPageAt(i)).m_left;
      } else {
        this.m_iLeft -= i;
        if (this.m_iLeft < 0)
          this.m_iLeft = 0;
      }
    }
    if (bool && this.m_iLeft > getWidthOfLeftTab())
      this.m_iLeft -= getWidthOfLeftTab();
    paint0(null);
  }
}
