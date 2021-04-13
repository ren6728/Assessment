package page_objects_test;

import base.BrowserDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page_objects.HomePage;
import page_objects.LoginPage;
import reader.MyDataReader;

public class LoginPageTest extends BrowserDriver {

    LoginPage objLoginPage = null;
    HomePage objHomePage = null;


    @BeforeMethod
    public void initializationOfElements() {

        objLoginPage= PageFactory.initElements(driver, LoginPage.class);
        objHomePage = PageFactory.initElements(driver, HomePage.class);
    }

    @DataProvider
    public Object[][] readData() throws Exception {
        String sheetName = null;
        MyDataReader myDataReader = new MyDataReader();
        String path = System.getProperty("user.dir") + "/testData/TestData.xlsx";
        myDataReader.setExcelFile(path);
        String environment = System.getProperty("env", "QA");

        if(environment.equals("QA")){
            sheetName="Sheet3";}
        else if(environment.equals("PROD")){
            sheetName="Sheet1";
        }

        Object[][] data = myDataReader.getExcelSheetData(sheetName);
       return data;
    }

    @Test(dataProvider ="readData")
    public void loginTest(String e, String p, String em){
        // Go to log in page
        objHomePage.goToLogInPage();
        // Send email and password, click submit
        objLoginPage.login(e, p);
        // Get error message
       String actualtext = objLoginPage.getErroMessage();

       /*
       * Assert error message . Hard Assertion --> If test fails it will stop any further process
       * Soft assertion --> It will not stop further executions due to assertion failure
       * Discover how to make soft assertion
       * */
       Assert.assertEquals(actualtext, em);
    }
}
