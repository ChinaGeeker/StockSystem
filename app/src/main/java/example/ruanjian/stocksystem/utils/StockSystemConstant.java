package example.ruanjian.stocksystem.utils;

public class StockSystemConstant
{
    public static final String LOG_PREFIX_NAME = "";
    public static final String LOG_FILE_NAME = "stockSystem.log";

//    public static final String LINE_BREAK = "\n";


    public static final int LOG_WARN = 0;
    public static final int LOG_INFO = 1;
    public static final int LOG_ERROR = 2;
    public static final int LOG_DEBUG = 3;

    public final static String STOCK_INFO = "STOCK_INFO";


    //失败
    public final static String FAILED = "FAILED";
    //    public final static String CHRSET_NAME_UTF8 = "utf-8";
    public final static String CHRSET_NAME_GBK = "gbk";

    public final static long REALTIME_UPDATE_TIME = 1000 * 60 * 5;// 股票更新时间 5分钟
    public final static Float STOCK_GAP = 0.5f;// 股票差距 0.5
    public final static int TYPE_LIST = 0;// stock list
    public final static int TYPE_HISTORY = 1;// history
    public final static int TYPE_SETTING = 2;// ssetting

    public final static String STOCK_IMAGE = "STOCK_IMAGE";// 股票动态图
    public final static String DEFAULT = "DEFAULT";

    public final static int REQUEST_PIC = 1;// 选择图片
    public final static int REQUEST_CARTURE = 2;// 拍照

    public final static int STATE_SUCCESS = 1;// 登录成功
    public final static int STATE_CANCELED = 0;// 未登录


    public final static String STOCK_SYSTEM_DATABASES_NAME = "stockSystem.db";

    public final static String ACCOUNT_STOCK_TABLE_NAME = "accountStock";

    public final static String RECORD_TIME_NAME = "recordTime";


    public final static String ACCOUNT_TABLE_NAME = "account";
    public final static String ACCOUNT_COLUMN_ACCOUNT_NAME = "accountName";
    public final static String ACCOUNT_COLUMN_PASSWORD_NAME = "password";
    public final static String ACCOUNT_COLUMN_LOGIN_TIME_NAME = "loginTime";
    public final static String ACCOUNT_COLUMN_LOGIN_STATE_NAME = "loginState"; // 设计 只有一个登录成功的
    public final static String ACCOUNT_COLUMN_ICON_PATH_NAME = "userIconPath";


    public final static String HISTORY_BROWSING_TABLE_NAME = "historyBrows";
    //数据库的索引列名称
    public final static String ACCOUNT_NAME = "accountName";
    public final static String RECORD_DETAIL = "recordDetail";
    public final static String STOCK_NAME = "stockName";
    public final static String HISTORY_BROWSING_TIME = "historyTime";

    public final static String HISTORY_BROWSING_ADD = "$1您添加了股票$2，股票代码为$3";
    public final static String HISTORY_BROWSING_VIEW = "$1您访问了股票$2，股票代码为$3";

    public final static String HISTORY_BROWSING_DELETE = "$1您删除了股票$2，股票代码为$3";


    public final static int HISTORY_ADD_TYPE = 0;
    public final static int HISTORY_VIEW_TYPE = 1;
    public final static int HISTORY_REMOVE_TYPE = 2;


    //各个股票信息在数据库的索引列名称
    public final static String STOCK_TABLE_NAME = "stock";
    public final static String STOCK_NUMBER = "stockNumber";
    public final static String STOCK_TITLE_NAME = "stockName";
    public final static String STOCK_OPENING_PRICE = "openPrice";
    public final static String STOCK_CLOSING_PRICE = "closePrice";
    public final static String STOCK_CURRENT_PRICE = "curPrice";
    public final static String STOCK_MAX_PRICE = "maxPrice";
    public final static String STOCK_MIN_PRICE = "minPrice";
    //    list=sh601006
    //股票查询网址
    public static final String QUERY_URL = "http://hq.sinajs.cn/list=";
    //股票K线图
    public static final String QUERY_IMG ="http://image.sinajs.cn/newchart/daily/n/$.gif";

    //各个股票信息在数组中的索引
    public static final int NAME=0;
    public static final int OPENING_PRICE=1;
    public static final int CLOSING_PRICE=2;
    public static final int CURRENT_PRICE=3;
    public static final int MAX_PRICE=4;
    public static final int MIN_PRICE=5;
    public static final String STOCK_DATA_REX = "str_(.+)=\"(.*)\"(.*)";


}
