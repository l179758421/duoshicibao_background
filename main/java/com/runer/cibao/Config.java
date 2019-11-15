package com.runer.cibao;

/**
 * @Author szhua
 * 2018-05-16
 * 11:30
 * @Descriptionssmartcommunity== Config
 **/
public final class Config {

    public  static  final int PER_UNIT_WORD_NUM =30 ;

    public static final int BOOK_PRICE = 10;


    /**
     * 激活、未激活，过期
     */

    /*激活*/
    public static final  int CODE_ACTIVE = 1 ;
    /*未激活*/
    public static  final int CODE_NOT_USE = 0;
    /*已过期*/
    public static  final int CODE_OUT_TIME =2 ;
    /**
     * sex
     */
    //nv
    public static  final  int FEMALE =0 ;
    //nan
    public static final int MALE =1 ;
    /**
     * 课本测试
     */
    //英译汉
    public static final int LESSONTESTTYPE1 =1 ;
    //汉译英
    public static  final int LESSONTESTTYPE2 =2 ;
    //音翻英
    public static final int LESSONTESTTYPE3 =3 ;


    /**
     * 测试的类型
     */
    public static  final int BOOK_TEST =1 ;

    public static  final int UNIT_TEST =2 ;


    /**
     * 测试是否通过
     */
    public static  final int PASSED =1;

    public static  final int NOT_PASSED =0;


    /**
     * 第三方登录的类型
     */
    public static  final int WX =1 ;

    public static final int WEIBO =2 ;

    public static final int QQ =3 ;

    /**
     * 测试的数量
     */
    public static  final int BOOK_TEST_NUM =60 ;

    /**
     * 单元测试的数量
     */
    public static  final int UNIT_TEST_NUM =10 ;


    public static  final String A ="optionA";
    public static  final String B ="optionB";
    public static  final String C ="optionC";
    public static  final String D ="optionD";

    /**
     * 广告的类型
     */
     public static  final int RECHARGE_AD_TYPE =1 ;

    /**
     * 是否是学前测试
     */
    public static  final int IS_PRE_TEST =1 ;

    public static  final int NOT_PRE_TEST =0 ;
    /**
     * 是否只有显示错误的信息
     */
    public static  final  int IS_WRONG_ONLY = 1 ;

    public static  final int  ALL_WRONG_RIGHT =0 ;
    /**
     * 是否是当前的书籍
     */
    public static  final int IS_CURRENT =1 ;

    public static final int NOT_CURRENT =0 ;

    /**
     * order的类型
     */
    public static  final int ORDER_TYPE_RECHARGE =1 ;

    public static  final int ORDER_TPYE_BUY_BOOKS =2 ;


    /**
     * 是否历史单词
     */
    public static final int DELETED_ALLREADY =1  ;

    public static  final int DELETED_NOT  =0 ;

    /**
     * 是否是当前的单词
     */
    public static  final int IS_NOW_WORDS = 0;

    public static  final int NOT_NOW_WORDS = 1 ;


    /**
     * 单词的数量
     */
    public static  final int NEW_WORDS_PERMIT_NUM =20 ;


    /**
     * 纠错是否已被解决
     */
    public static  final int IS_RESOLVED = 1 ;

    //未解决
    public static  final int IS_NOT_RESOLVED =0 ;


    /**
     * 申请的状态
     */
    public static final int PASSED_STATE =1 ;

    //待审核
    public static  final int TO_PASSED_STATE =0 ;


    public static  final String DEFAULT_PASS="123456";

    public static  final String PASS_SALT="bhklMN";

    /*是否是超级管理员*/

    public static  final int IS_SUPER_MASTER =1 ;
    public static  final int NOT_MASTER =2 ;


    /**
     * 超级管理员的Name ；
     */
    public static  final String ADMIN_NAME ="Admin" ;


    /**
     * msg 的状态
     * **/
public static final int NOT_READ =0 ;
public static  final int READED =1 ;

    /**
     * 推送的状态
     */
    public static  final int PSUH_SUCCESS = 1 ;
    public static  final int PUSH_FAILED = 0 ;

    /**
     * 消息的类型
     */
    public static  final int SYSTEM_MSG = 1 ;
    public   static  final int CLASS_MSG =2 ;
    public static  final int WEB_MSG =3 ;
    public static  final int SINGAL_USER =4 ;

    /**
     * 推送的时候额外的字段key ：
     * data:{json数据}
     */
    public static  final String JPUSH_JSON_EXTRAS_KEY ="data" ;
    /**
     * 验证码的过期时间 ；
     */
    public static  final Integer codeOutTime =180 ;

    /**
     *  发送验证码的类型
     */
    //注册；
    public static  final int REGISTER =1 ;
    //修改密码
    public static  final int UPDATE_PASS_OR_FORGET =2 ;
    //绑定手机
    public static  final int BIND_PHONE =3 ;

    /**
      角色的名称
     */
    public static  final String ROLES_ADMIN ="管理员";
    public static  final String ROLES_AGENTS ="经销商";
    public static  final String ROLES_SCHOOL_MASTER ="校长";
    public static final String ROLES_TEACHER ="教师";


    /**
     *日期的格式
     */
    public static final String DATE_FORMAT_ALL ="yyyy-MM-dd HH:mm:ss" ;
    public static  final String DATE_FORMAT_ONLY_DAY ="yyyy-MM-dd" ;

    /**
     * 是否学习
     */
    public static  final  Integer isLearnEd =1 ;
    public static  final Integer notLearnEd =0 ;


    //
    public static  final String all ="all" ;
   public static  final String current ="current" ;
   public static  final String left ="left" ;


   public static final  int isMonth = 0 ;
   public static  final int notMonth =1 ;


    /**
     * 单词的学习的状态；
     * 1。未学习，提取出来进行认知---
     *  *                      2。认知成功-》
     *  *                      3。强化成功-》    ---不再是生词--
     *  *                      4。效果检测-》 -> a,生词本  b,变为生词了
     *  *                      //复习；
     *  *                      5。  a:语音学习 b:听音辨意   c智能听写  d句子填空  e句式练习
     */
    /**
     *
     */
    public static final  int isFinished = 1 ;
    public static  final int notFinished =0 ;

    public static  final int isBatchCreate = 1;
    public static  final int notBatchCreate = 0;


    public static final int RANKING_SCHOOL_TYPE =0 ;
    public static  final int RANKING_PROVINCE_TYPE =1 ;
    public static  final int RANKING_COUNTRY_TYPE =2 ;




}
