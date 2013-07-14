package com.sweettracker.model.visitors;

import com.sweettracker.model.Entry;

public interface IVisitor {

    void visit(Entry entry);
}
