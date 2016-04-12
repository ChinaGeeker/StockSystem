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
    public final static String CHARSET_NAME_UTF8 = "utf-8";
    public final static String CHARSET_NAME_GBK = "gbk";

    public final static long REALTIME_UPDATE_TIME = 1000 * 5;// 股票更新时间 5分钟

//    public final static long REALTIME_UPDATE_TIME = 1000 * 60 * 5;// 股票更新时间 5分钟
    public final static Float STOCK_GAP = 0.5f;// 股票差距 0.5
    public final static int TYPE_LIST = 0;// stock list
    public final static int TYPE_HISTORY = 1;// history
    public final static int TYPE_SETTING = 2;// setting

    public final static String STOCK_GIF_IMAGE = "STOCK_GIF_IMAGE";// 股票动态图
    public final static String DEFAULT = "DEFAULT";

    public final static int REQUEST_PIC = 1;// 选择图片
    public final static int REQUEST_CAPRTURE = 2;// 拍照

    public final static int STATE_SUCCESS = 1;// 成功
    public final static int STATE_CANCELED = 0;// 失败


    public final static String STOCK_SYSTEM_DATABASES_NAME = "stockSystem.db";

    public final static String ACCOUNT_STOCK_TABLE_NAME = "accountStock";

    public final static String RECORD_TIME_NAME = "recordTime";


    public final static String ACCOUNT_COLUMN_ACCOUNT_NAME = "accountName";
    public final static String ACCOUNT_COLUMN_PASSWORD_NAME = "accountPassword";
    public final static String ACCOUNT_COLUMN_LOGIN_TIME_NAME = "loginTime";
    public final static String ACCOUNT_COLUMN_LOGIN_STATE_NAME = "loginState"; // 设计 只有一个登录成功的
    public final static String ACCOUNT_COLUMN_ICON_PATH_NAME = "headIconPath";

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
    public final static String STOCK_OPENING_PRICE = "openingPrice";
    public final static String STOCK_CLOSING_PRICE = "closingPrice";
    public final static String STOCK_CURRENT_PRICE = "currentPrice";
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


    public final static String[] LETTER_ARRAY = new String[]{"↑",/*"☆",*/"#","A","B","C","D","E","F","G","H","I","J","K",
            "L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};// ↑ 最上面 ; # 非拼音开头 比如数字 或其他符号 开头

    public static final String VERSION_UPGRADE_ACTION = "VERSION_UPGRADE_ACTION";
    public static final String STOCK_MAIN_ACTION = "STOCK_MAIN_ACTION";
    public static final String STOCK_GIF_IMAGE_ACTION = "STOCK_GIF_IMAGE_ACTION";


    public static final String STOCK_MAIN_TYPE = "STOCK_MAIN_TYPE";


    public static final String UPDATE_STOCK_URL ="http://192.168.56.1:80/StockSystem/updateStock.php";
    public static final String QUERY_ACCOUNT_STOCK_URL ="http://192.168.56.1:80/StockSystem/queryAccountStock.php";
    public static final String REGISTER_URL ="http://192.168.56.1:80/StockSystem/register.php";
    public static final String LOGIN_URL ="http://192.168.56.1:80/StockSystem/login.php";
    public static final String ADD_STOCK_URL ="http://192.168.56.1:80/StockSystem/addStock.php";
    public static final String ADD_ACCOUNT_STOCK_URL ="http://192.168.56.1:80/StockSystem/addAccountStock.php";
    public static final String SEARCH_STOCK_URL ="http://192.168.56.1:80/StockSystem/searchStock.php";

    public static final String DELETE_ACCOUNT_STOCK_URL ="http://192.168.56.1:80/StockSystem/deleteAccountStock.php";

    public static final String SYSTEM_VERSION_URL ="http://192.168.56.1:80/StockSystem/versionInfo.php";

    public static final String RETURN_TYPE = "returnType";
    public static final String RETURN_MESSAGE = "returnMessage";
    public static final String RETURN_CONTENT = "returnContent";

    public static final String SYSTEM_VERSION_NAME = "versionName";
    public static final String SYSTEM_VERSION_CODE = "versionCode";
    public static final String SYSTEM_VERSION_DESC = "describe";
    public static final String SYSTEM_VERSION_APK_URL = "versionUrl";

}
