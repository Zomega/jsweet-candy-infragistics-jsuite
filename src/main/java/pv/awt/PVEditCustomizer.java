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
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.beans.Customizer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import pv.util.PVBorder;
import pv.util.PVColorChooser;

public class PVEditCustomizer extends Container implements Customizer, ActionListener, ItemListener, TextListener, FocusListener {
  PropertyChangeSupport m_sup;

  PVEdit m_targ;

  TextField m_txt;

  Checkbox[] m_c;

  Choice[] m_k;

  int m_width;

  int m_height;

  Font m_f = new Font("SansSerif", 0, 11);

  public PVColorChooser m_clr;

  public Button[] m_b;

  public int m_id = -100;

  public String m_name = "Edit";

  public int m_top = 30;

  public PVEditCustomizer() {
    init0();
  }

  protected void init0() {
    this.m_sup = new PropertyChangeSupport(this);
    setLayout(null);
    setSize(295, this.m_top + 322);
  }

  public Dimension getPreferredSize() {
    return new Dimension(295, this.m_top + 322);
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
    this.m_targ = (PVEdit)paramObject;
    this.m_width = (this.m_targ.getSize()).width;
    this.m_height = (this.m_targ.getSize()).height;
    if (this.m_c != null)
      return;
    this.m_id = -100;
    this.m_c = new Checkbox[11];
    this.m_k = new Choice[12];
    Label[] arrayOfLabel = new Label[18];
    arrayOfLabel[0] = new Label(" Text");
    arrayOfLabel[1] = new Label("String");
    arrayOfLabel[2] = new Label("Alignment");
    arrayOfLabel[3] = new Label("Case");
    arrayOfLabel[4] = new Label(" Border & Shadow");
    arrayOfLabel[5] = new Label("Border");
    arrayOfLabel[6] = new Label("Shadow Style");
    arrayOfLabel[7] = new Label("Shadow Width");
    arrayOfLabel[8] = new Label("Font Style");
    arrayOfLabel[9] = new Label("Odometer");
    arrayOfLabel[10] = new Label(" Text 3D Effect");
    arrayOfLabel[11] = new Label("Horizontal");
    arrayOfLabel[12] = new Label("Vertical");
    arrayOfLabel[13] = new Label(" Selection");
    arrayOfLabel[14] = new Label("On Focus");
    arrayOfLabel[15] = new Label("Painting");
    arrayOfLabel[16] = new Label("Caret Rate");
    arrayOfLabel[17] = new Label(" Colors");
    this.m_txt = new TextField(this.m_targ.getText());
    add(this.m_txt);
    this.m_txt.setFont(this.m_f);
    this.m_txt.addTextListener(this);
    this.m_c[0] = new Checkbox("Aureole", this.m_targ.getAureole());
    this.m_c[1] = new Checkbox("Hide Caret", this.m_targ.getCaretHidden());
    this.m_c[2] = new Checkbox("Invert Caret", this.m_targ.getCaretInverted());
    this.m_c[3] = new Checkbox("Editable", this.m_targ.getEditable());
    this.m_c[4] = new Checkbox("EnableUndo", this.m_targ.getEnableUndo());
    this.m_c[5] = new Checkbox("AutoResize", this.m_targ.getAutoResize());
    this.m_c[6] = new Checkbox("AutoScroll", this.m_targ.getAutoScroll());
    this.m_c[7] = new Checkbox("Invert Selection", this.m_targ.getSelectionInverted());
    this.m_c[8] = new Checkbox("KillSelByMouse", this.m_targ.getSelectionKillByMouse());
    this.m_c[9] = new Checkbox("Insert Edit Mode", this.m_targ.getInsertEditMode());
    this.m_c[10] = new Checkbox("AllowModeChange", this.m_targ.getAllowChangeMode());
    int i;
    for (i = 0; i < 18; i++) {
      add(arrayOfLabel[i]);
      arrayOfLabel[i].setFont(this.m_f);
      if (i <= 11) {
        this.m_k[i] = new Choice();
        add(this.m_k[i]);
        this.m_k[i].addItemListener(this);
        this.m_k[i].setFont(this.m_f);
        if (i == 6)
          add(this.m_c[0]);
      }
    }
    this.m_k[0].addItem("Left");
    this.m_k[0].addItem("Center");
    this.m_k[0].addItem("Right");
    this.m_k[0].select(this.m_targ.getAlignment());
    this.m_k[1].addItem("No change");
    this.m_k[1].addItem("Upper");
    this.m_k[1].addItem("Lower");
    this.m_k[1].select(this.m_targ.getCase());
    this.m_k[2].addItem("None");
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Plain " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Raised " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Sunken " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Outdent " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Indent " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Picture " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Raised++ " + i);
    for (i = 1; i < 11; i++)
      this.m_k[2].addItem("Sunken++ " + i);
    int j = this.m_targ.getBorderStyle();
    if (j >= 0 && j < 81)
      this.m_k[2].select(j);
    this.m_k[3].addItem("None");
    this.m_k[3].addItem("Right-Bottom");
    this.m_k[3].addItem("Left-Bottom");
    this.m_k[3].addItem("Left-Top");
    this.m_k[3].addItem("Right-Top");
    this.m_k[3].select(this.m_targ.getShadow());
    for (i = 0; i < 16; i++)
      this.m_k[4].addItem(String.valueOf(i));
    this.m_k[4].select(this.m_targ.getShadowWidth());
    this.m_k[4].setEnabled(!(this.m_targ.getShadow() == 0));
    for (i = 4; i > 0; i--)
      this.m_k[5].addItem("Left " + i);
    this.m_k[5].addItem("None");
    for (i = 1; i < 5; i++)
      this.m_k[5].addItem("Right " + i);
    this.m_k[5].select(this.m_targ.getHorizontal3D() + 4);
    for (i = 4; i > 0; i--)
      this.m_k[6].addItem("Top " + i);
    this.m_k[6].addItem("None");
    for (i = 1; i < 5; i++)
      this.m_k[6].addItem("Bottom " + i);
    this.m_k[6].select(this.m_targ.getVertical3D() + 4);
    this.m_k[7].addItem("Underline-2");
    this.m_k[7].addItem("Underline-1");
    this.m_k[7].addItem("Underline");
    this.m_k[7].addItem("Underline+1");
    this.m_k[7].addItem("Normal");
    this.m_k[7].addItem("Formatted");
    this.m_k[7].addItem("Format Lines");
    this.m_k[7].addItem("LED");
    this.m_k[7].addItem("LED Bold");
    if ((i = this.m_targ.getFontStyle() + 4) < 0)
      i = 0;
    this.m_k[7].select(i);
    this.m_k[8].addItem("None");
    this.m_k[8].addItem("Light Top");
    this.m_k[8].addItem("Light Center");
    this.m_k[8].addItem("Light Bottom");
    this.m_k[8].addItem("Normal Top");
    this.m_k[8].addItem("Normal Center");
    this.m_k[8].addItem("Normal Bottom");
    this.m_k[8].addItem("Dark Top");
    this.m_k[8].addItem("Dark Center");
    this.m_k[8].addItem("Dark Bottom");
    this.m_k[8].select(this.m_targ.getOdometer());
    this.m_k[9].addItem("Restore Old");
    this.m_k[9].addItem("Select All");
    this.m_k[9].addItem("None, CaretEnd");
    this.m_k[9].addItem("None, CaretStart");
    this.m_k[9].select(this.m_targ.getSelectionOnFocus());
    this.m_k[10].addItem("While Focus");
    this.m_k[10].addItem("Always");
    this.m_k[10].addItem("None");
    this.m_k[10].select(this.m_targ.getSelectionPaint());
    this.m_k[11].addItem("310 ms");
    this.m_k[11].addItem("410 ms");
    this.m_k[11].addItem("510 ms");
    this.m_k[11].addItem("610 ms");
    this.m_k[11].select((this.m_targ.getCaretRate() - 300) / 100);
    for (i = 0; i < 11; i++) {
      if (i > 0) {
        add(this.m_c[i]);
        this.m_c[i].setBounds(12 + (i + 1) / 4 * 85, this.m_top + 210 + (i + 1) % 4 * 16, 85 + i / 7 * 35, 16);
      }
      this.m_c[i].setFont(this.m_f);
    }
    arrayOfLabel[0].setBounds(11, this.m_top, 26, 12);
    arrayOfLabel[1].setBounds(14, this.m_top + 12, 70, 12);
    this.m_txt.setBounds(12, this.m_top + 24, 80, 19);
    arrayOfLabel[2].setBounds(113, this.m_top + 12, 80, 12);
    this.m_k[0].setBounds(111, this.m_top + 24, 85, 20);
    arrayOfLabel[3].setBounds(212, this.m_top + 12, 75, 12);
    this.m_k[1].setBounds(210, this.m_top + 24, 75, 20);
    arrayOfLabel[4].setBounds(11, this.m_top + 52, 91, 12);
    arrayOfLabel[5].setBounds(14, this.m_top + 64, 75, 12);
    this.m_k[2].setBounds(12, this.m_top + 76, 75, 20);
    arrayOfLabel[6].setBounds(113, this.m_top + 64, 80, 12);
    this.m_k[3].setBounds(111, this.m_top + 76, 85, 20);
    arrayOfLabel[7].setBounds(210, this.m_top + 64, 75, 12);
    this.m_k[4].setBounds(210, this.m_top + 76, 75, 20);
    arrayOfLabel[8].setBounds(113, this.m_top + 116, 80, 12);
    this.m_k[7].setBounds(111, this.m_top + 128, 85, 20);
    arrayOfLabel[9].setBounds(212, this.m_top + 116, 75, 12);
    this.m_k[8].setBounds(210, this.m_top + 128, 75, 20);
    arrayOfLabel[13].setBounds(110, this.m_top + 156, 48, 12);
    arrayOfLabel[14].setBounds(113, this.m_top + 168, 80, 12);
    this.m_k[9].setBounds(111, this.m_top + 180, 85, 20);
    arrayOfLabel[15].setBounds(212, this.m_top + 168, 75, 12);
    this.m_k[10].setBounds(210, this.m_top + 180, 75, 20);
    arrayOfLabel[10].setBounds(11, this.m_top + 104, 73, 12);
    arrayOfLabel[11].setBounds(14, this.m_top + 116, 75, 12);
    this.m_k[5].setBounds(12, this.m_top + 128, 75, 20);
    arrayOfLabel[12].setBounds(14, this.m_top + 149, 75, 12);
    this.m_k[6].setBounds(12, this.m_top + 161, 75, 20);
    this.m_c[0].setBounds(12, this.m_top + 184, 75, 16);
    this.m_k[5].setEnabled(!this.m_targ.getAureole());
    this.m_k[6].setEnabled(!this.m_targ.getAureole());
    arrayOfLabel[16].setBounds(11, this.m_top + 208, 71, 12);
    this.m_k[11].setBounds(12, this.m_top + 220, 73, 20);
    arrayOfLabel[17].setBounds(11, this.m_top + 272, 35, 12);
    if (this.m_b == null)
      this.m_b = new Button[10];
    this.m_b[0] = new Button("Backgrnd");
    this.m_b[1] = new Button("Foregrnd");
    this.m_b[2] = new Button("Disabled");
    this.m_b[3] = new Button("Border");
    this.m_b[4] = new Button("Shadow");
    this.m_b[5] = new Button("Text3D");
    this.m_b[6] = new Button("Caret");
    this.m_b[7] = new Button("SelFore");
    this.m_b[8] = new Button("SelBack");
    this.m_b[9] = new Button("Update");
    for (i = 0; i < 10; i++) {
      this.m_b[i].setFont(this.m_f);
      add(this.m_b[i]);
      this.m_b[i].addActionListener(this);
      if (i == 9) {
        this.m_b[i].setBounds(213, 0, 80, 17);
      } else {
        this.m_b[i].setBounds(10 + i % 5 * 56 + i / 5 * 28, this.m_top + 284 + i / 5 * 16, 54, 15);
      }
    }
    this.m_id = -1;
  }

  public void paint(Graphics paramGraphics) {
    Color color = getBackground();
    paramGraphics.setColor(color);
    paramGraphics.fillRect(0, 0, (getSize()).width, (getSize()).height);
    String str = "Infragistics " + this.m_name;
    Font font = new Font("SansSerif", 1, 18);
    paramGraphics.setFont(font);
    paramGraphics.setColor(Color.white);
    paramGraphics.drawString(str, 2, 13);
    paramGraphics.setColor(Color.black);
    paramGraphics.drawString(str, 3, 14);
    font = new Font("Courier", 1, 13);
    paramGraphics.setFont(font);
    paramGraphics.drawString("www.infragistics.com", 4, 26);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 4, 288, 46), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 56, 288, 46), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(104, this.m_top + 108, 189, 46), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 108, 90, 98), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(104, this.m_top + 160, 189, 46), 12386493, color);
    PVBorder.draw(paramGraphics, new Rectangle(5, this.m_top + 277, 288, 44), 12386493, color);
    try {
      super.paint(paramGraphics);
      return;
    } catch (Exception exception) {
      return;
    }
  }

  public void actionPerformed(ActionEvent paramActionEvent) {
    if (this.m_id > -2)
      doAll(paramActionEvent.getSource(), 0);
  }

  public void itemStateChanged(ItemEvent paramItemEvent) {
    if (this.m_id > -2)
      doAll(paramItemEvent.getSource(), 100);
  }

  public void textValueChanged(TextEvent paramTextEvent) {
    if (this.m_id > -2)
      doAll(paramTextEvent.getSource(), 200);
  }

  public void focusLost(FocusEvent paramFocusEvent) {
    if (this.m_id > -2)
      doAll(paramFocusEvent.getSource(), 200);
  }

  public void focusGained(FocusEvent paramFocusEvent) {}

  public void handleColor(Color paramColor, String paramString) {
    if (this.m_clr == null) {
      this.m_clr = new PVColorChooser(paramString, paramColor);
      this.m_clr.addActionListener(this);
      return;
    }
    this.m_clr.setColor(paramString, paramColor);
  }

  public void doAll(Object paramObject, int paramInt) {
    if (paramInt == 0) {
      Color color = null;
      if (paramObject == this.m_clr) {
        if (this.m_clr.m_i > 1)
          return;
        color = this.m_clr.m_old;
        switch (this.m_id) {
          case 0:
            this.m_targ.setBackground(color);
            break;
          case 1:
            this.m_targ.setForeground(color);
            break;
          case 2:
            this.m_targ.setDisabledForeground(color);
            break;
          case 3:
            this.m_targ.setBorderColor(color);
            break;
          case 4:
            this.m_targ.setShadowColor(color);
            break;
          case 5:
            this.m_targ.setText3DColor(color);
            break;
          case 6:
            this.m_targ.setCaretColor(color);
            break;
          case 7:
            this.m_targ.setSelectionForeground(color);
            break;
          case 8:
            this.m_targ.setSelectionBackground(color);
            break;
        }
      } else {
        while (paramObject != this.m_b[paramInt]) {
          if (++paramInt > 8) {
            fix();
            return;
          }
        }
        switch (this.m_id = paramInt) {
          case 0:
            color = this.m_targ.getBackground();
            break;
          case 1:
            color = this.m_targ.getForeground();
            break;
          case 2:
            color = this.m_targ.getDisabledForeground();
            break;
          case 3:
            color = this.m_targ.getBorderColor();
            break;
          case 4:
            color = this.m_targ.getShadowColor();
            break;
          case 5:
            color = this.m_targ.getText3DColor();
            break;
          case 6:
            color = this.m_targ.getCaretColor();
            break;
          case 7:
            color = this.m_targ.getSelectionForeground();
            break;
          case 8:
            color = this.m_targ.getSelectionBackground();
            break;
        }
        handleColor(color, this.m_b[paramInt].getLabel());
        return;
      }
    }
    if (paramInt == 100) {
      paramInt = 0;
      while (paramObject != this.m_k[paramInt] && ++paramInt <= 11);
      if (paramInt <= 11) {
        int i = this.m_k[paramInt].getSelectedIndex();
        switch (paramInt) {
          case 0:
            this.m_targ.setAlignment(i);
            break;
          case 1:
            this.m_targ.setCase(i);
            if (i == 0)
              this.m_targ.setText(this.m_txt.getText());
            break;
          case 2:
            this.m_targ.setBorderStyle(i);
            break;
          case 3:
            this.m_targ.setShadow(i);
            this.m_k[4].setEnabled(!(i <= 0));
            break;
          case 4:
            this.m_targ.setShadowWidth(i);
            break;
          case 5:
            this.m_targ.setHorizontal3D(i - 4);
            break;
          case 6:
            this.m_targ.setVertical3D(i - 4);
            break;
          case 7:
            this.m_targ.setFontStyle(i - 4);
            break;
          case 8:
            this.m_targ.setOdometer(i);
            break;
          case 9:
            this.m_targ.setSelectionOnFocus(i);
            break;
          case 10:
            this.m_targ.setSelectionPaint(i);
            break;
          case 11:
            this.m_targ.setCaretRate(320 + 100 * i);
            break;
        }
        if (paramInt > 1 && paramInt < 8) {
          this.m_targ.setSize(this.m_width, this.m_height);
          this.m_targ.getParent().doLayout();
        }
      }
    }
    if (paramInt == 200 && paramObject == this.m_txt)
      this.m_targ.setText(this.m_txt.getText());
    this.m_targ.paint(null);
  }

  void fix() {
    int i = this.m_c.length;
    while (i-- > 0) {
      boolean bool = this.m_c[i].getState();
      switch (i) {
        case 0:
          if (bool != this.m_targ.getAureole()) {
            this.m_targ.setAureole(bool);
            this.m_k[5].setEnabled(!bool);
            this.m_k[6].setEnabled(!bool);
          }
        case 1:
          if (bool != this.m_targ.getCaretHidden())
            this.m_targ.setCaretHidden(bool);
        case 2:
          if (bool != this.m_targ.getCaretInverted())
            this.m_targ.setCaretInverted(bool);
        case 3:
          if (bool != this.m_targ.getEditable())
            this.m_targ.setEditable(bool);
        case 4:
          if (bool != this.m_targ.getEnableUndo())
            this.m_targ.setEnableUndo(bool);
        case 5:
          if (bool != this.m_targ.getAutoResize())
            this.m_targ.setAutoResize(bool);
        case 6:
          if (bool != this.m_targ.getAutoScroll())
            this.m_targ.setAutoScroll(bool);
        case 7:
          if (bool != this.m_targ.getSelectionKillByMouse())
            this.m_targ.setSelectionKillByMouse(bool);
        case 8:
          if (bool != this.m_targ.getSelectionInverted())
            this.m_targ.setSelectionInverted(bool);
        case 9:
          if (bool != this.m_targ.getInsertEditMode())
            this.m_targ.setInsertEditMode(bool);
        case 10:
          if (bool != this.m_targ.getAllowChangeMode())
            this.m_targ.setAllowChangeMode(bool);
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
