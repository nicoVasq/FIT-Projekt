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
import sun.misc.resources.Messages;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterIT {
    private static WebDriver driver;
    private static JavascriptExecutor jse;

    private static final String FIT = "http://localhost:4200/";
    private static final String AdminTool = "http://localhost:4200/admin-tool/login";
    private static final String Account = "http://localhost:4200/konto/login";

    private static String FirmenToken;

    @BeforeClass
    public static void initClass(){
        System.setProperty("webdriver.chrome.driver", ".\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        jse = (JavascriptExecutor)driver;
    }
    @AfterClass
    public static void closeDriver (){
        /*try {
            Thread.sleep(5000);*/
            driver.close();
        /*}catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }*/
    }

    @Test
    public void TEST000_LOADSides(){
        driver.get(FIT);
        driver.get(AdminTool);
        driver.get(Account);
    }
    @Test
    public void TEST001_Register()
    {
        driver.get(FIT);
        driver.findElement(By.cssSelector(".btn-dark")).click(); //Clicks the button

        //insert Data
        driver.findElement(By.id("companyName")).sendKeys("Testfirma AAG");
        driver.findElement(By.id("street")).sendKeys("TStraße");
        driver.findElement(By.id("streetNumber")).sendKeys("9");
        driver.findElement(By.id("zipCode")).sendKeys("4030");
        driver.findElement(By.id("city")).sendKeys("Linz");

        //scroll down
        jse.executeScript("window.scrollBy(0,250)", "");

        //insert more Data
        driver.findElement(By.id("firstName")).sendKeys("Max");
        driver.findElement(By.id("lastName")).sendKeys("Mustermann");
        driver.findElement(By.id("email")).sendKeys("fit.website.testing.l@gmail.com");
        driver.findElement(By.id("phoneNumber")).sendKeys("01234567890");
        driver.findElement(By.id("checkboxBranch0")).findElement(By.cssSelector(".cr")).click();
        driver.findElement(By.id("checkboxBranch1")).findElement(By.cssSelector(".cr")).click();

        //scroll down
        jse.executeScript("window.scrollBy(0,250)", "");

        //press button to send information
        driver.findElement(By.xpath("//button[contains(text(),'Abschicken')]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Test if the OK Window pops up
        assertTrue(driver.findElement(By.cssSelector(".toast-title")).isDisplayed());
    }

    @Test
    public void TEST002_AcceptKonto(){
        //log into the AdminTool
        driver.get(AdminTool);
        driver.findElement(By.id("email")).sendKeys("fit.website.testing.l@gmail.com");
        driver.findElement(By.id("password")).sendKeys("test123");
        driver.findElement(By.xpath("//button[contains(text(),'Einloggen')]")).click();

        //accept the company
        driver.findElement(By.xpath("//div[contains(text(),'Firmen bestätigen')]")).click();
        driver.findElement(By.xpath("//button[@class='btn btn-sm btn-success mb-0-5 py-0 px-1']")).click();

        //Test if OK Window pops up
        assertTrue(driver.findElement(By.cssSelector(".toast-title")).isDisplayed());
    }

    @Test
    public void TEST003_GETCODE (){
        String emailContent = "";
        driver.get("http://localhost:4200");
        try {
            Message messages[] = getUnreadMessages();
            for (Message m:messages)
                if(m.getSubject().contains("Ihr Firmenantrag wurde akzeptiert"))
                    emailContent = m.getContent().toString();
            if(emailContent == "")
                Assert.fail("no EMAIL with FirmenToken found");

            Pattern pattern = Pattern.compile("([A-Z, 0-9]{4}-[A-Z, 0-9]{4}-[A-Z, 0-9]{4})");
            Matcher matcher = pattern.matcher(emailContent);
            if(matcher.find())
                FirmenToken=matcher.group(1);
            else
                Assert.fail("no FirmenToken found");

            driver.findElement(By.id("code-part1")).sendKeys(FirmenToken);
            driver.findElement(By.id("btnFitRegister")).click();

            assertTrue(driver.findElement(By.cssSelector(".toast-container")).isDisplayed());

            System.out.println("FirmenToken: " + FirmenToken);
        }catch (NoSuchProviderException e) {
            e.printStackTrace();
        }catch (MessagingException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    /*@Test
    public void TEST004_Read(){
        driver.get(Account);

        System.out.println("FirmenToken: " + FirmenToken);
        if(FirmenToken != null && FirmenToken != "not found"){
            driver.findElement(By.id("code-part1")).sendKeys(FirmenToken);
            driver.findElement(By.id("btnFitRegister")).click();

            assertTrue(driver.findElement(By.cssSelector(".toast-title")).isDisplayed());
        }
        else {
            Assert.fail("No FirmenToken found");
        }
    }*/

    private Message[] getUnreadMessages(){
        Message[] messages = null;
        try {
            /*SearchTerm term = new SearchTerm() {
                public boolean match(Message message) {
                    try {
                        if (message.getSubject().contains(subject) && message.isSet(Flags.Flag.SEEN)) {
                            return true;
                        }
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            };*/
            Properties properties = new Properties();
            properties.setProperty("imap.googlemail.com","imaps");
            Session se = Session.getDefaultInstance(properties);
            Store store = se.getStore("imaps");
            store.connect("imap.gmail.com","fit.website.testing.l@gmail.com","testingPassword");
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            folder.setFlags(messages,new Flags(Flags.Flag.SEEN),true);
            folder.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        }catch (MessagingException ex){
            ex.printStackTrace();
        }
        return messages;
    }
}
