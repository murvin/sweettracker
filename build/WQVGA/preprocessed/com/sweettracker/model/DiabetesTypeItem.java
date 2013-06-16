package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DiabetesTypeItem implements ISerializable {
    
    private int type;
    private float beforeMealMin, beforeMealMax;
    private float afterMealMin, afterMealMax;
    
    public static DiabetesTypeItem getDefault(int type) {
        DiabetesTypeItem item = new DiabetesTypeItem();
        switch (type) {
            case Constants.DIABETES_TYPE_NONE: {
                item.setBeforeMealMin(4.0f);
                item.setBeforeMealMax(5.9f);
                item.setAfterMealMin(4.0f);
                item.setAfterMealMax(7.8f);
                break;
            }
            case Constants.DIABETES_TYPE_ONE: {
                item.setBeforeMealMin(4.0f);
                item.setBeforeMealMax(7.0f);
                item.setAfterMealMin(4.0f);
                item.setAfterMealMax(8.5f);
                break;
            }
            case Constants.DIABETES_TYPE_TWO: {
                item.setBeforeMealMin(4.0f);
                item.setBeforeMealMax(7.0f);
                item.setAfterMealMin(4.0f);
                item.setAfterMealMax(9.0f);
                break;
            }
        }
        item.setType(type);
        return item;
    }
    
    public DiabetesTypeItem() {
    }
    
    public DiabetesTypeItem(int type, float beforeMealMin, float beforeMealMax, float afterMealMin, float afterMealMax) {
        this.type = type;
        this.beforeMealMin = beforeMealMin;
        this.beforeMealMax = beforeMealMax;
        this.afterMealMin = afterMealMin;
        this.afterMealMax = afterMealMax;
    }
    
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }
    
    public void setDiabetesType(int diabetes_type) {
        if (diabetes_type != Constants.DIABETES_TYPE_NONE && diabetes_type != Constants.DIABETES_TYPE_ONE
                && diabetes_type != Constants.DIABETES_TYPE_TWO) {
            throw new IllegalArgumentException();
        }
        
        this.type = diabetes_type;
    }

    /**
     * @return the beforeMealMin
     */
    public float getBeforeMealMin() {
        return beforeMealMin;
    }

    /**
     * @param beforeMealMin the beforeMealMin to set
     */
    public void setBeforeMealMin(float beforeMealMin) {
        this.beforeMealMin = beforeMealMin;
    }

    /**
     * @return the beforeMealMax
     */
    public float getBeforeMealMax() {
        return beforeMealMax;
    }

    /**
     * @param beforeMealMax the beforeMealMax to set
     */
    public void setBeforeMealMax(float beforeMealMax) {
        this.beforeMealMax = beforeMealMax;
    }

    /**
     * @return the afterMealMin
     */
    public float getAfterMealMin() {
        return afterMealMin;
    }

    /**
     * @param afterMealMin the afterMealMin to set
     */
    public void setAfterMealMin(float afterMealMin) {
        this.afterMealMin = afterMealMin;
    }

    /**
     * @return the afterMealMax
     */
    public float getAfterMealMax() {
        return afterMealMax;
    }

    /**
     * @param afterMealMax the afterMealMax to set
     */
    public void setAfterMealMax(float afterMealMax) {
        this.afterMealMax = afterMealMax;
    }
    
    public void serialize(DataOutputStream dis) throws IOException {
        dis.writeInt(type);
        dis.writeFloat(afterMealMin);
        dis.writeFloat(afterMealMax);
        dis.writeFloat(beforeMealMin);
        dis.writeFloat(beforeMealMax);
    }
    
    public void deserialize(DataInputStream dos) throws IOException {
        type = dos.readInt();
        afterMealMin = dos.readFloat();
        afterMealMax = dos.readFloat();
        beforeMealMin = dos.readFloat();
        beforeMealMax = dos.readFloat();
    }
}
