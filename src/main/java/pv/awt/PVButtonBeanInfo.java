package pv.awt;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class PVButtonBeanInfo extends SimpleBeanInfo {
  protected int m_i = 46;

  static Class class$pv$awt$PVButtonCustomizer;

  static Class class$pv$awt$PVButton;

  public PVButtonBeanInfo() {
    this.m_i = 46;
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

  protected Class getCls(boolean paramBoolean) {
    return paramBoolean ? ((class$pv$awt$PVButtonCustomizer != null) ? class$pv$awt$PVButtonCustomizer : (class$pv$awt$PVButtonCustomizer = class$("pv.awt.PVButtonCustomizer"))) : ((class$pv$awt$PVButton != null) ? class$pv$awt$PVButton : (class$pv$awt$PVButton = class$("pv.awt.PVButton")));
  }

  protected String getStr(int paramInt) {
    switch (paramInt) {
      case 0:
        return "Background";
      case 1:
        return "Foreground";
      case 2:
        return "Bounds";
      case 3:
        return "ShadowColor";
      case 4:
        return "Text3DColor";
      case 5:
        return "BorderColor";
      case 6:
        return "Horizontal3D";
      case 7:
        return "Vertical3D";
      case 8:
        return "Font";
      case 9:
        return "Name";
      case 10:
        return "Text";
      case 11:
        return "AutoResize";
      case 12:
        return "Shadow";
      case 13:
        return "ShadowWidth";
      case 14:
        return "Aureole";
      case 15:
        return "TextPressed";
      case 16:
        return "Pressed";
      case 17:
        return "CanKeepPressed";
      case 18:
        return "ThreeButtonStates";
      case 19:
        return "CanGetFocus";
      case 20:
        return "SwitchColorsOnPress";
      case 21:
        return "SwitchShadowOnPress";
      case 22:
        return "ExtraPoppedUp";
      case 23:
        return "MultiLine";
      case 24:
        return "BorderStyle";
      case 25:
        return "EnableEnter";
      case 26:
        return "EnableSpace";
      case 27:
        return "EnableEscape";
      case 28:
        return "Enabled";
      case 29:
        return "MinLeftEdgeOfText";
      case 30:
        return "EnableKeyboard";
      case 31:
        return "EnableMouse";
      case 32:
        return "EnableRightMouse";
      case 33:
        return "TextAlignment";
      case 34:
        return "MouseInsideForeground";
      case 35:
        return "ActionCommand";
      case 36:
        return "DoubleBuffered";
      case 37:
        return "FocusRectAroundText";
      case 38:
        return "ImageAlignment";
      case 39:
        return "Triangle";
      case 40:
        return "CheckBox";
      case 41:
        return "StretchedImage";
      case 42:
        return "ImageEdge";
      case 43:
        return "TriangleSize";
      case 44:
        return "ToggleOnPress";
    }
    return "FocusRect";
  }

  public Image getIcon(int paramInt) {
    return loadImage(String.valueOf(iconName()) + ((paramInt == 2) ? "32.gif" : "16.gif"));
  }

  protected String iconName() {
    return "ImButt";
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
