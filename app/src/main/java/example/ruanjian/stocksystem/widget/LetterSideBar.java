package example.ruanjian.stocksystem.widget;

import android.view.View;
import android.graphics.Paint;
import android.content.Context;
import android.widget.TextView;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.graphics.Typeface;
import android.util.AttributeSet;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.interce.ILetterChange;
import example.ruanjian.stocksystem.utils.StockSystemConstant;

public class LetterSideBar extends View
{

    private Paint _paint;
    private TextView _letterTxt;

    private int _curChoose = -1;


    private ILetterChange _iLetterChange;


    public LetterSideBar(Context context)
    {
        super(context);
    }

    public LetterSideBar(Context context, AttributeSet attrs)
    {

        super(context, attrs);
    }

    public LetterSideBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    public void setILetterChange(ILetterChange iLetterChange)
    {
        _iLetterChange = iLetterChange;
    }


    public void setTextView(TextView letterTxt)
    {
        _letterTxt = letterTxt;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (_paint == null)
        {
            _paint =  new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        int len = StockSystemConstant.LETTER_ARRAY.length;
        float singleHeight = (getHeight() / len);
        for(int index = 0; index < len; index++)
        {
            _paint.setTypeface(Typeface.DEFAULT_BOLD);
            _paint.setAntiAlias(true);
            if (index == _curChoose)
            {
                _paint.setColor(StockSystemApplication.getInstance().getColorById(R.color.colorGreen));
                _paint.setTextSize(14);
            }
            else
            {
                _paint.setColor(StockSystemApplication.getInstance().getColorById(R.color.colorWhite));
                _paint.setTextSize(12);
            }
            String letterStr =  StockSystemConstant.LETTER_ARRAY[index];
            float posX = (getWidth() - _paint.measureText(letterStr)) / 2;
            float posY = singleHeight * (index + 1);
            canvas.drawText(letterStr, posX, posY, _paint);
            _paint.reset();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int actionFlag = event.getAction();
        float posY = event.getY();
        int len = StockSystemConstant.LETTER_ARRAY.length;
        int index = (int)(posY / getHeight() * len);
        String letterStr =  StockSystemConstant.LETTER_ARRAY[index];
        if (_curChoose != index)
        {
            _curChoose = index;
            invalidate();
        }
        if (_iLetterChange != null)
        {
            _iLetterChange.sectionChange(_curChoose);
//            _iLetterChange.letterChange(letterStr);
        }
        switch (actionFlag)
        {
            case MotionEvent.ACTION_UP:
                if (_letterTxt != null)
                {
                    _letterTxt.setVisibility(INVISIBLE);
                }
                break;
            default:
                if (_letterTxt != null)
                {
                    _letterTxt.setText(letterStr);
                    _letterTxt.setVisibility(VISIBLE);
                }
                break;
        }
        return true;
    }



}
