package utils;

import com.github.javafaker.Faker;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import io.cucumber.java.an.E;
import io.cucumber.java.en_lol.WEN;
import io.cucumber.java.en_old.Ac;
import org.junit.platform.commons.util.PackageUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class utility {
    private static Faker faker = new Faker();

    public static WebElement waitForElementVisible(WebDriver driver, By locator, int timeoutMillis) {
        return new WebDriverWait(driver, Duration.ofMillis(timeoutMillis))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    public static void clickWithJS(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void safeClick(WebDriver driver, By locator) {
        try {
            waitForElementVisible(driver, locator, 800).click();
        } catch (Exception e) {
            clickWithJS(driver, waitForElementVisible(driver, locator, 800));
        }
    }

    public static void safeClickTimer(WebDriver driver, By locator, int timeoutMillis) {
        waitForElementVisible(driver, locator, timeoutMillis).click();
    }

    public static void pause( int timeoutMillis) {
        try {
            System.out.println("[WAIT] Delay selama " + timeoutMillis + "ms");
            Thread.sleep(timeoutMillis);
        } catch (InterruptedException e) {
            System.err.println("[ERROR] Gagal delay: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void clickMultipleTime(WebDriver driver, By locator, int times, int timeoutMillis) {
        for (int i = 0; i < times; i++) {
            safeClick(driver, locator);
            pause(timeoutMillis);
        }
    }

    public static void rightClickWithJS(WebDriver driver, By locator) {
        String js = """
                    var evt = new MouseEvent('contextmenu', {
                        bubbles: true,
                        cancelable: true,
                        view: window,
                        button: 2
                    });
                    arguments[0].dispatchEvent(evt);
                """;
        ((JavascriptExecutor) driver).executeScript(js, waitForElementVisible(driver, locator, 800));
    }

    public static void doubleClick(WebDriver driver, By locator) {
        new Actions(driver).doubleClick(waitForElementVisible(driver, locator, 800)).perform();
    }

    public static void clickMultipleElements(WebDriver driver, List<By> locators) {
        for (By locator : locators) {
            safeClick(driver, locator);
        }
    }

    public static void dragAndDrop(WebDriver driver, By sourceLocator, By targetLocator) {
        WebElement source = waitForElementVisible(driver, sourceLocator, 800);
        WebElement target = waitForElementVisible(driver, targetLocator, 800);
        new Actions(driver).dragAndDrop(source, target).perform();
    }

    public static void clickAndHold(WebDriver driver, By sourceLocator, By targetLocator) {
        WebElement source = waitForElementVisible(driver, sourceLocator, 800);
        WebElement target = waitForElementVisible(driver, targetLocator, 800);
        new Actions(driver).clickAndHold(source).moveToElement(target).perform();
    }

    public static void resizeElement(WebDriver driver, By resizeElement, int xOffset, int yOffset) {
        WebElement resizer = waitForElementVisible(driver, resizeElement, 800);
        new Actions(driver).clickAndHold(resizer).moveByOffset(xOffset, yOffset).release().perform();
    }

    public static void findElement(WebDriver driver, By locator, String blockPosition) {
        WebElement element = waitForElementVisible(driver, locator, 800);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void getElementTextSafe(WebDriver driver, By locator, String defaultValue) {
        WebElement element = waitForElementVisible(driver, locator, 800);
        String text = element.getText();
        System.out.println("Text : " + text);
    }

    public static void sendKeys(WebDriver driver, By locator, String text) {
        WebElement input = waitForElementVisible(driver, locator, 800);
        input.clear();
        input.sendKeys(text);
    }

    public static void sendKeysWithJS(WebDriver driver, By locator, String text) {
        WebElement element = waitForElementVisible(driver, locator, 800);
        String script = "arguments[0].value='" + text + "';";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    public static void sendKeysAndKeyButton(WebDriver driver, By locator, String text, Keys key) {
        WebElement element = waitForElementVisible(driver, locator, 800);
        element.clear();
        element.sendKeys(text);
        element.sendKeys(key);

    }

    public static void pressKeyButton (WebDriver driver, By locator, Keys key){
        WebElement element = waitForElementVisible(driver, locator, 800);
        element.clear();
        element.sendKeys(key);
    }

    public static boolean checkElement(WebDriver driver, By locator, int timeoutMillis) {
        WebElement element;

        try {
            element = waitForElementVisible(driver, locator, timeoutMillis);
        } catch (TimeoutException | NoSuchElementException e) {
            System.err.println("Element tidak ditemukan atau tidak terlihat: " + locator);
            return false;
        }

        if (element == null) {
            System.err.println("Element null meskipun tidak ada exception: " + locator);
            return false;
        }

        String tagName = element.getTagName();
        String typeAttr = element.getAttribute("type");
        String valueAttr = element.getAttribute("value");
        String text = element.getText();
        String colorCss = element.getCssValue("color");

        boolean isVisible = element.isDisplayed();
        boolean isEnabled = element.isEnabled();
        boolean isEditable = isVisible && isEnabled &&
                (tagName.equalsIgnoreCase("input") || tagName.equalsIgnoreCase("textarea"));

        System.out.println("Elemen ditemukan: " + locator);
        System.out.println("Tag       : <" + tagName + ">");
        System.out.println("Type      : " + (typeAttr != null ? typeAttr : "N/A"));
        System.out.println("Visible   : " + isVisible);
        System.out.println("Enabled   : " + isEnabled);
        System.out.println("Editable  : " + isEditable);
        System.out.println("Text      : \"" + text + "\"");
        System.out.println("Value     : \"" + valueAttr + "\"");
        System.out.println("CSS color : " + colorCss);

        return isVisible && isEnabled;

    }

    public static void switchToWindowByIndex(WebDriver driver, int index){
        List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
        if (index < 0 || index >= windowHandles.size()){
            throw new RuntimeException("Index tab di luar jangkauan: " + index);
        }
        driver.switchTo().window(windowHandles.get(index));
    }

    public static void switchToDefaultTab(WebDriver driver) {
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
    }

    public static void printAllWindowTitles(WebDriver driver) {
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            System.out.println("Window title: " + driver.getTitle());
        }
    }

    public static void acceptAlert(WebDriver driver){
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public static void dismissAlert(WebDriver driver){
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public static void getAlertText(WebDriver driver){
        Alert alert = driver.switchTo().alert();
        driver.switchTo().alert().getText();
        System.out.println(alert.getText());
    }

    public static void sendKeysToAlert(WebDriver driver, String inputText) {
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(inputText);
        alert.accept();
    }

        public static String generateFullName() {
        return faker.name().fullName();
    }

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateAddress() {
        return faker.address().fullAddress();
    }

    public static String generatePhoneNumber() {
        Faker faker = new Faker();
        String number = faker.number().digits(10);
        return number;
    }

    public static String generateCompanyName() {
        return faker.company().name();
    }

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generatePassword(){
        return faker.internet().password();
    }

    public static String generateBirthDate() {
        Date birthDate = faker.date().birthday(22, 39);
        return new SimpleDateFormat("MM/dd/yyyy").format(birthDate);
    }

    public static void selectDropDownByText(WebDriver driver, By locator, String text){
        WebElement dropdown = waitForElementVisible(driver, locator, 800);
        new Select(dropdown).selectByVisibleText(text);
    }
    public static void selectDropDownByIndex(WebDriver driver, By locator, String value) {
        WebElement dropdown = waitForElementVisible(driver, locator, 800);
        new Select(dropdown).selectByValue(value);
    }

    public static void checkImageStatus(String imageUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("[VALIDATION] Gambar aktif: " + imageUrl);
            } else {
                System.err.println("[ERROR] Gambar rusak: " + imageUrl);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Gagal mengecek gambar: " + e.getMessage());
        }
    }

    /** Memeriksa apakah link aktif (HTTP OK). Utilities.checkLinkStatus("https://example.com"); */
    public static void checkLinkStatus(String linkUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(linkUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("[VALIDATION] Link aktif: " + linkUrl);
            } else {
                System.err.println("[ERROR] Link rusak: " + linkUrl);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Gagal mengecek link: " + e.getMessage());
        }
    }

    /** Utilities.validate(driver); */
    public static void validate(WebDriver driver) {
        List<WebElement> elements = new ArrayList<>();
        elements.addAll(driver.findElements(By.tagName("a")));
        elements.addAll(driver.findElements(By.tagName("img")));

        for (WebElement el : elements) {
            String url = el.getTagName().equals("a") ? el.getAttribute("href") : el.getAttribute("src");

            if (url == null || url.trim().isEmpty()) {
                continue; // skip jika kosong
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(3000);
                connection.connect();

                int status = connection.getResponseCode();
                String type = el.getTagName().equals("a") ? "LINK" : "IMAGE";

                if (status == 200) {
                    System.out.println("[VALID " + type + "] " + url + " - " + connection.getResponseMessage() + " - " + connection.getResponseCode());
                } else {
                    System.err.println("[BROKEN " + type + "] " + url + " - " + connection.getResponseMessage() + " - " + connection.getResponseCode());
                }

            } catch (Exception e) {
                String type = el.getTagName().equals("a") ? "LINK" : "IMAGE";
                System.err.println("[ERROR " + type + "] " + url + " - " + e.getMessage());
            }
        }
    }
}






