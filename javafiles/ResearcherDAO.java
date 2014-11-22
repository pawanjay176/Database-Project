package jdbc;

public interface ResearcherDAO {
	public Researcher getresearcherByKey(int studentId);
	public void updateProjectKey(int studentId,int projectId,String key);
	public void searchProj(String key);
	//public void addresearcher(researcher res);
	// public void updateStudent(Student student);
	// public void deleteStudent(Student student);
	
}

