package org.jooq.impl;

import java.beans.Introspector;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import org.jooq.Converter;

public class AbstractXMLasObjectBinding<T> extends AbstractVarcharBinding<T> {
   private static final long serialVersionUID = -2153155338260706262L;
   private final Converter<Object, T> converter;

   protected AbstractXMLasObjectBinding(Class<T> theType) {
      this.converter = new AbstractXMLasObjectBinding.XMLasObjectConverter(theType);
   }

   public final Converter<Object, T> converter() {
      return this.converter;
   }

   private static final class XMLasObjectConverter<T> implements Converter<Object, T> {
      private static final long serialVersionUID = -2153155338260706262L;
      Class<T> type;
      XmlRootElement root;
      transient JAXBContext ctx;

      private XMLasObjectConverter(Class<T> type) {
         this.type = type;
         this.root = (XmlRootElement)type.getAnnotation(XmlRootElement.class);
         this.ctx = this.initCtx();
      }

      private final JAXBContext initCtx() {
         try {
            return JAXBContext.newInstance(this.type);
         } catch (JAXBException var2) {
            throw new DataBindingException(var2);
         }
      }

      public T from(Object t) {
         return t == null ? null : JAXB.unmarshal(new StringReader("" + t), this.type);
      }

      public Object to(T u) {
         if (u == null) {
            return null;
         } else {
            try {
               StringWriter s = new StringWriter();
               Object o = u;
               if (this.root == null) {
                  o = new JAXBElement(new QName(Introspector.decapitalize(this.type.getSimpleName())), this.type, u);
               }

               Marshaller m = this.ctx.createMarshaller();
               m.setProperty("jaxb.fragment", true);
               m.marshal(o, s);
               return s.toString();
            } catch (JAXBException var5) {
               throw new DataBindingException(var5);
            }
         }
      }

      public Class<Object> fromType() {
         return Object.class;
      }

      public Class<T> toType() {
         return this.type;
      }

      private void writeObject(ObjectOutputStream oos) throws IOException {
         oos.defaultWriteObject();
      }

      private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
         ois.defaultReadObject();
         this.ctx = this.initCtx();
      }

      // $FF: synthetic method
      XMLasObjectConverter(Class x0, Object x1) {
         this(x0);
      }
   }
}
