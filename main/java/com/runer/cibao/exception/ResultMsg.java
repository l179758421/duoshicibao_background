package com.runer.cibao.exception;

/**
 * @Author szhua
 * 2018-05-14
 * 14:38
 * @Descriptionssmartcommunity== ResultMsg
 **/
public enum  ResultMsg {

    SUCCESS("succsee",100),


    LIST_DATA_UNKNOW_ERROR("列表数据未知",110),
    ENTITY_ID_NOT_EXISTS("当前ID不存在",104),
    CODE_SEND_FAILED("验证码发送失败",105),
    PHONE_NUMER_NOT_EXISTS("手机号不存在",106),
    CODE_IS_NULL("验证码为空",107) ,
    CODE_IS_NOT_EQUAL("验证码校验失败",108),
    CODE_IS_NOT_SEND_OR_TIMEOUT("验证码已失效或者未发送",99),
    PHONE_NUMBER_IS_REGISTED("手机号已被注册",109),
    MOBILEPHONE_EXISTS("手机号已经存在",101),
    PHONE_NUMBER_IS_NULL("手机号或者UID不能为空",102),
    UPLOAD_FILE_SUCCESS("文件上传成功",SUCCESS.getMsgCode()),
    UPLOAD_FILE_ERROR("文件上传失败",112),
    FILE_IS_NULL("文件为空",113),
    IDS_IS_NOT_ILLEGAL("ids格式不正确",114),
    USERNAME_IS_NULL("用户名为空",115),
    PASSWORD_IS_NULL("密码为空",116),
    PASSWORID_IS_ILLEGEAL("密码不正确",117),
    USER_IS_NOT_EXIST("用户不存在",118),
    COOKIE_IS_NULL("用户的COOKIE不存在",119),
    ILLEGEAL_LOGIN("你正在用非正常方式进入本站",120),
    COOKIE_IS_TIME_OUT("你的Cookie已经失效,请重新登陆",121),
    COOKIE_SAVE_FAILED("cookie储存失败,请重新登陆",122),
    LOGIN_FLAG_IS_ILLEAGAL("登录的标志不符合标准",123),
    ADMIN_USERLOGIN_TIMEOUT("后台用户未登录",124),

    USER_ID_IS_NOT_ALLOWED_NULL("用户的id不能够为空",125),
    NUMBER_IS_NULL_FOR_REDEEMCODE("充值码的数量为空",126),

    TEMPTE_IS_NOT_ALLOWED_NULL("模版不能够为空",127),

    OS_ERROR("发生系统错误",128),

    BOOK_ID_IS_NULL("课本的id不能够为空",129),
    PARAPHRASE_IS_NULL("释义为空",130),
    SIMPLESTENCE_IS_NULL("例句为空",140),
    TRANSLATE_IS_NULL("翻译为空",141),
    ROOTSAFFIXES_IS_NULL("词根词缀不能为空",142),
    WORD_NAME_IS_NULL("单词的名称为空",143),
    CONENT_IS_NOT_ALLOWED_NULL("内容不允许为空",144),
    ID_IS_NULL("id不能够为空",145),
    CODE_ISNOT_SENT("验证码还未发送",146),
    USER_IS_EXISTED("用户已经存在了",147),
    PASSWORD_LENGTH_IS_6("密码长度至少6位",148),
    USERNAME_IS_EXISTED("用户名已存在",149),

    //word 相关
    WORD_IS_NULL("word不允许为空",150),
    PHONETIC_SYMBOL_IS_NULL("音标不能为空",151),
    INTERPRETATION_IS_NULL("释义不能为空",152),
    LIST_DATA_IS_EMPTY("数据为空",153),
    PLEASE_CHECLK_EXCEL("请检查您录入的excel文件，是否表头不符或内容不存在,检查后上传！",154),
    UNIT_ID_IS_NULL("单元ID不能够为空",155),
    FIRST_INPUT_BOOKINFO_OR_UNITINFO("确保课本单元版本信息与数据库中保持一致",156),

    OPTION_IS_NULL("选项为空",157),

    RIGHT_ANSWER_IS_NULL("请填写正确的答案",158),

    NOT_FOUND_ERROR("实体类为未找到错误",159),

    ENTITY_IS_EXISTED("实体类早已存在",160),

    BOOKID_UNITID_IS_BOTH_NOT_NULL("课本id和单元id不能同时存在",161),

    BOOKID_UNITID_IS_BOTH_NULL("课本id和单元id不能同时为空",161),

    TEST_QUESTIONS_IS_EXISTED("课本或者单元的套题已经存在,请选择编辑当前套题",162),


    THIRD_TYPE_IS_NULL("第三方的类型不能够为空",163),

    ALREADY_BINDED("已经绑定了",164),

    PASSWORD_IS_NULL_FOR_BINDING_PHONE("第一次绑定手机必须填写密码",165),

    ADDRESS_IS_EXISTED_FOR_THIS_USER("此用户已有收货地址，不能够重复添加",166),

    ADDRESS_ID_IS_NULL("省市区id不能为空",167),

    DETAIL_ADDRESS_IS_NULL("详细地址不能为空",168),
    RECEIVED_NAME_IS_NULL("收货人姓名不能为空",169),

    BOOKID_UNITID_IS_BOTH_EXISTED("课本id和单元id不能同时存在",170),

    TODAY_TESTED("今天已经测试过了，明天再来吧",171),

    NOT_FOUND("未找到对应的数据",172),

    CODE_TIME_OUT("对不起，此充值码已过期",173),
    CODE_IS_NOT_ILLELALL("您输入的充值码有误",174),
    YOUR_ACCOUNT_IS_NOT_ENOUGH("您的账户余额不足",175),

    NEW_WORDS_LIMIT_OUT("生词个数达到上限，请学习巩固！！！",176),

    NOW_WORD_CAN_NOT_DELETE("当前生词不可删除",177),


    WORD_IS_CONSOLIDATED("本单词已巩固过了",178),

    DES_IS_NULL("描述不能够为空",179),

    TODAY_PUNCHED("今日已经打卡了",180),


    USER_IS_BINDED_SCHOOL("当前用户已经绑定学校了",181),

    LOGIN_NAME_IS_NULL("登录名不能为空",182),

    LOGIN_NAME_REPEATED("登录名重复了",183),
    SCHOOL_ID_IS_NULL("学校ID为空",184),
    JPUSH_FAILED("极光推送失败",185),

    WORD_NAME_EXISTED("此单词已经存在",186) ,

    CODE_TYPE_IS_NUll("验证码的类型为空",187 ),
    SUP_READ_BOOK_TIMEOUT("课本免费体验时间已过",188),

    HAD_BOUGHT_BOOKS("您已购买此图书，请直接同步离线数据进行学习",199),

    CHANGCHUANGENGXIN("上传更新成功!",200),

    CH_BN_CHONGMING("上传文件含有不同的课本名,请检查后重新上传!",201),

    ;


    private String message ;
    private int msgCode ;
    ResultMsg(String message ,int msgCode){
        this.message =message ;
        this.msgCode =msgCode ;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }
}
