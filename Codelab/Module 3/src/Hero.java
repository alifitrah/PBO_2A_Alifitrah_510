public class Hero extends GameCharacter {

    public Hero(String name, int health) {
        super(name, health);
    }

    @Override
    public void attack(GameCharacter target) {
        System.out.println(this.getName() + " attacks " + target.getName() + " using Orbital Strike!");
        target.setHealth(target.getHealth() - 20);
        System.out.println(target.getName() + " now has " + target.getHealth() + " health ");
    }
}