package com.pixelsapphire.silicon.io;

import org.jetbrains.annotations.Nullable;

/**
 * Signals that an I/O exception has occurred. This unchecked version of {@link java.io.IOException}
 * is necessary for methods that override non-throwing methods.
 */
@SuppressWarnings("unused")
public class SiliconIOException extends RuntimeException {

    /**
     * Constructs a new {@code SiliconIOException} with {@code null} as its detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to {@link #initCause(Throwable)}.
     */
    public SiliconIOException() {
        super();
    }

    /**
     * Constructs a new {@code SiliconIOException} with the specified detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause(Throwable)}.
     */
    public SiliconIOException(@Nullable String message) {
        super(message);
    }

    /**
     * Constructs a new {@code SiliconIOException} with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
     *
     * @param cause the cause, which is saved for later retrieval by the {@link #getCause()} method
     *              ({@code null} is permitted, and indicates that the cause is nonexistent or unknown)
     */
    public SiliconIOException(@Nullable Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and cause. Note that the detail
     * message associated with cause is not automatically incorporated in this runtime exception's detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause, which is saved for later retrieval by the {@link #getCause()} method
     *                ({@code null} is permitted, and indicates that the cause is nonexistent or unknown)
     */
    public SiliconIOException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause              the cause, which is saved for later retrieval by the {@link #getCause()} method
     * @param enableSuppression  whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public SiliconIOException(@Nullable String message, @Nullable Throwable cause,
                              boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
