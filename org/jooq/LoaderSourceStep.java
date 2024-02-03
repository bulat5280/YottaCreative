package org.jooq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.stream.Stream;
import org.xml.sax.InputSource;

public interface LoaderSourceStep<R extends Record> {
   LoaderRowsStep<R> loadArrays(Object[]... var1);

   LoaderRowsStep<R> loadArrays(Iterable<? extends Object[]> var1);

   LoaderRowsStep<R> loadArrays(Iterator<? extends Object[]> var1);

   LoaderRowsStep<R> loadArrays(Stream<? extends Object[]> var1);

   LoaderRowsStep<R> loadRecords(Record... var1);

   LoaderRowsStep<R> loadRecords(Iterable<? extends Record> var1);

   LoaderRowsStep<R> loadRecords(Iterator<? extends Record> var1);

   LoaderRowsStep<R> loadRecords(Stream<? extends Record> var1);

   @Support
   LoaderCSVStep<R> loadCSV(File var1) throws FileNotFoundException;

   @Support
   LoaderCSVStep<R> loadCSV(File var1, String var2) throws FileNotFoundException, UnsupportedEncodingException;

   @Support
   LoaderCSVStep<R> loadCSV(File var1, Charset var2) throws FileNotFoundException;

   @Support
   LoaderCSVStep<R> loadCSV(File var1, CharsetDecoder var2) throws FileNotFoundException;

   @Support
   LoaderCSVStep<R> loadCSV(String var1);

   @Support
   LoaderCSVStep<R> loadCSV(InputStream var1);

   @Support
   LoaderCSVStep<R> loadCSV(InputStream var1, String var2) throws UnsupportedEncodingException;

   @Support
   LoaderCSVStep<R> loadCSV(InputStream var1, Charset var2);

   @Support
   LoaderCSVStep<R> loadCSV(InputStream var1, CharsetDecoder var2);

   @Support
   LoaderCSVStep<R> loadCSV(Reader var1);

   @Support
   LoaderXMLStep<R> loadXML(File var1) throws FileNotFoundException;

   @Support
   LoaderXMLStep<R> loadXML(File var1, String var2) throws FileNotFoundException, UnsupportedEncodingException;

   @Support
   LoaderXMLStep<R> loadXML(File var1, Charset var2) throws FileNotFoundException;

   @Support
   LoaderXMLStep<R> loadXML(File var1, CharsetDecoder var2) throws FileNotFoundException;

   @Support
   LoaderXMLStep<R> loadXML(String var1);

   @Support
   LoaderXMLStep<R> loadXML(InputStream var1);

   @Support
   LoaderXMLStep<R> loadXML(InputStream var1, String var2) throws UnsupportedEncodingException;

   @Support
   LoaderXMLStep<R> loadXML(InputStream var1, Charset var2);

   @Support
   LoaderXMLStep<R> loadXML(InputStream var1, CharsetDecoder var2);

   @Support
   LoaderXMLStep<R> loadXML(Reader var1);

   @Support
   LoaderXMLStep<R> loadXML(InputSource var1);

   @Support
   LoaderJSONStep<R> loadJSON(File var1) throws FileNotFoundException;

   @Support
   LoaderJSONStep<R> loadJSON(File var1, String var2) throws FileNotFoundException, UnsupportedEncodingException;

   @Support
   LoaderJSONStep<R> loadJSON(File var1, Charset var2) throws FileNotFoundException;

   @Support
   LoaderJSONStep<R> loadJSON(File var1, CharsetDecoder var2) throws FileNotFoundException;

   @Support
   LoaderJSONStep<R> loadJSON(String var1);

   @Support
   LoaderJSONStep<R> loadJSON(InputStream var1);

   @Support
   LoaderJSONStep<R> loadJSON(InputStream var1, String var2) throws UnsupportedEncodingException;

   @Support
   LoaderJSONStep<R> loadJSON(InputStream var1, Charset var2);

   @Support
   LoaderJSONStep<R> loadJSON(InputStream var1, CharsetDecoder var2);

   @Support
   LoaderJSONStep<R> loadJSON(Reader var1);
}
