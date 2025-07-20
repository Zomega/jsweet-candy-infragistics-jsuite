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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import pv.util.PVBorder;
import pv.util.PVColorChooser;

public class PVCalendarCustomizer extends Container implements Customizer, ItemListener, ActionListener, FocusListener {
  PropertyChangeSupport m_sup = new PropertyChangeSupport(this);

  PVCalendar m_targ;

  PVColorChooser m_clr;

  TextField[] m_t;

  Checkbox[] m_c;

  Choice[] m_k;

  Button[] m_b;

  int m_width = 200;

  int m_height = 200;

  int m_id = -100;

  public PVCalendarCustomizer() {
    setLayout(null);
    setSize(297, 435);
  }

  public Dimension getPreferredSize() {
    return new Dimension(297, 435);
  }

  public void removeNotify() {
    if (this.m_clr != null)
      this.m_clr.dispose();
    this.m_clr = null;
    super.removeNotify();
    fix();
  }

  public void setObject(Object paramObject) {
    this.m_targ = (PVCalendar)paramObject;
    this.m_width = (this.m_targ.getSize()).width;
    this.m_height = (this.m_targ.getSize()).height;
    if (this.m_t != null)
      return;
    this.m_id = -100;
    Label[] arrayOfLabel = new Label[22];
    arrayOfLabel[0] = new Label("Number of");
    arrayOfLabel[1] = new Label("Months");
    arrayOfLabel[2] = new Label("Month-Year");
    arrayOfLabel[3] = new Label(" Strings");
    arrayOfLabel[4] = new Label("Days Of Week  (separator - Space)");
    arrayOfLabel[5] = new Label("Month String");
    arrayOfLabel[6] = new Label("ID");
    arrayOfLabel[7] = new Label(" Calendar Border");
    arrayOfLabel[8] = new Label("Main Style");
    arrayOfLabel[9] = new Label("Sub Style");
    arrayOfLabel[10] = new Label(" Width Factor");
    arrayOfLabel[11] = new Label(" Cell's Border");
    arrayOfLabel[12] = new Label(" Width");
    arrayOfLabel[13] = new Label("Sharpness");
    arrayOfLabel[14] = new Label(" Shadow");
    arrayOfLabel[15] = new Label("Position");
    arrayOfLabel[16] = new Label(" Width");
    arrayOfLabel[17] = new Label(" Text 3D Effect");
    arrayOfLabel[18] = new Label("Horizontal");
    arrayOfLabel[19] = new Label("Vertical");
    arrayOfLabel[20] = new Label(" Colors");
    arrayOfLabel[21] = new Label(" Action Mask");
    Font font = new Font("SansSerif", 0, 11);
    this.m_t = new TextField[3];
    this.m_t[0] = new TextField(this.m_targ.getMonth() + "-" + this.m_targ.getYear());
    this.m_t[1] = new TextField(this.m_targ.getDOW());
    this.m_t[2] = new TextField(this.m_targ.getMonthString(0));
    this.m_c = new Checkbox[16];
    this.m_c[0] = new Checkbox("Indent", this.m_targ.getIndentCellBorder());
    this.m_c[1] = new Checkbox("Aureole", this.m_targ.getAureole());
    this.m_c[2] = new Checkbox("Solid Triangle", this.m_targ.getSolidTriangle());
    this.m_c[3] = new Checkbox("Monday First", this.m_targ.getMondayFirst());
    this.m_c[4] = new Checkbox("Show SelDate", this.m_targ.getDisplaySelDate());
    this.m_c[5] = new Checkbox("CurrDate Button", this.m_targ.getCurrentDateButton());
    this.m_c[6] = new Checkbox("Year Editor", !this.m_targ.getHideYearField());
    this.m_c[7] = new Checkbox("MultiSelection", this.m_targ.getMultiSelection());
    this.m_c[8] = new Checkbox("RangeSelection", this.m_targ.getRangeSelection());
    int i = this.m_targ.getActionMask();
    this.m_c[9] = new Checkbox("Date Change", !((i & 0x1000000) == 0));
    this.m_c[10] = new Checkbox("Month-Year", !((i & 0x4000000) == 0));
    this.m_c[11] = new Checkbox("DoubleClick", !((i & 0x8000000) == 0));
    this.m_c[12] = new Checkbox("Enter key", !((i & 0x10000000) == 0));
    this.m_c[13] = new Checkbox("Escape key", !((i & 0x20000000) == 0));
    this.m_c[14] = new Checkbox("Got Focus", !((i & 0x40000000) == 0));
    this.m_c[15] = new Checkbox("Lost Focus", !((i & Integer.MIN_VALUE) == 0));
    this.m_k = new Choice[11];
    byte b;
    for (b = 0; b < 22; b++) {
      add(arrayOfLabel[b]);
      arrayOfLabel[b].setFont(font);
      if (b <= 10) {
        this.m_k[b] = new Choice();
        add(this.m_k[b]);
        this.m_k[b].addItemListener(this);
        this.m_k[b].setFont(font);
        if (b == 0)
          for (byte b1 = 0; b1 < 3; b1++) {
            this.m_t[b1].setFont(font);
            add(this.m_t[b1]);
            this.m_t[b1].addFocusListener(this);
          }
        if (b == 6)
          add(this.m_c[0]);
      }
    }
    for (b = 0; b < 16; b++) {
      this.m_c[b].setFont(font);
      if (b > 0) {
        add(this.m_c[b]);
        if (b < 9) {
          this.m_c[b].setBounds(102, 214 + b * ((b == 1) ? 12 : 17), 94, 16);
        } else {
          this.m_c[b].setBounds(205, 208 + (b - 7) * 17, 83, 16);
        }
      }
    }
    arrayOfLabel[21].setBounds(204, 226, 65, 12);
    this.m_k[0].addItem("One");
    this.m_k[0].addItem("Three");
    this.m_k[0].addItem("Six");
    this.m_k[0].addItem("Twelve");
    i = this.m_targ.getNumberOfMonths() / 3;
    if (i > 2)
      i = 3;
    this.m_k[0].select(i);
    for (b = 1; b < 13; b++)
      this.m_k[1].addItem(String.valueOf(b));
    this.m_k[1].select(0);
    this.m_k[2].addItem("None");
    this.m_k[2].addItem("Plain");
    this.m_k[2].addItem("Raised");
    this.m_k[2].addItem("Sunken");
    this.m_k[2].addItem("Outdent");
    this.m_k[2].addItem("Indent");
    this.m_k[2].addItem("Picture");
    this.m_k[2].addItem("Raised++");
    this.m_k[2].addItem("Sunken++");
    i = this.m_targ.getBorderStyle();
    if ((i & 0xFF) > 80)
      i = 80;
    this.m_k[2].select(((i & 0xFF) + 9) / 10);
    for (b = 0; b < 10; b++)
      this.m_k[3].addItem("Type " + b);
    if (i < 1)
      i = 1;
    this.m_k[3].select(((i & 0xFF) - 1) % 10);
    this.m_k[4].addItem("Single");
    this.m_k[4].addItem("Double");
    this.m_k[4].addItem("Triple");
    this.m_k[4].addItem("Four Times");
    this.m_k[4].addItem("Five Times");
    this.m_k[4].addItem("Six Times");
    this.m_k[4].addItem("Seven Times");
    this.m_k[4].select(i >> 8 & 0x7);
    for (b = 0; b < 8; b++)
      this.m_k[5].addItem(String.valueOf(b));
    this.m_k[5].select(this.m_targ.getCellBorderWidth());
    this.m_k[6].addItem("Very dim");
    this.m_k[6].addItem("Dim");
    this.m_k[6].addItem("Normal");
    this.m_k[6].addItem("Sharp");
    this.m_k[6].select(this.m_targ.getSharpness3D());
    this.m_k[7].addItem("None");
    this.m_k[7].addItem("Right-Bottom");
    this.m_k[7].addItem("Left-Bottom");
    this.m_k[7].addItem("Left-Top");
    this.m_k[7].addItem("Right-Top");
    this.m_k[7].select(this.m_targ.getShadow());
    for (b = 0; b < 16; b++)
      this.m_k[8].addItem(String.valueOf(b));
    i = this.m_targ.getShadowWidth();
    this.m_k[8].select(i);
    this.m_k[9].addItem("Left");
    this.m_k[9].addItem("None");
    this.m_k[9].addItem("Right");
    this.m_k[9].select(this.m_targ.getHorizontal3D() + 1);
    this.m_k[10].addItem("Top");
    this.m_k[10].addItem("None");
    this.m_k[10].addItem("Bottom");
    this.m_k[10].select(this.m_targ.getVertical3D() + 1);
    arrayOfLabel[0].setBounds(14, 32, 73, 12);
    arrayOfLabel[1].setBounds(19, 44, 63, 12);
    this.m_k[0].setBounds(12, 56, 75, 20);
    arrayOfLabel[2].setBounds(14, 78, 73, 12);
    this.m_t[0].setBounds(12, 90, 75, 20);
    arrayOfLabel[3].setBounds(113, 32, 39, 12);
    arrayOfLabel[4].setBounds(113, 44, 175, 12);
    this.m_t[1].setBounds(111, 56, 174, 20);
    arrayOfLabel[5].setBounds(113, 78, 70, 12);
    this.m_t[2].setBounds(111, 90, 123, 20);
    arrayOfLabel[6].setBounds(241, 78, 30, 12);
    this.m_k[1].setBounds(236, 90, 50, 20);
    arrayOfLabel[7].setBounds(11, 119, 84, 12);
    arrayOfLabel[8].setBounds(14, 131, 75, 12);
    this.m_k[2].setBounds(12, 143, 75, 20);
    arrayOfLabel[9].setBounds(113, 131, 75, 12);
    this.m_k[3].setBounds(111, 143, 75, 20);
    arrayOfLabel[10].setBounds(210, 131, 75, 12);
    this.m_k[4].setBounds(211, 143, 75, 20);
    arrayOfLabel[11].setBounds(11, 172, 66, 12);
    arrayOfLabel[12].setBounds(12, 183, 75, 12);
    this.m_k[5].setBounds(12, 195, 75, 20);
    arrayOfLabel[13].setBounds(12, 215, 75, 12);
    this.m_k[6].setBounds(12, 227, 75, 20);
    this.m_c[0].setBounds(12, 250, 75, 16);
    arrayOfLabel[14].setBounds(11, 275, 46, 12);
    arrayOfLabel[15].setBounds(14, 288, 75, 12);
    this.m_k[7].setBounds(12, 301, 75, 20);
    arrayOfLabel[16].setBounds(12, 324, 75, 12);
    this.m_k[8].setBounds(12, 336, 75, 20);
    arrayOfLabel[17].setBounds(111, 172, 74, 12);
    arrayOfLabel[18].setBounds(113, 183, 75, 12);
    this.m_k[9].setBounds(111, 196, 75, 20);
    arrayOfLabel[19].setBounds(212, 183, 75, 12);
    this.m_k[10].setBounds(210, 196, 75, 20);
    setAur(this.m_targ.getAureole());
    this.m_k[8].setEnabled(!(this.m_targ.getShadow() == 0));
    this.m_c[0].setEnabled(!(this.m_targ.getCellBorderWidth() <= 0));
    arrayOfLabel[20].setBounds(11, 369, 35, 12);
    this.m_b = new Button[9];
    this.m_b[0] = new Button("Foreground");
    this.m_b[1] = new Button("Background");
    this.m_b[2] = new Button("Calendar");
    this.m_b[3] = new Button("Sunday");
    this.m_b[4] = new Button("Text3D");
    this.m_b[5] = new Button("Border");
    this.m_b[6] = new Button("Shadow");
    this.m_b[7] = new Button("SelCell");
    this.m_b[8] = new Button("Update");
    for (b = 0; b < 9; b++) {
      this.m_b[b].setFont(font);
      add(this.m_b[b]);
      this.m_b[b].addActionListener(this);
      if (b == 8) {
        this.m_b[b].setBounds(215, 0, 80, 18);
      } else {
        this.m_b[b].setBounds(12 + b % 4 * 70, 382 + b / 4 * 18, 67, 16);
      }
    }
    this.m_id = -1;
  }

  public void itemStateChanged(ItemEvent paramItemEvent) {
    if (this.m_id > -2)
      doItem(paramItemEvent.getSource());
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (this.m_id > -2)
      doButt(paramActionEvent.getSource());
  }

  public void focusLost(FocusEvent paramFocusEvent) {
    if (this.m_id > -2)
      doFocus(paramFocusEvent.getSource());
  }

  public void focusGained(FocusEvent paramFocusEvent) {}

  private void setAur(boolean paramBoolean) {
    this.m_targ.setAureole(paramBoolean);
    this.m_k[8].setEnabled(!paramBoolean);
    this.m_k[9].setEnabled(!paramBoolean);
  }

  public void paint(Graphics paramGraphics) {
    Color color = getBackground();
    paramGraphics.setColor(color);
    paramGraphics.fillRect(0, 0, (getSize()).width, (getSize()).height);
    paramGraphics.setFont(new Font("SansSerif", 1, 18));
    paramGraphics.setColor(Color.white);
    paramGraphics.drawString("Infragistics Calendar", 5, 13);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawString("Infragistics Calendar", 6, 14);
    paramGraphics.setFont(new Font("Courier", 1, 13));
    paramGraphics.drawString("www.infragistics.com", 6, 26);
    PVBorder.draw(paramGraphics, new Rectangle(102, 36, 193, 81), 11305053, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 124, 290, 46), 11305053, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 177, 90, 96), 11305053, color);
    PVBorder.draw(paramGraphics, new Rectangle(102, 177, 193, 46), 11305053, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 280, 90, 86), 11305053, color);
    PVBorder.draw(paramGraphics, new Rectangle(199, 231, 96, 135), 11305053, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 374, 290, 49), 11305053, color);
    try {
      super.paint(paramGraphics);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  void doFocus(Object paramObject) {
    if (paramObject == this.m_t[0]) {
      String str = this.m_t[0].getText();
      int i;
      for (i = str.length(); i > 0 && !Character.isDigit(str.charAt(i - 1)); i--);
      int j;
      for (j = i; j > 0 && Character.isDigit(str.charAt(j - 1)); j--);
      if (i > j)
        this.m_targ.setYear((new Integer(str.substring(j, i))).intValue());
      for (i = j - 1; i > 0 && !Character.isDigit(str.charAt(i - 1)); i--);
      for (j = i; j > 0 && Character.isDigit(str.charAt(j - 1)); j--);
      if (i > j)
        this.m_targ.setMonth((new Integer(str.substring(j, i))).intValue());
      this.m_t[0].setText(String.valueOf(String.valueOf(this.m_targ.getMonth())) + "-" + String.valueOf(this.m_targ.getYear()));
      return;
    }
    if (paramObject == this.m_t[1]) {
      this.m_targ.setDOW(this.m_t[1].getText());
      return;
    }
    if (paramObject == this.m_t[2])
      this.m_targ.setMonthString(this.m_k[1].getSelectedIndex(), this.m_t[2].getText());
  }

  void doItem(Object paramObject) {
    byte b = 0;
    int i = 0;
    b = 0;
    while (paramObject != this.m_k[b]) {
      if (++b > 10)
        return;
    }
    i = this.m_k[b].getSelectedIndex();
    switch (b) {
      case 0:
        this.m_targ.setNumberOfMonths((i < 3) ? (i * 3) : 12);
        break;
      case 1:
        this.m_t[1].setText(this.m_targ.getMonthString(i));
        break;
      case 2:
      case 3:
      case 4:
        i = (this.m_k[2].getSelectedIndex() - 1) * 10;
        if (i < 0) {
          i = 0;
        } else {
          i += this.m_k[3].getSelectedIndex() + 1;
          i |= this.m_k[4].getSelectedIndex() << 8;
        }
        this.m_targ.setBorderStyle(i);
        break;
      case 5:
        this.m_targ.setCellBorderWidth(i);
        this.m_c[0].setEnabled(!(i <= 0));
        break;
      case 6:
        this.m_targ.setSharpness3D(i);
        break;
      case 7:
        this.m_targ.setShadow(i);
        this.m_k[8].setEnabled(!(i <= 0));
        break;
      case 8:
        this.m_targ.setShadowWidth(i);
        break;
      case 9:
        this.m_targ.setHorizontal3D(i - 1);
        break;
      case 10:
        this.m_targ.setVertical3D(i - 1);
        break;
    }
    this.m_targ.setSize(this.m_width, this.m_height);
    this.m_targ.getParent().doLayout();
  }

  void doButt(Object paramObject) {
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
          this.m_targ.setCalendarColor(color);
          break;
        case 3:
          this.m_targ.setSundayColor(color);
          break;
        case 4:
          this.m_targ.setText3DColor(color);
          break;
        case 5:
          this.m_targ.setBorderColor(color);
          break;
        case 6:
          this.m_targ.setShadowColor(color);
          break;
        case 7:
          this.m_targ.setSelCellColor(color);
          break;
      }
      this.m_targ.paint(null);
      return;
    }
    byte b = 0;
    while (paramObject != this.m_b[b]) {
      if (++b > 7) {
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
        color = this.m_targ.getCalendarColor();
        break;
      case 3:
        color = this.m_targ.getSundayColor();
        break;
      case 4:
        color = this.m_targ.getText3DColor();
        break;
      case 5:
        color = this.m_targ.getBorderColor();
        break;
      case 6:
        color = this.m_targ.getShadowColor();
        break;
      case 7:
        color = this.m_targ.getSelCellColor();
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
    int i = 0;
    int j = this.m_c.length;
    while (j-- > 0) {
      boolean bool = this.m_c[j].getState();
      switch (j) {
        case 0:
          if (bool != this.m_targ.getIndentCellBorder())
            this.m_targ.setIndentCellBorder(bool);
          break;
        case 1:
          if (bool != this.m_targ.getAureole())
            setAur(bool);
          break;
        case 2:
          if (bool != this.m_targ.getSolidTriangle())
            this.m_targ.setSolidTriangle(bool);
          break;
        case 3:
          if (bool != this.m_targ.getMondayFirst())
            this.m_targ.setMondayFirst(bool);
          break;
        case 4:
          if (bool != this.m_targ.getDisplaySelDate())
            this.m_targ.setDisplaySelDate(bool);
          break;
        case 5:
          if (bool != this.m_targ.getCurrentDateButton())
            this.m_targ.setCurrentDateButton(bool);
          break;
        case 6:
          if (bool == this.m_targ.getHideYearField())
            this.m_targ.setHideYearField(!bool);
          break;
        case 7:
          if (bool != this.m_targ.getMultiSelection())
            this.m_targ.setMultiSelection(bool);
          break;
        case 8:
          if (bool != this.m_targ.getRangeSelection())
            this.m_targ.setRangeSelection(bool);
          break;
        case 9:
          i = 16777216;
          break;
        case 10:
          i = 67108864;
          break;
        case 11:
          i = 134217728;
          break;
        case 12:
          i = 268435456;
          break;
        case 13:
          i = 536870912;
          break;
        case 14:
          i = 1073741824;
          break;
        case 15:
          i = Integer.MIN_VALUE;
          break;
      }
      if (j > 6) {
        i = bool ? (this.m_targ.getActionMask() | i) : (this.m_targ.getActionMask() & (i ^ 0xFFFFFFFF));
        this.m_targ.setActionMask(i);
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
