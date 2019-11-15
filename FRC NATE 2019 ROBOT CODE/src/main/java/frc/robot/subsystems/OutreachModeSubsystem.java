/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.OutreachModeCommand;

/**
 * Add your docs here.
 */
public class OutreachModeSubsystem extends Subsystem {
  public boolean OutreachMode;
  
  public void ModeSetter(Boolean mode) {
    OutreachMode = mode;
  }

  public void ModeExecute() {

    if (OutreachMode) {
      
      Robot.armSubsystem.outreachArmSpeed = RobotMap.OutreachArmSpeed;
      Robot.extentionSubsystem.outreachExtentionSpeed = RobotMap.OutreachExtentionSpeed;
      Robot.WristSubsystem.outreachWristSpeed = RobotMap.OutreachwristSpeed;
      Robot.driveSubsystem.outreachDriveSpeed = RobotMap.OutreachDriveSpeed;

    } else if(!OutreachMode) {

      Robot.armSubsystem.outreachArmSpeed = 1;
      Robot.extentionSubsystem.outreachExtentionSpeed = 1;
      Robot.WristSubsystem.outreachWristSpeed = 1;
      Robot.driveSubsystem.outreachDriveSpeed = 1;

    }

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new OutreachModeCommand(false));
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
