package adapter;

/**
 * Created by MYPC on 21.4.2015.
 */
public class Kisiler {

    private String isim;
    private String id;

    public Kisiler(String isim,String id) {
        this.isim = isim;
        this.id = id;

    }

    public String getIsım() {
        return this.isim;
    }
    public String getId() {
        return this.id;
    }

}