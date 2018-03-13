package toDo;

import java.util.Date;

public class Task {

	private String taskId;
	
	private String taskName;
	
	private boolean isDone;
	
	private Date doneAt;
	
	private Date createdAt;

	public Task() {}
	

	public Task(String taskId, String taskName, boolean isDone, Date doneAt, Date createdAt) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.isDone = isDone;
		this.doneAt = doneAt;
		this.createdAt = createdAt;
	}



	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public Date getDoneAt() {
		return doneAt;
	}

	public void setDoneAt(Date doneAt) {
		this.doneAt = doneAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}

