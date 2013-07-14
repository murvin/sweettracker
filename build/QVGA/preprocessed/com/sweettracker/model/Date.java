package com.sweettracker.model;

import com.uikit.datastructures.IComparable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Date implements ISerializable, IComparable {

    private int day;
    private int month;
    private int year;
    
    private int hour;
    private int mins;
    private int am_pm;

    /**
     * Creates a new date with the given parameters.
     * 
     * @param day           the day (1-31)
     * @param month         the month (1-12)
     * @param year          the year (####)
     */
    public Date(int day, int month, int year, int hour, int mins, int am_pm) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.mins = mins;
        this.am_pm = am_pm;
    }

    public Date() {
    }

    public boolean isGreater(Date anotherDate) {
        boolean isGreater = false;
        if (anotherDate.getYear() > this.getYear()) {
            isGreater = false;
        } else if (anotherDate.getYear() < this.getYear()) {
            isGreater = true;
        } else {
            if (this.getMonth() > anotherDate.getMonth()) {
                isGreater = true;
            } else if (this.getMonth() < anotherDate.getMonth()) {
                isGreater = false;
            } else {
                if (this.getDay() < anotherDate.getDay()) {
                    isGreater = false;
                } else if (this.getDay() > anotherDate.getDay()) {
                    isGreater = true;
                }
            }
        }
        return isGreater;
    }

    /**
     * Used to get the associated day of this date.
     * 
     * @return              the date day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Used to get the associated month of this date.
     * 
     * @return              the date month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Used to get the associated year of this date.
     * 
     * @return              the date year              
     */
    public int getYear() {
        return this.year;
    }
    
    public int getHour(){
        return this.hour;
    }
    
    public int getMins(){
        return this.mins;
    }
    
    public int getAmPm(){
        return this.am_pm;
    }

    public boolean equals(Object obj) {
        return ((obj instanceof Date)
                && (((Date) obj).getDay() == this.day)
                && (((Date) obj).getMonth() == this.month)
                && (((Date) obj).getYear() == this.year));
    }

    public int hashCode() {
        return day ^ month * year;
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("\nDay\t : ").append(day);
        str.append("\nMonth\t : ").append(month);
        str.append("\nYear\t : ").append(year);
        return str.toString();
    }

    public void serialize(DataOutputStream dos) throws IOException {
        dos.writeInt(day);
        dos.writeInt(month);
        dos.writeInt(year);
        dos.writeInt(hour);
        dos.writeInt(mins);
        dos.writeInt(am_pm);
    }

    public void deserialize(DataInputStream dis) throws IOException {
        day = dis.readInt();
        month = dis.readInt();
        year = dis.readInt();
        hour = dis.readInt();
        mins = dis.readInt();
        am_pm = dis.readInt();
    }

    public int compareTo(Object comparable) {
        if (comparable instanceof Date) {
            if (((Date) comparable).equals(this)) {
                return 0;
            } else {
                return this.isGreater(((Date) comparable)) ? 1 : -1;
            }
        }
        return -1;
    }
}
