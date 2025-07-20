package pv.awt;

public class PVPassword extends PVEdit {
  public static final int MATCH = 8;

  protected char m_echo = '*';

  protected String m_password;

  public PVPassword() {
    this(null, '*');
  }

  public PVPassword(char paramChar) {
    this(null, paramChar);
  }

  public PVPassword(String paramString, char paramChar) {
    this.m_echo = paramChar;
    setPassword(paramString);
    setSelectionOnFocus(1);
    if (this.m_password != null || this.m_echo != '\000')
      setCase(2);
  }

  public void setEchoCharacter(String paramString) {
    this.m_echo = Character.MIN_VALUE;
    if (paramString != null && paramString.length() > 0)
      this.m_echo = paramString.charAt(0);
    paint(null);
  }

  public String getEchoCharacter() {
    return (this.m_echo == '\000') ? "" : String.valueOf(this.m_echo);
  }

  public void setDigitsOnly(boolean paramBoolean) {
    this.m_2 &= 0xFFFFFFF7;
    if (paramBoolean)
      this.m_2 |= 0x8;
    checkLen();
  }

  public boolean getDigitsOnly() {
    return !((this.m_2 & 0x8) == 0);
  }

  protected String text() {
    if (this.m_echo == '\000')
      return this.m_text;
    String str1 = "";
    String str2 = getEchoCharacter();
    for (int i = this.m_text.length(); i > 0; i--)
      str1 = String.valueOf(str1) + str2;
    return str1;
  }

  void checkLen() {
    super.checkLen();
    int i = this.m_text.length();
    if (getDigitsOnly())
      for (byte b = 0; b < i; b++) {
        if (!Character.isDigit(this.m_text.charAt(b))) {
          this.m_text = String.valueOf(this.m_text.substring(0, b)) + ((b + 1 >= i) ? "" : this.m_text.substring(b + 1, i));
          i--;
        }
      }
    if (isMatch())
      fireAction(8);
  }

  public boolean isMatch() {
    return (this.m_password != null && doCase(this.m_text).compareTo(doCase(this.m_password)) == 0);
  }

  public void setPassword(String paramString) {
    if (paramString != null && paramString.length() == 0)
      paramString = null;
    this.m_password = paramString;
  }

  public String getPassword() {
    return (this.m_password == null) ? "" : this.m_password;
  }
}
