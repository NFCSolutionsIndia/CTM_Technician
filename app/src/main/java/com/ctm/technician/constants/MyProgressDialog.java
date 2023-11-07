package com.ctm.technician.constants;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ctm.technician.R;


public class MyProgressDialog extends ProgressDialog {

	View rootView;
	CommonFunctions com;

	public MyProgressDialog(Context context) {
		super(context, R.style.ThemeDialogCustom);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rootView = mInflater.inflate(R.layout.custom_progress_dialog, null);
		rootView.setBackgroundColor(Color.TRANSPARENT);
		com = new CommonFunctions(context);
	}

	@Override
	public void show() {
		super.show();
		setContentView(R.layout.custom_progress_dialog);
	}

	@Override
	public void setMessage(CharSequence message) {
		TextView txtM = (TextView) rootView.findViewById(R.id.txtMessage);
		txtM.setText(message);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			View view = this.findViewById(R.id.txtMessage);
			if (view != null) {
				// Shouldn't be null. Just to be paranoid enough.
				com.setFontMediumNormal(view, com.getString(R.color.black));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}