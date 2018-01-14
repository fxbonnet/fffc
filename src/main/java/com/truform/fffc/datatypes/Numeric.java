package com.truform.fffc.datatypes;

import com.truform.fffc.exceptions.NumericException;

public class Numeric {
    private String numeric;

    public Numeric(String input) throws NumericException {
        String candidate = input.trim();

        if (!candidate.matches("-?[0-9]+(?:\\.[0-9]+)?")) {
            throw new NumericException("Expected positive or negative real number, found: " + candidate);
        }

        numeric = candidate;
    }

    @Override
    public String toString() {
        return numeric;
    }
}
