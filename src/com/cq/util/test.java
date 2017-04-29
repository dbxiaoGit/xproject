/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

import java.io.IOException;

import org.apache.log4j.Logger;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger l=Logger.getLogger(test.class);
		l.info("info   1");
		l.debug("debug   2");
		l.warn("warn 3");
		l.error("error 4");
		try {
			Runtime.getRuntime().exec("taskkill /F /IM xxx* /T ");
			Thread.sleep(10000);
			Runtime.getRuntime().exec("xxx.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
