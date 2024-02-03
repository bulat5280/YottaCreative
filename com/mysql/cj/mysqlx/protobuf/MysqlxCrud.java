package com.mysql.cj.mysqlx.protobuf;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.RepeatedFieldBuilder;
import com.google.protobuf.SingleFieldBuilder;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.GeneratedMessage.BuilderParent;
import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
import com.google.protobuf.Internal.EnumLiteMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MysqlxCrud {
   private static final Descriptor internal_static_Mysqlx_Crud_Column_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Column_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Projection_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Projection_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Collection_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Collection_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Limit_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Limit_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Order_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Order_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Find_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Find_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Insert_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Insert_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Update_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Update_fieldAccessorTable;
   private static final Descriptor internal_static_Mysqlx_Crud_Delete_descriptor;
   private static FieldAccessorTable internal_static_Mysqlx_Crud_Delete_fieldAccessorTable;
   private static FileDescriptor descriptor;

   private MysqlxCrud() {
   }

   public static void registerAllExtensions(ExtensionRegistry registry) {
   }

   public static FileDescriptor getDescriptor() {
      return descriptor;
   }

   static {
      String[] descriptorData = new String[]{"\n\u0011mysqlx_crud.proto\u0012\u000bMysqlx.Crud\u001a\u0011mysqlx_expr.proto\u001a\u0016mysqlx_datatypes.proto\"[\n\u0006Column\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\r\n\u0005alias\u0018\u0002 \u0001(\t\u00124\n\rdocument_path\u0018\u0003 \u0003(\u000b2\u001d.Mysqlx.Expr.DocumentPathItem\">\n\nProjection\u0012!\n\u0006source\u0018\u0001 \u0002(\u000b2\u0011.Mysqlx.Expr.Expr\u0012\r\n\u0005alias\u0018\u0002 \u0001(\t\"*\n\nCollection\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012\u000e\n\u0006schema\u0018\u0002 \u0001(\t\"*\n\u0005Limit\u0012\u0011\n\trow_count\u0018\u0001 \u0002(\u0004\u0012\u000e\n\u0006offset\u0018\u0002 \u0001(\u0004\"~\n\u0005Order\u0012\u001f\n\u0004expr\u0018\u0001 \u0002(\u000b2\u0011.Mysqlx.Expr.Expr\u00124\n\tdirection\u0018\u0002 \u0001(\u000e2\u001c.Mysqlx.Crud.Orde", "r.Direction:\u0003ASC\"\u001e\n\tDirection\u0012\u0007\n\u0003ASC\u0010\u0001\u0012\b\n\u0004DESC\u0010\u0002\"\u009a\u0002\n\u000fUpdateOperation\u0012-\n\u0006source\u0018\u0001 \u0002(\u000b2\u001d.Mysqlx.Expr.ColumnIdentifier\u0012:\n\toperation\u0018\u0002 \u0002(\u000e2'.Mysqlx.Crud.UpdateOperation.UpdateType\u0012 \n\u0005value\u0018\u0003 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\"z\n\nUpdateType\u0012\u0007\n\u0003SET\u0010\u0001\u0012\u000f\n\u000bITEM_REMOVE\u0010\u0002\u0012\f\n\bITEM_SET\u0010\u0003\u0012\u0010\n\fITEM_REPLACE\u0010\u0004\u0012\u000e\n\nITEM_MERGE\u0010\u0005\u0012\u0010\n\fARRAY_INSERT\u0010\u0006\u0012\u0010\n\fARRAY_APPEND\u0010\u0007\"ò\u0002\n\u0004Find\u0012+\n\ncollection\u0018\u0002 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_mode", "l\u0018\u0003 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012+\n\nprojection\u0018\u0004 \u0003(\u000b2\u0017.Mysqlx.Crud.Projection\u0012#\n\bcriteria\u0018\u0005 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012&\n\u0004args\u0018\u000b \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012!\n\u0005limit\u0018\u0006 \u0001(\u000b2\u0012.Mysqlx.Crud.Limit\u0012!\n\u0005order\u0018\u0007 \u0003(\u000b2\u0012.Mysqlx.Crud.Order\u0012#\n\bgrouping\u0018\b \u0003(\u000b2\u0011.Mysqlx.Expr.Expr\u0012,\n\u0011grouping_criteria\u0018\t \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\"\u008b\u0002\n\u0006Insert\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0002 \u0001(\u000e2\u0016.Mysqlx.Crud.Dat", "aModel\u0012'\n\nprojection\u0018\u0003 \u0003(\u000b2\u0013.Mysqlx.Crud.Column\u0012)\n\u0003row\u0018\u0004 \u0003(\u000b2\u001c.Mysqlx.Crud.Insert.TypedRow\u0012&\n\u0004args\u0018\u0005 \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u001a,\n\bTypedRow\u0012 \n\u0005field\u0018\u0001 \u0003(\u000b2\u0011.Mysqlx.Expr.Expr\"¥\u0002\n\u0006Update\u0012+\n\ncollection\u0018\u0002 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0003 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012#\n\bcriteria\u0018\u0004 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012&\n\u0004args\u0018\b \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012!\n\u0005limit\u0018\u0005 \u0001(\u000b2\u0012.Mysqlx.Crud.Limit\u0012!\n\u0005order\u0018\u0006 ", "\u0003(\u000b2\u0012.Mysqlx.Crud.Order\u0012/\n\toperation\u0018\u0007 \u0003(\u000b2\u001c.Mysqlx.Crud.UpdateOperation\"ô\u0001\n\u0006Delete\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0002 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012#\n\bcriteria\u0018\u0003 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012&\n\u0004args\u0018\u0006 \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012!\n\u0005limit\u0018\u0004 \u0001(\u000b2\u0012.Mysqlx.Crud.Limit\u0012!\n\u0005order\u0018\u0005 \u0003(\u000b2\u0012.Mysqlx.Crud.Order*$\n\tDataModel\u0012\f\n\bDOCUMENT\u0010\u0001\u0012\t\n\u0005TABLE\u0010\u0002B\u001e\n\u001ccom.mysql.cj.mysqlx.protobuf"};
      InternalDescriptorAssigner assigner = new InternalDescriptorAssigner() {
         public ExtensionRegistry assignDescriptors(FileDescriptor root) {
            MysqlxCrud.descriptor = root;
            return null;
         }
      };
      FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new FileDescriptor[]{MysqlxExpr.getDescriptor(), MysqlxDatatypes.getDescriptor()}, assigner);
      internal_static_Mysqlx_Crud_Column_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
      internal_static_Mysqlx_Crud_Column_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Column_descriptor, new String[]{"Name", "Alias", "DocumentPath"});
      internal_static_Mysqlx_Crud_Projection_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(1);
      internal_static_Mysqlx_Crud_Projection_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Projection_descriptor, new String[]{"Source", "Alias"});
      internal_static_Mysqlx_Crud_Collection_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(2);
      internal_static_Mysqlx_Crud_Collection_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Collection_descriptor, new String[]{"Name", "Schema"});
      internal_static_Mysqlx_Crud_Limit_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(3);
      internal_static_Mysqlx_Crud_Limit_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Limit_descriptor, new String[]{"RowCount", "Offset"});
      internal_static_Mysqlx_Crud_Order_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(4);
      internal_static_Mysqlx_Crud_Order_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Order_descriptor, new String[]{"Expr", "Direction"});
      internal_static_Mysqlx_Crud_UpdateOperation_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(5);
      internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_UpdateOperation_descriptor, new String[]{"Source", "Operation", "Value"});
      internal_static_Mysqlx_Crud_Find_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(6);
      internal_static_Mysqlx_Crud_Find_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Find_descriptor, new String[]{"Collection", "DataModel", "Projection", "Criteria", "Args", "Limit", "Order", "Grouping", "GroupingCriteria"});
      internal_static_Mysqlx_Crud_Insert_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(7);
      internal_static_Mysqlx_Crud_Insert_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Insert_descriptor, new String[]{"Collection", "DataModel", "Projection", "Row", "Args"});
      internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor = (Descriptor)internal_static_Mysqlx_Crud_Insert_descriptor.getNestedTypes().get(0);
      internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor, new String[]{"Field"});
      internal_static_Mysqlx_Crud_Update_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(8);
      internal_static_Mysqlx_Crud_Update_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Update_descriptor, new String[]{"Collection", "DataModel", "Criteria", "Args", "Limit", "Order", "Operation"});
      internal_static_Mysqlx_Crud_Delete_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(9);
      internal_static_Mysqlx_Crud_Delete_fieldAccessorTable = new FieldAccessorTable(internal_static_Mysqlx_Crud_Delete_descriptor, new String[]{"Collection", "DataModel", "Criteria", "Args", "Limit", "Order"});
      MysqlxExpr.getDescriptor();
      MysqlxDatatypes.getDescriptor();
   }

   public static final class Delete extends GeneratedMessage implements MysqlxCrud.DeleteOrBuilder {
      private static final MysqlxCrud.Delete defaultInstance = new MysqlxCrud.Delete(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Delete> PARSER = new AbstractParser<MysqlxCrud.Delete>() {
         public MysqlxCrud.Delete parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Delete(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int COLLECTION_FIELD_NUMBER = 1;
      private MysqlxCrud.Collection collection_;
      public static final int DATA_MODEL_FIELD_NUMBER = 2;
      private MysqlxCrud.DataModel dataModel_;
      public static final int CRITERIA_FIELD_NUMBER = 3;
      private MysqlxExpr.Expr criteria_;
      public static final int ARGS_FIELD_NUMBER = 6;
      private List<MysqlxDatatypes.Scalar> args_;
      public static final int LIMIT_FIELD_NUMBER = 4;
      private MysqlxCrud.Limit limit_;
      public static final int ORDER_FIELD_NUMBER = 5;
      private List<MysqlxCrud.Order> order_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Delete(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Delete(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Delete getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Delete getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Delete(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = 0;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  MysqlxCrud.Collection.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.collection_.toBuilder();
                  }

                  this.collection_ = (MysqlxCrud.Collection)input.readMessage(MysqlxCrud.Collection.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.collection_);
                     this.collection_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 16:
                  int rawValue = input.readEnum();
                  MysqlxCrud.DataModel value = MysqlxCrud.DataModel.valueOf(rawValue);
                  if (value == null) {
                     unknownFields.mergeVarintField(2, rawValue);
                  } else {
                     this.bitField0_ |= 2;
                     this.dataModel_ = value;
                  }
                  break;
               case 26:
                  MysqlxExpr.Expr.Builder subBuilder = null;
                  if ((this.bitField0_ & 4) == 4) {
                     subBuilder = this.criteria_.toBuilder();
                  }

                  this.criteria_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.criteria_);
                     this.criteria_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 4;
                  break;
               case 34:
                  MysqlxCrud.Limit.Builder subBuilder = null;
                  if ((this.bitField0_ & 8) == 8) {
                     subBuilder = this.limit_.toBuilder();
                  }

                  this.limit_ = (MysqlxCrud.Limit)input.readMessage(MysqlxCrud.Limit.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.limit_);
                     this.limit_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 8;
                  break;
               case 42:
                  if ((mutable_bitField0_ & 32) != 32) {
                     this.order_ = new ArrayList();
                     mutable_bitField0_ |= 32;
                  }

                  this.order_.add(input.readMessage(MysqlxCrud.Order.PARSER, extensionRegistry));
                  break;
               case 50:
                  if ((mutable_bitField0_ & 8) != 8) {
                     this.args_ = new ArrayList();
                     mutable_bitField0_ |= 8;
                  }

                  this.args_.add(input.readMessage(MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var13) {
            throw var13.setUnfinishedMessage(this);
         } catch (IOException var14) {
            throw (new InvalidProtocolBufferException(var14.getMessage())).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 32) == 32) {
               this.order_ = Collections.unmodifiableList(this.order_);
            }

            if ((mutable_bitField0_ & 8) == 8) {
               this.args_ = Collections.unmodifiableList(this.args_);
            }

            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Delete.class, MysqlxCrud.Delete.Builder.class);
      }

      public Parser<MysqlxCrud.Delete> getParserForType() {
         return PARSER;
      }

      public boolean hasCollection() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxCrud.Collection getCollection() {
         return this.collection_;
      }

      public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
         return this.collection_;
      }

      public boolean hasDataModel() {
         return (this.bitField0_ & 2) == 2;
      }

      public MysqlxCrud.DataModel getDataModel() {
         return this.dataModel_;
      }

      public boolean hasCriteria() {
         return (this.bitField0_ & 4) == 4;
      }

      public MysqlxExpr.Expr getCriteria() {
         return this.criteria_;
      }

      public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
         return this.criteria_;
      }

      public List<MysqlxDatatypes.Scalar> getArgsList() {
         return this.args_;
      }

      public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
         return this.args_;
      }

      public int getArgsCount() {
         return this.args_.size();
      }

      public MysqlxDatatypes.Scalar getArgs(int index) {
         return (MysqlxDatatypes.Scalar)this.args_.get(index);
      }

      public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
         return (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index);
      }

      public boolean hasLimit() {
         return (this.bitField0_ & 8) == 8;
      }

      public MysqlxCrud.Limit getLimit() {
         return this.limit_;
      }

      public MysqlxCrud.LimitOrBuilder getLimitOrBuilder() {
         return this.limit_;
      }

      public List<MysqlxCrud.Order> getOrderList() {
         return this.order_;
      }

      public List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList() {
         return this.order_;
      }

      public int getOrderCount() {
         return this.order_.size();
      }

      public MysqlxCrud.Order getOrder(int index) {
         return (MysqlxCrud.Order)this.order_.get(index);
      }

      public MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int index) {
         return (MysqlxCrud.OrderOrBuilder)this.order_.get(index);
      }

      private void initFields() {
         this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
         this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
         this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
         this.args_ = Collections.emptyList();
         this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
         this.order_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasCollection()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getCollection().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            int i;
            for(i = 0; i < this.getArgsCount(); ++i) {
               if (!this.getArgs(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            if (this.hasLimit() && !this.getLimit().isInitialized()) {
               this.memoizedIsInitialized = 0;
               return false;
            } else {
               for(i = 0; i < this.getOrderCount(); ++i) {
                  if (!this.getOrder(i).isInitialized()) {
                     this.memoizedIsInitialized = 0;
                     return false;
                  }
               }

               this.memoizedIsInitialized = 1;
               return true;
            }
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.collection_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeEnum(2, this.dataModel_.getNumber());
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeMessage(3, this.criteria_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeMessage(4, this.limit_);
         }

         int i;
         for(i = 0; i < this.order_.size(); ++i) {
            output.writeMessage(5, (MessageLite)this.order_.get(i));
         }

         for(i = 0; i < this.args_.size(); ++i) {
            output.writeMessage(6, (MessageLite)this.args_.get(i));
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.collection_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeEnumSize(2, this.dataModel_.getNumber());
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeMessageSize(3, this.criteria_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeMessageSize(4, this.limit_);
            }

            int i;
            for(i = 0; i < this.order_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.order_.get(i));
            }

            for(i = 0; i < this.args_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.args_.get(i));
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Delete parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Delete parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Delete parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Delete parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Delete parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Delete parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Delete parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Delete)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Delete parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Delete)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Delete parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Delete parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Delete)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Delete.Builder newBuilder() {
         return MysqlxCrud.Delete.Builder.create();
      }

      public MysqlxCrud.Delete.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Delete.Builder newBuilder(MysqlxCrud.Delete prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Delete.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Delete.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Delete.Builder builder = new MysqlxCrud.Delete.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Delete(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Delete(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Delete.Builder> implements MysqlxCrud.DeleteOrBuilder {
         private int bitField0_;
         private MysqlxCrud.Collection collection_;
         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> collectionBuilder_;
         private MysqlxCrud.DataModel dataModel_;
         private MysqlxExpr.Expr criteria_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> criteriaBuilder_;
         private List<MysqlxDatatypes.Scalar> args_;
         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
         private MysqlxCrud.Limit limit_;
         private SingleFieldBuilder<MysqlxCrud.Limit, MysqlxCrud.Limit.Builder, MysqlxCrud.LimitOrBuilder> limitBuilder_;
         private List<MysqlxCrud.Order> order_;
         private RepeatedFieldBuilder<MysqlxCrud.Order, MysqlxCrud.Order.Builder, MysqlxCrud.OrderOrBuilder> orderBuilder_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Delete.class, MysqlxCrud.Delete.Builder.class);
         }

         private Builder() {
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.args_ = Collections.emptyList();
            this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            this.order_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.args_ = Collections.emptyList();
            this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            this.order_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Delete.alwaysUseFieldBuilders) {
               this.getCollectionFieldBuilder();
               this.getCriteriaFieldBuilder();
               this.getArgsFieldBuilder();
               this.getLimitFieldBuilder();
               this.getOrderFieldBuilder();
            }

         }

         private static MysqlxCrud.Delete.Builder create() {
            return new MysqlxCrud.Delete.Builder();
         }

         public MysqlxCrud.Delete.Builder clear() {
            super.clear();
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.bitField0_ &= -3;
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.criteriaBuilder_.clear();
            }

            this.bitField0_ &= -5;
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -9;
            } else {
               this.argsBuilder_.clear();
            }

            if (this.limitBuilder_ == null) {
               this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            } else {
               this.limitBuilder_.clear();
            }

            this.bitField0_ &= -17;
            if (this.orderBuilder_ == null) {
               this.order_ = Collections.emptyList();
               this.bitField0_ &= -33;
            } else {
               this.orderBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor;
         }

         public MysqlxCrud.Delete getDefaultInstanceForType() {
            return MysqlxCrud.Delete.getDefaultInstance();
         }

         public MysqlxCrud.Delete build() {
            MysqlxCrud.Delete result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Delete buildPartial() {
            MysqlxCrud.Delete result = new MysqlxCrud.Delete(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.collectionBuilder_ == null) {
               result.collection_ = this.collection_;
            } else {
               result.collection_ = (MysqlxCrud.Collection)this.collectionBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.dataModel_ = this.dataModel_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            if (this.criteriaBuilder_ == null) {
               result.criteria_ = this.criteria_;
            } else {
               result.criteria_ = (MysqlxExpr.Expr)this.criteriaBuilder_.build();
            }

            if (this.argsBuilder_ == null) {
               if ((this.bitField0_ & 8) == 8) {
                  this.args_ = Collections.unmodifiableList(this.args_);
                  this.bitField0_ &= -9;
               }

               result.args_ = this.args_;
            } else {
               result.args_ = this.argsBuilder_.build();
            }

            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 8;
            }

            if (this.limitBuilder_ == null) {
               result.limit_ = this.limit_;
            } else {
               result.limit_ = (MysqlxCrud.Limit)this.limitBuilder_.build();
            }

            if (this.orderBuilder_ == null) {
               if ((this.bitField0_ & 32) == 32) {
                  this.order_ = Collections.unmodifiableList(this.order_);
                  this.bitField0_ &= -33;
               }

               result.order_ = this.order_;
            } else {
               result.order_ = this.orderBuilder_.build();
            }

            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Delete.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Delete) {
               return this.mergeFrom((MysqlxCrud.Delete)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Delete.Builder mergeFrom(MysqlxCrud.Delete other) {
            if (other == MysqlxCrud.Delete.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasCollection()) {
                  this.mergeCollection(other.getCollection());
               }

               if (other.hasDataModel()) {
                  this.setDataModel(other.getDataModel());
               }

               if (other.hasCriteria()) {
                  this.mergeCriteria(other.getCriteria());
               }

               if (this.argsBuilder_ == null) {
                  if (!other.args_.isEmpty()) {
                     if (this.args_.isEmpty()) {
                        this.args_ = other.args_;
                        this.bitField0_ &= -9;
                     } else {
                        this.ensureArgsIsMutable();
                        this.args_.addAll(other.args_);
                     }

                     this.onChanged();
                  }
               } else if (!other.args_.isEmpty()) {
                  if (this.argsBuilder_.isEmpty()) {
                     this.argsBuilder_.dispose();
                     this.argsBuilder_ = null;
                     this.args_ = other.args_;
                     this.bitField0_ &= -9;
                     this.argsBuilder_ = MysqlxCrud.Delete.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null;
                  } else {
                     this.argsBuilder_.addAllMessages(other.args_);
                  }
               }

               if (other.hasLimit()) {
                  this.mergeLimit(other.getLimit());
               }

               if (this.orderBuilder_ == null) {
                  if (!other.order_.isEmpty()) {
                     if (this.order_.isEmpty()) {
                        this.order_ = other.order_;
                        this.bitField0_ &= -33;
                     } else {
                        this.ensureOrderIsMutable();
                        this.order_.addAll(other.order_);
                     }

                     this.onChanged();
                  }
               } else if (!other.order_.isEmpty()) {
                  if (this.orderBuilder_.isEmpty()) {
                     this.orderBuilder_.dispose();
                     this.orderBuilder_ = null;
                     this.order_ = other.order_;
                     this.bitField0_ &= -33;
                     this.orderBuilder_ = MysqlxCrud.Delete.alwaysUseFieldBuilders ? this.getOrderFieldBuilder() : null;
                  } else {
                     this.orderBuilder_.addAllMessages(other.order_);
                  }
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasCollection()) {
               return false;
            } else if (!this.getCollection().isInitialized()) {
               return false;
            } else if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
               return false;
            } else {
               int i;
               for(i = 0; i < this.getArgsCount(); ++i) {
                  if (!this.getArgs(i).isInitialized()) {
                     return false;
                  }
               }

               if (this.hasLimit() && !this.getLimit().isInitialized()) {
                  return false;
               } else {
                  for(i = 0; i < this.getOrderCount(); ++i) {
                     if (!this.getOrder(i).isInitialized()) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }

         public MysqlxCrud.Delete.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Delete parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Delete)MysqlxCrud.Delete.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Delete)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasCollection() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxCrud.Collection getCollection() {
            return this.collectionBuilder_ == null ? this.collection_ : (MysqlxCrud.Collection)this.collectionBuilder_.getMessage();
         }

         public MysqlxCrud.Delete.Builder setCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.collection_ = value;
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Delete.Builder setCollection(MysqlxCrud.Collection.Builder builderForValue) {
            if (this.collectionBuilder_ == null) {
               this.collection_ = builderForValue.build();
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Delete.Builder mergeCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.collection_ != MysqlxCrud.Collection.getDefaultInstance()) {
                  this.collection_ = MysqlxCrud.Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
               } else {
                  this.collection_ = value;
               }

               this.onChanged();
            } else {
               this.collectionBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Delete.Builder clearCollection() {
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
               this.onChanged();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxCrud.Collection.Builder getCollectionBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxCrud.Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
         }

         public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
            return (MysqlxCrud.CollectionOrBuilder)(this.collectionBuilder_ != null ? (MysqlxCrud.CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder() : this.collection_);
         }

         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> getCollectionFieldBuilder() {
            if (this.collectionBuilder_ == null) {
               this.collectionBuilder_ = new SingleFieldBuilder(this.getCollection(), this.getParentForChildren(), this.isClean());
               this.collection_ = null;
            }

            return this.collectionBuilder_;
         }

         public boolean hasDataModel() {
            return (this.bitField0_ & 2) == 2;
         }

         public MysqlxCrud.DataModel getDataModel() {
            return this.dataModel_;
         }

         public MysqlxCrud.Delete.Builder setDataModel(MysqlxCrud.DataModel value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.dataModel_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Delete.Builder clearDataModel() {
            this.bitField0_ &= -3;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.onChanged();
            return this;
         }

         public boolean hasCriteria() {
            return (this.bitField0_ & 4) == 4;
         }

         public MysqlxExpr.Expr getCriteria() {
            return this.criteriaBuilder_ == null ? this.criteria_ : (MysqlxExpr.Expr)this.criteriaBuilder_.getMessage();
         }

         public MysqlxCrud.Delete.Builder setCriteria(MysqlxExpr.Expr value) {
            if (this.criteriaBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.criteria_ = value;
               this.onChanged();
            } else {
               this.criteriaBuilder_.setMessage(value);
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.Delete.Builder setCriteria(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = builderForValue.build();
               this.onChanged();
            } else {
               this.criteriaBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.Delete.Builder mergeCriteria(MysqlxExpr.Expr value) {
            if (this.criteriaBuilder_ == null) {
               if ((this.bitField0_ & 4) == 4 && this.criteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.criteria_ = MysqlxExpr.Expr.newBuilder(this.criteria_).mergeFrom(value).buildPartial();
               } else {
                  this.criteria_ = value;
               }

               this.onChanged();
            } else {
               this.criteriaBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.Delete.Builder clearCriteria() {
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.criteriaBuilder_.clear();
            }

            this.bitField0_ &= -5;
            return this;
         }

         public MysqlxExpr.Expr.Builder getCriteriaBuilder() {
            this.bitField0_ |= 4;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getCriteriaFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.criteriaBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.criteriaBuilder_.getMessageOrBuilder() : this.criteria_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getCriteriaFieldBuilder() {
            if (this.criteriaBuilder_ == null) {
               this.criteriaBuilder_ = new SingleFieldBuilder(this.getCriteria(), this.getParentForChildren(), this.isClean());
               this.criteria_ = null;
            }

            return this.criteriaBuilder_;
         }

         private void ensureArgsIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
               this.args_ = new ArrayList(this.args_);
               this.bitField0_ |= 8;
            }

         }

         public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.argsBuilder_ == null ? Collections.unmodifiableList(this.args_) : this.argsBuilder_.getMessageList();
         }

         public int getArgsCount() {
            return this.argsBuilder_ == null ? this.args_.size() : this.argsBuilder_.getCount();
         }

         public MysqlxDatatypes.Scalar getArgs(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.Scalar)this.args_.get(index) : (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
         }

         public MysqlxCrud.Delete.Builder setArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.set(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder setArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addArgs(MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addArgs(MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addAllArgs(Iterable<? extends MysqlxDatatypes.Scalar> values) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.args_);
               this.onChanged();
            } else {
               this.argsBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder clearArgs() {
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -9;
               this.onChanged();
            } else {
               this.argsBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder removeArgs(int index) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.remove(index);
               this.onChanged();
            } else {
               this.argsBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxDatatypes.Scalar.Builder getArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
         }

         public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index) : (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.argsBuilder_ != null ? this.argsBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.args_);
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
            return this.getArgsFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
            if (this.argsBuilder_ == null) {
               this.argsBuilder_ = new RepeatedFieldBuilder(this.args_, (this.bitField0_ & 8) == 8, this.getParentForChildren(), this.isClean());
               this.args_ = null;
            }

            return this.argsBuilder_;
         }

         public boolean hasLimit() {
            return (this.bitField0_ & 16) == 16;
         }

         public MysqlxCrud.Limit getLimit() {
            return this.limitBuilder_ == null ? this.limit_ : (MysqlxCrud.Limit)this.limitBuilder_.getMessage();
         }

         public MysqlxCrud.Delete.Builder setLimit(MysqlxCrud.Limit value) {
            if (this.limitBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.limit_ = value;
               this.onChanged();
            } else {
               this.limitBuilder_.setMessage(value);
            }

            this.bitField0_ |= 16;
            return this;
         }

         public MysqlxCrud.Delete.Builder setLimit(MysqlxCrud.Limit.Builder builderForValue) {
            if (this.limitBuilder_ == null) {
               this.limit_ = builderForValue.build();
               this.onChanged();
            } else {
               this.limitBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 16;
            return this;
         }

         public MysqlxCrud.Delete.Builder mergeLimit(MysqlxCrud.Limit value) {
            if (this.limitBuilder_ == null) {
               if ((this.bitField0_ & 16) == 16 && this.limit_ != MysqlxCrud.Limit.getDefaultInstance()) {
                  this.limit_ = MysqlxCrud.Limit.newBuilder(this.limit_).mergeFrom(value).buildPartial();
               } else {
                  this.limit_ = value;
               }

               this.onChanged();
            } else {
               this.limitBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 16;
            return this;
         }

         public MysqlxCrud.Delete.Builder clearLimit() {
            if (this.limitBuilder_ == null) {
               this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
               this.onChanged();
            } else {
               this.limitBuilder_.clear();
            }

            this.bitField0_ &= -17;
            return this;
         }

         public MysqlxCrud.Limit.Builder getLimitBuilder() {
            this.bitField0_ |= 16;
            this.onChanged();
            return (MysqlxCrud.Limit.Builder)this.getLimitFieldBuilder().getBuilder();
         }

         public MysqlxCrud.LimitOrBuilder getLimitOrBuilder() {
            return (MysqlxCrud.LimitOrBuilder)(this.limitBuilder_ != null ? (MysqlxCrud.LimitOrBuilder)this.limitBuilder_.getMessageOrBuilder() : this.limit_);
         }

         private SingleFieldBuilder<MysqlxCrud.Limit, MysqlxCrud.Limit.Builder, MysqlxCrud.LimitOrBuilder> getLimitFieldBuilder() {
            if (this.limitBuilder_ == null) {
               this.limitBuilder_ = new SingleFieldBuilder(this.getLimit(), this.getParentForChildren(), this.isClean());
               this.limit_ = null;
            }

            return this.limitBuilder_;
         }

         private void ensureOrderIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
               this.order_ = new ArrayList(this.order_);
               this.bitField0_ |= 32;
            }

         }

         public List<MysqlxCrud.Order> getOrderList() {
            return this.orderBuilder_ == null ? Collections.unmodifiableList(this.order_) : this.orderBuilder_.getMessageList();
         }

         public int getOrderCount() {
            return this.orderBuilder_ == null ? this.order_.size() : this.orderBuilder_.getCount();
         }

         public MysqlxCrud.Order getOrder(int index) {
            return this.orderBuilder_ == null ? (MysqlxCrud.Order)this.order_.get(index) : (MysqlxCrud.Order)this.orderBuilder_.getMessage(index);
         }

         public MysqlxCrud.Delete.Builder setOrder(int index, MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.set(index, value);
               this.onChanged();
            } else {
               this.orderBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder setOrder(int index, MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addOrder(MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.add(value);
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addOrder(int index, MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.add(index, value);
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addOrder(MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addOrder(int index, MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder addAllOrder(Iterable<? extends MysqlxCrud.Order> values) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.order_);
               this.onChanged();
            } else {
               this.orderBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder clearOrder() {
            if (this.orderBuilder_ == null) {
               this.order_ = Collections.emptyList();
               this.bitField0_ &= -33;
               this.onChanged();
            } else {
               this.orderBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Delete.Builder removeOrder(int index) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.remove(index);
               this.onChanged();
            } else {
               this.orderBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.Order.Builder getOrderBuilder(int index) {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int index) {
            return this.orderBuilder_ == null ? (MysqlxCrud.OrderOrBuilder)this.order_.get(index) : (MysqlxCrud.OrderOrBuilder)this.orderBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList() {
            return this.orderBuilder_ != null ? this.orderBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.order_);
         }

         public MysqlxCrud.Order.Builder addOrderBuilder() {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().addBuilder(MysqlxCrud.Order.getDefaultInstance());
         }

         public MysqlxCrud.Order.Builder addOrderBuilder(int index) {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().addBuilder(index, MysqlxCrud.Order.getDefaultInstance());
         }

         public List<MysqlxCrud.Order.Builder> getOrderBuilderList() {
            return this.getOrderFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.Order, MysqlxCrud.Order.Builder, MysqlxCrud.OrderOrBuilder> getOrderFieldBuilder() {
            if (this.orderBuilder_ == null) {
               this.orderBuilder_ = new RepeatedFieldBuilder(this.order_, (this.bitField0_ & 32) == 32, this.getParentForChildren(), this.isClean());
               this.order_ = null;
            }

            return this.orderBuilder_;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface DeleteOrBuilder extends MessageOrBuilder {
      boolean hasCollection();

      MysqlxCrud.Collection getCollection();

      MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder();

      boolean hasDataModel();

      MysqlxCrud.DataModel getDataModel();

      boolean hasCriteria();

      MysqlxExpr.Expr getCriteria();

      MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder();

      List<MysqlxDatatypes.Scalar> getArgsList();

      MysqlxDatatypes.Scalar getArgs(int var1);

      int getArgsCount();

      List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();

      MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int var1);

      boolean hasLimit();

      MysqlxCrud.Limit getLimit();

      MysqlxCrud.LimitOrBuilder getLimitOrBuilder();

      List<MysqlxCrud.Order> getOrderList();

      MysqlxCrud.Order getOrder(int var1);

      int getOrderCount();

      List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList();

      MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int var1);
   }

   public static final class Update extends GeneratedMessage implements MysqlxCrud.UpdateOrBuilder {
      private static final MysqlxCrud.Update defaultInstance = new MysqlxCrud.Update(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Update> PARSER = new AbstractParser<MysqlxCrud.Update>() {
         public MysqlxCrud.Update parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Update(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int COLLECTION_FIELD_NUMBER = 2;
      private MysqlxCrud.Collection collection_;
      public static final int DATA_MODEL_FIELD_NUMBER = 3;
      private MysqlxCrud.DataModel dataModel_;
      public static final int CRITERIA_FIELD_NUMBER = 4;
      private MysqlxExpr.Expr criteria_;
      public static final int ARGS_FIELD_NUMBER = 8;
      private List<MysqlxDatatypes.Scalar> args_;
      public static final int LIMIT_FIELD_NUMBER = 5;
      private MysqlxCrud.Limit limit_;
      public static final int ORDER_FIELD_NUMBER = 6;
      private List<MysqlxCrud.Order> order_;
      public static final int OPERATION_FIELD_NUMBER = 7;
      private List<MysqlxCrud.UpdateOperation> operation_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Update(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Update(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Update getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Update getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Update(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = 0;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 18:
                  MysqlxCrud.Collection.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.collection_.toBuilder();
                  }

                  this.collection_ = (MysqlxCrud.Collection)input.readMessage(MysqlxCrud.Collection.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.collection_);
                     this.collection_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 24:
                  int rawValue = input.readEnum();
                  MysqlxCrud.DataModel value = MysqlxCrud.DataModel.valueOf(rawValue);
                  if (value == null) {
                     unknownFields.mergeVarintField(3, rawValue);
                  } else {
                     this.bitField0_ |= 2;
                     this.dataModel_ = value;
                  }
                  break;
               case 34:
                  MysqlxExpr.Expr.Builder subBuilder = null;
                  if ((this.bitField0_ & 4) == 4) {
                     subBuilder = this.criteria_.toBuilder();
                  }

                  this.criteria_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.criteria_);
                     this.criteria_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 4;
                  break;
               case 42:
                  MysqlxCrud.Limit.Builder subBuilder = null;
                  if ((this.bitField0_ & 8) == 8) {
                     subBuilder = this.limit_.toBuilder();
                  }

                  this.limit_ = (MysqlxCrud.Limit)input.readMessage(MysqlxCrud.Limit.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.limit_);
                     this.limit_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 8;
                  break;
               case 50:
                  if ((mutable_bitField0_ & 32) != 32) {
                     this.order_ = new ArrayList();
                     mutable_bitField0_ |= 32;
                  }

                  this.order_.add(input.readMessage(MysqlxCrud.Order.PARSER, extensionRegistry));
                  break;
               case 58:
                  if ((mutable_bitField0_ & 64) != 64) {
                     this.operation_ = new ArrayList();
                     mutable_bitField0_ |= 64;
                  }

                  this.operation_.add(input.readMessage(MysqlxCrud.UpdateOperation.PARSER, extensionRegistry));
                  break;
               case 66:
                  if ((mutable_bitField0_ & 8) != 8) {
                     this.args_ = new ArrayList();
                     mutable_bitField0_ |= 8;
                  }

                  this.args_.add(input.readMessage(MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var13) {
            throw var13.setUnfinishedMessage(this);
         } catch (IOException var14) {
            throw (new InvalidProtocolBufferException(var14.getMessage())).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 32) == 32) {
               this.order_ = Collections.unmodifiableList(this.order_);
            }

            if ((mutable_bitField0_ & 64) == 64) {
               this.operation_ = Collections.unmodifiableList(this.operation_);
            }

            if ((mutable_bitField0_ & 8) == 8) {
               this.args_ = Collections.unmodifiableList(this.args_);
            }

            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Update_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Update.class, MysqlxCrud.Update.Builder.class);
      }

      public Parser<MysqlxCrud.Update> getParserForType() {
         return PARSER;
      }

      public boolean hasCollection() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxCrud.Collection getCollection() {
         return this.collection_;
      }

      public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
         return this.collection_;
      }

      public boolean hasDataModel() {
         return (this.bitField0_ & 2) == 2;
      }

      public MysqlxCrud.DataModel getDataModel() {
         return this.dataModel_;
      }

      public boolean hasCriteria() {
         return (this.bitField0_ & 4) == 4;
      }

      public MysqlxExpr.Expr getCriteria() {
         return this.criteria_;
      }

      public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
         return this.criteria_;
      }

      public List<MysqlxDatatypes.Scalar> getArgsList() {
         return this.args_;
      }

      public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
         return this.args_;
      }

      public int getArgsCount() {
         return this.args_.size();
      }

      public MysqlxDatatypes.Scalar getArgs(int index) {
         return (MysqlxDatatypes.Scalar)this.args_.get(index);
      }

      public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
         return (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index);
      }

      public boolean hasLimit() {
         return (this.bitField0_ & 8) == 8;
      }

      public MysqlxCrud.Limit getLimit() {
         return this.limit_;
      }

      public MysqlxCrud.LimitOrBuilder getLimitOrBuilder() {
         return this.limit_;
      }

      public List<MysqlxCrud.Order> getOrderList() {
         return this.order_;
      }

      public List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList() {
         return this.order_;
      }

      public int getOrderCount() {
         return this.order_.size();
      }

      public MysqlxCrud.Order getOrder(int index) {
         return (MysqlxCrud.Order)this.order_.get(index);
      }

      public MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int index) {
         return (MysqlxCrud.OrderOrBuilder)this.order_.get(index);
      }

      public List<MysqlxCrud.UpdateOperation> getOperationList() {
         return this.operation_;
      }

      public List<? extends MysqlxCrud.UpdateOperationOrBuilder> getOperationOrBuilderList() {
         return this.operation_;
      }

      public int getOperationCount() {
         return this.operation_.size();
      }

      public MysqlxCrud.UpdateOperation getOperation(int index) {
         return (MysqlxCrud.UpdateOperation)this.operation_.get(index);
      }

      public MysqlxCrud.UpdateOperationOrBuilder getOperationOrBuilder(int index) {
         return (MysqlxCrud.UpdateOperationOrBuilder)this.operation_.get(index);
      }

      private void initFields() {
         this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
         this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
         this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
         this.args_ = Collections.emptyList();
         this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
         this.order_ = Collections.emptyList();
         this.operation_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasCollection()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getCollection().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            int i;
            for(i = 0; i < this.getArgsCount(); ++i) {
               if (!this.getArgs(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            if (this.hasLimit() && !this.getLimit().isInitialized()) {
               this.memoizedIsInitialized = 0;
               return false;
            } else {
               for(i = 0; i < this.getOrderCount(); ++i) {
                  if (!this.getOrder(i).isInitialized()) {
                     this.memoizedIsInitialized = 0;
                     return false;
                  }
               }

               for(i = 0; i < this.getOperationCount(); ++i) {
                  if (!this.getOperation(i).isInitialized()) {
                     this.memoizedIsInitialized = 0;
                     return false;
                  }
               }

               this.memoizedIsInitialized = 1;
               return true;
            }
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(2, this.collection_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeEnum(3, this.dataModel_.getNumber());
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeMessage(4, this.criteria_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeMessage(5, this.limit_);
         }

         int i;
         for(i = 0; i < this.order_.size(); ++i) {
            output.writeMessage(6, (MessageLite)this.order_.get(i));
         }

         for(i = 0; i < this.operation_.size(); ++i) {
            output.writeMessage(7, (MessageLite)this.operation_.get(i));
         }

         for(i = 0; i < this.args_.size(); ++i) {
            output.writeMessage(8, (MessageLite)this.args_.get(i));
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(2, this.collection_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeEnumSize(3, this.dataModel_.getNumber());
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeMessageSize(4, this.criteria_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeMessageSize(5, this.limit_);
            }

            int i;
            for(i = 0; i < this.order_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.order_.get(i));
            }

            for(i = 0; i < this.operation_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.operation_.get(i));
            }

            for(i = 0; i < this.args_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(8, (MessageLite)this.args_.get(i));
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Update parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Update)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Update parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Update)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Update parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Update)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Update parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Update)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Update parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Update)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Update parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Update)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Update parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Update)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Update parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Update)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Update parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Update)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Update parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Update)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Update.Builder newBuilder() {
         return MysqlxCrud.Update.Builder.create();
      }

      public MysqlxCrud.Update.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Update.Builder newBuilder(MysqlxCrud.Update prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Update.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Update.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Update.Builder builder = new MysqlxCrud.Update.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Update(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Update(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Update.Builder> implements MysqlxCrud.UpdateOrBuilder {
         private int bitField0_;
         private MysqlxCrud.Collection collection_;
         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> collectionBuilder_;
         private MysqlxCrud.DataModel dataModel_;
         private MysqlxExpr.Expr criteria_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> criteriaBuilder_;
         private List<MysqlxDatatypes.Scalar> args_;
         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
         private MysqlxCrud.Limit limit_;
         private SingleFieldBuilder<MysqlxCrud.Limit, MysqlxCrud.Limit.Builder, MysqlxCrud.LimitOrBuilder> limitBuilder_;
         private List<MysqlxCrud.Order> order_;
         private RepeatedFieldBuilder<MysqlxCrud.Order, MysqlxCrud.Order.Builder, MysqlxCrud.OrderOrBuilder> orderBuilder_;
         private List<MysqlxCrud.UpdateOperation> operation_;
         private RepeatedFieldBuilder<MysqlxCrud.UpdateOperation, MysqlxCrud.UpdateOperation.Builder, MysqlxCrud.UpdateOperationOrBuilder> operationBuilder_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Update_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Update.class, MysqlxCrud.Update.Builder.class);
         }

         private Builder() {
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.args_ = Collections.emptyList();
            this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            this.order_ = Collections.emptyList();
            this.operation_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.args_ = Collections.emptyList();
            this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            this.order_ = Collections.emptyList();
            this.operation_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Update.alwaysUseFieldBuilders) {
               this.getCollectionFieldBuilder();
               this.getCriteriaFieldBuilder();
               this.getArgsFieldBuilder();
               this.getLimitFieldBuilder();
               this.getOrderFieldBuilder();
               this.getOperationFieldBuilder();
            }

         }

         private static MysqlxCrud.Update.Builder create() {
            return new MysqlxCrud.Update.Builder();
         }

         public MysqlxCrud.Update.Builder clear() {
            super.clear();
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.bitField0_ &= -3;
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.criteriaBuilder_.clear();
            }

            this.bitField0_ &= -5;
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -9;
            } else {
               this.argsBuilder_.clear();
            }

            if (this.limitBuilder_ == null) {
               this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            } else {
               this.limitBuilder_.clear();
            }

            this.bitField0_ &= -17;
            if (this.orderBuilder_ == null) {
               this.order_ = Collections.emptyList();
               this.bitField0_ &= -33;
            } else {
               this.orderBuilder_.clear();
            }

            if (this.operationBuilder_ == null) {
               this.operation_ = Collections.emptyList();
               this.bitField0_ &= -65;
            } else {
               this.operationBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Update.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor;
         }

         public MysqlxCrud.Update getDefaultInstanceForType() {
            return MysqlxCrud.Update.getDefaultInstance();
         }

         public MysqlxCrud.Update build() {
            MysqlxCrud.Update result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Update buildPartial() {
            MysqlxCrud.Update result = new MysqlxCrud.Update(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.collectionBuilder_ == null) {
               result.collection_ = this.collection_;
            } else {
               result.collection_ = (MysqlxCrud.Collection)this.collectionBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.dataModel_ = this.dataModel_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            if (this.criteriaBuilder_ == null) {
               result.criteria_ = this.criteria_;
            } else {
               result.criteria_ = (MysqlxExpr.Expr)this.criteriaBuilder_.build();
            }

            if (this.argsBuilder_ == null) {
               if ((this.bitField0_ & 8) == 8) {
                  this.args_ = Collections.unmodifiableList(this.args_);
                  this.bitField0_ &= -9;
               }

               result.args_ = this.args_;
            } else {
               result.args_ = this.argsBuilder_.build();
            }

            if ((from_bitField0_ & 16) == 16) {
               to_bitField0_ |= 8;
            }

            if (this.limitBuilder_ == null) {
               result.limit_ = this.limit_;
            } else {
               result.limit_ = (MysqlxCrud.Limit)this.limitBuilder_.build();
            }

            if (this.orderBuilder_ == null) {
               if ((this.bitField0_ & 32) == 32) {
                  this.order_ = Collections.unmodifiableList(this.order_);
                  this.bitField0_ &= -33;
               }

               result.order_ = this.order_;
            } else {
               result.order_ = this.orderBuilder_.build();
            }

            if (this.operationBuilder_ == null) {
               if ((this.bitField0_ & 64) == 64) {
                  this.operation_ = Collections.unmodifiableList(this.operation_);
                  this.bitField0_ &= -65;
               }

               result.operation_ = this.operation_;
            } else {
               result.operation_ = this.operationBuilder_.build();
            }

            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Update.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Update) {
               return this.mergeFrom((MysqlxCrud.Update)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Update.Builder mergeFrom(MysqlxCrud.Update other) {
            if (other == MysqlxCrud.Update.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasCollection()) {
                  this.mergeCollection(other.getCollection());
               }

               if (other.hasDataModel()) {
                  this.setDataModel(other.getDataModel());
               }

               if (other.hasCriteria()) {
                  this.mergeCriteria(other.getCriteria());
               }

               if (this.argsBuilder_ == null) {
                  if (!other.args_.isEmpty()) {
                     if (this.args_.isEmpty()) {
                        this.args_ = other.args_;
                        this.bitField0_ &= -9;
                     } else {
                        this.ensureArgsIsMutable();
                        this.args_.addAll(other.args_);
                     }

                     this.onChanged();
                  }
               } else if (!other.args_.isEmpty()) {
                  if (this.argsBuilder_.isEmpty()) {
                     this.argsBuilder_.dispose();
                     this.argsBuilder_ = null;
                     this.args_ = other.args_;
                     this.bitField0_ &= -9;
                     this.argsBuilder_ = MysqlxCrud.Update.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null;
                  } else {
                     this.argsBuilder_.addAllMessages(other.args_);
                  }
               }

               if (other.hasLimit()) {
                  this.mergeLimit(other.getLimit());
               }

               if (this.orderBuilder_ == null) {
                  if (!other.order_.isEmpty()) {
                     if (this.order_.isEmpty()) {
                        this.order_ = other.order_;
                        this.bitField0_ &= -33;
                     } else {
                        this.ensureOrderIsMutable();
                        this.order_.addAll(other.order_);
                     }

                     this.onChanged();
                  }
               } else if (!other.order_.isEmpty()) {
                  if (this.orderBuilder_.isEmpty()) {
                     this.orderBuilder_.dispose();
                     this.orderBuilder_ = null;
                     this.order_ = other.order_;
                     this.bitField0_ &= -33;
                     this.orderBuilder_ = MysqlxCrud.Update.alwaysUseFieldBuilders ? this.getOrderFieldBuilder() : null;
                  } else {
                     this.orderBuilder_.addAllMessages(other.order_);
                  }
               }

               if (this.operationBuilder_ == null) {
                  if (!other.operation_.isEmpty()) {
                     if (this.operation_.isEmpty()) {
                        this.operation_ = other.operation_;
                        this.bitField0_ &= -65;
                     } else {
                        this.ensureOperationIsMutable();
                        this.operation_.addAll(other.operation_);
                     }

                     this.onChanged();
                  }
               } else if (!other.operation_.isEmpty()) {
                  if (this.operationBuilder_.isEmpty()) {
                     this.operationBuilder_.dispose();
                     this.operationBuilder_ = null;
                     this.operation_ = other.operation_;
                     this.bitField0_ &= -65;
                     this.operationBuilder_ = MysqlxCrud.Update.alwaysUseFieldBuilders ? this.getOperationFieldBuilder() : null;
                  } else {
                     this.operationBuilder_.addAllMessages(other.operation_);
                  }
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasCollection()) {
               return false;
            } else if (!this.getCollection().isInitialized()) {
               return false;
            } else if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
               return false;
            } else {
               int i;
               for(i = 0; i < this.getArgsCount(); ++i) {
                  if (!this.getArgs(i).isInitialized()) {
                     return false;
                  }
               }

               if (this.hasLimit() && !this.getLimit().isInitialized()) {
                  return false;
               } else {
                  for(i = 0; i < this.getOrderCount(); ++i) {
                     if (!this.getOrder(i).isInitialized()) {
                        return false;
                     }
                  }

                  for(i = 0; i < this.getOperationCount(); ++i) {
                     if (!this.getOperation(i).isInitialized()) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }

         public MysqlxCrud.Update.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Update parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Update)MysqlxCrud.Update.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Update)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasCollection() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxCrud.Collection getCollection() {
            return this.collectionBuilder_ == null ? this.collection_ : (MysqlxCrud.Collection)this.collectionBuilder_.getMessage();
         }

         public MysqlxCrud.Update.Builder setCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.collection_ = value;
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Update.Builder setCollection(MysqlxCrud.Collection.Builder builderForValue) {
            if (this.collectionBuilder_ == null) {
               this.collection_ = builderForValue.build();
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Update.Builder mergeCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.collection_ != MysqlxCrud.Collection.getDefaultInstance()) {
                  this.collection_ = MysqlxCrud.Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
               } else {
                  this.collection_ = value;
               }

               this.onChanged();
            } else {
               this.collectionBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Update.Builder clearCollection() {
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
               this.onChanged();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxCrud.Collection.Builder getCollectionBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxCrud.Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
         }

         public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
            return (MysqlxCrud.CollectionOrBuilder)(this.collectionBuilder_ != null ? (MysqlxCrud.CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder() : this.collection_);
         }

         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> getCollectionFieldBuilder() {
            if (this.collectionBuilder_ == null) {
               this.collectionBuilder_ = new SingleFieldBuilder(this.getCollection(), this.getParentForChildren(), this.isClean());
               this.collection_ = null;
            }

            return this.collectionBuilder_;
         }

         public boolean hasDataModel() {
            return (this.bitField0_ & 2) == 2;
         }

         public MysqlxCrud.DataModel getDataModel() {
            return this.dataModel_;
         }

         public MysqlxCrud.Update.Builder setDataModel(MysqlxCrud.DataModel value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.dataModel_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Update.Builder clearDataModel() {
            this.bitField0_ &= -3;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.onChanged();
            return this;
         }

         public boolean hasCriteria() {
            return (this.bitField0_ & 4) == 4;
         }

         public MysqlxExpr.Expr getCriteria() {
            return this.criteriaBuilder_ == null ? this.criteria_ : (MysqlxExpr.Expr)this.criteriaBuilder_.getMessage();
         }

         public MysqlxCrud.Update.Builder setCriteria(MysqlxExpr.Expr value) {
            if (this.criteriaBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.criteria_ = value;
               this.onChanged();
            } else {
               this.criteriaBuilder_.setMessage(value);
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.Update.Builder setCriteria(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = builderForValue.build();
               this.onChanged();
            } else {
               this.criteriaBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.Update.Builder mergeCriteria(MysqlxExpr.Expr value) {
            if (this.criteriaBuilder_ == null) {
               if ((this.bitField0_ & 4) == 4 && this.criteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.criteria_ = MysqlxExpr.Expr.newBuilder(this.criteria_).mergeFrom(value).buildPartial();
               } else {
                  this.criteria_ = value;
               }

               this.onChanged();
            } else {
               this.criteriaBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.Update.Builder clearCriteria() {
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.criteriaBuilder_.clear();
            }

            this.bitField0_ &= -5;
            return this;
         }

         public MysqlxExpr.Expr.Builder getCriteriaBuilder() {
            this.bitField0_ |= 4;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getCriteriaFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.criteriaBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.criteriaBuilder_.getMessageOrBuilder() : this.criteria_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getCriteriaFieldBuilder() {
            if (this.criteriaBuilder_ == null) {
               this.criteriaBuilder_ = new SingleFieldBuilder(this.getCriteria(), this.getParentForChildren(), this.isClean());
               this.criteria_ = null;
            }

            return this.criteriaBuilder_;
         }

         private void ensureArgsIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
               this.args_ = new ArrayList(this.args_);
               this.bitField0_ |= 8;
            }

         }

         public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.argsBuilder_ == null ? Collections.unmodifiableList(this.args_) : this.argsBuilder_.getMessageList();
         }

         public int getArgsCount() {
            return this.argsBuilder_ == null ? this.args_.size() : this.argsBuilder_.getCount();
         }

         public MysqlxDatatypes.Scalar getArgs(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.Scalar)this.args_.get(index) : (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
         }

         public MysqlxCrud.Update.Builder setArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.set(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder setArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addArgs(MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addArgs(MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addAllArgs(Iterable<? extends MysqlxDatatypes.Scalar> values) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.args_);
               this.onChanged();
            } else {
               this.argsBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder clearArgs() {
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -9;
               this.onChanged();
            } else {
               this.argsBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Update.Builder removeArgs(int index) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.remove(index);
               this.onChanged();
            } else {
               this.argsBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxDatatypes.Scalar.Builder getArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
         }

         public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index) : (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.argsBuilder_ != null ? this.argsBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.args_);
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
            return this.getArgsFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
            if (this.argsBuilder_ == null) {
               this.argsBuilder_ = new RepeatedFieldBuilder(this.args_, (this.bitField0_ & 8) == 8, this.getParentForChildren(), this.isClean());
               this.args_ = null;
            }

            return this.argsBuilder_;
         }

         public boolean hasLimit() {
            return (this.bitField0_ & 16) == 16;
         }

         public MysqlxCrud.Limit getLimit() {
            return this.limitBuilder_ == null ? this.limit_ : (MysqlxCrud.Limit)this.limitBuilder_.getMessage();
         }

         public MysqlxCrud.Update.Builder setLimit(MysqlxCrud.Limit value) {
            if (this.limitBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.limit_ = value;
               this.onChanged();
            } else {
               this.limitBuilder_.setMessage(value);
            }

            this.bitField0_ |= 16;
            return this;
         }

         public MysqlxCrud.Update.Builder setLimit(MysqlxCrud.Limit.Builder builderForValue) {
            if (this.limitBuilder_ == null) {
               this.limit_ = builderForValue.build();
               this.onChanged();
            } else {
               this.limitBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 16;
            return this;
         }

         public MysqlxCrud.Update.Builder mergeLimit(MysqlxCrud.Limit value) {
            if (this.limitBuilder_ == null) {
               if ((this.bitField0_ & 16) == 16 && this.limit_ != MysqlxCrud.Limit.getDefaultInstance()) {
                  this.limit_ = MysqlxCrud.Limit.newBuilder(this.limit_).mergeFrom(value).buildPartial();
               } else {
                  this.limit_ = value;
               }

               this.onChanged();
            } else {
               this.limitBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 16;
            return this;
         }

         public MysqlxCrud.Update.Builder clearLimit() {
            if (this.limitBuilder_ == null) {
               this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
               this.onChanged();
            } else {
               this.limitBuilder_.clear();
            }

            this.bitField0_ &= -17;
            return this;
         }

         public MysqlxCrud.Limit.Builder getLimitBuilder() {
            this.bitField0_ |= 16;
            this.onChanged();
            return (MysqlxCrud.Limit.Builder)this.getLimitFieldBuilder().getBuilder();
         }

         public MysqlxCrud.LimitOrBuilder getLimitOrBuilder() {
            return (MysqlxCrud.LimitOrBuilder)(this.limitBuilder_ != null ? (MysqlxCrud.LimitOrBuilder)this.limitBuilder_.getMessageOrBuilder() : this.limit_);
         }

         private SingleFieldBuilder<MysqlxCrud.Limit, MysqlxCrud.Limit.Builder, MysqlxCrud.LimitOrBuilder> getLimitFieldBuilder() {
            if (this.limitBuilder_ == null) {
               this.limitBuilder_ = new SingleFieldBuilder(this.getLimit(), this.getParentForChildren(), this.isClean());
               this.limit_ = null;
            }

            return this.limitBuilder_;
         }

         private void ensureOrderIsMutable() {
            if ((this.bitField0_ & 32) != 32) {
               this.order_ = new ArrayList(this.order_);
               this.bitField0_ |= 32;
            }

         }

         public List<MysqlxCrud.Order> getOrderList() {
            return this.orderBuilder_ == null ? Collections.unmodifiableList(this.order_) : this.orderBuilder_.getMessageList();
         }

         public int getOrderCount() {
            return this.orderBuilder_ == null ? this.order_.size() : this.orderBuilder_.getCount();
         }

         public MysqlxCrud.Order getOrder(int index) {
            return this.orderBuilder_ == null ? (MysqlxCrud.Order)this.order_.get(index) : (MysqlxCrud.Order)this.orderBuilder_.getMessage(index);
         }

         public MysqlxCrud.Update.Builder setOrder(int index, MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.set(index, value);
               this.onChanged();
            } else {
               this.orderBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder setOrder(int index, MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOrder(MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.add(value);
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOrder(int index, MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.add(index, value);
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOrder(MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOrder(int index, MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addAllOrder(Iterable<? extends MysqlxCrud.Order> values) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.order_);
               this.onChanged();
            } else {
               this.orderBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder clearOrder() {
            if (this.orderBuilder_ == null) {
               this.order_ = Collections.emptyList();
               this.bitField0_ &= -33;
               this.onChanged();
            } else {
               this.orderBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Update.Builder removeOrder(int index) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.remove(index);
               this.onChanged();
            } else {
               this.orderBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.Order.Builder getOrderBuilder(int index) {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int index) {
            return this.orderBuilder_ == null ? (MysqlxCrud.OrderOrBuilder)this.order_.get(index) : (MysqlxCrud.OrderOrBuilder)this.orderBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList() {
            return this.orderBuilder_ != null ? this.orderBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.order_);
         }

         public MysqlxCrud.Order.Builder addOrderBuilder() {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().addBuilder(MysqlxCrud.Order.getDefaultInstance());
         }

         public MysqlxCrud.Order.Builder addOrderBuilder(int index) {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().addBuilder(index, MysqlxCrud.Order.getDefaultInstance());
         }

         public List<MysqlxCrud.Order.Builder> getOrderBuilderList() {
            return this.getOrderFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.Order, MysqlxCrud.Order.Builder, MysqlxCrud.OrderOrBuilder> getOrderFieldBuilder() {
            if (this.orderBuilder_ == null) {
               this.orderBuilder_ = new RepeatedFieldBuilder(this.order_, (this.bitField0_ & 32) == 32, this.getParentForChildren(), this.isClean());
               this.order_ = null;
            }

            return this.orderBuilder_;
         }

         private void ensureOperationIsMutable() {
            if ((this.bitField0_ & 64) != 64) {
               this.operation_ = new ArrayList(this.operation_);
               this.bitField0_ |= 64;
            }

         }

         public List<MysqlxCrud.UpdateOperation> getOperationList() {
            return this.operationBuilder_ == null ? Collections.unmodifiableList(this.operation_) : this.operationBuilder_.getMessageList();
         }

         public int getOperationCount() {
            return this.operationBuilder_ == null ? this.operation_.size() : this.operationBuilder_.getCount();
         }

         public MysqlxCrud.UpdateOperation getOperation(int index) {
            return this.operationBuilder_ == null ? (MysqlxCrud.UpdateOperation)this.operation_.get(index) : (MysqlxCrud.UpdateOperation)this.operationBuilder_.getMessage(index);
         }

         public MysqlxCrud.Update.Builder setOperation(int index, MysqlxCrud.UpdateOperation value) {
            if (this.operationBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOperationIsMutable();
               this.operation_.set(index, value);
               this.onChanged();
            } else {
               this.operationBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder setOperation(int index, MysqlxCrud.UpdateOperation.Builder builderForValue) {
            if (this.operationBuilder_ == null) {
               this.ensureOperationIsMutable();
               this.operation_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.operationBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOperation(MysqlxCrud.UpdateOperation value) {
            if (this.operationBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOperationIsMutable();
               this.operation_.add(value);
               this.onChanged();
            } else {
               this.operationBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOperation(int index, MysqlxCrud.UpdateOperation value) {
            if (this.operationBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOperationIsMutable();
               this.operation_.add(index, value);
               this.onChanged();
            } else {
               this.operationBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOperation(MysqlxCrud.UpdateOperation.Builder builderForValue) {
            if (this.operationBuilder_ == null) {
               this.ensureOperationIsMutable();
               this.operation_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.operationBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addOperation(int index, MysqlxCrud.UpdateOperation.Builder builderForValue) {
            if (this.operationBuilder_ == null) {
               this.ensureOperationIsMutable();
               this.operation_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.operationBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Update.Builder addAllOperation(Iterable<? extends MysqlxCrud.UpdateOperation> values) {
            if (this.operationBuilder_ == null) {
               this.ensureOperationIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.operation_);
               this.onChanged();
            } else {
               this.operationBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Update.Builder clearOperation() {
            if (this.operationBuilder_ == null) {
               this.operation_ = Collections.emptyList();
               this.bitField0_ &= -65;
               this.onChanged();
            } else {
               this.operationBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Update.Builder removeOperation(int index) {
            if (this.operationBuilder_ == null) {
               this.ensureOperationIsMutable();
               this.operation_.remove(index);
               this.onChanged();
            } else {
               this.operationBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder getOperationBuilder(int index) {
            return (MysqlxCrud.UpdateOperation.Builder)this.getOperationFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.UpdateOperationOrBuilder getOperationOrBuilder(int index) {
            return this.operationBuilder_ == null ? (MysqlxCrud.UpdateOperationOrBuilder)this.operation_.get(index) : (MysqlxCrud.UpdateOperationOrBuilder)this.operationBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.UpdateOperationOrBuilder> getOperationOrBuilderList() {
            return this.operationBuilder_ != null ? this.operationBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.operation_);
         }

         public MysqlxCrud.UpdateOperation.Builder addOperationBuilder() {
            return (MysqlxCrud.UpdateOperation.Builder)this.getOperationFieldBuilder().addBuilder(MysqlxCrud.UpdateOperation.getDefaultInstance());
         }

         public MysqlxCrud.UpdateOperation.Builder addOperationBuilder(int index) {
            return (MysqlxCrud.UpdateOperation.Builder)this.getOperationFieldBuilder().addBuilder(index, MysqlxCrud.UpdateOperation.getDefaultInstance());
         }

         public List<MysqlxCrud.UpdateOperation.Builder> getOperationBuilderList() {
            return this.getOperationFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.UpdateOperation, MysqlxCrud.UpdateOperation.Builder, MysqlxCrud.UpdateOperationOrBuilder> getOperationFieldBuilder() {
            if (this.operationBuilder_ == null) {
               this.operationBuilder_ = new RepeatedFieldBuilder(this.operation_, (this.bitField0_ & 64) == 64, this.getParentForChildren(), this.isClean());
               this.operation_ = null;
            }

            return this.operationBuilder_;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface UpdateOrBuilder extends MessageOrBuilder {
      boolean hasCollection();

      MysqlxCrud.Collection getCollection();

      MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder();

      boolean hasDataModel();

      MysqlxCrud.DataModel getDataModel();

      boolean hasCriteria();

      MysqlxExpr.Expr getCriteria();

      MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder();

      List<MysqlxDatatypes.Scalar> getArgsList();

      MysqlxDatatypes.Scalar getArgs(int var1);

      int getArgsCount();

      List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();

      MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int var1);

      boolean hasLimit();

      MysqlxCrud.Limit getLimit();

      MysqlxCrud.LimitOrBuilder getLimitOrBuilder();

      List<MysqlxCrud.Order> getOrderList();

      MysqlxCrud.Order getOrder(int var1);

      int getOrderCount();

      List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList();

      MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int var1);

      List<MysqlxCrud.UpdateOperation> getOperationList();

      MysqlxCrud.UpdateOperation getOperation(int var1);

      int getOperationCount();

      List<? extends MysqlxCrud.UpdateOperationOrBuilder> getOperationOrBuilderList();

      MysqlxCrud.UpdateOperationOrBuilder getOperationOrBuilder(int var1);
   }

   public static final class Insert extends GeneratedMessage implements MysqlxCrud.InsertOrBuilder {
      private static final MysqlxCrud.Insert defaultInstance = new MysqlxCrud.Insert(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Insert> PARSER = new AbstractParser<MysqlxCrud.Insert>() {
         public MysqlxCrud.Insert parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Insert(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int COLLECTION_FIELD_NUMBER = 1;
      private MysqlxCrud.Collection collection_;
      public static final int DATA_MODEL_FIELD_NUMBER = 2;
      private MysqlxCrud.DataModel dataModel_;
      public static final int PROJECTION_FIELD_NUMBER = 3;
      private List<MysqlxCrud.Column> projection_;
      public static final int ROW_FIELD_NUMBER = 4;
      private List<MysqlxCrud.Insert.TypedRow> row_;
      public static final int ARGS_FIELD_NUMBER = 5;
      private List<MysqlxDatatypes.Scalar> args_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Insert(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Insert(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Insert getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Insert getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Insert(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = 0;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  MysqlxCrud.Collection.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.collection_.toBuilder();
                  }

                  this.collection_ = (MysqlxCrud.Collection)input.readMessage(MysqlxCrud.Collection.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.collection_);
                     this.collection_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 16:
                  int rawValue = input.readEnum();
                  MysqlxCrud.DataModel value = MysqlxCrud.DataModel.valueOf(rawValue);
                  if (value == null) {
                     unknownFields.mergeVarintField(2, rawValue);
                  } else {
                     this.bitField0_ |= 2;
                     this.dataModel_ = value;
                  }
                  break;
               case 26:
                  if ((mutable_bitField0_ & 4) != 4) {
                     this.projection_ = new ArrayList();
                     mutable_bitField0_ |= 4;
                  }

                  this.projection_.add(input.readMessage(MysqlxCrud.Column.PARSER, extensionRegistry));
                  break;
               case 34:
                  if ((mutable_bitField0_ & 8) != 8) {
                     this.row_ = new ArrayList();
                     mutable_bitField0_ |= 8;
                  }

                  this.row_.add(input.readMessage(MysqlxCrud.Insert.TypedRow.PARSER, extensionRegistry));
                  break;
               case 42:
                  if ((mutable_bitField0_ & 16) != 16) {
                     this.args_ = new ArrayList();
                     mutable_bitField0_ |= 16;
                  }

                  this.args_.add(input.readMessage(MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var13) {
            throw var13.setUnfinishedMessage(this);
         } catch (IOException var14) {
            throw (new InvalidProtocolBufferException(var14.getMessage())).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 4) == 4) {
               this.projection_ = Collections.unmodifiableList(this.projection_);
            }

            if ((mutable_bitField0_ & 8) == 8) {
               this.row_ = Collections.unmodifiableList(this.row_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.args_ = Collections.unmodifiableList(this.args_);
            }

            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Insert.class, MysqlxCrud.Insert.Builder.class);
      }

      public Parser<MysqlxCrud.Insert> getParserForType() {
         return PARSER;
      }

      public boolean hasCollection() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxCrud.Collection getCollection() {
         return this.collection_;
      }

      public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
         return this.collection_;
      }

      public boolean hasDataModel() {
         return (this.bitField0_ & 2) == 2;
      }

      public MysqlxCrud.DataModel getDataModel() {
         return this.dataModel_;
      }

      public List<MysqlxCrud.Column> getProjectionList() {
         return this.projection_;
      }

      public List<? extends MysqlxCrud.ColumnOrBuilder> getProjectionOrBuilderList() {
         return this.projection_;
      }

      public int getProjectionCount() {
         return this.projection_.size();
      }

      public MysqlxCrud.Column getProjection(int index) {
         return (MysqlxCrud.Column)this.projection_.get(index);
      }

      public MysqlxCrud.ColumnOrBuilder getProjectionOrBuilder(int index) {
         return (MysqlxCrud.ColumnOrBuilder)this.projection_.get(index);
      }

      public List<MysqlxCrud.Insert.TypedRow> getRowList() {
         return this.row_;
      }

      public List<? extends MysqlxCrud.Insert.TypedRowOrBuilder> getRowOrBuilderList() {
         return this.row_;
      }

      public int getRowCount() {
         return this.row_.size();
      }

      public MysqlxCrud.Insert.TypedRow getRow(int index) {
         return (MysqlxCrud.Insert.TypedRow)this.row_.get(index);
      }

      public MysqlxCrud.Insert.TypedRowOrBuilder getRowOrBuilder(int index) {
         return (MysqlxCrud.Insert.TypedRowOrBuilder)this.row_.get(index);
      }

      public List<MysqlxDatatypes.Scalar> getArgsList() {
         return this.args_;
      }

      public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
         return this.args_;
      }

      public int getArgsCount() {
         return this.args_.size();
      }

      public MysqlxDatatypes.Scalar getArgs(int index) {
         return (MysqlxDatatypes.Scalar)this.args_.get(index);
      }

      public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
         return (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index);
      }

      private void initFields() {
         this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
         this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
         this.projection_ = Collections.emptyList();
         this.row_ = Collections.emptyList();
         this.args_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasCollection()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getCollection().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            int i;
            for(i = 0; i < this.getProjectionCount(); ++i) {
               if (!this.getProjection(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            for(i = 0; i < this.getRowCount(); ++i) {
               if (!this.getRow(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            for(i = 0; i < this.getArgsCount(); ++i) {
               if (!this.getArgs(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.collection_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeEnum(2, this.dataModel_.getNumber());
         }

         int i;
         for(i = 0; i < this.projection_.size(); ++i) {
            output.writeMessage(3, (MessageLite)this.projection_.get(i));
         }

         for(i = 0; i < this.row_.size(); ++i) {
            output.writeMessage(4, (MessageLite)this.row_.get(i));
         }

         for(i = 0; i < this.args_.size(); ++i) {
            output.writeMessage(5, (MessageLite)this.args_.get(i));
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.collection_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeEnumSize(2, this.dataModel_.getNumber());
            }

            int i;
            for(i = 0; i < this.projection_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.projection_.get(i));
            }

            for(i = 0; i < this.row_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.row_.get(i));
            }

            for(i = 0; i < this.args_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.args_.get(i));
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Insert parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Insert parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Insert parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Insert parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Insert parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Insert parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Insert parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Insert)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Insert parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Insert)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Insert parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Insert parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Insert)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Insert.Builder newBuilder() {
         return MysqlxCrud.Insert.Builder.create();
      }

      public MysqlxCrud.Insert.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Insert.Builder newBuilder(MysqlxCrud.Insert prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Insert.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Insert.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Insert.Builder builder = new MysqlxCrud.Insert.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Insert(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Insert(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Insert.Builder> implements MysqlxCrud.InsertOrBuilder {
         private int bitField0_;
         private MysqlxCrud.Collection collection_;
         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> collectionBuilder_;
         private MysqlxCrud.DataModel dataModel_;
         private List<MysqlxCrud.Column> projection_;
         private RepeatedFieldBuilder<MysqlxCrud.Column, MysqlxCrud.Column.Builder, MysqlxCrud.ColumnOrBuilder> projectionBuilder_;
         private List<MysqlxCrud.Insert.TypedRow> row_;
         private RepeatedFieldBuilder<MysqlxCrud.Insert.TypedRow, MysqlxCrud.Insert.TypedRow.Builder, MysqlxCrud.Insert.TypedRowOrBuilder> rowBuilder_;
         private List<MysqlxDatatypes.Scalar> args_;
         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Insert.class, MysqlxCrud.Insert.Builder.class);
         }

         private Builder() {
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.projection_ = Collections.emptyList();
            this.row_ = Collections.emptyList();
            this.args_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.projection_ = Collections.emptyList();
            this.row_ = Collections.emptyList();
            this.args_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Insert.alwaysUseFieldBuilders) {
               this.getCollectionFieldBuilder();
               this.getProjectionFieldBuilder();
               this.getRowFieldBuilder();
               this.getArgsFieldBuilder();
            }

         }

         private static MysqlxCrud.Insert.Builder create() {
            return new MysqlxCrud.Insert.Builder();
         }

         public MysqlxCrud.Insert.Builder clear() {
            super.clear();
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.bitField0_ &= -3;
            if (this.projectionBuilder_ == null) {
               this.projection_ = Collections.emptyList();
               this.bitField0_ &= -5;
            } else {
               this.projectionBuilder_.clear();
            }

            if (this.rowBuilder_ == null) {
               this.row_ = Collections.emptyList();
               this.bitField0_ &= -9;
            } else {
               this.rowBuilder_.clear();
            }

            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -17;
            } else {
               this.argsBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor;
         }

         public MysqlxCrud.Insert getDefaultInstanceForType() {
            return MysqlxCrud.Insert.getDefaultInstance();
         }

         public MysqlxCrud.Insert build() {
            MysqlxCrud.Insert result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Insert buildPartial() {
            MysqlxCrud.Insert result = new MysqlxCrud.Insert(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.collectionBuilder_ == null) {
               result.collection_ = this.collection_;
            } else {
               result.collection_ = (MysqlxCrud.Collection)this.collectionBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.dataModel_ = this.dataModel_;
            if (this.projectionBuilder_ == null) {
               if ((this.bitField0_ & 4) == 4) {
                  this.projection_ = Collections.unmodifiableList(this.projection_);
                  this.bitField0_ &= -5;
               }

               result.projection_ = this.projection_;
            } else {
               result.projection_ = this.projectionBuilder_.build();
            }

            if (this.rowBuilder_ == null) {
               if ((this.bitField0_ & 8) == 8) {
                  this.row_ = Collections.unmodifiableList(this.row_);
                  this.bitField0_ &= -9;
               }

               result.row_ = this.row_;
            } else {
               result.row_ = this.rowBuilder_.build();
            }

            if (this.argsBuilder_ == null) {
               if ((this.bitField0_ & 16) == 16) {
                  this.args_ = Collections.unmodifiableList(this.args_);
                  this.bitField0_ &= -17;
               }

               result.args_ = this.args_;
            } else {
               result.args_ = this.argsBuilder_.build();
            }

            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Insert.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Insert) {
               return this.mergeFrom((MysqlxCrud.Insert)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Insert.Builder mergeFrom(MysqlxCrud.Insert other) {
            if (other == MysqlxCrud.Insert.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasCollection()) {
                  this.mergeCollection(other.getCollection());
               }

               if (other.hasDataModel()) {
                  this.setDataModel(other.getDataModel());
               }

               if (this.projectionBuilder_ == null) {
                  if (!other.projection_.isEmpty()) {
                     if (this.projection_.isEmpty()) {
                        this.projection_ = other.projection_;
                        this.bitField0_ &= -5;
                     } else {
                        this.ensureProjectionIsMutable();
                        this.projection_.addAll(other.projection_);
                     }

                     this.onChanged();
                  }
               } else if (!other.projection_.isEmpty()) {
                  if (this.projectionBuilder_.isEmpty()) {
                     this.projectionBuilder_.dispose();
                     this.projectionBuilder_ = null;
                     this.projection_ = other.projection_;
                     this.bitField0_ &= -5;
                     this.projectionBuilder_ = MysqlxCrud.Insert.alwaysUseFieldBuilders ? this.getProjectionFieldBuilder() : null;
                  } else {
                     this.projectionBuilder_.addAllMessages(other.projection_);
                  }
               }

               if (this.rowBuilder_ == null) {
                  if (!other.row_.isEmpty()) {
                     if (this.row_.isEmpty()) {
                        this.row_ = other.row_;
                        this.bitField0_ &= -9;
                     } else {
                        this.ensureRowIsMutable();
                        this.row_.addAll(other.row_);
                     }

                     this.onChanged();
                  }
               } else if (!other.row_.isEmpty()) {
                  if (this.rowBuilder_.isEmpty()) {
                     this.rowBuilder_.dispose();
                     this.rowBuilder_ = null;
                     this.row_ = other.row_;
                     this.bitField0_ &= -9;
                     this.rowBuilder_ = MysqlxCrud.Insert.alwaysUseFieldBuilders ? this.getRowFieldBuilder() : null;
                  } else {
                     this.rowBuilder_.addAllMessages(other.row_);
                  }
               }

               if (this.argsBuilder_ == null) {
                  if (!other.args_.isEmpty()) {
                     if (this.args_.isEmpty()) {
                        this.args_ = other.args_;
                        this.bitField0_ &= -17;
                     } else {
                        this.ensureArgsIsMutable();
                        this.args_.addAll(other.args_);
                     }

                     this.onChanged();
                  }
               } else if (!other.args_.isEmpty()) {
                  if (this.argsBuilder_.isEmpty()) {
                     this.argsBuilder_.dispose();
                     this.argsBuilder_ = null;
                     this.args_ = other.args_;
                     this.bitField0_ &= -17;
                     this.argsBuilder_ = MysqlxCrud.Insert.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null;
                  } else {
                     this.argsBuilder_.addAllMessages(other.args_);
                  }
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasCollection()) {
               return false;
            } else if (!this.getCollection().isInitialized()) {
               return false;
            } else {
               int i;
               for(i = 0; i < this.getProjectionCount(); ++i) {
                  if (!this.getProjection(i).isInitialized()) {
                     return false;
                  }
               }

               for(i = 0; i < this.getRowCount(); ++i) {
                  if (!this.getRow(i).isInitialized()) {
                     return false;
                  }
               }

               for(i = 0; i < this.getArgsCount(); ++i) {
                  if (!this.getArgs(i).isInitialized()) {
                     return false;
                  }
               }

               return true;
            }
         }

         public MysqlxCrud.Insert.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Insert parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Insert)MysqlxCrud.Insert.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Insert)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasCollection() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxCrud.Collection getCollection() {
            return this.collectionBuilder_ == null ? this.collection_ : (MysqlxCrud.Collection)this.collectionBuilder_.getMessage();
         }

         public MysqlxCrud.Insert.Builder setCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.collection_ = value;
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Insert.Builder setCollection(MysqlxCrud.Collection.Builder builderForValue) {
            if (this.collectionBuilder_ == null) {
               this.collection_ = builderForValue.build();
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Insert.Builder mergeCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.collection_ != MysqlxCrud.Collection.getDefaultInstance()) {
                  this.collection_ = MysqlxCrud.Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
               } else {
                  this.collection_ = value;
               }

               this.onChanged();
            } else {
               this.collectionBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Insert.Builder clearCollection() {
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
               this.onChanged();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxCrud.Collection.Builder getCollectionBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxCrud.Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
         }

         public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
            return (MysqlxCrud.CollectionOrBuilder)(this.collectionBuilder_ != null ? (MysqlxCrud.CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder() : this.collection_);
         }

         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> getCollectionFieldBuilder() {
            if (this.collectionBuilder_ == null) {
               this.collectionBuilder_ = new SingleFieldBuilder(this.getCollection(), this.getParentForChildren(), this.isClean());
               this.collection_ = null;
            }

            return this.collectionBuilder_;
         }

         public boolean hasDataModel() {
            return (this.bitField0_ & 2) == 2;
         }

         public MysqlxCrud.DataModel getDataModel() {
            return this.dataModel_;
         }

         public MysqlxCrud.Insert.Builder setDataModel(MysqlxCrud.DataModel value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.dataModel_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Insert.Builder clearDataModel() {
            this.bitField0_ &= -3;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.onChanged();
            return this;
         }

         private void ensureProjectionIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.projection_ = new ArrayList(this.projection_);
               this.bitField0_ |= 4;
            }

         }

         public List<MysqlxCrud.Column> getProjectionList() {
            return this.projectionBuilder_ == null ? Collections.unmodifiableList(this.projection_) : this.projectionBuilder_.getMessageList();
         }

         public int getProjectionCount() {
            return this.projectionBuilder_ == null ? this.projection_.size() : this.projectionBuilder_.getCount();
         }

         public MysqlxCrud.Column getProjection(int index) {
            return this.projectionBuilder_ == null ? (MysqlxCrud.Column)this.projection_.get(index) : (MysqlxCrud.Column)this.projectionBuilder_.getMessage(index);
         }

         public MysqlxCrud.Insert.Builder setProjection(int index, MysqlxCrud.Column value) {
            if (this.projectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureProjectionIsMutable();
               this.projection_.set(index, value);
               this.onChanged();
            } else {
               this.projectionBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder setProjection(int index, MysqlxCrud.Column.Builder builderForValue) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.projectionBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addProjection(MysqlxCrud.Column value) {
            if (this.projectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureProjectionIsMutable();
               this.projection_.add(value);
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addProjection(int index, MysqlxCrud.Column value) {
            if (this.projectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureProjectionIsMutable();
               this.projection_.add(index, value);
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addProjection(MysqlxCrud.Column.Builder builderForValue) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addProjection(int index, MysqlxCrud.Column.Builder builderForValue) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addAllProjection(Iterable<? extends MysqlxCrud.Column> values) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.projection_);
               this.onChanged();
            } else {
               this.projectionBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder clearProjection() {
            if (this.projectionBuilder_ == null) {
               this.projection_ = Collections.emptyList();
               this.bitField0_ &= -5;
               this.onChanged();
            } else {
               this.projectionBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder removeProjection(int index) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.remove(index);
               this.onChanged();
            } else {
               this.projectionBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.Column.Builder getProjectionBuilder(int index) {
            return (MysqlxCrud.Column.Builder)this.getProjectionFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.ColumnOrBuilder getProjectionOrBuilder(int index) {
            return this.projectionBuilder_ == null ? (MysqlxCrud.ColumnOrBuilder)this.projection_.get(index) : (MysqlxCrud.ColumnOrBuilder)this.projectionBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.ColumnOrBuilder> getProjectionOrBuilderList() {
            return this.projectionBuilder_ != null ? this.projectionBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.projection_);
         }

         public MysqlxCrud.Column.Builder addProjectionBuilder() {
            return (MysqlxCrud.Column.Builder)this.getProjectionFieldBuilder().addBuilder(MysqlxCrud.Column.getDefaultInstance());
         }

         public MysqlxCrud.Column.Builder addProjectionBuilder(int index) {
            return (MysqlxCrud.Column.Builder)this.getProjectionFieldBuilder().addBuilder(index, MysqlxCrud.Column.getDefaultInstance());
         }

         public List<MysqlxCrud.Column.Builder> getProjectionBuilderList() {
            return this.getProjectionFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.Column, MysqlxCrud.Column.Builder, MysqlxCrud.ColumnOrBuilder> getProjectionFieldBuilder() {
            if (this.projectionBuilder_ == null) {
               this.projectionBuilder_ = new RepeatedFieldBuilder(this.projection_, (this.bitField0_ & 4) == 4, this.getParentForChildren(), this.isClean());
               this.projection_ = null;
            }

            return this.projectionBuilder_;
         }

         private void ensureRowIsMutable() {
            if ((this.bitField0_ & 8) != 8) {
               this.row_ = new ArrayList(this.row_);
               this.bitField0_ |= 8;
            }

         }

         public List<MysqlxCrud.Insert.TypedRow> getRowList() {
            return this.rowBuilder_ == null ? Collections.unmodifiableList(this.row_) : this.rowBuilder_.getMessageList();
         }

         public int getRowCount() {
            return this.rowBuilder_ == null ? this.row_.size() : this.rowBuilder_.getCount();
         }

         public MysqlxCrud.Insert.TypedRow getRow(int index) {
            return this.rowBuilder_ == null ? (MysqlxCrud.Insert.TypedRow)this.row_.get(index) : (MysqlxCrud.Insert.TypedRow)this.rowBuilder_.getMessage(index);
         }

         public MysqlxCrud.Insert.Builder setRow(int index, MysqlxCrud.Insert.TypedRow value) {
            if (this.rowBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureRowIsMutable();
               this.row_.set(index, value);
               this.onChanged();
            } else {
               this.rowBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder setRow(int index, MysqlxCrud.Insert.TypedRow.Builder builderForValue) {
            if (this.rowBuilder_ == null) {
               this.ensureRowIsMutable();
               this.row_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.rowBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addRow(MysqlxCrud.Insert.TypedRow value) {
            if (this.rowBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureRowIsMutable();
               this.row_.add(value);
               this.onChanged();
            } else {
               this.rowBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addRow(int index, MysqlxCrud.Insert.TypedRow value) {
            if (this.rowBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureRowIsMutable();
               this.row_.add(index, value);
               this.onChanged();
            } else {
               this.rowBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addRow(MysqlxCrud.Insert.TypedRow.Builder builderForValue) {
            if (this.rowBuilder_ == null) {
               this.ensureRowIsMutable();
               this.row_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.rowBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addRow(int index, MysqlxCrud.Insert.TypedRow.Builder builderForValue) {
            if (this.rowBuilder_ == null) {
               this.ensureRowIsMutable();
               this.row_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.rowBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addAllRow(Iterable<? extends MysqlxCrud.Insert.TypedRow> values) {
            if (this.rowBuilder_ == null) {
               this.ensureRowIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.row_);
               this.onChanged();
            } else {
               this.rowBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder clearRow() {
            if (this.rowBuilder_ == null) {
               this.row_ = Collections.emptyList();
               this.bitField0_ &= -9;
               this.onChanged();
            } else {
               this.rowBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder removeRow(int index) {
            if (this.rowBuilder_ == null) {
               this.ensureRowIsMutable();
               this.row_.remove(index);
               this.onChanged();
            } else {
               this.rowBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.Insert.TypedRow.Builder getRowBuilder(int index) {
            return (MysqlxCrud.Insert.TypedRow.Builder)this.getRowFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.Insert.TypedRowOrBuilder getRowOrBuilder(int index) {
            return this.rowBuilder_ == null ? (MysqlxCrud.Insert.TypedRowOrBuilder)this.row_.get(index) : (MysqlxCrud.Insert.TypedRowOrBuilder)this.rowBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.Insert.TypedRowOrBuilder> getRowOrBuilderList() {
            return this.rowBuilder_ != null ? this.rowBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.row_);
         }

         public MysqlxCrud.Insert.TypedRow.Builder addRowBuilder() {
            return (MysqlxCrud.Insert.TypedRow.Builder)this.getRowFieldBuilder().addBuilder(MysqlxCrud.Insert.TypedRow.getDefaultInstance());
         }

         public MysqlxCrud.Insert.TypedRow.Builder addRowBuilder(int index) {
            return (MysqlxCrud.Insert.TypedRow.Builder)this.getRowFieldBuilder().addBuilder(index, MysqlxCrud.Insert.TypedRow.getDefaultInstance());
         }

         public List<MysqlxCrud.Insert.TypedRow.Builder> getRowBuilderList() {
            return this.getRowFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.Insert.TypedRow, MysqlxCrud.Insert.TypedRow.Builder, MysqlxCrud.Insert.TypedRowOrBuilder> getRowFieldBuilder() {
            if (this.rowBuilder_ == null) {
               this.rowBuilder_ = new RepeatedFieldBuilder(this.row_, (this.bitField0_ & 8) == 8, this.getParentForChildren(), this.isClean());
               this.row_ = null;
            }

            return this.rowBuilder_;
         }

         private void ensureArgsIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.args_ = new ArrayList(this.args_);
               this.bitField0_ |= 16;
            }

         }

         public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.argsBuilder_ == null ? Collections.unmodifiableList(this.args_) : this.argsBuilder_.getMessageList();
         }

         public int getArgsCount() {
            return this.argsBuilder_ == null ? this.args_.size() : this.argsBuilder_.getCount();
         }

         public MysqlxDatatypes.Scalar getArgs(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.Scalar)this.args_.get(index) : (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
         }

         public MysqlxCrud.Insert.Builder setArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.set(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder setArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addArgs(MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addArgs(MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder addAllArgs(Iterable<? extends MysqlxDatatypes.Scalar> values) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.args_);
               this.onChanged();
            } else {
               this.argsBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder clearArgs() {
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -17;
               this.onChanged();
            } else {
               this.argsBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Insert.Builder removeArgs(int index) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.remove(index);
               this.onChanged();
            } else {
               this.argsBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxDatatypes.Scalar.Builder getArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
         }

         public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index) : (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.argsBuilder_ != null ? this.argsBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.args_);
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
            return this.getArgsFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
            if (this.argsBuilder_ == null) {
               this.argsBuilder_ = new RepeatedFieldBuilder(this.args_, (this.bitField0_ & 16) == 16, this.getParentForChildren(), this.isClean());
               this.args_ = null;
            }

            return this.argsBuilder_;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }

      public static final class TypedRow extends GeneratedMessage implements MysqlxCrud.Insert.TypedRowOrBuilder {
         private static final MysqlxCrud.Insert.TypedRow defaultInstance = new MysqlxCrud.Insert.TypedRow(true);
         private final UnknownFieldSet unknownFields;
         public static Parser<MysqlxCrud.Insert.TypedRow> PARSER = new AbstractParser<MysqlxCrud.Insert.TypedRow>() {
            public MysqlxCrud.Insert.TypedRow parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
               return new MysqlxCrud.Insert.TypedRow(input, extensionRegistry);
            }
         };
         public static final int FIELD_FIELD_NUMBER = 1;
         private List<MysqlxExpr.Expr> field_;
         private byte memoizedIsInitialized;
         private int memoizedSerializedSize;
         private static final long serialVersionUID = 0L;

         private TypedRow(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
         }

         private TypedRow(boolean noInit) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
         }

         public static MysqlxCrud.Insert.TypedRow getDefaultInstance() {
            return defaultInstance;
         }

         public MysqlxCrud.Insert.TypedRow getDefaultInstanceForType() {
            return defaultInstance;
         }

         public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
         }

         private TypedRow(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            int mutable_bitField0_ = false;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

            try {
               boolean done = false;

               while(!done) {
                  int tag = input.readTag();
                  switch(tag) {
                  case 0:
                     done = true;
                     break;
                  case 10:
                     if (!(mutable_bitField0_ & true)) {
                        this.field_ = new ArrayList();
                        mutable_bitField0_ |= true;
                     }

                     this.field_.add(input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry));
                     break;
                  default:
                     if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                        done = true;
                     }
                  }
               }
            } catch (InvalidProtocolBufferException var11) {
               throw var11.setUnfinishedMessage(this);
            } catch (IOException var12) {
               throw (new InvalidProtocolBufferException(var12.getMessage())).setUnfinishedMessage(this);
            } finally {
               if (mutable_bitField0_ & true) {
                  this.field_ = Collections.unmodifiableList(this.field_);
               }

               this.unknownFields = unknownFields.build();
               this.makeExtensionsImmutable();
            }

         }

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Insert.TypedRow.class, MysqlxCrud.Insert.TypedRow.Builder.class);
         }

         public Parser<MysqlxCrud.Insert.TypedRow> getParserForType() {
            return PARSER;
         }

         public List<MysqlxExpr.Expr> getFieldList() {
            return this.field_;
         }

         public List<? extends MysqlxExpr.ExprOrBuilder> getFieldOrBuilderList() {
            return this.field_;
         }

         public int getFieldCount() {
            return this.field_.size();
         }

         public MysqlxExpr.Expr getField(int index) {
            return (MysqlxExpr.Expr)this.field_.get(index);
         }

         public MysqlxExpr.ExprOrBuilder getFieldOrBuilder(int index) {
            return (MysqlxExpr.ExprOrBuilder)this.field_.get(index);
         }

         private void initFields() {
            this.field_ = Collections.emptyList();
         }

         public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
               return true;
            } else if (isInitialized == 0) {
               return false;
            } else {
               for(int i = 0; i < this.getFieldCount(); ++i) {
                  if (!this.getField(i).isInitialized()) {
                     this.memoizedIsInitialized = 0;
                     return false;
                  }
               }

               this.memoizedIsInitialized = 1;
               return true;
            }
         }

         public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();

            for(int i = 0; i < this.field_.size(); ++i) {
               output.writeMessage(1, (MessageLite)this.field_.get(i));
            }

            this.getUnknownFields().writeTo(output);
         }

         public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
               return size;
            } else {
               size = 0;

               for(int i = 0; i < this.field_.size(); ++i) {
                  size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.field_.get(i));
               }

               size += this.getUnknownFields().getSerializedSize();
               this.memoizedSerializedSize = size;
               return size;
            }
         }

         protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(data);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(data, extensionRegistry);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(data);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(data, extensionRegistry);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(InputStream input) throws IOException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(input);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(input, extensionRegistry);
         }

         public static MysqlxCrud.Insert.TypedRow parseDelimitedFrom(InputStream input) throws IOException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseDelimitedFrom(input);
         }

         public static MysqlxCrud.Insert.TypedRow parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseDelimitedFrom(input, extensionRegistry);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(CodedInputStream input) throws IOException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(input);
         }

         public static MysqlxCrud.Insert.TypedRow parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MysqlxCrud.Insert.TypedRow)PARSER.parseFrom(input, extensionRegistry);
         }

         public static MysqlxCrud.Insert.TypedRow.Builder newBuilder() {
            return MysqlxCrud.Insert.TypedRow.Builder.create();
         }

         public MysqlxCrud.Insert.TypedRow.Builder newBuilderForType() {
            return newBuilder();
         }

         public static MysqlxCrud.Insert.TypedRow.Builder newBuilder(MysqlxCrud.Insert.TypedRow prototype) {
            return newBuilder().mergeFrom(prototype);
         }

         public MysqlxCrud.Insert.TypedRow.Builder toBuilder() {
            return newBuilder(this);
         }

         protected MysqlxCrud.Insert.TypedRow.Builder newBuilderForType(BuilderParent parent) {
            MysqlxCrud.Insert.TypedRow.Builder builder = new MysqlxCrud.Insert.TypedRow.Builder(parent);
            return builder;
         }

         // $FF: synthetic method
         TypedRow(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
            this(x0, x1);
         }

         // $FF: synthetic method
         TypedRow(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
            this(x0);
         }

         static {
            defaultInstance.initFields();
         }

         public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Insert.TypedRow.Builder> implements MysqlxCrud.Insert.TypedRowOrBuilder {
            private int bitField0_;
            private List<MysqlxExpr.Expr> field_;
            private RepeatedFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> fieldBuilder_;

            public static final Descriptor getDescriptor() {
               return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
               return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Insert.TypedRow.class, MysqlxCrud.Insert.TypedRow.Builder.class);
            }

            private Builder() {
               this.field_ = Collections.emptyList();
               this.maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
               super(parent);
               this.field_ = Collections.emptyList();
               this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
               if (MysqlxCrud.Insert.TypedRow.alwaysUseFieldBuilders) {
                  this.getFieldFieldBuilder();
               }

            }

            private static MysqlxCrud.Insert.TypedRow.Builder create() {
               return new MysqlxCrud.Insert.TypedRow.Builder();
            }

            public MysqlxCrud.Insert.TypedRow.Builder clear() {
               super.clear();
               if (this.fieldBuilder_ == null) {
                  this.field_ = Collections.emptyList();
                  this.bitField0_ &= -2;
               } else {
                  this.fieldBuilder_.clear();
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder clone() {
               return create().mergeFrom(this.buildPartial());
            }

            public Descriptor getDescriptorForType() {
               return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
            }

            public MysqlxCrud.Insert.TypedRow getDefaultInstanceForType() {
               return MysqlxCrud.Insert.TypedRow.getDefaultInstance();
            }

            public MysqlxCrud.Insert.TypedRow build() {
               MysqlxCrud.Insert.TypedRow result = this.buildPartial();
               if (!result.isInitialized()) {
                  throw newUninitializedMessageException(result);
               } else {
                  return result;
               }
            }

            public MysqlxCrud.Insert.TypedRow buildPartial() {
               MysqlxCrud.Insert.TypedRow result = new MysqlxCrud.Insert.TypedRow(this);
               int from_bitField0_ = this.bitField0_;
               if (this.fieldBuilder_ == null) {
                  if ((this.bitField0_ & 1) == 1) {
                     this.field_ = Collections.unmodifiableList(this.field_);
                     this.bitField0_ &= -2;
                  }

                  result.field_ = this.field_;
               } else {
                  result.field_ = this.fieldBuilder_.build();
               }

               this.onBuilt();
               return result;
            }

            public MysqlxCrud.Insert.TypedRow.Builder mergeFrom(Message other) {
               if (other instanceof MysqlxCrud.Insert.TypedRow) {
                  return this.mergeFrom((MysqlxCrud.Insert.TypedRow)other);
               } else {
                  super.mergeFrom(other);
                  return this;
               }
            }

            public MysqlxCrud.Insert.TypedRow.Builder mergeFrom(MysqlxCrud.Insert.TypedRow other) {
               if (other == MysqlxCrud.Insert.TypedRow.getDefaultInstance()) {
                  return this;
               } else {
                  if (this.fieldBuilder_ == null) {
                     if (!other.field_.isEmpty()) {
                        if (this.field_.isEmpty()) {
                           this.field_ = other.field_;
                           this.bitField0_ &= -2;
                        } else {
                           this.ensureFieldIsMutable();
                           this.field_.addAll(other.field_);
                        }

                        this.onChanged();
                     }
                  } else if (!other.field_.isEmpty()) {
                     if (this.fieldBuilder_.isEmpty()) {
                        this.fieldBuilder_.dispose();
                        this.fieldBuilder_ = null;
                        this.field_ = other.field_;
                        this.bitField0_ &= -2;
                        this.fieldBuilder_ = MysqlxCrud.Insert.TypedRow.alwaysUseFieldBuilders ? this.getFieldFieldBuilder() : null;
                     } else {
                        this.fieldBuilder_.addAllMessages(other.field_);
                     }
                  }

                  this.mergeUnknownFields(other.getUnknownFields());
                  return this;
               }
            }

            public final boolean isInitialized() {
               for(int i = 0; i < this.getFieldCount(); ++i) {
                  if (!this.getField(i).isInitialized()) {
                     return false;
                  }
               }

               return true;
            }

            public MysqlxCrud.Insert.TypedRow.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
               MysqlxCrud.Insert.TypedRow parsedMessage = null;

               try {
                  parsedMessage = (MysqlxCrud.Insert.TypedRow)MysqlxCrud.Insert.TypedRow.PARSER.parsePartialFrom(input, extensionRegistry);
               } catch (InvalidProtocolBufferException var8) {
                  parsedMessage = (MysqlxCrud.Insert.TypedRow)var8.getUnfinishedMessage();
                  throw var8;
               } finally {
                  if (parsedMessage != null) {
                     this.mergeFrom(parsedMessage);
                  }

               }

               return this;
            }

            private void ensureFieldIsMutable() {
               if ((this.bitField0_ & 1) != 1) {
                  this.field_ = new ArrayList(this.field_);
                  this.bitField0_ |= 1;
               }

            }

            public List<MysqlxExpr.Expr> getFieldList() {
               return this.fieldBuilder_ == null ? Collections.unmodifiableList(this.field_) : this.fieldBuilder_.getMessageList();
            }

            public int getFieldCount() {
               return this.fieldBuilder_ == null ? this.field_.size() : this.fieldBuilder_.getCount();
            }

            public MysqlxExpr.Expr getField(int index) {
               return this.fieldBuilder_ == null ? (MysqlxExpr.Expr)this.field_.get(index) : (MysqlxExpr.Expr)this.fieldBuilder_.getMessage(index);
            }

            public MysqlxCrud.Insert.TypedRow.Builder setField(int index, MysqlxExpr.Expr value) {
               if (this.fieldBuilder_ == null) {
                  if (value == null) {
                     throw new NullPointerException();
                  }

                  this.ensureFieldIsMutable();
                  this.field_.set(index, value);
                  this.onChanged();
               } else {
                  this.fieldBuilder_.setMessage(index, value);
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder setField(int index, MysqlxExpr.Expr.Builder builderForValue) {
               if (this.fieldBuilder_ == null) {
                  this.ensureFieldIsMutable();
                  this.field_.set(index, builderForValue.build());
                  this.onChanged();
               } else {
                  this.fieldBuilder_.setMessage(index, builderForValue.build());
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder addField(MysqlxExpr.Expr value) {
               if (this.fieldBuilder_ == null) {
                  if (value == null) {
                     throw new NullPointerException();
                  }

                  this.ensureFieldIsMutable();
                  this.field_.add(value);
                  this.onChanged();
               } else {
                  this.fieldBuilder_.addMessage(value);
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder addField(int index, MysqlxExpr.Expr value) {
               if (this.fieldBuilder_ == null) {
                  if (value == null) {
                     throw new NullPointerException();
                  }

                  this.ensureFieldIsMutable();
                  this.field_.add(index, value);
                  this.onChanged();
               } else {
                  this.fieldBuilder_.addMessage(index, value);
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder addField(MysqlxExpr.Expr.Builder builderForValue) {
               if (this.fieldBuilder_ == null) {
                  this.ensureFieldIsMutable();
                  this.field_.add(builderForValue.build());
                  this.onChanged();
               } else {
                  this.fieldBuilder_.addMessage(builderForValue.build());
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder addField(int index, MysqlxExpr.Expr.Builder builderForValue) {
               if (this.fieldBuilder_ == null) {
                  this.ensureFieldIsMutable();
                  this.field_.add(index, builderForValue.build());
                  this.onChanged();
               } else {
                  this.fieldBuilder_.addMessage(index, builderForValue.build());
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder addAllField(Iterable<? extends MysqlxExpr.Expr> values) {
               if (this.fieldBuilder_ == null) {
                  this.ensureFieldIsMutable();
                  com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.field_);
                  this.onChanged();
               } else {
                  this.fieldBuilder_.addAllMessages(values);
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder clearField() {
               if (this.fieldBuilder_ == null) {
                  this.field_ = Collections.emptyList();
                  this.bitField0_ &= -2;
                  this.onChanged();
               } else {
                  this.fieldBuilder_.clear();
               }

               return this;
            }

            public MysqlxCrud.Insert.TypedRow.Builder removeField(int index) {
               if (this.fieldBuilder_ == null) {
                  this.ensureFieldIsMutable();
                  this.field_.remove(index);
                  this.onChanged();
               } else {
                  this.fieldBuilder_.remove(index);
               }

               return this;
            }

            public MysqlxExpr.Expr.Builder getFieldBuilder(int index) {
               return (MysqlxExpr.Expr.Builder)this.getFieldFieldBuilder().getBuilder(index);
            }

            public MysqlxExpr.ExprOrBuilder getFieldOrBuilder(int index) {
               return this.fieldBuilder_ == null ? (MysqlxExpr.ExprOrBuilder)this.field_.get(index) : (MysqlxExpr.ExprOrBuilder)this.fieldBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends MysqlxExpr.ExprOrBuilder> getFieldOrBuilderList() {
               return this.fieldBuilder_ != null ? this.fieldBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.field_);
            }

            public MysqlxExpr.Expr.Builder addFieldBuilder() {
               return (MysqlxExpr.Expr.Builder)this.getFieldFieldBuilder().addBuilder(MysqlxExpr.Expr.getDefaultInstance());
            }

            public MysqlxExpr.Expr.Builder addFieldBuilder(int index) {
               return (MysqlxExpr.Expr.Builder)this.getFieldFieldBuilder().addBuilder(index, MysqlxExpr.Expr.getDefaultInstance());
            }

            public List<MysqlxExpr.Expr.Builder> getFieldBuilderList() {
               return this.getFieldFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getFieldFieldBuilder() {
               if (this.fieldBuilder_ == null) {
                  this.fieldBuilder_ = new RepeatedFieldBuilder(this.field_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                  this.field_ = null;
               }

               return this.fieldBuilder_;
            }

            // $FF: synthetic method
            Builder(BuilderParent x0, Object x1) {
               this(x0);
            }
         }
      }

      public interface TypedRowOrBuilder extends MessageOrBuilder {
         List<MysqlxExpr.Expr> getFieldList();

         MysqlxExpr.Expr getField(int var1);

         int getFieldCount();

         List<? extends MysqlxExpr.ExprOrBuilder> getFieldOrBuilderList();

         MysqlxExpr.ExprOrBuilder getFieldOrBuilder(int var1);
      }
   }

   public interface InsertOrBuilder extends MessageOrBuilder {
      boolean hasCollection();

      MysqlxCrud.Collection getCollection();

      MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder();

      boolean hasDataModel();

      MysqlxCrud.DataModel getDataModel();

      List<MysqlxCrud.Column> getProjectionList();

      MysqlxCrud.Column getProjection(int var1);

      int getProjectionCount();

      List<? extends MysqlxCrud.ColumnOrBuilder> getProjectionOrBuilderList();

      MysqlxCrud.ColumnOrBuilder getProjectionOrBuilder(int var1);

      List<MysqlxCrud.Insert.TypedRow> getRowList();

      MysqlxCrud.Insert.TypedRow getRow(int var1);

      int getRowCount();

      List<? extends MysqlxCrud.Insert.TypedRowOrBuilder> getRowOrBuilderList();

      MysqlxCrud.Insert.TypedRowOrBuilder getRowOrBuilder(int var1);

      List<MysqlxDatatypes.Scalar> getArgsList();

      MysqlxDatatypes.Scalar getArgs(int var1);

      int getArgsCount();

      List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();

      MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int var1);
   }

   public static final class Find extends GeneratedMessage implements MysqlxCrud.FindOrBuilder {
      private static final MysqlxCrud.Find defaultInstance = new MysqlxCrud.Find(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Find> PARSER = new AbstractParser<MysqlxCrud.Find>() {
         public MysqlxCrud.Find parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Find(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int COLLECTION_FIELD_NUMBER = 2;
      private MysqlxCrud.Collection collection_;
      public static final int DATA_MODEL_FIELD_NUMBER = 3;
      private MysqlxCrud.DataModel dataModel_;
      public static final int PROJECTION_FIELD_NUMBER = 4;
      private List<MysqlxCrud.Projection> projection_;
      public static final int CRITERIA_FIELD_NUMBER = 5;
      private MysqlxExpr.Expr criteria_;
      public static final int ARGS_FIELD_NUMBER = 11;
      private List<MysqlxDatatypes.Scalar> args_;
      public static final int LIMIT_FIELD_NUMBER = 6;
      private MysqlxCrud.Limit limit_;
      public static final int ORDER_FIELD_NUMBER = 7;
      private List<MysqlxCrud.Order> order_;
      public static final int GROUPING_FIELD_NUMBER = 8;
      private List<MysqlxExpr.Expr> grouping_;
      public static final int GROUPING_CRITERIA_FIELD_NUMBER = 9;
      private MysqlxExpr.Expr groupingCriteria_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Find(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Find(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Find getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Find getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Find(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = 0;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               MysqlxExpr.Expr.Builder subBuilder;
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 18:
                  MysqlxCrud.Collection.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.collection_.toBuilder();
                  }

                  this.collection_ = (MysqlxCrud.Collection)input.readMessage(MysqlxCrud.Collection.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.collection_);
                     this.collection_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 24:
                  int rawValue = input.readEnum();
                  MysqlxCrud.DataModel value = MysqlxCrud.DataModel.valueOf(rawValue);
                  if (value == null) {
                     unknownFields.mergeVarintField(3, rawValue);
                  } else {
                     this.bitField0_ |= 2;
                     this.dataModel_ = value;
                  }
                  break;
               case 34:
                  if ((mutable_bitField0_ & 4) != 4) {
                     this.projection_ = new ArrayList();
                     mutable_bitField0_ |= 4;
                  }

                  this.projection_.add(input.readMessage(MysqlxCrud.Projection.PARSER, extensionRegistry));
                  break;
               case 42:
                  subBuilder = null;
                  if ((this.bitField0_ & 4) == 4) {
                     subBuilder = this.criteria_.toBuilder();
                  }

                  this.criteria_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.criteria_);
                     this.criteria_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 4;
                  break;
               case 50:
                  MysqlxCrud.Limit.Builder subBuilder = null;
                  if ((this.bitField0_ & 8) == 8) {
                     subBuilder = this.limit_.toBuilder();
                  }

                  this.limit_ = (MysqlxCrud.Limit)input.readMessage(MysqlxCrud.Limit.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.limit_);
                     this.limit_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 8;
                  break;
               case 58:
                  if ((mutable_bitField0_ & 64) != 64) {
                     this.order_ = new ArrayList();
                     mutable_bitField0_ |= 64;
                  }

                  this.order_.add(input.readMessage(MysqlxCrud.Order.PARSER, extensionRegistry));
                  break;
               case 66:
                  if ((mutable_bitField0_ & 128) != 128) {
                     this.grouping_ = new ArrayList();
                     mutable_bitField0_ |= 128;
                  }

                  this.grouping_.add(input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry));
                  break;
               case 74:
                  subBuilder = null;
                  if ((this.bitField0_ & 16) == 16) {
                     subBuilder = this.groupingCriteria_.toBuilder();
                  }

                  this.groupingCriteria_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.groupingCriteria_);
                     this.groupingCriteria_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 16;
                  break;
               case 90:
                  if ((mutable_bitField0_ & 16) != 16) {
                     this.args_ = new ArrayList();
                     mutable_bitField0_ |= 16;
                  }

                  this.args_.add(input.readMessage(MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var13) {
            throw var13.setUnfinishedMessage(this);
         } catch (IOException var14) {
            throw (new InvalidProtocolBufferException(var14.getMessage())).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 4) == 4) {
               this.projection_ = Collections.unmodifiableList(this.projection_);
            }

            if ((mutable_bitField0_ & 64) == 64) {
               this.order_ = Collections.unmodifiableList(this.order_);
            }

            if ((mutable_bitField0_ & 128) == 128) {
               this.grouping_ = Collections.unmodifiableList(this.grouping_);
            }

            if ((mutable_bitField0_ & 16) == 16) {
               this.args_ = Collections.unmodifiableList(this.args_);
            }

            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Find_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Find.class, MysqlxCrud.Find.Builder.class);
      }

      public Parser<MysqlxCrud.Find> getParserForType() {
         return PARSER;
      }

      public boolean hasCollection() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxCrud.Collection getCollection() {
         return this.collection_;
      }

      public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
         return this.collection_;
      }

      public boolean hasDataModel() {
         return (this.bitField0_ & 2) == 2;
      }

      public MysqlxCrud.DataModel getDataModel() {
         return this.dataModel_;
      }

      public List<MysqlxCrud.Projection> getProjectionList() {
         return this.projection_;
      }

      public List<? extends MysqlxCrud.ProjectionOrBuilder> getProjectionOrBuilderList() {
         return this.projection_;
      }

      public int getProjectionCount() {
         return this.projection_.size();
      }

      public MysqlxCrud.Projection getProjection(int index) {
         return (MysqlxCrud.Projection)this.projection_.get(index);
      }

      public MysqlxCrud.ProjectionOrBuilder getProjectionOrBuilder(int index) {
         return (MysqlxCrud.ProjectionOrBuilder)this.projection_.get(index);
      }

      public boolean hasCriteria() {
         return (this.bitField0_ & 4) == 4;
      }

      public MysqlxExpr.Expr getCriteria() {
         return this.criteria_;
      }

      public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
         return this.criteria_;
      }

      public List<MysqlxDatatypes.Scalar> getArgsList() {
         return this.args_;
      }

      public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
         return this.args_;
      }

      public int getArgsCount() {
         return this.args_.size();
      }

      public MysqlxDatatypes.Scalar getArgs(int index) {
         return (MysqlxDatatypes.Scalar)this.args_.get(index);
      }

      public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
         return (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index);
      }

      public boolean hasLimit() {
         return (this.bitField0_ & 8) == 8;
      }

      public MysqlxCrud.Limit getLimit() {
         return this.limit_;
      }

      public MysqlxCrud.LimitOrBuilder getLimitOrBuilder() {
         return this.limit_;
      }

      public List<MysqlxCrud.Order> getOrderList() {
         return this.order_;
      }

      public List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList() {
         return this.order_;
      }

      public int getOrderCount() {
         return this.order_.size();
      }

      public MysqlxCrud.Order getOrder(int index) {
         return (MysqlxCrud.Order)this.order_.get(index);
      }

      public MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int index) {
         return (MysqlxCrud.OrderOrBuilder)this.order_.get(index);
      }

      public List<MysqlxExpr.Expr> getGroupingList() {
         return this.grouping_;
      }

      public List<? extends MysqlxExpr.ExprOrBuilder> getGroupingOrBuilderList() {
         return this.grouping_;
      }

      public int getGroupingCount() {
         return this.grouping_.size();
      }

      public MysqlxExpr.Expr getGrouping(int index) {
         return (MysqlxExpr.Expr)this.grouping_.get(index);
      }

      public MysqlxExpr.ExprOrBuilder getGroupingOrBuilder(int index) {
         return (MysqlxExpr.ExprOrBuilder)this.grouping_.get(index);
      }

      public boolean hasGroupingCriteria() {
         return (this.bitField0_ & 16) == 16;
      }

      public MysqlxExpr.Expr getGroupingCriteria() {
         return this.groupingCriteria_;
      }

      public MysqlxExpr.ExprOrBuilder getGroupingCriteriaOrBuilder() {
         return this.groupingCriteria_;
      }

      private void initFields() {
         this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
         this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
         this.projection_ = Collections.emptyList();
         this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
         this.args_ = Collections.emptyList();
         this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
         this.order_ = Collections.emptyList();
         this.grouping_ = Collections.emptyList();
         this.groupingCriteria_ = MysqlxExpr.Expr.getDefaultInstance();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasCollection()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getCollection().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            int i;
            for(i = 0; i < this.getProjectionCount(); ++i) {
               if (!this.getProjection(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
               this.memoizedIsInitialized = 0;
               return false;
            } else {
               for(i = 0; i < this.getArgsCount(); ++i) {
                  if (!this.getArgs(i).isInitialized()) {
                     this.memoizedIsInitialized = 0;
                     return false;
                  }
               }

               if (this.hasLimit() && !this.getLimit().isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               } else {
                  for(i = 0; i < this.getOrderCount(); ++i) {
                     if (!this.getOrder(i).isInitialized()) {
                        this.memoizedIsInitialized = 0;
                        return false;
                     }
                  }

                  for(i = 0; i < this.getGroupingCount(); ++i) {
                     if (!this.getGrouping(i).isInitialized()) {
                        this.memoizedIsInitialized = 0;
                        return false;
                     }
                  }

                  if (this.hasGroupingCriteria() && !this.getGroupingCriteria().isInitialized()) {
                     this.memoizedIsInitialized = 0;
                     return false;
                  } else {
                     this.memoizedIsInitialized = 1;
                     return true;
                  }
               }
            }
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(2, this.collection_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeEnum(3, this.dataModel_.getNumber());
         }

         int i;
         for(i = 0; i < this.projection_.size(); ++i) {
            output.writeMessage(4, (MessageLite)this.projection_.get(i));
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeMessage(5, this.criteria_);
         }

         if ((this.bitField0_ & 8) == 8) {
            output.writeMessage(6, this.limit_);
         }

         for(i = 0; i < this.order_.size(); ++i) {
            output.writeMessage(7, (MessageLite)this.order_.get(i));
         }

         for(i = 0; i < this.grouping_.size(); ++i) {
            output.writeMessage(8, (MessageLite)this.grouping_.get(i));
         }

         if ((this.bitField0_ & 16) == 16) {
            output.writeMessage(9, this.groupingCriteria_);
         }

         for(i = 0; i < this.args_.size(); ++i) {
            output.writeMessage(11, (MessageLite)this.args_.get(i));
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(2, this.collection_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeEnumSize(3, this.dataModel_.getNumber());
            }

            int i;
            for(i = 0; i < this.projection_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.projection_.get(i));
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeMessageSize(5, this.criteria_);
            }

            if ((this.bitField0_ & 8) == 8) {
               size += CodedOutputStream.computeMessageSize(6, this.limit_);
            }

            for(i = 0; i < this.order_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.order_.get(i));
            }

            for(i = 0; i < this.grouping_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(8, (MessageLite)this.grouping_.get(i));
            }

            if ((this.bitField0_ & 16) == 16) {
               size += CodedOutputStream.computeMessageSize(9, this.groupingCriteria_);
            }

            for(i = 0; i < this.args_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(11, (MessageLite)this.args_.get(i));
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Find parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Find)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Find parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Find)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Find parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Find)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Find parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Find)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Find parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Find)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Find parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Find)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Find parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Find)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Find parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Find)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Find parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Find)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Find parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Find)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Find.Builder newBuilder() {
         return MysqlxCrud.Find.Builder.create();
      }

      public MysqlxCrud.Find.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Find.Builder newBuilder(MysqlxCrud.Find prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Find.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Find.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Find.Builder builder = new MysqlxCrud.Find.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Find(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Find(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Find.Builder> implements MysqlxCrud.FindOrBuilder {
         private int bitField0_;
         private MysqlxCrud.Collection collection_;
         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> collectionBuilder_;
         private MysqlxCrud.DataModel dataModel_;
         private List<MysqlxCrud.Projection> projection_;
         private RepeatedFieldBuilder<MysqlxCrud.Projection, MysqlxCrud.Projection.Builder, MysqlxCrud.ProjectionOrBuilder> projectionBuilder_;
         private MysqlxExpr.Expr criteria_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> criteriaBuilder_;
         private List<MysqlxDatatypes.Scalar> args_;
         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
         private MysqlxCrud.Limit limit_;
         private SingleFieldBuilder<MysqlxCrud.Limit, MysqlxCrud.Limit.Builder, MysqlxCrud.LimitOrBuilder> limitBuilder_;
         private List<MysqlxCrud.Order> order_;
         private RepeatedFieldBuilder<MysqlxCrud.Order, MysqlxCrud.Order.Builder, MysqlxCrud.OrderOrBuilder> orderBuilder_;
         private List<MysqlxExpr.Expr> grouping_;
         private RepeatedFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> groupingBuilder_;
         private MysqlxExpr.Expr groupingCriteria_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> groupingCriteriaBuilder_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Find_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Find.class, MysqlxCrud.Find.Builder.class);
         }

         private Builder() {
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.projection_ = Collections.emptyList();
            this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.args_ = Collections.emptyList();
            this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            this.order_ = Collections.emptyList();
            this.grouping_ = Collections.emptyList();
            this.groupingCriteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.projection_ = Collections.emptyList();
            this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.args_ = Collections.emptyList();
            this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            this.order_ = Collections.emptyList();
            this.grouping_ = Collections.emptyList();
            this.groupingCriteria_ = MysqlxExpr.Expr.getDefaultInstance();
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Find.alwaysUseFieldBuilders) {
               this.getCollectionFieldBuilder();
               this.getProjectionFieldBuilder();
               this.getCriteriaFieldBuilder();
               this.getArgsFieldBuilder();
               this.getLimitFieldBuilder();
               this.getOrderFieldBuilder();
               this.getGroupingFieldBuilder();
               this.getGroupingCriteriaFieldBuilder();
            }

         }

         private static MysqlxCrud.Find.Builder create() {
            return new MysqlxCrud.Find.Builder();
         }

         public MysqlxCrud.Find.Builder clear() {
            super.clear();
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.bitField0_ &= -3;
            if (this.projectionBuilder_ == null) {
               this.projection_ = Collections.emptyList();
               this.bitField0_ &= -5;
            } else {
               this.projectionBuilder_.clear();
            }

            if (this.criteriaBuilder_ == null) {
               this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.criteriaBuilder_.clear();
            }

            this.bitField0_ &= -9;
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -17;
            } else {
               this.argsBuilder_.clear();
            }

            if (this.limitBuilder_ == null) {
               this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
            } else {
               this.limitBuilder_.clear();
            }

            this.bitField0_ &= -33;
            if (this.orderBuilder_ == null) {
               this.order_ = Collections.emptyList();
               this.bitField0_ &= -65;
            } else {
               this.orderBuilder_.clear();
            }

            if (this.groupingBuilder_ == null) {
               this.grouping_ = Collections.emptyList();
               this.bitField0_ &= -129;
            } else {
               this.groupingBuilder_.clear();
            }

            if (this.groupingCriteriaBuilder_ == null) {
               this.groupingCriteria_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.groupingCriteriaBuilder_.clear();
            }

            this.bitField0_ &= -257;
            return this;
         }

         public MysqlxCrud.Find.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor;
         }

         public MysqlxCrud.Find getDefaultInstanceForType() {
            return MysqlxCrud.Find.getDefaultInstance();
         }

         public MysqlxCrud.Find build() {
            MysqlxCrud.Find result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Find buildPartial() {
            MysqlxCrud.Find result = new MysqlxCrud.Find(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.collectionBuilder_ == null) {
               result.collection_ = this.collection_;
            } else {
               result.collection_ = (MysqlxCrud.Collection)this.collectionBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.dataModel_ = this.dataModel_;
            if (this.projectionBuilder_ == null) {
               if ((this.bitField0_ & 4) == 4) {
                  this.projection_ = Collections.unmodifiableList(this.projection_);
                  this.bitField0_ &= -5;
               }

               result.projection_ = this.projection_;
            } else {
               result.projection_ = this.projectionBuilder_.build();
            }

            if ((from_bitField0_ & 8) == 8) {
               to_bitField0_ |= 4;
            }

            if (this.criteriaBuilder_ == null) {
               result.criteria_ = this.criteria_;
            } else {
               result.criteria_ = (MysqlxExpr.Expr)this.criteriaBuilder_.build();
            }

            if (this.argsBuilder_ == null) {
               if ((this.bitField0_ & 16) == 16) {
                  this.args_ = Collections.unmodifiableList(this.args_);
                  this.bitField0_ &= -17;
               }

               result.args_ = this.args_;
            } else {
               result.args_ = this.argsBuilder_.build();
            }

            if ((from_bitField0_ & 32) == 32) {
               to_bitField0_ |= 8;
            }

            if (this.limitBuilder_ == null) {
               result.limit_ = this.limit_;
            } else {
               result.limit_ = (MysqlxCrud.Limit)this.limitBuilder_.build();
            }

            if (this.orderBuilder_ == null) {
               if ((this.bitField0_ & 64) == 64) {
                  this.order_ = Collections.unmodifiableList(this.order_);
                  this.bitField0_ &= -65;
               }

               result.order_ = this.order_;
            } else {
               result.order_ = this.orderBuilder_.build();
            }

            if (this.groupingBuilder_ == null) {
               if ((this.bitField0_ & 128) == 128) {
                  this.grouping_ = Collections.unmodifiableList(this.grouping_);
                  this.bitField0_ &= -129;
               }

               result.grouping_ = this.grouping_;
            } else {
               result.grouping_ = this.groupingBuilder_.build();
            }

            if ((from_bitField0_ & 256) == 256) {
               to_bitField0_ |= 16;
            }

            if (this.groupingCriteriaBuilder_ == null) {
               result.groupingCriteria_ = this.groupingCriteria_;
            } else {
               result.groupingCriteria_ = (MysqlxExpr.Expr)this.groupingCriteriaBuilder_.build();
            }

            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Find.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Find) {
               return this.mergeFrom((MysqlxCrud.Find)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Find.Builder mergeFrom(MysqlxCrud.Find other) {
            if (other == MysqlxCrud.Find.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasCollection()) {
                  this.mergeCollection(other.getCollection());
               }

               if (other.hasDataModel()) {
                  this.setDataModel(other.getDataModel());
               }

               if (this.projectionBuilder_ == null) {
                  if (!other.projection_.isEmpty()) {
                     if (this.projection_.isEmpty()) {
                        this.projection_ = other.projection_;
                        this.bitField0_ &= -5;
                     } else {
                        this.ensureProjectionIsMutable();
                        this.projection_.addAll(other.projection_);
                     }

                     this.onChanged();
                  }
               } else if (!other.projection_.isEmpty()) {
                  if (this.projectionBuilder_.isEmpty()) {
                     this.projectionBuilder_.dispose();
                     this.projectionBuilder_ = null;
                     this.projection_ = other.projection_;
                     this.bitField0_ &= -5;
                     this.projectionBuilder_ = MysqlxCrud.Find.alwaysUseFieldBuilders ? this.getProjectionFieldBuilder() : null;
                  } else {
                     this.projectionBuilder_.addAllMessages(other.projection_);
                  }
               }

               if (other.hasCriteria()) {
                  this.mergeCriteria(other.getCriteria());
               }

               if (this.argsBuilder_ == null) {
                  if (!other.args_.isEmpty()) {
                     if (this.args_.isEmpty()) {
                        this.args_ = other.args_;
                        this.bitField0_ &= -17;
                     } else {
                        this.ensureArgsIsMutable();
                        this.args_.addAll(other.args_);
                     }

                     this.onChanged();
                  }
               } else if (!other.args_.isEmpty()) {
                  if (this.argsBuilder_.isEmpty()) {
                     this.argsBuilder_.dispose();
                     this.argsBuilder_ = null;
                     this.args_ = other.args_;
                     this.bitField0_ &= -17;
                     this.argsBuilder_ = MysqlxCrud.Find.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null;
                  } else {
                     this.argsBuilder_.addAllMessages(other.args_);
                  }
               }

               if (other.hasLimit()) {
                  this.mergeLimit(other.getLimit());
               }

               if (this.orderBuilder_ == null) {
                  if (!other.order_.isEmpty()) {
                     if (this.order_.isEmpty()) {
                        this.order_ = other.order_;
                        this.bitField0_ &= -65;
                     } else {
                        this.ensureOrderIsMutable();
                        this.order_.addAll(other.order_);
                     }

                     this.onChanged();
                  }
               } else if (!other.order_.isEmpty()) {
                  if (this.orderBuilder_.isEmpty()) {
                     this.orderBuilder_.dispose();
                     this.orderBuilder_ = null;
                     this.order_ = other.order_;
                     this.bitField0_ &= -65;
                     this.orderBuilder_ = MysqlxCrud.Find.alwaysUseFieldBuilders ? this.getOrderFieldBuilder() : null;
                  } else {
                     this.orderBuilder_.addAllMessages(other.order_);
                  }
               }

               if (this.groupingBuilder_ == null) {
                  if (!other.grouping_.isEmpty()) {
                     if (this.grouping_.isEmpty()) {
                        this.grouping_ = other.grouping_;
                        this.bitField0_ &= -129;
                     } else {
                        this.ensureGroupingIsMutable();
                        this.grouping_.addAll(other.grouping_);
                     }

                     this.onChanged();
                  }
               } else if (!other.grouping_.isEmpty()) {
                  if (this.groupingBuilder_.isEmpty()) {
                     this.groupingBuilder_.dispose();
                     this.groupingBuilder_ = null;
                     this.grouping_ = other.grouping_;
                     this.bitField0_ &= -129;
                     this.groupingBuilder_ = MysqlxCrud.Find.alwaysUseFieldBuilders ? this.getGroupingFieldBuilder() : null;
                  } else {
                     this.groupingBuilder_.addAllMessages(other.grouping_);
                  }
               }

               if (other.hasGroupingCriteria()) {
                  this.mergeGroupingCriteria(other.getGroupingCriteria());
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasCollection()) {
               return false;
            } else if (!this.getCollection().isInitialized()) {
               return false;
            } else {
               int i;
               for(i = 0; i < this.getProjectionCount(); ++i) {
                  if (!this.getProjection(i).isInitialized()) {
                     return false;
                  }
               }

               if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                  return false;
               } else {
                  for(i = 0; i < this.getArgsCount(); ++i) {
                     if (!this.getArgs(i).isInitialized()) {
                        return false;
                     }
                  }

                  if (this.hasLimit() && !this.getLimit().isInitialized()) {
                     return false;
                  } else {
                     for(i = 0; i < this.getOrderCount(); ++i) {
                        if (!this.getOrder(i).isInitialized()) {
                           return false;
                        }
                     }

                     for(i = 0; i < this.getGroupingCount(); ++i) {
                        if (!this.getGrouping(i).isInitialized()) {
                           return false;
                        }
                     }

                     if (this.hasGroupingCriteria() && !this.getGroupingCriteria().isInitialized()) {
                        return false;
                     } else {
                        return true;
                     }
                  }
               }
            }
         }

         public MysqlxCrud.Find.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Find parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Find)MysqlxCrud.Find.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Find)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasCollection() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxCrud.Collection getCollection() {
            return this.collectionBuilder_ == null ? this.collection_ : (MysqlxCrud.Collection)this.collectionBuilder_.getMessage();
         }

         public MysqlxCrud.Find.Builder setCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.collection_ = value;
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Find.Builder setCollection(MysqlxCrud.Collection.Builder builderForValue) {
            if (this.collectionBuilder_ == null) {
               this.collection_ = builderForValue.build();
               this.onChanged();
            } else {
               this.collectionBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Find.Builder mergeCollection(MysqlxCrud.Collection value) {
            if (this.collectionBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.collection_ != MysqlxCrud.Collection.getDefaultInstance()) {
                  this.collection_ = MysqlxCrud.Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
               } else {
                  this.collection_ = value;
               }

               this.onChanged();
            } else {
               this.collectionBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Find.Builder clearCollection() {
            if (this.collectionBuilder_ == null) {
               this.collection_ = MysqlxCrud.Collection.getDefaultInstance();
               this.onChanged();
            } else {
               this.collectionBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxCrud.Collection.Builder getCollectionBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxCrud.Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
         }

         public MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder() {
            return (MysqlxCrud.CollectionOrBuilder)(this.collectionBuilder_ != null ? (MysqlxCrud.CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder() : this.collection_);
         }

         private SingleFieldBuilder<MysqlxCrud.Collection, MysqlxCrud.Collection.Builder, MysqlxCrud.CollectionOrBuilder> getCollectionFieldBuilder() {
            if (this.collectionBuilder_ == null) {
               this.collectionBuilder_ = new SingleFieldBuilder(this.getCollection(), this.getParentForChildren(), this.isClean());
               this.collection_ = null;
            }

            return this.collectionBuilder_;
         }

         public boolean hasDataModel() {
            return (this.bitField0_ & 2) == 2;
         }

         public MysqlxCrud.DataModel getDataModel() {
            return this.dataModel_;
         }

         public MysqlxCrud.Find.Builder setDataModel(MysqlxCrud.DataModel value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.dataModel_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Find.Builder clearDataModel() {
            this.bitField0_ &= -3;
            this.dataModel_ = MysqlxCrud.DataModel.DOCUMENT;
            this.onChanged();
            return this;
         }

         private void ensureProjectionIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.projection_ = new ArrayList(this.projection_);
               this.bitField0_ |= 4;
            }

         }

         public List<MysqlxCrud.Projection> getProjectionList() {
            return this.projectionBuilder_ == null ? Collections.unmodifiableList(this.projection_) : this.projectionBuilder_.getMessageList();
         }

         public int getProjectionCount() {
            return this.projectionBuilder_ == null ? this.projection_.size() : this.projectionBuilder_.getCount();
         }

         public MysqlxCrud.Projection getProjection(int index) {
            return this.projectionBuilder_ == null ? (MysqlxCrud.Projection)this.projection_.get(index) : (MysqlxCrud.Projection)this.projectionBuilder_.getMessage(index);
         }

         public MysqlxCrud.Find.Builder setProjection(int index, MysqlxCrud.Projection value) {
            if (this.projectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureProjectionIsMutable();
               this.projection_.set(index, value);
               this.onChanged();
            } else {
               this.projectionBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder setProjection(int index, MysqlxCrud.Projection.Builder builderForValue) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.projectionBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addProjection(MysqlxCrud.Projection value) {
            if (this.projectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureProjectionIsMutable();
               this.projection_.add(value);
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addProjection(int index, MysqlxCrud.Projection value) {
            if (this.projectionBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureProjectionIsMutable();
               this.projection_.add(index, value);
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addProjection(MysqlxCrud.Projection.Builder builderForValue) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addProjection(int index, MysqlxCrud.Projection.Builder builderForValue) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.projectionBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addAllProjection(Iterable<? extends MysqlxCrud.Projection> values) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.projection_);
               this.onChanged();
            } else {
               this.projectionBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder clearProjection() {
            if (this.projectionBuilder_ == null) {
               this.projection_ = Collections.emptyList();
               this.bitField0_ &= -5;
               this.onChanged();
            } else {
               this.projectionBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Find.Builder removeProjection(int index) {
            if (this.projectionBuilder_ == null) {
               this.ensureProjectionIsMutable();
               this.projection_.remove(index);
               this.onChanged();
            } else {
               this.projectionBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.Projection.Builder getProjectionBuilder(int index) {
            return (MysqlxCrud.Projection.Builder)this.getProjectionFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.ProjectionOrBuilder getProjectionOrBuilder(int index) {
            return this.projectionBuilder_ == null ? (MysqlxCrud.ProjectionOrBuilder)this.projection_.get(index) : (MysqlxCrud.ProjectionOrBuilder)this.projectionBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.ProjectionOrBuilder> getProjectionOrBuilderList() {
            return this.projectionBuilder_ != null ? this.projectionBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.projection_);
         }

         public MysqlxCrud.Projection.Builder addProjectionBuilder() {
            return (MysqlxCrud.Projection.Builder)this.getProjectionFieldBuilder().addBuilder(MysqlxCrud.Projection.getDefaultInstance());
         }

         public MysqlxCrud.Projection.Builder addProjectionBuilder(int index) {
            return (MysqlxCrud.Projection.Builder)this.getProjectionFieldBuilder().addBuilder(index, MysqlxCrud.Projection.getDefaultInstance());
         }

         public List<MysqlxCrud.Projection.Builder> getProjectionBuilderList() {
            return this.getProjectionFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.Projection, MysqlxCrud.Projection.Builder, MysqlxCrud.ProjectionOrBuilder> getProjectionFieldBuilder() {
            if (this.projectionBuilder_ == null) {
               this.projectionBuilder_ = new RepeatedFieldBuilder(this.projection_, (this.bitField0_ & 4) == 4, this.getParentForChildren(), this.isClean());
               this.projection_ = null;
            }

            return this.projectionBuilder_;
         }

         public boolean hasCriteria() {
            return (this.bitField0_ & 8) == 8;
         }

         public MysqlxExpr.Expr getCriteria() {
            return this.criteriaBuilder_ == null ? this.criteria_ : (MysqlxExpr.Expr)this.criteriaBuilder_.getMessage();
         }

         public MysqlxCrud.Find.Builder setCriteria(MysqlxExpr.Expr value) {
            if (this.criteriaBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.criteria_ = value;
               this.onChanged();
            } else {
               this.criteriaBuilder_.setMessage(value);
            }

            this.bitField0_ |= 8;
            return this;
         }

         public MysqlxCrud.Find.Builder setCriteria(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = builderForValue.build();
               this.onChanged();
            } else {
               this.criteriaBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 8;
            return this;
         }

         public MysqlxCrud.Find.Builder mergeCriteria(MysqlxExpr.Expr value) {
            if (this.criteriaBuilder_ == null) {
               if ((this.bitField0_ & 8) == 8 && this.criteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.criteria_ = MysqlxExpr.Expr.newBuilder(this.criteria_).mergeFrom(value).buildPartial();
               } else {
                  this.criteria_ = value;
               }

               this.onChanged();
            } else {
               this.criteriaBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 8;
            return this;
         }

         public MysqlxCrud.Find.Builder clearCriteria() {
            if (this.criteriaBuilder_ == null) {
               this.criteria_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.criteriaBuilder_.clear();
            }

            this.bitField0_ &= -9;
            return this;
         }

         public MysqlxExpr.Expr.Builder getCriteriaBuilder() {
            this.bitField0_ |= 8;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getCriteriaFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.criteriaBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.criteriaBuilder_.getMessageOrBuilder() : this.criteria_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getCriteriaFieldBuilder() {
            if (this.criteriaBuilder_ == null) {
               this.criteriaBuilder_ = new SingleFieldBuilder(this.getCriteria(), this.getParentForChildren(), this.isClean());
               this.criteria_ = null;
            }

            return this.criteriaBuilder_;
         }

         private void ensureArgsIsMutable() {
            if ((this.bitField0_ & 16) != 16) {
               this.args_ = new ArrayList(this.args_);
               this.bitField0_ |= 16;
            }

         }

         public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.argsBuilder_ == null ? Collections.unmodifiableList(this.args_) : this.argsBuilder_.getMessageList();
         }

         public int getArgsCount() {
            return this.argsBuilder_ == null ? this.args_.size() : this.argsBuilder_.getCount();
         }

         public MysqlxDatatypes.Scalar getArgs(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.Scalar)this.args_.get(index) : (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
         }

         public MysqlxCrud.Find.Builder setArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.set(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder setArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addArgs(MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addArgs(int index, MysqlxDatatypes.Scalar value) {
            if (this.argsBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureArgsIsMutable();
               this.args_.add(index, value);
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addArgs(MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addArgs(int index, MysqlxDatatypes.Scalar.Builder builderForValue) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.argsBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addAllArgs(Iterable<? extends MysqlxDatatypes.Scalar> values) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.args_);
               this.onChanged();
            } else {
               this.argsBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder clearArgs() {
            if (this.argsBuilder_ == null) {
               this.args_ = Collections.emptyList();
               this.bitField0_ &= -17;
               this.onChanged();
            } else {
               this.argsBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Find.Builder removeArgs(int index) {
            if (this.argsBuilder_ == null) {
               this.ensureArgsIsMutable();
               this.args_.remove(index);
               this.onChanged();
            } else {
               this.argsBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxDatatypes.Scalar.Builder getArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
         }

         public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int index) {
            return this.argsBuilder_ == null ? (MysqlxDatatypes.ScalarOrBuilder)this.args_.get(index) : (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.argsBuilder_ != null ? this.argsBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.args_);
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public MysqlxDatatypes.Scalar.Builder addArgsBuilder(int index) {
            return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, MysqlxDatatypes.Scalar.getDefaultInstance());
         }

         public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
            return this.getArgsFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
            if (this.argsBuilder_ == null) {
               this.argsBuilder_ = new RepeatedFieldBuilder(this.args_, (this.bitField0_ & 16) == 16, this.getParentForChildren(), this.isClean());
               this.args_ = null;
            }

            return this.argsBuilder_;
         }

         public boolean hasLimit() {
            return (this.bitField0_ & 32) == 32;
         }

         public MysqlxCrud.Limit getLimit() {
            return this.limitBuilder_ == null ? this.limit_ : (MysqlxCrud.Limit)this.limitBuilder_.getMessage();
         }

         public MysqlxCrud.Find.Builder setLimit(MysqlxCrud.Limit value) {
            if (this.limitBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.limit_ = value;
               this.onChanged();
            } else {
               this.limitBuilder_.setMessage(value);
            }

            this.bitField0_ |= 32;
            return this;
         }

         public MysqlxCrud.Find.Builder setLimit(MysqlxCrud.Limit.Builder builderForValue) {
            if (this.limitBuilder_ == null) {
               this.limit_ = builderForValue.build();
               this.onChanged();
            } else {
               this.limitBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 32;
            return this;
         }

         public MysqlxCrud.Find.Builder mergeLimit(MysqlxCrud.Limit value) {
            if (this.limitBuilder_ == null) {
               if ((this.bitField0_ & 32) == 32 && this.limit_ != MysqlxCrud.Limit.getDefaultInstance()) {
                  this.limit_ = MysqlxCrud.Limit.newBuilder(this.limit_).mergeFrom(value).buildPartial();
               } else {
                  this.limit_ = value;
               }

               this.onChanged();
            } else {
               this.limitBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 32;
            return this;
         }

         public MysqlxCrud.Find.Builder clearLimit() {
            if (this.limitBuilder_ == null) {
               this.limit_ = MysqlxCrud.Limit.getDefaultInstance();
               this.onChanged();
            } else {
               this.limitBuilder_.clear();
            }

            this.bitField0_ &= -33;
            return this;
         }

         public MysqlxCrud.Limit.Builder getLimitBuilder() {
            this.bitField0_ |= 32;
            this.onChanged();
            return (MysqlxCrud.Limit.Builder)this.getLimitFieldBuilder().getBuilder();
         }

         public MysqlxCrud.LimitOrBuilder getLimitOrBuilder() {
            return (MysqlxCrud.LimitOrBuilder)(this.limitBuilder_ != null ? (MysqlxCrud.LimitOrBuilder)this.limitBuilder_.getMessageOrBuilder() : this.limit_);
         }

         private SingleFieldBuilder<MysqlxCrud.Limit, MysqlxCrud.Limit.Builder, MysqlxCrud.LimitOrBuilder> getLimitFieldBuilder() {
            if (this.limitBuilder_ == null) {
               this.limitBuilder_ = new SingleFieldBuilder(this.getLimit(), this.getParentForChildren(), this.isClean());
               this.limit_ = null;
            }

            return this.limitBuilder_;
         }

         private void ensureOrderIsMutable() {
            if ((this.bitField0_ & 64) != 64) {
               this.order_ = new ArrayList(this.order_);
               this.bitField0_ |= 64;
            }

         }

         public List<MysqlxCrud.Order> getOrderList() {
            return this.orderBuilder_ == null ? Collections.unmodifiableList(this.order_) : this.orderBuilder_.getMessageList();
         }

         public int getOrderCount() {
            return this.orderBuilder_ == null ? this.order_.size() : this.orderBuilder_.getCount();
         }

         public MysqlxCrud.Order getOrder(int index) {
            return this.orderBuilder_ == null ? (MysqlxCrud.Order)this.order_.get(index) : (MysqlxCrud.Order)this.orderBuilder_.getMessage(index);
         }

         public MysqlxCrud.Find.Builder setOrder(int index, MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.set(index, value);
               this.onChanged();
            } else {
               this.orderBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder setOrder(int index, MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addOrder(MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.add(value);
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addOrder(int index, MysqlxCrud.Order value) {
            if (this.orderBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureOrderIsMutable();
               this.order_.add(index, value);
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addOrder(MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addOrder(int index, MysqlxCrud.Order.Builder builderForValue) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.orderBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addAllOrder(Iterable<? extends MysqlxCrud.Order> values) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.order_);
               this.onChanged();
            } else {
               this.orderBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder clearOrder() {
            if (this.orderBuilder_ == null) {
               this.order_ = Collections.emptyList();
               this.bitField0_ &= -65;
               this.onChanged();
            } else {
               this.orderBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Find.Builder removeOrder(int index) {
            if (this.orderBuilder_ == null) {
               this.ensureOrderIsMutable();
               this.order_.remove(index);
               this.onChanged();
            } else {
               this.orderBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxCrud.Order.Builder getOrderBuilder(int index) {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().getBuilder(index);
         }

         public MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int index) {
            return this.orderBuilder_ == null ? (MysqlxCrud.OrderOrBuilder)this.order_.get(index) : (MysqlxCrud.OrderOrBuilder)this.orderBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList() {
            return this.orderBuilder_ != null ? this.orderBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.order_);
         }

         public MysqlxCrud.Order.Builder addOrderBuilder() {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().addBuilder(MysqlxCrud.Order.getDefaultInstance());
         }

         public MysqlxCrud.Order.Builder addOrderBuilder(int index) {
            return (MysqlxCrud.Order.Builder)this.getOrderFieldBuilder().addBuilder(index, MysqlxCrud.Order.getDefaultInstance());
         }

         public List<MysqlxCrud.Order.Builder> getOrderBuilderList() {
            return this.getOrderFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxCrud.Order, MysqlxCrud.Order.Builder, MysqlxCrud.OrderOrBuilder> getOrderFieldBuilder() {
            if (this.orderBuilder_ == null) {
               this.orderBuilder_ = new RepeatedFieldBuilder(this.order_, (this.bitField0_ & 64) == 64, this.getParentForChildren(), this.isClean());
               this.order_ = null;
            }

            return this.orderBuilder_;
         }

         private void ensureGroupingIsMutable() {
            if ((this.bitField0_ & 128) != 128) {
               this.grouping_ = new ArrayList(this.grouping_);
               this.bitField0_ |= 128;
            }

         }

         public List<MysqlxExpr.Expr> getGroupingList() {
            return this.groupingBuilder_ == null ? Collections.unmodifiableList(this.grouping_) : this.groupingBuilder_.getMessageList();
         }

         public int getGroupingCount() {
            return this.groupingBuilder_ == null ? this.grouping_.size() : this.groupingBuilder_.getCount();
         }

         public MysqlxExpr.Expr getGrouping(int index) {
            return this.groupingBuilder_ == null ? (MysqlxExpr.Expr)this.grouping_.get(index) : (MysqlxExpr.Expr)this.groupingBuilder_.getMessage(index);
         }

         public MysqlxCrud.Find.Builder setGrouping(int index, MysqlxExpr.Expr value) {
            if (this.groupingBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureGroupingIsMutable();
               this.grouping_.set(index, value);
               this.onChanged();
            } else {
               this.groupingBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder setGrouping(int index, MysqlxExpr.Expr.Builder builderForValue) {
            if (this.groupingBuilder_ == null) {
               this.ensureGroupingIsMutable();
               this.grouping_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.groupingBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addGrouping(MysqlxExpr.Expr value) {
            if (this.groupingBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureGroupingIsMutable();
               this.grouping_.add(value);
               this.onChanged();
            } else {
               this.groupingBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addGrouping(int index, MysqlxExpr.Expr value) {
            if (this.groupingBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureGroupingIsMutable();
               this.grouping_.add(index, value);
               this.onChanged();
            } else {
               this.groupingBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addGrouping(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.groupingBuilder_ == null) {
               this.ensureGroupingIsMutable();
               this.grouping_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.groupingBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addGrouping(int index, MysqlxExpr.Expr.Builder builderForValue) {
            if (this.groupingBuilder_ == null) {
               this.ensureGroupingIsMutable();
               this.grouping_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.groupingBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Find.Builder addAllGrouping(Iterable<? extends MysqlxExpr.Expr> values) {
            if (this.groupingBuilder_ == null) {
               this.ensureGroupingIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.grouping_);
               this.onChanged();
            } else {
               this.groupingBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Find.Builder clearGrouping() {
            if (this.groupingBuilder_ == null) {
               this.grouping_ = Collections.emptyList();
               this.bitField0_ &= -129;
               this.onChanged();
            } else {
               this.groupingBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Find.Builder removeGrouping(int index) {
            if (this.groupingBuilder_ == null) {
               this.ensureGroupingIsMutable();
               this.grouping_.remove(index);
               this.onChanged();
            } else {
               this.groupingBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxExpr.Expr.Builder getGroupingBuilder(int index) {
            return (MysqlxExpr.Expr.Builder)this.getGroupingFieldBuilder().getBuilder(index);
         }

         public MysqlxExpr.ExprOrBuilder getGroupingOrBuilder(int index) {
            return this.groupingBuilder_ == null ? (MysqlxExpr.ExprOrBuilder)this.grouping_.get(index) : (MysqlxExpr.ExprOrBuilder)this.groupingBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxExpr.ExprOrBuilder> getGroupingOrBuilderList() {
            return this.groupingBuilder_ != null ? this.groupingBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.grouping_);
         }

         public MysqlxExpr.Expr.Builder addGroupingBuilder() {
            return (MysqlxExpr.Expr.Builder)this.getGroupingFieldBuilder().addBuilder(MysqlxExpr.Expr.getDefaultInstance());
         }

         public MysqlxExpr.Expr.Builder addGroupingBuilder(int index) {
            return (MysqlxExpr.Expr.Builder)this.getGroupingFieldBuilder().addBuilder(index, MysqlxExpr.Expr.getDefaultInstance());
         }

         public List<MysqlxExpr.Expr.Builder> getGroupingBuilderList() {
            return this.getGroupingFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getGroupingFieldBuilder() {
            if (this.groupingBuilder_ == null) {
               this.groupingBuilder_ = new RepeatedFieldBuilder(this.grouping_, (this.bitField0_ & 128) == 128, this.getParentForChildren(), this.isClean());
               this.grouping_ = null;
            }

            return this.groupingBuilder_;
         }

         public boolean hasGroupingCriteria() {
            return (this.bitField0_ & 256) == 256;
         }

         public MysqlxExpr.Expr getGroupingCriteria() {
            return this.groupingCriteriaBuilder_ == null ? this.groupingCriteria_ : (MysqlxExpr.Expr)this.groupingCriteriaBuilder_.getMessage();
         }

         public MysqlxCrud.Find.Builder setGroupingCriteria(MysqlxExpr.Expr value) {
            if (this.groupingCriteriaBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.groupingCriteria_ = value;
               this.onChanged();
            } else {
               this.groupingCriteriaBuilder_.setMessage(value);
            }

            this.bitField0_ |= 256;
            return this;
         }

         public MysqlxCrud.Find.Builder setGroupingCriteria(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.groupingCriteriaBuilder_ == null) {
               this.groupingCriteria_ = builderForValue.build();
               this.onChanged();
            } else {
               this.groupingCriteriaBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 256;
            return this;
         }

         public MysqlxCrud.Find.Builder mergeGroupingCriteria(MysqlxExpr.Expr value) {
            if (this.groupingCriteriaBuilder_ == null) {
               if ((this.bitField0_ & 256) == 256 && this.groupingCriteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.groupingCriteria_ = MysqlxExpr.Expr.newBuilder(this.groupingCriteria_).mergeFrom(value).buildPartial();
               } else {
                  this.groupingCriteria_ = value;
               }

               this.onChanged();
            } else {
               this.groupingCriteriaBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 256;
            return this;
         }

         public MysqlxCrud.Find.Builder clearGroupingCriteria() {
            if (this.groupingCriteriaBuilder_ == null) {
               this.groupingCriteria_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.groupingCriteriaBuilder_.clear();
            }

            this.bitField0_ &= -257;
            return this;
         }

         public MysqlxExpr.Expr.Builder getGroupingCriteriaBuilder() {
            this.bitField0_ |= 256;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getGroupingCriteriaFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getGroupingCriteriaOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.groupingCriteriaBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.groupingCriteriaBuilder_.getMessageOrBuilder() : this.groupingCriteria_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getGroupingCriteriaFieldBuilder() {
            if (this.groupingCriteriaBuilder_ == null) {
               this.groupingCriteriaBuilder_ = new SingleFieldBuilder(this.getGroupingCriteria(), this.getParentForChildren(), this.isClean());
               this.groupingCriteria_ = null;
            }

            return this.groupingCriteriaBuilder_;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface FindOrBuilder extends MessageOrBuilder {
      boolean hasCollection();

      MysqlxCrud.Collection getCollection();

      MysqlxCrud.CollectionOrBuilder getCollectionOrBuilder();

      boolean hasDataModel();

      MysqlxCrud.DataModel getDataModel();

      List<MysqlxCrud.Projection> getProjectionList();

      MysqlxCrud.Projection getProjection(int var1);

      int getProjectionCount();

      List<? extends MysqlxCrud.ProjectionOrBuilder> getProjectionOrBuilderList();

      MysqlxCrud.ProjectionOrBuilder getProjectionOrBuilder(int var1);

      boolean hasCriteria();

      MysqlxExpr.Expr getCriteria();

      MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder();

      List<MysqlxDatatypes.Scalar> getArgsList();

      MysqlxDatatypes.Scalar getArgs(int var1);

      int getArgsCount();

      List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();

      MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(int var1);

      boolean hasLimit();

      MysqlxCrud.Limit getLimit();

      MysqlxCrud.LimitOrBuilder getLimitOrBuilder();

      List<MysqlxCrud.Order> getOrderList();

      MysqlxCrud.Order getOrder(int var1);

      int getOrderCount();

      List<? extends MysqlxCrud.OrderOrBuilder> getOrderOrBuilderList();

      MysqlxCrud.OrderOrBuilder getOrderOrBuilder(int var1);

      List<MysqlxExpr.Expr> getGroupingList();

      MysqlxExpr.Expr getGrouping(int var1);

      int getGroupingCount();

      List<? extends MysqlxExpr.ExprOrBuilder> getGroupingOrBuilderList();

      MysqlxExpr.ExprOrBuilder getGroupingOrBuilder(int var1);

      boolean hasGroupingCriteria();

      MysqlxExpr.Expr getGroupingCriteria();

      MysqlxExpr.ExprOrBuilder getGroupingCriteriaOrBuilder();
   }

   public static final class UpdateOperation extends GeneratedMessage implements MysqlxCrud.UpdateOperationOrBuilder {
      private static final MysqlxCrud.UpdateOperation defaultInstance = new MysqlxCrud.UpdateOperation(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.UpdateOperation> PARSER = new AbstractParser<MysqlxCrud.UpdateOperation>() {
         public MysqlxCrud.UpdateOperation parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.UpdateOperation(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int SOURCE_FIELD_NUMBER = 1;
      private MysqlxExpr.ColumnIdentifier source_;
      public static final int OPERATION_FIELD_NUMBER = 2;
      private MysqlxCrud.UpdateOperation.UpdateType operation_;
      public static final int VALUE_FIELD_NUMBER = 3;
      private MysqlxExpr.Expr value_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private UpdateOperation(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private UpdateOperation(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.UpdateOperation getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.UpdateOperation getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private UpdateOperation(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = false;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  MysqlxExpr.ColumnIdentifier.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.source_.toBuilder();
                  }

                  this.source_ = (MysqlxExpr.ColumnIdentifier)input.readMessage(MysqlxExpr.ColumnIdentifier.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.source_);
                     this.source_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 16:
                  int rawValue = input.readEnum();
                  MysqlxCrud.UpdateOperation.UpdateType value = MysqlxCrud.UpdateOperation.UpdateType.valueOf(rawValue);
                  if (value == null) {
                     unknownFields.mergeVarintField(2, rawValue);
                  } else {
                     this.bitField0_ |= 2;
                     this.operation_ = value;
                  }
                  break;
               case 26:
                  MysqlxExpr.Expr.Builder subBuilder = null;
                  if ((this.bitField0_ & 4) == 4) {
                     subBuilder = this.value_.toBuilder();
                  }

                  this.value_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.value_);
                     this.value_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 4;
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var13) {
            throw var13.setUnfinishedMessage(this);
         } catch (IOException var14) {
            throw (new InvalidProtocolBufferException(var14.getMessage())).setUnfinishedMessage(this);
         } finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.UpdateOperation.class, MysqlxCrud.UpdateOperation.Builder.class);
      }

      public Parser<MysqlxCrud.UpdateOperation> getParserForType() {
         return PARSER;
      }

      public boolean hasSource() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxExpr.ColumnIdentifier getSource() {
         return this.source_;
      }

      public MysqlxExpr.ColumnIdentifierOrBuilder getSourceOrBuilder() {
         return this.source_;
      }

      public boolean hasOperation() {
         return (this.bitField0_ & 2) == 2;
      }

      public MysqlxCrud.UpdateOperation.UpdateType getOperation() {
         return this.operation_;
      }

      public boolean hasValue() {
         return (this.bitField0_ & 4) == 4;
      }

      public MysqlxExpr.Expr getValue() {
         return this.value_;
      }

      public MysqlxExpr.ExprOrBuilder getValueOrBuilder() {
         return this.value_;
      }

      private void initFields() {
         this.source_ = MysqlxExpr.ColumnIdentifier.getDefaultInstance();
         this.operation_ = MysqlxCrud.UpdateOperation.UpdateType.SET;
         this.value_ = MysqlxExpr.Expr.getDefaultInstance();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasSource()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.hasOperation()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getSource().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (this.hasValue() && !this.getValue().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.source_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeEnum(2, this.operation_.getNumber());
         }

         if ((this.bitField0_ & 4) == 4) {
            output.writeMessage(3, this.value_);
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.source_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeEnumSize(2, this.operation_.getNumber());
            }

            if ((this.bitField0_ & 4) == 4) {
               size += CodedOutputStream.computeMessageSize(3, this.value_);
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.UpdateOperation parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.UpdateOperation parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.UpdateOperation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.UpdateOperation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.UpdateOperation)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.UpdateOperation.Builder newBuilder() {
         return MysqlxCrud.UpdateOperation.Builder.create();
      }

      public MysqlxCrud.UpdateOperation.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.UpdateOperation.Builder newBuilder(MysqlxCrud.UpdateOperation prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.UpdateOperation.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.UpdateOperation.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.UpdateOperation.Builder builder = new MysqlxCrud.UpdateOperation.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      UpdateOperation(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      UpdateOperation(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.UpdateOperation.Builder> implements MysqlxCrud.UpdateOperationOrBuilder {
         private int bitField0_;
         private MysqlxExpr.ColumnIdentifier source_;
         private SingleFieldBuilder<MysqlxExpr.ColumnIdentifier, MysqlxExpr.ColumnIdentifier.Builder, MysqlxExpr.ColumnIdentifierOrBuilder> sourceBuilder_;
         private MysqlxCrud.UpdateOperation.UpdateType operation_;
         private MysqlxExpr.Expr value_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> valueBuilder_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.UpdateOperation.class, MysqlxCrud.UpdateOperation.Builder.class);
         }

         private Builder() {
            this.source_ = MysqlxExpr.ColumnIdentifier.getDefaultInstance();
            this.operation_ = MysqlxCrud.UpdateOperation.UpdateType.SET;
            this.value_ = MysqlxExpr.Expr.getDefaultInstance();
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.source_ = MysqlxExpr.ColumnIdentifier.getDefaultInstance();
            this.operation_ = MysqlxCrud.UpdateOperation.UpdateType.SET;
            this.value_ = MysqlxExpr.Expr.getDefaultInstance();
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.UpdateOperation.alwaysUseFieldBuilders) {
               this.getSourceFieldBuilder();
               this.getValueFieldBuilder();
            }

         }

         private static MysqlxCrud.UpdateOperation.Builder create() {
            return new MysqlxCrud.UpdateOperation.Builder();
         }

         public MysqlxCrud.UpdateOperation.Builder clear() {
            super.clear();
            if (this.sourceBuilder_ == null) {
               this.source_ = MysqlxExpr.ColumnIdentifier.getDefaultInstance();
            } else {
               this.sourceBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.operation_ = MysqlxCrud.UpdateOperation.UpdateType.SET;
            this.bitField0_ &= -3;
            if (this.valueBuilder_ == null) {
               this.value_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.valueBuilder_.clear();
            }

            this.bitField0_ &= -5;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
         }

         public MysqlxCrud.UpdateOperation getDefaultInstanceForType() {
            return MysqlxCrud.UpdateOperation.getDefaultInstance();
         }

         public MysqlxCrud.UpdateOperation build() {
            MysqlxCrud.UpdateOperation result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.UpdateOperation buildPartial() {
            MysqlxCrud.UpdateOperation result = new MysqlxCrud.UpdateOperation(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.sourceBuilder_ == null) {
               result.source_ = this.source_;
            } else {
               result.source_ = (MysqlxExpr.ColumnIdentifier)this.sourceBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.operation_ = this.operation_;
            if ((from_bitField0_ & 4) == 4) {
               to_bitField0_ |= 4;
            }

            if (this.valueBuilder_ == null) {
               result.value_ = this.value_;
            } else {
               result.value_ = (MysqlxExpr.Expr)this.valueBuilder_.build();
            }

            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.UpdateOperation.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.UpdateOperation) {
               return this.mergeFrom((MysqlxCrud.UpdateOperation)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.UpdateOperation.Builder mergeFrom(MysqlxCrud.UpdateOperation other) {
            if (other == MysqlxCrud.UpdateOperation.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasSource()) {
                  this.mergeSource(other.getSource());
               }

               if (other.hasOperation()) {
                  this.setOperation(other.getOperation());
               }

               if (other.hasValue()) {
                  this.mergeValue(other.getValue());
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasSource()) {
               return false;
            } else if (!this.hasOperation()) {
               return false;
            } else if (!this.getSource().isInitialized()) {
               return false;
            } else {
               return !this.hasValue() || this.getValue().isInitialized();
            }
         }

         public MysqlxCrud.UpdateOperation.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.UpdateOperation parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.UpdateOperation)MysqlxCrud.UpdateOperation.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.UpdateOperation)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasSource() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxExpr.ColumnIdentifier getSource() {
            return this.sourceBuilder_ == null ? this.source_ : (MysqlxExpr.ColumnIdentifier)this.sourceBuilder_.getMessage();
         }

         public MysqlxCrud.UpdateOperation.Builder setSource(MysqlxExpr.ColumnIdentifier value) {
            if (this.sourceBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.source_ = value;
               this.onChanged();
            } else {
               this.sourceBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder setSource(MysqlxExpr.ColumnIdentifier.Builder builderForValue) {
            if (this.sourceBuilder_ == null) {
               this.source_ = builderForValue.build();
               this.onChanged();
            } else {
               this.sourceBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder mergeSource(MysqlxExpr.ColumnIdentifier value) {
            if (this.sourceBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.source_ != MysqlxExpr.ColumnIdentifier.getDefaultInstance()) {
                  this.source_ = MysqlxExpr.ColumnIdentifier.newBuilder(this.source_).mergeFrom(value).buildPartial();
               } else {
                  this.source_ = value;
               }

               this.onChanged();
            } else {
               this.sourceBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder clearSource() {
            if (this.sourceBuilder_ == null) {
               this.source_ = MysqlxExpr.ColumnIdentifier.getDefaultInstance();
               this.onChanged();
            } else {
               this.sourceBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxExpr.ColumnIdentifier.Builder getSourceBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxExpr.ColumnIdentifier.Builder)this.getSourceFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ColumnIdentifierOrBuilder getSourceOrBuilder() {
            return (MysqlxExpr.ColumnIdentifierOrBuilder)(this.sourceBuilder_ != null ? (MysqlxExpr.ColumnIdentifierOrBuilder)this.sourceBuilder_.getMessageOrBuilder() : this.source_);
         }

         private SingleFieldBuilder<MysqlxExpr.ColumnIdentifier, MysqlxExpr.ColumnIdentifier.Builder, MysqlxExpr.ColumnIdentifierOrBuilder> getSourceFieldBuilder() {
            if (this.sourceBuilder_ == null) {
               this.sourceBuilder_ = new SingleFieldBuilder(this.getSource(), this.getParentForChildren(), this.isClean());
               this.source_ = null;
            }

            return this.sourceBuilder_;
         }

         public boolean hasOperation() {
            return (this.bitField0_ & 2) == 2;
         }

         public MysqlxCrud.UpdateOperation.UpdateType getOperation() {
            return this.operation_;
         }

         public MysqlxCrud.UpdateOperation.Builder setOperation(MysqlxCrud.UpdateOperation.UpdateType value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.operation_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.UpdateOperation.Builder clearOperation() {
            this.bitField0_ &= -3;
            this.operation_ = MysqlxCrud.UpdateOperation.UpdateType.SET;
            this.onChanged();
            return this;
         }

         public boolean hasValue() {
            return (this.bitField0_ & 4) == 4;
         }

         public MysqlxExpr.Expr getValue() {
            return this.valueBuilder_ == null ? this.value_ : (MysqlxExpr.Expr)this.valueBuilder_.getMessage();
         }

         public MysqlxCrud.UpdateOperation.Builder setValue(MysqlxExpr.Expr value) {
            if (this.valueBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.value_ = value;
               this.onChanged();
            } else {
               this.valueBuilder_.setMessage(value);
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder setValue(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.valueBuilder_ == null) {
               this.value_ = builderForValue.build();
               this.onChanged();
            } else {
               this.valueBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder mergeValue(MysqlxExpr.Expr value) {
            if (this.valueBuilder_ == null) {
               if ((this.bitField0_ & 4) == 4 && this.value_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.value_ = MysqlxExpr.Expr.newBuilder(this.value_).mergeFrom(value).buildPartial();
               } else {
                  this.value_ = value;
               }

               this.onChanged();
            } else {
               this.valueBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 4;
            return this;
         }

         public MysqlxCrud.UpdateOperation.Builder clearValue() {
            if (this.valueBuilder_ == null) {
               this.value_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.valueBuilder_.clear();
            }

            this.bitField0_ &= -5;
            return this;
         }

         public MysqlxExpr.Expr.Builder getValueBuilder() {
            this.bitField0_ |= 4;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getValueFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getValueOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.valueBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.valueBuilder_.getMessageOrBuilder() : this.value_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getValueFieldBuilder() {
            if (this.valueBuilder_ == null) {
               this.valueBuilder_ = new SingleFieldBuilder(this.getValue(), this.getParentForChildren(), this.isClean());
               this.value_ = null;
            }

            return this.valueBuilder_;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }

      public static enum UpdateType implements ProtocolMessageEnum {
         SET(0, 1),
         ITEM_REMOVE(1, 2),
         ITEM_SET(2, 3),
         ITEM_REPLACE(3, 4),
         ITEM_MERGE(4, 5),
         ARRAY_INSERT(5, 6),
         ARRAY_APPEND(6, 7);

         public static final int SET_VALUE = 1;
         public static final int ITEM_REMOVE_VALUE = 2;
         public static final int ITEM_SET_VALUE = 3;
         public static final int ITEM_REPLACE_VALUE = 4;
         public static final int ITEM_MERGE_VALUE = 5;
         public static final int ARRAY_INSERT_VALUE = 6;
         public static final int ARRAY_APPEND_VALUE = 7;
         private static EnumLiteMap<MysqlxCrud.UpdateOperation.UpdateType> internalValueMap = new EnumLiteMap<MysqlxCrud.UpdateOperation.UpdateType>() {
            public MysqlxCrud.UpdateOperation.UpdateType findValueByNumber(int number) {
               return MysqlxCrud.UpdateOperation.UpdateType.valueOf(number);
            }
         };
         private static final MysqlxCrud.UpdateOperation.UpdateType[] VALUES = values();
         private final int index;
         private final int value;

         public final int getNumber() {
            return this.value;
         }

         public static MysqlxCrud.UpdateOperation.UpdateType valueOf(int value) {
            switch(value) {
            case 1:
               return SET;
            case 2:
               return ITEM_REMOVE;
            case 3:
               return ITEM_SET;
            case 4:
               return ITEM_REPLACE;
            case 5:
               return ITEM_MERGE;
            case 6:
               return ARRAY_INSERT;
            case 7:
               return ARRAY_APPEND;
            default:
               return null;
            }
         }

         public static EnumLiteMap<MysqlxCrud.UpdateOperation.UpdateType> internalGetValueMap() {
            return internalValueMap;
         }

         public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor)getDescriptor().getValues().get(this.index);
         }

         public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
         }

         public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor)MysqlxCrud.UpdateOperation.getDescriptor().getEnumTypes().get(0);
         }

         public static MysqlxCrud.UpdateOperation.UpdateType valueOf(EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
               throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            } else {
               return VALUES[desc.getIndex()];
            }
         }

         private UpdateType(int index, int value) {
            this.index = index;
            this.value = value;
         }
      }
   }

   public interface UpdateOperationOrBuilder extends MessageOrBuilder {
      boolean hasSource();

      MysqlxExpr.ColumnIdentifier getSource();

      MysqlxExpr.ColumnIdentifierOrBuilder getSourceOrBuilder();

      boolean hasOperation();

      MysqlxCrud.UpdateOperation.UpdateType getOperation();

      boolean hasValue();

      MysqlxExpr.Expr getValue();

      MysqlxExpr.ExprOrBuilder getValueOrBuilder();
   }

   public static final class Order extends GeneratedMessage implements MysqlxCrud.OrderOrBuilder {
      private static final MysqlxCrud.Order defaultInstance = new MysqlxCrud.Order(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Order> PARSER = new AbstractParser<MysqlxCrud.Order>() {
         public MysqlxCrud.Order parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Order(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int EXPR_FIELD_NUMBER = 1;
      private MysqlxExpr.Expr expr_;
      public static final int DIRECTION_FIELD_NUMBER = 2;
      private MysqlxCrud.Order.Direction direction_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Order(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Order(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Order getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Order getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Order(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = false;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  MysqlxExpr.Expr.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.expr_.toBuilder();
                  }

                  this.expr_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.expr_);
                     this.expr_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 16:
                  int rawValue = input.readEnum();
                  MysqlxCrud.Order.Direction value = MysqlxCrud.Order.Direction.valueOf(rawValue);
                  if (value == null) {
                     unknownFields.mergeVarintField(2, rawValue);
                  } else {
                     this.bitField0_ |= 2;
                     this.direction_ = value;
                  }
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var13) {
            throw var13.setUnfinishedMessage(this);
         } catch (IOException var14) {
            throw (new InvalidProtocolBufferException(var14.getMessage())).setUnfinishedMessage(this);
         } finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Order_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Order.class, MysqlxCrud.Order.Builder.class);
      }

      public Parser<MysqlxCrud.Order> getParserForType() {
         return PARSER;
      }

      public boolean hasExpr() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxExpr.Expr getExpr() {
         return this.expr_;
      }

      public MysqlxExpr.ExprOrBuilder getExprOrBuilder() {
         return this.expr_;
      }

      public boolean hasDirection() {
         return (this.bitField0_ & 2) == 2;
      }

      public MysqlxCrud.Order.Direction getDirection() {
         return this.direction_;
      }

      private void initFields() {
         this.expr_ = MysqlxExpr.Expr.getDefaultInstance();
         this.direction_ = MysqlxCrud.Order.Direction.ASC;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasExpr()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getExpr().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.expr_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeEnum(2, this.direction_.getNumber());
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.expr_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeEnumSize(2, this.direction_.getNumber());
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Order parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Order)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Order parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Order)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Order parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Order)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Order parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Order)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Order parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Order)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Order parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Order)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Order parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Order)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Order parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Order)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Order parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Order)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Order parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Order)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Order.Builder newBuilder() {
         return MysqlxCrud.Order.Builder.create();
      }

      public MysqlxCrud.Order.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Order.Builder newBuilder(MysqlxCrud.Order prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Order.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Order.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Order.Builder builder = new MysqlxCrud.Order.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Order(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Order(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Order.Builder> implements MysqlxCrud.OrderOrBuilder {
         private int bitField0_;
         private MysqlxExpr.Expr expr_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> exprBuilder_;
         private MysqlxCrud.Order.Direction direction_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Order_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Order.class, MysqlxCrud.Order.Builder.class);
         }

         private Builder() {
            this.expr_ = MysqlxExpr.Expr.getDefaultInstance();
            this.direction_ = MysqlxCrud.Order.Direction.ASC;
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.expr_ = MysqlxExpr.Expr.getDefaultInstance();
            this.direction_ = MysqlxCrud.Order.Direction.ASC;
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Order.alwaysUseFieldBuilders) {
               this.getExprFieldBuilder();
            }

         }

         private static MysqlxCrud.Order.Builder create() {
            return new MysqlxCrud.Order.Builder();
         }

         public MysqlxCrud.Order.Builder clear() {
            super.clear();
            if (this.exprBuilder_ == null) {
               this.expr_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.exprBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.direction_ = MysqlxCrud.Order.Direction.ASC;
            this.bitField0_ &= -3;
            return this;
         }

         public MysqlxCrud.Order.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor;
         }

         public MysqlxCrud.Order getDefaultInstanceForType() {
            return MysqlxCrud.Order.getDefaultInstance();
         }

         public MysqlxCrud.Order build() {
            MysqlxCrud.Order result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Order buildPartial() {
            MysqlxCrud.Order result = new MysqlxCrud.Order(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.exprBuilder_ == null) {
               result.expr_ = this.expr_;
            } else {
               result.expr_ = (MysqlxExpr.Expr)this.exprBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.direction_ = this.direction_;
            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Order.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Order) {
               return this.mergeFrom((MysqlxCrud.Order)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Order.Builder mergeFrom(MysqlxCrud.Order other) {
            if (other == MysqlxCrud.Order.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasExpr()) {
                  this.mergeExpr(other.getExpr());
               }

               if (other.hasDirection()) {
                  this.setDirection(other.getDirection());
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasExpr()) {
               return false;
            } else {
               return this.getExpr().isInitialized();
            }
         }

         public MysqlxCrud.Order.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Order parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Order)MysqlxCrud.Order.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Order)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasExpr() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxExpr.Expr getExpr() {
            return this.exprBuilder_ == null ? this.expr_ : (MysqlxExpr.Expr)this.exprBuilder_.getMessage();
         }

         public MysqlxCrud.Order.Builder setExpr(MysqlxExpr.Expr value) {
            if (this.exprBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.expr_ = value;
               this.onChanged();
            } else {
               this.exprBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Order.Builder setExpr(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.exprBuilder_ == null) {
               this.expr_ = builderForValue.build();
               this.onChanged();
            } else {
               this.exprBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Order.Builder mergeExpr(MysqlxExpr.Expr value) {
            if (this.exprBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.expr_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.expr_ = MysqlxExpr.Expr.newBuilder(this.expr_).mergeFrom(value).buildPartial();
               } else {
                  this.expr_ = value;
               }

               this.onChanged();
            } else {
               this.exprBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Order.Builder clearExpr() {
            if (this.exprBuilder_ == null) {
               this.expr_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.exprBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxExpr.Expr.Builder getExprBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getExprFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getExprOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.exprBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.exprBuilder_.getMessageOrBuilder() : this.expr_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getExprFieldBuilder() {
            if (this.exprBuilder_ == null) {
               this.exprBuilder_ = new SingleFieldBuilder(this.getExpr(), this.getParentForChildren(), this.isClean());
               this.expr_ = null;
            }

            return this.exprBuilder_;
         }

         public boolean hasDirection() {
            return (this.bitField0_ & 2) == 2;
         }

         public MysqlxCrud.Order.Direction getDirection() {
            return this.direction_;
         }

         public MysqlxCrud.Order.Builder setDirection(MysqlxCrud.Order.Direction value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.direction_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Order.Builder clearDirection() {
            this.bitField0_ &= -3;
            this.direction_ = MysqlxCrud.Order.Direction.ASC;
            this.onChanged();
            return this;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }

      public static enum Direction implements ProtocolMessageEnum {
         ASC(0, 1),
         DESC(1, 2);

         public static final int ASC_VALUE = 1;
         public static final int DESC_VALUE = 2;
         private static EnumLiteMap<MysqlxCrud.Order.Direction> internalValueMap = new EnumLiteMap<MysqlxCrud.Order.Direction>() {
            public MysqlxCrud.Order.Direction findValueByNumber(int number) {
               return MysqlxCrud.Order.Direction.valueOf(number);
            }
         };
         private static final MysqlxCrud.Order.Direction[] VALUES = values();
         private final int index;
         private final int value;

         public final int getNumber() {
            return this.value;
         }

         public static MysqlxCrud.Order.Direction valueOf(int value) {
            switch(value) {
            case 1:
               return ASC;
            case 2:
               return DESC;
            default:
               return null;
            }
         }

         public static EnumLiteMap<MysqlxCrud.Order.Direction> internalGetValueMap() {
            return internalValueMap;
         }

         public final EnumValueDescriptor getValueDescriptor() {
            return (EnumValueDescriptor)getDescriptor().getValues().get(this.index);
         }

         public final EnumDescriptor getDescriptorForType() {
            return getDescriptor();
         }

         public static final EnumDescriptor getDescriptor() {
            return (EnumDescriptor)MysqlxCrud.Order.getDescriptor().getEnumTypes().get(0);
         }

         public static MysqlxCrud.Order.Direction valueOf(EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
               throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            } else {
               return VALUES[desc.getIndex()];
            }
         }

         private Direction(int index, int value) {
            this.index = index;
            this.value = value;
         }
      }
   }

   public interface OrderOrBuilder extends MessageOrBuilder {
      boolean hasExpr();

      MysqlxExpr.Expr getExpr();

      MysqlxExpr.ExprOrBuilder getExprOrBuilder();

      boolean hasDirection();

      MysqlxCrud.Order.Direction getDirection();
   }

   public static final class Limit extends GeneratedMessage implements MysqlxCrud.LimitOrBuilder {
      private static final MysqlxCrud.Limit defaultInstance = new MysqlxCrud.Limit(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Limit> PARSER = new AbstractParser<MysqlxCrud.Limit>() {
         public MysqlxCrud.Limit parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Limit(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int ROW_COUNT_FIELD_NUMBER = 1;
      private long rowCount_;
      public static final int OFFSET_FIELD_NUMBER = 2;
      private long offset_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Limit(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Limit(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Limit getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Limit getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Limit(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = false;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 8:
                  this.bitField0_ |= 1;
                  this.rowCount_ = input.readUInt64();
                  break;
               case 16:
                  this.bitField0_ |= 2;
                  this.offset_ = input.readUInt64();
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var11) {
            throw var11.setUnfinishedMessage(this);
         } catch (IOException var12) {
            throw (new InvalidProtocolBufferException(var12.getMessage())).setUnfinishedMessage(this);
         } finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Limit.class, MysqlxCrud.Limit.Builder.class);
      }

      public Parser<MysqlxCrud.Limit> getParserForType() {
         return PARSER;
      }

      public boolean hasRowCount() {
         return (this.bitField0_ & 1) == 1;
      }

      public long getRowCount() {
         return this.rowCount_;
      }

      public boolean hasOffset() {
         return (this.bitField0_ & 2) == 2;
      }

      public long getOffset() {
         return this.offset_;
      }

      private void initFields() {
         this.rowCount_ = 0L;
         this.offset_ = 0L;
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasRowCount()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeUInt64(1, this.rowCount_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeUInt64(2, this.offset_);
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeUInt64Size(1, this.rowCount_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeUInt64Size(2, this.offset_);
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Limit parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Limit parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Limit parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Limit parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Limit parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Limit parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Limit parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Limit)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Limit parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Limit)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Limit parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Limit parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Limit)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Limit.Builder newBuilder() {
         return MysqlxCrud.Limit.Builder.create();
      }

      public MysqlxCrud.Limit.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Limit.Builder newBuilder(MysqlxCrud.Limit prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Limit.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Limit.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Limit.Builder builder = new MysqlxCrud.Limit.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Limit(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Limit(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Limit.Builder> implements MysqlxCrud.LimitOrBuilder {
         private int bitField0_;
         private long rowCount_;
         private long offset_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Limit.class, MysqlxCrud.Limit.Builder.class);
         }

         private Builder() {
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Limit.alwaysUseFieldBuilders) {
            }

         }

         private static MysqlxCrud.Limit.Builder create() {
            return new MysqlxCrud.Limit.Builder();
         }

         public MysqlxCrud.Limit.Builder clear() {
            super.clear();
            this.rowCount_ = 0L;
            this.bitField0_ &= -2;
            this.offset_ = 0L;
            this.bitField0_ &= -3;
            return this;
         }

         public MysqlxCrud.Limit.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor;
         }

         public MysqlxCrud.Limit getDefaultInstanceForType() {
            return MysqlxCrud.Limit.getDefaultInstance();
         }

         public MysqlxCrud.Limit build() {
            MysqlxCrud.Limit result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Limit buildPartial() {
            MysqlxCrud.Limit result = new MysqlxCrud.Limit(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.rowCount_ = this.rowCount_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.offset_ = this.offset_;
            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Limit.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Limit) {
               return this.mergeFrom((MysqlxCrud.Limit)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Limit.Builder mergeFrom(MysqlxCrud.Limit other) {
            if (other == MysqlxCrud.Limit.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasRowCount()) {
                  this.setRowCount(other.getRowCount());
               }

               if (other.hasOffset()) {
                  this.setOffset(other.getOffset());
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            return this.hasRowCount();
         }

         public MysqlxCrud.Limit.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Limit parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Limit)MysqlxCrud.Limit.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Limit)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasRowCount() {
            return (this.bitField0_ & 1) == 1;
         }

         public long getRowCount() {
            return this.rowCount_;
         }

         public MysqlxCrud.Limit.Builder setRowCount(long value) {
            this.bitField0_ |= 1;
            this.rowCount_ = value;
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Limit.Builder clearRowCount() {
            this.bitField0_ &= -2;
            this.rowCount_ = 0L;
            this.onChanged();
            return this;
         }

         public boolean hasOffset() {
            return (this.bitField0_ & 2) == 2;
         }

         public long getOffset() {
            return this.offset_;
         }

         public MysqlxCrud.Limit.Builder setOffset(long value) {
            this.bitField0_ |= 2;
            this.offset_ = value;
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Limit.Builder clearOffset() {
            this.bitField0_ &= -3;
            this.offset_ = 0L;
            this.onChanged();
            return this;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface LimitOrBuilder extends MessageOrBuilder {
      boolean hasRowCount();

      long getRowCount();

      boolean hasOffset();

      long getOffset();
   }

   public static final class Collection extends GeneratedMessage implements MysqlxCrud.CollectionOrBuilder {
      private static final MysqlxCrud.Collection defaultInstance = new MysqlxCrud.Collection(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Collection> PARSER = new AbstractParser<MysqlxCrud.Collection>() {
         public MysqlxCrud.Collection parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Collection(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int NAME_FIELD_NUMBER = 1;
      private Object name_;
      public static final int SCHEMA_FIELD_NUMBER = 2;
      private Object schema_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Collection(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Collection(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Collection getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Collection getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Collection(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = false;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               ByteString bs;
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  bs = input.readBytes();
                  this.bitField0_ |= 1;
                  this.name_ = bs;
                  break;
               case 18:
                  bs = input.readBytes();
                  this.bitField0_ |= 2;
                  this.schema_ = bs;
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var12) {
            throw var12.setUnfinishedMessage(this);
         } catch (IOException var13) {
            throw (new InvalidProtocolBufferException(var13.getMessage())).setUnfinishedMessage(this);
         } finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Collection.class, MysqlxCrud.Collection.Builder.class);
      }

      public Parser<MysqlxCrud.Collection> getParserForType() {
         return PARSER;
      }

      public boolean hasName() {
         return (this.bitField0_ & 1) == 1;
      }

      public String getName() {
         Object ref = this.name_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.name_ = s;
            }

            return s;
         }
      }

      public ByteString getNameBytes() {
         Object ref = this.name_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public boolean hasSchema() {
         return (this.bitField0_ & 2) == 2;
      }

      public String getSchema() {
         Object ref = this.schema_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.schema_ = s;
            }

            return s;
         }
      }

      public ByteString getSchemaBytes() {
         Object ref = this.schema_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.schema_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      private void initFields() {
         this.name_ = "";
         this.schema_ = "";
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasName()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeBytes(1, this.getNameBytes());
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeBytes(2, this.getSchemaBytes());
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeBytesSize(1, this.getNameBytes());
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeBytesSize(2, this.getSchemaBytes());
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Collection parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Collection parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Collection parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Collection parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Collection parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Collection parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Collection parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Collection)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Collection parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Collection)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Collection parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Collection parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Collection)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Collection.Builder newBuilder() {
         return MysqlxCrud.Collection.Builder.create();
      }

      public MysqlxCrud.Collection.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Collection.Builder newBuilder(MysqlxCrud.Collection prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Collection.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Collection.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Collection.Builder builder = new MysqlxCrud.Collection.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Collection(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Collection(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Collection.Builder> implements MysqlxCrud.CollectionOrBuilder {
         private int bitField0_;
         private Object name_;
         private Object schema_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Collection.class, MysqlxCrud.Collection.Builder.class);
         }

         private Builder() {
            this.name_ = "";
            this.schema_ = "";
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.schema_ = "";
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Collection.alwaysUseFieldBuilders) {
            }

         }

         private static MysqlxCrud.Collection.Builder create() {
            return new MysqlxCrud.Collection.Builder();
         }

         public MysqlxCrud.Collection.Builder clear() {
            super.clear();
            this.name_ = "";
            this.bitField0_ &= -2;
            this.schema_ = "";
            this.bitField0_ &= -3;
            return this;
         }

         public MysqlxCrud.Collection.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor;
         }

         public MysqlxCrud.Collection getDefaultInstanceForType() {
            return MysqlxCrud.Collection.getDefaultInstance();
         }

         public MysqlxCrud.Collection build() {
            MysqlxCrud.Collection result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Collection buildPartial() {
            MysqlxCrud.Collection result = new MysqlxCrud.Collection(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.name_ = this.name_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.schema_ = this.schema_;
            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Collection.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Collection) {
               return this.mergeFrom((MysqlxCrud.Collection)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Collection.Builder mergeFrom(MysqlxCrud.Collection other) {
            if (other == MysqlxCrud.Collection.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasName()) {
                  this.bitField0_ |= 1;
                  this.name_ = other.name_;
                  this.onChanged();
               }

               if (other.hasSchema()) {
                  this.bitField0_ |= 2;
                  this.schema_ = other.schema_;
                  this.onChanged();
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            return this.hasName();
         }

         public MysqlxCrud.Collection.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Collection parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Collection)MysqlxCrud.Collection.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Collection)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
         }

         public String getName() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.name_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.name_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public MysqlxCrud.Collection.Builder setName(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.name_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Collection.Builder clearName() {
            this.bitField0_ &= -2;
            this.name_ = MysqlxCrud.Collection.getDefaultInstance().getName();
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Collection.Builder setNameBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.name_ = value;
               this.onChanged();
               return this;
            }
         }

         public boolean hasSchema() {
            return (this.bitField0_ & 2) == 2;
         }

         public String getSchema() {
            Object ref = this.schema_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.schema_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         public ByteString getSchemaBytes() {
            Object ref = this.schema_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.schema_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public MysqlxCrud.Collection.Builder setSchema(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.schema_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Collection.Builder clearSchema() {
            this.bitField0_ &= -3;
            this.schema_ = MysqlxCrud.Collection.getDefaultInstance().getSchema();
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Collection.Builder setSchemaBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.schema_ = value;
               this.onChanged();
               return this;
            }
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface CollectionOrBuilder extends MessageOrBuilder {
      boolean hasName();

      String getName();

      ByteString getNameBytes();

      boolean hasSchema();

      String getSchema();

      ByteString getSchemaBytes();
   }

   public static final class Projection extends GeneratedMessage implements MysqlxCrud.ProjectionOrBuilder {
      private static final MysqlxCrud.Projection defaultInstance = new MysqlxCrud.Projection(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Projection> PARSER = new AbstractParser<MysqlxCrud.Projection>() {
         public MysqlxCrud.Projection parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Projection(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int SOURCE_FIELD_NUMBER = 1;
      private MysqlxExpr.Expr source_;
      public static final int ALIAS_FIELD_NUMBER = 2;
      private Object alias_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Projection(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Projection(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Projection getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Projection getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Projection(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = false;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  MysqlxExpr.Expr.Builder subBuilder = null;
                  if ((this.bitField0_ & 1) == 1) {
                     subBuilder = this.source_.toBuilder();
                  }

                  this.source_ = (MysqlxExpr.Expr)input.readMessage(MysqlxExpr.Expr.PARSER, extensionRegistry);
                  if (subBuilder != null) {
                     subBuilder.mergeFrom(this.source_);
                     this.source_ = subBuilder.buildPartial();
                  }

                  this.bitField0_ |= 1;
                  break;
               case 18:
                  ByteString bs = input.readBytes();
                  this.bitField0_ |= 2;
                  this.alias_ = bs;
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var12) {
            throw var12.setUnfinishedMessage(this);
         } catch (IOException var13) {
            throw (new InvalidProtocolBufferException(var13.getMessage())).setUnfinishedMessage(this);
         } finally {
            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Projection.class, MysqlxCrud.Projection.Builder.class);
      }

      public Parser<MysqlxCrud.Projection> getParserForType() {
         return PARSER;
      }

      public boolean hasSource() {
         return (this.bitField0_ & 1) == 1;
      }

      public MysqlxExpr.Expr getSource() {
         return this.source_;
      }

      public MysqlxExpr.ExprOrBuilder getSourceOrBuilder() {
         return this.source_;
      }

      public boolean hasAlias() {
         return (this.bitField0_ & 2) == 2;
      }

      public String getAlias() {
         Object ref = this.alias_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.alias_ = s;
            }

            return s;
         }
      }

      public ByteString getAliasBytes() {
         Object ref = this.alias_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.alias_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      private void initFields() {
         this.source_ = MysqlxExpr.Expr.getDefaultInstance();
         this.alias_ = "";
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else if (!this.hasSource()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else if (!this.getSource().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
         } else {
            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeMessage(1, this.source_);
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeBytes(2, this.getAliasBytes());
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeMessageSize(1, this.source_);
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeBytesSize(2, this.getAliasBytes());
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Projection parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Projection parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Projection parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Projection parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Projection parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Projection parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Projection parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Projection)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Projection parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Projection)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Projection parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Projection parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Projection)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Projection.Builder newBuilder() {
         return MysqlxCrud.Projection.Builder.create();
      }

      public MysqlxCrud.Projection.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Projection.Builder newBuilder(MysqlxCrud.Projection prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Projection.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Projection.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Projection.Builder builder = new MysqlxCrud.Projection.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Projection(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Projection(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Projection.Builder> implements MysqlxCrud.ProjectionOrBuilder {
         private int bitField0_;
         private MysqlxExpr.Expr source_;
         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> sourceBuilder_;
         private Object alias_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Projection.class, MysqlxCrud.Projection.Builder.class);
         }

         private Builder() {
            this.source_ = MysqlxExpr.Expr.getDefaultInstance();
            this.alias_ = "";
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.source_ = MysqlxExpr.Expr.getDefaultInstance();
            this.alias_ = "";
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Projection.alwaysUseFieldBuilders) {
               this.getSourceFieldBuilder();
            }

         }

         private static MysqlxCrud.Projection.Builder create() {
            return new MysqlxCrud.Projection.Builder();
         }

         public MysqlxCrud.Projection.Builder clear() {
            super.clear();
            if (this.sourceBuilder_ == null) {
               this.source_ = MysqlxExpr.Expr.getDefaultInstance();
            } else {
               this.sourceBuilder_.clear();
            }

            this.bitField0_ &= -2;
            this.alias_ = "";
            this.bitField0_ &= -3;
            return this;
         }

         public MysqlxCrud.Projection.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor;
         }

         public MysqlxCrud.Projection getDefaultInstanceForType() {
            return MysqlxCrud.Projection.getDefaultInstance();
         }

         public MysqlxCrud.Projection build() {
            MysqlxCrud.Projection result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Projection buildPartial() {
            MysqlxCrud.Projection result = new MysqlxCrud.Projection(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            if (this.sourceBuilder_ == null) {
               result.source_ = this.source_;
            } else {
               result.source_ = (MysqlxExpr.Expr)this.sourceBuilder_.build();
            }

            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.alias_ = this.alias_;
            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Projection.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Projection) {
               return this.mergeFrom((MysqlxCrud.Projection)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Projection.Builder mergeFrom(MysqlxCrud.Projection other) {
            if (other == MysqlxCrud.Projection.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasSource()) {
                  this.mergeSource(other.getSource());
               }

               if (other.hasAlias()) {
                  this.bitField0_ |= 2;
                  this.alias_ = other.alias_;
                  this.onChanged();
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            if (!this.hasSource()) {
               return false;
            } else {
               return this.getSource().isInitialized();
            }
         }

         public MysqlxCrud.Projection.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Projection parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Projection)MysqlxCrud.Projection.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Projection)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasSource() {
            return (this.bitField0_ & 1) == 1;
         }

         public MysqlxExpr.Expr getSource() {
            return this.sourceBuilder_ == null ? this.source_ : (MysqlxExpr.Expr)this.sourceBuilder_.getMessage();
         }

         public MysqlxCrud.Projection.Builder setSource(MysqlxExpr.Expr value) {
            if (this.sourceBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.source_ = value;
               this.onChanged();
            } else {
               this.sourceBuilder_.setMessage(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Projection.Builder setSource(MysqlxExpr.Expr.Builder builderForValue) {
            if (this.sourceBuilder_ == null) {
               this.source_ = builderForValue.build();
               this.onChanged();
            } else {
               this.sourceBuilder_.setMessage(builderForValue.build());
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Projection.Builder mergeSource(MysqlxExpr.Expr value) {
            if (this.sourceBuilder_ == null) {
               if ((this.bitField0_ & 1) == 1 && this.source_ != MysqlxExpr.Expr.getDefaultInstance()) {
                  this.source_ = MysqlxExpr.Expr.newBuilder(this.source_).mergeFrom(value).buildPartial();
               } else {
                  this.source_ = value;
               }

               this.onChanged();
            } else {
               this.sourceBuilder_.mergeFrom(value);
            }

            this.bitField0_ |= 1;
            return this;
         }

         public MysqlxCrud.Projection.Builder clearSource() {
            if (this.sourceBuilder_ == null) {
               this.source_ = MysqlxExpr.Expr.getDefaultInstance();
               this.onChanged();
            } else {
               this.sourceBuilder_.clear();
            }

            this.bitField0_ &= -2;
            return this;
         }

         public MysqlxExpr.Expr.Builder getSourceBuilder() {
            this.bitField0_ |= 1;
            this.onChanged();
            return (MysqlxExpr.Expr.Builder)this.getSourceFieldBuilder().getBuilder();
         }

         public MysqlxExpr.ExprOrBuilder getSourceOrBuilder() {
            return (MysqlxExpr.ExprOrBuilder)(this.sourceBuilder_ != null ? (MysqlxExpr.ExprOrBuilder)this.sourceBuilder_.getMessageOrBuilder() : this.source_);
         }

         private SingleFieldBuilder<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getSourceFieldBuilder() {
            if (this.sourceBuilder_ == null) {
               this.sourceBuilder_ = new SingleFieldBuilder(this.getSource(), this.getParentForChildren(), this.isClean());
               this.source_ = null;
            }

            return this.sourceBuilder_;
         }

         public boolean hasAlias() {
            return (this.bitField0_ & 2) == 2;
         }

         public String getAlias() {
            Object ref = this.alias_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.alias_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         public ByteString getAliasBytes() {
            Object ref = this.alias_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.alias_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public MysqlxCrud.Projection.Builder setAlias(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.alias_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Projection.Builder clearAlias() {
            this.bitField0_ &= -3;
            this.alias_ = MysqlxCrud.Projection.getDefaultInstance().getAlias();
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Projection.Builder setAliasBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.alias_ = value;
               this.onChanged();
               return this;
            }
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface ProjectionOrBuilder extends MessageOrBuilder {
      boolean hasSource();

      MysqlxExpr.Expr getSource();

      MysqlxExpr.ExprOrBuilder getSourceOrBuilder();

      boolean hasAlias();

      String getAlias();

      ByteString getAliasBytes();
   }

   public static final class Column extends GeneratedMessage implements MysqlxCrud.ColumnOrBuilder {
      private static final MysqlxCrud.Column defaultInstance = new MysqlxCrud.Column(true);
      private final UnknownFieldSet unknownFields;
      public static Parser<MysqlxCrud.Column> PARSER = new AbstractParser<MysqlxCrud.Column>() {
         public MysqlxCrud.Column parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return new MysqlxCrud.Column(input, extensionRegistry);
         }
      };
      private int bitField0_;
      public static final int NAME_FIELD_NUMBER = 1;
      private Object name_;
      public static final int ALIAS_FIELD_NUMBER = 2;
      private Object alias_;
      public static final int DOCUMENT_PATH_FIELD_NUMBER = 3;
      private List<MysqlxExpr.DocumentPathItem> documentPath_;
      private byte memoizedIsInitialized;
      private int memoizedSerializedSize;
      private static final long serialVersionUID = 0L;

      private Column(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
         super(builder);
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = builder.getUnknownFields();
      }

      private Column(boolean noInit) {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.unknownFields = UnknownFieldSet.getDefaultInstance();
      }

      public static MysqlxCrud.Column getDefaultInstance() {
         return defaultInstance;
      }

      public MysqlxCrud.Column getDefaultInstanceForType() {
         return defaultInstance;
      }

      public final UnknownFieldSet getUnknownFields() {
         return this.unknownFields;
      }

      private Column(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         this.memoizedIsInitialized = -1;
         this.memoizedSerializedSize = -1;
         this.initFields();
         int mutable_bitField0_ = 0;
         com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

         try {
            boolean done = false;

            while(!done) {
               int tag = input.readTag();
               ByteString bs;
               switch(tag) {
               case 0:
                  done = true;
                  break;
               case 10:
                  bs = input.readBytes();
                  this.bitField0_ |= 1;
                  this.name_ = bs;
                  break;
               case 18:
                  bs = input.readBytes();
                  this.bitField0_ |= 2;
                  this.alias_ = bs;
                  break;
               case 26:
                  if ((mutable_bitField0_ & 4) != 4) {
                     this.documentPath_ = new ArrayList();
                     mutable_bitField0_ |= 4;
                  }

                  this.documentPath_.add(input.readMessage(MysqlxExpr.DocumentPathItem.PARSER, extensionRegistry));
                  break;
               default:
                  if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                     done = true;
                  }
               }
            }
         } catch (InvalidProtocolBufferException var12) {
            throw var12.setUnfinishedMessage(this);
         } catch (IOException var13) {
            throw (new InvalidProtocolBufferException(var13.getMessage())).setUnfinishedMessage(this);
         } finally {
            if ((mutable_bitField0_ & 4) == 4) {
               this.documentPath_ = Collections.unmodifiableList(this.documentPath_);
            }

            this.unknownFields = unknownFields.build();
            this.makeExtensionsImmutable();
         }

      }

      public static final Descriptor getDescriptor() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor;
      }

      protected FieldAccessorTable internalGetFieldAccessorTable() {
         return MysqlxCrud.internal_static_Mysqlx_Crud_Column_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Column.class, MysqlxCrud.Column.Builder.class);
      }

      public Parser<MysqlxCrud.Column> getParserForType() {
         return PARSER;
      }

      public boolean hasName() {
         return (this.bitField0_ & 1) == 1;
      }

      public String getName() {
         Object ref = this.name_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.name_ = s;
            }

            return s;
         }
      }

      public ByteString getNameBytes() {
         Object ref = this.name_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.name_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public boolean hasAlias() {
         return (this.bitField0_ & 2) == 2;
      }

      public String getAlias() {
         Object ref = this.alias_;
         if (ref instanceof String) {
            return (String)ref;
         } else {
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
               this.alias_ = s;
            }

            return s;
         }
      }

      public ByteString getAliasBytes() {
         Object ref = this.alias_;
         if (ref instanceof String) {
            ByteString b = ByteString.copyFromUtf8((String)ref);
            this.alias_ = b;
            return b;
         } else {
            return (ByteString)ref;
         }
      }

      public List<MysqlxExpr.DocumentPathItem> getDocumentPathList() {
         return this.documentPath_;
      }

      public List<? extends MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathOrBuilderList() {
         return this.documentPath_;
      }

      public int getDocumentPathCount() {
         return this.documentPath_.size();
      }

      public MysqlxExpr.DocumentPathItem getDocumentPath(int index) {
         return (MysqlxExpr.DocumentPathItem)this.documentPath_.get(index);
      }

      public MysqlxExpr.DocumentPathItemOrBuilder getDocumentPathOrBuilder(int index) {
         return (MysqlxExpr.DocumentPathItemOrBuilder)this.documentPath_.get(index);
      }

      private void initFields() {
         this.name_ = "";
         this.alias_ = "";
         this.documentPath_ = Collections.emptyList();
      }

      public final boolean isInitialized() {
         byte isInitialized = this.memoizedIsInitialized;
         if (isInitialized == 1) {
            return true;
         } else if (isInitialized == 0) {
            return false;
         } else {
            for(int i = 0; i < this.getDocumentPathCount(); ++i) {
               if (!this.getDocumentPath(i).isInitialized()) {
                  this.memoizedIsInitialized = 0;
                  return false;
               }
            }

            this.memoizedIsInitialized = 1;
            return true;
         }
      }

      public void writeTo(CodedOutputStream output) throws IOException {
         this.getSerializedSize();
         if ((this.bitField0_ & 1) == 1) {
            output.writeBytes(1, this.getNameBytes());
         }

         if ((this.bitField0_ & 2) == 2) {
            output.writeBytes(2, this.getAliasBytes());
         }

         for(int i = 0; i < this.documentPath_.size(); ++i) {
            output.writeMessage(3, (MessageLite)this.documentPath_.get(i));
         }

         this.getUnknownFields().writeTo(output);
      }

      public int getSerializedSize() {
         int size = this.memoizedSerializedSize;
         if (size != -1) {
            return size;
         } else {
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
               size += CodedOutputStream.computeBytesSize(1, this.getNameBytes());
            }

            if ((this.bitField0_ & 2) == 2) {
               size += CodedOutputStream.computeBytesSize(2, this.getAliasBytes());
            }

            for(int i = 0; i < this.documentPath_.size(); ++i) {
               size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.documentPath_.get(i));
            }

            size += this.getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
         }
      }

      protected Object writeReplace() throws ObjectStreamException {
         return super.writeReplace();
      }

      public static MysqlxCrud.Column parseFrom(ByteString data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Column)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Column parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Column)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Column parseFrom(byte[] data) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Column)PARSER.parseFrom(data);
      }

      public static MysqlxCrud.Column parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
         return (MysqlxCrud.Column)PARSER.parseFrom(data, extensionRegistry);
      }

      public static MysqlxCrud.Column parseFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Column)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Column parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Column)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Column parseDelimitedFrom(InputStream input) throws IOException {
         return (MysqlxCrud.Column)PARSER.parseDelimitedFrom(input);
      }

      public static MysqlxCrud.Column parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Column)PARSER.parseDelimitedFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Column parseFrom(CodedInputStream input) throws IOException {
         return (MysqlxCrud.Column)PARSER.parseFrom(input);
      }

      public static MysqlxCrud.Column parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
         return (MysqlxCrud.Column)PARSER.parseFrom(input, extensionRegistry);
      }

      public static MysqlxCrud.Column.Builder newBuilder() {
         return MysqlxCrud.Column.Builder.create();
      }

      public MysqlxCrud.Column.Builder newBuilderForType() {
         return newBuilder();
      }

      public static MysqlxCrud.Column.Builder newBuilder(MysqlxCrud.Column prototype) {
         return newBuilder().mergeFrom(prototype);
      }

      public MysqlxCrud.Column.Builder toBuilder() {
         return newBuilder(this);
      }

      protected MysqlxCrud.Column.Builder newBuilderForType(BuilderParent parent) {
         MysqlxCrud.Column.Builder builder = new MysqlxCrud.Column.Builder(parent);
         return builder;
      }

      // $FF: synthetic method
      Column(CodedInputStream x0, ExtensionRegistryLite x1, Object x2) throws InvalidProtocolBufferException {
         this(x0, x1);
      }

      // $FF: synthetic method
      Column(com.google.protobuf.GeneratedMessage.Builder x0, Object x1) {
         this(x0);
      }

      static {
         defaultInstance.initFields();
      }

      public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<MysqlxCrud.Column.Builder> implements MysqlxCrud.ColumnOrBuilder {
         private int bitField0_;
         private Object name_;
         private Object alias_;
         private List<MysqlxExpr.DocumentPathItem> documentPath_;
         private RepeatedFieldBuilder<MysqlxExpr.DocumentPathItem, MysqlxExpr.DocumentPathItem.Builder, MysqlxExpr.DocumentPathItemOrBuilder> documentPathBuilder_;

         public static final Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor;
         }

         protected FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Column_fieldAccessorTable.ensureFieldAccessorsInitialized(MysqlxCrud.Column.class, MysqlxCrud.Column.Builder.class);
         }

         private Builder() {
            this.name_ = "";
            this.alias_ = "";
            this.documentPath_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private Builder(BuilderParent parent) {
            super(parent);
            this.name_ = "";
            this.alias_ = "";
            this.documentPath_ = Collections.emptyList();
            this.maybeForceBuilderInitialization();
         }

         private void maybeForceBuilderInitialization() {
            if (MysqlxCrud.Column.alwaysUseFieldBuilders) {
               this.getDocumentPathFieldBuilder();
            }

         }

         private static MysqlxCrud.Column.Builder create() {
            return new MysqlxCrud.Column.Builder();
         }

         public MysqlxCrud.Column.Builder clear() {
            super.clear();
            this.name_ = "";
            this.bitField0_ &= -2;
            this.alias_ = "";
            this.bitField0_ &= -3;
            if (this.documentPathBuilder_ == null) {
               this.documentPath_ = Collections.emptyList();
               this.bitField0_ &= -5;
            } else {
               this.documentPathBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Column.Builder clone() {
            return create().mergeFrom(this.buildPartial());
         }

         public Descriptor getDescriptorForType() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor;
         }

         public MysqlxCrud.Column getDefaultInstanceForType() {
            return MysqlxCrud.Column.getDefaultInstance();
         }

         public MysqlxCrud.Column build() {
            MysqlxCrud.Column result = this.buildPartial();
            if (!result.isInitialized()) {
               throw newUninitializedMessageException(result);
            } else {
               return result;
            }
         }

         public MysqlxCrud.Column buildPartial() {
            MysqlxCrud.Column result = new MysqlxCrud.Column(this);
            int from_bitField0_ = this.bitField0_;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
               to_bitField0_ |= 1;
            }

            result.name_ = this.name_;
            if ((from_bitField0_ & 2) == 2) {
               to_bitField0_ |= 2;
            }

            result.alias_ = this.alias_;
            if (this.documentPathBuilder_ == null) {
               if ((this.bitField0_ & 4) == 4) {
                  this.documentPath_ = Collections.unmodifiableList(this.documentPath_);
                  this.bitField0_ &= -5;
               }

               result.documentPath_ = this.documentPath_;
            } else {
               result.documentPath_ = this.documentPathBuilder_.build();
            }

            result.bitField0_ = to_bitField0_;
            this.onBuilt();
            return result;
         }

         public MysqlxCrud.Column.Builder mergeFrom(Message other) {
            if (other instanceof MysqlxCrud.Column) {
               return this.mergeFrom((MysqlxCrud.Column)other);
            } else {
               super.mergeFrom(other);
               return this;
            }
         }

         public MysqlxCrud.Column.Builder mergeFrom(MysqlxCrud.Column other) {
            if (other == MysqlxCrud.Column.getDefaultInstance()) {
               return this;
            } else {
               if (other.hasName()) {
                  this.bitField0_ |= 1;
                  this.name_ = other.name_;
                  this.onChanged();
               }

               if (other.hasAlias()) {
                  this.bitField0_ |= 2;
                  this.alias_ = other.alias_;
                  this.onChanged();
               }

               if (this.documentPathBuilder_ == null) {
                  if (!other.documentPath_.isEmpty()) {
                     if (this.documentPath_.isEmpty()) {
                        this.documentPath_ = other.documentPath_;
                        this.bitField0_ &= -5;
                     } else {
                        this.ensureDocumentPathIsMutable();
                        this.documentPath_.addAll(other.documentPath_);
                     }

                     this.onChanged();
                  }
               } else if (!other.documentPath_.isEmpty()) {
                  if (this.documentPathBuilder_.isEmpty()) {
                     this.documentPathBuilder_.dispose();
                     this.documentPathBuilder_ = null;
                     this.documentPath_ = other.documentPath_;
                     this.bitField0_ &= -5;
                     this.documentPathBuilder_ = MysqlxCrud.Column.alwaysUseFieldBuilders ? this.getDocumentPathFieldBuilder() : null;
                  } else {
                     this.documentPathBuilder_.addAllMessages(other.documentPath_);
                  }
               }

               this.mergeUnknownFields(other.getUnknownFields());
               return this;
            }
         }

         public final boolean isInitialized() {
            for(int i = 0; i < this.getDocumentPathCount(); ++i) {
               if (!this.getDocumentPath(i).isInitialized()) {
                  return false;
               }
            }

            return true;
         }

         public MysqlxCrud.Column.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            MysqlxCrud.Column parsedMessage = null;

            try {
               parsedMessage = (MysqlxCrud.Column)MysqlxCrud.Column.PARSER.parsePartialFrom(input, extensionRegistry);
            } catch (InvalidProtocolBufferException var8) {
               parsedMessage = (MysqlxCrud.Column)var8.getUnfinishedMessage();
               throw var8;
            } finally {
               if (parsedMessage != null) {
                  this.mergeFrom(parsedMessage);
               }

            }

            return this;
         }

         public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
         }

         public String getName() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.name_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.name_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public MysqlxCrud.Column.Builder setName(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.name_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Column.Builder clearName() {
            this.bitField0_ &= -2;
            this.name_ = MysqlxCrud.Column.getDefaultInstance().getName();
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Column.Builder setNameBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 1;
               this.name_ = value;
               this.onChanged();
               return this;
            }
         }

         public boolean hasAlias() {
            return (this.bitField0_ & 2) == 2;
         }

         public String getAlias() {
            Object ref = this.alias_;
            if (!(ref instanceof String)) {
               ByteString bs = (ByteString)ref;
               String s = bs.toStringUtf8();
               if (bs.isValidUtf8()) {
                  this.alias_ = s;
               }

               return s;
            } else {
               return (String)ref;
            }
         }

         public ByteString getAliasBytes() {
            Object ref = this.alias_;
            if (ref instanceof String) {
               ByteString b = ByteString.copyFromUtf8((String)ref);
               this.alias_ = b;
               return b;
            } else {
               return (ByteString)ref;
            }
         }

         public MysqlxCrud.Column.Builder setAlias(String value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.alias_ = value;
               this.onChanged();
               return this;
            }
         }

         public MysqlxCrud.Column.Builder clearAlias() {
            this.bitField0_ &= -3;
            this.alias_ = MysqlxCrud.Column.getDefaultInstance().getAlias();
            this.onChanged();
            return this;
         }

         public MysqlxCrud.Column.Builder setAliasBytes(ByteString value) {
            if (value == null) {
               throw new NullPointerException();
            } else {
               this.bitField0_ |= 2;
               this.alias_ = value;
               this.onChanged();
               return this;
            }
         }

         private void ensureDocumentPathIsMutable() {
            if ((this.bitField0_ & 4) != 4) {
               this.documentPath_ = new ArrayList(this.documentPath_);
               this.bitField0_ |= 4;
            }

         }

         public List<MysqlxExpr.DocumentPathItem> getDocumentPathList() {
            return this.documentPathBuilder_ == null ? Collections.unmodifiableList(this.documentPath_) : this.documentPathBuilder_.getMessageList();
         }

         public int getDocumentPathCount() {
            return this.documentPathBuilder_ == null ? this.documentPath_.size() : this.documentPathBuilder_.getCount();
         }

         public MysqlxExpr.DocumentPathItem getDocumentPath(int index) {
            return this.documentPathBuilder_ == null ? (MysqlxExpr.DocumentPathItem)this.documentPath_.get(index) : (MysqlxExpr.DocumentPathItem)this.documentPathBuilder_.getMessage(index);
         }

         public MysqlxCrud.Column.Builder setDocumentPath(int index, MysqlxExpr.DocumentPathItem value) {
            if (this.documentPathBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureDocumentPathIsMutable();
               this.documentPath_.set(index, value);
               this.onChanged();
            } else {
               this.documentPathBuilder_.setMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Column.Builder setDocumentPath(int index, MysqlxExpr.DocumentPathItem.Builder builderForValue) {
            if (this.documentPathBuilder_ == null) {
               this.ensureDocumentPathIsMutable();
               this.documentPath_.set(index, builderForValue.build());
               this.onChanged();
            } else {
               this.documentPathBuilder_.setMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Column.Builder addDocumentPath(MysqlxExpr.DocumentPathItem value) {
            if (this.documentPathBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureDocumentPathIsMutable();
               this.documentPath_.add(value);
               this.onChanged();
            } else {
               this.documentPathBuilder_.addMessage(value);
            }

            return this;
         }

         public MysqlxCrud.Column.Builder addDocumentPath(int index, MysqlxExpr.DocumentPathItem value) {
            if (this.documentPathBuilder_ == null) {
               if (value == null) {
                  throw new NullPointerException();
               }

               this.ensureDocumentPathIsMutable();
               this.documentPath_.add(index, value);
               this.onChanged();
            } else {
               this.documentPathBuilder_.addMessage(index, value);
            }

            return this;
         }

         public MysqlxCrud.Column.Builder addDocumentPath(MysqlxExpr.DocumentPathItem.Builder builderForValue) {
            if (this.documentPathBuilder_ == null) {
               this.ensureDocumentPathIsMutable();
               this.documentPath_.add(builderForValue.build());
               this.onChanged();
            } else {
               this.documentPathBuilder_.addMessage(builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Column.Builder addDocumentPath(int index, MysqlxExpr.DocumentPathItem.Builder builderForValue) {
            if (this.documentPathBuilder_ == null) {
               this.ensureDocumentPathIsMutable();
               this.documentPath_.add(index, builderForValue.build());
               this.onChanged();
            } else {
               this.documentPathBuilder_.addMessage(index, builderForValue.build());
            }

            return this;
         }

         public MysqlxCrud.Column.Builder addAllDocumentPath(Iterable<? extends MysqlxExpr.DocumentPathItem> values) {
            if (this.documentPathBuilder_ == null) {
               this.ensureDocumentPathIsMutable();
               com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.documentPath_);
               this.onChanged();
            } else {
               this.documentPathBuilder_.addAllMessages(values);
            }

            return this;
         }

         public MysqlxCrud.Column.Builder clearDocumentPath() {
            if (this.documentPathBuilder_ == null) {
               this.documentPath_ = Collections.emptyList();
               this.bitField0_ &= -5;
               this.onChanged();
            } else {
               this.documentPathBuilder_.clear();
            }

            return this;
         }

         public MysqlxCrud.Column.Builder removeDocumentPath(int index) {
            if (this.documentPathBuilder_ == null) {
               this.ensureDocumentPathIsMutable();
               this.documentPath_.remove(index);
               this.onChanged();
            } else {
               this.documentPathBuilder_.remove(index);
            }

            return this;
         }

         public MysqlxExpr.DocumentPathItem.Builder getDocumentPathBuilder(int index) {
            return (MysqlxExpr.DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().getBuilder(index);
         }

         public MysqlxExpr.DocumentPathItemOrBuilder getDocumentPathOrBuilder(int index) {
            return this.documentPathBuilder_ == null ? (MysqlxExpr.DocumentPathItemOrBuilder)this.documentPath_.get(index) : (MysqlxExpr.DocumentPathItemOrBuilder)this.documentPathBuilder_.getMessageOrBuilder(index);
         }

         public List<? extends MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathOrBuilderList() {
            return this.documentPathBuilder_ != null ? this.documentPathBuilder_.getMessageOrBuilderList() : Collections.unmodifiableList(this.documentPath_);
         }

         public MysqlxExpr.DocumentPathItem.Builder addDocumentPathBuilder() {
            return (MysqlxExpr.DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().addBuilder(MysqlxExpr.DocumentPathItem.getDefaultInstance());
         }

         public MysqlxExpr.DocumentPathItem.Builder addDocumentPathBuilder(int index) {
            return (MysqlxExpr.DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().addBuilder(index, MysqlxExpr.DocumentPathItem.getDefaultInstance());
         }

         public List<MysqlxExpr.DocumentPathItem.Builder> getDocumentPathBuilderList() {
            return this.getDocumentPathFieldBuilder().getBuilderList();
         }

         private RepeatedFieldBuilder<MysqlxExpr.DocumentPathItem, MysqlxExpr.DocumentPathItem.Builder, MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathFieldBuilder() {
            if (this.documentPathBuilder_ == null) {
               this.documentPathBuilder_ = new RepeatedFieldBuilder(this.documentPath_, (this.bitField0_ & 4) == 4, this.getParentForChildren(), this.isClean());
               this.documentPath_ = null;
            }

            return this.documentPathBuilder_;
         }

         // $FF: synthetic method
         Builder(BuilderParent x0, Object x1) {
            this(x0);
         }
      }
   }

   public interface ColumnOrBuilder extends MessageOrBuilder {
      boolean hasName();

      String getName();

      ByteString getNameBytes();

      boolean hasAlias();

      String getAlias();

      ByteString getAliasBytes();

      List<MysqlxExpr.DocumentPathItem> getDocumentPathList();

      MysqlxExpr.DocumentPathItem getDocumentPath(int var1);

      int getDocumentPathCount();

      List<? extends MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathOrBuilderList();

      MysqlxExpr.DocumentPathItemOrBuilder getDocumentPathOrBuilder(int var1);
   }

   public static enum DataModel implements ProtocolMessageEnum {
      DOCUMENT(0, 1),
      TABLE(1, 2);

      public static final int DOCUMENT_VALUE = 1;
      public static final int TABLE_VALUE = 2;
      private static EnumLiteMap<MysqlxCrud.DataModel> internalValueMap = new EnumLiteMap<MysqlxCrud.DataModel>() {
         public MysqlxCrud.DataModel findValueByNumber(int number) {
            return MysqlxCrud.DataModel.valueOf(number);
         }
      };
      private static final MysqlxCrud.DataModel[] VALUES = values();
      private final int index;
      private final int value;

      public final int getNumber() {
         return this.value;
      }

      public static MysqlxCrud.DataModel valueOf(int value) {
         switch(value) {
         case 1:
            return DOCUMENT;
         case 2:
            return TABLE;
         default:
            return null;
         }
      }

      public static EnumLiteMap<MysqlxCrud.DataModel> internalGetValueMap() {
         return internalValueMap;
      }

      public final EnumValueDescriptor getValueDescriptor() {
         return (EnumValueDescriptor)getDescriptor().getValues().get(this.index);
      }

      public final EnumDescriptor getDescriptorForType() {
         return getDescriptor();
      }

      public static final EnumDescriptor getDescriptor() {
         return (EnumDescriptor)MysqlxCrud.getDescriptor().getEnumTypes().get(0);
      }

      public static MysqlxCrud.DataModel valueOf(EnumValueDescriptor desc) {
         if (desc.getType() != getDescriptor()) {
            throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
         } else {
            return VALUES[desc.getIndex()];
         }
      }

      private DataModel(int index, int value) {
         this.index = index;
         this.value = value;
      }
   }
}
