/*  Copyright (C) 2003-2015 JabRef contributors.
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/
package net.sf.jabref.model.entry;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import net.sf.jabref.model.database.BibDatabase;

import com.google.common.base.Strings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;

public class BibEntry {
    private static final Log LOGGER = LogFactory.getLog(BibEntry.class);

    public static final String TYPE_HEADER = "entrytype";
    public static final String KEY_FIELD = "bibtexkey";
    protected static final String ID_FIELD = "id";
    public static final String DEFAULT_TYPE = "misc";

    private String id;
    private String type;
    private Map<String, String> fields = new HashMap<>();

    private final VetoableChangeSupport changeSupport = new VetoableChangeSupport(this);

    // Search and grouping status is stored in boolean fields for quick reference:
    private boolean searchHit;
    private boolean groupHit;

    private String parsedSerialization;

    /*
    * marks if the complete serialization, which was read from file, should be used.
    * Is set to false, if parts of the entry change
     */
    private boolean changed;

    public BibEntry() {
        this(IdGenerator.next());
    }

    public BibEntry(String id) {
        this(id, DEFAULT_TYPE);
    }

    public BibEntry(String id, String type) {
        Objects.requireNonNull(id, "Every BibEntry must have an ID");

        this.id = id;
        changed = true;
        setType(type);
    }

    /**
     * Returns an set containing the names of all fields that are
     * set for this particular entry.
     *
     * @return a set of existing field names
     */
    public Set<String> getFieldNames() {
        return new TreeSet<>(fields.keySet());
    }

    /**
     * Returns this entry's type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets this entry's type.
     */
    public void setType(String type) {
        String newType;
        if ((type == null) || type.isEmpty()) {
            newType = DEFAULT_TYPE;
        } else {
            newType = type;
        }

        String oldType = this.type;

        try {
            // We set the type before throwing the changeEvent, to enable
            // the change listener to access the new value if the change
            // sets off a change in database sorting etc.
            this.type = newType.toLowerCase(Locale.ENGLISH);
            changed = true;
            firePropertyChangedEvent(TYPE_HEADER, oldType, newType);
        } catch (PropertyVetoException pve) {
            LOGGER.warn(pve);
        }
    }

    /**
     * Sets this entry's type.
     */
    public void setType(EntryType type) {
        this.setType(type.getName());
    }

    /**
     * Sets this entry's ID, provided the database containing it
     * doesn't veto the change.
     */
    public void setId(String id) {
        Objects.requireNonNull(id, "Every BibEntry must have an ID");

        try {
            firePropertyChangedEvent(BibEntry.ID_FIELD, this.id, id);
        } catch (PropertyVetoException pv) {
            throw new IllegalStateException("Couldn't change ID: " + pv);
        }

        this.id = id;
        changed = true;
    }

    /**
     * Returns this entry's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the contents of the given field, or null if it is not set.
     */
    public String getField(String name) {
        return fields.get(toLowerCase(name));
    }

    /**
     * Returns the contents of the given field as an Optional.
     */
    public Optional<String> getFieldOptional(String name) {
        return Optional.ofNullable(fields.get(toLowerCase(name)));
    }

    /**
     * Returns true if the entry has the given field, or false if it is not set.
     */
    public boolean hasField(String name) {
        return fields.containsKey(toLowerCase(name));
    }

    private String toLowerCase(String fieldName) {
        Objects.requireNonNull(fieldName, "field name must not be null");

        return fieldName.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Returns the contents of the given field, its alias or null if both are
     * not set.
     * <p>
     * The following aliases are considered (old bibtex <-> new biblatex) based
     * on the BibLatex documentation, chapter 2.2.5:
     * address      <-> location
     * annote           <-> annotation
     * archiveprefix    <-> eprinttype
     * journal      <-> journaltitle
     * key              <-> sortkey
     * pdf          <-> file
     * primaryclass     <-> eprintclass
     * school           <-> institution
     * These work bidirectional.
     * <p>
     * Special attention is paid to dates: (see the BibLatex documentation,
     * chapter 2.3.8)
     * The fields 'year' and 'month' are used if the 'date'
     * field is empty. Conversely, getFieldOrAlias("year") also tries to
     * extract the year from the 'date' field (analogously for 'month').
     */
    public String getFieldOrAlias(String name) {
        String fieldValue = getField(toLowerCase(name));

        if (!Strings.isNullOrEmpty(fieldValue)) {
            return fieldValue;
        }

        // No value of this field found, so look at the alias
        String aliasForField = EntryConverter.FIELD_ALIASES.get(name);

        if (aliasForField != null) {
            return getField(aliasForField);
        }

        // Finally, handle dates
        if ("date".equals(name)) {
            String year = getField("year");
            MonthUtil.Month month = MonthUtil.getMonth(getField("month"));
            if (year != null) {
                if (month.isValid()) {
                    return year + '-' + month.twoDigitNumber;
                } else {
                    return year;
                }
            }
        }
        if ("year".equals(name) || "month".equals(name)) {
            String date = getField("date");
            if (date == null) {
                return null;
            }

            // Create date format matching dates with year and month
            DateFormat df = new DateFormat() {

                static final String FORMAT1 = "yyyy-MM-dd";
                static final String FORMAT2 = "yyyy-MM";
                final SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT1);
                final SimpleDateFormat sdf2 = new SimpleDateFormat(FORMAT2);


                @Override
                public StringBuffer format(Date dDate, StringBuffer toAppendTo, FieldPosition fieldPosition) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Date parse(String source, ParsePosition pos) {
                    if ((source.length() - pos.getIndex()) == FORMAT1.length()) {
                        return sdf1.parse(source, pos);
                    }
                    return sdf2.parse(source, pos);
                }
            };

            try {
                Date parsedDate = df.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsedDate);
                if ("year".equals(name)) {
                    return Integer.toString(calendar.get(Calendar.YEAR));
                }
                if ("month".equals(name)) {
                    return Integer.toString(calendar.get(Calendar.MONTH) + 1); // Shift by 1 since in this calendar Jan = 0
                }
            } catch (ParseException e) {
                // So not a date with year and month, try just to parse years
                df = new SimpleDateFormat("yyyy");

                try {
                    Date parsedDate = df.parse(date);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(parsedDate);
                    if ("year".equals(name)) {
                        return Integer.toString(calendar.get(Calendar.YEAR));
                    }
                } catch (ParseException e2) {
                    LOGGER.warn("Could not parse entry " + name, e2);
                    return null; // Date field not in valid format
                }
            }
        }
        return null;
    }

    /**
     * Returns the bibtex key, or null if it is not set.
     */
    public String getCiteKey() {
        return fields.get(KEY_FIELD);
    }

    public void setCiteKey(String newCiteKey) {
        setField(KEY_FIELD, newCiteKey);
    }

    public boolean hasCiteKey() {
        return !Strings.isNullOrEmpty(getCiteKey());
    }

    /**
     * Sets a number of fields simultaneously. The given HashMap contains field
     * names as keys, each mapped to the value to set.
     */
    public void setField(Map<String, String> fields) {
        Objects.requireNonNull(fields, "fields must not be null");

        fields.forEach((field, value) -> setField(field, value));
    }

    /**
     * Trecho de codigo A1: responsavel por validar o campo ano.
     */

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public static String verificaAno(String ano) {
        Calendar cal = Calendar.getInstance();
        int anoInicial = 1970;
        int anoAtual = cal.get(Calendar.YEAR);
        int valor = 0;
        try {
            valor = Integer.parseInt(ano);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O caractere digitado no campo\nYear não é um número",
                    "Erro - caractere inválido", 0);
        }
        String str = null;
        boolean flag = true;
        //checa se o ano eh valido
        while (flag && ((valor < anoInicial) || (valor > anoAtual))) {
            str = JOptionPane.showInputDialog(null, "Ocorreu ao tentar inserir Year.\nDigite novamente:\n", "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println(str);

            if ((str == null) || str.equals("") || !(isNumeric(str))) {
                flag = false;
                valor = -1;
            } else {
                valor = Integer.parseInt(str);
            }

        }
        if (!flag) {
            valor = 2016;
        }

        ano = Integer.toString(valor);

        return ano;
    }

    /**
     * Fim de A1.
     */


    /**
     * Trecho de codigo A2: responsavel por validar o campo bibexkey.
     */

    public String verificaBibtexkey(String bibtexkey, boolean flag) {
        if (Character.isLetter(bibtexkey.charAt(0)) && (bibtexkey.length() > 1)) {
            return bibtexkey;
        }
        JOptionPane.showMessageDialog(null,
                "Ocorreu ao tentar inserir Bibtexkey. \nUma chave foi gerada automaticamente. \n");
        if (flag) {
            return "Art".concat(this.getId());
        }
        return "Book".concat(this.getId());
    }

    /**
     * Fim de A2.
     */

    /**
     * Trecho de codico C1. Referente às modificações escolhidas pelo grupo.
     */
    public String verificaLetraMinus(String campo, String nomeCampo) {
        if (Character.isLetter(campo.charAt(0)) && Character.isLowerCase(campo.charAt(0))) {
            int flag = JOptionPane.showConfirmDialog(null, "É interessante que o campo " + nomeCampo
                    + " comece com letra maiúscula.\nPodemos fazer isso para você ?\n", "Primeiro Caractere Minúsculo",
                    0);
            if (flag == JOptionPane.YES_OPTION) {
                campo = campo.replaceFirst(Character.toString(campo.charAt(0)),
                        Character.toString(Character.toUpperCase(campo.charAt(0))));
            }
        }
        return campo;
    }

    /**
     * Fim de C1.
     */

    /**
     * Set a field, and notify listeners about the change.
     *
     * @param name  The field to set.
     * @param value The value to set.
     */
    public void setField(String name, String value) {
        Objects.requireNonNull(name, "field name must not be null");
        Objects.requireNonNull(value, "field value must not be null");

        if (value.isEmpty()) {
            clearField(name);
            return;
        }

        String fieldName = toLowerCase(name);

        if (BibEntry.ID_FIELD.equals(fieldName)) {
            throw new IllegalArgumentException("The field name '" + name + "' is reserved");
        }

        //Verifica se o ano e o bibtexkey eh valido para entradas do tipo "Article" ou "Book"
        if (type.equals("article") || type.equals("book")) {

            if (name.equals("year")) {
                System.out.println(value);
                value = verificaAno(value);
            }

            if (type.equals("article")) {
                if (name.equals("bibtexkey")) {
                    value = verificaBibtexkey(value, true);
                }
            }

            if (type.equals("book")) {
                if (name.equals("bibtexkey")) {
                    value = verificaBibtexkey(value, false);
                }
            }
        }

        //INICIO
        // para todos os campos abaixo, caso tenha uma letra no 1a pos, ela sera colocada como maiusculo
        if (type.equals("article") || type.equals("book")) {

            if (name.equals("title")) {
                value = verificaLetraMinus(value, "Title");
            }
            if (name.equals("publisher")) {
                value = verificaLetraMinus(value, "Publisher");
            }
            if (name.equals("author")) {
                value = verificaLetraMinus(value, "Author");
            }
            if (name.equals("editor")) {
                value = verificaLetraMinus(value, "Editor");
            }
            if (name.equals("journal")) {
                value = verificaLetraMinus(value, "Journal");
            }
            System.out.println(name + ' ' + value);
        }

        //FIM

        changed = true;

        String oldValue = fields.get(fieldName);
        try {
            // We set the field before throwing the changeEvent, to enable
            // the change listener to access the new value if the change
            // sets off a change in database sorting etc.
            fields.put(fieldName, value);
            firePropertyChangedEvent(fieldName, oldValue, value);
        } catch (PropertyVetoException pve) {
            // Since we have already made the change, we must undo it since
            // the change was rejected:
            fields.put(fieldName, oldValue);
            throw new IllegalArgumentException("Change rejected: " + pve);
        }
    }

    /**
     * Remove the mapping for the field name, and notify listeners about
     * the change.
     *
     * @param name The field to clear.
     */
    public void clearField(String name) {
        String fieldName = toLowerCase(name);

        changed = true;

        if (BibEntry.ID_FIELD.equals(fieldName)) {
            throw new IllegalArgumentException("The field name '" + name + "' is reserved");
        }
        Object oldValue = fields.get(fieldName);
        fields.remove(fieldName);
        try {
            firePropertyChangedEvent(fieldName, oldValue, null);
        } catch (PropertyVetoException pve) {
            throw new IllegalArgumentException("Change rejected: " + pve);
        }

    }

    /**
     * Determines whether this entry has all the given fields present. If a non-null
     * database argument is given, this method will try to look up missing fields in
     * entries linked by the "crossref" field, if any.
     *
     * @param allFields An array of field names to be checked.
     * @param database  The database in which to look up crossref'd entries, if any. This
     *                  argument can be null, meaning that no attempt will be made to follow crossrefs.
     * @return true if all fields are set or could be resolved, false otherwise.
     */
    public boolean allFieldsPresent(List<String> allFields, BibDatabase database) {
        final String orSeparator = "/";

        for (String field : allFields) {
            String fieldName = toLowerCase(field);
            // OR fields
            if (fieldName.contains(orSeparator)) {
                String[] altFields = field.split(orSeparator);

                if (!atLeastOnePresent(altFields, database)) {
                    return false;
                }
            } else {
                if (BibDatabase.getResolvedField(fieldName, this, database) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean atLeastOnePresent(String[] fieldsToCheck, BibDatabase database) {
        for (String field : fieldsToCheck) {
            String fieldName = toLowerCase(field);

            String value = BibDatabase.getResolvedField(fieldName, this, database);
            if ((value != null) && !value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void firePropertyChangedEvent(String fieldName, Object oldValue, Object newValue)
            throws PropertyVetoException {
        changeSupport.fireVetoableChange(new PropertyChangeEvent(this, fieldName, oldValue, newValue));
    }

    /**
     * Adds a VetoableChangeListener, which is notified of field
     * changes. This is useful for an object that needs to update
     * itself each time a field changes.
     */
    public void addPropertyChangeListener(VetoableChangeListener listener) {
        changeSupport.addVetoableChangeListener(listener);
    }

    /**
     * Removes a property listener.
     */
    public void removePropertyChangeListener(VetoableChangeListener listener) {
        changeSupport.removeVetoableChangeListener(listener);
    }

    /**
     * Returns a clone of this entry. Useful for copying.
     */
    @Override
    public Object clone() {
        BibEntry clone = new BibEntry(id, type);
        clone.fields = new HashMap<>(fields);
        return clone;
    }

    /**
     * This returns a canonical BibTeX serialization. Special characters such as "{" or "&" are NOT escaped, but written
     * as is
     * <p>
     * Serializes all fields, even the JabRef internal ones. Does NOT serialize "KEY_FIELD" as field, but as key
     */
    @Override
    public String toString() {
        return CanonicalBibtexEntry.getCanonicalRepresentation(this);
    }

    public boolean isSearchHit() {
        return searchHit;
    }

    public void setSearchHit(boolean searchHit) {
        this.searchHit = searchHit;
    }

    public boolean isGroupHit() {
        return groupHit;
    }

    public void setGroupHit(boolean groupHit) {
        this.groupHit = groupHit;
    }

    /**
     * @param maxCharacters The maximum number of characters (additional
     *                      characters are replaced with "..."). Set to 0 to disable truncation.
     * @return A short textual description of the entry in the format:
     * Author1, Author2: Title (Year)
     */
    public String getAuthorTitleYear(int maxCharacters) {
        String[] s = new String[] {getFieldOptional("author").orElse("N/A"), getFieldOptional("title").orElse("N/A"),
                getFieldOptional("year").orElse("N/A")};

        String text = s[0] + ": \"" + s[1] + "\" (" + s[2] + ')';
        if ((maxCharacters <= 0) || (text.length() <= maxCharacters)) {
            return text;
        }
        return text.substring(0, maxCharacters + 1) + "...";
    }

    /**
     * Will return the publication date of the given bibtex entry conforming to ISO 8601, i.e. either YYYY or YYYY-MM.
     *
     * @return will return the publication date of the entry or null if no year was found.
     */
    public String getPublicationDate() {
        if (!hasField("year")) {
            return null;
        }

        String year = getField("year");

        if (hasField("month")) {
            MonthUtil.Month month = MonthUtil.getMonth(getField("month"));
            if (month.isValid()) {
                return year + "-" + month.twoDigitNumber;
            }
        }
        return year;
    }


    public void setParsedSerialization(String parsedSerialization) {
        changed = false;
        this.parsedSerialization = parsedSerialization;
    }

    public String getParsedSerialization() {
        return parsedSerialization;
    }

    public boolean hasChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void putKeywords(List<String> keywords) {
        Objects.requireNonNull(keywords);
        // Set Keyword Field
        String oldValue = this.getField("keywords");
        String newValue;
        if (keywords.isEmpty()) {
            newValue = null;
        } else {
            newValue = String.join(", ", keywords);
        }
        if (newValue == null) {
            if (oldValue != null) {
                this.clearField("keywords");
            }
            return;
        }
        if ((oldValue == null) || !oldValue.equals(newValue)) {
            this.setField("keywords", newValue);
        }
    }

    /**
     * Check if a keyword already exists (case insensitive), if not: add it
     *
     * @param keyword Keyword to add
     */
    public void addKeyword(String keyword) {
        Objects.requireNonNull(keyword, "keyword must not be empty");

        if (keyword.isEmpty()) {
            return;
        }

        List<String> keywords = this.getSeparatedKeywords();
        Boolean duplicate = false;

        for (String key : keywords) {
            if (keyword.equalsIgnoreCase(key)) {
                duplicate = true;
                break;
            }
        }

        if (!duplicate) {
            keywords.add(keyword);
            this.putKeywords(keywords);
        }
    }

    /**
     * Add multiple keywords to entry
     *
     * @param keywords Keywords to add
     */
    public void addKeywords(List<String> keywords) {
        Objects.requireNonNull(keywords);

        for (String keyword : keywords) {
            this.addKeyword(keyword);
        }
    }

    public List<String> getSeparatedKeywords() {
        return net.sf.jabref.model.entry.EntryUtil.getSeparatedKeywords(this.getField("keywords"));
    }

    public Collection<String> getFieldValues() {
        return fields.values();
    }

    public Map<String, String> getFieldMap() {
        // TODO Auto-generated method stub
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        BibEntry entry = (BibEntry) o;
        return Objects.equals(type, entry.type) && Objects.equals(fields, entry.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, fields);
    }
}
