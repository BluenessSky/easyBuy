package org.java.search.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.pojo.SearchItem;
import org.java.common.pojo.SearchProduct;
import org.java.search.mapper.SearchMapper;
import org.java.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchMapper searchMapper;
	@Autowired
	private SolrServer solrServer;
	@Value("${SOLR_PAGE_SIZE}")
	private Integer SOLR_PAGE_SIZE;
	@Override
	public EasyBuyResult insertAllProductToSolr() {
		try {
			// 1.查询所有的商品
			List<SearchProduct> list = searchMapper.selectProduct();
			//2.将商品添加到solr中
			for (SearchProduct product : list) {
				SolrInputDocument document=new SolrInputDocument();
				document.setField("id",product.getId());
				document.setField("item_title",product.getName());
				document.setField("item_price",(product.getPrice()).longValue());
				document.setField("item_image",product.getFilename());
				document.setField("item_description",product.getDescription());
				document.setField("item_category_one",product.getCategoryNameOne());
				document.setField("item_category_two",product.getCategoryNameTwo());
				document.setField("item_category_three",product.getCategoryNameThree());
				solrServer.add(document);
			}
			solrServer.commit();
			return EasyBuyResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return EasyBuyResult.build(500,"商品全部添加到solr失败");
		}
	}
	@Override
	public SearchItem getSearchResult(String queryString, Integer pageNow, Integer pagesize) throws Exception {
		SearchItem searchItem=new SearchItem();
		//创建solrQuery对象
		SolrQuery query=new SolrQuery();
		//添加查询条件
		query.setQuery(queryString);
		//设置排序
		query.setSort("id",ORDER.desc);
		if(pagesize==null){
			pagesize=SOLR_PAGE_SIZE;
		}
		if(pageNow==null||pageNow<1){
			pageNow=1;
		}
		query.setStart((pageNow-1)*pagesize);
		query.setRows(pagesize);
		query.set("df", "item_keywords");
		//设置高亮
		query.setHighlight(true);
		//设置高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<span style='color:red;'>");
		query.setHighlightSimplePost("</span>");
		//执行查询，得到response对象
		QueryResponse response = solrServer.query(query);
		//执行查询
		SolrDocumentList results=response.getResults();
		//获取到信息的总条数
		long numFound=results.getNumFound();
		searchItem.setCount(numFound);
		//总页数
		long pageCount = numFound / pagesize;
		if(numFound%pagesize!=0){
			pageCount++;
		}
		searchItem.setPageCount((int)pageCount);
		List<SearchProduct> list=new ArrayList<>();
		//遍历集合，将集合中的对象放到集合
		for(SolrDocument solrDocument:results){
			SearchProduct item=new SearchProduct();
			//获得高亮集合
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list2 = highlighting.get(solrDocument.get("id")).get("item_title");
			String title="";
			if(list2!=null&&list2.size()>0){
				title=list2.get(0);
			}else {
				title=(String) solrDocument.get("item_title");
			}
			item.setName(title);
			item.setId(Integer.parseInt((String)solrDocument.get("id")));
			item.setDescription((String)solrDocument.get("item_description"));
			item.setPrice(Float.valueOf(solrDocument.get("item_price").toString()));
			item.setFilename((String)solrDocument.get("item_image"));
			item.setCategoryNameOne((String)solrDocument.get("item_category_one"));
			item.setCategoryNameTwo((String)solrDocument.get("item_category_two"));
			item.setCategoryNameThree((String)solrDocument.get("item_category_three"));
			list.add(item);
		}
		searchItem.setProductList(list);
		return searchItem;
	}
	
}
