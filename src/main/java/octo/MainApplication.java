package octo;

import octo.model.ColumnMetadata;
import octo.service.FffcService;
import octo.service.FffcServiceImpl;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by anjana on 27/05/18.
 */
public class MainApplication {

    public static final Logger LOG = Logger.getLogger(MainApplication.class);

    public static void main(String args[]) {
        String columnMetadataFilePath = "files/metadata.csv";
        String inputFilePath = "files/input";
        String outputFilePath = "files/output.csv";

        FffcService fffcService = new FffcServiceImpl();
        try {
            List<ColumnMetadata> columnMetadataList = fffcService.getColumnMetadata(columnMetadataFilePath);
            List<String> formattedRows = fffcService.readInputFile(inputFilePath, columnMetadataList);
            fffcService.writeToCsv(outputFilePath, formattedRows);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Problem converting fixed format file to csv !!!\n" + e.getMessage());
        }

    }


}
