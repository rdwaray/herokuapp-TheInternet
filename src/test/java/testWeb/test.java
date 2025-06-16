package testWeb;

import com.github.dockerjava.api.model.CpuUsageConfig;
import com.github.javafaker.BackToTheFuture;
import com.github.javafaker.Faker;
import io.cucumber.java.zh_cn.但是;
import io.cucumber.java.zh_cn.假如;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class test {
    private static WebDriver driver;
    private static Faker faker;
    private static WebDriverWait wait;

    @BeforeClass
    public static void setupTest(){
        driver = driverFactory.getDriver();
        wait = driverFactory.getWait();
        faker = driverFactory.getFaker();
    }

    @Before
    public void setUp(){
        driver.manage().deleteAllCookies();
        driver.get("https://the-internet.herokuapp.com/");
    }
    @Test
    public void ABTesting(){
        By shadowTest = By.xpath("//a[normalize-space()='A/B Testing']");
        utility.findElement(driver,shadowTest,"center");
        utility.safeClick(driver,shadowTest);

        By text = By.xpath("//*[@id=\"content\"]/div/h3");
        utility.getElementTextSafe(driver,text,"Message Not Found");
    }

    @Test
    public void addRemoveElements(){
        By elementTest =By.xpath("//a[normalize-space()='Add/Remove Elements']");
        utility.safeClick(driver,elementTest);
        By addElement = By.xpath("//button[@onclick='addElement()']");
        utility.clickMultipleTime(driver,addElement,5,500);

        By removeElement = By.xpath("//div[@id='elements']//button[1]");
        utility.clickMultipleTime(driver,removeElement,5, 500);
    }

    @Test
    public void basicAuth(){
        By authTest = By.xpath("//a[normalize-space()='Basic Auth']");
        utility.safeClick(driver,authTest);
        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
        By text = By.xpath("//*[@id=\"content\"]/div/p");
        utility.getElementTextSafe(driver,text,"Message Not Found");
    }

    @Test
    public void brokenImage(){
        By imageTest = By.xpath("//a[normalize-space()='Broken Images']");
        utility.safeClick(driver,imageTest);
        utility.validate(driver);
    }

    @Test
    public void challengingDOM(){
        By challengeDOMTest = By.xpath("//a[normalize-space()='Challenging DOM']");
        utility.safeClick(driver,challengeDOMTest);
        By text = By.xpath("//*[@id=\"content\"]/div/div/div/div[2]");
        utility.getElementTextSafe(driver,text,"Message Not Found");
    }

    @Test
    public void checkBox(){
        By checkBoxTest = By.xpath("//a[normalize-space()='Checkboxes']");
        utility.safeClick(driver,checkBoxTest);
        By box1 = By.xpath("//input[1]");
        By box2 = By.xpath("//input[2]");
        utility.safeClick(driver,box1);
        utility.safeClick(driver,box2);
    }

    @Test
    public void contextMenu(){
        By contextTest = By.xpath("//a[normalize-space()='Context Menu']");
        utility.safeClick(driver,contextTest);

        By box = By.id("hot-spot");
        utility.rightClickWithJS(driver,box);
        utility.getAlertText(driver);
        utility.acceptAlert(driver);
    }

    @Test
    public void digestAuth(){
        By digestTest = By.xpath("//a[normalize-space()='Digest Authentication']");
        utility.safeClick(driver,digestTest);
        driver.get("https://admin:admin@the-internet.herokuapp.com/digest_auth");
        By text = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credenti')]");
        utility.getElementTextSafe(driver,text,"Message Not Found");
    }

    @Test
    public void dissapearingElements(){
        By dissapearTest = By.xpath("//a[normalize-space()='Disappearing Elements']");
        utility.safeClick(driver,dissapearTest);
        By home = By.xpath("//a[normalize-space()='Home']");
        By about = By.xpath("//a[normalize-space()='About']");
        By contactUs = By.xpath("//a[normalize-space()='Contact Us']");
        By portfolio = By.xpath("//a[normalize-space()='Portfolio']");
        By gallery = By.xpath("//a[normalize-space()='Gallery']");

        utility.safeClick(driver,home);
        driver.navigate().back();
        utility.safeClick(driver,about);
        driver.navigate().back();
        utility.safeClick(driver,contactUs);
        driver.navigate().back();
        utility.safeClick(driver,portfolio);
        driver.navigate().back();
        utility.safeClick(driver,gallery);
        driver.navigate().back();
    }

    @Test
    public void dragAndDrop(){
        By dragDropTest = By.xpath("//a[normalize-space()='Drag and Drop']");
        utility.safeClick(driver,dragDropTest);
        By target1 = By.id("column-a");
        By target2 = By.id("column-b");
        utility.dragAndDrop(driver,target1,target2);
    }

    @Test
    public void dropdown(){
        By dropTest = By.xpath("//a[normalize-space()='Dropdown']");
        utility.safeClick(driver,dropTest);
        By dropMenu = By.id("dropdown");
        utility.safeClick(driver,dropMenu);
        utility.selectDropDownByIndex(driver,dropMenu,"1");
    }

    @Test
    public void dynamicContent(){
        By dynamicTest = By.xpath("//a[normalize-space()='Dynamic Content']");
        utility.safeClick(driver,dynamicTest);
        By element = By.xpath("//div[@class='row']//div[2]//div[1]//img[1]");
        utility.checkElement(driver,element,1000);
    }

    @Test
    public void dynamicControls(){
        By dynamicControl = By.xpath("//a[normalize-space()='Dynamic Controls']");
        utility.safeClick(driver,dynamicControl);

        By checkBox = By.xpath("//input[@type='checkbox']");
        utility.safeClick(driver,checkBox);

        By enabler1 = By.xpath("//button[normalize-space()='Remove']");
        utility.safeClick(driver,enabler1);



        By input = By.xpath("//input[@type='text']");
        utility.checkElement(driver,input,1000);

        By enabler2 = By.xpath("//button[normalize-space()='Enable']");
        utility.safeClick(driver,enabler2);

        utility.pause(12000);
        WebElement inputText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));
        inputText.sendKeys("test");


        utility.checkElement(driver,input,1000);
    }

    @Test
    public void dynamicLoading(){
        By dynamicLoadingTest = By.xpath("//a[normalize-space()='Dynamic Loading']");
        utility.safeClick(driver,dynamicLoadingTest);

        By test1 = By.xpath("//a[normalize-space()='Example 1: Element on page that is hidden']");
        utility.safeClick(driver,test1);

        By start1 = By.xpath("//button[normalize-space()='Start']");
        utility.safeClick(driver,start1);

        By message1 = By.id("finish");
        utility.checkElement(driver,message1,1000);

        utility.pause(7000);
        utility.getElementTextSafe(driver,message1,"Message Not Found");

        driver.get("https://the-internet.herokuapp.com/dynamic_loading");

        By test2 = By.xpath("//a[normalize-space()='Example 2: Element rendered after the fact']");
        utility.safeClick(driver,test2);

        By start2 = By.xpath("//button[normalize-space()='Start']");
        utility.safeClick(driver,start2);

        By message2 = By.id("finish");

        utility.pause(7000);
        utility.getElementTextSafe(driver,message2,"Message Not Found");

    }

    @Test
    public void entryAdModal(){
        By modalTest = By.xpath("//a[normalize-space()='Entry Ad']");
        utility.safeClick(driver,modalTest);

        By modal = By.xpath("//h3[normalize-space()='This is a modal window']");
        utility.getElementTextSafe(driver,modal,"Message Not Found");

        By closeModal = By.xpath("//p[normalize-space()='Close']");
        utility.safeClick(driver,closeModal);


        By displayModal = By.id("restart-ad");
        utility.safeClick(driver,displayModal);
    }

    @Test
    public void downloadFile(){
        By downloadTest = By.xpath("//a[normalize-space()='File Download']");
        utility.safeClick(driver,downloadTest);

        By file = By.xpath("//a[normalize-space()='Learning.txt']");
        utility.findElement(driver,file,"center");
        utility.safeClick(driver,file);
    }

    @Test
    public void uploadFile(){
     By uploadTest = By.xpath("//a[normalize-space()='File Upload']");
     utility.safeClick(driver,uploadTest);

    By upload = By.id("file-upload");
    utility.sendKeys(driver,upload,"C:\\Users\\Ray\\IdeaProjects\\herokuapp\\src\\test\\resources\\material\\Learning.txt");
    By uploadFile = By.id("file-submit");
    utility.safeClick(driver,uploadFile);
    }

    @Test
    public void floatingMenu(){
        By floatingTest = By.xpath("//a[normalize-space()='Floating Menu']");
        utility.safeClick(driver,floatingTest);

        By footer  = By.xpath("//a[@target='_blank']");
        utility.findElement(driver,footer,"center");

        By home = By.xpath("//a[normalize-space()='Home']");
        By news = By.xpath("//a[normalize-space()='News']");
        By contact = By.xpath("//a[normalize-space()='Contact']");
        By about = By.xpath("//a[normalize-space()='About']");

        utility.checkElement(driver,home,1000);
        utility.checkElement(driver,news,1000);
        utility.checkElement(driver,contact,1000);
        utility.checkElement(driver,home,1000);

        utility.safeClick(driver,home);
        utility.safeClick(driver,news);
        utility.safeClick(driver,contact);
        utility.safeClick(driver,about);
    }

    @Test
    public void forgotPassword(){
        By forgotTest = By.xpath("//a[normalize-space()='Forgot Password']");
        utility.safeClick(driver,forgotTest);

        By emailForm = By.id("email");
        String emailText = utility.generateEmail();
        utility.sendKeys(driver,emailForm,emailText);

        By submitEmail = By.id("form_submit");
        utility.safeClick(driver,submitEmail);
    }

    @Test
    public void formAuth(){
        By authTest = By.xpath("//a[normalize-space()='Form Authentication']");
        utility.safeClick(driver,authTest);

        By userForm = By.id("username");
        utility.sendKeys(driver,userForm,"tomsmith");

        By passwordForm = By.id("password");
        utility.sendKeys(driver,passwordForm,"SuperSecretPassword!");

        By loginButton = By.xpath("//i[@class='fa fa-2x fa-sign-in']");
        utility.safeClick(driver,loginButton);

        By text = By.xpath("//h4[@class='subheader']");
        utility.getElementTextSafe(driver,text,"Message Not Found");

        By logout = By.xpath("//i[@class='icon-2x icon-signout']");
        utility.safeClick(driver,logout);
    }

    @Test
    public void frames(){
        By frameTest = By.xpath("//a[normalize-space()='Frames']");
        utility.safeClick(driver,frameTest);

        By nestedFrame = By.xpath("//a[normalize-space()='Nested Frames']");
        utility.safeClick(driver,nestedFrame);

        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        System.out.println(driver.getPageSource());

        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-top");

        driver.switchTo().frame("frame-middle");
        System.out.println(driver.getPageSource());

        driver.switchTo().defaultContent();
        driver.switchTo().frame("frame-top");

        driver.switchTo().frame("frame-right");
        System.out.println(driver.getPageSource());

        driver.switchTo().defaultContent();

        driver.switchTo().frame("frame-bottom");
        driver.switchTo().defaultContent();
        System.out.println(driver.getPageSource());
    }

    @Test
    public void horizontalSlider(){
        By sliderTest = By.xpath("//a[normalize-space()='Horizontal Slider']");
        utility.safeClick(driver,sliderTest);

        By slider = By.xpath("//*[@id=\"content\"]/div/div/input");
        utility.safeClick(driver,slider);
        utility.pressKeyButton(driver,slider,Keys.ARROW_LEFT);
        utility.pressKeyButton(driver,slider,Keys.ARROW_LEFT);
        utility.pressKeyButton(driver,slider,Keys.ARROW_LEFT);
    }

    @Test
    public void hover(){
        By hoverTest = By.xpath("//a[normalize-space()='Hovers']");
        utility.safeClick(driver,hoverTest);

        Actions actions = new Actions(driver);

        WebElement img1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='example']//div[1]//img[1]")));
        actions.moveToElement(img1).perform();
        By info1 = By.xpath("//h5[normalize-space()='name: user1']");
        utility.getElementTextSafe(driver,info1,"Message Not Found");


        WebElement img2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='row']//div[2]//img[1]")));
        actions.moveToElement(img2).perform();
        By info2 = By.xpath("//h5[normalize-space()='name: user2']");
        utility.getElementTextSafe(driver,info2,"Message Not Found");

        WebElement img3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[3]//img[1]")));
        actions.moveToElement(img3).perform();
        By info3 = By.xpath("//h5[normalize-space()='name: user3']");
        utility.getElementTextSafe(driver,info3,"Message Not Found");

    }

    @Test
    public void inputs(){
        By inputTest = By.xpath("//a[normalize-space()='Inputs']");
        utility.findElement(driver,inputTest,"center");
        utility.safeClick(driver,inputTest);

        By inputBox = By.xpath("//input[@type='number']");
        utility.sendKeys(driver,inputBox,"123");
    }

    @Test
    public void javascriptAlert(){
        By alertTest = By.xpath("//a[normalize-space()='JavaScript Alerts']");
        utility.safeClick(driver,alertTest);

        By alert1 = By.xpath("//button[@onclick='jsAlert()']");
        utility.safeClick(driver,alert1);
        utility.getAlertText(driver);
        utility.acceptAlert(driver);


        By alert2 = By.xpath("//button[@onclick='jsConfirm()']");
        utility.safeClick(driver,alert2);
        utility.getAlertText(driver);
        utility.dismissAlert(driver);


        By alert3 = By.xpath("//button[@onclick='jsPrompt()']");
        utility.safeClick(driver,alert3);
        utility.getAlertText(driver);
        utility.sendKeysToAlert(driver,"mana");

    }

    @Test
    public void javaScriptError(){
        By errorTest = By.xpath("//a[normalize-space()='JavaScript onload event error']");
        utility.safeClick(driver,errorTest);
        utility.validate(driver);
    }

    @Test
    public void keyPress(){
        By keyPressTest = By.xpath("//a[normalize-space()='Key Presses']");
        utility.safeClick(driver,keyPressTest);

        By target = By.id("target");
        utility.pressKeyButton(driver,target,Keys.ARROW_DOWN);
        utility.pressKeyButton(driver,target,Keys.ARROW_LEFT);
        utility.pressKeyButton(driver,target,Keys.ARROW_RIGHT);
        utility.pressKeyButton(driver,target,Keys.ARROW_UP);

    }

    @Test
    public void largeDeepDOM(){
        By deepDOMTest = By.xpath("//a[normalize-space()='Large & Deep DOM']");
        utility.safeClick(driver,deepDOMTest);

        By target = By.id("sibling-30.3");
        utility.findElement(driver,target,"center");
        utility.getElementTextSafe(driver,target,"Message Not Found");
    }

    @Test
    public void window(){
        By windowTest = By.xpath("//a[normalize-space()='Multiple Windows']");
        utility.safeClick(driver,windowTest);

        By addWindow = By.xpath("//a[normalize-space()='Click Here']");
        utility.safeClick(driver,addWindow);
        utility.switchToDefaultTab(driver);
        utility.switchToWindowByIndex(driver,1);
        utility.printAllWindowTitles(driver);
    }

    @Test
    public void notificationMessage(){
        By notifMessage = By.xpath("//a[normalize-space()='Notification Messages']");
        utility.safeClick(driver,notifMessage);

        By msg = By.id("flash");
        utility.getElementTextSafe(driver,msg,"Message Not Found");
        By notif = By.xpath("//a[normalize-space()='Click here']");
        utility.safeClick(driver,notif);

        utility.pause(5000);
        utility.getElementTextSafe(driver,msg,"Message Not Found");
        utility.safeClick(driver,notif);

        utility.pause(5000);

        utility.getElementTextSafe(driver,msg,"Message Not Found");
    }

    @Test
    public void statusCode(){
        By statusTest = By.xpath("//a[normalize-space()='Redirect Link']");
        utility.safeClick(driver,statusTest);

        By redirect = By.xpath("//a[@id='redirect']");
        utility.safeClick(driver,redirect);

        utility.validate(driver);

        By code200 = By.xpath("//a[normalize-space()='200']");
        utility.safeClick(driver,code200);
        utility.validate(driver);
        By goBack = By.xpath("//a[normalize-space()='here']");
        utility.safeClick(driver,goBack);

        By code301 = By.xpath("//a[normalize-space()='301']");
        utility.safeClick(driver,code301);
        utility.validate(driver);
        utility.safeClick(driver,goBack);

        By code404 = By.xpath("//a[normalize-space()='404']");
        utility.safeClick(driver,code404);
        utility.validate(driver);
        utility.safeClick(driver,goBack);

        By code500 = By.xpath("//a[normalize-space()='500']");
        utility.safeClick(driver,code500);
        utility.validate(driver);
        utility.safeClick(driver,goBack);
    }

    @Test
    public void secureDownload(){
        By secureTest = By.xpath("//a[normalize-space()='Secure File Download']");
        utility.safeClick(driver,secureTest);
        driver.get("https://admin:admin@the-internet.herokuapp.com/download_secure");

        By file = By.xpath("//a[normalize-space()='some-file.txt']");
        utility.safeClick(driver,file);

    }

    @Test
    public void shadowDOM(){
        By shadowTest = By.xpath("//a[normalize-space()='Shadow DOM']");
        utility.safeClick(driver,shadowTest);

        WebElement content = driver.findElement(By.xpath("//*[@id=\"content\"]/my-paragraph[1]"));
        SearchContext shadowRoot = content.getShadowRoot();
        WebElement text = shadowRoot.findElement(By.cssSelector("p"));
        System.out.println(text.getText());
    }

    @Test
    public void shiftingContent(){
        By shiftingTest = By.xpath("//a[normalize-space()='Shifting Content']");
        utility.safeClick(driver,shiftingTest);
        By menuELement = By.xpath("//a[normalize-space()='Example 1: Menu Element']");
        utility.safeClick(driver,menuELement);

        By home = By.xpath("//a[normalize-space()='Home']");
        utility.safeClick(driver,home);
        driver.navigate().back();

        By about = By.xpath("//a[normalize-space()='About']");
        utility.safeClick(driver,about);
        driver.navigate().back();

        By contactUs = By.xpath("//a[normalize-space()='Contact Us']");
        utility.safeClick(driver,contactUs);
        driver.navigate().back();

        By random = By.xpath("//p[contains(text(),'To specify a differant numbor of pixels to shift t')]//a[contains(text(),'click here')]");
        utility.safeClick(driver,random);

        By portfolio = By.xpath("//a[normalize-space()='Portfolio']");
        utility.safeClick(driver,portfolio);
        driver.navigate().back();

        By gallery = By.xpath("//a[@class='shift']");
        utility.safeClick(driver,gallery);
        driver.navigate().back();

        driver.get("https://the-internet.herokuapp.com/shifting_content");

        By anImage = By.xpath("//a[normalize-space()='Example 1: Menu Element']");
        utility.safeClick(driver,anImage);

        By image = By.xpath("//img[@class='shift']");
        utility.checkElement(driver,image,1000);

        By randomPlace = By.xpath("//p[contains(text(),'To specify a differant numbor of pixels to shift t')]//a[contains(text(),'click here')]");
        utility.safeClick(driver,randomPlace);

        utility.pause(3000);
        utility.checkElement(driver,image,1000);

        driver.get("https://the-internet.herokuapp.com/shifting_content");

        By listShift = By.xpath("//a[normalize-space()='Example 3: List']");
        utility.safeClick(driver,listShift);

        By listText = By.xpath("//*[@id=\"content\"]/div/div/div");
        utility.getElementTextSafe(driver,listText,"Message Not Found");

        driver.navigate().refresh();
        utility.pause(3000);

        utility.getElementTextSafe(driver,listText,"Message Not Found");
    }

    @Test
    public void dataTable(){
        By tableTest = By.xpath("//a[normalize-space()='Sortable Data Tables']");
        utility.findElement(driver,tableTest,"center");
        utility.safeClick(driver,tableTest);

        WebElement emailAssert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"table1\"]/tbody/tr[1]/td[3]")));
        String emailText = emailAssert.getText();
        System.out.println("Email ditemukan: " + emailText);
        assertEquals("jsmith@gmail.com", emailText);
    }

    @Test
    public void statusTest(){
        By status = By.xpath("//a[normalize-space()='Status Codes']");
        utility.findElement(driver,status,"center");
        utility.safeClick(driver,status);

        utility.validate(driver);

    }
    @AfterClass
    public static void tearDownAfterClass() {
    driverFactory.quitDriver();
    }
}

