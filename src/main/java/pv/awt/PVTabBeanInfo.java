package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVTabBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    String str = null;
    PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[25];
    try {
      for (byte b = 0; b < 25; b++) {
        switch (b) {
          case 0:
            str = "Background";
            break;
          case 1:
            str = "Foreground";
            break;
          case 2:
            str = "ScrollColor";
            break;
          case 3:
            str = "Text3DColor";
            break;
          case 4:
            str = "Font";
            break;
          case 5:
            str = "Name";
            break;
          case 6:
            str = "TopStyle";
            break;
          case 7:
            str = "TabShape";
            break;
          case 8:
            str = "ScrollShape";
            break;
          case 9:
            str = "BorderStyle";
            break;
          case 10:
            str = "Text3D";
            break;
          case 11:
            str = "ScrollDelta";
            break;
          case 12:
            str = "AutoSizeTabWidth";
            break;
          case 13:
            str = "ParentColors";
            break;
          case 14:
            str = "ScrollBorder";
            break;
          case 15:
            str = "ChangeOnClick";
            break;
          case 16:
            str = "TabHeight";
            break;
          case 17:
            str = "DarkInactiveTabs";
            break;
          case 18:
            str = "Text3DInactiveTabs";
            break;
          case 19:
            str = "LineUpInactiveTabs";
            break;
          case 20:
            str = "EnableMouse";
            break;
          case 21:
            str = "EnableRightMouse";
            break;
          case 22:
            str = "DoubleBuffered";
            break;
          case 23:
            str = "WidthOfLeftTab";
            break;
          default:
            str = "Bounds";
            break;
        }
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(str, PVTab.class);
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  public Image getIcon(int paramInt) {
    return loadImage((paramInt == 2) ? "Tab32.gif" : "Tab16.gif");
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(PVTab.class, PVTabCustomizer.class);
  }
}
