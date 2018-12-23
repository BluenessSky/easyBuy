package org.java.exception;
/**
 * 重复秒杀异常
 * @author yan
 *
 */
public class RepeatSeckillException extends RuntimeException{
	
	public RepeatSeckillException(String message){
		super(message);
	}
	public RepeatSeckillException(String message,Throwable throwable){
		super(message,throwable);
	}
}
