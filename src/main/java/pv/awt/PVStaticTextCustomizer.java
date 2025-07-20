package pv.awt;

public class PVStaticTextCustomizer extends PVEditCustomizer {
  protected void init0() {
    this.m_name = "Static Text";
    super.init0();
  }

  public void setObject(Object paramObject) {
    super.setObject(paramObject);
    byte b = 0;
    while (++b < 11) {
      if (b != 5)
        this.m_c[b].setEnabled(false);
      if (b < 4)
        this.m_b[b + 5].setEnabled(false);
      if (b < 4)
        this.m_k[b + 8].setEnabled(false);
    }
  }
}
