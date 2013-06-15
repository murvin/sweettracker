package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class User implements ISerializable {

    private Entries entries;

    public Entries getEntries() {
        return this.entries;
    }

    public void setEntries(Entries cycle) {
        this.entries = cycle;
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeBoolean(entries != null);
        if (entries != null) {
            entries.serialize(dos);
        }
    }

    public void deserialize(DataInputStream dis) throws IOException {
        if (dis.readBoolean()) {
            entries = new Entries();
            entries.deserialize(dis);
        }
    }
}
