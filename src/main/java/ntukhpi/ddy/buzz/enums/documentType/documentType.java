package ntukhpi.ddy.buzz.enums.documentType;


public enum documentType {
    full("Повний"),
    children("Дитячій"),
    discount ("Пільговий");


    private final String displayName;

    documentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static documentType getByType(String nameTT) {
        int index = -1;
        documentType[] ttValues = values();
        boolean flFound = false;
        while (!flFound && index<ttValues.length-1) {
            index++;
            if (ttValues[index].getDisplayName().equals(nameTT)) {
                flFound = true;
            }
        }
        documentType tt = null;
        if (!flFound) {
            tt = documentType.values()[0];
        } else {
            tt = documentType.values()[index];
        }
        return tt;
    }

    public static documentType getTypesById(int index) {
        if (index >= documentType.values().length) {
            return documentType.values()[0];
        } else {
            return documentType.values()[index];
        }
    }

    public static String[]  getTypes() {
        documentType[] tt = values();
        String[] ttNames = new String[tt.length];
        for (int i = 0; i < tt.length; i++) {
            ttNames[i] = tt[i].getDisplayName();
        }
        return ttNames;
    }
}
