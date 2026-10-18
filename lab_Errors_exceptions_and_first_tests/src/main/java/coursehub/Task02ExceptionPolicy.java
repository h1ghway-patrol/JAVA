package coursehub;

import java.util.List;

public final class Task02ExceptionPolicy {
    private Task02ExceptionPolicy() { }
    public enum Choice { CHECKED, UNCHECKED, OTHER_RESULT, FATAL_OR_NOT_RECOVERABLE }
    public record Case(String situation, Choice choice, String reason) { }

    public static List<Case> examples() {
        return List.of(
            new Case("Неверный аргумент метода", Choice.UNCHECKED, "IllegalArgumentException: вызывающий код передал значение вне контракта."),
            new Case("Отсутствующий файл при импорте", Choice.CHECKED, "IOException: файл может появиться или путь можно исправить; вызывающий слой решает, повторять ли операцию."),
            new Case("Нарушение состояния объекта", Choice.UNCHECKED, "IllegalStateException: действие нельзя выполнить при текущем состоянии объекта."),
            new Case("Временный сетевой отказ", Choice.CHECKED, "IOException: часто это ожидаемая внешняя проблема, которую вызывающий слой может обработать или повторить."),
            new Case("Ошибка конфигурации", Choice.UNCHECKED, "IllegalStateException или собственное unchecked-исключение: приложение не может корректно работать с такой настройкой."),
            new Case("Недостаток памяти", Choice.FATAL_OR_NOT_RECOVERABLE, "OutOfMemoryError — Error, обычно приложение не должно пытаться продолжать обычную работу."),
            new Case("Курс не найден при необязательном поиске", Choice.OTHER_RESULT, "Optional.empty явно обозначает отсутствие без исключения."),
            new Case("Пользователь уже записан на курс", Choice.OTHER_RESULT, "Если это ожидаемый исход бизнес-операции, можно вернуть типизированный результат с кодом отказа."),
            new Case("Неверный формат числа во входном файле", Choice.CHECKED, "На границе импорта можно преобразовать NumberFormatException в CourseImportException с номером записи."),
            new Case("Ошибка программиста: null там, где он запрещён", Choice.UNCHECKED, "NullPointerException/IllegalArgumentException указывают на нарушение контракта, а не штатный сценарий.")
        );
    }
}
