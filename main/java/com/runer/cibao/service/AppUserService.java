package com.runer.cibao.service;

import com.runer.cibao.base.ApiResult;
import com.runer.cibao.domain.*;
import com.runer.cibao.domain.repository.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/6/12
 **/
public interface AppUserService extends BaseService<AppUser,AppUserRepository>{


    Page<AppUser> findAppUsers(String schoolUid, Long schoolId,
                               Long classInSchoolId, String userName, Long techaerId, String uid, Integer isBatchCreated, Integer page, Integer limit) ;

    Page<AppUser> findAppUsersC(String schoolUid, Long schoolId,
                                Long classInSchoolId, String userName, Long techaerId, String uid, Integer isBatchCreated, Integer page, Integer limit) ;

    ApiResult findAppUserByPhoneNum(String phoneNum) ;


    /**
     *  String password ;
     *
     *     @NotFound(action = NotFoundAction.IGNORE)
     *     @ManyToOne(fetch = FetchType.LAZY)
     *     @JoinColumn(name = "class_id")
     *     private ClassInSchool classInSchool;
     *
     *
     *     @Column(unique = true)
     *     private String phoneNum ;
     *
     *     @Temporal(TemporalType.TIMESTAMP)
     *     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
     *     private Date registerDate;
     *
     *     private String imgUrl ;
     *
     *     private String name ;
     *
     *     private Long provinceId ;
     *
     *     private Long cityId ;
     *
     *     private Long areaId ;
     *
     *     private String address ;
     *
     *
     *     private String sign ;
     *
     *     private Integer sex =Config.FEMALE;
     *
     *
     *     @Temporal(TemporalType.DATE)
     *     @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
     *     private Date birthDay ;
     * @return
     */
    ApiResult updateAppUserInfo(Long id, String name, Long provinceId,
                                Long cityId, Long areaId, Integer sex,
                                String sign, Date birthDay, Long classInSchoolId,
                                MultipartFile headerFile);
    /**
     * 通过uid查找appUser
     * @param uid
     * @return
     */
    ApiResult findAppUserByUid(String uid);
    /**
     * 分配学生的班级；
     * @param userId
     * @param classInSchoolId
     * @return
     */
    ApiResult distributeClassInschool(Long userId, Long classInSchoolId) ;


    /**
     * 根据uid添加到school 中；
     * @param userUid
     * @param schoolId
     * @return
     */
    ApiResult addUserToSChool(String userUid, Long schoolId) ;


    /**
     *解除school的绑定
     * @param id
     * @return
     */
    ApiResult uniteSchools(Long id) ;

    /**
     * 批量的解除绑定；
     * @param ids
     * @return
     */
    ApiResult uniteSchoolsWithIds(String ids) ;



    ApiResult perfectUserInfo(Long id, String phoneNum, String name) ;

    /**
     * 查找用户个人信息和积分
     * @param appUserId
     * @return
     */
    ApiResult findUserAndIntegral(Long appUserId);



    /**
     * 批量生成uid
     * @param num
     * @param pwd
     * @param schoolId
     * @return
     */
    ApiResult batchCreateUID(Integer num, String pwd, Long schoolId);

    /**
     * 导出uid账户
     * @param httpResponse
     * @return
     */
    ApiResult exportUid(HttpServletResponse httpResponse);

    /**
     * 通过ids导出appUser
     * @param ids
     * @return
     */
    ApiResult exportUidByIds(String ids,String title,String sheetname) ;

    ApiResult findAppUserBySchoolId(String schoolUid);

    List<AppUser> findAppUserBySchoolId2(String schoolUid);

    List<AppUser> findAppUserByClassInSchool(Long classId);

    /**
     * 根据学校uid分页查询用户
     * @param schoolUid
     * @param page
     * @param limit
     * @return
     */
   Page<AppUser> findBySchoolUid(String schoolUid, String userName, Integer page, Integer limit);


    Page<AppUser> findByTeacherId(Long teacherId, String userName, Integer page, Integer limit);

    AppUserDetail findAppUserDetail(Long userId);

    /**
     * 导出excel
     * @param
     * @param fileName
     * @param response
     * @return
     */
    ApiResult exportReportExcel(Long userId, String fileName, HttpServletResponse response) ;

    ApiResult personalReportExcel(Long classId, String fileName, HttpServletResponse response);

    ApiResult personalReportExcel2(Long userId, String fileName, HttpServletResponse response) ;

    /**
     * 上传用户头像
     * @param id
     * @param img
     * @return
     */
    ApiResult  uploadUserImag(Long id, MultipartFile img);

    /**
     * 学习报告转为excel（批量）
     * @param list
     * @return
     */
    List<StudyReportExcel> reportToExcels(List<AppUser> list);

    /**
     * 个人学习报告转为excel（批量）
     * @param list
     * @return
     */
    List<PersonalReportExcel> personalReportToExcels(List<PersonalReportBean> list);
    /**
     * 单个学习报告转excel
     * @param appUser
     * @return
     */
    StudyReportExcel reportToExcel(AppUser appUser);

    /**
     * 个人学习报告转excel
     * @return
     */
    PersonalReportExcel personalReportToExcel(PersonalReportBean reportBean);

//======================================================
    /**
     * 个人学习报告展示详情
     */
    PersonalReport personalReport(Long appUserId);



}
