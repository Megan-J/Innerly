package edu.sjsu.android.jams.entries;

import java.util.List;


/**
 * A list of previous entries, consisting of date and entries.
 */
public class EntryListItemDateContent {
    private String date;
    private List<EntryListItemContent> entryList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryListItemDateContent(String date, List<EntryListItemContent> entryList) {
        this.date = date;
        this.entryList = entryList;
    }

    /**
     * Returns the date of the journal entry.
     * @return the date of the journal entry
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns a List containing the title and content of the journal entry.
     * @return a List containing the title and content of the journal entry
     */
    public List<EntryListItemContent> getEntryList() {
        return entryList;
    }
}