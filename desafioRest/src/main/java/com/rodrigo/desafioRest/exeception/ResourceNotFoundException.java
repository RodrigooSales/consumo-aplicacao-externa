package com.rodrigo.desafioRest.exeception;

public class ResourceNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}
