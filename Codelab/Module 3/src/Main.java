public class Main {
    public static void main(String[] args) {
        GameCharacter generalCharacter = new GameCharacter("General Character", 100);
        Hero brimstone = new Hero("Brimstone", 150);
        Enemy viper = new Enemy("Viper", 200);

        System.out.println("Initial status:");
        System.out.println(brimstone.getName() + " has health: " + brimstone.getHealth());
        System.out.println(viper.getName() + " has health: " + viper.getHealth());


        brimstone.attack(viper);
        brimstone.attack(viper);
        viper.attack(brimstone);

    }
}