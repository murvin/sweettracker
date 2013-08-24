package com.sweettracker.model.visitors;
import com.sweettracker.model.Entry;
import java.util.Vector;

public class MonthEntryVisitor implements IVisitor {

    private Vector entries;
    private int month, year;

    public MonthEntryVisitor(int month, int year) {
        this.month = month;
        this.year = year;
        this.entries = new Vector();
    }

    public Vector getEntries() {
        return this.entries;
    }

    public void visit(Entry entry) {
        if (entry.getDate().getYear() == this.year) {
            if (entry.getDate().getMonth() == this.month) {
                this.entries.addElement(entry);
            }
        }
    }
}
