package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVEditBeanInfo extends SimpleBeanInfo {
  protected int m_i = 47;

  static Class class$pv$awt$PVEditCustomizer;

  static Class class$pv$awt$PVEdit;

  public PVEditBeanInfo() {
    this.m_i = 47;
  }

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVEditCustomizer != null) ? class$pv$awt$PVEditCustomizer : (class$pv$awt$PVEditCustomizer = class$("pv.awt.PVEditCustomizer"))) : ((class$pv$awt$PVEdit != null) ? class$pv$awt$PVEdit : (class$pv$awt$PVEdit = class$("pv.awt.PVEdit")));
  }

  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor[] arrayOfPropertyDescriptor = new PropertyDescriptor[this.m_i];
      for (byte b = 0; b < this.m_i; b++) {
        arrayOfPropertyDescriptor[b] = new PropertyDescriptor(getStr(b), getCls(false));
        arrayOfPropertyDescriptor[b].setBound(true);
      }
      return arrayOfPropertyDescriptor;
    } catch (IntrospectionException introspectionException) {
      throw new Error(introspectionException.toString());
    }
  }

  protected String getStr(int paramInt) {
    switch (paramInt) {
      case 0:
        return "Background";
      case 1:
        return "Foreground";
      case 2:
        return "Columns";
      case 3:
        return "ShadowColor";
      case 4:
        return "BorderColor";
      case 5:
        return "Text3DColor";
      case 6:
        return "Horizontal3D";
      case 7:
        return "Vertical3D";
      case 8:
        return "Font";
      case 9:
        return "HideKeyEvent";
      case 10:
        return "Alignment";
      case 11:
        return "Text";
      case 12:
        return "AutoResize";
      case 13:
        return "FontStyle";
      case 14:
        return "Aureole";
      case 15:
        return "Odometer";
      case 16:
        return "BorderStyle";
      case 17:
        return "Shadow";
      case 18:
        return "ShadowWidth";
      case 19:
        return "Bounds";
      case 20:
        return "Name";
      case 21:
        return "DoubleBuffered";
      case 22:
        return "Editable";
      case 23:
        return "Enabled";
      case 24:
        return "InsertEditMode";
      case 25:
        return "AllowChangeMode";
      case 26:
        return "AutoScroll";
      case 27:
        return "CaretShape";
      case 28:
        return "CaretHidden";
      case 29:
        return "CaretInverted";
      case 30:
        return "CaretRate";
      case 31:
        return "CaretColor";
      case 32:
        return "SelectionBackground";
      case 33:
        return "SelectionForeground";
      case 34:
        return "SelectionOnFocus";
      case 35:
        return "SelectionInverted";
      case 36:
        return "SelectionKillByMouse";
      case 37:
        return "SelectionPaint";
      case 38:
        return "DisabledForeground";
      case 39:
        return "EnableUndo";
      case 40:
        return "SelectAllOnDoubleClick";
      case 41:
        return "ActionMask";
      case 42:
        return "FocusRect";
      case 43:
        return "Case";
      case 44:
        return "IncludeCharacters";
      case 45:
        return "ExcludeCharacters";
    }
    return "MaxLength";
  }

  public Image getIcon(int paramInt) {
    return loadImage(String.valueOf(iconName()) + ((paramInt == 2) ? "32.gif" : "16.gif"));
  }

  protected String iconName() {
    return "Edit";
  }

  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(getCls(false), getCls(true));
  }

  static Class class$(String paramString) {
    try {
      return Class.forName(paramString);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new NoClassDefFoundError(classNotFoundException.getMessage());
    }
  }
}
