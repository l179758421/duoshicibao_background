package com.runer.cibao.util.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;

/**
 * @Author szhua
 * 2018-04-18
 * 11:28
 * @Descriptionsbaby_photos== PageableUtil
 **/
public class PageableUtil {

    public static ModelMap getPageModels(ModelMap modelMap, Page page) {
        modelMap.put("datas", page.getContent());
        modelMap.put("count", page.getTotalElements());
        modelMap.put("page", page.getPageable().getPageNumber() + 1);
        modelMap.put("rows", page.getPageable().getPageSize());
        return modelMap;
    }


    /**
     * 获取基础分页对象
     *
     * @param page 获取第几页
     * @param size 每页条数
     * @param dtos 排序对象数组
     * @return
     */
    public static Pageable basicPage(Integer page, Integer size, SortDto... dtos) {
        Sort sort = SortTools.basicSort(dtos);
        page = (page == null || page < 1) ? 0 : page - 1;
        size = (size == null || size <= 0) ? 10 : size;
        Pageable pageable = null;
        if (sort != null) {
            pageable = new PageRequest(page, size, sort);
        } else {
            pageable = new PageRequest(page, size);
        }
        return pageable;
    }

    /**
     * 获取基础分页对象，每页条数默认15条
     * - 默认以id降序排序
     *
     * @param page 获取第几页
     * @return
     */
    public static Pageable basicPage(Integer page) {
        return basicPage(page, 0);
    }

    /**
     * 获取基础分页对象，每页条数默认15条
     *
     * @param page 获取第几页
     * @param dtos 排序对象数组
     * @return
     */
    public static Pageable basicPage(Integer page, SortDto... dtos) {
        return basicPage(page, 0, dtos);
    }

    /**
     * 获取基础分页对象，排序方式默认降序
     *
     * @param page       获取第几页
     * @param size       每页条数
     * @param orderField 排序字段
     * @return
     */
    public static Pageable basicPage(Integer page, Integer size, String orderField) {
        return basicPage(page, size, new SortDto("desc", orderField));
    }

    /**
     * 获取基础分页对象
     * - 每页条数默认15条
     * - 排序方式默认降序
     *
     * @param page       获取第几页
     * @param orderField 排序字段
     * @return
     */
    public static Pageable basicPage(Integer page, String orderField) {
        return basicPage(page, 0, new SortDto("desc", orderField));
    }


}
