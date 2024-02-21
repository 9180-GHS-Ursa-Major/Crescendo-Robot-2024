package frc.robot.Subsystems;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    Spark led = new Spark(0);
    Optional<Alliance> ally = DriverStation.getAlliance();

    public LEDSubsystem() {}

    public void setAllianceColour() {
        if (ally.isPresent()) {
            if (ally.get() == Alliance.Red) {
                led.set(0.61);
            }
            if (ally.get() == Alliance.Blue) {
                led.set(0.87);
            }
        } else {
            led.set(0.53);
        }
    }

    public void noteColor() {
        led.set(0.69);
    }

    public void speakerAmpColur() {
        led.set(0.77);
    }

    @Override
    public void periodic() {
        setAllianceColour();
    }
}
