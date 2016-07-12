package com.seeker.datedialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.seeker.demo.datedialogexample.R;
import com.seeker.datedialog.widget.util.DeviceUtils;

/**
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since 2016/7/12
 */
public abstract class AbsBottomDialog extends Dialog {
    protected Context mContext;

    public AbsBottomDialog(Context context) {
        super(context);
        this.mContext = context;
        this.init(this.mContext);
    }

    private void init(Context context) {
        try {
            this.requestWindowFeature(1);
//            this.layoutView = this.viewFactory.getLayoutInflater().inflate(layoutResID, (ViewGroup)null);
            this.setContentView(this.getLayoutId());
            this.getWindow().getDecorView().setBackgroundDrawable(new ColorDrawable('\uff00'));
            Window ex = this.getWindow();
            ColorDrawable dw = new ColorDrawable('\uff00');
            WindowManager.LayoutParams lp = ex.getAttributes();
            lp.width = DeviceUtils.getScreenWidth(context);
            ex.setBackgroundDrawable(dw);
            ex.setGravity(80);
            ex.setWindowAnimations(R.style.DialogBottomAnimation_NEW);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public void show() {
        super.show();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = DeviceUtils.getScreenWidth(this.mContext);
        this.getWindow().setAttributes(lp);
    }

    public abstract int getLayoutId();
}
