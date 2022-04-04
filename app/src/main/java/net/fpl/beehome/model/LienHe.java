package net.fpl.beehome.model;

public class LienHe {

    private String name;
    private String numberPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public LienHe() {
    }

    public LienHe(String name, String numberPhone) {
        this.name = name;
        this.numberPhone = numberPhone;
    }
}
