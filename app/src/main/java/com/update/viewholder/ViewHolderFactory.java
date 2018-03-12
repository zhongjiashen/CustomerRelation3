package com.update.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.crcxj.activity.R;
import com.update.viewbar.SquareImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:    申中佳
 * Version    V1.0
 * Date:      2018/2/26 0026 下午 5:41
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2018/2/26 0026         申中佳               V1.0
 */
public class ViewHolderFactory {




    /**
     * 安装登记列表
     *
     * @param context
     * @return
     */
    public static InstallRegistrationHolder getInstallRegistrationHolder(Context context) {
        return new InstallRegistrationHolder(LayoutInflater.from(context).inflate(R.layout.item_install_registration, null, false));
    }

    public static PerformInstallationHolder getPerformInstallationHolder(Context context) {
        return new PerformInstallationHolder(LayoutInflater.from(context).inflate(R.layout.item_perform_installation, null, false));
    }

    /**
     * 单项选择
     *
     * @param context
     * @return
     */
    public static StateAuditChoiceHolder getStateAuditChoiceHolder(Context context) {
        return new StateAuditChoiceHolder(LayoutInflater.from(context).inflate(R.layout.item_single_choice, null, false));
    }

    /**
     * 商品选择
     *
     * @param context
     * @return
     */
    public static ChooseGoodsHolder getChooseGoodsHolder(Context context) {
        return new ChooseGoodsHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_goods, null, false));
    }

    public static SerialNumberHolder getSerialNumberHolder(Context context) {
        return new SerialNumberHolder(LayoutInflater.from(context).inflate(R.layout.item_serial_number, null, false));
    }

    /**
     * 获取商品选择结果（添加安装登记）
     *
     * @param context
     * @return
     */
    public static ChooseGoodsResultHolder getChooseGoodsResultHolder(Context context) {
        return new ChooseGoodsResultHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_goods_result, null, false));
    }

    /**
     * 获取商品选择结果（添加安装登记）
     *
     * @param context
     * @return
     */
    public static ChooseFileResultHolder getChooseFileResultHolder(Context context) {
        return new ChooseFileResultHolder(LayoutInflater.from(context).inflate(R.layout.item_select_photos, null, false));
    }

    public static class InstallRegistrationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_receipt_number)
        TextView tvReceiptNumber;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;
        @BindView(R.id.tv_audit_status)
        TextView tvAuditStatus;
        @BindView(R.id.tv_maintenance_status)
        TextView tvMaintenanceStatus;

        InstallRegistrationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class PerformInstallationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;
        @BindView(R.id.tv_technician)
        TextView tvTechnician;
        @BindView(R.id.tv_commodity_information)
        TextView tvCommodityInformation;
        @BindView(R.id.tv_maintenance_status)
        TextView tvMaintenanceStatus;
        @BindView(R.id.tv_audit_status)
        TextView tvAuditStatus;

        PerformInstallationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class StateAuditChoiceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        public TextView tvText;

        StateAuditChoiceHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ChooseGoodsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_good_name)
        public TextView tvGoodName;
        @BindView(R.id.tv_coding)
        public TextView tvCoding;
        @BindView(R.id.tv_specifications)
        public TextView tvSpecifications;
        @BindView(R.id.tv_model)
        public TextView tvModel;
        @BindView(R.id.tv_unit)
        public TextView tvUnit;
        @BindView(R.id.cb_view)
        public CheckBox cbView;
        @BindView(R.id.sl_view)
        public SLView2 slView;
        @BindView(R.id.tv_serial_number)
        public TextView tvSerialNumber;
        @BindView(R.id.ll_number)
        public LinearLayout llNumber;
        @BindView(R.id.v_line)
        public View vLine;

        ChooseGoodsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class SerialNumberHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_serial_number)
        public TextView tvSerialNumber;
        @BindView(R.id.iv_editor)
        public ImageView ivEditor;
        @BindView(R.id.iv_delete)
        public ImageView ivDelete;

        SerialNumberHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ChooseGoodsResultHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_goods_information)
        public TextView tvGoodsInformation;
        @BindView(R.id.tv_registration_number)
        public TextView tvRegistrationNumber;

        ChooseGoodsResultHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ChooseFileResultHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.siv_image)
        public SquareImageView sivImage;
        @BindView(R.id.iv_delete)
        public ImageView ivDelete;
        ChooseFileResultHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
