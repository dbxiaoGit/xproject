/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class SimpleExcuter implements Excuter {
	public Logger log;
	public FirefoxDriver controller,exc;
	public WebDriverWait wdwC,wdwE;
	public String username="u";
	public String password="p";
	public int h,m,s;
	
	public SimpleExcuter(int h,int m,int s) {
		this.h = h;
		this.m = m;
		this.s = s;
		log = Logger.getLogger(SimpleExcuter.class);
		new Schedule(this.h,this.m,this.s,this);
	}
	
	@Override
	public void start(){
		log = Logger.getLogger(SimpleExcuter.class);
		init();
		controller.navigate().refresh();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String pageString = controller.getPageSource();
		if (readString(pageString,"buglog1","buglog2").equals("1")){
			doCheck();
		}
		end();
	}
	
	@Override
	public void init(){
		try {
			log.info("enter function init()");
			controller = new FirefoxDriver();
			controller.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			controller.manage().deleteAllCookies();
			controller.get("https://npm.taobao.org");
			wdwC = new WebDriverWait(controller,30);
			wdwC.until(ExpectedConditions.presenceOfElementLocated(By.id("username"))).sendKeys(username);
			controller.findElementById("password_input").sendKeys(password);
			controller.findElementById("login_button").click();
			wdwC.until(ExpectedConditions.presenceOfElementLocated(By.id("update_status_btn")));
		} catch(TimeoutException e) {
			log.error(e);
		}
	}
	
	@Override
	public String readString( String source,String leftTag,String rightTag) {
		log.info("enter function readString()");
		int posLeft = source.indexOf(leftTag);
		int posRight = source.indexOf(rightTag);
		String commond = "-1";
		log.info("init commond value --> "+commond);
		if(posLeft == -1 || posRight == -1) {
			log.info("source have no leftTag...");
			log.info("return -1");
			log.info("end function correlation ");
			return commond;
		}
		commond = source.substring(posLeft+leftTag.length(), posRight);
		log.info("change commond value --> "+commond);
		log.info("return " + commond);
		log.info("end function correlation");
		log.info("end function readString()");
		return commond;
	}
	
	@Override
	public void writeString(String string) {
		log.info("enter function writeString()");
		try {
			wdwC.until(ExpectedConditions.presenceOfElementLocated(By.className("lazyeditor-loader")));
			log.info("is located By.className(\"lazyeditor-loader\") "+isLocated(controller,By.className("lazyeditor-loader")));
			log.info("is located By.xpath "+isLocated(controller,By.xpath("//a[@name='comment']")));
			controller.executeScript("callLazyEditorCommentDescription(document.getElementsByClassName('lazyeditor-loader')[0]);", "");
			Thread.sleep(3000);
			controller.findElement(By.cssSelector("#editor-CommentDescription > div.editor-toolbar > span.editor-btn.editor-ico.editor-ico-source")).click();
			wdwC.until(ExpectedConditions.presenceOfElementLocated(By.className("editor-iframe")));
			log.info("is located By.className(\"editor-iframe\") "+isLocated(controller,By.className("editor-iframe")));
			controller.switchTo().frame(controller.findElementByClassName("editor-iframe"));
			controller.findElementByTagName("body").sendKeys(string);
			Thread.sleep(3000);
			controller.switchTo().parentFrame();
			controller.findElementById("update_status_btn").click();
			Thread.sleep(3000);
		} catch(TimeoutException e) {
			log.error(e);
		} catch (InterruptedException e1) {
			log.error(e1);
		}
		log.info("end function writeString()");
	}
	
	@Override
	public abstract void doCheck();
	
	@Override
	public String getImgString(FirefoxDriver fd,WebElement we) {
		log.info("enter function getImgString()");
		String s = null;
		Point location = we.getLocation();
		Dimension size = we.getSize();
		BufferedImage bigImage = null;
		try {
			bigImage = ImageIO.read(fd.getScreenshotAs(OutputType.FILE));
			BufferedImage subImage1 = bigImage.getSubimage(location.getX(), location.getY(), size.getWidth(),
			size.getHeight());
			ImageIO.write(subImage1, "png", new File("d:/code1.png"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(subImage1, "jpg",baos); 
			byte[] a = baos.toByteArray();
			s = OutputType.BASE64.convertFromPngBytes(a);
		} catch (IOException e) {
			log.error(e);
		} 
		s = "<img src=\"data:image/jpg;base64,"+s+"\"/>";
		log.info(s);
		log.info("end function getImgString()");
		return s;
	}
	
	@Override
	public boolean isLocated(WebDriver driver,By locator) {
		try {
			log.info("enter function isLocated()");
			driver.findElement(locator);
			log.info("return true ");
			return true ;
		} catch(NoSuchElementException e) {
			log.error("return false");
			return false;
		}
	}
	
	@Override
	public void end() {
		if (controller != null) {
			controller.quit();
		}
		if (exc != null) {
			exc.quit();
		}
		new Schedule(this.h,this.m,this.s,this);
	}
}
