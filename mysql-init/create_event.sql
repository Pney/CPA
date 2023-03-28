CREATE EVENT delete_expired_tokens
	ON SCHEDULE EVERY 1 DAY
	STARTS CURDATE() + INTERVAL 8 HOURS
	DO
		DELETE FROM black_list_token WHERE date_expiration < SUB_DATE(curdate(), INTERVAL 8 HOUR);