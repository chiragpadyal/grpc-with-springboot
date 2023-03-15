package com.devproblems.grpc.client.service;


import com.devProblems.*;
import com.google.protobuf.Descriptors;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class MessageClientService {
    @GrpcClient("grpc-devproblems-service")
    MessageServiceGrpc.MessageServiceStub asynchronousClient;

    public List<Map<Descriptors.FieldDescriptor, Object>> getMessage() throws InterruptedException {
        final Empty empty = Empty.newBuilder().build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchronousClient.getMessages(empty, new StreamObserver<Message>() {
            @Override
            public void onNext(Message message) {
                response.add(message.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> sendMessage(String text) throws InterruptedException {
        final User user = User.newBuilder().setUserId(1).setName("dummy").build();
        final Message message = Message.newBuilder().setText(text).setUserId(user.getUserId()).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchronousClient.sendMessage(message, new StreamObserver<Message>() {
            @Override
            public void onNext(Message message) {
                response.add(message.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }
}
