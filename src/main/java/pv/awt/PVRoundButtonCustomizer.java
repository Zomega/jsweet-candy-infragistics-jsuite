package pv.awt;

import java.awt.Choice;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import pv.util.PVBorder;

public class PVRoundButtonCustomizer extends PVButtonCustomizer {
  protected Choice[] m_K;

  protected void init0() {
    this.m_top += 49;
    this.m_name = "Round";
    super.init0();
  }

  public void setObject(Object paramObject) {
    this.m_targ = (PVRoundButton)paramObject;
    if (this.m_K != null)
      return;
    this.m_id = -100;
    Label[] arrayOfLabel = new Label[3];
    arrayOfLabel[0] = new Label("Shape");
    arrayOfLabel[1] = new Label("Center Area");
    arrayOfLabel[2] = new Label("Colors Depth");
    this.m_K = new Choice[3];
    byte b;
    for (b = 0; b < 3; b++) {
      this.m_K[b] = new Choice();
      add(this.m_K[b]);
      this.m_K[b].addItemListener(this);
      this.m_K[b].setFont(this.m_f);
      add(arrayOfLabel[b]);
      arrayOfLabel[b].setFont(this.m_f);
      arrayOfLabel[b].setBounds(14 + b * 89, 40, 75, 12);
      this.m_K[b].setBounds(12 + b * 89, 52, 75, 20);
    }
    for (b = 0; b < 6; b++)
      this.m_K[0].addItem("Radius " + b);
    this.m_K[0].addItem("Ellipse");
    this.m_K[0].select(((PVRoundButton)paramObject).getShape());
    this.m_K[1].addItem("Smallest");
    this.m_K[1].addItem("Very Small");
    this.m_K[1].addItem("Small");
    this.m_K[1].addItem("Normal 1");
    this.m_K[1].addItem("Normal 2");
    this.m_K[1].addItem("Large");
    this.m_K[1].addItem("Very Large");
    this.m_K[1].addItem("Largest");
    this.m_K[1].select(((PVRoundButton)paramObject).getCenterArea());
    this.m_K[2].addItem("Flat");
    this.m_K[2].addItem("Very Light");
    this.m_K[2].addItem("Light");
    this.m_K[2].addItem("Normal");
    this.m_K[2].addItem("Deep");
    this.m_K[2].addItem("Very Deep");
    this.m_K[2].select(((PVRoundButton)paramObject).getColorsDepth());
    super.setObject(paramObject);
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    PVBorder.draw(paramGraphics, new Rectangle(5, 36, 270, 43), 12386493, getBackground());
  }

  public void doEvent(Object paramObject, int paramInt) {
    if (paramObject == this.m_K[0]) {
      ((PVRoundButton)this.m_targ).setShape(this.m_K[0].getSelectedIndex());
    } else if (paramObject == this.m_K[1]) {
      ((PVRoundButton)this.m_targ).setCenterArea(this.m_K[1].getSelectedIndex());
    } else if (paramObject == this.m_K[2]) {
      ((PVRoundButton)this.m_targ).setColorsDepth(this.m_K[2].getSelectedIndex());
    }
    super.doEvent(paramObject, paramInt);
  }
}
