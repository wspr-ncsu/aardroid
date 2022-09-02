package org.argus.jnsaf.server.jnsaf_grpc

object JNSafGrpc {
  val METHOD_LOAD_APK: _root_.io.grpc.MethodDescriptor[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKRequest, org.argus.jnsaf.server.jnsaf_grpc.LoadAPKResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("jnsaf_server.JNSaf", "LoadAPK"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.LoadAPKRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.LoadAPKResponse))
      .build()
  
  val METHOD_TAINT_ANALYSIS: _root_.io.grpc.MethodDescriptor[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest, org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("jnsaf_server.JNSaf", "TaintAnalysis"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse))
      .build()
  
  val METHOD_GET_SUMMARY: _root_.io.grpc.MethodDescriptor[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest, org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("jnsaf_server.JNSaf", "GetSummary"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse))
      .build()
  
  val METHOD_REGISTER_ICC: _root_.io.grpc.MethodDescriptor[org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest, org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("jnsaf_server.JNSaf", "RegisterICC"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse))
      .build()
  
  val METHOD_REGISTER_TAINT: _root_.io.grpc.MethodDescriptor[org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest, org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("jnsaf_server.JNSaf", "RegisterTaint"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest))
      .setResponseMarshaller(new scalapb.grpc.Marshaller(org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse))
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("jnsaf_server.JNSaf")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(org.argus.jnsaf.server.jnsaf_grpc.JnsafGrpcProto.javaDescriptor))
      .addMethod(METHOD_LOAD_APK)
      .addMethod(METHOD_TAINT_ANALYSIS)
      .addMethod(METHOD_GET_SUMMARY)
      .addMethod(METHOD_REGISTER_ICC)
      .addMethod(METHOD_REGISTER_TAINT)
      .build()
  
  trait JNSaf extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = JNSaf
    def loadAPK(responseObserver: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKResponse]): _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKRequest]
    def taintAnalysis(request: org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse]
    def getSummary(request: org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse]
    def registerICC(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse]
    def registerTaint(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse]
  }
  
  object JNSaf extends _root_.scalapb.grpc.ServiceCompanion[JNSaf] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[JNSaf] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = org.argus.jnsaf.server.jnsaf_grpc.JnsafGrpcProto.javaDescriptor.getServices().get(0)
  }
  
  trait JNSafBlockingClient {
    def serviceCompanion = JNSaf
    def taintAnalysis(request: org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest): org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse
    def getSummary(request: org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest): org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse
    def registerICC(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest): org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse
    def registerTaint(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest): org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse
  }
  
  class JNSafBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[JNSafBlockingStub](channel, options) with JNSafBlockingClient {
    override def taintAnalysis(request: org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest): org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_TAINT_ANALYSIS, options), request)
    }
    
    override def getSummary(request: org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest): org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_GET_SUMMARY, options), request)
    }
    
    override def registerICC(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest): org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_REGISTER_ICC, options), request)
    }
    
    override def registerTaint(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest): org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse = {
      _root_.io.grpc.stub.ClientCalls.blockingUnaryCall(channel.newCall(METHOD_REGISTER_TAINT, options), request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): JNSafBlockingStub = new JNSafBlockingStub(channel, options)
  }
  
  class JNSafStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[JNSafStub](channel, options) with JNSaf {
    override def loadAPK(responseObserver: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKResponse]): _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKRequest] = {
      _root_.io.grpc.stub.ClientCalls.asyncClientStreamingCall(channel.newCall(METHOD_LOAD_APK, options), responseObserver)
    }
    
    override def taintAnalysis(request: org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_TAINT_ANALYSIS, options), request))
    }
    
    override def getSummary(request: org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_GET_SUMMARY, options), request))
    }
    
    override def registerICC(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_REGISTER_ICC, options), request))
    }
    
    override def registerTaint(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest): scala.concurrent.Future[org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse] = {
      scalapb.grpc.Grpc.guavaFuture2ScalaFuture(_root_.io.grpc.stub.ClientCalls.futureUnaryCall(channel.newCall(METHOD_REGISTER_TAINT, options), request))
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): JNSafStub = new JNSafStub(channel, options)
  }
  
  def bindService(serviceImpl: JNSaf, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
    .addMethod(
      METHOD_LOAD_APK,
      _root_.io.grpc.stub.ServerCalls.asyncClientStreamingCall(new _root_.io.grpc.stub.ServerCalls.ClientStreamingMethod[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKRequest, org.argus.jnsaf.server.jnsaf_grpc.LoadAPKResponse] {
        override def invoke(observer: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKResponse]): _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.LoadAPKRequest] =
          serviceImpl.loadAPK(observer)
      }))
    .addMethod(
      METHOD_TAINT_ANALYSIS,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest, org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse] {
        override def invoke(request: org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.TaintAnalysisResponse]): Unit =
          serviceImpl.taintAnalysis(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_GET_SUMMARY,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest, org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse] {
        override def invoke(request: org.argus.jnsaf.server.jnsaf_grpc.GetSummaryRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.GetSummaryResponse]): Unit =
          serviceImpl.getSummary(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_REGISTER_ICC,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest, org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse] {
        override def invoke(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterICCRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.RegisterICCResponse]): Unit =
          serviceImpl.registerICC(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .addMethod(
      METHOD_REGISTER_TAINT,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest, org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse] {
        override def invoke(request: org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintRequest, observer: _root_.io.grpc.stub.StreamObserver[org.argus.jnsaf.server.jnsaf_grpc.RegisterTaintResponse]): Unit =
          serviceImpl.registerTaint(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): JNSafBlockingStub = new JNSafBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): JNSafStub = new JNSafStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = org.argus.jnsaf.server.jnsaf_grpc.JnsafGrpcProto.javaDescriptor.getServices().get(0)
  
}