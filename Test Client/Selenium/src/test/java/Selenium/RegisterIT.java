package Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class RegisterIT {
    private static WebDriver driver;
    private static JavascriptExecutor jse;
    private static boolean initialized = false;
    private static boolean lastTestcaseExecuted = false;
    @Before
    public void initClass(){
        try {
            if(!initialized) {
                System.setProperty("webdriver.chrome.driver", "C:\\opt\\Selenium\\chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().deleteAllCookies();
                driver.manage().window().fullscreen();
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
                driver.get("http://localhost:4200/");
                initialized = true;
                jse = (JavascriptExecutor)driver;
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    @After
    public void closeDriver (){
        try {
            if (lastTestcaseExecuted) {
                Thread.sleep(5000);
                driver.close();
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void TEST001_Register()
    {
        try {
            driver.findElement(By.cssSelector(".btn-dark")).click(); //Clicks the button
            Thread.sleep(2000);

            driver.findElement(By.id("companyName")).sendKeys("Testfirma AAG");
            driver.findElement(By.id("street")).sendKeys("TStraße");
            driver.findElement(By.id("streetNumber")).sendKeys("9");
            driver.findElement(By.id("zipCode")).sendKeys("4030");
            driver.findElement(By.id("city")).sendKeys("Linz");
            //driver.findElement(By.cssSelector(".custom-control:nth-child(2)")).click();
            jse.executeScript("window.scrollBy(0,250)", "");

            driver.findElement(By.id("firstName")).sendKeys("Max");
            driver.findElement(By.id("lastName")).sendKeys("Mustermann");
            driver.findElement(By.id("email")).sendKeys("Max.Mustermann@samplemail.com");
            driver.findElement(By.id("phoneNumber")).sendKeys("01234567890");
            driver.findElement(By.id("checkboxBranch0")).findElement(By.cssSelector(".cr")).click();
            driver.findElement(By.id("checkboxBranch1")).findElement(By.cssSelector(".cr")).click();

            driver.findElement(By.cssSelector(".btn-block")).click();

            Thread.sleep(7000);

            assertTrue(driver.findElement(By.cssSelector(".toast-title")).isDisplayed());

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
