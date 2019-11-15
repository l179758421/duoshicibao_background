package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.BookUnit;
import com.runer.cibao.domain.repository.BookUnitRepository;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/15
 **/
public interface BookUnitService extends BaseService<BookUnit,BookUnitRepository> {

    List<BookUnit> findUnits(Long bookId, String bookversion, String bookStage, String bookName,
                             Long unitId, String unitName, String bookversionLikeName,
                             String booklikeName, String unitLikeName);

    ApiResult addOrUpdateUnit(Long id, String name, Long bookId);

    /**
     * 根据bookId分页查
     * @param bookId
     * @param page
     * @param limit
     * @return
     */
    Page<BookUnit> findByBookId(Long bookId, String unitName, Integer page, Integer limit);

    List<BookUnit> personalLearnBookUnitList(Long userId, Long bookId);

}
