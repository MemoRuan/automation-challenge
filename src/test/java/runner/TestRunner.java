package runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Test runner enables the execution via cmd command
@CucumberOptions(features = {"src/test/java/features"}, glue = "steps")
public class TestRunner extends AbstractTestNGCucumberTests {

}
