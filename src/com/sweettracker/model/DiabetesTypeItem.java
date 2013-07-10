package com.sweettracker.model;

public class DiabetesTypeItem  {

    private int type;
    private float beforeMealMin, beforeMealMax;
    private float afterMealMin, afterMealMax;

    public static DiabetesTypeItem getDefault(int type, int unit) {
        DiabetesTypeItem item = new DiabetesTypeItem();
        switch (type) {
            case Constants.DIABETES_TYPE_NONE: {
                if (unit == Constants.UNIT_MMOL) {
                    item.setBeforeMealMin(4.0f);
                    item.setBeforeMealMax(5.9f);
                    item.setAfterMealMin(4.0f);
                    item.setAfterMealMax(7.8f);
                } else {
                    item.setBeforeMealMin(72.07f);
                    item.setBeforeMealMax(106.31f);
                    item.setAfterMealMin(72.07f);
                    item.setAfterMealMax(140.54f);
                }
                break;
            }
            case Constants.DIABETES_TYPE_ONE: {
                if (unit == Constants.UNIT_MMOL) {
                    item.setBeforeMealMin(4.0f);
                    item.setBeforeMealMax(7.0f);
                    item.setAfterMealMin(4.0f);
                    item.setAfterMealMax(8.5f);
                } else {
                    item.setBeforeMealMin(72.07f);
                    item.setBeforeMealMax(126.12f);
                    item.setAfterMealMin(72.07f);
                    item.setAfterMealMax(153.15f);
                }
                break;
            }
            case Constants.DIABETES_TYPE_TWO: {
                if (unit == Constants.UNIT_MMOL) {
                    item.setBeforeMealMin(4.0f);
                    item.setBeforeMealMax(7.0f);
                    item.setAfterMealMin(4.0f);
                    item.setAfterMealMax(9.0f);
                } else {
                    item.setBeforeMealMin(72.07f);
                    item.setBeforeMealMax(126.12f);
                    item.setAfterMealMin(72.07f);
                    item.setAfterMealMax(162.16f);
                }
                break;
            }
        }
        item.setType(type);
        return item;
    }

    public DiabetesTypeItem() {
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
}
