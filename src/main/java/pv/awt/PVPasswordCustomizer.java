package pv.awt;

import java.awt.Checkbox;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import pv.util.PVBorder;

public class PVPasswordCustomizer extends PVEditCustomizer {
  protected TextField[] m_t;

  protected Checkbox[] m_C;

  protected void init0() {
    this.m_name = "Password";
    this.m_top += 100;
    super.init0();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    PVPassword pVPassword = (PVPassword)paramObject;
    int i = pVPassword.getActionMask();
    this.m_C = new Checkbox[6];
    this.m_C[0] = new Checkbox("DigitsOnly", pVPassword.getDigitsOnly());
    this.m_C[1] = new Checkbox("Enter Key", !((i & 0x1) == 0));
    this.m_C[2] = new Checkbox("Escape Key", !((i & 0x4) == 0));
    this.m_C[3] = new Checkbox("Mouse DblClick", !((i & 0x2) == 0));
    this.m_C[4] = new Checkbox("Password Match", !((i & 0x8) == 0));
    this.m_C[5] = new Checkbox("Maximum Length", !((i & 0x10) == 0));
    this.m_t = new TextField[3];
    this.m_t[0] = new TextField(pVPassword.getEchoCharacter());
    this.m_t[1] = new TextField(String.valueOf(pVPassword.getMaxLength()));
    this.m_t[2] = new TextField(pVPassword.getPassword());
    Label[] arrayOfLabel = new Label[4];
    arrayOfLabel[0] = new Label("Echo Character:");
    arrayOfLabel[1] = new Label("Maximum Length:");
    arrayOfLabel[2] = new Label("Password:");
    arrayOfLabel[3] = new Label(" Fire Action On");
    for (i = 0; i < 6; i++) {
      this.m_C[i].setFont(this.m_f);
      if (i <= 3) {
        arrayOfLabel[i].setFont(this.m_f);
        add(arrayOfLabel[i]);
        if (i <= 2) {
          arrayOfLabel[i].setBounds(13, 43 + i * 21, 100 - i / 2 * 45, 13);
          this.m_t[i].addTextListener(this);
          this.m_t[i].setFont(this.m_f);
          add(this.m_t[i]);
          this.m_t[i].setBounds(114 - i / 2 * 45, 40 + i * 21, 35 + i / 2 * 45, 18);
        }
      }
    }
    for (i = 0; i < 6; i++) {
      add(this.m_C[i]);
      if (i > 0)
        this.m_C[i].setBounds(175, 22 + i * 17, 105, 16);
    }
    this.m_C[0].setBounds(12, 106, 84, 17);
    arrayOfLabel[3].setBounds(192, 27, 75, 12);
    super.setObject(paramObject);
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 31, 153, 97), 12386493, getBackground());
    PVBorder.draw(paramGraphics, new Rectangle(167, 31, 126, 97), 12386493, getBackground());
  }

  public void doAll(Object paramObject, int paramInt) {
    PVPassword pVPassword = (PVPassword)this.m_targ;
    int i = 0;
    if (paramInt == 200) {
      paramInt = 0;
      while (paramObject != this.m_t[paramInt]) {
        if (++paramInt > 2) {
          paramInt = 200;
          break;
        }
      }
      if (paramInt < 3) {
        switch (paramInt) {
          case 0:
            pVPassword.setEchoCharacter(this.m_t[0].getText());
            break;
          case 1:
            try {
              i = (new Integer(this.m_t[1].getText())).intValue();
            } catch (Exception exception) {
              break;
            }
            pVPassword.setMaxLength(i);
            break;
          case 2:
            pVPassword.setPassword(this.m_t[2].getText());
            break;
        }
        paramInt = 1000;
      }
    }
    super.doAll(paramObject, paramInt);
  }

  void fix() {
    int i = this.m_C.length;
    PVPassword pVPassword = (PVPassword)this.m_targ;
    while (i-- > 0) {
      boolean bool = this.m_C[i].getState();
      if (i == 0) {
        if (bool != pVPassword.getDigitsOnly())
          pVPassword.setDigitsOnly(bool);
        continue;
      }
      int j = pVPassword.getActionMask();
      i = 1 << --i;
      if (bool) {
        j |= i;
      } else {
        j &= i ^ 0xFFFFFFFF;
      }
      pVPassword.setActionMask(j);
    }
    super.fix();
  }
}
