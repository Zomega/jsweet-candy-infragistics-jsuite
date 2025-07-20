package pv.awt;

public class PVMask extends PVEdit {
  public static final int FULL = 32;

  protected StringBuffer m_sbMask;

  protected StringBuffer m_sbText = new StringBuffer("");

  protected char m_chEscape = '~';

  protected char m_chPrompt = '_';

  protected char m_ch0 = ' ';

  public PVMask() {
    this("********");
  }

  public PVMask(String paramString) {
    super("");
    setMask(paramString);
    setCaretShape(1);
    this.m_1 |= 0x1014024;
  }

  public Object getValue() {
    return ((this.m_2 & 0xC0000000) != 0) ? build(!((this.m_2 & 0x40000000) != 0)) : doText(false);
  }

  public void setValue(Object paramObject) {
    if ((this.m_2 & 0xC0000000) != 0 && paramObject != null) {
      setEditablePartOfText(paramObject.toString());
      return;
    }
    super.setValue(paramObject);
  }

  public int getValueType() {
    return this.m_2 >>> 30;
  }

  public void setValueType(int paramInt) {
    this.m_2 = this.m_2 & 0x3FFFFFFF | (paramInt & 0x3) << 30;
  }

  public boolean getCutByEmptyPosition() {
    return !((this.m_4 & 0x2000) == 0);
  }

  public void setCutByEmptyPosition(boolean paramBoolean) {
    this.m_4 &= 0xFFFFDFFF;
    if (paramBoolean)
      this.m_4 |= 0x2000;
  }

  public String getEscapeChar() {
    return String.valueOf(this.m_chEscape);
  }

  public void setEscapeChar(String paramString) {
    if (paramString != null && paramString.length() > 0)
      this.m_chEscape = paramString.charAt(0);
  }

  public String getPromptChar() {
    return String.valueOf(this.m_chPrompt);
  }

  public void setPromptChar(String paramString) {
    if (paramString != null && paramString.length() > 0)
      this.m_chPrompt = paramString.charAt(0);
  }

  public String getEmptyChar() {
    return String.valueOf(this.m_ch0);
  }

  public void setEmptyChar(String paramString) {
    if (paramString != null && paramString.length() > 0)
      this.m_ch0 = paramString.charAt(0);
  }

  public void setMask(String paramString) {
    this.m_4 &= 0xFFFFEFFF;
    if (paramString == null)
      paramString = "";
    this.m_sbMask = new StringBuffer(paramString);
    int i = 0;
    int j = this.m_sbMask.length();
    byte b1 = 0;
    byte b2 = 0;
    this.m_iCaret = 0;
    while (i < j) {
      char c = this.m_sbMask.charAt(i++);
      switch (c) {
        case '#':
          c = '\001';
          b1++;
          break;
        case '@':
          c = '\002';
          b1++;
          break;
        case '&':
          c = '\003';
          b1++;
          break;
        case '*':
          c = '\004';
          b1++;
          break;
        case '^':
          c = '\005';
          b1++;
          break;
        case '|':
          c = '\006';
          b1++;
          break;
        case '?':
          c = '\020';
          b1++;
          break;
        case '%':
          c = '\021';
          b1++;
          break;
        case '!':
          c = '\022';
          b1++;
          break;
        case '\\':
          c = '\023';
          b1++;
          this.m_4 |= 0x1000;
          break;
      }
      if (b1 == 1)
        this.m_iCaret = b2;
      if (c == this.m_chEscape && i < j)
        switch (this.m_sbMask.charAt(i)) {
          case '!':
          case '#':
          case '%':
          case '&':
          case '*':
          case '?':
          case '@':
          case '\\':
          case '^':
          case '|':
            c = this.m_sbMask.charAt(i++);
            break;
        }
      this.m_sbMask.setCharAt(b2++, c);
    }
    this.m_sbMask.setLength(b2);
    j = this.m_sbText.length();
    if (j > b1) {
      this.m_sbText.setLength(b1);
    } else {
      for (i = j; i < b1; i++)
        this.m_sbText.append('\007');
    }
    this.m_text = doCase(null);
    this.m_iEndSel = this.m_iStartSel = this.m_iCaret;
    paint(null);
  }

  public String getMask() {
    StringBuffer stringBuffer = new StringBuffer(this.m_sbMask.toString());
    byte b1 = 0;
    byte b2 = 0;
    int i = stringBuffer.length();
    while (b1 < i) {
      char c = this.m_sbMask.charAt(b1++);
      switch (c) {
        case '\001':
          c = '#';
          break;
        case '\002':
          c = '@';
          break;
        case '\003':
          c = '&';
          break;
        case '\004':
          c = '*';
          break;
        case '\005':
          c = '^';
          break;
        case '\006':
          c = '|';
          break;
        case '\020':
          c = '?';
          break;
        case '\021':
          c = '%';
          break;
        case '\022':
          c = '!';
          break;
        case '\023':
          c = '\\';
          break;
        case '!':
        case '#':
        case '%':
        case '&':
        case '*':
        case '?':
        case '@':
        case '\\':
        case '^':
        case '|':
          stringBuffer.insert(b2++, this.m_chEscape);
          break;
      }
      stringBuffer.setCharAt(b2++, c);
    }
    return stringBuffer.toString();
  }

  public void setText(String paramString) {
    byte b2 = 0;
    if (this.m_text == null) {
      super.setText(null);
      return;
    }
    if (paramString == null)
      paramString = "";
    int i = paramString.length();
    int j = this.m_sbMask.length();
    for (byte b1 = 0; b1 < j; b1++) {
      if (is1_6(this.m_sbMask.charAt(b1))) {
        boolean bool = (b1 < i) ? paramString.charAt(b1) : true;
        this.m_sbText.setCharAt(b2++, bool);
      }
    }
    this.m_text = doCase(null);
    paint(null);
  }

  public String getText() {
    return doText(false);
  }

  public String getEditablePartOfText() {
    return build(false);
  }

  public void setEditablePartOfText(String paramString) {
    int i = this.m_sbText.length();
    this.m_sbText = new StringBuffer(paramString);
    if (paramString == null)
      paramString = "";
    int j = paramString.length();
    if (j > i) {
      this.m_sbText.setLength(i);
    } else {
      for (int k = j; k < i; k++)
        this.m_sbText.append('\007');
    }
    this.m_text = doCase(null);
    paint(null);
  }

  public String getCleanText() {
    return build(true);
  }

  public void setCleanText(String paramString) {
    setEditablePartOfText(paramString);
  }

  String build(boolean paramBoolean) {
    StringBuffer stringBuffer = new StringBuffer(this.m_sbText.toString());
    byte b1 = 0;
    byte b2 = 0;
    int i = stringBuffer.length();
    while (b1 < i) {
      char c;
      if ((c = this.m_sbText.charAt(b1++)) == '\007')
        if (!paramBoolean) {
          c = this.m_ch0;
        } else {
          continue;
        }
      stringBuffer.setCharAt(b2++, c);
    }
    if (b2 < i)
      stringBuffer.setLength(b2);
    return new String(stringBuffer);
  }

  public boolean isFull() {
    return !((this.m_4 & 0x40000000) != 0);
  }

  char chekCh(char paramChar1, char paramChar2) {
    switch (paramChar2) {
      case '\001':
        if (!Character.isDigit(paramChar1))
          paramChar1 = '\007';
        return paramChar1;
      case '\002':
      case '\005':
      case '\006':
        if (!Character.isLetter(paramChar1))
          paramChar1 = '\007';
        if (paramChar2 != '\002')
          paramChar1 = (paramChar2 == '\005') ? Character.toUpperCase(paramChar1) : Character.toLowerCase(paramChar1);
        return paramChar1;
      case '\003':
        if (!Character.isLetter(paramChar1) && paramChar1 != ' ')
          paramChar1 = '\007';
        return paramChar1;
      case '\004':
        if (paramChar1 < ' ')
          paramChar1 = '\007';
        return paramChar1;
      case '\023':
        if (!super.isOK(paramChar1))
          paramChar1 = '\007';
        return paramChar1;
      case '\022':
        if (paramChar1 == ' ')
          return '\007';
      case '\021':
        if (paramChar1 != ' ');
        return paramChar1;
      case '\020':
        if (Character.isLetterOrDigit(paramChar1) == (!(paramChar2 != '\022')))
          paramChar1 = '\007';
        return paramChar1;
    }
    return Character.MIN_VALUE;
  }

  boolean is1_6(char paramChar) {
    return !((paramChar <= '\000' || paramChar > '\006') && (paramChar < '\020' || paramChar > '\023'));
  }

  boolean isOK(char paramChar) {
    return ((this.m_4 & 0x1000) == 0) ? super.isOK(paramChar) : true;
  }

  protected String text() {
    return doText(!((this.m_1 & Integer.MIN_VALUE) == 0));
  }

  boolean replaceSelByChar(char paramChar) {
    byte b2 = 0;
    int i = -1;
    int j = this.m_sbMask.length();
    while (++i < j && !is1_6(this.m_sbMask.charAt(i)));
    if (this.m_iCaret < i)
      this.m_iCaret = i;
    if (this.m_iEndSel < i)
      this.m_iEndSel = i;
    if (this.m_iStartSel < i)
      this.m_iStartSel = i;
    for (i = 0; i < this.m_iStartSel; i++) {
      if (is1_6(this.m_sbMask.charAt(i)))
        b2++;
    }
    int k = this.m_sbText.length();
    if (b2 >= k && paramChar != '\b')
      return false;
    byte b1 = b2;
    for (i = i; i < this.m_iEndSel; i++) {
      if (is1_6(this.m_sbMask.charAt(i)))
        b1++;
    }
    if (paramChar == '\b') {
      if (this.m_iEndSel == this.m_iStartSel) {
        if (b2-- == 0)
          return false;
        while (this.m_iStartSel > 0 && !is1_6(this.m_sbMask.charAt(--this.m_iStartSel)));
        this.m_iCaret = this.m_iStartSel;
      }
      paramChar = '\007';
    }
    if (paramChar == '\007') {
      if (this.m_iEndSel != this.m_iStartSel && b1 == b2)
        return false;
    } else {
      for (i = this.m_iStartSel; i < j && !is1_6(this.m_sbMask.charAt(i)); i++);
      paramChar = chekCh(paramChar, this.m_sbMask.charAt(i));
      if (paramChar <= '\007')
        return false;
    }
    if (getInsertEditMode()) {
      int m = k;
      if (b1 == b2)
        if (paramChar == '\007') {
          b1++;
        } else {
          m--;
        }
      String str = this.m_sbText.toString().substring(b1, m);
      this.m_sbText.setLength(b2);
      if (paramChar != '\007') {
        this.m_sbText.append(paramChar);
        this.m_iStartSel++;
      }
      this.m_sbText.append(str);
    } else {
      this.m_sbText.setCharAt(b2++, paramChar);
      for (i = b2; i < b1; i++)
        this.m_sbText.setCharAt(i, '\007');
      if (paramChar != '\007' || this.m_iEndSel == this.m_iStartSel)
        this.m_iStartSel++;
    }
    for (i = this.m_sbText.length(); i < k; i++)
      this.m_sbText.append('\007');
    this.m_iEndSel = this.m_iCaret = this.m_iStartSel;
    return true;
  }

  protected boolean filterChar(char paramChar) {
    int i = this.m_sbMask.length();
    if (paramChar == '\b') {
      if (this.m_iEndSel == 0)
        return true;
    } else {
      if (paramChar <= '\033')
        return false;
      if (this.m_iStartSel == i)
        return true;
    }
    if (paramChar == '')
      paramChar = '\007';
    if (!replaceSelByChar(paramChar))
      return true;
    this.m_text = doCase(null);
    if ((this.m_4 & 0x40000000) == 0)
      fireAction(32);
    if (this.m_iCaret >= i)
      return true;
    while (!is1_6(this.m_sbMask.charAt(this.m_iCaret++))) {
      if (this.m_iCaret >= i) {
        while (--this.m_iCaret > 0 && !is1_6(this.m_sbMask.charAt(this.m_iCaret)));
        this.m_iStartSel = this.m_iEndSel = ++this.m_iCaret;
        return true;
      }
    }
    this.m_iStartSel = this.m_iEndSel = --this.m_iCaret;
    return true;
  }

  String doText(boolean paramBoolean) {
    if (this.m_sbMask == null)
      return "";
    StringBuffer stringBuffer = new StringBuffer(this.m_sbMask.toString());
    int i = 0;
    int j = this.m_sbText.length();
    int k = this.m_sbMask.length();
    this.m_4 &= 0xBFFFFFFF;
    for (byte b = 0; b < k && i != j; b++) {
      char c = '\007';
      char c1 = this.m_sbMask.charAt(b);
      if (c1 < ' ') {
        try {
          c = chekCh(this.m_sbText.charAt(i), c1);
        } catch (Exception exception) {}
        if (c < ' ') {
          if (getCutByEmptyPosition()) {
            stringBuffer.setLength((i == 0) ? 0 : b);
            while (i < j)
              this.m_sbText.setCharAt(i++, '\007');
            break;
          }
          this.m_sbText.setCharAt(i, c);
          c = paramBoolean ? this.m_chPrompt : this.m_ch0;
          this.m_4 |= 0x40000000;
        }
        i++;
        stringBuffer.setCharAt(b, c);
      }
    }
    return new String(stringBuffer);
  }

  String doCase(String paramString) {
    if (getCase() > 0)
      this.m_sbText = new StringBuffer((getCase() == 1) ? this.m_sbText.toString().toUpperCase() : this.m_sbText.toString().toLowerCase());
    return doText(false);
  }
}
