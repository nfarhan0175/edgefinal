package com.example.edgefinal;

public class data {
    private int id;
    private String name;
    private String note;

    // Constructor
    public data(int id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }
}
