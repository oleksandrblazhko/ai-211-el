-- Команди створення таблиць
CREATE TABLE UserInformation (
    UserID INT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE,
    Age SMALLINT CHECK (Age >= 0 AND Age <= 99)
);

CREATE TABLE User (
    UserID INT PRIMARY KEY,
    Login VARCHAR(20) CHECK (LENGTH(Login) < 20),
    Password VARCHAR(20) CHECK (LENGTH(Password) < 20)
);

CREATE TABLE HealthCenterEmployee (
    EmployeeID INT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Age SMALLINT CHECK (Age >= 0 AND Age <= 99),
    Position VARCHAR(255) CHECK (LENGTH(Position) > 5)
);

CREATE TABLE HealthQuestion (
    QuestionID INT PRIMARY KEY,
    QuestionText TEXT CHECK (LENGTH(QuestionText) > 0 AND LENGTH(QuestionText) < 500),
    Date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE HealthAnswer (
    AnswerID INT PRIMARY KEY,
    AnswerText TEXT CHECK (LENGTH(AnswerText) > 0 AND LENGTH(AnswerText) < 500),
    Date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE HealthData (
    DataID INT PRIMARY KEY,
    UserID INT,
    Date DATE CHECK (Date <= CURRENT_DATE),
    HealthDataText TEXT CHECK (LENGTH(HealthDataText) < 500),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);
