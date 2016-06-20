/*  Criado por Alessandra, Alexandre, Lucas, Tiago e Vinicius para o Trabalho Prático 1 da disciplina
 * Engenharia de Software II.
 */
package net.sf.jabref.importer.fileformat;

import net.sf.jabref.importer.ImportFormatReader;
import net.sf.jabref.importer.OutputPrinter;
import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.BibtexEntryTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This importer exists only to enable `--importToOpen someEntry.bib`
 *
 * It is NOT intended to import a bib file. This is done via the option action, which treats the metadata fields
 * The metadata is not required to be read here, as this class is NOT called at --import
 */
public class CsvContentImporter extends ImportFormat {

    /**
     * @return true as we have no effective way to decide whether a file is in bibtex format or not. See
     *         https://github.com/JabRef/jabref/pull/379#issuecomment-158685726 for more details.
     */
    @Override
    public boolean isRecognizedFormat(InputStream in) throws IOException {
        return true;
    }

    /**
     * Parses the given input stream.
     * Only plain bibtex entries are returned.
     * That especially means that metadata is ignored.
     *
     * @param in the inputStream to read from
     * @param status the OutputPrinter to put status to
     * @return a list of BibTeX entries contained in the given inputStream
     */
    @Override
    public List<BibEntry> importEntries(InputStream in, OutputPrinter status) throws IOException {
        List<BibEntry> elements = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(ImportFormatReader.getReaderDefaultEncoding(in))) {
            String str;
            while ((str = bf.readLine()) != null) {
                if (!str.trim().isEmpty()) {
                    String[] fields = str.split(",");
                    BibEntry entry = new BibEntry();
                    int i = 1;
                    entry.setType(BibtexEntryTypes.BOOK);
                    entry.setField("year", fields[0]);
                    entry.setField("author", fields[1]);
                    entry.setField("title", fields[2]);
                    entry.setField("publisher", fields[3]);
                    elements.add(entry);
                    str = bf.readLine();
                }
            }
        }
        return elements;
    }

    @Override
    public String getFormatName() {
        return "CSV";
    }

    @Override
    public String getExtensions() {
        return "csv";
    }
}
