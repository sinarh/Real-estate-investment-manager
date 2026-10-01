DROP TABLE IF EXISTS interestedproperties;
DROP TABLE IF EXISTS ownedproperties;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    security_answer VARCHAR(512) NOT NULL
);

CREATE TABLE ownedproperties (
    propertyid SERIAL PRIMARY KEY,
    userid INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    propertyname VARCHAR(120) NOT NULL,
    propertytype VARCHAR(80) NOT NULL,
    country VARCHAR(80) NOT NULL,
    province VARCHAR(80) NOT NULL,
    city VARCHAR(80) NOT NULL,
    yearbuilt INTEGER NOT NULL,
    size_sqft NUMERIC(12, 2) NOT NULL,
    bedrooms INTEGER NOT NULL,
    bathrooms INTEGER NOT NULL,
    features TEXT,
    buyingprice NUMERIC(14, 2) NOT NULL,
    propertyvalue NUMERIC(14, 2) NOT NULL,
    date DATE NOT NULL,
    expenses NUMERIC(14, 2) NOT NULL DEFAULT 0,
    UNIQUE (userid, propertyname)
);

CREATE TABLE interestedproperties (
    id SERIAL PRIMARY KEY,
    propertyname VARCHAR(120) NOT NULL,
    propertytype VARCHAR(80) NOT NULL,
    country VARCHAR(80) NOT NULL,
    province VARCHAR(80) NOT NULL,
    city VARCHAR(80) NOT NULL,
    yearbuilt INTEGER NOT NULL,
    proplink TEXT,
    size_sqft NUMERIC(12, 2) NOT NULL,
    bedrooms INTEGER NOT NULL,
    bathrooms INTEGER NOT NULL,
    features TEXT,
    buyingprice NUMERIC(14, 2) NOT NULL,
    realtorname VARCHAR(120),
    realtornumber VARCHAR(40),
    contactdate DATE NOT NULL,
    responsereceived BOOLEAN NOT NULL DEFAULT FALSE,
    userid INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (userid, propertyname)
);
