package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.Response;
import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.model.BookModel;
import com.example.bookstoreapplication.service.IbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BooksPage")
public class BookController {

    @Autowired
    IbookService ibookService;

    //----------------------------- Add New Books (Only Admin)----------------------------------------------------------------------
    @PostMapping("/Add_Books/Admin")
    public ResponseEntity<Response> addBooks(@RequestHeader String token, @RequestBody BookDTO bookDTO) {
        ibookService.addBooks(token,bookDTO);
        Response response = new Response(bookDTO, "Book Added Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Update Books Data (Only Admin)------------------------------------------------------------------
    @PutMapping("/Update_Books_Data/Admin")
    public ResponseEntity<Response> updateBook(@RequestHeader String token,@RequestParam int id, @RequestBody BookDTO bookDTO) {
        BookModel update = ibookService.updateBooksData(token,id,bookDTO);
        Response response = new Response(update, "Book Update Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //--------------------------------- Delete Book Data (Only Admin)------------------------------------------------------------------
    @DeleteMapping("/Delete_Book/Admin")
    public ResponseEntity<Response> deleteBook(@RequestHeader String token, @RequestParam int id) {
        ibookService.deleteBookById(token, id);
        Response response = new Response("book deleted for id :" +id+" ", "Book delete Successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Show All Books Data (AnyOne)--------------------------------------------------------------------
    @GetMapping("/Show All Books Data")
    public ResponseEntity<Response> showAllBooksData(){
        List<BookModel> bookModelList = ibookService.showAllBooks();
        Response response = new Response(bookModelList, "All Books Data" );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Search Books by Book id (AnyOne)--------------------------------------------------------------------
    @GetMapping("/Find_Book_By_Id")
    public ResponseEntity<Response> getBookById(@RequestParam int id) {
        BookModel bookModel = ibookService.getBookByID(id);
        Response response = new Response(bookModel, "successfully record founded for given id: " + id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Search Books by Book Name (AnyOne)--------------------------------------------------------------------
    @GetMapping("/Search_Books_By_Name")
    public ResponseEntity<Response> searchBooksByName(@RequestParam String bookName) {
        List<BookModel> bookModelList = ibookService.searchBookByName(bookName);
        Response response = new Response(bookModelList, "successfully record founded for given book name: " + bookName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------------- Search Books by Author Name (AnyOne)--------------------------------------------------------------------
    @GetMapping("/Search_Books_By_Author_Name")
    public ResponseEntity<Response> searchBooksByAuthorName(@RequestParam String authorName) {
        List<BookModel> bookModelList = ibookService.searchBookByAuthorName(authorName);
        Response response = new Response(bookModelList, "successfully record founded for given Author name: " + authorName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Sort Book Data By Price High To Low (AnyOne)---------------------------------
    @GetMapping("/Sort_Books_By_Price_HighToLow")
    public ResponseEntity<Response> sortBooksByPriceHighToLow() {
        List<BookModel> sortedList = ibookService.sortBookByPriceHighToLow();
        Response response = new Response(sortedList, "Sort Books By Price High To Low");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Sort Book Data By Price Low To High (AnyOne)------------------------------------------------
    @GetMapping("/Sort_Books_By_Price_LowToHigh")
    public ResponseEntity<Response> sortBooksByPriceLowToHigh() {
        List<BookModel> sortedList = ibookService.sortBookByPriceLowToHigh();
        Response response = new Response(sortedList, "Sort Books By Price Low To High");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //--------------------------------- Sort Book Data By Newest Arrivals books (AnyOne)--------------------------------------------
    @GetMapping("/Sort_Books_By_Newest_Arrivals")
    public ResponseEntity<Response> sortBooksByNewestArrivals() {
        List<BookModel> sortedList = ibookService.sortBooksByNewestArrivals();
        Response response = new Response(sortedList, "Sort Books By Newest Arrivals");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
