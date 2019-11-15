package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.NoArmCommand;

/**
 * Add your docs here.
 */
public class NoArmSubsystem extends Subsystem {
  public boolean NoArm;
  public int speed;
  
  public void ModeSetter(Boolean mode) {
    NoArm = mode;
  }

  public void ModeExecute() {

    if (NoArm) {
      
      speed = 0;

    } else if(!NoArm) {

      speed = 1;

    }

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new NoArmCommand(false));
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
