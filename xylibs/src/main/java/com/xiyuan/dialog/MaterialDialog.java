package com.xiyuan.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiyuan.xylibs.R;

/**
 * Created by YT on 2016/3/16.
 */
public class MaterialDialog extends Dialog implements View.OnClickListener{

    private static final int TITLE_COLOR_DEFAULT = Color.parseColor("#333333");

    private static final int MESSAGE_COLOR_DEFAULT = Color.parseColor("#666666");

    private static final int NEGATIVE_COLOR_DEFAULT = Color.parseColor("#555555");

    private static final int POSITIVE_COLOR_DEFAULT = Color.parseColor("#ff359ff2");

    private TextView dialogTitle;

    private LinearLayout contentBox;

    private TextView message;

    private TextView dialogNegative;

    private TextView dialogPositive;

    private boolean autoDismissAfterPositiveClick = true;

    private View.OnClickListener negativeClickListener = null;

    private View.OnClickListener positiveClickListener = null;

    public MaterialDialog(Context context) {
        super(context, R.style.MaterialDialog);
        setContentView(R.layout.xiyuan_material_dialog);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int w = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) - (120 * metrics.density + 0.5f));

        dialogTitle = (TextView) this.findViewById(R.id.dialogTitle);
        dialogTitle.setLayoutParams(new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.WRAP_CONTENT));

        contentBox = (LinearLayout) this.findViewById(R.id.contentBox);
        message = (TextView) this.findViewById(R.id.message);
        dialogNegative = (TextView) this.findViewById(R.id.dialogNegative);
        dialogPositive = (TextView) this.findViewById(R.id.dialogPositive);
    }

    public void setAutoDismissAfterPositiveClick(boolean autoDismissAfterPositiveClick) {
        this.autoDismissAfterPositiveClick = autoDismissAfterPositiveClick;
    }

    public MaterialDialog setDialogTitle(CharSequence title)
    {
        return setDialogTitle(title, TITLE_COLOR_DEFAULT);
    }

    public MaterialDialog setDialogTitle(CharSequence title, int color)
    {
        dialogTitle.setText(title);
        dialogTitle.setTextColor(color);
        return this;
    }

    public MaterialDialog setDialogMessage(CharSequence msg)
    {
        return setDialogMessage(msg, MESSAGE_COLOR_DEFAULT);
    }

    public MaterialDialog setDialogMessage(CharSequence msg, int color)
    {
        message.setText(msg);
        message.setTextColor(color);
        return this;
    }

    public MaterialDialog setDialogNegative(CharSequence label, View.OnClickListener listener)
    {
        return setDialogNegative(label, NEGATIVE_COLOR_DEFAULT, listener);
    }

    public MaterialDialog setDialogNegative(CharSequence label, int color,View.OnClickListener listener)
    {
        negativeClickListener = listener;

        dialogNegative.setText(label);
        dialogNegative.setTextColor(color);
        dialogNegative.setOnClickListener(this);
        dialogNegative.setVisibility(View.VISIBLE);
        return this;
    }

    public MaterialDialog setDialogNegativeColor(int color)
    {
        dialogNegative.setTextColor(color);
        dialogNegative.setVisibility(View.VISIBLE);
        return this;
    }

    public MaterialDialog setDialogNegative(CharSequence label)
    {
        return setDialogNegative(label, null);
    }

    public MaterialDialog setDialogPositive(CharSequence label, View.OnClickListener listener)
    {
        return setDialogPositive(label, POSITIVE_COLOR_DEFAULT, listener);
    }

    public MaterialDialog setDialogPositive(CharSequence label, int color,View.OnClickListener listener)
    {
        positiveClickListener = listener;

        dialogPositive.setText(label);
        dialogPositive.setTextColor(color);
        dialogPositive.setOnClickListener(this);
        dialogPositive.setVisibility(View.VISIBLE);
        return this;
    }

    public MaterialDialog setDialogPositiveColor(int color)
    {
        dialogPositive.setTextColor(color);
        dialogPositive.setVisibility(View.VISIBLE);
        return this;
    }

    public MaterialDialog setDialogPositive(CharSequence label)
    {
        return setDialogPositive(label, null);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.dialogNegative) {
            if (negativeClickListener != null) {
                negativeClickListener.onClick(v);
            }
            else {
                dismiss();
            }
        }
        else if (i == R.id.dialogPositive) {
            if (positiveClickListener != null) {
                positiveClickListener.onClick(v);
                if (autoDismissAfterPositiveClick) {
                    dismiss();
                }
            }
            else {
                dismiss();
            }
        }
    }

    public final View replaceContent(int layoutId)
    {
        message.setVisibility(View.GONE);
        View view = getLayoutInflater().inflate(layoutId, contentBox, true);
        return view;
    }

}
