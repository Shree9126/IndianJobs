package com.tamilanjobs.Helper;


import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tamilanjobs.R;


public class Dialogue {

    static MaterialDialog.Builder builderConnectionError(AppCompatActivity activity) {

        if (activity != null) {
            return new MaterialDialog.Builder(activity)
                    .title(R.string.dialogue_connection_error_title)
                    .content(R.string.dialogue_connection_error_content)
                    .positiveText(R.string.dhalogue_btn_ok)
                    .canceledOnTouchOutside(false);
        } else {
            return null;
        }
    }

    public static MaterialDialog connectionError(AppCompatActivity activity) {

        if (activity != null) {
            return builderConnectionError(activity).build();
        } else {
            return null;
        }
    }

    public static MaterialDialog loading(AppCompatActivity activity) {

        if (activity != null) {
            return new MaterialDialog.Builder(activity)
                    .content(R.string.dhalogue_loading_content)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .build();
        } else {
            return null;
        }
    }

    public static MaterialDialog connectionErrorFinish(final AppCompatActivity activity) {

        if (activity != null) {
            return builderConnectionError(activity).dismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    activity.onBackPressed();
                }
            }).build();
        } else {
            return null;
        }
    }

    static MaterialDialog.Builder builderTryAgain(AppCompatActivity activity) {
        return new MaterialDialog.Builder(activity)
                .title(R.string.dialogue_try_again_title)
                .content(R.string.dialogue_try_again_content)
                .positiveText(R.string.dhalogue_btn_try_again)
                .canceledOnTouchOutside(false);
    }

    public static MaterialDialog tryAgain(AppCompatActivity activity) {
        return builderTryAgain(activity).build();
    }

    public static MaterialDialog tryAgainFinish(final AppCompatActivity activity) {
        return builderTryAgain(activity).dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                activity.onBackPressed();
            }
        }).build();
    }
/*

    public static MaterialDialog deleteBeneficiary(AppCompatActivity activity) {
        return new MaterialDialog.Builder(activity)
                .title(R.string.dialogue_delete_ben_title)
                .content(R.string.dialogue_delete_ben_content)
                .negativeText(R.string.dhalogue_btn_cancel)
                .positiveText(R.string.dhalogue_btn_delete)
                .canceledOnTouchOutside(false)
                .build();
    }
*/


    public static MaterialDialog retry(final AppCompatActivity activity , MaterialDialog.SingleButtonCallback retryCallback) {
        return new MaterialDialog.Builder(activity)
                .title(R.string.dialogue_retry_title)
                .content(R.string.dialogue_retry_content)
                .positiveText(R.string.dhalogue_btn_retry)
                .negativeText(R.string.dhalogue_btn_exit)
                .cancelable(false)
                .onPositive(retryCallback)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        activity.finish();
                    }
                })
                .canceledOnTouchOutside(false)
                .build();
    }


}
