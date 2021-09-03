package tests;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Test {
        
	private static WebDriver driver;
	private static ExtentReports extent = new ExtentReports();
	private static ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
	private static ExtentTest feature;
    private static ExtentTest scenario;
    private static String previousHash = "000000008d9dc510f23c2657fc4f67bea30078cc05a90eb89e84cc475c080805";
    private static String currentHash = "";
    private static JSONObject endpointBody; 
	
	@Before
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        extent.attachReporter(spark);
        feature = extent.createTest(Feature.class, "Blockchain Block API Endpoint");
        scenario = feature.createNode(Scenario.class, "Verify Blockchain Hash is correct from /10 to /20 endpoints of Blockchain Api Website");
	}
	
	@After
	public void afterClass() {
        driver.quit();
        extent.flush();
	}
	
	@Given("user is at https:\\/\\/api.blockcypher.com\\/v1\\/btc\\/main\\/blocks")
	public void user_is_at_blockchain_url() {
		try {
			driver.get("https://api.blockcypher.com/v1/btc/main/blocks");
	        scenario.createNode(new GherkinKeyword("Given"), "user is at https:\\/\\/api.blockcypher.com\\/v1\\/btc\\/main\\/blocks").pass("Passed");
		} catch (Exception e) {
			System.out.println(e.toString());
	        try {
				scenario.createNode(new GherkinKeyword("Given"), "user is at https:\\/\\/api.blockcypher.com\\/v1\\/btc\\/main\\/blocks").fail("Failed");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@When("user goes to the Website adding \\/{string} to the URL")
    public void user_goes_to_the_website_adding_endpoint_to_the_url(String Endpoint) throws Throwable {
		try {
        driver.get("https://api.blockcypher.com/v1/btc/main/blocks/" + Endpoint);
        scenario.createNode(new GherkinKeyword("When"), "user goes to the Website adding \\/{string} to the URL").pass("Passed");
		} catch (Exception e) {
			System.out.println(e.toString());
	        scenario.createNode(new GherkinKeyword("When"), "user goes to the Website adding \\/{string} to the URL").fail("Failed");
		}
        
    }
    
	@Then("prev_block is equal to previous Hash")
    public void prev_block_is_equal_to_previous_hash() throws Throwable {
		try {
	    	endpointBody = new JSONObject(driver.findElement(By.xpath("//body")).getText());
	    	currentHash = endpointBody.getString("prev_block");
	    	System.out.println("PREVIOUS: " + previousHash + "\n CURRENT: " + currentHash);
	    	if (previousHash.equalsIgnoreCase(currentHash)) {
	    		scenario.createNode(new GherkinKeyword("Then"), "prev_block is equal to previous Hash").pass("Passed");
	    	} else {
		        scenario.createNode(new GherkinKeyword("Then"), "prev_block is equal to previous Hash").fail("Failed");
	    	}
	        
    	} catch (Exception e) {
			System.out.println(e.toString());
	        scenario.createNode(new GherkinKeyword("Then"), "prev_block is equal to previous Hash").fail("Failed");
    	}
		previousHash = endpointBody.getString("hash");
    }
    
}