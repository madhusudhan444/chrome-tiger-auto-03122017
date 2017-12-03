package TigerAirAutomationProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QueueJumpSelection {

	public void rtnQueueJumpSelect(WebDriver obj) throws Exception 
	
	{
		// TODO Auto-generated method stub

		
		obj.findElement(By.xpath("//div[@class='extras-maincontent']/div[2]/div[2]/a")).click();
//		obj.findElement(By.xpath("/html/body/div/div[4]/form/div[1]/div/div[2]/div[2]/div[2]/a")).click();
		
		
		Thread.sleep(2000);
		obj.findElement(By.xpath("//*[@id=\"modalqueuejump\"]/div/div/div/div/div[1]/div/div/div[1]/div/div/label")).click();
//		obj.findElement(By.xpath("//*[@class='close-button']")).click();
	
		obj.findElement(By.xpath("//*[@id=\"modalqueuejump\"]")).click();
		
		
	}
	
	
	public void oneWayQueueJumpSelect(WebDriver obj) 
	
	{
		// TODO Auto-generated method stub

		obj.findElement(By.xpath("//div[@class='extras-maincontent']/div[2]/div[2]/a")).click();
		obj.findElement(By.xpath("//*[@for='qjchk']")).click();
		obj.findElement(By.xpath("//*[@class='close-button']")).click();
	}


}
