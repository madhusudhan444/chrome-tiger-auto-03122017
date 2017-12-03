package TigerAirAutomationProject;

import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.io.File;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import static org.openqa.selenium.OutputType.*;

public class CarHireSelection

{
    public void mSelectCarHire(WebDriver obj) throws Exception 
    {
//        FirefoxDriver wd;
//        wd = new FirefoxDriver();
//        wd.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//        wd.get("https://uatbooking.tigerair.com.au/TigerAirIBE3415/Booking/Extras");
 
    	
    	obj.findElement(By.linkText("show hire cars")).click();
    	Thread.sleep(2000);
 //   	obj.findElement(By.xpath("html/body/div[1]/div[4]/form/div[3]/div/div/div/div[2]/div/div[2]/div/div[2]/div/div[2]/div/div/div/div/div[1]/div/div[1]/div/div/div/div[5]/button")).click();
    	obj.findElement(By.xpath("//*[@id=\"resultsListSectionOne\"]/div/div/div/div/div[1]/div/div[1]/div/div/div/div[5]/button")).click();
    							
    	
    	Thread.sleep(2000);
//    	obj.findElement(By.xpath("//*[@id=\"CarHirePanel\"]/div/span")).click();
    	obj.findElement(By.xpath("//*[@id=\"CarHirePanel\"]/div/h1/img")).click();
    	
//    	
//    	
//    	//*[@id="resultsListSectionOne"]/div/div/div/div/div[1]/div/div[1]/div/div/div/div[5]/button
    	
    	
    	
    	
//    	obj.findElement(By.linkText("show hire cars")).click();
//    	obj.findElement(By.xpath("//div[@class='owl-wrapper']//button[.='Select']")).click();
//    	obj.findElement(By.cssSelector("span.close-button")).click();
//    	obj.findElement(By.xpath("//*[@class='close-button']")).click();
    }
    
//    public static boolean isAlertPresent(FirefoxDriver wd) {
//        try {
//            wd.switchTo().alert();
//            return true;
//        } catch (NoAlertPresentException e) {
//            return false;
//        }
//    }
}



