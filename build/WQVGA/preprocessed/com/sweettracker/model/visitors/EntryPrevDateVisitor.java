package com.sweettracker.model.visitors;

import com.sweettracker.model.Date;
import com.sweettracker.model.Entry;

public class EntryPrevDateVisitor implements IVisitor {

    private Date date;
    private Entry tempEntries;

    public EntryPrevDateVisitor(Date date) {
        this.date = date;
    }

    public void visit(Entry entries) {
        if (entries.getDate().compareTo(date) == -1) {
            if (tempEntries == null) {
                tempEntries = entries;
            } else {
                if (entries.getDate().compareTo(tempEntries.getDate()) == 1) {
                    tempEntries = entries;
                }
            }
        }
    }

    public Entry getPrevDateEntries() {
        return tempEntries;
    }
}
