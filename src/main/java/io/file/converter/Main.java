package io.file.converter;

import org.apache.commons.lang3.ArrayUtils;

public class Main {

    public static void main(String[] argv) {
        if (ArrayUtils.isEmpty(argv) || argv.length < 3) {
            throw new IllegalArgumentException("You need to provide the following parameters : " +
                    "{path to metadata file} {path to data source} {path to target source}");
        }

        String pathToHeaderDescriptor = argv[0];
        String pathToDataSource = argv[1];
        String pathToDataTarget = argv[2];
    }
}
