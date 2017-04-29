/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public interface Excuter {
	public void start();
	public void init();
	public String readString( String source,String leftTag,String rightTag);
	public void writeString(String string);
	public void doCheck();
	public String getImgString(FirefoxDriver fd,WebElement we);
	public boolean isLocated(WebDriver driver,By locator);
	public void end();
	
}
