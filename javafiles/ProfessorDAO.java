package jdbc;

public interface ProfessorDAO {
	public Researcher getresearcherByKey(int studentId);
	public void updateProjectKey(int studentId,int projectId,String key);
	public void searchProj(String key);
	public void addResearcher(Researcher res);
	// public void updateStudent(Student student);
	public void deleteResearcher(Researcher res);

}
