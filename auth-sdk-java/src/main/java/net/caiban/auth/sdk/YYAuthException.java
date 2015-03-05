/**
 * 
 */
package net.caiban.auth.sdk;

/**
 * @author parox
 *
 */
public class YYAuthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public YYAuthException(){
		super();
	}

	public YYAuthException(String msg){
		super(msg);
	}

	public YYAuthException(String msg, Throwable cause){
		super(msg, cause);
	}

}
