package ntukhpi.ddy.buzz.enums.wagonType;

import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;

public enum wagonType {
    sleep("Спальний вагон"),
    coupe("Купений вагон"),
    lux("Люксовий вагон"),
    coupeFirst("Купейний вагон — 1-го класу"),
    plackart("Плакартний вагон"),
    interCityFirst("Сидячий вагон — 1-го класу"),
    interCitySecond("Сидячий вагон — 2-го класу"),
    firstSeated("Відкритий вагон - 1-го класу"),
    second("Відкритий вагон — 2-го класу");


    private final String displayName;

    wagonType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static wagonType getByType(String nameWT) {
        int index = -1;
        wagonType[] wtValues = values();
        boolean flFound = false;
        while (!flFound && index<wtValues.length-1) {
            index++;
            if (wtValues[index].getDisplayName().equals(nameWT)) {
                flFound = true;
            }
        }
        wagonType wt = null;
        if (!flFound) {
            wt = wagonType.values()[0];
        } else {
            wt = wagonType.values()[index];
        }
        return wt;
    }

    public static wagonType getTypeById(int index) {
        if (index >= wagonType.values().length) {
            return wagonType.values()[0];
        } else {
            return wagonType.values()[index];
        }
    }

    public static String[]  getTypes() {
        wagonType[] vr = values();
        String[] wtNames = new String[vr.length];
        for (int i = 0; i < vr.length; i++) {
            wtNames[i] = vr[i].getDisplayName();
        }
        return wtNames;
    }
}
