package pv.awt;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import pv.util.PVBorder;

public class PVCalculator extends Container implements ActionListener, KeyListener, TextListener {
  public static final int FORMAT = 1;

  public static final int BASIC = 2;

  public static final int INT = 4;

  public static final int POWER = 8;

  public static final int SIN = 16;

  public static final int OK_CANCEL = 32;

  public static final int CALCULATION_EVENT = 256;

  public static final int ALL_BUTTONS_EVENT = 512;

  public static final int INVALIDATE_EVENT = 1024;

  public static final int PAINT_EVENT = 1025;

  protected Dimension m_prefer;

  protected Dimension m_minS;

  protected Dimension m_maxS;

  public String m_act;

  public double m_random = 100.0D;

  Color m_cSh = Color.gray;

  int m_iBodr = 13;

  transient double m_d1;

  transient double m_d2;

  transient long m_i1;

  transient long m_i2;

  int m_0 = -2146107133;

  int m_1 = 8726;

  transient int m_w;

  transient int m_h;

  protected Vector m_list;

  public PVNumeric m_field;

  public PVNumeric m_memory;

  protected transient ActionListener m_aLs;

  public PVCalculator() {
    setFont(new Font("SansSerif", 1, 12));
    setBackground(Color.lightGray);
    setForeground(Color.black);
    init0();
  }

  public void paint(Graphics paramGraphics) {
    if ((this.m_1 & 0x40000000) == 0)
      doLayout();
    Rectangle rectangle = client();
    paramGraphics.setColor(getBackground());
    paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    super.paint(paramGraphics);
    rectangle = new Rectangle(getSize());
    PVBorder.shadow(paramGraphics, rectangle, getShadowColor(), (getParent() == null) ? Color.lightGray : getParent().getBackground(), getShadow(), getShadowWidth());
    PVBorder.draw(paramGraphics, rectangle, this.m_iBodr, getBackground());
    if ((this.m_0 & 0x400) != 0)
      fireAction(1025);
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

  public void setPreferredSize(Dimension paramDimension) {
    this.m_prefer = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension = new Dimension((this.m_prefer == null) ? getSize() : this.m_prefer);
    if (dimension.height < 2)
      dimension.height = 8 + 22 * (8 + (this.m_0 & 0x1) + (this.m_0 >> 5 & 0x1));
    if (dimension.width < 2) {
      byte b = 4;
      byte b1 = -1;
      while (++b1 < 4) {
        if ((this.m_0 & 2 << b1) != 0)
          b++;
      }
      dimension.width = 8 + 40 * b;
    }
    return dimension;
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    super.processEvent(paramAWTEvent);
    if (paramAWTEvent instanceof MouseEvent)
      switch (paramAWTEvent.getID()) {
        case 502:
          if ((((MouseEvent)paramAWTEvent).getModifiers() & 0x1804) == 0) {
            requestFocus();
            return;
          }
          break;
        case 505:
          this.m_1 &= Integer.MAX_VALUE;
          return;
        default:
          this.m_1 |= Integer.MIN_VALUE;
          return;
        case 506:
          break;
      }
  }

  public boolean mouseIn() {
    if ((this.m_1 & Integer.MIN_VALUE) != 0 || (this.m_field.m_1 & 0x4000000) != 0 || (this.m_memory.m_1 & 0x4000000) != 0)
      return true;
    for (byte b = 0; b < 50; b++) {
      if (((button(b)).m_1 & 0x20000000) != 0)
        return true;
    }
    return false;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
    paramObjectOutputStream.defaultWriteObject();
    if (this.m_aLs instanceof java.io.Serializable) {
      paramObjectOutputStream.writeObject("aLs");
      paramObjectOutputStream.writeObject(this.m_aLs);
    }
    paramObjectOutputStream.writeObject(null);
  }

  private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
    paramObjectInputStream.defaultReadObject();
    if ((this.m_0 & Integer.MIN_VALUE) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0();
  }

  void init0() {
    enableEvents(48L);
    this.m_1 &= 0xFFFF;
    setLayout(null);
    init();
    setFormat(getFormat());
    doLayout();
  }

  void init() {
    byte b = -1;
    if (this.m_field == null)
      while (++b < 2) {
        PVNumeric pVNumeric = new PVNumeric();
        pVNumeric.m_1 = pVNumeric.m_1 & 0xFFFFFFBF | 0x12024;
        pVNumeric.m_3 = 537919512;
        pVNumeric.setMaxDecimals(14);
        add(pVNumeric);
        pVNumeric.addKeyListener(this);
        if (b == 0) {
          pVNumeric.addTextListener(this);
          this.m_field = pVNumeric;
          continue;
        }
        pVNumeric.setBackground(new Color(14737632));
        this.m_memory = pVNumeric;
      }
    if (this.m_list == null) {
      this.m_list = new Vector(50);
      b = -1;
      while (++b < 50) {
        String str = null;
        switch (b) {
          case 0:
            str = "int";
            break;
          case 1:
            str = "float";
            break;
          case 2:
            str = "sci";
            break;
          case 4:
            str = "MS";
            break;
          case 5:
            str = "MR";
            break;
          case 6:
            str = "M+";
            break;
          case 7:
            str = "M-";
            break;
          case 8:
            str = "Bk";
            break;
          case 9:
            str = "7";
            break;
          case 10:
            str = "4";
            break;
          case 11:
            str = "1";
            break;
          case 12:
            str = "0";
            break;
          case 13:
            str = "CE";
            break;
          case 14:
            str = "8";
            break;
          case 15:
            str = "5";
            break;
          case 16:
            str = "2";
            break;
          case 17:
            str = "+/-";
            break;
          case 18:
            str = "C";
            break;
          case 19:
            str = "9";
            break;
          case 20:
            str = "6";
            break;
          case 21:
            str = "3";
            break;
          case 22:
            str = ".";
            break;
          case 23:
            str = "/";
            break;
          case 24:
            str = "*";
            break;
          case 25:
            str = "-";
            break;
          case 26:
            str = "+";
            break;
          case 27:
            str = "=";
            break;
          case 28:
            str = "x^2";
            break;
          case 29:
            str = "x^3";
            break;
          case 30:
            str = "Sqrt";
            break;
          case 31:
            str = "1/x";
            break;
          case 32:
            str = "n!";
            break;
          case 33:
            str = "Mod";
            break;
          case 34:
            str = "Or";
            break;
          case 35:
            str = "And";
            break;
          case 36:
            str = "Xor";
            break;
          case 37:
            str = "Not";
            break;
          case 38:
            str = "x^y";
            break;
          case 39:
            str = "Log";
            break;
          case 40:
            str = "Exp";
            break;
          case 41:
            str = "<?>";
            break;
          case 42:
            str = "e";
            break;
          case 43:
            str = "Sin";
            break;
          case 44:
            str = "Cos";
            break;
          case 45:
            str = "Tan";
            break;
          case 46:
            str = "Atn";
            break;
          case 47:
            str = "pi";
            break;
          case 48:
            str = "OK";
            break;
          case 49:
            str = "Cancel";
            break;
        }
        PVImageButton pVImageButton = new PVImageButton(str);
        pVImageButton.setFont(null);
        if (b < 3) {
          pVImageButton.setCheckBox(true);
        } else if (b < 6) {
          pVImageButton.setTriangleSize(11);
        }
        pVImageButton.setCanGetFocus(false);
        pVImageButton.setFocusRect(false);
        pVImageButton.setBackground(null);
        pVImageButton.setForeground(null);
        if (b == 48)
          pVImageButton.setBorderStyle(9994377);
        if (b != 3)
          add(pVImageButton);
        pVImageButton.addActionListener(this);
        this.m_list.addElement(pVImageButton);
      }
      setTriangles(true);
      setDigitForeground(new Color(192));
      setOperationForeground(new Color(10485760));
    }
  }

  public void invalidate() {
    Rectangle rectangle = client();
    if (rectangle.width > 10 && (rectangle.width != this.m_w || rectangle.height != this.m_h)) {
      doLayout();
      if ((this.m_0 & 0x400) != 0)
        fireAction(1024);
    }
  }

  public void doLayout() {
    if (this.m_list == null)
      return;
    int i = -1;
    byte b = 4;
    int j = 8 + (this.m_0 & 0x1) + (this.m_0 >> 5 & 0x1);
    while (++i < 4) {
      if ((this.m_0 & 2 << i) != 0)
        b++;
    }
    Rectangle rectangle = client();
    this.m_w = rectangle.width;
    this.m_h = rectangle.height;
    int k = getMargins();
    if (k * 20 + 100 > rectangle.width && (k = (rectangle.width - 100) / 20) < 0)
      k = 0;
    rectangle.grow(-k, -k);
    int m = rectangle.width / b;
    int n = rectangle.height / j;
    if (m < 4 || n < 4)
      return;
    i = getSpacing();
    int i1 = (n < 7) ? 0 : i;
    if (i1 + i1 > n)
      i1 = n / 2;
    int i2 = (m < 7) ? 0 : i;
    if (i2 + i2 > m)
      i2 = m / 2;
    m = (rectangle.width + i2) / b;
    int i3 = m * b - i2;
    int i4 = getMaxHeight();
    if (rectangle.width > i3 + 1)
      rectangle.x += (rectangle.width - i3) / 2;
    rectangle.width = i3;
    if (n > i1 + i4)
      i1 = n - i4;
    n = (rectangle.height + i1) / j;
    i4 = n - i1;
    i3 = m - i2;
    int i5 = rectangle.x + rectangle.width;
    rectangle.height += rectangle.y;
    if ((k = i4) < 18)
      k = 18 - ((i1 > 1) ? 1 : 0);
    if (k < 13 || k > n)
      k = n;
    this.m_field.setBounds(rectangle.x, rectangle.y, rectangle.width, k);
    rectangle.y += n;
    i = rectangle.width / 4;
    button(4).setBounds(rectangle.x, rectangle.y, i - i2, i4);
    button(5).setBounds(rectangle.x + i, rectangle.y, i - i2, i4);
    button(6).setBounds(i5 - i - i + i2, rectangle.y, i - i2, i4);
    button(7).setBounds(i5 - i + i2, rectangle.y, i - i2, i4);
    rectangle.y += n;
    this.m_memory.setBounds(rectangle.x, rectangle.y - k + i4, rectangle.width, k);
    rectangle.y += n;
    i = rectangle.width / 3;
    boolean bool = !((this.m_0 & 0x1) == 0);
    if (bool) {
      button(0).setBounds(rectangle.x, rectangle.y, i - 1, i4);
      button(1).setBounds(rectangle.x + i, rectangle.y, rectangle.width - i - i, i4);
      button(2).setBounds(i5 - i + 1, rectangle.y, i - 1, i4);
      rectangle.y += n;
    }
    if (button(0).isVisible() != bool)
      for (i = 0; i < 3; i++)
        button(i).setVisible(bool);
    k = 0;
    int i6 = rectangle.x;
    bool = true;
    for (i = 8; i < 48; i++) {
      if (i > 8 && (i - 8) % 5 == 0) {
        if (i > 27)
          bool = !((this.m_0 & 2 << k++) == 0);
        if (bool)
          i6 += m;
      }
      button(i).setBounds(i6, rectangle.y + (i - 8) % 5 * n, i3, i4);
      if (button(i).isVisible() != bool)
        button(i).setVisible(bool);
    }
    if (bool = !((this.m_0 & 0x20) == 0)) {
      rectangle.y += n * 5;
      k = rectangle.height - i4 - 1;
      if (k > rectangle.y) {
        k = rectangle.height - (i4 += (k - rectangle.y) / 2) - 1;
      } else {
        k = rectangle.y;
      }
      i3 = (rectangle.width - i2) / 2;
      if (i3 > 70)
        i3 -= (i3 - 70) / 2;
      if ((i = rectangle.width - i3 + i3 + i2) > 0) {
        if (i > i2 + i2) {
          i2 = i /= 3;
        } else {
          i /= 2;
        }
        rectangle.x += i;
      }
      button(48).setBounds(rectangle.x, k, i3, i4);
      button(49).setBounds(rectangle.x + i3 + i2, k, i3, i4);
    }
    if (button(48).isVisible() != bool)
      for (i = 48; i < 50; i++)
        button(i).setVisible(bool);
    this.m_1 |= 0x40000000;
  }

  public Rectangle client() {
    Rectangle rectangle = new Rectangle(getSize());
    PVBorder.shadow(null, rectangle, null, null, getShadow(), getShadowWidth());
    PVBorder.draw(null, rectangle, this.m_iBodr, null);
    return rectangle;
  }

  public void setFormat(int paramInt) {
    paramInt = (paramInt & 0x3) % 3;
    this.m_0 = this.m_0 & 0xFFCFFFFF | paramInt << 20;
    byte b = -1;
    PVNumeric pVNumeric = this.m_field;
    while (++b < 3) {
      button(b).setPressed(!(b != paramInt));
      if (b < 2) {
        pVNumeric.setFreeEntry(!(paramInt != 2));
        pVNumeric.setEnableFloatPoint(!(paramInt == 0));
        pVNumeric.onLostFocus();
        pVNumeric = this.m_memory;
      }
    }
  }

  public int getFormat() {
    return this.m_0 >> 20 & 0x3;
  }

  public void setActionMask(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFF0FF | paramInt & 0xF00;
  }

  public int getActionMask() {
    return this.m_0 & 0xF00;
  }

  public void keyReleased(KeyEvent paramKeyEvent) {
    key(paramKeyEvent);
  }

  public void keyTyped(KeyEvent paramKeyEvent) {
    key(paramKeyEvent);
  }

  public void keyPressed(KeyEvent paramKeyEvent) {
    key(paramKeyEvent);
  }

  public void textValueChanged(TextEvent paramTextEvent) {
    this.m_1 |= 0x400000;
  }

  protected void key(KeyEvent paramKeyEvent) {
    // Note: The byte code below is all the decompiler provided.
    // The actual code is a reconstruction by Gemini.

    //   0: aload_0
    //   1: getfield m_1 : I
    //   4: bipush #16
    //   6: ishr
    //   7: bipush #63
    //   9: iand
    //   10: istore_2
    //   11: aload_1
    //   12: invokevirtual getKeyChar : ()C
    //   15: invokestatic toUpperCase : (C)C
    //   18: istore_3
    //   19: aload_1
    //   20: invokevirtual getID : ()I
    //   23: sipush #402
    //   26: if_icmpne -> 33
    //   29: iconst_0
    //   30: goto -> 34
    //   33: iconst_1
    //   34: istore #4
    //   36: iload #4
    //   38: ifeq -> 472
    //   41: iload_2
    //   42: ifle -> 50
    //   45: iconst_m1
    //   46: istore_2
    //   47: goto -> 483
    //   50: iload_3
    //   51: lookupswitch default -> 438, 8 -> 284, 10 -> 396, 26 -> 267, 27 -> 414, 32 -> 312, 33 -> 360, 37 -> 366, 38 -> 378, 42 -> 336, 43 -> 348, 44 -> 324, 45 -> 342, 46 -> 324, 47 -> 330, 48 -> 432, 61 -> 354, 63 -> 390, 77 -> 300, 80 -> 306, 82 -> 295, 83 -> 290, 90 -> 260, 94 -> 384, 124 -> 372, 126 -> 318
    //   260: aload_1
    //   261: invokevirtual isControlDown : ()Z
    //   264: ifeq -> 483
    //   267: aload_1
    //   268: invokevirtual getID : ()I
    //   271: sipush #401
    //   274: if_icmpne -> 483
    //   277: aload_0
    //   278: invokevirtual undo : ()V
    //   281: goto -> 483
    //   284: bipush #8
    //   286: istore_2
    //   287: goto -> 483
    //   290: iconst_4
    //   291: istore_2
    //   292: goto -> 483
    //   295: iconst_5
    //   296: istore_2
    //   297: goto -> 483
    //   300: bipush #6
    //   302: istore_2
    //   303: goto -> 483
    //   306: bipush #47
    //   308: istore_2
    //   309: goto -> 483
    //   312: bipush #13
    //   314: istore_2
    //   315: goto -> 483
    //   318: bipush #17
    //   320: istore_2
    //   321: goto -> 483
    //   324: bipush #22
    //   326: istore_2
    //   327: goto -> 483
    //   330: bipush #23
    //   332: istore_2
    //   333: goto -> 483
    //   336: bipush #24
    //   338: istore_2
    //   339: goto -> 483
    //   342: bipush #25
    //   344: istore_2
    //   345: goto -> 483
    //   348: bipush #26
    //   350: istore_2
    //   351: goto -> 483
    //   354: bipush #27
    //   356: istore_2
    //   357: goto -> 483
    //   360: bipush #32
    //   362: istore_2
    //   363: goto -> 483
    //   366: bipush #33
    //   368: istore_2
    //   369: goto -> 483
    //   372: bipush #34
    //   374: istore_2
    //   375: goto -> 483
    //   378: bipush #35
    //   380: istore_2
    //   381: goto -> 483
    //   384: bipush #36
    //   386: istore_2
    //   387: goto -> 483
    //   390: bipush #41
    //   392: istore_2
    //   393: goto -> 483
    //   396: aload_0
    //   397: invokevirtual getEnterAsOK : ()Z
    //   400: ifeq -> 408
    //   403: bipush #48
    //   405: goto -> 410
    //   408: bipush #27
    //   410: istore_2
    //   411: goto -> 483
    //   414: aload_0
    //   415: invokevirtual getEnterAsOK : ()Z
    //   418: ifeq -> 426
    //   421: bipush #49
    //   423: goto -> 428
    //   426: bipush #18
    //   428: istore_2
    //   429: goto -> 483
    //   432: bipush #12
    //   434: istore_2
    //   435: goto -> 483
    //   438: iload_3
    //   439: bipush #49
    //   441: if_icmplt -> 483
    //   444: iload_3
    //   445: bipush #57
    //   447: if_icmpgt -> 483
    //   450: bipush #11
    //   452: iconst_5
    //   453: iload_3
    //   454: bipush #49
    //   456: isub
    //   457: iconst_3
    //   458: irem
    //   459: imul
    //   460: iadd
    //   461: iload_3
    //   462: bipush #49
    //   464: isub
    //   465: iconst_3
    //   466: idiv
    //   467: isub
    //   468: istore_2
    //   469: goto -> 483
    //   472: aload_0
    //   473: dup
    //   474: getfield m_1 : I
    //   477: ldc -4128769
    //   479: iand
    //   480: putfield m_1 : I
    //   483: iload_2
    //   484: ifeq -> 497
    //   487: aload_1
    //   488: iconst_0
    //   489: invokevirtual setKeyCode : (I)V
    //   492: aload_1
    //   493: iconst_0
    //   494: invokevirtual setKeyChar : (C)V
    //   497: iload_2
    //   498: ifle -> 537
    //   501: aload_0
    //   502: iload_2
    //   503: invokevirtual button : (I)Lpv/awt/PVImageButton;
    //   506: iload #4
    //   508: invokevirtual setPressed : (Z)V
    //   511: iload #4
    //   513: ifeq -> 537
    //   516: aload_0
    //   517: aload_0
    //   518: getfield m_1 : I
    //   521: ldc -4128769
    //   523: iand
    //   524: iload_2
    //   525: bipush #16
    //   527: ishl
    //   528: ior
    //   529: putfield m_1 : I
    //   532: aload_0
    //   533: iload_2
    //   534: invokevirtual action : (I)V
    //   537: return

    // 0: aload_0
    // 1: getfield m_1 : I
    // 4: bipush #16
    // 6: ishr
    // 7: bipush #63
    // 9: iand
    // 10: istore_2
    // Extract a "code" from m_1 by right-shifting 16 bits and then masking with 0x3F (63).
    // This suggests m_1 holds various flags or states, and bits 16-21 (6 bits) represent this code.
    int extractedCode = (this.m_1 >>> 16) & 63;

    // 11: aload_1
    // 12: invokevirtual getKeyChar : ()C
    // 15: invokestatic toUpperCase : (C)C
    // 18: istore_3
    // Get the character of the key pressed and convert it to uppercase.
    char keyChar = Character.toUpperCase(paramKeyEvent.getKeyChar());

    // 19: aload_1
    // 20: invokevirtual getID : ()I
    // 23: sipush #402
    // 26: if_icmpne -> 33
    // 29: iconst_0
    // 30: goto -> 34
    // 33: iconst_1
    // 34: istore #4
    // Determine if it's a KEY_RELEASED event (ID 402) or not.
    // `isPressedEvent` is true if it's NOT KEY_RELEASED (i.e., KEY_PRESSED).
    boolean isPressedEvent = (paramKeyEvent.getID() != KeyEvent.KEY_RELEASED);

    // 36: iload #4
    // 38: ifeq -> 472
    // If it's a KEY_PRESSED event (isPressedEvent is true):
    if (isPressedEvent) {
      // 41: iload_2
      // 42: ifle -> 50
      // 45: iconst_m1
      // 46: istore_2
      // If extractedCode is positive, set it to -1.
      // This suggests that a previous key press might still be "active" or registered.
      if (extractedCode > 0) {
        extractedCode = -1;
      } else {
        // 50: iload_3
        // 51: lookupswitch default -> 438, ...
        // Use a switch statement on the uppercase `keyChar`.
        switch (keyChar) {
          case 8: // Backspace
            // 284: bipush #8
            // 286: istore_2
            extractedCode = 8;
            break;
          case 10: // Enter (Line Feed)
            // 396: aload_0
            // 397: invokevirtual getEnterAsOK : ()Z
            // 400: ifeq -> 408
            // 403: bipush #48
            // 405: goto -> 410
            // 408: bipush #27
            // 410: istore_2
            // If getEnterAsOK() is true, map Enter to 48, else map to 27.
            extractedCode = getEnterAsOK() ? 48 : 27;
            break;
          case 26: // Ctrl+Z (ASCII SUB - Substitute character)
            // 267: aload_1
            // 268: invokevirtual getID : ()I
            // 271: sipush #401
            // 274: if_icmpne -> 483
            // If it's a KEY_PRESSED event (ID 401, which is KeyEvent.KEY_PRESSED)
            if (paramKeyEvent.getID() == KeyEvent.KEY_PRESSED) {
              // 260: aload_1
              // 261: invokevirtual isControlDown : ()Z
              // 264: ifeq -> 483
              // And Control key is down
              if (paramKeyEvent.isControlDown()) {
                // 277: aload_0
                // 278: invokevirtual undo : ()V
                // Call the undo method and skip further processing of this key.
                undo();
                return; // This leads to 483 directly in bytecode by skipping further assignment to extractedCode
              }
            }
            // If not Ctrl+Z or not KEY_PRESSED, then extractedCode remains its default/previous value,
            // effectively skipping the main action block for this specific case.
            break;
          case 27: // Escape
            // 414: aload_0
            // 415: invokevirtual getEnterAsOK : ()Z
            // 418: ifeq -> 426
            // 421: bipush #49
            // 423: goto -> 428
            // 426: bipush #18
            // 428: istore_2
            // If getEnterAsOK() is true, map Escape to 49, else map to 18.
            extractedCode = getEnterAsOK() ? 49 : 18;
            break;
          case 32: // Spacebar
            // 312: bipush #13
            // 314: istore_2
            extractedCode = 13;
            break;
          case 33: // !
            // 360: bipush #32
            // 362: istore_2
            extractedCode = 32;
            break;
          case 37: // %
            // 366: bipush #33
            // 368: istore_2
            extractedCode = 33;
            break;
          case 38: // &
            // 378: bipush #35
            // 380: istore_2
            extractedCode = 35;
            break;
          case 42: // *
            // 336: bipush #24
            // 338: istore_2
            extractedCode = 24;
            break;
          case 43: // +
            // 348: bipush #26
            // 350: istore_2
            extractedCode = 26;
            break;
          case 44: // ,
          case 46: // .
            // 324: bipush #22
            // 326: istore_2
            extractedCode = 22;
            break;
          case 45: // -
            // 342: bipush #25
            // 344: istore_2
            extractedCode = 25;
            break;
          case 47: // /
            // 330: bipush #23
            // 332: istore_2
            extractedCode = 23;
            break;
          case 48: // 0
            // 432: bipush #12
            // 434: istore_2
            extractedCode = 12;
            break;
          case 61: // =
            // 354: bipush #27
            // 356: istore_2
            extractedCode = 27;
            break;
          case 63: // ?
            // 390: bipush #41
            // 392: istore_2
            extractedCode = 41;
            break;
          case 'M': // 'M'
            // 300: bipush #6
            // 302: istore_2
            extractedCode = 6;
            break;
          case 'P': // 'P'
            // 306: bipush #47
            // 308: istore_2
            extractedCode = 47;
            break;
          case 'R': // 'R'
            // 295: iconst_5
            // 296: istore_2
            extractedCode = 5;
            break;
          case 'S': // 'S'
            // 290: iconst_4
            // 291: istore_2
            extractedCode = 4;
            break;
          case 'Z': // 'Z' - This case is handled by the Ctrl+Z logic above (case 26)
            // If it reaches here, it means Ctrl was not down or it wasn't a KEY_PRESSED event for 'Z'.
            // The bytecode for 'Z' directly jumps to 483 if `isControlDown()` is false.
            // So, no `extractedCode` is assigned here for 'Z' unless Ctrl is down.
            break;
          case 94: // ^
            // 384: bipush #36
            // 386: istore_2
            extractedCode = 36;
            break;
          case 124: // |
            // 372: bipush #34
            // 374: istore_2
            extractedCode = 34;
            break;
          case 126: // ~
            // 318: bipush #17
            // 320: istore_2
            extractedCode = 17;
            break;
          default:
            // 438: iload_3
            // 439: bipush #49
            // 441: if_icmplt -> 483
            // 444: iload_3
            // 445: bipush #57
            // 447: if_icmpgt -> 483
            // If keyChar is between '1' (49) and '9' (57) inclusive:
            if (keyChar >= '1' && keyChar <= '9') {
              // 450: bipush #11
              // 452: iconst_5
              // 453: iload_3
              // 454: bipush #49
              // 456: isub
              // 457: iconst_3
              // 458: irem
              // 459: imul
              // 460: iadd
              // 461: iload_3
              // 462: bipush #49
              // 464: isub
              // 465: iconst_3
              // 466: idiv
              // 467: isub
              // 468: istore_2
              // Complex calculation for numeric keys '1' through '9':
              // extractedCode = 11 + 5 * ((keyChar - '1') % 3) - ((keyChar - '1') / 3)
              extractedCode = 11 + 5 * ((keyChar - '1') % 3) - ((keyChar - '1') / 3);
            } else {
              // Default case: no specific mapping, extractedCode keeps its initial value (0 or -1 if > 0)
              // This goto 483 implies skipping further processing if no specific mapping found.
              // The `extractedCode` would retain its initial value or be -1 from the initial check.
            }
            break;
        }
      }
    } else { // 472: aload_0 dup getfield m_1 : I ldc -4128769 iand putfield m_1 : I
      // If it's a KEY_RELEASED event (isPressedEvent is false):
      // Clear the bits (16-21) in m_1.
      // -4128769 in decimal is 0xFFBF FFFF in hex. This masks out bits 16-21.
      this.m_1 = this.m_1 & 0xFFBF_FFFF; // Using underscore for readability in Java 7+
    }

    // 483: iload_2
    // 484: ifeq -> 497
    // If extractedCode is not 0:
    if (extractedCode != 0) {
      // 487: aload_1
      // 488: iconst_0
      // 489: invokevirtual setKeyCode : (I)V
      // 492: aload_1
      // 493: iconst_0
      // 494: invokevirtual setKeyChar : (C)V
      // Consume the key event by setting its key code and key character to 0.
      paramKeyEvent.setKeyCode(0);
      paramKeyEvent.setKeyChar((char) 0);
    }

    // 497: iload_2
    // 498: ifle -> 537
    // If extractedCode is positive (greater than 0):
    if (extractedCode > 0) {
      // 501: aload_0
      // 502: iload_2
      // 503: invokevirtual button : (I)Lpv/awt/PVImageButton;
      // 506: iload #4
      // 508: invokevirtual setPressed : (Z)V
      // Get a button associated with the extractedCode and set its pressed state
      // based on whether it was a KEY_PRESSED or KEY_RELEASED event.
      this.button(extractedCode).setPressed(isPressedEvent);

      // 511: iload #4
      // 513: ifeq -> 537
      // If it was a KEY_PRESSED event (isPressedEvent is true):
      if (isPressedEvent) {
        // 516: aload_0
        // 517: aload_0
        // 518: getfield m_1 : I
        // 521: ldc -4128769
        // 523: iand
        // 524: iload_2
        // 525: bipush #16
        // 527: ishl
        // 528: ior
        // 529: putfield m_1 : I
        // Set the bits 16-21 of m_1 to the extractedCode.
        // This essentially "remembers" which key/action is currently pressed.
        this.m_1 = (this.m_1 & 0xFFBF_FFFF) | (extractedCode << 16);

        // 532: aload_0
        // 533: iload_2
        // 534: invokevirtual action : (I)V
        // Call the `action` method, passing the extractedCode.
        this.action(extractedCode);
      }
    }
    // 537: return
  }

  public void undo() {
    PVNumeric pVNumeric = this.m_field;
    for (byte b = 0; b < 2; b++) {
      String str1 = pVNumeric.m_text;
      String str2 = pVNumeric.m_undo;
      if (str2 != null && str2.length() > 0) {
        pVNumeric.m_3 |= 0x400000;
        pVNumeric.setText(str2);
        if (str1.length() > 0)
          pVNumeric.m_undo = str1;
      }
      pVNumeric = this.m_memory;
    }
  }

  void undo(PVEdit paramPVEdit) {
    for (byte b = 0; b < 2 && paramPVEdit.m_text.length() != 0; b++) {
      if (b != 0 || !paramPVEdit.m_text.equals(paramPVEdit.m_undo))
        paramPVEdit.m_undo = paramPVEdit.m_text;
      paramPVEdit = (paramPVEdit == this.m_field) ? this.m_memory : this.m_field;
    }
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    Object object = paramActionEvent.getSource();
    byte b = -1;
    while (object != button(++b)) {
      if (b > 49)
        return;
    }
    requestFocus();
    action(b);
  }

  public void action(int paramInt) {
    double d;
    if (!button(paramInt).isEnabled())
      return;
    String str1 = this.m_field.m_text;
    String str2 = this.m_memory.m_text;
    switch (paramInt) {
      case 0:
      case 1:
      case 2:
        setFormat(paramInt);
        break;
      case 4:
        if (str1.length() > 0 && !str1.equals(str2)) {
          undo(this.m_memory);
          this.m_memory.m_3 |= 0x400000;
          this.m_memory.setText(str1);
        }
        break;
      case 5:
        if (str2.length() > 0 && !str2.equals(str1)) {
          undo(this.m_field);
          this.m_1 |= 0x400000;
          this.m_field.m_3 |= 0x400000;
          this.m_field.setText(str2);
        }
        break;
      case 6:
      case 7:
        d = this.m_field.getDouble();
        if (d != this.m_field.m_null) {
          undo(this.m_memory);
          long l1 = this.m_field.getLong();
          long l2 = this.m_memory.getLong();
          double d1;
          if ((d1 = this.m_memory.getDouble()) == this.m_memory.m_null)
            d1 = (l2 = 0L);
          this.m_memory.m_3 |= 0x400000;
          if (getFormat() == 0) {
            this.m_memory.setLong(l2 + ((paramInt == 6) ? l1 : -l1));
            break;
          }
          this.m_memory.setDouble(d1 + ((paramInt == 6) ? d : -d));
        }
        break;
      default:
        if (paramInt < 23 || paramInt == 41 || paramInt == 42 || paramInt == 47) {
          act1(paramInt);
          break;
        }
        act2(paramInt);
        break;
    }
    if ((this.m_0 & 0x200) != 0 || paramInt > 47)
      fireAction(paramInt);
  }

  void lastOp(int paramInt, boolean paramBoolean) {
    if (paramInt > 22 && paramInt < 39 && getKeepPressedOperationButton()) {
      button(paramInt).setCanKeepPressed(paramBoolean);
      button(paramInt).setPressed(paramBoolean);
    }
  }

  void act1(int paramInt) {
    int i = 0;
    PVNumeric pVNumeric = this.m_memory;
    if (pVNumeric.checkFocus() == 0)
      pVNumeric = this.m_field;
    double d = pVNumeric.getDouble();
    switch (paramInt) {
      case 47:
        i = 127;
        d = Math.PI;
        break;
      case 42:
        i = 127;
        d = Math.E;
        break;
      case 41:
        i = 127;
        d = Math.random() * this.m_random;
        break;
      case 22:
        i = pVNumeric.m_chDec;
        if (getFormat() == 2 && pVNumeric.getText().indexOf((char)i) >= 0)
          i = 0;
        break;
      case 17:
        if (pVNumeric.getText().length() == 0) {
          i = 45;
          break;
        }
        if (d != pVNumeric.m_null)
          d = -d;
        i = 127;
        break;
      case 18:
        lastOp(this.m_1 >> 24 & 0x3F, false);
        this.m_1 &= 0xC03FFFFF;
        pVNumeric = this.m_field;
      case 13:
        i = 127;
        d = pVNumeric.m_null;
        break;
      case 8:
        i = 8;
        break;
      case 19:
      case 20:
      case 21:
        i++;
      case 14:
      case 15:
      case 16:
        i++;
      case 9:
      case 10:
      case 11:
        i++;
      case 12:
        i += '0';
        if (--paramInt != 11 && paramInt % 5 > 0)
          i += (5 - paramInt % 5) * 3;
        break;
    }
    if (i != 0) {
      pVNumeric.m_1 |= 0x800000;
      if (i == 127) {
        undo(pVNumeric);
        pVNumeric.setDouble(d);
        pVNumeric.select((paramInt == 17) ? 100 : 0, 100);
      } else {
        pVNumeric.doKey((char)i, false);
        pVNumeric.m_1 |= 0x8000000;
      }
      if (pVNumeric == this.m_field)
        this.m_1 |= 0x400000;
      pVNumeric.m_1 &= 0xFF7FFFFF;
      pVNumeric.paint(null);
    }
  }

  void act2(int paramInt) {
    if (this.m_field.checkFocus() == 0)
      this.m_field.requestFocus();
    int i = this.m_1 >> 24 & 0x3F;
    long l1 = this.m_field.getLong();
    long l2 = l1;
    double d1 = this.m_field.getDouble();
    double d2 = d1;
    double d3 = this.m_field.m_null;
    if ((i == 0 && paramInt == 27) || d1 == d3)
      return;
    boolean bool = ((paramInt < 28 || paramInt > 32) && (paramInt < 37 || paramInt == 38)) ? false : true;
    if (bool) {
      i = paramInt;
    } else {
      if ((this.m_1 & 0x800000) == 0) {
        this.m_d2 = this.m_d1 = d1;
        this.m_i2 = this.m_i1 = l1;
        this.m_1 |= 0x800000;
      }
      boolean bool1 = ((this.m_1 & 0x400000) != 0) ? false : true;
      if (paramInt == 27) {
        if (bool1) {
          d2 = this.m_d2;
          l2 = this.m_i2;
        }
      } else {
        lastOp(i, false);
        if (bool1) {
          i = 0;
          this.m_d2 = d1;
          this.m_i2 = l2;
        }
        this.m_1 = this.m_1 & 0xC0FFFFFF | paramInt << 24;
        lastOp(paramInt, true);
      }
      if (!bool1) {
        this.m_d2 = d2 = d1;
        this.m_i2 = l2 = l1;
        d1 = this.m_d1;
        l1 = this.m_i1;
        this.m_1 &= 0xFFBFFFFF;
      }
    }
    String str = null;
    Object object = calculate(i, l1, l2, d1, d2);
    if (object != null) {
      if (object instanceof String)
        str = (String)object;
      if (object instanceof Number) {
        l1 = ((Number)object).longValue();
        d1 = ((Number)object).doubleValue();
      }
    } else if (d1 < 0.0D && (i == 30 || i == 32 || i == 39)) {
      str = "Invalid";
    } else if ((d1 == 0.0D && i == 31) || (d2 == 0.0D && i == 23)) {
      str = "Infinity";
    } else if (i == 32 && l1 > 169L) {
      str = "Too big";
    } else {
      try {
        switch (i) {
          case 23:
            d1 /= d2;
            if (l2 != 0L)
              l1 /= l2;
            break;
          case 24:
            d1 *= d2;
            l1 *= l2;
            break;
          case 25:
            d1 -= d2;
            l1 -= l2;
            break;
          case 26:
            d1 += d2;
            l1 += l2;
            break;
          case 28:
            d1 *= d1;
            l1 *= l1;
            break;
          case 29:
            d1 = d1 * d1 * d1;
            l1 = l1 * l1 * l1;
            break;
          case 30:
            d1 = Math.sqrt(d1);
            break;
          case 31:
            d1 = 1.0D / d1;
            break;
          case 32:
            l2 = l1;
            for (d1 = Math.floor(d1); --l2 > 1L; d1 *= l2)
              l1 *= l2;
            break;
          case 33:
            l1 %= l2;
            d1 = l1;
            break;
          case 34:
            l1 |= l2;
            d1 = l1;
            break;
          case 35:
            l1 &= l2;
            d1 = l1;
            break;
          case 36:
            l1 ^= l2;
            d1 = l1;
            break;
          case 37:
            l1 ^= 0xFFFFFFFFFFFFFFFFL;
            d1 = l1;
            break;
          case 38:
            d1 = Math.pow(d1, d2);
            break;
          case 39:
            d1 = Math.log(d1);
            break;
          case 40:
            d1 = Math.exp(d1);
            break;
          case 43:
            d1 = Math.sin(d1);
            break;
          case 44:
            d1 = Math.cos(d1);
            break;
          case 45:
            d1 = Math.tan(d1);
            break;
          case 46:
            d1 = Math.atan(d1);
            break;
        }
        if (i > 37 || i == 30 || i == 31)
          l1 = (long)d1;
        if (Character.isLetter(String.valueOf(d1).charAt(0)))
          str = "Error";
      } catch (Exception exception) {
        str = "Error";
      }
    }
    if (getFormat() < 2 && (d1 <= -9.223372036854776E18D || d1 >= 9.223372036854776E18D))
      str = "Too big";
    if (str != null) {
      this.m_field.temp(str);
      return;
    }
    undo(this.m_field);
    this.m_field.m_3 |= 0x400000;
    if (i != 0) {
      if (!bool) {
        this.m_d1 = d1;
        this.m_i1 = l1;
      }
      if (getFormat() == 0) {
        this.m_field.setLong(l1);
      } else {
        this.m_field.setDouble(d1);
      }
      if ((this.m_0 & 0x100) != 0) {
        fireAction(256);
        return;
      }
    } else {
      this.m_field.paint(null);
    }
  }

  protected Object calculate(int paramInt, long paramLong1, long paramLong2, double paramDouble1, double paramDouble2) {
    return null;
  }

  public PVImageButton button(int paramInt) {
    init();
    return (paramInt > 49) ? null : this.m_list.elementAt(paramInt);
  }

  public void fireAction(int paramInt) {
    if (this.m_aLs != null)
      this.m_aLs.actionPerformed(new ActionEvent(this, paramInt, this.m_act, 0));
  }

  public int getAppearance() {
    return this.m_0 & 0xFF;
  }

  public void setAppearance(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFFF00 | paramInt & 0xFF;
    this.m_1 &= 0xBFFFFFFF;
  }

  public boolean getEnterAsOK() {
    return !((this.m_0 & 0x8000) == 0);
  }

  public void setEnterAsOK(boolean paramBoolean) {
    this.m_0 &= 0xFFFF7FFF;
    if (paramBoolean)
      this.m_0 |= 0x8000;
  }

  public boolean getKeepPressedOperationButton() {
    return !((this.m_0 & 0x400000) != 0);
  }

  public void setKeepPressedOperationButton(boolean paramBoolean) {
    this.m_0 |= 0x400000;
    if (paramBoolean)
      this.m_0 &= 0xFFBFFFFF;
  }

  public int getBorderStyle() {
    return this.m_iBodr;
  }

  public void setBorderStyle(int paramInt) {
    this.m_iBodr = paramInt;
    this.m_1 &= 0xBFFFFFFF;
  }

  public int getShadow() {
    return this.m_0 >> 12 & 0x7;
  }

  public void setShadow(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFF8FFF | (paramInt & 0x7) % 5 << 12;
    this.m_1 &= 0xBFFFFFFF;
  }

  public int getShadowWidth() {
    return this.m_0 >> 16 & 0xF;
  }

  public void setShadowWidth(int paramInt) {
    this.m_0 = this.m_0 & 0xFFF0FFFF | (paramInt & 0xF) << 16;
    this.m_1 &= 0xBFFFFFFF;
  }

  public Color getShadowColor() {
    return (this.m_cSh == null) ? Color.gray : this.m_cSh;
  }

  public void setShadowColor(Color paramColor) {
    this.m_cSh = paramColor;
    repaint();
  }

  public int getMaxHeight() {
    return this.m_1 & 0x3F;
  }

  public void setMaxHeight(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFFFFC0 | paramInt & 0x3F;
    this.m_1 &= 0xBFFFFFFF;
  }

  public int getSpacing() {
    return this.m_1 >> 8 & 0xF;
  }

  public void setSpacing(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFFF0FF | (paramInt & 0xF) << 8;
    this.m_1 &= 0xBFFFFFFF;
  }

  public int getMargins() {
    return this.m_1 >> 12 & 0xF;
  }

  public void setMargins(int paramInt) {
    this.m_1 = this.m_1 & 0xFFFF0FFF | (paramInt & 0xF) << 12;
    this.m_1 &= 0xBFFFFFFF;
  }

  public void setLong(long paramLong) {
    lastOp(this.m_1 >> 24 & 0x3F, false);
    this.m_1 &= 0xC000FFFF;
    this.m_field.setLong(paramLong);
  }

  public long getLong() {
    return this.m_field.getLong();
  }

  public void setDouble(double paramDouble) {
    lastOp(this.m_1 >> 24 & 0x3F, false);
    this.m_1 &= 0xC000FFFF;
    this.m_field.setDouble(paramDouble);
  }

  public double getDouble() {
    return this.m_field.getDouble();
  }

  public void setText(String paramString) {
    lastOp(this.m_1 >> 24 & 0x3F, false);
    this.m_1 &= 0xC000FFFF;
    this.m_field.setText(paramString);
    this.m_field.m_1 &= 0xF7FFFFFF;
  }

  public String getText() {
    return this.m_field.getText();
  }

  public int getButtonsBorder() {
    return button(4).getBorderStyle();
  }

  public void setButtonsBorder(int paramInt) {
    if (paramInt != getButtonsBorder())
      for (byte b = 4; b < 48; b++)
        button(b).setBorderStyle(paramInt);
  }

  public boolean getTriangles() {
    return !(button(4).getTriangle() == 0);
  }

  public void setTriangles(boolean paramBoolean) {
    if (paramBoolean != getTriangles()) {
      button(4).setTriangle(paramBoolean ? 5 : 0);
      button(5).setTriangle(paramBoolean ? 3 : 0);
    }
  }

  public boolean getButtons3States() {
    return button(4).getThreeButtonStates();
  }

  public void setButtons3States(boolean paramBoolean) {
    if (paramBoolean != getButtons3States())
      for (byte b = 4; b < 50; b++)
        button(b).setThreeButtonStates(paramBoolean);
  }

  public Color getButtonsBackground() {
    return button(4).getBackground();
  }

  public void setButtonsBackground(Color paramColor) {
    if (paramColor != getButtonsBackground())
      for (byte b = 4; b < 48; b++)
        button(b).setBackground(paramColor);
  }

  public Color getDigitForeground() {
    return button(9).getForeground();
  }

  public void setDigitForeground(Color paramColor) {
    if (paramColor != getDigitForeground())
      for (byte b = 8; b < 48; b++) {
        button(b).setForeground(paramColor);
        if (b == 12 || b == 17)
          b++;
        if (b == 22)
          b = 40;
        if (b == 42)
          b = 46;
      }
  }

  public Color getOperationForeground() {
    return button(23).getForeground();
  }

  public void setOperationForeground(Color paramColor) {
    if (paramColor != getOperationForeground())
      for (byte b = 13; b < 39; b++) {
        button(b).setForeground(paramColor);
        if (b == 13 || b == 18)
          b += 4;
        if (b == 27)
          b = 32;
        if (b == 36)
          b++;
      }
  }

  public void setText3D(int paramInt1, int paramInt2) {
    for (byte b = 0; b < 50; b++)
      button(b).setText3D(paramInt1, paramInt2);
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  public void requestFocus() {
    if (this.m_memory.checkFocus() == 0)
      this.m_field.requestFocus();
  }
}
