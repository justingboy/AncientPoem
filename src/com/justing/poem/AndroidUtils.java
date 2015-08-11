package com.justing.poem;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.j150713.storage.Preference;
import com.j150713.storage.PreferenceHelper;

public class AndroidUtils {

	public static JSONObject getDeviceInfo(Context paramContext)
			throws JSONException {
		PreferenceHelper.init(paramContext);
		JSONObject localJSONObject = new JSONObject();
		localJSONObject.put("dm", Build.MODEL);
		localJSONObject.put("dmf", Build.MANUFACTURER);
		localJSONObject.put("device_brand", Build.BRAND);
		localJSONObject.put("device_product", Build.PRODUCT);
		localJSONObject.put("cpu_abi", Build.CPU_ABI);
		localJSONObject.put("ov", Build.VERSION.RELEASE);
		TelephonyManager localTelephonyManager = (TelephonyManager) paramContext
				.getSystemService("phone");
		localJSONObject.put("network_operator",
				localTelephonyManager.getNetworkOperatorName());
		localJSONObject.put("nt",
				getNetworkTypeName(localTelephonyManager.getNetworkType()));
		localJSONObject.put("sim_serial",
				localTelephonyManager.getSimSerialNumber());
		String str1 = getImei(paramContext);
		String str2 = getImis(paramContext);
		localJSONObject.put("imei", str1);
		localJSONObject.put("imsi", str2);
		localJSONObject.put("ut", getUserType(str2));
		Preference.savePhoneImei(str1);
		WindowManager localWindowManager = (WindowManager) paramContext
				.getSystemService("window");
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
		int i = localDisplayMetrics.densityDpi;
		localJSONObject.put("ss", localDisplayMetrics.widthPixels + "*"
				+ localDisplayMetrics.heightPixels);
		localJSONObject.put("display_density", localDisplayMetrics.density);
		localJSONObject.put("display_dpi", i);
		localJSONObject.put("locale", getLocalLanguage(paramContext));
		Preference.saveScreenDpi(i);
		return localJSONObject;
	}

	public static String getNetworkTypeName(int paramInt) {
		switch (paramInt) {
		case 7:
		default:
			return "Unknown";
		case 1:
			return "GPRS";
		case 2:
			return "EDGE";
		case 4:
			return "CDMA";
		case 5:
			return "EVDO_0";
		case 6:
			return "EVDO_A";
		case 8:
			return "HSDPA";
		case 10:
			return "HSPA";
		case 9:
			return "HSUPA";
		}
	}

	public static int getUserType(String paramString) {
		if (paramString == null) {
			return 0;
		}
		int i = 0;
		if ((paramString.startsWith("46000"))
				|| (paramString.startsWith("46002"))
				|| (paramString.startsWith("46007"))) {
			i = 1;
		}
		for (;;) {
			// return i;
		}
	}

	public static String getLocalLanguage(Context paramContext) {
		return paramContext.getResources().getConfiguration().locale
				.getLanguage();
	}
	
	/**
	 * MEI 为TAC + FAC + SNR + SP 　　 1、前6位数(TAC)是"型号核准号码"，一般代表机型
	 * （开头两位数字一般是86,,35,52(比较少),其他四位随机）） 　　 2、接着的2位数(FAC)是"最后装配号"，一般代表产地。
	 * （现在的代表数字只有00,01,02,03,04（已经不代表产地了）） 　 　3、之后的6位数(SNR)是"串号"，一般代表生产顺序号。 (随机)
	 * 　　 4、最后1位数(SP)通常是"0"，为检验码，目前暂备用 （一般默认为0,1,2,3）
	 * 
	 * @param context
	 * @return
	 */
	public static String getImei(Context context) {
		String imei = null;
		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("dInfo", Context.MODE_PRIVATE);
		long lastTime = localSharedPreferences.getLong("brashtime", 0L);
		String localObject = "";
		// 表示每次调用这个方法的时候，在30分钟之内从本地获取相同的imei,超过30分钟，则重新生成一个imei
		if ((localSharedPreferences != null)
				&& (System.currentTimeMillis() - lastTime < (1000 * 60 * 30))) {
			Log.i("msg",
					"juyou-->imei="
							+ localSharedPreferences.getString("brashimei",
									"86345200622380"));

			imei = localSharedPreferences.getString("brashimei",
					"86345200622380");

			return imei;
		}
		TelephonyManager localTelephonyManager = (TelephonyManager) context
				.getSystemService("phone");
		if (localTelephonyManager != null) {
			localObject = localTelephonyManager.getDeviceId();

			if ((localObject != null) && (localObject.length() > 0)) {

				String TAC2[] = { "86", "35", "86", "35", "52", "86", "86",
						"86", "35", "52" };
				String FAC2[] = { "00", "01", "00", "02", "00", "03", "02",
						"04", "00", "02", };
				String SP1[] = { "0", "1", "2", "0", "0", "0", "0", "3", "3",
						"0" };
				String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
						"9" };
				int round = (int) (10.0D * Math.random());
				String TAC = TAC2[round] + str[getRoundInt()]
						+ str[getRoundInt()] + str[getRoundInt()]
						+ str[getRoundInt()];
				String FAC = FAC2[round];
				String SNR = str[getRoundInt()] + str[getRoundInt()]
						+ str[getRoundInt()] + str[getRoundInt()]
						+ str[getRoundInt()] + str[getRoundInt()];
				String SP = SP1[round];

				String IMEI = TAC + FAC + SNR + SP;

				localSharedPreferences.edit().putString("brashimei", IMEI)
						.putLong("brashtime", System.currentTimeMillis())
						.commit();
				Log.i("msg", "juyou--->imei=" + IMEI);
				return IMEI;
			}
		}
		return null;
	}

	
	public static String getImis(Context ctx) {
		String imsi = null;
		SharedPreferences localSharedPreferences = ctx.getSharedPreferences(
				"dInfo", Context.MODE_PRIVATE);
		long lastTime = localSharedPreferences.getLong("brashIMSItime", 0L);
		// 表示每次调用这个方法的时候，在30分钟之内从本地获取相同的imei,超过30分钟，则重新生成一个imei
		if ((localSharedPreferences != null)
				&& (System.currentTimeMillis() - lastTime < (1000 * 60 * 30))) {
			Log.i("msg",
					"doudou-->imei="
							+ localSharedPreferences.getString("brashimsi",
									"460030912121001"));
			imsi = localSharedPreferences.getString("brashimsi",
					"460030912121001");

			return imsi;
		}

		String round[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer sb = new StringBuffer();
		// imis 的组成=MCC+MNC+EF+M0M1M2M3+ABCD
		String mcc = "460";// 代表中国
		String mnc[] = { "00", "01", "02", "03" };// 代表着移动/联通/电信
		String ef = round[getRoundInt()] + round[getRoundInt()] + "";// 随机生成
		int a = getRoundInt();
		int b = getRoundInt();
		int c = getRoundInt();
		int d = getRoundInt();
		String m0m1m2m3 = round[a] + round[b] + round[c] + round[d] + ""; // 对称
		String abcd = round[getRoundInt()] + round[getRoundInt()]
				+ round[getRoundInt()] + round[getRoundInt()] + "";
		int mncIndex = (int) (Math.random() * 4);
		imsi = sb.append(mcc).append(mnc[mncIndex]).append(ef).append(m0m1m2m3)
				.append(abcd).toString();
		localSharedPreferences.edit().putString("brashimsi", imsi)
				.putLong("brashIMSItime", System.currentTimeMillis()).commit();
		Log.i("msg", "doudou--->imsi=" + imsi);

		return imsi;
	}

	public static int getRoundInt() {
		return (int) (Math.random() * 10);
	}
}
