package pv.awt;

import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import pv.util.PVBorder;

public class PVTimeCustomizer extends PVEditCustomizer {
  protected Choice m_K;

  protected Checkbox[] m_C;

  protected TextField[] m_t;

  protected void init0() {
    this.m_name = "Time Edit";
    this.m_top += 87;
    super.init0();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    PVTime pVTime = (PVTime)paramObject;
    this.m_C = new Checkbox[5];
    this.m_C[0] = new Checkbox("Show Seconds", pVTime.getShowSeconds());
    this.m_C[1] = new Checkbox("Twelve Hours", pVTime.getTwelveHours());
    this.m_C[2] = new Checkbox("Leading Zero", pVTime.getLeadingZero());
    this.m_C[3] = new Checkbox("Allow Null", pVTime.getAllowNull());
    this.m_C[4] = new Checkbox("Blank String For Null Time", pVTime.getBlankForNull());
    this.m_t = new TextField[2];
    this.m_t[0] = new TextField(pVTime.getSeparator());
    this.m_t[1] = new TextField(pVTime.getAMPM());
    byte b;
    for (b = 0; b < 5; b++) {
      add(this.m_C[b]);
      this.m_C[b].setFont(this.m_f);
      if (b < 4)
        this.m_C[b].setBounds(12, 42 + b * 17, 100, 16);
      if (b <= 1) {
        this.m_t[b].addTextListener(this);
        this.m_t[b].setFont(this.m_f);
      }
    }
    add(this.m_t[0]);
    add(this.m_t[1]);
    this.m_K = new Choice();
    this.m_K.addItem("Stop");
    this.m_K.addItem("Clock");
    this.m_K.addItem("Timer");
    this.m_K.addItem("Back Timer");
    this.m_K.select(pVTime.getTimer());
    this.m_K.setFont(this.m_f);
    this.m_K.addItemListener(this);
    add(this.m_K);
    Label[] arrayOfLabel = new Label[3];
    arrayOfLabel[0] = new Label("Separator");
    arrayOfLabel[1] = new Label("AM / PM");
    arrayOfLabel[2] = new Label("Clock / Timer");
    for (b = 0; b < 3; b++) {
      add(arrayOfLabel[b]);
      arrayOfLabel[b].setFont(this.m_f);
    }
    arrayOfLabel[0].setBounds(116, 44, 54, 12);
    this.m_t[0].setBounds(125, 56, 25, 19);
    arrayOfLabel[1].setBounds(172, 44, 42, 12);
    this.m_t[1].setBounds(164, 56, 55, 19);
    arrayOfLabel[2].setBounds(223, 44, 64, 12);
    this.m_K.setBounds(223, 56, 62, 20);
    this.m_C[4].setBounds(125, 92, 150, 16);
    if (!this.m_C[1].getState())
      this.m_t[0].setEnabled(false);
    super.setObject(paramObject);
    this.m_k[1].setEnabled(false);
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 36, 288, 79), 12386493, getBackground());
  }

  public void doAll(Object paramObject, int paramInt) {
    PVTime pVTime = (PVTime)this.m_targ;
    if (paramInt == 100) {
      paramInt = 0;
      while (paramObject != this.m_C[paramInt]) {
        if (++paramInt > 4) {
          paramInt = 100;
          break;
        }
      }
      if (paramInt == 100 && paramObject == this.m_K) {
        pVTime.setTimer(this.m_K.getSelectedIndex());
        paramInt = 1000;
      }
    }
    if (paramInt == 200) {
      if (paramObject == this.m_t[0])
        pVTime.setSeparator(this.m_t[0].getText());
      if (paramObject == this.m_t[1])
        pVTime.setAMPM(this.m_t[1].getText());
    }
    super.doAll(paramObject, paramInt);
  }

  void fix() {
    int i = this.m_C.length;
    boolean bool = false;
    PVTime pVTime = (PVTime)this.m_targ;
    while (i-- > 0) {
      boolean bool1 = this.m_C[i].getState();
      switch (i) {
        case 0:
          if (bool1 != pVTime.getShowSeconds()) {
            bool = true;
            pVTime.setShowSeconds(bool1);
          }
        case 1:
          if (bool1 != pVTime.getTwelveHours()) {
            bool = true;
            pVTime.setTwelveHours(bool1);
            this.m_t[0].setEnabled(bool1);
          }
        case 2:
          if (bool1 != pVTime.getLeadingZero()) {
            bool = true;
            pVTime.setLeadingZero(bool1);
          }
        case 3:
          if (bool1 != pVTime.getAllowNull()) {
            bool = true;
            pVTime.setAllowNull(bool1);
          }
        case 4:
          if (bool1 != pVTime.getBlankForNull()) {
            bool = true;
            pVTime.setBlankForNull(bool1);
          }
      }
    }
    if (bool)
      pVTime.setText(this.m_txt.getText());
    super.fix();
  }
}
