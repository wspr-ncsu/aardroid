// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jnsaf.server.jnsaf_grpc

@SerialVersionUID(0L)
final case class GetSummaryRequest(
    apkDigest: _root_.scala.Predef.String = "",
    componentName: _root_.scala.Predef.String = "",
    signature: _root_.scala.Predef.String = "",
    gen: _root_.scala.Boolean = false,
    depth: _root_.scala.Int = 0
    ) extends scalapb.GeneratedMessage with scalapb.Message[GetSummaryRequest] with scalapb.lenses.Updatable[GetSummaryRequest] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (apkDigest != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, apkDigest) }
      if (componentName != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, componentName) }
      if (signature != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(3, signature) }
      if (gen != false) { __size += _root_.com.google.protobuf.CodedOutputStream.computeBoolSize(4, gen) }
      if (depth != 0) { __size += _root_.com.google.protobuf.CodedOutputStream.computeInt32Size(5, depth) }
      __size
    }
    final override def serializedSize: _root_.scala.Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      {
        val __v = apkDigest
        if (__v != "") {
          _output__.writeString(1, __v)
        }
      };
      {
        val __v = componentName
        if (__v != "") {
          _output__.writeString(2, __v)
        }
      };
      {
        val __v = signature
        if (__v != "") {
          _output__.writeString(3, __v)
        }
      };
      {
        val __v = gen
        if (__v != false) {
          _output__.writeBool(4, __v)
        }
      };
      {
        val __v = depth
        if (__v != 0) {
          _output__.writeInt32(5, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest = {
      var __apkDigest = this.apkDigest
      var __componentName = this.componentName
      var __signature = this.signature
      var __gen = this.gen
      var __depth = this.depth
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __apkDigest = _input__.readString()
          case 18 =>
            __componentName = _input__.readString()
          case 26 =>
            __signature = _input__.readString()
          case 32 =>
            __gen = _input__.readBool()
          case 40 =>
            __depth = _input__.readInt32()
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest(
          apkDigest = __apkDigest,
          componentName = __componentName,
          signature = __signature,
          gen = __gen,
          depth = __depth
      )
    }
    def withApkDigest(__v: _root_.scala.Predef.String): GetSummaryRequest = copy(apkDigest = __v)
    def withComponentName(__v: _root_.scala.Predef.String): GetSummaryRequest = copy(componentName = __v)
    def withSignature(__v: _root_.scala.Predef.String): GetSummaryRequest = copy(signature = __v)
    def withGen(__v: _root_.scala.Boolean): GetSummaryRequest = copy(gen = __v)
    def withDepth(__v: _root_.scala.Int): GetSummaryRequest = copy(depth = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = apkDigest
          if (__t != "") __t else null
        }
        case 2 => {
          val __t = componentName
          if (__t != "") __t else null
        }
        case 3 => {
          val __t = signature
          if (__t != "") __t else null
        }
        case 4 => {
          val __t = gen
          if (__t != false) __t else null
        }
        case 5 => {
          val __t = depth
          if (__t != 0) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(apkDigest)
        case 2 => _root_.scalapb.descriptors.PString(componentName)
        case 3 => _root_.scalapb.descriptors.PString(signature)
        case 4 => _root_.scalapb.descriptors.PBoolean(gen)
        case 5 => _root_.scalapb.descriptors.PInt(depth)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest
}

object GetSummaryRequest extends scalapb.GeneratedMessageCompanion[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest(
      __fieldsMap.getOrElse(__fields.get(0), "").asInstanceOf[_root_.scala.Predef.String],
      __fieldsMap.getOrElse(__fields.get(1), "").asInstanceOf[_root_.scala.Predef.String],
      __fieldsMap.getOrElse(__fields.get(2), "").asInstanceOf[_root_.scala.Predef.String],
      __fieldsMap.getOrElse(__fields.get(3), false).asInstanceOf[_root_.scala.Boolean],
      __fieldsMap.getOrElse(__fields.get(4), 0).asInstanceOf[_root_.scala.Int]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(3).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(4).get).map(_.as[_root_.scala.Boolean]).getOrElse(false),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(5).get).map(_.as[_root_.scala.Int]).getOrElse(0)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = JnsafGrpcProto.javaDescriptor.getMessageTypes.get(4)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = JnsafGrpcProto.scalaDescriptor.messages(4)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest(
  )
  implicit class GetSummaryRequestLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest](_l) {
    def apkDigest: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.apkDigest)((c_, f_) => c_.copy(apkDigest = f_))
    def componentName: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.componentName)((c_, f_) => c_.copy(componentName = f_))
    def signature: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.signature)((c_, f_) => c_.copy(signature = f_))
    def gen: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Boolean] = field(_.gen)((c_, f_) => c_.copy(gen = f_))
    def depth: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Int] = field(_.depth)((c_, f_) => c_.copy(depth = f_))
  }
  final val APK_DIGEST_FIELD_NUMBER = 1
  final val COMPONENT_NAME_FIELD_NUMBER = 2
  final val SIGNATURE_FIELD_NUMBER = 3
  final val GEN_FIELD_NUMBER = 4
  final val DEPTH_FIELD_NUMBER = 5
}
