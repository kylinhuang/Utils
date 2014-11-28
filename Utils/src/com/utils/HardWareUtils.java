package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

/** 
* @ClassName: HardWareUtils 
* @Description:  硬件 信息
* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
* @date 2014-11-21 上午10:41:56  
*/
public class HardWareUtils {
	
	
	 
	
	/** 
	* getNumCores 
	* <ul>
	* <li> get cup number -  CPU的个数   </li>
	* </ul>
	* @Description:   Check the "/sys/devices/system/cpu/" number of cpu[0-9]
	* @return int    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 上午11:33:33
	* @return
	*/
	public static int getNumCores() {
		// Private Class to display only CPU devices in the directory listing
		class CpuFilter implements FileFilter {
			@Override
			public boolean accept(File pathname) {
				// Check if filename is "cpu", followed by a single digit number
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return true;
				}
				return false;
			}
		}

		try {
			// Get directory containing CPU info
			File dir = new File("/sys/devices/system/cpu/");
			// Filter to only list the devices we care about
			File[] files = dir.listFiles(new CpuFilter());
			// Return the number of cores (virtual CPU devices)
			return files.length;
		} catch (Exception e) {
			// Print exception
			e.printStackTrace();
			// Default to return 1 core
			return 1;
		}
	}
	

	

 

	/** 
	* getDeviceId 
	* <ul>
	* <li>  get DeviceId   IMEI 设备唯一标识 </li>
	* <li>   </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:49:51
	* @param context
	* @return
	*/
	public static  String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	private String getMac(Context context) {
		String result = "";
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		result = wifiInfo.getMacAddress();
		return result;
	}


	private String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-cpu型号 //2-cpu频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		Log.i("text", "cpuinfo:" + cpuInfo[0] + " " + cpuInfo[1]);
		return cpuInfo;
	}

	 
	/** 
	* getResolutionWidth
	* <ul>
	* <li> get screen resolution Width  - 获取屏幕分辨率  宽   </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:13:00
	* @param activity Activity
	* @return
	*/
	public int getResolutionWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}
	
	/** 
	* getResolutionHeight 
	* <ul>
	* <li>  get screen resolution Height   - 获取屏幕分辨率  高  </li>
	* <li>   </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return int    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:17:15
	* @param activity
	* @return
	*/
	public int getResolutionHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

 
	/** 
	* getCpuFrequence 
	* <ul>
	* <li> 获取CPU最大频率  </li>
	* </ul>
	* @Description: 获取CPU最大频率  readFile /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午1:52:16
	* @return
	*/
	public static String getCpuFrequence() {
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" };
			cmd = new ProcessBuilder(args);

			Process process = cmd.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String line = reader.readLine();
			// return StringUtils.parseLongSafe(line, 10, 0);
			float result = (Float.valueOf(line)) / 1000000;
			return result + "GHz";
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "";
	}

 
	/** 
	* getMinCpuFreq 
	* <ul>
	* <li> 获取CPU最小频率（单位KHZ）  </li>
	* </ul>
	* @Description: 获取CPU最小频率（单位KHZ） readFile /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:06:01
	* @return
	*/
	public static String getMinCpuFreq() {
		String result = "";
		float a = 0;
		ProcessBuilder cmd;
		try {
			String[] args = { "/system/bin/cat",
					"/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[24];
			while (in.read(re) != -1) {
				result = result + new String(re);
				a = (Float.valueOf(result)) / 1000000;
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			result = "N/A";
		}
		// return result.trim();
		return a + "GHz";
	}

 
	/** 
	* getCurCpuFreq 
	* <ul>
	* <li>  实时获取CPU当前频率（单位KHZ）： </li>
	* </ul>
	* @Description: readFile /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:07:38
	* @return
	*/
	public static String getCurCpuFreq() {
		String result = "N/A";
		float a = 0;
		try {
			FileReader fr = new FileReader(
					"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			result = text.trim();
			a = (Float.valueOf(result)) / 1000000;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return result;
		return a + "GHz";
	}

 
	/** 
	* getTotalMemory 
	* <ul>
	* <li> 内存(RAM) 随机高效存储器 random access memory   断电数据丢失 ：/proc/meminfo  </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:19:51
	* @return
	*/
	public String getTotalMemory(Context context) {
		String str1 = "/proc/meminfo";
		String str2 = "";
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			while ((str2 = localBufferedReader.readLine()) != null) {
				long mTotal;
				// beginIndex
				int begin = str2.indexOf(':');
				// endIndex
				int end = str2.indexOf('k');
				// 截取字符串信息
				str2 = str2.substring(begin + 1, end).trim();
				mTotal = Integer.parseInt(str2);
				return Formatter.formatFileSize(context, mTotal * 1024);
			}
		} catch (IOException e) {

		}
		return str2;
	}

	// 获得总内存
	public long getmem_TOLAL() {
		long mTotal;
		// /proc/meminfo读出的内核信息进行解释
		String path = "/proc/meminfo";
		String content = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path), 8);
			String line;
			if ((line = br.readLine()) != null) {
				content = line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// beginIndex
		int begin = content.indexOf(':');
		// endIndex
		int end = content.indexOf('k');
		// 截取字符串信息
		content = content.substring(begin + 1, end).trim();
		mTotal = Integer.parseInt(content);
		return mTotal;
	}


	/** 
	* getRomSize 
	* <ul>
	* <li>  Rom的实际大小  </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:30:45
	* @param context
	* @return
	*/
	public String getRomSize(Context context) {
		long[] romInfo = new long[2];
		// Total rom memory
		romInfo[0] = getTotalInternalMemorySize();

		// Available rom memory
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		romInfo[1] = blockSize * availableBlocks;
		return Formatter.formatFileSize(context, romInfo[0]);
		// return romInfo;
	}

	public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}


	
	/** 
	* getSDCardSize 
	* <ul>
	* <li>  get SDCard Size </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:44:12
	* @param context
	* @return
	*/
	public String getSDCardSize(Context context) {
		String size = null;
		try {
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				File sdcardDir = Environment.getExternalStorageDirectory();
				StatFs sf = new StatFs(sdcardDir.getPath());
				long bSize = sf.getBlockSize();
				long bCount = sf.getBlockCount();
				long availBlocks = sf.getAvailableBlocks();

				size = Formatter.formatFileSize(context, bSize * bCount);// 总大小
//				size = Formatter.formatFileSize(context, bSize * availBlocks);// 可用大小
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size;

	}
	
	/** 
	* getSDCardAvailableSize 
	* <ul>
	* <li>  get SDCard Available Size </li>
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:45:52
	* @param context
	* @return
	*/
	public String getSDCardAvailableSize(Context context) {
		String size = null;
		try {
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				File sdcardDir = Environment.getExternalStorageDirectory();
				StatFs sf = new StatFs(sdcardDir.getPath());
				long bSize = sf.getBlockSize();
				long bCount = sf.getBlockCount();
				long availBlocks = sf.getAvailableBlocks();

//				size = Formatter.formatFileSize(context, bSize * bCount);// 总大小
				size = Formatter.formatFileSize(context, bSize * availBlocks);// 可用大小
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;

	}

	/** 
	* getRomAvailableSize 
	* <ul>
	* <li> Rom available space  </li> 
	* </ul>
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
	* @date 2014-11-21 下午2:34:39
	* @param context
	* @return
	*/
	public String getRomAvailableSize(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		// return (availableBlocks * blockSize) * 1.0f / (1024 * 1024);
		return Formatter.formatFileSize(context,(availableBlocks * blockSize));
	}
}


