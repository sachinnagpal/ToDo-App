package toDo;

public class TaskCreate {

	private String taskName;
	
	private boolean isDone;
	
	private String doneAt;

	public TaskCreate() {}
	
	public TaskCreate(String taskName, boolean isDone, String doneAt) {
		super();
		this.taskName = taskName;
		this.isDone = isDone;
		this.doneAt = doneAt;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	public String getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(String doneAt) {
		this.doneAt = doneAt;
	}	
}
