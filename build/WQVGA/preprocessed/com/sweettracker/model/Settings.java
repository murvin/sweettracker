package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Settings implements ISerializable {

    private int glucose_unit;
    private float targetLevel;
    private int target_glucose_unit;
    private int diabetes_type;
    private int currentLocale;
    private int currentTheme;
    private boolean hasAcceptedTerms;
    private String code;
    private final String defaultCode = "0000";

    public void initDefault() {
        currentLocale = 3;
        currentTheme = 0;
        targetLevel = 5.5f;
        code = defaultCode;
        diabetes_type = Constants.DIABETES_TYPE_NONE;
        glucose_unit = Constants.UNIT_MMOL;
        target_glucose_unit = Constants.UNIT_MMOL;
    }

    public void serialize(DataOutputStream dis) throws IOException {
        dis.writeInt(diabetes_type);
        dis.writeInt(this.glucose_unit);
        dis.writeFloat(this.targetLevel);
        dis.writeInt(currentLocale);
        dis.writeInt(currentTheme);
        dis.writeBoolean(hasAcceptedTerms);
        dis.writeUTF(code);
        dis.writeInt(target_glucose_unit);
    }

    public void deserialize(DataInputStream dos) throws IOException {
        diabetes_type = dos.readInt();
        glucose_unit = dos.readInt();
        targetLevel = dos.readFloat();
        currentLocale = dos.readInt();
        currentTheme = dos.readInt();
        hasAcceptedTerms = dos.readBoolean();
        code = dos.readUTF();
        target_glucose_unit = dos.readInt();
    }

    public int getGlucoseUnit() {
        return glucose_unit;
    }

    public float getTargetLevel() {
        return this.targetLevel;
    }
    
    public int getTargetGlucoseUnit(){
        return this.target_glucose_unit;
    }
    
    public void setTargetGlucoseUnit(int targetGlucoseUnit){
        if (targetGlucoseUnit != Constants.UNIT_MG && targetGlucoseUnit != Constants.UNIT_MMOL) {
            throw new IllegalArgumentException();
        }
        
        this.target_glucose_unit = targetGlucoseUnit;
    }

    public void setTargetLevel(float targetLevel) {
        this.targetLevel = targetLevel;
    }

    public void setGlucoseUnit(int glucoseUnit) {
        if (glucoseUnit != Constants.UNIT_MG && glucoseUnit != Constants.UNIT_MMOL) {
            throw new IllegalArgumentException();
        }

        this.glucose_unit = glucoseUnit;
    }

    public void setDiabetesType(int diabetes_type) {
        this.diabetes_type = diabetes_type;
    }

    public int getDiabetesTypeItem() {
        return this.diabetes_type;
    }

    public void setCurrentLocale(int currentLocale) {
        this.currentLocale = currentLocale;
    }

    public int getCurrentLocale() {
        return this.currentLocale;
    }

    public void setCurrentTheme(int currentTheme) {
        this.currentTheme = currentTheme;
    }

    public int getCurrentTheme() {
        return this.currentTheme;
    }

    public boolean hasAcceptedTerms() {
        return hasAcceptedTerms;
    }

    public void setHasAcceptedTerms(boolean hasAcceptedTerms) {
        this.hasAcceptedTerms = hasAcceptedTerms;
    }
    
    public void setCode(String code){
        this.code = code;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public boolean hasDefaultCode(){
        return this.code.equals(defaultCode);
    }
}
