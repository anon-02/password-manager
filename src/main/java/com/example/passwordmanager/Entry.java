package com.example.passwordmanager;

import javafx.scene.image.Image;

import java.io.IOException;

public interface Entry {
    String getName();
    String getUnderName();
    Image getImage() throws IOException;
}
