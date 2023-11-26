package com.example.passwordmanager.Model;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

/** Description of class **/

public class WebsiteEntry extends Entry{

    private String URL;

    public WebsiteEntry(String name, String identifier, int passwordLength, String URL) throws IOException {
        super("WEB", name, identifier, "ALLSIGNS", passwordLength);
        this.URL = URL;
        setImage(new Image(Objects.requireNonNull(getClass().getResource("Images/door-key.png")).openStream()));
    }


}
