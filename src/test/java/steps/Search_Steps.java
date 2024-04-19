package steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import runner.TestRunner;
import java.time.Duration;


public class Search_UsingGo{

    private WebDriver driver;

    @Before
    public void setup(){
        System.out.println("Opening the Web Browser");
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/main/resources/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();

        //return;
    }

    //@After
    public void tearDown(){
        driver.quit();
    }


    @Given("the user opens the app")
    public void theUserOpensTheApp() throws InterruptedException {
        System.out.println("Launching the WebBrowser");
        driver.get("http://localhost:3000");
        Thread.sleep(5000);
        System.out.println("WebBrowser Launched");

    }

    @When("the application finish loading")
    public void theApplicationFinishLoading() {
        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(10)).until((ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")))));
        String header = element.getText();
        Assert.assertEquals(header, "Get Github Repos");
        System.out.println("WebApp fully Loaded, header's title: " + header);
    }

    @And("the user enters {string} in the input text field")
    public void theUserEntersInTheInputTextField(String repo) {
        System.out.println("The user is going to enter the string in the text field");
        WebElement inputTextField = driver.findElement(By.xpath("//input[@id='username']"));
        inputTextField.sendKeys(repo);
        System.out.println("This is the name entered: " + repo);
    }

    @And("the user clicks Go button")
    public void theUserClicksGoButton() {
        System.out.println("The user is going to click Go button");
        WebElement goButton = driver.findElement(By.xpath("//button[@class='submit']"));
        goButton.click();
        System.out.println("Go button clicked");
    }

    @Then("the repo list is displayed in the results section")
    public void theRepoListIsDisplayedInTheResultsSection() throws InterruptedException {
        System.out.println("The user is going to see see the results table");
        WebElement resultsArea = (new WebDriverWait(driver, Duration.ofSeconds(10)).until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"repo-amount\"]")))));
        String foundRepos = resultsArea.getText();
        Boolean verifyResults = foundRepos.matches("No repos");
        Assert.assertFalse(verifyResults);
        System.out.println("foundRepos = " + foundRepos);

    }
}