package com.update.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cr.activity.SLView2;
import com.crcxj.activity.R;
import com.update.utils.CustomTextWatcher;
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
    public static InstallRegistrationHolder getInstallRegistrationHolder(Context context, ViewGroup parent) {
        return new InstallRegistrationHolder(LayoutInflater.from(context).inflate(R.layout.item_install_registration, parent, false));
    }

    public static PerformInstallationHolder getPerformInstallationHolder(Context context, ViewGroup parent) {
        return new PerformInstallationHolder(LayoutInflater.from(context).inflate(R.layout.item_perform_installation, parent, false));
    }

    /**
     * 单项选择
     *
     * @param context
     * @return
     */
    public static StateAuditChoiceHolder getStateAuditChoiceHolder(Context context, ViewGroup parent) {
        return new StateAuditChoiceHolder(LayoutInflater.from(context).inflate(R.layout.item_single_choice, parent, false));
    }

    /**
     * 商品选择
     *
     * @param context
     * @return
     */
    public static ChooseGoodsHolder getChooseGoodsHolder(Context context, ViewGroup parent) {
        return new ChooseGoodsHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_goods, parent, false));
    }

    public static SerialNumberHolder getSerialNumberHolder(Context context, ViewGroup parent) {
        return new SerialNumberHolder(LayoutInflater.from(context).inflate(R.layout.item_serial_number, parent, false));
    }

    /**
     * 获取商品选择结果（添加安装登记）
     *
     * @param context
     * @return
     */
    public static ChooseGoodsResultHolder getChooseGoodsResultHolder(Context context, ViewGroup parent) {
        return new ChooseGoodsResultHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_goods_result, parent, false));
    }

    /**
     * 获取商品选择结果（添加安装登记）
     *
     * @param context
     * @return
     */
    public static ChooseFileResultHolder getChooseFileResultHolder(Context context, ViewGroup parent) {
        return new ChooseFileResultHolder(LayoutInflater.from(context).inflate(R.layout.item_select_photos, parent, false));
    }

    /**
     * 项目
     *
     * @param context
     * @return
     */
    public static ProjectHolder getProjectHolder(Context context, ViewGroup parent) {
        return new ProjectHolder(LayoutInflater.from(context).inflate(R.layout.item_project, parent, false));
    }

    /**
     * 项目
     *
     * @param context
     * @return
     */
    public static ChoiceProjectHolder getChoiceProjectHolder(Context context, ViewGroup parent) {
        return new ChoiceProjectHolder(LayoutInflater.from(context).inflate(R.layout.item_choice_project, parent, false));
    }


    /**
     * 项目
     *
     * @param context
     * @return
     */
    public static ChoiceLogisticsCompanyHolder getChoiceLogisticsCompanyHolder(Context context, ViewGroup parent) {
        return new ChoiceLogisticsCompanyHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_logistics_company, parent, false));
    }

    /**
     * 项目
     *
     * @param context
     * @return
     */
    public static JxcCkglKcpdXzspHolder getJxcCkglKcpdXzspHolder(Context context, ViewGroup parent) {
        return new JxcCkglKcpdXzspHolder(LayoutInflater.from(context).inflate(R.layout.item_jxc_ckgl_kcdb_xzsp, parent, false));
    }

    /**
     * 利润表
     *
     * @param context
     * @return
     */
    public static LrbHolder getLrbHolder(Context context, ViewGroup parent) {
        return new LrbHolder(LayoutInflater.from(context).inflate(R.layout.item_lrb, parent, false));
    }

    /**
     * 财务报表明细
     *
     * @param context
     * @return
     */
    public static CwbbmxHolder getCwbbmxHolder(Context context, ViewGroup parent) {
        return new CwbbmxHolder(LayoutInflater.from(context).inflate(R.layout.item_cwbbmx, parent, false));
    }

    public static class InstallRegistrationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_data)
        public TextView tvData;
        @BindView(R.id.tv_receipt_number)
        public TextView tvReceiptNumber;
        @BindView(R.id.tv_company_name)
        public TextView tvCompanyName;
        @BindView(R.id.tv_audit_status)
        public TextView tvAuditStatus;
        @BindView(R.id.tv_maintenance_status)
        public TextView tvMaintenanceStatus;

        InstallRegistrationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class PerformInstallationHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_company_name)
        public TextView tvCompanyName;
        @BindView(R.id.tv_technician)
        public TextView tvTechnician;
        @BindView(R.id.tv_commodity_information)
        public TextView tvCommodityInformation;
        @BindView(R.id.tv_maintenance_status)
        public TextView tvMaintenanceStatus;
        @BindView(R.id.tv_audit_status)
        public TextView tvAuditStatus;

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
        @BindView(R.id.tv_warranty_status)
        public TextView tvWarrantyStatus;
        @BindView(R.id.ll_warranty_status)
        public LinearLayout llWarrantyStatus;
        @BindView(R.id.tv_fault_type)
        public TextView tvFaultType;
        @BindView(R.id.ll_fault_type)
        public LinearLayout llFaultType;
        @BindView(R.id.et_fault_description)
        public EditText etFaultDescription;
        @BindView(R.id.ll_maintenance)
        public LinearLayout llMaintenance;
        @BindView(R.id.et_bz)
        public EditText etBz;

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

    public static class ProjectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_data)
        public TextView tvData;
        @BindView(R.id.tv_receipt_number)
        public TextView tvReceiptNumber;
        @BindView(R.id.tv_audit_status)
        public TextView tvAuditStatus;
        @BindView(R.id.tv_company_name)
        public TextView tvCompanyName;
        @BindView(R.id.tv_project_name)
        public TextView tvProjectName;
        @BindView(R.id.tv_phase)
        public TextView tvPhase;
        @BindView(R.id.tv_money)
        public TextView tvMoney;

        ProjectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ChoiceProjectHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_receipt_number)
        public TextView tvReceiptNumber;
        @BindView(R.id.tv_company_name)
        public TextView tvCompanyName;
        @BindView(R.id.tv_project_name)
        public TextView tvProjectName;
        @BindView(R.id.tv_phase)
        public TextView tvPhase;
        @BindView(R.id.tv_money)
        public TextView tvMoney;

        ChoiceProjectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ChoiceLogisticsCompanyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_code)
        public TextView tvCode;
        @BindView(R.id.tv_name)
        public TextView tvName;
        @BindView(R.id.tv_type)
        public TextView tvType;

        ChoiceLogisticsCompanyHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class JxcCkglKcpdXzspHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.et_mc)
        public TextView etMc;
        @BindView(R.id.et_bh)
        public TextView etBh;
        @BindView(R.id.et_gg)
        public TextView etGg;
        @BindView(R.id.cb_view)
        public CheckBox cbView;
        @BindView(R.id.et_xh)
        public TextView etXh;
        @BindView(R.id.et_kc)
        public TextView etKc;
        @BindView(R.id.tv_serial_number)
        public TextView tvSerialNumber;
        @BindView(R.id.sl_view)
        public SLView2 slView;
        @BindView(R.id.ll_number)
        public LinearLayout llNumber;
        @BindView(R.id.tv_cpph)
        public TextView tvCpph;
        @BindView(R.id.ll_cpph)
        public LinearLayout llCpph;
        @BindView(R.id.tv_scrq)
        public TextView tvScrq;
        @BindView(R.id.ll_scrq)
        public LinearLayout llScrq;
        @BindView(R.id.tv_yxqz)
        public TextView tvYxqz;
        @BindView(R.id.ll_yxqz)
        public LinearLayout llYxqz;
        @BindView(R.id.ll_v)
        public LinearLayout llV;

        JxcCkglKcpdXzspHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public static class LrbHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mz)
        public TextView tvMz;
        @BindView(R.id.tv_je)
        public TextView tvJe;
        @BindView(R.id.tv_qcye)
        public TextView tvQcye;
        @BindView(R.id.tv_ljje)
        public TextView tvLjje;

        LrbHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class CwbbmxHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_data)
        public TextView tvData;
        @BindView(R.id.tv_djmc)
        public TextView tvDjmc;
        @BindView(R.id.tv_djlx)
        public TextView tvDjlx;
        @BindView(R.id.tv_djbh)
        public TextView tvDjbh;
        @BindView(R.id.tv_wldw)
        public TextView tvWldw;
        @BindView(R.id.tv_je)
        public TextView tvJe;
        @BindView(R.id.tv_ftje)
        public TextView tvFtje;

        CwbbmxHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
