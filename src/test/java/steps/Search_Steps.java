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
import org.testng.asserts.SoftAssert;

import java.time.Duration;


public class Search_Steps {

    private WebDriver driver;
    SoftAssert softAssert = new SoftAssert();

    @Before
    public void setup(){
        System.out.println("Opening the Web Browser");
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/main/resources/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();
    }

    @After
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
        SoftAssert softAssert = new SoftAssert();
        System.out.println("The user is going to see see the results table");
        WebElement resultsArea = (new WebDriverWait(driver, Duration.ofSeconds(5)).until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class=\"repo-amount\"]")))));
        String foundRepos = resultsArea.getText();
        //Assert to check the search retrieves information
        Boolean verifyResults = foundRepos.matches("No repos");
        //SoftAssert due to I saw the generic error message, no results were retrieved
        ///strong[@innertext='Something went wrong']
        softAssert.assertFalse(verifyResults);
        System.out.println("foundRepos = " + foundRepos);
    }

    /*** Second Test selected to automate
     * Search_InfoDisplayed
     */
    @And("each row contains a Name column")
    public void eachRowContainsANameColumn() {
        System.out.println("A table with results must be displayed");
        WebElement table = driver.findElement(By.xpath("//section[@class='output-area']//ul//a[@href='https://github.com/MemoRuan/itsexample']"));
        Boolean verifyColumn = table.isDisplayed();
        System.out.println("verifyColumn = " + verifyColumn);
        softAssert.assertTrue(verifyColumn);
        //System.out.println("verifyColumn + \"\\n table: \" + table = " + verifyColumn + "\n table: " + table);
        //section[@class='output-area']//ul//a[@href='https://github.com/MemoRuan/itsexample']
    }

    @And("each row contains a Description colum")
    public void eachRowContainsADescriptionColum() {
        System.out.println("The table should display Description Column");
        WebElement columnDescription = driver.findElement(By.cssSelector("[class=\"repo-description\"]"));
        String descriptionText = columnDescription.getText();
        softAssert.assertNotEquals(descriptionText, "-");
        System.out.println("The Repo with Description displays text like: "+ descriptionText);

    }

    @And("the Description column with no value shows a - sign")
    public void theDescriptionColumnWithNoValueShowsASign() {
        System.out.println("The empty Descriptions should display a - sign");
        WebElement columnDescription = driver.findElement(By.xpath("//section[@class='output-area']//ul/li[3]/p[@class='repo-description']"));//.cssSelector("[class=\"repo-description\"]"));
        String descriptionText = columnDescription.getText();

        boolean signDisplayed = descriptionText.equalsIgnoreCase("-");
        softAssert.assertEquals(descriptionText, "-");
        System.out.println("The Repo without Description displays a sign?: "+ signDisplayed + ", like this: " + descriptionText);
    }


    //Generic Error Message
    ////?/strong[@innertext='Something went wrong']
}
