package com.example.passwordmanager;

import javafx.scene.image.Image;

import java.io.IOException;

public interface DisplayableEntry {
    String getName();
    String getUnderName();
    Image getImage() throws IOException;
    int getType();
    int getEntryId();
}
