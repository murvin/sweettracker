package com.sweettracker.model.visitors;

import com.sweettracker.model.Entry;
import java.io.DataOutputStream;
import java.io.IOException;

public class SerializationVisitor implements IVisitor {

    private DataOutputStream dos;

    public SerializationVisitor(DataOutputStream dos) {
        this.dos = dos;
    }

    public void visit(Entry entry) {
        try {
            entry.serialize(dos);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
