package jym.manager.model.task;

import java.time.LocalDateTime;
import java.util.Objects;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.commons.util.CollectionUtil;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.Deadline;
import jym.manager.model.task.Description;
import jym.manager.model.task.Location;
import jym.manager.model.task.Priority;

/**
 * Represents a task in the JYM program.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task extends TaskManagerItem implements ReadOnlyTask {

	private Description descr;
	private Location loc;
	private Deadline dueDate;
	private Priority pri;
	private Complete compl;

    private UniqueTagList tags;

    
	public Task(Description description, Object ... objects) throws IllegalValueException{
		assert !CollectionUtil.isAnyNull(description, objects);
		this.descr = description;
		this.loc = new Location();
		this.dueDate = new Deadline();
		this.pri = new Priority(0);
		for(int i = 0; i < objects.length; i++){
    		Object o = objects[i];
    		if(o instanceof String){
    			this.loc = new Location((String)o);
    		} else if(o instanceof Location){ 
    			this.loc = (Location)o;
    		} else if(o instanceof LocalDateTime){
    			this.dueDate = new Deadline((LocalDateTime)o);
    		} else if(o instanceof Deadline){ 
    			this.dueDate = (Deadline)o;
    		} else if(o instanceof Priority){
    			this.pri = (Priority)o;
    		} else if(o instanceof Integer){
    			this.pri = new Priority((Integer)o);
    		} else if(o instanceof UniqueTagList){
    			this.tags = new UniqueTagList((UniqueTagList)o);
    		}
    	}
	}
	public Task(Description d, Deadline due, Location location){
		this.descr = d;
		this.dueDate = due;
		this.loc = location;
	
	}
    /**
     * Every field must be present and not null.
     */

    public Task(Description description, Location location, Deadline due, Priority p, UniqueTagList tags) {
    //	assert !CollectionUtil.isAnyNull(description, location, due);
    	this.descr = description;
    	this.loc = (location == null)? new Location():location;
    	this.dueDate = (due == null) ? new Deadline():due;
    	this.pri = p;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getLocation(), source.getDate(), source.getPriority(), source.getTags());
    }
   

    public Task(Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.descr = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.loc = new Location();
        this.dueDate = new Deadline();
        this.pri = new Priority();
    }

    @Override
    public Description getDescription() {
        return this.descr;
    }

    @Override
    public Location getLocation() {
        return this.loc;
    }

	@Override
	public Deadline getDate() {
		return this.dueDate;
	}

	@Override
	public Priority getPriority() {
		return this.pri;
	}

    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.descr, this.loc, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public boolean hasDeadline(){
    	return this.dueDate != null && this.dueDate.hasDeadline();
    }
	@Override
	public Complete getComplete() {
		return this.compl;
	}




}
