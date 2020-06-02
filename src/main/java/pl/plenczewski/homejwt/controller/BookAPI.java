package pl.plenczewski.homejwt.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.plenczewski.homejwt.model.Book;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookAPI {

    private List<Book> bookList;

    public BookAPI() {
        bookList = new ArrayList<>();
        bookList.add(new Book("LOL"));
        bookList.add(new Book("LOL2"));
    }


    @GetMapping
    public ResponseEntity<List<Book>> getBooks(){
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
}
