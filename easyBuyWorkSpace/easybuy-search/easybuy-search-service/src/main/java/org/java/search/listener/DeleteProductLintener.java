package org.java.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.java.common.pojo.SearchProduct;
import org.java.search.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteProductLintener implements MessageListener{
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
			solrServer.deleteById(msg);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
