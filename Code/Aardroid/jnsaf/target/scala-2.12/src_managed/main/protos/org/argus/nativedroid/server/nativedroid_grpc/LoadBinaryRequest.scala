// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.nativedroid.server.nativedroid_grpc

@SerialVersionUID(0L)
final case class LoadBinaryRequest(
    buffer: _root_.com.google.protobuf.ByteString = _root_.com.google.protobuf.ByteString.EMPTY
    ) extends scalapb.GeneratedMessage with scalapb.Message[LoadBinaryRequest] with scalapb.lenses.Updatable[LoadBinaryRequest] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (buffer != _root_.com.google.protobuf.ByteString.EMPTY) { __size += _root_.com.google.protobuf.CodedOutputStream.computeBytesSize(1, buffer) }
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
        val __v = buffer
        if (__v != _root_.com.google.protobuf.ByteString.EMPTY) {
          _output__.writeBytes(1, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest = {
      var __buffer = this.buffer
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __buffer = _input__.readBytes()
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest(
          buffer = __buffer
      )
    }
    def withBuffer(__v: _root_.com.google.protobuf.ByteString): LoadBinaryRequest = copy(buffer = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = buffer
          if (__t != _root_.com.google.protobuf.ByteString.EMPTY) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PByteString(buffer)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest
}

object LoadBinaryRequest extends scalapb.GeneratedMessageCompanion[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest(
      __fieldsMap.getOrElse(__fields.get(0), _root_.com.google.protobuf.ByteString.EMPTY).asInstanceOf[_root_.com.google.protobuf.ByteString]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.com.google.protobuf.ByteString]).getOrElse(_root_.com.google.protobuf.ByteString.EMPTY)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = NativedroidGrpcProto.javaDescriptor.getMessageTypes.get(9)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = NativedroidGrpcProto.scalaDescriptor.messages(9)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest(
  )
  implicit class LoadBinaryRequestLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest](_l) {
    def buffer: _root_.scalapb.lenses.Lens[UpperPB, _root_.com.google.protobuf.ByteString] = field(_.buffer)((c_, f_) => c_.copy(buffer = f_))
  }
  final val BUFFER_FIELD_NUMBER = 1
}
