package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVCalculatorBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    String str = null;
    PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[23];
    try {
      for (byte b = 0; b < arrayOfPropertyDescriptor.length; b++) {
        switch (b) {
          case 0:
            str = "Background";
            break;
          case 1:
            str = "Foreground";
            break;
          case 2:
            str = "Font";
            break;
          case 3:
            str = "BorderStyle";
            break;
          case 4:
            str = "Shadow";
            break;
          case 5:
            str = "ShadowWidth";
            break;
          case 6:
            str = "ShadowColor";
            break;
          case 7:
            str = "Name";
            break;
          case 8:
            str = "ButtonsBorder";
            break;
          case 9:
            str = "Buttons3States";
            break;
          case 10:
            str = "ButtonsBackground";
            break;
          case 11:
            str = "Appearance";
            break;
          case 12:
            str = "Spacing";
            break;
          case 13:
            str = "Margins";
            break;
          case 14:
            str = "MaxHeight";
            break;
          case 15:
            str = "Format";
            break;
          case 16:
            str = "DigitForeground";
            break;
          case 17:
            str = "OperationForeground";
            break;
          case 18:
            str = "Triangles";
            break;
          case 19:
            str = "EnterAsOK";
            break;
          case 20:
            str = "ActionMask";
            break;
          case 21:
            str = "KeepPressedOperationButton";
            break;
          default:
            str = "Bounds";
            break;
        }
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(str, PVCalculator.class);
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  public Image getIcon(int paramInt) {
    return loadImage((paramInt == 2) ? "Clc32.gif" : "Clc16.gif");
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(PVCalculator.class);
  }
}
