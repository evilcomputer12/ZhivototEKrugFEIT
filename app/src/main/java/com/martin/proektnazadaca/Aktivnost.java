package com.martin.proektnazadaca;
public class Aktivnost {
    public String id, imeAktivnost, opisAktivnost, kraenRokAktivnost, lokacijaAktivnost, liceImeAktivnost, emailLiceAktivnost, telefonLiceAktivnost, urgencyAktivnost, recuringAktivnost, timeOfRecordCreation;
    public Aktivnost(){

    }

    public String getId() {
        return id;
    }

    public String getImeAktivnost() {
        return imeAktivnost;
    }

    public String getOpisAktivnost() {
        return opisAktivnost;
    }

    public String getKraenRokAktivnost() {
        return kraenRokAktivnost;
    }

    public String getLokacijaAktivnost() {
        return lokacijaAktivnost;
    }

    public String getLiceImeAktivnost() {
        return liceImeAktivnost;
    }

    public String getEmailLiceAktivnost() {
        return emailLiceAktivnost;
    }

    public String getTelefonLiceAktivnost() {
        return telefonLiceAktivnost;
    }

    public String getUrgencyAktivnost() {
        return urgencyAktivnost;
    }

    public String getRecuringAktivnost() {
        return recuringAktivnost;
    }

    public String getTimeOfRecordCreation() {
        return timeOfRecordCreation;
    }


    public Aktivnost(String id, String imeAktivnost, String opisAktivnost, String kraenRokAktivnost, String lokacijaAktivnost, String liceImeAktivnost, String emailLiceAktivnost, String telefonLiceAktivnost, String urgencyAktivnost, String recuringAktivnost, String timeOfRecordCreation){
        this.id = id;
        this.imeAktivnost = imeAktivnost;
        this.opisAktivnost = opisAktivnost;
        this.kraenRokAktivnost = kraenRokAktivnost;
        this.lokacijaAktivnost = lokacijaAktivnost;
        this.liceImeAktivnost = liceImeAktivnost;
        this.emailLiceAktivnost = emailLiceAktivnost;
        this.telefonLiceAktivnost = telefonLiceAktivnost;
        this.urgencyAktivnost = urgencyAktivnost;
        this.recuringAktivnost = recuringAktivnost;
        this.timeOfRecordCreation = timeOfRecordCreation;
    }
}
