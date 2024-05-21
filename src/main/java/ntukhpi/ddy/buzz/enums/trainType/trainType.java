package ntukhpi.ddy.buzz.enums.trainType;

import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;

public enum trainType {
    interCityPlus("Високо швидкісні"),
    interCity("Швидкісні"),
    fast("Швидкі"),
    passenger("Пасажирський");


    private final String displayName;

    trainType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static trainType getByType(String nameTT) {
        int index = -1;
        trainType[] ttValues = values();
        boolean flFound = false;
        while (!flFound && index<ttValues.length-1) {
            index++;
            if (ttValues[index].getDisplayName().equals(nameTT)) {
                flFound = true;
            }
        }
        trainType tt = null;
        if (!flFound) {
            tt = trainType.values()[0];
        } else {
            tt = trainType.values()[index];
        }
        return tt;
    }

    public static trainType getTypesById(int index) {
        if (index >= trainType.values().length) {
            return trainType.values()[0];
        } else {
            return trainType.values()[index];
        }
    }

    public static String[]  getTypes() {
        trainType[] tt = values();
        String[] ttNames = new String[tt.length];
        for (int i = 0; i < tt.length; i++) {
            ttNames[i] = tt[i].getDisplayName();
        }
        return ttNames;
    }
}
