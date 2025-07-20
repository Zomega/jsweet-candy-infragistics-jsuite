package pv.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.Serializable;

public class Page0 implements Serializable {
  public Component m_panel;

  public Object m_text;

  public Color m_foreground;

  public Color m_background;

  public transient Image m_image;

  public boolean m_enabled = true;

  public int m_width = 60;

  public transient int m_left;

  public Page0(Component paramComponent, Object paramObject) {
    this.m_panel = paramComponent;
    if (paramObject instanceof Image) {
      this.m_image = (Image)paramObject;
      return;
    }
    this.m_text = paramObject;
  }

  public String toString() {
    return (this.m_text == null) ? "" : this.m_text.toString();
  }
}
