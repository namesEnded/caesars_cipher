package com.example.labOne;

public class letterTableItem {
    private int id;
    private char letter;
    private int freq;

    public letterTableItem(int id, char letter, int freq) {
        this.id = id;
        this.letter = letter;
        this.freq = freq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }
}
