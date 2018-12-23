package org.java.contorller;

import java.util.HashMap;
import java.util.Map;

import org.java.common.utils.FastDFSClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController {
	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map<String,Object> upload(MultipartFile uploadFile){
		Map<String,Object> map=new HashMap<>();
		try {
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:resource/fdfs_client.conf");
			//获取原始的文件名
			String oldName=uploadFile.getOriginalFilename();
			//获取原始文件的扩展名
			String extName=oldName.substring(oldName.lastIndexOf(".")+1);
			//上传fastDFS服务器
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			//真实服务器地址http://192.168.25.133/group1/M00/00/02/wKgZhVu7UqCAYRoxAAhK4WtLHjI743.png
			path="http://192.168.25.133/"+path;
			map.put("error",0);
			map.put("url",path);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error",1);
			map.put("message","图片上传失败");
			return map;
		}
	}
}
