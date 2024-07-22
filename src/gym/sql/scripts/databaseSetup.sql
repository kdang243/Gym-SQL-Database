DROP TABLE Requires;
DROP TABLE Uses;
DROP TABLE Trains;
DROP TABLE WorksOn;
DROP TABLE Leads;
DROP TABLE Utilizes;
DROP TABLE OccursIn;
DROP TABLE Attends;
DROP TABLE PersonalTrainer2;
DROP TABLE PersonalTrainer1;
DROP TABLE Staff2;
DROP TABLE Staff1;
DROP TABLE FitnessClass2;
DROP TABLE FitnessClass1;
DROP TABLE Session2;
DROP TABLE Session1;
DROP TABLE Equipment2;
DROP TABLE Equipment1;
DROP TABLE Area;
DROP TABLE Floor;
DROP TABLE Member;
DROP TABLE Clearance;

CREATE TABLE Clearance (
	clearanceLevel INTEGER PRIMARY KEY
);

CREATE TABLE Member (
	MID INTEGER,
	name VARCHAR(20),
	phone VARCHAR(14),
    email VARCHAR(30),
    clearanceLevel INTEGER NOT NULL,
    PRIMARY KEY (MID),
    FOREIGN KEY (clearanceLevel) REFERENCES Clearance(clearanceLevel) 
    ON DELETE SET NULL
);

CREATE TABLE Floor (
	FNumber INTEGER,
	capacity INTEGER,
	PRIMARY KEY (FNumber)
);

CREATE TABLE Area (
	ANumber INTEGER,
   	AType VARCHAR(20),
    FNumber INTEGER NOT NULL,
    PRIMARY KEY (ANumber, AType),
	FOREIGN KEY (FNumber) REFERENCES Floor(FNumber)
	ON DELETE CASCADE
  );

CREATE TABLE Equipment1 (
	name VARCHAR(20),
	usages VARCHAR(20),
	PRIMARY KEY (name)
);

CREATE TABLE Equipment2 (
	EID INTEGER,
    name VARCHAR(20),
    condition VARCHAR(20),
	ANumber INTEGER,
    AType VARCHAR(20) NOT NULL,
    PRIMARY KEY (EID),
    FOREIGN KEY (ANumber, AType) REFERENCES Area(ANumber, AType)
    ON DELETE CASCADE,
    FOREIGN KEY (name) REFERENCES Equipment1(name)
    ON DELETE CASCADE
  );

CREATE TABLE Session1 (
	startTime DATE,
    endTime DATE,
    duration INTEGER,
    PRIMARY KEY (startTime, endTime)
  );

CREATE TABLE Session2 (
	sessionDate DATE,
    startTime DATE,
    endtime DATE,
    PRIMARY KEY (sessionDate, startTime),
	FOREIGN KEY (startTime, endTime) REFERENCES Session1(startTime, endTime)
	ON DELETE CASCADE
  );

CREATE TABLE FitnessClass1 (
    startTime DATE,
    endTime DATE,
    duration INTEGER,
    PRIMARY KEY (startTime, endTime)
  );

CREATE TABLE FitnessClass2 (
	FCType VARCHAR(15),
    sessionDate DATE,
    startTime DATE,
    endTime DATE,
    PRIMARY KEY (sessionDate, startTime),
	FOREIGN KEY (startTime, endTime) REFERENCES FitnessClass1(startTime, endTime)
	ON DELETE CASCADE
  );

CREATE TABLE Staff1 (
	SType VARCHAR(20),
    workingHours VARCHAR(10),
    PRIMARY KEY (SType)
  );

CREATE TABLE Staff2 (
	SID INTEGER,
    name VARCHAR(20),
    SType VARCHAR(20),
    PRIMARY KEY (SID),
	FOREIGN KEY (SType) REFERENCES Staff1(SType)
	ON DELETE CASCADE
  );

CREATE TABLE PersonalTrainer1 (
	SType VARCHAR(20),
    workingHours VARCHAR(10),
    PRIMARY KEY (SType)
);

CREATE TABLE PersonalTrainer2 (
	SID INTEGER,
   	name VARCHAR(20),
    SType VARCHAR(20),
    program VARCHAR(15),
    PRIMARY KEY (SID),
	FOREIGN KEY (SType) REFERENCES PersonalTrainer1(SType)
	ON DELETE CASCADE
  );

CREATE TABLE Attends (
	MID INTEGER,
	sessionDate DATE,
	startTime DATE,
	locker INTEGER,
	PRIMARY KEY (MID, sessionDate, startTime),
	FOREIGN KEY (MID) REFERENCES Member(MID)
	ON DELETE CASCADE,
	FOREIGN KEY (sessionDate, startTime) REFERENCES Session2(sessionDate, startTime)
	ON DELETE CASCADE
);

CREATE TABLE OccursIn (
	sessionDate DATE,
	startTime DATE,
	ANumber INTEGER,
	AType VARCHAR(20),
	PRIMARY KEY (sessionDate, startTime, ANumber, AType),
	FOREIGN KEY (sessionDate, startTime) REFERENCES FitnessClass2(sessionDate, startTime)         
	ON DELETE CASCADE,
	FOREIGN KEY (ANumber, AType) REFERENCES Area(ANumber, AType)
	ON DELETE CASCADE
);

CREATE TABLE Utilizes (
	EID INTEGER,
	sessionDate DATE,
	startTime DATE,
	PRIMARY KEY (EID, sessionDate, startTime),
	FOREIGN KEY (EID) REFERENCES Equipment2(EID)
	ON DELETE CASCADE,
	FOREIGN KEY (sessionDate, startTime) REFERENCES FitnessClass2(sessionDate, startTime)         
	ON DELETE CASCADE
);

CREATE TABLE Leads (
	SID INTEGER,
	sessionDate DATE,
	startTime DATE,
	PRIMARY KEY (SID, sessionDate, startTime),
	FOREIGN KEY (SID) REFERENCES Staff2(SID)
	ON DELETE CASCADE,
	FOREIGN KEY (sessionDate, startTime) REFERENCES FitnessClass2(sessionDate, startTime)         
	ON DELETE CASCADE
);

CREATE TABLE WorksOn (
	SID INTEGER,
	FNumber INTEGER,
	PRIMARY KEY (SID, FNumber),
	FOREIGN KEY (SID) REFERENCES Staff2(SID)
	ON DELETE CASCADE,
	FOREIGN KEY (FNumber) REFERENCES Floor(FNumber)
	ON DELETE CASCADE
);

CREATE TABLE Trains (
	MID INTEGER,
	SID INTEGER,
	PRIMARY KEY (MID, SID),
	FOREIGN KEY (MID) REFERENCES Member(MID)
	ON DELETE CASCADE,
	FOREIGN KEY (SID) REFERENCES PersonalTrainer2
	ON DELETE CASCADE
);

CREATE TABLE Uses (
	MID INTEGER,
	EID INTEGER,
	PRIMARY KEY (MID, EID),
	FOREIGN KEY (MID) REFERENCES Member(MID)
	ON DELETE CASCADE,
	FOREIGN KEY (EID) REFERENCES Equipment2(EID)
	ON DELETE CASCADE
);

CREATE TABLE Requires (
	clearanceLevel INTEGER,
	FNumber INTEGER,
	PRIMARY KEY (clearanceLevel, FNumber),
	FOREIGN KEY (clearanceLevel) REFERENCES Clearance(clearanceLevel)
	ON DELETE CASCADE,
	FOREIGN KEY (FNumber) REFERENCES Floor(FNumber)
	ON DELETE CASCADE
);

INSERT
INTO 		Clearance(clearanceLevel)
VALUES	('1');

INSERT
INTO 		Clearance(clearanceLevel)
VALUES	('2');

INSERT
INTO 		Clearance(clearanceLevel)
VALUES	('3');

INSERT
INTO 		Member(MID, name, phone, email, clearanceLevel)
VALUES	('1','Kevin Dang', '(778) 123 1234', 'kevindang@gmail.com', '1');

INSERT
INTO 		Member(MID, name, phone, email, clearanceLevel)
VALUES	('2', 'Carter Yam', '(778) 234 2345', 'carteryam@gmail.com', '2');

INSERT
INTO 		Member(MID, name, phone, email, clearanceLevel)
VALUES	('3', 'Kushagra Sethi', '(778) 345 3456', 'kushagrasethi@gmail.com', '3');

INSERT
INTO 		Member(MID, name, phone, email, clearanceLevel)
VALUES	('4', 'John Smith', '(778) 456 4567', 'johnsmith@gmail.com', '3');

INSERT
INTO 		Member(MID, name, phone, email, clearanceLevel)
VALUES	('5', 'Jane Doe', '(778) 567 5678', 'janedoe@gmail.com', '1');

INSERT
INTO 		Member(MID, name, phone, email, clearanceLevel)
VALUES	('6', 'Pascal Siakam', '(778) 678 6789', 'pascalsiakam@gmail.com', '2');

INSERT
INTO 		Floor(FNumber, capacity)
VALUES	('1', '75');

INSERT
INTO 		Floor(FNumber, capacity)
VALUES	('2', '70');

INSERT
INTO 		Floor(FNumber, capacity)
VALUES	('3', '65');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('1', 'Body Building', '1');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('2', 'Cardio', '2');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('2', 'Free Weights', '1');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('3', 'Turf', '2');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('4', 'Squat Racks', '1');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('5', 'Machines', '1');

INSERT
INTO 		Area(ANumber, AType, FNumber)
VALUES	('3', 'Calisthenics', '3');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Lat pulldown', 'Back muscle');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Squat rack', 'Legs');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Bench Press Rack', 'Chest');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Treadmill', 'Cardio');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Elliptical', 'Cardio');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Yoga mats', 'Yoga exercises');

INSERT
INTO 		EQUIPMENT1(name, usages)
VALUES	('Calf raise machine', 'Calves');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('1', 'Lat pulldown', 'Brand spankin new', '1', 'Body Building');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('2', 'Squat rack', 'Brand spankin new', '1', 'Body Building');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('3', 'Bench Press Rack', 'Used', '1', 'Body Building');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('4', 'Treadmill', 'Rusty', '2', 'Cardio');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('7', 'Elliptical', 'Like new', '2', 'Cardio');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('5', 'Yoga mats', 'Brand spankin new', '3', 'Calisthenics');

INSERT
INTO 		EQUIPMENT2(EID, name, condition, ANumber, AType)
VALUES	('6', 'Calf raise machine', 'Used', '1', 'Body Building');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 14:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:56:22', 'YYYY-MM-DD HH24:MI:SS'), '5597');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 02:01:52', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 04:11:57', 'YYYY-MM-DD HH24:MI:SS'), '7805');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 20:13:48', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:55:43', 'YYYY-MM-DD HH24:MI:SS'), '2515');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 10:08:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 11:46:21', 'YYYY-MM-DD HH24:MI:SS'), '5901');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 15:33:56', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:35:06', 'YYYY-MM-DD HH24:MI:SS'), '3670');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 15:30:14', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:57:45', 'YYYY-MM-DD HH24:MI:SS'), '5251');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 15:29:30', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:42:11', 'YYYY-MM-DD HH24:MI:SS'), '4361');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 18:11:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:01:34', 'YYYY-MM-DD HH24:MI:SS'), '6634');

INSERT
INTO 		Session1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), '7200');

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2022-09-22 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 14:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:56:22', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2022-10-21 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 02:01:52', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 04:11:57', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2022-11-20 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:13:48', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:55:43', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2022-12-19 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 10:08:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 11:46:21', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2023-01-18 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:33:56', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:35:06', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2023-01-19 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:30:14', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:57:45', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2023-01-20 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:29:30', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:42:11', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2023-01-20 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:11:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:01:34', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		Session2(sessionDate, startTime, endTime)
VALUES	(TO_DATE('2023-01-22 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		FITNESSCLASS1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 15:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 17:23:05', 'YYYY-MM-DD HH24:MI:SS'), '2401');

INSERT
INTO 		FITNESSCLASS1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 16:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:23:05', 'YYYY-MM-DD HH24:MI:SS'), '2402');

INSERT
INTO 		FITNESSCLASS1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 17:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 19:23:05', 'YYYY-MM-DD HH24:MI:SS'), '2403');

INSERT
INTO 		FITNESSCLASS1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 18:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:23:05', 'YYYY-MM-DD HH24:MI:SS'), '2404');

INSERT
INTO 		FITNESSCLASS1(startTime, endTime, duration)
VALUES	(TO_DATE('2000-01-01 19:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 21:23:05', 'YYYY-MM-DD HH24:MI:SS'), '2405');

INSERT
INTO 		FITNESSCLASS2(FCType, sessionDate, startTime, endTime)
VALUES	('Cardio', TO_DATE('2022-08-14 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 17:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		FITNESSCLASS2(FCType, sessionDate, startTime, endTime)
VALUES	('Zumba', TO_DATE('2022-08-15 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		FITNESSCLASS2(FCType, sessionDate, startTime, endTime)
VALUES	('Yoga', TO_DATE('2022-08-16 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 17:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 19:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		FITNESSCLASS2(FCType, sessionDate, startTime, endTime)
VALUES	('HIIT', TO_DATE('2022-08-17 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		FITNESSCLASS2(FCType, sessionDate, startTime, endTime)
VALUES	('Flow', TO_DATE('2022-08-18 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 19:23:05', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 21:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Full-Time Trainer', '9am-5pm');

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Part-Time Trainer', '1pm-5pm');

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Auxiliary Trainer', '1pm-5pm');

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Yoga Master', '7pm-9pm');

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Celebrity Trainer', '9am-5pm');

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Receptionist', '7pm-9pm');

INSERT
INTO 		STAFF1(SType, workingHours)
VALUES	('Custodian', '7pm-9pm');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('1', 'Ice Cube', 'Full-Time Trainer');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('2', 'Kevin Hart', 'Part-Time Trainer');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('3', 'Rihanna', 'Auxiliary Trainer');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('4', 'The Rock', 'Yoga Master');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('5', 'Ryan Gosling', 'Celebrity Trainer');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('6', 'Ice Spice', 'Receptionist');

INSERT
INTO 		STAFF2(SID, name, SType)
VALUES	('7', 'Rebecca Black', 'Custodian');

INSERT
INTO 		PERSONALTRAINER1(SType, workingHours)
VALUES	('Full-Time Trainer', '9am-5pm');

INSERT
INTO 		PERSONALTRAINER1(SType, workingHours)
VALUES	('Part-Time Trainer', '1pm-5pm');

INSERT
INTO 		PERSONALTRAINER1(SType, workingHours)
VALUES	('Auxiliary Trainer', '1pm-5pm');

INSERT
INTO 		PERSONALTRAINER1(SType, workingHours)
VALUES	('Yoga Master', '7pm-9pm');

INSERT
INTO 		PERSONALTRAINER1(SType, workingHours)
VALUES	('Celebrity Trainer', '7pm-9pm');

INSERT
INTO 		PERSONALTRAINER2(SID, name, SType, program)
VALUES	('1', 'Ice Cube', 'Full-Time Trainer', 'Cardio');

INSERT
INTO 		PERSONALTRAINER2(SID, name, SType, program)
VALUES	('2', 'Kevin Hart', 'Part-Time Trainer', 'Free Weights');

INSERT
INTO 		PERSONALTRAINER2(SID, name, SType, program)
VALUES	('3', 'Rihanna', 'Auxiliary Trainer', 'Free Weights');

INSERT
INTO 		PERSONALTRAINER2(SID, name, SType, program)
VALUES	('4', 'The Rock', 'Yoga Master', 'Yoga');

INSERT
INTO 		PERSONALTRAINER2(SID, name, SType, program)
VALUES	('5', 'Ryan Gosling', 'Celebrity Trainer', 'Spin');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('1', TO_DATE('2022-09-22 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 14:23:05', 'YYYY-MM-DD HH24:MI:SS'), '23');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('1', TO_DATE('2022-10-21 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 02:01:52', 'YYYY-MM-DD HH24:MI:SS'), '24');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('3', TO_DATE('2022-11-20 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 20:13:48', 'YYYY-MM-DD HH24:MI:SS'), '25');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('6', TO_DATE('2022-12-19 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 10:08:00', 'YYYY-MM-DD HH24:MI:SS'), '26');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('2', TO_DATE('2023-01-18 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:33:56', 'YYYY-MM-DD HH24:MI:SS'), '27');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('2', TO_DATE('2023-01-19 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:30:14', 'YYYY-MM-DD HH24:MI:SS'), '41');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('2', TO_DATE('2023-01-20 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:29:30', 'YYYY-MM-DD HH24:MI:SS'), '53');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('6', TO_DATE('2023-01-20 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:11:00', 'YYYY-MM-DD HH24:MI:SS'), '14');

INSERT
INTO 		Attends(MID, sessionDate, startTime, locker)
VALUES	('6', TO_DATE('2023-01-22 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), '5');

INSERT
INTO		OccursIn(sessionDate, startTime, ANumber, AType)
VALUES	(TO_DATE('2022-08-14 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:23:05', 'YYYY-MM-DD HH24:MI:SS'), '2', 'Cardio');

INSERT
INTO		OccursIn(sessionDate, startTime, ANumber, AType)
VALUES	(TO_DATE('2022-08-15 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:23:05', 'YYYY-MM-DD HH24:MI:SS'), '3', 'Turf');

INSERT
INTO		Utilizes(EID, sessionDate, startTime)
VALUES	('5', TO_DATE('2022-08-16 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 17:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		Utilizes(EID, sessionDate, startTime)
VALUES	('4', TO_DATE('2022-08-17 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		Leads(SID, sessionDate, startTime)
VALUES	('1', TO_DATE('2022-08-14 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 15:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		Leads(SID, sessionDate, startTime)
VALUES	('1', TO_DATE('2022-08-15 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 16:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		Leads(SID, sessionDate, startTime)
VALUES	('4', TO_DATE('2022-08-16 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 17:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		Leads(SID, sessionDate, startTime)
VALUES	('6', TO_DATE('2022-08-17 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 18:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		Leads(SID, sessionDate, startTime)
VALUES	('5', TO_DATE('2022-08-18 00:00:01', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2000-01-01 19:23:05', 'YYYY-MM-DD HH24:MI:SS'));

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('1', '1');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('1', '2');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('1', '3');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('2', '1');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('3', '2');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('3', '3');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('4', '1');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('5', '1');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('6', '1');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('7', '1');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('7', '2');

INSERT
INTO		WorksOn(SID, FNumber)
VALUES	('7', '3');

INSERT
INTO		Trains(MID, SID)
VALUES	('4', '2');

INSERT
INTO		Trains(MID, SID)
VALUES	('6', '1');

INSERT
INTO		Uses(MID, EID)
VALUES	('1', '1');

INSERT
INTO		Uses(MID, EID)
VALUES	('2', '6');

INSERT
INTO		Uses(MID, EID)
VALUES	('3', '7');

INSERT
INTO		Uses(MID, EID)
VALUES	('6', '2');

INSERT
INTO		Requires(clearanceLevel, FNumber)
VALUES	('1', '1');

INSERT
INTO		Requires(clearanceLevel, FNumber)
VALUES	('1', '2');

INSERT
INTO		Requires(clearanceLevel, FNumber)
VALUES	('2', '3');