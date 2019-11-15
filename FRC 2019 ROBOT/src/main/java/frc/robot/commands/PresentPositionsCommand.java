package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PresentPositionsCommand extends Command {
  public double armPresetPosition;
  public double extentionPresetPosition;
  public double wristPresetPosition;
  public boolean presetPostitonGoto;
  public int limelightPipeline;
  public int dateStamp;
  public int dateSeconds;
  public int timer;
  public int position;
  

  public PresentPositionsCommand(int positionID) {
    position = positionID;
    requires(Robot.armSubsystem);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    if (position == 1) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset1;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset1;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset1;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset1;
    } else if (position == 2) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset2;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset2;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset2;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset2;
    } else if (position == 3) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset3;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset3;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset3;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset3;
    } else if (position == 4) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset4;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset4;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset4;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset4;
    } else if (position == 5) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset5;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset5;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset5;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset5;
    } else if (position == 6) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset6;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset6;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset6;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset6;
    } else if (position == 7) {
      armPresetPosition = Robot.SmartDashboardSubsystem.armPreset7;
      extentionPresetPosition = Robot.SmartDashboardSubsystem.extentionPreset7;
      wristPresetPosition = Robot.SmartDashboardSubsystem.wristPreset7;
      limelightPipeline = Robot.SmartDashboardSubsystem.pipelinePreset7;
    }

    dateStamp = (int)System.currentTimeMillis();
    Robot.armSubsystem.armGoto = true;
    Robot.extentionSubsystem.extentionGoto = true;
    presetPostitonGoto = false;
    Robot.LimeLightSubsystem.setPipeline(limelightPipeline);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.armSubsystem.armSetAngle(armPresetPosition / 100);
    Robot.armSubsystem.armAngle();
    Robot.extentionSubsystem.extentionSetPosition(extentionPresetPosition / 100);
    Robot.extentionSubsystem.extentionPosition(Robot.oi.extentionEncoder.get());
    // Robot.WristSubsystem.wristSetAngle(wristPresetPosition / 100);
    // Robot.WristSubsystem.wristPosition(Robot.oi.wristEncoder.get());
    dateSeconds = (int)System.currentTimeMillis();

    // if (!Robot.armSubsystem.armGoto && !Robot.extentionSubsystem.extentionGoto && !Robot.WristSubsystem.wristGoto) {
    if (!Robot.armSubsystem.armGoto && !Robot.extentionSubsystem.extentionGoto) {
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