package guitests;

import guitests.guihandles.*;
import javafx.stage.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import java.util.concurrent.TimeoutException;

import jym.manager.TestApp;
import jym.manager.commons.core.EventsCenter;
import jym.manager.model.TaskManager;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.testutil.TestUtil;
import jym.manager.testutil.TypicalTestTasks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A GUI Test class for AddressBook.
 */
public abstract class TaskManagerGuiTest {

    /* The TestName Rule makes the current test name available inside test methods */
    @Rule
    public TestName name = new TestName();

    TestApp testApp;

    protected TypicalTestTasks td = new TypicalTestTasks();

    /*
     *   Handles to GUI elements present at the start up are created in advance
     *   for easy access from child classes.
     */
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected TaskListPanelHandle taskListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    private Stage stage;
    protected CompleteTaskListPanelHandle completeTaskListPanel;

    @BeforeClass
    public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            taskListPanel = mainGui.getTaskListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
            completeTaskListPanel = mainGui.getCompleteTaskListPanel();
        });
        EventsCenter.clearSubscribers();
        testApp = (TestApp) FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
     * Override this in child classes to set the initial local data.
     * Return null to use the data in the file specified in {@link #getDataFileLocation()}
     */
    protected TaskManager getInitialData() {
        TaskManager ab = TestUtil.generateEmptyAddressBook();
        TypicalTestTasks.loadAddressBookWithSampleData(ab);
        return ab;
    }

    /**
     * Override this in child classes to set the data file location.
     * @return
     */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
    public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Asserts the task shown in the card is same as the given task
     */
    public void assertMatching(ReadOnlyTask task, TaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
     * Asserts the size of the task list is equal to the given number.
     */
    protected void assertListSize(int size) {
        int numberOfPeople = taskListPanel.getNumberOfPeople();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in the Result Display area is same as the given string.
     * @param expected
     */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }
    
    /**
     * Asserts the size of the complete task list is equal to the given number.
     */
    protected void assertCompleteListSize(int size) {
        int numberOfTask = completeTaskListPanel.getNumberOfTask();
        assertEquals(size, numberOfTask);
    }
}
