package com.acronix.danachos.mcm.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by User on 11/27/2017.
 */

public class PopupUtil {
    private static Dialog mDialog;

    public static void showLoading(Context context, String msg) {
        dismiss();
        mDialog = ProgressDialog.show(context, "",
                msg, true);
    }

    public static void dismiss() {
        if(mDialog != null)
        {
            mDialog.dismiss();
        }
    }

}
