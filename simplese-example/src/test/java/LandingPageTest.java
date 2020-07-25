import com.elementer.example.AccountPage;
import com.elementer.example.LandingPage;
import com.elementer.example.LoginPage;
import com.rationaleemotions.page.Link;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LandingPageTest {

    private RemoteWebDriver driver;
    private LandingPage landingPage;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/vimal/tools/selenium-drivers/chromedriver");
        driver = new ChromeDriver();

        driver.get("https://www.phptravels.net/en");
    }

    @Test
    public void testUserOnLandingPage() {
        landingPage = new LandingPage(driver);
        Assert.assertEquals(landingPage.getLatestonblogstitle().getText(), "Latest On Blogs");
        Link myAccountLink = landingPage.getMyaccountlinks().get(1);
        Assert.assertEquals(myAccountLink.getUnderlyingElement().getText().trim().toLowerCase(), "my account");
    }

    @Test(dependsOnMethods = {"testUserOnLandingPage"})
    public void testLoginPage() {
        Link myAccountLink = landingPage.getMyaccountlinks().get(1);
        myAccountLink.click();

        landingPage.getLoginlink().click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.phptravels.net/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.getUsernametextbox().type("user@phptravels.com");
        loginPage.getPasswordtextbox().type("demouser");
        loginPage.getLoginbutton().click();

//        Assert.assertEquals(driver.getCurrentUrl(), "https://www.phptravels.net/account/");
        AccountPage accountPage = new AccountPage(driver);
        Assert.assertEquals(accountPage.getWelcomeuserlabel().getText().trim().toLowerCase(), "hi, demo user");
        Assert.assertEquals(landingPage.getMyaccountlinks().get(1).getUnderlyingElement().getText().trim().toLowerCase(), "demo");
    }

//    @Test(dependsOnMethods = {"testLoginPage"})
//    public void testAccountPage() {
//    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
