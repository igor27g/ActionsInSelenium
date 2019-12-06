import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActionPractice {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;


    @BeforeEach
    public void driverSetup()
    {
        String fakeStore = "https://fakestore.testelka.pl/";
        String fakeStoreWindsurfing = "https://fakestore.testelka.pl/product-category/windsurfing/";
        String fakeStoreKonto = "https://fakestore.testelka.pl/moje-konto/";
        String blog = "https://testelka.pl/blog/";
        String allegro = "https://allegro.pl/";

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        driver.manage().window().maximize();

       actions = new Actions(driver);
    }

    @AfterEach
    public void driverClose()
    {
        driver.close();
        driver.quit();
    }


    @Test
    public void clickExample() {

        driver.navigate().to("https://jqueryui.com/selectable/#default");

        //actions.moveByOffset(488,300).click().build().perform();

        driver.switchTo().frame(0);
        List<WebElement> listElements = driver.findElements(By.cssSelector("#selectable>li"));
        WebElement firstElement = listElements.get(0);
        actions.click(firstElement).build().perform();
    }

    @Test
    public void doubleClickExample() {
        driver.navigate().to("https://www.plus2net.com/javascript_tutorial/ondblclick-demo.php");
        //actions.moveByOffset(330,173).doubleClick().build().perform();

        WebElement box = driver.findElement(By.cssSelector("box"));
        actions.doubleClick(box).build().perform();
    }

    @Test
    public void doubleClickMyExample() {
        driver.navigate().to("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_ev_ondblclick");
        WebElement iframeId = driver.findElement(By.cssSelector("#iframeResult"));
        driver.switchTo().frame(iframeId);
        WebElement buttonDoubleClickMe = driver.findElement(By.cssSelector("button[ondblclick='myFunction()']"));
        actions.doubleClick(buttonDoubleClickMe).build().perform();
    }

    @Test
    public void contextClickExample() {
        driver.navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        WebElement editOption = driver.findElement(By.cssSelector(".context-menu-icon-edit"));
        WebElement button = driver.findElement(By.cssSelector(".context-menu-one"));
        actions.contextClick(button).click(editOption).build().perform();
    }



    @Test
    public void sendKeysExample() {
        driver.navigate().to("https://fakestore.testelka.pl/moje-konto/");
        WebElement login = driver.findElement(By.cssSelector("#username"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", login);  // scrolluje do tego elementu
         // actions.sendKeys(login, "Testowy user").build().perform();                          // teraz, dopiero widzi i może wpisać
        //actions.sendKeys(login,Keys.SHIFT,"testowy user").build().perform();                  // równorzędne
        actions.click(login).sendKeys(Keys.SHIFT, "testowy user").build().perform();            // równorzędne
    }

    @Test
    public void sendKeysMyExample() {
        driver.navigate().to("https://fakestore.testelka.pl/moje-konto/");
        WebElement password = driver.findElement(By.cssSelector("#reg_password"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", password);
        actions.click(password).sendKeys("test hasło").build().perform();
    }

    @Test
    public void pressingKeysExample() {
        driver.navigate().to("https://jqueryui.com/selectable/#default");
        driver.switchTo().frame(0);
        List<WebElement> listItems = driver.findElements(By.cssSelector("li.ui-selectee"));

        //actions.keyDown(Keys.CONTROL).click(listItems.get(0)).click(listItems.get(1)).keyUp(Keys.CONTROL).build().perform();
        //actions.keyDown(Keys.CONTROL).click(listItems.get(0)).click(listItems.get(1)).keyUp(Keys.CONTROL).click(listItems.get(3)).build().perform();

        actions.keyDown(Keys.CONTROL).click(listItems.get(3)).click(listItems.get(4)).keyUp(Keys.CONTROL).build().perform();
    }


    //Potwierdź, że w punkcie numer 2, wybór opcji „Koszyk” z menu kontekstowego przenosi nas na stronę koszyka.
    @Test
    public void goToCart() {
        driver.navigate().to("https://fakestore.testelka.pl/actions/#");
        WebElement dropdownMenu = driver.findElement(By.cssSelector("#menu-link"));
        WebElement menuCart = driver.findElement(By.cssSelector(".menu-cart"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdownMenu);
        actions.contextClick(dropdownMenu).click(menuCart).build().perform();
        Assertions.assertEquals("https://fakestore.testelka.pl/koszyk/", driver.getCurrentUrl(), "Wrong url website");
    }

    //Potwierdź, że w punkcie numer 3, podwójny klik na prostokącie zmieni jego kolor na #f55d7a (do konwersji z hex na rgba możesz użyć tego narzędzia).
    @Test
    public void doubleClickDiv() {
        driver.navigate().to("https://fakestore.testelka.pl/actions/#");
        WebElement yellowDiv = driver.findElement(By.cssSelector("#double-click"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", yellowDiv);
        actions.doubleClick(yellowDiv).build().perform();
        Assertions.assertEquals("rgba(245, 93, 122, 1)", yellowDiv.getCssValue("background-color"), "Wrong color of div");
    }

    //Potwierdź, że w punkcie numer 5, po wprowadzeniu i zatwierdzeniu tekstu poniżej przycisku „Zatwierdź” zostanie wyświetlona informacja
    // „Wprowadzony tekst: ” plus Twój tekst.
    @Test
    public void confirmText() {
        driver.navigate().to("https://fakestore.testelka.pl/actions/#");
        WebElement input = driver.findElement(By.cssSelector("#input"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        String testTekst = "Test";
        actions.sendKeys(input, testTekst).build().perform();
        WebElement button = driver.findElement(By.cssSelector("[onclick='zatwierdzTekst()']"));
        actions.click(button).build().perform();
        WebElement output = driver.findElement(By.cssSelector("#output"));
        Assertions.assertEquals("Wprowadzony tekst: " + testTekst, output.getText(), "Text output is not correct");
    }

    //Potwierdź, że w punkcie numer 6, zaznaczanie kwadratów z liczbami ze wciśniętym przyciskiem CTRL nie powoduje odznaczania już zaznaczonych (czyli jeżeli przytrzymamy CTRL i
    // wybierzemy 1, 5 i 7 to wszystkie trzy kafelki będą zaznaczone).

    @Test
    public  void numbers() {
        driver.navigate().to("https://fakestore.testelka.pl/actions/#");
        WebElement list1 = driver.findElement(By.cssSelector("#selectable"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", list1);
        List<WebElement>  listNumbers = driver.findElements(By.cssSelector(".ui-state-default ui-selectee"));
        WebElement n1 = listNumbers.get(0);
        WebElement n5 = listNumbers.get(4);
        WebElement n7 = listNumbers.get(6);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listNumbers);

        actions.keyDown(Keys.CONTROL).click(n1).click(n5).click(n7).keyUp(Keys.CONTROL).build().perform();

        Assertions.assertAll(
                ()-> Assertions.assertTrue(n1.getAttribute("class").contains("ui-selected"), "Item with id=3 was not seletec"),
                ()-> Assertions.assertTrue(n5.getAttribute("class").contains("ui-selected"), "Item with id=5 was not seletec"),
                ()-> Assertions.assertTrue(n7.getAttribute("class").contains("ui-selected"), "Item with id=7 was not seletec")
        );


    }


}






