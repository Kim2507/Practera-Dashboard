package dashboard;

import java.util.HashSet;
import java.util.Set;

public class Team {
    public static String coordinatorID;
    private String mentorID;
    private String teamID;
    private Set<Student> students;
    public Team(String teamID) {
        setStudents(new HashSet<>());
        setTeamID(teamID);
    }
    public Team(String teamID, Set<Student> students) {
        setTeamID(teamID);
        setStudents(students);
    }
    public void setTeamID(String teamID) {
        this.teamID=teamID;
    }
    public void setStudents(Set<Student> students) {
        this.students=students;
    }
    public void setMentorID(String mentorID) {
        this.mentorID=mentorID;
    }
    public String getMentorID() {
        return mentorID;
    }
    public String getTeamID() {
        return teamID;
    }
    public Set<Student> getStudents() {
        return students;
    }
}
