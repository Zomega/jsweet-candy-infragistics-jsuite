package pv.awt;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import pv.util.PVBorder;
import pv.util.PVImage;
import pv.util.PVUtility;

public class PVNumericPlus extends PVNumeric implements ActionListener, FocusListener, WindowListener {
  public static final int OPENED = 8;

  public static final int CLOSED = 16;

  public String m_title = "Calculator";

  public boolean m_modal = false;

  protected PVCalculator m_cal;

  Color m_cBut = Color.lightGray;

  int m_iButBo = 2031633;

  int m_7 = 4194304;

  transient int m_iButL;

  transient int m_iButW;

  transient Image m_img3;

  transient Dimension m_old;

  protected static transient Window m_win;

  transient long m_t;

  protected void init0() {
    if (this.m_cal == null) {
      this.m_cal = new PVCalculator();
      this.m_cal.m_0 = -2146073565;
      this.m_cal.addActionListener(this);
      this.m_cal.m_field.addFocusListener(this);
      this.m_cal.m_field.addActionListener(this);
      this.m_cal.m_memory.addFocusListener(this);
      this.m_cal.m_memory.setFocusTraversable(false);
    }
    super.init0();
    this.m_7 &= 0xFFFFFE10;
  }

  public void paint0(Graphics paramGraphics) {
    if (paramGraphics == null)
      return;
    super.paint0(paramGraphics);
    Rectangle rectangle = super.myRect();
    rectangle.x = this.m_iButL;
    rectangle.width = this.m_iButW;
    if (rectangle.width > 1 && rectangle.height > 1) {
      PVBorder.shadow(paramGraphics, rectangle, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), getShadow(), getShadowWidth());
      PVBorder.draw(paramGraphics, rectangle, this.m_iButBo, getButtonColor(), isOpened());
      paramGraphics.setColor(getButtonColor());
      paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
      byte b = -1;
      if (this.m_img3 != null) {
        PVImage.center(paramGraphics, null, this.m_img3, rectangle.x, rectangle.y, rectangle.width, rectangle.height, 100, 9);
      } else {
        switch (getTriangle()) {
          case 2:
            b = 37;
            break;
          case 3:
            b = 69;
            break;
          case 4:
            b = 101;
            break;
          case 5:
            b = 1;
            break;
          default:
            b = 5;
            break;
          case 0:
            break;
        }
      }
      if (b >= 0)
        PVImage.center(paramGraphics, getButtonColor(), null, rectangle.x, rectangle.y, rectangle.width, ++rectangle.height, 15 - (b & 0x4), b | (this.m_7 & 0x1) << 4 | (isEnabled() ? 0 : 128));
    }
  }

  protected Rectangle myRect() {
    Rectangle rectangle = super.myRect();
    int i = getButtonWidth();
    if (i < 0)
      i = 8 + rectangle.height / 3 | 0x1;
    this.m_iButW = i;
    rectangle.width -= i;
    this.m_iButL = getButtonInFront() ? 0 : rectangle.width;
    if (getButtonInFront())
      rectangle.x += i;
    return rectangle;
  }

  public int checkFocus() {
    return ((this.m_7 & 0x2) == 0) ? 0 : (((this.m_7 & 0x4) != 0) ? 4 : (((this.m_1 & Integer.MIN_VALUE) == 0) ? 8 : super.checkFocus()));
  }

  boolean time(long paramLong) {
    long l = System.currentTimeMillis();
    if (paramLong < 0L)
      this.m_t = l;
    return !(l - this.m_t <= paramLong);
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getSource() == this.m_cal.m_field) {
      if (paramActionEvent.getModifiers() == 5)
        focusLost(null);
      return;
    }
    if (paramActionEvent.getSource() != this.m_cal)
      return;
    int i = paramActionEvent.getID();
    if (i == 48 || i == 49)
      close(!(i != 48), true);
    if (i == 1024) {
      Window window = (Window)this.m_cal.getParent();
      i = (this.m_cal.getSize()).height;
      if (i > 10 && this.m_old != null && window != null && (i = this.m_old.height - i) > 6) {
        window.setSize((window.getSize()).width, (window.getSize()).height + i);
        window.pack();
      }
    }
    if (i == 1025) {
      if ((this.m_7 & 0x8C) == 136)
        this.m_cal.m_field.requestFocus();
      this.m_7 &= 0xFFFFFFF7;
    }
  }

  public void focusLost(FocusEvent paramFocusEvent) {
    this.m_7 &= 0xFFFFFEF7;
    if ((this.m_7 & 0x40000001) == 1 && (!this.m_cal.mouseIn() || paramFocusEvent == null) && time(50L)) {
      close(!(paramFocusEvent != null), !((this.m_1 & 0x4000000) == 0 && paramFocusEvent != null));
      if ((this.m_1 & 0x4000000) != 0 && paramFocusEvent != null)
        this.m_7 |= 0x100;
    }
  }

  public void focusGained(FocusEvent paramFocusEvent) {
    if ((this.m_7 & 0x2) == 0)
      processFocusEvent(null);
    time(-1L);
    this.m_7 = this.m_7 & 0xFFFFFE77 | 0x4;
  }

  protected void processFocusEvent(FocusEvent paramFocusEvent) {
    boolean bool = true;
    if (paramFocusEvent != null && paramFocusEvent.getID() == 1005)
      bool = false;
    if (!bool) {
      if (!time(150L) || (this.m_7 & 0x80) != 0 || (isOpened() && (this.m_1 & 0x10) == 0 && this.m_cal.mouseIn()))
        return;
      time(-1L);
    } else if (paramFocusEvent != null && !time(150L)) {
      return;
    }
    if ((((this.m_7 & 0x2) == 0) ? false : true) == bool)
      return;
    this.m_7 &= 0xFFFFFFFD;
    if (bool)
      this.m_7 |= 0x2;
    paint(null);
    super.processFocusEvent((paramFocusEvent == null) ? new FocusEvent(this, 1004) : paramFocusEvent);
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    int i = paramAWTEvent.getID();
    if (i == 1005 && isOpened() && ((this.m_1 & 0x10) != 0 || ((this.m_7 & 0x40000080) == 0 && !this.m_cal.mouseIn() && time(50L))))
      close(false, false);
    if (i == 402)
      this.m_7 &= 0xFFFFFE77;
    if (i == 401) {
      int j;
      switch (j = ((KeyEvent)paramAWTEvent).getKeyCode()) {
        case 40:
          if (((KeyEvent)paramAWTEvent).getModifiers() != 0) {
            open();
            return;
          }
          break;
        case 9:
        case 10:
        case 27:
          close(false, !(j == 9));
          break;
      }
    }
    if (isFocusTraversable() && paramAWTEvent instanceof MouseEvent) {
      int j = ((MouseEvent)paramAWTEvent).getX();
      if (i == 504 || i == 503) {
        this.m_7 &= 0xFFFFFE57;
        if (j >= this.m_iButL && j <= this.m_iButL + this.m_iButW)
          this.m_7 |= 0x20;
        if ((this.m_7 >> 1 & 0x20) != (this.m_7 & 0x20)) {
          j = 0;
          this.m_7 |= 0x40;
          if ((this.m_7 & 0x20) == 0) {
            j = 2;
            this.m_7 &= 0xFFFFFFBF;
          }
          setCursor(Cursor.getPredefinedCursor(j));
        }
      }
      if (i == 501) {
        if ((this.m_7 & 0x40000001) == 1) {
          close(false, true);
        } else if ((this.m_7 & 0x100) == 0 && (((MouseEvent)paramAWTEvent).getModifiers() & 0x1804) == 0 && j >= this.m_iButL && j <= this.m_iButL + this.m_iButW) {
          open();
        }
        this.m_7 &= 0xFFFFFEFF;
      }
      if (i == 505)
        this.m_7 &= 0xFFFFFE57;
    }
    super.processEvent(paramAWTEvent);
  }

  public void removeNotify() {
    this.m_7 &= 0xFFFFFE13;
    close(false, false);
    super.removeNotify();
  }

  public boolean isOpened() {
    return !(m_win == null || (this.m_7 & 0x1) == 0);
  }

  public PVCalculator getCalculator() {
    return this.m_cal;
  }

  public boolean getDialog() {
    return !((this.m_7 & 0x80000) == 0);
  }

  public void setDialog(boolean paramBoolean) {
    this.m_7 &= 0xFFF7FFFF;
    if (paramBoolean)
      this.m_7 |= 0x80000;
  }

  public boolean getWaitOK_Cancel() {
    return !((this.m_7 & 0x40000000) == 0);
  }

  public void setWaitOK_Cancel(boolean paramBoolean) {
    this.m_7 &= 0xBFFFFFFF;
    if (paramBoolean)
      this.m_7 |= 0x40000000;
  }

  public boolean getButtonInFront() {
    return !((this.m_7 & Integer.MIN_VALUE) == 0);
  }

  public void setButtonInFront(boolean paramBoolean) {
    this.m_7 &= Integer.MAX_VALUE;
    if (paramBoolean)
      this.m_7 |= Integer.MIN_VALUE;
    invalidate();
  }

  public int getButtonWidth() {
    return ((this.m_7 & 0x3F000000) == 0) ? -1 : ((this.m_7 >> 24 & 0x3F) - 1);
  }

  public void setButtonWidth(int paramInt) {
    this.m_7 &= 0xC0FFFFFF;
    if (paramInt >= 0) {
      if (paramInt > 60)
        paramInt = 60;
      this.m_7 |= ++paramInt << 24;
    }
    invalidate();
  }

  public Color getButtonColor() {
    return (this.m_cBut == null) ? getBackColor() : this.m_cBut;
  }

  public void setButtonColor(Color paramColor) {
    this.m_cBut = paramColor;
    paint(null);
  }

  public int getTriangle() {
    return this.m_7 >> 21 & 0x7;
  }

  public void setTriangle(int paramInt) {
    this.m_7 = this.m_7 & 0xFF1FFFFF | (paramInt & 0x7) << 21;
    paint(null);
  }

  public int getButtonBorder() {
    return this.m_iButBo;
  }

  public void setButtonBorder(int paramInt) {
    this.m_iButBo = paramInt;
    paint(null);
  }

  public void setImage(Image paramImage) {
    this.m_img3 = paramImage;
    paint(null);
  }

  public Image getImage() {
    return this.m_img3;
  }

  public boolean close(boolean paramBoolean) {
    return close(paramBoolean, true);
  }

  protected boolean close(boolean paramBoolean1, boolean paramBoolean2) {
    if (!isOpened())
      return false;
    this.m_cal.button(48).setPressed(false);
    this.m_cal.button(49).setPressed(false);
    this.m_7 &= 0xFFFFFE72;
    m_win.setVisible(false);
    if (paramBoolean1 && this.m_cal.getDouble() != getDouble()) {
      setText(this.m_cal.getText());
      fireChange();
    }
    if (paramBoolean2) {
      if ((this.m_1 & 0xA0000000) == 0)
        requestFocus();
    } else {
      processFocusEvent(new FocusEvent(this, 1005));
    }
    paint(null);
    fireAction(16);
    return true;
  }

  public boolean open() {
    if (isOpened() || this.m_cal == null || getParent() == null)
      return false;
    if (m_win != null && m_win.isVisible())
      return false;
    Rectangle rectangle = getBounds();
    Frame frame = PVUtility.frame(this, rectangle);
    rectangle.y += rectangle.height;
    if (this.m_old == null) {
      this.m_cal.setPreferredSize(this.m_old = this.m_cal.getPreferredSize());
    } else {
      this.m_cal.setSize(this.m_old);
    }
    rectangle.width = this.m_old.width;
    rectangle.height = this.m_old.height;
    this.m_7 |= 0x89;
    time(-1L);
    if (m_win != null && m_win.getComponent(0) != this.m_cal) {
      m_win.removeAll();
      try {
        m_win.dispose();
      } catch (Exception exception) {}
      m_win = null;
    }
    if (m_win == null)
      try {
        if (getDialog()) {
          m_win = new Dialog(frame, this.m_title, this.m_modal);
          rectangle.height += 50;
          rectangle.width += 8;
          m_win.addWindowListener(this);
        } else {
          m_win = new Window(frame);
        }
        m_win.setLayout(new BorderLayout());
        m_win.add(this.m_cal);
      } catch (Exception exception) {
        return false;
      }
    this.m_cal.setText(this.m_text);
    m_win.setBackground(getParent().getBackground());
    PVUtility.screen(this, rectangle);
    m_win.setBounds(rectangle);
    paint(null);
    fireAction(8);
    m_win.setVisible(true);
    return true;
  }

  public void windowOpened(WindowEvent paramWindowEvent) {}

  public void windowClosed(WindowEvent paramWindowEvent) {}

  public void windowIconified(WindowEvent paramWindowEvent) {}

  public void windowDeiconified(WindowEvent paramWindowEvent) {}

  public void windowActivated(WindowEvent paramWindowEvent) {}

  public void windowClosing(WindowEvent paramWindowEvent) {
    close(false, true);
  }

  public void windowDeactivated(WindowEvent paramWindowEvent) {}
}
