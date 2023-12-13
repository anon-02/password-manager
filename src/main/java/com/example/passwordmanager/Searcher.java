package com.example.passwordmanager;

import java.util.ArrayList;
import java.util.List;

public class Searcher {

    public static ArrayList<DisplayableEntry> search(List<DisplayableEntry> currentPasswordEntries, String searchTerm) {
        ArrayList<DisplayableEntry> matchingEntries = new ArrayList<>();
        for (DisplayableEntry entry : currentPasswordEntries) {
            System.out.println(entry.getSearchTerm());
            if (entry.getSearchTerm().contains(searchTerm.toLowerCase())) {
                matchingEntries.add(entry);
            }
        }
        return matchingEntries;
    }
}