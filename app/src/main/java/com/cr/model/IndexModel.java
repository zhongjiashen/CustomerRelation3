package com.cr.model;

import android.content.Intent;

public class IndexModel {
	private int logoId;
	private String logoName;
	private Intent mIntent;
	private String khzlname;
	public int getLogoId() {
		return logoId;
	}
	public void setLogoId(int logoId) {
		this.logoId = logoId;
	}
	public String getLogoName() {
		return logoName;
	}
	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public Intent getIntent() {
		return mIntent;
	}

	public void setIntent(Intent intent) {
		mIntent = intent;
	}

	public String getKhzlname() {
		return khzlname;
	}

	public void setKhzlname(String khzlname) {
		this.khzlname = khzlname;
	}
}
