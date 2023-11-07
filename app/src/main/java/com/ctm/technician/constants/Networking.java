package com.ctm.technician.constants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networking {


	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int getConnectivityStatus(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;

			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	public static String getConnectivityStatusString(Context context) {

		int conn = Networking.getConnectivityStatus(context);
		String status = null;
		if (conn == Networking.TYPE_WIFI) {
			status = "Wifi enabled";
		} else if (conn == Networking.TYPE_MOBILE) {
			status = "Mobile data enabled";
		} else if (conn == Networking.TYPE_NOT_CONNECTED) {
			status = "Not connected to Internet";
		}
		return status;
	}


}

