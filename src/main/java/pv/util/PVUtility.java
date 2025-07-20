package pv.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

public class PVUtility {
  public static final Frame frame(Component paramComponent, Rectangle paramRectangle) {
    return (Frame)parent(paramComponent, paramRectangle, true);
  }

  public static final Window parent(Component paramComponent, Rectangle paramRectangle, boolean paramBoolean) {
    boolean bool = false;
    Window window = null;
    while (!(paramComponent instanceof Frame) && (paramComponent = paramComponent.getParent()) != null) {
      if (!bool && paramRectangle != null)
        if (paramComponent instanceof java.applet.Applet) {
          Point point = paramComponent.getLocationOnScreen();
          paramRectangle.x += point.x;
          paramRectangle.y += point.y;
        } else {
          paramRectangle.x += (paramComponent.getBounds()).x;
          paramRectangle.y += (paramComponent.getBounds()).y;
        }
      if (bool = paramComponent instanceof Window) {
        window = (Window)paramComponent;
        if (paramBoolean || !(paramComponent instanceof java.awt.Dialog))
          continue;
        break;
      }
    }
    return paramBoolean ? window : (Window)paramComponent;
  }

  public static final void screen(Component paramComponent, Rectangle paramRectangle) {
    Dimension dimension = paramComponent.getToolkit().getScreenSize();
    if (paramRectangle.y + paramRectangle.height + 30 > dimension.height)
      paramRectangle.y -= (paramComponent.getSize()).height + paramRectangle.height;
    if (paramRectangle.y > (dimension.height -= paramRectangle.height + 30))
      paramRectangle.y = dimension.height;
    if (paramRectangle.y < 0)
      paramRectangle.y = 0;
    if (paramRectangle.x + (paramComponent.getSize()).width > 1 && paramRectangle.x < dimension.width) {
      if (paramRectangle.x > (dimension.width -= paramRectangle.width))
        paramRectangle.x = dimension.width;
      if (paramRectangle.x < 0)
        paramRectangle.x = 0;
    }
  }

  public static final void setBackground(Component paramComponent, Color paramColor) {
    paramComponent.setBackground(paramColor);
    byte b = (paramComponent instanceof Container) ? ((Container)paramComponent).getComponentCount() : 0;
    while (b-- > 0)
      setBackground(((Container)paramComponent).getComponent(b), paramColor);
  }
}
