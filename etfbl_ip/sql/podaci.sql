USE etfbl_ip;

INSERT INTO MANUFACTURER (Name, Country, Address, Phone, Fax, Email)
VALUES 
('Tesla', 'United States', '3500 Deer Creek Road, Palo Alto', '+1-800-123-4567', NULL, 'tesla@tesla.com'),
('Rimac', 'Croatia', 'Sveta Nedelja, Zagrebačka 6', '+385-1-234-5678', NULL, 'info@rimac.hr'),
('Nio', 'China', 'Pudong, Shanghai', NULL, NULL, 'nio@nio.com'),
('Volkswagen',     'Germany',  'Berliner Ring 2, Wolfsburg',                '+49-5361-9-0',        '+49-5361-9-99',      'info@volkswagen.com'),
('BYD',            'China',    'No. 3009 BYD Road, Pingshan, Shenzhen',     '+86-755-8988-8888',   NULL,                 'contact@byd.com'),
('Renault',        'France',   '13 Quai Alphonse Le Gallo, Boulogne-Billancourt', '+33-1-76840404', NULL,               'contact@renault.com'),
('Giant',          'Taiwan',   'No. 19 Anhe Rd., Taichung',                 '+886-42463-3000',    NULL,                 'service@giant.com.tw'),
('Specialized',    'United States', '15130 Concord Circle, Morgan Hill, CA','+1-408-779-6229',     NULL,                 'info@specialized.com'),
('Segway-Ninebot', 'United States', '14 Technology Dr, Bedford, NH',        '+1-603-222-6000',     NULL,                 'support@segway.com'),
('Xiaomi',         'China',    'No. 68 Qinghe Middle St, Haidian, Beijing', '+86-400-100-5678',    NULL,                 'mobility@xiaomi.com'),
('Yamaha',         'Japan',    '2500 Shingai, Iwata, Shizuoka',             '+81-538-32-1115',     NULL,                 'global@yamaha-motor.co.jp');

INSERT INTO VEHICLE
  (UID,       PurchasePrice, Model,          MANUFACTURER_ManufacturerID,
   Status,    PricePerSecond, X,       Y)
VALUES
  ('TESLA123', 80000.00, 'Model S',        1, 'AVAILABLE', 0.00104167, 17.1843, 44.7866),
  ('RIMAC456',100000.00, 'Nevera',         2, 'AVAILABLE',    0.00138889, 15.9819, 45.8150),

  ('GIANT001',   1200.00, 'Explore E+',    7, 'AVAILABLE', 0.00083333, 18.4131, 43.8563),
  ('SPEC002',    1500.00, 'Turbo Vado',    8, 'AVAILABLE', 0.00069444, 17.1910, 44.7722),

  ('SEGWAY01',    500.00, 'KickScooter MAX', 9, 'AVAILABLE', 0.00300000, 18.6938, 44.5460),
  ('XIAOMI04',    450.00, 'Mi Scooter 4',   10,'AVAILABLE',    0.00250000, 17.9125, 44.2264);

INSERT INTO CAR (VEHICLE_VehicleID, PurchaseDate, Description) VALUES
  (1, '2022-01-15', 'Electric luxury sedan'),
  (2, '2023-03-20', 'Electric hypercar');

INSERT INTO E_BIKE (VEHICLE_VehicleID, Autonomy) VALUES
  (3, 120), 
  (4, 100);

INSERT INTO E_SCOOTER (VEHICLE_VehicleID, MaxSpeed) VALUES
  (5, 25),    
  (6, 20);

INSERT INTO USER (Username, Password, FirstName, LastName)
VALUES 
('admin1', '12345678', 'Marko', 'Petrovic'),
('operator1', '12345678', 'Ivana', 'Jovanovic'),
('manager1', '12345678', 'Nenad', 'Popovic');

INSERT INTO EMPLOYEE (USER_UserID, Role)
VALUES
(1, 'ADMINISTRATOR'),
(2, 'OPERATOR'),
(3, 'MANAGER');

INSERT INTO USER (Username, Password, FirstName, LastName)
VALUES
('milan123', '12345678', 'Milan', 'Nikolich'),
('ana456', '12345678', 'Ana', 'Petrovic'),
('jelena789', '12345678', 'Jelena', 'Stojanovic'),
('nikola321', '12345678', 'Nikola', 'Djordjevic');

INSERT INTO CLIENT (USER_UserID, IDNumber, PassportNumber, Email, Phone, HasAvatarImage)
VALUES
(4, '123456789', NULL, 'milan@example.com', '+381641112233', 1),
(5, '987654321', NULL, 'ana@example.com', '+381641112244', 0),
(6, '112233445', NULL, 'jelena@example.com', '+381641112255', 1),
(7, '556677889', NULL, 'nikola@example.com', '+381641112266', 0);

INSERT INTO RENTAL
  (StartTime,              StartX,   StartY,
   EndTime,                EndX,     EndY,
   Duration, DriversLicense, PaymentCard, TotalAmount,
   VEHICLE_VehicleID, CLIENT_USER_UserID)
VALUES
('2025-04-15 08:00:00', 17.1843, 44.7866, 
 '2025-04-15 08:45:00', 17.2000, 44.7900, 
 2700, 'B2389451', '4333333333333333', 8.10, 5, 6),

('2025-03-02 18:30:00', 17.1843, 44.7866, 
 '2025-03-02 20:00:00', 17.2100, 44.8200, 
 5400, 'B1234567', '4111111111111111', 5.63, 1, 4),

('2025-02-10 06:15:00', 18.4131, 43.8563, 
 '2025-02-10 07:15:00', 18.4400, 43.8700, 
 3600, 'B7654321', '4222222222222',    3.00, 3, 5),

('2024-09-25 12:10:00', 15.9819, 45.8150, 
 '2024-09-25 15:10:00', 16.0000, 45.8200, 
10800, 'B4523891', '4333333333334444', 15.00, 2, 7),

('2024-01-14 09:00:00', 17.1910, 44.7722, 
 '2024-01-14 10:30:00', 17.2050, 44.7800, 
 5400, 'B8723110', '4555555555555555', 3.75, 4, 4),

('2023-06-05 14:45:00', 17.9125, 44.2264, 
 '2023-06-05 15:15:00', 17.9300, 44.2300, 
 1800, 'B9988776', '4999999999999999', 4.50, 6, 5),

('2023-02-11 10:00:00', 17.1843, 44.7866, 
 '2023-02-11 12:00:00', 17.2100, 44.8200, 
 7200, 'B1122334', '4111111111110000', 7.50, 1, 6),

('2022-11-23 17:20:00', 18.4131, 43.8563, 
 '2022-11-23 17:50:00', 18.4250, 43.8600, 
 1800, 'B5566778', '4222222222220000', 1.50, 3, 7);
 
INSERT INTO BREAKDOWN
  (Description,          BreakdownTime,        RepairTime,          VEHICLE_VehicleID)
VALUES
('Brake pad worn out',   '2024-07-10 15:00:00','2024-07-12 09:30:00', 1),
('Software glitch',      '2025-02-05 11:20:00','2025-02-05 12:00:00', 2),
('Seat post cracked',    '2024-11-21 08:45:00','2024-11-22 14:20:00', 3),
('Chain replacement',    '2023-03-14 10:00:00','2023-03-14 11:45:00', 4),
('Throttle sensor error','2025-01-28 09:10:00','2025-01-29 16:30:00', 5),
('Punctured tyre',       '2022-06-18 13:25:00','2022-06-18 14:05:00', 6),
('Battery connector loose','2024-02-02 06:40:00','2024-02-02 07:20:00',5),
('Headlight broken',     '2023-12-05 19:00:00','2023-12-06 12:30:00', 6);

INSERT INTO POST (Title, Content)
VALUES
('Welcome to our system!', 'Try out our new electric vehicle rental app.'),
('New vehicles available!', 'We have added new Tesla and Rimac cars to our fleet.'),
('Charging Network Expanded',
   'We have partnered with local municipalities to add 50 new public chargers across Banja Luka and Sarajevo. Enjoy faster top-ups wherever you ride.'),
  ('App Update 2.0.0 Released',
   'Dark mode, real‑time vehicle tracking and streamlined check‑out are now live. Update the app in the Play Store or App Store.'),
  ('Refer a Friend, Earn Credit',
   'Invite your friends via the “Refer” tab and get credit when they complete their first ride.'),
  ('Celebrate Earth Day with Us',
   'Ride an e‑bike on 22 April and we will plant a tree for every 100 km ridden by the community.'),
  ('Fleet Maintenance Completed',
   'All vehicles have undergone a full spring service for optimum safety and performance.'),
  ('Night Owl Rates Now Live',
   'From 22:00 – 06:00 every night, enjoy rentals at 25 % lower rates. Perfect for late-night commutes.');

INSERT INTO PROMOTION (Title, Description, StartsAt, EndsAt)
VALUES
('20% Student Discount', 'All students get a 20% discount on their first ride!', NOW() - INTERVAL 1 DAY, NOW() + INTERVAL 30 DAY),
('Free Weekend Ride', 'Enjoy 2 hours of free rides this weekend!', NOW() + INTERVAL 3 DAY, NOW() + INTERVAL 5 DAY),
  ('Earth Day 2025 – Ride Green',
   'On 22 April all e-bike rides are 50 % off. Go green!',
   '2025-04-22 00:00:00', '2025-04-22 23:59:59'),

  ('Spring Break 15% Off',
   'Take 15 % off every rental from 25 April to 4 May to celebrate the long weekend.',
   '2025-04-25 00:00:00', '2025-05-04 23:59:59'),

  ('Mother''s Day Special – Free Helmets',
   'Ride on 11 May and pick up a free safety helmet at our partner hubs.',
   '2025-05-11 00:00:00', '2025-05-11 23:59:59'),

  ('Night Rider 30% Discount',
   'All rentals that start between 22:00 and 06:00 are 30 % cheaper! Valid for the next 10 days.',
   NOW() + INTERVAL 1 DAY, NOW() + INTERVAL 11 DAY),

  ('Refer & Earn Bonus',
   'Earn double referral credit for every new user you bring in.',
   NOW(), NOW() + INTERVAL 60 DAY),

  ('Summer Kick-off – 2 h Free',
   'Start your summer adventures with 120 free minutes on your first rental in June.',
   '2025-06-01 00:00:00', '2025-06-07 23:59:59');


