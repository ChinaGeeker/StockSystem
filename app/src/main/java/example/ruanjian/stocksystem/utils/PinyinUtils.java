package example.ruanjian.stocksystem.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils
{

    private static HanyuPinyinOutputFormat format;
    static {

        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
    }

    public static String getFirstUppercaseLetter(String stockName)
    {
        int len = stockName.length();
        if (len >= 1)
        {
            try {
                char first = stockName.charAt(0);
                String[] strings = PinyinHelper.toHanyuPinyinStringArray(first, format);
                if (strings == null)// 不是汉字
                {
                    return "#";
                }
                else
                {
                    return strings[0].substring(0, 1);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        return "#";
    }

}
