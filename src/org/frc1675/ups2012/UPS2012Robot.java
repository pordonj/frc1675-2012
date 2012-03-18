/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc1675.ups2012;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Ultrasonic.Unit;
import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc1675.auton.*;
import org.frc1675.auton.BabbysFirstAutonomous;
import org.frc1675.driver.JoystickTankController;
import org.frc1675.driver.TankController;
import org.frc1675.driver.XBoxController;
import org.frc1675.driver.XBoxTankController;
import org.frc1675.robot.component.SourceCounter;
import org.frc1675.robot.component.Storage;
import org.frc1675.robot.component.TwoMotorGearbox;
import org.frc1675.robot.system.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class UPS2012Robot extends IterativeRobot {
    public static final double WHEEL_ENCODER_DPP = 0.0698;
    
    private static final int ULTRASONIC_SKIP_COUNT = 5;
    private static final double ULTRASONIC_MIN_DISTANCE = 4.0;
    private static final int ULTRASONIC_MIN_DETECT_COUNT = 20;
    
    //CRIO CARD SLOT DEFINITIONS
    private static final int DIGITAL_SIDECAR_1 = 1;
    
    //DSC 1 PWM SLOT DEFINITIONS
    private static final int LEFT_DRIVE = 1;
    private static final int RIGHT_DRIVE = 2;
    private static final int SHOOTER = 3;
    private static final int HOOD_MOTOR = 4; // REQUIRES JUMPER
    private static final int TURRET_MOTOR = 5; //REQUIRES JUMPER
    private static final int CAMERA_SERVO = 6; //REQUIRES JUMPER
    private static final int LEFT_SHIFT_SERVO = 7; //REQUIRES JUMPER
    private static final int RIGHT_SHIFT_SERVO = 8; //REQUIRES JUMPER
    
    private static final int INTAKE_MOTOR = 10;//changed this with the camera port
    
    //DSC 1 GPIO SLOT DEFINITIONS
    private static final int ULTRASONIC_1_INPUT = 1;
    private static final int ULTRASONIC_1_OUTPUT = 2;
    private static final int ULTRASONIC_2_INPUT = 3;
    private static final int ULTRASONIC_2_OUTPUT = 4;
    private static final int ULTRASONIC_3_INPUT = 5;
    private static final int ULTRASONIC_3_OUTPUT = 6;
    private static final int TURRET_ENCODER_A = 7;
    private static final int TURRET_ENCODER_B = 8;
    private static final int HOOD_ENCODER_A = 9;
    private static final int HOOD_ENCODER_B = 10;
    private static final int HOOD_LIMIT = 11;
    private static final int BRIDGE_ARM_IN_LIMIT = 12;
    private static final int BRIDGE_ARM_OUT_LIMIT = 13;
    private static final int SHOOTER_ENCODER_A = 14;
    
    //DSC 1 RELAY SLOT DEFINITIONS
    private static final int ELEVATOR_A = 1;
    private static final int ELEVATOR_B = 2;
    private static final int BRIDGE_ARM = 3;

    //vision IPs
    private static final String VISION_CAMERA = "10.16.75.2";
    private static final String INTAKE_CAMERA = "192.168.0.90";

    //CONTROLLERS
    XBoxController driverController;
    XBoxController operatorController;
    
    //ROBOT SYSTEMS
    Storage ballStorage;
    SimpleTankDrive drive;
//    ShooterSystem shooterSystem;
//    PresetFinderShooterSystem shooterSystem;
    RotatingPresetShooterSystem shooterSystem;
    BridgeArm bridgeArm;

    //VISION SYSTEM
    VisionSystem visionSystem;
    
    AutonomousScript autonScript;

    Counter shooterCounter;
    
    Hood hood;
    
    SourceCounter counter;
    Turret turret;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //instantiate controllers
        driverController = new XBoxController(new Joystick(1));
//        Joystick leftStick = new Joystick(1);
//        Joystick rightStick = new Joystick(3);

        operatorController = new XBoxController(new Joystick(2));
        
        //instantiate DSC 1 PWM components
        SpeedController leftDrive = new Victor(DIGITAL_SIDECAR_1, LEFT_DRIVE);
        SpeedController rightDrive = new Victor(DIGITAL_SIDECAR_1, RIGHT_DRIVE);
        Servo leftSideShifter = new Servo(DIGITAL_SIDECAR_1, LEFT_SHIFT_SERVO); //MUST HAVE JUMPER
        Servo rightSideShifter = new Servo(DIGITAL_SIDECAR_1, RIGHT_SHIFT_SERVO); //MUST HAVE JUMPER
        SpeedController intakeMotor = new Victor(DIGITAL_SIDECAR_1, INTAKE_MOTOR);
        SpeedController turretMotor = new Victor(DIGITAL_SIDECAR_1, TURRET_MOTOR); //MUST HAVE JUMPER
        
        Servo cameraServo = new Servo(DIGITAL_SIDECAR_1, CAMERA_SERVO); //MUST HAVE JUMPER
        
        SpeedController hoodMotor = new Victor(DIGITAL_SIDECAR_1, HOOD_MOTOR);
        SpeedController shooterMotor = new Victor(DIGITAL_SIDECAR_1, SHOOTER);
        
        
        //instantiate DSC 1 relay components
        Relay leftLift = new Relay (DIGITAL_SIDECAR_1, ELEVATOR_A, Relay.Direction.kBoth);
        Relay rightLift = new Relay (DIGITAL_SIDECAR_1, ELEVATOR_B, Relay.Direction.kBoth);
        Relay bridgeArmRelay = new Relay(DIGITAL_SIDECAR_1, BRIDGE_ARM, Relay.Direction.kBoth);
        
        //instantiate DSC 1 GPIO components
//        Encoder leftEncoder = new Encoder(DIGITAL_SIDECAR_2, LEFT_ENCODER_A, DIGITAL_SIDECAR_2, LEFT_ENCODER_B, false);
//        Encoder rightEncoder = new Encoder(DIGITAL_SIDECAR_2, RIGHT_ENCODER_A, DIGITAL_SIDECAR_2, RIGHT_ENCODER_B, false);
        shooterCounter = new Counter(DIGITAL_SIDECAR_1, SHOOTER_ENCODER_A);
//      Encoder turretEncoder = new Encoder(DIGITAL_SIDECAR_1, TURRET_ENCODER_A, DIGITAL_SIDECAR_1, TURRET_ENCODER_B, false);
        Encoder hoodEncoder = new Encoder(DIGITAL_SIDECAR_1, HOOD_ENCODER_A, DIGITAL_SIDECAR_1, HOOD_ENCODER_B, true);
        DigitalInput hoodLimit = new DigitalInput(DIGITAL_SIDECAR_1, HOOD_LIMIT);
        DigitalInput armInLimit = new DigitalInput(DIGITAL_SIDECAR_1, BRIDGE_ARM_IN_LIMIT);
        DigitalInput armOutLimit = new DigitalInput(DIGITAL_SIDECAR_1, BRIDGE_ARM_OUT_LIMIT);
        DigitalInput ballInLimit = new DigitalInput(DIGITAL_SIDECAR_1, ULTRASONIC_3_OUTPUT);
        
        //instantiate DSC 2 GPIO components
        Ultrasonic ultraSonic1 = new Ultrasonic(DIGITAL_SIDECAR_1, ULTRASONIC_1_INPUT, DIGITAL_SIDECAR_1, ULTRASONIC_1_OUTPUT, Unit.kInches);
        Ultrasonic ultraSonic2 = new Ultrasonic(DIGITAL_SIDECAR_1, ULTRASONIC_2_INPUT, DIGITAL_SIDECAR_1, ULTRASONIC_2_OUTPUT, Unit.kInches);
        //Ultrasonic ultraSonic3 = new Ultrasonic(DIGITAL_SIDECAR_1, ULTRASONIC_3_INPUT, DIGITAL_SIDECAR_1, ULTRASONIC_3_OUTPUT, Unit.kInches);
        
        
        
        
        
        
        //Make pre-system changes
//        leftEncoder.setDistancePerPulse(WHEEL_ENCODER_DPP);
//        rightEncoder.setDistancePerPulse(WHEEL_ENCODER_DPP);
        ultraSonic1.setAutomaticMode(true);
        ultraSonic1.setEnabled(true);
        ultraSonic2.setAutomaticMode(true);
        ultraSonic2.setEnabled(true);
        //ultraSonic3.setAutomaticMode(true);
        //ultraSonic3.setEnabled(true);
        
        //Instantiate sub-systems
        TankController tankController = new XBoxTankController(driverController);
//        TankController tankController = new JoystickTankController(leftStick, rightStick);
//        Shooter shooter = new SteppingShooter(shooterMotor, operatorController);
//        Shooter shooter = new VoltageShooter(shooterMotor);
        Shooter shooter = new PIDShooter(shooterMotor, shooterCounter);
        turret = new SimpleTurret(turretMotor, operatorController);
        //Hood hood = new SimpleHood(hoodMotor, operatorController);
        hood = new EncoderHood(hoodMotor, operatorController, hoodEncoder, hoodLimit);
//        PIDHoodSource hoodSource = new PIDHoodSource(hoodEncoder, hoodLimit);
//        PIDHoodOutput hoodOutput = new PIDHoodOutput(hoodMotor, hoodLimit);
//        hood = new PIDHood(hoodSource, hoodOutput);
        
//        shooterSystem = new ShooterSystem(shooter, turret, hood);
//        shooterSystem = new PresetFinderShooterSystem(shooter, hood, turret, driverController);
        shooterSystem = new RotatingPresetShooterSystem(shooter, hood, turret, operatorController);
        UltrasonicWrapper ultraSonicWrapper1 = new UltrasonicWrapper(ultraSonic1, 8.0, 1);
        UltrasonicWrapper ultraSonicWrapper2 = new UltrasonicWrapper(ultraSonic2, 8.0, 1);
        //UltrasonicWrapper ultraSonicWrapper3 = new UltrasonicWrapper(ultraSonic3, 6.0, 5);

        //instantiate camera
//        AxisCamera visionCamera = AxisCamera.getInstance(VISION_CAMERA);
//        visionCamera.writeCompression(0);
//        visionCamera.writeResolution(AxisCamera.ResolutionT.k320x240);
        //Instantiate systems
        //drive = new EncoderTankDrive(tankController, leftGearbox, rightGearbox, leftSideShifter, rightSideShifter, leftEncoder, rightEncoder);
        drive = new SimpleTankDrive(tankController, leftDrive, rightDrive, leftSideShifter, rightSideShifter);
        Elevator elevator = new Elevator(leftLift,rightLift, operatorController);
        Intake intake = new Intake(intakeMotor, operatorController);
        ballStorage = new Storage(intake, elevator, -50, ultraSonicWrapper1, ultraSonicWrapper2, ballInLimit);
//        shooterSystem = new ShooterSystem(shooter, turret, hood);
        bridgeArm = new SimpleBridgeArm(bridgeArmRelay, operatorController, armInLimit, armOutLimit);
//        visionSystem = new VisionSystem(visionCamera);
        
        
        //Pick autonomous script
        autonScript = new BabbysFirstAutonomous(shooterSystem, ballStorage);
        //autonScript = new FeedingScript(ballStorage, 6.0);
        counter = new SourceCounter(shooterCounter);
        
        SmartDashboardOutputProfiler.getInstance().setProfile("Driver");
        SmartDashboardOutputProfiler.getInstance().putString("Driver","Title: ", "Welcome to Team 1675");
    }
    
    
    /**
     * Called at start of autonomous
     */
    public void autonomousInit(){
        hoodReady = false;
        reportTimer.start();
        shooterCounter.start();
    }

    
    boolean hoodReady = false;
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
//        while(!hoodReady){
//            hoodReady = hood.setAngle(-30);
//        }
        
//        autonScript.doAutonomous();
//        if(reportTimer.get() > 1.0){
//            ticksPastSecond = shooterCounter.get();
//            shooterCounter.reset();
//            reportTimer.reset();
//        }
//        int rpm = (int) (ticksPastSecond / 6L);
//        SmartDashboard.putInt("Shooter RPM", rpm);
    }

    Timer reportTimer = new Timer();

    public void teleopInit(){
//        shooterCounter.start();
//        reportTimer.start();
//        counter.start();
    }

    long ticksPastSecond = 0;
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        drive.driveTeleop();
//        boolean shooting = shooterSystem.doTeleop();
        shooterSystem.doTeleop();
        //ballStorage.doManual(false);
        ballStorage.manage(false);
        bridgeArm.doBridgeArm();
        turret.doTurret();
//        if(reportTimer.get() > 1.0){
//            ticksPastSecond = shooterCounter.get();
//            shooterCounter.reset();
//            reportTimer.reset();
//        }
//        int rpm = (int) (ticksPastSecond / 6L);
//        SmartDashboard.putInt("Shooter RPM", rpm);
//        SmartDashboard.putDouble("PID Get", counter.pidGet());
//        SmartDashboard.putDouble("Enc Period", shooterCounter.getPeriod());
    }

    private int periodToRPM(double period) {
        long ticksPerSec = (long)(1.0 / period);
        long ticksPerMin = ticksPerSec * 60L;
        return (int)(ticksPerMin / 360L);
    }
}
