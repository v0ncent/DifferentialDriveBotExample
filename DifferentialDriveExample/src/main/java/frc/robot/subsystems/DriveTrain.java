package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
    private final CANSparkMax leftLeader = new CANSparkMax(Constants.FRONTLEFTMOTOR_PORT,MotorType.kBrushless);
    private final CANSparkMax leftFollower = new CANSparkMax(Constants.BACKLEFTMOTOR_PORT,MotorType.kBrushless);
    private final CANSparkMax rightLeader = new CANSparkMax(Constants.FRONTRIGHTMOTOR_PORT,MotorType.kBrushless);
    private final CANSparkMax rightFollower = new CANSparkMax(Constants.BACKRIGHTMOTOR_PORT,MotorType.kBrushless);
    
    private final MotorControllerGroup leftGroup = new MotorControllerGroup(leftLeader, leftFollower);
    private final MotorController rightGroup = new MotorControllerGroup(rightLeader, rightFollower);

    /*
     * Think of a X,Y plane (What you would use to create a graph).
     * We can actually split each section of the graph into quadrants.
     * -----------------
     * |      |        |
     * |  2   |   1    |    We do that like so !
     * |      |        |    So if I have a positive pair of numbers (we call those coordinates) like (1,3) for example, 
     * |------|--------|    the point we would place would be in quadrant 1 and so forth depending on the points you put on the graph.
     * |   3  |   4    |
     * |      |        |
     * -----------------
     */
    public void arcadeDrive(double drive, double rotate) {
        // variables for determening the quadrants
        double maximum = Math.max(Math.abs(drive), Math.abs(rotate));
        double total = drive + rotate;
        double difference = drive - rotate;

        // set speed according to the quadrant that the values are in
        if (drive >= 0) {
            if (rotate >= 0) { // quadrant 1 
                leftGroup.set(maximum);
                rightGroup.set(difference);
            } else { // quadrant 2
                leftGroup.set(total);
                rightGroup.set(maximum);
            }
        } else { 
            if (rotate >= 0) { // quadrant 4
                leftGroup.set(total);
                rightGroup.set(-maximum);
            } else { // quadrant 3
                leftGroup.set(-maximum);
                rightGroup.set(difference);
            }
        }
    }

    public void semiConstantCurvatureDrive(double T, double S) {
        // calculate semi-constant curvature values
        double L = 12 * (((T + Math.abs(T) * S) + (T+S)) / 2);
        double R = 12 * (((T - Math.abs(T) * S) + (T-S)) / 2);

        // Determine maximum output
        double m = Math.max(Math.abs(T),Math.abs(S));

        // scale if needed 
        if (m > 1.0) {
            L /= m;
            R /= m;
        }

        // set mototrs to calculated power
        leftGroup.set(L);
        rightGroup.set(R);

    }

}
