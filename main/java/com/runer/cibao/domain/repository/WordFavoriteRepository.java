package com.runer.cibao.domain.repository;

import com.runer.cibao.domain.WordFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WordFavoriteRepository extends JpaRepository<WordFavorite,Long>,JpaSpecificationExecutor<WordFavorite> {
         WordFavorite findByUserIdAndAndWordId(Long userId, Long wordId);
         List<WordFavorite> findByUserId(Long userId);
}
