package ma.ismagi.inventoryservice.exception.model;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, int status) {}
