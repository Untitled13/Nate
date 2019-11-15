package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.VacuumPumpCommand;


public class VacuumPumpSubsystem extends Subsystem {

    public boolean tunable = false;

    public final WPI_VictorSPX vacuumPumpMaster, vacuumPumpSlave;

    public double vacuumPumpOnOff;


	public VacuumPumpSubsystem(boolean tunable) {
        this.tunable = tunable;
        
        //setup motors
        vacuumPumpMaster = new WPI_VictorSPX(RobotMap.vacuumPumpMasterPort);
        vacuumPumpSlave = new WPI_VictorSPX(RobotMap.vacuumPumpSlavePort);

        vacuumPumpSlave.follow(vacuumPumpMaster);

    }

  	// DRIVE THE PUMPS
    public void vacuumPump(Boolean pumpOn, Boolean pumpOff) {

        //pumps
        if (pumpOn) {
            vacuumPumpMaster.set(RobotMap.vacuumPumpSpeed);
            vacuumPumpOnOff = 1;
        } else if (pumpOff) {
            vacuumPumpMaster.set(0);
            vacuumPumpOnOff = 0;
        }

        //air equalizer valve
        if (vacuumPumpOnOff == 1) {
            airEqualizerClose();
          }
          else {
            airEqualizerOpen();
          }

    }

    public void airEqualizerOpen() {
        Equalizer(false);
    }
  
    public void airEqualizerClose() {
    
        Equalizer(true);
    }
  
    public void Equalizer(boolean state) {
        Robot.oi.vacuumSolenoid.set(state);
    }

    public void stop() {
      vacuumPumpMaster.set(ControlMode.PercentOutput, 0);
      vacuumPumpSlave.set(ControlMode.PercentOutput, 0);
    }

	@Override
	public void initDefaultCommand() {
      setDefaultCommand(new VacuumPumpCommand());
	}
}