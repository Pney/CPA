CREATE EVENT delete_expired_tokens
	ON SCHEDULE EVERY 1 DAY
	STARTS CURDATE() + INTERVAL 8 HOUR
	DO
		DELETE FROM black_list_token WHERE date_expiration < DATE_SUB(curdate(), INTERVAL 8 HOUR);