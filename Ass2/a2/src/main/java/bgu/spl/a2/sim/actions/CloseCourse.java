package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class CloseCourse extends Action{

    private String CourseName;
    private String DepartmentName;

    public CloseCourse(String DepartmentName, String CourseName){
        this.CourseName = CourseName;
        this.DepartmentName = DepartmentName;
        this.setActionName("Close Course");
    }

    @Override
    protected void start() {

        ((DepartmentPrivateState) this.ActorState).RemoveCourseFromDepartment(this.CourseName);
        List<Action<Boolean>> actions = new ArrayList<>();
        Action<Boolean> CloseCourseConfirmation = new CloseCourseConfirmation(this.CourseName);
        actions.add(CloseCourseConfirmation);
        sendMessage(CloseCourseConfirmation, this.CourseName, new CoursePrivateState());
        then(actions,()->{
            this.ActorState.addRecord(getActionName());
            Boolean result = actions.get(0).getResult().get();
            if(result == true) {
                {
                    complete(true);
                }
            }
            else{
                complete(false);
            }
        });
    }
}
