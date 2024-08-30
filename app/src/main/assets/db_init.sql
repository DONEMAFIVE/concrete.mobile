CREATE TABLE IF NOT EXISTS 'config' (
'id' INTEGER NOT NULL,
'parameter' TEXT NOT NULL,
'value' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'current' (
'orderId' INTEGER NOT NULL,
'recepieId' INTEGER NOT NULL,
'state' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'factory_complectation' (
'id' INTEGER NOT NULL,
'parameter' TEXT NOT NULL,
'value' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'inert_balance' (
'id' INTEGER NOT NULL,
'parameter' TEXT NOT NULL,
'value' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'inert_upload' (
'id' INTEGER NOT NULL,
'date' TEXT NOT NULL,
'time' TEXT NOT NULL,
'buncker1' FLOAT NOT NULL,
'buncker2' FLOAT NOT NULL,
'buncker3' FLOAT NOT NULL,
'buncker4' FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'organizations' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
'name' TEXT NOT NULL,
'fullname' TEXT NOT NULL,
'persona' INT NOT NULL,
'inn' TEXT NOT NULL,
'kpp' TEXT NOT NULL,
'okpo' TEXT NOT NULL,
'phone' TEXT NOT NULL,
'address' TEXT NOT NULL,
'comment' TEXT NOT NULL,
'contactName' TEXT NOT NULL,
'contactPhone' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'transporters' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
'regNumberAuto' TEXT NOT NULL,
'organizationName' TEXT NOT NULL,
'persona' INT NOT NULL,
'inn' TEXT NOT NULL,
'driverName' TEXT NOT NULL,
'markAuto' TEXT NOT NULL,
'phone' TEXT NOT NULL,
'address' TEXT NOT NULL,
'comment' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'requisites' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
'organizationType' TEXT NOT NULL,
'organizationName' TEXT NOT NULL,
'inn' TEXT NOT NULL,
'address' TEXT NOT NULL,
'headName' TEXT NOT NULL,
'phone' TEXT NOT NULL,
'fax' TEXT NOT NULL,
'site' TEXT NOT NULL,
'email' TEXT NOT NULL,
'comment' TEXT NOT NULL,
'loadAddress' TEXT NOT NULL,
'dispatcherName' TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'mixes' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,
'nameOrder' TEXT NOT NULL,
'numberOrder' INTEGER NOT NULL,
'date' TEXT NOT NULL,
'time' TEXT NOT NULL,
'organization' TEXT NOT NULL,
'organizationID' INTEGER NOT NULL,
'transporter' TEXT NOT NULL,
'transporterID' INTEGER NOT NULL,
'recepie' TEXT NOT NULL,
'recepieID' INTEGER NOT NULL,
'mixCounter' INTEGER NOT NULL,
'completeCapacity' FLOAT NOT NULL,
'totalCapacity' FLOAT NOT NULL,
'silos1' FLOAT NOT NULL,
'silos2' FLOAT NOT NULL,
'bunker11' FLOAT NOT NULL,
'bunker12' FLOAT NOT NULL,
'bunker21' FLOAT NOT NULL,
'bunker22' FLOAT NOT NULL,
'bunker31' FLOAT NOT NULL,
'bunker32' FLOAT NOT NULL,
'bunker41' FLOAT NOT NULL,
'bunker42' FLOAT NOT NULL,
'water1' FLOAT NOT NULL,
'water2' FLOAT NOT NULL,
'dwpl' FLOAT NOT NULL,
'chemy1' FLOAT NOT NULL,
'chemy2' FLOAT NOT NULL,
'uploadAddress' TEXT NOT NULL,
'amountConcrete' FLOAT NOT NULL,
'paymentOption' TEXT NOT NULL,
'operator' TEXT NOT NULL,
'loadingTime' TEXT NOT NULL
);

INSERT INTO 'mixes' ('id','nameOrder','numberOrder','date','time','organization','organizationID',
'transporter','transporterID','recepie','recepieID','mixCounter','completeCapacity','totalCapacity',
'silos1','silos2','bunker11','bunker12','bunker21','bunker22','bunker31','bunker32','bunker41','bunker42',
'water1','water2','dwpl','chemy1','chemy2','uploadAddress','amountConcrete','paymentOption','operator','loadingTime') VALUES (
0, '-', 0, '05.02.1999', '-', '-', 0, '-', 0, '-', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '-', 0, '-', '-', '-');


CREATE TABLE IF NOT EXISTS 'recepies' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
'date' TEXT NOT NULL,
'time' TEXT NOT NULL,
'name' TEXT NOT NULL,
'mark' TEXT NOT NULL,
'classPie' TEXT NOT NULL,
'description' TEXT NOT NULL,
'bunckerRecepie11' FLOAT NOT NULL,
'bunckerRecepie12' FLOAT NOT NULL,
'bunckerRecepie21' FLOAT NOT NULL,
'bunckerRecepie22' FLOAT NOT NULL,
'bunckerRecepie31' FLOAT NOT NULL,
'bunckerRecepie32' FLOAT NOT NULL,
'bunckerRecepie41' FLOAT NOT NULL,
'bunckerRecepie42' FLOAT NOT NULL,
'bunckerShortage11' FLOAT NOT NULL,
'bunckerShortage12' FLOAT NOT NULL,
'bunckerShortage21' FLOAT NOT NULL,
'bunckerShortage22' FLOAT NOT NULL,
'bunckerShortage31' FLOAT NOT NULL,
'bunckerShortage32' FLOAT NOT NULL,
'bunckerShortage41' FLOAT NOT NULL,
'bunckerShortage42' FLOAT NOT NULL,
'chemyRecepie1' FLOAT NOT NULL,
'chemyShortage1' FLOAT NOT NULL,
'chemyShortage2' FLOAT NOT NULL,
'water1Recepie' FLOAT NOT NULL,
'water2Recepie' FLOAT NOT NULL,
'water1Shortage' FLOAT NOT NULL,
'water2Shortage' FLOAT NOT NULL,
'silosRecepie1' FLOAT NOT NULL,
'silosRecepie2' FLOAT NOT NULL,
'silosShortage1' FLOAT NOT NULL,
'silosShortage2' FLOAT NOT NULL,
'humidity11' FLOAT NOT NULL,
'humidity12' FLOAT NOT NULL,
'humidity21' FLOAT NOT NULL,
'humidity22' FLOAT NOT NULL,
'humidity31' FLOAT NOT NULL,
'humidity32' FLOAT NOT NULL,
'humidity41' FLOAT NOT NULL,
'humidity42' FLOAT NOT NULL,
'uniNumber' TEXT NOT NULL,
'timeMix' INTEGER NOT NULL,
'chemy2Recepie' FLOAT NOT NULL,
'chemy3Recepie' FLOAT NOT NULL,
'chemy2Shortage' FLOAT NOT NULL,
'chemy3Shortage' FLOAT NOT NULL,
'pathToHumidity' INTEGER NOT NULL,
'preDosingWaterPercent' INTEGER NOT NULL,
'fibra' FLOAT NOT NULL,
'dDryCh' FLOAT NOT NULL,
'amperageFluidity' FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS 'users' (
'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
'userName' TEXT NOT NULL,
'dateCreation' TEXT NOT NULL,
'login' TEXT NOT NULL,
'password' TEXT NOT NULL,
'accessLevel' INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS 'orders' (
  'id' INTEGER PRIMARY KEY,
  'nameOrder' TEXT NOT NULL,
  'numberOrder' TEXT NOT NULL,
  'date' TEXT NOT NULL,
  'completionDate' TEXT NOT NULL,
  'organizationName' TEXT NOT NULL,
  'organizationID' INTEGER NOT NULL,
  'transporter' TEXT NOT NULL,
  'transporterID' INTEGER NOT NULL,
  'recepie' TEXT NOT NULL,
  'recepieID' INTEGER NOT NULL,
  'totalCapacity' float NOT NULL,
  'maxMixCapacity' float NOT NULL,
  'totalMixCounter' INTEGER NOT NULL,
  'markConcrete' TEXT NOT NULL,
  'classConcrete' TEXT NOT NULL,
  'pieBuncker11' FLOAT NOT NULL,
  'pieBuncker12' FLOAT NOT NULL,
  'pieBuncker21' FLOAT NOT NULL,
  'pieBuncker22' FLOAT NOT NULL,
  'pieBuncker31' FLOAT NOT NULL,
  'pieBuncker32' FLOAT NOT NULL,
  'pieBuncker41' FLOAT NOT NULL,
  'pieBuncker42' FLOAT NOT NULL,
  'pieChemy1' FLOAT NOT NULL,
  'pieChemy2' FLOAT NOT NULL,
  'pieWater1' FLOAT NOT NULL,
  'pieWater2' FLOAT NOT NULL,
  'pieSilos1' FLOAT NOT NULL,
  'pieSilos2' FLOAT NOT NULL,
  'shortageBuncker11' FLOAT NOT NULL,
  'shortageBuncker12' FLOAT NOT NULL,
  'shortageBuncker21' FLOAT NOT NULL,
  'shortageBuncker22' FLOAT NOT NULL,
  'shortageBuncker31' FLOAT NOT NULL,
  'shortageBuncker32' FLOAT NOT NULL,
  'shortageBuncker41' FLOAT NOT NULL,
  'shortageBuncker42' FLOAT NOT NULL,
  'shortageChemy1' FLOAT NOT NULL,
  'shortageChemy2' FLOAT NOT NULL,
  'shortageWater1' FLOAT NOT NULL,
  'shortageWater2' FLOAT NOT NULL,
  'shortageSilos1' FLOAT NOT NULL,
  'shortageSilos2' FLOAT NOT NULL,
  'countBuncker11' FLOAT NOT NULL,
  'countBuncker12' FLOAT NOT NULL,
  'countBuncker21' FLOAT NOT NULL,
  'countBuncker22' FLOAT NOT NULL,
  'countBuncker31' FLOAT NOT NULL,
  'countBuncker32' FLOAT NOT NULL,
  'countBuncker41' FLOAT NOT NULL,
  'countBuncker42' FLOAT NOT NULL,
  'countChemy1' FLOAT NOT NULL,
  'countChemy2' FLOAT NOT NULL,
  'countWater1' FLOAT NOT NULL,
  'countWater2' FLOAT NOT NULL,
  'countSilos1' FLOAT NOT NULL,
  'countSilos2' FLOAT NOT NULL,
  'state' INTEGER NOT NULL,
  'currentMixCount' INTEGER NOT NULL,
  'uploadAddress' TEXT NOT NULL,
  'amountConcrete' TEXT NOT NULL,
  'paymentOption' TEXT NOT NULL,
  'operator' TEXT NOT NULL,
  'comment' TEXT NOT NULL
);

INSERT INTO 'users' ('id', 'userName', 'dateCreation', 'login', 'password', 'accessLevel') VALUES
(1, 'Оператор', '01.09.2022', 'operator', '68d3460cda2cc5ff11b2511a44aa213abc083f93e6719bbd00fe9f61a86a8f26', 3),
(2, 'Диспетчер', '01.09.2022', 'dispatcher', 'c3e36aed52f1ba2d3b832711459cb3305c7f463b707003b7f9b6dc7b8563eaa6', 1),
(3, 'Пуско-наладчик', '01.09.2022', 'engineer', '6eea2d12d6e4d99b6ba1623c7574332024e91021ad56efbde4d92ee068ae5fc7', 2),
(4, 'Администратор', '01.09.2022', 'admin', '7a3ba4c33876d50db08a1cc613e649351ed213a326a328ab9a00e5e2d621b978', 3);

INSERT INTO 'organizations' ('id', 'name', 'fullname', 'persona', 'inn', 'kpp','okpo','phone','address','comment','contactName','contactPhone') VALUES
(1, 'руководитель организации', 'название организации', 0, '1234567890', '12345', '6789', '+79090909090', 'адрес компании','комментарий', 'контактное лицо', 'телефон контактного лица');

INSERT INTO 'transporters' ('id', 'regNumberAuto', 'organizationName', 'persona', 'inn', 'driverName','markAuto','phone','address','comment') VALUES
(1, 'рег номер', 'название организации', 0, '1234567890', 'водитель', 'марка авто', '+79090909090', 'адрес','комментарий');

INSERT INTO 'requisites' ('id', 'organizationType', 'organizationName', 'inn', 'address', 'headName', 'phone', 'fax', 'site', 'email', 'comment', 'loadAddress', 'dispatcherName') VALUES
(1, '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-', '-');

INSERT OR IGNORE INTO  'factory_complectation'('id', 'parameter', 'value') VALUES
(1, 'inertBunckerCounter', '2'),
(2, 'comboBunckerOption', 'false'),
(3, 'silosCounter', '1'),
(4, 'water2', 'false'),
(5, 'humidityMixerSensor', 'false'),
(6, 'chemyCounter', '1'),
(7, 'transporterType', '11'),
(8, 'fibraOption', 'false'),
(9, 'dropConveyor', 'false'),
(10, 'mixCapacity', '1.0'),
(11, 'hydroGate', 'false'),
(12, 'amperageSensor', 'false'),
(13, 'dwpl', 'false'),
(14, 'dDryCHDispenser', 'false'),
(15, 'vibroFunnelMixer', 'false'),
(16, 'humSensorInert', 'false'),
(17, 'planetarTypeMixer', 'false'),
(18, 'reverseModuleFactory', 'false');

INSERT OR IGNORE INTO 'config' ('id', 'parameter', 'value') VALUES
(1, 'first_run', 'true'),
(2, 'plc_ip', '192.168.250.10'),
(3, 'first_run', 'true'),
(4, 'yandex_option', 'false'),
(5, 'time_sync', '10:00'),
(6, 'server_update', '188.225.42.106'),
(7, 'localization_level', 'ru'),
(8, 'hardkey', '38074eb8063d86c07d8da55703a8ad592d0d76291c14ea4f6d32ee220f3a72e32f6ba566480bbadad835ec881e60f9ab'),
(9, 'productionNumber', '0'),
(10, 'exchange_level', '0'),
(11, 'scada_ip', '192.168.250.189'),
(12, 'rest_server_ip', '188.225.42.106'),
(13, 'hmi_ip', '192.168.250.9'),
(14, 'buncker11', 'Бункер 1'),
(15, 'buncker12', '-'),
(16, 'buncker21', 'Бункер 2'),
(17, 'buncker22', '-'),
(18, 'buncker31', 'Бункер 3'),
(19, 'buncker32', '-'),
(20, 'buncker41', 'Бункер 4'),
(21, 'buncker42', '-'),
(22, 'chemy1', 'Химия'),
(23, 'chemy2', '-'),
(24, 'chemy3', '-'),
(25, 'silos1', 'Цемент'),
(26, 'silos2', '-'),
(27, 'silos3', '-'),
(28, 'silos4', '-'),
(29, 'water1', 'Вода'),
(30, 'water2', '-');

INSERT OR IGNORE INTO 'current' ('orderId', 'recepieId', 'state') VALUES
(0, 1, 'idle');

INSERT OR IGNORE INTO 'inert_balance' ('id', 'parameter', 'value') VALUES
(1, 'buncker1_millage', '0.0'),
(2, 'buncker2_millage', '0.0'),
(3, 'buncker3_millage', '0.0'),
(4, 'buncker4_millage', '0.0'),
(5, 'water_millage', '0.0'),
(6, 'chemy1_millage', '0.0'),
(7, 'chemy2_millage', '0.0'),
(8, 'chemy3_millage', '0.0'),
(9, 'silos1_millage', '0.0'),
(10, 'silos2_millage', '0.0'),
(11, 'silos3_millage', '0.0'),
(12, 'silos4_millage', '0.0'),
(13, 'inert_storage1', '0.0'),
(14, 'inert_storage2', '0.0'),
(15, 'inert_storage3', '0.0'),
(16, 'inert_storage4', '0.0'),
(17, 'set_storage_buncker1', '4'),
(18, 'set_storage_buncker2', '4'),
(19, 'set_storage_buncker3', '4'),
(20, 'set_storage_buncker4', '4');

