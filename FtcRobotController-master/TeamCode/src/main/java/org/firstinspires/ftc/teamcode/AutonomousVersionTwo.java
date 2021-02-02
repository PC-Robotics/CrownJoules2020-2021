package org.firstinspires.ftc.teamcode;
/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

//Note: Positive Turn turns Left

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * Thoughts:
 *
 * Ring Scenarios:
 *      Go around and possibly work with shooting at an angle....
 *          TOWER
 *              \
 *               \
 *                Robot

 *  1 RING
 *      Detect ring and go right.
 *
 *
 *
 *
 *
 *  4 RINGS
 *
 *  0 RINGS
 */
@Autonomous(name = "workingAuto")
public class AutonomousVersionTwo extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "ATNmabz/////AAABmVmSVXYWb0VmjazeKz6YlEcEFyR/Bwx7J4S4ErBVLHCofHNpuB7OysvKUlcQFXu9lTTNk2HXz/RNh44Cmlq/aTt7HoMb0E3wehoR7nEZtqUydd6qsQj729DrE8ivYcXFI+whlD9swrR3vzpr5WP2Ru6UfA9JZNJ/k8UOU/l9EKJn+gh6Hs/E1pimaFYxExW/2nv3pxuKFUjrP6FDUd8C4CzNwPLvectKrkdzjr8esEfoLqfj+rtC4Ae949C6BDG8E1WyKUxKWl6y+YcKpI0Gf3Vh9Ye99acF35/o7Y16+couYcaIDOd6idCNURLE8FDwJN+wM7BgGfY0z/wkMeULVwONl515au8bnhY92hQfzCLF";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    MecanumHardware robot = new MecanumHardware();
    String label = "null";
    int endAuto = 20000000;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();
        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null)
            tfod.activate();



            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 1.78 or 16/9).

            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            tfod.setZoom(2, 1.78);


        /** Wait for the game to begin */
        telemetry.addData(">", "Make sure this message pops up and then you're good to go!");
        telemetry.update();
        waitForStart();

        //worst comes to worst we can make the while loop go through for a few times first and then start...

        while (opModeIsActive()) {
            robot.init(hardwareMap);
            STOP();

            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        label = recognition.getLabel();
                    }
                    telemetry.update();
                }
            }


            /*
            if(label.equals("Quad"))
                quadCase();
            else if(label.equals("Single"))
                singleCase();
            else
                noRingCase();
            */
        }

        if(tfod != null)
            tfod.shutdown();
    }

    //remember that in each of these cases... the robot is actually at the white line...
    //Let's try and deliver in the landing zone for 4 rings...
    //Deliver in the landing zone for all rings...

    public void quadCase(){
        /**
            Let's think about the overall logic here...
            1 - shoot from left of the rings since we're going to have to put wobble goal anyways
        */
        drive(.5);
        sleep(400);

        turn(.5); //45degrees
        sleep(400);

        drive(.5);
        sleep(400);

        turn(-.5); //forwardfacing
        sleep(400);

        drive(.5);
        sleep(1000);


        turn(-.5); //0degrees
        sleep(400);

        shootThreeRings();
        
        STOP();
        sleep(1000);
    }

    public void singleCase(){

    }

    public void noRingCase(){
        drive(0.5);   //Initial Drive Forward
        robot.output.setPower(.9);
        robot.output2.setPower(.9);
        sleep(1650); //change timing here for initial drive

        //Stop Initial Drive Forward
        shootThreeRings();

        drive(.25);
        sleep(350);

        STOP();
        sleep(endAuto);
    }

    public void shootThreeRings(){
        STOP();
        robot.output.setPower(.9);
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

        robot.input.setPower(0);
        robot.output.setPower(.9);
        robot.output2.setPower(.9);
        sleep(1000);

        robot.input.setPower(1);
        sleep(350);

        robot.input.setPower(0);
        sleep(1000);
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

    public void turn(double power){
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(-power);
        robot.rightBack.setPower(-power);
    }

    /**
     * Initialize the Vuforia localization engine.
     */

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}






