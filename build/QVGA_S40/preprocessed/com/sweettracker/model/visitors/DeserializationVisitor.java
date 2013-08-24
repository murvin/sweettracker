package com.sweettracker.model.visitors;

import com.sweettracker.model.Entry;
import java.io.DataInputStream;
import java.io.IOException;

public class DeserializationVisitor implements IVisitor {

    private DataInputStream dis;

    public DeserializationVisitor(DataInputStream dis) {
        this.dis = dis;
    }

    public void visit(Entry entry) {
        try {
            entry.deserialize(dis);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
