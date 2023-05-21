package src;

import java.util.Date;

public class tabela {
    String id, emrimbiemri, nrpersonal, fakulteti, drejtimi, titullitemes,DataEaplikimit;
    public tabela(String id, String emrimbiemri, String nrpersonal, String fakulteti, String drejtitmi, String titullitemes,String DataEaplikimit) {
        this.id = id;
        this.emrimbiemri = emrimbiemri;
        this.nrpersonal = nrpersonal;
        this.fakulteti = fakulteti;
        this.titullitemes = titullitemes;
        this.drejtimi = drejtitmi;
        this.DataEaplikimit=DataEaplikimit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmri_dhe_Mbiemri() {
        return emrimbiemri;
    }

    public void setEmri_dhe_Mbiemri(String emrimbiemri) {
        this.emrimbiemri = emrimbiemri;
    }

    public String getNr_personal() {
        return nrpersonal;
    }

    public void setNr_personal(String nrpersonal) {
        this.nrpersonal = nrpersonal;
    }

    public String getFakulteti() {
        return fakulteti;
    }

    public void setFakulteti(String fakulteti) {
        this.fakulteti = fakulteti;
    }


    public String getDrejtimi() {
        return drejtimi;
    }

    public void setDrejtimi(String drejtimi) {
        this.drejtimi = drejtimi;
    }

    public String getTitulli_i_temes() {
        return titullitemes;
    }

    public void setTitulli_i_temes(String titullitemes) {
        this.titullitemes = titullitemes;
    }

    public String getDataEaplikimit() {
        return DataEaplikimit;
    }

    public void setDataEaplikimit(String dataEaplikimit) {
        DataEaplikimit = dataEaplikimit;
    }
}
