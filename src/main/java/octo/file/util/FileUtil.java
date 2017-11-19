package octo.file.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtil {

  public static String getAbsolutePath(String file) throws URISyntaxException {
    if (file == null || file.matches("\\s*")) {
      return null;
    }
    URL url = FileUtil.class.getResource(file);
    if (url != null) {
      return Paths.get(url.toURI()).toFile().getAbsolutePath();
    } else {
      return Paths.get(file).toFile().getAbsolutePath();
    }
  }

}
