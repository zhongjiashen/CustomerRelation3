package com.cr.adapter.gzpt.dwzl;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.crcxj.activity.R;

public class GzptDwzlDwLxfsAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    private BaseActivity activity;

    public GzptDwzlDwLxfsAdapter(List<Map<String, Object>> list, BaseActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final Map<String, Object> objMap = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.activity_gzpt_dwzl_dw_lxfs_item, null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
            viewHolder.namevalueTextView = (TextView) convertView.findViewById(R.id.namevalue_textview);
            viewHolder.dhImageView = (ImageView) convertView.findViewById(R.id.dh_imageview);
            viewHolder.dxImageView = (ImageView) convertView.findViewById(R.id.dx_imageview);
            viewHolder.qqImageView = (ImageView) convertView.findViewById(R.id.qq_imageview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (objMap.get("lb").toString().equals("2")) {
            viewHolder.nameTextView.setText("手机");
            viewHolder.dhImageView.setVisibility(View.VISIBLE);
            viewHolder.dxImageView.setVisibility(View.VISIBLE);
            viewHolder.qqImageView.setVisibility(View.GONE);
        } else if (objMap.get("lb").toString().equals("1")) {
            viewHolder.nameTextView.setText("固话");
            viewHolder.dhImageView.setVisibility(View.VISIBLE);
            viewHolder.dxImageView.setVisibility(View.INVISIBLE);
            viewHolder.qqImageView.setVisibility(View.GONE);
        } else if (objMap.get("lb").toString().equals("3")) {
            viewHolder.nameTextView.setText("邮箱");
            viewHolder.dhImageView.setVisibility(View.INVISIBLE);
            viewHolder.dxImageView.setVisibility(View.INVISIBLE);
            viewHolder.qqImageView.setVisibility(View.GONE);
        } else if (objMap.get("lb").toString().equals("4")) {
            viewHolder.nameTextView.setText("Q Q");
            viewHolder.dhImageView.setVisibility(View.GONE);
            viewHolder.dxImageView.setVisibility(View.INVISIBLE);
            viewHolder.qqImageView.setVisibility(View.VISIBLE);
        } else if (objMap.get("lb").toString().equals("5")) {
            viewHolder.nameTextView.setText("   ");
            viewHolder.dhImageView.setVisibility(View.GONE);
            viewHolder.dxImageView.setVisibility(View.GONE);
            viewHolder.qqImageView.setVisibility(View.GONE);
        } else {
            viewHolder.dhImageView.setVisibility(View.GONE);
            viewHolder.dxImageView.setVisibility(View.INVISIBLE);
            viewHolder.qqImageView.setVisibility(View.INVISIBLE);
        }
        viewHolder.dhImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String number = objMap.get("itemno").toString();
                if (number.equals("")) {
                    activity.showToastPromopt("电话号码为空");
                } else {
                    //用intent启动拨打电话
                    callPhone(number);
//                    Intent gh1intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
//                    activity.startActivity(gh1intent);
                }
            }
        });
        viewHolder.dxImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String number5 = objMap.get("itemno").toString();
                if (number5.equals("")) {
                    activity.showToastPromopt("电话号码为空");
                } else {//发送短信
//                    callPhone(number5);
                    Intent intentdx1 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number5));
                    activity.startActivity(intentdx1);
                }
            }
        });
        viewHolder.qqImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String number5 = objMap.get("itemno").toString();
                if (number5.equals("")) {
                    activity.showToastPromopt("请输入QQ号！");
                } else {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + number5 + "&version=1";
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        });
        viewHolder.namevalueTextView.setText(objMap.get("itemno").toString());
        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView namevalueTextView;
        ImageView dhImageView;
        ImageView dxImageView;
        ImageView qqImageView;
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        activity.startActivity(intent);
    }

}