package com.example.api.demo.feature.author;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepositoryPageable extends PagingAndSortingRepository<Author, Long> {

    Author findByName(String name);

}
