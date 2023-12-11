package com.example.passwordmanager;

import com.example.passwordmanager.DisplayableEntry;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class SecureNoteEntry extends PasswordEntry {

    private String name, noteSubject, noteContent;

    public SecureNoteEntry(String name, String noteSubject, String noteContent) {
        this.name = name;
        this.noteSubject = noteSubject;
        this.noteContent = noteContent;
    }

    public String getName() {return this.name;}
    public String getNoteSubject() {return this.noteSubject;}
    public String getNoteContent() {return this.noteContent;}

    @Override
    public String getUnderName() {
        return getNoteSubject();
    }

    @Override
    public Image getImage() throws IOException {
        return new Image(Objects.requireNonNull(getClass().getResource("Images/note.png")).openStream());
    }

    public void setName(String s) {this.name = s;}
    public void setNoteSubject(String s) {this.noteSubject = s;}
    public void setNoteContent(String s) {this.noteContent = s;}

    @Override
    public String getSearchTerm() {
        String searchTerm = "";
        String category;
        searchTerm += getName() + "Secure note";
        try {
            category = getCategory().getName();
        } catch (NullPointerException e) {
            category = "";
        }
        return (searchTerm + category).toLowerCase().replace(" ", "");
    }
}
