package com.update.base;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qtkj.qtkezukeshouandroid.R;
import com.qtkj.qtkezukeshouandroid.widget.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2017/11/7 0007 下午 3:36
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2017/11/7 0007         申中佳               V1.0
 */
public class SelectPhotosAdapter extends BaseRecycleViewAdapter<LocalMedia> {


    public SelectPhotosAdapter(Activity activity) {
        super(activity);
    }

    /**
     * 子类添加自己的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_select_photos, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        if (list.size() == position) {
            viewHolder.sivImage.setImageResource(R.drawable.shape_border_stroke);
            viewHolder.llSelecter.setVisibility(View.VISIBLE);
            viewHolder.ivDelete.setVisibility(View.GONE);
            viewHolder.tvNumber.setText(list.size() + "/6");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 进入相册 以下是例子：用不到的api可以不写
                    PictureSelector.create(activity)
                            .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                            .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                            .maxSelectNum(6-list.size())// 最大图片选择数量 int
                            .minSelectNum(1)// 最小选择数量 int
                            .imageSpanCount(4)// 每行显示个数 int
                            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                            .previewImage(true)// 是否可预览图片 true or false
                            .previewVideo(false)// 是否可预览视频 true or false
                            .enablePreviewAudio(false) // 是否可播放音频 true or false
                            .isCamera(true)// 是否显示拍照按钮 true or false
                            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                            .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                            .enableCrop(true)// 是否裁剪 true or false
                            .compress(true)// 是否压缩 true or false
//                        .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
//                        .isGif()// 是否显示gif图片 true or false
//                        .compressSavePath(getPath())//压缩图片保存地址
                            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                            .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                            .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                            .openClickSound(true)// 是否开启点击声音 true or false
//                            .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                        .cropCompressQuality()// 裁剪压缩质量 默认90 int
                            .minimumCompressSize(100)// 小于100kb的图片不压缩
                            .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                        .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                            .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                            .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                        .videoQuality()// 视频录制质量 0 or 1 int
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                }
            });
        } else {
            viewHolder.llSelecter.setVisibility(View.GONE);
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
            Glide.with(activity).load(list.get(position).getCompressPath()).into(viewHolder.sivImage);
            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        int i = list.size();
        if (i < 6) {
            return i + 1;
        } else {
            return 6;
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.siv_image)
        SquareImageView sivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.ll_selecter)
        LinearLayout llSelecter;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
