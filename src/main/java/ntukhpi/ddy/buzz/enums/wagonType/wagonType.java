package ntukhpi.ddy.buzz.enums.wagonType;

import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;

public enum wagonType {
    sleep("Спальний вагон"),
    coupe("Купе"),
    coupeFirst("Купейний — 1-го класу"),
    plackart("Плакарт"),
    interCityFirst("Сидячий — 1-го класу"),
    interCitySecond("Сидячий — 2-го класу"),
    second("Відкритий — 2-го класу");

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

    public static int getNumberOfSeats(wagonType wagonType){
        int number = 0;
        if(wagonType.getDisplayName().equals("Спальний вагон")){
            number = 18;
        } else if (wagonType.getDisplayName().equals("Купе")) {
            number = 36;
        } else if (wagonType.getDisplayName().equals("Плакарт")) {
            number = 54;
        }
        else if (wagonType.getDisplayName().equals("Відкритий — 2-го класу")) {
            number = 68;
        }
        else if (wagonType.getDisplayName().equals("Купейний — 1-го клас")) {
            number = 45;
        }
        else if (wagonType.getDisplayName().equals("Сидячий — 1-го класу")) {
            number = 56;
        }
        else if (wagonType.getDisplayName().equals("Сидячий — 2-го класу")) {
            number = 80;
        }
        return number;
    }
}
