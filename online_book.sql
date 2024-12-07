
CREATE TABLE User(
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT '',
    username VARCHAR(10) NOT NULL,
    password VARCHAR(100) NOT NULL DEFAULT '',
    role_id INT,
    created_at DATETIME,
    updated_at DATETIME,
    is_active TINYINT(1) DEFAULT 1
);
CREATE TABLE Roles(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL 
);
ALTER TABLE User ADD FOREIGN KEY (role_id) REFERENCES Roles (id);

CREATE TABLE tokens(
    id int PRIMARY KEY AUTO_INCREMENT,
    token varchar(255) UNIQUE NOT NULL,
    token_type varchar(50) NOT NULL,
    expiration_date DATETIME,
    revoked tinyint(1) NOT NULL,
    expired tinyint(1) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Audience (
    id INT,
    friend_id INT,
    status VARCHAR(20) DEFAULT 'Accepted',
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id, friend_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK(user_id < friend_id)
);

CREATE TABLE Author (
    authorID INT PRIMARY KEY AUTO_INCREMENT,
    id INT UNIQUE NOT NULL,
    bio TEXT,
    IDCard VARCHAR(255),
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Book (
    bookID INT PRIMARY KEY AUTO_INCREMENT,
    bTitle VARCHAR(255) NOT NULL,
    bDescription TEXT,
    coverImage VARCHAR(255),
    publishYear INT
);

-- Đặt hàng - orders
CREATE TABLE Orders(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES User(id),
    fullname VARCHAR(100) DEFAULT '',
    email VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money FLOAT CHECK(total_money >= 0)
);
-- xóa 1 đơn hàng => xóa mềm => thêm trường active
ALTER TABLE Orders ADD COLUMN active TINYINT(1);
-- Trạng thái đơn hàng chỉ đc phép nhận "một số giá trị cụ thể"
ALTER TABLE Orders 
MODIFY COLUMN status ENUM('pending', 'processing', 'cancelled')
COMMENT 'Trạng thái đơn hàng';

CREATE TABLE order_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    FOREIGN KEY (order_id) REFERENCES Orders (id),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES Book (bookID),
    price FLOAT CHECK(price >= 0),
    number_of_products INT CHECK(number_of_products > 0),
    total_money FLOAT CHECK(total_money >= 0),
    color VARCHAR(20) DEFAULT ''
);

CREATE TABLE ListOfReading (
    listID INT PRIMARY KEY AUTO_INCREMENT,
    id INT,
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Include (
    bookID INT,
    listID INT,
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (listID) REFERENCES ListOfReading(listID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (bookID, listID)
);

CREATE TABLE UserBookmarks (
    ubID INT PRIMARY KEY AUTO_INCREMENT,
    id INT,
    createDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Marks (
    ubID INT,
    bookID INT,
    FOREIGN KEY (ubID) REFERENCES UserBookmarks(ubID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (ubID, bookID)
);

CREATE TABLE Report (
    rpID INT PRIMARY KEY AUTO_INCREMENT,
    bookID INT,
    id INT,
    reportDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reportContent TEXT,
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Review (
    reviewID INT PRIMARY KEY AUTO_INCREMENT,
    id INT,
    bookID INT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Category (
    cateID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    cateDescription TEXT
);

CREATE TABLE Cate (
    bookID INT,
    cateID INT,
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (cateID) REFERENCES Category(cateID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (bookID, cateID)
);

CREATE TABLE Chapter (
    cID INT PRIMARY KEY AUTO_INCREMENT,
    bookID INT,
    cTitle VARCHAR(255),
    cContent TEXT,
    Fee DECIMAL(10, 2),
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Point (
    pointID INT PRIMARY KEY AUTO_INCREMENT,
    id INT,
    amount DECIMAL(10, 2),
    type VARCHAR(50),
    viewCount INT DEFAULT 0,
    adPoint DECIMAL(10, 2) DEFAULT 0,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payment (
    PayID INT PRIMARY KEY AUTO_INCREMENT,
    id INT,
    PayType VARCHAR(50),
    PayAmount DECIMAL(10, 2),
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE DepositWithdraw (
    PayID INT,
    pointID INT,
    isAuthor BOOLEAN,
    FOREIGN KEY (PayID) REFERENCES Payment(PayID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (pointID) REFERENCES Point(pointID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (PayID, pointID)
);

CREATE TABLE Claim (
    id INT,
    pointID INT,
    Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (pointID) REFERENCES Point(pointID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id, pointID)
);

CREATE TABLE WriteTranslate (
    bookID INT,
    authorID INT,
    FOREIGN KEY (bookID) REFERENCES Book(bookID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (authorID) REFERENCES Author(authorID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (bookID, authorID)
);

CREATE TABLE Payfor (
    pointID INT,
    payID INT,
    cID INT,
    FOREIGN KEY (pointID) REFERENCES Point(pointID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (payID) REFERENCES Payment(PayID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (cID) REFERENCES Chapter(cID) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (pointID, payID, cID)
);
