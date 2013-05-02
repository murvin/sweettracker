package com.sweettracker.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Settings implements ISerializable {

    private int glucose_unit;
    private Profile profile;
    private String currentLocale;
    private int currentTheme;
    private boolean hasShownTOSOnStartUp;

    public void initDefault() {
        profile = new Profile();
        profile.setName("Your name");
        profile.setPassword(1234);

        currentLocale = "en-GB";
        currentTheme = 0;
    }

    public void serialize(DataOutputStream dis) throws IOException {

        dis.write(this.glucose_unit);

        dis.writeBoolean(profile != null);
        if (profile != null) {
            profile.serialize(dis);
        }

        dis.writeUTF(currentLocale);
        dis.write(currentTheme);
        dis.writeBoolean(hasShownTOSOnStartUp);
    }

    public void deserialize(DataInputStream dos) throws IOException {
        glucose_unit = dos.read();

        if (dos.readBoolean()) {
            profile = new Profile();
            profile.deserialize(dos);
        }

        currentLocale = dos.readUTF();
        currentTheme = dos.read();
        hasShownTOSOnStartUp = dos.readBoolean();
    }

    public int getGlucoseUnit() {
        return glucose_unit;
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

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

    public String getCurrentLocale() {
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
