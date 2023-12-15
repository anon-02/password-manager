package com.example.passwordmanager.Model.Entries;

import com.example.passwordmanager.fxmlHelper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class SecureNoteEntry extends PasswordEntry {

    private String name, noteSubject, noteContent;
    int type = 4;
    int entryId;
    private fxmlHelper helper = fxmlHelper.getInstance();

    public SecureNoteEntry(int entryId, String name, String noteSubject, String noteContent) {
        this.entryId = entryId;
        this.name = name;
        this.noteSubject = noteSubject;
        this.noteContent = noteContent;

    }

    public String getName() {return this.name;}
    public String getNoteSubject() {return this.noteSubject;}
    public String getNoteContent() {return this.noteContent;}
    public int getType() {return type;}

    @Override
    public int getEntryId() {
        return entryId;
    }

    @Override
    public String getUnderName() {
        return getNoteSubject();
    }

    @Override
    public Image getImage() throws IOException {
        return helper.getImage("note.png");
    }

    public void setName(String s) {this.name = s;}
    public void setNoteSubject(String s) {this.noteSubject = s;}
    public void setNoteContent(String s) {this.noteContent = s;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecureNoteEntry that = (SecureNoteEntry) o;
        return type == that.type && entryId == that.entryId && Objects.equals(name, that.name) && Objects.equals(noteSubject, that.noteSubject) && Objects.equals(noteContent, that.noteContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, noteSubject, noteContent, type, entryId, helper);
    }

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
