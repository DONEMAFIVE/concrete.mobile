DROP TABLE IF EXISTS 'tags_manual';

CREATE TABLE IF NOT EXISTS 'tags_manual' (
'id' INTEGER NOT NULL,
'tagName' TEXT NOT NULL,
'number' INTEGER NOT NULL,
'start' INTEGER NOT NULL,
'bit' INTEGER NOT NULL,
'dbArea' TEXT NOT NULL,
'type' TEXT NOT NULL,
'value' TEXT NOT NULL,
'isAlarm' INTEGER NOT NULL
);

INSERT OR IGNORE INTO 'tags_manual' ('id', 'tagName', 'number', 'start', 'bit', 'dbArea', 'type', 'value', 'isAlarm') VALUES
(0, 'Переключение Ручной/автоматический режим', 15, 0, 2, 'DB', 'Bool', 'false', 0),
(1, 'Сброс всех аварий', 15, 0, 3, 'DB', 'Bool', 'false', 0),
(2, 'Управление заслонкой бункер инертных 11', 15, 0, 4, 'DB', 'Bool', 'false', 0),
(3, 'Управление заслонкой бункер инертных 12', 15, 0, 5, 'DB', 'Bool', 'false', 0),
(4, 'Управление заслонкой бункер инертных 21', 15, 0, 6, 'DB', 'Bool', 'false', 0),
(5, 'Управление заслонкой бункер инертных 22', 15, 0, 7, 'DB', 'Bool', 'false', 0),
(6, 'Управление заслонкой бункер инертных 31', 15, 1, 0, 'DB', 'Bool', 'false', 0),
(7, 'Управление заслонкой бункер инертных 32', 15, 1, 1, 'DB', 'Bool', 'false', 0),
(8, 'Управление заслонкой бункер инертных 41', 15, 1, 2, 'DB', 'Bool', 'false', 0),
(9, 'Управление заслонкой бункер инертных 42', 15, 1, 3, 'DB', 'Bool', 'false', 0),
(10, 'Управление заслонкой дозатор воды', 15, 1, 5, 'DB', 'Bool', 'false', 0),
(11, 'Управление заслонкой дозатор химии', 15, 1, 7, 'DB', 'Bool', 'false', 0),
(12, 'Управление заслонкой дозатор цемента', 15, 2, 3, 'DB', 'Bool', 'false', 0),
(13, 'Управление горизонтальный конвейера', 15, 1, 4, 'DB', 'Bool', 'false', 0),
(14, 'Управление насос воды', 15, 1, 6, 'DB', 'Bool', 'false', 0),
(15, 'Управление насос химии 1', 15, 2, 0, 'DB', 'Bool', 'false', 0),
(16, 'Управление насос химии 2', 15, 2, 1, 'DB', 'Bool', 'false', 0),
(17, 'Управление насос химии 3', 0, 0, 0, 'DB', 'Bool', 'false', 0),
(18, 'Управление шнек цемента 1', 15, 2, 5, 'DB', 'Bool', 'false', 0),
(19, 'Управление шнек цемента 2', 15, 2, 6, 'DB', 'Bool', 'false', 0),
(20, 'Управление шнек цемента 3', 0, 2, 6, 'DB', 'Bool', 'false', 0),
(21, 'Управлением затвором сброса смесителя - Открыть', 15, 3, 0, 'DB', 'Bool', 'false', 0),
(22, 'Управление Скипом направление вверх', 15, 3, 5, 'DB', 'Bool', 'false', 0),
(23, 'Управление Скипом направление вниз', 15, 3, 6, 'DB', 'Bool', 'false', 0),
(24, 'Управление аэрация силос 1', 15, 3, 7, 'DB', 'Bool', 'false', 0),
(25, 'Управлением двигателем смесителя', 15, 3, 2, 'DB', 'Bool', 'false', 0),
(26, 'Веса сохранены', 16, 12, 0, 'DB', 'Bool', 'false', 0),
(27, 'Калибровка ДК - ноль', 15, 4, 5, 'DB', 'Bool', 'false', 0),
(28, 'Калибровка ДК - с грузом', 15, 4, 6, 'DB', 'Bool', 'false', 0),
(29, 'Калибровка ДВ - ноль', 15, 4, 7, 'DB', 'Bool', 'false', 0),
(30, 'Калибровка ДВ - с грузом', 15, 5, 0, 'DB', 'Bool', 'false', 0),
(31, 'Калибровка ДЦ - ноль', 15, 5, 1, 'DB', 'Bool', 'false', 0),
(32, 'Калибровка ДЦ - с грузом', 15, 5, 2, 'DB', 'Bool', 'false', 0),
(33, 'Калибровка ДХ - ноль', 15, 5, 3, 'DB', 'Bool', 'false', 0),
(34, 'Калибровка ДХ - с грузом', 15, 5, 4, 'DB', 'Bool', 'false', 0),
(35, 'Рецепт_Бункер_1_1', 10, 14, 0, 'DB', 'Real', '0.0', 0),
(36, 'Рецепт_Бункер_1_2', 10, 18, 0, 'DB', 'Real', '0.0', 0),
(37, 'Рецепт_Бункер_2_1', 10, 22, 0, 'DB', 'Real', '0.0', 0),
(38, 'Рецепт_Бункер_2_2', 10, 26, 0, 'DB', 'Real', '0.0', 0),
(39, 'Рецепт_Бункер_3_1', 10, 30, 0, 'DB', 'Real', '0.0', 0),
(40, 'Рецепт_Бункер_3_2', 10, 34, 0, 'DB', 'Real', '0.0', 0),
(41, 'Рецепт_Бункер_4_1', 10, 38, 0, 'DB', 'Real', '0.0', 0),
(42, 'Рецепт_Бункер_4_2', 10, 42, 0, 'DB', 'Real', '0.0', 0),
(43, 'Рецепт_Вода', 10, 46, 0, 'DB', 'Real', '0.0', 0),
(44, 'Рецепт_Химия_1', 10, 50, 0, 'DB', 'Real', '0.0', 0),
(45, 'Рецепт_Химия_2', 10, 54, 0, 'DB', 'Real', '0.0', 0),
(46, 'Рецепт_Химия_3', 10, 58, 0, 'DB', 'Real', '0.0', 0),
(47, 'Рецепт_Цемент_1', 10, 62, 0, 'DB', 'Real', '0.0', 0),
(48, 'Рецепт_Цемент_2', 10, 66, 0, 'DB', 'Real', '0.0', 0),
(49, 'Рецепт_Цемент_3', 10, 70, 0, 'DB', 'Real', '0.0', 0),
(50, 'Недосып_Бункер_1_1', 10, 78, 0, 'DB', 'Real', '0.0', 0),
(51, 'Недосып_Бункер_1_2', 10, 82, 0, 'DB', 'Real', '0.0', 0),
(52, 'Недосып_Бункер_2_1', 10, 86, 0, 'DB', 'Real', '0.0', 0),
(53, 'Недосып_Бункер_2_2', 10, 90, 0, 'DB', 'Real', '0.0', 0),
(54, 'Недосып_Бункер_3_1', 10, 94, 0, 'DB', 'Real', '0.0', 0),
(55, 'Недосып_Бункер_3_2', 10, 98, 0, 'DB', 'Real', '0.0', 0),
(56, 'Недосып_Бункер_4_1', 10, 102, 0, 'DB', 'Real', '0.0', 0),
(57, 'Недосып_Бункер_4_2', 10, 106, 0, 'DB', 'Real', '0.0', 0),
(58, 'Недосып_Вода1', 10, 110, 0, 'DB', 'Real', '0.0', 0),
(59, 'Недосып_Химия_1', 10, 114, 0, 'DB', 'Real', '0.0', 0),
(60, 'Недосып_Химия_2', 10, 118, 0, 'DB', 'Real', '0.0', 0),
(61, 'Недосып_Цемент_1', 10, 126, 0, 'DB', 'Real', '0.0', 0),
(62, 'Объем_текущей_загрузки_смесителя', 10, 142, 0, 'DB', 'Real', '0.0', 0),
(63, 'Масса_одного_куба_бетона', 10, 146, 0, 'DB', 'Real', '0.0', 0),
(64, 'Объем_партии', 10, 150, 0, 'DB', 'Real', '0.0', 0),
(65, 'Время_перемешивания_уст', 10, 266, 0, 'DB', 'DInt', '0', 0),
(66, 'Таймер_включения_горизонтального_конвейера', 10, 158, 0, 'DB', 'Real', '0.0', 0),
(67, 'Калибровка_ДК_Вес_Груза', 10, 198, 0, 'DB', 'Real', '0.0', 0),
(68, 'Калибровка_ДВ_Вес_Груза', 10, 202, 0, 'DB', 'Real', '0.0', 0),
(69, 'Калибровка_ДЦ_Вес_Груза', 10, 206, 0, 'DB', 'Real', '0.0', 0),
(70, 'Калибровка_ДХ_Вес_Груза', 10, 210, 0, 'DB', 'Real', '0.0', 0),
(71, 'Сброс_счётчик_цикла', 15, 7, 1, 'DB', 'Bool', 'false', 0),
(72, 'Калибровка_ДК_Аналоговый_сигнал', 19, 0, 0, 'DB', 'Int', '0', 0),
(73, 'Калибровка_ДВ_Аналоговый_сигнал', 19, 2, 0, 'DB', 'Int', '0', 0),
(74, 'Калибровка_ДЦ_Аналоговый_сигнал', 19, 6, 0, 'DB', 'Int', '0', 0),
(75, 'Калибровка_ДХ_Аналоговый_сигнал', 19, 4, 0, 'DB', 'Int', '0', 0),
(76, 'Ручное включение вибраторов', 0, 0, 0, 'DB', 'Bool', 'false', 0),
(77, 'Управление конвейером выгрузки', 15, 3, 3, 'DB', 'Bool', 'false', 0),
(78, 'Смазка валов смесителя', 15, 3, 4, 'DB', 'Bool', 'false', 0),
(79, 'Управление вибратором дозатора цемента', 15, 2, 4, 'DB', 'Bool', 'false', 0),
(80, 'Управление вибратором силоса цемента', 15, 4, 0, 'DB', 'Bool', 'false', 0),
(81, 'Управление фильтром цемента', 15, 4, 1, 'DB', 'Bool', 'false', 0),
(82, 'Управлением затвором сброса смесителя - Закрыть', 15, 3, 1, 'DB', 'Bool', 'false', 0),
(83, 'Ручное включение клапан воды', 15, 7, 0, 'DB', 'Bool', 'false', 0),
(84, 'Запуск перемешивание (кнопка)', 15, 0, 0, 'DB', 'Bool', 'false', 0),
(85, 'Стоп перемешивание (кнопка)', 15, 0, 1, 'DB', '0', 'false', 0),
(86, 'Недосып_Цемент_2', 10, 130, 0, 'DB', 'Real', '0', 0),
(87, 'Недосып_Цемент_шнек', 0, 0, 0, 'DB', 'Real', '0', 0),
(88, 'Недосып_Химия_1_насос', 0, 0, 0, 'DB', 'Real', '0', 0),
(89, 'Недосып_Вода_насос', 0, 0, 0, 'DB', 'Real', '0', 0),
(90, 'Комплектация завода - тип транспортера (0-лента 11лента-скип 12 лента-лента)', 34, 40, 0, 'DB', 'Int', '0', 0),
(91, 'Обнуление весов ДК (кнопка >0<)', 15, 8, 2, 'DB', 'Bool', 'false', 0),
(92, 'Обнуление весов ДВ (кнопка >0<)', 15, 8, 4, 'DB', 'Bool', 'false', 0),
(93, 'Обнуление весов ДХ (кнопка >0<)', 15, 8, 3, 'DB', 'Bool', 'false', 0),
(94, 'Обнуление весов ДЦ (кнопка >0<)', 15, 8, 5, 'DB', 'Bool', 'false', 0),
(95, 'Переход в импульсный режим Бункер 11, кг', 10, 162, 0, 'DB', 'Real', '0', 0),
(96, 'Переход в импульсный режим Бункер 12, кг', 10, 166, 0, 'DB', 'Real', '0', 0),
(97, 'Переход в импульсный режим Бункер 21, кг', 10, 170, 0, 'DB', 'Real', '0', 0),
(98, 'Переход в импульсный режим Бункер 22, кг', 10, 174, 0, 'DB', 'Real', '0', 0),
(99, 'Переход в импульсный режим Бункер 31, кг', 10, 178, 0, 'DB', 'Real', '0', 0),
(100, 'Переход в импульсный режим Бункер 32, кг', 10, 182, 0, 'DB', 'Real', '0', 0),
(101, 'Переход в импульсный режим Бункер 41, кг', 10, 186, 0, 'DB', 'Real', '0', 0),
(102, 'Переход в импульсный режим Бункер 42, кг', 10, 210, 0, 'DB', 'Real', '0', 0),
(103, 'Галочка авт. обнуление весов - ДК', 15, 9, 0, 'DB', 'Bool', 'false', 0),
(104, 'Галочка авт. обнуление весов - Вода', 15, 9, 2, 'DB', 'Bool', 'false', 0),
(105, 'Галочка авт. обнуление весов - Химия', 15, 9, 1, 'DB', 'Bool', 'false', 0),
(106, 'Галочка авт. обнуление весов - Цемент', 15, 9, 3, 'DB', 'Bool', 'false', 0),
(107, 'Галочка авт. очередь загрузки материалов', 15, 8, 7, 'DB', 'Bool', 'false', 0),
(108, 'Процент веса переключения в импульсный режим', 34, 80, 0, 'DB', 'Int', '0', 0),
(109, 'Т переход  вимпульсный (уставка)', 0, 0, 0, 'DB', 'DInt', '0', 0),
(110, 'Кнопка П/А ДК', 15, 10, 2, 'DB', 'Bool', 'false', 0),
(111, 'Кнопка П/А ДХ', 15, 10, 3, 'DB', 'Bool', 'false', 0),
(112, 'Кнопка П/А ДЦ', 15, 10, 4, 'DB', 'Bool', 'false', 0),
(113, 'Кнопка П/А ДВ', 15, 10, 5, 'DB', 'Bool', 'false', 0),
(114, 'Кнопка разгрузки скипа', 15, 11, 3, 'DB', 'Bool', 'false', 0),
(115, 'Ручное управление наклонного конвейера (транспортер лента-лента)', 15, 11, 4, 'DB', 'Bool', 'false', 0),
(116, 'Ручное управление дозатор фибры', 15, 15, 0, 'DB', 'Bool', 'false', 0),
(117, 'Калибровка аналоговый сигнал с преобр фибры', 19, 38, 0, 'DB', 'Int', '0', 0),
(118, 'Калибровка ДФ вес при нуле', 15, 14, 4, 'DB', 'Bool', 'false', 0),
(119, 'Калибровка ДФ вес с грузом', 15, 14, 5, 'DB', 'Bool', 'false', 0),
(120, 'Калибровка ДФ вес груза', 10, 442, 0, 'DB', 'Real', '0', 0),
(121, 'Калибровка ДФ кнопка >0< обнуление весов', 15, 14, 6, 'DB', 'Bool', 'false', 0),
(122, 'Рецепт Фибра', 10, 74, 0, 'DB', 'Real', '0', 0),
(123, 'Влажность бункер 11', 10, 230, 0, 'DB', 'Real', '0', 0),
(124, 'Влажность бункер 12', 10, 234, 0, 'DB', 'Real', '0', 0),
(125, 'Влажность бункер 21', 10, 238, 0, 'DB', 'Real', '0', 0),
(126, 'Влажность бункер 22', 10, 242, 0, 'DB', 'Real', '0', 0),
(127, 'Влажность бункер 31', 10, 246, 0, 'DB', 'Real', '0', 0),
(128, 'Влажность бункер 32', 10, 250, 0, 'DB', 'Real', '0', 0),
(129, 'Влажность бункер 41', 10, 254, 0, 'DB', 'Real', '0', 0),
(130, 'Влажность бункер 42', 10, 258, 0, 'DB', 'Real', '0', 0),
(131, 'Рецепт Вода 2', 0, 0, 0, 'DB', 'Real', '0', 0),
(132, 'Недосып Вода 2', 0, 0, 0, 'DB', 'Real', '0', 0),
(133, 'Ручное управление насос воды 2', 0, 0, 0, 'DB', 'Bool', 'false', 0),
(134, 'Ручное управление клапан воды 2', 0, 0, 0, 'DB', 'Bool', 'false', 0),
(135, 'Вода кнопка плюс', 15, 10, 0, 'DB', 'Bool', 'false', 0),
(136, 'Вода кнопка минус', 15, 10, 1, 'DB', 'Bool', 'false', 0),
(137, 'Кнопка реверс ДК', 15, 16, 6, 'DB', 'Bool', 'false', 0),
(138, 'Кнопка конвейер выгрузки ДК', 15, 16, 7, 'DB', 'Bool', 'false', 0),
(139, 'Сброс счетчик моточасы лента ДК', 15, 12, 1, 'DB', 'Bool', 'false', 0),
(140, 'Сброс счетчик моточасы ЛТ', 15, 12, 2, 'DB', 'Bool', 'false', 0),
(141, 'Сброс счетчик моточасы скип', 15, 12, 3, 'DB', 'Bool', 'false', 0),
(142, 'Сброс счетчик моточасы смесителя', 15, 12, 4, 'DB', 'Bool', 'false', 0),
(143, 'Сброс счетчик моточасы шнек 1', 15, 12, 5, 'DB', 'Bool', 'false', 0),
(144, 'Сброс счетчик моточасы шнек 2', 15, 12, 6, 'DB', 'Bool', 'false', 0),
(145, 'Сброс счетчик моточасы шнек 3', 15, 12, 7, 'DB', 'Bool', 'false', 0),
(146, 'Сброс счетчик моточасы КВ', 15, 13, 0, 'DB', 'Bool', 'false', 0),
(147, 'Сброс счетчик моточасы НВ', 15, 13, 1, 'DB', 'Bool', 'false', 0),
(148, 'Сброс счетчик моточасы НХ1', 15, 13, 2, 'DB', 'Bool', 'false', 0),
(149, 'Сброс счетчик моточасы НХ2', 15, 13, 3, 'DB', 'Bool', 'false', 0),
(150, 'Сброс счетчик моточасы маслостанции', 15, 13, 4, 'DB', 'Bool', 'false', 0),
(151, 'Ручное управление насоса ДВПЛ', 15, 17, 1, 'DB', 'Bool', 'false', 0),
(152, 'Сброс счетчика ДВПЛ', 15, 17, 2, 'DB', 'Bool', 'false', 0),
(153, 'Доза ДВПЛ набрана', 15, 17, 4, 'DB', 'Bool', 'false', 0),
(154, 'Опция корректировки рецепта', 15, 17, 5, 'DB', 'Bool', 'false', 0),
(155, 'Галочка - активация автокорректировки недосыпа шнека', 15, 17, 6, 'DB', 'Bool', 'false', 0),
(156, 'Галочка Авторазгрузка', 15, 6, 5, 'DB', 'Bool', 'false', 0),
(157, 'Галочка импульсная разгрузка смесителя', 15, 6, 6, 'DB', 'Bool', 'false', 0),
(158, 'Выбор силос 1-2 (false - 1, true - 2)', 15, 6, 7, 'DB', 'Bool', 'false', 0),
(159, 'Галочка активации вибратора бункера 11', 34, 82, 0, 'DB', 'Int', '0', 0),
(160, 'Галочка активации вибратора бункера 12', 34, 84, 0, 'DB', 'Int', '0', 0),
(161, 'Галочка активации вибратора бункера 21', 34, 86, 0, 'DB', 'Int', '0', 0),
(162, 'Галочка активации вибратора бункера 22', 34, 88, 0, 'DB', 'Int', '0', 0),
(163, 'Галочка активации вибратора бункера 31', 34, 90, 0, 'DB', 'Int', '0', 0),
(164, 'Галочка активации вибратора бункера 32', 34, 92, 0, 'DB', 'Int', '0', 0),
(165, 'Галочка активации вибратора бункера 41', 34, 94, 0, 'DB', 'Int', '0', 0),
(166, 'Галочка активации вибратора бункера 42', 34, 96, 0, 'DB', 'Int', '0', 0),
(167, 'Галочка - включена корректировка недосыпа на инертных', 15, 18, 6, 'DB', 'Bool', 'false', 0),
(168, 'Кнопка П/А запуск вибратор фибры', 15, 15, 1, 'DB', 'Bool', 'false', 0),
(169, 'Галочка - использовать дозатор фибры', 15, 15, 2, 'DB', 'Bool', 'false', 0),
(170, 'ДСХ_Время_работы_1_цикла_шнека', 34, 122, 0, 'DB', 'DInt', '0', 0),
(170, 'ДСХ_Калибровка_вес_доз_за_время', 10, 570, 0, 'DB', 'Real', '0', 0),
(172, 'Кнопка_вкл_шнек_ДСХ_1_импульс', 15, 19, 5, 'DB', 'Bool', 'false', 0),
(173, 'Рецепт_ДСХ', 10, 574, 0, 'DB', 'Real', '0', 0),
(174, 'Ручное управление шнек ДСх', 15, 19, 3, 'DB', 'Bool', 'false', 0),
(175, 'Кнопка применить рецепт - руч', 15, 18, 3, 'DB', 'Bool', 'false', 0),
(176, 'Опция - включить корректировку влажности по датчику в инерт бункере', 15, 19, 7, 'DB', 'Bool', 'false', 0),
(177, 'Номер бункера где установлен датчик влажности', 34, 130, 0, 'DB', 'Int', '0', 0),
(178, 'Есть датчик закрытого положения на шибере смесителя', 15, 18, 1, 'DB', 'Bool', 'false', 0),
(179, 'Есть датчик открытого положения на шибере смесителя', 15, 18, 2, 'DB', 'Bool', 'false', 0),
(180, 'Ручное включение вибратора воронки смесителя', 15, 18, 4, 'DB', 'Bool', 'false', 0),
(181, 'Опция - корректировать доп бункер с песком без датчика влажности', 15, 21, 1, 'DB', 'Bool', 'false', 0),
(182, 'Номер бункера с песком без датчика влажности для корректировки по датчику из другого бункера', 34, 138, 0, 'DB', 'Int', '0', 0),
(183, 'Калиб_ДВПЛ_кнопка_калибровка', 15, 22, 7, 'DB', 'Bool', 'false', 0),
(184, 'ДВПЛ Количество воды на 1 импульс', 10, 606, 0, 'DB', 'Real', '0', 0),
(185, 'Доза ДВПЛ сохранена', 15, 22, 5, 'DB', 'Bool', 'false', 0),
(186, 'ДВПЛ заданная влажность по рецепту', 10, 490, 5, 'DB', 'Real', '0', 0),
(187, 'Опция открытие по времени или по датчику', 15, 21, 4, 'DB', 'Bool', 'false', 0),
(188, 'Опция использовать верхний скиповый датчик как аварийный', 15, 21, 3, 'DB', 'Bool', 'false', 0),
(189, 'Кнопка - открыть только  до середины шибер', 15, 23, 1, 'DB', 'Bool', 'false', 0),
(190, 'Сброс всех ручных сигналов по выходам', 15, 23, 5, 'DB', 'Bool', 'false', 0);
