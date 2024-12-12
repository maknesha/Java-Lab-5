import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

abstract class Flower {
    private String name;
    private int freshness;
    private int stemLength;
    private double price;

    public Flower(String name, int freshness, int stemLength, double price) {
        if (freshness < 0 || freshness > 100) {
            throw new IllegalArgumentException("Рівень свіжості має бути в діапазоні 0-100.");
        }
        if (stemLength <= 0 || price <= 0) {
            throw new IllegalArgumentException("Довжина стебла та ціна мають бути додатніми значеннями.");
        }
        this.name = name;
        this.freshness = freshness;
        this.stemLength = stemLength;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getFreshness() {
        return freshness;
    }

    public int getStemLength() {
        return stemLength;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (свіжість: " + freshness + "%, довжина стебла: " + stemLength + " см, ціна: " + price + " грн)";
    }
}

class Rose extends Flower {
    private String color;

    public Rose(String name, int freshness, int stemLength, double price, String color) {
        super(name, freshness, stemLength, price);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Троянда " + color + " - " + super.toString();
    }
}

class Tulip extends Flower {
    private String variety;

    public Tulip(String name, int freshness, int stemLength, double price, String variety) {
        super(name, freshness, stemLength, price);
        this.variety = variety;
    }

    @Override
    public String toString() {
        return "Тюльпан (" + variety + ") - " + super.toString();
    }
}

class Daisy extends Flower {
    private int petalCount;

    public Daisy(String name, int freshness, int stemLength, double price, int petalCount) {
        super(name, freshness, stemLength, price);
        this.petalCount = petalCount;
    }

    @Override
    public String toString() {
        return "Ромашка (пелюсток: " + petalCount + ") - " + super.toString();
    }
}

class Bouquet {
    private List<Flower> flowers = new ArrayList<>();
    private List<Double> accessories = new ArrayList<>();

    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public void addAccessory(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна аксесуара має бути додатною.");
        }
        accessories.add(price);
    }

    public double calculateTotalPrice() {
        double total = flowers.stream().mapToDouble(Flower::getPrice).sum();
        total += accessories.stream().mapToDouble(Double::doubleValue).sum();
        return total;
    }

    public void sortByFreshness() {
        flowers.sort(Comparator.comparingInt(Flower::getFreshness).reversed());
    }

    public List<Flower> findFlowersByStemLength(int minLength, int maxLength) {
        if (minLength > maxLength) {
            throw new IllegalArgumentException("Мінімальна довжина не може бути більшою за максимальну.");
        }
        List<Flower> result = new ArrayList<>();
        for (Flower flower : flowers) {
            if (flower.getStemLength() >= minLength && flower.getStemLength() <= maxLength) {
                result.add(flower);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Букет:\n");
        for (Flower flower : flowers) {
            sb.append(flower.toString()).append("\n");
        }
        sb.append("Загальна вартість: ").append(calculateTotalPrice()).append(" грн");
        return sb.toString();
    }
}

public class Lab5 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Bouquet bouquet = new Bouquet();

            Rose rose = new Rose("Червона троянда", 70, 50, 150.0, "червона");
            Tulip tulip = new Tulip("Жовтий тюльпан", 90, 40, 100.0, "жовтий");
            Daisy daisy = new Daisy("Біла ромашка", 85, 35, 50.0, 34);

            bouquet.addFlower(rose);
            bouquet.addFlower(tulip);
            bouquet.addFlower(daisy);

            bouquet.addAccessory(30.0);
            bouquet.addAccessory(20.0);

            System.out.println("\nДо сортування за свіжістю:");
            System.out.println(bouquet);

            bouquet.sortByFreshness();
            System.out.println("\nПісля сортування за свіжістю:");
            System.out.println(bouquet);

            System.out.print("\nВведіть мінімальну довжину стебла: ");
            int minLength = scanner.nextInt();
            System.out.print("Введіть максимальну довжину стебла: ");
            int maxLength = scanner.nextInt();

            System.out.println("\nКвіти з довжиною стебла в діапазоні " + minLength + "-" + maxLength + " см:");
            List<Flower> matchingFlowers = bouquet.findFlowersByStemLength(minLength, maxLength);
            for (Flower flower : matchingFlowers) {
                System.out.println(flower);
            }

        } catch (Exception e) {
            System.out.println("Сталася помилка: " + e.getMessage());
        }
    }
}
