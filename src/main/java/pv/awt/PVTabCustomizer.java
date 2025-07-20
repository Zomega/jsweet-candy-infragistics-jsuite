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

public class PVTabCustomizer extends Container implements Customizer, ItemListener, ActionListener, TextListener {
  protected PVColorChooser m_clr;

  protected PropertyChangeSupport m_sup = new PropertyChangeSupport(this);

  protected PVTab m_targ;

  protected TextField[] m_e;

  protected Choice[] m_k;

  protected Checkbox[] m_c;

  protected Button[] m_b;

  protected int m_id = -100;

  protected int m_iCur = -1;

  public PVTabCustomizer() {
    setLayout(null);
    setSize(258, 378);
  }

  public Dimension getPreferredSize() {
    return new Dimension(258, 378);
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
    this.m_targ = (PVTab)paramObject;
    if (this.m_e != null) {
      getPage(true);
      return;
    }
    this.m_id = -100;
    Font font = new Font("SansSerif", 0, 11);
    this.m_k = new Choice[7];
    this.m_c = new Checkbox[6];
    Label[] arrayOfLabel = new Label[12];
    arrayOfLabel[0] = new Label("Border Style");
    arrayOfLabel[1] = new Label("Shape of Tabs");
    arrayOfLabel[2] = new Label("Text3D Effect");
    arrayOfLabel[3] = new Label("Button's Border");
    arrayOfLabel[4] = new Label("Button's Shape");
    arrayOfLabel[5] = new Label("Scroll Delta");
    arrayOfLabel[6] = new Label(" Scroll Buttons");
    arrayOfLabel[7] = new Label(" Colors");
    arrayOfLabel[8] = new Label("Client Size (width / height):");
    arrayOfLabel[9] = new Label(" DEMO Current Page");
    arrayOfLabel[10] = new Label("Tab Label       and");
    arrayOfLabel[11] = new Label(" Width");
    this.m_c[0] = new Checkbox("Top", this.m_targ.getTopStyle());
    this.m_c[1] = new Checkbox("Parent Colors", this.m_targ.getParentColors());
    this.m_c[2] = new Checkbox("AutoSize Tab to Label Width", this.m_targ.getAutoSizeTabWidth());
    this.m_c[3] = new Checkbox("Dark Background on Inactive Tabs", this.m_targ.getDarkInactiveTabs());
    this.m_c[4] = new Checkbox("Text 3D Effect on Inactive Tabs", this.m_targ.getText3DInactiveTabs());
    this.m_c[5] = new Checkbox("Inactive Tabs Lined Up with Active Tab", this.m_targ.getLineUpInactiveTabs());
    int i;
    for (i = 0; i < 6; i++) {
      this.m_c[i].setFont(font);
      if (i == 0) {
        this.m_c[0].setBounds(12, 40, 50, 16);
      } else if (i == 1) {
        this.m_c[1].setBounds(94, 40, 105, 16);
      } else {
        this.m_c[i].setBounds(12, 22 + i * 18, 200, 16);
      }
      add(this.m_c[i]);
    }
    arrayOfLabel[6].setBounds(14, 173, 71, 12);
    for (i = 0; i < 12; i++) {
      add(arrayOfLabel[i]);
      arrayOfLabel[i].setFont(font);
      if (i < 6) {
        this.m_k[i] = new Choice();
        add(this.m_k[i]);
        this.m_k[i].setFont(font);
        this.m_k[i].addItemListener(this);
        arrayOfLabel[i].setBounds(14 + i % 3 * 82, 137 + i / 3 * 50, 75, 12);
        this.m_k[i].setBounds(12 + i % 3 * 82, 149 + i / 3 * 50, 75, 20);
      }
    }
    this.m_b = new Button[11];
    this.m_b[0] = new Button("Foregrnd");
    this.m_b[1] = new Button("Backgrnd");
    this.m_b[2] = new Button("Buttons");
    this.m_b[3] = new Button("Text3D");
    this.m_b[4] = new Button("Background of Panel & Tab");
    this.m_b[5] = new Button("Add");
    this.m_b[6] = new Button("Insert");
    this.m_b[7] = new Button("Remove");
    this.m_b[8] = new Button("<");
    this.m_b[9] = new Button(">");
    this.m_b[10] = new Button("Update");
    arrayOfLabel[7].setBounds(14, 228, 36, 12);
    for (i = 0; i < 11; i++) {
      this.m_b[i].setFont(font);
      this.m_b[i].addActionListener(this);
      if (i == 10) {
        this.m_b[i].setBounds(178, 0, 80, 18);
        add(this.m_b[i]);
      }
      if (i <= 3) {
        add(this.m_b[i]);
        this.m_b[i].setBounds(12 + 60 * i, 243, 57, 18);
      }
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
    if (i > 80 || i < 0)
      i = -1;
    this.m_k[0].select(i);
    for (i = 1; i < 8; i++)
      this.m_k[1].addItem("Radius " + i);
    this.m_k[1].select(this.m_targ.getTabShape() - 1);
    this.m_k[2].addItem("Plane");
    this.m_k[2].addItem("Indent");
    this.m_k[2].addItem("Outdent");
    this.m_k[2].addItem("Aureole");
    this.m_k[2].select(this.m_targ.getText3D());
    this.m_k[3].addItem("None");
    this.m_k[3].addItem("Simple1");
    this.m_k[3].addItem("Simple2");
    this.m_k[3].addItem("Simple3");
    this.m_k[3].addItem("Simple4");
    this.m_k[3].addItem("Simple5");
    this.m_k[3].addItem("Standard1");
    this.m_k[3].addItem("Standard2");
    this.m_k[3].addItem("Standard3");
    this.m_k[3].addItem("Vague");
    this.m_k[3].addItem("Double");
    this.m_k[3].addItem("Triple");
    this.m_k[3].addItem("Triple2");
    this.m_k[3].addItem("Frame");
    this.m_k[3].addItem("Picture");
    i = -1;
    switch (this.m_targ.getScrollBorder()) {
      case 0:
        i = 0;
        break;
      case 196609:
        i = 1;
        break;
      case 163842:
        i = 2;
        break;
      case 1474578:
        i = 3;
        break;
      case 11960466:
        i = 4;
        break;
      case 95847570:
        i = 5;
        break;
      case 2031633:
        i = 6;
        break;
      case 9994377:
        i = 7;
        break;
      case 81036937:
        i = 8;
        break;
      case 634795091:
        i = 9;
        break;
      case 1056388057:
        i = 10;
        break;
      case 937369209:
        i = 11;
        break;
      case 1874767546:
        i = 12;
        break;
      case 1420252785:
        i = 13;
        break;
      case 1311728473:
        i = 14;
        break;
    }
    this.m_k[3].select(i);
    this.m_k[4].addItem("Square");
    this.m_k[4].addItem("Normal");
    this.m_k[4].addItem("Narrow");
    this.m_k[4].select(this.m_targ.getScrollShape());
    this.m_k[5].addItem("One Tab");
    this.m_k[5].addItem("2 pixels");
    this.m_k[5].addItem("5 pixels");
    this.m_k[5].addItem("10 pixels");
    this.m_k[5].addItem("20 pixels");
    this.m_k[5].addItem("50 pixels");
    this.m_k[5].addItem("100 pixels");
    int j = this.m_targ.getScrollDelta();
    for (i = 0; j > 0; i++)
      j = j * 2 / 5;
    this.m_k[5].select(i);
    arrayOfLabel[8].setBounds(14, 279, 131, 14);
    this.m_e = new TextField[4];
    for (i = 0; i < 4; i++) {
      this.m_e[i] = new TextField();
      this.m_e[i].setFont(font);
      this.m_e[i].addTextListener(this);
      if (i <= 1) {
        add(this.m_e[i]);
        this.m_e[i].setBounds(146 + i * 42, 278, 40, 17);
      }
    }
    add(this.m_b[8]);
    add(this.m_b[9]);
    this.m_b[8].setBounds(12, 297, 14, 14);
    this.m_b[9].setBounds(124, 297, 14, 14);
    getPage(true);
    arrayOfLabel[9].setBackground(new Color(-12033));
    arrayOfLabel[9].setBounds(26, 297, 98, 14);
    for (i = 0; i < 2; i++) {
      add(this.m_b[i + 5]);
      arrayOfLabel[10 + i].setBounds(109 + i * 103, 312, 101 - i * 62, 12);
      this.m_e[2 + i].setBounds(107 + i * 103, 324, 101 - i * 62, 18);
    }
    add(this.m_b[7]);
    this.m_b[5].setBounds(18, 324, 30, 18);
    this.m_b[6].setBounds(50, 324, 45, 18);
    this.m_b[7].setBounds(18, 345, 77, 18);
    add(this.m_e[2]);
    add(this.m_e[3]);
    add(this.m_b[4]);
    this.m_b[4].setBounds(107, 347, 142, 16);
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

  public void textValueChanged(TextEvent paramTextEvent) {
    if (this.m_id > -2)
      doText(paramTextEvent.getSource());
  }

  void getPage(boolean paramBoolean) {
    if (paramBoolean) {
      Dimension dimension = this.m_targ.getClientSize();
      this.m_e[0].setText(String.valueOf(dimension.width));
      this.m_e[1].setText(String.valueOf(dimension.height));
    }
    this.m_iCur = this.m_targ.getCurrentIndex();
    if (this.m_iCur < 0)
      return;
    this.m_e[2].setText(this.m_targ.getLabelAt(this.m_iCur));
    this.m_e[3].setText(String.valueOf(this.m_targ.getTabWidthAt(this.m_iCur)));
  }

  void setPage() {
    if (this.m_iCur < 0)
      return;
    this.m_targ.setLabelAt(this.m_e[2].getText(), this.m_iCur);
    try {
      this.m_targ.setTabWidthAt((new Integer(this.m_e[3].getText())).intValue(), this.m_iCur);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  public void paint(Graphics paramGraphics) {
    Color color = getBackground();
    paramGraphics.setColor(color);
    paramGraphics.fillRect(0, 0, (getSize()).width, (getSize()).height);
    paramGraphics.setFont(new Font("SansSerif", 1, 18));
    paramGraphics.setColor(Color.white);
    paramGraphics.drawString("Infragistics Tab", 8, 14);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawString("Infragistics Tab", 9, 15);
    paramGraphics.setFont(new Font("Courier", 1, 13));
    paramGraphics.drawString("www.infragistics.com", 10, 29);
    PVBorder.draw(paramGraphics, new Rectangle(5, 178, 253, 48), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 233, 253, 36), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, 303, 253, 68), 12386493, color);
    try {
      super.paint(paramGraphics);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  void doItem(Object paramObject) {
    byte b = 0;
    int i = 0;
    b = 6;
    while (paramObject != this.m_k[b - 6]) {
      if (++b > 11)
        return;
    }
    i = this.m_k[b - 6].getSelectedIndex();
    switch (b) {
      case 6:
        this.m_targ.setBorderStyle(i);
        return;
      case 7:
        this.m_targ.setTabShape(i + 1);
        return;
      case 8:
        this.m_targ.setText3D(i);
        return;
      case 9:
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
        this.m_targ.setScrollBorder(i);
        return;
      case 10:
        this.m_targ.setScrollShape(i);
        return;
      case 11:
        if (i-- > 0)
          i = (1 + i / 3 * 9) * (2 + i % 3 * 7 / 2);
        this.m_targ.setScrollDelta(i);
        return;
    }
  }

  void doText(Object paramObject) {
    byte b = -1;
    while (++b < 4 && paramObject != this.m_e[b]);
    if (b == 4)
      return;
    if (b > 1) {
      setPage();
      return;
    }
    try {
      this.m_targ.setClientSize((new Integer(this.m_e[0].getText())).intValue(), (new Integer(this.m_e[1].getText())).intValue());
      this.m_targ.getParent().doLayout();
      return;
    } catch (Exception exception) {
      return;
    }
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
          this.m_targ.setScrollColor(color);
          break;
        case 3:
          this.m_targ.setText3DColor(color);
          break;
        case 4:
          this.m_targ.getPanelAt(this.m_iCur).setBackground(color);
          break;
      }
      this.m_targ.paint0(null);
      return;
    }
    byte b = 0;
    while (paramObject != this.m_b[b]) {
      if (++b > 9) {
        fix();
        return;
      }
    }
    switch (b) {
      case 0:
        color = this.m_targ.getForeground();
        break;
      case 1:
        color = this.m_targ.getBackground();
        break;
      case 2:
        color = this.m_targ.getScrollColor();
        break;
      case 3:
        color = this.m_targ.getText3DColor();
        break;
      case 4:
        if (this.m_iCur < 0)
          return;
        color = this.m_targ.getPanelAt(this.m_iCur).getBackground();
        break;
      case 5:
        this.m_targ.addPage(null, this.m_e[2].getText());
        this.m_targ.setCurrentIndex(this.m_targ.getCount());
        getPage(false);
        break;
      case 6:
        this.m_targ.insertPageAt(null, this.m_e[2].getText(), this.m_iCur);
        if (this.m_iCur < 0)
          this.m_iCur = 0;
        break;
      case 7:
        this.m_targ.removePageAt(this.m_iCur);
        getPage(false);
        break;
      case 8:
      case 9:
        this.m_targ.shiftIndex((b - 8) * 2 - 1);
        getPage(false);
        break;
    }
    if (b < 5) {
      this.m_id = b;
      if (this.m_clr == null) {
        this.m_clr = new PVColorChooser(this.m_b[b].getLabel(), color);
        this.m_clr.addActionListener(this);
        return;
      }
      this.m_clr.setColor(this.m_b[b].getLabel(), color);
    }
  }

  void fix() {
    int i = this.m_c.length;
    while (i-- > 0) {
      boolean bool = this.m_c[i].getState();
      switch (i) {
        case 0:
          if (bool != this.m_targ.getTopStyle())
            this.m_targ.setTopStyle(bool);
        case 1:
          if (bool != this.m_targ.getParentColors())
            this.m_targ.setParentColors(bool);
        case 2:
          if (bool != this.m_targ.getAutoSizeTabWidth())
            this.m_targ.setAutoSizeTabWidth(bool);
        case 3:
          if (bool != this.m_targ.getDarkInactiveTabs())
            this.m_targ.setDarkInactiveTabs(bool);
        case 4:
          if (bool != this.m_targ.getText3DInactiveTabs())
            this.m_targ.setText3DInactiveTabs(bool);
        case 5:
          if (bool != this.m_targ.getLineUpInactiveTabs())
            this.m_targ.setLineUpInactiveTabs(bool);
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
