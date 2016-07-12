package com.seeker.datedialog.widget.wheel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seeker.datedialog.widget.util.DeviceUtils;
import com.seeker.datedialog.widget.wheel.abs.WheelAdapterView;
import com.seeker.datedialog.widget.wheel.abs.WheelGallery;


/**
 * 纯文字，WheelTextView。。 选中的文字，会高亮，中部有实现分割线
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since 2016/4/13
 */
public class WheelTextView extends WheelView{

    private  WheelTextAdapter wheelTextAdapter;
    Context context;
    private String[] mData;
    private OnWheelItemSelectedListener listener;
    private int textColor=0xffff5073;
    private int textColorUnselect=0xff999999;

    public WheelTextView(Context context) {
        super(context);
        
        init(context);
    }

    /**
     * 换肤 需要这个构造函数
     * @param context
     * @param attrs
     */
    public WheelTextView(Context context, AttributeSet attrs) {
        super(context,attrs);
        
        init(context);
    }

    public WheelTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context){
        this.context=context;
        this.wheelTextAdapter = new WheelTextAdapter(context);
        this.setAdapter(wheelTextAdapter);
        this.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelAdapterView<?> parent, View view, int position, long id) {
                wheelTextAdapter.setSelectItem(position);
                if(listener!=null){
                    listener.onItemSelected(parent,view,position,id);
                }

            }

            @Override
            public void onNothingSelected(WheelAdapterView<?> parent) {

            }
        });
    }
    
    
    public void setData(String[] data) {
        mData = data;
        wheelTextAdapter.setData(mData);
        wheelTextAdapter.notifyDataSetChanged();
    }

    public void setSelect(int select){
        this.setSelection(select);
        wheelTextAdapter.setSelectItem(select);
    }
    
    public void setColor(int color){
//        super.setPaintColor(color);
        textColor=color;
    }
    
    public void setOnWheelItemSelectListener(OnWheelItemSelectedListener listener){
        this.listener=listener;
    }


/**
 * Adapter数据源 TextVIew实现
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since  2016/4/13 21:39
 */
    protected class WheelTextAdapter extends BaseAdapter {
        String[] mData = {};
        int mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int mHeight = 60;
        private int selectItem;
        Context mContext = null;

        public WheelTextAdapter(Context context) {
            mContext = context;
            mHeight = (int) DeviceUtils.dip2px(context, mHeight);
        }

        public void setData(String[] data) {
            mData = data;
            this.notifyDataSetChanged();
        }
//
//        public void setItemSize(int width, int height) {
//            mWidth = width;
//            mHeight = (int) Utils.pixelToDp(mContext, height);
//        }

        public void setSelectItem(int selectItem) {

            if (this.selectItem != selectItem) {
                this.selectItem = selectItem;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public String getItem(int position) {
            return mData[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = null;

            if (null == convertView) {
                convertView = new TextView(mContext);
                convertView.setLayoutParams(new WheelGallery.LayoutParams(mWidth, mHeight));
                textView = (TextView) convertView;
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            }

            if (null == textView) {
                textView = (TextView) convertView;
            }

            String text = mData[position];
            textView.setText(text);

            if(selectItem==position){
                textView.setTextColor(textColor);
            }else{
                textView.setTextColor(textColorUnselect);
            }

            return convertView;
        }
    }


    public interface OnWheelItemSelectedListener {
        public void onItemSelected(WheelAdapterView<?> parent, View view, int position, long id);
    }
}
