//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.teamcode.MecanumHardware;
//@Disabled
//@TeleOp(name="TankDrive")
//public class TankDrive extends LinearOpMode {
//
//    /*
//    /*
//    /*
//    MecanumHardware robot = new MecanumHardware();   // Use a Pushbot's hardware
//
//    @Override
//    public void runOpMode() {
//        double left;
//        double right;
//        double drive;
//        double turn;
//        double max;
//        double clawPosition = 0.5;                  // Servo mid position
//        final double CLAW_SPEED = 0.01;                 // sets rate to move servo
//        boolean grabberVal = true;
//        double motorTester = 0.0;
//        boolean directionToggle = true;
//        boolean xToggle = true;
//        boolean yToggle = true;
//
//        /* Initialize the hardware variables.
//         * The init() method of the hardware class does all the work here
//         */
//        robot.init(hardwareMap);
//
//        // Send telemetry message to signify robot waiting;
//        telemetry.addData("Say", "Hello Driver");    //
//        telemetry.update();
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//
//        // run until the end of the match (driver presses STOP)
//        while (opModeIsActive()) {
//
//            robot.leftFront.setPower(gamepad1.left_trigger);
//            robot.leftBack.setPower(gamepad1.left_trigger);
//            robot.rightFront.setPower(gamepad1.right_trigger);
//            robot.rightBack.setPower(gamepad1.right_trigger);
//
//
//            if(gamepad1.a && directionToggle){
//                robot.leftFront.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
//                robot.rightFront.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
//                robot.leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
//                robot.rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
//                directionToggle = false;
//            }
//            else if(gamepad1.a && !directionToggle){
//                robot.leftFront.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
//                robot.rightFront.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
//                robot.leftBack.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
//                robot.rightBack.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
//                directionToggle = true;
//            }
//
//            telemetry.addData("Toggle: ", directionToggle);
//
//            if(gamepad1.x && xToggle){
//                robot.input.setPower(1);
//                xToggle = false;
//            }
//            else if(gamepad1.x && !xToggle){
//                robot.input.setPower(0);
//                xToggle = true;
//            }
//            if(gamepad1.y && yToggle){
//                robot.output.setPower(1);
//                yToggle = false;
//            }
//            else if(gamepad1.y && !yToggle){
//                robot.output.setPower(1);
//                yToggle = true;
//            }
//            telemetry.update();
//
//        }
//    }
//
//    */
//}
