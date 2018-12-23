package org.java.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.java.common.pojo.SearchProduct;
import org.java.pojo.EasybuyProduct;
import org.java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class AddProductLintener implements MessageListener{
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FreeMarkerConfig configuration1;
	@Override
	public void onMessage(Message message) {
			//获取到message中的内容
			TextMessage textMessage = (TextMessage) message;
			try {
				//获取到消息(msg实际上就是商品的id)
				String msg = textMessage.getText();
				Thread.sleep(1000);
				System.out.println(msg);
				EasybuyProduct product = productService.getById(Integer.parseInt(msg));
				//将添加的商品生成一个静态的HTML页面
				//1创建一个模板文件
				//2创建configuration对象
				//Configuration configuration=new Configuration(Configuration.getVersion());
				//3设置模板文件路径
				//configuration.setDirectoryForTemplateLoading(new File("F:/easyBuyWorkSpace/easybuy-item-web/src/main/webapp/WEB-INF/ftl"));
				//4设置字符集
				//configuration.setDefaultEncoding("utf-8");
				//5configuration加载模板文件
				Configuration configuration = configuration1.getConfiguration();
				Template template =  configuration.getTemplate("productDeatil.ftl");
				//6创建数据集
				Map<String,Object> map=new HashMap<>();
				map.put("product",product);
				//7创建write对象，指定输出路径
				Writer writer=new FileWriter(new File("F:/j123/"+product.getId()+".html"));
				template.process(map, writer);
				//8关闭流
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
