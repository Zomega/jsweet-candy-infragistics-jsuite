package pv.awt;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import pv.util.PVBorder;
import pv.util.PVColorChooser;

public class PVButtonCustomizer extends Container implements Customizer, ActionListener, ItemListener, TextListener {
  PropertyChangeSupport m_sup;

  PVColorChooser m_clr;

  PVButton m_targ;

  TextField[] m_t;

  Choice[] m_k;

  Checkbox[] m_c;

  Button[] m_b;

  int m_width;

  int m_height;

  int m_id = -100;

  Font m_f = new Font("SansSerif", 0, 11);

  public String m_name = "Button";

  public int m_top = 84;

  public PVButtonCustomizer() {
    init0();
  }

  protected void init0() {
    this.m_sup = new PropertyChangeSupport(this);
    setLayout(null);
    setSize(278, this.m_top + 317);
  }

  public Dimension getPreferredSize() {
    return new Dimension(278, this.m_top + 317);
  }

  public void removeNotify() {
    if (this.m_clr != null)
      try {
        this.m_clr.dispose();
      } catch (Exception exception) {}
    this.m_clr = null;
    super.removeNotify();
    fix();
  }

  public void setObject(Object paramObject) {
    PVButton pVButton = this.m_targ = (PVButton)paramObject;
    this.m_width = (pVButton.getSize()).width;
    this.m_height = (pVButton.getSize()).height;
    if (this.m_c != null)
      return;
    this.m_id = -100;
    this.m_k = new Choice[8];
    this.m_c = new Checkbox[14];
    Label[] arrayOfLabel = new Label[12];
    this.m_t = new TextField[3];
    this.m_t[0] = new TextField(pVButton.getText());
    this.m_t[1] = new TextField(pVButton.getTextPressed());
    this.m_t[2] = new TextField((pVButton.getFileImageAt(0) == null) ? "" : pVButton.getFileImageAt(0));
    byte b;
    for (b = 0; b < 3; b++) {
      this.m_t[b].setFont(this.m_f);
      add(this.m_t[b]);
      this.m_t[b].addTextListener(this);
    }
    arrayOfLabel[0] = new Label(" Text");
    arrayOfLabel[1] = new Label("Main");
    arrayOfLabel[2] = new Label("Pressed");
    arrayOfLabel[3] = new Label("Border Style:");
    arrayOfLabel[4] = new Label(" Shadow");
    arrayOfLabel[5] = new Label("Position");
    arrayOfLabel[6] = new Label(" Width");
    arrayOfLabel[7] = new Label(" Text 3D effect");
    arrayOfLabel[8] = new Label(" Colors");
    arrayOfLabel[9] = new Label("Image Alignment");
    arrayOfLabel[10] = new Label("File-Image for Button State");
    arrayOfLabel[11] = new Label("Triangle");
    for (b = 0; b < 12; b++) {
      add(arrayOfLabel[b]);
      arrayOfLabel[b].setFont(this.m_f);
      if (b < 8) {
        this.m_k[b] = new Choice();
        add(this.m_k[b]);
        this.m_k[b].setFont(this.m_f);
        this.m_k[b].addItemListener(this);
      }
    }
    this.m_c[0] = new Checkbox("Aureole", pVButton.getAureole());
    this.m_c[1] = new Checkbox("Three Button States", pVButton.getThreeButtonStates());
    this.m_c[2] = new Checkbox("Can Keep Pressed State", pVButton.getCanKeepPressed());
    this.m_c[3] = new Checkbox("Can Get Focus", pVButton.getCanGetFocus());
    this.m_c[4] = new Checkbox("Switch Colors On Press", pVButton.getSwitchColorsOnPress());
    this.m_c[5] = new Checkbox("Switch Shadow On Press", pVButton.getSwitchShadowOnPress());
    this.m_c[6] = new Checkbox("Extra Popped Up", pVButton.getExtraPoppedUp());
    this.m_c[7] = new Checkbox("Multi-Lined Text", pVButton.getMultiLine());
    this.m_c[8] = new Checkbox("Auto Resize", pVButton.getAutoResize());
    this.m_c[9] = new Checkbox("Enable Enter Key", pVButton.getEnableEnter());
    this.m_c[10] = new Checkbox("Enable Space Key", pVButton.getEnableSpace());
    this.m_c[11] = new Checkbox("Enable Escape Key", pVButton.getEnableEscape());
    this.m_c[12] = new Checkbox("Focus Rectangle", pVButton.getFocusRect());
    this.m_c[13] = new Checkbox("Focus Rectangle Around Text", pVButton.getFocusRectAroundText());
    for (b = 0; b < 14; b++) {
      this.m_c[b].setFont(this.m_f);
      add(this.m_c[b]);
      if (b > 0)
        this.m_c[b].setBounds(113, this.m_top + 20 + b * 17, 165, 16);
    }
    this.m_k[0].addItem("None");
    this.m_k[0].addItem("Simple1");
    this.m_k[0].addItem("Simple2");
    this.m_k[0].addItem("Simple3");
    this.m_k[0].addItem("Simple4");
    this.m_k[0].addItem("Simple5");
    this.m_k[0].addItem("Standard1");
    this.m_k[0].addItem("Standard2");
    this.m_k[0].addItem("Standard3");
    this.m_k[0].addItem("Vague");
    this.m_k[0].addItem("Double");
    this.m_k[0].addItem("Triple");
    this.m_k[0].addItem("Triple2");
    this.m_k[0].addItem("Frame");
    this.m_k[0].addItem("Picture");
    b = -1;
    switch (pVButton.getBorderStyle()) {
      case 0:
        b = 0;
        break;
      case 196609:
        b = 1;
        break;
      case 163842:
        b = 2;
        break;
      case 1474578:
        b = 3;
        break;
      case 11960466:
        b = 4;
        break;
      case 95847570:
        b = 5;
        break;
      case 2031633:
        b = 6;
        break;
      case 9994377:
        b = 7;
        break;
      case 81036937:
        b = 8;
        break;
      case 634795091:
        b = 9;
        break;
      case 1056388057:
        b = 10;
        break;
      case 937369209:
        b = 11;
        break;
      case 1874767546:
        b = 12;
        break;
      case 1420252785:
        b = 13;
        break;
      case 1311728473:
        b = 14;
        break;
    }
    if (b >= 0)
      this.m_k[0].select(b);
    this.m_k[1].addItem("None");
    this.m_k[1].addItem("Right-Bottom");
    this.m_k[1].addItem("Left-Bottom");
    this.m_k[1].addItem("Left-Top");
    this.m_k[1].addItem("Right-Top");
    this.m_k[1].select(pVButton.getShadow());
    for (b = 0; b < 16; b++)
      this.m_k[2].addItem(String.valueOf(b));
    this.m_k[2].select(pVButton.getShadowWidth());
    this.m_k[3].addItem("Left 2");
    this.m_k[3].addItem("Left 1");
    this.m_k[3].addItem("None");
    this.m_k[3].addItem("Right 1");
    this.m_k[3].addItem("Right 2");
    this.m_k[3].select(pVButton.getHorizontal3D() + 2);
    this.m_k[4].addItem("Top 2");
    this.m_k[4].addItem("Top 1");
    this.m_k[4].addItem("None");
    this.m_k[4].addItem("Bottom 1");
    this.m_k[4].addItem("Bottom 2");
    this.m_k[4].select(pVButton.getVertical3D() + 2);
    this.m_k[5].addItem("Right");
    this.m_k[5].addItem("Left");
    this.m_k[5].addItem("Bottom");
    this.m_k[5].addItem("Top");
    this.m_k[5].addItem("Center");
    this.m_k[5].select(pVButton.getImageAlignment());
    this.m_k[6].addItem("Main");
    this.m_k[6].addItem("Pressed");
    this.m_k[6].addItem("Flat");
    this.m_k[6].addItem("Disabled");
    this.m_k[6].select(0);
    this.m_k[7].addItem("None");
    this.m_k[7].addItem("Lined");
    this.m_k[7].addItem("Left");
    this.m_k[7].addItem("Up");
    this.m_k[7].addItem("Right");
    this.m_k[7].addItem("Down");
    this.m_k[7].addItem("Box");
    this.m_k[7].addItem("CheckBox");
    this.m_k[7].select(pVButton.getTriangle() & 0x7);
    arrayOfLabel[9].setBounds(10, this.m_top - 44, 78, 12);
    this.m_k[5].setBounds(12, this.m_top - 32, 76, 20);
    arrayOfLabel[10].setBounds(95, this.m_top - 44, 128, 12);
    this.m_t[2].setBounds(95, this.m_top - 32, 77, 20);
    this.m_k[6].setBounds(173, this.m_top - 32, 49, 20);
    arrayOfLabel[11].setBounds(226, this.m_top - 44, 39, 12);
    this.m_k[7].setBounds(226, this.m_top - 32, 40, 20);
    arrayOfLabel[0].setBounds(10, this.m_top, 25, 12);
    arrayOfLabel[1].setBounds(15, this.m_top + 12, 26, 12);
    this.m_t[0].setBounds(12, this.m_top + 24, 80, 18);
    arrayOfLabel[2].setBounds(15, this.m_top + 42, 43, 12);
    this.m_t[1].setBounds(12, this.m_top + 54, 80, 18);
    arrayOfLabel[3].setBounds(113, this.m_top + 12, 66, 12);
    this.m_k[0].setBounds(180, this.m_top + 8, 85, 20);
    arrayOfLabel[4].setBounds(10, this.m_top + 82, 45, 12);
    arrayOfLabel[5].setBounds(15, this.m_top + 95, 70, 12);
    this.m_k[1].setBounds(12, this.m_top + 107, 80, 20);
    arrayOfLabel[6].setBounds(15, this.m_top + 128, 70, 12);
    this.m_k[2].setBounds(12, this.m_top + 140, 80, 20);
    arrayOfLabel[7].setBounds(10, this.m_top + 172, 73, 12);
    this.m_k[3].setBounds(12, this.m_top + 185, 80, 20);
    this.m_k[4].setBounds(12, this.m_top + 210, 80, 20);
    this.m_c[0].setBounds(12, this.m_top + 235, 80, 16);
    setAur(this.m_c[0].getState());
    this.m_k[2].setEnabled(!(pVButton.getShadow() == 0));
    arrayOfLabel[8].setBounds(11, this.m_top + 262, 37, 12);
    this.m_b = new Button[7];
    this.m_b[0] = new Button("Foreground");
    this.m_b[1] = new Button("Background");
    this.m_b[2] = new Button("Mouse");
    this.m_b[3] = new Button("Border");
    this.m_b[4] = new Button("Shadow");
    this.m_b[5] = new Button("Text3D");
    this.m_b[6] = new Button("Update");
    for (b = 0; b < 7; b++) {
      this.m_b[b].setFont(this.m_f);
      add(this.m_b[b]);
      this.m_b[b].addActionListener(this);
      if (b == 6) {
        this.m_b[b].setBounds(205, 0, 70, 18);
      } else {
        this.m_b[b].setBounds(12 + b % 3 * 86, this.m_top + b / 3 * 17 + 274, 83, 16);
      }
    }
    this.m_id = -1;
  }

  private void setAur(boolean paramBoolean) {
    this.m_targ.setAureole(paramBoolean);
    this.m_k[3].setEnabled(!paramBoolean);
    this.m_k[4].setEnabled(!paramBoolean);
  }

  public void paint(Graphics paramGraphics) {
    Color color = getBackground();
    paramGraphics.setColor(color);
    paramGraphics.fillRect(0, 0, (getSize()).width, (getSize()).height);
    String str = "Infragistics " + this.m_name;
    paramGraphics.setFont(new Font("SansSerif", 1, 18));
    paramGraphics.setColor(Color.white);
    paramGraphics.drawString(str, 3, 14);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawString(str, 4, 15);
    paramGraphics.setFont(new Font("Courier", 1, 13));
    paramGraphics.drawString("www.infragistics.com", 5, 29);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top - 48, 270, 43), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 4, 95, 74), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(107, this.m_top + 4, 168, 29), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 87, 95, 80), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 177, 95, 80), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 267, 270, 47), 12386493, color);
    try {
      super.paint(paramGraphics);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (this.m_id > -2)
      doEvent(paramActionEvent.getSource(), 0);
  }

  public void itemStateChanged(ItemEvent paramItemEvent) {
    if (this.m_id > -2)
      doEvent(paramItemEvent.getSource(), 100);
  }

  public void textValueChanged(TextEvent paramTextEvent) {
    if (this.m_id > -2)
      doEvent(paramTextEvent.getSource(), 200);
  }

  public void doEvent(Object paramObject, int paramInt) {
    PVButton pVButton = this.m_targ;
    if (paramInt == 0) {
      Color color = null;
      if (paramObject == this.m_clr) {
        if (this.m_clr.m_i > 1)
          return;
        color = this.m_clr.m_old;
        switch (this.m_id) {
          case 0:
            pVButton.setForeground(color);
            break;
          case 1:
            pVButton.setBackground(color);
            break;
          case 2:
            pVButton.setMouseInsideForeground(color);
            break;
          case 3:
            pVButton.setBorderColor(color);
            break;
          case 4:
            pVButton.setShadowColor(color);
            break;
          case 5:
            pVButton.setText3DColor(color);
            break;
        }
      } else {
        while (paramObject != this.m_b[paramInt]) {
          if (++paramInt > 5) {
            fix();
            return;
          }
        }
        switch (this.m_id = paramInt) {
          case 0:
            color = pVButton.getForeground();
            break;
          case 1:
            color = pVButton.getBackground();
            break;
          case 2:
            color = pVButton.getMouseInsideForeground();
            break;
          case 3:
            color = pVButton.getBorderColor();
            break;
          case 4:
            color = pVButton.getShadowColor();
            break;
          case 5:
            color = pVButton.getText3DColor();
            break;
        }
        if (this.m_clr == null) {
          this.m_clr = new PVColorChooser(this.m_b[this.m_id].getLabel(), color);
          this.m_clr.addActionListener(this);
        } else {
          this.m_clr.setColor(this.m_b[this.m_id].getLabel(), color);
        }
      }
    }
    if (paramInt == 100) {
      paramInt = 0;
      while (paramObject != this.m_k[paramInt]) {
        if (++paramInt > 7) {
          paramInt = 100;
          break;
        }
      }
      if (paramInt <= 7) {
        int i = this.m_k[paramInt].getSelectedIndex();
        if (paramInt == 0)
          switch (i) {
            case 1:
              i = 196609;
              break;
            case 2:
              i = 163842;
              break;
            case 3:
              i = 1474578;
              break;
            case 4:
              i = 11960466;
              break;
            case 5:
              i = 95847570;
              break;
            case 6:
              i = 2031633;
              break;
            case 7:
              i = 9994377;
              break;
            case 8:
              i = 81036937;
              break;
            case 9:
              i = 634795091;
              break;
            case 10:
              i = 1056388057;
              break;
            case 11:
              i = 937369209;
              break;
            case 12:
              i = 1874767546;
              break;
            case 13:
              i = 1420252785;
              break;
            case 14:
              i = 1311728473;
              break;
          }
        switch (paramInt) {
          case 0:
            pVButton.setBorderStyle(i);
            break;
          case 1:
            pVButton.setShadow(i);
            this.m_k[2].setEnabled(!(i <= 0));
            break;
          case 2:
            pVButton.setShadowWidth(i);
            break;
          case 3:
            pVButton.setHorizontal3D(i - 2);
            break;
          case 4:
            pVButton.setVertical3D(i - 2);
            break;
          case 5:
            pVButton.setImageAlignment(i);
            break;
          case 6:
            this.m_t[2].setText(pVButton.getFileImageAt(i));
            break;
          case 7:
            pVButton.setTriangle(i);
            break;
        }
        paramInt = 1000;
      }
    }
    if (paramInt == 200) {
      if (paramObject == this.m_t[0]) {
        if (pVButton.getText().compareTo(pVButton.getTextPressed()) == 0)
          this.m_t[1].setText(this.m_t[0].getText());
        pVButton.setText(this.m_t[0].getText());
        if (pVButton.getAutoResize())
          paramInt = 1000;
      }
      if (paramObject == this.m_t[1])
        pVButton.setTextPressed(this.m_t[1].getText());
      if (paramObject == this.m_t[2])
        pVButton.setFileImageAt(this.m_t[2].getText(), this.m_k[6].getSelectedIndex());
    }
    if (paramInt == 1000) {
      pVButton.setSize(this.m_width, this.m_height);
      pVButton.getParent().doLayout();
    }
    pVButton.paint(null);
  }

  void fix() {
    int i = this.m_c.length;
    while (i-- > 0) {
      boolean bool = this.m_c[i].getState();
      switch (i) {
        case 0:
          if (bool != this.m_targ.getAureole())
            setAur(bool);
        case 1:
          if (bool != this.m_targ.getThreeButtonStates())
            this.m_targ.setThreeButtonStates(bool);
        case 2:
          if (bool != this.m_targ.getCanKeepPressed())
            this.m_targ.setCanKeepPressed(bool);
        case 3:
          if (bool != this.m_targ.getCanGetFocus())
            this.m_targ.setCanGetFocus(bool);
        case 4:
          if (bool != this.m_targ.getSwitchColorsOnPress())
            this.m_targ.setSwitchColorsOnPress(bool);
        case 5:
          if (bool != this.m_targ.getSwitchShadowOnPress())
            this.m_targ.setSwitchShadowOnPress(bool);
        case 6:
          if (bool != this.m_targ.getExtraPoppedUp())
            this.m_targ.setExtraPoppedUp(bool);
        case 7:
          if (bool != this.m_targ.getMultiLine())
            this.m_targ.setMultiLine(bool);
        case 8:
          if (bool != this.m_targ.getAutoResize())
            this.m_targ.setAutoResize(bool);
        case 9:
          if (bool != this.m_targ.getEnableEnter())
            this.m_targ.setEnableEnter(bool);
        case 10:
          if (bool != this.m_targ.getEnableSpace())
            this.m_targ.setEnableSpace(bool);
        case 11:
          if (bool != this.m_targ.getEnableEscape())
            this.m_targ.setEnableEscape(bool);
        case 12:
          if (bool != this.m_targ.getFocusRect())
            this.m_targ.setFocusRect(bool);
        case 13:
          if (bool != this.m_targ.getFocusRectAroundText())
            this.m_targ.setFocusRectAroundText(bool);
      }
    }
    this.m_sup.firePropertyChange("", (Object)null, (Object)null);
  }

  public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
    this.m_sup.addPropertyChangeListener(paramPropertyChangeListener);
  }

  public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
    this.m_sup.removePropertyChangeListener(paramPropertyChangeListener);
  }
}
