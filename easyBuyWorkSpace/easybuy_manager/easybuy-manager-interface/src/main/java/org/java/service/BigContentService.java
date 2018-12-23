package org.java.service;

import java.util.List;

import org.java.common.pojo.EasyBuyResult;
import org.java.pojo.EasybuyBigcontent;

public interface BigContentService {
	/**
	 * 上传大广告
	 * @param bigContent
	 * @return
	 */
	EasyBuyResult saveBigContent(EasybuyBigcontent bigContent);
	/**
	 * 查询所有的大广告
	 * @return
	 */
	List<EasybuyBigcontent> getAllBigContents();
}
