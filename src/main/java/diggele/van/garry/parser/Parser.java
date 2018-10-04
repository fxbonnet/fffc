package diggele.van.garry.parser;

import java.io.IOException;
import java.nio.file.Path;

public interface Parser<T> {

    T parse(Path aFile) throws IOException;

}
