package com.example.passwordmanager.Model;

import javafx.scene.image.Image;

import java.io.IOException;

public interface DisplayableEntry {
    public void delete();
    String getName();
    String getUnderName();
    Image getImage() throws IOException;
}