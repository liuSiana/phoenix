package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.Locator;

public class ReadXMLUtil {
	public static Map<String, Map<String, Locator>> pageMap = null;
	//只加载一遍
	static{
		readXml();
	}
	/**
	 * 读取xml文件，获取页面所有的元素信息
	 * 
	 * @return 返回一个对象列表
	 */
	
	private static Map<String, Map<String, Locator>> readXml() {
		Locator locator = null;
		//为了能够精确到某个page的某个元素，需要一个key值来进行标记，所以采用Map来进行保存所有的页面数据
		
		try {
			// 1：创建一个saxReader的对象
			SAXReader saxReader = new SAXReader();
			// 2：得到xml文件对象
			Document document = saxReader.read(ReadXMLUtil.class.getResourceAsStream("/pages.xml"));
			// 3:得到xml文件的根标签
			Element element = document.getRootElement();
			// 4：得到所有想要的标签--》此处想要的是page标签，如果想要得到别的标签，传入对应的标签名即可
			List<Element> pageElements = element.elements("page");
			//初始化pageMap
			pageMap =new HashMap<String, Map<String,Locator>>();
			// 5：循环遍历所有的标签，得到每个page标签
			for (Element pageElement : pageElements) {
				// 6:循环得到page标签下的所有locator标签
				List<Element> locators = pageElement.elements("locator");
				String pageName=pageElement.attributeValue("name");
				//为了能够精确的找到某个locator中的元素，需要一个key来进行标记，所以采用Map来进行保存所有的locator数据
				Map<String, Locator> locatorMap=new HashMap<String, Locator>();
				// 7：循环遍历locator标签，得到每个locator标签下的属性值
				for (Element locatorElement : locators) {
					// 8：传入属性名称，得到每个的属性值
					String by = locatorElement.attributeValue("by");
					String value = locatorElement.attributeValue("value");
					String desc = locatorElement.attributeValue("desc");
					// 9：通过带参构造方法，将所有的属性值封装在locator对象中
					locator = new Locator(by, value, desc);
					//10：将所有的locator对象添加到list中
					locatorMap.put(desc, locator);
				}
				pageMap.put(pageName, locatorMap);
			}

			return pageMap;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Map<String, Map<String, Locator>> pageMap = readXml();
		Locator locator=pageMap.get("注册页面").get("注册按钮");
		System.out.println(locator);
		
		//反射
		//1：可以通过对象调用字节码，获取类的信息
		ExcelUtil excelUtil=new ExcelUtil();
		String className=excelUtil.getClass().getName();
		System.out.println(className);
		//也可以通过类直接调用
		String clazz=ExcelUtil.class.getName();
		System.out.println(clazz);
	}
}
