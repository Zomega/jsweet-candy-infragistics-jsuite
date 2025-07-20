package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVSpinPlusBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    String str = null;
    PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[14];
    try {
      for (byte b = 0; b < 14; b++) {
        switch (b) {
          case 0:
            str = "Background";
            break;
          case 1:
            str = "Foreground";
            break;
          case 2:
            str = "ButtonColor";
            break;
          case 3:
            str = "Font";
            break;
          case 4:
            str = "Name";
            break;
          case 5:
            str = "DefaultImages";
            break;
          case 6:
            str = "AutoSpin";
            break;
          case 7:
            str = "Style";
            break;
          case 8:
            str = "Delay";
            break;
          case 9:
            str = "SpeedUp";
            break;
          case 10:
            str = "ButtonWidth";
            break;
          case 11:
            str = "ButtonBorder";
            break;
          case 12:
            str = "PVObject";
            break;
          default:
            str = "Bounds";
            break;
        }
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(str, PVSpinPlus.class);
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  public Image getIcon(int paramInt) {
    return loadImage((paramInt == 2) ? "Spin32.gif" : "Spin16.gif");
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(PVSpinPlus.class, PVSpinPlusCustomizer.class);
  }
}
