package pv.util;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class PVColorChooser extends Frame implements KeyListener, ActionListener {
  public static final int DONE = 0;

  public static final int UPDATE = 1;

  public static final int CANCEL = 2;

  public static final int NULL = 3;

  public static final int CLOSE = 4;

  public static final int SELECT = 5;

  public static final int DEACTIVATED = 6;

  public transient TextField[] m_t;

  public transient Button[] m_b;

  public transient Color m_old;

  public transient int m_i = 4;

  transient int m_iC = -1;

  public transient boolean m_dispose = false;

  protected transient ActionListener m_aLs;

  public PVColorChooser() {
    this(null, null);
  }

  public PVColorChooser(String paramString, Color paramColor) {
    setLayout(null);
    setBackground(Color.lightGray);
    setResizable(false);
    this.m_b = new Button[3];
    this.m_b[0] = new Button("OK");
    this.m_b[1] = new Button("Reset");
    this.m_b[2] = new Button("Cancel");
    this.m_t = new TextField[3];
    byte b = -1;
    while (++b < 3) {
      this.m_b[b].setFont(new Font("SansSerif", 1, 12));
      this.m_b[b].setBounds(5 + 77 * b, 140, 72, 20);
      this.m_b[b].addActionListener(this);
      this.m_b[b].addKeyListener(this);
      add(this.m_b[b]);
    }
    b = -1;
    while (++b < 3) {
      this.m_t[b] = new TextField();
      this.m_t[b].addKeyListener(this);
      this.m_t[b].setBounds(10 + (2 - b) * 49, 114, 38, 17);
      add(this.m_t[b]);
    }
    enableEvents(124L);
    setBounds(3, 3, 236, 183);
    setColor(paramString, paramColor);
  }

  public void setColor(String paramString, Color paramColor) {
    if (paramString != null)
      setTitle(paramString);
    this.m_iC = ((this.m_old = paramColor) == null) ? -1 : (paramColor.getRGB() & 0xFFFFFF);
    setVisible(true);
    requestFocus();
  }

  public Color getColor() {
    return (this.m_iC == -1) ? null : new Color(this.m_iC);
  }

  protected void fireChange(Color paramColor) {}

  public void paint(Graphics paramGraphics) {
    try {
      Rectangle rectangle = new Rectangle(5, 25, (getSize()).width - 9, 82);
      int i = 5;
      i = drawPal(paramGraphics, i, -1, 0, 0, 255, 255, 255);
      i = drawPal(paramGraphics, i, 0, -1, 0, 0, 255, 255);
      i = drawPal(paramGraphics, i, 1, 0, 0, 0, 0, 255);
      i = drawPal(paramGraphics, i, 0, 0, -1, 255, 0, 255);
      i = drawPal(paramGraphics, i, 0, 1, 0, 255, 0, 0);
      i = drawPal(paramGraphics, i, -1, 0, 0, 255, 255, 0);
      i = drawPal(paramGraphics, i, 0, 0, 1, 0, 255, 0);
      PVBorder.draw(paramGraphics, rectangle, 175, 139, 0, Color.lightGray);
      PVBorder.draw(paramGraphics, new Rectangle(154, 109, 34, 27), 1369, 1369, 0, Color.lightGray);
      PVBorder.draw(paramGraphics, new Rectangle(190, 109, 41, 27), 2257, 2986, 0, Color.lightGray);
      for (i = 0; i < 3; i++)
        PVBorder.draw(paramGraphics, new Rectangle(5 + i * 49, 109, 48, 27), 120619, 47370, 0, new Color(255 << (2 - i) * 8));
      drawRGB(paramGraphics, false);
      System.gc();
      return;
    } catch (Exception exception) {
      return;
    }
  }

  int drawPal(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    for (byte b = 0; b < 4; b++) {
      byte b1 = 26;
      for (double d = 0.12D; d < 1.05D; d += 0.2D) {
        paramGraphics.setColor(new Color(((int)(paramInt5 * d) & 0xFF) << 16 | ((int)(paramInt6 * d) & 0xFF) << 8 | (int)(paramInt7 * d) & 0xFF));
        paramGraphics.fillRect(paramInt1, b1, 8, 8);
        paramGraphics.setColor(new Color((paramInt5 + (int)((255 - paramInt5) * d) & 0xFF) << 16 | (paramInt6 + (int)((255 - paramInt6) * d) & 0xFF) << 8 | paramInt7 + (int)((255 - paramInt7) * d) & 0xFF));
        paramGraphics.fillRect(paramInt1, 40 + b1, 8, 8);
        b1 += 8;
      }
      paramInt1 += 8;
      paramInt5 += 56 * paramInt2;
      paramInt6 += 56 * paramInt3;
      paramInt7 += 56 * paramInt4;
    }
    return paramInt1;
  }

  void drawRGB(Graphics paramGraphics, boolean paramBoolean) {
    byte b = -1;
    if (!paramBoolean)
      while (++b < 3)
        this.m_t[b].setText((this.m_iC == -1) ? "" : String.valueOf(this.m_iC >> b * 8 & 0xFF));
    boolean bool = (paramGraphics != null) ? false : true;
    if (bool && (paramGraphics = getGraphics()) == null)
      return;
    paramGraphics.setColor((this.m_old == null) ? getBackground() : this.m_old);
    paramGraphics.fillRect(158, 113, 26, 19);
    if (this.m_iC != -1)
      paramGraphics.setColor(new Color(this.m_iC));
    paramGraphics.fillRect(194, 113, 33, 19);
    if (this.m_iC == -1) {
      paramGraphics.setColor(getForeground());
      paramGraphics.drawString("null", 199, 126);
    }
    if (bool)
      paramGraphics.dispose();
    fire(5);
  }

  protected void processEvent(AWTEvent paramAWTEvent) {
    super.processEvent(paramAWTEvent);
    int i = paramAWTEvent.getID();
    if (i == 201) {
      setVisible(false);
      fire(4);
    }
    if (i == 206)
      fire(6);
    if (i == 401) {
      i = ((KeyEvent)paramAWTEvent).getKeyCode();
      if (i == 10 || i == 27)
        doAct(this.m_b[(i - 5) / 10]);
      if (i == 38 || i == 40)
        for (byte b = 0; b < 3; b++)
          doText(new KeyEvent(this.m_t[b], 401, 0L, 0, i));
    }
    if (paramAWTEvent instanceof MouseEvent) {
      int j = ((MouseEvent)paramAWTEvent).getX();
      int k = ((MouseEvent)paramAWTEvent).getY();
      if (i == 506)
        doMouse(j, k);
      if (i == 500) {
        if (((MouseEvent)paramAWTEvent).getClickCount() == 1) {
          requestFocus();
          doMouse(j, k);
          return;
        }
        if (j > 194 || k < 106)
          doAct(this.m_b[0]);
      }
    }
  }

  void doMouse(int paramInt1, int paramInt2) {
    paramInt2 -= 22;
    if (paramInt1 < 2 || paramInt1 > 228 || paramInt2 < 1 || paramInt2 > 138)
      return;
    paramInt1 -= 6;
    if (paramInt1 < 0)
      paramInt1 = 0;
    if (paramInt1 > 219)
      paramInt1 = 219;
    if (paramInt2 < 86) {
      paramInt2 -= 4;
      if (paramInt2 < 0)
        paramInt2 = 0;
      if (paramInt2 > 80)
        paramInt2 = 80;
      char c = Character.MIN_VALUE;
      switch ((paramInt1 >> 5) % 7) {
        case 0:
          c = 'ȳ';
          break;
        case 1:
          c = '#';
          break;
        case 2:
          c = 'ă';
          break;
        case 3:
          c = '̂';
          break;
        case 4:
          c = '̐';
          break;
        case 5:
          c = 'Ȱ';
          break;
        case 6:
          c = '1';
          break;
      }
      this.m_iC = 0;
      for (byte b = 0; b < 3; b++) {
        int i = c >> b * 4 & 0xFF & 0x1;
        int j = (c >> b * 4 & 0xFF & 0x2) >> 1;
        i = j * 255 + (i - j) * 255 * paramInt1 % 32 / 31;
        if (paramInt2 < 40)
          i = i * paramInt2 / 40;
        if (paramInt2 > 40)
          i += (255 - i) * (paramInt2 - 40) / 40;
        this.m_iC |= (i & 0xFF) << b * 8;
      }
      drawRGB(null, false);
    }
  }

  void doText(KeyEvent paramKeyEvent) {
    int i = paramKeyEvent.getKeyCode();
    byte b = -1;
    int j = 0;
    if (i == 10 || i == 27)
      processEvent(paramKeyEvent);
    if (paramKeyEvent.isActionKey() && i != 38 && i != 40)
      return;
    if (i >= 32 && i != 127) {
      if (i > 57 || i < 48)
        paramKeyEvent.consume();
      if (paramKeyEvent.getID() == 401) {
        if (i == 38)
          i = 5;
        if (i == 40)
          i = -5;
      }
      if (((paramKeyEvent.getID() != 402) ? false : true) == ((i <= 20) ? false : true)) {
        while (++b < 2 && paramKeyEvent.getSource() != this.m_t[b]);
        if (this.m_iC == -1)
          this.m_iC = 0;
        String str = this.m_t[b].getText();
        if (str.length() > 0 && str.charAt(0) != '0')
          try {
            j = (new Integer(str)).intValue();
          } catch (Exception exception) {}
        if (i < 20) {
          if ((j += i) < 0)
            j = 0;
          if (j > 255)
            j = 255;
        } else {
          for (i = -20; j > 999; i = j /= 10);
          if (j > 255)
            i = j = 255;
          if (j <= 0)
            i = j = 0;
        }
        if (i > -15)
          this.m_t[b].setText((j > 0) ? String.valueOf(j) : "");
        this.m_iC &= 255 << b * 8 ^ 0xFFFFFFFF;
        this.m_iC |= j << b * 8;
        drawRGB(null, true);
      }
    }
  }

  void doAct(Object paramObject) {
    byte b = 0;
    while (paramObject != this.m_b[b]) {
      if (++b > 2)
        return;
    }
    if (b == 1) {
      this.m_iC = -1;
      this.m_old = null;
      b = 3;
    }
    if (b != 2 && this.m_iC != -1)
      this.m_old = new Color(this.m_iC);
    if ((b & 0x1) != 0) {
      fireChange(this.m_old);
      drawRGB(null, false);
    } else {
      setVisible(false);
    }
    fire(b);
  }

  void fire(int paramInt) {
    this.m_i = paramInt;
    if (this.m_aLs != null)
      this.m_aLs.actionPerformed(new ActionEvent(this, paramInt, null, 0));
    if ((paramInt & 0x1) == 0 && this.m_dispose)
      dispose();
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  public void keyReleased(KeyEvent paramKeyEvent) {
    doText(paramKeyEvent);
  }

  public void keyTyped(KeyEvent paramKeyEvent) {
    doText(paramKeyEvent);
  }

  public void keyPressed(KeyEvent paramKeyEvent) {
    doText(paramKeyEvent);
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    doAct(paramActionEvent.getSource());
  }
}
