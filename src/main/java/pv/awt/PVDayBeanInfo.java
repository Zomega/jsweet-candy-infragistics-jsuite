package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVDayBeanInfo extends SimpleBeanInfo {
  public PropertyDescriptor[] getPropertyDescriptors() {
    String str = null;
    PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[48];
    try {
      for (byte b = 0; b < 48; b++) {
        switch (b) {
          case 0:
            str = "Background";
            break;
          case 1:
            str = "Foreground";
            break;
          case 2:
            str = "AppointmentBackground";
            break;
          case 3:
            str = "SelectionColor";
            break;
          case 4:
            str = "Text3DColor";
            break;
          case 5:
            str = "ShadowColor";
            break;
          case 6:
            str = "BorderColor";
            break;
          case 7:
            str = "CaptionForeground";
            break;
          case 8:
            str = "RowBackground";
            break;
          case 9:
            str = "SpinColor";
            break;
          case 10:
            str = "RowLabelBackground";
            break;
          case 11:
            str = "RowLabelForeground";
            break;
          case 12:
            str = "GridColor";
            break;
          case 13:
            str = "Font";
            break;
          case 14:
            str = "CaptionFont";
            break;
          case 15:
            str = "RowLabelFont";
            break;
          case 16:
            str = "BorderStyle";
            break;
          case 17:
            str = "Shadow";
            break;
          case 18:
            str = "ShadowWidth";
            break;
          case 19:
            str = "CaptionText3D";
            break;
          case 20:
            str = "RowLabelText3D";
            break;
          case 21:
            str = "ShowDOW";
            break;
          case 22:
            str = "TwelveHours";
            break;
          case 23:
            str = "Grid";
            break;
          case 24:
            str = "FocusRectangle";
            break;
          case 25:
            str = "EnableDeleteKey";
            break;
          case 26:
            str = "EnableAlarmDialog";
            break;
          case 27:
            str = "EnableEditDialog";
            break;
          case 28:
            str = "Editable";
            break;
          case 29:
            str = "RowLabelBorder";
            break;
          case 30:
            str = "ActionMask";
            break;
          case 31:
            str = "SpinBorder";
            break;
          case 32:
            str = "SpinTimeHeight";
            break;
          case 33:
            str = "SpinDateHeight";
            break;
          case 34:
            str = "RowHeight";
            break;
          case 35:
            str = "CaptionBorder";
            break;
          case 36:
            str = "Delta";
            break;
          case 37:
            str = "StartTime";
            break;
          case 38:
            str = "EndTime";
            break;
          case 39:
            str = "Calendar";
            break;
          case 40:
            str = "DialogOnDoubleClick";
            break;
          case 41:
            str = "DarkPressedButton";
            break;
          case 42:
            str = "Enabled";
            break;
          case 43:
            str = "ShowRowLabels";
            break;
          case 44:
            str = "ShowCaption";
            break;
          case 45:
            str = "UseLocale";
            break;
          case 46:
            str = "Name";
            break;
          default:
            str = "Bounds";
            break;
        }
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(str, PVDay.class);
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  public Image getIcon(int paramInt) {
    return loadImage((paramInt == 2) ? "Day32.gif" : "Day16.gif");
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(PVDay.class, PVDayCustomizer.class);
  }
}
