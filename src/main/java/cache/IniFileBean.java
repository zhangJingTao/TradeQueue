package cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

import exception.IniConfigException;


/**
 * ini配置Bean.
 * 
 * @author harry.zu
 * 
 */
public final class IniFileBean {
	private static Logger logger = Logger.getLogger(IniFileBean.class);

	/**
	 * ini配置的单个section.
	 * 
	 */
	public final class IniSection {
		private String sectionName = null;
		private HashMap<String, String> iniPars = new HashMap<String, String>();

		/**
		 * 获得某个配置项的�?.
		 * 
		 * @param name
		 *            配置项的�?
		 * 
		 * @return 配置项的�?
		 */
		public String getValue(final String name) {
			return iniPars.get(name);
		}

		/**
		 * 设置某个配置项的�?.
		 * 
		 * @param name
		 *            配置项名�?
		 * @param value
		 *            配置项�??
		 */
		public void setValue(final String name, final String value) {
			iniPars.put(name, value);
		}

		/**
		 * 获得section的名�?.
		 * 
		 * @return section的名�?
		 */
		public String getSectionName() {
			return sectionName;
		}

		/**
		 * 设置section的名�?.
		 * 
		 * @param sectionName
		 *            section的名�?
		 */
		public void setSectionName(final String sectionName) {
			this.sectionName = sectionName;
		}
	}

	private HashMap<String, IniSection> sections = new HashMap<String, IniSection>();

	/**
	 * 构�?�方�?.
	 * 
	 * @param input
	 *            输入�?
	 */
	public IniFileBean(final InputStream input) {
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(
				input));

		try {
			boolean isFirstLine = true;
			boolean isSection = false;
			String sectionName = null;
			String line = null;
			IniSection secion = new IniSection();

			while ((line = reader.readLine()) != null) {
				String key = null;

				line = line.trim();

				if (!"".equals(line) && !line.trim().startsWith("#")) {
					String[] lineKeyAndVal = line.split("=");

					key = lineKeyAndVal[0].trim();

					// this is section
					if (key.startsWith("[")) {
						if (!key.endsWith("]")) {
							throw new IniConfigException(
									"Section name must end with ']', like [section-name].");
						} else {
							isSection = true;
							secion = new IniSection();

							sectionName = key.substring(1, key.length() - 1)
									.trim();

							if (sectionName == null || "".equals(sectionName)) {
								throw new IniConfigException(
										"Section can't be null string.");
							}

							secion.setSectionName(sectionName);
							sections.put(sectionName, secion);

							logger.debug("Section init success!Section name:"
									+ sectionName);
						}
					} else {// this is action
						String parValue = "";

						if (lineKeyAndVal.length == 2) {
							parValue = lineKeyAndVal[1].trim();
						}

						isSection = false;

						if (key.endsWith("]")) {
							throw new IniConfigException(
									"Section name must start with '[', like [section-name].");
						} else {
							secion.setValue(key, parValue);

							logger.debug("Section content init success!Par name:"
									+ key);
						}
					}

					if (isFirstLine && !isSection) {
						throw new IniConfigException(
								"Firstline of action config file must be section name, like [section-name].");
					}
					if (isFirstLine) {
						isFirstLine = false;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据section名称获得section的bean.
	 * 
	 * @param sectionName
	 *            section名称
	 * 
	 * @return section的bean
	 */
	public IniSection getSection(final String sectionName) {
		return sections.get(sectionName);
	}

	/**
	 * 获取�?有section的名�?.
	 * 
	 * @return section名称数组
	 */
	public String[] getSections() {
		String[] sectionNames = new String[sections.size()];
		sections.keySet().toArray(sectionNames);

		return sectionNames;
	}
}
