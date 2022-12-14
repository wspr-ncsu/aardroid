// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jawa.flow.summary.summary

@SerialVersionUID(0L)
final case class SuThis(
    heap: scala.Option[org.argus.jawa.flow.summary.summary.SuHeap] = None
    ) extends scalapb.GeneratedMessage with scalapb.Message[SuThis] with scalapb.lenses.Updatable[SuThis] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (heap.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(heap.get.serializedSize) + heap.get.serializedSize }
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
      heap.foreach { __v =>
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jawa.flow.summary.summary.SuThis = {
      var __heap = this.heap
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __heap = Option(_root_.scalapb.LiteParser.readMessage(_input__, __heap.getOrElse(org.argus.jawa.flow.summary.summary.SuHeap.defaultInstance)))
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jawa.flow.summary.summary.SuThis(
          heap = __heap
      )
    }
    def getHeap: org.argus.jawa.flow.summary.summary.SuHeap = heap.getOrElse(org.argus.jawa.flow.summary.summary.SuHeap.defaultInstance)
    def clearHeap: SuThis = copy(heap = None)
    def withHeap(__v: org.argus.jawa.flow.summary.summary.SuHeap): SuThis = copy(heap = Option(__v))
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => heap.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => heap.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jawa.flow.summary.summary.SuThis
}

object SuThis extends scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuThis] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuThis] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jawa.flow.summary.summary.SuThis = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jawa.flow.summary.summary.SuThis(
      __fieldsMap.get(__fields.get(0)).asInstanceOf[scala.Option[org.argus.jawa.flow.summary.summary.SuHeap]]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jawa.flow.summary.summary.SuThis] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jawa.flow.summary.summary.SuThis(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[scala.Option[org.argus.jawa.flow.summary.summary.SuHeap]])
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SummaryProto.javaDescriptor.getMessageTypes.get(4)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SummaryProto.scalaDescriptor.messages(4)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = org.argus.jawa.flow.summary.summary.SuHeap
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jawa.flow.summary.summary.SuThis(
  )
  implicit class SuThisLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuThis]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jawa.flow.summary.summary.SuThis](_l) {
    def heap: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuHeap] = field(_.getHeap)((c_, f_) => c_.copy(heap = Option(f_)))
    def optionalHeap: _root_.scalapb.lenses.Lens[UpperPB, scala.Option[org.argus.jawa.flow.summary.summary.SuHeap]] = field(_.heap)((c_, f_) => c_.copy(heap = f_))
  }
  final val HEAP_FIELD_NUMBER = 1
}
