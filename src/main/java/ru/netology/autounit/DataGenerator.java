package ru.netology.autounit;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(Faker faker) {
        String[] cities = {
                "Москва", "Санкт-Петербург", "Казань",
                "Новосибирск", "Екатеринбург", "Самара"
        };
        String city = cities[new Random().nextInt(cities.length)];
        return city;
    }

    public static String generateName(Faker faker) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        return lastName + " " + firstName;
    }

    public static String generatePhone(Faker faker) {
        String phone = "+7" + faker.number().digits(10);
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));

            String city = generateCity(faker);
            String name = generateName(faker);
            String phone = generatePhone(faker);

            UserInfo user = new UserInfo(city, name, phone);
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}