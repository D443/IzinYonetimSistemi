package adapter;

/**
 * Created by MYPC on 26.4.2015.
 */
public class BasvurularYonetim {

    private String basvuruTarih;
    private String izinSure;
    private String formAd;
    private String FormId;

    public BasvurularYonetim(String basvuruTarih,String izinSure,String formAd, String FormId  ) {
        this.basvuruTarih = basvuruTarih;
        this.izinSure = izinSure;
        this.formAd= formAd;
        this.FormId = FormId;

    }

    public String getBasvuruTarih() {
        return this.basvuruTarih;
    }
    public String getIzinSure() {
        return this.izinSure;
    }
    public String getformAd() {
        return this.formAd;
    }
    public String getFormId() {
        return this.FormId;
    }

}