package org.java.exception;
/**
 * 秒杀结束异常
 * @author yan
 *
 */
public class SeckillClosedException extends RuntimeException{
	
	public SeckillClosedException(String message){
		super(message);
	}
	public SeckillClosedException(String message,Throwable throwable){
		super(message,throwable);
	}
}
