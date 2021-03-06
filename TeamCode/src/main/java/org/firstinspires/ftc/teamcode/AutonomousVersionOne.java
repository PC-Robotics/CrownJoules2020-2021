package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name="AutonomousDriveNew")
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
            //negative turn is turn left
            drive(.5);
            sleep(1000); //700 initially


            drive(.5);
            sleep(800);

            drive(0);
            turn(-.5);
            sleep(900); //800 is fine maybe turn for 700

            drive(.1);
            sleep(400);

            drive(0);
            robot.wobble.setPower(-.7);
            sleep(1500);

            robot.wobble.setPower(.3);
            sleep(250);

            drive(0);
            sleep(750);

            drive(-.5);
            robot.wobble.setPower(.1);
            sleep(100);

            turn(.5);
            sleep(1100); //placement is fine just wokr

            drive(0);
            robot.wobble.setPower(1);
            sleep(500);


            drive(-.2);
            robot.wobble.setPower(0);
            sleep(300);

            drive(0);
            turn(-.5);
            sleep(150);

            //WIND UP
            drive(0);
            robot.wobble.setPower(0);
            robot.output.setPower(.85);
            robot.output2.setPower(.85);
            sleep(1500); //change timing here for initial drive

            robot.input.setPower(1);
            sleep(250);

            robot.input.setPower(0);
            robot.output.setPower(.85);
            robot.output2.setPower(.85);
            sleep(1000);

            robot.input.setPower(1);
            sleep(250);
            //33+6

            robot.input.setPower(0);
            robot.output.setPower(.85);
            robot.output2.setPower(.85);
            sleep(500);

            robot.input.setPower(1);
            sleep(1000);

            robot.input.setPower(0);
            sleep(1000);

            drive(.7);
            sleep(400);
            
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

    public void turn(double power) {
        robot.leftFront.setPower(-power);  //This isn't inverted the motor config is just weird...
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        robot.leftBack.setPower(-power);
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
