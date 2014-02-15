boolean secondaryBall = ballSetting.get();
        double shootTime = 5; //Amount of time needed to shoot and move forward
        double maxBackDistance = 6;
        ShooterRack.setHighRPM();
        ShooterRack.setToFiring();

        //if running two ball autonomous
        if (secondaryBall) {
            while (!DriveTrain.autoDistance(ShooterRack.shooterDistance, 0.7)) {
            }
            //shoot first ball
            ShooterRack.autonomousFiring();
            //pick up second ball
            while (!Feeder.ballLimit.get() && DriveTrain.getDistanceToWall() < maxBackDistance) {
                DriveTrain.driveStraight(-0.7);
                Feeder.feed();
            }
            ShooterRack.setToFiring();
        }
        while (!DriveTrain.autoDistance(ShooterRack.shooterDistance, 0.7)) {
        }
        while (Feeder.ballLimit.get() && DriverStation.getInstance().getMatchTime() < shootTime) {
            if (Vision.getBlobCount() == 2) {
                ShooterRack.autonomousFiring();
            }
        }
        ShooterRack.setToFiring();
        if (Feeder.ballLimit.get() && DriverStation.getInstance().getMatchTime() < 8.0) {
            ShooterRack.autonomousFiring();
        }

        while (!DriveTrain.autoDistance(5, 0.7))//Close enough to the wall to count as being within our zone
        {
        }
        DriveTrain.stop();