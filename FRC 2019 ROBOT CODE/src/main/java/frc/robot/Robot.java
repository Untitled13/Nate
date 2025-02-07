package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.sensors.Camera;
import frc.robot.sensors.Limelight;
import frc.robot.sensors.PDP;
import frc.robot.sensors.ShuffleBoard;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CameraServoSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExtentionSubsystem;
import frc.robot.subsystems.RobotClimbSubsystem;
import frc.robot.subsystems.VacuumPumpSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class Robot extends TimedRobot {
  // Subsystem Instantiations
  public static DriveSubsystem driveSubsystem = new DriveSubsystem(true);
  public static ArmSubsystem armSubsystem = new ArmSubsystem(false);
  public static ExtentionSubsystem extentionSubsystem = new ExtentionSubsystem(false);
  public static WristSubsystem WristSubsystem = new WristSubsystem(false);
  public static VacuumPumpSubsystem VacuumPumpSubsystem = new VacuumPumpSubsystem(false);
  public static CameraServoSubsystem cameraServoSubsystem = new CameraServoSubsystem(false);
  public static Camera cameraServer = new Camera();
  public static RobotClimbSubsystem RobotClimbSubsystem = new RobotClimbSubsystem();
  public static ShuffleBoard ShuffleBoard = new ShuffleBoard();
  public static Limelight Limelight = new Limelight();
  public static PDP PDP = new PDP();
  public static OI oi;   

  @Override
  public void robotInit() {
    oi = new OI();
    ShuffleBoard.init();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousPeriodic() {
    matchPeriodic();
  }
  
   @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

  }

  public void matchPeriodic() {
    Scheduler.getInstance().run();
  }
}