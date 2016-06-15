import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by SergeyKontyava on 6/15/2016.
 */
public class jsAlertsTests {

    private WebDriver Driver = null;
    private String URL = "https://the-internet.herokuapp.com/javascript_alerts";

    @BeforeTest
    public void initialize(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver_win32/chromedriver.exe");
        Driver = new ChromeDriver();
        Driver.get(URL);
    }

    @Test
    public void jsAlertTest() {
        Driver.findElement(By.cssSelector("button[onclick*=\"Alert\"]")).click();
        try {
            WebDriverWait wait = new WebDriverWait(Driver, 10000);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsConfirmOKTest(){
        Driver.findElement(By.cssSelector("button[onclick*='Confirm']")).click();
        try {
            WebDriverWait wait = new WebDriverWait(Driver, 10000);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsConfirmDismissTest(){
        String message = "You clicked: Cancel";
        Driver.findElement(By.cssSelector("button[onclick*='Confirm']")).click();
        try {
            new WebDriverWait(Driver, 10000).until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.switchTo().alert();
            alert.dismiss();

            Assert.assertEquals(Driver.findElement(By.id("result")).getText().trim(), message, "Messages are not the same");
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsPromptTest() {
        String message = "You entered: Prompt test";
        Driver.findElement(By.cssSelector("button[onclick*='Prompt']")).click();
        try {
            new WebDriverWait(Driver, 10000).until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.switchTo().alert();
            alert.sendKeys("Prompt test");
            alert.accept();

            Assert.assertEquals(Driver.findElement(By.id("result")).getText().trim(), message, "Result messages are not the same");
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void cleanup(){
        Driver.close();
    }
}
