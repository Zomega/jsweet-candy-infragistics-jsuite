package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVWeekBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    String str = null;
    PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[24];
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
            str = "NumberOfDays";
            break;
          case 4:
            str = "NumberOfWeeks";
            break;
          case 5:
            str = "DeltaOfWeek";
            break;
          case 6:
            str = "TimeScrollBarWidth";
            break;
          case 7:
            str = "TimeScrollBarOnLeft";
            break;
          case 8:
            str = "ShowLines";
            break;
          case 9:
            str = "ShowDateScrollBar";
            break;
          case 10:
            str = "DOW";
            break;
          case 11:
            str = "ShadowColor";
            break;
          case 12:
            str = "BorderColor";
            break;
          case 13:
            str = "BorderStyle";
            break;
          case 14:
            str = "Shadow";
            break;
          case 15:
            str = "ShadowWidth";
            break;
          case 16:
            str = "Delta";
            break;
          case 17:
            str = "StartTime";
            break;
          case 18:
            str = "EndTime";
            break;
          case 19:
            str = "RowHeight";
            break;
          case 20:
            str = "Format";
            break;
          case 21:
            str = "Name";
            break;
          case 22:
            str = "Enabled";
            break;
          default:
            str = "Bounds";
            break;
        }
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(str, PVWeek.class);
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  public Image getIcon(int paramInt) {
    return loadImage((paramInt == 2) ? "Week32.gif" : "Week16.gif");
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(PVWeek.class);
  }
}
