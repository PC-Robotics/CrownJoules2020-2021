package org.firstinspires.ftc.teamcode.FourBarPrograms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name="ShaurnavStrafeAttempt", group="JankBot")
public class ShaurnavStrafeAttempt extends LinearOpMode {
    double clawPosition  = 0.51;

    MecanumHardware robot = new MecanumHardware();

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false;


    private static final String VUFORIA_KEY =
            "AbKyy9P/////AAABmZpghjpXrUjnj2qaQhfvPa0P0vGa6y5QOc6+Chvg+1l0XCq0f9A4VEWVzgalF6UiB6cUoUmEGyLyoMd8Go16eFzm1ZRYpkvdFujYpvxIj3Se0mT69+hWVqrwLTPbFJwDODQPbbfgFmLKXNUA5FEvYvtc0r9LqMdhYVyQTc29gfGcSvricz9O0IrK8vJjsWqgNH1a3gixHJDiyWE3ngnXyKzsgREfV5UxIk5Dcy/6FLjzNI1LeKVEeq2LfQkf72zyCYQZ6GgOnc/MXqgqqkPWpfuKY8KgS1SbxXGms4TaecQQDd1LknNXPq9KoIJUB+MYmLix8SqUlmrlj1K4tNUJksFdhdcYTFRs9+Auw0wMyWAn";
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    private final double inchVal = 41.667;


    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;


    @Override
    public void runOpMode() {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        robot.init(hardwareMap);
        robot.imu.initialize(parameters);

        composeTelemetry();

        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        // Loop and update the dashboard
        telemetry.update();



        //41.667 ticks/1 inch

        encoderDrive(0.3,30,30,30,
                30,7);
        grabbingBlock();
        miniLiftingUp();
        takeBreak(800);
        encoderDrive(0.9,-12,-12,-12,
                -12,7);
        //encoderRightTurn();
        takeBreak(200);
        //gyroRight();
        encoderDrive(0.5,39,-39,-39,
                39,7); //right and left are invesed
        letGoBlock();
        takeBreak(800);
        encoderDrive(0.5,-46,46,46, //47 orig
                -46,7);
        //encoderLeftTurn();
        takeBreak(200);
        //headingCalibrate();
        encoderDrive(0.3,12,12, 12,
                12,7);
        grabbingBlock();
        takeBreak(800);
        encoderDrive(0.9,-12,-12,-12,
                -12,7);
        //encoderRightTurn();
        takeBreak(200);
        //gyroRight();
        encoderDrive(0.5,47,-47,-47,
                47,7);
        letGoBlock();
        takeBreak(800);
        encoderDrive(0.5,-54,53,53, //55 orig
                -53,7);
        //encoderLeftTurn();
        takeBreak(200);
        //headingCalibrate();
        encoderDrive(0.3,14,14,14,
                14,7);
        grabbingBlock();
        takeBreak(800);
        encoderDrive(0.9,-18,-18,-18,
                -18,7);
        //encoderRightTurn();
        takeBreak(200);
        //gyroRight();
        encoderDrive(0.5,58,-58,-58,
                58,7);
        letGoBlock();
        takeBreak(800);
        encoderDrive(0.9,-12,-12,-12,
                -12,7);





    }

    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
            // Acquiring the angles is relatively expensive; we don't want
            // to do that in each of the three items that need that info, as that's
            // three times the necessary expense.
            angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = robot.imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return robot.imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return robot.imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    public void headingCalibrate() {
        telemetry.update();
        double heading =  angles.firstAngle;

        if (heading < 0) {
            while (heading < 0) { //neg
                robot.leftFront.setPower(-0.1);
                robot.rightFront.setPower(0.1);
                robot.leftBack.setPower(-0.1);
                robot.rightBack.setPower(0.1);
                telemetry.update();
                angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;


            }
        }
        else if (heading > 0) {
            while (heading > 0) { //pos


                robot.leftFront.setPower(0.1);
                robot.rightFront.setPower(-0.1);
                robot.leftBack.setPower(0.1);
                robot.rightBack.setPower(-0.1);
                telemetry.update();
                angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;
            }
        }

    }

    public void gyroRight() {
        telemetry.update();
        double heading =  angles.firstAngle;

        /*while (heading > -90) { //b/w -75 & -90

            robot.leftFront.setPower(0.1);
            robot.rightFront.setPower(-0.1);
            robot.leftBack.setPower(0.1);
            robot.rightBack.setPower(-0.1);
            telemetry.update();
            angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = angles.firstAngle;

        } */

        if (heading < -90) {

            while (heading < -90) { //neg


                robot.leftFront.setPower(-0.1);
                robot.rightFront.setPower(0.1);
                robot.leftBack.setPower(-0.1);
                robot.rightBack.setPower(0.1);
                telemetry.update();
                angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;


            }
        }
        else if (heading > -90) {
            while (heading > -90) { //pos


                robot.leftFront.setPower(0.1);
                robot.rightFront.setPower(-0.1);
                robot.leftBack.setPower(0.1);
                robot.rightBack.setPower(-0.1);
                telemetry.update();
                angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;


            }
        }




    }

    //TWEAK VALUES
    public void encoderRightTurn() {

        encoderDrive(0.6,24,-24,24,
                -24,7);

    }

    //TWEAK VALUES
    public void encoderLeftTurn() {

        encoderDrive(0.6,-24,24,-24,
                24,7);

    }

    //TWEAK VALUES
    public void encoderRightTurnEnd() {

        encoderDrive(0.6,28,-28,28,
                -28,7);

    }

    //TWEAK VALUES
    public void encoderLeftTurnEnd() {

        encoderDrive(0.6,-28,28,-28,
                28,7);
    }

    public void gyroLeft() {
        telemetry.update();
        double heading =  angles.firstAngle;

       /* while (heading < 90) { //b/w 75 & 90

            robot.leftFront.setPower(-0.1);
            robot.rightFront.setPower(0.1);
            robot.leftBack.setPower(-0.1);
            robot.rightBack.setPower(0.1);
            telemetry.update();
            angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = angles.firstAngle;

        } */

        if (heading < 90) {

            while (heading < 90) { //neg


                robot.leftFront.setPower(-0.1);
                robot.rightFront.setPower(0.1);
                robot.leftBack.setPower(-0.1);
                robot.rightBack.setPower(0.1);
                telemetry.update();
                angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;


            }
        }
        else if (heading > 90) {
            while (heading > 90) { //pos


                robot.leftFront.setPower(0.1);
                robot.rightFront.setPower(-0.1);
                robot.leftBack.setPower(0.1);
                robot.rightBack.setPower(-0.1);
                telemetry.update();
                angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                heading = angles.firstAngle;
            }
        }




    }

    public void encoderDrive(double speed,
                             double leftInchesFront, double rightInchesFront, double leftInchesBack, double rightInchesBack,
                             double timeoutS) {
        int frontLeftTarget;
        int frontRightTarget;
        int backLeftTarget;
        int backRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            double leftTicksFront = inchVal * leftInchesFront;
            double rightTicksFront = inchVal * rightInchesFront;
            double leftTicksBack = inchVal * leftInchesBack;
            double rightTicksBack = inchVal * rightInchesBack;


            // Determine new target position, and pass to motor controller
            frontLeftTarget = robot.leftFront.getCurrentPosition() + (int)(leftTicksFront);
            frontRightTarget = robot.rightFront.getCurrentPosition() + (int)(rightTicksFront);
            backLeftTarget = robot.leftBack.getCurrentPosition() + (int)(leftTicksBack);
            backRightTarget = robot.rightBack.getCurrentPosition() + (int)(rightTicksBack);
            robot.leftFront.setTargetPosition(frontLeftTarget);
            robot.rightFront.setTargetPosition(frontRightTarget);
            robot.leftBack.setTargetPosition(backLeftTarget);
            robot.rightBack.setTargetPosition(backRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            robot.period.reset();
            robot.leftFront.setPower(Math.abs(speed));
            robot.rightFront.setPower(Math.abs(speed));
            robot.leftBack.setPower(Math.abs(speed));
            robot.rightBack.setPower(Math.abs(speed));

            //CONTINUE HERE

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (robot.period.seconds() < timeoutS) &&
                    (robot.leftFront.isBusy() && robot.rightFront.isBusy() && robot.leftBack.isBusy() && robot.rightBack.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", frontLeftTarget,  frontLeftTarget, backLeftTarget, backRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftFront.getCurrentPosition(),
                        robot.rightFront.getCurrentPosition(),
                        robot.leftBack.getCurrentPosition(),
                        robot.rightBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
            robot.rightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }


    //18.667 in per second
    //Motor values down 0.1
    public void sleepRunningBack(int time) {

        robot.rightBack.setPower(-0.5);
        robot.leftFront.setPower(-0.5);
        robot.rightFront.setPower(-0.5);
        robot.leftBack.setPower(-0.5);

        sleep(time);

        robot.rightBack.setPower(0.0);
        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);


    }

    public void sleepRunning(int time) {

        robot.rightBack.setPower(0.5);
        robot.leftFront.setPower(0.5);
        robot.rightFront.setPower(0.5);
        robot.leftBack.setPower(0.5);

        sleep(time);

        robot.rightBack.setPower(0.0);
        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);


    }


    public void strafeRight(int time) {


        robot.leftFront.setPower(-0.8); //0.4 is very consistent
        robot.rightFront.setPower(0.8);
        robot.leftBack.setPower(0.8);
        robot.rightBack.setPower(-0.8);
        sleep(time);

        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);
        robot.rightBack.setPower(0.0);



    }

    public void strafeLeft(int time) {


        robot.leftFront.setPower(0.8); //0.4 is very consistent
        robot.rightFront.setPower(-0.8);
        robot.leftBack.setPower(-0.8);
        robot.rightBack.setPower(0.8);
        sleep(time);

        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);
        robot.rightBack.setPower(0.0);



    }



    public void rightTurn() {


        robot.leftFront.setPower(0.6);
        robot.rightFront.setPower(-0.6);
        robot.leftBack.setPower(0.6);
        robot.rightBack.setPower(-0.6);
        sleep(1850);

        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);
        robot.rightBack.setPower(0.0);



    }


    public void leftTurn() {


        robot.leftFront.setPower(-0.6);
        robot.rightFront.setPower(0.6);
        robot.leftBack.setPower(-0.6);
        robot.rightBack.setPower(0.6);
        sleep(1850);

        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);
        robot.rightBack.setPower(0.0);



    }

    public void takeBreak(int sleepTime) {

        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);
        robot.rightBack.setPower(0.0);
        sleep(sleepTime);
    }


    public void grabbingBlock() {

        clawPosition = 0.6;
        clawPosition = Range.clip(clawPosition, 0, 1);
        robot.grabber.setPosition(clawPosition);

    }

    public void letGoBlock() {

        clawPosition = 0.5;
        clawPosition = Range.clip(clawPosition, 0, 1);
        robot.grabber.setPosition(clawPosition);

    }

    public void liftingUp() {

        robot.lift.setPower(-0.8);
        sleep(100);

    }

    public void miniLiftingUp() {
        robot.lift.setPower(-0.4);
    }



    public void miniLiftingDown() {

        robot.lift.setPower(0.4);

    }

    public void liftingDown() {

        robot.lift.setPower(0.6);
        sleep(300);

    }

    public void noDrag() {

        robot.lift.setPower(-0.2);
        sleep(50);

    }



    public void draggerBacker(int time) {

        robot.lift.setPower(0.6);
        robot.leftFront.setPower(-0.5);
        robot.rightFront.setPower(-0.5);
        robot.leftBack.setPower(-0.5);
        robot.rightBack.setPower(-0.5);
        sleep(time);

        robot.leftFront.setPower(0.0);
        robot.rightFront.setPower(0.0);
        robot.leftBack.setPower(0.0);
        robot.rightBack.setPower(0.0);

    }

  /* public void GyroSetUp() {
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        robot.imu.initialize(parameters);

        //angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        /*firstAngle = heading
        secondAngle = roll
        thirdAngle = pitch
         */



    //}

   /* public void GyroCalibration() {

        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        /*firstAngle = heading
        secondAngle = roll
        thirdAngle = pitch
         */
  /*      telemetry.addData("heading",  "%.2f", angles.firstAngle);

        if ((angles.firstAngle != 0) && (angles.firstAngle > 180)) {

            while (angles.firstAngle > 270) {

                robot.leftFront.setPower(0.6);
                robot.rightFront.setPower(-0.6);
                robot.leftBack.setPower(0.6);
                robot.rightBack.setPower(-0.6);


            }

        }

        else if ((angles.firstAngle != 0) && (angles.firstAngle < 180)){

            while (angles.firstAngle > 0) {

            }
                robot.leftFront.setPower(-0.6);
                robot.rightFront.setPower(0.6);
                robot.leftBack.setPower(-0.6);
                robot.rightBack.setPower(0.6);

        }

        else {

            robot.leftFront.setPower(0.0);
            robot.rightFront.setPower(0.0);
            robot.leftBack.setPower(0.0);
            robot.rightBack.setPower(0.0);

        }



    } */

    public double webcamSight() {

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(robot.cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;

        parameters.cameraName = robot.webcamName;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));


        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        // waitForStart();

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.

        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        targetsSkyStone.activate();
        robot.period.reset();
        while (robot.period.milliseconds() < 3000) { //!isStopRequested()
            // check all the trackable targets to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    //new
                    if (trackable.getName().equals("Stone Target")) {

                        telemetry.addLine("Stone Target is visible");
                    }
                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            String positionSkystone = "";
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",

                        translation.get(0), translation.get(1), translation.get(2)); //translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);

                double xPosition = translation.get(1);
                return xPosition;
            }
            else {
                positionSkystone = "left";
                telemetry.addData("Visible Target", "none");
                //do movement and grabbing
            }
            telemetry.addData("Skystone position", positionSkystone);
            telemetry.update();
        }

        targetsSkyStone.deactivate();
        return 0.0;
    }
}
