package br.com.magalu.servicewishlist.servicies.exceptions;

public class WishlistExceededException extends RuntimeException{

    public WishlistExceededException(String message) {
        super(message);
    }

    public WishlistExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
