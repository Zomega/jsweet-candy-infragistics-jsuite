package pv.awt;

import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import pv.util.PVBorder;

public class PVCurrencyCustomizer extends PVEditCustomizer {
  protected Choice[] m_K;

  protected Checkbox[] m_C;

  protected TextField[] m_t;

  protected void init0() {
    this.m_name = "Currency";
    this.m_top += 120;
    super.init0();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    PVCurrency pVCurrency = (PVCurrency)paramObject;
    this.m_t = new TextField[6];
    this.m_t[0] = new TextField(String.valueOf(pVCurrency.getMaxValue()));
    this.m_t[1] = new TextField(String.valueOf(pVCurrency.getMinValue()));
    this.m_t[2] = new TextField(String.valueOf(pVCurrency.getDelta()));
    this.m_t[3] = new TextField(pVCurrency.getSymbol());
    this.m_t[4] = new TextField(pVCurrency.getDecimalSeparator());
    this.m_t[5] = new TextField(pVCurrency.getThousandSeparator());
    this.m_C = new Checkbox[5];
    this.m_C[0] = new Checkbox("Thousand Sep", pVCurrency.getThousandSeparators());
    this.m_C[1] = new Checkbox("Max", pVCurrency.getUpperValidation());
    this.m_C[2] = new Checkbox("Min", pVCurrency.getLowerValidation());
    this.m_C[3] = new Checkbox("Enable Arrow Keys", pVCurrency.getEnableArrows());
    this.m_C[4] = new Checkbox("Allow Null Value", pVCurrency.getAllowNull());
    Label[] arrayOfLabel = new Label[5];
    arrayOfLabel[0] = new Label("Positive");
    arrayOfLabel[1] = new Label("Negative");
    arrayOfLabel[2] = new Label("Symbol");
    arrayOfLabel[3] = new Label("Decimals  Sep");
    arrayOfLabel[4] = new Label("Delta:");
    this.m_K = new Choice[3];
    byte b = 0;
    b = 0;
    while (b < 6) {
      this.m_t[b].addTextListener(this);
      this.m_t[b].setFont(this.m_f);
      if (b != 5) {
        add(arrayOfLabel[b]);
        arrayOfLabel[b].setFont(this.m_f);
        this.m_C[b].setFont(this.m_f);
        if (b == 3)
          add(this.m_t[4]);
        if (b < 3) {
          this.m_K[b] = new Choice();
          this.m_K[b].setFont(this.m_f);
          this.m_K[b].addItemListener(this);
          add(this.m_K[b]);
        }
        if (b == 1)
          add(this.m_t[3]);
        b++;
        continue;
      }
      break;
    }
    this.m_K[0].addItem("$1.5");
    this.m_K[0].addItem("1.5$");
    this.m_K[0].addItem("$ 1.5");
    this.m_K[0].addItem("1.5 $");
    this.m_K[0].select(pVCurrency.getFormatPositive());
    this.m_K[1].addItem("($1.5)");
    this.m_K[1].addItem("-$1.5");
    this.m_K[1].addItem("$-1.5");
    this.m_K[1].addItem("$1.5-");
    this.m_K[1].addItem("(1.5$)");
    this.m_K[1].addItem("-1.5$");
    this.m_K[1].addItem("1.5-$");
    this.m_K[1].addItem("1.5$-");
    this.m_K[1].addItem("-1.5 $");
    this.m_K[1].addItem("-$ 1.5");
    this.m_K[1].addItem("1.5 $-");
    this.m_K[1].select(pVCurrency.getFormatNegative());
    for (b = 0; b < 7; b++)
      this.m_K[2].addItem(String.valueOf(b));
    this.m_K[2].select(pVCurrency.getDecimals());
    arrayOfLabel[0].setBounds(13, 36, 48, 12);
    this.m_K[0].setBounds(12, 48, 48, 20);
    arrayOfLabel[1].setBounds(66, 36, 48, 12);
    this.m_K[1].setBounds(65, 48, 76, 20);
    arrayOfLabel[2].setBounds(13, 72, 38, 12);
    this.m_t[3].setBounds(12, 84, 47, 19);
    arrayOfLabel[3].setBounds(65, 73, 73, 12);
    this.m_K[2].setBounds(65, 85, 42, 20);
    this.m_t[4].setBounds(110, 85, 30, 19);
    this.m_C[0].setBounds(12, 115, 98, 18);
    this.m_t[5].setBounds(110, 115, 30, 19);
    add(this.m_C[0]);
    add(this.m_t[5]);
    for (b = 0; b < 2; b++) {
      add(this.m_C[b + 1]);
      add(this.m_t[b]);
      this.m_C[b + 1].setBounds(162, 39 + b * 20, 45, 16);
      this.m_t[b].setBounds(210, 37 + b * 20, 74, 18);
    }
    add(this.m_C[3]);
    add(this.m_C[4]);
    add(this.m_t[2]);
    arrayOfLabel[4].setBounds(178, 86, 26, 12);
    this.m_t[2].setBounds(210, 83, 74, 18);
    this.m_C[3].setBounds(162, 105, 120, 16);
    this.m_C[4].setBounds(162, 125, 120, 16);
    super.setObject(paramObject);
    this.m_k[1].setEnabled(false);
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 31, 143, 117), 12386493, getBackground());
    PVBorder.draw(paramGraphics, new Rectangle(155, 31, 138, 117), 12386493, getBackground());
  }

  public void doAll(Object paramObject, int paramInt) {
    PVCurrency pVCurrency = (PVCurrency)this.m_targ;
    if (paramInt == 100) {
      paramInt = 0;
      while (paramObject != this.m_K[paramInt]) {
        if (++paramInt > 2) {
          paramInt = 100;
          break;
        }
      }
      if (paramInt < 3) {
        int i = this.m_K[paramInt].getSelectedIndex();
        switch (paramInt) {
          case 0:
            pVCurrency.setFormatPositive(i);
            break;
          case 1:
            pVCurrency.setFormatNegative(i);
            break;
          case 2:
            pVCurrency.setDecimals(i);
            break;
        }
        paramInt = 1000;
      }
    }
    if (paramInt == 1000)
      pVCurrency.setText(this.m_txt.getText());
    if (paramInt == 200) {
      paramInt = 0;
      while (paramObject != this.m_t[paramInt]) {
        if (++paramInt > 5) {
          paramInt = 200;
          break;
        }
      }
      if (paramInt < 6) {
        double d = 0.0D;
        String str = this.m_t[paramInt].getText();
        if (paramInt < 3)
          try {
            d = (new Double(str)).doubleValue();
          } catch (Exception exception) {
            paramInt = 1000;
          }
        switch (paramInt) {
          case 0:
            pVCurrency.setMaxValue(d);
            break;
          case 1:
            pVCurrency.setMinValue(d);
            break;
          case 2:
            pVCurrency.setDelta(d);
            break;
          case 3:
            pVCurrency.setSymbol(str);
            break;
          case 4:
            pVCurrency.setDecimalSeparator(str);
            break;
          case 5:
            pVCurrency.setThousandSeparator(str);
            break;
        }
        paramInt = 1000;
      }
    }
    super.doAll(paramObject, paramInt);
  }

  void fix() {
    int i = this.m_C.length;
    boolean bool = false;
    PVCurrency pVCurrency = (PVCurrency)this.m_targ;
    while (i-- > 0) {
      boolean bool1 = this.m_C[i].getState();
      switch (i) {
        case 0:
          if (bool1 != pVCurrency.getThousandSeparators()) {
            bool = true;
            pVCurrency.setThousandSeparators(bool1);
          }
        case 1:
          if (bool1 != pVCurrency.getUpperValidation()) {
            bool = true;
            pVCurrency.setUpperValidation(bool1);
          }
        case 2:
          if (bool1 != pVCurrency.getLowerValidation()) {
            bool = true;
            pVCurrency.setLowerValidation(bool1);
          }
        case 3:
          if (bool1 != pVCurrency.getEnableArrows()) {
            bool = true;
            pVCurrency.setEnableArrows(bool1);
          }
        case 4:
          if (bool1 != pVCurrency.getAllowNull()) {
            bool = true;
            pVCurrency.setAllowNull(bool1);
          }
      }
    }
    if (bool)
      pVCurrency.setText(this.m_txt.getText());
    super.fix();
  }
}
