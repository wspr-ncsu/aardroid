// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jawa.flow.summary.summary

@SerialVersionUID(0L)
final case class SuHeap(
    heapAccess: _root_.scala.collection.Seq[org.argus.jawa.flow.summary.summary.HeapAccess] = _root_.scala.collection.Seq.empty
    ) extends scalapb.GeneratedMessage with scalapb.Message[SuHeap] with scalapb.lenses.Updatable[SuHeap] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      heapAccess.foreach(heapAccess => __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(heapAccess.serializedSize) + heapAccess.serializedSize)
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
      heapAccess.foreach { __v =>
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jawa.flow.summary.summary.SuHeap = {
      val __heapAccess = (_root_.scala.collection.immutable.Vector.newBuilder[org.argus.jawa.flow.summary.summary.HeapAccess] ++= this.heapAccess)
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __heapAccess += _root_.scalapb.LiteParser.readMessage(_input__, org.argus.jawa.flow.summary.summary.HeapAccess.defaultInstance)
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jawa.flow.summary.summary.SuHeap(
          heapAccess = __heapAccess.result()
      )
    }
    def clearHeapAccess = copy(heapAccess = _root_.scala.collection.Seq.empty)
    def addHeapAccess(__vs: org.argus.jawa.flow.summary.summary.HeapAccess*): SuHeap = addAllHeapAccess(__vs)
    def addAllHeapAccess(__vs: TraversableOnce[org.argus.jawa.flow.summary.summary.HeapAccess]): SuHeap = copy(heapAccess = heapAccess ++ __vs)
    def withHeapAccess(__v: _root_.scala.collection.Seq[org.argus.jawa.flow.summary.summary.HeapAccess]): SuHeap = copy(heapAccess = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => heapAccess
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PRepeated(heapAccess.map(_.toPMessage)(_root_.scala.collection.breakOut))
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jawa.flow.summary.summary.SuHeap
}

object SuHeap extends scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuHeap] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuHeap] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jawa.flow.summary.summary.SuHeap = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jawa.flow.summary.summary.SuHeap(
      __fieldsMap.getOrElse(__fields.get(0), Nil).asInstanceOf[_root_.scala.collection.Seq[org.argus.jawa.flow.summary.summary.HeapAccess]]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jawa.flow.summary.summary.SuHeap] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jawa.flow.summary.summary.SuHeap(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.collection.Seq[org.argus.jawa.flow.summary.summary.HeapAccess]]).getOrElse(_root_.scala.collection.Seq.empty)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SummaryProto.javaDescriptor.getMessageTypes.get(3)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SummaryProto.scalaDescriptor.messages(3)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = org.argus.jawa.flow.summary.summary.HeapAccess
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jawa.flow.summary.summary.SuHeap(
  )
  implicit class SuHeapLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuHeap]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jawa.flow.summary.summary.SuHeap](_l) {
    def heapAccess: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.collection.Seq[org.argus.jawa.flow.summary.summary.HeapAccess]] = field(_.heapAccess)((c_, f_) => c_.copy(heapAccess = f_))
  }
  final val HEAP_ACCESS_FIELD_NUMBER = 1
}
