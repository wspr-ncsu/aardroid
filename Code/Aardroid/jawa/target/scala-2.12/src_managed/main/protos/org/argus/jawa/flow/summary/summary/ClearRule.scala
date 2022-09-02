// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jawa.flow.summary.summary

@SerialVersionUID(0L)
final case class ClearRule(
    heapBase: scala.Option[org.argus.jawa.flow.summary.summary.HeapBase] = None
    ) extends scalapb.GeneratedMessage with scalapb.Message[ClearRule] with scalapb.lenses.Updatable[ClearRule] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (heapBase.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(heapBase.get.serializedSize) + heapBase.get.serializedSize }
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
      heapBase.foreach { __v =>
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jawa.flow.summary.summary.ClearRule = {
      var __heapBase = this.heapBase
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __heapBase = Option(_root_.scalapb.LiteParser.readMessage(_input__, __heapBase.getOrElse(org.argus.jawa.flow.summary.summary.HeapBase.defaultInstance)))
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jawa.flow.summary.summary.ClearRule(
          heapBase = __heapBase
      )
    }
    def getHeapBase: org.argus.jawa.flow.summary.summary.HeapBase = heapBase.getOrElse(org.argus.jawa.flow.summary.summary.HeapBase.defaultInstance)
    def clearHeapBase: ClearRule = copy(heapBase = None)
    def withHeapBase(__v: org.argus.jawa.flow.summary.summary.HeapBase): ClearRule = copy(heapBase = Option(__v))
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => heapBase.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => heapBase.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jawa.flow.summary.summary.ClearRule
}

object ClearRule extends scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.ClearRule] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.ClearRule] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jawa.flow.summary.summary.ClearRule = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jawa.flow.summary.summary.ClearRule(
      __fieldsMap.get(__fields.get(0)).asInstanceOf[scala.Option[org.argus.jawa.flow.summary.summary.HeapBase]]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jawa.flow.summary.summary.ClearRule] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jawa.flow.summary.summary.ClearRule(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[scala.Option[org.argus.jawa.flow.summary.summary.HeapBase]])
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SummaryProto.javaDescriptor.getMessageTypes.get(19)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SummaryProto.scalaDescriptor.messages(19)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = org.argus.jawa.flow.summary.summary.HeapBase
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jawa.flow.summary.summary.ClearRule(
  )
  implicit class ClearRuleLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.ClearRule]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jawa.flow.summary.summary.ClearRule](_l) {
    def heapBase: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.HeapBase] = field(_.getHeapBase)((c_, f_) => c_.copy(heapBase = Option(f_)))
    def optionalHeapBase: _root_.scalapb.lenses.Lens[UpperPB, scala.Option[org.argus.jawa.flow.summary.summary.HeapBase]] = field(_.heapBase)((c_, f_) => c_.copy(heapBase = f_))
  }
  final val HEAP_BASE_FIELD_NUMBER = 1
}
