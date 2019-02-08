package com.assignment.fffc.validators;

import org.springframework.stereotype.Component;

@Component
public class Validator {

    public static int getSize(String metadatum) {

        return Integer.parseInt(metadatum);
    }
}
