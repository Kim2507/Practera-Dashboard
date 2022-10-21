// Nam Doan, Kim Trinh, Liam O'Connor
// Goals for 4/26/2021
// Store all necessary data
// Calculate the percentage of work completed for each student
//
// Figured out we can use feedback_read_by_student to get the string of the program and experience name
// So far for 4/26/2021
// * Got StudentID, ProgramID, ExperienceID
// * Converted ProgramID and Experience ID to a String
// * Figured out how to get a student's progress from a total number of assessments and the amount submitted
// Need to do (Assigned 4/26/2021)
// * Use Slider_data to figure out if a student is on track or not (Create setOnTrack method in Student)
//
// Error: The slider_data reader reads the last modified answers, we need the most recent ones
// Fix: Make methods that return whenever found first: (intern.stage, intern.industryengagement, intern.ontrack)
// ACTUAL FIX: I noticed that all the question go in order: stage, industryengagement, ontrack; ontrack at the end, the fix was to add a return statement at the ontrack else
package dashboard;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends Application {
    static BorderPane mainPane = new BorderPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Storing enrollment_data into an ArrayList
        List<String[]> enrollmentData = new ArrayList<>();
        Scanner file = new Scanner(new File("src/data/enrollment_data.csv"));
        while (file.hasNextLine()) {
            enrollmentData.add(file.nextLine().split(","));
        }
        //

        // Using enrollment_data.csv
        // Stores user_id and teamID
        // 4/26/2021 Add constructor to Student to store program_id as well as experience_id
        Map<String, Team> teamData = getTeamData(enrollmentData);

        // Getting a random student
        Student temp = (Student) teamData.get("1843").getStudents().toArray()[new Random().nextInt(teamData.get("1843").getStudents().size())];
        //

        // Creating the top
        Text program = new Text(temp.getProgramString());
        Text experience = new Text(temp.getExperienceString());
        program.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 30));
        experience.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        VBox top = new VBox();
        top.getChildren().add(program);
        top.getChildren().add(experience);
        top.setAlignment(Pos.CENTER);
        BorderPane.setMargin(top,new Insets(30));
        mainPane.setTop(top);
        //

        // Showcasing your group mates in the middle
        VBox teamMembers = new VBox(15);
        teamMembers.setPadding(new Insets(30));
        Text headerText = new Text("User ID           Assessments       Has Everything?     On Track?");
        headerText.setFont(Font.font(("arial"),FontWeight.BOLD, FontPosture.REGULAR, 25));
        teamMembers.getChildren().add(headerText);
        for (Student student: teamData.get(temp.getTeamID()).getStudents()) {
            HBox tempHBox = new HBox(90);
            if (!student.getStudentID().equals(temp.getStudentID())) {
                //Created tempTexts so the font can be set
                Text tempText1 = new Text();
                tempText1.setFont(Font.font(("arial"),FontWeight.NORMAL, FontPosture.REGULAR, 25));
                Text tempText2 = new Text();
                tempText2.setFont(Font.font(("arial"),FontWeight.NORMAL, FontPosture.REGULAR, 25));
                Text tempText3 = new Text();
                tempText3.setFont(Font.font(("arial"),FontWeight.NORMAL, FontPosture.REGULAR, 25));
                Text tempText4 = new Text();
                tempText4.setFont(Font.font(("arial"),FontWeight.NORMAL, FontPosture.REGULAR, 25));
                Text tempText5 = new Text();
                tempText5.setFont(Font.font(("arial"),FontWeight.NORMAL, FontPosture.REGULAR, 25));
                tempText1.setText(student.getStudentID());
                tempText2.setText(student.getProgress() + "% complete");
                tempText3.setText(student.getNecessaryResource());
                //tempText4.setText(student.getEngagedSupervisor());
                tempText5.setText(student.getOnTrack());
                tempHBox.getChildren().addAll(tempText1, tempText2, tempText3, tempText4,  tempText5);
                teamMembers.getChildren().add(tempHBox);
            }

        }
        mainPane.setCenter(teamMembers);

        primaryStage.setTitle("Trailblazer's Dashboard");
        primaryStage.setScene(new Scene(mainPane, 800, 800));
        primaryStage.show();
    }

    private static Map<String,Team> getTeamData(List<String[]> enrollmentData) throws FileNotFoundException {
        Map<String, Team> teamData = new HashMap<>();
        for (int i = 0; i<enrollmentData.size();i++) {
            String[] temp = enrollmentData.get(i);
            if (temp[3].equals("coordinator")) Team.coordinatorID=temp[0];
            else {
                if (teamData.containsKey(temp[2])) {
                    if (temp[3].equals("mentor")) {
                        teamData.get(temp[2]).setMentorID(temp[0]);
                    } else if (temp[3].equals("participant")) {
                        teamData.get(temp[2]).getStudents().add(new Student(temp[0], temp[1], temp[4], temp[2]));
                    }
                } else {
                    teamData.put(temp[2],new Team(temp[2]));
                    if (temp[3].equals("mentor")) {
                        teamData.get(temp[2]).setMentorID(temp[0]);
                    } else if (temp[3].equals("participant")) {
                        teamData.get(temp[2]).getStudents().add(new Student(temp[0], temp[1], temp[4], temp[2]));
                    }
                }
            }
        }
        return teamData;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
