package com.example.service;


import java.util.List;

import com.example.entity.ComprobanteDePago;

public interface ComprobanteDePagoService {
	public ComprobanteDePago guardarComprobante(ComprobanteDePago comprobante);
	
    public List<ComprobanteDePago> listarTodos();
    
    public List<ComprobanteDePago> buscarPorDniONombre(String dato);
	
    void anularComprobante(Integer idComprobante);
    
    public ComprobanteDePago buscarPorId(Integer idComprobante);
}
