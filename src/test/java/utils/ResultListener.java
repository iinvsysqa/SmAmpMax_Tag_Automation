package utils;
import org.testng.*;
import java.io.*;
import java.util.*;

public class ResultListener implements ITestListener {
    @Override
    public void onFinish(ITestContext context) {
        File file = new File("test-results.txt");
        try (PrintWriter out = new PrintWriter(file)) {
            Collection<ITestResult> passed = context.getPassedTests().getAllResults();
            Collection<ITestResult> failed = context.getFailedTests().getAllResults();
            Collection<ITestResult> skipped = context.getSkippedTests().getAllResults();

            out.println("PASSED_COUNT=" + passed.size());
            passed.forEach(r -> out.println("PASS:" + r.getMethod().getMethodName()));

            out.println("FAILED_COUNT=" + failed.size());
            failed.forEach(r -> out.println("FAIL:" + r.getMethod().getMethodName()));

            out.println("SKIPPED_COUNT=" + skipped.size());
            skipped.forEach(r -> out.println("SKIP:" + r.getMethod().getMethodName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
