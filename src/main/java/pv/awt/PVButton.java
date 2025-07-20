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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import pv.util.PVBorder;
import pv.util.PVImage;

public class PVButton extends Component implements Serializable {
  public static final int NONE = 0;

  public static final int SIMPLE = 196609;

  public static final int SIMPLE1 = 163842;

  public static final int SIMPLE2 = 1474578;

  public static final int SIMPLE3 = 11960466;

  public static final int SIMPLE4 = 95847570;

  public static final int STANDARD = 2031633;

  public static final int STANDARD1 = 9994377;

  public static final int STANDARD2 = 81036937;

  public static final int VAGUE = 634795091;

  public static final int DOUBLE = 1056388057;

  public static final int RIGHT = 0;

  public static final int LEFT = 1;

  public static final int BOTTOM = 2;

  public static final int TOP = 3;

  public static final int CENTER = 4;

  int m_1 = 65536;

  int m_2 = 1476546611;

  int m_3 = 8192;

  int m_4 = 1021509632;

  Color m_c3D = Color.white;

  Color m_cSh = Color.gray;

  Color m_cBo;

  Color m_cMF;

  int m_iBodr = 1245209;

  protected String m_text;

  protected String m_textP;

  protected String m_act = "pv";

  protected Dimension m_prefer;

  protected Dimension m_minS;

  protected Dimension m_maxS;

  protected Vector m_imFiles;

  transient Image[] m_ims;

  transient ActionListener m_aLs;

  protected transient Image m_im;

  public PVButton(String paramString, int paramInt1, int paramInt2) {
    this.m_text = (paramString == null) ? "" : paramString;
    this.m_iBodr = paramInt2;
    setFont(new Font("SansSerif", 1, paramInt1));
    setBackground(Color.lightGray);
    setForeground(Color.black);
    init0();
  }

  public PVButton(String paramString) {
    this(paramString, 12, 2031633);
  }

  public PVButton() {
    this("Button");
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
    if ((this.m_3 & 0x2000) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0();
  }

  public void paint(Graphics paramGraphics) {
    boolean bool = (paramGraphics != null) ? false : true;
    if (bool) {
      if (this.m_im == null) {
        repaint();
        return;
      }
      if (!isShowing() || (paramGraphics = getGraphics()) == null)
        return;
    }
    Dimension dimension = getSize();
    if (getAutoResize()) {
      dimension = getPreferredSize();
      if (dimension.width != (getSize()).width || dimension.height != (getSize()).height)
        setSize(dimension);
    }
    if ((this.m_1 & 0x60000802) == 2048)
      this.m_1 |= 0x1;
    int i = 0;
    int j = 0;
    if (this.m_im != null && ((i = this.m_im.getWidth(null)) < dimension.width || (j = this.m_im.getHeight(null)) < dimension.height))
      this.m_im = null;
    if (getDoubleBuffered() && this.m_im == null)
      try {
        this.m_im = createImage((dimension.width > i) ? dimension.width : i, (dimension.height > j) ? dimension.height : j);
      } catch (Exception exception) {}
    Graphics graphics = (this.m_im == null) ? paramGraphics : this.m_im.getGraphics();
    try {
      paint0(graphics);
    } catch (Exception exception) {}
    if (graphics != paramGraphics) {
      graphics.dispose();
      paramGraphics.drawImage(this.m_im, 0, 0, null);
    }
    if (bool)
      paramGraphics.dispose();
  }

  protected void init0() {
    this.m_1 &= 0x3FFFFFF;
    enableEvents(0x38L | (getCanGetFocus() ? 4L : 0L));
  }

  public void removeNotify() {
    this.m_1 &= 0xBFFFFFF;
    super.removeNotify();
  }

  public boolean isFocusTraversable() {
    return !(!isAble() || !getCanGetFocus());
  }

  boolean isAble() {
    PVButton pVButton = this;
    Container container;
    while ((container = pVButton.getParent()) != null) {
      if (!container.isEnabled())
        return false;
    }
    return isEnabled();
  }

  public void setFont(Font paramFont) {
    super.setFont(paramFont);
    paint(null);
  }

  public String getActionCommand() {
    return this.m_act;
  }

  public void setActionCommand(String paramString) {
    this.m_act = paramString;
  }

  public String getText() {
    return this.m_text;
  }

  public void setText(String paramString) {
    this.m_text = (paramString == null) ? "" : paramString;
    paint(null);
  }

  public String getTextPressed() {
    return (this.m_textP == null) ? this.m_text : this.m_textP;
  }

  public void setTextPressed(String paramString) {
    this.m_textP = (paramString == this.m_text) ? null : paramString;
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    paint(null);
  }

  public int getShadow() {
    return this.m_2 >> 8 & 0x7;
  }

  public void setShadow(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFFF8FF | (paramInt & 0x7) % 5 << 8;
    paint(null);
  }

  public int getShadowWidth() {
    return this.m_2 >> 12 & 0xF;
  }

  public void setShadowWidth(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFF0FFF | (paramInt & 0xF) << 12;
    paint(null);
  }

  public int getHorizontal3D() {
    return (this.m_2 >> 4 & 0x7) - 4;
  }

  public void setHorizontal3D(int paramInt) {
    paramInt += 4;
    if (paramInt > 6)
      paramInt = 6;
    if (paramInt < 1)
      paramInt = 1;
    this.m_2 = this.m_2 & 0xFFFFFF8F | paramInt << 4;
    paint(null);
  }

  public int getVertical3D() {
    return (this.m_2 & 0x7) - 4;
  }

  public void setVertical3D(int paramInt) {
    paramInt += 4;
    if (paramInt > 6)
      paramInt = 6;
    if (paramInt < 1)
      paramInt = 1;
    this.m_2 = this.m_2 & 0xFFFFFFF8 | paramInt;
    paint(null);
  }

  public void setText3D(int paramInt1, int paramInt2) {
    setHorizontal3D(paramInt1);
    setVertical3D(paramInt2);
  }

  public boolean isFlat() {
    return !((this.m_1 & 0x1) == 0);
  }

  public boolean getPressed() {
    return !((this.m_1 & (((this.m_1 & 0x8400) == 33792) ? 8 : 2)) == 0);
  }

  public void setPressed(boolean paramBoolean) {
    this.m_1 = this.m_1 & 0xFFFFFFF4 | (paramBoolean ? (((this.m_1 & 0x8400) == 33792) ? 10 : 2) : (((this.m_1 & 0x20000800) == 2048) ? 1 : 0));
    paint(null);
  }

  public boolean getDoubleBuffered() {
    return !((this.m_2 & 0x100000) != 0);
  }

  public void setDoubleBuffered(boolean paramBoolean) {
    this.m_2 |= 0x100000;
    if (paramBoolean)
      this.m_2 &= 0xFFEFFFFF;
  }

  public boolean getAutoResize() {
    return !((this.m_1 & 0x10) == 0);
  }

  public void setAutoResize(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFFEF;
    if (paramBoolean) {
      this.m_1 |= 0x10;
      paint(null);
    }
  }

  public boolean getCanKeepPressed() {
    return !((this.m_1 & 0x400) == 0);
  }

  public void setCanKeepPressed(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFBFF;
    if (paramBoolean)
      this.m_1 |= 0x400;
  }

  public boolean getToggleOnPress() {
    return !((this.m_1 & 0x8000) != 0);
  }

  public void setToggleOnPress(boolean paramBoolean) {
    this.m_1 |= 0x8000;
    if (paramBoolean)
      this.m_1 &= 0xFFFF7FFF;
  }

  public boolean getThreeButtonStates() {
    return !((this.m_1 & 0x800) == 0);
  }

  public void setThreeButtonStates(boolean paramBoolean) {
    this.m_1 &= 0xFFFFF7FF;
    if (paramBoolean)
      this.m_1 |= 0x800;
    setPressed(false);
  }

  public boolean getCanGetFocus() {
    return !((this.m_1 & 0x4) != 0);
  }

  public void setCanGetFocus(boolean paramBoolean) {
    if (paramBoolean) {
      this.m_1 &= 0xFFFFFFFB;
      enableEvents(4L);
      return;
    }
    this.m_1 |= 0x4;
    disableEvents(4L);
  }

  public boolean getFocusRect() {
    return !((this.m_1 & 0x20) != 0);
  }

  public void setFocusRect(boolean paramBoolean) {
    this.m_1 |= 0x20;
    if (paramBoolean)
      this.m_1 &= 0xFFFFFFDF;
  }

  public boolean getFocusRectAroundText() {
    return !((this.m_2 & 0x1000000) == 0);
  }

  public void setFocusRectAroundText(boolean paramBoolean) {
    this.m_2 &= 0xFEFFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x1000000;
  }

  public boolean getAureole() {
    return !((this.m_3 & 0x4000) == 0);
  }

  public void setAureole(boolean paramBoolean) {
    this.m_3 &= 0xFFFFBFFF;
    if (paramBoolean)
      this.m_3 |= 0x4000;
    paint(null);
  }

  public boolean getSwitchColorsOnPress() {
    return !((this.m_1 & 0x100) == 0);
  }

  public void setSwitchColorsOnPress(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFEFF;
    if (paramBoolean)
      this.m_1 |= 0x100;
  }

  public boolean getSwitchShadowOnPress() {
    return !((this.m_1 & 0x200) == 0);
  }

  public void setSwitchShadowOnPress(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFDFF;
    if (paramBoolean)
      this.m_1 |= 0x200;
  }

  public boolean getExtraPoppedUp() {
    return !((this.m_1 & 0x40) == 0);
  }

  public void setExtraPoppedUp(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFFBF;
    if (paramBoolean)
      this.m_1 |= 0x40;
    paint(null);
  }

  public boolean getMultiLine() {
    return !((this.m_1 & 0x80) == 0);
  }

  public void setMultiLine(boolean paramBoolean) {
    this.m_1 &= 0xFFFFFF7F;
    if (paramBoolean)
      this.m_1 |= 0x80;
    paint(null);
  }

  public boolean getEnableEnter() {
    return !((this.m_1 & 0x1000) != 0);
  }

  public void setEnableEnter(boolean paramBoolean) {
    this.m_1 |= 0x1000;
    if (paramBoolean)
      this.m_1 &= 0xFFFFEFFF;
  }

  public boolean getEnableSpace() {
    return !((this.m_1 & 0x2000) != 0);
  }

  public void setEnableSpace(boolean paramBoolean) {
    this.m_1 |= 0x2000;
    if (paramBoolean)
      this.m_1 &= 0xFFFFDFFF;
  }

  public boolean getEnableEscape() {
    return !((this.m_1 & 0x4000) == 0);
  }

  public void setEnableEscape(boolean paramBoolean) {
    this.m_1 &= 0xFFFFBFFF;
    if (paramBoolean)
      this.m_1 |= 0x4000;
  }

  public boolean getEnableKeyboard() {
    return !((this.m_2 & 0x80) != 0);
  }

  public void setEnableKeyboard(boolean paramBoolean) {
    this.m_2 |= 0x80;
    if (paramBoolean)
      this.m_2 &= 0xFFFFFF7F;
  }

  public boolean getEnableMouse() {
    return !((this.m_2 & 0x8) != 0);
  }

  public void setEnableMouse(boolean paramBoolean) {
    this.m_2 |= 0x8;
    if (paramBoolean)
      this.m_2 &= 0xFFFFFFF7;
  }

  public void setEnableRightMouse(boolean paramBoolean) {
    this.m_2 &= 0xFFFFF7FF;
    if (paramBoolean)
      this.m_2 |= 0x800;
  }

  public boolean getEnableRightMouse() {
    return !((this.m_2 & 0x800) == 0);
  }

  public void setBorderColor(Color paramColor) {
    if (paramColor != getBorderColor()) {
      this.m_cBo = paramColor;
      paint(null);
    }
  }

  public Color getBorderColor() {
    return (this.m_cBo == null) ? getBackColor() : this.m_cBo;
  }

  public void setBackColor(Color paramColor) {
    setBackground(paramColor);
  }

  public Color getBackColor() {
    Color color = getBackground();
    return (color == null) ? Color.lightGray : color;
  }

  public void setBackground(Color paramColor) {
    super.setBackground(paramColor);
    paint(null);
  }

  public Color getMouseInsideForeground() {
    return (this.m_cMF == null) ? getForeground() : this.m_cMF;
  }

  public void setMouseInsideForeground(Color paramColor) {
    if (paramColor != getMouseInsideForeground())
      this.m_cMF = paramColor;
  }

  public Color getText3DColor() {
    return (this.m_c3D == null) ? getBackground() : this.m_c3D;
  }

  public void setText3DColor(Color paramColor) {
    this.m_c3D = paramColor;
    paint(null);
  }

  public Color getShadowColor() {
    return (this.m_cSh == null) ? Color.gray : this.m_cSh;
  }

  public void setShadowColor(Color paramColor) {
    this.m_cSh = paramColor;
    paint(null);
  }

  public void setEnabled(boolean paramBoolean) {
    if ((this.m_1 & 0x802) == 2048)
      this.m_1 |= 0x1;
    super.setEnabled(paramBoolean);
    paint(null);
  }

  public void setMinLeftEdgeOfText(int paramInt) {
    if (paramInt < -10)
      paramInt = -10;
    if (paramInt > 21)
      paramInt = 21;
    this.m_2 = this.m_2 & 0x7FFFFFF | paramInt + 10 << 27;
    paint(null);
  }

  public int getMinLeftEdgeOfText() {
    return (this.m_2 >>> 27) - 10;
  }

  public int getTextAlignment() {
    return 2 - (this.m_2 >> 18 & 0x3);
  }

  public void setTextAlignment(int paramInt) {
    this.m_2 = this.m_2 & 0xFFF3FFFF | 2 - (paramInt & 0x3) % 3 << 18;
    paint(null);
  }

  public boolean getStretchedImage() {
    return !((this.m_2 & 0x2000000) == 0);
  }

  public void setStretchedImage(boolean paramBoolean) {
    this.m_2 &= 0xFDFFFFFF;
    if (paramBoolean)
      this.m_2 |= 0x2000000;
  }

  public boolean setFileImageAt(String paramString, int paramInt) {
    Image image = null;
    paramInt &= 0x3;
    if (this.m_imFiles == null)
      this.m_imFiles = new Vector(paramInt + 1);
    while (paramInt >= this.m_imFiles.size())
      this.m_imFiles.addElement(null);
    this.m_imFiles.setElementAt(paramString, paramInt);
    if (paramString != null && (image = PVImage.file11(this, paramString)) == null)
      this.m_1 &= 0xF7FFFFFF;
    setImageAt(image, paramInt);
    return !(image == null);
  }

  public String getFileImageAt(int paramInt) {
    return (paramInt < 0 || this.m_imFiles == null) ? null : ((paramInt >= this.m_imFiles.size()) ? null : this.m_imFiles.elementAt(paramInt));
  }

  public int getImageAlignment() {
    return this.m_1 >> 16 & 0xF;
  }

  public void setImageAlignment(int paramInt) {
    this.m_1 = this.m_1 & 0xFFF0FFFF | (paramInt & 0x7) % 5 << 16;
    paint(null);
  }

  public int getImageEdge() {
    return this.m_2 >> 16 & 0x3;
  }

  public void setImageEdge(int paramInt) {
    this.m_2 = this.m_2 & 0xFFFCFFFF | (paramInt & 0x3) << 16;
    paint(null);
  }

  public void setImageAt(Image paramImage, int paramInt) {
    if (this.m_ims == null)
      this.m_ims = new Image[4];
    this.m_ims[paramInt & 0x3] = paramImage;
  }

  public Image getImageAt(int paramInt) {
    return (this.m_ims == null) ? null : this.m_ims[paramInt & 0x3];
  }

  public int getTriangleSize() {
    return this.m_4 >>> 26;
  }

  public void setTriangleSize(int paramInt) {
    this.m_4 = this.m_4 & 0x3FFFFFF | (paramInt & 0x3F) << 26;
  }

  public int getTriangle() {
    return this.m_1 >> 20 & 0x37;
  }

  public void setTriangle(int paramInt) {
    this.m_1 = this.m_1 & 0xFC8FFFFF | (paramInt & 0x37) << 20;
    paint(null);
  }

  public boolean isCheckBox() {
    return !((this.m_1 & 0x700000) != 7340032);
  }

  public void setCheckBox(boolean paramBoolean) {
    this.m_1 &= 0xFC80FFFF;
    if (paramBoolean) {
      this.m_1 |= 0x718480;
      this.m_2 = this.m_2 & 0xFDF0FFFF | 0x1040000;
      setBackground(null);
    }
    paint(null);
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  void exitMouse() {
    if ((this.m_1 & 0x24000000) == 0)
      return;
    this.m_1 &= 0xDBFFFFFF;
    if (!isAble())
      return;
    boolean bool = ((this.m_1 & 0x8402) != 1026 && (this.m_1 & 0x8408) != 33800) ? false : true;
    if (!bool)
      if ((this.m_1 & 0x40000000) != 0) {
        if (!(bool = ((this.m_1 & 0x3) != (this.m_1 >> 11 & 0x1)) ? false : true)) {
          this.m_1 = this.m_1 & 0xFFFFFFFC | this.m_1 >> 11 & 0x1;
          fireAction(4);
        }
      } else if (!(bool = ((this.m_1 & 0x801) == 2048) ? false : true)) {
        this.m_1 = this.m_1 & 0xFFFFFFFC | 0x1;
      }
    paint(null);
  }

  protected void processMouseMotionEvent(MouseEvent paramMouseEvent) {
    super.processMouseMotionEvent(paramMouseEvent);
    int i = paramMouseEvent.getX();
    int j = paramMouseEvent.getY();
    if (i < 0 || j < 0 || i > (getSize()).width || j > (getSize()).height) {
      exitMouse();
      return;
    }
    if (!isAble() || ((paramMouseEvent.getModifiers() & 0x1004) != 0 && !getEnableRightMouse()))
      return;
    if ((this.m_1 & 0x20000000) == 0) {
      this.m_1 |= 0x4000000;
    } else {
      return;
    }
    if ((this.m_1 & 0x34000000) == 67108864) {
      this.m_1 = this.m_1 & 0xFBFFFFFF | 0x20000000;
      boolean bool = ((this.m_1 & 0x402) != 1026) ? false : true;
      if (!bool)
        if (paramMouseEvent.getID() == 503 && paramMouseEvent.getModifiers() == 0) {
          if (bool = ((this.m_1 & 0x40000000) == 0) ? false : true) {
            this.m_1 &= 0xBFFFFFFF;
          } else if (!(bool = ((this.m_1 & 0x3) != 0) ? false : true)) {
            this.m_1 &= 0xFFFFFFFC;
          }
        } else {
          bool = ((this.m_1 & 0x40000000) != 0 && (this.m_1 & 0x3) != (((this.m_1 & 0x8400) == 1024) ? 0 : 2)) ? false : true;
          if (!bool) {
            this.m_1 = this.m_1 & 0xFFFFFFFC | (((this.m_1 & 0x8400) == 1024) ? 0 : 2);
            if (getPressed())
              fireAction(2);
          }
        }
      paint(null);
    }
  }

  protected void processMouseEvent(MouseEvent paramMouseEvent) {
    super.processMouseEvent(paramMouseEvent);
    if (paramMouseEvent.getID() == 505) {
      exitMouse();
      return;
    }
    if (paramMouseEvent.getID() == 504) {
      this.m_1 |= 0x4000000;
    } else {
      if ((paramMouseEvent.getModifiers() & 0x800) != 0 || ((paramMouseEvent.getModifiers() & 0x1004) != 0 && !getEnableRightMouse()) || !getEnableMouse() || !isAble() || (this.m_1 & 0x10000000) != 0)
        return;
      if (paramMouseEvent.getID() == 501) {
        this.m_3 &= 0xFFFCFFFF;
        if ((this.m_1 & 0x40000000) != 0)
          return;
        this.m_1 |= 0x40000000;
        if ((this.m_1 & 0x8402) == 1026) {
          this.m_1 &= 0xFFFFFFFC;
        } else {
          this.m_1 = this.m_1 & 0xFFFFFFFC | 0x2;
        }
        fireAction(((this.m_1 & 0x8400) == 1024) ? 1 : 2);
        if ((this.m_1 & 0x80000004) == 0) {
          requestFocus();
          return;
        }
      } else if (paramMouseEvent.getID() == 502) {
        this.m_1 &= 0xBFFFFFFF;
        if ((this.m_1 & 0x400) != 0) {
          if ((this.m_1 & 0x20008000) != 536903680)
            return;
          if ((this.m_1 & 0x8) != 0) {
            this.m_1 &= 0xFFFFFFF4;
          } else {
            this.m_1 |= 0xA;
          }
          fireAction(5 - (this.m_2 & 0x2));
        } else if (getPressed()) {
          this.m_1 &= 0xFFFFFFFC;
          fireAction(5);
        }
      } else {
        return;
      }
    }
    paint(null);
  }

  protected void processFocusEvent(FocusEvent paramFocusEvent) {
    super.processFocusEvent(paramFocusEvent);
    this.m_1 |= Integer.MIN_VALUE;
    if (paramFocusEvent.getID() == 1005) {
      this.m_1 &= 0x2FFFFFFF;
      if ((this.m_1 & 0x402) != 1026)
        this.m_1 &= 0xFFFFFFFD;
    }
    paint(null);
  }

  protected void processKeyEvent(KeyEvent paramKeyEvent) {
    super.processKeyEvent(paramKeyEvent);
    if (!getEnableKeyboard() || (this.m_1 & 0x40000000) != 0 || paramKeyEvent.isConsumed())
      return;
    int i = 0;
    int j = paramKeyEvent.getKeyCode();
    if (j == 10 && getEnableEnter())
      i = 17;
    if (j == 32 && getEnableSpace())
      i = 33;
    if (j == 27 && getEnableEscape())
      i = 65;
    if (j == 112 && (paramKeyEvent.getModifiers() & 0x1F) == 9)
      i = this.m_3 |= 0x10000;
    if (i == 0)
      return;
    if (paramKeyEvent.getID() == 401) {
      if ((this.m_1 & 0x10000000) == 0) {
        this.m_1 |= 0x10000000;
        setPressed(!((this.m_1 & 0x402) == 1026));
        fireAction(i);
        return;
      }
    } else if ((this.m_1 & 0x10000000) != 0 && paramKeyEvent.getID() == 402) {
      this.m_1 &= 0xEFFFFFFF;
      if ((this.m_1 & 0x400) == 0)
        setPressed(false);
    }
  }

  public void fireAction(int paramInt) {
    paramInt &= this.m_3 >> 17 & 0x6 | 0x71;
    if (this.m_aLs != null && (paramInt & 0x7) != 0)
      this.m_aLs.actionPerformed(new ActionEvent(this, 1001, this.m_act, paramInt & 0x77));
  }

  public void doClick() {
    fireAction(1);
  }

  protected Rectangle drawTextAndImage(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.m_1 >> 20 & 0x7;
    int j = paramInt1;
    int k = paramInt2;
    int m = 4;
    int n = getTriangleSize();
    if ((this.m_1 & 0x8000000) == 0) {
      while (m-- > 0) {
        String str1;
        if (getImageAt(m) == null && (str1 = getFileImageAt(m)) != null)
          setFileImageAt(str1, m);
      }
      this.m_1 |= 0x8000000;
    }
    Color color1 = getBackColor();
    if (!isEnabled()) {
      m = 3;
    } else if ((this.m_1 & 0x1) != 0) {
      m = 2;
    } else {
      m = getPressed() ? 1 : 0;
    }
    if (i == 7)
      if (m == 3) {
        m = getPressed() ? 3 : 2;
      } else {
        if (m == 2)
          m = 0;
        if ((this.m_1 & 0x60000000) != 1610612736)
          color1 = Color.white;
      }
    Image image = getImageAt(m);
    if (image == null)
      if (i > 0) {
        m = i | ((i == 7) ? m : ((m == 3) ? 8 : (m & 0x1 | (((this.m_1 & 0x1000000) != 0) ? 2 : 0) | ((m == 2 || (this.m_1 & 0x2000000) != 0) ? 4 : 0)))) << 4;
      } else {
        image = getImageAt(0);
      }
    Dimension dimension = null;
    int i1 = getImageEdge() << 1;
    int i2 = (paramInt3 > paramInt4) ? paramInt4 : paramInt3;
    while (i2 < 6 + i1 * 2 && i1 > 0)
      i1 -= 2;
    if (image != null) {
      dimension = new Dimension(image.getWidth(null), image.getHeight(null));
    } else if (i > 0) {
      if ((paramInt3 < (i & 0x1) * (n + i1) || paramInt4 < (i - 1 & 0x1) * (n + i1)) && i1 > 0)
        i1 -= 2;
      dimension = PVImage.center(null, null, null, 0, 0, paramInt3 - i1, paramInt4 - i1, n, m);
    }
    if (dimension != null) {
      i = getImageAlignment();
      if (getStretchedImage()) {
        dimension.width = paramInt3 - i1;
        dimension.height = paramInt4 - i1;
        n = 1000;
        i = -1;
      }
      this.m_2 |= 0x4000000;
      Dimension dimension1 = getTextDimension();
      this.m_2 &= 0xFBFFFFFF;
      j += (paramInt3 - dimension.width) / 2;
      k += (paramInt4 - dimension.height) / 2;
      i1 >>= 1;
      if (getText().length() != 0)
        switch (i) {
          case 0:
            j = paramInt1 + paramInt3 - dimension.width - i1;
            paramInt3 -= dimension.width + i1;
            break;
          case 1:
            j = paramInt1 += i1;
            paramInt1 += dimension.width;
            paramInt3 -= dimension.width + i1;
            break;
          case 2:
            k = paramInt2 + paramInt4 - dimension.height - i1;
            if (paramInt4 > dimension1.height + dimension.height + i1) {
              paramInt4 -= dimension.height + i1;
              break;
            }
            paramInt4 = dimension1.height;
            break;
          case 3:
            k = paramInt2 + i1;
            paramInt2 += paramInt4;
            if (paramInt4 > dimension1.height + dimension.height + i1) {
              paramInt4 -= dimension.height + i1;
            } else {
              paramInt4 = dimension1.height;
            }
            paramInt2 -= paramInt4;
            break;
        }
      if (i >= 0)
        if (isCheckBox()) {
          paramInt2--;
        } else {
          j += i2 = (this.m_1 & 0x3) - 1;
          k += i2;
          if ((this.m_1 & 0x43) == 0) {
            j++;
            k++;
          }
        }
      if (image != null) {
        paramGraphics.drawImage(image, j, k, dimension.width, dimension.height, null);
      } else {
        PVImage.center(paramGraphics, color1, null, j, k, dimension.width, dimension.height, n, m);
      }
    }
    Rectangle rectangle = new Rectangle(1000, 1000, 1, 1);
    paramInt1++;
    String str = "";
    paramGraphics.setFont(getFont());
    FontMetrics fontMetrics = paramGraphics.getFontMetrics();
    i1 = (this.m_1 & 0x3) - 1;
    int i3 = 0;
    int i4 = fontMetrics.getAscent();
    if ((this.m_1 & 0x43) == 0)
      i1++;
    if (isCheckBox()) {
      i1 = 0;
      paramInt2++;
    }
    paramInt2 += i1;
    Color color2 = getText3DColor();
    Color color3 = ((this.m_1 & 0x22000000) == 0) ? getForeground() : getMouseInsideForeground();
    if (!isEnabled()) {
      color2 = getBackColor().brighter();
      color3 = getBackColor().darker();
    } else if ((this.m_1 & 0x102) == 258) {
      color1 = color2;
      color2 = color3;
      color3 = color1;
    }
    int i5 = this.m_3 & 0x30000;
    long l = (i5 == 131072) ? 34555749885939838L : 16123002742581525L;
    if (i5 != 0) {
      while (i3 < 11)
        str = String.valueOf(str) + (char)(int)(65L + (l >> i3++ * 5 & 0x1FL));
    } else {
      str = (getPressed() && this.m_textP != null) ? this.m_textP : this.m_text;
    }
    Vector vector = new Vector();
    int i6 = fontMetrics.getHeight();
    if (!getMultiLine()) {
      vector.addElement(str);
    } else {
      lines(vector, str, fontMetrics, paramInt3, paramInt4, i6, i4);
    }
    i3 = (paramInt4 - vector.size() * i6) / 2;
    if (i3 > 0 || vector.size() < 2)
      paramInt2 += i3;
    rectangle.y = paramInt2;
    rectangle.height = i6 * vector.size();
    paramInt2 += i4 * 15 >>> 4;
    i3 = getHorizontal3D();
    i4 = getVertical3D();
    if (!isEnabled()) {
      i3 = i4 = 1;
    } else if ((this.m_1 & 0x41) == 1) {
      i3 = i4 = 0;
    }
    m = 0;
    while (m < vector.size()) {
      n = paramInt2 + m * i6;
      if (n <= paramInt2 + paramInt4) {
        str = vector.elementAt(m++);
        i5 = fontMetrics.stringWidth(str);
        if (rectangle.width < i5)
          rectangle.width = i5;
        switch (getTextAlignment()) {
          case 0:
            i5 = paramInt3 - i5 - 4;
            break;
          case 1:
            i5 = 2;
            break;
          default:
            i5 = (paramInt3 - i5 - 4) / 2;
            break;
        }
        i5 += paramInt1;
        if (i5 < (i2 = getMinLeftEdgeOfText() + paramInt1))
          i5 = i2;
        i5 += i1;
        if (rectangle.x > i5)
          rectangle.x = i5;
        paramGraphics.setColor(color2);
        if (getAureole()) {
          for (i3 = -1; i3 < 2; i3++) {
            for (i4 = -1; i4 < 2; i4++)
              paramGraphics.drawString(str, i5 + i3, n + i4);
          }
        } else {
          i2 = i5;
          i = n;
          if (i3 < 0) {
            i5 -= i3;
          } else {
            i2 += i3;
          }
          if (i4 < 0) {
            n -= i4;
          } else {
            i += i4;
          }
          paramGraphics.drawString(str, i2, i);
        }
        paramGraphics.setColor(color3);
        paramGraphics.drawString(str, i5, n);
        continue;
      }
      break;
    }
    rectangle.grow(getAureole() ? 4 : 3, 1);
    return rectangle;
  }

  void lines(Vector paramVector, String paramString, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    byte b1 = 0;
    byte b2 = 0;
    byte b3 = 0;
    int i = paramString.length();
    while (b1 < i) {
      if (++b1 < i && paramFontMetrics.stringWidth(paramString.substring(0, b1)) + 2 < paramInt1) {
        if (paramString.charAt(b1) == ' ')
          b2 = b1;
        if (paramString.charAt(b1) < ' ') {
          b2 = b1;
        } else {
          continue;
        }
      }
      paramInt4 += paramInt3;
      if (b2 > 0 && b1 == i && paramInt4 < paramInt2 && paramFontMetrics.stringWidth(paramString.substring(0, b1)) >= paramInt1)
        b1 = b2;
      b3 = b1;
      if (b2 == 0 || b1 == i) {
        b2 = b3;
        if (b1 < i)
          b2--;
      } else {
        b1 = b2;
      }
      while (b1 < i && paramString.charAt(b1) > ' ')
        b1++;
      while (b1 < i && paramString.charAt(b1) <= ' ')
        b1++;
      if (paramInt4 > paramInt2 || b1 == i) {
        if (paramFontMetrics.stringWidth(paramString.substring(0, b3)) >= paramInt1 || b1 < i) {
          if (b3 < 4)
            b3 = 4;
          paramString = String.valueOf(paramString.substring(0, b3 - 3)) + "...";
          b2 = b3;
        }
        i = b1;
      }
      paramVector.addElement(paramString.substring(0, b2));
      if (b1 != i) {
        paramString = paramString.substring(b1);
        i = paramString.length();
        b2 = b1 = 0;
        continue;
      }
      break;
    }
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_prefer = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension1 = (this.m_prefer == null) ? getSize() : new Dimension(this.m_prefer);
    if (dimension1.width < 2)
      dimension1.width = 70;
    if (dimension1.height < 2)
      dimension1.height = 23;
    if (!getAutoResize())
      return dimension1;
    Rectangle rectangle = new Rectangle(dimension1);
    PVBorder.shadow(null, rectangle, null, null, getShadow(), getShadowWidth());
    PVBorder.draw(null, rectangle, this.m_iBodr, null);
    Dimension dimension2 = getTextDimension();
    dimension2.width += dimension1.width - rectangle.width;
    dimension2.height += dimension1.height - rectangle.height;
    return dimension2;
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

  protected Dimension getTextDimension() {
    FontMetrics fontMetrics = getFontMetrics(getFont());
    int i = fontMetrics.getHeight() + 2;
    Dimension dimension1 = null;
    Dimension dimension2 = new Dimension(i, i);
    String str = (getPressed() && this.m_textP != null) ? this.m_textP : this.m_text;
    if (str.length() > 0) {
      dimension2.width = fontMetrics.stringWidth(str) + 8;
      if (getAureole()) {
        dimension2.width += 2;
        dimension2.height += 2;
      } else {
        dimension2.width += ((i = getHorizontal3D()) > 0) ? i : -i;
        dimension2.height += ((i = getVertical3D()) > 0) ? i : -i;
      }
    }
    if ((this.m_2 & 0x6000000) == 0) {
      if ((i = this.m_1 >> 20 & 0x7) > 0) {
        int j = dimension2.height + 1 + getImageEdge() * 2;
        dimension1 = PVImage.center(null, null, null, 0, 0, j, j, getTriangleSize(), i);
      }
      if (dimension1 == null) {
        Image image = getImageAt(0);
        if (image == null)
          return dimension2;
        dimension1 = new Dimension(image.getWidth(null), image.getHeight(null));
      }
      if (getImageAlignment() < 2) {
        dimension2.width += dimension1.width;
      } else if (getImageAlignment() < 4) {
        dimension2.height += dimension1.height;
      }
      if (dimension2.width < dimension1.width)
        dimension2.width = dimension1.width;
      if (dimension2.height < dimension1.height)
        dimension2.height = dimension1.height;
    }
    return dimension2;
  }

  public void paint0(Graphics paramGraphics) {
    int i = (getSize()).width;
    int j = (getSize()).height;
    if (i > 1 && j > 1 && paramGraphics != null) {
      Rectangle rectangle = new Rectangle(0, 0, i, j);
      boolean bool = (this.m_iBodr == 0 || isCheckBox()) ? false : true;
      int k = getShadow();
      if (getShadowWidth() == 0)
        k = 0;
      if (k > 0) {
        if ((this.m_1 & 0x202) == 514)
          k += 2;
        if (k > 4)
          k -= 4;
        PVBorder.shadow(null, rectangle, null, null, k, getShadowWidth());
      }
      if (rectangle.width > 1 && rectangle.height > 1) {
        paramGraphics.setColor(getBackColor());
        paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if (bool)
          PVBorder.draw(null, rectangle, this.m_iBodr, null);
        if (rectangle.width != (this.m_3 & 0xFFF) || rectangle.height != this.m_3 >>> 20)
          this.m_3 = this.m_3 & 0xFF000 | rectangle.width | rectangle.height << 20;
        foc0(paramGraphics, drawTextAndImage(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height), rectangle);
      }
      rectangle.x = rectangle.y = 0;
      rectangle.width = i;
      rectangle.height = j;
      PVBorder.shadow(paramGraphics, rectangle, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), k, getShadowWidth());
      if (bool && (this.m_1 & 0x801) != 2049)
        PVBorder.draw(paramGraphics, rectangle, this.m_iBodr, getBorderColor(), !((this.m_1 & 0x2) == 0));
    }
  }

  void foc0(Graphics paramGraphics, Rectangle paramRectangle1, Rectangle paramRectangle2) {
    if ((this.m_1 & 0x80000020) == Integer.MIN_VALUE) {
      if (getFocusRectAroundText())
        paramRectangle2 = paramRectangle1;
      paramGraphics.setColor(getForeground());
      PVBorder.focus(paramGraphics, paramRectangle2.x + 1, paramRectangle2.y + 1, paramRectangle2.width - 3, paramRectangle2.height - 3);
    }
  }
}
