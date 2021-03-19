import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ConfirmationPage;
import pages.FormPage;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class TestSample {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","D:\\WebDrivers\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Keyboard and Mouse Input
        driver.get("http://formy-project.herokuapp.com/keypress");
        WebElement text_input = driver.findElement(By.id("name"));
        text_input.click();
        text_input.sendKeys("LVK");

        WebElement button = driver.findElement(By.id("button"));
        button.click();
        //

        // Autocomplete
        driver.get("http://formy-project.herokuapp.com/autocomplete");
        WebElement autocomplete = driver.findElement(By.id("autocomplete"));
        autocomplete.sendKeys("1555 Park Blvd, Palo Alto, CA");
        Thread.sleep(1000); // 1 second delay

        WebElement autocompleteResult = driver.findElement(By.className("pac-item"));
        autocompleteResult.click();
        //

        // Scroll
        driver.get("http://formy-project.herokuapp.com/scroll");
        WebElement name = driver.findElement(By.id("name"));
        Actions action = new Actions(driver);
        action.moveToElement(name);
        name.sendKeys("Test Text");
        //

        // Switch to an Active Window
        driver.get("http://formy-project.herokuapp.com/switch-window");
        WebElement newTabButton = driver.findElement(By.id("new-tab-button"));
        newTabButton.click();

        String originalHandle = driver.getWindowHandle();

        for (String handle1: driver.getWindowHandles()){
            driver.switchTo().window(handle1);
        }
        driver.switchTo().window(originalHandle);
        //

        // Switch to Alert
        driver.get("http://formy-project.herokuapp.com/switch-window");
        WebElement alertButton = driver.findElement(By.id("alert-button"));
        alertButton.click();

        Alert alert = driver.switchTo().alert();
        alert.accept();
        //

        // Executing JaveScript
        driver.get("http://formy-project.herokuapp.com/modal");
        WebElement modalButton = driver.findElement(By.id("modal-button"));
        modalButton.click();

        WebElement closeButton = driver.findElement(By.id("close-button"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", closeButton);
        //

        // Drag and Drop
        driver.get("http://formy-project.herokuapp.com/dragdrop");

        WebElement image = driver.findElement(By.id("image"));
        WebElement box = driver.findElement(By.id("box"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(image, box).build().perform();
        //

        // Radiobuttons and Checkboxes
        driver.get("http://formy-project.herokuapp.com/radiobutton");
        WebElement radioButton1 = driver.findElement(By.id("radio-button-1"));
        radioButton1.click();

        WebElement radioButton2 = driver.findElement(By.cssSelector("input[value='option2']"));
        radioButton2.click();

        WebElement radioButton3 = driver.findElement(By.xpath("/html/body/div/div[3]/input"));
        radioButton3.click();
        //

        // Date Picker
        driver.get("http://formy-project.herokuapp.com/datepicker");
        WebElement datefield = driver.findElement(By.id("datepicker"));
        datefield.sendKeys("03/03/2022");
        datefield.sendKeys(Keys.RETURN);
        //

        // Dropdown
        driver.get("http://formy-project.herokuapp.com/dropdown");
        WebElement dropdownMenu = driver.findElement(By.id("dropdownMenuButton"));
        dropdownMenu.click();
        WebElement autocompleteItem = driver.findElement(By.id("autocomplete"));
        autocompleteItem.click();
        //

        // File Upload - DOES NOT WORK!!!
        //driver.get("http://formy-project.herokuapp.com/fileupload");
        //WebElement fileUploadField = driver.findElement(By.id("file-upload-field"));
        //dropdownMenu.sendKeys("D:\\jdk-8u181-windows-x64.exe");
        //

        // Implicit Waits
        // waits for a specified amount of time before throwing a no such element exception (default time is 0)
        driver.get("http://formy-project.herokuapp.com/autocomplete");
        WebElement autocomplete1 = driver.findElement(By.id("autocomplete"));
        autocomplete1.sendKeys("1555 Park Blvd, Palo Alto, CA");

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement autocompleteResult1 = driver.findElement(By.className("pac-item"));
        autocompleteResult1.click();
        //

        // Explicit Waits
        // waits for a specified amount od fime for a certain condition to be true.If the condition is not met in time,
        // an excption is thrown
        driver.get("http://formy-project.herokuapp.com/autocomplete");
        WebElement autocomplete2 = driver.findElement(By.id("autocomplete"));
        autocomplete2.sendKeys("1555 Park Blvd, Palo Alto, CA");

        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement autocompleteResult2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pac-item")));
        autocompleteResult2.click();
        //

        // Complete Webform
        driver.get("http://formy-project.herokuapp.com/form");

        submitForm(driver);

        waitForAlertBanner(driver);

        assertEquals("The form was successfully submitted!", getAlertBannerText(driver));

        //driver.quit();

        // Complete Webform with PageObject
        driver.get("http://formy-project.herokuapp.com/form");

        FormPage formPage = new FormPage();
        formPage.submitForm(driver);

        ConfirmationPage confirmationPage = new ConfirmationPage();
        confirmationPage.waitForAlertBanner(driver);

        assertEquals("The form was successfully submitted!", confirmationPage.getAlertBannerText(driver));

        driver.quit();
    }

    public static void submitForm(WebDriver driver){
        driver.findElement(By.id("first-name")).sendKeys("Phil");

        driver.findElement(By.id("last-name")).sendKeys("Collins");

        driver.findElement(By.id("job-title")).sendKeys("Artist");

        driver.findElement(By.id("radio-button-2")).click();

        driver.findElement(By.id("checkbox-1")).click();

        driver.findElement(By.id("select-menu")).click();

        driver.findElement(By.cssSelector("option[value='4']")).click();

        driver.findElement(By.id("datepicker")).sendKeys("03/21/2021");
        driver.findElement(By.id("datepicker")).sendKeys(Keys.RETURN);

        driver.findElement(By.cssSelector(".btn.btn-lg.btn-primary")).click();
    }

    public static void waitForAlertBanner(WebDriver driver)
    {
        WebDriverWait waitTime = new WebDriverWait(driver, 10);
        waitTime.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert")));
    }

    public static String getAlertBannerText(WebDriver driver)
    {
        return driver.findElement(By.className("alert")).getText();
    }
}
