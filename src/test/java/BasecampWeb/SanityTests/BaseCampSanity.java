package BasecampWeb.SanityTests;

import Extensions.Web.Verifications;
import Utilities.CommonOps;
import Utilities.HelperMethods;
import Utilities.Listeners;
import WorkFlows.WebFlows;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import static Utilities.HelperMethods.getDataFromXML;

@org.testng.annotations.Listeners(Listeners.class)
public class BaseCampSanity extends CommonOps {
    @Test(description = "Login Sanity Test")
    @Description("Test description: Logging in with a user")
    public void testLogin(){
        WebFlows.signIn(getDataFromXML("UserEmail"),getDataFromXML("Password"));
        Verifications.verifyTextInElement(basecampMainPage.pageTitle_txt, basecampMainPage.pageTitle_txt.getText(), "Your Projects");
    }

    @Test(description = "SignUp Sanity Test", enabled = false)
    @Description("Test description: Signing up a new user")
    public void testSignup() throws InterruptedException {
        WebFlows.signUp(HelperMethods.returnRandomFullName(),HelperMethods.randomEmailGenerator(),HelperMethods.returnRandomPassword(),HelperMethods.returnRandomName());
        Verifications.verifyTextInElement(basecampSignUpFlow.finishAccountCreationWindowTitle_txt, basecampSignUpFlow.finishAccountCreationWindowTitle_txt.getText(), "Your account’s ready to go! 👍" );
        basecampSignUpFlow.noThanks_btn.click();
        Verifications.verifyTextInElement(basecampSignUpFlow.userNameTitle_txt, basecampSignUpFlow.userNameTitle_txt.getText(), WebFlows._userName);
    }

    @Test(description = "Add new project")
    @Description("Test description: Adding a new project")
    public void testAddNewProject() throws InterruptedException {
        int _numberOfProjectsBeforeAdding = HelperMethods.numberOfProjectsNow();
        if (_numberOfProjectsBeforeAdding == 3){
            System.out.println("Cannot Add Anymore Projects Under This App Package");
        }else {
            WebFlows.addNewProject(HelperMethods.returnRandomName() + HelperMethods.returnRandomDate(), HelperMethods.returnRandomFullName());
            Verifications.verifyNumberOfElementsProjectAdd(basecampMainPage.projects_list, _numberOfProjectsBeforeAdding);
        }
    }

    @Test(description = "Remove a project")
    @Description("Test description: Removing a project")
    public void testRemoveAProject() throws InterruptedException {
        int _numberOfProjectsBeforeRemoving = HelperMethods.numberOfProjectsNow();
        if(_numberOfProjectsBeforeRemoving == 0){
            System.out.println("No Projects To Delete");
        }else {
            WebFlows.removeAProject();
            Verifications.verifyNumberOfElementsProjectRemove(basecampMainPage.projects_list, _numberOfProjectsBeforeRemoving);
        }
    }

}
