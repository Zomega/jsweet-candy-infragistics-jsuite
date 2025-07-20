package pv.awt;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PVSpin extends Container implements ActionListener, Runnable, Serializable {
  public static final int INCREMENT = 64;

  public static final int DECREMENT = 128;

  public static final int HORIZONTAL_CENTER = 0;

  public static final int HORIZONTAL_LEFT = 2;

  public static final int HORIZONTAL_RIGHT = 1;

  public static final int HORIZONTAL_TOP = 4;

  public static final int HORIZONTAL_BOTTOM = 3;

  public static final int VERTICAL_CENTER = 8;

  public static final int VERTICAL_LEFT = 10;

  public static final int VERTICAL_RIGHT = 9;

  public static final int VERTICAL_TOP = 12;

  public static final int VERTICAL_BOTTOM = 11;

  protected PVImageButton m_but1;

  protected PVImageButton m_but2;

  protected Component m_obj;

  protected int m_0 = 13107616;

  protected Dimension m_prefer;

  protected Dimension m_minS;

  protected Dimension m_maxS;

  transient ActionListener m_aLs;

  static transient Thread m_spin;

  static transient int m_iDelay = 1;

  public PVSpin(int paramInt) {
    init0(paramInt);
  }

  public PVSpin() {
    this(0);
  }

  public void invalidate() {
    adjust();
  }

  public void requestFocus() {
    if (this.m_obj != null)
      this.m_obj.requestFocus();
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
    if (this.m_but1 != null && (this.m_but1.m_3 & 0x2000) != 0) {
      Object object;
      while ((object = paramObjectInputStream.readObject()) != null) {
        if ("aLs".equals(object))
          addActionListener((ActionListener)paramObjectInputStream.readObject());
      }
    }
    init0(-1);
  }

  void init0(int paramInt) {
    setLayout(null);
    this.m_0 &= 0x3FFFFFFF;
    PVImageButton pVImageButton = this.m_but1;
    while (pVImageButton == null) {
      pVImageButton = new PVImageButton("");
      pVImageButton.setCanGetFocus(false);
      pVImageButton.m_3 |= 0xC0000;
      pVImageButton.setTriangleSize(11);
      pVImageButton.addActionListener(this);
      add(pVImageButton);
      if (this.m_but1 == null) {
        this.m_but1 = pVImageButton;
        pVImageButton = null;
        continue;
      }
      this.m_but2 = pVImageButton;
      break;
    }
    setStyle(paramInt);
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if ((paramActionEvent.getModifiers() & 0x4) != 0)
      stop();
    if ((paramActionEvent.getModifiers() & 0x2) != 0) {
      if (((paramActionEvent.getSource() != this.m_but1) ? false : true) == (((this.m_0 & 0x8) != 8) ? false : true)) {
        this.m_0 &= 0xBFFFFFFF;
        fireSpin(64);
      } else {
        this.m_0 |= 0x40000000;
        fireSpin(128);
      }
      m_iDelay = getDelay();
      if (m_spin == null)
        try {
          m_spin = new Thread(this);
          m_spin.start();
          return;
        } catch (Exception exception) {
          return;
        }
    }
  }

  void stop() {
    m_iDelay = 1;
    if (m_spin != null) {
      try {
        m_spin.interrupt();
      } catch (Exception exception) {}
      try {
        m_spin.stop();
      } catch (Exception exception) {}
      m_spin = null;
    }
  }

  public void run() {
    while (m_iDelay > 3 && m_spin != null) {
      try {
        Thread.sleep(m_iDelay);
      } catch (InterruptedException interruptedException) {}
      if (m_iDelay > 3)
        fireSpin(((this.m_0 & 0x40000000) == 0) ? 64 : 128);
      if (getSpeedUp() && m_iDelay > 5)
        m_iDelay = m_iDelay * 7 >>> 3;
    }
  }

  public PVImageButton[] getButtons() {
    return new PVImageButton[] { this.m_but1, this.m_but2 };
  }

  public void setObject(Component paramComponent) {
    if (this.m_obj != paramComponent) {
      if (this.m_obj != null)
        remove(this.m_obj);
      if ((this.m_obj = paramComponent) != null)
        add(this.m_obj);
      adjust();
    }
  }

  public Component getObject() {
    return this.m_obj;
  }

  public void setDefaultImages(boolean paramBoolean) {
    if (paramBoolean) {
      this.m_0 &= 0xFFFFDFFF;
      setStyle(-1);
      return;
    }
    this.m_0 |= 0x2000;
    this.m_but1.setTriangle(0);
    this.m_but2.setTriangle(0);
  }

  public boolean getDefaultImages() {
    return !((this.m_0 & 0x2000) != 0);
  }

  public int getStyle() {
    return this.m_0 & 0xF;
  }

  public void setStyle(int paramInt) {
    if (paramInt < 0) {
      paramInt = this.m_0 & 0xF;
    } else {
      if (paramInt > 12)
        paramInt = 12;
      if (paramInt > 4 && paramInt < 8)
        paramInt = 8;
      this.m_0 = this.m_0 & 0xFFFFFFF0 | paramInt;
    }
    if (getDefaultImages()) {
      this.m_but1.setTriangle(paramInt / 8 + 2);
      this.m_but2.setTriangle(paramInt / 8 + 4);
    }
    adjust();
  }

  public int getDelay() {
    return this.m_0 >> 16 & 0x3FF;
  }

  public void setDelay(int paramInt) {
    if (paramInt < 5)
      paramInt = 5;
    if (paramInt > 1000)
      paramInt = 1000;
    this.m_0 = this.m_0 & 0xFC00FFFF | paramInt << 16;
  }

  public boolean getSpeedUp() {
    return !((this.m_0 & 0x1000) != 0);
  }

  public void setSpeedUp(boolean paramBoolean) {
    this.m_0 |= 0x1000;
    if (paramBoolean)
      this.m_0 &= 0xFFFFEFFF;
  }

  public int getButtonWidth() {
    return this.m_0 >> 4 & 0x7F;
  }

  public void setButtonWidth(int paramInt) {
    this.m_0 = this.m_0 & 0xFFFFF80F | (paramInt & 0x7F) << 4;
    adjust();
  }

  public int getButtonBorder() {
    return this.m_but1.getBorderStyle();
  }

  public void setButtonBorder(int paramInt) {
    this.m_but1.setBorderStyle(paramInt);
    this.m_but2.setBorderStyle(paramInt);
    adjust();
  }

  public void setButtonColor(Color paramColor) {
    this.m_but1.setBackground(paramColor);
    this.m_but2.setBackground(paramColor);
  }

  public Color getButtonColor() {
    return this.m_but1.getBackColor();
  }

  public void setFont(Font paramFont) {
    super.setFont(paramFont);
    if (this.m_but1 != null) {
      this.m_but1.setFont(paramFont);
      this.m_but2.setFont(paramFont);
    }
    if (this.m_obj != null)
      this.m_obj.setFont(paramFont);
  }

  public void setPreferredSize(Dimension paramDimension) {
    this.m_prefer = paramDimension;
  }

  public Dimension getPreferredSize() {
    Dimension dimension1 = new Dimension((this.m_prefer == null) ? getSize() : this.m_prefer);
    int i = getButtonWidth();
    if (i == 0)
      i = 26;
    if (this.m_obj == null) {
      if (dimension1.width < 2)
        dimension1.width = i;
      if (dimension1.height < 2)
        dimension1.height = i;
      return dimension1;
    }
    Dimension dimension2 = this.m_obj.getPreferredSize();
    if (dimension1.width < 2) {
      dimension1.width = dimension2.width;
      if ((this.m_0 & 0x7) == 1 || (this.m_0 & 0x7) == 2 || (this.m_0 & 0xF) == 0)
        dimension1.width += i;
    }
    if (dimension1.height < 2) {
      dimension1.height = dimension2.height;
      if ((this.m_0 & 0x7) > 2 || (this.m_0 & 0xF) == 8)
        dimension1.height += i;
    }
    return dimension1;
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

  public synchronized void adjust() {
    stop();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = (getSize()).width;
    int i1 = (getSize()).height;
    if (n > 2 && i1 > 2) {
      int i2 = getButtonWidth();
      if (i2 == 0)
        i2 = 26;
      int i3 = i2;
      boolean bool = ((this.m_0 & 0x8) != 0) ? false : true;
      j = this.m_0 & 0x7;
      if (j == 0)
        j = bool ? 1 : 3;
      if (j < 3) {
        if (i2 > n)
          i2 = n;
        if (bool)
          i2 &= 0x7E;
        n -= i2;
        i3 = i1;
      } else {
        if (i3 > i1)
          i3 = i1;
        if (!bool)
          i3 &= 0x7E;
        i1 -= i3;
        i2 = n;
      }
      j = 0;
      switch (this.m_0 & 0x7) {
        case 1:
          m = n;
          break;
        case 2:
          i = i2;
          break;
        case 3:
          k = i1;
          break;
        case 4:
          j = i3;
          break;
        case 0:
          if (bool) {
            i = i2 >>> 1;
            break;
          }
          j = i3 >>> 1;
          break;
      }
      if (this.m_obj != null)
        this.m_obj.setBounds(i, j, n, i1);
      if (bool) {
        i = i2 >>> 1;
        this.m_but1.setBounds(m, k, i, i3);
        if ((this.m_0 & 0x7) == 0)
          m += n;
        this.m_but2.setBounds(m + i2 - i, k, i, i3);
        return;
      }
      j = i3 >>> 1;
      this.m_but1.setBounds(m, k, i2, j);
      if ((this.m_0 & 0x7) == 0)
        k += i1;
      this.m_but2.setBounds(m, k + i3 - j, i2, j);
    }
  }

  public synchronized void addActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.add(this.m_aLs, paramActionListener);
  }

  public synchronized void removeActionListener(ActionListener paramActionListener) {
    this.m_aLs = AWTEventMulticaster.remove(this.m_aLs, paramActionListener);
  }

  public void fireSpin(int paramInt) {
    if (this.m_aLs != null)
      this.m_aLs.actionPerformed(new ActionEvent(this, 1001, null, paramInt));
  }
}
