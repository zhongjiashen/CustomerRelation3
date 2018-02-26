package com.update.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.crcxj.activity.R;

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

    public static InstallRegistrationHolder getInstallRegistrationHolder(Context context) {
        return new InstallRegistrationHolder(LayoutInflater.from(context).inflate(R.layout.item_install_registration, null, false));
    }

    public static class InstallRegistrationHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_receipt_number)
        TextView tvReceiptNumber;
        @BindView(R.id.tv_audit_status)
        TextView tvAuditStatus;
        @BindView(R.id.tv_company_name)
        TextView tvCompanyName;

        InstallRegistrationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
