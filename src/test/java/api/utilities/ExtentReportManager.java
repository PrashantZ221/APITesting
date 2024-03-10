package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.poi.hpsf.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter; //Responsible for look and feel of the report
    public ExtentReports extent;  // To show the common information
    public ExtentTest test;   //Creating entries of test case - pass, fail, skip
    String repName;

    public void onStart(ITestContext testContext) {
        //String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        //repName="Test-Report"+timestamp+".html";

        sparkReporter= new ExtentSparkReporter("Report1");

        sparkReporter.config().setDocumentTitle("RestAssuredProject");
        sparkReporter.config().setReportName("Pet store Users API");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Pet store User API");
        extent.setSystemInfo("Operating system", System.getProperty("os.name"));
        extent.setSystemInfo("User name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("user", "pavan");
    }

    public void onTestSuccess(ITestResult result) {
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult result) {
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
