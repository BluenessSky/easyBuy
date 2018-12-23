package org.java.solrj.text;


import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrj {
	/**
	 * 添加
	 * @throws Exception
	 */
	/*@Test
	public void insertToSolr() throws Exception{
		//1创建solrServer对象
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8180/solr");
		//2创建document
		SolrInputDocument document=new SolrInputDocument();
		document.setField("id","test001");
		document.setField("item_title","霸王洗发水");
		document.setField("item_price","10");
		//3将文件对象添加到solr
		solrServer.add(document);
		//4提交
		solrServer.commit();
	}
	*//**
	 * 修改
	 * @throws Exception
	 *//*
	@Test
	public void update() throws Exception{
		//1创建solrServer对象
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8180/solr/collection2");
		//2创建document
		SolrInputDocument document=new SolrInputDocument();
		document.setField("id","test001");
		document.setField("item_title","扫地机器人");
		document.setField("item_price","3985");
		//3将文件对象添加到solr
		solrServer.add(document);
		//4提交
		solrServer.commit();
	}
	*//**
	 * 删除
	 * @throws Exception
	 *//*
	@Test
	public void deletebuyId() throws Exception{
		//1创建solrServer对象
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8180/solr/collection2");
		solrServer.deleteById("test001");
		solrServer.commit();
	}
	*//**
	 * 查询
	 * @throws Exception
	 *//*
	@Test
	public void select() throws Exception{
		//1创建solrServer对象
		SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8180/solr/collection2");
		//2创建solrQuery对象
		SolrQuery query=new SolrQuery();
		query.set("q","*:*");
		//3分页
		query.setStart(0);
		query.setRows(10);
		//4搜索的默认域
		query.set("df","item_keywords");
		//5设置高亮
		query.setHighlight(true);
		//6设置高亮显示域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<div>");
		query.setHighlightSimplePost("</div>");
		//7执行查询，得到response对象
		QueryResponse response = solrServer.query(query);
		//8执行查询获得结果集
		SolrDocumentList results = response.getResults();
		//9获取总的条数
		long numFound = results.getNumFound();
		System.out.println("获取的总的条数为"+numFound);
		//10遍历查询结果
		for (SolrDocument solrDocument : results) {
			System.out.println("id:"+solrDocument.get("id"));
		}
	}*/
	
	/**
	 * 集群版
	 * 添加
	 * @throws Exception
	 */
	/*@Test
	public void insertToSolrColud() throws Exception{
		//创建solr集群colud
		CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
		//设置Df
		cloudSolrServer.setDefaultCollection("collection2");
		//1创建solrServer对象
		//SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8080/solr/collection2");
		//2创建document
		SolrInputDocument document=new SolrInputDocument();
		document.setField("id","test001");
		document.setField("item_title","霸王洗发水");
		document.setField("item_price","10");
		//3将文件对象添加到solr
		cloudSolrServer.add(document);
		//4提交
		cloudSolrServer.commit();
	}*/
	/**
	 * solrColud修改
	 * @throws Exception
	 */
	/*@Test
	public void updateToSolrColud() throws Exception{
		//创建solr集群colud
		CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
		//设置Df
		cloudSolrServer.setDefaultCollection("collection2");
		//1创建solrServer对象
		//SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8080/solr/collection2");
		//2创建document
		SolrInputDocument document=new SolrInputDocument();
		document.setField("id","test001");
		document.setField("item_title","飘柔134");
		document.setField("item_price","10");
		//3将文件对象添加到solr
		cloudSolrServer.add(document);
		//4提交
		cloudSolrServer.commit();
	}*/
	/**
	 * solrColud
	 * 删除
	 * @throws Exception
	 */
	/*@Test
	public void deleteIdToSolrColud() throws Exception{
		//创建solr集群colud
		CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
		//设置Df
		cloudSolrServer.setDefaultCollection("collection2");
		//cloudSolrServer.deleteById("test001");
		//cloudSolrServer.deleteByQuery("id:test001");
		cloudSolrServer.deleteByQuery("item_title:飘柔");
		cloudSolrServer.commit();
	}*/
	/**
	 * 集群solrColud
	 * 查询
	 * @throws Exception
	 */
	/*@Test
	public void selectToSolrColud() throws Exception{
		//创建solr集群colud
				CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.130:2181,192.168.25.130:2182,192.168.25.130:2183");
				//设置Df
				cloudSolrServer.setDefaultCollection("collection2");
		//1创建solrServer对象
		//SolrServer solrServer=new HttpSolrServer("http://192.168.25.130:8080/solr/collection2");
		//2创建solrQuery对象
		SolrQuery query=new SolrQuery();
		//query.set("q","*:*");
		query.setQuery("联想");
		//3分页
		query.setStart(0);
		query.setRows(10);
		//4搜索的默认域
		query.set("df","item_keywords");
		//5设置高亮
		query.setHighlight(true);
		//6设置高亮显示域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<div>");
		query.setHighlightSimplePost("</div>");
		//7执行查询，得到response对象
		QueryResponse response = cloudSolrServer.query(query);
		//8执行查询获得结果集
		SolrDocumentList results = response.getResults();
		//9获取总的条数
		long numFound = results.getNumFound();
		System.out.println("获取的总的条数为"+numFound);
		//10遍历查询结果
		for (SolrDocument solrDocument : results) {
			System.out.println("id:"+solrDocument.get("id"));
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			//判断title是否有关键字
			String title="";
			if(list!=null&&list.size()>0){
				title=list.get(0);
			}else{
				title=(String) solrDocument.get("item_title");
			}
			System.out.println("item_title"+title);
			System.out.println("item_price"+solrDocument.get("item_price"));
		}*/
	/*}*/
}
