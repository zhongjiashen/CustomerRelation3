package com.cr.adapter.khfw;

import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cr.activity.BaseActivity;
import com.crcxj.activity.R;

/**
 * 客户服务-选择商品的适配器
 * 
 * @author caiyanfei
 * @version $Id: KhfwXzspAdapter.java, v 0.1 2015-4-27 下午3:32:24 caiyanfei Exp $
 */
public class KhfwXzspAdapter extends BaseAdapter {

    List<Map<String, Object>> list;
    List<Map<String, Object>> oldList;
    private BaseActivity      activity;
    private int               selectIndex;

    public KhfwXzspAdapter(List<Map<String, Object>> list, BaseActivity activity) {
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

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final Map<String, Object> objMap = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                R.layout.activity_khfw_xzsp_item, null);// 这个过程相当耗时间
            viewHolder = new ViewHolder();
            viewHolder.mcTextView = (TextView) convertView.findViewById(R.id.mc_textview);
            viewHolder.bhTextView = (TextView) convertView.findViewById(R.id.bh_textview);
            viewHolder.ggTextView = (TextView) convertView.findViewById(R.id.gg_textview);
            viewHolder.xhTextView = (TextView) convertView.findViewById(R.id.xh_textview);
//            viewHolder.kcTextView = (TextView) convertView.findViewById(R.id.kc_textview);
            convertView.setTag(viewHolder);
        }else{
        	viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.mcTextView.setText("名称：" + objMap.get("name").toString());
        viewHolder.bhTextView.setText("编号：" + objMap.get("code").toString());
        viewHolder.ggTextView.setText("规格：" + objMap.get("specs").toString());
        viewHolder.xhTextView.setText("型号：" + objMap.get("model").toString());
        return convertView;
    }

    static class ViewHolder {
        TextView mcTextView;
        TextView bhTextView;
        TextView ggTextView;
        TextView xhTextView;
//        TextView kcTextView;
//        CheckBox itemCheckBox;
    }
}