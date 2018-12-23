package org.java.search.service;

import org.java.common.pojo.EasyBuyResult;
import org.java.common.pojo.SearchItem;


public interface SearchService {
	/**
	 * 查询所有的商品后将商品上传到solr中
	 * @return
	 */
	EasyBuyResult insertAllProductToSolr();
	/**
	 * solr分页查询
	 * @param query 查询条件
	 * @param pageNow 当前页码
	 * @param pagesize 每页显示的信息条数
	 * @return
	 * @throws Exception
	 */
	SearchItem getSearchResult(String query,Integer pageNow,Integer pagesize) throws Exception;
}
