package com.syncrooms.Homepage.Utility;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;



/**
 * @author hp
 *
 */
public class BaseClass {
	public static WebDriver driver;
	public static String baseURL="";
	public static String parent_window;
	public static JavascriptExecutor js;
	public static Robot robot;
	public static Rectangle rectangle;
	
	public String screenShot(String name) {
		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String filename=System.getProperty("user.dir")+"\\FailedTCsScreenShots\\"+name+"_testfail.jpg";
		try {
			FileUtils.copyFile(srcFile, new File(filename));
			return filename;
			
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
		
	}
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
	public void getAllCookies() {
		Set<Cookie> cookies=driver.manage().getCookies();
		Iterator<Cookie> itr=cookies.iterator();
		while (itr.hasNext()) {
			Cookie cookie = (Cookie) itr.next();
			String cookieValue=cookie.getValue();
			System.out.println(cookieValue);
		}
	}
	
	public void handlingDatePickerJS(By locator) {
	 js=(JavascriptExecutor)driver;
	 //js.executeScript("document.getElementById('datePicker').value='12/02/1994'");
	 js.executeScript("document.querySelector('#datePicker').value='12/02/1994'");
	
		
	}
	
	public void robotScreenShot(String format) throws Exception {
		robot=new Robot();
		SimpleDateFormat df=new SimpleDateFormat("mm-dd-yyyy_HH-mm");
		String fileName=df.format(new Date());
		
		//df.format(new GregorianCalendar().getTime());
		rectangle=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage image=robot.createScreenCapture(rectangle);
		ImageIO.write(image, format, new File(System.getProperty("user.dir")+"\\RobotScreenShots\\"+fileName+".jpg"));
		
		
	}
	
	public void dynamicRadioButton(By locator,String actualValue) {
		List<WebElement> radioButtons=driver.findElements(locator);
		Iterator<WebElement> itr=radioButtons.iterator();
		
		while (itr.hasNext()) {
			WebElement radioButton =itr.next();
			String value=radioButton.getAttribute("id");
			if (value.equalsIgnoreCase(actualValue)) {
				radioButton.click();
				break;
			}
			
		}
	}
	
	public void selectMonthYearDatepicker(By year,By month) {
		WebElement monthdropdown = driver.findElement(month);
		Select monthCombo = new Select(monthdropdown);
		monthCombo.selectByVisibleText("March");

		WebElement yeardropdown = driver.findElement(year);
		Select yearCombo = new Select(yeardropdown);
		yearCombo.selectByVisibleText("2015");
		
	}
	public void datePicker(By datepickerLocator,String data){
	    try{

	        WebElement dateWidget = driver.findElement(datepickerLocator);

	        List<WebElement> rows = dateWidget.findElements(By.tagName("tr"));  
	        List<WebElement> columns = dateWidget.findElements(By.tagName("td"));  

	        for (WebElement cell: columns){
	            if (cell.getText().equals(data)){
	                cell.findElement(By.linkText(data)).click();
	                break; 
	            }
	        }
	    }catch(Exception e){
	        e.getMessage();
	    }
	}
	
	
	public void multipleWindows() {
		
		parent_window=driver.getWindowHandle();
		Set<String> winds=driver.getWindowHandles();
		Iterator<String> itr=winds.iterator();
		while (itr.hasNext()) {
			String chiled_window = (String) itr.next();
			if (!parent_window.equals(chiled_window)) {
				
			driver.switchTo().window(chiled_window);
			System.out.println(driver.getTitle());
			driver.close();
			}
		}
		driver.switchTo().window(parent_window);
	}
	
	public void handleJavaScriptAlert() {
		
	}
	
	 public void takeMultipleSnaps() throws InterruptedException{
		  Thread.sleep(5000);
		  driver.get("https://www.digitalcitizen.life/4-ways-take-screenshots-windows-8-81-using-built-tools");
		 Thread.sleep(5000);
		 int index=1;
		 JavascriptExecutor exe=(JavascriptExecutor)driver;
		 exe.executeScript("window.scrollTo(0,0)");
		 Boolean b=(Boolean)exe.executeScript("return document.documentElement.scrollHeight>document.documentElement.clientHeight");
		 Long scrollH=(Long)exe.executeScript("return document.documentElement.scrollHeight");
		 Long clientH=(Long)exe.executeScript("return document.documentElement.clientHeight");
		 if (b.booleanValue()) {
			 while(scrollH.intValue()>0) {
				 
				 File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				 try {
					FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\ScreenShots\\Screen-"+index+".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     exe.executeScript("window.scrollTo(0,"+clientH*index+")");
			     scrollH=scrollH-clientH;
			     Thread.sleep(3000);
			     index++;
			 }
		}else {
		 File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"\\ScreenShots\\Screen-1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	  }

}
