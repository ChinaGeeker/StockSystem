package example.ruanjian.stocksystem.info;

public class VersionInfo
{
    private int _versionCode;
    private String _versionUrl;
    private String _versionName;
    private String _versionDescribe;


    public int get_versionCode() {
        return _versionCode;
    }

    public void set_versionCode(int _versionCode) {
        this._versionCode = _versionCode;
    }

    public String get_versionDescribe() {
        return _versionDescribe;
    }

    public void set_versionDescribe(String _versionDescribe) {
        this._versionDescribe = _versionDescribe;
    }

    public String get_versionName() {
        return _versionName;
    }

    public void set_versionName(String _versionName) {
        this._versionName = _versionName;
    }

    public String get_versionUrl() {
        return _versionUrl;
    }

    public void set_versionUrl(String _versionUrl) {
        this._versionUrl = _versionUrl;
    }
}
