package example.ruanjian.stocksystem.info;

import java.io.Serializable;

public class StockInfo implements Serializable {

	//股票编号
	private  String _number;
	//股票名称
	private  String _stockName;
	//今日开盘价
	private  String _openingPrice;
	//昨日收盘价
	private  String _closingPrice;
	//当前价格
	private  String _currentPrice;
	//今日最高价
	private  String _maxPrice;
	//今日最低价
	private  String _minPrice;

	//取得股票编号
	public String get_number()
	{
		return _number;
	}
	//设置股票编号
	public void set_number(String _number)
	{
		this._number = _number;
	}
	//取得股票名称
	public String getName()
	{
		return _stockName;
	}
	//设置股票名称
	public void setName(String stockName)
	{
		this._stockName=stockName;
	}
	//取得股票今日开盘价
	public String get_openingPrice()
	{
		return _openingPrice;
	}
	//设置股票今日开盘价
	public void set_openingPrice(String _openingPrice)
	{
		this._openingPrice = _openingPrice;
	}
	//取得股票昨日收盘价
	public String getClosing_price()
	{
		return _closingPrice;
	}
	//设置股票昨日收盘价
	public void setClosing_price(String closing_price)
	{
		this._closingPrice=closing_price;
	}
	//取得股票当前价格
	public String get_currentPrice()
	{
		return _currentPrice;
	}

	public float getCurrentPrice()
	{
		return Float.parseFloat(_currentPrice);
	}

	//设置股票当前价格
	public void set_currentPrice(String _currentPrice)
	{
		this._currentPrice = _currentPrice;
	}
	//取得股票今日最高价
	public String get_maxPrice()
	{
		return _maxPrice;
	}
	//设置股票今日最高价
	public void set_maxPrice(String _maxPrice)
	{
		this._maxPrice = _maxPrice;
	}
	//取得股票今日最低价
	public String get_minPrice()
	{
		return _minPrice;
	}
	//设置股票今日最低价
	public void set_minPrice(String _minPrice)
	{
		this._minPrice = _minPrice;
	}
}