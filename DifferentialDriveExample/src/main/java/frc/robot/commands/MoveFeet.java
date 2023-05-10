package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class MoveFeet extends CommandBase {
    private final DriveTrain driveTrain;
    private final double feet;

    public MoveFeet(DriveTrain driveTrain, double feet) {
        this.driveTrain = driveTrain;
        this.feet = feet;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        driveTrain.arcadeDrive(-0.2,0);
    }

    @Override
    public void end(boolean interuppted) {
        driveTrain.arcadeDrive(0, 0);
        System.out.println("End of auto rotations\nleft: " + driveTrain.getLefEncoderPosition() + " right: " + driveTrain.getRightEncoderPosition());
    }

    @Override
    public boolean isFinished() {
        return driveTrain.ticks2Feet(driveTrain.getLefEncoderPosition()) >= feet 
        && 
        driveTrain.ticks2Feet(driveTrain.getRightEncoderPosition()) >= feet;
    }
    
}
