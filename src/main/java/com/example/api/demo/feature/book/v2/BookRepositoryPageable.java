package com.example.api.demo.feature.book.v2;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.api.demo.feature.book.Book;

@Repository
public interface BookRepositoryPageable extends PagingAndSortingRepository<Book, Long> {

}
