package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.units.Angle;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {
    private final CANSparkMax armMotor1 = new CANSparkMax(ArmConstants.kArmPort1, MotorType.kBrushless);
    private final CANSparkMax armMotor2 = new CANSparkMax(ArmConstants.kArmPort2, MotorType.kBrushless);

    DutyCycleEncoder encoder = new DutyCycleEncoder(ArmConstants.kEncoderPort);
   

    public ArmSubsystem() {
        armMotor1.setInverted(false);
        armMotor2.setInverted(true);
        armMotor2.follow(armMotor1, true);
        encoder.setDistancePerRotation(-ArmConstants.armDPR);
    }

    //Control the arm at a certain speed
    public void armControl(double speed) {
         armMotor1.set(0.25 * speed);
        
    }

    //Set the arm to a desired angle - use the absolute encoder to read the position
    public void armSet(double angle) {
        if (armAngle() == angle) {
            System.out.println("Arm angle = " + angle);
        } else if (armAngle() < angle) {
            armControl(-0.25);
        } else if (armAngle() > angle) {
            armControl(0.25);
        }
    }

    public double armAngle() {
        double angle = encoder.getDistance();
        return angle;
    }

    public void resetArmCoder() {
        encoder.reset();
    }

    @Override
    public void periodic() {
        double armAngle = Math.toDegrees(armAngle());
        double distancePR = encoder.getDistancePerRotation();
        double armAngleRadians = armAngle() / Math.PI;
        double offSet = encoder.getPositionOffset();

        SmartDashboard.putNumber("Arm Angle", armAngle);
        SmartDashboard.putNumber("Arm Angle R", armAngleRadians);
        SmartDashboard.putNumber("DPR", distancePR);
        SmartDashboard.putNumber("Position Offset", offSet);
    }
}
