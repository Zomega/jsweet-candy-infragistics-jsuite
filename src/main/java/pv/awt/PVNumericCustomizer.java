package pv.awt;

import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import pv.util.PVBorder;

public class PVNumericCustomizer extends PVEditCustomizer {
  protected TextField[] m_t;

  protected Checkbox[] m_C;

  protected void init0() {
    this.m_name = "Numeric";
    this.m_top += 107;
    super.init0();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    PVNumeric pVNumeric = (PVNumeric)paramObject;
    this.m_t = new TextField[7];
    this.m_t[0] = new TextField(String.valueOf(pVNumeric.getMaxLength()));
    this.m_t[1] = new TextField(String.valueOf(pVNumeric.getMaxIntegers()));
    this.m_t[2] = new TextField(String.valueOf(pVNumeric.getMaxDecimals()));
    this.m_t[3] = new TextField(String.valueOf(pVNumeric.getDelta()));
    this.m_t[4] = new TextField(pVNumeric.getDecimalSeparator());
    this.m_t[5] = new TextField(String.valueOf(pVNumeric.getMaxValue()));
    this.m_t[6] = new TextField(String.valueOf(pVNumeric.getMinValue()));
    this.m_C = new Checkbox[7];
    this.m_C[0] = new Checkbox("Enable Float Point", pVNumeric.getEnableFloatPoint());
    this.m_C[1] = new Checkbox("Enable Arrow Keys", pVNumeric.getEnableArrows());
    this.m_C[2] = new Checkbox("Max", pVNumeric.getUpperValidation());
    this.m_C[3] = new Checkbox("Min", pVNumeric.getLowerValidation());
    this.m_C[4] = new Checkbox("Set To Limit", pVNumeric.getToLimit());
    this.m_C[5] = new Checkbox("Free Entry (E-power)", pVNumeric.getFreeEntry());
    this.m_C[6] = new Checkbox("Allow Null Value", pVNumeric.getAllowNull());
    Label[] arrayOfLabel = new Label[4];
    arrayOfLabel[0] = new Label("Maximums for Digits");
    arrayOfLabel[1] = new Label("Length / Integer / Decimal");
    arrayOfLabel[2] = new Label("Delta:");
    arrayOfLabel[3] = new Label("Point:");
    for (byte b = 0; b < 7; b++) {
      this.m_C[b].setFont(this.m_f);
      add(this.m_t[b]);
      this.m_t[b].setFont(this.m_f);
      this.m_t[b].addTextListener(this);
      if (b == 4) {
        add(this.m_C[0]);
        add(this.m_C[1]);
      }
      if (b == 5)
        add(this.m_C[2]);
      if (b <= 3) {
        add(arrayOfLabel[b]);
        arrayOfLabel[b].setFont(this.m_f);
      }
    }
    add(this.m_C[3]);
    add(this.m_C[4]);
    add(this.m_C[5]);
    add(this.m_C[6]);
    arrayOfLabel[0].setBounds(25, 34, 100, 11);
    arrayOfLabel[1].setBounds(14, 45, 126, 12);
    this.m_t[0].setBounds(20, 57, 32, 18);
    this.m_t[1].setBounds(57, 57, 32, 18);
    this.m_t[2].setBounds(94, 57, 32, 18);
    arrayOfLabel[2].setBounds(12, 80, 28, 12);
    this.m_t[3].setBounds(40, 78, 38, 18);
    arrayOfLabel[3].setBounds(86, 80, 26, 12);
    this.m_t[4].setBounds(112, 78, 25, 18);
    this.m_C[0].setBounds(12, 97, 127, 16);
    this.m_C[1].setBounds(12, 114, 127, 16);
    this.m_C[2].setBounds(160, 39, 48, 16);
    this.m_t[5].setBounds(210, 37, 73, 18);
    this.m_C[3].setBounds(160, 59, 48, 16);
    this.m_t[6].setBounds(210, 57, 73, 18);
    this.m_C[4].setBounds(160, 80, 128, 16);
    this.m_C[5].setBounds(160, 97, 128, 16);
    this.m_C[6].setBounds(160, 114, 128, 16);
    super.setObject(paramObject);
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 31, 140, 104), 12386493, getBackground());
    PVBorder.draw(paramGraphics, new Rectangle(153, 31, 140, 104), 12386493, getBackground());
  }

  public void doAll(Object paramObject, int paramInt) {
    PVNumeric pVNumeric = (PVNumeric)this.m_targ;
    if (paramInt == 200) {
      paramInt = 0;
      while (paramObject != this.m_t[paramInt] && ++paramInt <= 6);
      if (paramInt < 7) {
        double d = 0.0D;
        if (paramInt != 4)
          try {
            d = (new Double(this.m_t[paramInt].getText())).doubleValue();
          } catch (Exception exception) {
            paramInt = 1000;
          }
        switch (paramInt) {
          case 0:
            pVNumeric.setMaxLength((int)d);
            break;
          case 1:
            pVNumeric.setMaxIntegers((int)d);
            break;
          case 2:
            pVNumeric.setMaxDecimals((int)d);
            break;
          case 3:
            pVNumeric.setDelta(d);
            break;
          case 4:
            pVNumeric.setDecimalSeparator(this.m_t[2].getText());
            break;
          case 5:
            pVNumeric.setMaxValue(d);
            break;
          case 6:
            pVNumeric.setMinValue(d);
            break;
        }
        pVNumeric.setText(this.m_txt.getText());
        paramInt = 1000;
      } else {
        paramInt = 200;
      }
    }
    super.doAll(paramObject, paramInt);
  }

  void fix() {
    int i = this.m_C.length;
    boolean bool = false;
    PVNumeric pVNumeric = (PVNumeric)this.m_targ;
    while (i-- > 0) {
      boolean bool1 = this.m_C[i].getState();
      switch (i) {
        case 0:
          if (bool1 != pVNumeric.getEnableFloatPoint()) {
            bool = true;
            pVNumeric.setEnableFloatPoint(bool1);
          }
        case 1:
          if (bool1 != pVNumeric.getEnableArrows()) {
            bool = true;
            pVNumeric.setEnableArrows(bool1);
          }
        case 2:
          if (bool1 != pVNumeric.getUpperValidation()) {
            bool = true;
            pVNumeric.setUpperValidation(bool1);
          }
        case 3:
          if (bool1 != pVNumeric.getLowerValidation()) {
            bool = true;
            pVNumeric.setLowerValidation(bool1);
          }
        case 4:
          if (bool1 != pVNumeric.getToLimit()) {
            bool = true;
            pVNumeric.setToLimit(bool1);
          }
        case 5:
          if (bool1 != pVNumeric.getFreeEntry()) {
            bool = true;
            pVNumeric.setFreeEntry(bool1);
          }
        case 6:
          if (bool1 != pVNumeric.getAllowNull()) {
            bool = true;
            pVNumeric.setAllowNull(bool1);
          }
      }
    }
    if (bool)
      pVNumeric.setText(this.m_txt.getText());
    super.fix();
  }
}
