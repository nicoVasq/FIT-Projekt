package Selenium;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import javax.mail.*;
import javax.mail.search.FlagTerm;

import com.sun.mail.util.MailLogger;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterIT {
    private static WebDriver driver;
    private static JavascriptExecutor jse;

    @BeforeClass
    public static void initClass(){
        System.setProperty("webdriver.chrome.driver", ".\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("http://localhost:4200/");
        jse = (JavascriptExecutor)driver;
    }
    @AfterClass
    public static void closeDriver (){
        try {
            Thread.sleep(5000);
            driver.close();
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
            driver.findElement(By.id("email")).sendKeys("fit.website.testing.l@gmail.com");
            driver.findElement(By.id("phoneNumber")).sendKeys("01234567890");
            driver.findElement(By.id("checkboxBranch0")).findElement(By.cssSelector(".cr")).click();
            driver.findElement(By.id("checkboxBranch1")).findElement(By.cssSelector(".cr")).click();

            jse.executeScript("window.scrollBy(0,250)", "");
            driver.findElement(By.xpath("//button[contains(text(),'Abschicken')]")).click();

            Thread.sleep(5000);

            assertTrue(driver.findElement(By.cssSelector(".toast-title")).isDisplayed());

        }
        catch (InterruptedException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void TEST002_GETCODE (){
        Properties properties = new Properties();
        properties.setProperty("imap.googlemail.com","imaps");
        Session se = Session.getDefaultInstance(properties);
        try {
            Store store = se.getStore("imaps");
            store.connect("imap.gmail.com","fit.website.testing.l@gmail.com","testingPassword");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            System.out.println(messages.length);
            for (Message m:messages)
                if(m.getSubject().contains("Ihr Firmenantrag wurde eingereicht"))
                    System.out.println(m.getContent().toString());
             folder.close(false);
             store.close();
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        }catch (MessagingException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
