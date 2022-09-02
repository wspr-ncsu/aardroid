// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.nativedroid.server.nativedroid_grpc

@SerialVersionUID(0L)
final case class MethodMap(
    methodName: _root_.scala.Predef.String = "",
    funcAddr: _root_.scala.Long = 0L
    ) extends scalapb.GeneratedMessage with scalapb.Message[MethodMap] with scalapb.lenses.Updatable[MethodMap] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (methodName != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, methodName) }
      if (funcAddr != 0L) { __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(2, funcAddr) }
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
        val __v = methodName
        if (__v != "") {
          _output__.writeString(1, __v)
        }
      };
      {
        val __v = funcAddr
        if (__v != 0L) {
          _output__.writeInt64(2, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.nativedroid.server.nativedroid_grpc.MethodMap = {
      var __methodName = this.methodName
      var __funcAddr = this.funcAddr
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __methodName = _input__.readString()
          case 16 =>
            __funcAddr = _input__.readInt64()
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.nativedroid.server.nativedroid_grpc.MethodMap(
          methodName = __methodName,
          funcAddr = __funcAddr
      )
    }
    def withMethodName(__v: _root_.scala.Predef.String): MethodMap = copy(methodName = __v)
    def withFuncAddr(__v: _root_.scala.Long): MethodMap = copy(funcAddr = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = methodName
          if (__t != "") __t else null
        }
        case 2 => {
          val __t = funcAddr
          if (__t != 0L) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(methodName)
        case 2 => _root_.scalapb.descriptors.PLong(funcAddr)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.nativedroid.server.nativedroid_grpc.MethodMap
}

object MethodMap extends scalapb.GeneratedMessageCompanion[org.argus.nativedroid.server.nativedroid_grpc.MethodMap] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.nativedroid.server.nativedroid_grpc.MethodMap] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.nativedroid.server.nativedroid_grpc.MethodMap = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.nativedroid.server.nativedroid_grpc.MethodMap(
      __fieldsMap.getOrElse(__fields.get(0), "").asInstanceOf[_root_.scala.Predef.String],
      __fieldsMap.getOrElse(__fields.get(1), 0L).asInstanceOf[_root_.scala.Long]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.nativedroid.server.nativedroid_grpc.MethodMap] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.nativedroid.server.nativedroid_grpc.MethodMap(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Long]).getOrElse(0L)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = NativedroidGrpcProto.javaDescriptor.getMessageTypes.get(3)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = NativedroidGrpcProto.scalaDescriptor.messages(3)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.nativedroid.server.nativedroid_grpc.MethodMap(
  )
  implicit class MethodMapLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.nativedroid.server.nativedroid_grpc.MethodMap]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.nativedroid.server.nativedroid_grpc.MethodMap](_l) {
    def methodName: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.methodName)((c_, f_) => c_.copy(methodName = f_))
    def funcAddr: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.funcAddr)((c_, f_) => c_.copy(funcAddr = f_))
  }
  final val METHOD_NAME_FIELD_NUMBER = 1
  final val FUNC_ADDR_FIELD_NUMBER = 2
}
