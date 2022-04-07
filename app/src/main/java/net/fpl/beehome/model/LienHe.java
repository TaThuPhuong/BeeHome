package net.fpl.beehome.model;

import java.io.Serializable;

public class LienHe implements Serializable {

    private String name;
    private String numberPhone;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public LienHe(String name, String numberPhone, boolean check) {
        this.name = name;
        this.numberPhone = numberPhone;
        this.check = check;
    }

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

    @Override
    public String toString() {
        return "LienHe{" +
                "name='" + name + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", check=" + check +
                '}';
    }
}
