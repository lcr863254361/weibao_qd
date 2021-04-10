package com.orient.modeldata.dataanalyze.offline.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.orient.utils.CommonTools;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessColumnEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.sqlengine.api.ISqlEngine;

public class PlotUtil {
	private ISqlEngine sqlEngine;

	private IBusinessModelService modelService;

	private IBusinessModel model;

	private List<Map> dataList;

	private String directory;

	private Map<String, Channel> channels = new HashMap<String, Channel>();

	private static final String FOLDER = "数据文件夹"; // 文件夹名称

	private static final String TABLE = "数据表"; // 表格名称

	public PlotUtil(ISqlEngine orientSqlEngine, IBusinessModelService businessModelService, IBusinessModel model, List<Map> dataList, String directory) {
		this.sqlEngine = orientSqlEngine;
		this.modelService = businessModelService;
		this.model = model;
		this.dataList = dataList;
		this.directory = directory;
	}

	public void init() throws IOException {
		String dataFolder = directory /* + File.separator + FOLDER */
				+ File.separator + TABLE;

		new File(dataFolder).mkdirs();

		for (IBusinessColumn column : model.getAllBcCols()) {
			BusinessColumnEnum type = (BusinessColumnEnum) column.getColType();
			String columnName = column.getS_column_name();
			String filePath = dataFolder + File.separator + columnName + ".dat";
			Object stream = null;

			switch (type) {
			case C_Arith:
			case C_Integer:
			case C_BigInteger:
			case C_Decimal:
			case C_Float:
			case C_Double:
			case C_Dynamic:
				stream = new DataOutputStream(new BufferedOutputStream(
						new FileOutputStream(new File(filePath))));
				break;

			case C_Relation:
			case C_Text:
			case C_Simple:
			case C_Date:
			case C_DateTime:
			case C_Boolean:
			case C_SingleEnum:
			case C_MultiEnum:
			case C_SingleTableEnum:
			case C_SubTable:
			case C_MultiTableEnum:
				stream = new BufferedWriter(new FileWriter(filePath));
				break;
			}

			Channel channel = new Channel(type, stream);
			channels.put(columnName, channel);
		}

	}

	@SuppressWarnings("rawtypes")
	public void destory() throws IOException {
		Iterator iter = channels.keySet().iterator();
		while (iter.hasNext()) {
			Channel channel = channels.get(iter.next());
			Object stream = channel.getStream();
			if (stream == null) {
				continue;
			}
			if (stream instanceof DataOutputStream) {
				DataOutputStream out = (DataOutputStream) stream;
				out.flush();
				out.close();
			} else {
				BufferedWriter writer = (BufferedWriter) stream;
				writer.flush();
				writer.close();
			}
		}

		// 销毁完毕后，启动OrientPlot程序
	}

	@SuppressWarnings("unchecked")
	public void writeData() throws Exception {
		String split = new String(new char[] { '\0' });
		modelService.dataChangeModel(sqlEngine, model.getAllBcCols(), dataList);
		for (Map data : dataList) {
			Iterator iter = channels.keySet().iterator();
			while (iter.hasNext()) {
				String key = CommonTools.Obj2String(iter.next());
				String value = CommonTools.Obj2String(data.get(key));
				Channel channel = channels.get(key);
				BusinessColumnEnum type = channel.getType();
				Object stream = channel.getStream();
				if (stream == null) {
					continue;
				}
				if (stream instanceof DataOutputStream) {
					DataOutputStream out = (DataOutputStream) stream;
					switch (type) {
					case C_Arith:
						out.writeDouble("".equals(value)? Float.NaN : Float.valueOf(value));
						break;
					case C_Integer:
						out.writeInt("".equals(value)? 0 : Integer.valueOf(value));
						break;
					case C_BigInteger:
						out.writeLong("".equals(value)? 0l : Long.valueOf(value));
						break;
					case C_Float:
						out.writeFloat("".equals(value)? Float.NaN : Float.valueOf(value));
						break;
					case C_Double:
						out.writeDouble("".equals(value)? Double.NaN : Double.valueOf(value));
						break;
					case C_Dynamic:
						out.writeDouble("".equals(value)? Double.NaN : Double.valueOf(value));
						break;
					case C_Decimal:
						out.writeDouble("".equals(value)? Double.NaN : Double.valueOf(value));
						break;
					}
				}
				else {
					BufferedWriter writer = (BufferedWriter) stream;
					writer.write(value + split);
				}
			}
		}
	}

	public void writeXml(boolean isAppend) throws IOException {
		Document doc = DocumentHelper.createDocument();
		Element rootEL = doc.addElement("edmpost");
		rootEL.addAttribute("version", "1.0");
		rootEL.addAttribute("name", model.getDisplay_name());

		Element scripting = rootEL.addElement("scripting");
		scripting.setText("muParser");

		Element windownumEL = rootEL.addElement("windownum");
		windownumEL.setText("1");

		// Element folderEL = rootEL.addElement("folder");
		// folderEL.addAttribute("name", FOLDER);

		// Element tableEL = folderEL.addElement("table");
		// tableEL.addAttribute("name", TABLE);

		Element tableEL = rootEL.addElement("table");
		tableEL.addAttribute("name", TABLE);

		Element dataEL = tableEL.addElement("data");
		dataEL.addAttribute("rowcount", String.valueOf(dataList.size()));
		dataEL.addAttribute("columncount", String.valueOf(model.getAllBcCols().size()));

		for (IBusinessColumn businessColumn : model.getAllBcCols()) {
			Element column = dataEL.addElement("column");
			column.addAttribute("name", businessColumn.getDisplay_name());
			column.addAttribute("file", businessColumn.getS_column_name()
					+ ".dat");

			Channel channel = channels.get(businessColumn.getS_column_name());
			BusinessColumnEnum type = channel.getType();
			Object stream = channel.getStream();

			if (stream instanceof DataOutputStream) {
				switch (type) {
				case C_Arith:
					column.addAttribute("type", "double");
					break;
				case C_Integer:
					column.addAttribute("type", "int");
					break;
				case C_BigInteger:
					column.addAttribute("type", "long");
					break;
				case C_Float:
					column.addAttribute("type", "float");
					break;
				case C_Double:
					column.addAttribute("type", "double");
					break;
				case C_Dynamic:
					column.addAttribute("type", "double");
					break;
				case C_Decimal:
					column.addAttribute("type", "double");
					break;
				}

				// column.addAttribute("split", "Build-In");
			} else {
				column.addAttribute("type", "NUL");
				// column.addAttribute("split", "");
			}

			column.addAttribute("readonly", "0");
		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");

		XMLWriter xmlWriter = new XMLWriter(new BufferedOutputStream(
				new FileOutputStream(new File(directory + File.separator
						+ model.getDisplay_name() + ".xml"))), format);
		xmlWriter.write(doc);
		xmlWriter.close();
	}

	class Channel {
		BusinessColumnEnum type;
		Object stream;

		public Channel(BusinessColumnEnum type, Object stream) {
			this.type = type;
			this.stream = stream;
		}

		public BusinessColumnEnum getType() {
			return type;
		}

		public void setType(BusinessColumnEnum type) {
			this.type = type;
		}

		public Object getStream() {
			return stream;
		}

		public void setStream(Object stream) {
			this.stream = stream;
		}

	}

}
