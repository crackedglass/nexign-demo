INSERT INTO subscribers(subscriber_id, number) 
VALUES 
    (1, '12345623'),
    (2, '78910113'),
    (3, '12131415');

INSERT INTO cdrs(cdr_id, call_type, caller_subscriber_id, receiver_subscriber_id, start_timestamp, end_timestamp)
VALUES
    (1, 'INCOMING', 1, 2, '2025-01-01 10:00:00+00', '2025-01-01 10:10:00+00'),
    (2, 'OUTCOMING', 2, 3, '2025-01-01 12:00:00+00', '2025-01-01 12:15:00+00');