package example.ruanjian.stocksystem.info;

import java.io.Serializable;

public class AccountInfo implements Serializable
{


    private int _loginState = 0; //0 为没有登录, 1 登录

    private String _password;
    private String _accountName;

    private int _loginTime = 0;

    private String _userIconPath = "DEFAULT";

    public int get_loginState() {
        return _loginState;
    }

    public void set_loginState(int _loginState) {
        this._loginState = _loginState;
    }

    public String get_userIconPath() {
        return _userIconPath;
    }

    public void set_userIconPath(String _userIconPath) {
        this._userIconPath = _userIconPath;
    }


    public int get_loginTime() {
        return _loginTime;
    }

    public void set_loginTime(int _loginTime) {
        this._loginTime = _loginTime;
    }


    public String get_accountName()
    {
        return _accountName;
    }

    public void set_accountName(String _accountName)
    {
        this._accountName = _accountName;
    }


    public String get_password()
    {
        return _password;
    }

    public void set_password(String _password)
    {
        this._password = _password;
    }


}
