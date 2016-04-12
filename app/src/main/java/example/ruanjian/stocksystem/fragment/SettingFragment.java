package example.ruanjian.stocksystem.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.provider.MediaStore;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.graphics.drawable.ColorDrawable;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;

import example.ruanjian.stocksystem.R;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.asyncTask.CheckVersionAsyncTask;
import example.ruanjian.stocksystem.utils.AccountUtils;
import example.ruanjian.stocksystem.activity.LogActivity;
import example.ruanjian.stocksystem.utils.StockSystemConstant;
import example.ruanjian.stocksystem.service.RealTimeUpdateService;
import example.ruanjian.stocksystem.activity.SwitchAccountActivity;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View _view;

    private Button _lookLogBtn;
    private ImageView _iconBtn;
    private Button _forbidUpdateBtn;
    private Button _checkVersionBtn;
    private Button _changeAccountBtn;

    private PopupWindow _picPopupWindow;


    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void loading()
    {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (_view == null)
        {
            _view = inflater.inflate(R.layout.settting_fragment, null, false);
            _iconBtn = (ImageView) _view.findViewById(R.id.setting_fragment_iconBtn);
            _iconBtn.setOnClickListener(this);
            _changeAccountBtn = (Button) _view.findViewById(R.id.switchAccountBtn);
            _changeAccountBtn.setOnClickListener(this);

            _lookLogBtn = (Button) _view.findViewById(R.id.lookLogBtn);
            _lookLogBtn.setOnClickListener(this);

            _forbidUpdateBtn = (Button) _view.findViewById(R.id.forbidUpdateBtn);
            _forbidUpdateBtn.setOnClickListener(this);

            _checkVersionBtn = (Button) _view.findViewById(R.id.checkVersionBtn);
            _checkVersionBtn.setOnClickListener(this);

        }
       return _view;
    }

    private void refreshData()
    {
        if (AccountUtils.getCurLoginAccountInfo() == null)
        {
            Toast.makeText(getActivity(), R.string.accountPasswordWrong, Toast.LENGTH_LONG).show();
            return;
        }
        String iconPath = AccountUtils.getCurLoginAccountInfo().get_userIconPath();
        Bitmap bitmap = AccountUtils.getBitmapByIconPath(iconPath, getContext());
        if (bitmap != null)
        {
            _iconBtn.setImageBitmap(StockSystemApplication.getInstance().toRoundBitmap(bitmap));
        }
    }

    @Override
    public void onClick(View v)
    {
        int viewId = v.getId();
        Intent intent = new Intent();
        switch (viewId)
        {
            case R.id.setting_fragment_iconBtn:
                showPicPopupWindow();
                break;
            case R.id.switchAccountBtn:
                intent.setClass(getActivity(), SwitchAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.lookLogBtn:
                intent.setClass(getActivity(), LogActivity.class);
                startActivity(intent);
                break;
            case R.id.forbidUpdateBtn:
                StockSystemApplication.getInstance().stateRealTimeUpdate = StockSystemConstant.STATE_CANCELED;
                intent.setClass(getActivity(), RealTimeUpdateService.class);
                getActivity().stopService(intent);
                break;
            case R.id.checkVersionBtn:
                if (StockSystemApplication.getInstance().isConnectedNetWork() == true)
                {
                    new CheckVersionAsyncTask().execute();
                }
                break;
        }
    }


    private void saveCartureImage(Bitmap bitmap)
    {
        String path =  "/storage/emulated/0/stock/" + StockSystemApplication.getInstance().getCurTimeString() + ".jpg";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path, false);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            Uri uri = Uri.fromFile(new File(path));
            updateIcon(uri.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }catch (IOException e) {
            e.printStackTrace();
            StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null)
        {
            return;
        }
        if (requestCode == StockSystemConstant.REQUEST_CAPRTURE)
        {
            Bundle bundle = data.getExtras();
            if (bundle != null)
            {
                Bitmap bitmap = bundle.getParcelable("data");
                _iconBtn.setImageBitmap(StockSystemApplication.getInstance().toRoundBitmap(bitmap));
                saveCartureImage(bitmap);
            }
        }

        if (requestCode == StockSystemConstant.REQUEST_PIC)
        {
            Uri uri = data.getData();
            if (uri != null)
            {
                InputStream inputStream = null;
                try {
                    inputStream = getActivity().getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    _iconBtn.setImageBitmap(StockSystemApplication.getInstance().toRoundBitmap(bitmap));
                    updateIcon(uri.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    StockSystemApplication.getInstance().printLog(StockSystemConstant.LOG_ERROR, "", e);
                }
            }
        }
    }

    private void updateIcon(String iconPath)
    {
        int result = AccountUtils.updateIcon(iconPath, getActivity());
    }

    private void showPicPopupWindow()
    {
        if (_picPopupWindow == null)
        {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_popup_window, null);
            Button picStoreBtn = (Button) view.findViewById(R.id.picStoreBtn);
            Button picCancelBtn = (Button) view.findViewById(R.id.picCancelBtn);
            Button capturePhotoBtn = (Button) view.findViewById(R.id.capturePhotoBtn);

            picStoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, StockSystemConstant.REQUEST_PIC);
                    _picPopupWindow.dismiss();
                }
            });
            picCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _picPopupWindow.dismiss();
                }
            });
            capturePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, StockSystemConstant.REQUEST_CAPRTURE);
                    _picPopupWindow.dismiss();
                }
            });

            _picPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        _picPopupWindow.setOutsideTouchable(true);
        _picPopupWindow.setFocusable(false);
        _picPopupWindow.setBackgroundDrawable(new ColorDrawable(StockSystemApplication.getInstance().getColorById(R.color.colorBuff)));
        View mainView = LayoutInflater.from(getActivity()).inflate(R.layout.stock_main_activity, null);
        _picPopupWindow.showAtLocation(mainView, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onResume()
    {
        refreshData();
        super.onResume();
    }

}
