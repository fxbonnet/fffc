package octo.file.metadata;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MetadataParserTest {

  private MetadataParser sut = new MetadataParser();

  @Test
  public void shouldParseGoodMetadataFile() throws Exception {
    String file = "/octo/file/meta1";
    List<Metadata> metadata = sut.parse(file);
    assertThat(metadata.get(0), is(new Metadata("Birth date", 10, Datatype.DATE)));
    assertThat(metadata.get(1), is(new Metadata("First name", 15, Datatype.STRING)));
    assertThat(metadata.get(2), is(new Metadata("Last name", 15, Datatype.STRING)));
    assertThat(metadata.get(3), is(new Metadata("Weight", 5, Datatype.NUMERIC)));
  }

  @Test(expected = Exception.class)
  public void shouldNotParseMetadataFileWithExtraColumn() throws Exception {
    String file = "/octo/file/meta1_extra_column";
    sut.parse(file);
  }

  @Test(expected = Exception.class)
  public void shouldNotParseMetadataFileWithNegativeLength() throws Exception {
    String file = "/octo/file/meta1_negative_length";
    sut.parse(file);
  }

  @Test(expected = Exception.class)
  public void shouldNotParseMetadataFileWithInvalidLength() throws Exception {
    String file = "/octo/file/meta1_invalid_length";
    sut.parse(file);
  }

  @Test(expected = Exception.class)
  public void shouldNotParseMetadataFileWithInvalidDatatype() throws Exception {
    String file = "/octo/file/meta1_invalid_data_type";
    sut.parse(file);
  }
}
