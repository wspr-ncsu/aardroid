// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jnsaf.server.jnsaf_grpc

@SerialVersionUID(0L)
final case class TaintAnalysisResponse(
    taintResult: scala.Option[org.argus.jawa.flow.taint_result.TaintResult] = None
    ) extends scalapb.GeneratedMessage with scalapb.Message[TaintAnalysisResponse] with scalapb.lenses.Updatable[TaintAnalysisResponse] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (taintResult.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(taintResult.get.serializedSize) + taintResult.get.serializedSize }
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
      taintResult.foreach { __v =>
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse = {
      var __taintResult = this.taintResult
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __taintResult = Option(_root_.scalapb.LiteParser.readMessage(_input__, __taintResult.getOrElse(org.argus.jawa.flow.taint_result.TaintResult.defaultInstance)))
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse(
          taintResult = __taintResult
      )
    }
    def getTaintResult: org.argus.jawa.flow.taint_result.TaintResult = taintResult.getOrElse(org.argus.jawa.flow.taint_result.TaintResult.defaultInstance)
    def clearTaintResult: TaintAnalysisResponse = copy(taintResult = None)
    def withTaintResult(__v: org.argus.jawa.flow.taint_result.TaintResult): TaintAnalysisResponse = copy(taintResult = Option(__v))
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => taintResult.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => taintResult.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse
}

object TaintAnalysisResponse extends scalapb.GeneratedMessageCompanion[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse(
      __fieldsMap.get(__fields.get(0)).asInstanceOf[scala.Option[org.argus.jawa.flow.taint_result.TaintResult]]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[scala.Option[org.argus.jawa.flow.taint_result.TaintResult]])
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = JnsafGrpcProto.javaDescriptor.getMessageTypes.get(3)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = JnsafGrpcProto.scalaDescriptor.messages(3)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = org.argus.jawa.flow.taint_result.TaintResult
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse(
  )
  implicit class TaintAnalysisResponseLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse](_l) {
    def taintResult: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.taint_result.TaintResult] = field(_.getTaintResult)((c_, f_) => c_.copy(taintResult = Option(f_)))
    def optionalTaintResult: _root_.scalapb.lenses.Lens[UpperPB, scala.Option[org.argus.jawa.flow.taint_result.TaintResult]] = field(_.taintResult)((c_, f_) => c_.copy(taintResult = f_))
  }
  final val TAINT_RESULT_FIELD_NUMBER = 1
}
