package com.udacity.gradle.builditbigger;

/**
 * Created by Moha on 12/13/2017.
 */

// THIS test is based on disscustion post in the disscustion forum based
// on jeremy answer and some other students

// link to the post https://discussions.udacity.com/t/async-task-test-where-to-even-begin/159593/21
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by Moha on 12/12/2017.
 */

public class AsynckTaskTest extends AndroidTestCase {

    EndpointsAsyncTask task;
    String result;
    MockContext mockContext = new MockContext();
   // Context mockContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        result = null;
        task = new EndpointsAsyncTask() {
            @Override
            protected void onPostExecute(String joke) {
            }
        };
    }

    public void testAsyncReturnType() {

        try {

            //Default timeout for the GCM server is 20 seconds
            //If the .get can't get the result in 10 seconds, something is wrong anyway
            //Greater than 20 seconds results in an error string returned and requires further interpretation
            task.execute( mockContext);
            result = task.get(20, TimeUnit.SECONDS);
            assertNotNull(result);

        } catch (Exception e) {
            fail("Timed out");
        }
    }
}