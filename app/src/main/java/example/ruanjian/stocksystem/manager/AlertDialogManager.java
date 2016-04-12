package example.ruanjian.stocksystem.manager;

import android.view.View;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;

import example.ruanjian.stocksystem.R;


public class AlertDialogManager
{

    //  不带按钮
    public static AlertDialog getAlertDialog(Context context, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.alert_dialog_common, null, false);
        TextView textView =(TextView) view.findViewById(R.id.alert_common_contentTxt);
        textView.setText(message);
        builder.setView(view);
        return  builder.create();
    }

    public static void closeAlertDialog(AlertDialog alertDialog)
    {
        if (alertDialog != null)
        {
            alertDialog.dismiss();
        }
    }



}
