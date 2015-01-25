// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConfigBook.proto

package net.spright.theater.protos;

public final class ConfigBookProtos {
  private ConfigBookProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface ConfigBookOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required string playerPath = 1;
    /**
     * <code>required string playerPath = 1;</code>
     */
    boolean hasPlayerPath();
    /**
     * <code>required string playerPath = 1;</code>
     */
    java.lang.String getPlayerPath();
    /**
     * <code>required string playerPath = 1;</code>
     */
    com.google.protobuf.ByteString
        getPlayerPathBytes();

    // required string movieDir = 2;
    /**
     * <code>required string movieDir = 2;</code>
     */
    boolean hasMovieDir();
    /**
     * <code>required string movieDir = 2;</code>
     */
    java.lang.String getMovieDir();
    /**
     * <code>required string movieDir = 2;</code>
     */
    com.google.protobuf.ByteString
        getMovieDirBytes();
  }
  /**
   * Protobuf type {@code protos.ConfigBook}
   */
  public static final class ConfigBook extends
      com.google.protobuf.GeneratedMessage
      implements ConfigBookOrBuilder {
    // Use ConfigBook.newBuilder() to construct.
    private ConfigBook(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private ConfigBook(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final ConfigBook defaultInstance;
    public static ConfigBook getDefaultInstance() {
      return defaultInstance;
    }

    public ConfigBook getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private ConfigBook(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              playerPath_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              movieDir_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return net.spright.theater.protos.ConfigBookProtos.internal_static_protos_ConfigBook_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return net.spright.theater.protos.ConfigBookProtos.internal_static_protos_ConfigBook_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              net.spright.theater.protos.ConfigBookProtos.ConfigBook.class, net.spright.theater.protos.ConfigBookProtos.ConfigBook.Builder.class);
    }

    public static com.google.protobuf.Parser<ConfigBook> PARSER =
        new com.google.protobuf.AbstractParser<ConfigBook>() {
      public ConfigBook parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ConfigBook(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<ConfigBook> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string playerPath = 1;
    public static final int PLAYERPATH_FIELD_NUMBER = 1;
    private java.lang.Object playerPath_;
    /**
     * <code>required string playerPath = 1;</code>
     */
    public boolean hasPlayerPath() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string playerPath = 1;</code>
     */
    public java.lang.String getPlayerPath() {
      java.lang.Object ref = playerPath_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          playerPath_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string playerPath = 1;</code>
     */
    public com.google.protobuf.ByteString
        getPlayerPathBytes() {
      java.lang.Object ref = playerPath_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        playerPath_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required string movieDir = 2;
    public static final int MOVIEDIR_FIELD_NUMBER = 2;
    private java.lang.Object movieDir_;
    /**
     * <code>required string movieDir = 2;</code>
     */
    public boolean hasMovieDir() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string movieDir = 2;</code>
     */
    public java.lang.String getMovieDir() {
      java.lang.Object ref = movieDir_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          movieDir_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string movieDir = 2;</code>
     */
    public com.google.protobuf.ByteString
        getMovieDirBytes() {
      java.lang.Object ref = movieDir_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        movieDir_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      playerPath_ = "";
      movieDir_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasPlayerPath()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasMovieDir()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getPlayerPathBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getMovieDirBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getPlayerPathBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getMovieDirBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static net.spright.theater.protos.ConfigBookProtos.ConfigBook parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(net.spright.theater.protos.ConfigBookProtos.ConfigBook prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protos.ConfigBook}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements net.spright.theater.protos.ConfigBookProtos.ConfigBookOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return net.spright.theater.protos.ConfigBookProtos.internal_static_protos_ConfigBook_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return net.spright.theater.protos.ConfigBookProtos.internal_static_protos_ConfigBook_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                net.spright.theater.protos.ConfigBookProtos.ConfigBook.class, net.spright.theater.protos.ConfigBookProtos.ConfigBook.Builder.class);
      }

      // Construct using net.spright.theater.protos.ConfigBookProtos.ConfigBook.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        playerPath_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        movieDir_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return net.spright.theater.protos.ConfigBookProtos.internal_static_protos_ConfigBook_descriptor;
      }

      public net.spright.theater.protos.ConfigBookProtos.ConfigBook getDefaultInstanceForType() {
        return net.spright.theater.protos.ConfigBookProtos.ConfigBook.getDefaultInstance();
      }

      public net.spright.theater.protos.ConfigBookProtos.ConfigBook build() {
        net.spright.theater.protos.ConfigBookProtos.ConfigBook result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public net.spright.theater.protos.ConfigBookProtos.ConfigBook buildPartial() {
        net.spright.theater.protos.ConfigBookProtos.ConfigBook result = new net.spright.theater.protos.ConfigBookProtos.ConfigBook(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.playerPath_ = playerPath_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.movieDir_ = movieDir_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof net.spright.theater.protos.ConfigBookProtos.ConfigBook) {
          return mergeFrom((net.spright.theater.protos.ConfigBookProtos.ConfigBook)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(net.spright.theater.protos.ConfigBookProtos.ConfigBook other) {
        if (other == net.spright.theater.protos.ConfigBookProtos.ConfigBook.getDefaultInstance()) return this;
        if (other.hasPlayerPath()) {
          bitField0_ |= 0x00000001;
          playerPath_ = other.playerPath_;
          onChanged();
        }
        if (other.hasMovieDir()) {
          bitField0_ |= 0x00000002;
          movieDir_ = other.movieDir_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasPlayerPath()) {
          
          return false;
        }
        if (!hasMovieDir()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        net.spright.theater.protos.ConfigBookProtos.ConfigBook parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (net.spright.theater.protos.ConfigBookProtos.ConfigBook) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required string playerPath = 1;
      private java.lang.Object playerPath_ = "";
      /**
       * <code>required string playerPath = 1;</code>
       */
      public boolean hasPlayerPath() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string playerPath = 1;</code>
       */
      public java.lang.String getPlayerPath() {
        java.lang.Object ref = playerPath_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          playerPath_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string playerPath = 1;</code>
       */
      public com.google.protobuf.ByteString
          getPlayerPathBytes() {
        java.lang.Object ref = playerPath_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          playerPath_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string playerPath = 1;</code>
       */
      public Builder setPlayerPath(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        playerPath_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string playerPath = 1;</code>
       */
      public Builder clearPlayerPath() {
        bitField0_ = (bitField0_ & ~0x00000001);
        playerPath_ = getDefaultInstance().getPlayerPath();
        onChanged();
        return this;
      }
      /**
       * <code>required string playerPath = 1;</code>
       */
      public Builder setPlayerPathBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        playerPath_ = value;
        onChanged();
        return this;
      }

      // required string movieDir = 2;
      private java.lang.Object movieDir_ = "";
      /**
       * <code>required string movieDir = 2;</code>
       */
      public boolean hasMovieDir() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string movieDir = 2;</code>
       */
      public java.lang.String getMovieDir() {
        java.lang.Object ref = movieDir_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          movieDir_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string movieDir = 2;</code>
       */
      public com.google.protobuf.ByteString
          getMovieDirBytes() {
        java.lang.Object ref = movieDir_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          movieDir_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string movieDir = 2;</code>
       */
      public Builder setMovieDir(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        movieDir_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string movieDir = 2;</code>
       */
      public Builder clearMovieDir() {
        bitField0_ = (bitField0_ & ~0x00000002);
        movieDir_ = getDefaultInstance().getMovieDir();
        onChanged();
        return this;
      }
      /**
       * <code>required string movieDir = 2;</code>
       */
      public Builder setMovieDirBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        movieDir_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protos.ConfigBook)
    }

    static {
      defaultInstance = new ConfigBook(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:protos.ConfigBook)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protos_ConfigBook_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protos_ConfigBook_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020ConfigBook.proto\022\006protos\"2\n\nConfigBook" +
      "\022\022\n\nplayerPath\030\001 \002(\t\022\020\n\010movieDir\030\002 \002(\tB." +
      "\n\032net.spright.theater.protosB\020ConfigBook" +
      "Protos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_protos_ConfigBook_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_protos_ConfigBook_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_protos_ConfigBook_descriptor,
              new java.lang.String[] { "PlayerPath", "MovieDir", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}