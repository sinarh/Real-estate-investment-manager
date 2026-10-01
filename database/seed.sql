INSERT INTO users (username, password, security_question, security_answer)
VALUES (
    'demo',
    'pbkdf2$120000$AQIDBAUGBwgJCgsMDQ4PEA==$my8i9p1BbLrzhiCniPi13bkFwNL7AA+DakgnpJ7NIzE=',
    'What is your maternal grandmother''s maiden name?',
    'pbkdf2$120000$ERITFBUWFxgZGhscHR4fIA==$kCuZ/8WdYEJW+Y5xfmCW2vYcoByFH+fXzWAuqCNWp6M='
);

INSERT INTO ownedproperties (
    userid, propertyname, propertytype, country, province, city, yearbuilt,
    size_sqft, bedrooms, bathrooms, features, buyingprice, propertyvalue, date, expenses
) VALUES
    (1, 'Cedar Ridge', 'House', 'Canada', 'BC', 'Surrey', 2005, 1350, 3, 2, 'Pool, Garage', 250000, 335000, '2024-04-01', 18000),
    (1, 'Harbor Loft', 'Condo', 'Canada', 'BC', 'Vancouver', 2018, 780, 1, 1, 'Gym, Balcony', 560000, 625000, '2024-05-12', 9500),
    (1, 'Prairie Duplex', 'Townhouse', 'Canada', 'Alberta', 'Edmonton', 2013, 1600, 3, 3, 'Garage', 310000, 355000, '2024-06-03', 12000);

INSERT INTO interestedproperties (
    propertyname, propertytype, country, province, city, yearbuilt, proplink,
    size_sqft, bedrooms, bathrooms, features, buyingprice, realtorname,
    realtornumber, contactdate, responsereceived, userid
) VALUES (
    'Maple Walk', 'House', 'Canada', 'BC', 'Langley', 2000, 'https://example.com/maple-walk',
    1400, 3, 2, 'Patio, Driveway', 515000, 'Alex Morgan', '778-555-0142', CURRENT_DATE - INTERVAL '10 days', FALSE, 1
);
