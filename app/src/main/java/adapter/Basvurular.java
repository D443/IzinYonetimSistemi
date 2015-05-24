package adapter;

/**
 * Created by MYPC on 26.4.2015.
 */
public class Basvurular {

    private String basvuruTarih;
    private String izinSure;
    private String formOnay;
    private String FormId;

    public Basvurular(String basvuruTarih,String izinSure,String formOnay, String FormId  ) {
        this.basvuruTarih = basvuruTarih;
        this.izinSure = izinSure;
        this.formOnay = formOnay;
        this.FormId = FormId;

    }

    public String getBasvuruTarih() {
        return this.basvuruTarih;
    }
    public String getIzinSure() {
        return this.izinSure;
    }
    public String getformOnay() {
        return this.formOnay;
    }
    public String getFormId() {
        return this.FormId;
    }

}