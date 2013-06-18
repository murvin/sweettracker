package com.sweettracker.model.visitors;

import com.sweettracker.model.Date;
import com.sweettracker.model.Entry;

public class EntryPrevDateVisitor implements IVisitor {

    private Date date;
    private Entry tempEntry;

    public EntryPrevDateVisitor(Date date) {
        this.date = date;
    }

    public void visit(Entry entry) {
        if (entry.getDate().compareTo(date) == -1) {
            if (tempEntry == null) {
                tempEntry = entry;
            } else {
                if (entry.getDate().compareTo(tempEntry.getDate()) == 1) {
                    tempEntry = entry;
                }
            }
        }
    }

    public Entry getPrevDateEntries() {
        return tempEntry;
    }
}
