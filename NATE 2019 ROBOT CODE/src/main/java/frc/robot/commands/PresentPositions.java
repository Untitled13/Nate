package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PresentPositions extends Command {
  public double armPresetPosition;
  public double extentionPresetPosition;
  public double wristPresetPosition;
  public boolean presetPostitonGoto;
  public int limelightPipeline;
  public int dateStamp;
  public int dateSeconds;
  public int timer;
  public int position;
  

  public PresentPositions(int positionID) {
    position = positionID;
    requires(Robot.armSubsystem);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Smartdashboard.pullFromSmartdashboard();
    if (position == 1) {
      armPresetPosition = Robot.Smartdashboard.armPreset1;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset1;
      wristPresetPosition = Robot.Smartdashboard.wristPreset1;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset1;
    } else if (position == 2) {
      armPresetPosition = Robot.Smartdashboard.armPreset2;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset2;
      wristPresetPosition = Robot.Smartdashboard.wristPreset2;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset2;
    } else if (position == 3) {
      armPresetPosition = Robot.Smartdashboard.armPreset3;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset3;
      wristPresetPosition = Robot.Smartdashboard.wristPreset3;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset3;
    } else if (position == 4) {
      armPresetPosition = Robot.Smartdashboard.armPreset4;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset4;
      wristPresetPosition = Robot.Smartdashboard.wristPreset4;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset4;
    } else if (position == 5) {
      armPresetPosition = Robot.Smartdashboard.armPreset5;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset5;
      wristPresetPosition = Robot.Smartdashboard.wristPreset5;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset5;
    } else if (position == 6) {
      armPresetPosition = Robot.Smartdashboard.armPreset6;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset6;
      wristPresetPosition = Robot.Smartdashboard.wristPreset6;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset6;
    } else if (position == 7) {
      armPresetPosition = Robot.Smartdashboard.armPreset7;
      extentionPresetPosition = Robot.Smartdashboard.extentionPreset7;
      wristPresetPosition = Robot.Smartdashboard.wristPreset7;
      limelightPipeline = Robot.Smartdashboard.pipelinePreset7;
    }

    dateStamp = (int)System.currentTimeMillis();
    Robot.armSubsystem.setArmGoto(true);
    Robot.extentionSubsystem.setExtentionGoto(true);
    presetPostitonGoto = false;
    Robot.Limelight.limelightSetPipeline(limelightPipeline);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.armSubsystem.armSetAngle(armPresetPosition / 100);
    Robot.armSubsystem.armAngle();
    Robot.extentionSubsystem.extentionSetPosition(extentionPresetPosition / 100);
    Robot.extentionSubsystem.extentionPosition();
    // Robot.WristSubsystem.wristSetAngle(wristPresetPosition / 100);
    // Robot.WristSubsystem.wristPosition(Robot.oi.wristEncoder.get());
    dateSeconds = (int)System.currentTimeMillis();

    // if (!Robot.armSubsystem.armGoto && !Robot.extentionSubsystem.extentionGoto && !Robot.WristSubsystem.wristGoto) {
    if (!Robot.armSubsystem.getArmGoto() && !Robot.extentionSubsystem.getExtentionGoto()) {
      presetPostitonGoto = true;
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (presetPostitonGoto || dateSeconds >= (dateStamp + (RobotMap.presetPositionsTimeout * 1000)));

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}