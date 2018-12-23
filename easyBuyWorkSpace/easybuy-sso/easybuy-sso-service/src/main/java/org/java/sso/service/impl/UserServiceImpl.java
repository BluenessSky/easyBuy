package org.java.sso.service.impl;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.java.common.pojo.EasyBuyResult;
import org.java.common.utils.CookieUtils;
import org.java.common.utils.JsonUtils;
import org.java.jedis.JedisClient;
import org.java.jedis.JedisClientCluster;
import org.java.mapper.EasybuyUserMapper;
import org.java.pojo.EasybuyUser;
import org.java.pojo.EasybuyUserExample;
import org.java.pojo.EasybuyUserExample.Criteria;
import org.java.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private EasybuyUserMapper easybuyUserMapper;
	@Value("${MD5_SALT}")
	private String MD5_SALT;
	
	@Autowired
	private JedisClientCluster jedisClient;
	
	@Value("${SESSION_REDIS_EXPIRE}")
	private Integer SESSION_REDIS_EXPIRE;
	@Value("${EASYBUY_COOKIE}")
	private String EASYBUY_COOKIE;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${PHONE_MESSAGE}")
	private String PHONE_MESSAGE;
	@Override
	public EasyBuyResult checkData(String param, Integer type) {
		if(StringUtils.isBlank(param)){
			return EasyBuyResult.build(400,"无效参数",false);
		}
		
		EasybuyUserExample example=new EasybuyUserExample();
		Criteria createCriteria = example.createCriteria();
		if(type==1){
			//验证用户名
			createCriteria.andLoginnameEqualTo(param);
		}else if(type==2){
			createCriteria.andEmailEqualTo(param);
		}else if(type==3){
			createCriteria.andMobileEqualTo(param);
		}else{
			return EasyBuyResult.build(400,"验证类型无效");
		}
		List<EasybuyUser> list = easybuyUserMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return EasyBuyResult.build(400,"该用户已被注册",false);
		}
		return EasyBuyResult.ok(true);
	}
	@Override
	public EasyBuyResult register(EasybuyUser user) {
		try{
			user.setType(0);
			user.setState(0);//0未激活1已激活
			String code =UUID.randomUUID().toString();
			user.setCode(code);
	
			//MD5
			user.setPassword(md5(user.getPassword()));
			easybuyUserMapper.insertSelective(user);
			//注册成功后，发送一封邮件
			SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
			simpleMailMessage.setFrom("515844706@qq.com");
			simpleMailMessage.setTo(user.getEmail());
			simpleMailMessage.setSubject("欢迎注册易买");
			simpleMailMessage.setText("http://localhost:8083/user/jihuo?code="+code);
			mailSender.send(simpleMailMessage);
			return EasyBuyResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return EasyBuyResult.build(400,"注册失败");
		}
	}
	public String md5(String str){
		str=str+MD5_SALT;
		 String md5 = DigestUtils.md5DigestAsHex(str.getBytes());
		 return md5;
	}
	public static void main(String[] args) {
		UserServiceImpl impl=new UserServiceImpl();
		String md51 = impl.md5("123"+"java");
		String md52 = impl.md5(md51+"text");
		String md53 = impl.md5(md52+"tian");
		System.out.println(md53);
	}
	@Override
	public EasyBuyResult dologin(String username, String password) {
		if(StringUtils.isBlank(username)){
			return EasyBuyResult.build(400,"用户名不能为空");
		}
		if(StringUtils.isBlank(password)){
			return EasyBuyResult.build(400,"密码不能为空");
		}
		System.out.println("name"+username+"mi"+password);
		EasybuyUserExample example=new EasybuyUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andLoginnameEqualTo(username);
		//先将密码进行MD5加密，然后才进行验证
		
		createCriteria.andPasswordEqualTo(md5(password));
		List<EasybuyUser> list = easybuyUserMapper.selectByExample(example);
		if(list==null||list.size()==0){
			return EasyBuyResult.build(400,"用户名或密码错误");
		}
		EasybuyUser user = list.get(0);
		//
		if(user.getState()==0){
			//表示未激活账户
			return EasyBuyResult.build(400,"未激活账户,请到邮箱激活");
		}
		//登录成功
		//1.生成一个token--作为保存到Redis中的key
		String token=UUID.randomUUID().toString();
		//2保存到Redis中
		user.setPassword(null);
		jedisClient.set(token,JsonUtils.objectToJson(user));
		//3设置存在时间
		jedisClient.expire(token,SESSION_REDIS_EXPIRE);
		//4将token存入cookie
		return EasyBuyResult.ok(token);
	}
	@Override
	public EasyBuyResult getByToken(String token) {
		String json = jedisClient.get(token);
		if(StringUtils.isBlank(json)){
			return EasyBuyResult.build(400,"session已经过期");
		}
		EasybuyUser user=JsonUtils.jsonToPojo(json,EasybuyUser.class);
		jedisClient.expire(token, SESSION_REDIS_EXPIRE);
		return EasyBuyResult.ok(user);
	}

	@Override
	public EasyBuyResult logout(String token){
		jedisClient.expire(EASYBUY_COOKIE,0);
		return EasyBuyResult.ok();
	}
	@Override
	public EasyBuyResult jihuo(String code) {
		try{
			EasybuyUserExample example=new EasybuyUserExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andCodeEqualTo(code);
			//1.根据激活码查询用户
			List<EasybuyUser> list = easybuyUserMapper.selectByExample(example);
			if(list==null||list.size()==0){
				//无效激活码
				return EasyBuyResult.build(400,"无效的激活码");
			}
			EasybuyUser user = list.get(0);
			user.setState(1);
			user.setCode(null);
			easybuyUserMapper.updateByPrimaryKey(user);
			return EasyBuyResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return EasyBuyResult.build(400,"账户激活失败");
		}
	}
	

	@Override
	public boolean checkCode(String phone,String code) {
		//系统生成的验证码生成后存放在哪里？
		String randomCode = jedisClient.get(PHONE_MESSAGE+ phone);
		//进行比较
		if(randomCode.equals(code)){
			//验证通过'
			return true;
		}
		return false;
	}
	@Override
	public void sendPhoneMessage(String phone) {
		try{
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
			// 替换成你的AK
			final String accessKeyId = "LTAILF2LtaiYKtms";// 你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = "cJyiJfZrVvecscq5slDP5GhE2GNc6C";// 你的accessKeySecret，参考本文档步骤2
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName("易买网");
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode("SMS_148592584");
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			Random random=new Random();
			int code=random.nextInt(999999+1-100000)+100000
			jedisClient.set(PHONE_MESSAGE+ phone,code+"");
			jedisClient.expire(PHONE_MESSAGE+ phone,160);
			request.setTemplateParam("{\"code\":\""+code+"\"}");
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			//request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				// 请求成功
				System.out.println("短信发送成功");
			} else {
				System.out.println("短信发送失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
