package com.example.tu4q2;

public class Model {
    private String text;
    private boolean isSelected = false;
    public Model(String text, boolean selected) {
        this.text = text;
        isSelected = selected;
    }
    public String getText() {
        return text;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }
}