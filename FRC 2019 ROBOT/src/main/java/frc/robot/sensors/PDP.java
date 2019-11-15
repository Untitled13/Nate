/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class PDP extends Subsystem {
  PowerDistributionPanel pdp = new PowerDistributionPanel();
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public double PDPVoltage() {
    double voltage = pdp.getVoltage();
    return voltage;
  }

  public double PDPTotalAmp() {
    double totalAmp = pdp.getTotalCurrent();
    return totalAmp;
  } 

  public double PDPWristAmp() {
    double armAmp = pdp.getCurrent(13);
    return armAmp;
  }

  public double PDPArmAmp() {
    double armAmp = pdp.getCurrent(14);
    return armAmp;
  }

  public double PDPExtentionAmp() {
    double armAmp = pdp.getCurrent(15);
    return armAmp;
  }

  public double PDPLeftDriveAmp() {
    double left1Amp = pdp.getCurrent(2);
    double left2Amp = pdp.getCurrent(3);
    double leftAmp = left1Amp + left2Amp;
    return leftAmp;
  }

  public double PDPRightDriveAmp() {
    double right1Amp = pdp.getCurrent(0);
    double right2Amp = pdp.getCurrent(1);
    double rightAmp = right1Amp + right2Amp;
    return rightAmp;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
