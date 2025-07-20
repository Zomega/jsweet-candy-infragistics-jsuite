package pv.awt;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import pv.util.PVBorder;
import pv.util.PVImage;
import pv.util.PVUtility;

public class PVDatePlus extends PVDate implements ActionListener {
  public static final int OPENED = 8;

  public static final int CLOSED = 16;

  protected PVCalendar m_cal;

  Color m_cBut = Color.lightGray;

  int m_iButBo = 2031633;

  int m_7 = 4198400;

  protected Point m_XY;

  transient int m_iButL;

  transient int m_iButW;

  transient Image m_img3;

  transient Dimension m_old;

  protected static transient Window m_win;

  transient long m_t;

  public PVDatePlus(Date paramDate) {
    super(paramDate);
  }

  public PVDatePlus() {
    this(null);
  }

  protected void init0() {
    setCalendarObject(this.m_cal);
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

  public void actionPerformed(ActionEvent paramActionEvent) {
    Window window;
    if (paramActionEvent.getSource() != this.m_cal)
      return;
    int i = paramActionEvent.getModifiers();
    if ((i & 0x8000000) != 0) {
      if (i == 134217728)
        return;
      i = 134217728;
    }
    switch (i) {
      case 513:
        if ((this.m_7 & 0x8C) == 136)
          this.m_cal.requestFocus();
        this.m_7 &= 0xFFFFFFF7;
        return;
      case 1073741824:
        if ((this.m_7 & 0x2) == 0)
          processFocusEvent(new FocusEvent(this, 1004));
        this.m_t = System.currentTimeMillis();
        this.m_7 = this.m_7 & 0xFFFFFE77 | 0x4;
        return;
      case -2147483648:
        this.m_7 &= 0xFFFFFEF3;
        if ((this.m_7 & 0x40000001) == 1 && System.currentTimeMillis() - this.m_t > 50L) {
          close(false, !((this.m_1 & 0x4000000) == 0 && (this.m_cal.m_4 & 0x100) == 0));
          if ((this.m_1 & 0x4000000) != 0) {
            this.m_7 |= 0x100;
            return;
          }
        }
        break;
      case 16777216:
        if ((this.m_7 & 0x100081) == 1048577) {
          if ((this.m_cal.m_4 & 0x100) == 0)
            return;
        } else {
          break;
        }
      case 134217728:
      case 268435456:
      case 536870912:
      case 805306368:
        close(!(i == 536870912), true);
        return;
      case 512:
        window = (Window)this.m_cal.getParent();
        i = (this.m_cal.getSize()).height;
        if (i > 100 && this.m_old != null && window != null && (i = this.m_old.height - i) > 6) {
          window.setSize((window.getSize()).width, (window.getSize()).height + i);
          window.pack();
          return;
        }
        break;
    }
  }

  protected void processFocusEvent(FocusEvent paramFocusEvent) {
    if (paramFocusEvent.getID() == 1005) {
      if ((this.m_7 & 0x80) != 0 || (isOpened() && (this.m_1 & 0x10) == 0 && (this.m_cal.m_4 & 0x400) != 0))
        return;
      this.m_t = System.currentTimeMillis();
    } else if (System.currentTimeMillis() - this.m_t < 150L) {
      return;
    }
    if ((((this.m_7 & 0x2) == 0) ? false : true) == ((paramFocusEvent.getID() != 1004) ? false : true))
      return;
    this.m_7 &= 0xFFFFFFFD;
    if (paramFocusEvent.getID() == 1004)
      this.m_7 |= 0x2;
    paint(null);
    super.processFocusEvent(paramFocusEvent);
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    int i = paramAWTEvent.getID();
    if (i == 1005 && isOpened() && ((this.m_1 & 0x10) != 0 || ((this.m_7 & 0x40000080) == 0 && (this.m_cal.m_4 & 0x400) == 0)))
      close(false, false);
    if (i == 402)
      this.m_7 &= 0xFFFFFE77;
    if (i == 401) {
      int j;
      switch (j = ((KeyEvent)paramAWTEvent).getKeyCode()) {
        case 40:
          if (((KeyEvent)paramAWTEvent).getModifiers() != 0 && getEditableByCalendar())
            openCalendar();
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
      if (i == 503 || i == 504) {
        this.m_7 &= 0xFFFFFFCF;
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
        } else if (getEditableByCalendar() && (this.m_7 & 0x100) == 0 && (((MouseEvent)paramAWTEvent).getModifiers() & 0x1804) == 0 && j >= this.m_iButL && j <= this.m_iButL + this.m_iButW) {
          openCalendar();
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

  public void adjustXY(Rectangle paramRectangle) {}

  public Point getCalendarLocation() {
    return this.m_XY;
  }

  public void setCalendarLocation(Point paramPoint) {
    this.m_XY = paramPoint;
  }

  public int getCalendarWidth() {
    return (this.m_cal == null) ? 260 : (this.m_cal.getBounds()).width;
  }

  public void setCalendarWidth(int paramInt) {
    if (this.m_cal != null)
      this.m_cal.setSize(paramInt, (this.m_cal.getBounds()).height);
    this.m_old = null;
  }

  public int getCalendarHeight() {
    return (this.m_cal == null) ? 180 : (this.m_cal.getBounds()).height;
  }

  public void setCalendarHeight(int paramInt) {
    if (this.m_cal != null)
      this.m_cal.setSize((this.m_cal.getBounds()).width, paramInt);
    this.m_old = null;
  }

  public boolean isOpened() {
    return !(m_win == null || (this.m_7 & 0x1) == 0);
  }

  public int getCalendarMonths() {
    return this.m_7 >> 12 & 0xF;
  }

  public void setCalendarMonths(int paramInt) {
    this.m_7 = this.m_7 & 0xFFFF0FFF | (paramInt &= 0xF) << 12;
    if (this.m_cal != null)
      this.m_cal.setNumberOfMonths(paramInt);
  }

  public void setCalendarObject(PVCalendar paramPVCalendar) {
    if (paramPVCalendar == null)
      paramPVCalendar = new PVCalendar();
    if (paramPVCalendar != this.m_cal) {
      paramPVCalendar.m_2 |= 0xFF900200;
      paramPVCalendar.m_1 |= 0x3000000;
      paramPVCalendar.setNumberOfMonths(getCalendarMonths());
      paramPVCalendar.addActionListener(this);
      this.m_cal = paramPVCalendar;
    }
  }

  public PVCalendar getCalendarObject() {
    return this.m_cal;
  }

  public boolean getCloseOnDoubleClick() {
    return !((this.m_7 & 0x100000) != 0);
  }

  public void setCloseOnDoubleClick(boolean paramBoolean) {
    this.m_7 |= 0x100000;
    if (paramBoolean)
      this.m_7 &= 0xFFEFFFFF;
  }

  public boolean getWaitForCalendarDate() {
    return !((this.m_7 & 0x40000000) == 0);
  }

  public void setWaitForCalendarDate(boolean paramBoolean) {
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

  public boolean getEditableByCalendar() {
    return !((this.m_7 & 0x800) != 0);
  }

  public void setEditableByCalendar(boolean paramBoolean) {
    this.m_7 |= 0x800;
    if (paramBoolean)
      this.m_7 &= 0xFFFFF7FF;
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

  public boolean closeCalendar(boolean paramBoolean) {
    return close(paramBoolean, true);
  }

  protected boolean close(boolean paramBoolean1, boolean paramBoolean2) {
    if (!isOpened())
      return false;
    this.m_7 &= 0xFFFFFE72;
    m_win.setVisible(false);
    if (paramBoolean1) {
      Date date = this.m_cal.getSelDate();
      if (date != null && !date.equals(getDate())) {
        setDate(date);
        fireChange();
      }
    }
    if (paramBoolean2) {
      requestFocus();
    } else {
      processFocusEvent(new FocusEvent(this, 1005));
    }
    paint(null);
    fireAction(16);
    return true;
  }

  public boolean openCalendar() {
    if (isOpened() || this.m_cal == null || getParent() == null)
      return false;
    if (m_win != null && m_win.isVisible())
      return false;
    Rectangle rectangle = getBounds();
    Frame frame = PVUtility.frame(this, rectangle);
    if (this.m_XY == null) {
      adjustXY(rectangle);
      rectangle.y += rectangle.height;
    } else {
      rectangle.x = this.m_XY.x;
      rectangle.y = this.m_XY.y;
    }
    if (this.m_old == null)
      this.m_cal.setPreferredSize(this.m_old = this.m_cal.getPreferredSize());
    rectangle.width = this.m_old.width;
    rectangle.height = this.m_old.height;
    if (m_win != null && m_win.getComponent(0) != this.m_cal) {
      m_win.removeAll();
      try {
        m_win.dispose();
      } catch (Exception exception) {}
      m_win = null;
    }
    if (m_win == null) {
      try {
        m_win = new Window(frame);
      } catch (Exception exception) {
        return false;
      }
      m_win.setLayout(new BorderLayout());
      m_win.add(this.m_cal);
    }
    limits();
    if (getUseLocale()) {
      this.m_cal.setLocale(getLocale());
      this.m_cal.setLocaleStrings(true);
    }
    this.m_cal.m_calendar = this.m_calendar;
    this.m_cal.setSelDate(getDate());
    m_win.setBackground(getParent().getBackground());
    PVUtility.screen(this, rectangle);
    m_win.setBounds(rectangle);
    this.m_7 |= 0x89;
    fireAction(8);
    paint(null);
    m_win.setVisible(true);
    return true;
  }
}
