import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.fail;

public class TestOneWebDriver {

    private String URL = null;
    private WebDriver Driver;

    private String Username = "AlexeyKlimashevich";
    private String Password = "1";
    @BeforeMethod
    public void initialize(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver_win32/chromedriver.exe");
        URL = "https://192.168.100.26/";
        Driver = new ChromeDriver();
        Driver.get(URL);
    }

    @Test
    public void explicitWaitWebDriverTest() throws IOException, InterruptedException {
        WebElement usernameField = Driver.findElement(By.id("Username"));
        usernameField.sendKeys("");

        WebElement passwordField = Driver.findElement(By.id("Password"));
        passwordField.sendKeys("");

        WebElement loginButton = Driver.findElement(By.id("SubmitButton"));
        loginButton.click();

        Thread.sleep(10000);
        System.out.println("Thread slept for 10 seconds");

        WebElement nameField = Driver.findElement(By.id("name"));

        Assert.assertEquals(nameField.getText(), "Kontyava, Sergey", "Not the same value");
    }

    @Test
    public void explicitWaitForSignOutLinkToAppearTest(){
        WebElement usernameField = Driver.findElement(By.id("Username"));
        usernameField.sendKeys("AlexeyKlimashevich");

        WebElement passwordField = Driver.findElement(By.id("Password"));
        passwordField.sendKeys("1");

        WebElement loginButton = Driver.findElement(By.id("SubmitButton"));
        loginButton.click();

        WebElement signOutLink = (new WebDriverWait(Driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='Sign out']>ins")));
        Assert.assertEquals(signOutLink.getText(), "Sign Out", "Not the same value");
    }

    @Test
    public void searchByOfficeWaitToAppearTest(){

        WebDriverWait wait = new WebDriverWait(Driver, 10);
        WebElement formContainer = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".form-container")));
        Assert.assertEquals(formContainer.isDisplayed(), true, "Element is not displayed");

        WebElement usernameField = Driver.findElement(By.id("Username"));
        usernameField.sendKeys(Username);

        WebElement passwordField = Driver.findElement(By.id("Password"));
        passwordField.sendKeys(Password);

        WebElement loginButton = Driver.findElement(By.id("SubmitButton"));
        loginButton.click();

        WebElement homeLink = Driver.findElement(By.id("homeMenu"));
        Assert.assertEquals(homeLink.isDisplayed(), true, "Home link is not displayed");

        WebElement officeLink = Driver.findElement(By.id("officeMenu"));
        officeLink.click();

        WebDriverWait officeSearchWait = new WebDriverWait(Driver, 15);
        WebElement searchByOffice = officeSearchWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search")));

        Assert.assertEquals(searchByOffice.isDisplayed(), true, "Search by office field is not displayed");
    }

    @Test(dataProvider = "testData")
    public void ddtLoginTest(String firstName, String password, String result){
        if(getResult(result)){
            WebElement usernameField = Driver.findElement(By.id("Username"));
            usernameField.sendKeys(firstName);

            WebElement passwordField = Driver.findElement(By.id("Password"));
            passwordField.sendKeys(password);

            WebElement loginButton = Driver.findElement(By.id("SubmitButton"));
            loginButton.click();

            WebElement homeLink = Driver.findElement(By.id("homeMenu"));
            Assert.assertEquals(homeLink.isDisplayed(), getResult(result), "Home link is not displayed");

            WebElement signOutLink = Driver.findElement(By.cssSelector("a[title='Sign out']"));
            signOutLink.click();
        }
        else {
            WebElement usernameField = Driver.findElement(By.id("Username"));
            usernameField.sendKeys(firstName);

            WebElement passwordField = Driver.findElement(By.id("Password"));
            if(password.equals("null")){
                passwordField.sendKeys("");
            }else{
                passwordField.sendKeys(password);
            }
            WebElement loginButton = Driver.findElement(By.id("SubmitButton"));
            loginButton.click();

            WebElement passwordError = Driver.findElement(By.cssSelector("span[title*='required']"));
            Assert.assertEquals(passwordError.isDisplayed(), !getResult(result), "Password error message is not displayed");
            Boolean a= Boolean.getBoolean(result);
        }

    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][] {
                new Object[]{"AlexeyKlimashevich","1","true"},
                new Object[]{"SergeyKontyava","2","true"},
                new Object[]{"VadimZubovich","null","false"},
                new Object[]{"EugenBorisik","null","false"},
        };
    }

    public boolean getResult(String result){
        String resultValue = result.toLowerCase();
        if(resultValue.equals("true")){
            return true;
        }else{
            return false;
        }
    }
    @Test
    public void implicitWaiterWebDriverTest() {
        Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        try {
            WebElement username = Driver.findElement(By.id("Username"));
            username.sendKeys("sergeykontyava");
            WebElement password = Driver.findElement(By.id("Password"));
            password.sendKeys("1");

            WebElement loginButton = Driver.findElement(By.id("SubmitButton"));
            loginButton.click();

            Assert.assertEquals(Driver.findElement(By.cssSelector("a[title='Sign out']")).getText().trim(), "Sign Out", "Link texts are not the same");
        } catch (NoSuchElementException e) {
            fail("Element not found!!");
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void cleanup(){
        Driver.quit();
    }
}
