package org.java.seckill.quartz;

import java.util.Date;
import java.util.List;

import org.java.dto.Exposer;
import org.java.mapper.SeckillMapper;
import org.java.mapper.SuccessKilledMapper;
import org.java.pojo.Seckill;
import org.java.pojo.SuccessKilled;
import org.java.pojo.SuccessKilledExample;
import org.java.pojo.SuccessKilledExample.Criteria;
import org.springframework.beans.factory.annotation.Autowired;

public class Job {
	@Autowired
	private SuccessKilledMapper successKilledMapper;
	@Autowired
	private SeckillMapper seckillMapper;
	public void incrCount(){
		System.out.println("任务调度被执行了。。。。。");
		//查询成功秒杀的信息列表
		SuccessKilledExample example=new SuccessKilledExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andStateEqualTo((byte)0);
		List<SuccessKilled> list = successKilledMapper.selectByExample(example);
		//判断秒杀的时间和现在的系统时间相比是否已经达到半小时
		for(SuccessKilled kill:list){
			Date createTime = kill.getCreateTime();
			Date now=new Date();
			//如果超过了半小时那么将成功的记录删除，并且放回库存
			if((int)(now.getTime()-createTime.getTime())/(60/1000)>=30){
				
				successKilledMapper.deleteByPrimaryKey(kill);
				Seckill seckill=new Seckill();
				seckill.setSeckillId(kill.getSeckillId());
				seckill.setNumber(seckill.getNumber()+1);
				seckillMapper.updateByPrimaryKey(seckill);
			}
		}
	}
}
