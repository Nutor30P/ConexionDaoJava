/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionclientes.dao;

import com.mycompany.gestionclientes.models.Cliente;
import com.mysql.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pabue
 */
public class ClienteDao {

    public Connection conectar() throws ClassNotFoundException {
        String baseDeDatos = "java";
        String usuario = "root";
        String password = "";
        String hosting = "localhost";
        String puerto = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" + hosting + ":" + puerto + "/" + baseDeDatos + "?useSSL=false";

        Connection conexion = null;

        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conexion;

    }

    public void agregar(Cliente cliente) throws ClassNotFoundException {

        try {
            Connection conexion = conectar();

            String sql = "INSERT INTO clientes (id, nombre, apellido, email, telefono) VALUES (NULL, '" + cliente.getNombre() + ""
                    + "', '" + cliente.getApellido()
                    + "', '" + cliente.getEmail()
                    + "', '"
                    + cliente.getTelefono()
                    + "')";
            Statement statement = conexion.createStatement();
            statement.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Cliente> listar() throws ClassNotFoundException {

        List<Cliente> listado = new ArrayList<>();

        try {
            Connection conexion = conectar();

            String sql = "SELECT * FROM `clientes`";

            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql);

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido(resultado.getString("apellido"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setTelefono(resultado.getString("telefono"));
                listado.add(cliente);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listado;
    }

    public void eliminar(String id) throws ClassNotFoundException {

        try {
            Connection conexion = conectar();

            String sql = "DELETE FROM `clientes` WHERE `clientes`.`id` = " + id;
            Statement statement = conexion.createStatement();
            statement.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar(Cliente cliente) throws ClassNotFoundException {

        try {
            Connection conexion = conectar();

            String sql = "UPDATE `clientes` SET  `nombre` = '" + cliente.getNombre() + "', `apellido` = '" + cliente.getApellido() + "', `email` = '" + cliente.getEmail() + "', `telefono` = '" + cliente.getTelefono() + "' WHERE `clientes`.`id` = "+cliente.getId()+";";

            Statement statement = conexion.createStatement();
            statement.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void guardar(Cliente a) throws ClassNotFoundException {
        if(StringUtils.isEmptyOrWhitespaceOnly(a.getId())){
            agregar(a);
        }else{
            actualizar(a);
        }
    }
}
