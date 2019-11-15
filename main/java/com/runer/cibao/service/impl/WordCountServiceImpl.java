package com.runer.cibao.service.impl;

import com.runer.cibao.dao.WordCountDao;
import com.runer.cibao.domain.WordCount;
import com.runer.cibao.domain.repository.PersonalLearnWordRepository;
import com.runer.cibao.domain.repository.PersonalTestForBookRepository;
import com.runer.cibao.domain.repository.PersonalTestForUnitRepository;
import com.runer.cibao.domain.repository.WordCountRepository;
import com.runer.cibao.exception.SmartCommunityException;
import com.runer.cibao.service.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author k
 * @Date: Created in 16:18 2018/8/23
 * @Description:
 */
@Service
public class WordCountServiceImpl extends BaseServiceImp<WordCount, WordCountRepository> implements WordCountService {

    @Autowired
    private WordCountDao wordCountDao;

    @Autowired
    PersonalLearnWordRepository personalLearnWordRepository;

    @Autowired
    PersonalTestForUnitRepository personalTestForUnitRepository;

    @Autowired
    PersonalTestForBookRepository personalTestForBookRepository;

    @Override
    public Long getUnitCount(Long appUserId, Long unitId, Integer status) {
        return wordCountDao.getUnitCount(appUserId,unitId,status);
    }

    @Override
    public void plusUnitCount(Long appUserId, Long unitId, Integer status) {
        WordCount var = new WordCount();
        var.setAppUserId(appUserId);
        var.setBookUnitId(unitId);
        var.setStatus(status);
        var.setCreateDate(new Date());
        try {
            this.save(var);
        } catch (SmartCommunityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void plusNewWordsCount(Long appUserId) {
        WordCount var = this.getTodayNewWordsCountEntity(appUserId);
        var.setNewWordsCount(var.getNewWordsCount() + 1L);
        r.saveAndFlush(var);
    }

    @Override
    public Long getTodayNewWordsCount(Long appUserId){
        return this.getTodayNewWordsCountEntity(appUserId).getNewWordsCount();
    }

    @Override
    public WordCount getTodayNewWordsCountEntity(Long appUserId){
        WordCount newWordsCount =   wordCountDao.getTodayNewWordsCount(appUserId);
      if (newWordsCount ==  null){
          wordCountDao.createTodayNewWordsCount(appUserId);
          return wordCountDao.getTodayNewWordsCount(appUserId);
      }else {
          return newWordsCount;
      }
    }

    @Override
    public Integer getTodayOldWordsCount(Long appUserId){
        return  personalLearnWordRepository.findAllByTodayCount(appUserId).size();
    }

    public Integer getTodayLearnTime(Long appUserId){
        Long oldWordsTotalTime = personalLearnWordRepository.getTotalTodayLearnTime(appUserId);
        Long testUnitTotalTime = personalTestForUnitRepository.findTodayTestForUnit(appUserId).getTestTime();
        Long testBookTotalTime = personalTestForBookRepository.findTodayTestForBook(appUserId).getTestTime();
        return  null;
    }

}
