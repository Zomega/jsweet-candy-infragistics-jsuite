package pv.awt;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;

public class PVDatePlusCustomizer extends PVDateCustomizer {
  Panel m_ButCust;

  Choice[] m_X;

  Button[] m_B;

  Checkbox m_ck;

  int m_add;

  public void removeNotify() {
    ((PVDatePlus)this.m_targ).closeCalendar(false);
    this.m_targ.invalidate();
    super.removeNotify();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    if (this.m_ButCust == null)
      this.m_ButCust = new Panel();
    this.m_name = "DatePlus";
    this.m_B = new Button[3];
    this.m_B[0] = new Button("Edit Button");
    this.m_B[1] = new Button("Edit Date");
    this.m_B[2] = new Button("Color");
    add(this.m_B[0]);
    byte b;
    for (b = 0; b < 3; b++) {
      this.m_B[b].setFont(this.m_f);
      this.m_B[b].addActionListener(this);
      if (b < 2)
        this.m_B[b].setBounds(213, 17, 80, 16);
    }
    PVDatePlus pVDatePlus = (PVDatePlus)paramObject;
    super.setObject(pVDatePlus);
    this.m_id = -100;
    this.m_ButCust.setVisible(false);
    this.m_ButCust.add(this.m_B[1]);
    this.m_B[2].setBounds(90, 100, 120, 20);
    this.m_ButCust.add(this.m_B[2]);
    this.m_X = new Choice[4];
    Label[] arrayOfLabel = new Label[5];
    String str = null;
    b = 0;
    while (b < 5) {
      switch (b) {
        case 0:
          str = "Border";
          break;
        case 1:
          str = " Width";
          break;
        case 2:
          str = "In Front";
          break;
        case 3:
          str = "Triangle Style";
          break;
        default:
          str = "PVDatePlus Button";
          break;
      }
      arrayOfLabel[b] = new Label(str);
      this.m_ButCust.add(arrayOfLabel[b]);
      if (b != 4) {
        arrayOfLabel[b].setFont(this.m_f);
        this.m_X[b] = new Choice();
        this.m_X[b].setFont(this.m_f);
        this.m_X[b].addItemListener(this);
        this.m_ButCust.add(this.m_X[b]);
        arrayOfLabel[b].setBounds(90, 130 + b * 45, 120, 12);
        this.m_X[b].setBounds(90, 142 + b * 45, 120, 20);
        b++;
        continue;
      }
      break;
    }
    this.m_ck = new Checkbox("Enabled", pVDatePlus.getEditableByCalendar());
    this.m_ck.setFont(this.m_f);
    this.m_ck.setBounds(90, 320, 180, 16);
    this.m_ButCust.add(this.m_ck);
    arrayOfLabel[4].setFont(new Font("SansSerif", 1, 18));
    arrayOfLabel[4].setBounds(5, 15, 180, 20);
    this.m_X[0].addItem("Unknown");
    this.m_X[0].addItem("None");
    this.m_X[0].addItem("Simple1");
    this.m_X[0].addItem("Simple2");
    this.m_X[0].addItem("Simple3");
    this.m_X[0].addItem("Standard1");
    this.m_X[0].addItem("Standard2");
    this.m_X[0].addItem("Standard3");
    this.m_X[0].addItem("Standard4");
    this.m_X[0].addItem("Double1");
    this.m_X[0].addItem("Double2");
    this.m_X[0].addItem("Double3");
    b = -1;
    switch (pVDatePlus.getButtonBorder()) {
      case 0:
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
      case 196609:
        b = 5;
        break;
      case 1245201:
        b = 6;
        break;
      case 9994377:
        b = 7;
        break;
      case 81036937:
        b = 8;
        break;
      case 12517561:
        b = 9;
        break;
      case 132058745:
        b = 10;
        break;
      case 117375993:
        b = 11;
        break;
      default:
        b = 0;
        break;
    }
    this.m_X[0].select(b);
    this.m_X[1].addItem("Auto");
    for (b = 0; b < 61; b++)
      this.m_X[1].addItem(String.valueOf(b));
    this.m_X[1].select(pVDatePlus.getButtonWidth() + 1);
    this.m_X[2].addItem("False");
    this.m_X[2].addItem("True");
    this.m_X[2].select(pVDatePlus.getButtonInFront() ? 1 : 0);
    this.m_X[3].addItem("None");
    this.m_X[3].addItem("3D Solid");
    this.m_X[3].addItem("3D InvertedOnPress");
    this.m_X[3].addItem("Flat");
    this.m_X[3].addItem("Flat InvertedOnPress");
    this.m_X[3].addItem("OutLined");
    this.m_X[3].select(pVDatePlus.getTriangle());
    this.m_id = -1;
  }

  public void doAll(Object paramObject, int paramInt) {
    PVDatePlus pVDatePlus = (PVDatePlus)this.m_targ;
    if (paramInt == 100) {
      paramInt = 0;
      while (paramObject != this.m_X[paramInt]) {
        if (++paramInt > 3) {
          paramInt = 100;
          break;
        }
      }
      if (paramInt <= 3) {
        int i = this.m_X[paramInt].getSelectedIndex();
        switch (paramInt) {
          case 0:
            switch (i) {
              case 0:
                return;
              case 1:
                i = 0;
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
                i = 196609;
                break;
              case 6:
                i = 1245201;
                break;
              case 7:
                i = 9994377;
                break;
              case 8:
                i = 81036937;
                break;
              case 9:
                i = 12517561;
                break;
              case 10:
                i = 132058745;
                break;
              case 11:
                i = 117375993;
                break;
            }
            pVDatePlus.setButtonBorder(i);
            break;
          case 1:
            pVDatePlus.setButtonWidth(i - 1);
            break;
          case 2:
            pVDatePlus.setButtonInFront(!(i == 0));
            break;
          case 3:
            pVDatePlus.setTriangle(i);
            break;
        }
        paramInt = 1000;
      }
    }
    if (paramInt == 0) {
      if (this.m_id == 9 && paramObject == this.m_clr) {
        if (this.m_clr.m_i < 2)
          ((PVDatePlus)this.m_targ).setButtonColor(this.m_clr.m_old);
        return;
      }
      while (paramObject != this.m_B[paramInt] && ++paramInt <= 2);
      switch (paramInt) {
        case 0:
          if (this.m_ButCust == null)
            return;
          if ((this.m_add & 0x2) == 0) {
            this.m_ButCust.setBounds(getBounds());
            getParent().add(this.m_ButCust);
            this.m_add |= 0x2;
          }
          setVisible(false);
          this.m_ButCust.setVisible(true);
          this.m_B[1].requestFocus();
          break;
        case 1:
          ((PVDatePlus)this.m_targ).closeCalendar(false);
          this.m_ButCust.setVisible(false);
          setVisible(true);
          requestFocus();
          break;
        case 2:
          this.m_id = 9;
          handleColor(pVDatePlus.getButtonColor(), "Button");
          break;
      }
      if (paramInt == 3) {
        paramInt = 0;
      } else {
        paramInt = 1000;
      }
    }
    super.doAll(paramObject, paramInt);
    if (paramInt == 100 || paramInt == 1000) {
      pVDatePlus.invalidate();
      pVDatePlus.getParent().doLayout();
    }
  }

  void fix() {
    ((PVDatePlus)this.m_targ).setEditableByCalendar(this.m_ck.getState());
    super.fix();
  }
}
