package pv.awt;

import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import pv.util.PVBorder;

public class PVDateCustomizer extends PVEditCustomizer {
  protected Checkbox[] m_C;

  protected Choice[] m_K;

  protected Label[] m_L;

  protected TextField[] m_t;

  protected void init0() {
    this.m_name = "Date";
    this.m_top += 100;
    super.init0();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    PVDate pVDate1 = (PVDate)paramObject;
    this.m_C = new Checkbox[6];
    this.m_C[0] = new Checkbox("AutoCentury", pVDate1.getAutoCentury());
    this.m_C[1] = new Checkbox("Century", pVDate1.getShowCentury());
    this.m_C[2] = new Checkbox("Leading 0", pVDate1.getLeadingZero());
    this.m_C[3] = new Checkbox("Allow Null", pVDate1.getAllowNull());
    this.m_C[4] = new Checkbox("Current", pVDate1.getCurrentDate());
    this.m_C[5] = new Checkbox("Free Entry", pVDate1.getFreeEntry());
    this.m_L = new Label[6];
    this.m_L[0] = new Label("Format");
    this.m_L[1] = new Label("Separator");
    this.m_L[2] = new Label("MonthFormat");
    this.m_L[3] = new Label("Show DOW");
    this.m_L[4] = new Label("LowerLimit");
    this.m_L[5] = new Label("UpperLimit");
    this.m_K = new Choice[3];
    byte b;
    for (b = 0; b < 6; b++) {
      this.m_L[b].setFont(this.m_f);
      add(this.m_L[b]);
      if (b < 2 || b > 3)
        this.m_L[b].setBounds(13 + 54 * b / 2, 39 + (b & 0x1) * 33, 50 + b / 2 * 11, 12);
      if (b <= 2) {
        this.m_K[b] = new Choice();
        this.m_K[b].addItemListener(this);
        this.m_K[b].setFont(this.m_f);
        add(this.m_K[b]);
        if (b == 0) {
          this.m_K[0].setBounds(10, 51, 56, 19);
        } else {
          this.m_K[b].setBounds(68, 51 + b / 2 * 33, 64, 19);
          this.m_L[b + 1].setBounds(68, 39 + b / 2 * 33, 65, 12);
        }
      }
    }
    PVDate pVDate2 = new PVDate(pVDate1.getLowerLimit());
    this.m_t = new TextField[3];
    this.m_t[0] = new TextField(pVDate1.getSeparator());
    this.m_t[1] = new TextField(pVDate2.getText());
    pVDate2.setDate(pVDate1.getUpperLimit());
    this.m_t[2] = new TextField(pVDate2.getText());
    for (b = 0; b < 3; b++) {
      if (b > 0) {
        this.m_t[b].addFocusListener(this);
        this.m_L[b + 3].setBounds(139, 72 - (b & 0x1) * 33, 71, 12);
        this.m_t[b].setBounds(135, 84 - (b & 0x1) * 33, 74, 19);
      }
      this.m_t[b].setFont(this.m_f);
      add(this.m_t[b]);
    }
    this.m_t[0].addTextListener(this);
    this.m_t[0].setBounds(12, 84, 50, 20);
    this.m_K[0].addItem("M/D/Y");
    this.m_K[0].addItem("D/M/Y");
    this.m_K[0].addItem("Y/M/D");
    this.m_K[0].addItem("Y/D/M");
    this.m_K[0].addItem("M/D");
    this.m_K[0].addItem("D/M");
    this.m_K[0].addItem("M/Y");
    this.m_K[0].addItem("Y/M");
    this.m_K[0].select(pVDate1.getFormat());
    this.m_C[0].setBounds(12, 106, 99, 16);
    b = 0;
    while (b < 6) {
      this.m_C[b].setFont(this.m_f);
      if (b != 5) {
        add(this.m_C[b]);
        if (b > 0)
          this.m_C[b].setBounds(216, 30 + b * 18, 73, 16);
        b++;
        continue;
      }
      break;
    }
    this.m_K[1].addItem("mm");
    this.m_K[1].addItem("mmm");
    this.m_K[1].addItem("mmmm");
    this.m_K[1].addItem("mmmm,");
    this.m_K[1].select(pVDate1.getMonthFormat());
    this.m_K[2].addItem("None");
    this.m_K[2].addItem("3 Front");
    this.m_K[2].addItem("3 End");
    this.m_K[2].addItem("Front");
    this.m_K[2].addItem("End");
    this.m_K[2].select(pVDate1.getShowDOW());
    super.setObject(paramObject);
    this.m_k[1].setEnabled(false);
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 33, 288, 94), 12386493, getBackground());
  }

  public void doAll(Object paramObject, int paramInt) {
    PVDate pVDate = (PVDate)this.m_targ;
    if (paramInt == 100) {
      paramInt = 0;
      while (paramObject != this.m_K[paramInt]) {
        if (++paramInt > 2) {
          paramInt = 100;
          break;
        }
      }
      if (paramInt <= 2) {
        int i = this.m_K[paramInt].getSelectedIndex();
        switch (paramInt) {
          case 0:
            pVDate.setFormat(i);
            break;
          case 1:
            pVDate.setMonthFormat(i);
            break;
          case 2:
            pVDate.setShowDOW(i);
            break;
        }
        paramInt = 1000;
      }
    }
    if (paramInt == 200) {
      paramInt = 0;
      while (paramObject != this.m_t[paramInt]) {
        if (++paramInt > 2) {
          paramInt = 200;
          break;
        }
      }
      if (paramInt < 3) {
        PVDate pVDate1;
        switch (paramInt) {
          case 0:
            pVDate.setSeparator(this.m_t[0].getText());
            break;
          case 1:
          case 2:
            pVDate1 = new PVDate();
            pVDate1.setText(((TextField)paramObject).getText());
            this.m_t[paramInt].setText(pVDate1.getText());
            if (paramInt == 1) {
              pVDate.setLowerLimit(pVDate1.getDate());
              break;
            }
            pVDate.setUpperLimit(pVDate1.getDate());
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
    PVDate pVDate = (PVDate)this.m_targ;
    while (i-- > 0) {
      boolean bool1 = this.m_C[i].getState();
      switch (i) {
        case 0:
          if (bool1 != pVDate.getAutoCentury()) {
            bool = true;
            pVDate.setAutoCentury(bool1);
          }
        case 1:
          if (bool1 != pVDate.getShowCentury()) {
            bool = true;
            pVDate.setShowCentury(bool1);
          }
        case 2:
          if (bool1 != pVDate.getLeadingZero()) {
            bool = true;
            pVDate.setLeadingZero(bool1);
          }
        case 3:
          if (bool1 != pVDate.getAllowNull()) {
            bool = true;
            pVDate.setAllowNull(bool1);
          }
        case 4:
          if (bool1 != pVDate.getCurrentDate()) {
            bool = true;
            pVDate.setCurrentDate(bool1);
            if (bool1)
              this.m_txt.setText(pVDate.getText());
          }
        case 5:
          if (bool1 != pVDate.getFreeEntry()) {
            bool = true;
            pVDate.setFreeEntry(bool1);
          }
      }
    }
    if (bool)
      pVDate.setText(this.m_txt.getText());
    super.fix();
  }
}
