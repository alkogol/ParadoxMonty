package paradox;

import java.util.Random;

public class Paradox {
    private static Random random = new Random();
    private static final int ITERATIONS_COUNT = 1000000;
    private static double counter_no_choice = 0;
    private static double counter_rechoice = 0;
    private static double counter_no_rechoice_after = 0;
    private static final int TOTAL_DOORS_COUNT = 3;
    private static final int EMPTY_DOOR = 0;
    private static final int DOOR_WITH_CAR = 1;


    public static void main(String[] args) {

        for (int i = 0; i < ITERATIONS_COUNT; i++) {
            emulate();
        }
        System.out.println("Количество итераций: " + ITERATIONS_COUNT);
        System.out.println("Без изменений выбора: " + counter_no_choice / ITERATIONS_COUNT);
        System.out.println("поменяли выбор: " + counter_rechoice / ITERATIONS_COUNT);

    }
    //утилитная функция
    private static int generateRandomValueFromSpecificInt(int[] array) {
        int rndKey = array.length > 0 ? random.nextInt(array.length) : -1;
        return array[rndKey];
    }

    private static void emulate() {
        int[] doors = generateDoors();

        int choiceKey = getRandomDoorNumber();

        //запираем дверь, не выбранную игроком и без автомобиля
        int removeKey = removeRandomDoorExceptChoosen(doors, choiceKey);

        //1. Не меняем выбора
        if (doors[choiceKey] == DOOR_WITH_CAR) {
            counter_no_choice++;
        }

        //2. Выбираем другую дверь
        makeLastChoice(choiceKey, removeKey, doors);

    }

    /**
     * Получаем элемент массива со значением DOOR_WITH_CAR исключая индексы choiceKey и removeKey
     * @param choiceKey индекс/маркер выбранной двери
     * @param removeKey индекс/маркер удаленной двери
     * @param doors ожидаем массив из 3 элементов
     */
    private static void makeLastChoice(int choiceKey, int removeKey, int[] doors) {

        if (doors.length!=3) throw new RuntimeException("Count of doors expected 3\n"+"Current value "+doors.length);

        for (int i = 0; i < doors.length; i++) {
            if (i != choiceKey && i != removeKey && doors[i] == DOOR_WITH_CAR) {
                counter_rechoice++;
            }
        }
    }

    private static int getRandomDoorNumber() {
        return random.nextInt(TOTAL_DOORS_COUNT);
    }


    private static int removeRandomDoorExceptChoosen(int[] doors, int choice) {

        int[] indexesOfDoors = arrayOfIndexesExcludedOne(choice, doors);

        int removeKey = generateRandomValueFromSpecificInt(indexesOfDoors);
        if (doors[removeKey]!=DOOR_WITH_CAR){
            return removeKey;
        }
        for (int indexesOfDoor : indexesOfDoors) {
            if (doors[indexesOfDoor] != DOOR_WITH_CAR) {
                return indexesOfDoor;
            }
        }
        return -1;
    }
    private static int[] generateDoors() {
        int[] doors = new int[]{EMPTY_DOOR, EMPTY_DOOR, EMPTY_DOOR};
        doors[random.nextInt(TOTAL_DOORS_COUNT)] = DOOR_WITH_CAR;
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
