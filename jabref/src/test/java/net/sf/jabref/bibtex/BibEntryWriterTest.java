package net.sf.jabref.bibtex;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Set;

import net.sf.jabref.Globals;
import net.sf.jabref.JabRefPreferences;
import net.sf.jabref.exporter.LatexFieldFormatter;
import net.sf.jabref.importer.ParserResult;
import net.sf.jabref.importer.fileformat.BibtexParser;
import net.sf.jabref.model.database.BibDatabaseMode;
import net.sf.jabref.model.entry.BibEntry;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class BibEntryWriterTest {

    private BibEntryWriter writer;
    private static JabRefPreferences backup;

    @BeforeClass
    public static void setUp() {
        Globals.prefs = JabRefPreferences.getInstance();
        backup = Globals.prefs;
    }

    @AfterClass
    public static void tearDown() {
        Globals.prefs.overwritePreferences(backup);
    }

    @Before
    public void setUpWriter() {
        writer = new BibEntryWriter(new LatexFieldFormatter(), true);
    }

    /*TESTES PARA O CAMPO YEAR*/
    /*Testes para o campo Year de entrada Article*/
    @Test
    public void testVerifyFieldYearForArticleError() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        //set the field year
        entry.setField("year", "1969");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  year = {1969}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleHit() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "1970"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {1970}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleHit1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2000"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {2000}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleHit2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2016"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {2016}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleError1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "2017"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {2017}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleError2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "19700"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {19700}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleError3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "20102"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {20102}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleError4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "20as"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {20as}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }


    @Test
    public void testVerifyFieldYearForArticleError5() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "200"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {200}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleError6() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "197"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {197}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForArticleError7() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("year", "asdf"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  year = {asdf}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    /*Testes para o campo Year de entrada Book*/
    @Test
    public void testVerifyFieldYearForBookError() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        //set the field year
        entry.setField("year", "1969");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  year = {1969}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookHit() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "1970"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {1970}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookHit1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2000"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {2000}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookHit2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2016"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {2016}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "2017"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {2017}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "19700"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {19700}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "20102"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {20102}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "20as"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {20as}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError5() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "200"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {200}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError6() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "197"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {197}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldYearForBookError7() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("year", "asdf"); //set the field year

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  year = {asdf}," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    /*TESTES PARA O CAMPO BIBTEXKEY*/
    /*Testes para o campo Bibtexkey do item Article*/
    @Test
    public void testVerifyFieldBibtexkeyForArticleError() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "0");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{0," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleError1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "a");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{a," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleError2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "0a");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{0a," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleError3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "12");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{12," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleError4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "123");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{123," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleError5() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "A");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{A," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleHit() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "a10");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{a10," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleHit1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "abc");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{abc," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleHit2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "ABC");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{ABC," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleHit3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "Abc");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{Abc," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleHit4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "abcdefghijklmnopqrstuvwxyz0123456789");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{abcdefghijklmnopqrstuvwxyz0123456789," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForArticleHit5() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("bibtexkey", "A0123456789A");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Article{A0123456789A," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    /*Testes para o campo Bibtexkey do item Book*/
    @Test
    public void testVerifyFieldBibtexkeyForBookError() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "0");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{0," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookError1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "a");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{a," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookError2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "0a");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{0a," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookError3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "12");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{12," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookError4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "123");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{123," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookError5() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "A");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{A," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertNotEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookHit() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "a10");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{a10," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookHit1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "abc");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{abc," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookHit2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "ABC");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{ABC," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookHit3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "Abc");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{Abc," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookHit4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "abcdefghijklmnopqrstuvwxyz0123456789");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{abcdefghijklmnopqrstuvwxyz0123456789," + Globals.NEWLINE + "}"
                + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testVerifyFieldBibtexkeyForBookHit5() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("bibtexkey", "A0123456789A");//set the field bibtexkey

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();
        String expected = Globals.NEWLINE + "@Book{A0123456789A," + Globals.NEWLINE + "}" + Globals.NEWLINE;
        assertEquals(expected, actual);
    }

    @Test
    public void testSerialization() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        //set a required field
        entry.setField("author", "Foo Bar");
        entry.setField("journal", "International Journal of Something");
        //set an optional field
        entry.setField("number", "1");
        entry.setField("note", "some note");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Foo Bar}," + Globals.NEWLINE +
                "  journal = {International Journal of Something}," + Globals.NEWLINE +
                "  number  = {1}," + Globals.NEWLINE +
                "  note    = {some note}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    /*TESTES PARA NOVA FUNCIONALIDADE - ITEM 4*/
    //testes para article
    @Test
    public void testSerialization1() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        //set a required field
        entry.setField("author", "lucas A. Callegari");
        entry.setField("title", "mineração de Dados");
        entry.setField("journal", "agricultura e Ciência");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {lucas A. Callegari}," + Globals.NEWLINE +
                "  title  = {Mineração de Dados}," + Globals.NEWLINE +
                "  journal = {Agricultura e Ciência}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertNotEquals(expected, actual);
    }

    @Test
    public void testSerialization2() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        //set a required field
        entry.setField("author", "alessandra Camargo");
        entry.setField("title", "a Arte do Sucesso");
        entry.setField("journal", "vida");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  author  = {Alessandra Camargo}," + Globals.NEWLINE +
                "  title  = {A Arte do Sucesso}," + Globals.NEWLINE +
                "  journal = {Vida}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertNotEquals(expected, actual);
    }

    //testes para book
    @Test
    public void testSerialization3() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        //set a required field
        entry.setField("title", "sistemas Operacionais");
        entry.setField("publisher", "saraiva");
        entry.setField("author", "andrew S. Tanenbaum");
        entry.setField("editor", "pearson Prentice Hall");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  title  = {Sistemas Operacionais}," + Globals.NEWLINE +
                "  publisher  = {Saraiva}," + Globals.NEWLINE +
                "  author = {Andrew S. Tanenbaum}," + Globals.NEWLINE +
                "  editor = {Pearson Prentice Hall}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertNotEquals(expected, actual);
    }

    @Test
    public void testSerialization4() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        //set a required field
        entry.setField("title", "o Guia do Mochileiro das Galáxias");
        entry.setField("publisher", "saraiva");
        entry.setField("author", "douglas Adams");
        entry.setField("editor", "arqueiro");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  title  = {O Guia do Mochileiro das Galáxias}," + Globals.NEWLINE +
                "  publisher  = {Saraiva}," + Globals.NEWLINE +
                "  author = {Douglas Adams}," + Globals.NEWLINE +
                "  editor = {Arqueiro}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertNotEquals(expected, actual);
    }

    @Test
    public void roundTripTest() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Journal                  = {International Journal of Something}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
    }

    @Test
    public void roundTripWithPrependingNewlines() throws IOException {
        // @formatter:off
        String bibtexEntry = "\r\n@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Journal                  = {International Journal of Something}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
    }

    @Test
    public void roundTripWithModification() throws IOException {
        // @formatter:off
        String bibtexEntry = Globals.NEWLINE + "@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Journal                  = {International Journal of Something}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}," + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());

        // Modify entry
        entry.setField("author", "BlaBla");

        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("BlaBla", entry.getField("author"));

        // write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{test," + Globals.NEWLINE +
                "  author  = {BlaBla}," + Globals.NEWLINE +
                "  journal = {International Journal of Something}," + Globals.NEWLINE +
                "  number  = {1}," + Globals.NEWLINE +
                "  note    = {some note}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    @Test
    public void roundTripWithCamelCasingInTheOriginalEntryAndResultInLowerCase() throws IOException {
        // @formatter:off
        String bibtexEntry = Globals.NEWLINE + "@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Journal                  = {International Journal of Something}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}," + Globals.NEWLINE +
                "  HowPublished             = {asdf}," + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(6, entry.getFieldNames().size());

        // modify entry
        entry.setField("author", "BlaBla");

        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("BlaBla", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{test," + Globals.NEWLINE +
                "  author       = {BlaBla}," + Globals.NEWLINE +
                "  journal      = {International Journal of Something}," + Globals.NEWLINE +
                "  number       = {1}," + Globals.NEWLINE +
                "  note         = {some note}," + Globals.NEWLINE +
                "  howpublished = {asdf}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    @Test
    public void roundTripWithAppendedNewlines() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Journal                  = {International Journal of Something}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}\n\n";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // Only one appending newline is written by the writer, the rest by FileActions. So, these should be removed here.
        assertEquals(bibtexEntry.substring(0, bibtexEntry.length() - 1), actual);
    }

    @Test
    public void multipleWritesWithoutModification() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Journal                  = {International Journal of Something}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        String result = testSingleWrite(bibtexEntry);
        result = testSingleWrite(result);
        result = testSingleWrite(result);

        assertEquals(bibtexEntry, result);
    }

    private String testSingleWrite(String bibtexEntry) throws IOException {
        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
        return actual;
    }

    @Test
    public void monthFieldSpecialSyntax() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Article{test," + Globals.NEWLINE +
                "  Author                   = {Foo Bar}," + Globals.NEWLINE +
                "  Month                    = mar," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(4, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("month"));
        assertEquals("#mar#", entry.getField("month"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
    }

    @Test
    public void addFieldWithLongerLength() throws IOException {
        // @formatter:off
        String bibtexEntry = Globals.NEWLINE + Globals.NEWLINE + "@Article{test," + Globals.NEWLINE +
                "  author =  {BlaBla}," + Globals.NEWLINE +
                "  journal = {International Journal of Something}," + Globals.NEWLINE +
                "  number =  {1}," + Globals.NEWLINE +
                "  note =    {some note}," + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));
        Collection<BibEntry> entries = result.getDatabase().getEntries();
        BibEntry entry = entries.iterator().next();

        // modify entry
        entry.setField("howpublished", "asdf");

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Article{test," + Globals.NEWLINE +
                "  author       = {BlaBla}," + Globals.NEWLINE +
                "  journal      = {International Journal of Something}," + Globals.NEWLINE +
                "  number       = {1}," + Globals.NEWLINE +
                "  note         = {some note}," + Globals.NEWLINE +
                "  howpublished = {asdf}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    @Test
    public void doNotWriteEmptyFields() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("author", "  ");
        entry.setField("note", "some note");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE +
                "  note   = {some note}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    @Test
    public void trimFieldContents() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "article");
        entry.setField("note", "        some note    \t");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Article{," + Globals.NEWLINE + "  note = {some note}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    @Test
    public void testBookSerialization() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        //set a required field
        entry.setField("title", "Nice Book");
        entry.setField("author", "Foo Bar");
        //set an optional field
        entry.setField("number", "1");
        entry.setField("note", "some note");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE +
                "  title  = {Nice Book}," + Globals.NEWLINE +
                "  author = {Foo Bar}," + Globals.NEWLINE +
                "  number = {1}," + Globals.NEWLINE +
                "  note   = {some note}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on

        assertEquals(expected, actual);
    }

    @Test
    public void bookRoundTripTest() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Book{test," + Globals.NEWLINE +
                "  Title                   = {Nice Book}," + Globals.NEWLINE +
                "  Author                  = {Foo Bar}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
    }

    @Test
    public void bookRoundTripWithPrependingNewlines() throws IOException {
        // @formatter:off
        String bibtexEntry = "\r\n@Book{test," + Globals.NEWLINE +
                "  Title                   = {Nice Book}," + Globals.NEWLINE +
                "  Author                  = {Foo Bar}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
    }

    @Test
    public void bookRoundTripWithModification() throws IOException {
        // @formatter:off
        String bibtexEntry = Globals.NEWLINE + "@Book{test," + Globals.NEWLINE +
                "  Title                   = {Nice Book}," + Globals.NEWLINE +
                "  Author                  = {Foo Bar}," + Globals.NEWLINE +
                "  Note                    = {some note}," + Globals.NEWLINE +
                "  Number                  = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());

        // Modify entry
        entry.setField("author", "BlaBla");

        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("BlaBla", entry.getField("author"));

        // write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{test," + Globals.NEWLINE +
                "  title  = {Nice Book}," + Globals.NEWLINE +
                "  author = {BlaBla}," + Globals.NEWLINE +
                "  number = {1}," + Globals.NEWLINE +
                "  note   = {some note}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    //    @Test
    //    public void bookRoundTripWithCamelCasingInTheOriginalEntryAndResultInLowerCase() throws IOException {
//        // @formatter:off
//        String bibtexEntry = Globals.NEWLINE + "@Book{test," + Globals.NEWLINE +
//                "  Title                   = {Nice Book}," + Globals.NEWLINE +
//                "  Author                  = {Foo Bar}," + Globals.NEWLINE +
//                "  Note                     = {some note}," + Globals.NEWLINE +
//                "  Number                   = {1}" + Globals.NEWLINE +
//                "  HowPublished             = {asdf}," + Globals.NEWLINE +
//                "}";
//        // @formatter:on
    //
    //        // read in bibtex string
    //        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));
    //
    //        Collection<BibEntry> entries = result.getDatabase().getEntries();
    //        assertEquals(1, entries.size());
    //
    //        BibEntry entry = entries.iterator().next();
    //        assertEquals("test", entry.getCiteKey());
    //        assertEquals(6, entry.getFieldNames().size());
    //
    //        // modify entry
    //        entry.setField("author", "BlaBla");
    //
    //        Set<String> fields = entry.getFieldNames();
    //        assertTrue(fields.contains("author"));
    //        assertEquals("BlaBla", entry.getField("author"));
    //
    //        //write out bibtex string
    //        StringWriter stringWriter = new StringWriter();
    //
    //        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
    //        String actual = stringWriter.toString();
    //
//        // @formatter:off
//        String expected = Globals.NEWLINE + "@Book{test," + Globals.NEWLINE +
//                "  title       = {Nice Book}," + Globals.NEWLINE +
//                "  author      = {BlaBla}," + Globals.NEWLINE +
//                "  number       = {1}," + Globals.NEWLINE +
//                "  note         = {some note}," + Globals.NEWLINE +
//                "  howpublished = {asdf}," + Globals.NEWLINE +
//                "}" + Globals.NEWLINE;
//        // @formatter:on
    //        assertEquals(expected, actual);
    //    }

    @Test
    public void bookRoundTripWithAppendedNewlines() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Book{test," + Globals.NEWLINE +
                "  Title                   = {Nice Book}," + Globals.NEWLINE +
                "  Author                  = {Foo Bar}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}\n\n";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // Only one appending newline is written by the writer, the rest by FileActions. So, these should be removed here.
        assertEquals(bibtexEntry.substring(0, bibtexEntry.length() - 1), actual);
    }

    @Test
    public void bookMultipleWritesWithoutModification() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Book{test," + Globals.NEWLINE +
                "  Title                   = {Nice Book}," + Globals.NEWLINE +
                "  Author                  = {Foo Bar}," + Globals.NEWLINE +
                "  Note                     = {some note}," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        String result = testSingleWrite(bibtexEntry);
        result = testSingleWrite(result);
        result = testSingleWrite(result);

        assertEquals(bibtexEntry, result);
    }

    private String testBookSingleWrite(String bibtexEntry) throws IOException {
        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(5, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("author"));
        assertEquals("Foo Bar", entry.getField("author"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
        return actual;
    }

    @Test
    public void bookMonthFieldSpecialSyntax() throws IOException {
        // @formatter:off
        String bibtexEntry = "@Book{test," + Globals.NEWLINE +
                "  Title                   = {Nice Book}," + Globals.NEWLINE +
                "  Month                    = mar," + Globals.NEWLINE +
                "  Number                   = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));

        Collection<BibEntry> entries = result.getDatabase().getEntries();
        assertEquals(1, entries.size());

        BibEntry entry = entries.iterator().next();
        assertEquals("test", entry.getCiteKey());
        assertEquals(4, entry.getFieldNames().size());
        Set<String> fields = entry.getFieldNames();
        assertTrue(fields.contains("month"));
        assertEquals("#mar#", entry.getField("month"));

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();
        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        assertEquals(bibtexEntry, actual);
    }

    @Test
    public void addBookFieldWithLongerLength() throws IOException {
        // @formatter:off
        String bibtexEntry = Globals.NEWLINE + Globals.NEWLINE + "@Book{test," + Globals.NEWLINE +
                "  Title        = {Nice Book}," + Globals.NEWLINE +
                "  Author       = {Foo Bar}," + Globals.NEWLINE +
                "  Note         = {some note}," + Globals.NEWLINE +
                "  Number       = {1}" + Globals.NEWLINE +
                "}";
        // @formatter:on

        // read in bibtex string
        ParserResult result = BibtexParser.parse(new StringReader(bibtexEntry));
        Collection<BibEntry> entries = result.getDatabase().getEntries();
        BibEntry entry = entries.iterator().next();

        // modify entry
        entry.setField("howpublished", "asdf");

        //write out bibtex string
        StringWriter stringWriter = new StringWriter();

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);
        String actual = stringWriter.toString();

        // @formatter:off
        String expected = Globals.NEWLINE + "@Book{test," + Globals.NEWLINE +
                "  title        = {Nice Book}," + Globals.NEWLINE +
                "  author       = {Foo Bar}," + Globals.NEWLINE +
                "  number       = {1}," + Globals.NEWLINE +
                "  note         = {some note}," + Globals.NEWLINE +
                "  howpublished = {asdf}," + Globals.NEWLINE +
                "}" + Globals.NEWLINE;
        // @formatter:on
        assertEquals(expected, actual);
    }

    @Test
    public void doNotWriteEmptyBookFields() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("title", "  ");
        entry.setField("note", "some note");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  note  = {some note}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }

    @Test
    public void trimBookFieldContents() throws IOException {
        StringWriter stringWriter = new StringWriter();

        BibEntry entry = new BibEntry("1234", "book");
        entry.setField("note", "        some note    \t");

        writer.write(entry, stringWriter, BibDatabaseMode.BIBTEX);

        String actual = stringWriter.toString();

        String expected = Globals.NEWLINE + "@Book{," + Globals.NEWLINE + "  note = {some note}," + Globals.NEWLINE
                + "}" + Globals.NEWLINE;

        assertEquals(expected, actual);
    }
}

