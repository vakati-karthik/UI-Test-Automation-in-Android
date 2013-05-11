package edu.sjsu.cs185c.hw02.test;

//Import the uiautomator libraries
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class SimpleTestCase extends UiAutomatorTestCase {   

	public void testDemo() throws UiObjectNotFoundException {   
	   
	   // Simulate a short press on the HOME button.
	   getUiDevice().pressHome();
	   
	   // We're now in the home screen. Next, we want to simulate 
	   // a user bringing up the Homework 2 screen.
	   // If you use the uiautomatorviewer tool to capture a snapshot 
	   // of the Home screen, notice that the Homework 2 button's 
	   // text property that has the Question Tag.  We can 
	   // use this property to create a UiSelector to find the button. 
	   UiObject Homework2 = new UiObject(new UiSelector()
	      .text("Homework 2"));
	   
	   // Simulate a click to bring up the Homework 2 screen.
	   Homework2.click();
	   sleep(5000);
	   
	   // In the Homework 2 screen, select a question. In this case it's Question 4 with text "What is "detached mode" in Eclipse?".
	  /* UiObject detachedMode = new UiObject(new UiSelector()
	   .className("android.widget.ListView").instance(1)
	   .childSelector(new UiSelector().text("What is \"detached mode\" in Eclipse?")));*/
	   UiObject eclipse = new UiObject(new UiSelector()
	      .text("What does the author least like about Eclipse?"));
	   
	   // Simulate a click to enter the choices screen.
	   // Check whether the options are correct.
	   if (eclipse.exists()) {
		   eclipse.click();
		   sleep(5000);
	   }
	   assertTrue(new UiObject(new UiSelector()
	   .text("What does the author least like about Eclipse?")).exists());
	
	   // Next, select an option for Question 4. In this case it's Option 2 with text "A view in a separate window."
	   /*UiObject option2 = new UiObject(new UiSelector()
	   .className("android.widget.ListView").instance(1)
	   .childSelector(new UiSelector().text("What is \"detached mode\" in Eclipse?")));*/
	   UiObject option2 = new UiObject(new UiSelector()
	   .text("The Run As menu."));
	   
	   // Check if these are the options for the question from the title.
	   // Click on option 2 and wait for the HomeScreen again
	   if (option2.exists())
		   option2.clickAndWaitForNewWindow();   
	}   
}
