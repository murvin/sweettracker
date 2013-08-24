package com.sweettracker.model;

import com.sweettracker.model.visitors.DeserializationVisitor;
import com.sweettracker.model.visitors.EntryPrevDateVisitor;
import com.sweettracker.model.visitors.IVisitor;
import com.sweettracker.model.visitors.SerializationVisitor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.util.Vector;

public class Entries implements ISerializable {

    private Vector entriesList;
    private EntryPrevDateVisitor entryIndexVisitor;

    public Vector getEntries() {
        return this.entriesList;
    }

    public void addEntry(Entry entry) {
        if (this.entriesList == null) {
            this.entriesList = new Vector();
            this.entriesList.addElement(entry);
        } else {
            entryIndexVisitor = new EntryPrevDateVisitor(entry.getDate());
            try {
                accept(entryIndexVisitor);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            Entry prevDateEntries = entryIndexVisitor.getPrevDateEntries();
            int idx;
            if (prevDateEntries == null) {
                idx = 0;
            } else {
                idx = entriesList.indexOf(prevDateEntries) + 1;

            }

            this.entriesList.insertElementAt(entry, idx);
        }

    }

    public void accept(IVisitor visitor) throws IOException {
        if (entriesList != null) {
            for (int i = 0; i < entriesList.size(); i++) {
                Entry e = (Entry) entriesList.elementAt(i);
                visitor.visit(e);
            }
        }
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeBoolean(entriesList != null);
        if (entriesList != null) {
            dos.write(entriesList.size());
            accept(new SerializationVisitor(dos));
        }
    }

    public void deserialize(DataInputStream dis) throws IOException {
        if (dis.readBoolean()) {
            entriesList = new Vector();
            int size = dis.read();
            for (int i = 0; i < size; i++) {
                entriesList.addElement(new Entry());
            }

            accept(new DeserializationVisitor(dis));
        }
    }
}
