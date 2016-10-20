package jym.manager.model;

import java.util.Set;

import jym.manager.commons.core.UnmodifiableObservableList;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.Task;
import jym.manager.model.task.UniqueTaskList;
import jym.manager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Mark completes the given task. */
    void completeTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;    

    /** Updates the given task */
	void updateTask(ReadOnlyTask target, Task updatedTask) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);


}