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

public class PVSpinPlusCustomizer extends Container implements Customizer, TextListener, ItemListener, ActionListener {
  PropertyChangeSupport m_sup = new PropertyChangeSupport(this);

  PVColorChooser m_clr;

  PVButton[] m_sB;

  PVSpinPlus m_targ;

  TextField[] m_t;

  Choice[] m_k;

  Checkbox[] m_c;

  Label[] m_l;

  Button[] m_b;

  int m_id = -100;

  public PVSpinPlusCustomizer() {
    setLayout(null);
    setSize(295, 450);
  }

  public Dimension getPreferredSize() {
    return new Dimension(295, 450);
  }

  public void removeNotify() {
    if (this.m_clr != null) {
      this.m_clr.dispose();
      this.m_clr = null;
    }
    super.removeNotify();
    fix();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    this.m_targ = (PVSpinPlus)paramObject;
    this.m_sB = (PVButton[])this.m_targ.getButtons();
    Font font = new Font("SansSerif", 0, 11);
    this.m_l = new Label[19];
    this.m_l[0] = new Label("Style");
    this.m_l[1] = new Label("Delay (ms)");
    this.m_l[2] = new Label("Button Size");
    this.m_l[3] = new Label("Border Style");
    this.m_l[4] = new Label(" Spin Buttons");
    this.m_l[5] = new Label("Text1");
    this.m_l[6] = new Label("Text2");
    this.m_l[7] = new Label(" Text 3D effect");
    this.m_l[8] = new Label("Horizontal");
    this.m_l[9] = new Label("Vertical");
    this.m_l[10] = new Label("Object");
    this.m_l[11] = new Label("PVObject");
    this.m_l[12] = new Label(" Colors");
    this.m_l[13] = new Label("Notes:");
    this.m_l[14] = new Label("Spin may not be active at design time.");
    this.m_l[15] = new Label("To set properties of editor: use getObject()");
    this.m_l[16] = new Label("Example:");
    this.m_l[17] = new Label("PVDate d = (pw.awt.PVDate)spin1.getObject();");
    this.m_l[18] = new Label("d.setBackground(Color.pink);");
    this.m_c = new Checkbox[7];
    this.m_c[0] = new Checkbox("Default Images", this.m_targ.getDefaultImages());
    this.m_c[1] = new Checkbox("SpeedUp", this.m_targ.getSpeedUp());
    this.m_c[2] = new Checkbox("Switch Colors", this.m_sB[0].getSwitchColorsOnPress());
    this.m_c[3] = new Checkbox("Extra Popped Up", this.m_sB[0].getExtraPoppedUp());
    this.m_c[4] = new Checkbox("3 Button States", this.m_sB[0].getThreeButtonStates());
    this.m_c[5] = new Checkbox("Aureole", this.m_sB[0].getAureole());
    this.m_c[6] = new Checkbox("AutoSpin with PVObject", this.m_targ.getAutoSpin());
    this.m_t = new TextField[2];
    int i;
    for (i = 0; i < 2; i++) {
      this.m_t[i] = new TextField(this.m_sB[i].getText());
      this.m_t[i].setFont(font);
      this.m_t[i].addTextListener(this);
    }
    this.m_k = new Choice[7];
    for (i = 0; i < 19; i++) {
      add(this.m_l[i]);
      this.m_l[i].setFont(font);
      if (i < 7) {
        this.m_c[i].setFont(font);
        this.m_k[i] = new Choice();
        add(this.m_k[i]);
        this.m_k[i].setFont(font);
        this.m_k[i].addItemListener(this);
        if (i == 2) {
          add(this.m_c[0]);
          add(this.m_c[1]);
        }
        if (i == 3) {
          add(this.m_c[2]);
          add(this.m_c[3]);
          add(this.m_c[4]);
          add(this.m_t[0]);
          add(this.m_t[1]);
        }
      }
    }
    add(this.m_c[5]);
    this.m_k[0].addItem("Horizontal-Center");
    this.m_k[0].addItem("Horizontal-Right");
    this.m_k[0].addItem("Horizontal-Left");
    this.m_k[0].addItem("Horizontal-Bottom");
    this.m_k[0].addItem("Horizontal-Top");
    this.m_k[0].addItem("Vertical-Center");
    this.m_k[0].addItem("Vertical-Right");
    this.m_k[0].addItem("Vertical-Left");
    this.m_k[0].addItem("Vertical-Bottom");
    this.m_k[0].addItem("Vertical-Top");
    i = this.m_targ.getStyle();
    if (i > 4)
      i -= 3;
    this.m_k[0].select(i);
    i = 5;
    byte b = 0;
    int j = this.m_targ.getDelay();
    while (i < 2005) {
      this.m_k[1].addItem(String.valueOf(i));
      if (j <= i && j > b)
        j = b;
      if (b++ % 3 < 2) {
        i *= 2;
        continue;
      }
      i = i * 5 / 2;
    }
    if (j > --b)
      j = b;
    this.m_k[1].select(j);
    for (i = 0; i < 126; i++)
      this.m_k[2].addItem(String.valueOf(i));
    this.m_k[2].select(this.m_targ.getButtonWidth());
    this.m_k[3].addItem("Unknown");
    this.m_k[3].addItem("None");
    this.m_k[3].addItem("Simple");
    this.m_k[3].addItem("Simple1");
    this.m_k[3].addItem("Simple2");
    this.m_k[3].addItem("Simple3");
    this.m_k[3].addItem("Simple4");
    this.m_k[3].addItem("Standard");
    this.m_k[3].addItem("Standard1");
    this.m_k[3].addItem("Standard2");
    this.m_k[3].addItem("Vague");
    this.m_k[3].addItem("Double");
    this.m_k[3].addItem("Triple");
    this.m_k[3].addItem("Triple2");
    i = 0;
    switch (this.m_targ.getButtonBorder()) {
      case 0:
        i = 1;
        break;
      case 196609:
        i = 2;
        break;
      case 163842:
        i = 3;
        break;
      case 1474578:
        i = 4;
        break;
      case 11960466:
        i = 5;
        break;
      case 95847570:
        i = 6;
        break;
      case 2031633:
        i = 7;
        break;
      case 9994377:
        i = 8;
        break;
      case 81036937:
        i = 9;
        break;
      case 634795091:
        i = 10;
        break;
      case 1056388057:
        i = 11;
        break;
      case 937369209:
        i = 12;
        break;
      case 1874767546:
        i = 13;
        break;
    }
    this.m_k[3].select(i);
    this.m_k[4].addItem("Left 2");
    this.m_k[4].addItem("Left 1");
    this.m_k[4].addItem("None");
    this.m_k[4].addItem("Right 1");
    this.m_k[4].addItem("Right 2");
    this.m_k[4].select(this.m_sB[0].getHorizontal3D() + 2);
    this.m_k[5].addItem("Top 2");
    this.m_k[5].addItem("Top 1");
    this.m_k[5].addItem("None");
    this.m_k[5].addItem("Bottom 1");
    this.m_k[5].addItem("Bottom 2");
    this.m_k[5].select(this.m_sB[0].getVertical3D() + 2);
    this.m_l[0].setBounds(14, 42, 67, 12);
    this.m_k[0].setBounds(12, 54, 108, 20);
    this.m_l[1].setBounds(137, 42, 57, 12);
    this.m_k[1].setBounds(135, 54, 61, 20);
    this.m_l[2].setBounds(206, 42, 60, 12);
    this.m_k[2].setBounds(205, 54, 61, 20);
    this.m_c[0].setBounds(12, 77, 100, 16);
    this.m_c[1].setBounds(135, 77, 70, 16);
    this.m_l[3].setBounds(14, 146, 67, 12);
    this.m_k[3].setBounds(12, 158, 108, 20);
    for (i = 2; i < 5; i++)
      this.m_c[i].setBounds(12, 230 - (i - 2) * 17, 110, 16);
    this.m_l[4].setBounds(91, 134, 65, 12);
    this.m_l[5].setBounds(137, 146, 50, 12);
    this.m_t[0].setBounds(135, 158, 60, 19);
    this.m_l[6].setBounds(207, 146, 50, 12);
    this.m_t[1].setBounds(205, 158, 60, 19);
    this.m_l[7].setBounds(137, 182, 74, 12);
    this.m_l[8].setBounds(137, 195, 55, 12);
    this.m_k[4].setBounds(135, 207, 60, 20);
    this.m_l[9].setBounds(207, 195, 55, 12);
    this.m_k[5].setBounds(205, 207, 60, 20);
    this.m_c[5].setBounds(135, 230, 80, 16);
    this.m_k[6].addItem("Null");
    this.m_k[6].addItem("Numeric");
    this.m_k[6].addItem("Currency");
    this.m_k[6].addItem("Date");
    this.m_k[6].addItem("DateLong");
    this.m_k[6].addItem("Time");
    this.m_k[6].addItem("DatePlus");
    this.m_k[6].addItem("DateLongPlus");
    this.m_k[6].addItem("NumericPlus");
    this.m_k[6].select(this.m_targ.getPVObject());
    this.m_l[11].setBounds(14, 302, 110, 12);
    this.m_k[6].setBounds(12, 315, 110, 20);
    this.m_l[12].setBounds(20, 257, 50, 12);
    for (i = 13; i < 19; i++) {
      this.m_l[i].setBounds(14, 358 + (i - 13) * 14, 280, 12);
      this.m_l[i].setVisible(false);
    }
    this.m_b = new Button[4];
    this.m_b[0] = new Button("Foreground");
    this.m_b[1] = new Button("Background");
    this.m_b[2] = new Button("Border");
    this.m_b[3] = new Button("Update");
    for (i = 0; i < 4; i++) {
      this.m_b[i].setFont(font);
      this.m_b[i].addActionListener(this);
      add(this.m_b[i]);
      if (i == 3) {
        this.m_b[i].setBounds(194, 0, 80, 17);
      } else {
        this.m_b[i].setBounds(16 + i * 83, 270, 78, 16);
      }
    }
    add(this.m_c[6]);
    this.m_c[6].setBounds(12, 340, 140, 16);
    enableTxt();
    this.m_id = -1;
  }

  public void textValueChanged(TextEvent paramTextEvent) {
    if (this.m_id > -2)
      doText(paramTextEvent.getSource());
  }

  public void itemStateChanged(ItemEvent paramItemEvent) {
    if (this.m_id > -2)
      doItem(paramItemEvent.getSource());
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (this.m_id > -2)
      doBut(paramActionEvent.getSource());
  }

  void enableTxt() {
    boolean bool = (this.m_t[0].getText().length() <= 0 && this.m_t[1].getText().length() <= 0) ? false : true;
    this.m_c[2].setEnabled(bool);
    this.m_c[5].setEnabled(bool);
    this.m_k[4].setEnabled(bool);
    this.m_k[5].setEnabled(bool);
  }

  public void paint(Graphics paramGraphics) {
    Color color = getBackground();
    paramGraphics.setColor(color);
    paramGraphics.fillRect(0, 0, (getSize()).width, (getSize()).height);
    paramGraphics.setFont(new Font("SansSerif", 1, 18));
    paramGraphics.setColor(Color.white);
    paramGraphics.drawString("Infragistics Spin", 4, 14);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawString("Infragistics Spin", 5, 15);
    paramGraphics.setFont(new Font("Courier", 1, 13));
    paramGraphics.drawString("www.infragistics.com", 6, 29);
    PVBorder.draw(paramGraphics, new Rectangle(5, 38, 269, 62), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 187, 269, 67), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(126, 139, 148, 115), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 139, 124, 115), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 251, 269, 45), 12386493, color);
    try {
      super.paint(paramGraphics);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  void doItem(Object paramObject) {
    int i = 0;
    i = 0;
    while (paramObject != this.m_k[i]) {
      if (++i > 6)
        return;
    }
    int j = this.m_k[i].getSelectedIndex();
    switch (i) {
      case 0:
        if (j > 4)
          j += 3;
        this.m_targ.setStyle(j);
        return;
      case 1:
        for (i = (j > 0) ? 10 : 5; j > 1; i = i * 5 / 2) {
          if (j-- % 3 < 2) {
            i *= 2;
            continue;
          }
        }
        this.m_targ.setDelay(i);
        i = 12;
        return;
      case 2:
        this.m_targ.setButtonWidth(j);
        return;
      case 3:
        switch (j) {
          case 1:
            j = 0;
            break;
          case 2:
            j = 196609;
            break;
          case 3:
            j = 163842;
            break;
          case 4:
            j = 1474578;
            break;
          case 5:
            j = 11960466;
            break;
          case 6:
            j = 95847570;
            break;
          case 7:
            j = 2031633;
            break;
          case 8:
            j = 9994377;
            break;
          case 9:
            j = 81036937;
            break;
          case 10:
            j = 634795091;
            break;
          case 11:
            j = 1056388057;
            break;
          case 12:
            j = 937369209;
            break;
          case 13:
            j = 1874767546;
            break;
          default:
            return;
        }
        this.m_targ.setButtonBorder(j);
        return;
      case 4:
        this.m_sB[0].setHorizontal3D(j - 2);
        this.m_sB[1].setHorizontal3D(j - 2);
        return;
      case 5:
        this.m_sB[0].setVertical3D(j - 2);
        this.m_sB[1].setVertical3D(j - 2);
        return;
      case 6:
        if (this.m_targ.setPVObject(j)) {
          for (i = 13; i < 19; i++)
            this.m_l[i].setVisible(!(j == 0));
          Dimension dimension = this.m_targ.getSize();
          if (j > 0 && dimension.width < 30) {
            this.m_targ.setSize(100, dimension.height);
            return;
          }
        }
        break;
    }
  }

  void doText(Object paramObject) {
    if (!(paramObject instanceof TextField))
      return;
    this.m_sB[(paramObject == this.m_t[0]) ? 0 : 1].setText(((TextField)paramObject).getText());
    enableTxt();
  }

  void doBut(Object paramObject) {
    Color color = null;
    if (paramObject == this.m_clr) {
      if (this.m_clr.m_i > 1)
        return;
      color = this.m_clr.m_old;
      for (byte b1 = 0; b1 < 2; b1++) {
        switch (this.m_id) {
          case 0:
            this.m_sB[b1].setForeground(color);
            break;
          case 1:
            this.m_sB[b1].setBackground(color);
            break;
          case 2:
            this.m_sB[b1].setBorderColor(color);
            break;
        }
      }
      return;
    }
    byte b = 0;
    while (paramObject != this.m_b[b]) {
      if (++b > 2) {
        fix();
        return;
      }
    }
    switch (this.m_id = b) {
      case 0:
        color = this.m_sB[0].getForeground();
        break;
      case 1:
        color = this.m_sB[0].getBackground();
        break;
      case 2:
        color = this.m_sB[0].getBorderColor();
        break;
    }
    if (this.m_clr == null) {
      this.m_clr = new PVColorChooser(this.m_b[b].getLabel(), color);
      this.m_clr.addActionListener(this);
      return;
    }
    this.m_clr.setColor(this.m_b[b].getLabel(), color);
  }

  void fix() {
    int i = this.m_c.length;
    while (i-- > 0) {
      boolean bool = this.m_c[i].getState();
      switch (i) {
        case 0:
          if (bool != this.m_targ.getDefaultImages())
            this.m_targ.setDefaultImages(bool);
        case 1:
          if (bool != this.m_targ.getSpeedUp())
            this.m_targ.setSpeedUp(bool);
        case 2:
          if (bool != this.m_sB[0].getSwitchColorsOnPress()) {
            this.m_sB[0].setSwitchColorsOnPress(bool);
            this.m_sB[1].setSwitchColorsOnPress(bool);
          }
        case 3:
          if (bool != this.m_sB[0].getExtraPoppedUp()) {
            this.m_sB[0].setExtraPoppedUp(bool);
            this.m_sB[1].setExtraPoppedUp(bool);
          }
        case 4:
          if (bool != this.m_sB[0].getThreeButtonStates()) {
            this.m_sB[0].setThreeButtonStates(bool);
            this.m_sB[1].setThreeButtonStates(bool);
          }
        case 5:
          if (bool != this.m_sB[0].getAureole()) {
            this.m_sB[0].setAureole(bool);
            this.m_sB[1].setAureole(bool);
          }
        case 6:
          if (bool != this.m_targ.getAutoSpin())
            this.m_targ.setAutoSpin(bool);
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
