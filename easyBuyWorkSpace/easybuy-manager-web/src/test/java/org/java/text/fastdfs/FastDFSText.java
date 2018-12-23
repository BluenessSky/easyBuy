package org.java.text.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.java.common.utils.FastDFSClient;
import org.junit.Test;

public class FastDFSText {
	@Test
	public void textFastText() throws Exception{
		//1.导入fastdfs的jar包
		//2.加载配置文件，配置文件
		ClientGlobal.init("F:\\easyBuyWorkSpace\\easybuy-manager-web\\src\\main\\resources\\resource\\fdfs_client.conf");
		//3.创建trackerClient对象
		TrackerClient trackerClient=new TrackerClient();
		TrackerServer trackerServer=trackerClient.getConnection();
		//4.创建storageServer对象
		StorageServer storageServer=null;
		StorageClient storageClient=new StorageClient(trackerServer, storageServer);
		//6.使用storageServer实现上传图片
		String[] upload_file = storageClient.upload_file("D:/新建文件夹 (2)/lvguang.png","png",null);
		//7.返回一个数组
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	@Test
	public void textFastText1() throws Exception{
		FastDFSClient fastDFSClient=new FastDFSClient("F:\\easyBuyWorkSpace\\easybuy-manager-web\\src\\main\\resources\\resource\\fdfs_client.conf");
		String str=fastDFSClient.uploadFile("D:/新建文件夹 (2)/lvguang.png");
		System.out.println(str);
	}
}
