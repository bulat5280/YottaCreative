package org.jooq.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jooq.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLasDOMBinding extends AbstractVarcharBinding<Node> {
   private static final long serialVersionUID = -2153155338260706262L;
   private final Converter<Object, Node> converter = new Converter<Object, Node>() {
      private static final long serialVersionUID = -2153155338260706262L;

      public Node from(Object t) {
         return t == null ? null : XMLasDOMBinding.fromString("" + t);
      }

      public Object to(Node u) {
         return u == null ? null : XMLasDOMBinding.toString(u);
      }

      public Class<Object> fromType() {
         return Object.class;
      }

      public Class<Node> toType() {
         return Node.class;
      }
   };

   public final Converter<Object, Node> converter() {
      return this.converter;
   }

   static final String toString(Node node) {
      try {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         transformer.setOutputProperty("omit-xml-declaration", "yes");
         Source source = new DOMSource(node);
         Result target = new StreamResult(out);
         transformer.transform(source, target);
         return out.toString("UTF-8");
      } catch (Exception var5) {
         return "[ ERROR IN toString() : " + var5.getMessage() + " ]";
      }
   }

   public static Document fromString(String name) {
      Document document = builder().newDocument();
      DocumentFragment fragment = createContent(document, name);
      if (fragment != null) {
         document.appendChild(fragment);
      } else {
         document.appendChild(document.createElement(name));
      }

      return document;
   }

   public static DocumentBuilder builder() {
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

         try {
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
         } catch (ParserConfigurationException var4) {
         }

         try {
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
         } catch (ParserConfigurationException var3) {
         }

         try {
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
         } catch (ParserConfigurationException var2) {
         }

         factory.setXIncludeAware(false);
         factory.setExpandEntityReferences(false);
         factory.setNamespaceAware(true);
         DocumentBuilder builder = factory.newDocumentBuilder();
         return builder;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   static final DocumentFragment createContent(Document doc, String text) {
      if (text != null && text.contains("<")) {
         DocumentBuilder builder = builder();

         try {
            text = text.trim();
            if (text.startsWith("<?xml")) {
               Document parsed = builder.parse(new InputSource(new StringReader(text)));
               DocumentFragment fragment = parsed.createDocumentFragment();
               fragment.appendChild(parsed.getDocumentElement());
               return (DocumentFragment)doc.importNode(fragment, true);
            }

            String wrapped = "<dummy>" + text + "</dummy>";
            Document parsed = builder.parse(new InputSource(new StringReader(wrapped)));
            DocumentFragment fragment = parsed.createDocumentFragment();
            NodeList children = parsed.getDocumentElement().getChildNodes();

            while(children.getLength() > 0) {
               fragment.appendChild(children.item(0));
            }

            return (DocumentFragment)doc.importNode(fragment, true);
         } catch (IOException var7) {
         } catch (SAXException var8) {
         }
      }

      return null;
   }
}
