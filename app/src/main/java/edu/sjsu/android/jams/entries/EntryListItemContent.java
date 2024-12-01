package edu.sjsu.android.jams.entries;

/**
 * A list of entries, consisting of title and content.
 */
public class EntryListItemContent {
    private String title;
    private String content;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryListItemContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * Returns the title of the journal entry.
     * @return the title of the journal entry
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the content of the journal entry.
     * @return the content of the journal entry
     */
    public String getContent() {
        return content;
    }
}