package example.ruanjian.stocksystem.info;

import java.io.Serializable;

public class AccountStockInfo implements Serializable
{

    //股票编号
    private  String _number;
    //股票名称
    private  String _stockName;

    private String _accountName;

    private int _recordTime;

    public int get_recordTime() {
        return _recordTime;
    }

    public void set_recordTime(int _recordTime) {
        this._recordTime = _recordTime;
    }

    public String get_accountName()
    {
        return _accountName;
    }

    public void set_accountName(String _accountName)
    {
        this._accountName = _accountName;
    }

    public String get_number()
    {
        return _number;
    }

    public void set_number(String _number) {
        this._number = _number;
    }

    public String get_stockName() {
        return _stockName;
    }

    public void set_stockName(String _stockName) {
        this._stockName = _stockName;
    }
}
