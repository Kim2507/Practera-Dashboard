package dashboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Student {
    protected static Set<String> assessmentIDs;
    private double progress;
    private String studentID;
    private String programID;
    private String experienceID;
    private String teamID;
    private String onTrack;
    private String necessaryResource;
    private String engagedSupervisor;
    public Student(String studentID, String programID, String experienceID, String teamID) throws FileNotFoundException {
        setTeamID(teamID);
        setStudentID(studentID);
        setProgramID(programID);
        setExperienceID(experienceID);
        setAssessmentIDS();
        setProgress();
        setCheckIn();
    }
    public void setTeamID(String teamID) { this.teamID=teamID;}
    public void setStudentID(String studentID) {
        this.studentID=studentID;
    }
    public void setProgramID(String programID) {
        this.programID=programID;
    }
    public void setExperienceID(String experienceID) {
        this.experienceID=experienceID;
    }
    private void setCheckIn() throws FileNotFoundException {
        List<String[]> data = new ArrayList<>();
        Scanner file = new Scanner(new File("src/data/Slider_data.csv"));
        while (file.hasNextLine()) {
            data.add(file.nextLine().split(","));
        }
        // index 10 is the questions
        // index 9 is role
        // index 4 is user_id
        // index 6 is answer: 1 for yes, 0 for no
        // Intern.stage: Do you have everything you need to complete your work?
        // Intern.industry: Have you engaged with your supervisor this week?
        // Intern.ontrack: Do you think you are on track?
        for (int i = 0; i<data.size();i++) {
            if (data.get(i)[9].equals("participant") && data.get(i)[4].equals(getStudentID()) && data.get(i)[1].equals(getProgramID())) {
                if (data.get(i)[10].equals("Intern.stage")) {
                    necessaryResource = (data.get(i)[6].equals("1")) ? "Yes" : "  No";
                } else if (data.get(i)[10].equals("Intern.industryengagement")) {
                    engagedSupervisor= (data.get(i)[6].equals("1")) ? "Yes" : "  No";
                } else if (data.get(i)[10].equals("Intern.ontrack")) {
                    onTrack= (data.get(i)[6].equals("1")) ? "Yes" : "  No";
                    return;
                }
            }
        }
    }
    public String getOnTrack() {return onTrack;}
    public String getNecessaryResource() {return necessaryResource;}
    public String getEngagedSupervisor() { return engagedSupervisor;}
    public String getTeamID() {return teamID;}
    public String getStudentID() {return studentID;}
    public String getProgramID() {return programID;}
    public String getExperienceID() {return experienceID;}
    public String getProgramString() throws Exception {
        List<String[]> data = new ArrayList<>();
        Scanner file = new Scanner(new File("src/data/feedback_read_by_student.csv"));
        while (file.hasNextLine()) {
            data.add(file.nextLine().split(","));
        }
        for (int i = 0; i<data.size();i++) {
            if (data.get(i)[0].equals(getProgramID())) {
                return data.get(i)[3];
            }
        }
        return "";
    }
    public String getExperienceString() throws FileNotFoundException {
        List<String[]> data = new ArrayList<>();
        Scanner file = new Scanner(new File("src/data/feedback_read_by_student.csv"));
        while (file.hasNextLine()) {
            data.add(file.nextLine().split(","));
        }
        for (int i = 0; i<data.size();i++) {
            if (data.get(i)[1].equals(getExperienceID())) {
                return data.get(i)[2];
            }
        }
        return "";
    }
    private void setProgress() throws FileNotFoundException {
        List<String[]> data = new ArrayList<>();
        Scanner file = new Scanner(new File("src/data/assess_assessment_submissions.csv"));
        while (file.hasNextLine()) {
            data.add(file.nextLine().split(","));
        }
        int submissions = 0;
        for (int i = 0; i<data.size();i++) {
            // This if statement checks respectively:
            // Same Program ID
            // Student ID is the same
            // If the set of assessment IDs contain the one in the file
            if (data.get(i)[0].equals(getProgramID()) && data.get(i)[1].equals(getStudentID()) && assessmentIDs.contains(data.get(i)[5])) {
                submissions++;
            }
        }
        progress= ((double)submissions/assessmentIDs.size())*100;
    }
    public double getProgress() {
        return progress;
    }
    private void setAssessmentIDS() throws FileNotFoundException {
        List<String[]> data = new ArrayList<>();
        Scanner file = new Scanner(new File("src/data/assess_assessment_details.csv"));
        while (file.hasNextLine()) {
            data.add(file.nextLine().split(","));
        }
        Set<String> temp = new HashSet<>();
        for (int i = 0; i<data.size();i++) {
            if (data.get(i)[0].equals(getProgramID())) {
                temp.add(data.get(i)[2]);
            }
        }

        assessmentIDs=temp;
    }

    @Override
    public String toString() {
        return "Student: " + studentID;
    }

}
