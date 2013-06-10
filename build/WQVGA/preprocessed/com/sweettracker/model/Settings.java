package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Settings implements ISerializable {

    private int glucose_unit;
    private float targetLevel;
    private Profile profile;
    private int currentLocale;
    private int currentTheme;
    private boolean hasShownTOSOnStartUp;

    public void initDefault() {
        profile = new Profile();
        profile.setName("Your name");
        profile.setPassword(1234);

        currentLocale = 3;
        currentTheme = 0;
        targetLevel = 5.5f;
        glucose_unit =  Constants.UNIT_MMOL;
    }

    public void serialize(DataOutputStream dis) throws IOException {

        dis.writeInt(this.glucose_unit);
        dis.writeFloat(this.targetLevel);

        dis.writeBoolean(profile != null);
        if (profile != null) {
            profile.serialize(dis);
        }

        dis.writeInt(currentLocale);
        dis.writeInt(currentTheme);
        dis.writeBoolean(hasShownTOSOnStartUp);
    }

    public void deserialize(DataInputStream dos) throws IOException {
        glucose_unit = dos.readInt();
        targetLevel = dos.readFloat();

        if (dos.readBoolean()) {
            profile = new Profile();
            profile.deserialize(dos);
        }

        currentLocale = dos.readInt();
        currentTheme = dos.readInt();
        hasShownTOSOnStartUp = dos.readBoolean();
    }

    public int getGlucoseUnit() {
        return glucose_unit;
    }

    public float getTargetLevel() {
        return this.targetLevel;
    }

    public void setTargetLevel(float targetLevel) {
        this.targetLevel = targetLevel;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setGlucoseUnit(int glucoseUnit) {
        if (glucoseUnit != Constants.UNIT_MG && glucoseUnit != Constants.UNIT_MMOL) {
            throw new IllegalArgumentException();
        }

        this.glucose_unit = glucoseUnit;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public boolean isHasShownTOSOnStartUp() {
        return hasShownTOSOnStartUp;
    }

    public void setHasShownTOSOnStartUp(boolean hasShownTOSOnStartUp) {
        this.hasShownTOSOnStartUp = hasShownTOSOnStartUp;
    }
}
