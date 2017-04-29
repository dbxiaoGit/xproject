/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.in;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cq.util.SimpleExcuter;

public class ExcuterIn extends SimpleExcuter {

	public ExcuterIn(int h, int m, int s) {
		super(h, m, s);
	}

	public static void main(String[] args) {
		new ExcuterIn(9,0,0);
	}

	@Override
	public void doCheck() {
		int times = 0;
		String code = null;
		log.info("enter function doCheck()");
		try {	
			exc = new FirefoxDriver();
			exc.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			exc.manage().deleteAllCookies();
			exc.get("http://npm.taobao.org");
			wdwE = new WebDriverWait(exc,30);
			wdwE.until(ExpectedConditions.presenceOfElementLocated(By.id("login_button")));
			exc.findElementById("username").sendKeys(username);
			exc.findElementById("password_input").sendKeys(password);
			exc.findElementById("login_button").click();
			wdwE.until(ExpectedConditions.presenceOfElementLocated(By.id("checkin_btn")));
			exc.findElementById("checkin_btn").click();
			int aTimes = 0;
			do{
				Thread.sleep(3000);
				if(isLocated(exc,By.id("image"))) {
					String base64String = getImgString(exc,exc.findElement(By.id("image")));
					writeString(base64String);
					Thread.sleep(120000);
					do{
						Thread.sleep(120000);
						controller.navigate().refresh();
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						String pageString = controller.getPageSource();
						code = readString(pageString,"buglog3","buglog4");
						times++;
						log.info("try times "+times);
					} while(!Pattern.matches("[0-9a-zA-Z]{4}",code) && times < 5);
					if (times >=5) {
						log.info("code value is "+ code);
						log.info("code not correct");
					} else {
						log.info("times < 5 ,code value is "+ code);
						log.info("isLocated code_input element ? "+isLocated(exc,By.id("code_input")));
						exc.findElement(By.id("code_input")).sendKeys(code);
						Thread.sleep(3000);
					}
				} 
				Thread.sleep(3000);
				if (!isLocated(exc,By.id("image"))) {
					int i = (int) (10*Math.random());
					log.debug("sleep " + i + "minutes.");
					writeString(Integer.toString(i));
					Thread.sleep(i*60*1000);
				}
				exc.findElement(By.name("check")).click();
				Thread.sleep(30000);
				log.info("current title is "+exc.findElement(By.id("ui-dialog-title-tdialog")).getText() );
				aTimes++;
				log.info("debug aTimes is "+aTimes);
			}while (!exc.findElement(By.id("ui-dialog-title-tdialog")).getText().contains("成功") && aTimes < 3);
			if(exc.findElement(By.id("ui-dialog-title-tdialog")).getText().contains("成功")) {
				writeString("ok");
			} else {
				log.info("aTimes is "+aTimes);
			}
		} catch(Exception e) {
			log.error(e);
		}
		log.info("end function doCheck()");
	}

}
