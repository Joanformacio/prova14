/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.dao.ProductoDao;
import com.example.demo.models.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.*;


/**
 *
 * @author Joan
 */
@Controller
@RequestMapping("/Productos")
public class ControladorRest {
    
    @Autowired
    private ProductoDao productosDAO;
    
    @GetMapping
    public ResponseEntity<List<Producto>> getProducto(){
        List<Producto> productos= productosDAO.findAll();
        
        return ResponseEntity.ok(productos);
    }
    
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        Producto newProducto = productosDAO.save(producto);
        return ResponseEntity.ok(newProducto);
    }
    
    @RequestMapping(value="{productoId}")
    public ResponseEntity<Producto> getProductoById(@PathVariable("productoId") int productoId){
        Optional<Producto> optionalProducto=productosDAO.findById(productoId);
        if (optionalProducto.isPresent()){
            return ResponseEntity.ok(optionalProducto.get());
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    
    @DeleteMapping(value="{productoId}")
    public ResponseEntity<Producto> deleteProductoId(@PathVariable("productoId") int productoId){
        productosDAO.deleteById(productoId);
        
        return ResponseEntity.ok(null);
    }
    
    @PutMapping
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto){
        Optional<Producto> optionalProducto= productosDAO.findById(producto.getId());
        if(optionalProducto.isPresent()){
            Producto updateProducto = optionalProducto.get();
            updateProducto.setName(producto.getName());
            productosDAO.save(updateProducto);
            
            return ResponseEntity.ok(updateProducto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
