package pv.awt;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import pv.util.PVBorder;
import pv.util.PVLED;
import pv.util.PVOdometer;

public class PVEdit extends Component implements Runnable, Serializable {
  public static final int UNDERLINE = -2;

  public static final int NORMAL = 0;

  public static final int FORMATTED = 1;

  public static final int FORMATTED1 = 2;

  public static final int LED = 3;

  public static final int LED_BOLD = 4;

  public static final int UPPER = 1;

  public static final int LOWER = 2;

  public static final int ENTER = 1;

  public static final int DOUBLE_CLICK = 2;

  public static final int ESCAPE = 4;

  public static final int TAB = 5;

  public static final int LENGTH = 16;

  protected String m_text;

  protected String m_mask;

  int m_1;

  int m_2 = 20548;

  int m_3 = 536870912;

  int m_4 = 255;

  int m_iFont;

  int m_iBodr = 23;

  Color m_cBo;

  Color m_cSh = Color.gray;

  Color m_c3D = Color.green;

  Color m_cSB = new Color(-16777088);

  Color m_cSF = Color.white;

  Color m_cDF;

  Color m_cC;

  protected Dimension m_prefer;

  protected Dimension m_minS;

  protected Dimension m_maxS;

  protected transient String m_undo;

  protected int m_iUndo;

  public transient Rectangle m_r = new Rectangle();

  transient int m_iCaretW;

  transient int m_iCaretH = 10;

  transient int m_iCaretT = 10;

  protected transient int m_iCaret;

  protected transient int m_iEndSel;

  protected transient int m_iStartSel;

  protected transient int m_iScroll;

  protected transient int m_key;

  transient int m_iLine = 10;

  transient int m_iSide;

  transient TextListener m_tLs;

  transient ActionListener m_aLs;

  protected static transient Thread m_run;

  transient Image m_im;

  public void run() {
    while ((this.m_1 & 0x80100000) == -2146435072 && m_run != null) {
      try {
        Thread.sleep((((this.m_1 & 0x40000000) == 0) ? getCaretRate() : 20L));
      } catch (InterruptedException interruptedException) {}
      this.m_1 &= 0xBFFFFFFF;
      if ((this.m_1 & 0x80100000) == -2146435072) {
        if (m_run == null)
          return;
        if ((this.m_1 & 0x10000000) == 0) {
          this.m_1 |= 0x10000000;
        } else {
          this.m_1 &= 0xEFFFFFFF;
        }
        if ((this.m_1 & 0x1) == 0 && (this.m_3 & 0x40000) == 0)
          paint(null);
        this.m_3 &= 0xFFFBFFFF;
        continue;
      }
      break;
    }
  }

  public PVEdit() {
    this(null);
  }

  public PVEdit(String paramString) {
    this.m_3 |= Integer.MIN_VALUE;
    init0();
    setFont(new Font("SansSerif", 0, 12));
    setForeground(Color.black);
    setBackground(Color.white);
    setText(paramString);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    if (this.m_tLs instanceof Serializable) {
      paramObjectOutputStream.writeObject("tLs");
      paramObjectOutputStream.writeObject(this.m_tLs);
    }
    if (this.m_aLs instanceof Serializable) {
      paramObjectOutputStream.writeObject("aLs");
      paramObjectOutputStream.writeObject(this.m_aLs);
    }
    paramObjectOutputStream.writeObject(null);
  }

  private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
    paramObjectInputStream.defaultReadObject();
    if ((this.m_3 & Integer.MIN_VALUE) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("tLs".equals(object))
          addTextListener((TextListener)paramObjectInputStream.readObject());
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0();
  }

  public void paint(Graphics paramGraphics) {
    boolean bool = (paramGraphics != null) ? false : true;
    if (bool && (this.m_1 & 0x800002) == 2) {
      if (!isShowing()) {
        stopCaret();
        return;
      }
      paramGraphics = getGraphics();
    }
    if (paramGraphics == null)
      return;
    this.m_1 |= 0x2;
    if (this.m_text == null)
      this.m_text = "";
    Rectangle rectangle = getBounds();
    if (this.m_im != null && ((rectangle.x = this.m_im.getWidth(null)) < rectangle.width || (rectangle.y = this.m_im.getHeight(null)) < rectangle.height))
      this.m_im = null;
    if (this.m_im == null && getDoubleBuffered())
      try {
        this.m_im = createImage((rectangle.width > rectangle.x) ? rectangle.width : rectangle.x, (rectangle.height > rectangle.y) ? rectangle.height : rectangle.y);
      } catch (Exception exception) {}
    Graphics graphics = (this.m_im == null) ? paramGraphics : this.m_im.getGraphics();
    if (graphics == null) {
      stopCaret();
      return;
    }
    paint0(graphics);
    if (graphics != paramGraphics) {
      graphics.dispose();
      paramGraphics.drawImage(this.m_im, 0, 0, null);
    }
    if (bool)
      paramGraphics.dispose();
  }

  protected Rectangle myRect() {
    return new Rectangle(getSize());
  }

  protected void init0() {
    this.m_1 &= 0x3EFF3EC;
    enableEvents(60L);
    setEnabled(isEnabled());
    setUseLocale(getUseLocale());
  }

  public void stopCaret() {
    if (m_run != null && (this.m_1 & 0x100000) != 0) {
      this.m_1 &= 0xA7EFF3FF;
      try {
        m_run.interrupt();
      } catch (Exception exception) {}
      try {
        m_run.stop();
      } catch (Exception exception) {}
      m_run = null;
    }
  }

  void caret() {
    if ((this.m_1 & 0x80000080) == Integer.MIN_VALUE && m_run == null)
      try {
        this.m_1 |= 0x100000;
        m_run = new Thread(this);
        m_run.start();
        return;
      } catch (Exception exception) {
        return;
      }
  }

  public void removeNotify() {
    stopCaret();
    this.m_1 &= 0xFEFF3EC;
    super.removeNotify();
  }

  public void setLocale(Locale paramLocale) {
    super.setLocale(paramLocale);
    updateLocale(paramLocale);
  }

  public Locale getLocale() {
    try {
      return super.getLocale();
    } catch (Exception exception) {
      return Locale.getDefault();
    }
  }

  public void updateLocale(Locale paramLocale) {}

  public boolean getUseLocale() {
    return !((this.m_3 & 0x200000) == 0);
  }

  public void setUseLocale(boolean paramBoolean) {
    this.m_3 &= 0xFFDFFFFF;
    if (paramBoolean) {
      this.m_3 |= 0x200000;
      updateLocale(null);
    }
  }

  public boolean isFocusTraversable() {
    if ((this.m_2 & 0x4000000) != 0)
      return false;
    PVEdit pVEdit = this;
    Container container;
    while ((container = pVEdit.getParent()) != null) {
      if (!container.isEnabled())
        return false;
    }
    return isEnabled();
  }

  public void setFocusTraversable(boolean paramBoolean) {
    this.m_2 |= 0x4000000;
    if (paramBoolean)
      this.m_2 &= 0xFBFFFFFF;
  }

  public void setFont(Font paramFont) {
    this.m_iSide = 0;
    super.setFont(paramFont);
  }

  public int checkFocus() {
    return ((this.m_1 & Integer.MIN_VALUE) == 0) ? 0 : (((this.m_1 & 0x40000000) == 0) ? 2 : 1);
  }

  public void setEnabled(boolean paramBoolean) {
    super.setEnabled(paramBoolean);
    setCursor(Cursor.getPredefinedCursor(paramBoolean ? 2 : 0));
    if (!paramBoolean)
      stopCaret();
    paint(null);
  }

  public void setIncludeCharacters(String paramString) {
    this.m_mask = null;
    if (paramString != null && paramString.length() > 0)
      this.m_mask = paramString;
  }

  public String getIncludeCharacters() {
    return (this.m_mask != null && this.m_mask.charAt(0) != '\001') ? this.m_mask : "";
  }

  public void setExcludeCharacters(String paramString) {
    this.m_mask = null;
    if (paramString != null && paramString.length() > 0)
      this.m_mask = String.valueOf(String.valueOf('\001')) + paramString;
  }

  public String getExcludeCharacters() {
    return (this.m_mask != null && this.m_mask.charAt(0) == '\001') ? this.m_mask.substring(1, this.m_mask.length()) : "";
  }

  boolean isOK(char paramChar) {
    if (this.m_mask == null)
      return true;
    paramChar = Character.toUpperCase(paramChar);
    boolean bool = (this.m_mask.charAt(0) == '\001') ? false : true;
    int i = this.m_mask.length();
    while (i-- > 0) {
      if (Character.toUpperCase(this.m_mask.charAt(i)) == paramChar)
        return bool;
    }
    return !bool;
  }

  protected String text() {
    return (this.m_undo == null || (this.m_3 & 0x20000) == 0) ? this.m_text : this.m_undo;
  }

  public String getText() {
    return this.m_text;
  }

  public void setText(String paramString) {
    this.m_text = (paramString == null) ? "" : paramString;
    this.m_iScroll = 0;
    this.m_text = doCase(this.m_text);
    checkLen();
    paint(null);
  }

  public void setCase(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFFFCFF | (paramInt & 0x3) % 3 << 8;
    this.m_text = doCase(this.m_text);
    paint(null);
  }

  public int getCase() {
    return this.m_1 >> 8 & 0x3;
  }

  public void setMaxLength(int paramInt) {
    this.m_3 = this.m_3 & 0xFFFFFF00 | paramInt & 0xFF;
    checkLen();
    paint(null);
  }

  public int getMaxLength() {
    return this.m_3 & 0xFF;
  }

  public boolean getDoubleBuffered() {
    return !((this.m_2 & 0x100000) != 0);
  }

  public void setDoubleBuffered(boolean paramBoolean) {
    this.m_2 |= 0x100000;
    if (paramBoolean)
      this.m_2 &= 0xFFEFFFFF;
  }

  public boolean getInsertEditMode() {
    return !((this.m_1 & 0x4000) != 0);
  }

  public void setInsertEditMode(boolean paramBoolean) {
    this.m_1 |= 0x4000;
    if (paramBoolean)
      this.m_1 &= 0xFFFFBFFF;
    this.m_iSide = 0;
  }

  public boolean getHideKeyEvent() {
    return !((this.m_1 & 0x2000000) == 0);
  }

  public void setHideKeyEvent(boolean paramBoolean) {
    this.m_1 &= 0xFDFFFFFF;
    if (paramBoolean)
      this.m_1 |= 0x2000000;
  }

  public boolean getCaretHidden() {
    return !((this.m_1 & 0x8000) == 0);
  }

  public void setCaretHidden(boolean paramBoolean) {
    this.m_1 &= 0xFFFF7FFF;
    if (paramBoolean)
      this.m_1 |= 0x8000;
  }

  public int getCaretRate() {
    return (this.m_3 >> 28 & 0x3) * 100 + 310;
  }

  public void setCaretRate(int paramInt) {
    this.m_3 = this.m_3 & 0xCFFFFFFF | ((paramInt - 260) / 100 & 0x3) << 28;
  }

  public int getCaretShape() {
    return this.m_3 >> 26 & 0x3;
  }

  public void setCaretShape(int paramInt) {
    this.m_3 = this.m_3 & 0xF3FFFFFF | (paramInt & 0x3) % 3 << 26;
  }

  public boolean getAllowChangeMode() {
    return !((this.m_1 & 0x20) != 0);
  }

  public void setAllowChangeMode(boolean paramBoolean) {
    this.m_1 |= 0x20;
    if (paramBoolean)
      this.m_1 &= 0xFFFFFFDF;
  }

  public boolean getSelectionInverted() {
    return !((this.m_2 & 0x10000000) == 0);
  }

  public void setSelectionInverted(boolean paramBoolean) {
    this.m_2 &= 0xEFFFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x10000000;
  }

  public boolean getCaretInverted() {
    return !((this.m_2 & 0x20000000) == 0);
  }

  public void setCaretInverted(boolean paramBoolean) {
    this.m_2 &= 0xDFFFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x20000000;
  }

  public boolean getAutoResize() {
    return !((this.m_3 & 0x10000) == 0);
  }

  public void setAutoResize(boolean paramBoolean) {
    this.m_3 &= 0xFFFEFFFF;
    if (paramBoolean) {
      this.m_3 |= 0x10000;
      invalidate();
    }
  }

  public boolean getFocusRect() {
    return !((this.m_1 & 0x8) == 0);
  }

  public void setFocusRect(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFFF7;
    if (paramBoolean)
      this.m_1 |= 0x8;
  }

  public boolean getAutoScroll() {
    return !((this.m_1 & 0x40) != 0);
  }

  public void setAutoScroll(boolean paramBoolean) {
    this.m_1 |= 0x40;
    if (paramBoolean)
      this.m_1 &= 0xFFFFFFBF;
  }

  public boolean getSelectionKillByMouse() {
    return !((this.m_1 & 0x4) != 0);
  }

  public void setSelectionKillByMouse(boolean paramBoolean) {
    this.m_1 |= 0x4;
    if (paramBoolean)
      this.m_1 &= 0xFFFFFFFB;
  }

  public int getSelectionPaint() {
    return this.m_1 >> 18 & 0x3;
  }

  public void setSelectionPaint(int paramInt) {
    this.m_1 = this.m_1 & 0xFFF3FFFF | (paramInt & 0x3) % 3 << 18;
  }

  public int getSelectionOnFocus() {
    return this.m_1 >> 16 & 0x3;
  }

  public void setSelectionOnFocus(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFCFFFF | (paramInt & 0x3) << 16;
  }

  public boolean getEditable() {
    return !((this.m_1 & 0x80) != 0);
  }

  public void setEditable(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFF7F;
    if (!paramBoolean) {
      this.m_1 |= 0x80;
      stopCaret();
    }
    paint(null);
  }

  public boolean getEnableUndo() {
    return !((this.m_3 & 0x100000) != 0);
  }

  public void setEnableUndo(boolean paramBoolean) {
    this.m_3 |= 0x100000;
    if (paramBoolean)
      this.m_3 &= 0xFFEFFFFF;
  }

  public boolean getSelectAllOnDoubleClick() {
    return !((this.m_1 & 0x1000000) == 0);
  }

  public void setSelectAllOnDoubleClick(boolean paramBoolean) {
    this.m_1 &= 0xFEFFFFFF;
    if (paramBoolean)
      this.m_1 |= 0x1000000;
  }

  public int getFontStyle() {
    return this.m_iFont;
  }

  public void setFontStyle(int paramInt) {
    if (paramInt > 4)
      paramInt = 0;
    this.m_iFont = paramInt;
    invalidate();
  }

  public boolean getAureole() {
    return !((this.m_4 & Integer.MIN_VALUE) == 0);
  }

  public void setAureole(boolean paramBoolean) {
    this.m_4 &= Integer.MAX_VALUE;
    if (paramBoolean)
      this.m_4 |= Integer.MIN_VALUE;
    invalidate();
  }

  public int getOdometer() {
    return this.m_2 >> 16 & 0xF;
  }

  public void setOdometer(int paramInt) {
    this.m_2 = this.m_2 & 0xFFF0FFFF | (paramInt & 0xF) % 10 << 16;
    paint(null);
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    invalidate();
  }

  public int getShadow() {
    return this.m_2 >> 8 & 0x7;
  }

  public void setShadow(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFFF8FF | (paramInt & 0x7) % 5 << 8;
    invalidate();
  }

  public int getShadowWidth() {
    return this.m_2 >> 12 & 0xF;
  }

  public void setShadowWidth(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFF0FFF | (paramInt & 0xF) << 12;
    invalidate();
  }

  public void setColumns(int paramInt) {
    this.m_3 = this.m_3 & 0xFFFF00FF | (paramInt & 0xFF) << 8;
  }

  public int getColumns() {
    return this.m_3 >> 8 & 0xFF;
  }

  public int getAlignment() {
    return this.m_1 >> 12 & 0x3;
  }

  public void setAlignment(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFFCFFF | (paramInt & 0x3) % 3 << 12;
    paint(null);
  }

  public int getCaretPos() {
    return this.m_iCaret;
  }

  public void setCaretPos(int paramInt) {
    select(paramInt, paramInt);
  }

  public int getHorizontal3D() {
    return (this.m_2 >> 4 & 0x7) - 4;
  }

  public void setHorizontal3D(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFFFF8F | (paramInt + 4 & 0x7) << 4;
    invalidate();
  }

  public int getVertical3D() {
    return (this.m_2 & 0x7) - 4;
  }

  public void setVertical3D(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFFFFF8 | paramInt + 4 & 0x7;
    invalidate();
  }

  public Color getBackColor() {
    Color color = getBackground();
    return (color == null) ? Color.white : color;
  }

  public void setBackColor(Color paramColor) {
    setBackground(paramColor);
  }

  public void setBackground(Color paramColor) {
    super.setBackground(paramColor);
    paint(null);
  }

  public Color getSelectionBackground() {
    return (this.m_cSB == null) ? getBackColor() : this.m_cSB;
  }

  public void setSelectionBackground(Color paramColor) {
    this.m_cSB = paramColor;
  }

  public Color getSelectionForeground() {
    return (this.m_cSF == null) ? foreClr() : this.m_cSF;
  }

  public void setSelectionForeground(Color paramColor) {
    this.m_cSF = paramColor;
  }

  public Color getBorderColor() {
    return (this.m_cBo == null) ? getBackColor().darker() : this.m_cBo;
  }

  public void setBorderColor(Color paramColor) {
    if (!getBorderColor().equals(paramColor))
      this.m_cBo = paramColor;
    paint(null);
  }

  public Color getText3DColor() {
    return (this.m_c3D == null) ? getBackColor() : this.m_c3D;
  }

  public void setText3DColor(Color paramColor) {
    this.m_c3D = paramColor;
    paint(null);
  }

  public Color getDisabledForeground() {
    return (this.m_cDF == null) ? getBackColor().darker() : this.m_cDF;
  }

  public void setDisabledForeground(Color paramColor) {
    if (!getDisabledForeground().equals(paramColor))
      this.m_cDF = paramColor;
  }

  public Color getCaretColor() {
    return (this.m_cC == null) ? getForeground() : this.m_cC;
  }

  public void setCaretColor(Color paramColor) {
    if (paramColor != getCaretColor())
      this.m_cC = paramColor;
  }

  public Color getShadowColor() {
    return (this.m_cSh == null) ? Color.gray : this.m_cSh;
  }

  public void setShadowColor(Color paramColor) {
    this.m_cSh = paramColor;
    paint(null);
  }

  public int getActionMask() {
    return this.m_4 & 0xFF;
  }

  public void setActionMask(int paramInt) {
    this.m_4 = this.m_4 & 0xFFFFFF00 | paramInt & 0xFF;
  }

  public void temp(String paramString) {
    this.m_undo = paramString;
    this.m_3 |= 0x60000;
    select(0, 1000);
  }

  boolean temp() {
    if ((this.m_3 & 0x20000) == 0)
      return false;
    this.m_undo = null;
    this.m_3 &= 0xFFFDFFFF;
    this.m_1 &= 0xEFFFFFFF;
    return true;
  }

  public int getIndexAtPoint(int paramInt) {
    temp();
    String str = text();
    FontMetrics fontMetrics = getFontMetrics(getFont());
    int i = startText(str, fontMetrics);
    int j = str.length() + 1;
    paramInt -= this.m_r.x;
    if (this.m_iFont <= 0) {
      paramInt -= i;
      while (--j > 0 && paramInt <= fontMetrics.stringWidth(str.substring(0, j)));
    } else {
      while (--j > 0 && paramInt <= (j + i) * this.m_iSide);
    }
    return j;
  }

  public void select(int paramInt1, int paramInt2) {
    if (paramInt1 < 0)
      paramInt1 = this.m_iCaret;
    if (paramInt2 < 0)
      paramInt2 = this.m_iCaret;
    if (paramInt2 > this.m_text.length())
      paramInt2 = this.m_text.length();
    if (paramInt1 > paramInt2)
      paramInt1 = paramInt2;
    this.m_iStartSel = paramInt1;
    this.m_iCaret = this.m_iEndSel = paramInt2;
    paint(null);
  }

  public int getSelectionStart() {
    return this.m_iStartSel;
  }

  public int getSelectionEnd() {
    return this.m_iEndSel;
  }

  public String getSelectedText() {
    return new String(text().substring(this.m_iStartSel, this.m_iEndSel));
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_prefer = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension = (this.m_prefer == null) ? getSize() : new Dimension(this.m_prefer);
    if (dimension.width < 2)
      dimension.width = 100;
    if (dimension.height < 2)
      dimension.height = 20;
    if (getAutoResize()) {
      Rectangle rectangle = new Rectangle(dimension);
      doRect(rectangle, null);
      this.m_iSide = 0;
      calcSizes(rectangle.height, getFontMetrics(getFont()));
      int i = this.m_iSide;
      int j = this.m_iFont;
      int k = i - 1;
      if (j < 3) {
        if (rectangle.height < k)
          dimension.height += k - rectangle.height;
        if (rectangle.height > k * 2)
          dimension.height -= rectangle.height - k * 2;
      }
      if ((k = getColumns()) != 0)
        k *= i;
      if (j > 0 || k != 0) {
        if (k == 0) {
          if ((k = (rectangle.width + 3) / i) < 1)
            k = 1;
          k *= i;
        }
        dimension.width += k - rectangle.width + 1;
      }
      if (j == 2)
        dimension.width -= 2;
    }
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

  public void invalidate() {
    if ((getSize()).width > 1) {
      if (getAutoResize()) {
        Dimension dimension = getPreferredSize();
        this.m_iSide = 0;
        if (dimension.width != (getSize()).width || dimension.height != (getSize()).height)
          setSize(dimension);
      }
      this.m_iSide = 0;
      paint(null);
    }
  }

  void doRect(Rectangle paramRectangle, Graphics paramGraphics) {
    PVBorder.shadow(paramGraphics, paramRectangle, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), getShadow(), getShadowWidth());
    PVBorder.draw(paramGraphics, paramRectangle, this.m_iBodr, getBorderColor());
  }

  void checkLen() {
    int i = getMaxLength();
    if (i != 0) {
      if (i < this.m_text.length())
        this.m_text = this.m_text.substring(0, i);
      if (i == this.m_text.length())
        fireAction(16);
    }
  }

  public void paint0(Graphics paramGraphics) {
    if (paramGraphics == null) {
      paint(paramGraphics);
      return;
    }
    try {
      Rectangle rectangle = myRect();
      doRect(rectangle, null);
      this.m_r = rectangle;
      String str = "";
      int i = this.m_2 & 0x3000000;
      int j = 0;
      long l = (i >= 33554432) ? 34555749885939838L : 16123002742581525L;
      if (i != 0) {
        while (j < 11)
          str = String.valueOf(str) + (char)(int)(65L + (l >> j++ * 5 & 0x1FL));
      } else {
        str = text();
      }
      j = i = str.length();
      if ((this.m_3 & 0x20000) == 0) {
        if ((this.m_3 & 0xC00000) != 0) {
          this.m_iCaret = this.m_iEndSel = 1000;
          this.m_iStartSel = this.m_3 & 0x800000;
          this.m_3 &= 0xFF3FFFFF;
        }
        if (this.m_iCaret > j)
          this.m_iCaret = j;
        if (this.m_iStartSel > j)
          this.m_iStartSel = j;
        if (this.m_iEndSel > j)
          this.m_iEndSel = j;
      }
      paramGraphics.setFont(getFont());
      FontMetrics fontMetrics = paramGraphics.getFontMetrics();
      if (this.m_iSide <= 0)
        calcSizes(rectangle.height, fontMetrics);
      StringBuffer stringBuffer = new StringBuffer(str);
      while (i-- > 0) {
        if (stringBuffer.charAt(i) < ' ')
          stringBuffer.setCharAt(i, ' ');
      }
      str = new String(stringBuffer);
      i = j;
      boolean bool1 = (this.m_iStartSel == this.m_iEndSel || temp()) ? false : true;
      if (bool1) {
        if ((j = getSelectionPaint()) == 0)
          bool1 = ((this.m_1 & Integer.MIN_VALUE) == 0) ? false : true;
        if (j > 1)
          bool1 = false;
      }
      i = getOdometer();
      if (i > 0) {
        PVOdometer.draw(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, getBackColor(), i);
      } else {
        paramGraphics.setColor(getBackColor());
        paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
      }
      i = startText(str, fontMetrics);
      boolean bool2 = (!bool1 || getSelectionInverted()) ? false : true;
      if (bool2)
        drawHi(paramGraphics, str, fontMetrics, i, rectangle.x, rectangle.y);
      if ((j = this.m_iFont) <= 0) {
        drawTxt(paramGraphics, str, fontMetrics, i + rectangle.x, bool2, rectangle.y);
      } else if (j < 3) {
        drawBox(paramGraphics, str, fontMetrics, -i, bool2, rectangle.x, rectangle.y);
      } else {
        drawLED(paramGraphics, str, i, bool2, rectangle.x, rectangle.y, !(j != 3));
      }
      if (bool1 && !bool2) {
        paramGraphics.setXORMode(getBackColor());
        drawHi(paramGraphics, str, fontMetrics, i, rectangle.x, rectangle.y);
      }
      paramGraphics.setPaintMode();
      if ((this.m_1 & Integer.MIN_VALUE) != 0)
        i = setCaret(str, fontMetrics);
      if ((this.m_1 & 0x90108080) == -1877999616) {
        paramGraphics.setColor(getCaretColor());
        if (getCaretInverted())
          paramGraphics.setXORMode(getBackColor());
        drawCaret(paramGraphics, i + rectangle.x, this.m_iCaretT + rectangle.y, this.m_iCaretW, this.m_iCaretH);
      }
      paramGraphics.setPaintMode();
      doRect(rectangle = myRect(), paramGraphics);
      if ((this.m_1 & 0x80000088) == -2147483512) {
        paramGraphics.setColor(getBackground());
        paramGraphics.drawRect(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1);
        PVBorder.draw(paramGraphics, rectangle, 256, getForeground());
        return;
      }
    } catch (Exception exception) {
      return;
    }
  }

  protected void drawCaret(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int[] arrayOfInt1 = new int[5];
    int[] arrayOfInt2 = new int[5];
    arrayOfInt1[4] = --paramInt1;
    arrayOfInt1[1] = --paramInt1;
    arrayOfInt1[0] = --paramInt1;
    arrayOfInt1[3] = paramInt1 + paramInt3;
    arrayOfInt1[2] = paramInt1 + paramInt3;
    arrayOfInt2[2] = paramInt2;
    arrayOfInt2[1] = paramInt2;
    arrayOfInt2[4] = paramInt2 + paramInt4;
    arrayOfInt2[3] = paramInt2 + paramInt4;
    arrayOfInt2[0] = paramInt2 + paramInt4;
    if ((this.m_4 & 0x20000000) != 0) {
      paramInt4 = (paramInt4 + 3) / 5;
      arrayOfInt1[1] = arrayOfInt1[1] + paramInt4;
      arrayOfInt1[2] = arrayOfInt1[2] + paramInt4;
    }
    paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 5);
  }

  protected void onSetFocus() {
    if ((this.m_1 & 0x20000004) != 536870912) {
      this.m_iCaret = this.m_iEndSel;
      int i = getSelectionOnFocus();
      if (i > 0) {
        this.m_iStartSel = this.m_iEndSel = this.m_iCaret = (i == 3) ? 0 : this.m_text.length();
        if (i == 1)
          this.m_iStartSel = 0;
      }
    }
  }

  protected void onLostFocus() {}

  public boolean cut(String paramString) {
    if (this.m_iStartSel == this.m_iEndSel && paramString == null)
      return false;
    this.m_iCaret = this.m_iStartSel;
    String str = new String(this.m_text.substring(this.m_iEndSel));
    this.m_text = this.m_text.substring(0, this.m_iStartSel);
    if (paramString != null) {
      this.m_text = String.valueOf(this.m_text) + paramString;
      this.m_iCaret += paramString.length();
    }
    this.m_text = String.valueOf(this.m_text) + str;
    return true;
  }

  public void paste() {
    try {
      Transferable transferable = getToolkit().getSystemClipboard().getContents(this);
      String str = (String)transferable.getTransferData(DataFlavor.stringFlavor);
      if (str.length() > 0) {
        cut(str);
        int i = this.m_iCaret;
        this.m_1 |= 0x800000;
        setText(this.m_text);
        afterKey();
        this.m_1 &= 0xFF7FFFFF;
        select(i, i);
        return;
      }
    } catch (Exception exception) {}
  }

  public void copy() {
    try {
      StringSelection stringSelection = new StringSelection(getSelectedText());
      getToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  public void transferFocus() {
    this.m_1 |= 0x10;
    fireAction(5);
    super.transferFocus();
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    super.processEvent(paramAWTEvent);
    int i = paramAWTEvent.getID();
    if (i == 1004) {
      if ((this.m_1 & Integer.MIN_VALUE) == 0) {
        this.m_1 |= 0xC0800000;
        this.m_2 &= 0xFDFFFFFF;
        caret();
        if ((this.m_1 & 0x8000000) == 0)
          onSetFocus();
        this.m_1 &= 0xF77FFFFF;
      }
      paint(null);
    }
    if (i == 1005) {
      this.m_iScroll = 0;
      stopCaret();
      i = this.m_1 & 0x88000000;
      this.m_1 = this.m_1 & 0x27EFF3EF | 0x800000;
      if (i != 0)
        onLostFocus();
      this.m_1 &= 0xFF7FFFFF;
      paint(null);
    }
    if (!(paramAWTEvent instanceof MouseEvent))
      return;
    MouseEvent mouseEvent = (MouseEvent)paramAWTEvent;
    if (i == 506 && (this.m_1 & 0x60000002) == 536870914 && isFocusTraversable() && (mouseEvent.getModifiers() & 0x1804) == 0) {
      int j = this.m_iStartSel;
      int k = this.m_iEndSel;
      int m = this.m_iCaret;
      int n = getIndexAtPoint(mouseEvent.getX());
      if (n < m) {
        if (n >= j) {
          k = n;
        } else {
          if (m > j)
            k = j;
          j = n;
        }
      } else if (n > m) {
        if (n <= k) {
          j = n;
        } else {
          if (m < k)
            j = k;
          k = n;
        }
      }
      if (m != n) {
        this.m_iCaret = n;
        this.m_iStartSel = j;
        this.m_iEndSel = k;
        paint(null);
      }
    }
    if (i == 503 || i == 504)
      this.m_1 |= 0x4000000;
    if (i == 505)
      this.m_1 &= 0xFBFFFFFF;
    if (i == 502)
      this.m_1 &= 0x9FFFF3EF;
    if (i == 501 && (mouseEvent.getModifiers() & 0x1804) == 0 && isFocusTraversable()) {
      this.m_1 = this.m_1 & 0xFFFFF3EF | 0x20000000;
      if (mouseEvent.getClickCount() >= 2) {
        fireAction(2);
        selectWord();
        return;
      }
      if ((this.m_1 & 0x80000004) == 4)
        requestFocus();
      if ((this.m_1 & 0x40000000) == 0) {
        this.m_iEndSel = this.m_iStartSel = this.m_iCaret = getIndexAtPoint(mouseEvent.getX());
        this.m_1 |= 0x10000000;
        if ((this.m_1 & 0x80000004) == 0) {
          requestFocus();
        } else {
          paint(null);
        }
      }
      caret();
    }
  }

  public void processKeyEvent(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyCode();
    if (i == 9 && paramKeyEvent.getID() == 401) {
      this.m_1 |= 0x10;
      fireAction(5);
    }
    int j = (paramKeyEvent.isShiftDown() ? 1 : 0) | (paramKeyEvent.isControlDown() ? 2 : 0) | (paramKeyEvent.isMetaDown() ? 4 : 0) | (paramKeyEvent.isAltDown() ? 8 : 0);
    if (!getHideKeyEvent() || i == 9 || j == 2 || j == 8)
      super.processKeyEvent(paramKeyEvent);
    i = paramKeyEvent.getKeyCode();
    if (i > 10 && i < 27)
      return;
    if (paramKeyEvent.getID() == 402) {
      this.m_1 &= 0xBFFFF3EF;
      this.m_key = 0;
      return;
    }
    this.m_2 &= 0xFEFFFFFF;
    char c = paramKeyEvent.getKeyChar();
    if (!isFocusTraversable() || (this.m_1 & 0x48000000) == 1073741824 || c + 1 == i || c == i + 1)
      return;
    if (paramKeyEvent.getID() == 400) {
      if (paramKeyEvent.isConsumed())
        return;
      paramKeyEvent.consume();
    }
    switch (j) {
      case 0:
        if (paramKeyEvent.getID() != 400 && (c == '^' || c == '_' || c == '~'))
          return;
        break;
      case 2:
        if (paramKeyEvent.getID() == 401 && (i == 90 || i == 67 || i == 88 || i == 86)) {
          c = '\005';
          this.m_key = i;
          break;
        }
        return;
      case 9:
        if (i == 112) {
          this.m_2 |= 0x1000000;
          paint(null);
        }
      default:
        if (paramKeyEvent.getID() != 400 || j >= 8)
          return;
        break;
      case 1:
        break;
    }
    if (c != '\005') {
      if (paramKeyEvent.getID() == 401) {
        if ((this.m_1 & 0x800) != 0 && this.m_key != 0)
          return;
        this.m_1 |= 0x400;
        this.m_key = i;
        if (Character.isLetterOrDigit((char)i) && (this.m_1 & 0x800) == 0)
          return;
      } else {
        if ((c > '\000' && c < ' ') || c == '')
          return;
        this.m_1 |= 0x800;
        if (Character.isDefined(c))
          this.m_key = c;
      }
      if (!Character.isDefined((char)this.m_key))
        return;
    }
    String str = this.m_text;
    i = this.m_iCaret | this.m_iStartSel << 10 | this.m_iEndSel << 20;
    this.m_1 |= 0x10000001;
    if (j == 1)
      this.m_key |= 0x10000;
    if (c == '\005') {
      if (this.m_key == 90)
        undo();
      if (this.m_key == 86 && getEditable())
        paste();
      if (this.m_iStartSel != this.m_iEndSel && (this.m_key == 67 || this.m_key == 88)) {
        copy();
        if (this.m_key == 88) {
          this.m_key = 127;
          c = Character.MIN_VALUE;
        }
      }
    }
    if (c != '\005')
      doKey((char)this.m_key, !((this.m_1 & 0x800) != 0));
    caret();
    this.m_1 &= 0xF7FFFFFE;
    if (str.compareTo(this.m_text) != 0) {
      if (getEnableUndo()) {
        this.m_undo = str;
        this.m_iUndo = i;
      }
      fireChange();
      return;
    }
    if (this.m_undo == null && getEnableUndo()) {
      this.m_undo = str;
      this.m_iUndo = i;
    }
  }

  public void doKey(char paramChar, boolean paramBoolean) {
    if (paramChar == '\n')
      fireAction(1);
    if (paramChar == '\033')
      fireAction(4);
    boolean bool = !(!paramBoolean || (paramChar != '%' && paramChar != '\'' && paramChar != '$' && paramChar != '#'));
    if (paramChar == '\000' || (!getEditable() && !bool))
      return;
    temp();
    this.m_1 &= 0xBFFFFFFF;
    bool = !(paramChar >= ' ' && paramChar != '');
    try {
      if (paramBoolean || bool) {
        if ((this.m_key & 0x10000) != 0) {
          this.m_1 &= 0xFFFFFFFE;
          doShiftKey(paramChar);
          return;
        }
        if (bool && filterChar(paramChar)) {
          afterKey();
          return;
        }
        doKeyCode(paramChar);
        this.m_1 &= 0xFFFFFFFE;
        paint(null);
        return;
      }
      if (!Character.isDefined(paramChar) || !isOK(paramChar))
        return;
      if (filterChar(paramChar)) {
        afterKey();
        return;
      }
      bool = getInsertEditMode();
      if (cut(null))
        bool = true;
      StringBuffer stringBuffer = new StringBuffer(this.m_text);
      int i = this.m_iCaret;
      if (i < stringBuffer.length()) {
        if (bool) {
          stringBuffer.insert(i, paramChar);
        } else {
          stringBuffer.setCharAt(i, paramChar);
        }
      } else {
        stringBuffer.append(paramChar);
      }
      this.m_text = stringBuffer.toString();
      i++;
      if (getMaxLength() == 1) {
        this.m_text = String.valueOf(paramChar);
        i = 0;
      }
      this.m_iEndSel = this.m_iStartSel = this.m_iCaret = i;
      this.m_text = doCase(this.m_text);
      afterKey();
      return;
    } catch (Exception exception) {
      return;
    }
  }

  public void first(char paramChar) {
    this.m_1 |= 0x8000000;
    this.m_3 &= 0xFFBFFFFF;
    onSetFocus();
    this.m_iCaret = this.m_iEndSel = this.m_text.length();
    this.m_iStartSel = 0;
    doKey('', false);
    doKey(paramChar, false);
  }

  String doCase(String paramString) {
    return (getCase() == 0) ? paramString : ((getCase() == 1) ? paramString.toUpperCase() : paramString.toLowerCase());
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  public void fireAction(int paramInt) {
    if ((paramInt & this.m_4) == paramInt && this.m_aLs != null)
      this.m_aLs.actionPerformed(new ActionEvent(this, 1001, null, paramInt));
  }

  public synchronized void addTextListener(TextListener paramTextListener) {
    this.m_tLs = AWTEventMulticaster.add(this.m_tLs, paramTextListener);
  }

  public synchronized void removeTextListener(TextListener paramTextListener) {
    this.m_tLs = AWTEventMulticaster.remove(this.m_tLs, paramTextListener);
  }

  public void fireChange() {
    if (this.m_tLs != null)
      this.m_tLs.textValueChanged(new TextEvent(this, 900));
  }

  public void undo() {
    if (this.m_undo != this.m_text && this.m_undo != null && getEnableUndo()) {
      int i = this.m_iCaret | this.m_iStartSel << 10 | this.m_iEndSel << 20;
      String str = this.m_text;
      this.m_text = this.m_undo;
      this.m_iCaret = this.m_iUndo & 0x3FF;
      this.m_iStartSel = this.m_iUndo >> 10 & 0x3FF;
      this.m_iEndSel = this.m_iUndo >> 20 & 0x3FF;
      this.m_iUndo = i;
      this.m_undo = str;
      paint(null);
    }
  }

  public void selectWord() {
    int i = this.m_iCaret;
    int j = this.m_text.length();
    if (i > j)
      i = j;
    int k = i;
    if (getSelectAllOnDoubleClick()) {
      k = -1;
      i = j;
    } else {
      while (--k >= 0 && Character.isLetterOrDigit(this.m_text.charAt(k)));
      while (i < j && Character.isLetterOrDigit(this.m_text.charAt(i)))
        i++;
    }
    this.m_iStartSel = ++k;
    this.m_iEndSel = this.m_iCaret = i;
    paint(null);
  }

  int startText(String paramString, FontMetrics paramFontMetrics) {
    if (!getAutoScroll())
      this.m_iScroll = 0;
    int i = this.m_iScroll;
    if (this.m_iFont <= 0) {
      int j = paramFontMetrics.stringWidth(paramString);
      switch (getAlignment()) {
        case 1:
          i += this.m_r.width - j >> 1;
          return i;
        case 2:
          i += this.m_r.width - j - (isCaretVertical() ? this.m_iCaretW : ((this.m_iCaretW >> 1) - 1));
          return i;
      }
      i += 2;
    } else {
      int j = paramString.length();
      if (getAlignment() > 0) {
        i = (this.m_r.width + 2) / this.m_iSide - j;
        if (getAlignment() == 1)
          i >>= 1;
        i += this.m_iScroll;
      }
    }
    return i;
  }

  protected boolean filterChar(char paramChar) {
    return false;
  }

  void doShiftKey(int paramInt) {
    int i = this.m_iCaret;
    int j = this.m_iStartSel;
    int k = this.m_iEndSel;
    switch (paramInt) {
      case 35:
        j = k;
        k = i = this.m_text.length();
        break;
      case 36:
        k = j;
        j = i = 0;
        break;
      case 8:
      case 37:
        if (i > 0) {
          if (--i >= j) {
            k = i;
            break;
          }
          j = i;
        }
        break;
      case 39:
        if (i < this.m_text.length()) {
          if (++i <= k) {
            j = i;
            break;
          }
          k = i;
        }
        break;
      default:
        return;
    }
    this.m_iCaret = i;
    this.m_iStartSel = j;
    this.m_iEndSel = k;
    paint(null);
  }

  void doKeyCode(int paramInt) {
    switch (paramInt) {
      case 8:
        if (this.m_iEndSel == this.m_iStartSel) {
          if (this.m_iCaret == 0)
            return;
          this.m_iCaret--;
        }
      case 127:
        if (!cut(null)) {
          if (this.m_iCaret >= this.m_text.length())
            return;
          this.m_text = String.valueOf(this.m_text.substring(0, this.m_iCaret)) + this.m_text.substring(this.m_iCaret + 1);
        }
        this.m_iEndSel = this.m_iStartSel = this.m_iCaret;
        afterKey();
        return;
      case 36:
        this.m_iCaret = 0;
        break;
      case 35:
        this.m_iCaret = this.m_text.length();
        break;
      case 38:
      case 40:
        if (filterChar((paramInt == 38) ? 1 : 2))
          afterKey();
        return;
      case 37:
        if (this.m_iCaret > 0)
          this.m_iCaret--;
        break;
      case 39:
        if (this.m_iCaret < this.m_text.length())
          this.m_iCaret++;
        break;
      case 155:
        if (getAllowChangeMode())
          setInsertEditMode(!getInsertEditMode());
      default:
        return;
    }
    this.m_iEndSel = this.m_iStartSel = this.m_iCaret;
  }

  protected void afterKey() {
    this.m_1 &= 0xFFFFFFFE;
    checkLen();
    paint(null);
  }

  void calcSizes(int paramInt, FontMetrics paramFontMetrics) {
    this.m_4 &= 0xDFFFFFFF;
    boolean bool = isCaretVertical();
    int i = 0;
    if (!getAureole()) {
      i = (this.m_2 & 0x7) - 4;
      if (i < 0)
        i = -i;
      i = i + 1 >>> 1;
    }
    if (this.m_iFont > 2) {
      byte b1 = 1;
      byte b2 = (this.m_iFont == 3) ? 16 : 9;
      while (paramInt > 7 * (b1 + 1) + 4 * (paramInt - b1) / b2)
        b1++;
      int m = (paramInt - b1) / b2;
      if (m >= b1) {
        m = b1;
        if (b2 > 12)
          m--;
      }
      if (m == 0)
        m++;
      int n = (m >>> 2) + 3 >>> 1;
      int i1 = paramInt - b1 * 5 + m * 4 >>> 1;
      int i2 = i1 + m * 3 + b1 * 4 - 1;
      this.m_iSide = m * 3 + (b1 << 1) + n * 3 + (m >>> 1) + 1;
      this.m_iCaretW = 1 + (bool ? (m >>> 1) : (this.m_iSide * 3 >>> 2));
      this.m_iCaretT = i + (bool ? (i1 - n) : (i2 + 2));
      this.m_iCaretH = 2 + (bool ? (i2 - i1 + m) : (m / 2));
      return;
    }
    if (this.m_iFont <= 0 && getFont().isItalic())
      this.m_4 |= 0x20000000;
    int j = paramFontMetrics.getAscent();
    int k = paramFontMetrics.getDescent();
    this.m_iSide = (paramFontMetrics.stringWidth("WWW@@@") + 5) / 6;
    if (this.m_iFont == 2)
      this.m_iSide++;
    this.m_iLine = -i + (paramInt - j + k) / 2 + j;
    this.m_iCaretW = 1 + paramFontMetrics.stringWidth("13@-+ =AWiw") / (bool ? 80 : 12);
    this.m_iCaretT = i + this.m_iLine + k / 2 - (bool ? j : 0);
    this.m_iCaretH = 1 + (bool ? j : (k / 2));
    if (this.m_iCaretT + this.m_iCaretH > paramInt)
      this.m_iCaretT = paramInt - this.m_iCaretH;
    if (!getAureole()) {
      i = (this.m_2 >> 4 & 0x7) - 4;
      this.m_iSide += (i > 0) ? i : -i;
    }
  }

  Color foreClr() {
    return isEnabled() ? getForeground() : getDisabledForeground();
  }

  void drawLED(Graphics paramGraphics, String paramString, int paramInt1, boolean paramBoolean1, int paramInt2, int paramInt3, boolean paramBoolean2) {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    paramGraphics.setColor(getText3DColor());
    if (getAureole()) {
      for (i = -1; i < 2; i++) {
        for (j = -1; j < 2; j++) {
          if (i + j == 1 || i + j == -1)
            PVLED.draw(paramGraphics, paramString, new Rectangle(k + i + paramInt2, m + j + paramInt3, this.m_r.width, this.m_r.height), -paramInt1, paramBoolean2, 0, 0, null, null);
        }
      }
    } else {
      int n = (this.m_2 >> 4 & 0x7) - 4;
      int i1 = (this.m_2 & 0x7) - 4;
      if (n != 0 || i1 != 0) {
        if (n < 0) {
          k -= n;
        } else {
          i += n;
        }
        if (i1 < 0) {
          m -= i1;
        } else {
          j += i1;
        }
        PVLED.draw(paramGraphics, paramString, new Rectangle(i + paramInt2, j + paramInt3, this.m_r.width, this.m_r.height), -paramInt1, paramBoolean2, 0, 0, null, null);
      }
    }
    PVLED.draw(paramGraphics, paramString, new Rectangle(k + paramInt2, m + paramInt3, this.m_r.width, this.m_r.height), -paramInt1, paramBoolean2, this.m_iStartSel, this.m_iEndSel, foreClr(), paramBoolean1 ? getSelectionForeground() : null);
  }

  void drawTxt(Graphics paramGraphics, String paramString, FontMetrics paramFontMetrics, int paramInt1, boolean paramBoolean, int paramInt2) {
    int i = paramInt1;
    int j = paramInt1;
    int k = this.m_iLine + paramInt2;
    int m = k;
    paramGraphics.setColor(getText3DColor());
    if (getAureole()) {
      for (i = -1; i < 2; i += 2) {
        for (k = -1; k < 2; k += 2)
          paramGraphics.drawString(paramString, j + i, m + k);
      }
    } else {
      int n = (this.m_2 >> 4 & 0x7) - 4;
      int i1 = (this.m_2 & 0x7) - 4;
      if (n != 0 || i1 != 0) {
        if (n < 0) {
          j -= n;
        } else {
          i += n;
        }
        if (i1 < 0) {
          m -= i1;
        } else {
          k += i1;
        }
        paramGraphics.drawString(paramString, i, k);
      }
    }
    paramGraphics.setColor(foreClr());
    paramGraphics.drawString(paramString, j, m);
    k = m - (i = this.m_iFont);
    if (i < 0 && paramString.length() > 0)
      paramGraphics.drawLine(j, k, j + paramFontMetrics.stringWidth(paramString), k);
    if (paramBoolean) {
      paramGraphics.setColor(getSelectionForeground());
      paramGraphics.drawString(paramString.substring(0, this.m_iEndSel), j, m);
      paramGraphics.setColor(foreClr());
      paramGraphics.drawString(paramString.substring(0, this.m_iStartSel), j, m);
    }
  }

  void drawBox(Graphics paramGraphics, String paramString, FontMetrics paramFontMetrics, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3) {
    int i = -1 + paramInt2;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = this.m_iSide;
    int i2 = i + this.m_r.width;
    boolean bool = false;
    int i3 = (this.m_2 >> 4 & 0x7) - 4;
    int i4 = (this.m_2 & 0x7) - 4;
    if (i3 != 0 || i4 != 0) {
      if (i3 < 0) {
        k = -i3;
      } else {
        j = i3;
      }
      if (i4 < 0) {
        n = -i4;
      } else {
        m = i4;
      }
      bool = true;
      i1 -= j + k;
    }
    if (getAureole()) {
      bool = true;
      j = 100;
    }
    i3 = this.m_r.height + 2;
    Color color = foreClr();
    while (i < i2) {
      if (paramInt1 >= 0 && paramInt1 < paramString.length()) {
        String str = paramString.substring(paramInt1, paramInt1 + 1);
        i4 = i1 - paramFontMetrics.stringWidth(str) + 1 >> 1;
        if (bool) {
          paramGraphics.setColor(getText3DColor());
          if (j == 100) {
            for (j = -1; j < 2; j += 2) {
              for (m = -1; m < 2; m += 2)
                paramGraphics.drawString(str, i + i4 + k + j, this.m_iLine + n + m + paramInt3);
            }
            j = 100;
          } else {
            paramGraphics.drawString(str, i + i4 + j, this.m_iLine + m + paramInt3);
          }
        }
        paramGraphics.setColor((!paramBoolean || paramInt1 < this.m_iStartSel || paramInt1 >= this.m_iEndSel) ? color : getSelectionForeground());
        paramGraphics.drawString(str, i + i4 + k, this.m_iLine + n + paramInt3);
      }
      paramInt1++;
      i += this.m_iSide;
      if (this.m_iFont == 2) {
        paramGraphics.setColor(color);
        paramGraphics.drawLine(i, paramInt3, i, i3 + paramInt3);
      }
    }
  }

  void drawHi(Graphics paramGraphics, String paramString, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, int paramInt3) {
    int[] arrayOfInt1 = new int[5];
    int[] arrayOfInt2 = new int[5];
    paramGraphics.setColor(getSelectionBackground());
    if (this.m_iFont <= 0) {
      arrayOfInt1[4] = paramInt1 + paramInt2 + paramFontMetrics.stringWidth(paramString.substring(0, this.m_iStartSel));
      arrayOfInt1[1] = paramInt1 + paramInt2 + paramFontMetrics.stringWidth(paramString.substring(0, this.m_iStartSel));
      arrayOfInt1[0] = paramInt1 + paramInt2 + paramFontMetrics.stringWidth(paramString.substring(0, this.m_iStartSel));
      arrayOfInt1[3] = arrayOfInt1[0] + paramFontMetrics.stringWidth(paramString.substring(this.m_iStartSel, this.m_iEndSel));
      arrayOfInt1[2] = arrayOfInt1[0] + paramFontMetrics.stringWidth(paramString.substring(this.m_iStartSel, this.m_iEndSel));
      arrayOfInt2[4] = this.m_iLine + paramFontMetrics.getDescent() + paramInt3;
      arrayOfInt2[3] = this.m_iLine + paramFontMetrics.getDescent() + paramInt3;
      arrayOfInt2[0] = this.m_iLine + paramFontMetrics.getDescent() + paramInt3;
      arrayOfInt2[2] = arrayOfInt2[0] - (paramInt1 = paramFontMetrics.getHeight());
      arrayOfInt2[1] = arrayOfInt2[0] - (paramInt1 = paramFontMetrics.getHeight());
      if ((this.m_4 & 0x20000000) != 0) {
        arrayOfInt1[4] = arrayOfInt1[4] - 1;
        arrayOfInt1[0] = arrayOfInt1[4] - 1;
        arrayOfInt1[1] = arrayOfInt1[4] - 1 + (paramInt1 /= 5);
        arrayOfInt1[3] = arrayOfInt1[3] - 1;
        arrayOfInt1[2] = arrayOfInt1[3] - 1 + paramInt1;
      }
    } else {
      arrayOfInt1[4] = -1 + (paramInt1 + this.m_iStartSel) * this.m_iSide + paramInt2;
      arrayOfInt1[1] = -1 + (paramInt1 + this.m_iStartSel) * this.m_iSide + paramInt2;
      arrayOfInt1[0] = -1 + (paramInt1 + this.m_iStartSel) * this.m_iSide + paramInt2;
      arrayOfInt1[3] = arrayOfInt1[0] + (this.m_iEndSel - this.m_iStartSel) * this.m_iSide + 1;
      arrayOfInt1[2] = arrayOfInt1[0] + (this.m_iEndSel - this.m_iStartSel) * this.m_iSide + 1;
      arrayOfInt2[2] = paramInt3;
      arrayOfInt2[1] = paramInt3;
      arrayOfInt2[4] = this.m_r.height + paramInt3;
      arrayOfInt2[3] = this.m_r.height + paramInt3;
      arrayOfInt2[0] = this.m_r.height + paramInt3;
    }
    paramGraphics.fillPolygon(arrayOfInt1, arrayOfInt2, 5);
  }

  int setCaret(String paramString, FontMetrics paramFontMetrics) {
    int j = paramString.length();
    if (j > this.m_iCaret)
      j = this.m_iCaret;
    if (this.m_iFont <= 0) {
      int i1 = paramFontMetrics.stringWidth(paramString.substring(0, j)) + startText(paramString, paramFontMetrics);
      int i2 = paramFontMetrics.stringWidth("Wa") >>> 1;
      if (!getAutoScroll() || (i1 >= i2 && i1 <= this.m_r.width - i2))
        return i1;
      i2 <<= 1;
      int i3 = i1 + 2 - this.m_r.width;
      if (i3 > 0) {
        i3 += i2;
        if (getAlignment() == 2 && i3 > this.m_iScroll)
          i3 = this.m_iScroll;
        this.m_iScroll -= i3;
        i1 -= i3;
      } else if (i3 < -i2) {
        i3 = 2 - i1;
        if (i3 > 0) {
          i3 += i2;
          if (getAlignment() == 0 && this.m_iScroll + i3 > 0)
            i3 = -this.m_iScroll;
          this.m_iScroll += i3;
          i1 += i3;
        } else if (getAlignment() == 1 && this.m_iScroll != 0 && i3 < -i2) {
          if (this.m_iScroll > i2) {
            i2 = -i2;
          } else if (this.m_iScroll > -i2) {
            i2 = -this.m_iScroll;
          }
          this.m_iScroll += i2;
          i1 += i2;
        }
      }
      return i1;
    }
    int i = j + startText(paramString, paramFontMetrics);
    if (!getAutoScroll())
      return i * this.m_iSide + 1;
    int k = 2;
    int m = this.m_r.width / this.m_iSide;
    int n = i - m;
    if (n >= 0) {
      n += k;
      if (getAlignment() == 2 && n > this.m_iScroll)
        n = this.m_iScroll;
      this.m_iScroll -= n;
      i -= n;
    } else if (n < -k) {
      n = -i;
      if (n >= 0) {
        n += k;
        if (getAlignment() == 0 && this.m_iScroll + n > 0)
          n = -this.m_iScroll;
        this.m_iScroll += n;
        i += n;
      } else if (getAlignment() == 1 && this.m_iScroll != 0 && n < -k) {
        if (this.m_iScroll > k) {
          k = -k;
        } else if (this.m_iScroll > -k) {
          k = -this.m_iScroll;
        }
        this.m_iScroll += k;
        i += k;
      }
    }
    i *= this.m_iSide;
    if (isCaretVertical()) {
      k = this.m_iCaretW;
    } else {
      i += this.m_iSide - this.m_iCaretW >> 1;
      k = this.m_iCaretW / 3;
    }
    k = this.m_r.width - i + k;
    if (k < 0)
      i += k;
    return ++i;
  }

  public boolean isCaretVertical() {
    return (getCaretShape() == 0) ? getInsertEditMode() : (!(getCaretShape() != 1));
  }

  public Object getValue() {
    return getText();
  }

  public void setValue(Object paramObject) {
    setText((paramObject == null) ? "" : paramObject.toString());
  }
}
