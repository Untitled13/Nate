/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.sensors;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Add your docs here.
 */
public class ShuffleBoard {
    public ShuffleboardTab GamePlay = Shuffleboard.getTab("GamePlay");
    public ShuffleboardTab Tune = Shuffleboard.getTab("Tune");
    public ShuffleboardTab Debug = Shuffleboard.getTab("Debug");

    public void initialize() {
        
    }

}
