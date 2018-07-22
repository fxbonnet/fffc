package com.octo.au;

import java.util.Date;

import org.perf4j.StopWatch;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Calculates the execution timers for the test suite
 * @author Amol Kshirsagar
 */
public class TestExecutionListener implements ITestListener {

	//private static final Logger logger = LoggerFactory.getLogger(TestExecutionListener.class);

	private StopWatch stopWatch = new StopWatch();

	@Override
	public void onTestStart(ITestResult result) {
		result.getMethod().getMethodName();
		String mthName = result.getMethod().getMethodName();
		stopWatch.start();
		System.out.println("------------------------------------------------------");
		System.out.println("Test: {} START on {}"+" "+ mthName +" "+ new Date(stopWatch.getStartTime()));
		System.out.println("------------------------------------------------------");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		stopWatch.stop();
		String mthName = result.getMethod().getMethodName();
		System.out.println("------------------------------------------------------");
		System.out.println("Test: {} FINISH, EXECUTION TIME {} sec "+" "+ mthName+" "+ stopWatch.getElapsedTime() / 1000.);
		System.out.println("------------------------------------------------------");
	}

	@Override
	public void onTestFailure(ITestResult result) {
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}

}
