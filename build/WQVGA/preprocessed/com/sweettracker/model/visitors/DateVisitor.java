package com.sweettracker.model.visitors;

import com.sweettracker.model.Entry;
import java.util.Date;

public class DateVisitor implements IVisitor {

    private Entry entry;
    private Date date;

    public DateVisitor(Date date) {
        this.date = date;
    }

    public void visit(Entry entry) {
        if (entry.getDate().compareTo(this.date) == 0) {
            this.entry = entry;
        }
    }

    public Entry getEntries() {
        return this.entry;
    }
}
