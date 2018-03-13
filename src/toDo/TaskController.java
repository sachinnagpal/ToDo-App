package toDo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.text.SimpleDateFormat;

@Path("/task")
public class TaskController {

	@GET
	@Path("read")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Task> readTask() throws Exception {
		ArrayList<Task> data = new ArrayList<Task>();
		Connection con = getConnection();
		PreparedStatement statement = con.prepareStatement("Select * from TaskData");
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			Task task = new Task();
			Date doneDate = null;
			java.sql.Date startDate = null;
			java.sql.Date createdDate = result.getDate("createdAt");
			Date createdAt = new Date(createdDate.getTime());
			if (result.getDate("doneAt") != null) {
				startDate = result.getDate("doneAt");
				doneDate = new Date(startDate.getTime());
			}
			task.setTaskName(result.getString("taskName"));
			task.setDone(result.getBoolean("isDone"));
			task.setDoneAt(doneDate);
			task.setTaskId(result.getString("taskId"));
			task.setCreatedAt(createdAt);
			data.add(task);
		}
		return data;
	}

	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Task createTask(TaskCreate data) throws Exception {
		getConnection();
		createTable();
		return addTaskData(data);
	}

	@POST
	@Path("{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Task editTask(@PathParam("taskId") String taskId, TaskCreate data) throws Exception {
		//getConnection();
		// createTable();
		try {
			Date date = null;
			java.sql.Date startDate = null;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

			if (data.getDoneAt() != null && !data.getDoneAt().isEmpty()) {

				date = simpleDateFormat.parse(data.getDoneAt());
				startDate = new java.sql.Date(date.getTime());
			}
			Connection con = getConnection();
			String query = "UPDATE TaskData SET taskName = ? , isDone = ? , doneAt = ? WHERE taskId = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, data.getTaskName());
			preparedStmt.setBoolean(2, data.getIsDone());
			preparedStmt.setDate(3, startDate);
			preparedStmt.setString(4, taskId);
			preparedStmt.execute();

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Error while updating data");
			return null;
		}
		return fetchTaskByTaskId(taskId);

	}

	public Task addTaskData(TaskCreate data) throws Exception {
		Date date = null;
		java.sql.Date startDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		if (data.getDoneAt() != null && !data.getDoneAt().isEmpty()) {
			System.out.println(data.getDoneAt());
			date = simpleDateFormat.parse(data.getDoneAt());
			startDate = new java.sql.Date(date.getTime());
		}
		String taskId = UUID.randomUUID().toString();
		try {
			Connection con = getConnection();
			String query = "INSERT INTO TaskData (taskName , isDone, doneAt, taskId, createdAt) VALUES"
					+ " (?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, data.getTaskName());
			preparedStmt.setBoolean(2, data.getIsDone());
			preparedStmt.setDate(3, startDate);
			preparedStmt.setString(4, taskId);
			preparedStmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			preparedStmt.execute();
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception("Error while inserting data. Please check your data.");
		}
		return fetchTaskByTaskId(taskId);
	}

	public Task fetchTaskByTaskId(String taskId) throws Exception {
		Connection con = getConnection();
		PreparedStatement preparedStmt = con.prepareStatement("select * from TaskData where taskId='" + taskId + "'");
		// preparedStmt.setString(1, taskId);
		// ResultSet resultSet = preparedStmt.getResultSet();
		ResultSet resultSet = preparedStmt.executeQuery();
		if (resultSet == null) {
			return null;
		}
		resultSet.next();
		Date date = null;
		java.sql.Date startDate = null;
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (resultSet.getDate("doneAt") != null) {
			startDate = resultSet.getDate("doneAt");
			date = new Date(startDate.getTime());
			// startDate = new java.sql.Date(date.getTime());
		}
		Task task = new Task(resultSet.getString("taskId"), resultSet.getString("taskName"),
				resultSet.getBoolean("isDone"), date, new Date(resultSet.getDate("createdAt").getTime()));
		return task;
	}

	public static Connection getConnection() throws Exception {
		try {
			String Driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/test";
			Class.forName(Driver);
			Connection conn = DriverManager.getConnection(url, "root", "");
			System.out.println("Connected");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public static void createTable() throws Exception {
		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement(
					"CREATE TABLE IF NOT EXISTS TaskData(Task_ID int NOT NULL AUTO_INCREMENT, taskName varchar(255), isDone tinyint (1),createdAT datetime ,doneAT datetime, taskId varchar(50), PRIMARY KEY(Task_ID))");
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} 
	}

}
