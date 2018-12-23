package org.java.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.java.common.pojo.SearchProduct;
import org.java.search.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AddProductLintener implements MessageListener{
	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public void onMessage(Message message) {
			//获取到message中的内容
			TextMessage textMessage = (TextMessage) message;
			try {
				//获取到消息(msg实际上就是商品的id)
				String msg = textMessage.getText();
				Thread.sleep(1000);
				//根据id查询
				SearchProduct product = searchMapper.selectProductbyId(Integer.parseInt(msg));
				//将对象同步到索引库
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", product.getId());
				document.setField("item_title",product.getName());
				document.setField("item_price", (product.getPrice()).longValue());
				document.setField("item_image", product.getFilename());
				document.setField("item_description", product.getDescription());
				document.setField("item_category_one", product.getCategoryNameOne());
				document.setField("item_category_two", product.getCategoryNameTwo());
				document.setField("item_category_three", product.getCategoryNameThree());
				//3.将文件对象添加到solr中
				solrServer.add(document);
				solrServer.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
