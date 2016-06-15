import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class iFrameTest {

    private String URL = "https://the-internet.herokuapp.com/iframe";
    private WebDriver Driver;

    @BeforeTest
    public void initialize(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver_win32/chromedriver.exe");
        Driver = new ChromeDriver();
        Driver.get(URL);
    }

    @Test
    public void iFrameWebDriverTest() {
        String message = "Hello ";

        WebElement frame = Driver.findElement(By.tagName("iframe"));
        Driver.switchTo().frame(frame);

        WebElement textElement = Driver.findElement(By.id("tinymce"));
        textElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        textElement.sendKeys(Keys.DELETE);
        textElement.sendKeys(message);
        Driver.switchTo().defaultContent();
        Driver.findElement(By.cssSelector("#mceu_3 button[tabIndex='-1']")).click();
        Driver.switchTo().frame(frame);
        textElement.sendKeys("world!");

        Assert.assertEquals(textElement.getText(), "Hello world!", "Texts are not the same");
   }

    @AfterTest
    public void cleanup(){
        Driver.close();
    }
}
