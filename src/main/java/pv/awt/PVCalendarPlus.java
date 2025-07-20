package pv.awt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import pv.util.CalDay;
import pv.util.PVUtility;

public class PVCalendarPlus extends PVCalendar implements ActionListener, WindowListener, MouseListener, MouseMotionListener {
  public static final int DAY_OPENED = 4194304;

  public static final int DAY_CLOSED = 2097152;

  public static final int BEFORE_DAY_CLOSED = 1048576;

  PVDay m_day;

  int m_6 = 240;

  int m_key = 32;

  public String m_title = "Day";

  public PVCalendarPlus() {
    this.m_3 |= 0x380000;
  }

  public void fireAction(int paramInt) {
    super.fireAction(paramInt);
    if (((paramInt & 0x8000000) != 0 && paramInt > 134217728) || (paramInt == 16777216 && isDayOpened()))
      openDay();
    if (paramInt == Integer.MIN_VALUE && ((getDay()).m_2 & 0x20000000) == 0 && (this.m_6 & 0x111000) == 0)
      closeDay();
  }

  public PVDay getDay() {
    if (this.m_day == null) {
      this.m_day = new PVDay();
      this.m_day.addActionListener(this);
      this.m_day.setSpinDateHeight(0);
      this.m_day.setSpinBorder(5);
      this.m_day.setRowLabelBorder(5);
    }
    return this.m_day;
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (paramActionEvent.getID() == 2048 && paramActionEvent.getModifiers() == 27)
      closeDay();
    if (getAutoAddCustomDate() && (paramActionEvent.getID() & 0x60A) != 0)
      update();
  }

  public void setAutoAddCustomDate(boolean paramBoolean) {
    this.m_6 |= 0x800;
    if (paramBoolean)
      this.m_6 &= 0xFFFFF7FF;
  }

  public boolean getAutoAddCustomDate() {
    return !((this.m_6 & 0x800) != 0);
  }

  public void setDayWidth(int paramInt) {
    this.m_6 = this.m_6 & 0xFFFFF800 | paramInt & 0x7FF;
  }

  public int getDayWidth() {
    return this.m_6 & 0x7FF;
  }

  public void setDayHeight(int paramInt) {
    this.m_6 = this.m_6 & 0x1FFFFF | paramInt << 21;
  }

  public int getDayHeight() {
    return this.m_6 >>> 21;
  }

  public void setAccelerator(int paramInt) {
    this.m_key = paramInt;
  }

  public int getAccelerator() {
    return this.m_key;
  }

  protected void processKeyEvent(KeyEvent paramKeyEvent) {
    super.processKeyEvent(paramKeyEvent);
    if (this.m_key > 0 && paramKeyEvent.getKeyCode() == this.m_key && paramKeyEvent.getID() == 402)
      openDay();
  }

  public boolean isDayOpened() {
    return !((this.m_6 & 0x80000) == 0);
  }

  public void openDay() {
    if ((this.m_4 & 0x20) == 0)
      return;
    getDay().setDate(getSelDate());
    if (!isDayOpened())
      try {
        Rectangle rectangle = getBounds();
        Dialog dialog = new Dialog(PVUtility.frame(this, rectangle), this.m_title, false);
        dialog.addWindowListener(this);
        dialog.addMouseMotionListener(this);
        dialog.addMouseListener(this);
        dialog.setBackground(Color.lightGray);
        dialog.setLayout(new BorderLayout());
        int i = getDayWidth();
        Dimension dimension = getToolkit().getScreenSize();
        if (rectangle.x + rectangle.width + i > dimension.width) {
          if (rectangle.x > i || rectangle.x - i > dimension.width - rectangle.x - i - rectangle.width) {
            if ((rectangle.x -= i) < 0)
              rectangle.x = 0;
          } else {
            rectangle.x += rectangle.width;
          }
        } else {
          rectangle.x += rectangle.width;
        }
        if (rectangle.x + i > dimension.width)
          rectangle.x = dimension.width - i;
        if ((rectangle.width = getDayHeight()) > 0)
          rectangle.height = rectangle.width;
        this.m_6 |= 0x90000;
        dialog.setBounds(rectangle.x, rectangle.y, i, rectangle.height);
        this.m_day.setVisible(true);
        dialog.add(this.m_day);
        fire(4194304);
        dialog.setVisible(true);
        return;
      } catch (Exception exception) {
        return;
      }
  }

  public void closeDay() {
    if (!isDayOpened())
      return;
    Dialog dialog = (Dialog)this.m_day.getParent();
    if (dialog == null)
      return;
    fire(1048576);
    if ((this.m_4 & 0x8000010) == 134217744)
      return;
    this.m_6 &= 0xFFE6EFFF;
    dialog.removeAll();
    this.m_day.setVisible(false);
    dialog.setVisible(false);
    try {
      dialog.dispose();
    } catch (Exception exception) {}
    fire(2097152);
  }

  public void removeNotify() {
    super.removeNotify();
    closeDay();
  }

  void update() {
    Date date = getDay().getDate();
    int i = this.m_day.first(date);
    int j = this.m_day.last(date);
    String str = null;
    if (i >= 0)
      while (i <= j) {
        if (str == null) {
          str = "";
        } else {
          str = String.valueOf(str) + '\n';
        }
        String str1 = this.m_day.get(i++).toString();
        if (str1.indexOf('\n') >= 0)
          str1 = String.valueOf(str1.substring(0, str1.indexOf('\n'))) + "...";
        str = String.valueOf(str) + str1;
      }
    CalDay calDay = calDay(i = PVCalendar.pack(date));
    if (calDay != null) {
      if (str != null) {
        calDay.m_object = str;
        calDay.m_d0 = calDay.m_d0 & 0xFF800000 | i;
        calDay.m_d1 = 0;
        calDay.fix();
      } else {
        calDay.m_object = null;
        calDay.fix();
        if (calDay.m_back == null && calDay.m_fore == null && calDay.m_im == null)
          this.m_vDay.removeElement(calDay);
      }
    } else if (str != null) {
      addCustomDate(date, (Color)null, str);
    }
    paint0(null);
  }

  public void windowOpened(WindowEvent paramWindowEvent) {}

  public void windowClosed(WindowEvent paramWindowEvent) {}

  public void windowIconified(WindowEvent paramWindowEvent) {}

  public void windowDeiconified(WindowEvent paramWindowEvent) {}

  public void windowActivated(WindowEvent paramWindowEvent) {
    this.m_6 |= 0x1000;
  }

  public void windowClosing(WindowEvent paramWindowEvent) {
    closeDay();
  }

  public void windowDeactivated(WindowEvent paramWindowEvent) {
    this.m_6 &= 0xFFFEEFFF;
    if ((this.m_4 & 0x400) == 0 && getDay().checkState() == 0)
      closeDay();
  }

  public void mouseClicked(MouseEvent paramMouseEvent) {}

  public void mousePressed(MouseEvent paramMouseEvent) {}

  public void mouseReleased(MouseEvent paramMouseEvent) {}

  public void mouseEntered(MouseEvent paramMouseEvent) {}

  public void mouseExited(MouseEvent paramMouseEvent) {
    this.m_6 &= 0xFFEFFFFF;
  }

  public void mouseDragged(MouseEvent paramMouseEvent) {}

  public void mouseMoved(MouseEvent paramMouseEvent) {
    this.m_6 = this.m_6 & 0xFFFEFFFF | 0x100000;
  }
}
