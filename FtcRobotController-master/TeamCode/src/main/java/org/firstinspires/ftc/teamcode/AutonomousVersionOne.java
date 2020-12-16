package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name="AutonomousVersionOne")
public class AutonomousVersionOne extends LinearOpMode {

    MecanumHardware robot = new MecanumHardware();

    //intake rollers for 250 ms
    //then intake rollers for 400 ms
    //then intake rollers for 400 ms

    @Override
    public void runOpMode()
    {
        robot.init(hardwareMap);

        waitForStart();
        while(opModeIsActive())
        {
            robot.output.setPower(1);
            sleep(3000);

            robot.input.setPower(1);
            sleep(250);

            robot.output.setPower(1);
            sleep(3000);

            robot.input.setPower(1);
            sleep(400);

            robot.output.setPower(1);
            sleep(3000);

            robot.input.setPower(1);
            sleep(400);

            sleep(200000);
        }
    }


}
