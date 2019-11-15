/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.LimeLightCommand;

/**
 * Add your docs here.
 */
public class LimeLightSubsystem extends Subsystem {
  public double limelightX;
  public double limelightY;
  public double limelightArea;
  // public boolean limelightV;

  public int pipeline;
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void LimelightSetup() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3); 
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(2); 
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
  }
  
  public void Limelight () {
  
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    // limelightV = v;
    limelightX = x;
    limelightY = y;
    limelightArea = area;


    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);

    NetworkTableEntry pipelineEntry = table.getEntry("pipeline");
    	pipelineEntry.setNumber(pipeline);

  }

  public void setPipeline(int networkPipeline) {
		pipeline = networkPipeline;
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new LimeLightCommand());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
