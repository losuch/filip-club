package de.mlosoft.filipclub.error;

public enum ErrorCode {

	NOT_IMPLEMENTED("Not implemented"), NO_DATA_GIVEN("No data found"),
	UNDEFINED_ROLE("Undefined role"), DB_ERROR("Database error"),
	UNKNOWN_ERROR("Unknow error"), ACCESS_DENIED("Access denied"),
	USER_ALREADY_EXISTS("Error: Email is already in use!"), USER_NOT_FOUND("User not found!");

	public final String errorCode;

	private ErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}