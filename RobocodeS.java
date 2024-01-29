package IS004;

import robocode.*;
import java.awt.Color;

/**
 * RobocodeS - a robot by (Stephanie Pesce)
 */
public class RobocodeS extends Robot {
    /**
     * run: RobocodeS's default behavior
     */
    public void run() {
        setColors(Color.CYAN, Color.GREEN, Color.YELLOW); // body, gun, radar
        while (true) {
            ahead(100);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
        }
    }

    /**
     * onScannedRobot: 
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        double distance = e.getDistance();
        double firePower = Math.min(500 / distance, 3);
        double gunTurnAmount = e.getBearing() + getHeading() - getGunHeading();
        turnGunRight(gunTurnAmount);
        fire(firePower);

        double radarTurn = getHeading() - getRadarHeading() + e.getBearing();
        turnRadarRight(radarTurn);
    }

    /**
     * onHitByBullet: 
     */
    public void onHitByBullet(HitByBulletEvent e) {
        back(20);
        turnRight(45);
    }

    /**
     * onHitWall: 
     */
    public void onHitWall(HitWallEvent e) {
        back(20);
        System.out.println("Bearing: " + e.getBearing());
        if (e.getBearing() > -90 && e.getBearing() <= 90) {
            back(50);
        } else {
            ahead(50);
        }
    }

    class RammingBehavior {
        public void execute(ScannedRobotEvent e) {
            turnRight(e.getBearing());
            ahead(e.getDistance() + 5);
            fire(3);
        }
    }

    class DodgeBehavior {
        public void execute(HitByBulletEvent e) {
            turnLeft(90 - e.getBearing());
            ahead(100);
        }
    }

    class SniperBehavior {
        public void execute(ScannedRobotEvent e) {
            double gunTurnAmount = e.getBearing() + getHeading() - getGunHeading();
            turnGunRight(gunTurnAmount);
            fire(Math.min(500 / e.getDistance(), 3));
        }
    }

    class PatrolBehavior {
        int moveDirection = 1;

        public void execute() {
            turnRadarRight(360);
            ahead(100 * moveDirection);
            turnRight(90);
            moveDirection *= -1;
        }
    }
    

    RammingBehavior rammingBehavior = new RammingBehavior();
    DodgeBehavior dodgeBehavior = new DodgeBehavior();
    SniperBehavior sniperBehavior = new SniperBehavior();
    PatrolBehavior patrolBehavior = new PatrolBehavior();
}
