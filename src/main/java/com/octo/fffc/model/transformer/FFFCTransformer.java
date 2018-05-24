package com.octo.fffc.model.transformer;

import com.octo.fffc.exceptions.MetadataBuilderException;
import com.octo.fffc.exceptions.ReaderException;
import com.octo.fffc.exceptions.TransformationException;
import com.octo.fffc.exceptions.WriterException;
import com.octo.fffc.model.metadata.Metadata;
import com.octo.fffc.reader.FFFCReader;
import com.octo.fffc.reader.Reader;
import com.octo.fffc.writer.FFFCWriter;
import com.octo.fffc.writer.Writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.octo.fffc.model.metadata.MetadataBuilder.build;
import static java.lang.String.format;

/**
 * Transformer implementation for transforming fixed file format to csv format.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class FFFCTransformer implements Transformer {

    private final String inputDataFile;
    private final String outputFilePath;
    private final Function<String, String[]> rowProcessor;
    private final List<Metadata> metadatas;

    public FFFCTransformer(String metaDataFile,
                    String inputDatFile,
                    String outputFilePath) {
        this.inputDataFile = inputDatFile;
        this.outputFilePath = outputFilePath;
        this.metadatas = readMetadata(metaDataFile);
        this.rowProcessor = new RowProcessor(metadatas);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transform() {
        try (Writer writer = new FFFCWriter(outputFilePath);
             Reader reader = new FFFCReader(inputDataFile)) {

            //Write headers to file here.
            writeHeaders(writer);

            //read and transform data file.
            writeData(writer, reader);

        } catch (IOException ex) {
            throw new TransformationException(format("Cannot transform the file %s", inputDataFile), ex);
        }
    }

    private void writeData(Writer writer, Reader reader) throws ReaderException, WriterException {
        String line;
        while ((line = reader.nextLine()) != null) {
            writer.write(rowProcessor.apply(line));
        }
    }

    private void writeHeaders(Writer writer) throws WriterException {
        writer.write(metadatas.stream()
                .map(Metadata::getColumnName)
                .toArray(String[]::new));
    }

    private static List<Metadata> readMetadata(String metaDataFile) throws TransformationException {
        List<Metadata> metadatas = new ArrayList<>();
        try (Reader reader = new FFFCReader(metaDataFile)) {
            String metadataLine;
            Metadata previousMetadata = null;
            while ((metadataLine = reader.nextLine()) != null) {
                Metadata metadata = build(metadataLine, previousMetadata);
                previousMetadata = metadata;
                metadatas.add(metadata);
            }
        } catch (IOException | MetadataBuilderException ex) {
            throw new TransformationException(ex.getMessage(), ex);
        }

        return metadatas;
    }
}
