package example.ruanjian.stocksystem.info;

public class HistoryRecordInfo
{
    private String _accountName = "accountName";
    private String _recordDetail = "";
    private String _stockName = "";
    private String _stockNumber = "";
    private String _historyTime = "";


    public String get_accountName() {
        return _accountName;
    }

    public void set_accountName(String _accountName) {
        this._accountName = _accountName;
    }

    public String get_historyTime() {
        return _historyTime;
    }

    public void set_historyTime(String historyTime) {
        this._historyTime = historyTime;
    }

    public String get_recordDetail() {
        return _recordDetail;
    }

    public void set_recordDetail(String _recordDetail) {
        this._recordDetail = _recordDetail;
    }

    public String get_stockName() {
        return _stockName;
    }

    public void set_stockName(String _stockName) {
        this._stockName = _stockName;
    }

    public String get_stockNumber() {
        return _stockNumber;
    }

    public void set_stockNumber(String _stockNumber) {
        this._stockNumber = _stockNumber;
    }
}
