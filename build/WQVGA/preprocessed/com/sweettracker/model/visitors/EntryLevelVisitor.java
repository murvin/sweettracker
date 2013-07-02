package com.sweettracker.model.visitors;

import com.sweettracker.model.Entry;
import java.util.Vector;

public class EntryLevelVisitor implements IVisitor {

    private Vector entries;
    private int year, month;
    private int levelRange;

    public EntryLevelVisitor(int year, int month, int levelRange) {
        this.year = year;
        this.month = month;
        setLevelRange(levelRange);
    }

    public void setLevelRange(int levelRange) {
        this.levelRange = levelRange;
        this.entries = new Vector();
    }

    public void visit(Entry entry) {
        if (entry.getDate().getYear() == this.year
                && entry.getDate().getMonth() == this.month
                && entry.getLevelRange() == this.levelRange) {
            this.entries.addElement(entry);
        }
    }

    public int[] getEntryDates() {
        int[] entryDays = new int[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            Entry e = (Entry) entries.elementAt(i);
            entryDays[i] = e.getDate().getDay();
        }
        return entryDays;
    }
}
