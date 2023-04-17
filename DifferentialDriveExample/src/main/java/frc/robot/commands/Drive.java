package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.DriveTrain;

public class Drive extends CommandBase {
    private final CommandXboxController controller;
    private final DriveTrain driveTrain;

    public Drive(CommandXboxController controller, DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.controller = controller;
        addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double forwardBack = -controller.getLeftY();
        double rotate = controller.getLeftX();

        driveTrain.arcadeDrive(forwardBack, rotate);

        // to test out other driving method comment out above and uncomment below
        // driveTrain.semiConstantCurvatureDrive(forwardBack, rotate);
    }

    @Override
    public void end(boolean interuppted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
