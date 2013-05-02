package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface ISerializable {
    
    void serialize(DataOutputStream dis) throws IOException;
    
    void deserialize(DataInputStream dos) throws IOException;
}
