package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ManualVacuumPumpControl;

public class VacuumPumpSubsystem extends Subsystem {
    public boolean tunable = false;

    private final WPI_VictorSPX vacuumPumpMaster, vacuumPumpSlave;
    
    private Solenoid vacuumSolenoid = new Solenoid(RobotMap.PCM, RobotMap.vacuumSolenoid);

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
            airEqualizerClose();
        } else if (pumpOff) {
            vacuumPumpMaster.set(0);
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
        vacuumSolenoid.set(state);
    }

    public void stop() {
      vacuumPumpMaster.set(ControlMode.PercentOutput, 0);
      vacuumPumpSlave.set(ControlMode.PercentOutput, 0);
      vacuumSolenoid.set(false);
    }

	@Override
	public void initDefaultCommand() {
      setDefaultCommand(new ManualVacuumPumpControl());
	}
}