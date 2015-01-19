package util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	private Properties props = null;

	/**
	 * 构�?�函�?
	 * 
	 * @param fileName
	 *            配置文件名称
	 */
	public PropertiesUtil(String fileName) {
		String filePath = getPath(PropertiesUtil.class) + fileName;
		props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			// 关闭资源
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key值读取配置的�?
	 * 
	 * @param key
	 *            key�?
	 * @return key 键对应的�?
	 * @throws IOException
	 */
	public String readValue(String key){
		String result = props.getProperty(key);
		return result;
	}

	/**
	 * 读取properties的全部信�?
	 * 
	 * @throws FileNotFoundException
	 *             配置文件没有找到
	 * @throws IOException
	 *             关闭资源文件，或者加载配置文件错�?
	 * 
	 */
	public Map<String, String> readAllProperties()
			throws FileNotFoundException, IOException {
		// 保存�?有的键�??
		Map<String, String> map = new HashMap<String, String>();
		Enumeration en = props.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String Property = props.getProperty(key);
			map.put(key, Property);
		}
		return map;
	}

	private String getPath(Class name) {
		String strResult = null;
		if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
			strResult = name.getResource("/").toString().replace("file:/", "")
					.replace("%20", " ");
		} else {
			strResult = name.getResource("/").toString().replace("file:", "")
					.replace("%20", " ");
		}
		return strResult;
	}
	
	public static void main(String[] args) throws IOException {
		String str = new PropertiesUtil("/conf/download.properties").readValue("shopxx.url");
		System.out.println(str);
	}
}
