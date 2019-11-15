package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.BookWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/11
 **/
public interface BookWordRepository  extends JpaRepository<BookWord,Long> ,JpaSpecificationExecutor<BookWord> {


    BookWord findBookWordByWordName(String wordName) ;

    List<BookWord> findAllByLearnBookIdOrderByUnitDesAsc(Long bookId);

    BookWord findByWordNameAndLearnBookId(String wordName, Long bookId);

}
