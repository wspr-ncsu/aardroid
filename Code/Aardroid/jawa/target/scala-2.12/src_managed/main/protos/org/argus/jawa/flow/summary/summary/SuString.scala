// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jawa.flow.summary.summary

@SerialVersionUID(0L)
final case class SuString(
    str: _root_.scala.Predef.String = ""
    ) extends scalapb.GeneratedMessage with scalapb.Message[SuString] with scalapb.lenses.Updatable[SuString] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (str != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, str) }
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
        val __v = str
        if (__v != "") {
          _output__.writeString(1, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jawa.flow.summary.summary.SuString = {
      var __str = this.str
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __str = _input__.readString()
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jawa.flow.summary.summary.SuString(
          str = __str
      )
    }
    def withStr(__v: _root_.scala.Predef.String): SuString = copy(str = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = str
          if (__t != "") __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(str)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jawa.flow.summary.summary.SuString
}

object SuString extends scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuString] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuString] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jawa.flow.summary.summary.SuString = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jawa.flow.summary.summary.SuString(
      __fieldsMap.getOrElse(__fields.get(0), "").asInstanceOf[_root_.scala.Predef.String]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jawa.flow.summary.summary.SuString] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jawa.flow.summary.summary.SuString(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Predef.String]).getOrElse("")
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SummaryProto.javaDescriptor.getMessageTypes.get(10)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SummaryProto.scalaDescriptor.messages(10)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jawa.flow.summary.summary.SuString(
  )
  implicit class SuStringLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuString]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jawa.flow.summary.summary.SuString](_l) {
    def str: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.str)((c_, f_) => c_.copy(str = f_))
  }
  final val STR_FIELD_NUMBER = 1
}
