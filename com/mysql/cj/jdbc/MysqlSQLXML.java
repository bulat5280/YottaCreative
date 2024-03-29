package com.mysql.cj.jdbc;

import com.mysql.cj.api.exceptions.ExceptionInterceptor;
import com.mysql.cj.api.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.core.Messages;
import com.mysql.cj.core.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.SQLXML;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MysqlSQLXML implements SQLXML {
   private XMLInputFactory inputFactory;
   private XMLOutputFactory outputFactory;
   private String stringRep;
   private ResultSetInternalMethods owningResultSet;
   private int columnIndexOfXml;
   private boolean fromResultSet;
   private boolean isClosed = false;
   private boolean workingWithResult;
   private DOMResult asDOMResult;
   private SAXResult asSAXResult;
   private MysqlSQLXML.SimpleSaxToReader saxToReaderConverter;
   private StringWriter asStringWriter;
   private ByteArrayOutputStream asByteArrayOutputStream;
   private ExceptionInterceptor exceptionInterceptor;

   public MysqlSQLXML(ResultSetInternalMethods owner, int index, ExceptionInterceptor exceptionInterceptor) {
      this.owningResultSet = owner;
      this.columnIndexOfXml = index;
      this.fromResultSet = true;
      this.exceptionInterceptor = exceptionInterceptor;
   }

   protected MysqlSQLXML(ExceptionInterceptor exceptionInterceptor) {
      this.fromResultSet = false;
      this.exceptionInterceptor = exceptionInterceptor;
   }

   public synchronized void free() throws SQLException {
      try {
         this.stringRep = null;
         this.asDOMResult = null;
         this.asSAXResult = null;
         this.inputFactory = null;
         this.outputFactory = null;
         this.owningResultSet = null;
         this.workingWithResult = false;
         this.isClosed = true;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   public synchronized String getString() throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         return this.fromResultSet ? this.owningResultSet.getString(this.columnIndexOfXml) : this.stringRep;
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   private synchronized void checkClosed() throws SQLException {
      if (this.isClosed) {
         throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.0"), this.exceptionInterceptor);
      }
   }

   private synchronized void checkWorkingWithResult() throws SQLException {
      if (this.workingWithResult) {
         throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.1"), "S1009", this.exceptionInterceptor);
      }
   }

   public synchronized void setString(String str) throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         this.stringRep = str;
         this.fromResultSet = false;
      } catch (CJException var3) {
         throw SQLExceptionsMapping.translateException(var3, this.exceptionInterceptor);
      }
   }

   public synchronized boolean isEmpty() throws SQLException {
      this.checkClosed();
      this.checkWorkingWithResult();
      if (this.fromResultSet) {
         return false;
      } else {
         return this.stringRep == null || this.stringRep.length() == 0;
      }
   }

   public synchronized InputStream getBinaryStream() throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         return this.owningResultSet.getBinaryStream(this.columnIndexOfXml);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   public synchronized Reader getCharacterStream() throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         return this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   public <T extends Source> T getSource(Class<T> clazz) throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         InputSource reader;
         if (clazz != null && !clazz.equals(SAXSource.class)) {
            SQLException sqlEx;
            if (clazz.equals(DOMSource.class)) {
               try {
                  DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                  builderFactory.setNamespaceAware(true);
                  DocumentBuilder builder = builderFactory.newDocumentBuilder();
                  InputSource inputSource = null;
                  if (this.fromResultSet) {
                     inputSource = new InputSource(this.owningResultSet.getCharacterStream(this.columnIndexOfXml));
                  } else {
                     inputSource = new InputSource(new StringReader(this.stringRep));
                  }

                  return new DOMSource(builder.parse(inputSource));
               } catch (Throwable var6) {
                  sqlEx = SQLError.createSQLException(var6.getMessage(), "S1009", var6, this.exceptionInterceptor);
                  throw sqlEx;
               }
            } else {
               Object reader;
               if (clazz.equals(StreamSource.class)) {
                  reader = null;
                  if (this.fromResultSet) {
                     reader = this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
                  } else {
                     reader = new StringReader(this.stringRep);
                  }

                  return new StreamSource((Reader)reader);
               } else if (clazz.equals(StAXSource.class)) {
                  try {
                     reader = null;
                     if (this.fromResultSet) {
                        reader = this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
                     } else {
                        reader = new StringReader(this.stringRep);
                     }

                     return new StAXSource(this.inputFactory.createXMLStreamReader((Reader)reader));
                  } catch (XMLStreamException var7) {
                     sqlEx = SQLError.createSQLException(var7.getMessage(), "S1009", var7, this.exceptionInterceptor);
                     throw sqlEx;
                  }
               } else {
                  throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.2", new Object[]{clazz.toString()}), "S1009", this.exceptionInterceptor);
               }
            }
         } else {
            reader = null;
            if (this.fromResultSet) {
               reader = new InputSource(this.owningResultSet.getCharacterStream(this.columnIndexOfXml));
            } else {
               reader = new InputSource(new StringReader(this.stringRep));
            }

            return new SAXSource(reader);
         }
      } catch (CJException var8) {
         throw SQLExceptionsMapping.translateException(var8, this.exceptionInterceptor);
      }
   }

   public synchronized OutputStream setBinaryStream() throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         this.workingWithResult = true;
         return this.setBinaryStreamInternal();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   private synchronized OutputStream setBinaryStreamInternal() throws SQLException {
      this.asByteArrayOutputStream = new ByteArrayOutputStream();
      return this.asByteArrayOutputStream;
   }

   public synchronized Writer setCharacterStream() throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         this.workingWithResult = true;
         return this.setCharacterStreamInternal();
      } catch (CJException var2) {
         throw SQLExceptionsMapping.translateException(var2, this.exceptionInterceptor);
      }
   }

   private synchronized Writer setCharacterStreamInternal() throws SQLException {
      this.asStringWriter = new StringWriter();
      return this.asStringWriter;
   }

   public synchronized <T extends Result> T setResult(Class<T> clazz) throws SQLException {
      try {
         this.checkClosed();
         this.checkWorkingWithResult();
         this.workingWithResult = true;
         this.asDOMResult = null;
         this.asSAXResult = null;
         this.saxToReaderConverter = null;
         this.stringRep = null;
         this.asStringWriter = null;
         this.asByteArrayOutputStream = null;
         if (clazz != null && !clazz.equals(SAXResult.class)) {
            if (clazz.equals(DOMResult.class)) {
               this.asDOMResult = new DOMResult();
               return this.asDOMResult;
            } else if (clazz.equals(StreamResult.class)) {
               return new StreamResult(this.setCharacterStreamInternal());
            } else if (clazz.equals(StAXResult.class)) {
               try {
                  if (this.outputFactory == null) {
                     this.outputFactory = XMLOutputFactory.newInstance();
                  }

                  return new StAXResult(this.outputFactory.createXMLEventWriter(this.setCharacterStreamInternal()));
               } catch (XMLStreamException var5) {
                  SQLException sqlEx = SQLError.createSQLException(var5.getMessage(), "S1009", var5, this.exceptionInterceptor);
                  throw sqlEx;
               }
            } else {
               throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.3", new Object[]{clazz.toString()}), "S1009", this.exceptionInterceptor);
            }
         } else {
            this.saxToReaderConverter = new MysqlSQLXML.SimpleSaxToReader();
            this.asSAXResult = new SAXResult(this.saxToReaderConverter);
            return this.asSAXResult;
         }
      } catch (CJException var6) {
         throw SQLExceptionsMapping.translateException(var6, this.exceptionInterceptor);
      }
   }

   private Reader binaryInputStreamStreamToReader(ByteArrayOutputStream out) {
      try {
         String encoding = "UTF-8";

         try {
            ByteArrayInputStream bIn = new ByteArrayInputStream(out.toByteArray());
            XMLStreamReader reader = this.inputFactory.createXMLStreamReader(bIn);
            boolean var5 = false;

            int eventType;
            while((eventType = reader.next()) != 8) {
               if (eventType == 7) {
                  String possibleEncoding = reader.getEncoding();
                  if (possibleEncoding != null) {
                     encoding = possibleEncoding;
                  }
                  break;
               }
            }
         } catch (Throwable var7) {
         }

         return new StringReader(new String(out.toByteArray(), encoding));
      } catch (UnsupportedEncodingException var8) {
         throw new RuntimeException(var8);
      }
   }

   protected String readerToString(Reader reader) throws SQLException {
      StringBuilder buf = new StringBuilder();
      int charsRead = false;
      char[] charBuf = new char[512];

      int charsRead;
      try {
         while((charsRead = reader.read(charBuf)) != -1) {
            buf.append(charBuf, 0, charsRead);
         }
      } catch (IOException var7) {
         SQLException sqlEx = SQLError.createSQLException(var7.getMessage(), "S1009", var7, this.exceptionInterceptor);
         throw sqlEx;
      }

      return buf.toString();
   }

   protected synchronized Reader serializeAsCharacterStream() throws SQLException {
      this.checkClosed();
      if (this.workingWithResult) {
         if (this.stringRep != null) {
            return new StringReader(this.stringRep);
         }

         if (this.asDOMResult != null) {
            return new StringReader(this.domSourceToString());
         }

         if (this.asStringWriter != null) {
            return new StringReader(this.asStringWriter.toString());
         }

         if (this.asSAXResult != null) {
            return this.saxToReaderConverter.toReader();
         }

         if (this.asByteArrayOutputStream != null) {
            return this.binaryInputStreamStreamToReader(this.asByteArrayOutputStream);
         }
      }

      return this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
   }

   protected String domSourceToString() throws SQLException {
      try {
         DOMSource source = new DOMSource(this.asDOMResult.getNode());
         Transformer identity = TransformerFactory.newInstance().newTransformer();
         StringWriter stringOut = new StringWriter();
         Result result = new StreamResult(stringOut);
         identity.transform(source, result);
         return stringOut.toString();
      } catch (Throwable var5) {
         SQLException sqlEx = SQLError.createSQLException(var5.getMessage(), "S1009", var5, this.exceptionInterceptor);
         throw sqlEx;
      }
   }

   protected synchronized String serializeAsString() throws SQLException {
      this.checkClosed();
      if (this.workingWithResult) {
         if (this.stringRep != null) {
            return this.stringRep;
         }

         if (this.asDOMResult != null) {
            return this.domSourceToString();
         }

         if (this.asStringWriter != null) {
            return this.asStringWriter.toString();
         }

         if (this.asSAXResult != null) {
            return this.readerToString(this.saxToReaderConverter.toReader());
         }

         if (this.asByteArrayOutputStream != null) {
            return this.readerToString(this.binaryInputStreamStreamToReader(this.asByteArrayOutputStream));
         }
      }

      return this.owningResultSet.getString(this.columnIndexOfXml);
   }

   class SimpleSaxToReader extends DefaultHandler {
      StringBuilder buf = new StringBuilder();
      private boolean inCDATA = false;

      public void startDocument() throws SAXException {
         this.buf.append("<?xml version='1.0' encoding='UTF-8'?>");
      }

      public void endDocument() throws SAXException {
      }

      public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
         this.buf.append("<");
         this.buf.append(qName);
         if (attrs != null) {
            for(int i = 0; i < attrs.getLength(); ++i) {
               this.buf.append(" ");
               this.buf.append(attrs.getQName(i)).append("=\"");
               this.escapeCharsForXml(attrs.getValue(i), true);
               this.buf.append("\"");
            }
         }

         this.buf.append(">");
      }

      public void characters(char[] buffer, int offset, int len) throws SAXException {
         if (!this.inCDATA) {
            this.escapeCharsForXml(buffer, offset, len, false);
         } else {
            this.buf.append(buffer, offset, len);
         }

      }

      public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
         this.characters(ch, start, length);
      }

      public void startCDATA() throws SAXException {
         this.buf.append("<![CDATA[");
         this.inCDATA = true;
      }

      public void endCDATA() throws SAXException {
         this.inCDATA = false;
         this.buf.append("]]>");
      }

      public void comment(char[] ch, int start, int length) throws SAXException {
         this.buf.append("<!--");

         for(int i = 0; i < length; ++i) {
            this.buf.append(ch[start + i]);
         }

         this.buf.append("-->");
      }

      Reader toReader() {
         return new StringReader(this.buf.toString());
      }

      private void escapeCharsForXml(String str, boolean isAttributeData) {
         if (str != null) {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
               this.escapeCharsForXml(str.charAt(i), isAttributeData);
            }

         }
      }

      private void escapeCharsForXml(char[] buffer, int offset, int len, boolean isAttributeData) {
         if (buffer != null) {
            for(int i = 0; i < len; ++i) {
               this.escapeCharsForXml(buffer[offset + i], isAttributeData);
            }

         }
      }

      private void escapeCharsForXml(char c, boolean isAttributeData) {
         switch(c) {
         case '\r':
            this.buf.append("&#xD;");
            break;
         case '"':
            if (!isAttributeData) {
               this.buf.append("\"");
            } else {
               this.buf.append("&quot;");
            }
            break;
         case '&':
            this.buf.append("&amp;");
            break;
         case '<':
            this.buf.append("&lt;");
            break;
         case '>':
            this.buf.append("&gt;");
            break;
         default:
            if ((c < 1 || c > 31 || c == '\t' || c == '\n') && (c < 127 || c > 159) && c != 8232 && (!isAttributeData || c != '\t' && c != '\n')) {
               this.buf.append(c);
            } else {
               this.buf.append("&#x");
               this.buf.append(Integer.toHexString(c).toUpperCase());
               this.buf.append(";");
            }
         }

      }
   }
}
