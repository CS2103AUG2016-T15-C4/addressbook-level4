package guitests;

import jym.manager.commons.core.Messages;
import jym.manager.testutil.TestTask;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find homework", td.doHomework, td.writeProgram); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find write program",td.writeProgram);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find do Homework"); //no results
    }

//    @Test
//    public void find_invalidCommand_fail() {
//        commandBox.runCommand("findgeorge");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
