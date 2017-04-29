/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OnR implements Excuter {
	
	public static void main(String[] args) {
		new Schedule(14,02,0, new OnR());
		//new OnR().start();
	}
	
	@Override
	public void start() {
		try{
			DateFormat df = new SimpleDateFormat("EEEE");
			/*
			System.out.println(df.format(Calendar.getInstance().getTime()));
			System.out.println(df.format(Calendar.getInstance().getTime()).equals("星期六"));
			Calendar c1 = Calendar.getInstance();
			for(int i = 0 ; i <= 7 ; i++) {
				c1.set(Calendar.DAY_OF_WEEK,i);
				System.out.println(df.format(c1.getTime()));
			}
			*/
			if(!df.format(Calendar.getInstance().getTime()).equals("星期六") && !df.format(Calendar.getInstance().getTime()).equals("星期日")){
				Runtime.getRuntime().exec("ipconfig -release ");
				Thread.sleep(60000);
				Runtime.getRuntime().exec("ipconfig -renew");
				end();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String readString(String source, String leftTag, String rightTag) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void writeString(String string) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doCheck() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getImgString(FirefoxDriver fd, WebElement we) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isLocated(WebDriver driver, By locator) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void end() {
		// TODO Auto-generated method stub
		new Schedule(9,28,0, new OnR());
	}

}
