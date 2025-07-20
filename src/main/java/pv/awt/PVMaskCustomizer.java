package pv.awt;

import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import pv.util.PVBorder;

public class PVMaskCustomizer extends PVEditCustomizer {
  protected TextField[] m_t;

  protected void init0() {
    this.m_name = "Mask Edit";
    this.m_top += 104;
    super.init0();
  }

  public void setObject(Object paramObject) {
    if (this.m_targ != null)
      return;
    this.m_id = -100;
    PVMask pVMask = (PVMask)paramObject;
    this.m_t = new TextField[3];
    this.m_t[0] = new TextField(pVMask.getMask());
    this.m_t[1] = new TextField(pVMask.getPromptChar());
    this.m_t[2] = new TextField(pVMask.getEscapeChar());
    Label[] arrayOfLabel = new Label[6];
    arrayOfLabel[0] = new Label("Mask (filter flags):");
    arrayOfLabel[1] = new Label("*-any    #-digit    @-letter    ^-to upper  |-to lower case");
    arrayOfLabel[2] = new Label("?-digit/letter    &-letter/space    %-digit/letter/space");
    arrayOfLabel[3] = new Label("!-not digit/letter/space    \\-Include/ExcludeCharacters");
    arrayOfLabel[4] = new Label("Prompt Character");
    arrayOfLabel[5] = new Label("Escape for Mask characters");
    for (byte b = 0; b < 6; b++) {
      arrayOfLabel[b].setFont(this.m_f);
      add(arrayOfLabel[b]);
      if (b < 3) {
        this.m_t[b].addTextListener(this);
        this.m_t[b].setFont(this.m_f);
        add(this.m_t[b]);
      }
    }
    arrayOfLabel[0].setBounds(14, 39, 170, 12);
    arrayOfLabel[1].setBounds(12, 51, 275, 12);
    arrayOfLabel[2].setBounds(12, 63, 275, 12);
    arrayOfLabel[3].setBounds(12, 75, 275, 12);
    this.m_t[0].setBounds(12, 87, 272, 18);
    this.m_t[1].setBounds(12, 108, 15, 18);
    arrayOfLabel[4].setBounds(31, 113, 90, 12);
    this.m_t[2].setBounds(121, 108, 15, 18);
    arrayOfLabel[5].setBounds(140, 113, 150, 12);
    super.setObject(paramObject);
    this.m_txt.setText(pVMask.getCleanText());
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 36, 288, 96), 12386493, getBackground());
  }

  public void doAll(Object paramObject, int paramInt) {
    if (paramInt == 200) {
      PVMask pVMask = (PVMask)this.m_targ;
      if (paramObject == this.m_txt) {
        pVMask.setCleanText(this.m_txt.getText());
      } else {
        paramInt = 0;
        while (paramObject != this.m_t[paramInt]) {
          if (++paramInt > 2) {
            paramInt = 200;
            break;
          }
        }
        switch (paramInt) {
          case 0:
            pVMask.setMask(this.m_t[0].getText());
            this.m_txt.setText(pVMask.getCleanText());
            break;
          case 1:
            pVMask.setPromptChar(this.m_t[1].getText());
            break;
          case 2:
            pVMask.setEscapeChar(this.m_t[2].getText());
            break;
        }
      }
      paramInt = 1000;
    }
    super.doAll(paramObject, paramInt);
  }
}
