package icu.cyclone.alex.cisco;

import icu.cyclone.alex.utils.UText;

public class ComparedSection {
    private String origin;
    private String compared;
    private Boolean equal;

    public static ComparedSection getNew() {
        return new ComparedSection();
    }

    public ComparedSection() {
        origin = null;
        compared = null;
        equal = null;
    }

    public boolean hasOrigin() {
        return origin != null;
    }

    public boolean hasCompared() {
        return compared != null;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCompared() {
        return compared;
    }

    public Boolean isEqual() {
        return equal;
    }

    public ComparedSection setOrigin(String origin) {
        this.origin = origin;
        if (compared != null) {
            equal = origin.equals(compared);
        }
        return this;
    }

    public ComparedSection setCompared(String compared) {
        this.compared = compared;
        if (origin != null) {
            equal = compared.equals(origin);
        }
        return this;
    }

    @Override
    public String toString() {
        if (equal) {
            return origin;
        }
        if (origin.equals("")) {
            return UText.stringRepeat(" ", 5) + compared + System.lineSeparator() +
                    "! SECTION NOT FOUND IN ORIGIN !";
        } else if (compared.equals("")) {
            return origin + System.lineSeparator() +
                    "! SECTION NOT FOUND IN COMPARED !";
        }
        return origin + System.lineSeparator() +
                "! SECTION CHANGED ! " + compared;
    }
}
