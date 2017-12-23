package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends Action{

    private String studentID;
    private String DepartmentName;

    public AddStudent(String DepartmentName,String studentID ){
        this.studentID = studentID;
        this.DepartmentName = DepartmentName;
        this.setActionName("Add Student");
    }//constructor

    @Override
    protected void start() {
        System.out.println("Adding Student " + this.studentID);
        this.ActorState.addRecord(getActionName());
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> AddStudentConfirmation = new AddStudentConfirmation(this.DepartmentName,this.studentID);
        actions.add(AddStudentConfirmation);
        sendMessage(AddStudentConfirmation, this.studentID, new StudentPrivateState());
        then(actions,()->{
            Boolean result = actions.get(0).getResult().get();
            if(result == true) {
                complete(true);
                System.out.println("Student: " + this.studentID + " added");
            }
            else{
                complete(false);
                System.out.println("Student " + this.studentID + " not added");
            }
        });
    }
}

