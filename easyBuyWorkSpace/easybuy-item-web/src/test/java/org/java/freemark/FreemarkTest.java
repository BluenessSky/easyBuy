package org.java.freemark;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkTest {
	@Test
	public void helloWold() throws Exception{
		//1创建模板文件
		//2创建configuation对象
		Configuration configuration=new Configuration(Configuration.getVersion());
		//3模板对象的路径
		configuration.setDirectoryForTemplateLoading(new File("F:/easyBuyWorkSpace/easybuy-item-web/src/main/webapp/WEB-INF/ftl/"));
		//4模板的字符集
		configuration.setDefaultEncoding("utf-8");
		//5使用configuation对象加载模板文件
		Template template = configuration.getTemplate("hello.ftl");
		//6创建数据集 向模板填充数据 1mapper2pojo
		Map<String, Object> map=new HashMap<>();
		map.put("name","world");
		//7创建write对象，指定输出文件路径和文件名
		Writer writer=new FileWriter(new File("F:/j123/hello12.html"));
		//8使用模板对象的process方法输出文件
		template.process(map,writer);
		//9关闭流
		writer.close();
	}
	@Test
	public void pojo() throws Exception{
		//1创建模板文件
		//2创建configuation对象
		Configuration configuration=new Configuration(Configuration.getVersion());
		//3模板对象的路径
		configuration.setDirectoryForTemplateLoading(new File("F:/easyBuyWorkSpace/easybuy-item-web/src/main/webapp/WEB-INF/ftl/"));
		//4模板的字符集
		configuration.setDefaultEncoding("utf-8");
		//5使用configuation对象加载模板文件
		Template template = configuration.getTemplate("shudent.ftl");
		//6创建数据集 向模板填充数据2pojo
		Map<String, Object> map=new HashMap<>();
		Student stu=new Student(1,"等等",24,"上海");
		map.put("stu",stu);
		//7创建write对象，指定输出文件路径和文件名
		Writer writer=new FileWriter(new File("F:/j123/pojo.html"));
		//8使用模板对象的process方法输出文件
		template.process(map,writer);
		//9关闭流
		writer.close();
	}
	@Test
	public void list() throws Exception{
		//1创建模板文件
		//2创建configuation对象
		Configuration configuration=new Configuration(Configuration.getVersion());
		//3模板对象的路径
		configuration.setDirectoryForTemplateLoading(new File("F:/easyBuyWorkSpace/easybuy-item-web/src/main/webapp/WEB-INF/ftl/"));
		//4模板的字符集
		configuration.setDefaultEncoding("utf-8");
		//5使用configuation对象加载模板文件
		Template template = configuration.getTemplate("list.ftl");
		//6创建数据集 向模板填充数据2pojo
		Map<String, Object> map=new HashMap<>();
		Student stu=new Student(1,"等等",24,"上海");
		Student stu1=new Student(2,"等等你",24,"上海");
		Student stu2=new Student(3,"等等我",24,"上海");
		Student stu3=new Student(4,"等等他",24,"上海");
		List<Student> hhhlist=new ArrayList<>();
		hhhlist.add(stu1);
		hhhlist.add(stu2);
		hhhlist.add(stu3);
		map.put("alist",hhhlist);
		map.put("adate",new Date());
		map.put("test",null);
		//7创建write对象，指定输出文件路径和文件名
		Writer writer=new FileWriter(new File("F:/j123/list.html"));
		//8使用模板对象的process方法输出文件
		template.process(map,writer);
		//9关闭流
		writer.close();
	}
}
