package TigerAirAutomationProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.OBJ_ADAPTER;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TigerAirAutomation

{
	static WebDriver obj;
	static String PNR = null;

	public TigerAirAutomation(WebDriver obj) {

	}

	public static void main(String[] args) throws Exception

	{

		String filePath = "E:\\Sellenium\\FrameWork\\TestData\\BookingDetailsNEW.xlsx";
		FileInputStream file = null;
		XSSFWorkbook workBook = null;
		try {
			file = new FileInputStream(new File(filePath));
			workBook = new XSSFWorkbook(file);
			HashMap<String, String> map = new HashMap<String, String>();

			for (int i = 0; i < workBook.getNumberOfSheets(); i++)

			{

				XSSFSheet sheet = workBook.getSheetAt(i);
				int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();
				Row headerRow = sheet.getRow(0);
				for (int row = 1; row <= totalRows; row++) {
					Row row1 = sheet.getRow(row);
					for (int c = 0; c < row1.getLastCellNum(); c++)
						map.put(headerRow.getCell(c).getStringCellValue(), row1.getCell(c).getStringCellValue());

					newBooking(map);

					for (int col = 0; col < row1.getLastCellNum(); col++) {

						if (headerRow.getCell(col).getStringCellValue().equals("PNRnumber")) {

							row1.getCell(col).setCellValue(PNR);

						}
					}
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			FileOutputStream fos = new FileOutputStream(
					new File("E:\\Sellenium\\FrameWork\\TestData\\BookingDetailsNEW.xlsx"));
			workBook.write(fos);
			fos.close();
			file.close();

		}

	}

	private static void newBooking(HashMap<String, String> map) throws InterruptedException, Exception {
		// System.setProperty("webdriver.gecko.driver","C:\\Users\\madhusudhan\\Desktop\\geckodriver.exe");
		// WebDriver obj = new FirefoxDriver();

		System.setProperty("webdriver.chrome.driver", "E:\\Sellenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver obj = new ChromeDriver();

		obj.manage().window().maximize();
		obj.get("https://uatbooking.tigerair.com.au/TigerAirIBE3415/Booking/search");
//		obj.close();
		
		
		// Flight Search
		FlightSearch fSearch = new FlightSearch();
//		fSearch.fltSearchRtn(obj, map);

		String tripType = map.get("TripType");
		
		if (tripType.equals("oneway")) 
		{
			
			fSearch.fltSearchOneway(obj, map);
		}
		else 
		{
			fSearch.fltSearchRtn(obj, map);
				
		}
		
		
		
		// Flight selection
		String fare = "light";
		String rtnFareVal = map.get("fareType");

		ReturnFareFltSelect rtnLtFr = new ReturnFareFltSelect();
//		Thread.sleep(3000);
		obj.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		if (rtnFareVal.equals(fare)) {
			rtnLtFr.RtnLtFr(obj);
		} else {
			rtnLtFr.RtnExpFr(obj);
		}

//		Thread.sleep(3000);
		obj.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		// Enter Pax Details
		PaxDetails paxDet1 = new PaxDetails();
		paxDet1.PasngrDet1(obj, map);

		// // insert test case validation-- If required at any point of application
		// String PaxName = obj.findElement(By.id("passengerSelet")).getText();
		// System.out.println("Pax Name is :"+PaxName);

		// Pax Contact details
		PaxContactDetails PaxCntDet = new PaxContactDetails();
		PaxCntDet.PaxCntDetails(obj, map);

		// Cabin+ Selection
		CabinPlus cabinSelect = new CabinPlus();
		cabinSelect.rtnCabinPls(obj);

		// Baggage Selection
		BaggageSelection SlctBag = new BaggageSelection();
		SlctBag.rtnSelectBaggage(obj);

		// SPorts Equipment
		SportsEquipments SprtsEquip = new SportsEquipments();
		SprtsEquip.OnePieceSportsEquip(obj);

		// Navigate to Seat Selection page
		obj.findElement(By.id("ContinueBtn")).click();

		// Seat Selection
		SeatSelection SelectSeat = new SeatSelection();
		SelectSeat.noSeatSelection(obj);
//		Thread.sleep(9000);
		obj.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		
		 // Select Meal
		 MealsSelection SelectMeal = new MealsSelection();
		 SelectMeal.rtnMealsSelect(obj);
		 Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);	
		
		
		 // Select Queue JUmp
		 QueueJumpSelection SelectQjmp = new QueueJumpSelection();
		 SelectQjmp.rtnQueueJumpSelect(obj);
		 Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		
		
		 //Select Carbon Offset
		 CarbonOffsetSelection SelectCarbonOffset = new CarbonOffsetSelection();
		 SelectCarbonOffset.rtnCarbonOffsetSelection(obj);
		 Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		
		
		 //Select CarHire
		 CarHireSelection SelectCarHire = new CarHireSelection();
		 SelectCarHire.mSelectCarHire(obj);
		 Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 
		 
		 // //Select Hotels
		 // HotelSelection SelectHotel = new HotelSelection();
		 // SelectHotel.mHotelSelection(obj);
		 // Thread.sleep(2000);
		
		 //Select Airport Parking
		 AirportParkingSelection SelectAirPortPaking = new AirportParkingSelection();
		 SelectAirPortPaking.mairportParkingSelect(obj);
		 Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 
		// Select Insurance
		InsuranceSelection SelectInsure = new InsuranceSelection();
		SelectInsure.yesInsurance(obj);
		Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		 
		 
		// Navigate to Checkout page
		obj.findElement(By.id("ContinueBtn")).click();
		Thread.sleep(4000);
//		 obj.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		
		String FOP = map.get("FOP");
		
		
		switch(FOP) 
		
		{
		
		case "POLI" : 
			
			poliFOP poliPP = new poliFOP();
			poliPP.poliPay(obj, map);
			break;
			
		case "VOUCHER" :
			
			VoucherFOP voucherPP = new VoucherFOP();
			voucherPP.voucher(obj, map);
			
			// #disclaimer-check
			obj.findElement(By.id("disclaimer-check")).click();
			Thread.sleep(1000);
			obj.findElement(By.id("SubmitPaymentBtn")).click();

			break;

		case "VELOCITY" :
		
			VelocityFOP velocityPP = new VelocityFOP();
			velocityPP.velocityPay(obj, map);
			
			// #disclaimer-check
			obj.findElement(By.id("disclaimer-check")).click();
			Thread.sleep(1000);
			obj.findElement(By.id("SubmitPaymentBtn")).click();

			break;
		
		
		case "CARD" : 
			
			CardPayment visaCC = new CardPayment();
			visaCC.cardPay(obj, map);
			
			// #disclaimer-check
			obj.findElement(By.id("disclaimer-check")).click();
			Thread.sleep(1000);
			obj.findElement(By.id("SubmitPaymentBtn")).click();

			break;
			
			
		default	:
			
			System.out.println("Please Specify the FOP for the booking and try again");
			break;
 
		} 
		
		
		
			
		
//		// Card FOP
//		CardPayment visaCC = new CardPayment();
//		visaCC.cardPay(obj, map);
//
//		// #disclaimer-check
//		obj.findElement(By.id("disclaimer-check")).click();
//		// obj.findElement(By.xpath("//*[@class='check']")).click();
//		Thread.sleep(1000);
//
//		obj.findElement(By.id("SubmitPaymentBtn")).click();
//

//		Thread.sleep(20000);
//		Thread.sleep(10000);
		 obj.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		 
		 
		
		obj.navigate().refresh();
		PNR = obj.findElement(By.xpath("//*[@id=\"PNRNumber\"]/h2")).getText();

		System.out.println("______________________________________________________________");
		System.out.println("Booking Referenxe is : " + PNR);
		System.out.println("______________________________________________________________");
	}

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// obj.findElement(By.xpath("//*[@id=\"ux_widget_inner\"]/div[1]/div/div/a")).click();

// obj.switchTo().frame("frame").close();
// obj.findElement(By.xpath("//*[@id=\"ux_widget_inner\"]/div[1]/div/div/a")).click();

// obj.findElement(By.id("rl__roktAppsBanner213659633")).click();
// obj = (FirefoxDriver) obj.switchTo().frame(null);
// obj.findElement(By.xpath("//div[3]/div/div/div[1]/div/div/a")).click();

// String parent = obj.getWindowHandle();
// obj.switchTo().window(parent);
// //obj.close();
// obj.findElement(By.xpath("//*[@id=\"ux_widget_inner\"]/div[1]/div/div/a")).click();
//

// obj.switchTo().alert().dismiss();
//

// String parent = obj.getWindowHandle();
//
// Set<String> children = obj.getWindowHandles();
//
// String windowHandler = null;
//
// Iterator<String> itr = children.iterator();
//
// while(itr.hasNext ())
// {
//
// windowHandler = itr.next();
//
// if(!windowHandler.equals(parent))
// //if(!windowHandle.equals(parent))
// {
//
// obj.switchTo().window(windowHandler);
// Thread.sleep(1000);
//
// obj.close();
//
// }
//
// }

// Thread.sleep(2000);

// ROKTdailogClose roktmsg = new ROKTdailogClose();
// roktmsg.roktDailogClosing(obj);
//

// obj.findElement(By.id("ux_iagree_button")).click();
// obj.findElement(By.id("ux_smartsignup_layout_buttons_next")).click();

// Alert alrt = obj.switchTo().alert();
//
// alrt.dismiss();

// String frameID = "//*[@id=\"rl__widget";
// String regExp = "\\d{9}";
//
// Pattern p = Pattern (frameID + regExp);

// WebElement xPathUserList;
// xPathUserList.findElement(By.xpath(p));

// int size = obj.findElements(By.tagName("iframe")).size();
// System.out.println(size);
//
// Thread.sleep(5000);

// WebElement
// frame=obj.findElement(By.xpath("//iframe[contains(@class='wdHolder.*')]"));

// WebElement
// frame=obj.findElement(By.xpath("//iframe[contains(@id='rl__widget.*')]"));
// Thread.sleep(2000);
// obj.switchTo().frame(frame);

// Thread.sleep(2000);

// WebElement modal =
// obj.findElement(By.xpath("//div[contains(@class,'wdHolder')]"));
// WebElement radio =
// modal.findElement(By.xpath("//iframe[contains(@id='rl__widget.*')]"));
//
//
// obj.switchTo().frame(radio);

// obj.switchTo().parentFrame();

// obj.switchTo().frame(p);
// rl__widget212879716
// rl__widget511487027

// System.out.println("1");
//
// Thread.sleep(3000);
// obj.findElement(By.xpath("(//*[@class='ui_widget_close_button button close
// small'])[1]")).click();

//
// System.out.println("2");
//
// Thread.sleep(2000);
//
// obj.navigate().refresh();
//
//
// String PNR =
// obj.findElement(By.xpath("//*[@id=\"PNRNumber\"]/h2")).getText();
//
// System.out.println(PNR);
//
//
//
// map.put("PNRnumber",PNR);

// Label PNRdetail =new Label(22,2,PNR);
// wrkSht.addCell(PNRdetail);

//
//// adding xl file
//
// String excelFileName = "E:\\Sellenium\\Test.xls";//name of excel file
//
// String sheetName = "Sheet1";//name of sheet
//
// HSSFWorkbook wb = new HSSFWorkbook();
// HSSFSheet sheet = wb.createSheet(sheetName) ;
//
//// iterating r number of rows
// for (int r=1;r < 5; r++ )
// {
// HSSFRow row = sheet.createRow(r);
// HSSFCell cell = row.createCell(0);
//
// cell.setCellValue("Cell "+r+" "+r);
//
// //iterating c number of columns
//// for (int c=0;c < r; c++ )
//// {
//// HSSFCell cell = row.createCell(c);
////
//// cell.setCellValue("Cell "+r+" "+c);
//// }
// }
//
// FileOutputStream fileOut = new FileOutputStream(excelFileName);
//
//// write this workbook to an Outputstream.
// wb.write(fileOut);
// fileOut.flush();
// fileOut.close();
//

// }

// }

//// adding xl file
//
// String excelFileName = "E:\\Sellenium\\Test.xls";// name of excel file
// String sheetName = "Sheet1";// name of sheet
//
// HSSFWorkbook wb = new HSSFWorkbook();
// HSSFSheet sheet = wb.createSheet(sheetName);
//
//// iterating r number of rows
// for (int r = a; r < a+1; r++) {
// HSSFRow row = sheet.createRow(r);
// HSSFCell cell = row.createCell(0);
//
// cell.setCellValue(PNR);
// }
//
// FileOutputStream fileOut = new FileOutputStream(excelFileName);
//
//// write this workbook to an Outputstream.
// wb.write(fileOut);
// fileOut.flush();
// fileOut.close();
