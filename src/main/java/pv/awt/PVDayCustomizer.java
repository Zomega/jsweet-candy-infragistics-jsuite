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

public class PVDayCustomizer extends Container implements Customizer, ItemListener, ActionListener, TextListener {
  PropertyChangeSupport m_sup = new PropertyChangeSupport(this);

  PVDay m_targ;

  PVColorChooser m_clr;

  TextField[] m_t;

  Checkbox[] m_c;

  Choice[] m_k;

  Button[] m_b;

  int m_id = -100;

  public PVDayCustomizer() {
    setLayout(null);
    setSize(297, 415);
  }

  public Dimension getPreferredSize() {
    return new Dimension(297, 415);
  }

  public void removeNotify() {
    if (this.m_clr != null)
      this.m_clr.dispose();
    this.m_clr = null;
    super.removeNotify();
    fix();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    this.m_targ = (PVDay)paramObject;
    int j = 0;
    Label[] arrayOfLabel = new Label[14];
    arrayOfLabel[0] = new Label(" Borders & Shadow");
    arrayOfLabel[1] = new Label("Border");
    arrayOfLabel[2] = new Label("Shadow Style");
    arrayOfLabel[3] = new Label("Shadow Width");
    arrayOfLabel[4] = new Label("Spin Button Border");
    arrayOfLabel[5] = new Label("Row Label Border");
    arrayOfLabel[6] = new Label(" Text3D of Caption");
    arrayOfLabel[7] = new Label(" Text3D of Row Labels");
    arrayOfLabel[8] = new Label("SpinDate Height:");
    arrayOfLabel[9] = new Label("SpinTime Height:");
    arrayOfLabel[10] = new Label("Start:");
    arrayOfLabel[11] = new Label("End:");
    arrayOfLabel[12] = new Label("Delta:");
    arrayOfLabel[13] = new Label(" Colors");
    Font font = new Font("SansSerif", 0, 11);
    this.m_k = new Choice[7];
    this.m_t = new TextField[5];
    this.m_c = new Checkbox[10];
    this.m_c[0] = new Checkbox("Drop-down Calendar", this.m_targ.getCalendar());
    this.m_c[1] = new Checkbox("12/24 hours", this.m_targ.getTwelveHours());
    this.m_c[2] = new Checkbox("Day of the Week", this.m_targ.getShowDOW());
    this.m_c[3] = new Checkbox("Show Grid Lines", this.m_targ.getGrid());
    this.m_c[4] = new Checkbox("Focus Rectangle", this.m_targ.getFocusRectangle());
    this.m_c[5] = new Checkbox("Alarm Dialog", this.m_targ.getEnableAlarmDialog());
    this.m_c[6] = new Checkbox("Edit Dialog", this.m_targ.getEnableEditDialog());
    this.m_c[7] = new Checkbox("Dialog on Double Click", this.m_targ.getDialogOnDoubleClick());
    this.m_c[8] = new Checkbox("Editable", this.m_targ.getEditable());
    this.m_c[9] = new Checkbox("Enable Delete Key", this.m_targ.getEnableDeleteKey());
    int i;
    for (i = 0; i < 10; i++) {
      add(this.m_c[i]);
      this.m_c[i].setFont(font);
      this.m_c[i].setBounds(20 + i / 5 * 140, 240 + i % 5 * 16, 135, 15);
    }
    for (i = 0; i < 14; i++) {
      add(arrayOfLabel[i]);
      arrayOfLabel[i].setFont(font);
      if (i <= 6) {
        this.m_k[i] = new Choice();
        add(this.m_k[i]);
        this.m_k[i].addItemListener(this);
        this.m_k[i].setFont(font);
      }
    }
    String str = null;
    for (i = 0; i < 5; i++) {
      switch (i) {
        case 0:
          j = this.m_targ.getSpinDateHeight();
          break;
        case 1:
          j = this.m_targ.getSpinTimeHeight();
          break;
        case 2:
          str = this.m_targ.string(this.m_targ.getTimeAt(this.m_targ.m_start));
          break;
        case 3:
          str = this.m_targ.string(this.m_targ.getTimeAt(this.m_targ.m_end));
          break;
        case 4:
          j = this.m_targ.getDelta();
          break;
      }
      if ((i & 0x2) == 0)
        str = String.valueOf(j);
      this.m_t[i] = new TextField(str);
      this.m_t[i].setFont(font);
      add(this.m_t[i]);
      this.m_t[i].addTextListener(this);
    }
    this.m_k[0].addItem("None");
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Plain " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Raised " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Sunken " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Outdent " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Indent " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Picture " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Raised++ " + i);
    for (i = 1; i < 11; i++)
      this.m_k[0].addItem("Sunken++ " + i);
    i = this.m_targ.getBorderStyle();
    if (i >= 0 && i < 81)
      this.m_k[0].select(i);
    this.m_k[1].addItem("None");
    this.m_k[1].addItem("Right-Bottom");
    this.m_k[1].addItem("Left-Bottom");
    this.m_k[1].addItem("Left-Top");
    this.m_k[1].addItem("Right-Top");
    this.m_k[1].select(this.m_targ.getShadow());
    for (i = 0; i < 16; i++)
      this.m_k[2].addItem(String.valueOf(i));
    this.m_k[2].select(this.m_targ.getShadowWidth());
    addItems(this.m_k[3]);
    this.m_k[3].select(this.m_targ.getSpinBorder());
    addItems(this.m_k[4]);
    this.m_k[4].select(this.m_targ.getRowLabelBorder());
    this.m_k[5].addItem("None");
    this.m_k[5].addItem("Indent");
    this.m_k[5].addItem("Outdent");
    this.m_k[5].select(this.m_targ.getCaptionText3D());
    this.m_k[6].addItem("None");
    this.m_k[6].addItem("Indent");
    this.m_k[6].addItem("Outdent");
    this.m_k[6].select(this.m_targ.getRowLabelText3D());
    arrayOfLabel[13].setBounds(13, 322, 41, 12);
    this.m_b = new Button[13];
    this.m_b[0] = new Button("Foreground");
    this.m_b[1] = new Button("Background");
    this.m_b[2] = new Button("Caption Foreground");
    this.m_b[3] = new Button("Client Area");
    this.m_b[4] = new Button("Selection");
    this.m_b[5] = new Button("RowLabel Fore");
    this.m_b[6] = new Button("RowLabel Back");
    this.m_b[7] = new Button("Appointment");
    this.m_b[8] = new Button("Text3D");
    this.m_b[9] = new Button("Grid");
    this.m_b[10] = new Button("Border");
    this.m_b[11] = new Button("Spin");
    this.m_b[12] = new Button("Update");
    for (i = 0; i < 13; i++) {
      this.m_b[i].setFont(font);
      add(this.m_b[i]);
      this.m_b[i].addActionListener(this);
      if (i == 12) {
        this.m_b[i].setBounds(213, 0, 80, 18);
      } else {
        this.m_b[i].setBounds(13 + i / 4 * 110, 335 + i % 4 * 18, 55 + (1 - i / 8) * 52, 16);
      }
    }
    arrayOfLabel[0].setBounds(22, 30, 99, 12);
    arrayOfLabel[1].setBounds(16, 42, 75, 12);
    this.m_k[0].setBounds(12, 54, 80, 20);
    arrayOfLabel[2].setBounds(113, 42, 80, 12);
    this.m_k[1].setBounds(111, 54, 85, 20);
    arrayOfLabel[3].setBounds(210, 42, 75, 12);
    this.m_k[2].setBounds(210, 54, 75, 20);
    arrayOfLabel[4].setBounds(18, 76, 110, 12);
    this.m_k[3].setBounds(14, 88, 130, 20);
    arrayOfLabel[5].setBounds(160, 76, 110, 12);
    this.m_k[4].setBounds(156, 88, 130, 20);
    arrayOfLabel[6].setBounds(18, 118, 94, 12);
    this.m_k[5].setBounds(14, 130, 130, 20);
    arrayOfLabel[7].setBounds(160, 118, 114, 12);
    this.m_k[6].setBounds(156, 130, 130, 20);
    arrayOfLabel[8].setBounds(20, 172, 87, 12);
    this.m_t[0].setBounds(107, 168, 36, 20);
    arrayOfLabel[9].setBounds(164, 172, 85, 12);
    this.m_t[1].setBounds(249, 168, 36, 20);
    arrayOfLabel[10].setBounds(12, 209, 27, 12);
    this.m_t[2].setBounds(39, 205, 65, 20);
    arrayOfLabel[11].setBounds(115, 209, 25, 12);
    this.m_t[3].setBounds(140, 205, 65, 20);
    arrayOfLabel[12].setBounds(214, 209, 31, 12);
    this.m_t[4].setBounds(245, 205, 40, 20);
    this.m_id = -1;
  }

  void addItems(Choice paramChoice) {
    String str = "3D ";
    paramChoice.addItem("None");
    paramChoice.addItem("Black");
    paramChoice.addItem(String.valueOf(str) + "thin");
    paramChoice.addItem(String.valueOf(str) + "thin sharp");
    paramChoice.addItem(str);
    paramChoice.addItem(String.valueOf(str) + "sharp");
    paramChoice.addItem(String.valueOf(str) + "wide");
    paramChoice.addItem(String.valueOf(str) + "double");
    paramChoice.addItem(String.valueOf(str = "3 states, ") + "dark");
    paramChoice.addItem(String.valueOf(str) + "black");
    paramChoice.addItem(String.valueOf(str = String.valueOf(str) + "3D ") + "thin");
    paramChoice.addItem(String.valueOf(str) + "thin sharp");
    paramChoice.addItem(str);
    paramChoice.addItem(String.valueOf(str) + "sharp");
    paramChoice.addItem(String.valueOf(str) + "wide");
    paramChoice.addItem(String.valueOf(str) + "double");
  }

  public void itemStateChanged(ItemEvent paramItemEvent) {
    if (this.m_id > -2)
      doItem(paramItemEvent.getSource());
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (this.m_id > -2)
      doButt(paramActionEvent.getSource());
  }

  public void textValueChanged(TextEvent paramTextEvent) {
    if (this.m_id < -1)
      return;
    int i = PVDay.minutes(((TextField)paramTextEvent.getSource()).getText());
    if (i < 0)
      return;
    byte b = -1;
    while (++b < 5 && paramTextEvent.getSource() != this.m_t[b]);
    if (b == 0) {
      this.m_targ.setSpinDateHeight(i);
      return;
    }
    if (b == 1) {
      this.m_targ.setSpinTimeHeight(i);
      return;
    }
    if ((i = PVDay.minutes(this.m_t[4].getText())) > 9)
      this.m_targ.setDelta(i);
    if ((i = PVDay.minutes(this.m_t[2].getText())) >= 0)
      this.m_targ.setStartTime(i);
    if ((i = PVDay.minutes(this.m_t[3].getText())) > 9)
      this.m_targ.setEndTime(i);
  }

  public void paint(Graphics paramGraphics) {
    Color color = getBackground();
    paramGraphics.setColor(color);
    paramGraphics.fillRect(0, 0, (getSize()).width, (getSize()).height);
    paramGraphics.setFont(new Font("SansSerif", 1, 18));
    paramGraphics.setColor(Color.white);
    paramGraphics.drawString("Infragistics Day", 10, 13);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawString("Infragistics Day", 11, 14);
    paramGraphics.setFont(new Font("Courier", 1, 13));
    paramGraphics.drawString("www.infragistics.com", 12, 26);
    PVBorder.draw(paramGraphics, new Rectangle(5, 34, 288, 80), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 122, 288, 35), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 162, 288, 32), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 199, 288, 32), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 327, 290, 84), 12386493, color);
    try {
      super.paint(paramGraphics);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  protected void doItem(Object paramObject) {
    byte b = 0;
    int i = 0;
    b = 0;
    while (paramObject != this.m_k[b]) {
      if (++b > 6)
        return;
    }
    i = this.m_k[b].getSelectedIndex();
    switch (b) {
      case 0:
        this.m_targ.setBorderStyle(i);
        break;
      case 1:
        this.m_targ.setShadow(i);
        break;
      case 2:
        this.m_targ.setShadowWidth(i);
        break;
      case 3:
        this.m_targ.setSpinBorder(i);
        break;
      case 4:
        this.m_targ.setRowLabelBorder(i);
      case 5:
        this.m_targ.setCaptionText3D(i);
        break;
      case 6:
        this.m_targ.setRowLabelText3D(i);
        break;
    }
    this.m_targ.paint(null);
  }

  protected void doButt(Object paramObject) {
    Color color = null;
    if (paramObject == this.m_clr) {
      if (this.m_clr.m_i > 1)
        return;
      color = this.m_clr.m_old;
      switch (this.m_id) {
        case 0:
          this.m_targ.setForeground(color);
          break;
        case 1:
          this.m_targ.setBackground(color);
          break;
        case 2:
          this.m_targ.setCaptionForeground(color);
          break;
        case 3:
          this.m_targ.setRowBackground(color);
          break;
        case 4:
          this.m_targ.setSelectionColor(color);
          break;
        case 5:
          this.m_targ.setRowLabelForeground(color);
          break;
        case 6:
          this.m_targ.setRowLabelBackground(color);
          break;
        case 7:
          this.m_targ.setAppointmentBackground(color);
          break;
        case 8:
          this.m_targ.setText3DColor(color);
          break;
        case 9:
          this.m_targ.setGridColor(color);
          break;
        case 10:
          this.m_targ.setBorderColor(color);
          break;
        case 11:
          this.m_targ.setSpinColor(color);
          break;
      }
      this.m_targ.paint(null);
      return;
    }
    byte b = 0;
    while (paramObject != this.m_b[b]) {
      if (++b > 11) {
        fix();
        return;
      }
    }
    switch (this.m_id = b) {
      case 0:
        color = this.m_targ.getForeground();
        break;
      case 1:
        color = this.m_targ.getBackground();
        break;
      case 2:
        color = this.m_targ.getCaptionForeground();
        break;
      case 3:
        color = this.m_targ.getRowBackground();
        break;
      case 4:
        color = this.m_targ.getSelectionColor();
        break;
      case 5:
        color = this.m_targ.getRowLabelForeground();
        break;
      case 6:
        color = this.m_targ.getRowLabelBackground();
        break;
      case 7:
        color = this.m_targ.getAppointmentBackground();
        break;
      case 8:
        color = this.m_targ.getText3DColor();
        break;
      case 9:
        color = this.m_targ.getGridColor();
        break;
      case 10:
        color = this.m_targ.getBorderColor();
        break;
      case 11:
        color = this.m_targ.getSpinColor();
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
          if (bool != this.m_targ.getCalendar())
            this.m_targ.setCalendar(bool);
        case 1:
          if (bool != this.m_targ.getTwelveHours())
            this.m_targ.setTwelveHours(bool);
        case 2:
          if (bool != this.m_targ.getShowDOW())
            this.m_targ.setShowDOW(bool);
        case 3:
          if (bool != this.m_targ.getGrid())
            this.m_targ.setGrid(bool);
        case 4:
          if (bool != this.m_targ.getFocusRectangle())
            this.m_targ.setFocusRectangle(bool);
        case 5:
          if (bool != this.m_targ.getEnableAlarmDialog())
            this.m_targ.setEnableAlarmDialog(bool);
        case 6:
          if (bool != this.m_targ.getEnableEditDialog())
            this.m_targ.setEnableEditDialog(bool);
        case 7:
          if (bool != this.m_targ.getDialogOnDoubleClick())
            this.m_targ.setDialogOnDoubleClick(bool);
        case 8:
          if (bool != this.m_targ.getEditable())
            this.m_targ.setEditable(bool);
        case 9:
          if (bool != this.m_targ.getEnableDeleteKey())
            this.m_targ.setEnableDeleteKey(bool);
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
