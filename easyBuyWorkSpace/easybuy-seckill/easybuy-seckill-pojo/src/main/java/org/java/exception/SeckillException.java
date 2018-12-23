package org.java.exception;
/**
 * 执行秒杀的过程中出现的异常
 * @author yan
 *
 */
public class SeckillException extends RuntimeException{
	
	public SeckillException(String message){
		super(message);
	}
	public SeckillException(String message,Throwable throwable){
		super(message,throwable);
	}
}
