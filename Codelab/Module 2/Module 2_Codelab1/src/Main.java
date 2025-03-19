public class Main {
    public static void main(String[] args) {
        Animal1 animal1 = new Animal1();
        Animal2 animal2 = new Animal2();

        animal1.name = "Kucing";
        animal1.type = "Mamalia";
        animal1.sound = "Nyann~~";

        animal2.name = "Anjing";
        animal2.type = "Mamalia";
        animal2.sound = "Woof-Woof!!";

        animal1.talk();
        animal2.talk();

    }
}