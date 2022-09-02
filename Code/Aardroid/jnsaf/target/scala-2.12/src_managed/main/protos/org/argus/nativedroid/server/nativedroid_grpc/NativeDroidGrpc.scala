package org.argus.nativedroid.server.nativedroid_grpc

object NativeDroidGrpc {
  val METHOD_GEN_SUMMARY: _root_.io.grpc.MethodDescriptor[org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest, org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("nativedroid_server.NativeDroid", "GenSummary"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse))
      .build()
  
  val METHOD_GET_DYNAMIC_REGISTER_MAP: _root_.io.grpc.MethodDescriptor[org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest, org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("nativedroid_server.NativeDroid", "GetDynamicRegisterMap"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse))
      .build()
  
  val METHOD_HAS_SYMBOL: _root_.io.grpc.MethodDescriptor[org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest, org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("nativedroid_server.NativeDroid", "HasSymbol"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse))
      .build()
  
  val METHOD_ANALYSE_NATIVE_ACTIVITY: _root_.io.grpc.MethodDescriptor[org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest, org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("nativedroid_server.NativeDroid", "AnalyseNativeActivity"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse))
      .build()
  
  val METHOD_LOAD_BINARY: _root_.io.grpc.MethodDescriptor[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest, org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("nativedroid_server.NativeDroid", "LoadBinary"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryResponse))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("nativedroid_server.NativeDroid")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(org.argus.nativedroid.server.nativedroid_grpc.NativedroidGrpcProto.javaDescriptor))
      .addMethod(METHOD_GEN_SUMMARY)
      .addMethod(METHOD_GET_DYNAMIC_REGISTER_MAP)
      .addMethod(METHOD_HAS_SYMBOL)
      .addMethod(METHOD_ANALYSE_NATIVE_ACTIVITY)
      .addMethod(METHOD_LOAD_BINARY)
      .build()
  
  trait NativeDroid extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = NativeDroid
    def genSummary(request: org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse]
    def getDynamicRegisterMap(request: org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse]
    def hasSymbol(request: org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse]
    def analyseNativeActivity(request: org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse]
    def loadBinary(responseObserver: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryResponse]): _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest]
  }
  
  object NativeDroid extends _root_.scalapb.grpc.ServiceCompanion[NativeDroid] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[NativeDroid] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = org.argus.nativedroid.server.nativedroid_grpc.NativedroidGrpcProto.javaDescriptor.getServices().get(0)
  }
  
  trait NativeDroidBlockingClient {
    def serviceCompanion = NativeDroid
    def genSummary(request: org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest): org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse
    def getDynamicRegisterMap(request: org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest): org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse
    def hasSymbol(request: org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest): org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse
    def analyseNativeActivity(request: org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest): org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse
  }
  
  class NativeDroidBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[NativeDroidBlockingStub](channel, options) with NativeDroidBlockingClient {
    override def genSummary(request: org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest): org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_GEN_SUMMARY, options), request)
    }
    
    override def getDynamicRegisterMap(request: org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest): org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_GET_DYNAMIC_REGISTER_MAP, options), request)
    }
    
    override def hasSymbol(request: org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest): org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_HAS_SYMBOL, options), request)
    }
    
    override def analyseNativeActivity(request: org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest): org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_ANALYSE_NATIVE_ACTIVITY, options), request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): NativeDroidBlockingStub = new NativeDroidBlockingStub(channel, options)
  }
  
  class NativeDroidStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[NativeDroidStub](channel, options) with NativeDroid {
    override def genSummary(request: org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_GEN_SUMMARY, options), request))
    }
    
    override def getDynamicRegisterMap(request: org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_GET_DYNAMIC_REGISTER_MAP, options), request))
    }
    
    override def hasSymbol(request: org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_HAS_SYMBOL, options), request))
    }
    
    override def analyseNativeActivity(request: org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest): scala.concurrent.Future[org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_ANALYSE_NATIVE_ACTIVITY, options), request))
    }
    
    override def loadBinary(responseObserver: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryResponse]): _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest] = {
      _root_.io.grpc.stub.ClientCalls.asyncClientStreamingCall(channel.newCall(METHOD_LOAD_BINARY, options), responseObserver)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): NativeDroidStub = new NativeDroidStub(channel, options)
  }
  
  def bindService(serviceImpl: NativeDroid, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
    .addMethod(
      METHOD_GEN_SUMMARY,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest, org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse] {
        override def invoke(request: org.argus.nativedroid.server.nativedroid_grpc.GenSummaryRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.GenSummaryResponse]): Unit =
          serviceImpl.genSummary(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_GET_DYNAMIC_REGISTER_MAP,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest, org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse] {
        override def invoke(request: org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.GetDynamicRegisterMapResponse]): Unit =
          serviceImpl.getDynamicRegisterMap(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_HAS_SYMBOL,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest, org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse] {
        override def invoke(request: org.argus.nativedroid.server.nativedroid_grpc.HasSymbolRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.HasSymbolResponse]): Unit =
          serviceImpl.hasSymbol(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_ANALYSE_NATIVE_ACTIVITY,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest, org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse] {
        override def invoke(request: org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.AnalyseNativeActivityResponse]): Unit =
          serviceImpl.analyseNativeActivity(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_LOAD_BINARY,
      _root_.io.grpc.stub.ServerCalls.asyncClientStreamingCall(new _root_.io.grpc.stub.ServerCalls.ClientStreamingMethod[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest, org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryResponse] {
        override def invoke(observer: _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryResponse]): _root_.io.grpc.stub.StreamObserver[org.argus.nativedroid.server.nativedroid_grpc.LoadBinaryRequest] =
          serviceImpl.loadBinary(observer)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): NativeDroidBlockingStub = new NativeDroidBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): NativeDroidStub = new NativeDroidStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = org.argus.nativedroid.server.nativedroid_grpc.NativedroidGrpcProto.javaDescriptor.getServices().get(0)
  
}