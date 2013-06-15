package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Entry implements ISerializable {

    private Date date;
    private int timeInterval;
    private int units;
    private float glucoseLevel;
    private String note;
    private int levelRange; // normal, critical, high, none
    
    public static final int LEVEL_NONE = 0x000;
    public static final int LEVEL_NORMAL = 0x001;
    public static final int LEVEL_HIGH = 0x002;
    public static final int LEVEL_CRITICAL = 0x003;
    
    
    public static final int TIME_LESS_2_HOURS = 0x013;
    public static final int TIME_BETWEEEN_2_AND_8_HOURS = 0x014;
    public static final int TIME_GREATER_8_HOURS = 0x015;

    public Entry(Date date, int timeInterval, int units, float glocuseLevel, String note) {
        setDate(date);
        setTimeInterval(timeInterval);
        setUnits(units);
        setGlucoseLevel(glucoseLevel);
        setNote(note);
    }

    public boolean equals(Object obj) {
        return ((obj instanceof Entry)
                && (((Entry) obj).getDate() == this.date)
                && (((Entry) obj).getTimeInterval() == this.timeInterval)
                && (((Entry) obj).getUnits() == this.units)
                && (((Entry) obj).getGlucoseLevel() == this.glucoseLevel)
                && (((Entry) obj).getLevelRange() == this.levelRange)
                && (((Entry) obj).getNote().equals(this.note)));
    }

    public int hashCode() {
        return timeInterval ^ (int) glucoseLevel * units;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        if (date != null) {
            str.append("\nDate\t : ").append(date);
        }
        str.append("\nTime Interval\t : ").append(timeInterval);
        str.append("\nUnits\t : ").append(units == Constants.UNIT_MG ? "mg/dL" : "mmol/L");
        str.append("\nLevel\t : ").append(glucoseLevel);
        if (note != null) {
            str.append("\nNote\t : ").append(note);
        }
        return str.toString();
    }

    public final void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public void serialize(DataOutputStream dos) throws IOException {
        date.serialize(dos);
        dos.writeInt(timeInterval);
        dos.writeInt(units);
        dos.writeFloat(glucoseLevel);
        dos.writeUTF(note == null ? "" : note);
        dos.writeInt(this.levelRange);
    }

    public void deserialize(DataInputStream dis) throws IOException {
        this.date = new Date();
        this.date.deserialize(dis);
        this.timeInterval = dis.readInt();
        this.units = dis.readInt();
        this.glucoseLevel = dis.readFloat();
        this.note = dis.readUTF();
        this.levelRange = dis.readInt();
    }

    /**
     * @return the timeInterval
     */
    public int getTimeInterval() {
        return timeInterval;
    }

    /**
     * @param timeInterval the timeInterval to set
     */
    public final void setTimeInterval(int timeInterval) {
        if (timeInterval != TIME_LESS_2_HOURS && timeInterval != TIME_BETWEEEN_2_AND_8_HOURS && timeInterval != TIME_GREATER_8_HOURS) {
            throw new IllegalArgumentException();
        }
        this.timeInterval = timeInterval;
    }

    /**
     * @return the units
     */
    public int getUnits() {
        return units;
    }

    /**
     * @param units the units to set
     */
    public final void setUnits(int units) {
        if (units != Constants.UNIT_MG && units != Constants.UNIT_MMOL) {
            throw new IllegalArgumentException();
        }
        this.units = units;
    }

    /**
     * @return the glucoseLevel
     */
    public float getGlucoseLevel() {
        return glucoseLevel;
    }

    /**
     * @param glucoseLevel the glucoseLevel to set
     */
    public final void setGlucoseLevel(float glucoseLevel) {
        if (glucoseLevel < 0) {
            throw new IllegalArgumentException();
        }
        this.glucoseLevel = glucoseLevel;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public final void setNote(String note) {
        this.note = note;
    }
    
    public void setLevelRange (int levelRange){
        if (levelRange != LEVEL_NONE
            && levelRange != LEVEL_NORMAL
             && levelRange != LEVEL_HIGH
                && levelRange != LEVEL_CRITICAL){
              throw new IllegalArgumentException();
        }
        
        this.levelRange = levelRange;
    }
    
    public int getLevelRange(){
        return this.levelRange;
    }
}
