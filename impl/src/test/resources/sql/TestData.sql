INSERT INTO subscribers(subscriber_id, number) 
VALUES 
    (1, '12345623'),
    (2, '78910113'),
    (3, '12131415');

INSERT INTO cdrs(cdr_id, call_type, caller_subscriber_id, receiver_subscriber_id, start_timestamp, end_timestamp)
VALUES
    (DEFAULT, '02', 
    (SELECT subscriber_id FROM subscribers WHERE number = '12345623'),
    (SELECT subscriber_id FROM subscribers WHERE number = '78910113'),
     '2025-01-01 10:00:00+00', '2025-01-01 10:10:00+00'),
    (DEFAULT, '01',
    (SELECT subscriber_id FROM subscribers WHERE number = '78910113'),
    (SELECT subscriber_id FROM subscribers WHERE number = '12131415'),
     '2025-01-01 12:00:00+00', '2025-01-01 12:15:00+00');