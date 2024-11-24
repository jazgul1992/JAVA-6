import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;

public class UserDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество датарождения номертелефона пол):");
        String input = scanner.nextLine();

        try {
            String[] parsedData = parseInput(input);
            String fileName = parsedData[0] + ".txt";
            writeToFile(fileName, String.join(" ", parsedData));
            System.out.println("Данные успешно сохранены в файл: " + fileName);
        } catch (IllegalArgumentException | ParseException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Произошла ошибка при работе с файлом:");
            e.printStackTrace();
        }
    }

    private static String[] parseInput(String input) throws IllegalArgumentException, ParseException {
        String[] parts = input.split("\\s+");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Ожидается 6 элементов. Введено: " + parts.length);
        }

        String lastName = parts[0];
        String firstName = parts[1];
        String middleName = parts[2];
        String birthDate = parts[3];
        String phoneNumber = parts[4];
        String gender = parts[5];

        // Проверка даты рождения
        validateDate(birthDate);

        // Проверка номера телефона
        validatePhoneNumber(phoneNumber);

        // Проверка пола
        validateGender(gender);

        return new String[]{lastName, firstName, middleName, birthDate, phoneNumber, gender};
    }

    private static void validateDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        dateFormat.parse(date);
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Номер телефона должен содержать только цифры.");
        }
    }

    private static void validateGender(String gender) {
        if (!gender.equals("f") && !gender.equals("m")) {
            throw new IllegalArgumentException("Пол должен быть 'f' или 'm'.");
        }
    }

    private static void writeToFile(String fileName, String data) throws IOException {
        Path filePath = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(data);
            writer.newLine();
        }
    }
}

