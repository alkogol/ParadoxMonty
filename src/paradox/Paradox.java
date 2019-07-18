package paradox;

import java.util.Random;

public class Paradox {
    private static Random random = new Random();
    private static final int ITERATIONS_COUNT = 1000000;
    private static double counter_no_choice = 0;
    private static double counter_rechoice = 0;
    private static double counter_no_rechoice_after = 0;
    public static void main(String[] args) {

        for (int i = 0; i < ITERATIONS_COUNT; i++) {
            emulate();
        }
        System.out.println("Количество итераций: " + ITERATIONS_COUNT);
        System.out.println("Без изменений выбора: " + counter_no_choice / ITERATIONS_COUNT);
        System.out.println("поменяли выбор: " + counter_rechoice / ITERATIONS_COUNT);
        System.out.println("не меняли после убирания выбор: " + counter_no_rechoice_after / ITERATIONS_COUNT);

    }
    //утилитная функция
    private static int generateRandomValueFromSpecificInt(int[] array) {
        int rndKey = array.length > 0 ? random.nextInt(array.length) : -1;
        return array[rndKey];
    }

    private static void emulate() {
        int[] doors = generateDoors();

        int choiceKey = makeChoice();

        //запираем дверь, не выбранную игроком и без автомобиля
        int removeKey = removeDoor(doors, choiceKey);

        //1. Не меняем выбора
        if (doors[choiceKey] == 1) {
            counter_no_choice++;
        }

        //2. Выбираем другую дверь
        makeLastChoce(choiceKey, removeKey, doors);


    }

    private static void makeLastChoce(int choiceKey, int removeKey, int[] doors) {
        for (int i = 0; i < doors.length; i++) {
            if (i != choiceKey && i != removeKey && doors[i] == 1) {
                counter_rechoice++;
            }
        }
    }

    private static int makeChoice() {
        return random.nextInt(3);
    }


    private static int removeDoor(int[] doors, int choice) {
        int[] indexesOfDoors = arrayOfIndexesExcludedOne(choice, doors);
        int removeKey = generateRandomValueFromSpecificInt(indexesOfDoors);
        if (doors[removeKey]!=1){
            return removeKey;
        }
        for (int indexesOfDoor : indexesOfDoors) {
            if (doors[indexesOfDoor] != 1) {
                return indexesOfDoor;
            }
        }
        return -1;
    }
    private static int[] generateDoors() {
        int[] doors = new int[]{0, 0, 0};
        doors[random.nextInt(3)] = 1;
        return doors;
    }

    //утилитная функция
    private static int[] arrayOfIndexesExcludedOne(int excludedIndex, int[] array) {
        int[] arrayOfIndexes = new int[array.length - 1];
        //получаем массив индексов исключая один
        int firstIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != excludedIndex) {
                arrayOfIndexes[firstIndex] = i;
                firstIndex++;
            }
        }
        return arrayOfIndexes;
    }
}
