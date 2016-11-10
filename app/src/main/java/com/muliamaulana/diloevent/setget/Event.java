package com.muliamaulana.diloevent.setget;

/**
 * Created by muliamaulana on 11/9/16.
 */

public class Event {
    private String nama_event;
    private String deskripsi;
    private String tanggal;
    private String waktu;
    private String kuota_event;
    private String image;

    public Event() {
    }

    public Event(String nama_event, String deskripsi, String tanggal, String waktu, String kuota_event, String image) {
        this.nama_event = nama_event;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.kuota_event = kuota_event;
        this.image = image;
    }

    public String getNama_event() {
        return nama_event;
    }

    public void setNama_event(String nama_event) {
        this.nama_event = nama_event;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getKuota_event() {
        return kuota_event;
    }

    public void setKuota_event(String kuota_event) {
        this.kuota_event = kuota_event;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}