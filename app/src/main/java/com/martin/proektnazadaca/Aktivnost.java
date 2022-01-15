package com.martin.proektnazadaca;
public class Aktivnost {
    //public String id, imeAktivnost, opisAktivnost, kraenRokAktivnost, lokacijaAktivnost, liceImeAktivnost, emailLiceAktivnost, telefonLiceAktivnost, urgencyAktivnost, recuringAktivnost, timeOfRecordCreation, status, rating1, volonter, key;
    String id;
    String imeAktivnost;
    String opisAktivnost;
    String kraenRokAktivnost;
    String lokacijaAktivnost;
    String liceImeAktivnost;
    String emailLiceAktivnost;
    String telefonLiceAktivnost;
    String urgencyAktivnost;
    String recuringAktivnost;
    String timeOfRecordCreation;
    String status;
    String rating1;
    String volonter;
    String key;

    public Aktivnost() {

    }


    public Aktivnost(String id, String imeAktivnost, String opisAktivnost, String kraenRokAktivnost, String lokacijaAktivnost, String liceImeAktivnost, String emailLiceAktivnost, String telefonLiceAktivnost, String urgencyAktivnost, String recuringAktivnost, String timeOfRecordCreation, String key) {
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
        this.status = "Активна";
        this.rating1 = "Неоценето";
        this.volonter = "NaN";
        this.key = key;
    }

    public Aktivnost(String id, String imeAktivnost, String opisAktivnost, String kraenRokAktivnost, String lokacijaAktivnost, String liceImeAktivnost, String emailLiceAktivnost, String telefonLiceAktivnost, String urgencyAktivnost, String recuringAktivnost, String timeOfRecordCreation, String status, String rating1, String volonter, String key) {
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
        this.status = status;
        this.rating1 = rating1;
        this.volonter = volonter;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImeAktivnost() {
        return imeAktivnost;
    }

    public void setImeAktivnost(String imeAktivnost) {
        this.imeAktivnost = imeAktivnost;
    }

    public String getOpisAktivnost() {
        return opisAktivnost;
    }

    public void setOpisAktivnost(String opisAktivnost) {
        this.opisAktivnost = opisAktivnost;
    }

    public String getKraenRokAktivnost() {
        return kraenRokAktivnost;
    }

    public void setKraenRokAktivnost(String kraenRokAktivnost) {
        this.kraenRokAktivnost = kraenRokAktivnost;
    }

    public String getLokacijaAktivnost() {
        return lokacijaAktivnost;
    }

    public void setLokacijaAktivnost(String lokacijaAktivnost) {
        this.lokacijaAktivnost = lokacijaAktivnost;
    }

    public String getLiceImeAktivnost() {
        return liceImeAktivnost;
    }

    public void setLiceImeAktivnost(String liceImeAktivnost) {
        this.liceImeAktivnost = liceImeAktivnost;
    }

    public String getEmailLiceAktivnost() {
        return emailLiceAktivnost;
    }

    public void setEmailLiceAktivnost(String emailLiceAktivnost) {
        this.emailLiceAktivnost = emailLiceAktivnost;
    }

    public String getTelefonLiceAktivnost() {
        return telefonLiceAktivnost;
    }

    public void setTelefonLiceAktivnost(String telefonLiceAktivnost) {
        this.telefonLiceAktivnost = telefonLiceAktivnost;
    }

    public String getUrgencyAktivnost() {
        return urgencyAktivnost;
    }

    public void setUrgencyAktivnost(String urgencyAktivnost) {
        this.urgencyAktivnost = urgencyAktivnost;
    }

    public String getRecuringAktivnost() {
        return recuringAktivnost;
    }

    public void setRecuringAktivnost(String recuringAktivnost) {
        this.recuringAktivnost = recuringAktivnost;
    }

    public String getTimeOfRecordCreation() {
        return timeOfRecordCreation;
    }

    public void setTimeOfRecordCreation(String timeOfRecordCreation) {
        this.timeOfRecordCreation = timeOfRecordCreation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRating1() {
        return rating1;
    }

    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    public String getVolonter() {
        return volonter;
    }

    public void setVolonter(String volonter) {
        this.volonter = volonter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}