package com.seeds.economic.assistance.myapplication2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;




public class CustomResponseDialog {
    private Context mContext;
    private Dialog dialog;
    private static String TAG = "LogCustomResponseDialog";

    public CustomResponseDialog(Context mContext) {
        try{
            this.mContext = mContext;
            dialog = new Dialog(mContext);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }catch (Exception e){

        }

    }


    public void showCustomDialog(){
        try{
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.progress_dialog);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        }catch (Exception e){

        }

    }

    public void hideCustomeDialog(){
        try{
            dialog.cancel();
        }catch (Exception e){

        }

    }

}
