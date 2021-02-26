package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name="AutonomousDrive")
public class AutonomousVersionOne extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();

    //intake rollers for 250 ms
    //then intake rollers for 400 ms
    //then intake rollers for 400 ms

    /*
       Move wobble goal to target zone
       shoot for top goal in tower

       3 seconds for winding up shooter motor
       .25 second for rolling the input
       .4 seconds for rolling the input for the next 2
     */

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);

        waitForStart();
        while(opModeIsActive())
        {
            drive(0.5);   //Initial Drive Forward
            robot.output.setPower(.85);
            robot.output2.setPower(.85);
            sleep(1950); //change timing here for initial drive

            drive(0);   //Stop Initial Drive Forward
            robot.output.setPower(.85);
            robot.output2.setPower(.9);
            sleep(1000);

            robot.input.setPower(1);
            sleep(250);

            robot.input.setPower(0);
            robot.output.setPower(.9);
            robot.output2.setPower(.9);
            sleep(1000);

            robot.input.setPower(1);
            sleep(250);
            //33+6

            robot.input.setPower(0);
            robot.output.setPower(.9);
            robot.output2.setPower(.9);
            sleep(500);

            robot.input.setPower(1);
            sleep(1000);

            robot.input.setPower(0);
            sleep(1000);

            drive(.25);
            sleep(350);
            
            STOP();
            sleep(200000);
        }
    }

    public void drive(double power) {
        robot.leftFront.setPower(power);  //negative should stay because of the direction of the robot
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        robot.leftBack.setPower(power);
    }

    public void STOP(){
        int power = 0;
        robot.leftFront.setPower(power);  //negative should stay because of the direction of the robot
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        robot.leftBack.setPower(power);
        robot.input.setPower(0);
        robot.output.setPower(0);
        robot.output2.setPower(0);
    }


}
