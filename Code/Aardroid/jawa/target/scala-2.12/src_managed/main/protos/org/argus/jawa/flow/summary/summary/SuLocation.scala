// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package org.argus.jawa.flow.summary.summary

@SerialVersionUID(0L)
final case class SuLocation(
    suLocation: org.argus.jawa.flow.summary.summary.SuLocation.SuLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.Empty
    ) extends scalapb.GeneratedMessage with scalapb.Message[SuLocation] with scalapb.lenses.Updatable[SuLocation] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      if (suLocation.virtualLocation.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(suLocation.virtualLocation.get.serializedSize) + suLocation.virtualLocation.get.serializedSize }
      if (suLocation.concreteLocation.isDefined) { __size += 1 + _root_.com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(suLocation.concreteLocation.get.serializedSize) + suLocation.concreteLocation.get.serializedSize }
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
      suLocation.virtualLocation.foreach { __v =>
        _output__.writeTag(1, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
      suLocation.concreteLocation.foreach { __v =>
        _output__.writeTag(2, 2)
        _output__.writeUInt32NoTag(__v.serializedSize)
        __v.writeTo(_output__)
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): org.argus.jawa.flow.summary.summary.SuLocation = {
      var __suLocation = this.suLocation
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.VirtualLocation(_root_.scalapb.LiteParser.readMessage(_input__, suLocation.virtualLocation.getOrElse(org.argus.jawa.flow.summary.summary.SuVirtualLocation.defaultInstance)))
          case 18 =>
            __suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.ConcreteLocation(_root_.scalapb.LiteParser.readMessage(_input__, suLocation.concreteLocation.getOrElse(org.argus.jawa.flow.summary.summary.SuConcreteLocation.defaultInstance)))
          case tag => _input__.skipField(tag)
        }
      }
      org.argus.jawa.flow.summary.summary.SuLocation(
          suLocation = __suLocation
      )
    }
    def getVirtualLocation: org.argus.jawa.flow.summary.summary.SuVirtualLocation = suLocation.virtualLocation.getOrElse(org.argus.jawa.flow.summary.summary.SuVirtualLocation.defaultInstance)
    def withVirtualLocation(__v: org.argus.jawa.flow.summary.summary.SuVirtualLocation): SuLocation = copy(suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.VirtualLocation(__v))
    def getConcreteLocation: org.argus.jawa.flow.summary.summary.SuConcreteLocation = suLocation.concreteLocation.getOrElse(org.argus.jawa.flow.summary.summary.SuConcreteLocation.defaultInstance)
    def withConcreteLocation(__v: org.argus.jawa.flow.summary.summary.SuConcreteLocation): SuLocation = copy(suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.ConcreteLocation(__v))
    def clearSuLocation: SuLocation = copy(suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.Empty)
    def withSuLocation(__v: org.argus.jawa.flow.summary.summary.SuLocation.SuLocation): SuLocation = copy(suLocation = __v)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => suLocation.virtualLocation.orNull
        case 2 => suLocation.concreteLocation.orNull
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => suLocation.virtualLocation.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
        case 2 => suLocation.concreteLocation.map(_.toPMessage).getOrElse(_root_.scalapb.descriptors.PEmpty)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = org.argus.jawa.flow.summary.summary.SuLocation
}

object SuLocation extends scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuLocation] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[org.argus.jawa.flow.summary.summary.SuLocation] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): org.argus.jawa.flow.summary.summary.SuLocation = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    org.argus.jawa.flow.summary.summary.SuLocation(
      suLocation = __fieldsMap.get(__fields.get(0)).asInstanceOf[scala.Option[org.argus.jawa.flow.summary.summary.SuVirtualLocation]].map(org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.VirtualLocation)
    .orElse[org.argus.jawa.flow.summary.summary.SuLocation.SuLocation](__fieldsMap.get(__fields.get(1)).asInstanceOf[scala.Option[org.argus.jawa.flow.summary.summary.SuConcreteLocation]].map(org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.ConcreteLocation))
    .getOrElse(org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.Empty)
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[org.argus.jawa.flow.summary.summary.SuLocation] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      org.argus.jawa.flow.summary.summary.SuLocation(
        suLocation = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).flatMap(_.as[scala.Option[org.argus.jawa.flow.summary.summary.SuVirtualLocation]]).map(org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.VirtualLocation)
    .orElse[org.argus.jawa.flow.summary.summary.SuLocation.SuLocation](__fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).flatMap(_.as[scala.Option[org.argus.jawa.flow.summary.summary.SuConcreteLocation]]).map(org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.ConcreteLocation))
    .getOrElse(org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.Empty)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = SummaryProto.javaDescriptor.getMessageTypes.get(17)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = SummaryProto.scalaDescriptor.messages(17)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = {
    var __out: _root_.scalapb.GeneratedMessageCompanion[_] = null
    (__number: @_root_.scala.unchecked) match {
      case 1 => __out = org.argus.jawa.flow.summary.summary.SuVirtualLocation
      case 2 => __out = org.argus.jawa.flow.summary.summary.SuConcreteLocation
    }
    __out
  }
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = org.argus.jawa.flow.summary.summary.SuLocation(
  )
  sealed trait SuLocation extends _root_.scalapb.GeneratedOneof {
    def isEmpty: _root_.scala.Boolean = false
    def isDefined: _root_.scala.Boolean = true
    def isVirtualLocation: _root_.scala.Boolean = false
    def isConcreteLocation: _root_.scala.Boolean = false
    def virtualLocation: scala.Option[org.argus.jawa.flow.summary.summary.SuVirtualLocation] = None
    def concreteLocation: scala.Option[org.argus.jawa.flow.summary.summary.SuConcreteLocation] = None
  }
  object SuLocation extends {
    @SerialVersionUID(0L)
    case object Empty extends org.argus.jawa.flow.summary.summary.SuLocation.SuLocation {
      type ValueType = _root_.scala.Nothing
      override def isEmpty: _root_.scala.Boolean = true
      override def isDefined: _root_.scala.Boolean = false
      override def number: _root_.scala.Int = 0
      override def value: _root_.scala.Nothing = throw new java.util.NoSuchElementException("Empty.value")
    }
  
    @SerialVersionUID(0L)
    final case class VirtualLocation(value: org.argus.jawa.flow.summary.summary.SuVirtualLocation) extends org.argus.jawa.flow.summary.summary.SuLocation.SuLocation {
      type ValueType = org.argus.jawa.flow.summary.summary.SuVirtualLocation
      override def isVirtualLocation: _root_.scala.Boolean = true
      override def virtualLocation: scala.Option[org.argus.jawa.flow.summary.summary.SuVirtualLocation] = Some(value)
      override def number: _root_.scala.Int = 1
    }
    @SerialVersionUID(0L)
    final case class ConcreteLocation(value: org.argus.jawa.flow.summary.summary.SuConcreteLocation) extends org.argus.jawa.flow.summary.summary.SuLocation.SuLocation {
      type ValueType = org.argus.jawa.flow.summary.summary.SuConcreteLocation
      override def isConcreteLocation: _root_.scala.Boolean = true
      override def concreteLocation: scala.Option[org.argus.jawa.flow.summary.summary.SuConcreteLocation] = Some(value)
      override def number: _root_.scala.Int = 2
    }
  }
  implicit class SuLocationLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuLocation]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, org.argus.jawa.flow.summary.summary.SuLocation](_l) {
    def virtualLocation: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuVirtualLocation] = field(_.getVirtualLocation)((c_, f_) => c_.copy(suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.VirtualLocation(f_)))
    def concreteLocation: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuConcreteLocation] = field(_.getConcreteLocation)((c_, f_) => c_.copy(suLocation = org.argus.jawa.flow.summary.summary.SuLocation.SuLocation.ConcreteLocation(f_)))
    def suLocation: _root_.scalapb.lenses.Lens[UpperPB, org.argus.jawa.flow.summary.summary.SuLocation.SuLocation] = field(_.suLocation)((c_, f_) => c_.copy(suLocation = f_))
  }
  final val VIRTUAL_LOCATION_FIELD_NUMBER = 1
  final val CONCRETE_LOCATION_FIELD_NUMBER = 2
}
