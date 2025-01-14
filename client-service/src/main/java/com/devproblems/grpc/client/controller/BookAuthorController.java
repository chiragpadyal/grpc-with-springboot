package com.devproblems.grpc.client.controller;

import com.devproblems.grpc.client.service.BookAuthorClientService;
import com.devproblems.grpc.client.service.MessageClientService;
import com.google.protobuf.Descriptors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Dev Problems(A Sarang Kumar Tak)
 * @YoutubeChannel https://www.youtube.com/channel/UCVno4tMHEXietE3aUTodaZQ
 */
@RestController
@AllArgsConstructor
public class BookAuthorController {

    final BookAuthorClientService bookAuthorClientService;
    final MessageClientService messageClientService;

    @GetMapping("/messages")
    public List<Map<Descriptors.FieldDescriptor, Object>> getMessage() throws InterruptedException {
        return messageClientService.getMessage();
    }

    @PostMapping("/messages")
    public List<Map<Descriptors.FieldDescriptor, Object>> setMessage(@RequestBody String request) throws InterruptedException {
        return messageClientService.sendMessage(request);
    }

    @GetMapping("/author/{id}")
    public Map<Descriptors.FieldDescriptor, Object> getAuthor(@PathVariable String id) {
        return bookAuthorClientService.getAuthor(Integer.parseInt(id));
    }

    @GetMapping("/book/{author_id}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getBookByAuthor(@PathVariable String author_id) throws InterruptedException {
        return bookAuthorClientService.getBooksByAuthor(Integer.parseInt(author_id));
    }

    @GetMapping("/book")
    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getExpensiveBook() throws InterruptedException {
        return bookAuthorClientService.getExpensiveBook();
    }

    @GetMapping("/book/author/{gender}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getBookByGender(@PathVariable String gender) throws InterruptedException {
        return bookAuthorClientService.getBooksByGender(gender);
    }
}
