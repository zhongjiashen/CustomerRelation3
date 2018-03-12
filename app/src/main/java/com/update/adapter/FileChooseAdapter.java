package com.update.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.crcxj.activity.R;
import com.update.model.FileChooseData;
import com.update.viewholder.ViewHolderFactory;

import java.util.List;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/3/12 0012 上午 11:29
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/3/12 0012         申中佳               V1.0
 */
public abstract class FileChooseAdapter extends RecyclerView.Adapter<ViewHolderFactory.ChooseFileResultHolder> {
    private List<FileChooseData> mList;



    public void setList(List<FileChooseData> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderFactory.ChooseFileResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolderFactory.getChooseFileResultHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolderFactory.ChooseFileResultHolder holder, int position) {
        if (mList==null||mList.size() == position) {//判断是否是添加新文件的条目
            holder.ivDelete.setVisibility(View.GONE);
            holder.sivImage.setImageResource(R.mipmap.add);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkFile();
                }
            });
        }else {
            holder.ivDelete.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public abstract void checkFile();

    @Override
    public int getItemCount() {
        if (mList == null)
            return 1;
        else
            return mList.size() + 1;
    }
}
