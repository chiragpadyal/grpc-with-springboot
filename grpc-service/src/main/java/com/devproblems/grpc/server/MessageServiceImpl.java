package com.devproblems.grpc.server;
import com.devProblems.Empty;
import com.devProblems.Message;
import com.devProblems.MessageServiceGrpc;
import com.devproblems.TempDB;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class MessageServiceImpl extends MessageServiceGrpc.MessageServiceImplBase {
/*    @Override
    public void sendMessage(Message request, StreamObserver<Message> responseObserver) {
        super.sendMessage(request, responseObserver);
    }*/
    private List<Message> messageList = new ArrayList<>();
    @Override
    public void getMessages(Empty request, StreamObserver<Message> responseObserver) {
        for (Message message : messageList) {
            responseObserver.onNext(message);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void sendMessage(Message request, StreamObserver<Message> responseObserver) {
        int nextId = messageList.size() + 1;
        Message newMessage = Message.newBuilder()
                .setMessageId(nextId)
                .setText(request.getText())
                .setUserId(request.getUserId())
                .build();

        messageList.add(newMessage);

        responseObserver.onNext(newMessage);
        responseObserver.onCompleted();
    }
}
